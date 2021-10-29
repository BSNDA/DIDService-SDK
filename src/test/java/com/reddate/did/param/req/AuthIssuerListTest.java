package com.reddate.did.param.req;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.reddate.did.sdk.param.req.AuthIssuerList;

public class AuthIssuerListTest {
    @Test
    public void testConstructor() {
        AuthIssuerList actualAuthIssuerList = new AuthIssuerList();
        actualAuthIssuerList.setDid("Did");
        actualAuthIssuerList.setPage(1);
        actualAuthIssuerList.setSize(3);
        assertEquals("Did", actualAuthIssuerList.getDid());
        assertEquals(1, actualAuthIssuerList.getPage().intValue());
        assertEquals(3, actualAuthIssuerList.getSize().intValue());
    }
}

