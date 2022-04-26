package com.reddate.did.sdk.util;

import java.util.Map;

import com.reddate.did.sdk.constant.ErrorMessage;
import com.reddate.did.sdk.exception.DidException;
import com.reddate.did.sdk.protocol.request.JsonSchema;

public class CptJsonSchemaUtils {

	public static void validateJsonSchema(Map<String, JsonSchema> jsonSchemeMap) {
		if(jsonSchemeMap == null || jsonSchemeMap.isEmpty()) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "cptJsonSchema is empty");
		}
		
		if(jsonSchemeMap.size() == 1) {
			String jsonSchemaName = jsonSchemeMap.keySet().iterator().next();
			if(jsonSchemaName == null || jsonSchemaName.trim().isEmpty()) {
				throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "cptJsonSchema is empty");
			}
			JsonSchema jsonSchema = jsonSchemeMap.get(jsonSchemaName);
			if(jsonSchema == null) {
				throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "cptJsonSchema is empty");
			}
		}
		
		for(String jsonSchemaName : jsonSchemeMap.keySet()) {
			if(jsonSchemaName == null || jsonSchemaName.trim().isEmpty()) {
				throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "cptJsonSchema is empty");
			}
			
			JsonSchema jsonSchema = jsonSchemeMap.get(jsonSchemaName);
			if(jsonSchema == null) {
				throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "cptJsonSchema is empty");
			}
			
			if(jsonSchema.getType() == null || jsonSchema.getType().trim().isEmpty()) {
				throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "cptJsonSchema.type is empty");
			}
			
			if(jsonSchema.getDescription() == null || jsonSchema.getDescription().trim().isEmpty()) {
				throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "cptJsonSchema.description is empty");
			}
			
			if(jsonSchema.getRequired() == null) {
				throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "cptJsonSchema.required is empty");
			}
		}
	}
	
	
}
