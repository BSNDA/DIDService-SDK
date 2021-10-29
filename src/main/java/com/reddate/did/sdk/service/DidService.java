package com.reddate.did.sdk.service;

import java.util.Objects;
import java.util.concurrent.TimeoutException;

import cn.hutool.core.util.ObjectUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.reddate.did.sdk.constant.ServiceURL;
import com.reddate.did.sdk.exception.DidException;
import com.reddate.did.sdk.param.req.ResetDidAuth;
import com.reddate.did.sdk.param.resp.DidDataWrapper;
import com.reddate.did.sdk.param.resp.DocumentInfo;
import com.reddate.did.sdk.protocol.common.BaseDidDocument;
import com.reddate.did.sdk.protocol.common.DidDocument;
import com.reddate.did.sdk.protocol.common.KeyPair;
import com.reddate.did.sdk.protocol.common.Proof;
import com.reddate.did.sdk.protocol.request.DidDocSotreReq;
import com.reddate.did.sdk.protocol.request.DidDocumentReq;
import com.reddate.did.sdk.protocol.request.RequestParam;
import com.reddate.did.sdk.protocol.request.ResetDidWrapper;
import com.reddate.did.sdk.protocol.response.CreateDidData;
import com.reddate.did.sdk.protocol.response.ResultData;
import com.reddate.did.sdk.util.DidUtils;
import com.reddate.did.sdk.util.ECDSAUtils;
import com.reddate.did.sdk.util.HttpUtils;

/**
 * 
 * The did module implement class,
 * 
 * this class contain the generated did, store did document on chain,
 * query did document,reset did authority main key function implement
 * 
 * 
 *
 */
public class DidService extends BaseService {

	public DidService(String url, String projectId, String token) {
		super(url, projectId,token);
	}

	private static final Logger logger = LoggerFactory.getLogger(DidService.class);
	
	
	/**
	 * 
	 * Create did document and store this document on block chain if choose store on block chain.
	 * 
	 * @param isStorageOnChain Store generated did document store on block chain 
	 * @return The did Identifier, generated did document and key pair.
	 */
	public ResultData<DidDataWrapper> generateDid(Boolean isStorageOnChain) {
		if (isStorageOnChain == null){
			throw new DidException("storage on chain is empty");
		}
		ResultData<CreateDidData> createDoc = createDidDocument();
		if(!createDoc.isSuccess()) {
			return ResultData.error(createDoc.getMsg(), DidDataWrapper.class);
		}
		logger.debug("create did information is :"+JSONObject.toJSONString(createDoc));
				
		if(isStorageOnChain) {
			ResultData<Boolean> sotreDoc = storeDidDocumentOnChain(createDoc.getData().getDidDocument());
			if(!sotreDoc.isSuccess()) {
				String msg = sotreDoc.getMsg();
				if(msg == null || msg.trim().isEmpty()) {
					msg = "store did document on chain failed";
				}
				return ResultData.error(msg, DidDataWrapper.class);
			}
		}
		
		DidDataWrapper dataWrapper =  new DidDataWrapper();
		dataWrapper.setDid(createDoc.getData().getDid());
		dataWrapper.setAuthKeyInfo(createDoc.getData().getAuthKeyInfo());
		dataWrapper.setRecyKeyInfo(createDoc.getData().getRecyKeyInfo());
		DocumentInfo documentInfo  = new DocumentInfo();
		documentInfo.setDid(createDoc.getData().getDidDocument().getDid());
		documentInfo.setAuthentication(createDoc.getData().getDidDocument().getAuthentication());
		documentInfo.setRecovery(createDoc.getData().getDidDocument().getRecovery());
		documentInfo.setCreated(createDoc.getData().getDidDocument().getCreated());
		documentInfo.setUpdated(createDoc.getData().getDidDocument().getUpdated());
		documentInfo.setVersion(createDoc.getData().getDidDocument().getVersion());
		documentInfo.setProof(createDoc.getData().getDidDocument().getProof());
		dataWrapper.setDocument(documentInfo);
        
		return ResultData.success(dataWrapper);
	}
	
