package com.reddate.did.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.util.HashMap;

import org.junit.Test;

import com.reddate.did.sdk.exception.DidException;
import com.reddate.did.sdk.param.req.AuthIssuerList;
import com.reddate.did.sdk.param.req.QueryCptList;
import com.reddate.did.sdk.param.req.RegisterAuthorityIssuer;
import com.reddate.did.sdk.param.req.RegisterCpt;
import com.reddate.did.sdk.protocol.request.JsonSchema;
import com.reddate.did.sdk.protocol.request.QueryCptByIdWrapper;
import com.reddate.did.sdk.service.AuthIssuerService;

public class AuthIssuerServiceTest {
    @Test
    public void testConstructor() {
        AuthIssuerService actualAuthIssuerService = new AuthIssuerService("https://example.org/example", "ABC123",
                "myproject");

        assertEquals("myproject", actualAuthIssuerService.getProjectId());
        assertEquals("https://example.org/example", actualAuthIssuerService.getUrl());
        assertEquals("ABC123", actualAuthIssuerService.getToken());
    }

    @Test
    public void testRegisterAuthIssuer() {
        AuthIssuerService authIssuerService = new AuthIssuerService("https://example.org/example", "ABC123", "myproject");

        RegisterAuthorityIssuer registerAuthorityIssuer = new RegisterAuthorityIssuer();
        registerAuthorityIssuer.setName("Name");
        registerAuthorityIssuer.setPrivateKey("42");
        registerAuthorityIssuer.setDid("Did");
        assertThrows(RuntimeException.class, () -> authIssuerService.registerAuthIssuer(registerAuthorityIssuer));
    }

    @Test
    public void testRegisterAuthIssuer2() {
        AuthIssuerService authIssuerService = new AuthIssuerService("invalid privateKey", "ABC123", "myproject");

        RegisterAuthorityIssuer registerAuthorityIssuer = new RegisterAuthorityIssuer();
        registerAuthorityIssuer.setName("Name");
        registerAuthorityIssuer.setPrivateKey("42");
        registerAuthorityIssuer.setDid("Did");
        assertThrows(RuntimeException.class, () -> authIssuerService.registerAuthIssuer(registerAuthorityIssuer));
    }

    @Test
    public void testRegisterAuthIssuer3() {
        AuthIssuerService authIssuerService = new AuthIssuerService("https://example.org/example", "invalid privateKey",
                "myproject");

        RegisterAuthorityIssuer registerAuthorityIssuer = new RegisterAuthorityIssuer();
        registerAuthorityIssuer.setName("Name");
        registerAuthorityIssuer.setPrivateKey("42");
        registerAuthorityIssuer.setDid("Did");
        assertThrows(RuntimeException.class, () -> authIssuerService.registerAuthIssuer(registerAuthorityIssuer));
    }

    @Test
    public void testQueryAuthIssuerList() {
        AuthIssuerService authIssuerService = new AuthIssuerService("https://example.org/example", "ABC123", "myproject");

        AuthIssuerList authIssuerList = new AuthIssuerList();
        authIssuerList.setPage(1);
        authIssuerList.setSize(3);
        authIssuerList.setDid("Did");
        assertThrows(RuntimeException.class, () -> authIssuerService.queryAuthIssuerList(authIssuerList));
    }

    @Test
    public void testQueryAuthIssuerList2() {
        AuthIssuerService authIssuerService = new AuthIssuerService("UUU/UUU", "ABC123", "myproject");

        AuthIssuerList authIssuerList = new AuthIssuerList();
        authIssuerList.setPage(1);
        authIssuerList.setSize(3);
        authIssuerList.setDid("Did");
        assertThrows(RuntimeException.class, () -> authIssuerService.queryAuthIssuerList(authIssuerList));
    }

    @Test
    public void testQueryAuthIssuerList3() {
        AuthIssuerService authIssuerService = new AuthIssuerService("https://example.org/example", "UUU/UUU", "myproject");

        AuthIssuerList authIssuerList = new AuthIssuerList();
        authIssuerList.setPage(1);
        authIssuerList.setSize(3);
        authIssuerList.setDid("Did");
        assertThrows(RuntimeException.class, () -> authIssuerService.queryAuthIssuerList(authIssuerList));
    }

    @Test
    public void testRegisterCpt() {
        AuthIssuerService authIssuerService = new AuthIssuerService("https://example.org/example", "ABC123", "myproject");

        RegisterCpt registerCpt = new RegisterCpt();
        registerCpt.setTitle("Dr");
        registerCpt.setDescription("The characteristics of someone or something");
        registerCpt.setCptId(123L);
        registerCpt.setPrivateKey("Private Key");
        registerCpt.setType("Type");
        registerCpt.setDid("Did");
        registerCpt.setCptJsonSchema(new HashMap<String, JsonSchema>(1));
        assertThrows(DidException.class, () -> authIssuerService.registerCpt(registerCpt));
    }

