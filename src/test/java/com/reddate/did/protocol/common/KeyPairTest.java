package com.reddate.did.protocol.common;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.reddate.did.sdk.protocol.common.KeyPair;

public class KeyPairTest {
    @Test
    public void testConstructor() {
        KeyPair actualKeyPair = new KeyPair();
        actualKeyPair.setPrivateKey("Private Key");
        actualKeyPair.setPublicKey("Public Key");
        actualKeyPair.setType("Type");
        assertEquals("Private Key", actualKeyPair.getPrivateKey());
        assertEquals("Public Key", actualKeyPair.getPublicKey());
        assertEquals("Type", actualKeyPair.getType());
    }
}

