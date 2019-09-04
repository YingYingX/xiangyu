/**
 * 
 */
package com.chinamobile.hnu.xiangyu.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import net.sf.json.JSONObject;

/**
 * @author The Old Man and the Sea
 *
 * 2018年2月10日
 */

public class ExceptionResolver implements HandlerExceptionResolver{
	
	private static final Logger logger = Logger.getLogger(ExceptionResolver.class);
	
	
	static{
		 //注册ConvertUtils,解决BeanUtils.copy时，属性为Date类型为空值时异常问题
		 ConvertUtils.register(new DateConvert(), java.util.Date.class);  
	     ConvertUtils.register(new DateConvert(), String.class);
	}

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.HandlerExceptionResolver#resolveException(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, java.lang.Exception)
	 */
	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception e) {
		// TODO Auto-generated method stub
		logger.error(request.getRequestURI()+" Exception",e);
		JSONObject json = new JSONObject();
		json.put("code",500);
		json.put("data","服务器异常");
		json.put("msg",e.toString());
		printWrite(json.toString(),response);
		return null;
	}
	
	
	  /**   
	  * 将错误信息添加到response中   
	  *   
	  * @param msg   
	  * @param response   
	  * @throws IOException   
	  */ 
	  private static void printWrite(String msg, HttpServletResponse response) {   
		 PrintWriter pw = null;
		 try {      
	       pw = response.getWriter();    
	       pw.write(msg);    
	       pw.flush();    
	       pw.close();   
	     } catch (Exception e) {     
	       e.printStackTrace();   
	     }finally {
			if(pw != null){
				pw.close();
			}
		}
	  }
	
}
