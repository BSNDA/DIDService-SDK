package com.reddate.did.sdk.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONArray;
import com.reddate.did.sdk.constant.ServiceURL;
import com.reddate.did.sdk.exception.DidException;
import com.reddate.did.sdk.param.req.CreateCredential;
import com.reddate.did.sdk.param.req.QueryCredentialList;
import com.reddate.did.sdk.param.req.RevokeCredential;
import com.reddate.did.sdk.protocol.common.DidDocument;
import com.reddate.did.sdk.protocol.common.PublicKey;
import com.reddate.did.sdk.protocol.request.CreateCredentialReq;
import com.reddate.did.sdk.protocol.request.CredentialWrapper;
import com.reddate.did.sdk.protocol.request.QueryCredentialWrapper;
import com.reddate.did.sdk.protocol.request.RequestParam;
import com.reddate.did.sdk.protocol.request.RevokCredentialReq;
import com.reddate.did.sdk.protocol.request.VerifyCredentialWrapper;
import com.reddate.did.sdk.protocol.response.BaseCredential;
import com.reddate.did.sdk.protocol.response.Pages;
import com.reddate.did.sdk.protocol.response.ResultData;
import com.reddate.did.sdk.util.ECDSAUtils;
import com.reddate.did.sdk.util.HttpUtils;
import com.reddate.did.sdk.util.Signatures;
import org.apache.commons.lang3.StringUtils;

/**
 * 
 * The did credential implement class,
 * this class contain create credential , verify credential,
 * revoke credential, query credential list function implement 
 * 
 * 
 *
 */
public class CredentialService extends BaseService{
	
	private DidService didService;
	
    public CredentialService(String url, String projectId, String token) {
        super(url, projectId,token);
        didService = new DidService(url, projectId, token);
    }

    /**
     * 
	 * Generate and issue a new credential base on the CPT template and input information 
	 * 
	 * 
	 * @param createCredential The credential field values
	 * @return Return a issued credential 
     */
    public CredentialWrapper createCredential(CreateCredential createCredential){
    	if (ObjectUtil.isEmpty(createCredential)){
    		throw new DidException("create credential is empty");
		}
		if (ObjectUtil.isEmpty(createCredential.getClaim())){
			throw new DidException("claim is empty");
		}
		if (StringUtils.isEmpty(createCredential.getIssuerDid())){
			throw new DidException("issuer did is empty");
		}
		if (StringUtils.isEmpty(createCredential.getExpirationDate())){
			throw new DidException("expiration date did is empty");
		}
		if (StringUtils.isEmpty(createCredential.getPrivateKey())){
			throw new DidException("private key is empty");
		}
		if (StringUtils.isEmpty(createCredential.getUserDid())){
			throw new DidException("user id is empty");
		}
		if (ObjectUtil.isEmpty(createCredential.getCptId())){
			throw new DidException("cpt id is empty");
		}
    	ResultData<DidDocument> didResult = didService.getDidDocument(createCredential.getIssuerDid());
    	if(didResult == null) {
    		throw new DidException("query issuer did  document failed");
    	}
    	
    	if(!didResult.isSuccess()) {
    		throw new DidException("query issuer did  document failed:"+didResult.getMsg());
    	}
    	
    	String publicKey = ECDSAUtils.getPublicKey(createCredential.getPrivateKey());
    	if(!didResult.getData().getAuthentication().getPublicKey().equals(publicKey)) {
    		throw new DidException("issuer private key is incorrect");
    	}
    	
    	RequestParam<CreateCredentialReq> reqParam = new RequestParam<>(this.getProjectId(),createCredential.getIssuerDid());
        CreateCredentialReq credential = new CreateCredentialReq();
        credential.setCptId(createCredential.getCptId());
        credential.setIssuerDid(createCredential.getIssuerDid());
        credential.setUserDid(createCredential.getUserDid());
        credential.setExpirationDate(createCredential.getExpirationDate());
        credential.setClaim(createCredential.getClaim());
        if(createCredential.getType() == null || createCredential.getType().trim().isEmpty()) {
        	credential.setType("Proof");
        }else {
        	credential.setType(createCredential.getType());
        }
        
        reqParam.setData(credential);
        
		String sign = Signatures.get().setInfo(this.getProjectId(),createCredential.getIssuerDid())
				.add("cptId", credential.getCptId())
				.add("userDid",credential.getUserDid())
				.add("expirationDate",credential.getExpirationDate())
				.add("claim",credential.getClaim())
				.add("type",credential.getType())
				.sign(createCredential.getPrivateKey());
		reqParam.setSign(sign);
		
        ResultData<CredentialWrapper> credentialWrapperResultData= null;
        try {
            credentialWrapperResultData = HttpUtils.postCall(this.getUrl()+ ServiceURL.CREATE_CREDENTIAL,this.getToken(),reqParam, CredentialWrapper.class);
        } catch (Exception e) {
            throw new DidException("create credential failed:"+e.getMessage());
        }
        if(!credentialWrapperResultData.isSuccess()) {
            throw new DidException(credentialWrapperResultData.getMsg());
        }
        
        
        CredentialWrapper credentialWrapper = credentialWrapperResultData.getData();
		String signValue = null;
		try {
			signValue = ECDSAUtils.sign(JSONArray.toJSON(credentialWrapper).toString(), createCredential.getPrivateKey());
		} catch (Exception e) {
			throw new DidException("calculate credential sign value failed");
		}
		Map<String, Object> proof = new HashMap<>();
		proof.put("creator", createCredential.getIssuerDid());
		proof.put("type", ECDSAUtils.TYPE);
		proof.put("signatureValue", signValue);
		credentialWrapper.setProof(proof);
        
        return credentialWrapper;
    }

