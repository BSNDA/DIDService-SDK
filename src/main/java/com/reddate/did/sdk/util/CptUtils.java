package com.reddate.did.sdk.util;



import com.reddate.did.sdk.protocol.request.CptInfo;
import com.reddate.did.sdk.protocol.request.RegisterCptWrapper;

/**
 * 
 * CPT template utils class 
 * 
 * 
 * 
 *
 */
public class CptUtils {

	/**
	 * 
	 * Parse CPT information to CPT Object
	 * 
	 * 
	 * @param registerCpt
	 * @return
	 */
    public static CptInfo assemblyCptInfo(RegisterCptWrapper registerCpt){
        CptInfo cptInfo = new CptInfo();
        cptInfo.setPublisherDid(registerCpt.getDid());
        cptInfo.setTitle(registerCpt.getTitle());
        cptInfo.setDescription(registerCpt.getDescription());
        cptInfo.setCptJsonSchema(registerCpt.getCptJsonSchema());
        cptInfo.setCreate(registerCpt.getCreate());
        cptInfo.setUpdate(registerCpt.getUpdate());
        cptInfo.setCptId(registerCpt.getCptId());
        cptInfo.setCptVersion(1);
        return cptInfo;
    }
	
}
