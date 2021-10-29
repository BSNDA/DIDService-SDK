package com.reddate.did.param.resp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import org.junit.Test;

import com.reddate.did.sdk.param.resp.DocumentInfo;
import com.reddate.did.sdk.protocol.common.Proof;
import com.reddate.did.sdk.protocol.common.PublicKey;

public class DocumentInfoTest {
    @Test
    public void testConstructor() {
        DocumentInfo actualDocumentInfo = new DocumentInfo();
        PublicKey publicKey = new PublicKey();
        publicKey.setPublicKey("Public Key");
        publicKey.setType("Type");
        actualDocumentInfo.setAuthentication(publicKey);
        actualDocumentInfo.setCreated("Jan 1, 2020 8:00am GMT+0100");
        actualDocumentInfo.setDid("Did");
        Proof proof = new Proof();
        proof.setCreator("Creator");
        proof.setSignatureValue("42");
        proof.setType("Type");
        actualDocumentInfo.setProof(proof);
        PublicKey publicKey1 = new PublicKey();
        publicKey1.setPublicKey("Public Key");
        publicKey1.setType("Type");
        actualDocumentInfo.setRecovery(publicKey1);
        actualDocumentInfo.setUpdated("2020-03-01");
        actualDocumentInfo.setVersion("1.0.2");
        assertSame(publicKey, actualDocumentInfo.getAuthentication());
        assertEquals("Jan 1, 2020 8:00am GMT+0100", actualDocumentInfo.getCreated());
        assertEquals("Did", actualDocumentInfo.getDid());
        assertSame(proof, actualDocumentInfo.getProof());
        assertSame(publicKey1, actualDocumentInfo.getRecovery());
        assertEquals("2020-03-01", actualDocumentInfo.getUpdated());
        assertEquals("1.0.2", actualDocumentInfo.getVersion());
    }
}

