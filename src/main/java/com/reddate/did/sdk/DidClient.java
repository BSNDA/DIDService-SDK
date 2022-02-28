package com.reddate.did.sdk;

import java.util.List;

import com.reddate.did.sdk.constant.ErrorMessage;
import com.reddate.did.sdk.exception.DidException;
import com.reddate.did.sdk.param.CryptoType;
import com.reddate.did.sdk.param.req.AuthIssuerList;
import com.reddate.did.sdk.param.req.CheckPermission;
import com.reddate.did.sdk.param.req.CreateCredential;
import com.reddate.did.sdk.param.req.CreatePermission;
import com.reddate.did.sdk.param.req.DeletePermission;
import com.reddate.did.sdk.param.req.QueryCptList;
import com.reddate.did.sdk.param.req.QueryCredentialList;
import com.reddate.did.sdk.param.req.QueryGrantedPermission;
import com.reddate.did.sdk.param.req.QueryPermission;
import com.reddate.did.sdk.param.req.QueryResourceHistory;
import com.reddate.did.sdk.param.req.RegisterAuthorityIssuer;
import com.reddate.did.sdk.param.req.RegisterCpt;
import com.reddate.did.sdk.param.req.ResetDidAuth;
import com.reddate.did.sdk.param.req.RevokeCredential;
import com.reddate.did.sdk.param.req.SaveResource;
import com.reddate.did.sdk.param.resp.DidDataWrapper;
import com.reddate.did.sdk.param.resp.GrantPermissionInfo;
import com.reddate.did.sdk.param.resp.PermissionInfo;
import com.reddate.did.sdk.param.resp.RegisterHubResult;
import com.reddate.did.sdk.param.resp.ResourceHistoryInfo;
import com.reddate.did.sdk.param.resp.SaveResourceResult;
import com.reddate.did.sdk.protocol.common.DidDocument;
import com.reddate.did.sdk.protocol.common.KeyPair;
import com.reddate.did.sdk.protocol.common.PublicKey;
import com.reddate.did.sdk.protocol.request.*;
import com.reddate.did.sdk.protocol.response.*;
import com.reddate.did.sdk.protocol.response.hub.CheckPermissionResp;
import com.reddate.did.sdk.protocol.response.hub.CreatePermissionResp;
import com.reddate.did.sdk.protocol.response.hub.DeletePermissionResp;
import com.reddate.did.sdk.protocol.response.hub.QueryResourceResp;
import com.reddate.did.sdk.service.AuthIssuerService;
import com.reddate.did.sdk.service.CredentialService;
import com.reddate.did.sdk.service.DidService;
import com.reddate.did.sdk.service.HubService;
import com.reddate.did.sdk.util.AesUtils;
import com.reddate.did.sdk.util.Secp256Util;

/**
 * Did SDK main class, all the BSN did service can be called by this class
 * method.
 * 
 * Before call BSN did service, you need create did client instance. for
 * example: String url = "https://didservice.bsngate.com:18602"; String token =
 * "3wxYHXwAm57grc9JUr2zrPHt9HC"; String projectId = "8320935187"; DidClient
 * didClient = new DidClient(url,projectId,token);
 */
public class DidClient {

	/**
	 * Did module service logic implement class
	 * 
	 */
	private DidService didService;

	/**
	 * Authenticate module service logic implement class
	 * 
	 */
	private AuthIssuerService authIssuerService;

	/**
	 * Credential module service logic implement class
	 * 
	 */
	private CredentialService credentialService;

	/**
	 * Identify HUB module service logic implement class
	 * 
	 */
	private HubService hubService;

	/**
	 * Did client construct
	 * 
	 * 
	 * @param url       BSN did service URL
	 * @param projectId The project Id of BSN assign
	 * @param token     The Token of BSN assign
	 */
	public DidClient(String url, String projectId, String token) {
		didService = new DidService(url, projectId, token);
		authIssuerService = new AuthIssuerService(url, projectId, token);
		credentialService = new CredentialService(url, projectId, token);
		hubService = new HubService(url, projectId, token);
	}

	/**
	 * Create did document and store this document on block chain if choose store on
	 * block chain.
	 * 
	 * @param isStorageOnChain Store generated did document store on block chain
	 * @return The did Identifier, generated did document and key pair.
	 */
	public DidDataWrapper createDid(Boolean isStorageOnChain) {
		ResultData<DidDataWrapper> genDidResult = null;
		try {
			genDidResult = didService.generateDid(isStorageOnChain);
		} catch (DidException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DidException(ErrorMessage.UNKNOWN_ERROR.getCode(), e.getMessage());
		}

		if (!genDidResult.isSuccess()) {
			throw new DidException(genDidResult.getCode(), genDidResult.getMsg());
		}

		return genDidResult.getData();
	}

