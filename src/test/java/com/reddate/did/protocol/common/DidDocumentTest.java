package com.reddate.did.protocol.common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import org.junit.Test;

import com.reddate.did.sdk.protocol.common.DidDocument;
import com.reddate.did.sdk.protocol.common.Proof;
import com.reddate.did.sdk.protocol.common.PublicKey;

public class DidDocumentTest {
    @Test
    public void testConstructor() {
        DidDocument actualDidDocument = new DidDocument();
        PublicKey publicKey = new PublicKey();
        publicKey.setPublicKey("Public Key");
        publicKey.setType("Type");
        actualDidDocument.setAuthentication(publicKey);
        actualDidDocument.setCreated("Jan 1, 2020 8:00am GMT+0100");
        actualDidDocument.setDid("Did");
        Proof proof = new Proof();
        proof.setCreator("Creator");
        proof.setSignatureValue("42");
        proof.setType("Type");
        actualDidDocument.setProof(proof);
        PublicKey publicKey1 = new PublicKey();
        publicKey1.setPublicKey("Public Key");
        publicKey1.setType("Type");
        actualDidDocument.setRecovery(publicKey1);
        actualDidDocument.setUpdated("2020-03-01");
        actualDidDocument.setVersion("1.0.2");
        assertSame(publicKey, actualDidDocument.getAuthentication());
        assertEquals("Jan 1, 2020 8:00am GMT+0100", actualDidDocument.getCreated());
        assertEquals("Did", actualDidDocument.getDid());
        assertSame(proof, actualDidDocument.getProof());
        assertSame(publicKey1, actualDidDocument.getRecovery());
        assertEquals("2020-03-01", actualDidDocument.getUpdated());
        assertEquals("1.0.2", actualDidDocument.getVersion());
    }
}

