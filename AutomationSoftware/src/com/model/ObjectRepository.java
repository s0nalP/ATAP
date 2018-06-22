package com.model;

import java.io.Serializable;

public class ObjectRepository implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
	public String getObject_type() {
		return object_type;
	}
	public void setObject_type(String object_type) {
		this.object_type = object_type;
	}
	public String getIdentification_type() {
		return identification_type;
	}
	public void setIdentification_type(String identification_type) {
		this.identification_type = identification_type;
	}
	public String getElement_value() {
		return element_value;
	}
	public void setElement_value(String element_value) {
		this.element_value = element_value;
	}
	private String page_name;
	private String element_name;
	private String object_type;
	private String identification_type;
	private String element_value;

}
