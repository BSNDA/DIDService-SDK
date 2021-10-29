package com.reddate.did.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;

import org.junit.Test;

import com.reddate.did.sdk.util.RipeMDUtils;

public class RipeMDUtilsTest {
    @Test
    public void testEncodeRipeMD128() throws Exception {
        assertEquals(Short.SIZE, RipeMDUtils.encodeRipeMd128("AAAAAAAA".getBytes("UTF-8")).length);
        assertEquals(Short.SIZE, RipeMDUtils.encodeRipeMd128(new byte[]{0, 'A', 'A', 'A', 'A', 'A', 'A', 'A'}).length);
    }

    @Test
    public void testEncodeRipeMD128Hex() throws Exception {
        assertEquals("7756d50c966032e4165cd8e78c8c3177", RipeMDUtils.encodeRipeMd128Hex("AAAAAAAA".getBytes("UTF-8")));
    }

    @Test
    public void testEncodeRipeMD160() throws Exception {
        assertEquals(20, RipeMDUtils.encodeRipeMd160("AAAAAAAA".getBytes("UTF-8")).length);
    }

    @Test
    public void testEncodeRipeMD160Hex() throws Exception {
        assertEquals("c7b8d405045aeb275b9585469870fe3cf39f4119",
                RipeMDUtils.encodeRipeMd160Hex("AAAAAAAA".getBytes("UTF-8")));
    }

    @Test
    public void testEncodeRipeMD256() throws Exception {
        assertEquals(Integer.SIZE, RipeMDUtils.encodeRipeMd256("AAAAAAAA".getBytes("UTF-8")).length);
    }

    @Test
    public void testEncodeRipeMD256Hex() throws Exception {
        assertEquals("7f83bc22236c040568da5449807ebb68c568228d3d2f4948b35b842b19f8688b",
                RipeMDUtils.encodeRipeMd256Hex("AAAAAAAA".getBytes("UTF-8")));
    }

    @Test
    public void testEncodeRipeMD320() throws Exception {
        assertEquals(40, RipeMDUtils.encodeRipeMd320("AAAAAAAA".getBytes("UTF-8")).length);
    }

    @Test
    public void testEncodeRipeMD320Hex() throws Exception {
        assertEquals("96a24e36414aa3c2b6ee9e018369b34ff38438163fca290674cc003889c19a48c0a286b737d57836",
                RipeMDUtils.encodeRipeMd320Hex("AAAAAAAA".getBytes("UTF-8")));
    }

    @Test
    public void testInitHmacRipeMD128Key() throws Exception {
        assertEquals(Short.SIZE, RipeMDUtils.initHmacRipeMd128Key().length);
    }

    @Test
    public void testEncodeHmacRipeMD128() throws Exception {
        byte[] data = "AAAAAAAA".getBytes("UTF-8");
        assertEquals(Short.SIZE, RipeMDUtils.encodeHmacRipeMd128(data, "AAAAAAAA".getBytes("UTF-8")).length);
    }

    @Test
    public void testEncodeHmacRipeMD1282() throws Exception {
        assertThrows(Exception.class, () -> RipeMDUtils.encodeHmacRipeMd128(null, null));
    }

    @Test
    public void testEncodeHmacRipeMD1283() throws Exception {
        assertEquals(Short.SIZE, RipeMDUtils.encodeHmacRipeMd128(new byte[]{0, 'A', 'A', 'A', 'A', 'A', 'A', 'A'},
                "AAAAAAAA".getBytes("UTF-8")).length);
    }

    @Test
    public void testEncodeHmacRipeMD1284() throws Exception {
        assertThrows(Exception.class, () -> RipeMDUtils.encodeHmacRipeMd128(new byte[]{}, "AAAAAAAA".getBytes("UTF-8")));
    }

    @Test
    public void testEncodeHmacRipeMD1285() throws Exception {
        assertThrows(Exception.class, () -> RipeMDUtils.encodeHmacRipeMd128("AAAAAAAA".getBytes("UTF-8"), null));
    }

