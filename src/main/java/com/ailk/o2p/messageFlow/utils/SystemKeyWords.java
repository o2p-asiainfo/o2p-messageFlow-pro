package com.ailk.o2p.messageFlow.utils;

public final class SystemKeyWords {
	private SystemKeyWords() {

	}

	public static final String DEFAULT_CHARSET = "UTF-8";
	public static final String I18N_LOADER_NAME = "i18nLoader";
	private static String appPath = "";
	public static final String LOCAL_ATTRIBUTE_NAME = "local";
	public static final String SESSIONID_COOKIE = "SESSIONID";
	public static final int SESSION_STORE_REDIS = 0;
	public static final String VALIDATION_CODE = "validation_code";
	public static final String CONTEXT_STYLE_THEME = "contextStyleTheme";
	public static final String CONTEXT_STYLE_SPECIAL = "contextStyleSpecial";
	public static final String CONTEXT_MENU_BELONGTO = "contextMenuBelongto";
	public static final String LOCAL_NAME="localeName";
	
	public static String getAPP_PATH() {
		return appPath;
	}
	
	public static void setAPP_PATH(String appPath_) {
		appPath = appPath_;
	}

	
	
}
