package com.reddate.did.sdk.constant;

public enum ErrorMessage {

	UNKNOWN_ERROR(9999, "Unknown exception "),

	PARAMETER_IS_EMPTY(1001, "is null"),
	
	PUBLIC_AND_PRIVATE_KEY_MISMATCH(1021, "Public and private keys do not match"),

	GENERATE_DID_FAIL(1044, "Failed to generate the DID"),

	ENCRYPT_KEY_FAILED(1337, "Failed to encrypt the key"),

	SIGNATURE_FAILED(1029, "Failed to sign the data"),

	PRIVATE_KEY_ILLEGAL_FORMAT(1304, "Private key format is invalid"),

	CPT_NOT_EXIST(1060, "CPT does not exist"),

	DID_NOT_EXIST(1041, "DID does not exist"),

	CPT_AND_ISSUER_CANNOT_MATCH(1062, "Issuer and publisherDid in the CPT do not match"),

	QUERY_GRANT_ENCPY_KEY_FAILED(1501, "Failed to query the encryption key of the granted resource"),

//	DECRPTY_GRANT_KEY_FAILED(1502, "Failed to decrypt the key of the granted resource"),

	RESOURCE_NOT_EISTS(1418, "Resource does not exist"),

	RECOVERY_KEY_INCORRECT(1504, "The recovery key pair is incorrect, cannot reset DID authentication"),

	PRK_PUK_NOT_MATCH(1306, " Public key format is invalid"),

	INVALID_DID(1043, "Invalid DID"),

	DECRYPT_KEY_FAILED(1455, "decrypt encptyKey failed"),

	DECRYPT_CONTENT_FAILED(1456, "decrypt content failed"),

	CREAT_KEY_FAIL(1085, "Failed to create the key pair"),

	HASH_DID_FAIL(1086, "Failed to calculate the DID"),

//	SIGN_DID_FAIL(1087, "Failed to calculate the DID Document signature"),

	CREATE_DID_DOC_FAIL(1088, "Failed to create the DID Document"),
	
	MNEM_IS_EMPTY(1090, "The mnemonic is empty"),
	
	VALIDATE_SIGN_ERROR(1422, "Signature verification failed"),
	
	DECRYPT_FAILED(1365, "Failed to decrypt the data"),
	
	ENCRYPT_FAILED(1366, "Failed to encrypt the data"),
	
	SAVE_RESOURCE_ERROR(1408, "Failed to save the resource"),
	
	 USER_NOT_EXISTS(1423, "The user is not registered"),
	 
	CRYPTO_TYPE_INCORRECT(1466, "Crypto type format is invalid"),
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
