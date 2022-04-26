package com.reddate.did.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.Test;

import com.reddate.did.sdk.exception.DidException;
import com.reddate.did.sdk.param.CryptoType;
import com.reddate.did.sdk.param.req.QueryCpt;
import com.reddate.did.sdk.param.req.ResetDidAuth;
import com.reddate.did.sdk.param.req.ResetDidAuthKey;
import com.reddate.did.sdk.param.resp.DidDataWrapper;
import com.reddate.did.sdk.param.resp.DocumentInfo;
import com.reddate.did.sdk.protocol.common.DidDocument;
import com.reddate.did.sdk.protocol.common.KeyPair;
import com.reddate.did.sdk.protocol.common.Proof;
import com.reddate.did.sdk.protocol.common.PublicKey;
import com.reddate.did.sdk.protocol.request.QueryCredentialWrapper;
import com.reddate.did.sdk.protocol.response.ResultData;
import com.reddate.did.sdk.service.DidService;

public class DidServiceTest {
	@Test
	public void testConstructor() {
		DidService actualDidService = new DidService("https://example.org/example", "ABC123", "myproject",
				CryptoType.ECDSA);

		assertEquals("myproject", actualDidService.getProjectId());
		assertEquals("https://example.org/example", actualDidService.getUrl());
		assertEquals("ABC123", actualDidService.getToken());
	}

	@Test
	public void testGenerateDid() {
		assertThrows(RuntimeException.class,
				() -> (new DidService("https://example.org/example", "ABC123", "myproject", CryptoType.ECDSA))
						.generateDid(true));
		assertThrows(RuntimeException.class,
				() -> (new DidService("UUU/UUU", "ABC123", "myproject", CryptoType.ECDSA)).generateDid(true));
		assertThrows(RuntimeException.class,
				() -> (new DidService("https://w3id.org/did/v1", "ABC123", "myproject", CryptoType.ECDSA))
						.generateDid(true));
	}

	@Test
	public void testGenerateDid2() {
		ResultData<DidDataWrapper> actualGenerateDidResult = (new DidService("https://example.org/example", "ABC123",
				"myproject", CryptoType.ECDSA)).generateDid(false);
		assertEquals(200, actualGenerateDidResult.getCode().intValue());
		assertEquals("success", actualGenerateDidResult.getMsg());
		DidDataWrapper data = actualGenerateDidResult.getData();
		DocumentInfo document = data.getDocument();
		assertEquals("1", document.getVersion());
		assertEquals("Secp256k1", data.getAuthKeyInfo().getType());
		assertEquals("Secp256k1", data.getRecyKeyInfo().getType());
		assertEquals("Secp256k1", document.getProof().getType());
		assertEquals("Secp256k1", document.getAuthentication().getType());
		assertEquals("Secp256k1", document.getRecovery().getType());
	}

	@Test
	public void testStoreDidDocumentOnChain() {
		DidService didService = new DidService("https://example.org/example", "ABC123", "myproject", CryptoType.ECDSA);

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
		assertThrows(RuntimeException.class, () -> didService.storeDidDocumentOnChain(didDocument));
	}

	@Test
	public void testStoreDidDocumentOnChain2() {
		DidService didService = new DidService("\"", "ABC123", "myproject", CryptoType.ECDSA);

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
		assertThrows(RuntimeException.class, () -> didService.storeDidDocumentOnChain(didDocument));
	}

	@Test
	public void testVerifyDidDocument() {
		DidService didService = new DidService("https://example.org/example", "ABC123", "myproject", CryptoType.ECDSA);

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
		assertThrows(RuntimeException.class, () -> didService.verifyDidDocument(didDocument));
	}

	@Test
	public void testVerifyDidDocument2() {
		DidService didService = new DidService("\"", "ABC123", "myproject", CryptoType.ECDSA);

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
		assertThrows(RuntimeException.class, () -> didService.verifyDidDocument(didDocument));
	}

