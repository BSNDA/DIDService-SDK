package com.reddate.did.sdk.exception;

/**
 * Did SDK common exception definition, DidException is a runtime exception, no
 * need catch this exception in code.
 */
public class DidException extends RuntimeException {

	/**
	 * exception error code
	 */
	private Integer code;

	public DidException(Integer code, String message) {
		super(message);
		this.code = code;
	}

	public Integer getCode() {
		return code;
	}

}
