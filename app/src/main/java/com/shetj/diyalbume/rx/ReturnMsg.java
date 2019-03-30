package com.shetj.diyalbume.rx;

/**
 * 类名称：ReturnMsg<br>
 * 内容摘要：响应返回类型的接收对象<br>
 * 属性描述：<br>
 * 方法描述：<br>
 * 修改备注：   <br>
 * 创建时间： 2016/7/26 11:24 <br>
 * 公司：深圳市华移科技股份有限公司<br>
 *
 * @author shetj<br>
 */


public class ReturnMsg<T> {
	//1:请求成功
	//0:请求失败
	//422:token 失效
	//500:服务器异常
	private int code;
	private String message;
	//返回的接收map
	private T datum;
	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public T getDatum() {
		return datum;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setDatum(T datum) {
		this.datum = datum;
	}
}
