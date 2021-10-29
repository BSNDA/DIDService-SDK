package com.reddate.did.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.HashMap;

import org.junit.Test;

import com.reddate.did.sdk.protocol.response.Pages;
import com.reddate.did.sdk.service.BaseService;

public class BaseServiceTest {
    @Test
    public void testConstructor() {
        BaseService actualBaseService = new BaseService("https://example.org/example", "ABC123", "myproject");

        assertEquals("myproject", actualBaseService.getProjectId());
        assertEquals("ABC123", actualBaseService.getToken());
        assertEquals("https://example.org/example", actualBaseService.getUrl());
    }

    @Test
    public void testParseToPage() {
        BaseService baseService = new BaseService("https://example.org/example", "ABC123", "myproject");
        HashMap<Object, Object> pageMap = new HashMap<Object, Object>(1);
        Pages<Object> actualParseToPageResult = baseService.<Object>parseToPage(pageMap, Object.class);
        assertNull(actualParseToPageResult.getPage());
        assertNull(actualParseToPageResult.getTotalPage());
        assertNull(actualParseToPageResult.getTotalNum());
        assertNull(actualParseToPageResult.getSize());
        assertNull(actualParseToPageResult.getResult());
    }
}

