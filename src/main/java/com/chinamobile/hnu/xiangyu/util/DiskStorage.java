/**
 * 
 */
package com.chinamobile.hnu.xiangyu.util;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

/**
 * 磁盘存储的工具类，包括公共存储和私有存储
 * 
 * @author songcl
 * @date 2018年5月25日
 */
public class DiskStorage {

	/**
	 * 私有文件存储目录
	 */
	private String basePrivateDir;

	/**
	 * 公有文件存储目录
	 */
	private String basePublicDir;

	/**
	 * 存储私有读的文件
	 * 
	 * @param request
	 * @param input
	 * @param fileName
	 * @param dirName
	 *            子目录
	 * @param uid
	 * @return
	 * @throws IOException
	 */
	public String savePrivateFile(HttpServletRequest request, InputStream input, String fileName, String dirName,
			int uid) throws IOException {
		return UploadDocumentUtil.uploadPrivate(request, input, fileName, basePrivateDir, dirName, String.valueOf(uid));
	}

	/**
	 * 组装成默认有效时间内的访问路径
	 * 
	 * @param fileId
	 * @param validseconds
	 * @return
	 */
	public String buildPrivatePath(String fileId) {
		return "/info/file/my.do?fileId=" + fileId;
	}

	/**
	 * 存储公共读的文件
	 * 
	 * @param request
	 * @param input
	 *            文件输入流
	 * @param fileName
	 *            上传时的原始文件名
	 * @param dirName
	 *            指定存储目录
	 * @param uid
	 *            存储的用户
	 * @return 如果成功，返回存储的fileId
	 * @throws IOException
	 */
	public String savePublicFile(HttpServletRequest request, InputStream input, String fileName, String dirName,
			int uid) throws IOException {
		return UploadDocumentUtil.uploadPrivate(request, input, fileName, basePublicDir, dirName, String.valueOf(uid));
	}

	/**
	 * 通过fileId获取访问路径
	 * 
	 * @param fileId
	 * @return
	 */
	public String buildPublicPath(String fileId) {
		return "/info/file/pub.do?fileId=" + fileId;
	}

	public String getBasePrivateDir() {
		return basePrivateDir;
	}

	public void setBasePrivateDir(String basePrivateDir) {
		this.basePrivateDir = basePrivateDir;
	}

	public String getBasePublicDir() {
		return basePublicDir;
	}

	public void setBasePublicDir(String basePublicDir) {
		this.basePublicDir = basePublicDir;
	}
}