    @Test
    public void testEncodeHmacRipeMD1286() throws Exception {
        assertThrows(Exception.class, () -> RipeMDUtils.encodeHmacRipeMd128("AAAAAAAA".getBytes("UTF-8"), new byte[]{}));
    }

    @Test
    public void testEncodeHmacRipeMD128Hex() throws Exception {
        byte[] data = "AAAAAAAA".getBytes("UTF-8");
        assertEquals("c40ff39a46b4fcaec9c4202676715e93",
                RipeMDUtils.encodeHmacRipeMd128Hex(data, "AAAAAAAA".getBytes("UTF-8")));
    }

    @Test
    public void testEncodeHmacRipeMD128Hex2() throws Exception {
        assertThrows(Exception.class, () -> RipeMDUtils.encodeHmacRipeMd128Hex(null, null));
    }

    @Test
    public void testEncodeHmacRipeMD128Hex3() throws Exception {
        assertThrows(Exception.class, () -> RipeMDUtils.encodeHmacRipeMd128Hex(null, "AAAAAAAA".getBytes("UTF-8")));
    }

    @Test
    public void testEncodeHmacRipeMD128Hex4() throws Exception {
        assertThrows(Exception.class, () -> RipeMDUtils.encodeHmacRipeMd128Hex(new byte[]{}, "AAAAAAAA".getBytes("UTF-8")));
    }

    @Test
    public void testEncodeHmacRipeMD128Hex5() throws Exception {
        assertThrows(Exception.class, () -> RipeMDUtils.encodeHmacRipeMd128Hex("AAAAAAAA".getBytes("UTF-8"), new byte[]{}));
    }

    @Test
    public void testInitHmacRipeMD160Key() throws Exception {
        assertEquals(20, RipeMDUtils.initHmacRipeMd160Key().length);
    }

    @Test
    public void testEncodeHmacRipeMD160() throws Exception {
        byte[] data = "AAAAAAAA".getBytes("UTF-8");
        assertEquals(20, RipeMDUtils.encodeHmacRipeMd160(data, "AAAAAAAA".getBytes("UTF-8")).length);
    }

    @Test
    public void testEncodeHmacRipeMD1602() throws Exception {
        assertNull(RipeMDUtils.encodeHmacRipeMd160(new byte[]{'A', 'A', 'A', 'A', 'A', 'A', 'A', 'A'}, null));
    }

    @Test
    public void testEncodeHmacRipeMD1603() throws Exception {
        assertNull(RipeMDUtils.encodeHmacRipeMd160("AAAAAAAA".getBytes("UTF-8"), new byte[]{}));
    }

    @Test
    public void testEncodeHmacRipeMD160Hex() throws Exception {
        byte[] data = "AAAAAAAA".getBytes("UTF-8");
        assertEquals("899800e4da1c707f6454a77d0fb473cbabf5915d",
                RipeMDUtils.encodeHmacRipeMd160Hex(data, "AAAAAAAA".getBytes("UTF-8")));
    }

    @Test
    public void testEncodeHmacRipeMD160Hex2() throws Exception {
        assertNull(RipeMDUtils.encodeHmacRipeMd160Hex(null, null));
    }

    @Test
    public void testEncodeHmacRipeMD160Hex3() throws Exception {
        assertNull(RipeMDUtils.encodeHmacRipeMd160Hex(null, "AAAAAAAA".getBytes("UTF-8")));
    }

    @Test
    public void testEncodeHmacRipeMD160Hex4() throws Exception {
        assertNull(RipeMDUtils.encodeHmacRipeMd160Hex(new byte[]{}, "AAAAAAAA".getBytes("UTF-8")));
    }

    @Test
    public void testEncodeHmacRipeMD160Hex5() throws Exception {
        assertNull(RipeMDUtils.encodeHmacRipeMd160Hex("AAAAAAAA".getBytes("UTF-8"), new byte[]{}));
    }
}

