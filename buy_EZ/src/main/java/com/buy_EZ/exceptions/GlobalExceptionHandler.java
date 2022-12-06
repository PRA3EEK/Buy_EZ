package com.buy_EZ.exceptions;

import java.sql.SQLException;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorDetails> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException manve, WebRequest wr){		
		ErrorDetails ed = new ErrorDetails(LocalDateTime.now(), "Validation Error", manve.getBindingResult().getFieldError().getDefaultMessage());	
	   return new ResponseEntity<ErrorDetails>(ed, HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(AdminException.class)
	public ResponseEntity<ErrorDetails> adminExceptionHandler(AdminException adminException, WebRequest wr){		
		ErrorDetails ed = new ErrorDetails(LocalDateTime.now(), adminException.getMessage(), wr.getDescription(false));
	   return new ResponseEntity<ErrorDetails>(ed, HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(CategoryException.class)
	public ResponseEntity<ErrorDetails> categoryExceptionHandler(CategoryException categoryException, WebRequest wr){
		ErrorDetails ed = new ErrorDetails(LocalDateTime.now(), categoryException.getMessage(), wr.getDescription(false));
	   return new ResponseEntity<ErrorDetails>(ed, HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(ProductException.class)
	public ResponseEntity<ErrorDetails> productExceptionHandler(ProductException productException, WebRequest wr){
		ErrorDetails ed = new ErrorDetails(LocalDateTime.now(), productException.getMessage(), wr.getDescription(false));
	   return new ResponseEntity<ErrorDetails>(ed, HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(PaymentException.class)
	public ResponseEntity<ErrorDetails> paymentExceptionHandler(PaymentException paymentException, WebRequest wr){
		ErrorDetails ed = new ErrorDetails(LocalDateTime.now(), paymentException.getMessage(), wr.getDescription(false));
	   return new ResponseEntity<ErrorDetails>(ed, HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(CustomerException.class)
	public ResponseEntity<ErrorDetails> customerExceptionHandler(CustomerException customerException, WebRequest wr){
		ErrorDetails ed = new ErrorDetails(LocalDateTime.now(), customerException.getMessage(), wr.getDescription(false));
	   return new ResponseEntity<ErrorDetails>(ed, HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(SQLException.class)
	public ResponseEntity<ErrorDetails> sqlExceptionHandler(SQLException sqlException, WebRequest wr){
		ErrorDetails ed = new ErrorDetails(LocalDateTime.now(), sqlException.getMessage(), wr.getDescription(false));
	   return new ResponseEntity<ErrorDetails>(ed, HttpStatus.BAD_REQUEST);
	}
	
	
}
