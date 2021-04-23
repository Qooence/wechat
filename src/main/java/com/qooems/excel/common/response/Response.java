package com.qooems.excel.common.response;

import org.apache.commons.lang3.StringUtils;

public class Response {
	
	private boolean success = true;
	
	private Integer code;
	
	private String message = "";
	
	private Object data;
	
	public Response(){
	}
	
	public Response(boolean success,Integer code,String message,Object data){
		this.success = success;
		this.code = code;
		this.message = message;
		this.data = data;
	}
	
	public Response(boolean success,String message,Object data){
		this.success = success;
		this.message = message;
		this.data = data;
	}
	
	public Response(boolean success,ResponseCode responseCode,Object data){
		this.success = success;
		this.data = data;
		this.code = responseCode.getCode();
		this.message = responseCode.getMessage();
	}
	
	public Response(boolean success,ResponseCode responseCode,String message,Object data){
		this.success = success;
		this.data = data;
		this.code = responseCode.getCode();
		this.message = message;
	}
	
	/**
	 * 执行成功，返回默认信息，不返回数据
	 * @return
	 */
	public static  Response success(){
		return success(null);
	}

	/**
	 * 执行成功，返回自定义信息，不返回数据
	 * @return
	 */
	public static  Response success(String message){
		return success(message,null);
	}
	
	/**
	 * 执行成功，返回默认信息，并返回数据
	 * @return
	 */
	public static  Response success(Object data){
		return success(null,data);
	}
	
	/**
	 * 执行成功，返回自定义信息与数据
	 * @return
	 */
	public static  Response success(String message,Object data){
		if(StringUtils.isBlank(message)){
			return new Response(true,ResponseCode.SUCCESS,data);
		}
		return new Response(true,ResponseCode.SUCCESS.getCode(),message,data);
	}
	
	/**
	 * 执行失败，不返回信息、返回码与数据
	 * @return
	 */
	public static  Response error(){
		return error("");
	}
	
	/**
	 * 执行失败，返回自定义信息，不返回数据及返回码
	 * @return
	 */
	public static  Response error(String message){
		return error(message, null);
	}
	
	/**
	 * 执行失败，返回自定义信息与数据，不返回返回码
	 * @return
	 */
	public static  Response error(String message,Object data){
		return new Response(false,message,data);
	}
	
	/**
	 * 执行失败，返回默认信息，不返回数据
	 * @return
	 */
	public static  Response error(ResponseCode responseCode){
		return error(responseCode,null);
	}
	
	/**
	 * 执行失败，返回默认信息+自定义信息，不返回数据
	 * @return
	 */
	public static  Response error(String message,ResponseCode responseCode){
		return new Response(false,responseCode,message,null);
	}
	
	/**
	 * 执行失败，返回默认信息及数据
	 * @return
	 */
	public static  Response error(ResponseCode responseCode,Object data){
		return new Response(false,responseCode,data);
	}
	
	/**
	 * 执行失败，返回自定义信息及返回码，不返回数据
	 * @return
	 */
	public static  Response error(Integer code,String message){
		return error(code,message,null);
	}
	
	/**
	 * 执行失败，返回自定义信息、返回码及数据
	 * @return
	 */
	public static  Response error(Integer code,String message,Object data){
		return new Response(false,code,message,data);
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
