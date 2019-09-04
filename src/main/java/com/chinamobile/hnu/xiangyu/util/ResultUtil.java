/**
 * 
 */
package com.chinamobile.hnu.xiangyu.util;

import com.chinamobile.hnu.xiangyu.util.dto.ResponseDto;

/**
 * @author The Old Man and the Sea
 *
 * 2018年3月22日
 */
public class ResultUtil {

	
	/****
	 * 用户没有登录
	 * @return
	 */
	public static ResponseDto unLogin(){
		ResponseDto dto = new ResponseDto();
		dto.setValue(1,"你还没有登录");
		return dto;
	}
	
	/***
	 * 服务器异常
	 * @return
	 */
	public static ResponseDto serviceException(){
		ResponseDto dto = new ResponseDto();
		dto.setValue(2,"操作失败");
		return dto;
	}
	
	
	/***
	 * 返回数据
	 * @param code
	 * @param msg
	 * @param data
	 * @return
	 */
	public static ResponseDto result(int code,String msg,Object data){
		ResponseDto dto = new ResponseDto();
		dto.setValue(code,msg,data);
		return dto;
	}
	
	/***
	 * 返回数据
	 * @param code
	 * @param msg
	 * @param data
	 * @return
	 */
	public static ResponseDto result(int code,String msg){
		ResponseDto dto = new ResponseDto();
		dto.setValue(code,msg);
		return dto;
	}
}
