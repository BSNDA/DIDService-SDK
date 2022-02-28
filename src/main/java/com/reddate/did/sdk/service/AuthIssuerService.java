package com.reddate.did.sdk.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.TreeMap;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONArray;
import com.reddate.did.sdk.constant.ErrorMessage;
import com.reddate.did.sdk.constant.ServiceURL;
import com.reddate.did.sdk.exception.DidException;
import com.reddate.did.sdk.param.req.AuthIssuerList;
import com.reddate.did.sdk.param.req.QueryCptList;
import com.reddate.did.sdk.param.req.RegisterAuthorityIssuer;
import com.reddate.did.sdk.param.req.RegisterCpt;
import com.reddate.did.sdk.protocol.request.AuthIssuerListWrapper;
import com.reddate.did.sdk.protocol.request.CptInfo;
import com.reddate.did.sdk.protocol.request.JsonSchema;
import com.reddate.did.sdk.protocol.request.Proof;
import com.reddate.did.sdk.protocol.request.QueryCptByIdWrapper;
import com.reddate.did.sdk.protocol.request.QueryCptListWrapper;
import com.reddate.did.sdk.protocol.request.RegisterAuthorityIssuerWrapper;
import com.reddate.did.sdk.protocol.request.RegisterCptWrapper;
import com.reddate.did.sdk.protocol.request.RequestParam;
import com.reddate.did.sdk.protocol.response.AuthorityIssuer;
import com.reddate.did.sdk.protocol.response.CptBaseInfo;
import com.reddate.did.sdk.protocol.response.Pages;
import com.reddate.did.sdk.protocol.response.ResultData;
import com.reddate.did.sdk.util.CptUtils;
import com.reddate.did.sdk.util.ECDSAUtils;
import com.reddate.did.sdk.util.GenerateCptIdUtils;
import com.reddate.did.sdk.util.HttpUtils;
import com.reddate.did.sdk.util.Signatures;
import org.apache.commons.lang3.StringUtils;

public class AuthIssuerService extends BaseService {

	public AuthIssuerService(String url, String projectId, String token) {
		super(url, projectId, token);
	}

