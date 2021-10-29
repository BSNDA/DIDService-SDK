package com.reddate.did.protocol.response;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.reddate.did.sdk.protocol.response.BaseCredentialData;

public class BaseCredentialDataTest {
    @Test
    public void testConstructor() {
        BaseCredentialData actualBaseCredentialData = new BaseCredentialData();
        actualBaseCredentialData.setCreated("Jan 1, 2020 8:00am GMT+0100");
        actualBaseCredentialData.setId("42");
        assertEquals("Jan 1, 2020 8:00am GMT+0100", actualBaseCredentialData.getCreated());
        assertEquals("42", actualBaseCredentialData.getId());
    }
}