	@Test
	public void testGetDidDocument() {
		assertThrows(RuntimeException.class,
				() -> (new DidService("https://example.org/example", "ABC123", "myproject", CryptoType.ECDSA))
						.getDidDocument("Did"));
		assertThrows(RuntimeException.class,
				() -> (new DidService("\"", "ABC123", "myproject", CryptoType.ECDSA)).getDidDocument("Did"));
	}

	@Test
	public void testResetDidAuth() throws Exception {
		DidService didService = new DidService("https://example.org/example", "ABC123", "myproject", CryptoType.ECDSA);

		ResetDidAuthKey resetDidAuthKey = new ResetDidAuthKey();
		resetDidAuthKey.setPublicKey("Public Key");
		resetDidAuthKey.setPrivateKey("Private Key");
		resetDidAuthKey.setType("Type");

		KeyPair keyPair = new KeyPair();
		keyPair.setPublicKey("Public Key");
		keyPair.setPrivateKey("Private Key");
		keyPair.setType("Type");

		ResetDidAuth resetDidAuth = new ResetDidAuth();
		resetDidAuth.setRecoveryKey(resetDidAuthKey);
		resetDidAuth.setPrimaryKeyPair(keyPair);
		resetDidAuth.setDid("Did");
		assertThrows(DidException.class, () -> didService.resetDidAuth(resetDidAuth));
	}

	@Test
	public void testResetDidAuth2() throws Exception {
		DidService didService = new DidService("\"", "ABC123", "myproject", CryptoType.ECDSA);

		ResetDidAuthKey resetDidAuthKey = new ResetDidAuthKey();
		resetDidAuthKey.setPublicKey("Public Key");
		resetDidAuthKey.setPrivateKey("Private Key");
		resetDidAuthKey.setType("Type");

		KeyPair keyPair = new KeyPair();
		keyPair.setPublicKey("Public Key");
		keyPair.setPrivateKey("Private Key");
		keyPair.setType("Type");

		ResetDidAuth resetDidAuth = new ResetDidAuth();
		resetDidAuth.setRecoveryKey(resetDidAuthKey);
		resetDidAuth.setPrimaryKeyPair(keyPair);
		resetDidAuth.setDid("Did");
		assertThrows(DidException.class, () -> didService.resetDidAuth(resetDidAuth));
	}

	@Test
	public void testQueryCptListByDid() {
		DidService didService = new DidService("https://example.org/example", "ABC123", "myproject", CryptoType.ECDSA);

		QueryCpt queryCptList = new QueryCpt();
		queryCptList.setPage(1);
		queryCptList.setSize(3);
		queryCptList.setDid("Did");
//        assertThrows(RuntimeException.class, () -> didService.queryCptListByDid(queryCptList));
	}

	@Test
	public void testQueryCptListByDid2() {
		DidService didService = new DidService("\"", "ABC123", "myproject", CryptoType.ECDSA);

		QueryCpt queryCptList = new QueryCpt();
		queryCptList.setPage(1);
		queryCptList.setSize(3);
		queryCptList.setDid("Did");
//        assertThrows(RuntimeException.class, () -> didService.queryCptListByDid(queryCptList));
	}

	@Test
	public void testGetRevokedCredList() {
		DidService didService = new DidService("https://example.org/example", "ABC123", "myproject", CryptoType.ECDSA);

		QueryCredentialWrapper queryCredentialWrapper = new QueryCredentialWrapper();
		queryCredentialWrapper.setPage(1);
		queryCredentialWrapper.setSize(3);
		queryCredentialWrapper.setDid("Did");
//        assertThrows(RuntimeException.class, () -> didService.getRevokedCredList(queryCredentialWrapper));
	}

	@Test
	public void testGetRevokedCredList2() {
		DidService didService = new DidService("\"", "ABC123", "myproject", CryptoType.ECDSA);

		QueryCredentialWrapper queryCredentialWrapper = new QueryCredentialWrapper();
		queryCredentialWrapper.setPage(1);
		queryCredentialWrapper.setSize(3);
		queryCredentialWrapper.setDid("Did");
//        assertThrows(RuntimeException.class, () -> didService.getRevokedCredList(queryCredentialWrapper));
	}
}
