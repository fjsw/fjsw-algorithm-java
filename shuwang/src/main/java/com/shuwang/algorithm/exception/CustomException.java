package com.shuwang.algorithm.exception;

public class CustomException extends RuntimeException{
	private static final long serialVersionUID = 8982346783787090125L;

	public CustomException(String msg){
		super(msg);
	}
}
