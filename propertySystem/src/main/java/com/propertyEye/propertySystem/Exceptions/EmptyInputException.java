package com.propertyEye.propertySystem.Exceptions;

public class EmptyInputException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String errorMessage;
	private final String errorCode="601";
	public String getErrorMessage() {
		return errorMessage;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "EmptyInputException [errorMessage=" + errorMessage + ", errorCode=" + errorCode + "]";
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public EmptyInputException(String errorMessage) {
		super();
		this.errorMessage = errorMessage;
	}
	public EmptyInputException() {
		super();
	}
}
