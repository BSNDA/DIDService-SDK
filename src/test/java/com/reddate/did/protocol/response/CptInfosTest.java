package com.reddate.did.protocol.response;

import static org.junit.Assert.assertSame;

import java.util.ArrayList;

import org.junit.Test;

import com.reddate.did.sdk.protocol.request.CptInfo;
import com.reddate.did.sdk.protocol.response.CptInfos;
import com.reddate.did.sdk.protocol.response.Pages;

public class CptInfosTest {
    @Test
    public void testConstructor() {
        CptInfos actualCptInfos = new CptInfos();
        Pages pages = new Pages();
        pages.setPage(1);
        pages.setTotalPage(1);
        pages.setTotalNum(10);
        pages.setResult(new ArrayList<CptInfo>());
        pages.setSize(3);
        actualCptInfos.setCptInfoPages(pages);
        assertSame(pages, actualCptInfos.getCptInfoPages());
    }
}

