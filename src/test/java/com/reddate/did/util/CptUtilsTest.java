package com.reddate.did.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;

import org.junit.Test;

import com.reddate.did.sdk.protocol.request.CptInfo;
import com.reddate.did.sdk.protocol.request.JsonSchema;
import com.reddate.did.sdk.protocol.request.Proof;
import com.reddate.did.sdk.protocol.request.RegisterCptWrapper;
import com.reddate.did.sdk.util.CptUtils;

public class CptUtilsTest {
    @Test
    public void testAssemblyCptInfo() {
        Proof proof = new Proof();
        proof.setCreator("Creator");
        proof.setSignatureValue("42");
        proof.setType("Type");

        RegisterCptWrapper registerCptWrapper = new RegisterCptWrapper();
        registerCptWrapper.setCreate("Create");
        registerCptWrapper.setProof(proof);
        registerCptWrapper.setTitle("Dr");
        registerCptWrapper.setDescription("The characteristics of someone or something");
        registerCptWrapper.setUpdate("2020-03-01");
        registerCptWrapper.setCptId(123L);
        registerCptWrapper.setType("Type");
        registerCptWrapper.setDid("Did");
        registerCptWrapper.setCptJsonSchema(new HashMap<String, JsonSchema>(1));
        CptInfo actualAssemblyCptInfoResult = CptUtils.assemblyCptInfo(registerCptWrapper);
        assertEquals(123L, actualAssemblyCptInfoResult.getCptId().longValue());
        assertEquals("2020-03-01", actualAssemblyCptInfoResult.getUpdate());
        assertEquals("Dr", actualAssemblyCptInfoResult.getTitle());
        assertEquals("Did", actualAssemblyCptInfoResult.getPublisherDid());
        assertEquals("The characteristics of someone or something", actualAssemblyCptInfoResult.getDescription());
        assertEquals("Create", actualAssemblyCptInfoResult.getCreate());
        assertEquals(1, actualAssemblyCptInfoResult.getCptVersion().intValue());
        assertTrue(actualAssemblyCptInfoResult.getCptJsonSchema().isEmpty());
    }
}

