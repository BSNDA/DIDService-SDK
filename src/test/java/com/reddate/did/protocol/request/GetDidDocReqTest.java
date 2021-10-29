package com.reddate.did.protocol.request;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.reddate.did.sdk.protocol.request.GetDidDocReq;

public class GetDidDocReqTest {
    @Test
    public void testConstructor() {
        GetDidDocReq actualGetDidDocReq = new GetDidDocReq();
        actualGetDidDocReq.setDid("Did");
        assertEquals("Did", actualGetDidDocReq.getDid());
    }
}

