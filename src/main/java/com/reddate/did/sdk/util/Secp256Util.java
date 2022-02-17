// Copyright 2021 Red Date Technology Ltd.  Licensed under MPLv2
// (https://www.mozilla.org/en-US/MPL/2.0/)
package com.reddate.did.sdk.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.crypto.ECDSASign;
import org.fisco.bcos.web3j.crypto.ECKeyPair;
import org.fisco.bcos.web3j.crypto.Hash;
import org.fisco.bcos.web3j.crypto.Keys;
import org.fisco.bcos.web3j.crypto.Sign;
import org.fisco.bcos.web3j.crypto.gm.GenCredential;
import org.fisco.bcos.web3j.crypto.gm.sm2.SM2Sign;
import org.fisco.bcos.web3j.crypto.gm.sm2.crypto.asymmetric.SM2Algorithm;
import org.fisco.bcos.web3j.crypto.gm.sm3.SM3Digest;
import org.fisco.bcos.web3j.crypto.tool.ECCDecrypt;
import org.fisco.bcos.web3j.crypto.tool.ECCEncrypt;
import org.fisco.bcos.web3j.utils.Numeric;

import com.google.common.base.Charsets;
import com.reddate.did.sdk.param.CryptoType;
import com.reddate.did.sdk.param.KeyPair;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.Security;
import java.security.SignatureException;

/**
 * Secp256 algorithm Utils class,
 * 
 * all Secp256 algorithm relate tolls method implement in this class
 */
public class Secp256Util {

	public static final String TYPE = "Secp256k1";

	static {
		Security.addProvider(new BouncyCastleProvider());
	}

	/**
	 * Generate a key pair
	 * 
	 * @param cryptoType
	 * @return
	 * @throws Exception
	 */
	public static KeyPair createKeyPair(CryptoType cryptoType) throws Exception {
		KeyPair keyPair = new KeyPair();
		ECKeyPair ecKeyPair = null;
		if (cryptoType == CryptoType.SM) {
			ecKeyPair = GenCredential.createGuomiKeyPair();
			keyPair.setType(CryptoType.SM);
		} else {
			ecKeyPair = Keys.createEcKeyPair();
			keyPair.setType(CryptoType.ECDSA);
		}
		keyPair.setPrivateKey(ecKeyPair.getPrivateKey().toString());
		keyPair.setPublicKey(ecKeyPair.getPublicKey().toString());
		return keyPair;
	}

	/**
	 * Get the public key form private key
	 * 
	 * @param cryptoType
	 * @param privateKey
	 * @return
	 */
	public static String getPublicKey(CryptoType cryptoType, String privateKey) {
		ECKeyPair ecKeyPair = null;
		String hexPrivateKey = new BigInteger(privateKey).toString(16);
		try {
			if (cryptoType == CryptoType.SM) {
				ecKeyPair = GenCredential.createGuomiKeyPair(hexPrivateKey);
			} else {
				ecKeyPair = GenCredential.createECDSAKeyPair(hexPrivateKey);
			}
		} catch (Exception e) {
			throw new RuntimeException("invalid privateKey");
		}
		return ecKeyPair.getPublicKey().toString();
	}

	/**
	 * Get the address form private key
	 * 
	 * @param cryptoType
	 * @param privateKey
	 * @return
	 * @throws Exception
	 */
	public static String getAddress(CryptoType cryptoType, String privateKey) throws Exception {
		ECKeyPair ecKeyPair = null;

		String hexPrivateKey = new BigInteger(privateKey).toString(16);
		if (cryptoType == CryptoType.SM) {
			ecKeyPair = GenCredential.createGuomiKeyPair(hexPrivateKey);
		} else {
			ecKeyPair = GenCredential.createECDSAKeyPair(hexPrivateKey);
		}
		Credentials credentials = GenCredential.create(ecKeyPair);
		return credentials.getAddress();
	}

	/**
	 * check a private key is a format correct private key or not
	 * 
	 * @param cryptoType
	 * @param privateKey
	 * @return
	 */
	public static Boolean isValidedPrivateKey(CryptoType cryptoType, String privateKey) {
		return (StringUtils.isNotEmpty(privateKey) && NumberUtils.isDigits(privateKey)
				&& new BigInteger(privateKey).compareTo(BigInteger.ZERO) > 0);
	}

	/**
	 * check a public key is a format correct public key or not
	 * 
	 * @param cryptoType
	 * @param publicKey
	 * @return
	 */
	public static Boolean isValidedPublickKey(CryptoType cryptoType, String publicKey) {
		return (StringUtils.isNotEmpty(publicKey) && StringUtils.isAlphanumeric(publicKey)
				&& NumberUtils.isDigits(publicKey));
	}

