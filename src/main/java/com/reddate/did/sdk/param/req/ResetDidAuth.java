package com.reddate.did.sdk.param.req;

import java.io.Serializable;

import com.reddate.did.sdk.protocol.common.KeyPair;

/**
 * Rest did document authority public key request data structure
 */
public class ResetDidAuth implements Serializable {

	/**
	 * did identifier
	 */
	private String did;

	/**
	 * primary public key and private key
	 */
	private KeyPair primaryKeyPair;

	/**
	 * reset authority public key and private key
	 */
	private ResetDidAuthKey recoveryKey;

	public String getDid() {
		return did;
	}

	public void setDid(String did) {
		this.did = did;
	}

	public ResetDidAuthKey getRecoveryKey() {
		return recoveryKey;
	}

	public void setRecoveryKey(ResetDidAuthKey recoveryKey) {
		this.recoveryKey = recoveryKey;
	}

	public KeyPair getPrimaryKeyPair() {
		return primaryKeyPair;
	}

	public void setPrimaryKeyPair(KeyPair primaryKeyPair) {
		this.primaryKeyPair = primaryKeyPair;
	}

}
