package com.reddate.did.param.req;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import org.junit.Test;

import com.reddate.did.sdk.param.req.ResetDidAuth;
import com.reddate.did.sdk.param.req.ResetDidAuthKey;
import com.reddate.did.sdk.protocol.common.KeyPair;

public class ResetDidAuthTest {
    @Test
    public void testConstructor() {
        ResetDidAuth actualResetDidAuth = new ResetDidAuth();
        actualResetDidAuth.setDid("Did");
        KeyPair keyPair = new KeyPair();
        keyPair.setPublicKey("Public Key");
        keyPair.setPrivateKey("Private Key");
        keyPair.setType("Type");
        actualResetDidAuth.setPrimaryKeyPair(keyPair);
        ResetDidAuthKey resetDidAuthKey = new ResetDidAuthKey();
        resetDidAuthKey.setPublicKey("Public Key");
        resetDidAuthKey.setPrivateKey("Private Key");
        resetDidAuthKey.setType("Type");
        actualResetDidAuth.setRecoveryKey(resetDidAuthKey);
        assertEquals("Did", actualResetDidAuth.getDid());
        assertSame(keyPair, actualResetDidAuth.getPrimaryKeyPair());
        assertSame(resetDidAuthKey, actualResetDidAuth.getRecoveryKey());
    }
}

