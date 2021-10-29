package com.reddate.did.protocol.response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import org.junit.Test;

import com.reddate.did.sdk.protocol.common.DidDocument;
import com.reddate.did.sdk.protocol.common.KeyPair;
import com.reddate.did.sdk.protocol.common.Proof;
import com.reddate.did.sdk.protocol.common.PublicKey;
import com.reddate.did.sdk.protocol.response.CreateDidData;

public class CreateDidDataTest {
    @Test
    public void testConstructor() {
        CreateDidData actualCreateDidData = new CreateDidData();
        KeyPair keyPair = new KeyPair();
        keyPair.setPublicKey("Public Key");
        keyPair.setPrivateKey("Private Key");
        keyPair.setType("Type");
        actualCreateDidData.setAuthKeyInfo(keyPair);
        actualCreateDidData.setDid("Did");
        PublicKey publicKey = new PublicKey();
        publicKey.setPublicKey("Public Key");
        publicKey.setType("Type");
        Proof proof = new Proof();
        proof.setCreator("Creator");
        proof.setSignatureValue("42");
        proof.setType("Type");
        PublicKey publicKey1 = new PublicKey();
        publicKey1.setPublicKey("Public Key");
        publicKey1.setType("Type");
        DidDocument didDocument = new DidDocument();
        didDocument.setRecovery(publicKey);
        didDocument.setUpdated("2020-03-01");
        didDocument.setProof(proof);
        didDocument.setCreated("Jan 1, 2020 8:00am GMT+0100");
        didDocument.setVersion("1.0.2");
        didDocument.setAuthentication(publicKey1);
        didDocument.setDid("Did");
        actualCreateDidData.setDidDocument(didDocument);
        KeyPair keyPair1 = new KeyPair();
        keyPair1.setPublicKey("Public Key");
        keyPair1.setPrivateKey("Private Key");
        keyPair1.setType("Type");
        actualCreateDidData.setRecyKeyInfo(keyPair1);
        assertSame(keyPair, actualCreateDidData.getAuthKeyInfo());
        assertEquals("Did", actualCreateDidData.getDid());
        assertSame(didDocument, actualCreateDidData.getDidDocument());
        assertSame(keyPair1, actualCreateDidData.getRecyKeyInfo());
    }
}

