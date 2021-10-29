package com.reddate.did.util;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.io.UnsupportedEncodingException;

import org.junit.Test;

import com.reddate.did.sdk.util.Base58Utils;

public class Base58UtilsTest {
    @Test
    public void testEncode() throws UnsupportedEncodingException, IllegalArgumentException {
        byte[] bytes = "AAAAAAAA".getBytes("UTF-8");
        assertArrayEquals(bytes, Base58Utils.decode(Base58Utils.encode(bytes)));
    }

    @Test
    public void testEncode2() throws IllegalArgumentException {
        byte[] byteArray = new byte[]{0, 'A', 'A', 'A', 'A', 'A', 'A', 'A'};
        assertArrayEquals(byteArray, Base58Utils.decode(Base58Utils.encode(byteArray)));
    }

    @Test
    public void testEncode3() throws IllegalArgumentException {
        byte[] byteArray = new byte[]{};
        assertArrayEquals(byteArray, Base58Utils.decode(Base58Utils.encode(byteArray)));
    }

    @Test
    public void testDecodeToBigInteger() throws IllegalArgumentException {
        assertThrows(IllegalArgumentException.class, () -> Base58Utils.decodeToBigInteger("Input"));
        assertEquals("130546", Base58Utils.decodeToBigInteger("foo").toString());
        assertEquals("584889621711786543479188656466418868554454211731455017244085522414579008191811299673190496036017573",
                Base58Utils.decodeToBigInteger("123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz").toString());
        assertEquals("0", Base58Utils.decodeToBigInteger("").toString());
    }
}