	/**
	 * Register a did to be a authority issuer, this function validate the did is on
	 * block chain, and validate it is not a authority issuer, and register it to be
	 * a issuer.
	 * 
	 * @param register Register authority issuer information.
	 * @return Return the register result
	 */
	public boolean registerAuthIssuer(RegisterAuthorityIssuer register) {

		if (ObjectUtil.isEmpty(register)) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "register is empty");
		}
		if (StringUtils.isEmpty(register.getName())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "register name is empty");
		}
		if (StringUtils.isEmpty(register.getDid())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "register did is empty");
		}
		if (StringUtils.isEmpty(register.getPrivateKey())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "register private key is empty");
		}
		RegisterAuthorityIssuerWrapper req = new RegisterAuthorityIssuerWrapper();
		req.setDid(register.getDid());
		req.setName(register.getName());

		String publicKey = null;
		try {
			publicKey = ECDSAUtils.getPublicKey(register.getPrivateKey());
		} catch (Exception e) {
			e.printStackTrace();
		}
		String publicKeySign = null;
		try {
			publicKeySign = ECDSAUtils.sign(publicKey, register.getPrivateKey());
		} catch (Exception e1) {
			throw new DidException(ErrorMessage.PRIVATE_KEY_ILLEGAL_FORMAT.getCode(),
					ErrorMessage.PRIVATE_KEY_ILLEGAL_FORMAT.getMessage());
		}
		req.setPublicKeySign(publicKeySign);

		String sign = Signatures.get().setInfo(this.getProjectId(), register.getDid()).add("name", register.getName())
				.add("publicKeySign", publicKeySign).sign(register.getPrivateKey());

		RequestParam<RegisterAuthorityIssuerWrapper> reqParam = new RequestParam<>(this.getProjectId(),
				register.getDid());
		reqParam.setData(req);
		reqParam.setSign(sign);

		ResultData<Boolean> regResult = HttpUtils.postCall(this.getUrl() + ServiceURL.REGISTER_AUTH_ISSUER,
				this.getToken(), reqParam, Boolean.class);
		if (!regResult.isSuccess()) {
			throw new DidException(regResult.getCode(), regResult.getMsg());
		}

		return regResult.isSuccess();
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

		if (ObjectUtil.isEmpty(query)) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "query param is empty");
		}
		if (query.getPage() == null) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "page is empty");
		}
		if (query.getSize() == null) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "size is empty");
		}

		AuthIssuerListWrapper authIssuerListWrapper = new AuthIssuerListWrapper();
		authIssuerListWrapper.setDid(query.getDid());
		authIssuerListWrapper.setPage(query.getPage());
		authIssuerListWrapper.setSize(query.getSize());

		RequestParam<AuthIssuerListWrapper> reqParam = new RequestParam<>(this.getProjectId(), query.getDid());
		reqParam.setData(authIssuerListWrapper);

		ResultData<Map> pageResult = HttpUtils.postCall(this.getUrl() + ServiceURL.QUERY_AUTH_ISSUER_LIST,
				this.getToken(), reqParam, Map.class);
		if (!pageResult.isSuccess()) {
			throw new DidException(pageResult.getCode(), pageResult.getMsg());
		}

		Pages<AuthorityIssuer> pages = this.parseToPage(pageResult.getData(), AuthorityIssuer.class);

		return pages;
	}

	/**
	 * Register a CPT template for the authority issuer, this function validate the
	 * authority issuer and CPT template information first, then add this CPT
	 * template on the block chain.
	 * 
	 * @param registerCpt Register CPT template information
	 * @return Return the CPT template Id,and CPT template version
	 */
	public CptBaseInfo registerCpt(RegisterCpt registerCpt) {
		if (ObjectUtil.isEmpty(registerCpt)) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "register cpt is empty");
		}
		if (StringUtils.isEmpty(registerCpt.getDescription())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "description is empty");
		}
		if (StringUtils.isEmpty(registerCpt.getTitle())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "title is empty");
		}
		if (StringUtils.isEmpty(registerCpt.getPrivateKey())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "private key is empty");
		}
		if (StringUtils.isEmpty(registerCpt.getType())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "type key is empty");
		}
		if (StringUtils.isEmpty(registerCpt.getDid())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "did is empty");
		}
		if (ObjectUtil.isEmpty(registerCpt.getCptJsonSchema())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "cpt json schema is empty");
		}
		RegisterCptWrapper registerCptWrapper = new RegisterCptWrapper();
		registerCptWrapper.setCptId(registerCpt.getCptId());
		registerCptWrapper.setDid(registerCpt.getDid());

		Map<String, JsonSchema> cptJsonSchema = new TreeMap<>();
		if (registerCpt.getCptJsonSchema() != null) {
			registerCpt.getCptJsonSchema().forEach((key, val) -> {
				JsonSchema jsonSchema = new JsonSchema();
				jsonSchema.setDescription(val.getDescription());
				jsonSchema.setRequired(val.getRequired());
				jsonSchema.setType(val.getType());
				cptJsonSchema.put(key, jsonSchema);
			});
		}
		registerCptWrapper.setCptJsonSchema(cptJsonSchema);

		registerCptWrapper.setTitle(registerCpt.getTitle());
		registerCptWrapper.setDescription(registerCpt.getDescription());
		registerCptWrapper.setType(registerCpt.getType());
		String nowStr = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		registerCptWrapper.setCreate(nowStr);
		registerCptWrapper.setUpdate(nowStr);
		registerCptWrapper.setCptId(GenerateCptIdUtils.getId());

		CptInfo cptInfo = CptUtils.assemblyCptInfo(registerCptWrapper);
		String signValue = null;
		try {
			signValue = ECDSAUtils.sign(JSONArray.toJSON(cptInfo).toString(), registerCpt.getPrivateKey());
		} catch (Exception e1) {
			e1.printStackTrace();
			throw new DidException(ErrorMessage.SIGNATURE_FAILED.getCode(), ErrorMessage.SIGNATURE_FAILED.getMessage());
		}

		Proof proof = new Proof();
		proof.setCreator(registerCpt.getDid());
		proof.setSignatureValue(signValue);
		proof.setType("Secp256k1");
		registerCptWrapper.setProof(proof);

		String sign = Signatures.get().setInfo(this.getProjectId(), registerCpt.getDid())
				.add("cptId", registerCptWrapper.getCptId()).add("did", registerCptWrapper.getDid())
				.add("jsonSchema", registerCptWrapper.getCptJsonSchema()).add("title", registerCptWrapper.getTitle())
				.add("description", registerCptWrapper.getDescription()).add("type", registerCptWrapper.getType())
				.add("proof", registerCptWrapper.getProof()).add("created", registerCptWrapper.getCreate())
				.add("updated", registerCptWrapper.getUpdate()).sign(registerCpt.getPrivateKey());

		RequestParam<RegisterCptWrapper> reqParam = new RequestParam<>(this.getProjectId(), registerCpt.getDid());
		reqParam.setData(registerCptWrapper);
		reqParam.setSign(sign);

		ResultData<CptBaseInfo> cptBaseInfo = HttpUtils.postCall(this.getUrl() + ServiceURL.REGISTER_CPT,
				this.getToken(), reqParam, CptBaseInfo.class);
		if (!cptBaseInfo.isSuccess()) {
			throw new DidException(cptBaseInfo.getCode(), cptBaseInfo.getMsg());
		}

		return cptBaseInfo.getData();
	}

	/**
	 * Query all registered CPT templates under the issuer according to the DID
	 * identifier of the issuer.
	 * 
	 * @param query Page information and authority issuer
	 * @return Return the CPT template list
	 */
	public Pages<CptInfo> queryCptListByDid(QueryCptList queryCpt) {
		if (ObjectUtil.isEmpty(queryCpt)) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "query param is empty");
		}
		if (StringUtils.isEmpty(queryCpt.getDid())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "did is empty");
		}
		if (ObjectUtil.isEmpty(queryCpt.getPage())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "page is empty");
		}
		if (ObjectUtil.isEmpty(queryCpt.getSize())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "size is empty");
		}
		RequestParam<QueryCptListWrapper> reqParam = new RequestParam<>(this.getProjectId(), queryCpt.getDid());
		QueryCptListWrapper queryCptListWrapper = new QueryCptListWrapper();
		queryCptListWrapper.setPage(queryCpt.getPage());
		queryCptListWrapper.setSize(queryCpt.getSize());
		queryCptListWrapper.setDid(queryCpt.getDid());
		reqParam.setData(queryCptListWrapper);

		ResultData<Map> cptListResult = HttpUtils.postCall(this.getUrl() + ServiceURL.QUERY_CPT_DID, this.getToken(),
				reqParam, Map.class);
		if (!cptListResult.isSuccess()) {
			throw new DidException(cptListResult.getCode(), cptListResult.getMsg());
		}

		Pages<CptInfo> pages = this.parseToPage(cptListResult.getData(), AuthorityIssuer.class);
		return pages;
	}

	/**
	 * Query CPT declaration information based on the CPT template ID.
	 * 
	 * @param cptId The CPT template Id
	 * @return Return the CPT template detail information
	 */
	public CptInfo queryCptById(Long cptId) {
		if (cptId == null) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "cpt id is empty");
		}
		RequestParam<QueryCptByIdWrapper> reqParam = new RequestParam<>(this.getProjectId(), null);
		QueryCptByIdWrapper query = new QueryCptByIdWrapper();
		query.setCptId(cptId);
		reqParam.setData(query);

		ResultData<CptInfo> cptInfo = HttpUtils.postCall(this.getUrl() + ServiceURL.QUERY_CPT_INFO, this.getToken(),
				reqParam, CptInfo.class);
		if (!cptInfo.isSuccess()) {
			throw new DidException(cptInfo.getCode(), cptInfo.getMsg());
		}

		return cptInfo.getData();
	}

	/**
	 * The issuer updates the declaration content in the CPT template that it has
	 * already registered.
	 * 
	 * @param registerCpt Update CPT template information
	 * @return Return the new CPT template Id and version
	 */
	public CptBaseInfo updateCpt(RegisterCpt registerCpt) {
		if (ObjectUtil.isEmpty(registerCpt)) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "register cpt is empty");
		}
		if (ObjectUtil.isEmpty(registerCpt.getCptId())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "cpt id is empty");
		}
		if (StringUtils.isEmpty(registerCpt.getDescription())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "description is empty");
		}
		if (StringUtils.isEmpty(registerCpt.getTitle())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "title is empty");
		}
		if (StringUtils.isEmpty(registerCpt.getPrivateKey())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "private key is empty");
		}
		if (StringUtils.isEmpty(registerCpt.getType())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "type key is empty");
		}
		if (StringUtils.isEmpty(registerCpt.getDid())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "did is empty");
		}
		if (ObjectUtil.isEmpty(registerCpt.getCptJsonSchema())) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "cpt json schema is empty");
		}
		RegisterCptWrapper registerCptWrapper = new RegisterCptWrapper();
		CptInfo onChainCptInfo = this.queryCptById(registerCpt.getCptId());
		if (onChainCptInfo == null) {
			throw new DidException(ErrorMessage.CPT_NOT_EXIST.getCode(), ErrorMessage.CPT_NOT_EXIST.getMessage());
		}
		registerCptWrapper.setCptId(onChainCptInfo.getCptId());
		registerCptWrapper.setCptJsonSchema(onChainCptInfo.getCptJsonSchema());

		registerCptWrapper.setTitle(onChainCptInfo.getTitle());
		registerCptWrapper.setDescription(onChainCptInfo.getDescription());
		registerCptWrapper.setCreate(onChainCptInfo.getCreate());
		String nowStr = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		registerCptWrapper.setUpdate(nowStr);
		if (registerCpt.getDid() != null && !registerCpt.getDid().trim().isEmpty()) {
			registerCptWrapper.setDid(registerCpt.getDid());
		}
		if (registerCpt.getCptJsonSchema() != null) {
			registerCptWrapper.setCptJsonSchema(registerCpt.getCptJsonSchema());
		}
		if (registerCpt.getTitle() != null && !registerCpt.getTitle().trim().isEmpty()) {
			registerCptWrapper.setTitle(registerCpt.getTitle());
		}
		if (registerCpt.getDescription() != null && !registerCpt.getDescription().trim().isEmpty()) {
			registerCptWrapper.setDescription(registerCpt.getDescription());
		}
		if (registerCpt.getType() != null && !registerCpt.getType().trim().isEmpty()) {
			registerCptWrapper.setType(registerCpt.getType());
		}

		CptInfo cptInfo = CptUtils.assemblyCptInfo(registerCptWrapper);
		Map<String, JsonSchema> cptJsonSchema = new TreeMap<>();

		if (cptInfo.getCptJsonSchema() != null) {
			cptInfo.getCptJsonSchema().forEach((key, val) -> {
				JsonSchema jsonSchema = new JsonSchema();
				jsonSchema.setDescription(val.getDescription());
				jsonSchema.setRequired(val.getRequired());
				jsonSchema.setType(val.getType());
				cptJsonSchema.put(key, jsonSchema);
			});
		}
		cptInfo.setCptJsonSchema(cptJsonSchema);

		cptInfo.setCptVersion(onChainCptInfo.getCptVersion() + 1);
		String signValue = null;
		try {
			signValue = ECDSAUtils.sign(JSONArray.toJSON(cptInfo).toString(), registerCpt.getPrivateKey());
		} catch (Exception e1) {
			e1.printStackTrace();
			throw new DidException(ErrorMessage.SIGNATURE_FAILED.getCode(), ErrorMessage.SIGNATURE_FAILED.getMessage());
		}

		Proof proof = new Proof();
		proof.setCreator(registerCpt.getDid());
		proof.setSignatureValue(signValue);
		proof.setType("Secp256k1");
		registerCptWrapper.setProof(proof);

		String sign = Signatures.get().setInfo(this.getProjectId(), registerCpt.getDid())
				.add("cptId", registerCptWrapper.getCptId()).add("did", registerCptWrapper.getDid())
				.add("jsonSchema", registerCptWrapper.getCptJsonSchema()).add("title", registerCptWrapper.getTitle())
				.add("description", registerCptWrapper.getDescription()).add("type", registerCptWrapper.getType())
				.add("proof", registerCptWrapper.getProof()).add("created", registerCptWrapper.getCreate())
				.add("updated", registerCptWrapper.getUpdate()).sign(registerCpt.getPrivateKey());

		RequestParam<RegisterCptWrapper> reqParam = new RequestParam<>(this.getProjectId(), registerCpt.getDid());
		reqParam.setData(registerCptWrapper);
		reqParam.setSign(sign);

		ResultData<CptBaseInfo> cptBaseInfo = HttpUtils.postCall(this.getUrl() + ServiceURL.UPDATE_CPT, this.getToken(),
				reqParam, CptBaseInfo.class);
		if (!cptBaseInfo.isSuccess()) {
			throw new DidException(cptBaseInfo.getCode(), cptBaseInfo.getMsg());
		}

		return cptBaseInfo.getData();
	}

}
