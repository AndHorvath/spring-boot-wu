package hu.webuni.airport.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import hu.webuni.airport.service.NonUniqueIataException;

@RestControllerAdvice
public class CustomExceptionHandler {
	
	// --- attributes ---------------------------------------------------------
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomExceptionHandler.class);
	
	// --- public methods -----------------------------------------------------

	@ExceptionHandler(NonUniqueIataException.class)
	public ResponseEntity<MyError> handleNonUniqueIata(NonUniqueIataException exception, WebRequest request) {
		LOGGER.warn(exception.getMessage(), exception);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MyError(exception.getMessage(), 1002));
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<MyError> handleValidationError(
		MethodArgumentNotValidException exception, WebRequest request) {
		
		LOGGER.warn(exception.getMessage(), exception);
		MyError myError = new MyError(exception.getMessage(), 1002);
		myError.setFieldErrors(exception.getBindingResult().getFieldErrors());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(myError);
	}
}