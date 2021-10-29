package com.reddate.did.protocol.response;

import static org.junit.Assert.assertSame;

import java.util.ArrayList;

import org.junit.Test;

import com.reddate.did.sdk.protocol.response.BaseCredential;
import com.reddate.did.sdk.protocol.response.CredentialData;
import com.reddate.did.sdk.protocol.response.Pages;

public class CredentialDataTest {
    @Test
    public void testConstructor() {
        CredentialData actualCredentialData = new CredentialData();
        Pages pages = new Pages();
        pages.setPage(1);
        pages.setTotalPage(1);
        pages.setTotalNum(10);
        pages.setResult(new ArrayList<BaseCredential>());
        pages.setSize(3);
        actualCredentialData.setCredentialPages(pages);
        assertSame(pages, actualCredentialData.getCredentialPages());
    }
}

