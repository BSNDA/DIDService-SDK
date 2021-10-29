package com.reddate.did.protocol.request;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.util.HashMap;

import org.junit.Test;

import com.reddate.did.sdk.protocol.request.CredentialWrapper;

public class CredentialWrapperTest {
    @Test
    public void testConstructor() {
        CredentialWrapper actualCredentialWrapper = new CredentialWrapper();
        HashMap<String, Object> stringObjectMap = new HashMap<String, Object>(1);
        actualCredentialWrapper.setClaim(stringObjectMap);
        actualCredentialWrapper.setContext("Context");
        actualCredentialWrapper.setCptId(123L);
        actualCredentialWrapper.setExpirationDate("2020-03-01");
        actualCredentialWrapper.setIssuerDid("Issuer Did");
        actualCredentialWrapper.setLongDesc("Long Desc");
        HashMap<String, Object> stringObjectMap1 = new HashMap<String, Object>(1);
        actualCredentialWrapper.setProof(stringObjectMap1);
        actualCredentialWrapper.setShortDesc("Short Desc");
        actualCredentialWrapper.setType("Type");
        actualCredentialWrapper.setUserDid("User Did");
        assertSame(stringObjectMap, actualCredentialWrapper.getClaim());
        assertEquals("Context", actualCredentialWrapper.getContext());
        assertEquals(123L, actualCredentialWrapper.getCptId().longValue());
        assertEquals("2020-03-01", actualCredentialWrapper.getExpirationDate());
        assertEquals("Issuer Did", actualCredentialWrapper.getIssuerDid());
        assertEquals("Long Desc", actualCredentialWrapper.getLongDesc());
        assertSame(stringObjectMap1, actualCredentialWrapper.getProof());
        assertEquals("Short Desc", actualCredentialWrapper.getShortDesc());
        assertEquals("Type", actualCredentialWrapper.getType());
        assertEquals("User Did", actualCredentialWrapper.getUserDid());
    }
}

