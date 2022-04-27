package com.reddate.did.sdk.constant;

/**
 * The request URL constant of the did service in BSN.
 */
public class ServiceURL {

	/**
	 * URL of create did service endpoint in BSN
	 */
	public static final String PUT_DID_ON_CHAIN = "/did/putDoc";

	/**
	 * URL of verify did document service endpoint in BSN
	 */
	public static final String VERIFY_DID_DOCUMENT = "/did/verifyDoc";

	/**
	 * URL of query did document service endpoint in BSN
	 */
	public static final String GET_DID_DOCUMENT = "/did/getDoc";

	/**
	 * URL of reset did document main authenticate service endpoint in BSN
	 */
	public static final String REST_DID_AUTH = "/did/resetDidAuth";
	
	/**
	 * URL of verify did identifier sign service endpoint in BSN
	 * 
	 */
	public static final String VERIFY_DID_SIGN = "/did/verifyDIdSign";

	/**
	 * URL of register authenticate issuer service endpoint in BSN
	 */
	public static final String REGISTER_AUTH_ISSUER = "/did/registerAuthIssuer";

	/**
	 * URL of query authenticate issuer service endpoint in BSN
	 */
	public static final String QUERY_AUTH_ISSUER_LIST = "/did/queryAuthIssuerList";

	/**
	 * URL of register CPT template service endpoint in BSN
	 */
	public static final String REGISTER_CPT = "/did/registerCpt";

	/**
	 * URL of query register CPT template list service endpoint in BSN
	 */
	public static final String QUERY_CPT_DID = "/did/queryCptList";

	/**
	 * URL of query register CPT template detail information service endpoint in BSN
	 */
	public static final String QUERY_CPT_INFO = "/did/queryCptById";

	/**
	 * URL of update register CPT template service endpoint in BSN
	 */
	public static final String UPDATE_CPT = "/did/updateCpt";

	/**
	 * URL of create credential service endpoint in BSN
	 */
	public static final String CREATE_CREDENTIAL = "/did/createCredential";

	/**
	 * URL of verify issued credential service endpoint in BSN
	 */
	public static final String VERIFY_CREDENTIAL = "/did/verifyCredential";

	/**
	 * URL of revoke issued credential service endpoint in BSN
	 */
	public static final String REVOKED_CRED = "/did/revokeCredential";

	/**
	 * URL of query revoke issued credential service endpoint in BSN
	 */
	public static final String GET_REVOKED_CRED_LIST = "/did/getRevokedCredList";

	/**
	 * URL of register an identify HUB user service endpoint in BSN
	 */
	public static final String HUB_REGISTER = "/hub/regiter";
	
	public static final String HUB_QUEERY_TYPE = "/hub/getType";

	/**
	 * URL of register an identify HUB user by public key service endpoint in BSN
	 */
	public static final String HUB_REGISTER_BY_PUBLIOCKEY = "/hub/regiterByIdPublicKey";
	
	/**
	 * URL of save some resource to identify HUB service endpoint in BSN
	 */
	public static final String HUB_SAVE_RESOURCE = "/hub/saveResource";

	/**
	 * URL of query resource in identify HUB service endpoint in BSN
	 */
	public static final String HUB_QUERY_RESOURCE = "/hub/getResource";

	/**
	 * URL of query resource history in identify HUB service endpoint in BSN
	 */
	public static final String HUB_QUERY_RESOURCE_HISTORY = "/hub/getResourceHistory";

	/**
	 * URL of delete resource in identify HUB service endpoint in BSN
	 */
	public static final String HUB_DELETE_RESOURCE = "/hub/deleteResource";

	/**
	 * URL of create permission for saved resource(s) in identify HUB service
	 * endpoint in BSN
	 */
	public static final String HUB_CREATE_PERMISSION = "/hub/createPerm";

	/**
	 * URL of delete created permission for saved resource(s) in identify HUB
	 * service endpoint in BSN
	 */
	public static final String HUB_DELETE_PERMISSION = "/hub/deletePermission";

	/**
	 * URL of query created grant for other identify HUB user in identify HUB
	 * service endpoint in BSN
	 */
	public static final String HUB_QUERY_PERMISSION = "/hub/queryPermission";

	/**
	 * URL of query grant resource to yourself in identify HUB service endpoint in
	 * BSN
	 */
	public static final String HUB_QUERY_GRANT_PERMISSION = "/hub/queryGrantedPermission";

	/**
	 * URL of check grant that if one identify HUB user have the grant for the
	 * resource in identify HUB service endpoint in BSN
	 */
	public static final String HUB_CHECK_PERMISSION = "/hub/checkPermission";

	
	/**
	 * URL of query current block info in BSN
	 */
	public static final String GET_BLOCK_INFO = "/did/getBlockInfo";
	
	/**
	 * URL of transfer owner in identify HUB service endpoint in BSN
	 */
	public static final String HUB_TRANSFER_OWNER = "/hub/transferOwner";
	
}
