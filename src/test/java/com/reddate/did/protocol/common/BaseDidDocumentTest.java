package com.reddate.did.protocol.common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import org.junit.Test;

import com.reddate.did.sdk.protocol.common.BaseDidDocument;
import com.reddate.did.sdk.protocol.common.PublicKey;

public class BaseDidDocumentTest {
    @Test
    public void testConstructor() {
        BaseDidDocument actualBaseDidDocument = new BaseDidDocument();
        PublicKey publicKey = new PublicKey();
        publicKey.setPublicKey("Public Key");
        publicKey.setType("Type");
        actualBaseDidDocument.setAuthentication(publicKey);
        actualBaseDidDocument.setContext("Context");
        PublicKey publicKey1 = new PublicKey();
        publicKey1.setPublicKey("Public Key");
        publicKey1.setType("Type");
        actualBaseDidDocument.setRecovery(publicKey1);
        assertSame(publicKey, actualBaseDidDocument.getAuthentication());
        assertEquals("Context", actualBaseDidDocument.getContext());
        assertSame(publicKey1, actualBaseDidDocument.getRecovery());
    }
}

