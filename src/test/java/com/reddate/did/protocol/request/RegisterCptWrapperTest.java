package com.reddate.did.protocol.request;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.util.HashMap;

import org.junit.Test;

import com.reddate.did.sdk.protocol.request.JsonSchema;
import com.reddate.did.sdk.protocol.request.Proof;
import com.reddate.did.sdk.protocol.request.RegisterCptWrapper;

public class RegisterCptWrapperTest {
    @Test
    public void testConstructor() {
        RegisterCptWrapper actualRegisterCptWrapper = new RegisterCptWrapper();
        actualRegisterCptWrapper.setCptId(123L);
        HashMap<String, JsonSchema> stringJsonSchemaMap = new HashMap<String, JsonSchema>(1);
        actualRegisterCptWrapper.setCptJsonSchema(stringJsonSchemaMap);
        actualRegisterCptWrapper.setCreate("Create");
        actualRegisterCptWrapper.setDescription("The characteristics of someone or something");
        actualRegisterCptWrapper.setDid("Did");
        Proof proof = new Proof();
        proof.setCreator("Creator");
        proof.setSignatureValue("42");
        proof.setType("Type");
        actualRegisterCptWrapper.setProof(proof);
        actualRegisterCptWrapper.setTitle("Dr");
        actualRegisterCptWrapper.setType("Type");
        actualRegisterCptWrapper.setUpdate("2020-03-01");
        assertEquals(123L, actualRegisterCptWrapper.getCptId().longValue());
        assertSame(stringJsonSchemaMap, actualRegisterCptWrapper.getCptJsonSchema());
        assertEquals("Create", actualRegisterCptWrapper.getCreate());
        assertEquals("The characteristics of someone or something", actualRegisterCptWrapper.getDescription());
        assertEquals("Did", actualRegisterCptWrapper.getDid());
        assertSame(proof, actualRegisterCptWrapper.getProof());
        assertEquals("Dr", actualRegisterCptWrapper.getTitle());
        assertEquals("Type", actualRegisterCptWrapper.getType());
        assertEquals("2020-03-01", actualRegisterCptWrapper.getUpdate());
    }
}

