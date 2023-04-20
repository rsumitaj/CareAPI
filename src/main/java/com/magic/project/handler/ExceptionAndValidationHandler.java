package com.magic.project.handler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ExceptionAndValidationHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		BindingResult br = ex.getBindingResult();
		List<ObjectError> errors = br.getAllErrors();
		List<String> list = new ArrayList<>();
		ResponseError responseError = new ResponseError("Validation Failed", list);
		for (ObjectError e : errors) {
			list.add(e.getDefaultMessage());
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseError);
	}
	@ExceptionHandler(DoctorNotFoundException.class)
	public ResponseEntity<Object> handleExceptionNotFoundException(DoctorNotFoundException ex) {
		List<String> list = new ArrayList<>();
		list.add(ex.getMessage());
		ResponseError responseError = new ResponseError("Doctor Not Found", list);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseError);

	}
	@ExceptionHandler(PatientNotFoundException.class)
	public ResponseEntity<Object> handleExceptiontNotFoundException(PatientNotFoundException ex) {
		List<String> list = new ArrayList<>();
		list.add(ex.getMessage());
		ResponseError responseError = new ResponseError("Patient Not Found", list);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseError);

	}

}
