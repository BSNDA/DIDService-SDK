package com.reddate.did.protocol.request;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import org.junit.Test;

import com.reddate.did.sdk.protocol.request.Proof;
import com.reddate.did.sdk.protocol.request.RevokeCredentialWrapper;

public class RevokeCredentialWrapperTest {
    @Test
    public void testConstructor() {
        RevokeCredentialWrapper actualRevokeCredentialWrapper = new RevokeCredentialWrapper();
        actualRevokeCredentialWrapper.setCptId(123L);
        actualRevokeCredentialWrapper.setCredId("42");
        actualRevokeCredentialWrapper.setDid("Did");
        Proof proof = new Proof();
        proof.setCreator("Creator");
        proof.setSignatureValue("42");
        proof.setType("Type");
        actualRevokeCredentialWrapper.setProof(proof);
        actualRevokeCredentialWrapper.setRovekeDate("2020-03-01");
        assertEquals(123L, actualRevokeCredentialWrapper.getCptId().longValue());
        assertEquals("42", actualRevokeCredentialWrapper.getCredId());
        assertEquals("Did", actualRevokeCredentialWrapper.getDid());
        assertSame(proof, actualRevokeCredentialWrapper.getProof());
        assertEquals("2020-03-01", actualRevokeCredentialWrapper.getRovekeDate());
    }
}

