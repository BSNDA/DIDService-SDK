package com.reddate.did.protocol.response;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.reddate.did.sdk.protocol.response.BaseCredential;

public class BaseCredentialTest {
    @Test
    public void testConstructor() {
        BaseCredential actualBaseCredential = new BaseCredential();
        actualBaseCredential.setCreated("Jan 1, 2020 8:00am GMT+0100");
        actualBaseCredential.setId("42");
        assertEquals("Jan 1, 2020 8:00am GMT+0100", actualBaseCredential.getCreated());
        assertEquals("42", actualBaseCredential.getId());
    }
}

