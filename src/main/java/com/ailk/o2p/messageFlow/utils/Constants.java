package com.ailk.o2p.messageFlow.utils;

public class Constants {
	/**
	 * 密码默认值
	 */
	protected static final String defaultPassword = "1";
	/**
	 * 密码失效时间 单位：天
	 */
	private int passwordTimeout;
	/**
	 * 短信验证码失效时间 单位：分钟
	 */
	private int authCodeTimeout;
	
	public  String getDefaultPassword() {
		return defaultPassword;
	}
	public  int getPasswordTimeout() {
		return passwordTimeout;
	}
	public  void setPasswordTimeout(int passwordTimeout_) {
		passwordTimeout = passwordTimeout_;
	}
	public  int getAuthCodeTimeout() {
		return authCodeTimeout;
	}
	public  void setAuthCodeTimeout(int authCodeTimeout_) {
		authCodeTimeout = authCodeTimeout_;
	}
	
}
