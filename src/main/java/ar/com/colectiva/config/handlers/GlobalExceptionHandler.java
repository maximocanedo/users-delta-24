package ar.com.colectiva.config.handlers;

import ar.com.colectiva.models.exceptions.Fail;
import ar.com.colectiva.validators.exceptions.ValidationError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Fail> handleExceptions(Exception e) {
        logger.error(e.getMessage(), e);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Fail("Error interno. "));
    }

    @ExceptionHandler(Fail.class)
    public ResponseEntity<Fail> handleExceptions(Fail e) {
        return ResponseEntity
                .status(e.getStatus())
                .body(e);
    }

    @ExceptionHandler(ValidationError.class)
    public ResponseEntity<ValidationError> handleValidationErrors(ValidationError e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e);
    }


}
