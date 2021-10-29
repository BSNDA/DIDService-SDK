package com.reddate.did.param.req;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.util.HashMap;

import org.junit.Test;

import com.reddate.did.sdk.param.req.CreateCredential;

public class CreateCredentialTest {
    @Test
    public void testConstructor() {
        CreateCredential actualCreateCredential = new CreateCredential();
        HashMap<String, Object> stringObjectMap = new HashMap<String, Object>(1);
        actualCreateCredential.setClaim(stringObjectMap);
        actualCreateCredential.setCptId(123L);
        actualCreateCredential.setExpirationDate("2020-03-01");
        actualCreateCredential.setIssuerDid("Issuer Did");
        actualCreateCredential.setPrivateKey("Private Key");
        actualCreateCredential.setType("Type");
        actualCreateCredential.setUserDid("User Did");
        assertSame(stringObjectMap, actualCreateCredential.getClaim());
        assertEquals(123L, actualCreateCredential.getCptId().longValue());
        assertEquals("2020-03-01", actualCreateCredential.getExpirationDate());
        assertEquals("Issuer Did", actualCreateCredential.getIssuerDid());
        assertEquals("Private Key", actualCreateCredential.getPrivateKey());
        assertEquals("Type", actualCreateCredential.getType());
        assertEquals("User Did", actualCreateCredential.getUserDid());
    }
}

