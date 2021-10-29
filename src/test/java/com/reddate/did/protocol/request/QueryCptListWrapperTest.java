package com.reddate.did.protocol.request;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.reddate.did.sdk.protocol.request.QueryCptListWrapper;

public class QueryCptListWrapperTest {
    @Test
    public void testConstructor() {
        QueryCptListWrapper actualQueryCptListWrapper = new QueryCptListWrapper();
        actualQueryCptListWrapper.setDid("Did");
        actualQueryCptListWrapper.setPage(1);
        actualQueryCptListWrapper.setSize(3);
        assertEquals("Did", actualQueryCptListWrapper.getDid());
        assertEquals(1, actualQueryCptListWrapper.getPage().intValue());
        assertEquals(3, actualQueryCptListWrapper.getSize().intValue());
    }
}

