package com.Providio_Automation.baseline.exception;

/**
 * @Author: ETG QA
 */
public class CustomException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	public CustomException() {}
	
	public CustomException(String message){
       super(message);
    }

}