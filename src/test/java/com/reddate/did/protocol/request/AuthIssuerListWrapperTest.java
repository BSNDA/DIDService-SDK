package com.reddate.did.protocol.request;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.reddate.did.sdk.protocol.request.AuthIssuerListWrapper;

public class AuthIssuerListWrapperTest {
    @Test
    public void testConstructor() {
        AuthIssuerListWrapper actualAuthIssuerListWrapper = new AuthIssuerListWrapper();
        actualAuthIssuerListWrapper.setDid("Did");
        actualAuthIssuerListWrapper.setPage(1);
        actualAuthIssuerListWrapper.setSize(3);
        assertEquals("Did", actualAuthIssuerListWrapper.getDid());
        assertEquals(1, actualAuthIssuerListWrapper.getPage().intValue());
        assertEquals(3, actualAuthIssuerListWrapper.getSize().intValue());
    }
}

