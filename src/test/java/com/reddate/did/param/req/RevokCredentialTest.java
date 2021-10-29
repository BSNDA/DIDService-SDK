package com.reddate.did.param.req;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.reddate.did.sdk.param.req.RevokeCredential;

public class RevokCredentialTest {
    @Test
    public void testConstructor() {
        RevokeCredential actualRevokCredential = new RevokeCredential();
        actualRevokCredential.setCptId(123L);
        actualRevokCredential.setCredId("42");
        actualRevokCredential.setDid("Did");
        actualRevokCredential.setPrivateKey("Private Key");
        assertEquals(123L, actualRevokCredential.getCptId().longValue());
        assertEquals("42", actualRevokCredential.getCredId());
        assertEquals("Did", actualRevokCredential.getDid());
        assertEquals("Private Key", actualRevokCredential.getPrivateKey());
    }

    @Test
    public void testConstructor2() {
        RevokeCredential actualRevokCredential = new RevokeCredential();
        actualRevokCredential.setCptId(123L);
        actualRevokCredential.setCredId("42");
        actualRevokCredential.setDid("Did");
        actualRevokCredential.setPrivateKey("Private Key");
        assertEquals(123L, actualRevokCredential.getCptId().longValue());
        assertEquals("42", actualRevokCredential.getCredId());
        assertEquals("Did", actualRevokCredential.getDid());
        assertEquals("Private Key", actualRevokCredential.getPrivateKey());
    }
}

