package com.reddate.did.param.req;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.reddate.did.sdk.param.req.RevokedCredList;

public class RevokedCredListTest {
    @Test
    public void testConstructor() {
        RevokedCredList actualRevokedCredList = new RevokedCredList();
        actualRevokedCredList.setDid("Did");
        actualRevokedCredList.setPage(1);
        actualRevokedCredList.setSize(3);
        assertEquals("Did", actualRevokedCredList.getDid());
        assertEquals(1, actualRevokedCredList.getPage().intValue());
        assertEquals(3, actualRevokedCredList.getSize().intValue());
    }
}

