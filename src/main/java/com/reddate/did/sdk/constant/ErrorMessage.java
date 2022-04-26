package com.reddate.did.sdk.constant;

public enum ErrorMessage {

	UNKNOWN_ERROR(9999, "service error: "),

	PARAMETER_IS_EMPTY(1001, " is empty"),
	
	PUBLIC_AND_PRIVATE_KEY_MISMATCH(1021, "public key and private key do not match"),

	GENERATE_DID_FAIL(1044, "generate DID failed"),

	ENCRYPT_KEY_FAILED(1337, "encrypt key failed"),

	SIGNATURE_FAILED(1338, "signature failed"),

	PRIVATE_KEY_ILLEGAL_FORMAT(1025, "private key is illegal format"),

	CPT_NOT_EXIST(1060, "CPT is not exist"),

	DID_NOT_EXIST(1041, "DID not exist"),

	CPT_AND_ISSUER_CANNOT_MATCH(1062, "issuer and CPT's publisherDid are not equals"),

	QUERY_GRANT_ENCPY_KEY_FAILED(1501, "query grant resource's encryption key failed"),

	DECRPTY_GRANT_KEY_FAILED(1502, "decrypt resource's encryption key failed"),

	RESOURCE_NOT_EISTS(1503, "resource is not exist"),

	RECOVERY_KEY_INCORRECT(1504, "recovery key pair is incorrect, can not reset DID auth"),

	PRK_PUK_NOT_MATCH(1505, "primary private key and public key do not match"),

	INVALID_DID(1043, "DID is illegal format"),

	DECRYPT_KEY_FAILED(1455, "decrypt encptyKey failed"),

	DECRYPT_CONTENT_FAILED(1456, "decrypt content failed"),

	CREAT_KEY_FAIL(1085, "create key failed"),

	HASH_DID_FAIL(1086, "calculate did identifier failed"),

	SIGN_DID_FAIL(1087, "calculate did document sign failed"),

	CREATE_DID_DOC_FAIL(1088, "create document failed"),
	
	MNEM_IS_EMPTY(1090, "mnemonic is empty"),
	
	VALIDATE_SIGN_ERROR(1422, "verify signature failed"), 
	
	DECRYPT_FAILED(1365, "decrypt failed"),
	
	ENCRYPT_FAILED(1366, "encrypt failed"),
	
	SAVE_RESOURCE_ERROR(1408, "save resource failed"),
	
	 USER_NOT_EXISTS(1423, "user is not registered"),
	 
	CRYPTO_TYPE_INCORRECT(1466, "crypto type is illegal format"),
	;

	private Integer code;

	private String message;

	private ErrorMessage(Integer code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public Integer getCode() {
		return code;
	}

	public static String getMessage(Integer code) {
		for (ErrorMessage error : ErrorMessage.values()) {
			if (error.code.equals(code)) {
				return error.message;
			}
		}
		return null;
	}

	public static String getMessage(ErrorMessage errorMessage) {
		for (ErrorMessage error : ErrorMessage.values()) {
			if (error.code.equals(errorMessage.code)) {
				return error.message;
			}
		}
		return null;
	}
}
