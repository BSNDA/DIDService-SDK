package com.reddate.did.sdk.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONArray;
import com.reddate.did.sdk.constant.ErrorMessage;
import com.reddate.did.sdk.constant.ServiceURL;
import com.reddate.did.sdk.exception.DidException;
import com.reddate.did.sdk.param.CryptoType;
import com.reddate.did.sdk.param.req.CreateCredential;
import com.reddate.did.sdk.param.req.QueryCredential;
import com.reddate.did.sdk.param.req.RevokeCredential;
import com.reddate.did.sdk.protocol.common.DidDocument;
import com.reddate.did.sdk.protocol.common.PublicKey;
import com.reddate.did.sdk.protocol.request.CptInfo;
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
 * The did credential implement class, this class contain create credential ,
 * verify credential, revoke credential, query credential list function
 * implement
 */
public class CredentialService extends BaseService {

	private DidService didService;

	private AuthIssuerService authIssuerService;

	public CredentialService(String url, String projectId, String token, CryptoType cryptoType) {
		super(url, projectId, token, cryptoType);
		didService = new DidService(url, projectId, token, cryptoType);
		authIssuerService = new AuthIssuerService(url, projectId, token, cryptoType);
	}

	/**
	 * Generate and issue a new credential base on the CPT template and input
	 * information
	 * 
	 * The issuer issues a certificate for the user according to the content of the
	 * certificate application filled in by the user. The certificate needs the
	 * signature of the issuer, so the issuer needs to pass in the master private
	 * key to sign.
	 * 
	 * @param createCredential The credential field values
	 * @return Return a issued credential
	 */
	public CredentialWrapper createCredential(CreateCredential createCredential) {
		if (ObjectUtil.isEmpty(createCredential)) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "createCredential is empty");
		}
		if (ObjectUtil.isEmpty(createCredential.getClaim())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "claim is empty");
		}
		if (StringUtils.isBlank(createCredential.getIssuerDid())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "issuerDid is empty");
		}
		if (StringUtils.isBlank(createCredential.getExpirationDate())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "expirationDate is empty");
		}
		if (StringUtils.isBlank(createCredential.getPrivateKey())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "privateKey is empty");
		}
		if (StringUtils.isBlank(createCredential.getUserDid())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "userDid is empty");
		}
		if (ObjectUtil.isEmpty(createCredential.getCptId())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "cptId is empty");
		}
		ResultData<DidDocument> didResult = didService.getDidDocument(createCredential.getIssuerDid());
		if (didResult == null) {
			throw new DidException(ErrorMessage.DID_NOT_EXIST.getCode(), ErrorMessage.DID_NOT_EXIST.getMessage());
		}

		if (!didResult.isSuccess()) {
			throw new DidException(didResult.getCode(), didResult.getMsg());
		}

		String publicKey = ECDSAUtils.getPublicKey(createCredential.getPrivateKey());
		if(publicKey == null || publicKey.trim().isEmpty()) {
			throw new DidException(ErrorMessage.PRIVATE_KEY_ILLEGAL_FORMAT.getCode(),
					ErrorMessage.PRIVATE_KEY_ILLEGAL_FORMAT.getMessage());
		} else if (!didResult.getData().getAuthentication().getPublicKey().equals(publicKey)) {
			throw new DidException(ErrorMessage.CPT_AND_ISSUER_CANNOT_MATCH.getCode(),
					ErrorMessage.CPT_AND_ISSUER_CANNOT_MATCH.getMessage());
		}

		CptInfo cptInfo = authIssuerService.queryCptById(createCredential.getCptId());

		RequestParam<CreateCredentialReq> reqParam = new RequestParam<>(this.getProjectId(),
				createCredential.getIssuerDid());
		CreateCredentialReq credential = new CreateCredentialReq();
		credential.setCptId(createCredential.getCptId());
		credential.setIssuerDid(createCredential.getIssuerDid());
		credential.setUserDid(createCredential.getUserDid());
		credential.setExpirationDate(createCredential.getExpirationDate());
		credential.setClaim(createCredential.getClaim());
		if (createCredential.getType() == null || createCredential.getType().trim().isEmpty()) {
			credential.setType("Proof");
		} else {
			credential.setType(createCredential.getType());
		}
		if(createCredential.getShortDesc() == null 
				|| createCredential.getShortDesc().trim().isEmpty()) {
			credential.setShortDesc(cptInfo.getTitle());
		}else {
			credential.setShortDesc(createCredential.getShortDesc());
		}
		credential.setLongDesc(createCredential.getLongDesc());

		reqParam.setData(credential);

		String sign = Signatures.get().setInfo(this.getProjectId(), createCredential.getIssuerDid())
				.add("cptId", credential.getCptId()).add("userDid", credential.getUserDid())
				.add("expirationDate", credential.getExpirationDate()).add("claim", credential.getClaim())
				.add("type", credential.getType()).add("shortDesc", credential.getShortDesc())
				.add("longDesc", credential.getLongDesc()).sign(createCredential.getPrivateKey());
		reqParam.setSign(sign);

		ResultData<CredentialWrapper> credentialWrapperResultData = HttpUtils.postCall(
				this.getUrl() + ServiceURL.CREATE_CREDENTIAL, this.getToken(), reqParam, CredentialWrapper.class);

		CredentialWrapper credentialWrapper = credentialWrapperResultData.getData();
		String signValue = ECDSAUtils.sign(JSONArray.toJSON(credentialWrapper).toString(),
				createCredential.getPrivateKey());
		Map<String, Object> proof = new HashMap<>();
		proof.put("creator", createCredential.getIssuerDid());
		proof.put("type", ECDSAUtils.TYPE);
		proof.put("signatureValue", signValue);
		credentialWrapper.setProof(proof);

		return credentialWrapper;
	}

	/**
	 * Verify that the credentials are reality and valid.
	 * 
	 * Through certificate verification, the authenticity of the certificate can be
	 * identified. Each certificate is signed by the issuing authentication private
	 * key, so as long as the signature is verified, it means that the certificate
	 * itself is true. On this basis, re-verify the validity period of the
	 * certificate and whether it has been revoked. If it passes, it means that the
	 * certificate is acceptable.
	 * 
	 * @param createCredential Credential detail information
	 * @param publicKey        Public key information
	 * @return Return the verify result
	 */
	public Boolean verifyCredential(CredentialWrapper createCredential, PublicKey publicKey) {
		if (ObjectUtil.isEmpty(createCredential)) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "createCredential is empty");
		}
		if (ObjectUtil.isEmpty(createCredential.getClaim())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "claim is empty");
		}
		if (StringUtils.isBlank(createCredential.getIssuerDid())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "issuerDid is empty");
		}
		if (StringUtils.isBlank(createCredential.getExpirationDate())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "expirationDate is empty");
		}
		if (StringUtils.isBlank(createCredential.getType())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "type is empty");
		}
		if (StringUtils.isBlank(createCredential.getCreated())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "created is empty");
		}
		if (StringUtils.isBlank(createCredential.getContext())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "context is empty");
		}
		if (ObjectUtil.isEmpty(createCredential.getProof())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "proof is empty");
		}
		if (ObjectUtil.isEmpty(createCredential.getId())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "id is empty");
		}
		if (StringUtils.isBlank(createCredential.getUserDid())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "userDid is empty");
		}
		if (ObjectUtil.isEmpty(createCredential.getCptId())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "cptId is empty");
		}
		if (ObjectUtil.isEmpty(publicKey)) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "publicKey is empty");
		}
		if (ObjectUtil.isEmpty(publicKey.getPublicKey())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "publicKey is empty");
		}
		if (ObjectUtil.isEmpty(publicKey.getType())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "publicKey.type is empty");
		}

		RequestParam<VerifyCredentialWrapper> reqParam = new RequestParam<>(this.getProjectId(),
				createCredential.getUserDid());
		VerifyCredentialWrapper verifyCredentialWrapper = new VerifyCredentialWrapper();
		verifyCredentialWrapper.setCredentialWrapper(createCredential);
		verifyCredentialWrapper.setPublicKey(publicKey);
		reqParam.setData(verifyCredentialWrapper);

		ResultData<Boolean> verifyCredential = HttpUtils.postCall(this.getUrl() + ServiceURL.VERIFY_CREDENTIAL,
				this.getToken(), reqParam, Boolean.class);

		return verifyCredential.getData();
	}

	/**
	 * revoke issued credential on block chain, add this credential to the revoke
	 * credential list on block chain.
	 * 
	 * @param cred Want to revoke credential information
	 * @return Return the revoke credential result
	 */
	public Boolean revokeCredential(RevokeCredential cred) {
		if (ObjectUtil.isEmpty(cred)) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "cred is empty");
		}
		if (StringUtils.isBlank(cred.getCredId())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "credId is empty");
		}
		if (StringUtils.isBlank(cred.getPrivateKey())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "privateKey is empty");
		}
		if (StringUtils.isBlank(cred.getDid())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "did is empty");
		}
		if (ObjectUtil.isEmpty(cred.getCptId())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "cptId is empty");
		}
		RequestParam<RevokCredentialReq> reqParam = new RequestParam<>(this.getProjectId(), cred.getDid());
		RevokCredentialReq revokCredentialReq = new RevokCredentialReq();
		revokCredentialReq.setCredId(cred.getCredId());
		revokCredentialReq.setCptId(cred.getCptId());
		revokCredentialReq.setDid(cred.getDid());
		revokCredentialReq
				.setRevokeDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
		String publicKeySignValue = ECDSAUtils.sign(ECDSAUtils.getPublicKey(cred.getPrivateKey()),
				cred.getPrivateKey());
		revokCredentialReq.setPublicKeySign(publicKeySignValue);

		StringBuffer signRawData = new StringBuffer();
		signRawData.append(revokCredentialReq.getDid()).append(revokCredentialReq.getRevokeDate())
				.append(revokCredentialReq.getCredId());
		String profSignVal = ECDSAUtils.sign(signRawData.toString(), cred.getPrivateKey());
		revokCredentialReq.setRevokeSign(profSignVal);
		reqParam.setData(revokCredentialReq);

		String sign = Signatures.get().setInfo(this.getProjectId(), cred.getDid())
				.add("credId", revokCredentialReq.getCredId()).add("cptId", revokCredentialReq.getCptId())
				.add("did", revokCredentialReq.getDid()).add("publicKeySign", revokCredentialReq.getPublicKeySign())
				.add("revokeDate", revokCredentialReq.getRevokeDate())
				.add("revokeSign", revokCredentialReq.getRevokeSign()).sign(cred.getPrivateKey());
		reqParam.setSign(sign);

		ResultData<Boolean> revokeResult = HttpUtils.postCall(this.getUrl() + ServiceURL.REVOKED_CRED, this.getToken(),
				reqParam, Boolean.class);

		return revokeResult.getData();
	}

	/**
	 * Query the revoked credential and and the revocation time, can query by the
	 * crednential's issuer did, also can query by the credential Id
	 * 
	 * @param queryCredential Paging information and authority issuer did
	 * @return Return the credential List
	 */
	public Pages<BaseCredential> getRevokedCredList(QueryCredential queryCredential) {
		if (ObjectUtil.isEmpty(queryCredential)) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "queryCredential is empty");
		}
		if (StringUtils.isBlank(queryCredential.getDid())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "did is empty");
		}
		if (ObjectUtil.isEmpty(queryCredential.getPage())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "page is empty");
		}
		if (ObjectUtil.isEmpty(queryCredential.getSize())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "size is empty");
		}
		RequestParam<QueryCredentialWrapper> reqParam = new RequestParam<>(this.getProjectId(),
				queryCredential.getDid());
		QueryCredentialWrapper queryCredentialWrapper = new QueryCredentialWrapper();
		queryCredentialWrapper.setDid(queryCredential.getDid());
		queryCredentialWrapper.setPage(queryCredential.getPage());
		queryCredentialWrapper.setSize(queryCredential.getSize());
		reqParam.setData(queryCredentialWrapper);

		ResultData<Map> credentialData = HttpUtils.postCall(this.getUrl() + ServiceURL.GET_REVOKED_CRED_LIST,
				this.getToken(), reqParam, Map.class);

		Pages<BaseCredential> pages = this.parseToPage(credentialData.getData(), BaseCredential.class);

		return pages;
	}

}
