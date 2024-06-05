package com.automation.baseline.exception;

/**
 * @Author: RafterOne QA
 */
public class CustomException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	public CustomException() {}
	
	public CustomException(String message){
       super(message);
    }

}