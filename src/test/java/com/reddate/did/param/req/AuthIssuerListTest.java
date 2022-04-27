package com.reddate.did.param.req;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.reddate.did.sdk.param.req.AuthIssuer;

public class AuthIssuerListTest {
    @Test
    public void testConstructor() {
        AuthIssuer actualAuthIssuerList = new AuthIssuer();
        actualAuthIssuerList.setDid("Did");
        actualAuthIssuerList.setPage(1);
        actualAuthIssuerList.setSize(3);
        assertEquals("Did", actualAuthIssuerList.getDid());
        assertEquals(1, actualAuthIssuerList.getPage().intValue());
        assertEquals(3, actualAuthIssuerList.getSize().intValue());
    }
}

