package com.app.exception;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyErrorDetails {
	
	private LocalDateTime timestamp;//localDate ANdTime use when error come
	private String message;// ErrorMeassage 
	private String desc;//Description 
}
