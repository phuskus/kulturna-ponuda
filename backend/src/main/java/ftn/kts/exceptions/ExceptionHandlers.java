package ftn.kts.exceptions;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ExceptionHandlers {

	@ExceptionHandler(PasswordNotChangedException.class)
	public ResponseEntity<ErrorMessage> passwordNotChangedExceptionHandler(PasswordNotChangedException ex, WebRequest request) {
		ErrorMessage message = new ErrorMessage(ex.getMessage(), ex.getJwt());
		return new ResponseEntity<>(message, HttpStatus.TEMPORARY_REDIRECT);
	}


	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ErrorMessage> badCredentialsExceptionHandler(BadCredentialsException ex, WebRequest request) {
		ErrorMessage message = new ErrorMessage("Invalid password!");
		return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler({AuthenticationException.class, AccessDeniedException.class})
	public ResponseEntity<ErrorMessage> authenticationExceptionHandler(Exception ex, WebRequest request) {
		ErrorMessage message = new ErrorMessage(ex.getMessage());
		return new ResponseEntity<>(message, HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(UniqueConstraintViolationException.class)
	public ResponseEntity<ErrorMessage> uniqueConstraintExceptionHandler(UniqueConstraintViolationException ex, WebRequest request) {
		ErrorMessage message = new ErrorMessage(ex.getMessage());
		message.getErrors().put(ex.getCauseField(), ex.getCauseMessage());
		return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(UserException.class)
	public ResponseEntity<ErrorMessage> loginExceptionHandler(UserException ex, WebRequest request) {
		ErrorMessage message = new ErrorMessage(ex.getMessage());
		message.getErrors().put(ex.getCauseField(), ex.getCauseMessage());
		return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
	}
		
	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<ErrorMessage> resourceNotFoundExceptionHandler(NoSuchElementException ex, WebRequest request) {
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
