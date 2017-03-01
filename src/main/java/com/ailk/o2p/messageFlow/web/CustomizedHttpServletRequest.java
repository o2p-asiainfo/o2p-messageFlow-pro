package com.ailk.o2p.messageFlow.web;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;

public class CustomizedHttpServletRequest extends HttpServletRequestWrapper
		implements HttpServletRequest {

	private HttpServletRequest currentRequest;
	private ServletContext currentServletContext;
	private String sid;

	public CustomizedHttpServletRequest(String defineSid,
			HttpServletRequest request, ServletContext servletContext) {
		super(request);
		currentRequest = request;
		currentServletContext = servletContext;
		sid = defineSid;
	}

	@Override
	public HttpSession getSession() {
		return new CustomizedHttpSession(sid, currentRequest.getSession(),
				currentRequest, currentServletContext);
	}

}
