package com.reddate.did.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.reddate.did.sdk.util.SHA256Utils;

public class SHA256UtilsTest {
    @Test
    public void testGetSHA256() {
        assertEquals(Integer.SIZE, SHA256Utils.getSha256("Str").length);
    }

    @Test
    public void testGetSHA256String() {
        assertEquals("8084a51b3c649e8899252edf603c110c821c00176fad58c411925b1890807b46",
                SHA256Utils.getSha256String("Str"));
    }
}

