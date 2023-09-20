package com.ohgiraffers.comprehensive.common.exceptionhandler;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(Exception.class)
	public String errorView(Exception e, Model model) {
		
		model.addAttribute("errorMessage", e.getMessage());
		
		return "common/error";
	}
	
}
