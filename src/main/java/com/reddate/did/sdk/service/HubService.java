package com.reddate.did.sdk.service;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.reddate.did.sdk.constant.ErrorMessage;
import com.reddate.did.sdk.constant.ServiceURL;
import com.reddate.did.sdk.exception.DidException;
import com.reddate.did.sdk.param.CryptoType;
import com.reddate.did.sdk.param.req.CheckPermission;
import com.reddate.did.sdk.param.req.CreatePermission;
import com.reddate.did.sdk.param.req.DeletePermission;
import com.reddate.did.sdk.param.req.Operation;
import com.reddate.did.sdk.param.req.QueryGrantedPermission;
import com.reddate.did.sdk.param.req.QueryPermission;
import com.reddate.did.sdk.param.req.QueryResourceHistory;
import com.reddate.did.sdk.param.req.SaveResource;
import com.reddate.did.sdk.param.req.TransferOwner;
import com.reddate.did.sdk.param.resp.GrantPermissionInfo;
import com.reddate.did.sdk.param.resp.PermissionInfo;
import com.reddate.did.sdk.param.resp.RegisterHubResult;
import com.reddate.did.sdk.param.resp.ResourceHistoryInfo;
import com.reddate.did.sdk.param.resp.SaveResourceResult;
import com.reddate.did.sdk.protocol.request.RequestParam;
import com.reddate.did.sdk.protocol.request.hub.CheckPermissionReq;
import com.reddate.did.sdk.protocol.request.hub.CreatePermissionReq;
import com.reddate.did.sdk.protocol.request.hub.DeletePermissionReq;
import com.reddate.did.sdk.protocol.request.hub.DeleteResourceReq;
import com.reddate.did.sdk.protocol.request.hub.QueryCryptoTypeReq;
import com.reddate.did.sdk.protocol.request.hub.QueryGrantedPermissionReq;
import com.reddate.did.sdk.protocol.request.hub.QueryPermissionReq;
import com.reddate.did.sdk.protocol.request.hub.QueryResourceHistoryReq;
import com.reddate.did.sdk.protocol.request.hub.QueryResourceReq;
import com.reddate.did.sdk.protocol.request.hub.RegisterHubByIdPublickeyReq;
import com.reddate.did.sdk.protocol.request.hub.RegisterHubReq;
import com.reddate.did.sdk.protocol.request.hub.SaveResourceReq;
import com.reddate.did.sdk.protocol.request.hub.TransferOwnerReq;
import com.reddate.did.sdk.protocol.response.QueryCryptoTypeResp;
import com.reddate.did.sdk.protocol.response.ResultData;
import com.reddate.did.sdk.protocol.response.hub.RegisterHubResp;
import com.reddate.did.sdk.protocol.response.hub.SaveResourceResp;
import com.reddate.did.sdk.protocol.response.hub.CheckPermissionResp;
import com.reddate.did.sdk.protocol.response.hub.CreatePermissionResp;
import com.reddate.did.sdk.protocol.response.hub.DeletePermissionResp;
import com.reddate.did.sdk.protocol.response.hub.QueryResourceResp;
import com.reddate.did.sdk.util.AesUtils;
import com.reddate.did.sdk.util.HttpUtils;
import com.reddate.did.sdk.util.Secp256Util;
import com.reddate.did.sdk.util.Signatures;
import org.apache.commons.lang3.StringUtils;

/**
 * The identify hub service implement, contain save resource ,update resource
 * ,delete resource, read resource crate permission, query permission,delete
 * permission and so an function implement
 */
public class HubService extends BaseService {

	public HubService(String url, String projectId, String token, CryptoType cryptoType) {
		super(url, projectId, token, cryptoType);
	}

