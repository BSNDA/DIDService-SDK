package com.reddate.did.sdk.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;

/**
 * Get long unique ID
 */
public class GenerateCptIdUtils {

	/**
	 * generated a CPT template Id utils function
	 * 
	 * @return return the Id value
	 */
	public static long getId() {
		StringBuffer generateId = new StringBuffer();
		generateId.append(RandomUtil.randomString("123456789", 2))
				.append(DateUtil.format(DateUtil.date(), "yyMMddHHmmssSSS"))
				.append(RandomUtil.randomString("123456789", 1));
		return Long.parseLong(generateId.toString());
	}

}
