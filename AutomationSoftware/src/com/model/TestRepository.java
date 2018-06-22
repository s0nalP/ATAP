package com.model;

import java.io.Serializable;

public class TestRepository implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String test_case_name;

	public String getTest_case_name() {
		return test_case_name;
	}

	public void setTest_case_name(String test_case_name) {
		this.test_case_name = test_case_name;
	}

	public String getTest_case_desc() {
		return test_case_desc;
	}

	public void setTest_case_desc(String test_case_desc) {
		this.test_case_desc = test_case_desc;
	}

	private String test_case_desc;

}
