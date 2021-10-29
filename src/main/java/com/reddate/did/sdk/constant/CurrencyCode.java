package com.reddate.did.sdk.constant;

/**
 * Did document constant text definition, and the static content fields definition in the document.
 * 
 * 
 *
 */
public class CurrencyCode {

    /**
     * Did prefix
     */
    public static final String DID_PREFIX = "did";

    /**
     * Did project name
     */
    public static final String DID_PROJECT_NAME = "bsn";

    /**
     * Did separator
     */
    public static final String DID_SEPARATOR = ":";
    /**
     *
     */
    public static final String DID_FORMAT_PREFIX = DID_PREFIX + DID_SEPARATOR + DID_PROJECT_NAME + DID_SEPARATOR;

    /**
     * W3C format standard address
     */
    public static final String W3C_FORMAT_ADDRESS = "https://w3id.org/did/v1";

    /**
     * Default version number
     */
    public static final String DEFAULT_VERSION = "1";

    /**
     * Primary public key ID
     */
    public static final String PRIMARY_PUBLIC_KEY_ID = "#keys-1";

    /**
     * Alternate public key ID
     */
    public static final String ALTERNATE_PUBLIC_KEY_ID = "#keys-2";

    /**
     * Regular verification of did identifier
     */
    public static final String DID_IDENTIFIER_PATTERN = "[a-zA-Z0-9]{27,28}";

    /**
     * Maximum length of issuer's name
     */
    public static final Integer MAX_AUTHORITY_ISSUER_NAME_LENGTH = 200;

    /**
     * W3C format standard certificate standard address
     */
    public static final String DEFAULT_CREDENTIAL_CONTEXT = "https://www.w3.org/2018/credentials/v1";

    /**
     * Registration voucher template default type
     */
    public static final String REGISTRATION_VOUCHER_TEMPLATE_TYPE = "Proof";

}
