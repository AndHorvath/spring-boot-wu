package hu.webuni.hr.ah.validation;

import org.springframework.validation.FieldError;

import java.util.List;

public class ValidationError {

    // --- attributes ---------------------------------------------------------

    private int errorCode;
    private String message;
    private List<FieldError> fieldErrors;

    // --- constructors -------------------------------------------------------

    public ValidationError(int errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public ValidationError(int errorCode, String message, List<FieldError> fieldErrors) {
        this.errorCode = errorCode;
        this.message = message;
        this.fieldErrors = fieldErrors;
    }

    // --- getters and setters ------------------------------------------------

    public int getErrorCode() { return errorCode; }
    public String getMessage() { return message; }
    public List<FieldError> getFieldErrors() { return fieldErrors; }
}