package com.reddate.did.param.req;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.util.HashMap;

import org.junit.Test;

import com.reddate.did.sdk.param.req.RegisterCpt;
import com.reddate.did.sdk.protocol.request.JsonSchema;

public class RegisterCptTest {
    @Test
    public void testConstructor() {
        RegisterCpt actualRegisterCpt = new RegisterCpt();
        actualRegisterCpt.setCptId(123L);
        HashMap<String, JsonSchema> stringJsonSchemaMap = new HashMap<String, JsonSchema>(1);
        actualRegisterCpt.setCptJsonSchema(stringJsonSchemaMap);
        actualRegisterCpt.setDescription("The characteristics of someone or something");
        actualRegisterCpt.setDid("Did");
        actualRegisterCpt.setPrivateKey("Private Key");
        actualRegisterCpt.setTitle("Dr");
        actualRegisterCpt.setType("Type");
        assertEquals(123L, actualRegisterCpt.getCptId().longValue());
        assertSame(stringJsonSchemaMap, actualRegisterCpt.getCptJsonSchema());
        assertEquals("The characteristics of someone or something", actualRegisterCpt.getDescription());
        assertEquals("Did", actualRegisterCpt.getDid());
        assertEquals("Private Key", actualRegisterCpt.getPrivateKey());
        assertEquals("Dr", actualRegisterCpt.getTitle());
        assertEquals("Type", actualRegisterCpt.getType());
    }
}

