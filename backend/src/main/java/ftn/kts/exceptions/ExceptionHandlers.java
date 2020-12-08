package ftn.kts.exceptions;

import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ExceptionHandlers {
	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<ErrorMessage> resourceNotFoundException(NoSuchElementException ex, WebRequest request) {
		ErrorMessage message = new ErrorMessage(ex.getMessage());
		return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
	}
	

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorMessage> globalExceptionHandler(MethodArgumentNotValidException ex, WebRequest request) {
		ErrorMessage message = new ErrorMessage("Validation errors occurred!");
		for (FieldError err : ex.getFieldErrors()) {
			message.getErrors().put(err.getField(), err.getDefaultMessage());
		}
		return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
	}


	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorMessage> globalExceptionHandler(Exception ex, WebRequest request) {
		ErrorMessage message = new ErrorMessage(ex.getMessage());
		return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
	}

}
