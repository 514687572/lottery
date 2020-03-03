package com.lottery.net.utils;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

public class JsonUtil {

	private JsonUtil() {
		super();
	}

	public static String buildJson(int msgCode, Map<String, Object> data) {
		Map<String, Object> map = new HashMap<>();
		map.put("code", msgCode);
		map.put("data", new Gson().toJson(data));
		return new Gson().toJson(map);
	}
}