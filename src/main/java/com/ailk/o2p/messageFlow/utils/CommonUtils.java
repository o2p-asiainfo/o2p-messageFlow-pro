package com.ailk.o2p.messageFlow.utils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;


import com.ailk.eaap.o2p.common.util.CustomBase64;
import com.ailk.eaap.op2.bo.Tenant;
import com.asiainfo.foundation.common.ExceptionCommon;
import com.asiainfo.foundation.exception.BusinessException;
import com.asiainfo.foundation.log.LogModel;
import com.asiainfo.foundation.log.Logger;
import com.linkage.rainbow.util.StringUtil;

public final class CommonUtils {

	private static final Logger log = Logger.getLog(CommonUtils.class);
	
	private CommonUtils(){
		
	}
	
	public static String genTransactionID() {
		String orderNum = new Date().hashCode() + "";
		String lpad = lpad(orderNum);
		return "1000010005"
				+ new SimpleDateFormat("yyyyMMdd").format(new Date()) + lpad;
	}

	public static String lpad(String number) {
		String f = "%0" + 10 + "d";
		return String.format(f, Integer.valueOf(number));
	}


	/**
	 * 获取配置文件值
	 * 
	 * @param proCode
	 * @return
	 */
	public static String getValueByProCode(String proCode) {
		Properties p = new Properties();
		try {
			p.load(CommonUtils.class
					.getResourceAsStream("/InterfaceURL.properties"));
			return (String) p.get(proCode);
		} catch (IOException e) {
			// /log.error(e.getStackTrace());
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(
					ExceptionCommon.WebExceptionCode,
					"Gets the configuration file value anomaly", null));
			return null;
		}
	}

	/**
	 * 获取配置文件中的中文值
	 * 
	 * @param proCode
	 * @return
	 */
	public static String getChineseValueByProCode(String proCode) {
		Properties p = new Properties();
		try {
			p.load(CommonUtils.class
					.getResourceAsStream("/eaap_common.properties"));
			return new String(((String) p.get(proCode)).getBytes("ISO-8859-1"),
					"utf-8");
		} catch (IOException e) {
			// /log.error(e.getStackTrace());
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(
					ExceptionCommon.WebExceptionCode,
					"Gets the configuration file in the Chinese value anomaly",
					null));
			return null;
		}
	}

	/**
	 * 从session里获租户对象（Tenant）
	 * @param session
	 * @return
	 */
	public static Tenant getTenant(Cookie[] cookies,HttpSession session){
		Tenant tenant = new Tenant();
		if(session != null){
			if(session.getAttribute("Tenant") != null){
				tenant = (Tenant) session.getAttribute("Tenant");
			}else{
				//将cookie里tenantId放入session
					if(cookies != null){
						String userTenantId="";
						String tenantId="";
						String tenantName="";
						String tenantCode="";
						String tenantLogo="";
						String tenantCountry="";
						String tenantProvince="";
						String tenantLanguage="";
						String tenantZipCode="";
						String tenantContractNum="";
						String tenantCurrency="";
						String tenantStatus="";
						String tenantTimeZone="";
						String tenantTheme="";
						for (Cookie cookie : cookies) {
						    if("Tenant.TenantId".equals(cookie.getName())){
						    	tenantId = getCookieVal(cookie);
								tenant.setTenantId(Integer.valueOf(tenantId));
						    }else if("Tenant.Name".equals(cookie.getName())){
						    	tenantName = getCookieVal(cookie);
								tenant.setName(tenantName);
						    }else if("Tenant.Code".equals(cookie.getName())){
						    	tenantCode = getCookieVal(cookie);
								tenant.setCode(tenantCode);
						    }else if("Tenant.Logo".equals(cookie.getName())){
						    	tenantLogo = getCookieVal(cookie);
								tenant.setLogo(tenantLogo);
						    }else if("Tenant.Country".equals(cookie.getName())){
						    	tenantCountry = getCookieVal(cookie);
								tenant.setCountry(tenantCountry);
						    }else if("Tenant.Province".equals(cookie.getName())){
						    	tenantProvince = getCookieVal(cookie);
								tenant.setProvince(tenantProvince);
						    }else if("Tenant.Language".equals(cookie.getName())){
						    	tenantLanguage = getCookieVal(cookie);
								tenant.setLanguage(tenantLanguage);
						    }else if("Tenant.ZipCode".equals(cookie.getName())){
						    	tenantZipCode = getCookieVal(cookie);
								tenant.setZipCode(tenantZipCode);
						    }else if("Tenant.ContractNum".equals(cookie.getName())){
						    	tenantContractNum = getCookieVal(cookie);
								tenant.setContractNum(tenantContractNum);
						    }else if("Tenant.Currency".equals(cookie.getName())){
						    	tenantCurrency = getCookieVal(cookie);
								tenant.setCurrency(tenantCurrency);
						    }else if("Tenant.Status".equals(cookie.getName())){
						    	tenantStatus = getCookieVal(cookie);
								tenant.setStatus(tenantStatus);
						    }else if("Tenant.TimeZone".equals(cookie.getName())){
						    	tenantTimeZone = getCookieVal(cookie);
								tenant.setTimeZone(tenantTimeZone);
						    }else if("Tenant.Theme".equals(cookie.getName())){
						    	tenantTheme = getCookieVal(cookie);
								tenant.setTheme(tenantTheme);
						    }
						}
				}
			
			}
		}
		return tenant;
	}

	public static String getCookieVal(Cookie cookie){
		String val = "";
		if(!StringUtil.isEmpty(cookie.getValue())){
			val = cookie.getValue();
			if(!StringUtil.isEmpty(val)){
				byte[] bytes =CustomBase64.decode(val);
				val = new String(bytes);
			}
		}
		return val;
	}
	
	
	
}