    @Test
    public void testRegisterCpt2() {
        AuthIssuerService authIssuerService = new AuthIssuerService("https://example.org/example", "ABC123", "myproject");

        RegisterCpt registerCpt = new RegisterCpt();
        registerCpt.setTitle("Dr");
        registerCpt.setDescription("The characteristics of someone or something");
        registerCpt.setCptId(null);
        registerCpt.setPrivateKey("Private Key");
        registerCpt.setType("Type");
        registerCpt.setDid("Did");
        registerCpt.setCptJsonSchema(new HashMap<String, JsonSchema>(1));
        assertThrows(DidException.class, () -> authIssuerService.registerCpt(registerCpt));
    }

    @Test
    public void testRegisterCpt3() {
        AuthIssuerService authIssuerService = new AuthIssuerService("https://example.org/example", "ABC123", "myproject");

        RegisterCpt registerCpt = new RegisterCpt();
        registerCpt.setTitle("Dr");
        registerCpt.setDescription("The characteristics of someone or something");
        registerCpt.setCptId(123L);
        registerCpt.setPrivateKey("42");
        registerCpt.setType("Type");
        registerCpt.setDid("Did");
        registerCpt.setCptJsonSchema(new HashMap<String, JsonSchema>(1));
        assertThrows(RuntimeException.class, () -> authIssuerService.registerCpt(registerCpt));
    }

    @Test
    public void testRegisterCpt4() {
        AuthIssuerService authIssuerService = new AuthIssuerService("https://example.org/example", "ABC123", "myproject");

        JsonSchema jsonSchema = new JsonSchema();
        jsonSchema.setDescription("The characteristics of someone or something");
        jsonSchema.setType("yyyy-MM-dd HH:mm:ss");
        jsonSchema.setRequired(true);

        HashMap<String, JsonSchema> stringJsonSchemaMap = new HashMap<String, JsonSchema>(1);
        stringJsonSchemaMap.put("yyyy-MM-dd HH:mm:ss", jsonSchema);

        RegisterCpt registerCpt = new RegisterCpt();
        registerCpt.setTitle("Dr");
        registerCpt.setDescription("The characteristics of someone or something");
        registerCpt.setCptId(123L);
        registerCpt.setPrivateKey("Private Key");
        registerCpt.setType("Type");
        registerCpt.setDid("Did");
        registerCpt.setCptJsonSchema(stringJsonSchemaMap);
        assertThrows(DidException.class, () -> authIssuerService.registerCpt(registerCpt));
    }

    @Test
    public void testRegisterCpt5() {
        AuthIssuerService authIssuerService = new AuthIssuerService("https://example.org/example", "ABC123", "myproject");

        RegisterCpt registerCpt = new RegisterCpt();
        registerCpt.setTitle("Dr");
        registerCpt.setDescription("The characteristics of someone or something");
        registerCpt.setCptId(null);
        registerCpt.setPrivateKey("123456789");
        registerCpt.setType("Type");
        registerCpt.setDid("Did");
        registerCpt.setCptJsonSchema(new HashMap<String, JsonSchema>(1));
        assertThrows(RuntimeException.class, () -> authIssuerService.registerCpt(registerCpt));
    }

    @Test
    public void testRegisterCpt6() {
        AuthIssuerService authIssuerService = new AuthIssuerService("\"", "ABC123", "myproject");

        RegisterCpt registerCpt = new RegisterCpt();
        registerCpt.setTitle("Dr");
        registerCpt.setDescription("The characteristics of someone or something");
        registerCpt.setCptId(123L);
        registerCpt.setPrivateKey("42");
        registerCpt.setType("Type");
        registerCpt.setDid("Did");
        registerCpt.setCptJsonSchema(new HashMap<String, JsonSchema>(1));
        assertThrows(RuntimeException.class, () -> authIssuerService.registerCpt(registerCpt));
    }

    @Test
    public void testRegisterCpt7() {
        AuthIssuerService authIssuerService = new AuthIssuerService("https://example.org/example", "yyyy-MM-dd HH:mm:ss",
                "myproject");

        RegisterCpt registerCpt = new RegisterCpt();
        registerCpt.setTitle("Dr");
        registerCpt.setDescription("The characteristics of someone or something");
        registerCpt.setCptId(123L);
        registerCpt.setPrivateKey("42");
        registerCpt.setType("Type");
        registerCpt.setDid("Did");
        registerCpt.setCptJsonSchema(new HashMap<String, JsonSchema>(1));
        assertThrows(RuntimeException.class, () -> authIssuerService.registerCpt(registerCpt));
    }

