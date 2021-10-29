package com.reddate.did.protocol.request;

import static org.junit.Assert.assertSame;

import java.util.HashMap;

import org.junit.Test;

import com.reddate.did.sdk.protocol.common.PublicKey;
import com.reddate.did.sdk.protocol.request.CredentialWrapper;
import com.reddate.did.sdk.protocol.request.VerifyCredentialWrapper;

public class VerifyCredentialWrapperTest {
    @Test
    public void testConstructor() {
        VerifyCredentialWrapper actualVerifyCredentialWrapper = new VerifyCredentialWrapper();
        CredentialWrapper credentialWrapper = new CredentialWrapper();
        credentialWrapper.setExpirationDate("2020-03-01");
        credentialWrapper.setUserDid("User Did");
        credentialWrapper.setId("42");
        credentialWrapper.setLongDesc("Long Desc");
        credentialWrapper.setContext("Context");
        credentialWrapper.setProof(new HashMap<String, Object>(1));
        credentialWrapper.setShortDesc("Short Desc");
        credentialWrapper.setClaim(new HashMap<String, Object>(1));
        credentialWrapper.setIssuerDid("Issuer Did");
        credentialWrapper.setCptId(123L);
        credentialWrapper.setCreated("Jan 1, 2020 8:00am GMT+0100");
        credentialWrapper.setType("Type");
        actualVerifyCredentialWrapper.setCredentialWrapper(credentialWrapper);
        PublicKey publicKey = new PublicKey();
        publicKey.setPublicKey("Public Key");
        publicKey.setType("Type");
        actualVerifyCredentialWrapper.setPublicKey(publicKey);
        assertSame(credentialWrapper, actualVerifyCredentialWrapper.getCredentialWrapper());
        assertSame(publicKey, actualVerifyCredentialWrapper.getPublicKey());
    }

    @Test
    public void testConstructor2() {
        VerifyCredentialWrapper actualVerifyCredentialWrapper = new VerifyCredentialWrapper();
        CredentialWrapper credentialWrapper = new CredentialWrapper();
        credentialWrapper.setExpirationDate("2020-03-01");
        credentialWrapper.setUserDid("User Did");
        credentialWrapper.setId("42");
        credentialWrapper.setLongDesc("Long Desc");
        credentialWrapper.setContext("Context");
        credentialWrapper.setProof(new HashMap<String, Object>(1));
        credentialWrapper.setShortDesc("Short Desc");
        credentialWrapper.setClaim(new HashMap<String, Object>(1));
        credentialWrapper.setIssuerDid("Issuer Did");
        credentialWrapper.setCptId(123L);
        credentialWrapper.setCreated("Jan 1, 2020 8:00am GMT+0100");
        credentialWrapper.setType("Type");
        actualVerifyCredentialWrapper.setCredentialWrapper(credentialWrapper);
        PublicKey publicKey = new PublicKey();
        publicKey.setPublicKey("Public Key");
        publicKey.setType("Type");
        actualVerifyCredentialWrapper.setPublicKey(publicKey);
        assertSame(credentialWrapper, actualVerifyCredentialWrapper.getCredentialWrapper());
        assertSame(publicKey, actualVerifyCredentialWrapper.getPublicKey());
    }
}

