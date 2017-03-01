package com.sso;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ailk.eaap.op2.common.EAAPConstants;
import com.asiainfo.integration.o2p.session.web.http.CookieUtil;
import com.asiainfo.integration.o2p.session.web.http.O2pCookie;
import com.asiainfo.portal.framework.external.DefaultPopedomImpl;
import com.asiainfo.portal.framework.external.IPopedom;
import com.asiainfo.portal.framework.external.OperInfo;
import com.asiainfo.portal.framework.external.PortalDataFetch;

/**
 * CRM侧SSO客户端实现类
 * 
 * 
 */
public class SSOPopedomImpl extends DefaultPopedomImpl implements IPopedom {

	private static final String PORTAL_POPEDOMIMPL_SESSION = "PORTAL_POPEDOMIMPL_SESSION"; // 根据需要可改变为接入系统本身的SESSION

	public boolean logout(String arg0, HttpSession session) {
		boolean flag = false;
		if (session == null) {
			flag = true;
		} else {
			String serialId = "";
			if (null != session.getAttribute("USERINFO_ATTR")) {
				serialId = (String) session.getAttribute("USERINFO_ATTR");
			}
			if (null == serialId || "".equals(serialId)) {
				flag = true;
			} else {
				session.removeAttribute("USERINFO_ATTR");
				flag = true;
			}
			session.invalidate();
		}
		return flag;
	}

	@Override
	protected boolean doSelfSession(HttpServletRequest request,
			HttpServletResponse response, OperInfo operInfo) {
		boolean isSuccess = false;
		String code = operInfo.getOplogname();
		// String operId = operInfo.getOpId();
		// String path = request.getServletPath();
		// cookie中获取sessionID,塞进Session,作为用户SSO模拟登录成功的标识
		String sessionId = PortalDataFetch.getSessionId(request);
		request.getSession().setAttribute("_SSO_CLIENT_SESSIONID", sessionId);
		if (null != sessionId) {
			request.getSession().setAttribute("userName", code);
			CookieUtil.addCookie(new O2pCookie(EAAPConstants.O2P_USER_NAME,
					"mawl"), response);
			isSuccess = true;
		}
		return isSuccess;
	}

	/**
	 * 把用户名放入cookie
	 */
	// private void addUserNameToCookie(String userName,HttpServletResponse
	// response){
	// String userNameBase64 = CustomBase64.encode(userName.getBytes());
	// String cookieValue = EAAPConstants.O2P_USER_NAME + "=" + userNameBase64 +
	// ";Path=/;HttpOnly;";
	// response.setHeader("SET-COOKIE", cookieValue);
	// }
	@Override
	protected boolean isLogin(HttpServletRequest request,
			HttpServletResponse response) {
		boolean flag = false;
		// cookie 中新的sso 票据
		String ssoSessionId = PortalDataFetch.getSessionId(request);
		// 自身会话关系的sso 票据
		String sessionId = (String) request.getSession().getAttribute(
				"_SSO_CLIENT_SESSIONID");
		OperInfo operInfo = null;
		try {
			operInfo = PortalDataFetch.getOperInfo(request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		HttpSession session = request.getSession(false);
		if (session != null
				&& session.getAttribute(PORTAL_POPEDOMIMPL_SESSION) != null) {
			if (ssoSessionId != sessionId) {
				doSelfSession(request, response, operInfo);
				flag = false;
			} else {
				flag = true;
			}
		}
		return flag;

	}

	// private String getAddress(String url){
	// Properties prop = null;
	// InputStream fis = null;
	// String addressResult = "";
	// if(null != url && !"".equals(url)){
	// fis = this.getClass().getResourceAsStream(url);
	// prop = new Properties();
	// try {
	// prop.load(fis);
	// String address = prop.getProperty("sso.address");
	// if(null != address){
	// addressResult = address;
	// }
	// } catch (FileNotFoundException e) {
	// log.error(LogModel.EVENT_APP_EXCPT, new
	// BusinessException(ExceptionCommon.WebExceptionCode,e.getMessage(),null));
	// } catch (IOException e){
	// log.error(LogModel.EVENT_APP_EXCPT, new
	// BusinessException(ExceptionCommon.WebExceptionCode,e.getMessage(),null));
	// }finally{
	// if(null != fis){
	// try {
	// fis.close();
	// } catch (IOException e) {
	// log.error(LogModel.EVENT_APP_EXCPT, new
	// BusinessException(ExceptionCommon.WebExceptionCode,e.getMessage(),null));
	// }
	// }
	// }
	// }
	// return addressResult;
	// }
}
