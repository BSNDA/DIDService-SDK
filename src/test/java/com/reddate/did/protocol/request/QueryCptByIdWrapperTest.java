package com.reddate.did.protocol.request;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.reddate.did.sdk.protocol.request.QueryCptByIdWrapper;

public class QueryCptByIdWrapperTest {
    @Test
    public void testConstructor() {
        QueryCptByIdWrapper actualQueryCptByIdWrapper = new QueryCptByIdWrapper();
        actualQueryCptByIdWrapper.setCptId(123L);
        assertEquals(123L, actualQueryCptByIdWrapper.getCptId().longValue());
    }
}