    /**
     * 
	 * Verify the issued credential is changed or not,
	 * this function validate the credential format and credential sign
	 * 
	 * 
	 * @param createCredential  Credential detail information
	 * @param publicKey Public key information
	 * @return Return the verify result
     */
    public Boolean verifyCredential(CredentialWrapper createCredential,PublicKey publicKey){
		if (ObjectUtil.isEmpty(createCredential)){
			throw new DidException("create credential is empty");
		}
		if (ObjectUtil.isEmpty(createCredential.getClaim())){
			throw new DidException("claim is empty");
		}
		if (StringUtils.isEmpty(createCredential.getIssuerDid())){
			throw new DidException("issuer did is empty");
		}
		if (StringUtils.isEmpty(createCredential.getExpirationDate())){
			throw new DidException("expiration date did is empty");
		}
		if (StringUtils.isEmpty(createCredential.getType())){
			throw new DidException("type did is empty");
		}
		if (StringUtils.isEmpty(createCredential.getCreated())){
			throw new DidException("created is empty");
		}
		if (StringUtils.isEmpty(createCredential.getContext())){
			throw new DidException("context is empty");
		}
		if (ObjectUtil.isEmpty(createCredential.getProof())){
			throw new DidException("proof is empty");
		}
		if (ObjectUtil.isEmpty(createCredential.getId())){
			throw new DidException("id is empty");
		}
		if (StringUtils.isEmpty(createCredential.getUserDid())){
			throw new DidException("user id is empty");
		}
		if (ObjectUtil.isEmpty(createCredential.getCptId())){
			throw new DidException("cpt id is empty");
		}

    	RequestParam<VerifyCredentialWrapper> reqParam = new RequestParam<>(this.getProjectId(),createCredential.getUserDid());
		VerifyCredentialWrapper verifyCredentialWrapper = new VerifyCredentialWrapper();
		verifyCredentialWrapper.setCredentialWrapper(createCredential);
		verifyCredentialWrapper.setPublicKey(publicKey);
        reqParam.setData(verifyCredentialWrapper);
        
        
        ResultData<Boolean> verifyCredential= null;
        try {
            verifyCredential = HttpUtils.postCall(this.getUrl()+ ServiceURL.VERIFY_CREDENTIAL,this.getToken(),reqParam, Boolean.class);
        } catch (Exception e) {
            throw new RuntimeException("verify credential failed:"+e.getMessage());
        }
        if(!verifyCredential.isSuccess()) {
        	throw new DidException(verifyCredential.getMsg());
        }
        
        return verifyCredential.getData();
    }
    
