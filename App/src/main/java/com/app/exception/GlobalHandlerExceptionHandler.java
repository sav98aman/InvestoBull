package com.app.exception;

import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;


@ControllerAdvice
public class GlobalHandlerExceptionHandler {
	
	
		// adding Candle Exception handle
		@ExceptionHandler(CandleException.class)
		public ResponseEntity<MyErrorDetails> AdminExceptiionHandller(CandleException ex,WebRequest req){
			MyErrorDetails err=new MyErrorDetails(LocalDateTime.now(), ex.getMessage(), req.getDescription(false));
			
			return new ResponseEntity<MyErrorDetails>(err,HttpStatus.NOT_FOUND);
		}
		@ExceptionHandler(NoHandlerFoundException.class)
		public ResponseEntity<MyErrorDetails> NoExceptionHandler(NoHandlerFoundException ex, WebRequest req){
			
			MyErrorDetails err=new MyErrorDetails(LocalDateTime.now(), ex.getMessage(), req.getDescription(false));

			return new ResponseEntity<MyErrorDetails>(err, HttpStatus.NOT_FOUND);
			
		}
		@ExceptionHandler(Exception.class)
		public ResponseEntity<MyErrorDetails> AllTypeExceptiionHandller(Exception ex,WebRequest req){
			MyErrorDetails err=new MyErrorDetails(LocalDateTime.now(), ex.getMessage(), req.getDescription(false));
			return new ResponseEntity<MyErrorDetails>(err,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	
}
