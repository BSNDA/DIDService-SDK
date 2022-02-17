package com.reddate.did.sdk.protocol.response;

import java.io.Serializable;

/**
 * Credential data structure
 */
public class CredentialData implements Serializable {

	/**
	 * credential pages information
	 */
	private Pages<BaseCredential> credentialPages;

	public Pages<BaseCredential> getCredentialPages() {
		return credentialPages;
	}

	public void setCredentialPages(Pages<BaseCredential> credentialPages) {
		this.credentialPages = credentialPages;
	}
}
