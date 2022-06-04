package hu.webuni.airport.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.validation.FieldError;

public class MyError {

	// --- attributes ---------------------------------------------------------
	
	private String message;
	private int errorCode;
	private List<FieldError> fieldErrors;
	
	// --- constructors -------------------------------------------------------
	
	public MyError(String message, int errorCode) {
		this.message = message;
		this.errorCode = errorCode;
		this.fieldErrors = new ArrayList<>();
	}
	
	// --- getters and setters ------------------------------------------------

	public String getMessage() { return message; }
	public int getErrorCode() { return errorCode; }
	public List<FieldError> getFieldErrors() { return fieldErrors; }

	public void setFieldErrors(List<FieldError> fieldErrors) { this.fieldErrors = fieldErrors; }	
}