package com.model;

import java.util.List;

public class TestCaseResults {
	
	List<StepResults> stepResults;
	
	private String testcasename;

	public String getTestcasename() {
		return testcasename;
	}

	public void setTestcasename(String testcasename) {
		this.testcasename = testcasename;
	}

	
	public String getTestCaseInfo() {
		return testCaseInfo;
	}

	public void setTestCaseInfo(String testCaseInfo) {
		this.testCaseInfo = testCaseInfo;
	}


	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}


	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}


	public String getTestState() {
		return testState;
	}

	public void setTestState(String testState) {
		this.testState = testState;
	}


	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}


	private String testCaseInfo;
	
	private boolean status;
	
	
	private String errMsg;
	
	private String testState;
	
	private String dateTime;
	
}