	/**
	 * Verify the did document format and sign is correct or not, this function will
	 * verify the document format and the document sign.
	 * 
	 * @param didDocument Did document content
	 * @return The verify result.
	 */
	public Boolean verifyDidDocument(DidDocument didDocument) {
		ResultData<Boolean> verifyResult = null;
		try {
			verifyResult = didService.verifyDidDocument(didDocument);
		} catch (DidException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DidException(ErrorMessage.UNKNOWN_ERROR.getCode(), e.getMessage());
		}

		if (!verifyResult.isSuccess()) {
			throw new DidException(verifyResult.getCode(), verifyResult.getMsg());
		}
		return verifyResult.getData();
	}

	/**
	 * Storing a generated did document On-chain. this function verify the document
	 * content first, then store the document On-chain.
	 * 
	 * 
	 * @param didDocument The did document content
	 * @return document On-chain result
	 */
	public Boolean storeDidDocumentOnChain(DidDocument didDocument) {
		ResultData<Boolean> storeResult = null;
		try {
			storeResult = didService.storeDidDocumentOnChain(didDocument);
		} catch (DidException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DidException(ErrorMessage.UNKNOWN_ERROR.getCode(), e.getMessage());
		}

		if (!storeResult.isSuccess()) {
			throw new DidException(storeResult.getCode(), storeResult.getMsg());
		}
		return true;
	}

	/**
	 * Query the did document content on the block chain. The DID Document contains
	 * the user's DID identifier, generation time, update time, public key,
	 * encryption algorithm, signature information, etc.
	 * 
	 * @param did Did identify
	 * @return The did document detail information
	 */
	public DidDocument getDidDocument(String did) {
		ResultData<DidDocument> queryDidDocResult = null;
		try {
			queryDidDocResult = didService.getDidDocument(did);
		} catch (DidException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DidException(ErrorMessage.UNKNOWN_ERROR.getCode(), e.getMessage());
		}

		if (!queryDidDocResult.isSuccess()) {
			throw new DidException(queryDidDocResult.getCode(), queryDidDocResult.getMsg());
		}
		return queryDidDocResult.getData();
	}

	/**
	 * The user completes the master public and private key update through the
	 * recovery public and private keys. After the key is updated, the user's DID
	 * Document will also be updated, but the DID identifier will not change.
	 * 
	 * @param restDidAuth Rest the did document key information.
	 * @return Return the reset main public key result
	 */
	public KeyPair resetDidAuth(ResetDidAuth restDidAuth) {
		ResultData<KeyPair> restAuth = null;
		try {
			restAuth = didService.resetDidAuth(restDidAuth);
		} catch (DidException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DidException(ErrorMessage.UNKNOWN_ERROR.getCode(), e.getMessage());
		}

		if (!restAuth.isSuccess()) {
			throw new DidException(restAuth.getCode(), restAuth.getMsg());
		}

		return restAuth.getData();
	}

	/**
	 * 
	 * Register a did to be a authority issuer, this function validate the did is on
	 * block chain, and validate it is not a authority issuer, and register it to be
	 * a issuer.
	 * 
	 * 
	 * @param register Register authority issuer information.
	 * @return Return the register result
	 */
	public boolean registerAuthIssuer(RegisterAuthorityIssuer register) {
		try {
			return authIssuerService.registerAuthIssuer(register);
		} catch (DidException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DidException(ErrorMessage.UNKNOWN_ERROR.getCode(), e.getMessage());
		}
	}

	/**
	 * Query the registered authority issuer list form the block chain.
	 * 
	 * The issuer information is public on the chain, so anyone can find all the
	 * issuers, but the DID identifier of the issuer is not returned by default. At
	 * the same time, it also supports query through the DID identifier of the
	 * issuing party, and all the information on the chain of the issuing party can
	 * be found.
	 * 
	 * @param query The page info and did identify
	 * @return return the authority list
	 */
	public Pages<AuthorityIssuer> queryAuthIssuerList(AuthIssuerList query) {
		try {
			return authIssuerService.queryAuthIssuerList(query);
		} catch (DidException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DidException(ErrorMessage.UNKNOWN_ERROR.getCode(), e.getMessage());
		}
	}

	/**
	 * Register a CPT template for the authority issuer, this function validate the
	 * authority issuer and CPT template information first, then add this CPT
	 * template on the block chain.
	 * 
	 * 
	 * @param registerCpt Register CPT template information
	 * @return Return the CPT template Id,and CPT template version
	 */
	public CptBaseInfo registerCpt(RegisterCpt registerCpt) {
		try {
			return authIssuerService.registerCpt(registerCpt);
		} catch (DidException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DidException(ErrorMessage.UNKNOWN_ERROR.getCode(), e.getMessage());
		}
	}

