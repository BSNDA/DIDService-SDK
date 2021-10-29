package com.reddate.did.param.req;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.reddate.did.sdk.param.req.AuthorityIssuer;

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

