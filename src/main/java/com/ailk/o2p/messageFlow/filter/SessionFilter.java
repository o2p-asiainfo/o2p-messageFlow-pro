package com.ailk.o2p.messageFlow.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.ailk.o2p.messageFlow.utils.CommonConfigurations;
import com.ailk.o2p.messageFlow.utils.SystemKeyWords;

public class SessionFilter implements Filter {

	private FilterConfig filterConfig;

	public void destroy() {
	}

	public void init(FilterConfig fc) throws ServletException {
		SystemKeyWords.setAPP_PATH(fc.getServletContext().getContextPath());
		fc.getServletContext()
				.setAttribute("APP_PATH", SystemKeyWords.getAPP_PATH());
		//样式属性设置
		fc.getServletContext()
		.setAttribute(SystemKeyWords.CONTEXT_STYLE_THEME,CommonConfigurations.getConfigurations().get(SystemKeyWords.CONTEXT_STYLE_THEME));
		fc.getServletContext()
		.setAttribute(SystemKeyWords.CONTEXT_STYLE_SPECIAL,CommonConfigurations.getConfigurations().get(SystemKeyWords.CONTEXT_STYLE_SPECIAL));
		fc.getServletContext()
		.setAttribute(SystemKeyWords.CONTEXT_MENU_BELONGTO,CommonConfigurations.getConfigurations().get(SystemKeyWords.CONTEXT_MENU_BELONGTO));
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {
		filterChain.doFilter(request, response);
	}

	public FilterConfig getFilterConfig() {
		return filterConfig;
	}

	public void setFilterConfig(FilterConfig filterConfig) {
		this.filterConfig = filterConfig;
	}
}
