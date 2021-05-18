package com.sany.utils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseMode {

	private Integer id;
	private Boolean success;
	private String msg;
	@JsonIgnore
	private String opText;// 记录操作日志
	@JsonIgnore
	private String source;// 记录系统mes，wms
	@JsonIgnore
	private String remark1;// 日志备用（订单号）
	@JsonIgnore
	private String remark2;// 日志备用（箱号）
	@JsonIgnore
	private String remark3;// 日志备用（操作的物料编码）
	@JsonIgnore
	private String remark4;// 日志备用（操作说明）

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOpText() {
		return opText;
	}

	public void setOpText(String opText) {
		this.opText = opText;
	}

	public void modify(Boolean success, String message) {
		setSuccess(success);
		setMsg(message);
	}

	public static BaseMode bulid(Boolean succ, String msg) {
		BaseMode o = new BaseMode();
		o.setSuccess(succ);
		o.setMsg(msg);
		return o;
	}

	/**
	 * @param succ
	 * @param msg    返回前端提示
	 * @param opText 日志
	 * @return
	 */
	public static BaseMode bulidWithLog(Boolean succ, String msg, String opText) {
		BaseMode o = new BaseMode();
		o.setSuccess(succ);
		o.setMsg(msg);
		o.setOpText(opText);
		return o;
	}
	


	/**
	 * 
	 * @param succ
	 * @param msg     返回前端提示
	 * @param opText  日志
	 * @param remark1 订单号
	 * @param remark2 箱号
	 * @param remark3 物料编码
	 * @param remark4 操作说明
	 * @return
	 */
	public static BaseMode buildWithCkdLog(Boolean succ, String msg, String opText, String remark1, String remark2,
			String remark3, String remark4) {
		BaseMode o = new BaseMode();
		o.setSuccess(succ);
		o.setMsg(msg);
		o.setOpText(opText);
		o.setRemark1(remark1);
		o.setRemark2(remark2);
		o.setRemark3(remark3);
		o.setRemark4(remark4);
		return o;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public BaseMode bulidSource(String source) {
		this.setSource(source);
		return this;
	}

	public String getRemark1() {
		return remark1;
	}

	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}

	public String getRemark2() {
		return remark2;
	}

	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}

	public String getRemark3() {
		return remark3;
	}

	public void setRemark3(String remark3) {
		this.remark3 = remark3;
	}

	public String getRemark4() {
		return remark4;
	}

	public void setRemark4(String remark4) {
		this.remark4 = remark4;
	}

}
