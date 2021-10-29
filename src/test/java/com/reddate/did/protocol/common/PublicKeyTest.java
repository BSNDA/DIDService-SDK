package com.reddate.did.protocol.common;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.reddate.did.sdk.protocol.common.PublicKey;

public class PublicKeyTest {
    @Test
    public void testConstructor() {
        PublicKey actualPublicKey = new PublicKey();
        actualPublicKey.setPublicKey("Public Key");
        actualPublicKey.setType("Type");
        assertEquals("Public Key", actualPublicKey.getPublicKey());
        assertEquals("Type", actualPublicKey.getType());
    }
}

