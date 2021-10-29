package com.reddate.did.sdk.util;


import org.fisco.bcos.web3j.crypto.*;
import org.fisco.bcos.web3j.crypto.Sign.SignatureData;
import org.fisco.bcos.web3j.crypto.gm.GenCredential;
import org.fisco.bcos.web3j.crypto.gm.sm2.SM2Sign;
import org.fisco.bcos.web3j.crypto.gm.sm3.SM3Digest;
import org.fisco.bcos.web3j.utils.Numeric;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;


/**
 * 
 * ECDSAU utils class,
 * some common tools method implement in this class
 * 
 * 
 *
 */
public class ECDSAUtils {

    public final static String TYPE = "Secp256k1";
    public final static String SIGN_FLAG = "SHA256withECDSA";
    private static String data = "ecdsa security";

    /**
     * 
     * Generate public and private keys
     * 
     * 
     * @return
     * @throws Exception
     */
    public static com.reddate.did.sdk.protocol.common.KeyPair createKey() throws Exception{
    	com.reddate.did.sdk.protocol.common.KeyPair keyPair = new com.reddate.did.sdk.protocol.common.KeyPair();
        ECKeyPair keyPairOriginal = Keys.createEcKeyPair();
        keyPair.setPublicKey(keyPairOriginal.getPublicKey().toString());
        keyPair.setPrivateKey(keyPairOriginal.getPrivateKey().toString());
        keyPair.setType(ECDSAUtils.TYPE);
        return keyPair;
    }

    /**
     * 
     * get public key from private key
     * 
     * @param privateKey
     * @return
     */
    public static String getPublicKey(String privateKey) {
        try {
            ECKeyPair keyPair = ECKeyPair.create(new BigInteger(privateKey));
            return String.valueOf(keyPair.getPublicKey());
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("invalid privateKey");
        }
    }
    
    /**
     * Sign the message according to the private key
     * @param message
     * @param privateKey
     */
    public static String sign(String message, String privateKey) throws Exception {
    	SignatureData sigData = secp256k1SignToSignature(message, new BigInteger(privateKey));
        return secp256k1SigBase64Serialization(sigData);
    }
    
    public static SignatureData secp256k1SignToSignature(String rawData, BigInteger privateKey) {
        ECKeyPair keyPair = GenCredential.createKeyPair(privateKey.toString(16));
        return secp256k1SignToSignature(rawData, keyPair);
    }
    
    public static SignatureData secp256k1SignToSignature(String rawData, ECKeyPair keyPair) {
    	 ECDSASign ecdsaSign = new ECDSASign();
         return ecdsaSign.secp256SignMessage(rawData.getBytes(StandardCharsets.UTF_8), keyPair);
    }
    
    public static String secp256k1SigBase64Serialization(
            SignatureData sigData) {
            byte[] sigBytes = new byte[65];
            sigBytes[64] = sigData.getV();
            System.arraycopy(sigData.getR(), 0, sigBytes, 0, 32);
            System.arraycopy(sigData.getS(), 0, sigBytes, 32, 32);
            return new String(base64Encode(sigBytes), StandardCharsets.UTF_8);
        }
    
    /**
     * Verify the signature of the message according to the public key
     * @param message
     * @param publicKey
     * @param signValue
     * @author shaopengfei
     */
    public static boolean verify(String message, String publicKey, String signValue) throws Exception {
    	SignatureData sigData =
                secp256k1SigBase64Deserialization(signValue);
        byte[] hashBytes = Hash.sha3(message.getBytes(StandardCharsets.UTF_8));
        ECDSASign ecdsaSign = new ECDSASign();
        return ecdsaSign.secp256Verify(hashBytes, new BigInteger(publicKey), sigData);
    	
    }

    public static SignatureData secp256k1SigBase64Deserialization(String signature) {
        byte[] sigBytes = base64Decode(signature.getBytes(StandardCharsets.UTF_8));
        byte[] r = new byte[32];
        byte[] s = new byte[32];
        System.arraycopy(sigBytes, 0, r, 0, 32);
        System.arraycopy(sigBytes, 32, s, 0, 32);
        return new SignatureData(sigBytes[64], r, s);
    }
    
    
    private static String secp256k1Sign(String rawData, BigInteger privateKey) {
        Sign.SignatureData sigData = secp256k1SignToSignature(rawData, privateKey);
        return secp256k1SigBase64Serialization(sigData);
    }
    
    private static boolean verifySecp256k1Signature(
            String rawData,
            String signatureBase64,
            BigInteger publicKey
    ) {
        if (rawData == null) {
            return false;
        }
            SM3Digest sm3Digest = new SM3Digest();
            byte[] hashBytes = sm3Digest.hash(rawData.getBytes(StandardCharsets.UTF_8));
            Sign.SignatureData sigData =
                    secp256k1SigBase64Deserialization(signatureBase64, publicKey);
            return SM2Sign.verify(hashBytes, sigData);
    }

    private static Sign.SignatureData secp256k1SigBase64Deserialization(
            String signature,
            BigInteger publicKey) {
        byte[] sigBytes = base64Decode(signature.getBytes(StandardCharsets.UTF_8));
        byte[] r = new byte[32];
        byte[] s = new byte[32];
        System.arraycopy(sigBytes, 0, r, 0, 32);
        System.arraycopy(sigBytes, 32, s, 0, 32);

        return new Sign.SignatureData(sigBytes[64], r, s, Numeric.toBytesPadded(publicKey, 64));
    }

    private static byte[] base64Encode(byte[] nonBase64Bytes) {
        return org.bouncycastle.util.encoders.Base64.encode(nonBase64Bytes);
    }

    private static byte[] base64Decode(byte[] base64Bytes) {
        return org.bouncycastle.util.encoders.Base64.decode(base64Bytes);
    }

}
