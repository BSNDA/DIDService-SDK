package com.reddate.did.protocol.request;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.reddate.did.sdk.protocol.request.QueryCredentialWrapper;

public class QueryCredentialWrapperTest {
    @Test
    public void testConstructor() {
        QueryCredentialWrapper actualQueryCredentialWrapper = new QueryCredentialWrapper();
        actualQueryCredentialWrapper.setDid("Did");
        actualQueryCredentialWrapper.setPage(1);
        actualQueryCredentialWrapper.setSize(3);
        assertEquals("Did", actualQueryCredentialWrapper.getDid());
        assertEquals(1, actualQueryCredentialWrapper.getPage().intValue());
        assertEquals(3, actualQueryCredentialWrapper.getSize().intValue());
    }
}

