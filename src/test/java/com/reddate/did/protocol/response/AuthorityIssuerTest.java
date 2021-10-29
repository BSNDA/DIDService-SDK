package com.reddate.did.protocol.response;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.reddate.did.sdk.protocol.response.AuthorityIssuer;

public class AuthorityIssuerTest {
    @Test
    public void testConstructor() {
        AuthorityIssuer actualAuthorityIssuer = new AuthorityIssuer();
        actualAuthorityIssuer.setDid("Did");
        actualAuthorityIssuer.setName("Name");
        assertEquals("Did", actualAuthorityIssuer.getDid());
        assertEquals("Name", actualAuthorityIssuer.getName());
    }
}

