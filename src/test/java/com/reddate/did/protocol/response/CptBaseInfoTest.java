package com.reddate.did.protocol.response;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.reddate.did.sdk.protocol.response.CptBaseInfo;

public class CptBaseInfoTest {
    @Test
    public void testConstructor() {
        CptBaseInfo actualCptBaseInfo = new CptBaseInfo();
        actualCptBaseInfo.setCptId(123L);
        actualCptBaseInfo.setCptVersion(1);
        assertEquals(123L, actualCptBaseInfo.getCptId().longValue());
        assertEquals(1, actualCptBaseInfo.getCptVersion().intValue());
    }
}