	/**
	 * Register the did user to be a identify hub user.
	 * 
	 * @param did The register did user
	 * @return Return the register result
	 */
	public RegisterHubResult registerHub(String did) {
		if (StringUtils.isBlank(did)) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "did is empty");
		}
		RequestParam<RegisterHubReq> reqParam = new RequestParam<>(this.getProjectId(), did);
		RegisterHubReq registerHubReq = new RegisterHubReq();
		registerHubReq.setDid(did);
		reqParam.setData(registerHubReq);

		ResultData<RegisterHubResp> regResult = HttpUtils.postCall(this.getUrl() + ServiceURL.HUB_REGISTER,
				this.getToken(), reqParam, RegisterHubResp.class);

		RegisterHubResult registerHubResult = new RegisterHubResult();
		registerHubResult.setUid(regResult.getData().getUid());
		registerHubResult.setSuccess(regResult.getData().isSuccess());
		registerHubResult.setMessage(regResult.getData().getMessage());

		return registerHubResult;
	}

	/**
	 * Register a identify hub user by the public key.
	 * 
	 * @param id        user Id
	 * @param publicKey decimal public key String
	 * @param cryptoType encryption Algorithm 
	 * @return Return the register result
	 */
	public RegisterHubResult registerHubByIdPublicKey(String id, String publicKey,CryptoType cryptoType) {
		if (StringUtils.isBlank(publicKey)) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "publicKey is empty");
		}
		
		if(cryptoType == null) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "cryptoType is empty");
		}
		
		RequestParam<RegisterHubByIdPublickeyReq> reqParam = new RequestParam<>(this.getProjectId(), null);
		RegisterHubByIdPublickeyReq registerHubReq = new RegisterHubByIdPublickeyReq();
		registerHubReq.setPublicKey(publicKey);
		registerHubReq.setCryptoType(cryptoType.name());
		if (id != null && !id.trim().isEmpty()) {
			registerHubReq.setId(id.trim());
		}
		reqParam.setData(registerHubReq);

		ResultData<RegisterHubResp> regResult = HttpUtils.postCall(
				this.getUrl() + ServiceURL.HUB_REGISTER_BY_PUBLIOCKEY, this.getToken(), reqParam,
				RegisterHubResp.class);

		RegisterHubResult registerHubResult = new RegisterHubResult();
		registerHubResult.setUid(regResult.getData().getUid());
		registerHubResult.setSuccess(regResult.getData().isSuccess());
		registerHubResult.setMessage(regResult.getData().getMessage());

		return registerHubResult;
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
		if (saveResource == null) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "saveResource is empty");
		}
		if (StringUtils.isBlank(saveResource.getUid())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "uid is empty");
		}
		if (StringUtils.isBlank(saveResource.getContent())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "content is empty");
		}
		if (StringUtils.isBlank(saveResource.getOwnerUid())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "ownerUid is empty");
		}
		if (saveResource.getGrant() == null) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "grant is empty");
		}
		if (saveResource.getGrant() != Operation.WRITE) {
			if (StringUtils.isBlank(saveResource.getUrl())) {
				throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "url is empty");
			}
		}
		if (StringUtils.isBlank(saveResource.getPrivateKey())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "privateKey is empty");
		}

		String encpyResourceKey = null;
		String resourceKey = null;
		if ((saveResource.getGrant() == Operation.WRITE || saveResource.getGrant() == Operation.UPDATE)
				&& !saveResource.getOwnerUid().equals(saveResource.getUid())) {
			CheckPermission checkPermission = new CheckPermission();
			checkPermission.setUid(saveResource.getUid());
			checkPermission.setPrivateKey(saveResource.getPrivateKey());
			checkPermission.setUrl(saveResource.getUrl());
			checkPermission.setGrant(saveResource.getGrant());
			checkPermission.setOwnerUid(saveResource.getOwnerUid());
			checkPermission.setGrantUid(saveResource.getUid());
			CheckPermissionResp checkPermissionResp = this.checkPermission(checkPermission);
			if (!checkPermissionResp.isSucces()) {
				String msg = "";
				String checkMessage = checkPermissionResp.getMessage();
				if (checkMessage != null) {
					if (checkMessage.contains("-")) {
						String[] tmp = checkMessage.split("-");
						msg = tmp[1].trim();
					} else {
						msg = checkMessage;
					}
				}
				throw new DidException(ErrorMessage.QUERY_GRANT_ENCPY_KEY_FAILED.getCode(),
						ErrorMessage.QUERY_GRANT_ENCPY_KEY_FAILED.getMessage() + ": " + msg);
			}
			encpyResourceKey = checkPermissionResp.getKey();
			resourceKey = Secp256Util.decrypt(cryptoType, encpyResourceKey, saveResource.getPrivateKey());
		}

		RequestParam<SaveResourceReq> reqParam = new RequestParam<>(this.getProjectId(), saveResource.getUid());
		SaveResourceReq saveResourceReq = new SaveResourceReq();
		saveResourceReq.setUid(saveResource.getUid());
		saveResourceReq.setContent(saveResource.getContent());
		saveResourceReq.setUrl(saveResource.getUrl());
		saveResourceReq.setOwnerUid(saveResource.getOwnerUid());
		saveResourceReq.setGrant(saveResource.getGrant().toString());
		saveResourceReq.setKey(resourceKey);
		String extraMsgSing = Secp256Util.sign(cryptoType, saveResourceReq.contractToString(),
				saveResource.getPrivateKey());
		saveResourceReq.setSign(extraMsgSing);
		reqParam.setData(saveResourceReq);

		String sign = Signatures.get().setInfo(this.getProjectId(), saveResource.getUid())
				.add("uid", saveResourceReq.getUid()).add("content", saveResourceReq.getContent())
				.add("url", saveResourceReq.getUrl()).add("ownerUid", saveResourceReq.getOwnerUid())
				.add("grant", saveResourceReq.getGrant()).add("sign", saveResourceReq.getSign())
				.sign(cryptoType, saveResource.getPrivateKey());
		reqParam.setSign(sign);

		ResultData<SaveResourceResp> regResult = HttpUtils.postCall(this.getUrl() + ServiceURL.HUB_SAVE_RESOURCE,
				this.getToken(), reqParam, SaveResourceResp.class);

		SaveResourceResult saveResourceResult = new SaveResourceResult();
		saveResourceResult.setEncryptKey(regResult.getData().getEncryptKey());
		saveResourceResult.setUrl(regResult.getData().getUrl());

		return saveResourceResult;
	}

	/**
	 * Query the saved resource in the identify hub, return the saved resource
	 * information
	 * 
	 * @param uid        user did
	 * @param privateKey The identify hub user's private key
	 * @param url        The resource URL in identify hub
	 * @return Return the resource encrypt content and encrypt Key
	 */
	public QueryResourceResp getResource(String uid, String privateKey, String url) {
		if (StringUtils.isBlank(uid)) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "uid is empty");
		}

		if (StringUtils.isBlank(privateKey)) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "privateKey is empty");
		}

		if (StringUtils.isBlank(url)) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "url is empty");
		}

		RequestParam<QueryResourceReq> reqParam = new RequestParam<>(this.getProjectId(), uid);
		QueryResourceReq queryResourceReq = new QueryResourceReq();
		queryResourceReq.setUid(uid);
		queryResourceReq.setUrl(url);
		String extraMsgSing = Secp256Util.sign(cryptoType, queryResourceReq.contractToString(), privateKey);
		queryResourceReq.setSign(extraMsgSing);
		reqParam.setData(queryResourceReq);

		String sign = Signatures.get().setInfo(this.getProjectId(), uid).add("uid", uid).add("url", url)
				.add("sign", queryResourceReq.getSign()).sign(cryptoType, privateKey);
		reqParam.setSign(sign);

		ResultData<QueryResourceResp> queryResult = HttpUtils.postCall(this.getUrl() + ServiceURL.HUB_QUERY_RESOURCE,
				this.getToken(), reqParam, QueryResourceResp.class);

		return queryResult.getData();
	}

	/**
	 * Delete the resource in the identify hub, this function validate the identify
	 * hub user's permission first, if this user have permission, then delete this
	 * permission.
	 * 
	 * @param uid        user id
	 * @param privateKey The identify hub user's private key
	 * @param url        The resource user in identify hub
	 * @return Return the delete result
	 */
	public Boolean deleteResource(String uid, String privateKey, String url) {
		if (StringUtils.isBlank(uid)) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "uid is empty");
		}

		if (StringUtils.isBlank(privateKey)) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "privateKey is empty");
		}

		if (StringUtils.isBlank(url)) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "url is empty");
		}

		RequestParam<DeleteResourceReq> reqParam = new RequestParam<>(this.getProjectId(), uid);
		DeleteResourceReq deleteResourceReq = new DeleteResourceReq();
		deleteResourceReq.setUid(uid);
		deleteResourceReq.setUrl(url);
		String extraMsgSing = Secp256Util.sign(cryptoType, deleteResourceReq.contractToString(), privateKey);
		deleteResourceReq.setSign(extraMsgSing);
		reqParam.setData(deleteResourceReq);

		String sign = Signatures.get().setInfo(this.getProjectId(), uid).add("uid", deleteResourceReq.getUid())
				.add("url", deleteResourceReq.getUrl()).add("sign", deleteResourceReq.getSign())
				.sign(cryptoType, privateKey);
		reqParam.setSign(sign);

		ResultData<Boolean> delResult = HttpUtils.postCall(this.getUrl() + ServiceURL.HUB_DELETE_RESOURCE,
				this.getToken(), reqParam, Boolean.class);

		return delResult.getData();
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
		if (StringUtils.isBlank(createPermission.getUid())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "uid is empty");
		}

		if (StringUtils.isBlank(createPermission.getPrivateKey())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "privateKey is empty");
		}

		if (createPermission.getGrant() != Operation.WRITE) {
			if (createPermission.getGrant() == null) {
				throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "grant is empty");
			}
		}

		if (StringUtils.isBlank(createPermission.getGrantUid())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "grantUid is empty");
		}

		if (StringUtils.isBlank(createPermission.getGrantPublicKey())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "grantPublicKey is empty");
		}

		String grantEncryptKey = null;
		String primaryKery = null;
		if (Operation.UPDATE == createPermission.getGrant()
				|| Operation.READ == createPermission.getGrant()) {
			QueryResourceResp queryResourceResp = this.getResource(createPermission.getUid(),
					createPermission.getPrivateKey(), createPermission.getUrl());

			if (queryResourceResp == null) {
				throw new DidException(ErrorMessage.RESOURCE_NOT_EISTS.getCode(),
						ErrorMessage.RESOURCE_NOT_EISTS.getMessage());
			}

			primaryKery = Secp256Util.decrypt(cryptoType, queryResourceResp.getKey(), createPermission.getPrivateKey());
			grantEncryptKey = Secp256Util.encrypt(cryptoType, primaryKery, createPermission.getGrantPublicKey());
		}

		RequestParam<CreatePermissionReq> reqParam = new RequestParam<>(this.getProjectId(), createPermission.getUid());
		CreatePermissionReq createPermissionReq = new CreatePermissionReq();
		createPermissionReq.setUid(createPermission.getUid());
		createPermissionReq.setUrl(createPermission.getUrl());
		createPermissionReq.setGrant(createPermission.getGrant().toString());
		createPermissionReq.setGrantUid(createPermission.getGrantUid());
		createPermissionReq.setGrantPublicKey(createPermission.getGrantPublicKey());
		createPermissionReq.setGrantEncryptKey(grantEncryptKey);

		String extraMsgSing = Secp256Util.sign(cryptoType, createPermissionReq.contractToString(),
				createPermission.getPrivateKey());
		createPermissionReq.setSign(extraMsgSing);
		reqParam.setData(createPermissionReq);

		String sign = Signatures.get().setInfo(this.getProjectId(), createPermission.getUid())
				.add("uid", createPermissionReq.getUid()).add("url", createPermissionReq.getUrl())
				.add("grant", createPermissionReq.getGrant()).add("grantUid", createPermissionReq.getGrantUid())
				.add("grantPublicKey", createPermissionReq.getGrantPublicKey())
				.add("grantEncryptKey", createPermissionReq.getGrantEncryptKey())
				.add("sign", createPermissionReq.getSign()).sign(cryptoType, createPermission.getPrivateKey());
		reqParam.setSign(sign);

		ResultData<CreatePermissionResp> queryResult = HttpUtils.postCall(
				this.getUrl() + ServiceURL.HUB_CREATE_PERMISSION, this.getToken(), reqParam,
				CreatePermissionResp.class);

		CreatePermissionResp createPermissionResp = queryResult.getData();
		if (createPermissionResp.getKey() == null || createPermissionResp.getKey().trim().isEmpty()) {
			createPermissionResp.setKey(primaryKery);
		}

		return createPermissionResp;
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
		if (deletePermission == null) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "deletePermission is empty");
		}

		if (StringUtils.isBlank(deletePermission.getUid())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "uid is empty");
		}

		if (StringUtils.isBlank(deletePermission.getUrl())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "url is empty");
		}

		if (StringUtils.isBlank(deletePermission.getPrivateKey())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "privateKey is empty");
		}

		if (StringUtils.isBlank(deletePermission.getGrantUid())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "grantUid is empty");
		}

		if (deletePermission.getGrant() == null) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "grant is empty");
		}

		RequestParam<DeletePermissionReq> reqParam = new RequestParam<>(this.getProjectId(), deletePermission.getUid());
		DeletePermissionReq deletePermissionReq = new DeletePermissionReq();
		deletePermissionReq.setUid(deletePermission.getUid());
		deletePermissionReq.setUrl(deletePermission.getUrl());
		deletePermissionReq.setGrant(deletePermission.getGrant().toString());
		deletePermissionReq.setGrantUid(deletePermission.getGrantUid());

		String extraMsgSing = Secp256Util.sign(cryptoType, deletePermissionReq.contractToString(),
				deletePermission.getPrivateKey());
		deletePermissionReq.setSign(extraMsgSing);
		reqParam.setData(deletePermissionReq);

		String sign = Signatures.get().setInfo(this.getProjectId(), deletePermission.getUid())
				.add("uid", deletePermissionReq.getUid()).add("url", deletePermissionReq.getUrl())
				.add("grant", deletePermissionReq.getGrant()).add("grantUid", deletePermissionReq.getGrantUid())
				.add("sign", deletePermissionReq.getSign()).sign(cryptoType, deletePermission.getPrivateKey());
		reqParam.setSign(sign);

		ResultData<DeletePermissionResp> queryResult = HttpUtils.postCall(
				this.getUrl() + ServiceURL.HUB_DELETE_PERMISSION, this.getToken(), reqParam,
				DeletePermissionResp.class);

		return queryResult.getData();
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
		if (queryPermission == null) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "queryPermission is empty");
		}

		if (StringUtils.isBlank(queryPermission.getUid())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "uid is empty");
		}

		if (StringUtils.isBlank(queryPermission.getPrivateKey())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "privateKey is empty");
		}

		RequestParam<QueryPermissionReq> reqParam = new RequestParam<>(this.getProjectId(), queryPermission.getUid());

		QueryPermissionReq queryPermissionReq = new QueryPermissionReq();
		queryPermissionReq.setUid(queryPermission.getUid());
		if (queryPermission.getFlag() != null) {
			queryPermissionReq.setFlag(queryPermission.getFlag().toString());
		}
		queryPermissionReq.setGrantUid(queryPermission.getGrantUid());

		String extraMsgSing = Secp256Util.sign(cryptoType, queryPermissionReq.contractToString(),
				queryPermission.getPrivateKey());
		queryPermissionReq.setSign(extraMsgSing);
		reqParam.setData(queryPermissionReq);

		String sign = Signatures.get().setInfo(this.getProjectId(), queryPermission.getUid())
				.add("uid", queryPermissionReq.getUid()).add("grantUid", queryPermissionReq.getGrantUid())
				.add("flag", queryPermissionReq.getFlag()).add("sign", queryPermissionReq.getSign())
				.sign(cryptoType, queryPermission.getPrivateKey());
		reqParam.setSign(sign);

		ResultData<List> queryResult = HttpUtils.postCall(this.getUrl() + ServiceURL.HUB_QUERY_PERMISSION,
				this.getToken(), reqParam, List.class);

		List<PermissionInfo> permissionList = new ArrayList<>();
		if (queryResult.getData() != null && queryResult.getData().size() > 0) {
			for (Object tmp : queryResult.getData()) {
				JSONObject jsonObj = (JSONObject) tmp;
				PermissionInfo permissionInfo = jsonObj.toJavaObject(PermissionInfo.class);
				permissionList.add(permissionInfo);
			}
		}

		return permissionList;
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
	public List<GrantPermissionInfo> queryGrantPermission(QueryGrantedPermission queryPermission) {
		if (queryPermission == null) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "queryPermission is empty");
		}

		if (StringUtils.isBlank(queryPermission.getUid())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "uid is empty");
		}

		if (StringUtils.isBlank(queryPermission.getPrivateKey())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "privateKey is empty");
		}

		RequestParam<QueryGrantedPermissionReq> reqParam = new RequestParam<>(this.getProjectId(),
				queryPermission.getUid());

		QueryGrantedPermissionReq queryPermissionReq = new QueryGrantedPermissionReq();
		queryPermissionReq.setUid(queryPermission.getUid());
		if (queryPermission.getGrant() != null) {
			queryPermissionReq.setGrant(queryPermission.getGrant().name());
		}
		if (queryPermission.getFlag() != null) {
			queryPermissionReq.setFlag(queryPermission.getFlag().toString());
		}
		queryPermissionReq.setOwnerUid(queryPermission.getOwnerUid());

		String extraMsgSing = Secp256Util.sign(cryptoType, queryPermissionReq.contractToString(),
				queryPermission.getPrivateKey());
		queryPermissionReq.setSign(extraMsgSing);
		reqParam.setData(queryPermissionReq);

		String sign = Signatures.get().setInfo(this.getProjectId(), queryPermission.getUid())
				.add("uid", queryPermissionReq.getUid()).add("ownerUid", queryPermissionReq.getOwnerUid())
				.add("grant", queryPermissionReq.getGrant()).add("flag", queryPermissionReq.getFlag())
				.add("sign", queryPermissionReq.getSign()).sign(cryptoType, queryPermission.getPrivateKey());
		reqParam.setSign(sign);

		ResultData<List> queryResult = HttpUtils.postCall(this.getUrl() + ServiceURL.HUB_QUERY_GRANT_PERMISSION,
				this.getToken(), reqParam, List.class);

		List<GrantPermissionInfo> permissionList = new ArrayList<>();
		if (queryResult.getData() != null && queryResult.getData().size() > 0) {
			for (Object tmp : queryResult.getData()) {
				JSONObject jsonObj = (JSONObject) tmp;
				GrantPermissionInfo permissionInfo = jsonObj.toJavaObject(GrantPermissionInfo.class);
				permissionList.add(permissionInfo);
			}
		}

		return permissionList;
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
		if (checkPermission == null) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "checkPermission is empty");
		}

		if (StringUtils.isBlank(checkPermission.getUid())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "uid is empty");
		}

		if (StringUtils.isBlank(checkPermission.getPrivateKey())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "privateKey is empty");
		}

		if (StringUtils.isBlank(checkPermission.getUrl())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "url is empty");
		}

		if (StringUtils.isBlank(checkPermission.getOwnerUid())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "ownerUid is empty");
		}

		if (StringUtils.isBlank(checkPermission.getGrantUid())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "grantUid is empty");
		}

		if (checkPermission.getGrant() == null) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "grant is empty");
		}

		RequestParam<CheckPermissionReq> reqParam = new RequestParam<>(this.getProjectId(), checkPermission.getUid());

		CheckPermissionReq checkPermissionReq = new CheckPermissionReq();
		checkPermissionReq.setUid(checkPermission.getUid());
		checkPermissionReq.setOwnerUid(checkPermission.getOwnerUid());
		checkPermissionReq.setGrantUid(checkPermission.getGrantUid());
		checkPermissionReq.setGrant(checkPermission.getGrant().toString());
		checkPermissionReq.setUrl(checkPermission.getUrl());

		String extraMsgSing = Secp256Util.sign(cryptoType, checkPermissionReq.contractToString(),
				checkPermission.getPrivateKey());
		checkPermissionReq.setSign(extraMsgSing);
		reqParam.setData(checkPermissionReq);

		ResultData<CheckPermissionResp> checkPermissionResp = HttpUtils.postCall(
				this.getUrl() + ServiceURL.HUB_CHECK_PERMISSION, this.getToken(), reqParam, CheckPermissionResp.class);

		return checkPermissionResp.getData();
	}

	/**
	 * Query the modification history operation records of your own resources,
	 * including save, update, and delete operations history.
	 * 
	 * @param queryResourceHistory Query resource history request param
	 * @return Return the resource operation history Information list
	 */
	public List<ResourceHistoryInfo> queryResourceHistory(QueryResourceHistory queryResourceHistory) {
		if (queryResourceHistory == null) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "queryResourceHistory is empty");
		}

		if (StringUtils.isBlank(queryResourceHistory.getUid())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "uid is empty");
		}

		if (StringUtils.isBlank(queryResourceHistory.getPrivateKey())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "privateKey is empty");
		}

		RequestParam<QueryResourceHistoryReq> reqParam = new RequestParam<>(this.getProjectId(),
				queryResourceHistory.getUid());

		QueryResourceHistoryReq queryResourceHistoryReq = new QueryResourceHistoryReq();
		queryResourceHistoryReq.setUid(queryResourceHistory.getUid());
		if (queryResourceHistory.getUrl() != null) {
			queryResourceHistoryReq.setUrl(queryResourceHistory.getUrl());
		}
		if (queryResourceHistory.getOperation() != null) {
			queryResourceHistoryReq.setOperation(queryResourceHistory.getOperation().name());
		}

		String extraMsgSing = Secp256Util.sign(cryptoType, queryResourceHistoryReq.contractToString(),
				queryResourceHistory.getPrivateKey());
		queryResourceHistoryReq.setSign(extraMsgSing);
		reqParam.setData(queryResourceHistoryReq);

		String sign = Signatures.get().setInfo(this.getProjectId(), queryResourceHistory.getUid())
				.add("uid", queryResourceHistoryReq.getUid()).add("url", queryResourceHistoryReq.getUrl())
				.add("operation", queryResourceHistoryReq.getOperation()).add("sign", queryResourceHistoryReq.getSign())
				.sign(cryptoType, queryResourceHistory.getPrivateKey());
		reqParam.setSign(sign);

		ResultData<List> queryResult = HttpUtils.postCall(this.getUrl() + ServiceURL.HUB_QUERY_RESOURCE_HISTORY,
				this.getToken(), reqParam, List.class);

		List<ResourceHistoryInfo> resourceHistoryList = new ArrayList<>();
		if (queryResult.getData() != null && queryResult.getData().size() > 0) {
			for (Object tmp : queryResult.getData()) {
				JSONObject jsonObj = (JSONObject) tmp;
				ResourceHistoryInfo resourceOperationHistoryInfo = jsonObj.toJavaObject(ResourceHistoryInfo.class);
				resourceHistoryList.add(resourceOperationHistoryInfo);
			}
		}

		return resourceHistoryList;
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
	public String decrypt(String content, String encptyKey, String privateKey) {
		if(content == null || content.trim().isEmpty()) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "content is empty");
		}
		
		if(encptyKey == null || encptyKey.trim().isEmpty()) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "encptyKey is empty");
		}

		if(privateKey == null || privateKey.trim().isEmpty()) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "privateKey is empty");
		}
		
		String key = null;
		try {
			key = Secp256Util.decrypt(cryptoType, encptyKey, privateKey);
		} catch (Exception e) {
			throw new DidException(ErrorMessage.DECRYPT_KEY_FAILED.getCode(),
					ErrorMessage.DECRYPT_KEY_FAILED.getMessage() + ": " + e.getMessage());
		}
		try {
			return AesUtils.decrypt(content, key);
		} catch (Exception e) {
			throw new DidException(ErrorMessage.DECRYPT_CONTENT_FAILED.getCode(),
					ErrorMessage.DECRYPT_CONTENT_FAILED.getMessage() + ": " + e.getMessage());
		}
	}
	
	/**
	 * Change the data's owner to the new user in the hub
	 * 
	 * This transfer change the data's owner and re-encryption key
	 * 
	 * @param transferOwner transfer data owner request parameter info
	 * @return return true if transfer the data owner to the new user success
	 */
	public Boolean transferOwner(TransferOwner transferOwner) {
		if (transferOwner == null) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "transferOwner is empty");
		}
		if (StringUtils.isBlank(transferOwner.getUid())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "uid is empty");
		}
		if (StringUtils.isBlank(transferOwner.getPrivateKey())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "privateKey is empty");
		}
		if (StringUtils.isBlank(transferOwner.getUrl())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "url is empty");
		}
		if (StringUtils.isBlank(transferOwner.getNewOwnerUid())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "newOwnerUid is empty");
		}
		if (StringUtils.isBlank(transferOwner.getNewOwnerPublicKey())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "newOwnerPublicKey is empty");
		}
		
		QueryResourceResp resourceInfo = this.getResource(transferOwner.getUid(), transferOwner.getPrivateKey(), transferOwner.getUrl());
		String resourceKey = Secp256Util.decrypt(cryptoType, resourceInfo.getKey(), transferOwner.getPrivateKey());
		String newKey = Secp256Util.encrypt(cryptoType, resourceKey, transferOwner.getNewOwnerPublicKey());
		
		RequestParam<TransferOwnerReq> reqParam = new RequestParam<>(this.getProjectId(), transferOwner.getUid());
		TransferOwnerReq transferOwnerReq = new TransferOwnerReq();
		transferOwnerReq.setUrl(transferOwner.getUrl());
		transferOwnerReq.setUid(transferOwner.getUid());
		transferOwnerReq.setNewOwnerUid(transferOwner.getNewOwnerUid());
		transferOwnerReq.setNewOwnerPublicKey(transferOwner.getNewOwnerPublicKey());
		transferOwnerReq.setNewKey(newKey);
		String extraMsgSing = Secp256Util.sign(cryptoType, transferOwnerReq.contractToString(),
				transferOwner.getPrivateKey());
		transferOwnerReq.setSign(extraMsgSing);
		reqParam.setData(transferOwnerReq);
		
		String sign = Signatures.get().setInfo(this.getProjectId(), transferOwner.getUid())
				.add("uid", transferOwnerReq.getUid())
				.add("url", transferOwnerReq.getUrl())
				.add("newOwnerUid", transferOwnerReq.getNewOwnerUid())
				.add("newOwnerPublicKey", transferOwnerReq.getNewOwnerPublicKey())
				.add("newKey", transferOwnerReq.getNewKey())
				.add("sign", transferOwnerReq.getSign())
				.sign(cryptoType, transferOwner.getPrivateKey());
		reqParam.setSign(sign);
		ResultData<Boolean> transferResult = HttpUtils.postCall(this.getUrl() + ServiceURL.HUB_TRANSFER_OWNER,
				this.getToken(), reqParam, Boolean.class);
		
		return transferResult.getData();
	}
	
	public CryptoType getCryptoTypeByIdOrPublicKey(String uid,String publicKey) {
		if ((uid == null || uid.trim().isEmpty()) && (publicKey == null || publicKey.trim().isEmpty())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "uid and publicKey are empty");
		}

		RequestParam<QueryCryptoTypeReq> reqParam = new RequestParam<>(this.getProjectId(),uid);
		QueryCryptoTypeReq queryCryptoTypeReq = new QueryCryptoTypeReq();
		if(uid != null && !uid.trim().isEmpty()) {
			queryCryptoTypeReq.setUid(uid);
		}
		if(publicKey != null && !publicKey.trim().isEmpty()) {
			queryCryptoTypeReq.setPublicKey(publicKey);
		}
		reqParam.setData(queryCryptoTypeReq);
		
		ResultData<QueryCryptoTypeResp> queryTypeResult = HttpUtils.postCall(this.getUrl() + ServiceURL.HUB_QUEERY_TYPE,
				this.getToken(), reqParam, QueryCryptoTypeResp.class);

		Integer type = queryTypeResult.getData().getType();
		if(type == null) {
			throw new DidException(ErrorMessage.USER_NOT_EXISTS.getCode(), ErrorMessage.USER_NOT_EXISTS.getMessage());
		}
		
		return CryptoType.ofVlaue(type);
	}
	
}
