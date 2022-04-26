package com.reddate.did;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.alibaba.fastjson.JSONObject;
import com.reddate.did.sdk.IpfsClient;
import com.reddate.did.sdk.param.CryptoType;
import com.reddate.did.sdk.param.KeyPair;
import com.reddate.did.sdk.param.ipfs.ResourceInfo;
import com.reddate.did.sdk.param.ipfs.SaveResource;
import com.reddate.did.sdk.util.Secp256Util;

public class IpfsClientTest {

	private static final String UP_KEY = "11111111111";
	
	private static final String DN_KEY = "222222222";
	
	@Test
	public void saveResourceTest() {
		IpfsClient client = new IpfsClient();
		String content = "this is a text content";
		SaveResource uploadResource = client.saveResource(UP_KEY, content, false, null,
				null);
		System.out.println(JSONObject.toJSONString(uploadResource));
		assertNotNull(uploadResource.getHash());
	}

	@Test
	public void saveResourceTest2() {
		IpfsClient client = new IpfsClient();
		String content = "this is a text content";
		KeyPair keyPair = Secp256Util.createKeyPair(CryptoType.ECDSA);
		SaveResource uploadResource = client.saveResource(UP_KEY, content, true,
				CryptoType.ECDSA, keyPair.getPrivateKey());
		System.out.println(JSONObject.toJSONString(uploadResource));
		assertNotNull(uploadResource.getHash());
	}

	@Test
	public void saveResourceTest3() {
		IpfsClient client = new IpfsClient();
		String content = "this is a text content";
		KeyPair keyPair = Secp256Util.createKeyPair(CryptoType.SM);
		SaveResource uploadResource = client.saveResource(UP_KEY, content, true,
				CryptoType.SM, keyPair.getPrivateKey());
		System.out.println(JSONObject.toJSONString(uploadResource));
		assertNotNull(uploadResource.getHash());
	}

	@Test
	public void getResourceTest() {
		IpfsClient client = new IpfsClient();
		String content = "this is a text content";
		SaveResource uploadResource = client.saveResource(UP_KEY, content, false, null,
				null);
		ResourceInfo resourceInfo = client.getResource(DN_KEY, uploadResource.getHash());
		System.out.println(JSONObject.toJSONString(resourceInfo));
		
		assertNotNull(resourceInfo.getContent());
	}
	
	@Test
	public void getResourceTest2() {
		IpfsClient client = new IpfsClient();
		String content = "this is a text content";
		KeyPair keyPair = Secp256Util.createKeyPair(CryptoType.ECDSA);
		SaveResource uploadResource = client.saveResource(UP_KEY, content, true,
				CryptoType.ECDSA, keyPair.getPrivateKey());		
		ResourceInfo resourceInfo = client.getResource(DN_KEY, uploadResource.getHash());
		System.out.println(JSONObject.toJSONString(resourceInfo));
		
		assertNotNull(resourceInfo.getContent());
	}
	
	@Test
	public void decryptTest() {
		IpfsClient client = new IpfsClient();
		// String content = "this is a text content";
		String plainContnet = client.decrypt(CryptoType.ECDSA, "1C2ADC66786611C68B1571E93646D8C2917DDBB9F1C4525C77D2FACD45E17B13", 
				"04e8840823c7d8052f5cc33ad0e6e1af05f738319b97ab1a6897fad70be5db4d858d010fbfb712d4662d99aa066f8149f80ed08437205b33b352f93adac069f4bc8468550abe6b827b9c969b32e3c8d040b0686608d16833ea2be604d1bc1bc3cc37c988f7d93de3e81f6a8428dbcae92cfe805c00", 
				"64888393446401789465443448557933038855114273908295731896328373621049092730670");
		
		assertNotNull(plainContnet);
		assertEquals("this is a text content", plainContnet);
	}
	
}
