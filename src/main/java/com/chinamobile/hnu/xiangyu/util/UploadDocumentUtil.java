/**
 * 
 */
package com.chinamobile.hnu.xiangyu.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.chinamobile.hnu.xiangyu.util.dto.ResponseDto;

/**
 * 存储文件的工具类型
 * 
 * @author songcl
 * @date 2018年5月25日
 */
@Component
public class UploadDocumentUtil {
	private static final Logger logger = LoggerFactory
			.getLogger(UploadDocumentUtil.class);

	@Autowired
	private DiskStorage storage;

	/**
	 * 上传到WebApp目录下的文件
	 * 
	 * @param request
	 * @param input
	 * @param fileName
	 * @param dirName
	 *            子目录
	 * @param prefix
	 * @return
	 * @throws FileUploadException
	 * @throws IOException
	 */
	// public static String upload(HttpServletRequest request, InputStream
	// input,
	// String fileName, String dirName,
	// String prefix) throws IOException {
	// // 文件是否存在
	// if (!ServletFileUpload.isMultipartContent(request)) {
	// logger.error("please select upload file");
	// throw new IllegalArgumentException("please select upload file");
	// }
	//
	// String savePath =
	// request.getSession().getServletContext().getRealPath("/") +
	// dirName + "/";
	// // 检查目录
	// File uploadDir = new File(savePath);
	// if (!uploadDir.exists()) {
	// uploadDir.mkdirs();
	// }
	//
	// if (!uploadDir.isDirectory()) {
	// logger.error("please select upload file, it is dir");
	// return null;
	// }
	//
	// // 检查目录写权限
	// if (!uploadDir.canWrite()) {
	// logger.error("upload file, cannot write to this dir");
	// throw new IOException("upload file, cannot write to this dir");
	// }
	//
	// // 文件保存目录URL
	// String saveUrl = request.getContextPath() + "/" + dirName + "/";
	// // 按日期分子目录存储
	// SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	// String ymd = sdf.format(new Date());
	// savePath += ymd + "/";
	// saveUrl += ymd + "/";
	// File dirFile = new File(savePath);
	// if (!dirFile.exists()) {
	// dirFile.mkdirs();
	// }
	//
	// String fileExt = fileName.substring(fileName.lastIndexOf(".") +
	// 1).toLowerCase();
	// SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
	// String newFileName = prefix + "_" + df.format(new Date()) + "_" + new
	// Random().nextInt(1000) + "." + fileExt;
	//
	// saveUrl += newFileName;
	//
	// logger.info("save file, name:" + saveUrl);
	//
	// FileOutputStream fos = new FileOutputStream(savePath + newFileName);
	// byte[] buffer = new byte[2048];
	//
	// try {
	// int num = 0;
	// while ((num = input.read(buffer)) > 0) {
	// fos.write(buffer, 0, num);
	// }
	// } catch (IOException ex) {
	// logger.error("save file exception:" + saveUrl, ex);
	// throw ex;
	// } finally {
	// try {
	// if (fos != null)
	// fos.close();
	// } catch (IOException e) {
	// }
	// }
	//
	// return saveUrl;
	// }

	/**
	 * 上传到其他指定目录的文件
	 * 
	 * @param request
	 * @param input
	 * @param fileName
	 * @param baseDir
	 *            带"/"的文件夹全路径
	 * @param dirName
	 *            子目录
	 * @param prefix
	 * @return
	 * @throws FileUploadException
	 * @throws IOException
	 */
	public static String uploadPrivate(HttpServletRequest request,
			InputStream input, String fileName, String baseDir, String dirName,
			String prefix) throws IOException {
		// 文件是否存在
		if (!ServletFileUpload.isMultipartContent(request)) {
			logger.error("please select upload file");
			throw new IllegalArgumentException("please select upload file");
		}

		String savePath = baseDir + dirName + "/";
		// 检查目录
		File uploadDir = new File(baseDir);
		if (!uploadDir.exists()) {
			uploadDir.mkdirs();
		}

		if (!uploadDir.isDirectory()) {
			logger.error("please select upload file, it is dir uploadDir{}",
					baseDir);
			return null;
		}

		// 检查目录写权限
		if (!uploadDir.canWrite()) {
			logger.error("upload file, cannot write to this dir");
			throw new IOException("upload file, cannot write to this dir");
		}

		// 文件保存目录URL
		String saveUrl = dirName + "/";
		// 按日期分子目录存储
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String ymd = sdf.format(new Date());
		savePath += ymd + "/";
		saveUrl += ymd + "/";
		File dirFile = new File(savePath);
		if (!dirFile.exists()) {
			dirFile.mkdirs();
		}

		String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1)
				.toLowerCase();
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		String newFileName = prefix + "_" + df.format(new Date()) + "_"
				+ new Random().nextInt(1000) + "." + fileExt;

