package com.reddate.did.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.util.HashMap;

import org.junit.Test;

import com.reddate.did.sdk.protocol.common.PublicKey;
import com.reddate.did.sdk.protocol.request.CredentialWrapper;
import com.reddate.did.sdk.protocol.request.VerifyCredentialWrapper;
import com.reddate.did.sdk.service.CredentialService;

public class CredentialServiceTest {
    @Test
    public void testConstructor() {
        CredentialService actualCredentialService = new CredentialService("https://example.org/example", "ABC123",
                "myproject");

        assertEquals("myproject", actualCredentialService.getProjectId());
        assertEquals("https://example.org/example", actualCredentialService.getUrl());
        assertEquals("ABC123", actualCredentialService.getToken());
    }

    @Test
    public void testCreateCredential() {
        CredentialService credentialService = new CredentialService("https://example.org/example", "ABC123", "myproject");

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
//        assertThrows(RuntimeException.class, () -> credentialService.createCredential(credentialWrapper, "Did"));
    }

    @Test
    public void testCreateCredential2() {
        CredentialService credentialService = new CredentialService("UUU/UUU", "ABC123", "myproject");

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
//        assertThrows(RuntimeException.class, () -> credentialService.createCredential(credentialWrapper, "Did"));
    }

    @Test
    public void testCreateCredential3() {
        CredentialService credentialService = new CredentialService("https://example.org/example", "UUU/UUU", "myproject");

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
//        assertThrows(RuntimeException.class, () -> credentialService.createCredential(credentialWrapper, "Did"));
    }

    @Test
    public void testVerifyCredential() {
        CredentialService credentialService = new CredentialService("https://example.org/example", "ABC123", "myproject");

        PublicKey publicKey = new PublicKey();
        publicKey.setPublicKey("Public Key");
        publicKey.setType("Type");

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

        VerifyCredentialWrapper verifyCredentialWrapper = new VerifyCredentialWrapper();
        verifyCredentialWrapper.setPublicKey(publicKey);
        verifyCredentialWrapper.setCredentialWrapper(credentialWrapper);
//        assertThrows(RuntimeException.class, () -> credentialService.verifyCredential(verifyCredentialWrapper, "Did"));
    }

    @Test
    public void testVerifyCredential2() {
        CredentialService credentialService = new CredentialService("\"", "ABC123", "myproject");

        PublicKey publicKey = new PublicKey();
        publicKey.setPublicKey("Public Key");
        publicKey.setType("Type");

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

        VerifyCredentialWrapper verifyCredentialWrapper = new VerifyCredentialWrapper();
        verifyCredentialWrapper.setPublicKey(publicKey);
        verifyCredentialWrapper.setCredentialWrapper(credentialWrapper);
//        assertThrows(RuntimeException.class, () -> credentialService.verifyCredential(verifyCredentialWrapper, "Did"));
    }
}

