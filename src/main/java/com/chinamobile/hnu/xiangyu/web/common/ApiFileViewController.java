/**
 * 
 */
package com.chinamobile.hnu.xiangyu.web.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chinamobile.hnu.xiangyu.user.pojo.UserAccount;
import com.chinamobile.hnu.xiangyu.user.service.AuthTokenService;
import com.chinamobile.hnu.xiangyu.util.DiskStorage;
import com.chinamobile.hnu.xiangyu.util.ResultUtil;
import com.chinamobile.hnu.xiangyu.util.UploadDocumentUtil;
import com.chinamobile.hnu.xiangyu.util.dto.ResponseDto;

/**
 * 文件读取
 * 
 * @author songcl
 * @date 2018年5月25日
 */
@Controller
@RequestMapping("/api/info/file")
public class ApiFileViewController {
	private final static Logger logger = LoggerFactory
			.getLogger(ApiFileViewController.class);

	@Value("${constant.logo}")
	private String LOGO;
	@Value("${constant.cover}")
	private String COVER;
	@Value("${constant.qrcode}")
	private String QRCODE;
	@Value("${constant.head}")
	private String HEAD;

	@Autowired
	private UploadDocumentUtil fileUtil;

	@Autowired
	private AuthTokenService tokenService;

	@Autowired
	private DiskStorage storage;

	/**
	 * 读取私有文件
	 * 
	 * @param request
	 * @param response
	 * @param fileId
	 * @throws IOException
	 */
	@RequestMapping(value = "/my.do")
	void myfile(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "fileId", required = true) String fileId)
			throws IOException {
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			logger.info("not login download private file:{}", fileId);
			return;
		}

		if (StringUtils.isBlank(fileId)) {
			return;
		}

		String fullPath = storage.getBasePrivateDir() + fileId;
		File f = new File(fullPath);
		logger.debug("read private file:{}, file path:{}", fileId, f.getPath());
		if (!f.exists()) {
			// 如果文件不存在，则直接返回
			logger.error("download private file, file not exist:{}", fileId);
			throw new FileNotFoundException(fileId);
		}

		String fileExt = fileId.substring(fileId.lastIndexOf(".") + 1)
				.toLowerCase();
		if ("png".equals(fileExt)) {
			response.setContentType("image/png");
		} else if ("jpg".equals(fileExt) || ".jpeg".equals(fileExt)) {
			response.setContentType("image/jpeg");
		} else if ("gif".equals(fileExt)) {
			response.setContentType("image/gif");
		} else if ("pdf".equals(fileExt)) {
			response.setContentType("application/pdf");
		} else if ("doc".equals(fileExt) || "docx".equals(fileExt)) {
			response.setContentType("application/x-download");
		}

		OutputStream out = response.getOutputStream();
		byte[] buffer = new byte[2048];
		FileInputStream input = new FileInputStream(f);

		// 依次读取文件内容并输出
		try {
			int num = 0;
			while ((num = input.read(buffer)) > 0) {
				out.write(buffer, 0, num);
			}
		} catch (IOException ex) {
			logger.error("read private file exception:{}", fileId, ex);
			throw ex;
		} finally {
			try {
				if (input != null)
					input.close();
			} catch (IOException e) {
			}
		}

		out.flush();
		out.close();
	}

	/**
	 * 读取公共文件
	 * 
	 * @param request
	 * @param response
	 * @param fileId
	 * @throws IOException
	 */
	@RequestMapping(value = "/pub.do")
	void pub(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "fileId", required = true) String fileId)
			throws IOException {
		if (StringUtils.isBlank(fileId)) {
			return;
		}

		String fullPath = storage.getBasePublicDir() + fileId;
		File f = new File(fullPath);
		logger.debug("read public file:{}, file path:{}", fileId, f.getPath());
		if (!f.exists()) {
			// 如果文件不存在，则直接返回
			logger.error("download public file, file not exist:{}", fileId);
			throw new FileNotFoundException(fileId);
		}

		String fileExt = fileId.substring(fileId.lastIndexOf(".") + 1)
				.toLowerCase();
		if ("png".equals(fileExt)) {
			response.setContentType("image/png");
		} else if ("jpg".equals(fileExt) || ".jpeg".equals(fileExt)) {
			response.setContentType("image/jpeg");
		} else if ("gif".equals(fileExt)) {
			response.setContentType("image/gif");
		} else if ("pdf".equals(fileExt)) {
			response.setContentType("application/pdf");
		} else if ("doc".equals(fileExt) || "docx".equals(fileExt)) {
			response.setContentType("application/x-download");
		} else if ("apk".equals(fileExt) || ".apk".equals(fileExt)) {
			response.setContentType("application/vnd.android.package-archive");
		}

		OutputStream out = response.getOutputStream();
		byte[] buffer = new byte[2048];
		FileInputStream input = new FileInputStream(f);

		// 依次读取文件内容并输出
		try {
			int num = 0;
			while ((num = input.read(buffer)) > 0) {
				out.write(buffer, 0, num);
			}
		} catch (IOException ex) {
			logger.error("read public file exception:{}", fileId, ex);
			throw ex;
		} finally {
			try {
				if (input != null)
					input.close();
			} catch (IOException e) {
			}
		}

		out.flush();
		out.close();
	}

	/****
	 * 上传公共文件
	 * 
	 * @param request
	 * @return
	 */
	@PostMapping("/save.do")
	@ResponseBody
	public ResponseDto sign(HttpServletRequest request,
			@RequestParam(required = true) Integer type) {
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		try {
			String temp = null;
			switch (type) {
			case 1:
				temp = LOGO;
				break;
			case 2:
				temp = COVER;
				break;
			case 3:
				temp = QRCODE;
				break;
			case 4:
				temp = HEAD;
				break;
			default:
				return ResultUtil.result(2, "保存失败，没有该类型文件");
			}
			if (temp == null) {
				logger.info("temp file url :{}", temp);
				return ResultUtil.result(2, "保存失败，请联系管理员");
			}
			ResponseDto dto = fileUtil.saveFile(request, user.getId()
					.toString(), temp, new String[] { "jpg", "png", "apk" },
					1024 * 1024 * 50);
			if (dto.getCode() != 0) {
				return ResultUtil.result(2, dto.getMsg().toString());
			} else {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("fileUrl", UploadDocumentUtil.buildPublicPath(dto
						.getData().toString()));
				map.put("fileId", dto.getData().toString());
				return ResultUtil.result(0, "保存成功", map);
			}
		} catch (Exception e) {
			logger.warn("save file exception：", e);
			return ResultUtil.serviceException();
		}
	}

}
