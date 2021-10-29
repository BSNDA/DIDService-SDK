package com.reddate.did.protocol.request;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.reddate.did.sdk.protocol.request.QueryCptListReq;

public class QueryCptListReqTest {
    @Test
    public void testConstructor() {
        QueryCptListReq actualQueryCptListReq = new QueryCptListReq();
        actualQueryCptListReq.setDid("Did");
        actualQueryCptListReq.setPage(1);
        actualQueryCptListReq.setSize(3);
        assertEquals("Did", actualQueryCptListReq.getDid());
        assertEquals(1, actualQueryCptListReq.getPage().intValue());
        assertEquals(3, actualQueryCptListReq.getSize().intValue());
    }
}

