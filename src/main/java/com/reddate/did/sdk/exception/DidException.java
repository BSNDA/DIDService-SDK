package com.reddate.did.sdk.exception;

/**
 * 
 * Did SDK common exception definition, DidException is a runtime exception, no need catch this exception in code.
 * 
 * 
 *
 */
public class DidException extends RuntimeException{

	/**
	 * exception error code
	 * 
	 */
	private Integer code;

	public DidException(String message) {
		super(message);
	}
	
	public DidException(Integer code,String message) {
		super(message);
		this.code = code;
	}
		
}
