package com.model;

import java.io.Serializable;

public class TimeComparision implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public int getIterationNo() {
		return iterationNo;
	}

	public void setIterationNo(int iterationNo) {
		this.iterationNo = iterationNo;
	}

	public double getTimeTakenProposed() {
		return timeTakenProposed;
	}

	public void setTimeTakenProposed(double timeTakenProposed) {
		this.timeTakenProposed = timeTakenProposed;
	}

	public double getTimeTakenOld() {
		return timeTakenOld;
	}

	public void setTimeTakenOld(double timeTakenOld) {
		this.timeTakenOld = timeTakenOld;
	}

	private int iterationNo;
	
	private double timeTakenProposed;
	
	private double timeTakenOld;

}
