package com.ailk.o2p.messageFlow.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public final class CommonConfigurations {

	private static Map<String, String> configurationsMap;

	private CommonConfigurations() {

	}

	public static void addProperties(Properties props) {
		if (configurationsMap == null) {
			configurationsMap = new HashMap<String, String>();
		}
		if (null != props && props.size() > 0) {
			for (Map.Entry<Object, Object> entry : props.entrySet()) {
				String key = entry.getKey().toString();
				String value = entry.getValue().toString();
				configurationsMap.put(key, value);
			}
		}
	}

	public static Map<String, String> getConfigurations() {
		return configurationsMap;
	}

}
