package com.reddate.did.protocol.common;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.reddate.did.sdk.protocol.common.Proof;

public class ProofTest {
    @Test
    public void testConstructor() {
        Proof actualProof = new Proof();
        actualProof.setCreator("Creator");
        actualProof.setSignatureValue("42");
        actualProof.setType("Type");
        assertEquals("Creator", actualProof.getCreator());
        assertEquals("42", actualProof.getSignatureValue());
        assertEquals("Type", actualProof.getType());
    }
}