    /**
     * 
	 * revoke issued credential on block chain, 
	 * add this credential to the revoke credential list on block chain.
	 * 
	 * 
	 * @param cred Want to revoke credential information
	 * @return Return the revoke credential result
     */
    public Boolean revokeCredential(RevokeCredential cred) {
    	if (ObjectUtil.isEmpty(cred)){
			throw new DidException("revoke credential is empty");
		}
		if (StringUtils.isEmpty(cred.getCredId())){
			throw new DidException("credential id is empty");
		}
		if (StringUtils.isEmpty(cred.getPrivateKey())){
			throw new DidException("private key is empty");
		}
		if (StringUtils.isEmpty(cred.getDid())){
			throw new DidException("did is empty");
		}
		if (ObjectUtil.isEmpty(cred.getCptId())){
			throw new DidException("cpt id is empty");
		}
    	RequestParam<RevokCredentialReq> reqParam = new RequestParam<>(this.getProjectId(),cred.getDid());
    	RevokCredentialReq revokCredentialReq = new RevokCredentialReq();
    	revokCredentialReq.setCredId(cred.getCredId());
    	revokCredentialReq.setCptId(cred.getCptId());
    	revokCredentialReq.setDid(cred.getDid());
    	revokCredentialReq.setRevokeDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    	String publicKeySignValue = null;
		try {
			publicKeySignValue = ECDSAUtils.sign(ECDSAUtils.getPublicKey(cred.getPrivateKey()), cred.getPrivateKey());
		} catch (Exception e) {
			throw new DidException("calculate public key sign value failed");
		}
    	revokCredentialReq.setPublicKeySign(publicKeySignValue);
    	
        StringBuffer signRawData = new StringBuffer();
        signRawData.append(revokCredentialReq.getDid()).
                append(revokCredentialReq.getRevokeDate()).
                append(revokCredentialReq.getCredId());
        String profSignVal = null;
        try {
			profSignVal = publicKeySignValue = ECDSAUtils.sign(signRawData.toString(), cred.getPrivateKey());
		} catch (Exception e1) {
			e1.printStackTrace();
			throw new DidException("calculate revoke credential sign value failed");
		}
        revokCredentialReq.setRevokeSign(profSignVal);
    	reqParam.setData(revokCredentialReq);
    	
		String sign = Signatures.get().setInfo(this.getProjectId(),cred.getDid())
				.add("credId", revokCredentialReq.getCredId())
				.add("cptId",revokCredentialReq.getCptId())
				.add("did",revokCredentialReq.getDid())
				.add("publicKeySign",revokCredentialReq.getPublicKeySign())
				.add("revokeDate",revokCredentialReq.getRevokeDate())
				.add("revokeSign",revokCredentialReq.getRevokeSign())
				.sign(cred.getPrivateKey());
		reqParam.setSign(sign);
    	
		 ResultData<Boolean> revokeResult = null;
	     try {
	    	 revokeResult = HttpUtils.postCall(this.getUrl()+ ServiceURL.REVOKED_CRED,this.getToken(),reqParam, Boolean.class);
	     } catch (Exception e) {
	    	 throw new RuntimeException("verify credential failed:"+e.getMessage());
	     }
	     if(!revokeResult.isSuccess()) {
	    	 throw new DidException(revokeResult.getMsg());
	     }
    	
		return revokeResult.getData();
	}
    
    /**
     * 
	 * Query the revoked credential list on the block chain
	 * 
	 * 
	 * @param queryCredentialList  Page information and authority issuer did
	 * @return Return the credential List
     */
	public Pages<BaseCredential> getRevokedCredList(QueryCredentialList queryCredentialList){
    	if (ObjectUtil.isEmpty(queryCredentialList)){
    		throw new DidException("query credential list param is empty");
		}
		if (StringUtils.isEmpty(queryCredentialList.getDid())){
			throw new DidException("did is empty");
		}
		if (ObjectUtil.isEmpty(queryCredentialList.getPage())){
			throw new DidException("page is empty");
		}
		if (ObjectUtil.isEmpty(queryCredentialList.getSize())){
			throw new DidException("size is empty");
		}
		RequestParam<QueryCredentialWrapper> reqParam = new RequestParam<>(this.getProjectId(),queryCredentialList.getDid());
		QueryCredentialWrapper queryCredentialWrapper = new QueryCredentialWrapper();
		queryCredentialWrapper.setDid(queryCredentialList.getDid());
		queryCredentialWrapper.setPage(queryCredentialList.getPage());
		queryCredentialWrapper.setSize(queryCredentialList.getSize());
		reqParam.setData(queryCredentialWrapper);
		ResultData<Map> credentialData = null;
		try {
			credentialData = HttpUtils.postCall(this.getUrl()+ServiceURL.GET_REVOKED_CRED_LIST,this.getToken(),reqParam, Map.class);
		} catch (Exception e) {
			throw new DidException("get revokedCred list failed:"+e.getMessage());
		}
		if(!credentialData.isSuccess()) {
			throw new DidException(credentialData.getMsg());
		}
		
		Pages<BaseCredential> pages = this.parseToPage(credentialData.getData(), BaseCredential.class);
		
		return pages;
	}
    
}
