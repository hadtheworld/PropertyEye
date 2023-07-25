package com.propertyEye.propertySystem.Exceptions;

public class EmptyOutputException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String errorMessage;
	private final String errorCode="602";
	
	@Override
	public String toString() {
		return "EmptyOutputException [errorMessage=" + errorMessage + ", errorCode=" + errorCode + "]";
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public EmptyOutputException(String errorMessage) {
		super();
		this.errorMessage = errorMessage;
	}
	public EmptyOutputException() {
		super();
	}
}
