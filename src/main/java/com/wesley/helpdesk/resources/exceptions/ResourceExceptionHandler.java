package com.wesley.helpdesk.resources.exceptions;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.wesley.helpdesk.services.exceptions.DataIntegrityViolationException;
import com.wesley.helpdesk.services.exceptions.ObjectNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler {
	
	@ExceptionHandler(ObjectNotFoundException.class)//definindo que o método é um manipulador de excessões da classe objectnotfoundexception, ou seja, quando houver uma exceção deste tipo no projeto, este manipulador será chemado
	public ResponseEntity<StandardError> objectNotFoundException(ObjectNotFoundException ex, HttpServletRequest request){
		
		StandardError error = new StandardError(System.currentTimeMillis(), HttpStatus.NOT_FOUND.value(), "Object Not Found", ex.getMessage(), request.getRequestURI());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}
	
	@ExceptionHandler(DataIntegrityViolationException.class)//definindo que o método é um manipulador de excessões da classe DataIntegrityViolationException, ou seja, quando houver uma exceção deste tipo no projeto, este manipulador será chemado
	public ResponseEntity<StandardError> dataIntegrityViolationException(DataIntegrityViolationException ex, HttpServletRequest request){
		
		StandardError error = new StandardError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(), "Violação de dados", ex.getMessage(), request.getRequestURI());
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandardError> validationErrors(MethodArgumentNotValidException ex, HttpServletRequest request){
	
		ValidationError errors = new ValidationError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(), "Validation Error", "Erro na validação dos campos", request.getRequestURI());
		
		for(FieldError err : ex.getBindingResult().getFieldErrors()) { //Como mais de um campo pode ser deixado em branco, com um loop pegamos os erro em todos os campos que não foram preenchidos
			errors.addError(err.getField(), err.getDefaultMessage());
		}
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
	}
	
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<StandardError> validationCpfEmailError(ConstraintViolationException ex, HttpServletRequest request){

		boolean isValidaCPF = ex.getMessage().contains("CPF");
		boolean isValidaEmail = ex.getMessage().contains("e-mail");
		
		StandardError error = null;

		if(isValidaCPF && isValidaEmail) {
			 error = new StandardError(System.currentTimeMillis(), HttpStatus.INTERNAL_SERVER_ERROR.value(), "Validation Error", "Número do registro de contribuinte individual brasileiro (CPF) inválido! : Endereço de E-Mail mal formado!", request.getRequestURI());
		} else if (isValidaCPF && !isValidaEmail) {
			 error = new StandardError(System.currentTimeMillis(), HttpStatus.INTERNAL_SERVER_ERROR.value(), "Erro na validação de CPF", "Número do registro de contribuinte individual brasileiro (CPF) inválido!", request.getRequestURI());
		} else if (!isValidaCPF && isValidaEmail){
			 error = new StandardError(System.currentTimeMillis(), HttpStatus.INTERNAL_SERVER_ERROR.value(), "Erro na validação de E-Mail", "Endereço de E-Mail mal formado!", request.getRequestURI());
		} else {
			error = new StandardError(System.currentTimeMillis(), HttpStatus.INTERNAL_SERVER_ERROR.value(), "Validation Error", ex.getMessage(), request.getRequestURI());
		}
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
	}
}
