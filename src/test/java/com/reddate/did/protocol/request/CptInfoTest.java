package com.reddate.did.protocol.request;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.util.HashMap;

import org.junit.Test;

import com.reddate.did.sdk.protocol.request.CptInfo;
import com.reddate.did.sdk.protocol.request.JsonSchema;
import com.reddate.did.sdk.protocol.request.Proof;

public class CptInfoTest {
    @Test
    public void testConstructor() {
        CptInfo actualCptInfo = new CptInfo();
        HashMap<String, JsonSchema> stringJsonSchemaMap = new HashMap<String, JsonSchema>(1);
        actualCptInfo.setCptJsonSchema(stringJsonSchemaMap);
        actualCptInfo.setCreate("Create");
        actualCptInfo.setDescription("The characteristics of someone or something");
        Proof proof = new Proof();
        proof.setCreator("Creator");
        proof.setSignatureValue("42");
        proof.setType("Type");
        actualCptInfo.setProof(proof);
        actualCptInfo.setPublisherDid("Publisher Did");
        actualCptInfo.setTitle("Dr");
        actualCptInfo.setUpdate("2020-03-01");
        assertSame(stringJsonSchemaMap, actualCptInfo.getCptJsonSchema());
        assertEquals("Create", actualCptInfo.getCreate());
        assertEquals("The characteristics of someone or something", actualCptInfo.getDescription());
        assertSame(proof, actualCptInfo.getProof());
        assertEquals("Publisher Did", actualCptInfo.getPublisherDid());
        assertEquals("Dr", actualCptInfo.getTitle());
        assertEquals("2020-03-01", actualCptInfo.getUpdate());
    }
}

