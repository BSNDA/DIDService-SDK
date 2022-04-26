package com.reddate.did.sdk.protocol.response;

import java.io.Serializable;
import java.math.BigInteger;

public class BlockInfoResp implements Serializable{

	private String blockNumber;
	
	private String groupId;

	public String getBlockNumber() {
		return blockNumber;
	}

	public void setBlockNumber(String blockNumber) {
		this.blockNumber = blockNumber;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	
	
	
}
