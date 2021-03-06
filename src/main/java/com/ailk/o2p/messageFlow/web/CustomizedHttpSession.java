package com.ailk.o2p.messageFlow.web;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

import com.ailk.o2p.messageFlow.cache.CacheFactory;
import com.ailk.o2p.messageFlow.cache.ICache;

public class CustomizedHttpSession implements HttpSession {
	protected ServletContext servletContext;
	protected HttpServletRequest currentRequest;
	protected HttpSession session;
	private Map<String, Object> map;
	private ICache cache;
	private String sid;

	public CustomizedHttpSession(String sid, HttpSession session,
			HttpServletRequest currentRequest, ServletContext servletContext) {
		this.session = session;
		this.currentRequest = currentRequest;
		this.servletContext = servletContext;
		this.sid = sid;
		// 初始化缓存对象
		cache = CacheFactory.newCacheInstance("REDIS");
		map = (Map<String, Object>) cache.get(sid);
		if (null == map) {
			map = new HashMap<String, Object>();
			cache.set(sid, map);
		}
	}

	public Object getAttribute(String key) {
		return map.get(key);
	}

	public Enumeration getAttributeNames() {
		return this.session.getAttributeNames();
	}

	public long getCreationTime() {
		return session.getCreationTime();
	}

	public String getId() {
		return session.getId();
	}

	public long getLastAccessedTime() {
		return session.getLastAccessedTime();
	}

	public int getMaxInactiveInterval() {
		return session.getMaxInactiveInterval();
	}

	public ServletContext getServletContext() {
		return session.getServletContext();
	}

	public HttpSessionContext getSessionContext() {
		return session.getSessionContext();
	}

	public Object getValue(String arg0) {
		return session.getValue(arg0);
	}

	public String[] getValueNames() {
		return session.getValueNames();
	}

	public void invalidate() {
		this.session.invalidate();
	}

	public boolean isNew() {
		return session.isNew();
	}

	public void putValue(String arg0, Object arg1) {
		session.putValue(arg0, arg1);
	}

	public void removeAttribute(String arg0) {
		this.session.removeAttribute(arg0);
	}

	public void removeValue(String arg0) {
		session.removeValue(arg0);
	}

	public void setAttribute(String key, Object value) {
		// this.session.setAttribute(arg0, arg1);
		map.put(key, value);
		cache.set(sid, map);
	}

	public void setMaxInactiveInterval(int arg0) {
		session.setMaxInactiveInterval(arg0);
	}

}
