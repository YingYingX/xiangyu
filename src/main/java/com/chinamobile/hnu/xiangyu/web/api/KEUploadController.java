/**
 * Copyright ©2016, 长沙豆子信息技术有限公司, All rights reserved.
 */
package com.chinamobile.hnu.xiangyu.web.api;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chinamobile.hnu.xiangyu.user.service.AuthTokenService;
import com.chinamobile.hnu.xiangyu.util.UploadDocumentUtil;
import com.chinamobile.hnu.xiangyu.util.dto.ResponseDto;

/**
 * KEUpload File插件
 * 
 * @author douzisong
 * @date 2017年5月7日
 */
@Controller
public class KEUploadController {
	private static final Logger logger = Logger
			.getLogger(KEUploadController.class);

	private String FWB = "fwb";

	@Autowired
	private UploadDocumentUtil fileUtil;

	@Autowired
	private AuthTokenService tokenService;

	@RequestMapping("/api/base/uploadfile.do")
	@ResponseBody
	public String upload(HttpServletRequest request,
			HttpServletResponse response) throws IOException, Exception {
		PrintWriter out = response.getWriter();

		// 定义允许上传的文件扩展名
		HashMap<String, String> extMap = new HashMap<String, String>();
		extMap.put("image", "gif,jpg,jpeg,png,bmp");
		extMap.put("flash", "swf,flv");
		extMap.put("media", "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb");
		extMap.put("file", "doc,docx,xls,xlsx,ppt,htm,html,txt,zip,rar,gz,bz2");

		response.setContentType("text/html; charset=UTF-8");
		if (!ServletFileUpload.isMultipartContent(request)) {
			out.print(getError("请选择文件。"));
			return "err";
		}

		String dirName = request.getParameter("dir");
		if (dirName == null) {
			dirName = "image";
		}
		if (!extMap.containsKey(dirName)) {
			out.print(getError("目录名不正确。"));
			return "err";
		}

		// String fileName = request.getOriginalFilename();
		// String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1)
		// .toLowerCase();
		// if (!Arrays.<String> asList(extMap.get(dirName).split(",")).contains(
		// fileExt)) {
		// out.print(getError("上传文件扩展名是不允许的扩展名。\n只允许" + extMap.get(dirName)
		// + "格式。"));
		//
		// }
		ResponseDto dto = fileUtil.saveFile(request, "0", FWB, new String[] {
				"jpg", "png" });
		String url = "";
		if (dto.getCode() == 2) {
			return "上传文件失败";
		} else if (dto.getCode() == 0) {
			String fileId = (String) dto.getData();
			url = UploadDocumentUtil.buildPublicPathURL(fileId);
		}
		logger.info(">>>>>>>>>>>url:" + url);

		JSONObject obj = new JSONObject();
		obj.put("error", 0);
		obj.put("url", url);
		out.print(obj.toString());

		return null;
		// SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		// UploadDocumentUtil.String path = storageService.getRootfolder();
		// String newFileName = "";
		// if (dirName.equals("file")) {
		// newFileName = ConstantDefine.FOLDER_RICHTXT + "/"
		// + df.format(new Date()) + "/" + new Date().getTime()
		// + fileName.substring(fileName.indexOf("."));
		// } else {
		// newFileName = ConstantDefine.FOLDER_RICHTXT + "/"
		// + df.format(new Date()) + "/" + new Date().getTime()
		// + fileName.substring(fileName.indexOf("."));
		// }

		// logger.info("save to file:" + newFileName);
		// File targetFile = new File(path, newFileName);
		// if (!targetFile.exists()) {
		// targetFile.mkdirs();
		// }
		// // 保存
		// try {
		// file.transferTo(targetFile);
		// } catch (Exception e) {
		// e.printStackTrace();
		//
		// }

	}

	private String getError(String message) {
		JSONObject obj = new JSONObject();
		obj.put("error", 1);
		obj.put("message", message);
		return obj.toString();
	}
}
