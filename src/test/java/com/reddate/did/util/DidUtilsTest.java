package com.reddate.did.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import org.junit.Test;

import com.reddate.did.sdk.protocol.common.BaseDidDocument;
import com.reddate.did.sdk.protocol.common.DidDocument;
import com.reddate.did.sdk.protocol.common.KeyPair;
import com.reddate.did.sdk.protocol.common.Proof;
import com.reddate.did.sdk.protocol.common.PublicKey;
import com.reddate.did.sdk.util.DidUtils;
import com.reddate.did.sdk.util.ECDSAUtils;

public class DidUtilsTest {
    @Test
    public void testGenerateBaseDidDocument() throws Exception {
        PublicKey publicKey = new PublicKey();
        publicKey.setPublicKey("Public Key");
        publicKey.setType("Type");

        Proof proof = new Proof();
        proof.setCreator("Creator");
        proof.setSignatureValue("42");
        proof.setType("Type");

        PublicKey publicKey1 = new PublicKey();
        publicKey1.setPublicKey("Public Key");
        publicKey1.setType("Type");

        DidDocument didDocument = new DidDocument();
        didDocument.setRecovery(publicKey);
        didDocument.setUpdated("2020-03-01");
        didDocument.setProof(proof);
        didDocument.setCreated("Jan 1, 2020 8:00am GMT+0100");
        didDocument.setVersion("1.0.2");
        didDocument.setAuthentication(publicKey1);
        didDocument.setDid("Did");
        BaseDidDocument actualGenerateBaseDidDocumentResult = DidUtils.generateBaseDidDocument(didDocument);
        assertSame(publicKey1, actualGenerateBaseDidDocumentResult.getAuthentication());
        assertSame(publicKey, actualGenerateBaseDidDocumentResult.getRecovery());
        assertEquals("https://w3id.org/did/v1", actualGenerateBaseDidDocumentResult.getContext());
    }

    @Test
    public void testGenerateBaseDidDocument2() throws Exception {
        KeyPair keyPair = new KeyPair();
        keyPair.setPublicKey("Public Key");
        keyPair.setPrivateKey("Private Key");
        keyPair.setType("Type");

        KeyPair keyPair1 = new KeyPair();
        keyPair1.setPublicKey("Public Key");
        keyPair1.setPrivateKey("Private Key");
        keyPair1.setType("Type");
        BaseDidDocument actualGenerateBaseDidDocumentResult = DidUtils.generateBaseDidDocument(keyPair, keyPair1);
        assertEquals("https://w3id.org/did/v1", actualGenerateBaseDidDocumentResult.getContext());
        PublicKey recovery = actualGenerateBaseDidDocumentResult.getRecovery();
        assertEquals(ECDSAUtils.TYPE, recovery.getType());
        assertEquals("Public Key", recovery.getPublicKey());
        PublicKey authentication = actualGenerateBaseDidDocumentResult.getAuthentication();
        assertEquals("Public Key", authentication.getPublicKey());
        assertEquals(ECDSAUtils.TYPE, authentication.getType());
    }

    @Test
    public void testGenerateDidIdentifierByBaseDidDocument() throws Exception {
        PublicKey publicKey = new PublicKey();
        publicKey.setPublicKey("Public Key");
        publicKey.setType("Type");

        PublicKey publicKey1 = new PublicKey();
        publicKey1.setPublicKey("Public Key");
        publicKey1.setType("Type");

        BaseDidDocument baseDidDocument = new BaseDidDocument();
        baseDidDocument.setContext("Context");
        baseDidDocument.setRecovery(publicKey);
        baseDidDocument.setAuthentication(publicKey1);
        assertEquals("2TWKU8nSzf7jK84uXySWcs8KzNYh", DidUtils.generateDidIdentifierByBaseDidDocument(baseDidDocument));
    }

    @Test
    public void testGenerateDidIdentifierByBaseDidDocument2() throws Exception {
        PublicKey publicKey = new PublicKey();
        publicKey.setPublicKey("Public Key");
        publicKey.setType("Type");

        PublicKey publicKey1 = new PublicKey();
        publicKey1.setPublicKey("Public Key");
        publicKey1.setType("Type");
        BaseDidDocument generateBaseDidDocumentResult = DidUtils.generateBaseDidDocument(new DidDocument());
        generateBaseDidDocumentResult.setContext("Context");
        generateBaseDidDocumentResult.setRecovery(publicKey);
        generateBaseDidDocumentResult.setAuthentication(publicKey1);
        assertEquals("2TWKU8nSzf7jK84uXySWcs8KzNYh",
                DidUtils.generateDidIdentifierByBaseDidDocument(generateBaseDidDocumentResult));
    }

    @Test
    public void testGenerateDidIdentifierByBaseDidDocument3() throws Exception {
        PublicKey publicKey = new PublicKey();
        publicKey.setPublicKey("SHA-256");
        publicKey.setType("");

        PublicKey publicKey1 = new PublicKey();
        publicKey1.setPublicKey("Public Key");
        publicKey1.setType("Type");

        BaseDidDocument baseDidDocument = new BaseDidDocument();
        baseDidDocument.setContext("Context");
        baseDidDocument.setRecovery(publicKey);
        baseDidDocument.setAuthentication(publicKey1);
        assertEquals("1Xy4XVd54EFm66ossxp8mDaox5c", DidUtils.generateDidIdentifierByBaseDidDocument(baseDidDocument));
    }

