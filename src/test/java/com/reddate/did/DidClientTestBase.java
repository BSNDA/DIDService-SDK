package com.reddate.did;

import com.reddate.did.sdk.DidClient;

public class DidClientTestBase {

	public DidClient getDidClient() {
//		String url = "http://127.0.0.1:19004";
//		String token = "123456789";
//		String projectId = "123";
		
		// internationality
//		String url = "https://did.bsngate.com:18602";
		// cn internal
		String url = "https://didservice.bsngate.com:18602";
		String token = "3wxYHXwAm57grc9JUr2zrPHt9HC";
		String projectId = "8320935187";		
		
		DidClient didClient = new DidClient(url,projectId,token);
		return didClient;
	}
	
	
}
