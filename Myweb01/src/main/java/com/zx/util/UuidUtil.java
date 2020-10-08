package com.zx.util;

import java.util.UUID;

/**
 * 产生UUID随机字符串工具类  产生一个全世界不会重复的固定长度的字符串
 */
public final class UuidUtil {
	private UuidUtil(){}
	public static String getUuid(){
		return UUID.randomUUID().toString().replace("-","");
	}

}