	/**
	 * Encrypt String data by public key
	 * 
	 * @param cryptoType
	 * @param data
	 * @param publicKey
	 * @return
	 * @throws Exception
	 */
	public static String encrypt(CryptoType cryptoType, String data, String publicKey) throws Exception {
		if (cryptoType == CryptoType.SM) {
			byte[] pub = Numeric.toBytesPadded(new BigInteger(publicKey), 64);
			String hexPbkX = org.bouncycastle.util.encoders.Hex.toHexString(pub, 0, 32);
			String hexPbkY = org.bouncycastle.util.encoders.Hex.toHexString(pub, 32, 32);
			byte[] tt = SM2Algorithm.encrypt(hexPbkX, hexPbkY, data.getBytes(Charsets.UTF_8));
			return Numeric.toHexString(tt).substring(2);
		} else {
			ECCEncrypt eccEncrypt = new ECCEncrypt(new BigInteger(publicKey));
			byte[] encBytes = eccEncrypt.encrypt(data.getBytes(StandardCharsets.UTF_8));
			return Numeric.toHexString(encBytes).substring(2);
		}
	}

	/**
	 * Decrypt String data by private key
	 * 
	 * @param cryptoType
	 * @param data
	 * @param privateKey
	 * @return
	 * @throws Exception
	 */
	public static String decrypt(CryptoType cryptoType, String data, String privateKey) throws Exception {
		if (cryptoType == CryptoType.SM) {
			String pvk = new BigInteger(privateKey).toString(16);
			byte[] tt2 = SM2Algorithm.decrypt(pvk, Numeric.hexStringToByteArray(data));
			return new String(tt2, Charsets.UTF_8);
		} else {
			ECCDecrypt eccDecrypt = new ECCDecrypt(new BigInteger(privateKey));
			byte[] decryptTytes = eccDecrypt.decrypt(Numeric.hexStringToByteArray(data));
			return new String(decryptTytes);
		}
	}

	/**
	 * Get the sign for String data and private key
	 * 
	 * @param cryptoType
	 * @param message
	 * @param privateKey
	 * @return
	 * @throws IOException
	 */
	public static String sign(CryptoType cryptoType, String message, String privateKey) throws IOException {
		if (cryptoType == CryptoType.SM) {
			Sign.SignatureData signatureData = SM2Sign.sign(message.getBytes(StandardCharsets.UTF_8),
					GenCredential.createGuomiKeyPair(new BigInteger(privateKey).toString(16)));
			return secp256k1SigBase64Serialization(signatureData);
		} else {
			ECDSASign ecdsaSign = new ECDSASign();
			Sign.SignatureData signatureData = ecdsaSign.signMessage(message.getBytes(StandardCharsets.UTF_8),
					GenCredential.createECDSAKeyPair(new BigInteger(privateKey).toString(16)));
			return secp256k1SigBase64Serialization(signatureData);
		}
	}

	/**
	 * Vefiry the sign by String message and public and sign value
	 * 
	 * @param cryptoType
	 * @param message
	 * @param publicKey
	 * @param signValue
	 * @return
	 * @throws SignatureException
	 */
	public static boolean verify(CryptoType cryptoType, String message, String publicKey, String signValue)
			throws SignatureException {
		boolean verify = false;
		if (cryptoType == CryptoType.SM) {
			SM3Digest sm3Digest = new SM3Digest();
			byte[] messageHash = sm3Digest.hash(message.getBytes(StandardCharsets.UTF_8));
			verify = SM2Sign.verify(messageHash, secp256k1SigBase64Deserialization(signValue, publicKey));
		} else {
			ECDSASign ecdsaSign = new ECDSASign();
			Sign.SignatureData signatureData = secp256k1SigBase64Deserialization(signValue, null);
			byte[] messageHash = Hash.sha3(message.getBytes(StandardCharsets.UTF_8));
			verify = ecdsaSign.verify(messageHash, new BigInteger(publicKey), signatureData);
		}
		return verify;
	}

	/**
	 * Serialization SignatureData to String data
	 * 
	 * @param sigData
	 * @return
	 */
	private static String secp256k1SigBase64Serialization(Sign.SignatureData sigData) {
		byte[] sigBytes = new byte[65];
		sigBytes[64] = sigData.getV();
		System.arraycopy(sigData.getR(), 0, sigBytes, 0, 32);
		System.arraycopy(sigData.getS(), 0, sigBytes, 32, 32);
		return Numeric.toHexString(sigBytes).substring(2);
	}

	/**
	 * Deserialization String signature data to SignatureData
	 * 
	 * @param signature
	 * @param publicKey
	 * @return
	 */
	private static Sign.SignatureData secp256k1SigBase64Deserialization(String signature, String publicKey) {
		byte[] sigBytes = Numeric.hexStringToByteArray("0x" + signature);
		byte[] r = new byte[32];
		byte[] s = new byte[32];
		System.arraycopy(sigBytes, 0, r, 0, 32);
		System.arraycopy(sigBytes, 32, s, 0, 32);
		if (publicKey == null) {
			return new Sign.SignatureData(sigBytes[64], r, s);
		} else {
			byte[] pub = Numeric.toBytesPadded(new BigInteger(publicKey), 64);
			return new Sign.SignatureData(sigBytes[64], r, s, pub);
		}
	}
}
