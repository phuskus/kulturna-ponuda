package ftn.kts.exceptions;

import java.io.FileNotFoundException;
import java.io.IOException;
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

	public static boolean ignoreSQLException(String sqlState) {

		if (sqlState == null) {
			System.out.println("The SQL state is not defined!");
			return false;
		}

		// X0Y32: Jar file already exists in schema
		if (sqlState.equalsIgnoreCase("X0Y32"))
			return true;

		// 42Y55: Table already exists in schema
		if (sqlState.equalsIgnoreCase("42Y55"))
			return true;

		return false;
	}

	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<ErrorMessage> resourceNotFoundException(NoSuchElementException ex, WebRequest request) {
		ErrorMessage message = new ErrorMessage(ex.getMessage());
		return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorMessage> validationExceptionHandler(MethodArgumentNotValidException ex,
			WebRequest request) {
		ErrorMessage message = new ErrorMessage("Validation errors occurred!");
		for (FieldError err : ex.getFieldErrors()) {
			message.getErrors().put(err.getField(), err.getDefaultMessage());
		}
		return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(FileNotFoundException.class)
	public ResponseEntity<ErrorMessage> fileNotFoundExceptionHandler(FileNotFoundException ex, WebRequest request) {
		ErrorMessage message = new ErrorMessage(ex.getMessage());
		return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(IOException.class)
	public ResponseEntity<ErrorMessage> ioExceptionHandler(IOException ex, WebRequest request) {
		ErrorMessage message = new ErrorMessage(ex.getMessage());
		return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorMessage> globalExceptionHandler(Exception ex, WebRequest request) {
		ErrorMessage message = new ErrorMessage(ex.getMessage());
		return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
	}

}
