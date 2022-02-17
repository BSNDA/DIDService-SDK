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
import com.reddate.did.sdk.protocol.request.hub.QueryGrantedPermissionReq;
import com.reddate.did.sdk.protocol.request.hub.QueryPermissionReq;
import com.reddate.did.sdk.protocol.request.hub.QueryResourceHistoryReq;
import com.reddate.did.sdk.protocol.request.hub.QueryResourceReq;
import com.reddate.did.sdk.protocol.request.hub.RegisterHubReq;
import com.reddate.did.sdk.protocol.request.hub.SaveResourceReq;
import com.reddate.did.sdk.protocol.response.ResultData;
import com.reddate.did.sdk.protocol.response.hub.RegisterHubResp;
import com.reddate.did.sdk.protocol.response.hub.SaveResourceResp;
import com.reddate.did.sdk.protocol.response.hub.CheckPermissionResp;
import com.reddate.did.sdk.protocol.response.hub.CreatePermissionResp;
import com.reddate.did.sdk.protocol.response.hub.DeletePermissionResp;
import com.reddate.did.sdk.protocol.response.hub.QueryResourceResp;
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

	public HubService(String url, String projectId, String token) {
		super(url, projectId, token);
	}

	/**
	 * Register and did user to be a identify hub user.
	 * 
	 * @param did       The register did user
	 * @param publicKey This did user's public key
	 * @return Return the register result
	 */
	public RegisterHubResult registerHub(String did, String publicKey) {
		if (StringUtils.isEmpty(did)) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "did is empty");
		}
		if (StringUtils.isEmpty(publicKey)) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "public key is empty");
		}
		RequestParam<RegisterHubReq> reqParam = new RequestParam<>(this.getProjectId(), did);
		RegisterHubReq registerHubReq = new RegisterHubReq();
		registerHubReq.setDid(did);
		registerHubReq.setPublicKey(publicKey);
		reqParam.setData(registerHubReq);

		ResultData<RegisterHubResp> regResult = HttpUtils.postCall(this.getUrl() + ServiceURL.HUB_REGISTER,
				this.getToken(), reqParam, RegisterHubResp.class);
		if (!regResult.isSuccess()) {
			throw new DidException(regResult.getCode(), regResult.getMsg());
		}

		RegisterHubResult registerHubResult = new RegisterHubResult();
		registerHubResult.setDid(regResult.getData().getUid());
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
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "save resource is empty");
		}
		if (StringUtils.isEmpty(saveResource.getDid())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "did is empty");
		}
		if (StringUtils.isEmpty(saveResource.getContent())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "content is empty");
		}
		if (StringUtils.isEmpty(saveResource.getOwnerDid())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "owner did is empty");
		}
		if (saveResource.getGrant() == null) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "grant is empty");
		}
		if (saveResource.getGrant() != Operation.WRITE) {
			if (StringUtils.isEmpty(saveResource.getUrl())) {
				throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "url is empty");
			}
		}
		if (StringUtils.isEmpty(saveResource.getPrivateKey())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "private key is empty");
		}

		String encpyResourceKey = null;
		String resourceKey = null;
		if ((saveResource.getGrant() == Operation.WRITE || saveResource.getGrant() == Operation.UPDATE)
				&& !saveResource.getOwnerDid().equals(saveResource.getDid())) {
			CheckPermission checkPermission = new CheckPermission();
			checkPermission.setDid(saveResource.getDid());
			checkPermission.setPrivateKey(saveResource.getPrivateKey());
			checkPermission.setUrl(saveResource.getUrl());
			checkPermission.setGrant(saveResource.getGrant());
			checkPermission.setOwnerDid(saveResource.getOwnerDid());
			checkPermission.setGrantDid(saveResource.getDid());
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
			try {
				resourceKey = Secp256Util.decrypt(CryptoType.ECDSA, encpyResourceKey, saveResource.getPrivateKey());
			} catch (Exception e) {
				throw new DidException(ErrorMessage.DECRPTY_GRANT_KEY_FAILED.getCode(),
						ErrorMessage.DECRPTY_GRANT_KEY_FAILED.getMessage() + e.getMessage());
			}
		}

		RequestParam<SaveResourceReq> reqParam = new RequestParam<>(this.getProjectId(), saveResource.getDid());
		SaveResourceReq saveResourceReq = new SaveResourceReq();
		saveResourceReq.setUid(saveResource.getDid());
		saveResourceReq.setContent(saveResource.getContent());
		saveResourceReq.setUrl(saveResource.getUrl());
		saveResourceReq.setOwnerUid(saveResource.getOwnerDid());
		saveResourceReq.setGrant(saveResource.getGrant().toString());
		saveResourceReq.setKey(resourceKey);
		String extraMsgSing = null;
		try {
			extraMsgSing = Secp256Util.sign(CryptoType.ECDSA, saveResourceReq.contractToString(),
					saveResource.getPrivateKey());
		} catch (Exception e1) {
			e1.printStackTrace();
			throw new DidException(ErrorMessage.SIGNATURE_FAILED.getCode(), ErrorMessage.SIGNATURE_FAILED.getMessage());
		}
		saveResourceReq.setSign(extraMsgSing);
		reqParam.setData(saveResourceReq);

		String sign = Signatures.get().setInfo(this.getProjectId(), saveResource.getDid())
				.add("uid", saveResourceReq.getUid()).add("content", saveResourceReq.getContent())
				.add("url", saveResourceReq.getUrl()).add("ownerUid", saveResourceReq.getOwnerUid())
				.add("grant", saveResourceReq.getGrant()).add("sign", saveResourceReq.getSign())
				.sign(saveResource.getPrivateKey());
		reqParam.setSign(sign);

		ResultData<SaveResourceResp> regResult = HttpUtils.postCall(this.getUrl() + ServiceURL.HUB_SAVE_RESOURCE,
				this.getToken(), reqParam, SaveResourceResp.class);
		if (!regResult.isSuccess()) {
			throw new DidException(regResult.getCode(), regResult.getMsg());
		}

		SaveResourceResult saveResourceResult = new SaveResourceResult();
		saveResourceResult.setEncryptKey(regResult.getData().getEncryptKey());
		saveResourceResult.setUrl(regResult.getData().getUrl());

		return saveResourceResult;
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
		if (StringUtils.isEmpty(did)) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "did is empty");
		}

		if (StringUtils.isEmpty(privateKey)) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "private key is empty");
		}

		if (StringUtils.isEmpty(url)) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "url is empty");
		}

		RequestParam<QueryResourceReq> reqParam = new RequestParam<>(this.getProjectId(), did);
		QueryResourceReq queryResourceReq = new QueryResourceReq();
		queryResourceReq.setUid(did);
		queryResourceReq.setUrl(url);
		String extraMsgSing = null;
		try {
			extraMsgSing = Secp256Util.sign(CryptoType.ECDSA, queryResourceReq.contractToString(), privateKey);
		} catch (Exception e1) {
			e1.printStackTrace();
			throw new DidException(ErrorMessage.SIGNATURE_FAILED.getCode(), ErrorMessage.SIGNATURE_FAILED.getMessage());
		}
		queryResourceReq.setSign(extraMsgSing);
		reqParam.setData(queryResourceReq);

		String sign = Signatures.get().setInfo(this.getProjectId(), did).add("uid", did).add("url", url)
				.add("sign", queryResourceReq.getSign()).sign(privateKey);
		reqParam.setSign(sign);

		ResultData<QueryResourceResp> queryResult = HttpUtils.postCall(this.getUrl() + ServiceURL.HUB_QUERY_RESOURCE,
				this.getToken(), reqParam, QueryResourceResp.class);
		if (!queryResult.isSuccess()) {
			throw new DidException(queryResult.getCode(), queryResult.getMsg());
		}

		return queryResult.getData();
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
		if (StringUtils.isEmpty(did)) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "did is empty");
		}

		if (StringUtils.isEmpty(privateKey)) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "private key is empty");
		}

		if (StringUtils.isEmpty(url)) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "url is empty");
		}

		RequestParam<DeleteResourceReq> reqParam = new RequestParam<>(this.getProjectId(), did);
		DeleteResourceReq deleteResourceReq = new DeleteResourceReq();
		deleteResourceReq.setUid(did);
		deleteResourceReq.setUrl(url);
		String extraMsgSing = null;
		try {
			extraMsgSing = Secp256Util.sign(CryptoType.ECDSA, deleteResourceReq.contractToString(), privateKey);
		} catch (Exception e1) {
			e1.printStackTrace();
			throw new DidException(ErrorMessage.SIGNATURE_FAILED.getCode(), ErrorMessage.SIGNATURE_FAILED.getMessage());
		}
		deleteResourceReq.setSign(extraMsgSing);
		reqParam.setData(deleteResourceReq);

		String sign = Signatures.get().setInfo(this.getProjectId(), did).add("uid", deleteResourceReq.getUid())
				.add("url", deleteResourceReq.getUrl()).add("sign", deleteResourceReq.getSign()).sign(privateKey);
		reqParam.setSign(sign);

		ResultData<Boolean> delResult = HttpUtils.postCall(this.getUrl() + ServiceURL.HUB_DELETE_RESOURCE,
				this.getToken(), reqParam, Boolean.class);

		if (!delResult.isSuccess()) {
			throw new DidException(delResult.getCode(), delResult.getMsg());
		}

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
		if (StringUtils.isEmpty(createPermission.getDid())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "did is empty");
		}

		if (StringUtils.isEmpty(createPermission.getPrivateKey())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "private key is empty");
		}

		if (createPermission.getGrant() != Operation.WRITE) {
			if (createPermission.getGrant() == null) {
				throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "grant is empty");
			}
		}

		if (StringUtils.isEmpty(createPermission.getGrantDid())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "grant did is empty");
		}

		if (StringUtils.isEmpty(createPermission.getGrantPublicKey())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "grant public key is empty");
		}

		String grantEncryptKey = null;
		String primaryKery = null;
		if (Operation.UPDATE == createPermission.getGrant() || Operation.DELETE == createPermission.getGrant()
				|| Operation.READ == createPermission.getGrant()) {
			QueryResourceResp queryResourceResp = this.getResource(createPermission.getDid(),
					createPermission.getPrivateKey(), createPermission.getUrl());

			if (queryResourceResp == null) {
				throw new DidException(ErrorMessage.RESOURCE_NOT_EISTS.getCode(),
						ErrorMessage.RESOURCE_NOT_EISTS.getMessage());
			}

			try {
				primaryKery = Secp256Util.decrypt(CryptoType.ECDSA, queryResourceResp.getKey(),
						createPermission.getPrivateKey());
				grantEncryptKey = Secp256Util.encrypt(CryptoType.ECDSA, primaryKery,
						createPermission.getGrantPublicKey());
			} catch (Exception e) {
				throw new DidException(ErrorMessage.ENCRYPT_KEY_FAILED.getCode(),
						ErrorMessage.ENCRYPT_KEY_FAILED.getMessage());
			}

		}

		RequestParam<CreatePermissionReq> reqParam = new RequestParam<>(this.getProjectId(), createPermission.getDid());
		CreatePermissionReq createPermissionReq = new CreatePermissionReq();
		createPermissionReq.setUid(createPermission.getDid());
		createPermissionReq.setUrl(createPermission.getUrl());
		createPermissionReq.setGrant(createPermission.getGrant().toString());
		createPermissionReq.setGrantUid(createPermission.getGrantDid());
		createPermissionReq.setGrantPublicKey(createPermission.getGrantPublicKey());
		createPermissionReq.setGrantEncryptKey(grantEncryptKey);

		String extraMsgSing = null;
		try {
			extraMsgSing = Secp256Util.sign(CryptoType.ECDSA, createPermissionReq.contractToString(),
					createPermission.getPrivateKey());
		} catch (Exception e1) {
			e1.printStackTrace();
			throw new DidException(ErrorMessage.SIGNATURE_FAILED.getCode(), ErrorMessage.SIGNATURE_FAILED.getMessage());
		}
		createPermissionReq.setSign(extraMsgSing);
		reqParam.setData(createPermissionReq);

		String sign = Signatures.get().setInfo(this.getProjectId(), createPermission.getDid())
				.add("uid", createPermissionReq.getUid()).add("url", createPermissionReq.getUrl())
				.add("grant", createPermissionReq.getGrant()).add("grantUid", createPermissionReq.getGrantUid())
				.add("grantPublicKey", createPermissionReq.getGrantPublicKey())
				.add("grantEncryptKey", createPermissionReq.getGrantEncryptKey())
				.add("sign", createPermissionReq.getSign()).sign(createPermission.getPrivateKey());
		reqParam.setSign(sign);

		ResultData<CreatePermissionResp> queryResult = HttpUtils.postCall(
				this.getUrl() + ServiceURL.HUB_CREATE_PERMISSION, this.getToken(), reqParam,
				CreatePermissionResp.class);

		if (!queryResult.isSuccess()) {
			throw new DidException(queryResult.getCode(), queryResult.getMsg());
		}

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
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "delete permission is empty");
		}

		if (StringUtils.isEmpty(deletePermission.getDid())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "did is empty");
		}

		if (StringUtils.isEmpty(deletePermission.getUrl())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "url is empty");
		}

		if (StringUtils.isEmpty(deletePermission.getPrivateKey())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "private key is empty");
		}

		if (StringUtils.isEmpty(deletePermission.getGrantDid())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "grant did is empty");
		}

		if (deletePermission.getGrant() == null) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "grant is empty");
		}

		RequestParam<DeletePermissionReq> reqParam = new RequestParam<>(this.getProjectId(), deletePermission.getDid());
		DeletePermissionReq deletePermissionReq = new DeletePermissionReq();
		deletePermissionReq.setUid(deletePermission.getDid());
		deletePermissionReq.setUrl(deletePermission.getUrl());
		deletePermissionReq.setGrant(deletePermission.getGrant().toString());
		deletePermissionReq.setGrantUid(deletePermission.getGrantDid());

		String extraMsgSing = null;
		try {
			extraMsgSing = Secp256Util.sign(CryptoType.ECDSA, deletePermissionReq.contractToString(),
					deletePermission.getPrivateKey());
		} catch (Exception e1) {
			e1.printStackTrace();
			throw new DidException(ErrorMessage.SIGNATURE_FAILED.getCode(), ErrorMessage.SIGNATURE_FAILED.getMessage());
		}
		deletePermissionReq.setSign(extraMsgSing);
		reqParam.setData(deletePermissionReq);

		String sign = Signatures.get().setInfo(this.getProjectId(), deletePermission.getDid())
				.add("uid", deletePermissionReq.getUid()).add("url", deletePermissionReq.getUrl())
				.add("grant", deletePermissionReq.getGrant()).add("grantUid", deletePermissionReq.getGrantUid())
				.add("sign", deletePermissionReq.getSign()).sign(deletePermission.getPrivateKey());
		reqParam.setSign(sign);

		ResultData<DeletePermissionResp> queryResult = HttpUtils.postCall(
				this.getUrl() + ServiceURL.HUB_DELETE_PERMISSION, this.getToken(), reqParam,
				DeletePermissionResp.class);
		if (!queryResult.isSuccess()) {
			throw new DidException(queryResult.getCode(), queryResult.getMsg());
		}

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
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "query permissionn is empty");
		}

		if (StringUtils.isEmpty(queryPermission.getDid())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "did is empty");
		}

		if (StringUtils.isEmpty(queryPermission.getPrivateKey())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "private key is empty");
		}

		RequestParam<QueryPermissionReq> reqParam = new RequestParam<>(this.getProjectId(), queryPermission.getDid());

		QueryPermissionReq queryPermissionReq = new QueryPermissionReq();
		queryPermissionReq.setUid(queryPermission.getDid());
		if (queryPermission.getFlag() != null) {
			queryPermissionReq.setFlag(queryPermission.getFlag().toString());
		}
		queryPermissionReq.setGrantUid(queryPermission.getGrantDid());

		String extraMsgSing = null;
		try {
			extraMsgSing = Secp256Util.sign(CryptoType.ECDSA, queryPermissionReq.contractToString(),
					queryPermission.getPrivateKey());
		} catch (Exception e1) {
			e1.printStackTrace();
			throw new DidException(ErrorMessage.SIGNATURE_FAILED.getCode(), ErrorMessage.SIGNATURE_FAILED.getMessage());
		}
		queryPermissionReq.setSign(extraMsgSing);
		reqParam.setData(queryPermissionReq);

		String sign = Signatures.get().setInfo(this.getProjectId(), queryPermission.getDid())
				.add("uid", queryPermissionReq.getUid()).add("grantUid", queryPermissionReq.getGrantUid())
				.add("flag", queryPermissionReq.getFlag()).add("sign", queryPermissionReq.getSign())
				.sign(queryPermission.getPrivateKey());
		reqParam.setSign(sign);

		ResultData<List> queryResult = HttpUtils.postCall(this.getUrl() + ServiceURL.HUB_QUERY_PERMISSION,
				this.getToken(), reqParam, List.class);
		if (!queryResult.isSuccess()) {
			throw new DidException(queryResult.getCode(), queryResult.getMsg());
		}

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
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "query permissionn is empty");
		}

		if (StringUtils.isEmpty(queryPermission.getDid())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "did is empty");
		}

		if (StringUtils.isEmpty(queryPermission.getPrivateKey())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "private key is empty");
		}

		RequestParam<QueryGrantedPermissionReq> reqParam = new RequestParam<>(this.getProjectId(),
				queryPermission.getDid());

		QueryGrantedPermissionReq queryPermissionReq = new QueryGrantedPermissionReq();
		queryPermissionReq.setUid(queryPermission.getDid());
		if (queryPermission.getGrant() != null) {
			queryPermissionReq.setGrant(queryPermission.getGrant().name());
		}
		if (queryPermission.getFlag() != null) {
			queryPermissionReq.setFlag(queryPermission.getFlag().toString());
		}
		queryPermissionReq.setOwnerUid(queryPermission.getOwnerDid());

		String extraMsgSing = null;
		try {
			extraMsgSing = Secp256Util.sign(CryptoType.ECDSA, queryPermissionReq.contractToString(),
					queryPermission.getPrivateKey());
		} catch (Exception e1) {
			e1.printStackTrace();
			throw new DidException(ErrorMessage.SIGNATURE_FAILED.getCode(), ErrorMessage.SIGNATURE_FAILED.getMessage());
		}
		queryPermissionReq.setSign(extraMsgSing);
		reqParam.setData(queryPermissionReq);

		String sign = Signatures.get().setInfo(this.getProjectId(), queryPermission.getDid())
				.add("uid", queryPermissionReq.getUid()).add("ownerUid", queryPermissionReq.getOwnerUid())
				.add("grant", queryPermissionReq.getGrant()).add("flag", queryPermissionReq.getFlag())
				.add("sign", queryPermissionReq.getSign()).sign(queryPermission.getPrivateKey());
		reqParam.setSign(sign);

		ResultData<List> queryResult = HttpUtils.postCall(this.getUrl() + ServiceURL.HUB_QUERY_GRANT_PERMISSION,
				this.getToken(), reqParam, List.class);
		if (!queryResult.isSuccess()) {
			throw new DidException(queryResult.getCode(), queryResult.getMsg());
		}

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
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "check permissionn is empty");
		}

		if (StringUtils.isEmpty(checkPermission.getDid())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "did is empty");
		}

		if (StringUtils.isEmpty(checkPermission.getPrivateKey())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "private key is empty");
		}

		if (StringUtils.isEmpty(checkPermission.getUrl())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "url is empty");
		}

		if (StringUtils.isEmpty(checkPermission.getOwnerDid())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "owner did is empty");
		}

		if (StringUtils.isEmpty(checkPermission.getGrantDid())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "grant did is empty");
		}

		if (checkPermission.getGrant() == null) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "grant is empty");
		}

		RequestParam<CheckPermissionReq> reqParam = new RequestParam<>(this.getProjectId(), checkPermission.getDid());

		CheckPermissionReq checkPermissionReq = new CheckPermissionReq();
		checkPermissionReq.setUid(checkPermission.getDid());
		checkPermissionReq.setOwnerUid(checkPermission.getOwnerDid());
		checkPermissionReq.setGrantUid(checkPermission.getGrantDid());
		checkPermissionReq.setGrant(checkPermission.getGrant().toString());
		checkPermissionReq.setUrl(checkPermission.getUrl());

		String extraMsgSing = null;
		try {
			extraMsgSing = Secp256Util.sign(CryptoType.ECDSA, checkPermissionReq.contractToString(),
					checkPermission.getPrivateKey());
		} catch (Exception e1) {
			e1.printStackTrace();
			throw new DidException(ErrorMessage.SIGNATURE_FAILED.getCode(), ErrorMessage.SIGNATURE_FAILED.getMessage());
		}
		checkPermissionReq.setSign(extraMsgSing);
		reqParam.setData(checkPermissionReq);

		ResultData<CheckPermissionResp> checkPermissionResp = HttpUtils.postCall(
				this.getUrl() + ServiceURL.HUB_CHECK_PERMISSION, this.getToken(), reqParam, CheckPermissionResp.class);
		if (!checkPermissionResp.isSuccess()) {
			throw new DidException(checkPermissionResp.getCode(), checkPermissionResp.getMsg());
		}

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
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "query resource history is empty");
		}

		if (StringUtils.isEmpty(queryResourceHistory.getDid())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "did is empty");
		}

		if (StringUtils.isEmpty(queryResourceHistory.getPrivateKey())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "private key is empty");
		}

		RequestParam<QueryResourceHistoryReq> reqParam = new RequestParam<>(this.getProjectId(),
				queryResourceHistory.getDid());

		QueryResourceHistoryReq queryResourceHistoryReq = new QueryResourceHistoryReq();
		queryResourceHistoryReq.setUid(queryResourceHistory.getDid());
		if (queryResourceHistory.getUrl() != null) {
			queryResourceHistoryReq.setUrl(queryResourceHistory.getUrl());
		}
		if (queryResourceHistory.getOperation() != null) {
			queryResourceHistoryReq.setOperation(queryResourceHistory.getOperation().name());
		}

		String extraMsgSing = null;
		try {
			extraMsgSing = Secp256Util.sign(CryptoType.ECDSA, queryResourceHistoryReq.contractToString(),
					queryResourceHistory.getPrivateKey());
		} catch (Exception e1) {
			e1.printStackTrace();
			throw new DidException(ErrorMessage.SIGNATURE_FAILED.getCode(), ErrorMessage.SIGNATURE_FAILED.getMessage());
		}
		queryResourceHistoryReq.setSign(extraMsgSing);
		reqParam.setData(queryResourceHistoryReq);

		String sign = Signatures.get().setInfo(this.getProjectId(), queryResourceHistory.getDid())
				.add("uid", queryResourceHistoryReq.getUid()).add("url", queryResourceHistoryReq.getUrl())
				.add("operation", queryResourceHistoryReq.getOperation()).add("sign", queryResourceHistoryReq.getSign())
				.sign(queryResourceHistory.getPrivateKey());
		reqParam.setSign(sign);

		ResultData<List> queryResult = HttpUtils.postCall(this.getUrl() + ServiceURL.HUB_QUERY_RESOURCE_HISTORY,
				this.getToken(), reqParam, List.class);
		if (!queryResult.isSuccess()) {
			throw new DidException(queryResult.getCode(), queryResult.getMsg());
		}

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
}
