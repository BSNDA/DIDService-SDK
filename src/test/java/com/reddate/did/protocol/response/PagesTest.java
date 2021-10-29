package com.reddate.did.protocol.response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.util.ArrayList;

import org.junit.Test;

import com.reddate.did.sdk.protocol.response.Pages;

public class PagesTest {
    @Test
    public void testConstructor() {
        Pages<Object> actualPages = new Pages<Object>();
        actualPages.setPage(1);
        ArrayList<Object> objectList = new ArrayList<Object>();
        actualPages.setResult(objectList);
        actualPages.setSize(3);
        actualPages.setTotalNum(10);
        actualPages.setTotalPage(1);
        assertEquals(1, actualPages.getPage().intValue());
        assertSame(objectList, actualPages.getResult());
        assertEquals(3, actualPages.getSize().intValue());
        assertEquals(10, actualPages.getTotalNum().intValue());
        assertEquals(1, actualPages.getTotalPage().intValue());
    }
}

