package com.reddate.did.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;

import java.math.BigInteger;

import org.fisco.bcos.web3j.crypto.ECKeyPair;

import org.fisco.bcos.web3j.crypto.Sign;
import org.junit.Test;

import com.reddate.did.sdk.util.ECDSAUtils;

public class ECDSAUtilsTest {
    @Test
    public void testCreateKey() throws Exception {
        assertEquals(ECDSAUtils.TYPE, ECDSAUtils.createKey().getType());
    }

    @Test
    public void testGetPublicKey() {
        assertThrows(RuntimeException.class, () -> ECDSAUtils.getPublicKey("Private Key"));
        assertEquals("1333193071510143823680032068730801233544396975037522064189058407646457415432098030100736581188409203"
                + "9760895910548088435115588884684157831830796456056960436", ECDSAUtils.getPublicKey("42"));
    }

    @Test
    public void testSign() throws Exception {
        assertEquals("9L7S9k/RUFqYjjfNiIdKpdqgNOjlE1eNlergfWSAAkwTBzoeQwGHn+P4Rr13C9L2dRQUQHGmMfEDbXKtAyhplQA=",
                ECDSAUtils.sign("Not all who wander are lost", "42"));
        assertEquals("rRGRFzql0aMrV8IBgEyZlH8VDwXFiPyMd7aSiWaz8Z52GUJLMYZkl59P5P0Ve3jmKVT2gEekEeCzgbgCMIVyxgE=",
                ECDSAUtils.sign("Message", "42"));
    }

    @Test
    public void testSecp256k1SignToSignature() {
        Sign.SignatureData actualSecp256k1SignToSignatureResult = ECDSAUtils.secp256k1SignToSignature("Raw Data",
                BigInteger.valueOf(1L));
        assertNull(actualSecp256k1SignToSignatureResult.getPub());
        assertEquals((byte) 0, actualSecp256k1SignToSignatureResult.getV());
        assertEquals(Integer.SIZE, actualSecp256k1SignToSignatureResult.getS().length);
        assertEquals(Integer.SIZE, actualSecp256k1SignToSignatureResult.getR().length);
    }

    @Test
    public void testSecp256k1SignToSignature2() {
        Sign.SignatureData actualSecp256k1SignToSignatureResult = ECDSAUtils.secp256k1SignToSignature("Raw Data",
                BigInteger.valueOf(16L));
        assertNull(actualSecp256k1SignToSignatureResult.getPub());
        assertEquals((byte) 0, actualSecp256k1SignToSignatureResult.getV());
        assertEquals(Integer.SIZE, actualSecp256k1SignToSignatureResult.getS().length);
        assertEquals(Integer.SIZE, actualSecp256k1SignToSignatureResult.getR().length);
    }

    @Test
    public void testSecp256k1SignToSignature3() {
        Sign.SignatureData actualSecp256k1SignToSignatureResult = ECDSAUtils.secp256k1SignToSignature("",
                BigInteger.valueOf(16L));
        assertNull(actualSecp256k1SignToSignatureResult.getPub());
        assertEquals((byte) 0, actualSecp256k1SignToSignatureResult.getV());
        assertEquals(Integer.SIZE, actualSecp256k1SignToSignatureResult.getS().length);
        assertEquals(Integer.SIZE, actualSecp256k1SignToSignatureResult.getR().length);
    }

    @Test
    public void testSecp256k1SignToSignature4() {
        BigInteger privateKey = BigInteger.valueOf(1L);
        Sign.SignatureData actualSecp256k1SignToSignatureResult = ECDSAUtils.secp256k1SignToSignature("Raw Data",
                new ECKeyPair(privateKey, BigInteger.valueOf(1L)));
        assertNull(actualSecp256k1SignToSignatureResult.getPub());
        assertEquals((byte) 0, actualSecp256k1SignToSignatureResult.getV());
        assertEquals(Integer.SIZE, actualSecp256k1SignToSignatureResult.getS().length);
        assertEquals(Integer.SIZE, actualSecp256k1SignToSignatureResult.getR().length);
    }

    @Test
    public void testSecp256k1SignToSignature5() {
        BigInteger privateKey = BigInteger.valueOf(Long.MAX_VALUE);
        Sign.SignatureData actualSecp256k1SignToSignatureResult = ECDSAUtils.secp256k1SignToSignature("42",
                new ECKeyPair(privateKey, BigInteger.valueOf(1L)));
        assertNull(actualSecp256k1SignToSignatureResult.getPub());
        assertEquals((byte) 1, actualSecp256k1SignToSignatureResult.getV());
        assertEquals(Integer.SIZE, actualSecp256k1SignToSignatureResult.getS().length);
        assertEquals(Integer.SIZE, actualSecp256k1SignToSignatureResult.getR().length);
    }
}

