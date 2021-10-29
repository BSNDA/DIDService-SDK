package com.reddate.did.param.resp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import org.junit.Test;

import com.reddate.did.sdk.param.resp.DidDataWrapper;
import com.reddate.did.sdk.param.resp.DocumentInfo;
import com.reddate.did.sdk.protocol.common.KeyPair;
import com.reddate.did.sdk.protocol.common.Proof;
import com.reddate.did.sdk.protocol.common.PublicKey;

public class DidDataWrapperTest {
    @Test
    public void testConstructor() {
        DidDataWrapper actualDidDataWrapper = new DidDataWrapper();
        KeyPair keyPair = new KeyPair();
        keyPair.setPublicKey("Public Key");
        keyPair.setPrivateKey("Private Key");
        keyPair.setType("Type");
        actualDidDataWrapper.setAuthKeyInfo(keyPair);
        actualDidDataWrapper.setDid("Did");
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
        DocumentInfo documentInfo = new DocumentInfo();
        documentInfo.setRecovery(publicKey);
        documentInfo.setUpdated("2020-03-01");
        documentInfo.setProof(proof);
        documentInfo.setCreated("Jan 1, 2020 8:00am GMT+0100");
        documentInfo.setVersion("1.0.2");
        documentInfo.setAuthentication(publicKey1);
        documentInfo.setDid("Did");
        actualDidDataWrapper.setDocument(documentInfo);
        KeyPair keyPair1 = new KeyPair();
        keyPair1.setPublicKey("Public Key");
        keyPair1.setPrivateKey("Private Key");
        keyPair1.setType("Type");
        actualDidDataWrapper.setRecyKeyInfo(keyPair1);
        assertSame(keyPair, actualDidDataWrapper.getAuthKeyInfo());
        assertEquals("Did", actualDidDataWrapper.getDid());
        assertSame(documentInfo, actualDidDataWrapper.getDocument());
        assertSame(keyPair1, actualDidDataWrapper.getRecyKeyInfo());
    }
}

