package com.reddate.did.sdk.service;

import java.math.BigInteger;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeoutException;

import cn.hutool.core.util.ObjectUtil;
import org.apache.commons.lang3.StringUtils;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.crypto.ECKeyPair;
import org.fisco.bcos.web3j.crypto.gm.GenCredential;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.reddate.did.sdk.constant.ErrorMessage;
import com.reddate.did.sdk.constant.ServiceURL;
import com.reddate.did.sdk.exception.DidException;
import com.reddate.did.sdk.param.CryptoType;
import com.reddate.did.sdk.param.req.DidSign;
import com.reddate.did.sdk.param.req.ResetDidAuth;
import com.reddate.did.sdk.param.resp.DidDataWrapper;
import com.reddate.did.sdk.param.resp.DocumentInfo;
import com.reddate.did.sdk.protocol.common.BaseDidDocument;
import com.reddate.did.sdk.protocol.common.DidDocument;
import com.reddate.did.sdk.protocol.common.KeyPair;
import com.reddate.did.sdk.protocol.common.Proof;
import com.reddate.did.sdk.protocol.request.DidDocSotreReq;
import com.reddate.did.sdk.protocol.request.DidDocumentReq;
import com.reddate.did.sdk.protocol.request.DidSignWrapper;
import com.reddate.did.sdk.protocol.request.RequestParam;
import com.reddate.did.sdk.protocol.request.ResetDidWrapper;
import com.reddate.did.sdk.protocol.response.BlockInfoResp;
import com.reddate.did.sdk.protocol.response.CreateDidData;
import com.reddate.did.sdk.protocol.response.ResultData;
import com.reddate.did.sdk.util.DidUtils;
import com.reddate.did.sdk.util.ECDSAUtils;
import com.reddate.did.sdk.util.HttpUtils;
import com.reddate.did.sdk.util.MnemonicUtil;
import com.reddate.did.sdk.util.Signatures;

/**
 * The did module implement class,
 * 
 * this class contain the generated did, store did document on chain, query did
 * document,reset did authority main key function implement
 */
public class DidService extends BaseService {

	private static final Logger logger = LoggerFactory.getLogger(DidService.class);

