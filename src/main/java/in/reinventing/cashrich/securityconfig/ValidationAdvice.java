package in.reinventing.cashrich.securityconfig;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import in.reinventing.cashrich.dtos.ExceptionResponse;
import in.reinventing.cashrich.exception.ResourceNotFoundException;
import in.reinventing.cashrich.exception.UnauthorizedException;
import in.reinventing.cashrich.exception.UserAlreadyPresentException;

@RestControllerAdvice
public class ValidationAdvice {

	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> invalidArgument(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach(e -> {
			String fieldName = ((FieldError) e).getField();
			String errorMessage = e.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		return errors;
	}

	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	@ExceptionHandler(ResourceNotFoundException.class) // Assuming you have a custom ResourceNotFoundException
	public ExceptionResponse resourceNotFound(ResourceNotFoundException ex) {
		ExceptionResponse exceptionResponse=new ExceptionResponse(ex.getMessage(), HttpStatus.NOT_FOUND.value(), null);
		return exceptionResponse;
	}
	
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	@ExceptionHandler(UsernameNotFoundException.class) // Assuming you have a custom ResourceNotFoundException
	public ExceptionResponse userNotFound(UsernameNotFoundException ex) {
		ExceptionResponse exceptionResponse=new ExceptionResponse(ex.getMessage(), HttpStatus.NOT_FOUND.value(), null);
		return exceptionResponse;
	}

	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(UserAlreadyPresentException.class) // Assuming you have a custom ResourceNotFoundException
	public ExceptionResponse userNotFound(UserAlreadyPresentException ex) {
		ExceptionResponse exceptionResponse=new ExceptionResponse(ex.getMessage(), HttpStatus.BAD_REQUEST.value(), null);
		return exceptionResponse;
	}
	
	@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(UnauthorizedException.class) // Assuming you have a custom UnauthorizedException
	public ExceptionResponse unauthorized(UnauthorizedException ex) {
		ExceptionResponse exceptionResponse=new ExceptionResponse(ex.getMessage(), HttpStatus.NOT_FOUND.value(), null);
		return exceptionResponse;
	}

	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class) // Catch-all for unhandled exceptions
	public ExceptionResponse internalServerError(Exception ex) {
		ExceptionResponse exceptionResponse=new ExceptionResponse(ex.getMessage(), HttpStatus.NOT_FOUND.value(), null);
		return exceptionResponse;
	}
}
