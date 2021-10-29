package com.reddate.did.param.req;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.reddate.did.sdk.param.req.ResetDidAuthKey;

public class ResetDidAuthKeyTest {
    @Test
    public void testConstructor() {
        ResetDidAuthKey actualResetDidAuthKey = new ResetDidAuthKey();
        actualResetDidAuthKey.setPrivateKey("Private Key");
        actualResetDidAuthKey.setPublicKey("Public Key");
        actualResetDidAuthKey.setType("Type");
        assertEquals("Private Key", actualResetDidAuthKey.getPrivateKey());
        assertEquals("Public Key", actualResetDidAuthKey.getPublicKey());
        assertEquals("Type", actualResetDidAuthKey.getType());
    }
}

