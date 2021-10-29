package com.reddate.did.protocol.request;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import org.junit.Test;

import com.reddate.did.sdk.protocol.common.DidDocument;
import com.reddate.did.sdk.protocol.common.Proof;
import com.reddate.did.sdk.protocol.common.PublicKey;
import com.reddate.did.sdk.protocol.request.ResetDidWrapper;

public class ResetDidWrapperTest {
    @Test
    public void testConstructor() {
        ResetDidWrapper actualResetDidWrapper = new ResetDidWrapper();
        actualResetDidWrapper.setAuthPubKeySign("Auth Pub Key Sign");
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
        actualResetDidWrapper.setDidDoc(didDocument);
        assertEquals("Auth Pub Key Sign", actualResetDidWrapper.getAuthPubKeySign());
        assertSame(didDocument, actualResetDidWrapper.getDidDoc());
    }
}

