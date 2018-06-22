package com.model;

import java.io.Serializable;

public class AutomationConverterVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int actionId;
	
	private String actionType;
	
	private String elementType;
	
	private String actionStatement;

	public int getActionId() {
		return actionId;
	}

	public void setActionId(int actionId) {
		this.actionId = actionId;
	}

	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	public String getElementType() {
		return elementType;
	}

	public void setElementType(String elementType) {
		this.elementType = elementType;
	}

	public String getActionStatement() {
		return actionStatement;
	}

	public void setActionStatement(String actionStatement) {
		this.actionStatement = actionStatement;
	}
	

}
