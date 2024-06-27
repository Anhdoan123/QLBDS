package com.javaweb.controllerAdvice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.javaweb.customEX.InvalidRequiredException;
import com.javaweb.model.errorDTO;


@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {
	 @ExceptionHandler(InvalidRequiredException.class)
	    public ResponseEntity<Object> handleCityNotFoundException(
	    		InvalidRequiredException ex, WebRequest request) {

	        errorDTO error = new errorDTO();
	        error.setError("Chưa đủ thông tin cần thiết");
	        error.setDetail("Cần nhập đầy đủ thông tin");

	        return new ResponseEntity<>(error, HttpStatus.BAD_GATEWAY);
	    }
}
