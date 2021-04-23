package com.qooems.excel.common.response;

public enum ResponseCode {

	/**
	 * 返回成功
	 */
	SUCCESS(0,"成功"),
	
	/**
	 * 请求参数异常
	 */
	PARAMS_ERROR(101,"请求参数异常"),
	
	/**
	 * 系统异常
	 */
	SYSTEM_ERROR(102,"系统异常"),
	
	/**
	 * 未登录
	 */
	NOT_LOGGED_IN(103,"您未登录，请先登录"),
	
	/**
	 * token、session过期
	 */
	LOGIN_TIMEOUT(104,"您长时间未操作，请重新登录"),
	
	/**
	 * 登录失败,用户名或密码不正确
	 */
	LOGIN_ERROR(105,"用户名或密码不正确"),
	
	/**
	 * 没有权限，拒绝访问
	 */
	PERMISSION_DENIED(106,"您没有权限执行此操作"),
	
	/**
	 * 未找到数据
	 */
	DATA_NOT_FOUND(107,"未找到数据"),
	
	/**
	 * 非法请求
	 */
	ILLEGAL_REQUEST(108,"非法请求"),
	
	/**
	 * 数据已存在
	 */
	DATA_IS_EXIST(109,"数据已存在"),

    /**
     * 数据异常
     */
    DATA_EXCEPTION(110,"数据异常"),

    /************************************************************************************************************************************/
	
	/**
	 * 身份证已注册
	 */
	IDNUMBER_IS_EXIST(201,"身份证已注册"),
	
	/**
	 * 手机号已注册
	 */
	MOBILE_IS_EXIST(202,"手机号已注册"),

    /**
     * 余额不足
     */
    INSUFFICIENT_BALANCE(203,"余额不足"),

    /**
     * 已支付
     */
    PAYED(204,"已支付"),

	/**
	 * 该工单所属套餐已有工单付款，不能退单
	 */
	WORK_ORDER_PAYED(205,"该工单所属套餐已有工单付款，不能退单");

	private int code;
	
	private String message;
	
	private ResponseCode(int code,String message){
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

}