	/**
	 * 
	 * Generated one did document
	 * 
	 * 
	 * @return The did Identifier, generated did document and key pair.
	 */
	private ResultData<CreateDidData> createDidDocument() {
		try {
			KeyPair primaryKeyPair = ECDSAUtils.createKey();
			KeyPair alternateKeyPair = ECDSAUtils.createKey();
			if (StringUtils.isBlank(primaryKeyPair.getPublicKey())
					|| StringUtils.isBlank(primaryKeyPair.getPrivateKey())
					|| StringUtils.isBlank(alternateKeyPair.getPublicKey())
					|| StringUtils.isBlank(alternateKeyPair.getPrivateKey())
			) {
				throw new DidException("create key pair failed");
			}

			BaseDidDocument baseDidDocument = DidUtils.generateBaseDidDocument(primaryKeyPair, alternateKeyPair);

			String didIdentifier = DidUtils.generateDidIdentifierByBaseDidDocument(baseDidDocument);
			if(StringUtils.isBlank(didIdentifier)){				
				throw new DidException("create did failed");
			}

			String did = DidUtils.generateDidByDidIdentifier(didIdentifier);
			if(StringUtils.isBlank(did)){
				throw new DidException("create did failed");
			}

			DidDocument didDocument = DidUtils.generateDidDocument(primaryKeyPair, alternateKeyPair, did);
			if(Objects.isNull(didDocument)){
				throw new DidException("create did document failed");
			}

			String signValue = ECDSAUtils.sign(JSONArray.toJSON(didDocument).toString(), primaryKeyPair.getPrivateKey());
			if(StringUtils.isBlank(signValue)){
				throw new DidException("sign did document failed");
			}

			Proof proof = new Proof();
			proof.setType(ECDSAUtils.TYPE);
			proof.setCreator(did);
			proof.setSignatureValue(signValue);
			didDocument.setProof(proof);

			CreateDidData createDidData = new CreateDidData();
			createDidData.setDid(did);
			createDidData.setAuthKeyInfo(primaryKeyPair);
			createDidData.setRecyKeyInfo(alternateKeyPair);
			createDidData.setDidDocument(didDocument);
			return ResultData.success(createDidData);
		}catch (TimeoutException e){
			e.printStackTrace();
			logger.error(e.getMessage(),e);
			return ResultData.error("create did failed", CreateDidData.class);
		}catch (Exception e){
			e.printStackTrace();
			logger.error(e.getMessage(),e);
			return ResultData.error("create did failed", CreateDidData.class);
		}
	}
	
	/**
	 * 
	 * Store this did document to block chain by request the did service
	 * 
	 * 
	 * @param document The did document
	 * @return On chain result 
	 */
	public ResultData<Boolean> storeDidDocumentOnChain(DidDocument document){
		if (ObjectUtil.isEmpty(document)){
			throw new DidException("document is empty");
		}
		if (StringUtils.isEmpty(document.getDid())){
			throw new DidException("did is empty");
		}
		if (StringUtils.isEmpty(document.getCreated())){
			throw new DidException("created is empty");
		}
		if (StringUtils.isEmpty(document.getUpdated())){
			throw new DidException("updated is empty");
		}
		if (StringUtils.isEmpty(document.getVersion())){
			throw new DidException("version is empty");
		}
		if (ObjectUtil.isEmpty(document.getRecovery())){
			throw new DidException("recovery is empty");
		}
		if (StringUtils.isEmpty(document.getRecovery().getPublicKey())){
			throw new DidException("recovery publicKey is empty");
		}
		if (StringUtils.isEmpty(document.getRecovery().getType())){
			throw new DidException("recovery type is empty");
		}
		if (ObjectUtil.isEmpty(document.getAuthentication())){
			throw new DidException("authentication is empty");
		}
		if (StringUtils.isEmpty(document.getAuthentication().getPublicKey())){
			throw new DidException("authentication publicKey is empty");
		}
		if (StringUtils.isEmpty(document.getAuthentication().getType())){
			throw new DidException("authentication type is empty");
		}
		if (ObjectUtil.isEmpty(document.getProof())){
			throw new DidException("proof is empty");
		}
		if (StringUtils.isEmpty(document.getProof().getType())){
			throw new DidException("proof type is empty");
		}
		if (StringUtils.isEmpty(document.getProof().getCreator())){
			throw new DidException("proof creator is empty");
		}
		if (StringUtils.isEmpty(document.getProof().getSignatureValue())){
			throw new DidException("proof signatureValue is empty");
		}
		RequestParam<DidDocSotreReq> reqParam = new RequestParam<>(this.getProjectId(),document.getDid());
		DidDocSotreReq didDocSotreReq = new DidDocSotreReq();
		didDocSotreReq.setDidDoc(document);
		reqParam.setData(didDocSotreReq);
		
		ResultData<Boolean> regResult = null;
		try {
			regResult = HttpUtils.postCall(this.getUrl()+ServiceURL.PUT_DID_ON_CHAIN,this.getToken(),reqParam, Boolean.class);
		} catch (Exception e) {
			throw new RuntimeException("store did document failed:"+e.getMessage());
		}
		
		if(regResult.isSuccess()) {
			return ResultData.success(null);
		}else {
			return ResultData.error("store did document failed:", Boolean.class);
		}	
	}

