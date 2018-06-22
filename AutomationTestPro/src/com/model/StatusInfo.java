package com.model;

public class StatusInfo {
	
	private String errMsg;

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public String getErrMsg() {
		return errMsg;
	}
	
	public void setStatus(boolean status) {
		this.status = status;
	}

	public boolean isStatus() {
		return status;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}

	private boolean status;
	
	private int type;
	
	

}
