package com.sany.entity;

import java.io.Serializable;

public class Parts  implements Serializable{

	
	private static final long serialVersionUID = 1L;
	private String partCode;
	private String orderNumber;
	private String batch;
	private String partSize;
	public String getPartCode() {
		return partCode;
	}
	public void setPartCode(String partCode) {
		this.partCode = partCode;
	}
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	public String getBatch() {
		return batch;
	}
	public void setBatch(String batch) {
		this.batch = batch;
	}
	public String getPartSize() {
		return partSize;
	}
	public void setPartSize(String partSize) {
		this.partSize = partSize;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getRoute() {
		return route;
	}
	public void setRoute(String route) {
		this.route = route;
	}
	private int num;
	private String route; //工艺路线
	

}
