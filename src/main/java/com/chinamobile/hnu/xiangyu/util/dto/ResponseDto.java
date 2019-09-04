package com.chinamobile.hnu.xiangyu.util.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author zwxiao
 * @create 2018/3/11
 **/
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDto implements Serializable {
    private static final long serialVersionUID = -6757080704476385401L;

    private int code;
    private String msg;
    private Object data;

	public void setValue(int code,String msg){
        this.code = code;
        this.msg = msg;
    }

    public void setValue(int code,String msg,Object data){
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
    
}