	public DidService(String url, String projectId, String token, CryptoType cryptoType) {
		super(url, projectId, token, cryptoType);
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
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "isStorageOnChain is empty");
		}
		ResultData<CreateDidData> createDoc = createDidDocument();
		if (!createDoc.isSuccess()) {
			return ResultData.error(createDoc.getCode(), createDoc.getMsg(), DidDataWrapper.class);
		}
		logger.debug("create did information is :" + JSONObject.toJSONString(createDoc));

		String didSign = ECDSAUtils.sign(createDoc.getData().getDid(),
				createDoc.getData().getAuthKeyInfo().getPrivateKey());

		if (isStorageOnChain) {
			storeDidDocumentOnChain(createDoc.getData().getDidDocument());
		}
		
		ECKeyPair ecKeyPair = GenCredential.createECDSAKeyPair(new BigInteger(createDoc.getData().getAuthKeyInfo().getPublicKey()).toString(16));
		Credentials credentials = GenCredential.create(ecKeyPair);
		String address = credentials.getAddress();
		
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
		dataWrapper.setDidSign(didSign);
		dataWrapper.setAddress(address);

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
			logger.error(e.getMessage(), e);
			return ResultData.error(ErrorMessage.GENERATE_DID_FAIL.getCode(),
					ErrorMessage.GENERATE_DID_FAIL.getMessage(), CreateDidData.class);
		} catch (Exception e) {
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
		if (StringUtils.isBlank(document.getDid())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "did is empty");
		}
		if (StringUtils.isBlank(document.getCreated())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "created is empty");
		}
		if (StringUtils.isBlank(document.getUpdated())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "updated is empty");
		}
		if (StringUtils.isBlank(document.getVersion())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "version is empty");
		}
		if (ObjectUtil.isEmpty(document.getRecovery())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "recovery is empty");
		}
		if (StringUtils.isBlank(document.getRecovery().getPublicKey())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "recovery.publicKey is empty");
		}
		if (StringUtils.isBlank(document.getRecovery().getType())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "recovery.type is empty");
		}
		if (ObjectUtil.isEmpty(document.getAuthentication())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "authentication is empty");
		}
		if (StringUtils.isBlank(document.getAuthentication().getPublicKey())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "authentication.publicKey is empty");
		}
		if (StringUtils.isBlank(document.getAuthentication().getType())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "authentication.type is empty");
		}
		if (ObjectUtil.isEmpty(document.getProof())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "proof is empty");
		}
		if (StringUtils.isBlank(document.getProof().getType())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "proof.type is empty");
		}
		if (StringUtils.isBlank(document.getProof().getCreator())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "proof.creator is empty");
		}
		if (StringUtils.isBlank(document.getProof().getSignatureValue())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "proof.signatureValue is empty");
		}
		RequestParam<DidDocSotreReq> reqParam = new RequestParam<>(this.getProjectId(), document.getDid());
		DidDocSotreReq didDocSotreReq = new DidDocSotreReq();
		didDocSotreReq.setDidDoc(document);
		reqParam.setData(didDocSotreReq);

		HttpUtils.postCall(this.getUrl() + ServiceURL.PUT_DID_ON_CHAIN, this.getToken(), reqParam, Boolean.class);

		return ResultData.success(null);
	}

	/**
	 * Verify the did document format and sign is correct or not
	 * 
	 * @param Did Document Did document content
	 * @return The verify result.
	 */
	public ResultData<Boolean> verifyDidDocument(DidDocument didDocument) {

		if (ObjectUtil.isEmpty(didDocument)) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "didDocument is empty");
		}
		if (StringUtils.isBlank(didDocument.getDid())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "did is empty");
		}
		if (StringUtils.isBlank(didDocument.getCreated())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "created is empty");
		}
		if (StringUtils.isBlank(didDocument.getUpdated())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "updated is empty");
		}
		if (StringUtils.isBlank(didDocument.getVersion())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "version is empty");
		}
		if (ObjectUtil.isEmpty(didDocument.getRecovery())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "recovery is empty");
		}
		if (StringUtils.isBlank(didDocument.getRecovery().getPublicKey())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "recovery.publicKey is empty");
		}
		if (StringUtils.isBlank(didDocument.getRecovery().getType())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "recovery.type is empty");
		}
		if (ObjectUtil.isEmpty(didDocument.getAuthentication())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "authentication is empty");
		}
		if (StringUtils.isBlank(didDocument.getAuthentication().getPublicKey())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "authentication.publicKey is empty");
		}
		if (StringUtils.isBlank(didDocument.getAuthentication().getType())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "authentication.type is empty");
		}
		if (ObjectUtil.isEmpty(didDocument.getProof())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "proof is empty");
		}
		if (StringUtils.isBlank(didDocument.getProof().getType())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "proof.type is empty");
		}
		if (StringUtils.isBlank(didDocument.getProof().getCreator())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "proof.creator is empty");
		}
		if (StringUtils.isBlank(didDocument.getProof().getSignatureValue())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "proof.signatureValue is empty");
		}
		RequestParam<DidDocSotreReq> reqParam = new RequestParam<>(this.getProjectId(), didDocument.getDid());
		DidDocSotreReq didDocSotreReq = new DidDocSotreReq();
		didDocSotreReq.setDidDoc(didDocument);
		reqParam.setData(didDocSotreReq);

		ResultData<Boolean> regResult = HttpUtils.postCall(this.getUrl() + ServiceURL.VERIFY_DID_DOCUMENT,
				this.getToken(), reqParam, Boolean.class);

		return ResultData.success(regResult.getData());
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
		if (StringUtils.isBlank(did)) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "did is empty");
		}
		RequestParam<DidDocumentReq> reqParam = new RequestParam<>(this.getProjectId(), did);
		DidDocumentReq didDocumentReq = new DidDocumentReq();
		didDocumentReq.setDid(did);
		reqParam.setData(didDocumentReq);
		ResultData<DidDocument> regResult = HttpUtils.postCall(this.getUrl() + ServiceURL.GET_DID_DOCUMENT,
				this.getToken(), reqParam, DidDocument.class);

		return ResultData.success(regResult.getData());
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
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "resetDidAuth is empty");
		}
		if (StringUtils.isBlank(resetDidAuth.getDid())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "did is empty");
		}

		if (ObjectUtil.isEmpty(resetDidAuth.getRecoveryKey())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "recoveryKey is empty");
		}
		if (ObjectUtil.isEmpty(resetDidAuth.getRecoveryKey().getPrivateKey())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "recoveryKey。privateKey is empty");
		}
		if (ObjectUtil.isEmpty(resetDidAuth.getRecoveryKey().getType())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "recoveryKey。type is empty");
		}
		if (ObjectUtil.isEmpty(resetDidAuth.getRecoveryKey().getPublicKey())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "recoveryKey。publicKey is empty");
		}
		ResultData<DidDocument> queryDidDocument = this.getDidDocument(resetDidAuth.getDid());
		if (!queryDidDocument.isSuccess()) {
			throw new DidException(queryDidDocument.getCode(), queryDidDocument.getMsg());
		}

		String recoveryPublicKey = ECDSAUtils.getPublicKey(resetDidAuth.getRecoveryKey().getPrivateKey());
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
			String publicKey = ECDSAUtils.getPublicKey(resetDidAuth.getPrimaryKeyPair().getPrivateKey());
			if (publicKey == null || !publicKey.equals(resetDidAuth.getPrimaryKeyPair().getPublicKey())) {
				throw new DidException(ErrorMessage.PRK_PUK_NOT_MATCH.getCode(),
						ErrorMessage.PRK_PUK_NOT_MATCH.getMessage());
			}
		}

		DidDocument newDidDocument = DidUtils.renewDidDocument(didDoc, keyPair);
		String signValue = ECDSAUtils.sign(JSONArray.toJSON(newDidDocument).toString(), keyPair.getPrivateKey());
		Proof proof = new Proof();
		proof.setType(ECDSAUtils.TYPE);
		proof.setCreator(newDidDocument.getDid());
		proof.setSignatureValue(signValue);
		newDidDocument.setProof(proof);
		String authPublicKeySign = ECDSAUtils.sign(recoveryPublicKey, resetDidAuth.getRecoveryKey().getPrivateKey());

		String signature = Signatures.get().setInfo(this.getProjectId(), didDoc.getDid())
				.add("document", newDidDocument).add("authPubKeySign", authPublicKeySign)
				.sign(resetDidAuth.getRecoveryKey().getPrivateKey());

		RequestParam<ResetDidWrapper> reqParam = new RequestParam<>(this.getProjectId(), newDidDocument.getDid());
		ResetDidWrapper resetDidWrapper = new ResetDidWrapper();
		resetDidWrapper.setDidDoc(didDoc);
		resetDidWrapper.setAuthPubKeySign(authPublicKeySign);
		reqParam.setData(resetDidWrapper);
		reqParam.setSign(signature);

		HttpUtils.postCall(this.getUrl() + ServiceURL.REST_DID_AUTH, this.getToken(), reqParam, KeyPair.class);

		return ResultData.success(keyPair);
	}

	public static KeyPair generalKeyPairByMnemonic(List<String> mnemList) {
//		if (mnemList == null || mnemList.size() != 12) {
//			throw new DidException(ErrorMessage.MNEM_NUMBER_INCORRECT.getCode(),
//					ErrorMessage.MNEM_NUMBER_INCORRECT.getMessage());
//		}

		if(mnemList == null || mnemList.isEmpty()) {
			throw new DidException(ErrorMessage.MNEM_IS_EMPTY.getCode(), ErrorMessage.MNEM_IS_EMPTY.getMessage());
		}
		
		for (int i = 0; i < mnemList.size(); i++) {
			String mnem = mnemList.get(i);
			if (mnem == null || mnem.trim().isEmpty()) {
				throw new DidException(ErrorMessage.MNEM_IS_EMPTY.getCode(), ErrorMessage.MNEM_IS_EMPTY.getMessage());
			}
			mnemList.set(i, mnem.trim());
		}

		KeyPair keyPair = MnemonicUtil.generalKeyPair(mnemList);
		return keyPair;
	}

	/**
	 * Query current block number and group id
	 * 
	 * @return return current block number and group id
	 */
	public BlockInfoResp getBlockInfo() {
		RequestParam<Object> reqParam = new RequestParam<>(this.getProjectId(), null);
		ResultData<BlockInfoResp> regResult = HttpUtils.postCall(this.getUrl() + ServiceURL.GET_BLOCK_INFO,
				this.getToken(), reqParam, BlockInfoResp.class);

		return regResult.getData();
	}

	/**
	 * 
	 * Verify the sign value of the did identify is correct or not  by the did identify related document's public key.
	 * 
	 *
	 * @param didSign the did identify and did identify's sign value
	 * @return Return the verify did identify result
	 */
	public ResultData<Boolean> verifyDIdSign(DidSign didSign) {
		if (didSign == null){
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(),"did and did sign is empty");
		}
		if (didSign.getDid() == null || didSign.getDid().trim().isEmpty()){
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(),"did is empty");
		}
		
		if (didSign.getDidSign() == null || didSign.getDidSign().trim().isEmpty()){
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(),"did sign is empty");
		}
		
		RequestParam<DidSignWrapper> reqParam = new RequestParam<>(this.getProjectId(),didSign.getDid());
		DidSignWrapper didSignWrapper = new DidSignWrapper();
		didSignWrapper.setDid(didSign.getDid());
		didSignWrapper.setDidSign(didSign.getDidSign());
		reqParam.setData(didSignWrapper);
		
		ResultData<Boolean> verifyResult = HttpUtils.postCall(this.getUrl()+ServiceURL.VERIFY_DID_SIGN,this.getToken(),reqParam, Boolean.class);
		return verifyResult;
	}
}
