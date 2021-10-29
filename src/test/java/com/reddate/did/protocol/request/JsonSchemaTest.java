package com.reddate.did.protocol.request;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.reddate.did.sdk.protocol.request.JsonSchema;

public class JsonSchemaTest {
    @Test
    public void testConstructor() {
        JsonSchema actualJsonSchema = new JsonSchema();
        actualJsonSchema.setDescription("The characteristics of someone or something");
        actualJsonSchema.setRequired(true);
        actualJsonSchema.setType("Type");
        assertEquals("The characteristics of someone or something", actualJsonSchema.getDescription());
        assertTrue(actualJsonSchema.getRequired());
        assertEquals("Type", actualJsonSchema.getType());
    }
}