	/**
	 * Verify the did document format and sign is correct or not
	 * 
	 * 
	 * @param Did Document Did document content
	 * @return The verify result.
	 */
	public ResultData<Boolean> verifyDidDocument(DidDocument didDocument){

		if (ObjectUtil.isEmpty(didDocument)){
			throw new DidException("document is empty");
		}
		if (StringUtils.isEmpty(didDocument.getDid())){
			throw new DidException("did is empty");
		}
		if (StringUtils.isEmpty(didDocument.getCreated())){
			throw new DidException("created is empty");
		}
		if (StringUtils.isEmpty(didDocument.getUpdated())){
			throw new DidException("updated is empty");
		}
		if (StringUtils.isEmpty(didDocument.getVersion())){
			throw new DidException("version is empty");
		}
		if (ObjectUtil.isEmpty(didDocument.getRecovery())){
			throw new DidException("recovery is empty");
		}
		if (StringUtils.isEmpty(didDocument.getRecovery().getPublicKey())){
			throw new DidException("recovery publicKey is empty");
		}
		if (StringUtils.isEmpty(didDocument.getRecovery().getType())){
			throw new DidException("recovery type is empty");
		}
		if (ObjectUtil.isEmpty(didDocument.getAuthentication())){
			throw new DidException("authentication is empty");
		}
		if (StringUtils.isEmpty(didDocument.getAuthentication().getPublicKey())){
			throw new DidException("authentication publicKey is empty");
		}
		if (StringUtils.isEmpty(didDocument.getAuthentication().getType())){
			throw new DidException("authentication type is empty");
		}
		if (ObjectUtil.isEmpty(didDocument.getProof())){
			throw new DidException("proof is empty");
		}
		if (StringUtils.isEmpty(didDocument.getProof().getType())){
			throw new DidException("proof type is empty");
		}
		if (StringUtils.isEmpty(didDocument.getProof().getCreator())){
			throw new DidException("proof creator is empty");
		}
		if (StringUtils.isEmpty(didDocument.getProof().getSignatureValue())){
			throw new DidException("proof signatureValue is empty");
		}
		RequestParam<DidDocSotreReq> reqParam = new RequestParam<>(this.getProjectId(),didDocument.getDid());
		DidDocSotreReq didDocSotreReq = new DidDocSotreReq();
		didDocSotreReq.setDidDoc(didDocument);
		reqParam.setData(didDocSotreReq);

		ResultData<Boolean> regResult = null;
		try {
			regResult = HttpUtils.postCall(this.getUrl()+ServiceURL.VERIFY_DID_DOCUMENT,this.getToken(),reqParam, Boolean.class);
		} catch (Exception e) {
			throw new RuntimeException("register auth issue list failed:"+e.getMessage());
		}

		if(regResult.isSuccess()) {
			return ResultData.success(regResult.getData());
		}else {
			return ResultData.error("register did on chain fialed", Boolean.class);
		}
	}

	/**
	 * 
	 * Query the did document information on block chain
	 * 
	 * @param did Identifier
	 * @return did document detail information
	 */
	public ResultData<DidDocument> getDidDocument(String did){
		if (StringUtils.isEmpty(did)){
			throw new DidException("did is empty");
		}
		RequestParam<DidDocumentReq> reqParam = new RequestParam<>(this.getProjectId(),did);
		DidDocumentReq didDocumentReq = new DidDocumentReq();
		didDocumentReq.setDid(did);
		reqParam.setData(didDocumentReq);
		ResultData<DidDocument> regResult = null;
		try {
			regResult = HttpUtils.postCall(this.getUrl()+ServiceURL.GET_DID_DOCUMENT,this.getToken(),reqParam, DidDocument.class);
		} catch (Exception e) {
			throw new RuntimeException("register auth issue list failed:"+e.getMessage());
		}

		if(regResult.isSuccess()) {
			return ResultData.success(regResult.getData());
		}else {
			return ResultData.error("register did on chain fialed", DidDocument.class);
		}
	}

