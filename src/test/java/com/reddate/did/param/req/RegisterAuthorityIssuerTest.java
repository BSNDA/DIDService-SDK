package com.reddate.did.param.req;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.reddate.did.sdk.param.req.RegisterAuthorityIssuer;

public class RegisterAuthorityIssuerTest {
    @Test
    public void testConstructor() {
        RegisterAuthorityIssuer actualRegisterAuthorityIssuer = new RegisterAuthorityIssuer();
        actualRegisterAuthorityIssuer.setPrivateKey("Private Key");
        assertEquals("Private Key", actualRegisterAuthorityIssuer.getPrivateKey());
    }
}