    @Test
    public void testGenerateDidByDidIdentifier() throws Exception {
        assertEquals("did:cbt:42", DidUtils.generateDidByDidIdentifier("42"));
        assertEquals("", DidUtils.generateDidByDidIdentifier(""));
    }

    @Test
    public void testGenerateDidDocument() throws Exception {
        KeyPair keyPair = new KeyPair();
        keyPair.setPublicKey("Public Key");
        keyPair.setPrivateKey("Private Key");
        keyPair.setType("Type");

        KeyPair keyPair1 = new KeyPair();
        keyPair1.setPublicKey("Public Key");
        keyPair1.setPrivateKey("Private Key");
        keyPair1.setType("Type");
        DidDocument actualGenerateDidDocumentResult = DidUtils.generateDidDocument(keyPair, keyPair1, "Did");
        assertEquals("1", actualGenerateDidDocumentResult.getVersion());
        assertEquals("Did", actualGenerateDidDocumentResult.getDid());
        PublicKey recovery = actualGenerateDidDocumentResult.getRecovery();
        assertEquals(ECDSAUtils.TYPE, recovery.getType());
        assertEquals("Public Key", recovery.getPublicKey());
        PublicKey authentication = actualGenerateDidDocumentResult.getAuthentication();
        assertEquals(ECDSAUtils.TYPE, authentication.getType());
        assertEquals("Public Key", authentication.getPublicKey());
    }

    @Test
    public void testRenewDidDocument() {
        PublicKey publicKey = new PublicKey();
        publicKey.setPublicKey("Public Key");
        publicKey.setType("Type");

        Proof proof = new Proof();
        proof.setCreator("Creator");
        proof.setSignatureValue("42");
        proof.setType("Type");

        PublicKey publicKey1 = new PublicKey();
        publicKey1.setPublicKey("Public Key");
        publicKey1.setType("Type");

        DidDocument didDocument = new DidDocument();
        didDocument.setRecovery(publicKey);
        didDocument.setUpdated("2020-03-01");
        didDocument.setProof(proof);
        didDocument.setCreated("Jan 1, 2020 8:00am GMT+0100");
        didDocument.setVersion("1.0.2");
        didDocument.setAuthentication(publicKey1);
        didDocument.setDid("Did");

        KeyPair keyPair = new KeyPair();
        keyPair.setPublicKey("Public Key");
        keyPair.setPrivateKey("Private Key");
        keyPair.setType("Type");
        DidDocument actualRenewDidDocumentResult = DidUtils.renewDidDocument(didDocument, keyPair);
        assertNull(actualRenewDidDocumentResult.getProof());
        PublicKey authentication = actualRenewDidDocumentResult.getAuthentication();
        assertEquals("Type", authentication.getType());
        assertEquals("Public Key", authentication.getPublicKey());
    }

    @Test
    public void testRenewDidDocument2() {
        PublicKey publicKey = new PublicKey();
        publicKey.setPublicKey("Public Key");
        publicKey.setType("Type");

        Proof proof = new Proof();
        proof.setCreator("Creator");
        proof.setSignatureValue("42");
        proof.setType("Type");

        PublicKey publicKey1 = new PublicKey();
        publicKey1.setPublicKey("Public Key");
        publicKey1.setType("Type");

        DidDocument didDocument = new DidDocument();
        didDocument.setRecovery(publicKey);
        didDocument.setUpdated("2020-03-01");
        didDocument.setProof(proof);
        didDocument.setCreated("Jan 1, 2020 8:00am GMT+0100");
        didDocument.setVersion("1.0.2");
        didDocument.setAuthentication(publicKey1);
        didDocument.setDid("Did");

        KeyPair keyPair = new KeyPair();
        keyPair.setPublicKey("Public Key");
        keyPair.setPrivateKey("Private Key");
        keyPair.setType(null);
        DidDocument actualRenewDidDocumentResult = DidUtils.renewDidDocument(didDocument, keyPair);
        assertNull(actualRenewDidDocumentResult.getProof());
        PublicKey authentication = actualRenewDidDocumentResult.getAuthentication();
        assertEquals(ECDSAUtils.TYPE, authentication.getType());
        assertEquals("Public Key", authentication.getPublicKey());
    }

    @Test
    public void testRenewDidDocument3() {
        PublicKey publicKey = new PublicKey();
        publicKey.setPublicKey("Public Key");
        publicKey.setType("Type");

        Proof proof = new Proof();
        proof.setCreator("Creator");
        proof.setSignatureValue("42");
        proof.setType("Type");

        PublicKey publicKey1 = new PublicKey();
        publicKey1.setPublicKey("Public Key");
        publicKey1.setType("Type");

        DidDocument didDocument = new DidDocument();
        didDocument.setRecovery(publicKey);
        didDocument.setUpdated("2020-03-01");
        didDocument.setProof(proof);
        didDocument.setCreated("Jan 1, 2020 8:00am GMT+0100");
        didDocument.setVersion("1.0.2");
        didDocument.setAuthentication(publicKey1);
        didDocument.setDid("Did");

        KeyPair keyPair = new KeyPair();
        keyPair.setPublicKey("Public Key");
        keyPair.setPrivateKey("Private Key");
        keyPair.setType("");
        DidDocument actualRenewDidDocumentResult = DidUtils.renewDidDocument(didDocument, keyPair);
        assertNull(actualRenewDidDocumentResult.getProof());
        PublicKey authentication = actualRenewDidDocumentResult.getAuthentication();
        assertEquals(ECDSAUtils.TYPE, authentication.getType());
        assertEquals("Public Key", authentication.getPublicKey());
    }
}

