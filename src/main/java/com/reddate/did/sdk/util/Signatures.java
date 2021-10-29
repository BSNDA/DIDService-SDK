package com.reddate.did.sdk.util;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 * Signatures relate utils method function
 * 
 * 
 *
 */
public class Signatures {
	
	private String projectId;
	
	private String did;
	
	private Map<String,String> map = new TreeMap<>();
	
	
	public static Signatures get() {
		return new Signatures();
	}
	
	/**
	 * Set the project Id and did to original sign String
	 * 
	 * 
	 * @param projectId
	 * @param did
	 * @return
	 */
	public Signatures setInfo(String projectId,String did) {
		if(projectId == null || projectId.trim().isEmpty()) {
			throw new RuntimeException("project id is empty");
		}
		this.projectId = projectId;
		
		if(did == null || did.trim().isEmpty()) {
			throw new RuntimeException("did is empty");
		}
		this.did = did;
		return this;
	}
	
	/**
	 * 
	 * Add a value to the sign String
	 * 
	 * @param name
	 * @param value
	 * @return
	 */
	public Signatures add(String name,Object value) {
		if(name == null || name.trim().isEmpty()) {
			throw new RuntimeException("name is empty");
		}
		if(value != null) {
			if(value instanceof Map) {
				Map<String,Object> dataMap = (Map<String,Object>)value;
				TreeMap<String,Object> tmpMap =new TreeMap<>();
				dataMap.forEach((k,v) -> {
					tmpMap.put(k, v);
				});
			}else {
				map.put(name, JSONObject.toJSONString(value));
			}
			
			
			
		}
		return this;
	}
	
	/**
	 * 
	 * Get the sign value for input value and private key
	 * 
	 * 
	 * @param privateKey
	 * @return
	 */
	public String sign(String privateKey) {
		if(privateKey == null || privateKey.trim().isEmpty()) {
			throw new RuntimeException("private key is empty");
		}
		try {
			return ECDSAUtils.sign(getSignStr(), privateKey);
		} catch (Exception e) {
			throw new RuntimeException("sign data failed:"+e.getMessage());
		}
	}

	/**
	 * 
	 * Verify the Sign Value
	 * 
	 * @param publicKey
	 * @param signValue
	 * @return
	 */
	public boolean verify(String publicKey,String signValue) {
		if(publicKey == null || publicKey.trim().isEmpty()) {
			throw new RuntimeException("public key is empty");
		}
		if(signValue == null || signValue.trim().isEmpty()) {
			throw new RuntimeException("sign value is empty");
		}
		try {
			return ECDSAUtils.verify(getSignStr(), publicKey, signValue);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("vefify sign failed:"+e.getMessage());
		}
	}
	
	/**
	 * 
	 * Get the sign original String
	 * 
	 * @return
	 */
	public String getSignStr() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(projectId);
		buffer.append(did);
		if(!map.isEmpty()) {
			Iterator<String> it = map.keySet().iterator();
			while(it.hasNext()) {
				String key = it.next();
				buffer.append(map.get(key));
			}
		}
//		System.out.println("the sign or verify original String is = "+buffer.toString());
		return buffer.toString();
	}
	
	
}
