package com.reddate.did.sdk.service;

import java.util.Objects;
import java.util.concurrent.TimeoutException;

import cn.hutool.core.util.ObjectUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.reddate.did.sdk.constant.ErrorMessage;
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
 * The did module implement class,
 * 
 * this class contain the generated did, store did document on chain, query did
 * document,reset did authority main key function implement
 */
public class DidService extends BaseService {

	private static final Logger logger = LoggerFactory.getLogger(DidService.class);

	public DidService(String url, String projectId, String token) {
		super(url, projectId, token);
	}

	/**
	 * Create did document, store this document on block chain if choose store on
	 * block chain.
	 * 
	 * @param isStorageOnChain true: On-chain, false: not On-chain
	 * @return The did Identifier, generated did document and key pair.
	 * 
	 */
	public ResultData<DidDataWrapper> generateDid(Boolean isStorageOnChain) {
		if (isStorageOnChain == null) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "isStorageOnChain can not empty");
		}
		ResultData<CreateDidData> createDoc = createDidDocument();
		if (!createDoc.isSuccess()) {
			return ResultData.error(createDoc.getCode(), createDoc.getMsg(), DidDataWrapper.class);
		}
		logger.debug("create did information is :" + JSONObject.toJSONString(createDoc));

		if (isStorageOnChain) {
			ResultData<Boolean> sotreDoc = storeDidDocumentOnChain(createDoc.getData().getDidDocument());
			if (!sotreDoc.isSuccess()) {
				String msg = sotreDoc.getMsg();
				if (msg == null || msg.trim().isEmpty()) {
					msg = "document On-chain failed";
				}
				return ResultData.error(sotreDoc.getCode(), msg, DidDataWrapper.class);
			}
		}

		DidDataWrapper dataWrapper = new DidDataWrapper();
		dataWrapper.setDid(createDoc.getData().getDid());
		dataWrapper.setAuthKeyInfo(createDoc.getData().getAuthKeyInfo());
		dataWrapper.setRecyKeyInfo(createDoc.getData().getRecyKeyInfo());
		DocumentInfo documentInfo = new DocumentInfo();
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
	 * Generated one did document
	 * 
	 * @return The did Identifier, generated did document and key pair.
	 * 
	 */
	private ResultData<CreateDidData> createDidDocument() {
		try {
			KeyPair authKeyPair = ECDSAUtils.createKey();
			KeyPair recyKeyPair = ECDSAUtils.createKey();
			if (StringUtils.isBlank(authKeyPair.getPublicKey()) || StringUtils.isBlank(authKeyPair.getPrivateKey())
					|| StringUtils.isBlank(recyKeyPair.getPublicKey())
					|| StringUtils.isBlank(recyKeyPair.getPrivateKey())) {
				throw new DidException(ErrorMessage.CREAT_KEY_FAIL.getCode(), ErrorMessage.CREAT_KEY_FAIL.getMessage());
			}

			BaseDidDocument baseDidDocument = DidUtils.generateBaseDidDocument(authKeyPair, recyKeyPair);

			String didIdentifier = DidUtils.generateDidIdentifierByBaseDidDocument(baseDidDocument);
			if (StringUtils.isBlank(didIdentifier)) {
				throw new DidException(ErrorMessage.HASH_DID_FAIL.getCode(), ErrorMessage.HASH_DID_FAIL.getMessage());
			}

			String did = DidUtils.generateDidByDidIdentifier(didIdentifier);
			if (StringUtils.isBlank(did)) {
				throw new DidException(ErrorMessage.HASH_DID_FAIL.getCode(), ErrorMessage.HASH_DID_FAIL.getMessage());
			}

			DidDocument didDocument = DidUtils.generateDidDocument(authKeyPair, recyKeyPair, did);
			if (Objects.isNull(didDocument)) {
				throw new DidException(ErrorMessage.CREATE_DID_DOC_FAIL.getCode(),
						ErrorMessage.CREATE_DID_DOC_FAIL.getMessage());
			}

			String signValue = ECDSAUtils.sign(JSONArray.toJSON(didDocument).toString(), authKeyPair.getPrivateKey());
			if (StringUtils.isBlank(signValue)) {
				throw new DidException(ErrorMessage.SIGN_DID_FAIL.getCode(), ErrorMessage.SIGN_DID_FAIL.getMessage());
			}

			Proof proof = new Proof();
			proof.setType(ECDSAUtils.TYPE);
			proof.setCreator(did);
			proof.setSignatureValue(signValue);
			didDocument.setProof(proof);

			CreateDidData createDidData = new CreateDidData();
			createDidData.setDid(did);
			createDidData.setAuthKeyInfo(authKeyPair);
			createDidData.setRecyKeyInfo(recyKeyPair);
			createDidData.setDidDocument(didDocument);
			return ResultData.success(createDidData);
		} catch (DidException e) {
			throw e;
		} catch (TimeoutException e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return ResultData.error(ErrorMessage.GENERATE_DID_FAIL.getCode(),
					ErrorMessage.GENERATE_DID_FAIL.getMessage(), CreateDidData.class);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return ResultData.error(ErrorMessage.GENERATE_DID_FAIL.getCode(),
					ErrorMessage.GENERATE_DID_FAIL.getMessage(), CreateDidData.class);
		}
	}

	/**
	 * Store this did document to block chain by request the did service
	 * 
	 * @param document The did document
	 * @return On chain result
	 */
	public ResultData<Boolean> storeDidDocumentOnChain(DidDocument document) {
		if (ObjectUtil.isEmpty(document)) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "document is empty");
		}
		if (StringUtils.isEmpty(document.getDid())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "did is empty");
		}
		if (StringUtils.isEmpty(document.getCreated())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "created is empty");
		}
		if (StringUtils.isEmpty(document.getUpdated())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "updated is empty");
		}
		if (StringUtils.isEmpty(document.getVersion())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "version is empty");
		}
		if (ObjectUtil.isEmpty(document.getRecovery())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "recovery is empty");
		}
		if (StringUtils.isEmpty(document.getRecovery().getPublicKey())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "recovery publicKey is empty");
		}
		if (StringUtils.isEmpty(document.getRecovery().getType())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "recovery type is empty");
		}
		if (ObjectUtil.isEmpty(document.getAuthentication())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "authentication is empty");
		}
		if (StringUtils.isEmpty(document.getAuthentication().getPublicKey())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "authentication publicKey is empty");
		}
		if (StringUtils.isEmpty(document.getAuthentication().getType())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "authentication type is empty");
		}
		if (ObjectUtil.isEmpty(document.getProof())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "proof is empty");
		}
		if (StringUtils.isEmpty(document.getProof().getType())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "proof type is empty");
		}
		if (StringUtils.isEmpty(document.getProof().getCreator())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "proof creator is empty");
		}
		if (StringUtils.isEmpty(document.getProof().getSignatureValue())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "proof signatureValue is empty");
		}
		RequestParam<DidDocSotreReq> reqParam = new RequestParam<>(this.getProjectId(), document.getDid());
		DidDocSotreReq didDocSotreReq = new DidDocSotreReq();
		didDocSotreReq.setDidDoc(document);
		reqParam.setData(didDocSotreReq);

		ResultData<Boolean> regResult = HttpUtils.postCall(this.getUrl() + ServiceURL.PUT_DID_ON_CHAIN, this.getToken(),
				reqParam, Boolean.class);
		if (regResult.isSuccess()) {
			return ResultData.success(null);
		} else {
			return ResultData.error(regResult.getCode(), regResult.getMsg(), Boolean.class);
		}
	}

	/**
	 * Verify the did document format and sign is correct or not
	 * 
	 * @param Did Document Did document content
	 * @return The verify result.
	 */
	public ResultData<Boolean> verifyDidDocument(DidDocument didDocument) {

		if (ObjectUtil.isEmpty(didDocument)) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "document is empty");
		}
		if (StringUtils.isEmpty(didDocument.getDid())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "did is empty");
		}
		if (StringUtils.isEmpty(didDocument.getCreated())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "created is empty");
		}
		if (StringUtils.isEmpty(didDocument.getUpdated())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "updated is empty");
		}
		if (StringUtils.isEmpty(didDocument.getVersion())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "version is empty");
		}
		if (ObjectUtil.isEmpty(didDocument.getRecovery())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "recovery is empty");
		}
		if (StringUtils.isEmpty(didDocument.getRecovery().getPublicKey())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "recovery publicKey is empty");
		}
		if (StringUtils.isEmpty(didDocument.getRecovery().getType())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "recovery type is empty");
		}
		if (ObjectUtil.isEmpty(didDocument.getAuthentication())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "authentication is empty");
		}
		if (StringUtils.isEmpty(didDocument.getAuthentication().getPublicKey())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "authentication publicKey is empty");
		}
		if (StringUtils.isEmpty(didDocument.getAuthentication().getType())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "authentication type is empty");
		}
		if (ObjectUtil.isEmpty(didDocument.getProof())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "proof is empty");
		}
		if (StringUtils.isEmpty(didDocument.getProof().getType())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "proof type is empty");
		}
		if (StringUtils.isEmpty(didDocument.getProof().getCreator())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "proof creator is empty");
		}
		if (StringUtils.isEmpty(didDocument.getProof().getSignatureValue())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "proof signatureValue is empty");
		}
		RequestParam<DidDocSotreReq> reqParam = new RequestParam<>(this.getProjectId(), didDocument.getDid());
		DidDocSotreReq didDocSotreReq = new DidDocSotreReq();
		didDocSotreReq.setDidDoc(didDocument);
		reqParam.setData(didDocSotreReq);

		ResultData<Boolean> regResult = HttpUtils.postCall(this.getUrl() + ServiceURL.VERIFY_DID_DOCUMENT,
				this.getToken(), reqParam, Boolean.class);
		if (regResult.isSuccess()) {
			return ResultData.success(regResult.getData());
		} else {
			return ResultData.error(regResult.getCode(), regResult.getMsg(), Boolean.class);
		}
	}

	/**
	 * Query the did document content on the block chain. The DID Document contains
	 * the user's DID identifier, generation time, update time, public key,
	 * encryption algorithm, signature information, etc.
	 * 
	 * @param did Did identify
	 * @return The did document detail information
	 */
	public ResultData<DidDocument> getDidDocument(String did) {
		if (StringUtils.isEmpty(did)) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "did is empty");
		}
		RequestParam<DidDocumentReq> reqParam = new RequestParam<>(this.getProjectId(), did);
		DidDocumentReq didDocumentReq = new DidDocumentReq();
		didDocumentReq.setDid(did);
		reqParam.setData(didDocumentReq);
		ResultData<DidDocument> regResult = HttpUtils.postCall(this.getUrl() + ServiceURL.GET_DID_DOCUMENT,
				this.getToken(), reqParam, DidDocument.class);

		if (regResult.isSuccess()) {
			return ResultData.success(regResult.getData());
		} else {
			return ResultData.error(regResult.getCode(), regResult.getMsg(), DidDocument.class);
		}
	}

	/**
	 * The user completes the master public and private key update through the
	 * recovery public and private keys. After the key is updated, the user's DID
	 * Document will also be updated, but the DID identifier will not change.
	 * 
	 * @param restDidAuth Rest the did document key information.
	 * @return Return the reset main public key result
	 */
	public ResultData<KeyPair> resetDidAuth(ResetDidAuth resetDidAuth) throws Exception {
		if (ObjectUtil.isEmpty(resetDidAuth)) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "reset did auth is empty");
		}
		if (StringUtils.isEmpty(resetDidAuth.getDid())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "did is empty");
		}

		if (ObjectUtil.isEmpty(resetDidAuth.getRecoveryKey())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "recovery key is empty");
		}
		if (ObjectUtil.isEmpty(resetDidAuth.getRecoveryKey().getPrivateKey())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "private key is empty");
		}
		if (ObjectUtil.isEmpty(resetDidAuth.getRecoveryKey().getType())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "type key is empty");
		}
		if (ObjectUtil.isEmpty(resetDidAuth.getRecoveryKey().getPublicKey())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "public key is empty");
		}
		ResultData<DidDocument> queryDidDocument = null;
		try {
			queryDidDocument = this.getDidDocument(resetDidAuth.getDid());
		} catch (DidException e1) {
			throw new DidException(e1.getCode(), e1.getMessage());
		} catch (Exception e1) {
			throw new DidException(ErrorMessage.UNKNOWN_ERROR.getCode(),
					"query did document on chian failed: " + e1.getMessage());
		}
		if (!queryDidDocument.isSuccess()) {
			throw new DidException(queryDidDocument.getCode(), queryDidDocument.getMsg());
		}

		String recoveryPublicKey = null;
		try {
			recoveryPublicKey = ECDSAUtils.getPublicKey(resetDidAuth.getRecoveryKey().getPrivateKey());
		} catch (Exception e2) {
			e2.printStackTrace();
		}

		if (recoveryPublicKey == null
				|| !recoveryPublicKey.equals(queryDidDocument.getData().getRecovery().getPublicKey())) {
			throw new DidException(ErrorMessage.RECOVERY_KEY_INCORRECT.getCode(),
					ErrorMessage.RECOVERY_KEY_INCORRECT.getMessage());
		}

		DidDocument didDoc = queryDidDocument.getData();
		KeyPair keyPair = resetDidAuth.getPrimaryKeyPair();
		if (resetDidAuth.getPrimaryKeyPair() == null || resetDidAuth.getPrimaryKeyPair().getPrivateKey() == null
				|| resetDidAuth.getPrimaryKeyPair().getPrivateKey().trim().isEmpty()
				|| resetDidAuth.getPrimaryKeyPair().getPublicKey() == null
				|| resetDidAuth.getPrimaryKeyPair().getPublicKey().trim().isEmpty()
				|| resetDidAuth.getPrimaryKeyPair().getType() == null
				|| resetDidAuth.getPrimaryKeyPair().getType().trim().isEmpty()) {
			keyPair = ECDSAUtils.createKey();
		} else {
			String publicKey = null;
			try {
				publicKey = ECDSAUtils.getPublicKey(resetDidAuth.getPrimaryKeyPair().getPrivateKey());
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (publicKey == null || !publicKey.equals(resetDidAuth.getPrimaryKeyPair().getPublicKey())) {
				throw new DidException(ErrorMessage.PRK_PUK_NOT_MATCH.getCode(),
						ErrorMessage.PRK_PUK_NOT_MATCH.getMessage());
			}
		}

		DidDocument newDidDocument = DidUtils.renewDidDocument(didDoc, keyPair);
		String signValue = ECDSAUtils.sign(JSONArray.toJSON(newDidDocument).toString(), keyPair.getPrivateKey());
		if (StringUtils.isBlank(signValue)) {
			throw new DidException(ErrorMessage.SIGNATURE_FAILED.getCode(), ErrorMessage.SIGNATURE_FAILED.getMessage());
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
			throw new DidException(ErrorMessage.SIGNATURE_FAILED.getCode(), ErrorMessage.SIGNATURE_FAILED.getMessage());
		}

		String signature = com.reddate.did.sdk.util.Signatures.get().setInfo(this.getProjectId(), didDoc.getDid())
				.add("document", newDidDocument).add("authPubKeySign", authPublicKeySign)
				.sign(resetDidAuth.getRecoveryKey().getPrivateKey());

		RequestParam<ResetDidWrapper> reqParam = new RequestParam<>(this.getProjectId(), newDidDocument.getDid());
		ResetDidWrapper resetDidWrapper = new ResetDidWrapper();
		resetDidWrapper.setDidDoc(didDoc);
		resetDidWrapper.setAuthPubKeySign(authPublicKeySign);
		reqParam.setData(resetDidWrapper);
		reqParam.setSign(signature);

		ResultData<KeyPair> restAuthResult = HttpUtils.postCall(this.getUrl() + ServiceURL.REST_DID_AUTH,
				this.getToken(), reqParam, KeyPair.class);
		if (restAuthResult.isSuccess()) {
			return ResultData.success(keyPair);
		} else {
			return ResultData.error(restAuthResult.getCode(), restAuthResult.getMsg(), KeyPair.class);
		}
	}

}