		saveUrl += newFileName;

		logger.info("save file, name:{},path:{}", saveUrl, savePath);

		FileOutputStream fos = new FileOutputStream(savePath + newFileName);
		byte[] buffer = new byte[2048];

		try {
			int num = 0;
			while ((num = input.read(buffer)) > 0) {
				fos.write(buffer, 0, num);
			}
		} catch (IOException ex) {
			logger.error("save file exception:{}", saveUrl, ex);
			throw ex;
		} finally {
			try {
				if (fos != null)
					fos.close();
			} catch (IOException e) {
			}
		}

		return saveUrl;
	}

	/**
	 * 上传文件
	 * 
	 * @param request
	 * @param file
	 * @param fileName
	 * @param dirName
	 * @return
	 * @throws FileUploadException
	 * @throws IOException
	 */
	// public static String upload(HttpServletRequest request, File file, String
	// fileName, String dirName)
	// throws FileUploadException, IOException {
	// String savePath =
	// request.getSession().getServletContext().getRealPath("/") +
	// dirName + "/";
	// File test = new File(savePath);
	// if (!test.exists()) {
	// test.mkdirs();
	// }
	//
	// // 文件保存目录URL
	// String saveUrl = request.getContextPath() + "/" + dirName + "/";
	// // 定义允许上传的文件扩展名
	// // HashMap<String, String> extMap = new HashMap<String, String>();
	// // extMap.put("image", "gif,jpg,jpeg,png,bmp");
	// // extMap.put("flash", "swf,flv");
	// // extMap.put("media",
	// // "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb");
	// // extMap.put("file",
	// // "doc,docx,xls,xlsx,ppt,htm,html,txt,zip,rar,gz,bz2");
	//
	// // 文件是否存在
	// if (!ServletFileUpload.isMultipartContent(request)) {
	// logger.error("please select upload file");
	// return null;
	// }
	//
	// // 检查目录
	// File uploadDir = new File(savePath);
	// if (!uploadDir.isDirectory()) {
	// logger.error("please select upload file, it is dir");
	// return null;
	// }
	//
	// // 检查目录写权限
	// if (!uploadDir.canWrite()) {
	// logger.error("upload file, cannot write to this dir");
	// return null;
	// }
	//
	// // String dirName = request.getParameter("dir");
	// // if (dirName == null) {
	// // dirName = "image";
	// // }
	// // if (!extMap.containsKey(dirName)) {
	// // logger.error("upload file, dir is error");
	// // return null;
	// // }
	//
	// // 创建文件夹
	// // savePath += dirName + "/";
	// // saveUrl += dirName + "/";
	// // File saveDirFile = new File(savePath);
	// // if (!saveDirFile.exists()) {
	// // saveDirFile.mkdirs();
	// // }
	//
	// // 按日期分子目录存储
	// SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	// String ymd = sdf.format(new Date());
	// savePath += ymd + "/";
	// saveUrl += ymd + "/";
	// File dirFile = new File(savePath);
	// if (!dirFile.exists()) {
	// dirFile.mkdirs();
	// }
	//
	// String fileExt = fileName.substring(fileName.lastIndexOf(".") +
	// 1).toLowerCase();
	// // if (!Arrays.<String>
	// // asList(extMap.get(dirName).split(",")).contains(fileExt)) {
	// // logger.error("upload file, unsupport file:" + fileExt);
	// // return null;
	// // }
	// SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
	// String newFileName = df.format(new Date()) + "_" + new
	// Random().nextInt(1000)
	// + "." + fileExt;
	//
	// saveUrl += newFileName;
	//
	// logger.info("save file, name:" + saveUrl);
	//
	// FileOutputStream fos = new FileOutputStream(savePath + newFileName);
	// byte[] buffer = new byte[2048];
	// InputStream in = new FileInputStream(file);
	// try {
	// int num = 0;
	// while ((num = in.read(buffer)) > 0) {
	// fos.write(buffer, 0, num);
	// }
	// } catch (IOException ex) {
	// throw ex;
	// } catch (Exception e) {
	// logger.error("save file exception:" + saveUrl, e);
	// return null;
	// } finally {
	// try {
	// if (in != null)
	// in.close();
	// if (fos != null)
	// fos.close();
	// } catch (IOException e) {
	// }
	// }
	//
	// return saveUrl;
	// }

	/***
	 * 上传文件
	 * 
	 * @param request
	 * @param uid
	 *            用户id
	 * @param subcatalog
	 *            子目录
	 * @return
	 * @throws Exception
	 */
	public ResponseDto saveFile(HttpServletRequest request, String uid,
			String subcatalog, String[] fileTypes) throws Exception {

		return saveFile(request, uid, subcatalog, fileTypes, 1024 * 1024 * 2);
	}

	public ResponseDto saveFile(HttpServletRequest request, String uid,
			String subcatalog, String[] fileTypes, Integer fileSize)
			throws Exception {

		if (!ServletFileUpload.isMultipartContent(request)) {
			return ResultUtil.result(1, "文件不能为空");
		}
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile file = multipartRequest.getFile("file");
		if (file == null) {
			return ResultUtil.result(1, "文件不能为空");
		} else if (file.getSize() > fileSize) {
			return ResultUtil.result(2, "文件过大");
		}
		String fileName = file.getOriginalFilename();
		String fileType = fileName.substring(fileName.lastIndexOf(".") + 1,
				fileName.length());
		String fileId = null;
		int temp = 0;
		for (String type : fileTypes) {
			if (type.equals(fileType)) {
				temp++;
				break;
			}
		}
		if (temp != 0) {
			fileId = UploadDocumentUtil.uploadPrivate(request,
					file.getInputStream(), file.getOriginalFilename(),
					storage.getBasePublicDir(), subcatalog, uid);
			if (StringUtils.isBlank(fileId)) {
				logger.info("文件夹不正确");
				return ResultUtil.result(2, "文件上传失败");
			}
		} else {
			return ResultUtil.result(2, "文件上传失败,文件格式不正确");
		}
		return ResultUtil.result(0, "操作成功", fileId);
	}

	/***
	 * 批量上传文件
	 * 
	 * @param request
	 * @param uid
	 *            用户id
	 * @param subcatalog
	 *            子目录
	 * @return
	 * @throws Exception
	 */
	public ResponseDto savebatchFile(HttpServletRequest request, String uid,
			String subcatalog, String[] fileTypes) throws Exception {

		if (!ServletFileUpload.isMultipartContent(request)) {
			return ResultUtil.result(1, "文件不能为空");
		}
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		List<MultipartFile> files = multipartRequest.getFiles("files");
		if (files.size() <= 0) {
			return ResultUtil.result(1, "文件不能为空");
		}
		List<String> fileIds = new ArrayList<String>();
		String fileName = null;
		String fileType = null;
		String fileId = null;

		List<MultipartFile> fileList = new ArrayList<>();

		int index = 1;
		for (MultipartFile file : files) {
			if (file.getSize() > 1024 * 1024 * 2) {
				return ResultUtil.result(2, "文件" + index + "过大");
			}
			index++;
			fileName = file.getOriginalFilename();
			fileType = fileName.substring(fileName.lastIndexOf(".") + 1,
					fileName.length());
			int temp = 0;
			for (String type : fileTypes) {
				if (type.equals(fileType)) {
					temp++;
					break;
				}
			}
			if (temp != 0) {
				fileList.add(file);
			} else {
				return ResultUtil.result(2, "文件上传失败,文件格式不正确");
			}
		}

		for (MultipartFile multipartFile : fileList) {
			fileId = UploadDocumentUtil.uploadPrivate(request,
					multipartFile.getInputStream(),
					multipartFile.getOriginalFilename(),
					storage.getBasePublicDir(), subcatalog, uid);
			if (StringUtils.isBlank(fileId)) {
				logger.info("文件夹不正确");
				return ResultUtil.result(2, "文件上传失败");
			}
			fileIds.add(fileId);
		}

		return ResultUtil.result(0, "操作成功", fileIds);
	}

	/**
	 * 组装成默认有效时间内的访问路径
	 * 
	 * @param fileId
	 * @param validseconds
	 * @return
	 */
	public static String buildPublicPath(String fileId) {
		return "/info/file/pub.do?fileId=" + fileId;
	}

	// http://139.224.233.10

	/**
	 * 组装成默认有效时间内的访问路径
	 * 
	 * @param fileId
	 * @param validseconds
	 * @return
	 */
	public static String buildPublicPathURL(String fileId) {
		return Const.IMG_URL + "/info/file/pub.do?fileId=" + fileId;
	}

	/**
	 * 组装成默认有效时间内的访问路径
	 * 
	 * @param fileId
	 * @param validseconds
	 * @return
	 */
	public static String buildPrivatePath(String fileId) {
		return "/info/file/my.do?fileId=" + fileId;
	}

}
