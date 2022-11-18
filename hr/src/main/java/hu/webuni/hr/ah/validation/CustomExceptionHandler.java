package hu.webuni.hr.ah.validation;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import hu.webuni.hr.ah.validation.identifier.NonExistingIdentifierException;
import hu.webuni.hr.ah.validation.identifier.NonUniqueIdentifierException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {

    // --- constants ----------------------------------------------------------

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomExceptionHandler.class);

    private static final int NOT_FOUND_CODE = HttpStatus.NOT_FOUND.value();
    private static final int BAD_REQUEST_CODE = HttpStatus.BAD_REQUEST.value();

    // --- public methods -----------------------------------------------------

    @ExceptionHandler(NonExistingIdentifierException.class)
    public ResponseEntity<ValidationError> handleNonExistingIdentifier(NonExistingIdentifierException exception) {
        LOGGER.error(exception.getMessage(), exception);
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(new ValidationError(NOT_FOUND_CODE, exception.getMessage()));
    }

    @ExceptionHandler(NonUniqueIdentifierException.class)
    public ResponseEntity<ValidationError> handleNonUniqueIdentifier(NonUniqueIdentifierException exception) {
        LOGGER.warn(exception.getMessage(), exception);
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(new ValidationError(BAD_REQUEST_CODE, exception.getMessage()));
    }

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<ValidationError> handleInvalidFormat(InvalidFormatException exception) {
        LOGGER.warn(exception.getMessage(), exception);
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(new ValidationError(BAD_REQUEST_CODE, exception.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationError> handleNotValidMethodArgument(MethodArgumentNotValidException exception) {
        LOGGER.warn(exception.getMessage(), exception);
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(new ValidationError(BAD_REQUEST_CODE, exception.getMessage(), exception.getFieldErrors()));
    }
}