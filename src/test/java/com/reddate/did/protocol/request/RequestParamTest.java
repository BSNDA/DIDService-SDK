package com.reddate.did.protocol.request;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.reddate.did.sdk.protocol.request.RequestParam;

public class RequestParamTest {
    @Test
    public void testConstructor() {
        RequestParam<Object> actualRequestParam = new RequestParam<Object>();
        actualRequestParam.setData("Data");
        actualRequestParam.setDid("Did");
        actualRequestParam.setProjectId("myproject");
        actualRequestParam.setSign("Sign");
        assertEquals("Did", actualRequestParam.getDid());
        assertEquals("myproject", actualRequestParam.getProjectId());
        assertEquals("Sign", actualRequestParam.getSign());
    }

    @Test
    public void testConstructor2() {
        RequestParam<Object> actualRequestParam = new RequestParam<Object>("myproject", "Did");
        actualRequestParam.setData("Data");
        actualRequestParam.setDid("Did");
        actualRequestParam.setProjectId("myproject");
        actualRequestParam.setSign("Sign");
        assertEquals("Did", actualRequestParam.getDid());
        assertEquals("myproject", actualRequestParam.getProjectId());
        assertEquals("Sign", actualRequestParam.getSign());
    }
}

