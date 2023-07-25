package com.propertyEye.propertySystem.advice;

import java.util.NoSuchElementException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.propertyEye.propertySystem.Exceptions.EmptyInputException;
import com.propertyEye.propertySystem.Exceptions.EmptyOutputException;
import com.propertyEye.propertySystem.Exceptions.UniqueValueException;

@ControllerAdvice
public class PropertyErrorAdvice extends ResponseEntityExceptionHandler{
	

	  @ExceptionHandler(Exception.class)
	  public ResponseEntity<Object> handleAllException(Exception ex) {

	    return new ResponseEntity<>("Entered Request Body is incompleted, please look into it",HttpStatus.BAD_REQUEST);
	  }
	@Override
	public ResponseEntity<Object> handleHttpRequestMethodNotSupported(
			HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request){
		return new ResponseEntity<>("Made Request is Not Supported for this endpoint",HttpStatus.BAD_REQUEST);
	}
	

	
	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<Object> handleNoSuchElementException(NoSuchElementException noSuchElementException){
		return new ResponseEntity<>("Wrong Id given, No record found at it",HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(EmptyInputException.class)
	public ResponseEntity<Object> handleEmptyInputException(EmptyInputException emptyInputException){
		return new ResponseEntity<>(emptyInputException.getErrorMessage(),HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException illegalArgumentException){
		return new ResponseEntity<>(illegalArgumentException.getMessage(),HttpStatus.FORBIDDEN);
	}
	
	@ExceptionHandler(EmptyOutputException.class)
	public ResponseEntity<Object> handleEmptyOutputException(EmptyOutputException emptyOutputException){
		return new ResponseEntity<>(emptyOutputException.getErrorMessage(),HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(UniqueValueException.class)
	public ResponseEntity<Object> handleUniqueValueException(UniqueValueException uniqueValueException){
		return new ResponseEntity<>(uniqueValueException.getErrorMessage(),HttpStatus.CONFLICT);
	}
}