    @Test
    public void testQueryCptListByDid() {
        AuthIssuerService authIssuerService = new AuthIssuerService("https://example.org/example", "ABC123", "myproject");

        QueryCptList queryCptList = new QueryCptList();
        queryCptList.setPage(1);
        queryCptList.setSize(3);
        queryCptList.setDid("Did");
        assertThrows(RuntimeException.class, () -> authIssuerService.queryCptListByDid(queryCptList));
    }

    @Test
    public void testQueryCptListByDid2() {
        AuthIssuerService authIssuerService = new AuthIssuerService("\"", "ABC123", "myproject");

        QueryCptList queryCptList = new QueryCptList();
        queryCptList.setPage(1);
        queryCptList.setSize(3);
        queryCptList.setDid("Did");
        assertThrows(RuntimeException.class, () -> authIssuerService.queryCptListByDid(queryCptList));
    }

    @Test
    public void testQueryCptById() {
        AuthIssuerService authIssuerService = new AuthIssuerService("https://example.org/example", "ABC123", "myproject");

        QueryCptByIdWrapper queryCptByIdWrapper = new QueryCptByIdWrapper();
        queryCptByIdWrapper.setCptId(123L);
//        assertThrows(RuntimeException.class, () -> authIssuerService.queryCptById(queryCptByIdWrapper));
    }

    @Test
    public void testQueryCptById2() {
        AuthIssuerService authIssuerService = new AuthIssuerService("\"", "ABC123", "myproject");

        QueryCptByIdWrapper queryCptByIdWrapper = new QueryCptByIdWrapper();
        queryCptByIdWrapper.setCptId(123L);
//        assertThrows(RuntimeException.class, () -> authIssuerService.queryCptById(queryCptByIdWrapper));
    }

    @Test
    public void testUpdateCpt() {
        AuthIssuerService authIssuerService = new AuthIssuerService("https://example.org/example", "ABC123", "myproject");

        RegisterCpt registerCpt = new RegisterCpt();
        registerCpt.setTitle("Dr");
        registerCpt.setDescription("The characteristics of someone or something");
        registerCpt.setCptId(123L);
        registerCpt.setPrivateKey("Private Key");
        registerCpt.setType("Type");
        registerCpt.setDid("Did");
        registerCpt.setCptJsonSchema(new HashMap<String, JsonSchema>(1));
        assertThrows(RuntimeException.class, () -> authIssuerService.updateCpt(registerCpt));
    }

    @Test
    public void testUpdateCpt2() {
        AuthIssuerService authIssuerService = new AuthIssuerService("https://example.org/example", "ABC123", "myproject");

        RegisterCpt registerCpt = new RegisterCpt();
        registerCpt.setTitle("Dr");
        registerCpt.setDescription("The characteristics of someone or something");
        registerCpt.setCptId(123L);
        registerCpt.setPrivateKey("42");
        registerCpt.setType("Type");
        registerCpt.setDid("Did");
        registerCpt.setCptJsonSchema(new HashMap<String, JsonSchema>(1));
        assertThrows(RuntimeException.class, () -> authIssuerService.updateCpt(registerCpt));
    }

    @Test
    public void testUpdateCpt3() {
        AuthIssuerService authIssuerService = new AuthIssuerService("https://example.org/example", "ABC123", "myproject");

        JsonSchema jsonSchema = new JsonSchema();
        jsonSchema.setDescription("The characteristics of someone or something");
        jsonSchema.setType("sign the cpt template data failed");
        jsonSchema.setRequired(true);

        HashMap<String, JsonSchema> stringJsonSchemaMap = new HashMap<String, JsonSchema>(1);
        stringJsonSchemaMap.put("sign the cpt template data failed", jsonSchema);

        RegisterCpt registerCpt = new RegisterCpt();
        registerCpt.setTitle("Dr");
        registerCpt.setDescription("The characteristics of someone or something");
        registerCpt.setCptId(123L);
        registerCpt.setPrivateKey("Private Key");
        registerCpt.setType("Type");
        registerCpt.setDid("Did");
        registerCpt.setCptJsonSchema(stringJsonSchemaMap);
        assertThrows(RuntimeException.class, () -> authIssuerService.updateCpt(registerCpt));
    }

    @Test
    public void testUpdateCpt4() {
        AuthIssuerService authIssuerService = new AuthIssuerService("\"", "ABC123", "myproject");

        RegisterCpt registerCpt = new RegisterCpt();
        registerCpt.setTitle("Dr");
        registerCpt.setDescription("The characteristics of someone or something");
        registerCpt.setCptId(123L);
        registerCpt.setPrivateKey("42");
        registerCpt.setType("Type");
        registerCpt.setDid("Did");
        registerCpt.setCptJsonSchema(new HashMap<String, JsonSchema>(1));
        assertThrows(RuntimeException.class, () -> authIssuerService.updateCpt(registerCpt));
    }
}

