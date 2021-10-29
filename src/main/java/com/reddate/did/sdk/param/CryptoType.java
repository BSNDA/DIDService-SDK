package com.reddate.did.sdk.param;

/**
 * 
 * Asymmetrical Encryption Algorithm type
 * 
 * 
 *
 */
public enum CryptoType {
	/**
	 * The ECDSA Asymmetrical Encryption Algorithm
	 */
	ECDSA,
	/**
	 * The SM2 Asymmetrical Encryption Algorithm
	 */
	SM;
	
	public static CryptoType ofVlaue(Integer value) {
		for(CryptoType tmp : CryptoType.values()) {
			if(tmp.ordinal() == value) {
				return tmp;
			}
		}
		return null;
	}
	
}
