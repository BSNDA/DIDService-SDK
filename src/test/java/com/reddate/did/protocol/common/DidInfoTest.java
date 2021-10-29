package com.reddate.did.protocol.common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import org.junit.Test;

import com.reddate.did.sdk.protocol.common.DidInfo;
import com.reddate.did.sdk.protocol.common.PublicKey;

public class DidInfoTest {
    @Test
    public void testConstructor() {
        DidInfo actualDidInfo = new DidInfo();
        actualDidInfo.setAuthentication(new String[]{"Authentication"});
        actualDidInfo.setContext("Context");
        PublicKey publicKey = new PublicKey();
        publicKey.setPublicKey("Public Key");
        publicKey.setType("Type");
        actualDidInfo.setPublicKey(publicKey);
        actualDidInfo.setRecovery(new String[]{"Recovery"});
        assertEquals("Context", actualDidInfo.getContext());
        assertSame(publicKey, actualDidInfo.getPublicKey());
    }
}