	/**
	 * 
	 * Reset the main public key in the did document on block chain. 
	 * this function first validate the recovery key, 
	 * after recovery pass, then reset the main public key in this document on block chain.
	 * 
	 * 
	 * @param restDidAuth  Rest the did document key information.
	 * @return Return the reset main public key result
	 */
	public ResultData<KeyPair> resetDidAuth(ResetDidAuth resetDidAuth) throws Exception{
		if (ObjectUtil.isEmpty(resetDidAuth)){
			throw new DidException("reset did auth is empty");
		}
		if (StringUtils.isEmpty(resetDidAuth.getDid())){
			throw new DidException("did is empty");
		}

		if (ObjectUtil.isEmpty(resetDidAuth.getRecoveryKey())){
			throw new DidException("recovery key is empty");
		}
		if (ObjectUtil.isEmpty(resetDidAuth.getRecoveryKey().getPrivateKey())){
			throw new DidException("private key is empty");
		}
		if (ObjectUtil.isEmpty(resetDidAuth.getRecoveryKey().getType())){
			throw new DidException("type key is empty");
		}
		if (ObjectUtil.isEmpty(resetDidAuth.getRecoveryKey().getPublicKey())){
			throw new DidException("public key is empty");
		}
		ResultData<DidDocument> queryDidDocument = null;
		try {
			queryDidDocument = this.getDidDocument(resetDidAuth.getDid());
		} catch (Exception e1) {
			throw new DidException("quey did document on chian failed");
		}
		if(!queryDidDocument.isSuccess()) {
			throw new DidException("quey did document on chian failed:"+queryDidDocument.getMsg());
		}
		
		String recoveryPublicKey = ECDSAUtils.getPublicKey(resetDidAuth.getRecoveryKey().getPrivateKey());
		if(!recoveryPublicKey.equals(queryDidDocument.getData().getRecovery().getPublicKey())) {
			throw new DidException("recovery key pair is incorrect, can not reset did auth");
		}
		
		DidDocument didDoc = queryDidDocument.getData();
		KeyPair keyPair = resetDidAuth.getPrimaryKeyPair();
		if(resetDidAuth.getPrimaryKeyPair() == null 
			|| resetDidAuth.getPrimaryKeyPair().getPrivateKey() == null || resetDidAuth.getPrimaryKeyPair().getPrivateKey().trim().isEmpty()
			|| resetDidAuth.getPrimaryKeyPair().getPublicKey() == null || resetDidAuth.getPrimaryKeyPair().getPublicKey().trim().isEmpty() 
			|| resetDidAuth.getPrimaryKeyPair().getType() == null || resetDidAuth.getPrimaryKeyPair().getType().trim().isEmpty()) {
			keyPair = ECDSAUtils.createKey();
		}else {
			String publicKey = ECDSAUtils.getPublicKey(resetDidAuth.getPrimaryKeyPair().getPrivateKey());
			if(publicKey == null || !publicKey.equals(resetDidAuth.getPrimaryKeyPair().getPublicKey())) {
				throw new DidException("primary private key and public key do not match");
			}
		}
		
		DidDocument newDidDocument = DidUtils.renewDidDocument(didDoc, keyPair);
		String signValue = ECDSAUtils.sign(JSONArray.toJSON(newDidDocument).toString(), keyPair.getPrivateKey());
		if(StringUtils.isBlank(signValue)){
			throw new DidException("sign did document failed");
		}

		// Assembling sign
		Proof proof = new Proof();
		proof.setType(ECDSAUtils.TYPE);
		proof.setCreator(newDidDocument.getDid());
		proof.setSignatureValue(signValue);
		newDidDocument.setProof(proof);
		
		String authPublicKeySign = null;
		try {
			authPublicKeySign = ECDSAUtils.sign(recoveryPublicKey, resetDidAuth.getRecoveryKey().getPrivateKey());
		} catch (Exception e1) {
			e1.printStackTrace();
			throw new RuntimeException("sign public key failed:"+e1.getMessage());
		}
		
		String signature = com.reddate.did.sdk.util.Signatures.get().setInfo(this.getProjectId(),didDoc.getDid())
				.add("document", newDidDocument)
				.add("authPubKeySign", authPublicKeySign)
				.sign(resetDidAuth.getRecoveryKey().getPrivateKey());
				
		RequestParam<ResetDidWrapper> reqParam = new RequestParam<>(this.getProjectId(),newDidDocument.getDid());
		ResetDidWrapper resetDidWrapper = new ResetDidWrapper();
		resetDidWrapper.setDidDoc(didDoc);
		resetDidWrapper.setAuthPubKeySign(authPublicKeySign);
		reqParam.setData(resetDidWrapper);
		reqParam.setSign(signature);
		
		ResultData<KeyPair> restAuthResult = null;
		try {
			restAuthResult = HttpUtils.postCall(this.getUrl()+ServiceURL.REST_DID_AUTH,this.getToken(),reqParam, KeyPair.class);
		} catch (Exception e) {
			throw new RuntimeException("rest did auth failed:"+e.getMessage());
		}
		
		if(restAuthResult.isSuccess()) {
			return ResultData.success(keyPair);
		}else {
			return ResultData.error("rest did auth failed", KeyPair.class);
		}
	}
	
}
