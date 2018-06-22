package com.model;

import java.io.Serializable;

public class StepRepository implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public String getTestCaseName() {
		return testCaseName;
	}
	public void setTestCaseName(String testCaseName) {
		this.testCaseName = testCaseName;
	}
	
	public String getStep_desc() {
		return step_desc;
	}
	public void setStep_desc(String step_desc) {
		this.step_desc = step_desc;
	}
	public String getPage_name() {
		return page_name;
	}
	public void setPage_name(String page_name) {
		this.page_name = page_name;
	}
	public String getElement_name() {
		return element_name;
	}
	public void setElement_name(String element_name) {
		this.element_name = element_name;
	}
	public String getAction_type() {
		return action_type;
	}
	public void setAction_type(String action_type) {
		this.action_type = action_type;
	}
	public String getElement_value() {
		return element_value;
	}
	public void setElement_value(String element_value) {
		this.element_value = element_value;
	}
	public int getStepno() {
		return stepno;
	}
	public void setStepno(int stepno) {
		this.stepno = stepno;
	}
	private String testCaseName;
	private int stepno;
	private String step_desc;
	private String page_name;
	private String element_name;
	private String action_type;
	private String element_value;

}
