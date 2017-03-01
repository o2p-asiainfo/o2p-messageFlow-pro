package com.ailk.o2p.messageFlow.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Validator;

import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import com.ailk.o2p.messageFlow.utils.CommonConfigurations;
import com.ailk.o2p.messageFlow.utils.CustomStringUtils;
import com.ailk.o2p.messageFlow.utils.PropLoaderFactory;
import com.ailk.o2p.messageFlow.utils.PropertiesLoader;
import com.ailk.o2p.messageFlow.utils.SystemKeyWords;
import com.asiainfo.foundation.log.Logger;

/**
 * 
 * @ClassName: BaseController
 * 
 * @Description: 控制器基类
 * 
 * @author 庄敬飞
 * 
 * @date 2015-5-12 上午11:36:10
 * 
 * 
 */
public class BaseController {

	protected static final String RESULT_OK = "0000";
	protected static final String RESULT_ERR = "0001";
	protected static final String RESULT_Exit = "0002";
	protected static final String RESULT_Used = "0003";
	protected static final String RETURN_CODE = "code";
	protected static final String RETURN_DESC = "desc";
	private static final Logger log = Logger.getLog(BaseController.class);

	private static PropertiesLoader i18nLoader = null;

	private static Map<String, String> configurations = null;
	
	@Autowired
	protected Validator validator;

	static {
		log.info("BaseController");
		i18nLoader = PropLoaderFactory
				.getPropertiesLoader(SystemKeyWords.I18N_LOADER_NAME);
		configurations = CommonConfigurations.getConfigurations();
		log.info("i18nLoader:{0}", i18nLoader);
		log.info("configurations:{0}", configurations);
	}

	/**
	 * @author 庄敬飞
	 * @Title: getClientIp
	 * @Description: 取客户端IP
	 * @param @param request
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	public String getClientIp(HttpServletRequest request) {
		return CustomStringUtils.getRemoteAddr(request);
	}

	/**
	 * @author 庄敬飞
	 * @Title: addTranslateMessage
	 * @Description: 增加需要国际化的消息并推送到页面
	 * @param @param mv
	 * @param @param messages 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	protected void addTranslateMessage(ModelAndView mv, List<String> messages) {
		Map<String, String> map = new HashMap<String, String>();
		for (String message : messages) {
			String value = getI18nMessage(message);
			map.put(message, value);
		}
		mv.addObject(SystemKeyWords.LOCAL_ATTRIBUTE_NAME, map);
	}

	protected String getI18nMessage(String key) {
		return i18nLoader.getProperty(key);
	}

	protected String getRequestValue(final HttpServletRequest request,
			String paramName, boolean escape) {
		String paramValue = request.getParameter(paramName);
		if (null != paramValue) {
			paramValue = CustomStringUtils.trim(paramValue);
			if (escape) {
//				paramValue = StringEscapeUtils.escapeHtml(paramValue);
				paramValue = StringEscapeUtils.escapeSql(paramValue);
			}
			return paramValue;
		} else {
			return CustomStringUtils.EMPTY;
		}
	}

	protected String getRequestValue(final HttpServletRequest request,
			String paramName) {
		return getRequestValue(request, paramName, true);
	}
	
	/**
	* @author 庄敬飞
	* @Title: validateWithException
	* @Description: 值对象验证
	* @param @param object
	* @param @return    设定文件
	* @return JSONObject    返回类型
	* @throws
	*/ 
//	protected <T> JSONObject validateWithException(T object) {
//		JSONObject json = new JSONObject();
//		try {
//			BeanValidators.validateWithException(validator, object);
//			json.put(RETURN_CODE, RESULT_OK);
//		} catch (ConstraintViolationException cve) {
//			Map<String, String> map = BeanValidators
//					.extractPropertyAndMessage(cve);
//			Set<String> keys = map.keySet();
//			for (String key : keys) {
//				String messageTemplate = MapUtils.getString(map, key);
//				map.put(key, i18nLoader.getProperty(messageTemplate));
//			}
//			json.put(RETURN_CODE, RESULT_ERR);
//			json.put(RETURN_DESC,
//					JSONObject.fromObject(map).toString());
//		}
//		return json;
//	}
}
