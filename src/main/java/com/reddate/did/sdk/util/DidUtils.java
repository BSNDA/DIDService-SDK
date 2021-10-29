package com.reddate.did.sdk.util;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONArray;
import com.reddate.did.sdk.constant.CurrencyCode;
import com.reddate.did.sdk.protocol.common.BaseDidDocument;
import com.reddate.did.sdk.protocol.common.DidDocument;
import com.reddate.did.sdk.protocol.common.KeyPair;
import com.reddate.did.sdk.protocol.common.PublicKey;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * Did utils class,
 * some did common tools method implement in this class
 * 
 * 
 *
 */
public final class DidUtils {

    /**
     * Generate base did document
     * @param primaryKeyPair
     * @param alternateKeyPair
     */
    public static BaseDidDocument generateBaseDidDocument(
           KeyPair primaryKeyPair,
           KeyPair alternateKeyPair
        ) throws Exception {
        BaseDidDocument baseDidDocument = new BaseDidDocument();
        baseDidDocument.setContext(CurrencyCode.W3C_FORMAT_ADDRESS);
        PublicKey primaryPublicKey = new PublicKey();
        primaryPublicKey.setType(ECDSAUtils.TYPE);
        primaryPublicKey.setPublicKey(primaryKeyPair.getPublicKey());
        baseDidDocument.setAuthentication(primaryPublicKey);
        PublicKey alternatePublicKey = new PublicKey();
        alternatePublicKey.setType(ECDSAUtils.TYPE);
        alternatePublicKey.setPublicKey(alternateKeyPair.getPublicKey());
        baseDidDocument.setRecovery(alternatePublicKey);
        return baseDidDocument;
    }

    /**
     * Generate base did document from 
     * @param primaryKeyPair
     * @param alternateKeyPair
     */
    public static BaseDidDocument generateBaseDidDocument(
    		final DidDocument didDocument
        ) throws Exception {
        BaseDidDocument baseDidDocument = new BaseDidDocument();
        baseDidDocument.setContext(CurrencyCode.W3C_FORMAT_ADDRESS);
        baseDidDocument.setAuthentication(didDocument.getAuthentication());
        baseDidDocument.setRecovery(didDocument.getRecovery());
        return baseDidDocument;
    }
    
    /**
     * Generate did identifier (encode the base did document after hashing twice)
     * @param baseDidDocument
     */
    public static String generateDidIdentifierByBaseDidDocument(
            BaseDidDocument baseDidDocument
        ) throws Exception {
        String baseDidDocumentStr = JSONArray.toJSON(baseDidDocument).toString();
        // Coding base did with sha256
        byte[] firstHashBaseDidDocument = SHA256Utils.getSha256(baseDidDocumentStr);
        // On this basis, ripemd160 coding is carried out
        byte[] secondHashBaseDidDocument = RipeMDUtils.encodeRipeMd160(firstHashBaseDidDocument);
        // Finally, base28 coding is carried out
        String afterEncodeBaseDidDocument = Base58Utils.encode(secondHashBaseDidDocument);
        return afterEncodeBaseDidDocument;
    }

    /**
     * Generate did
     * @param didIdentifier
     */
    public static String generateDidByDidIdentifier(
            String didIdentifier
    ) throws Exception {
        StringBuffer didFormatSplicing = new StringBuffer();
        if (StringUtils.isNotBlank(didIdentifier)){
            didFormatSplicing.append(CurrencyCode.DID_PREFIX);
            didFormatSplicing.append(CurrencyCode.DID_SEPARATOR);
            didFormatSplicing.append(CurrencyCode.DID_PROJECT_NAME);
            didFormatSplicing.append(CurrencyCode.DID_SEPARATOR);
            didFormatSplicing.append(didIdentifier);
        }
        return didFormatSplicing.toString();
    }

    /**
     * Generate did document
     * @param primaryKeyPair
     * @param alternateKeyPair
     * @param did
     */
    public static DidDocument generateDidDocument(
            KeyPair primaryKeyPair,
            KeyPair alternateKeyPair,
            String did
    ) throws Exception {
        DidDocument didDocument = new DidDocument();
        didDocument.setDid(did);
        didDocument.setVersion(CurrencyCode.DEFAULT_VERSION);
        String now = DateUtil.now();
        didDocument.setCreated(now);
        didDocument.setUpdated(now);
        PublicKey primaryPublicKey = new PublicKey();
        primaryPublicKey.setType(ECDSAUtils.TYPE);
        primaryPublicKey.setPublicKey(primaryKeyPair.getPublicKey());
        didDocument.setAuthentication(primaryPublicKey);
        PublicKey alternatePublicKey = new PublicKey();
        alternatePublicKey.setType(ECDSAUtils.TYPE);
        alternatePublicKey.setPublicKey(alternateKeyPair.getPublicKey());
        didDocument.setRecovery(alternatePublicKey);
        return didDocument;
    }

    /**
     * 
     * Copy a did document to an did document
     * 
     * @param didDocument
     * @param keyPair
     * @return
     */
    public static DidDocument renewDidDocument(
            DidDocument didDocument,
            KeyPair keyPair
    ){
        PublicKey primaryPublicKey = new PublicKey();
        if(keyPair.getType() == null || keyPair.getType().trim().isEmpty()) {
        	primaryPublicKey.setType(ECDSAUtils.TYPE);
        }else {
        	primaryPublicKey.setType(keyPair.getType().trim());	
        }
        primaryPublicKey.setPublicKey(keyPair.getPublicKey());
        didDocument.setAuthentication(primaryPublicKey);
        didDocument.setUpdated(DateUtil.now());
        didDocument.setProof(null);
        return didDocument;
    }
}