	/**
	 * Query all registered CPT templates under the issuer according to the DID
	 * identifier of the issuer.
	 * 
	 * @param query Page information and authority issuer
	 * @return Return the CPT template list
	 */
	public Pages<CptInfo> queryCptListByDid(QueryCptList query) {
		Pages<CptInfo> cptPages = null;
		try {
			cptPages = authIssuerService.queryCptListByDid(query);
		} catch (DidException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DidException(ErrorMessage.UNKNOWN_ERROR.getCode(), e.getMessage());
		}

		return cptPages;
	}

	/**
	 * Query CPT declaration information based on the CPT template ID.
	 * 
	 * @param cptId The CPT template Id
	 * @return Return the CPT template detail information
	 */
	public CptInfo queryCptById(Long cptId) {
		try {
			return authIssuerService.queryCptById(cptId);
		} catch (DidException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DidException(ErrorMessage.UNKNOWN_ERROR.getCode(), e.getMessage());
		}
	}

	/**
	 * The issuer updates the declaration content in the CPT template that it has
	 * already registered.
	 * 
	 * @param registerCpt Update CPT template information
	 * @return Return the new CPT template Id and version
	 */
	public CptBaseInfo updateCpt(RegisterCpt registerCpt) {
		try {
			return authIssuerService.updateCpt(registerCpt);
		} catch (DidException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DidException(ErrorMessage.UNKNOWN_ERROR.getCode(), e.getMessage());
		}
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
		try {
			return credentialService.createCredential(createCredential);
		} catch (DidException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DidException(ErrorMessage.UNKNOWN_ERROR.getCode(), e.getMessage());
		}
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
		try {
			return credentialService.verifyCredential(createCredential, publicKey);
		} catch (DidException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DidException(ErrorMessage.UNKNOWN_ERROR.getCode(), e.getMessage());
		}
	}

	/**
	 * 
	 * revoke issued credential on block chain, add this credential to the revoke
	 * credential list on block chain.
	 * 
	 * 
	 * @param cred Want to revoke credential information
	 * @return Return the revoke credential result
	 */
	public Boolean revokeCredential(RevokeCredential cred) {
		try {
			return credentialService.revokeCredential(cred);
		} catch (DidException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DidException(ErrorMessage.UNKNOWN_ERROR.getCode(), e.getMessage());
		}
	}

	/**
	 * Query the revoked credential and and the revocation time, can query by the
	 * crednential's issuer did, also can query by the credential Id
	 * 
	 * @param queryCredentialList Paging information and authority issuer did
	 * @return Return the credential List
	 */
	public Pages<BaseCredential> getRevokedCredList(QueryCredentialList queryCredentialList) {
		try {
			return credentialService.getRevokedCredList(queryCredentialList);
		} catch (DidException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DidException(ErrorMessage.UNKNOWN_ERROR.getCode(), e.getMessage());
		}
	}

	/**
	 * Register and did user to be a identify hub user.
	 * 
	 * @param did       The register did user
	 * @param publicKey This did user's public key
	 * @return Return the register result
	 */
	public RegisterHubResult registerHub(String did, String publicKey) {
		try {
			return hubService.registerHub(did, publicKey);
		} catch (DidException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DidException(ErrorMessage.UNKNOWN_ERROR.getCode(), e.getMessage());
		}
	}

	/**
	 * Save one resource to the identify hub, this function validate the user
	 * permission first, then save the resource to the identify hub if have
	 * permission
	 * 
	 * @param saveResource Save resource detail information
	 * @return Return the saved resource and encrypt Key
	 */
	public SaveResourceResult saveResource(SaveResource saveResource) {
		try {
			return hubService.saveResource(saveResource);
		} catch (DidException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DidException(ErrorMessage.UNKNOWN_ERROR.getCode(), e.getMessage());
		}
	}

	/**
	 * Query the saved resource in the identify hub, return the saved resource
	 * information
	 * 
	 * @param did        The identify hub user's did
	 * @param privateKey The identify hub user's private key
	 * @param url        The resource URL in identify hub
	 * @return Return the resource encrypt content and encrypt Key
	 */
	public QueryResourceResp getResource(String did, String privateKey, String url) {
		try {
			return hubService.getResource(did, privateKey, url);
		} catch (DidException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DidException(ErrorMessage.UNKNOWN_ERROR.getCode(), e.getMessage());
		}
	}

	/**
	 * Delete the resource in the identify hub, this function validate the identify
	 * hub user's permission first, if this user have permission, then delete this
	 * permission.
	 * 
	 * @param did        The identify hub user's did
	 * @param privateKey The identify hub user's private key
	 * @param url        The resource user in identify hub
	 * @return Return the delete result
	 */
	public Boolean deleteResource(String did, String privateKey, String url) {
		try {
			return hubService.deleteResource(did, privateKey, url);

		} catch (DidException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DidException(ErrorMessage.UNKNOWN_ERROR.getCode(), e.getMessage());
		}
	}

