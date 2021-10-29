package com.reddate.did.sdk.util;

import java.security.MessageDigest;
import java.security.Security;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;

/**
 * RipeMD160 help class
 */
public class RipeMDUtils {

	/**
	 * 
	 * encode type array by RipeMd128
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
    public static byte[] encodeRipeMd128(byte[] data) throws Exception{
        Security.addProvider(new BouncyCastleProvider());
        MessageDigest md=MessageDigest.getInstance("RipeMD128");
        return md.digest(data);
    }

    /**
     * 
     * encode type array by RipeMd128 to hex String
     * 
     * 
     * @param data
     * @return
     * @throws Exception
     */
    public static String encodeRipeMd128Hex(byte[] data) throws Exception{
        byte[] b= encodeRipeMd128(data);
        return new String(Hex.encode(b));
    }

    /**
     * 
     * encode type array by RipeMd160
     * 
     * @param data
     * @return
     * @throws Exception
     */
    public static byte[] encodeRipeMd160(byte[] data) throws Exception{
        Security.addProvider(new BouncyCastleProvider());
        MessageDigest md=MessageDigest.getInstance("RipeMD160");
        return md.digest(data);
    }
    
    /**
     * 
     * encode type array by RipeMd160 to hex String
     * 
     * @param data
     * @return
     * @throws Exception
     */
    public static String encodeRipeMd160Hex(byte[] data) throws Exception{
        byte[] b= encodeRipeMd160(data);
        return new String(Hex.encode(b));
    }

    /**
     * 
     * encode type array by RipeMd256
     * 
     * @param data
     * @return
     * @throws Exception
     */
    public static byte[] encodeRipeMd256(byte[] data) throws Exception{
        Security.addProvider(new BouncyCastleProvider());
        MessageDigest md=MessageDigest.getInstance("RipeMD256");
        return md.digest(data);
    }

    /**
     * 
     * encode type array by RipeMd256 to hex String
     * 
     * @param data
     * @return
     * @throws Exception
     */
    public static String encodeRipeMd256Hex(byte[] data) throws Exception{
        byte[] b= encodeRipeMd256(data);
        return new String(Hex.encode(b));
    }

    /**
     * 
     * encode type array by RipeMd320
     * 
     * 
     * @param data
     * @return
     * @throws Exception
     */
    public static byte[] encodeRipeMd320(byte[] data) throws Exception{
        Security.addProvider(new BouncyCastleProvider());
        MessageDigest md=MessageDigest.getInstance("RipeMD320");
        return md.digest(data);
    }

    /**
     * 
     * encode type array by RipeMd320 to hex String
     * 
     * @param data
     * @return
     * @throws Exception
     */
    public static String encodeRipeMd320Hex(byte[] data) throws Exception{
        byte[] b= encodeRipeMd320(data);
        return new String(Hex.encode(b));
    }

    /**
     * 
     * inital a RipeMd128 key
     * 
     * 
     * 
     * @return
     * @throws Exception
     */
    public static byte[] initHmacRipeMd128Key() throws Exception{
        Security.addProvider(new BouncyCastleProvider());
        KeyGenerator keyGenerator=KeyGenerator.getInstance("HmacRipeMD128");
        SecretKey secretKey=keyGenerator.generateKey();
        return secretKey.getEncoded();
    }

    /**
     * 
     * encode type array by HmacRipeMd128
     * 
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] encodeHmacRipeMd128(byte[] data, byte[] key) throws Exception{
        if (data==null || data.length<=0){
            throw new Exception("invalid data");
        }
        if (key==null || key.length<=0){
            throw new Exception("invalid key");
        }
        Security.addProvider(new BouncyCastleProvider());
        SecretKey secretKey=new SecretKeySpec(key,"HmacRipeMD128");
        Mac mac=Mac.getInstance(secretKey.getAlgorithm());
        mac.init(secretKey);
        return mac.doFinal(data);
    }

    /**
     * 
     * encode type array by HmacRipeMd128 to hex String
     * 
     * 
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static String encodeHmacRipeMd128Hex(byte[] data, byte[] key) throws Exception{
        if (key!=null && key.length>0){
            byte[] b= encodeHmacRipeMd128(data,key);
            return new String(Hex.encode(b));
        }
        throw new Exception("invalid key");
    }

    /**
     * 
     * inital a RipeMd160 key
     * 
     * 
     * @return
     * @throws Exception
     */
    public static byte[] initHmacRipeMd160Key() throws Exception{
        Security.addProvider(new BouncyCastleProvider());
        KeyGenerator keyGenerator=KeyGenerator.getInstance("HmacRipeMD160");
        SecretKey secretKey=keyGenerator.generateKey();
        return secretKey.getEncoded();
    }

    /**
     * 
     * encode type array by HmacRipeMd160
     * 
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] encodeHmacRipeMd160(byte[] data, byte[] key) throws Exception{
            if (key==null || key.length==0){
                return null;
            }
            Security.addProvider(new BouncyCastleProvider());
            SecretKey secretKey=new SecretKeySpec(key,"HmacRipeMD160");
            Mac mac=Mac.getInstance(secretKey.getAlgorithm());
            mac.init(secretKey);
            return mac.doFinal(data);
    }

    /**
     * 
     * encode type array by HmacRipeMd160 to hex String
     * 
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static String encodeHmacRipeMd160Hex(byte[] data, byte[] key) throws Exception{
        if (key==null || key.length==0 || data==null || data.length==0){
            return null;
        }
        byte[] b= encodeHmacRipeMd160(data,key);
        return new String(Hex.encode(b));
    }

}
