package com.sany.utils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ModeBean<T> extends BaseMode  {


	private int total;
	private T result;
	public T getResult() {
		return result;
	}
	public void setResult(T result) {
		this.result = result;
	}
	
	public static <T>  ModeBean<T> bulid(Boolean succ,String msg,T t)
	{
		ModeBean<T> o=new ModeBean<T>();
		o.setSuccess(succ);
		o.setMsg(msg);
		o.setResult(t);
		return o;
	}
	
	public static <T> ModeBean<T> bulidWithLog(Boolean succ, String msg, T t, String opText) {
		ModeBean<T> o = new ModeBean<T>();
		o.setSuccess(succ);
		o.setMsg(msg);
		o.setResult(t);
		o.setOpText(opText);
		return o;
	}

	public ModeBean<T> createTotal(int total)
	{
		this.setTotal(total);
		return this;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
}
