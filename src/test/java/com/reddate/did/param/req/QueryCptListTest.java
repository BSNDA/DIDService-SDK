package com.reddate.did.param.req;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.reddate.did.sdk.param.req.QueryCptList;

public class QueryCptListTest {
    @Test
    public void testConstructor() {
        QueryCptList actualQueryCptList = new QueryCptList();
        actualQueryCptList.setDid("Did");
        actualQueryCptList.setPage(1);
        actualQueryCptList.setSize(3);
        assertEquals("Did", actualQueryCptList.getDid());
        assertEquals(1, actualQueryCptList.getPage().intValue());
        assertEquals(3, actualQueryCptList.getSize().intValue());
    }
}

