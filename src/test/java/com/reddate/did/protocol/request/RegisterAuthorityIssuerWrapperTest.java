package com.reddate.did.protocol.request;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.reddate.did.sdk.protocol.request.RegisterAuthorityIssuerWrapper;

public class RegisterAuthorityIssuerWrapperTest {
    @Test
    public void testConstructor() {
        RegisterAuthorityIssuerWrapper actualRegisterAuthorityIssuerWrapper = new RegisterAuthorityIssuerWrapper();
        actualRegisterAuthorityIssuerWrapper.setDid("Did");
        actualRegisterAuthorityIssuerWrapper.setName("Name");
//        actualRegisterAuthorityIssuerWrapper.setPublicKey("Public Key");
        actualRegisterAuthorityIssuerWrapper.setPublicKeySign("Public Key Sign");
        assertEquals("Did", actualRegisterAuthorityIssuerWrapper.getDid());
        assertEquals("Name", actualRegisterAuthorityIssuerWrapper.getName());
//        assertEquals("Public Key", actualRegisterAuthorityIssuerWrapper.getPublicKey());
        assertEquals("Public Key Sign", actualRegisterAuthorityIssuerWrapper.getPublicKeySign());
    }
}