	/**
	 * Resource owner creates permission to access dataHub for other user.
	 * 
	 * One user can only request the resource owner add grant permission, and only
	 * the resource owner can grant permission to other user.
	 * 
	 * @param createPermission Create permission information
	 * @return Return the crate permission information
	 */
	public CreatePermissionResp createPermission(CreatePermission createPermission) {
		try {
			return hubService.createPermission(createPermission);
		} catch (DidException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DidException(ErrorMessage.UNKNOWN_ERROR.getCode(), e.getMessage());
		}
	}

	/**
	 * The user logically deletes the grant that has been created but has not been
	 * used.
	 * 
	 * Deleting permission can only be invoked by the resource owner. the permission
	 * record that has not been used is logically deleted.
	 * 
	 * @param deletePermission Permission Information
	 * @return Return the delete permission information
	 */
	public DeletePermissionResp deletePermission(DeletePermission deletePermission) {
		try {
			return hubService.deletePermission(deletePermission);
		} catch (DidException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DidException(ErrorMessage.UNKNOWN_ERROR.getCode(), e.getMessage());
		}
	}

	/**
	 * Users query the permissions of authorized third-party visitors.
	 * 
	 * Users can query all or part of the grant records from the grant's hubId and
	 * whether they have used.
	 * 
	 * @param queryPermission Query permission condition
	 * @return Return the permission Information list
	 */
	public List<PermissionInfo> queryPermission(QueryPermission queryPermission) {
		try {
			return hubService.queryPermission(queryPermission);
		} catch (DidException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DidException(ErrorMessage.UNKNOWN_ERROR.getCode(), e.getMessage());
		}
	}

	/**
	 * Query the granted permission information list to yourself
	 * 
	 * Users can query all or part of the authorization records from the ownner's
	 * hubId and whether they have used.
	 * 
	 * @param queryPermission Query permission condition
	 * @return Return the permission Information list
	 */
	public List<GrantPermissionInfo> queryGrantedPermission(QueryGrantedPermission queryPermission) {
		try {
			return hubService.queryGrantPermission(queryPermission);
		} catch (DidException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DidException(ErrorMessage.UNKNOWN_ERROR.getCode(), e.getMessage());
		}
	}

	/**
	 * Check if one hub user have WREAD/WRITE/UPDATE/DELTE permission for a
	 * resource. The resource owner have the resource WREAD/WRITE/UPDATE/DELTE
	 * permission. THe granted permission user have the granted user.
	 * 
	 * @param check Permission permission and resource information
	 * @return Return the check permission result
	 */
	public CheckPermissionResp checkPermission(CheckPermission checkPermission) {
		try {
			return hubService.checkPermission(checkPermission);
		} catch (DidException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DidException(ErrorMessage.UNKNOWN_ERROR.getCode(), e.getMessage());
		}
	}

	/**
	 * Query the modification history operation records of your own resources,
	 * including save, update, and delete operations history.
	 * 
	 * @param queryResourceHistory Query resource history request param
	 * @return Return the resource operation history Information list
	 */
	public List<ResourceHistoryInfo> queryResourceHistory(QueryResourceHistory queryResourceHistory) {
		try {
			return hubService.queryResourceHistory(queryResourceHistory);
		} catch (DidException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DidException(ErrorMessage.UNKNOWN_ERROR.getCode(), e.getMessage());
		}
	}

	/**
	 * Decrypt ciphertext secret key and ciphertext content, decrypt the secret key
	 * by Secp256k1 algorithm, decrypt the content by the AES-ECDSA algorithm.
	 * 
	 * @param content    the ciphertext content
	 * @param encptyKey  the ciphertext secret key
	 * @param privateKey the private key
	 * @return return the plaintext content
	 */
	public static String decrypt(String content, String encptyKey, String privateKey) {
		String key = null;
		try {
			key = Secp256Util.decrypt(CryptoType.ECDSA, encptyKey, privateKey);
		} catch (Exception e) {
			// throw new DidException(ErrorMessage.UNKNOWN_ERROR.getCode(), e.getMessage());
			throw new DidException(ErrorMessage.DECRYPT_KEY_FAILED.getCode(),
					ErrorMessage.DECRYPT_KEY_FAILED.getMessage() + ": " + e.getMessage());
		}
		try {
			return AesUtils.decrypt(content, key);
		} catch (Exception e) {
			// throw new DidException(ErrorMessage.UNKNOWN_ERROR.getCode(), e.getMessage());
			throw new DidException(ErrorMessage.DECRYPT_CONTENT_FAILED.getCode(),
					ErrorMessage.DECRYPT_CONTENT_FAILED.getMessage() + ": " + e.getMessage());
		}
	}
}
