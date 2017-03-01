package com.ailk.o2p.messageFlow.controller.messageFlow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ailk.eaap.op2.bo.Tenant;
import com.ailk.o2p.messageFlow.controller.BaseController;
import com.ailk.o2p.messageFlow.service.messageFlow.IMessageFlowService;
import com.ailk.o2p.messageFlow.utils.CommonUtils;
import com.asiainfo.integration.o2p.web.bo.UserRoleInfo;
import com.asiainfo.integration.o2p.web.util.WebConstants;

@Controller
public class MessageFlowController extends BaseController {

	@Autowired
	private IMessageFlowService messageFlowService;
	private static final Logger LOG = Logger.getLogger(MessageFlowController.class);
	
	@RequestMapping(value = "/messageFlow/main")
	public ModelAndView main(final HttpServletRequest request,
			final HttpServletResponse response) {
		boolean flag = false;//CRM登录标识
		try{
			String isLoginFlag = CommonUtils.getChineseValueByProCode("isCrmLoing");
			if(null != isLoginFlag && !"".equals(isLoginFlag)){
				if("Yes".equals(isLoginFlag)){
					flag = true;
				}
			}
		}catch(Exception e){
			flag = false;
		}
		
		List<Map<String,Object>> endpointSpecList = null;
		Map<String,Object> map = new HashMap<String,Object>();
		UserRoleInfo tU = (UserRoleInfo) request.getSession().getAttribute(WebConstants.O2P_SSO_USER_SESSION_KEY);//o2p管理员登录
		if(null != tU || flag){
			endpointSpecList = messageFlowService.getEndpointSpecList();
			map.put("isOpen", "Yes");
			if(null == tU){
				map.put("userName", "");
			}else{
				map.put("userName", tU.getName());
			}
		}else{
			map.put("isOpen", "NO");
		}
		List<Map<String,Object>> operatorList = messageFlowService.getOperatorListList();
		List<Map<String,Object>> in_Data_TypeList = messageFlowService.getData_TypeList();
		List<Map<String,Object>> ruleStrategyList = messageFlowService.getRuleStrategyList();
		List<Map<String,Object>>  condEvaluatorCollectionList = messageFlowService.getCondEvaluatorCollectionList();
		map.put("condEvaluatorCollectionList", condEvaluatorCollectionList);
		map.put("endpointSpecList", endpointSpecList);
		map.put("in_Data_TypeList", in_Data_TypeList);
		map.put("stateType", "new");//状态
		map.put("ruleStrategyList", ruleStrategyList);
		map.put("operatorList", operatorList);
		List<String> messages = this.getMainMessage();
		
		ModelAndView mv = new ModelAndView("main.jsp",map);
		addTranslateMessage(mv, messages);
		return mv;
	}
	
	private List<String> getMainMessage(){
		List<String> messages = new ArrayList<String>();
		// 添加页面上需要国际化的消息
		messages.add("eaap.op2.conf.messageArrange.tab.properties.allow");
		messages.add("eaap.op2.conf.messageArrange.tab.properties.notAllow");
		messages.add("eaap.op2.conf.messageArrange.tab.properties.endpointID");
		messages.add("eaap.op2.conf.messageArrange.tab.properties.endpointName");
		messages.add("eaap.op2.conf.messageArrange.tab.properties.specificationName");
		messages.add("eaap.op2.conf.messageArrange.tab.properties.specificationCode");
		messages.add("eaap.op2.conf.messageArrange.tab.properties.inputDataType");
		messages.add("eaap.op2.conf.messageArrange.tab.properties.outputDataType");
		messages.add("eaap.op2.conf.messageArrange.tab.properties.allowInputTrace");
		messages.add("eaap.op2.conf.messageArrange.tab.properties.allowInputDataLog");
		messages.add("eaap.op2.conf.messageArrange.tab.properties.endpointDescription");
		messages.add("eaap.op2.conf.messageArrange.tab.properties.specificationDescription");
		messages.add("eaap.op2.conf.messageArrange.dialog.head.messageFlowName");
		messages.add("eaap.op2.conf.messageArrange.menu.title.messageFlow");
		messages.add("eaap.op2.conf.messageArrange.dialog.button.close");
		messages.add("eaap.op2.conf.messageArrange.dialog.button.ok");
		messages.add("eaap.op2.conf.messageArrange.dialog.button.add");
		messages.add("eaap.op2.conf.messageArrange.menu.title.open");
		messages.add("eaap.op2.conf.messageArrange.menu.title.save");
		messages.add("eaap.op2.conf.server.supplier.choose");
		messages.add("eaap.op2.conf.messageArrange.menu.title.line");
		messages.add("eaap.op2.conf.messageArrange.menu.title.delete");
		messages.add("eaap.op2.conf.messageArrange.menu.title.help");
		messages.add("eaap.op2.conf.messageArrange.dialog.head.messageFlowID");
		messages.add("eaap.op2.conf.messageArrange.error.pageConfig");
		messages.add("eaap.op2.conf.messageArrange.tab.title.endpointProperty");
		messages.add("eaap.op2.conf.messageArrange.dialog.title.messageflowlist");
		/////
		messages.add("eaap.op2.conf.messageArrange.error.addfirsts");
		messages.add("eaap.op2.conf.messageArrange.error.lineExists");
		messages.add("eaap.op2.conf.messageArrange.error.messageNameNotNull");
		messages.add("eaap.op2.conf.messageArrange.error.oneRecords");
		messages.add("eaap.op2.conf.messageArrange.error.dataNotComplete");
		messages.add("eaap.op2.conf.messageArrange.error.noData");
		messages.add("eaap.op2.conf.messageArrange.error.oneBeginNode");
		messages.add("eaap.op2.conf.messageArrange.error.oneEndNode");
		messages.add("eaap.op2.conf.techimpl.attrSpecName");
		messages.add("eaap.op2.conf.messageArrange.menu.title.endpoint");
		messages.add("eaap.op2.conf.messageArrange.error.notEmpty");
		messages.add("eaap.op2.conf.messageArrange.alert.info.saveErrot");
		messages.add("eaap.op2.conf.messageArrange.alert.info.saveAndRefresh");
		messages.add("eaap.op2.conf.messageArrange.error.detail");
		messages.add("eaap.op2.conf.messageArrange.tab.properties.synchronization");
		messages.add("eaap.op2.conf.messageArrange.tab.properties.asynchronization");
		//
		messages.add("eaap.op2.conf.messageArrange.tab.properties.messageRouteMethod");
		messages.add("eaap.op2.conf.messageArrange.tab.properties.routePolicy");
		messages.add("eaap.op2.conf.messageArrange.tab.properties.routeCondition");
		messages.add("eaap.op2.conf.messageArrange.dialog.head.id");
		messages.add("eaap.op2.conf.messageArrange.dialog.properties.comparisonOperators");
		messages.add("eaap.op2.conf.messageArrange.dialog.properties.matchValue");
		messages.add("eaap.op2.conf.messageArrange.menu.title.operate");
		messages.add("eaap.op2.conf.messageArrange.top.menu.addRouteCondition");
		messages.add("eaap.op2.conf.messageArrange.top.menu.addGroup");
		messages.add("eaap.op2.conf.messageArrange.dialog.properties.or");
		messages.add("eaap.op2.conf.messageArrange.dialog.properties.and");
		messages.add("eaap.op2.conf.messageArrange.dialog.properties.valuesExpressionContent");
		messages.add("eaap.op2.conf.manager.auth.authformula");
		messages.add("eaap.op2.conf.messageArrange.dialog.head.parsingMechanismCoding");
		messages.add("eaap.op2.conf.messageArrange.dialog.head.parsingMechanismName");
		messages.add("eaap.op2.conf.messageArrange.dialog.title.getValueExprWin");
		messages.add("eaap.op2.conf.messageArrange.menu.title.conditionEvaluator");
		messages.add("eaap.op2.conf.messageArrange.dialog.properties.expression");
		messages.add("eaap.op2.conf.messageArrange.dialog.button.cancel");
		messages.add("eaap.op2.conf.techimpl.deleteOK");
		messages.add("eaap.op2.conf.techimpl.deleteFail");
		messages.add("eaap.op2.conf.messageArrange.error.cannotDel");
		messages.add("eaap.op2.conf.messageArrange.alert.info.deleteRouteConditionWithSubNode");
		messages.add("eaap.op2.conf.messageArrange.dialog.button.query");
		messages.add("eaap.op2.conf.messageArrange.expr.exprType");
		messages.add("eaap.op2.conf.messageArrange.expr.typeOne");
		messages.add("eaap.op2.conf.messageArrange.expr.typeTwo");
		return messages;
	}

	@RequestMapping(value = "/messageFlow/toSomeMessageArrangeConfig")
	public ModelAndView toSomeMessageArrangeConfig(final HttpServletRequest request,
			final HttpServletResponse response) {
		String messageFlowId = this.getRequestValue(request, "messageFlowId");
		String flag = this.getRequestValue(request, "flag");
		String optType = this.getRequestValue(request, "optType");
		List<Map<String,Object>> endpointSpecList = null;
		String orgId = "";
		boolean crmflag = false;//CRM登录标识
		try{
			String isLoginFlag = CommonUtils.getChineseValueByProCode("isCrmLoing");
			if(null != isLoginFlag && !"".equals(isLoginFlag)){
				if("Yes".equals(isLoginFlag)){
					crmflag = true;
				}
			}
		}catch(Exception e){
			crmflag = false;
		}
		UserRoleInfo tU = null;
		Map<String,Object> map = new HashMap<String,Object>();
		if(null != flag && !"".equals(flag)){
			if("portal".equals(flag)){
				map.put("flag", "portal");
				//portal用户信息
				tU = (UserRoleInfo) request.getSession().getAttribute(WebConstants.O2P_PORTAL_USER_SESSION_KEY);
				if(null != tU){
					orgId = tU.getId();
					map.put("orgId", orgId);
					map.put("messageFlowId", messageFlowId);
					map.put("isOpen", "NO");
					map.put("userName", tU.getName());
					endpointSpecList = messageFlowService.getEndpointSpecListByPortal();//取得针对合作伙伴开放的端点
				}else{
					map.put("orgId", "");
					map.put("messageFlowId", "");
					map.put("isOpen", "NO");
				}
			}
		}else{
			tU = (UserRoleInfo) request.getSession().getAttribute(WebConstants.O2P_SSO_USER_SESSION_KEY);//管理员登录
			if(null != tU || crmflag){
				endpointSpecList = messageFlowService.getEndpointSpecList();
				map.put("messageFlowId", messageFlowId);
				map.put("isOpen", "Yes");
			}else{
				map.put("messageFlowId", "");
				map.put("isOpen", "NO");
			}
		}
		List<Map<String,Object>> operatorList = messageFlowService.getOperatorListList();
		List<Map<String,Object>> in_Data_TypeList = messageFlowService.getData_TypeList();
		List<Map<String,Object>> ruleStrategyList = messageFlowService.getRuleStrategyList();
		List<Map<String,Object>>  condEvaluatorCollectionList = messageFlowService.getCondEvaluatorCollectionList();
		map.put("condEvaluatorCollectionList", condEvaluatorCollectionList);
		map.put("endpointSpecList", endpointSpecList);
		map.put("in_Data_TypeList", in_Data_TypeList);
		map.put("stateType", "open");//状态
		map.put("ruleStrategyList", ruleStrategyList);
		map.put("operatorList", operatorList);
		map.put("optType", optType);
		
		ModelAndView mv = new ModelAndView("main.jsp",map);
		List<String> messages = this.getSomeConfigMessage();
		addTranslateMessage(mv, messages);
		return mv;
	}
	
	private List<String> getSomeConfigMessage(){
		List<String> messages = new ArrayList<String>();
		// 添加页面上需要国际化的消息
		messages.add("eaap.op2.conf.messageArrange.tab.properties.allow");
		messages.add("eaap.op2.conf.messageArrange.tab.properties.notAllow");
		messages.add("eaap.op2.conf.messageArrange.tab.properties.endpointID");
		messages.add("eaap.op2.conf.messageArrange.tab.properties.endpointName");
		messages.add("eaap.op2.conf.messageArrange.tab.properties.specificationName");
		messages.add("eaap.op2.conf.messageArrange.tab.properties.specificationCode");
		messages.add("eaap.op2.conf.messageArrange.tab.properties.inputDataType");
		messages.add("eaap.op2.conf.messageArrange.tab.properties.outputDataType");
		messages.add("eaap.op2.conf.messageArrange.tab.properties.allowInputTrace");
		messages.add("eaap.op2.conf.messageArrange.tab.properties.allowInputDataLog");
		messages.add("eaap.op2.conf.messageArrange.tab.properties.endpointDescription");
		messages.add("eaap.op2.conf.messageArrange.tab.properties.specificationDescription");
		messages.add("eaap.op2.conf.messageArrange.dialog.head.messageFlowName");
		messages.add("eaap.op2.conf.messageArrange.menu.title.messageFlow");
		messages.add("eaap.op2.conf.messageArrange.dialog.button.close");
		messages.add("eaap.op2.conf.messageArrange.dialog.button.ok");
		messages.add("eaap.op2.conf.messageArrange.dialog.button.add");
		messages.add("eaap.op2.conf.messageArrange.menu.title.open");
		messages.add("eaap.op2.conf.messageArrange.menu.title.save");
		messages.add("eaap.op2.conf.server.supplier.choose");
		messages.add("eaap.op2.conf.messageArrange.menu.title.line");
		messages.add("eaap.op2.conf.messageArrange.menu.title.delete");
		messages.add("eaap.op2.conf.messageArrange.menu.title.help");
		messages.add("eaap.op2.conf.messageArrange.dialog.head.messageFlowID");
		messages.add("eaap.op2.conf.messageArrange.error.pageConfig");
		messages.add("eaap.op2.conf.messageArrange.tab.title.endpointProperty");
		messages.add("eaap.op2.conf.messageArrange.dialog.title.messageflowlist");
	   /////
			messages.add("eaap.op2.conf.messageArrange.error.addfirsts");
			messages.add("eaap.op2.conf.messageArrange.error.lineExists");
			messages.add("eaap.op2.conf.messageArrange.error.messageNameNotNull");
			messages.add("eaap.op2.conf.messageArrange.error.oneRecords");
			messages.add("eaap.op2.conf.messageArrange.error.dataNotComplete");
			messages.add("eaap.op2.conf.messageArrange.error.noData");
			messages.add("eaap.op2.conf.messageArrange.error.oneBeginNode");
			messages.add("eaap.op2.conf.messageArrange.error.oneEndNode");
			messages.add("eaap.op2.conf.techimpl.attrSpecName");
			messages.add("eaap.op2.conf.messageArrange.menu.title.endpoint");
			messages.add("eaap.op2.conf.messageArrange.error.notEmpty");
			messages.add("eaap.op2.conf.messageArrange.alert.info.saveErrot");
			messages.add("eaap.op2.conf.messageArrange.alert.info.saveAndRefresh");
			messages.add("eaap.op2.conf.messageArrange.error.detail");
			messages.add("eaap.op2.conf.messageArrange.tab.properties.synchronization");
			messages.add("eaap.op2.conf.messageArrange.tab.properties.asynchronization");
			messages.add("eaap.op2.conf.messageArrange.tab.properties.messageRouteMethod");
			messages.add("eaap.op2.conf.messageArrange.tab.properties.routePolicy");
			messages.add("eaap.op2.conf.messageArrange.tab.properties.routeCondition");
			messages.add("eaap.op2.conf.messageArrange.dialog.head.id");
			messages.add("eaap.op2.conf.messageArrange.dialog.properties.comparisonOperators");
			messages.add("eaap.op2.conf.messageArrange.dialog.properties.matchValue");
			messages.add("eaap.op2.conf.messageArrange.menu.title.operate");
			messages.add("eaap.op2.conf.messageArrange.top.menu.addRouteCondition");
			messages.add("eaap.op2.conf.messageArrange.top.menu.addGroup");
			messages.add("eaap.op2.conf.messageArrange.dialog.properties.or");
			messages.add("eaap.op2.conf.messageArrange.dialog.properties.and");
			messages.add("eaap.op2.conf.messageArrange.dialog.properties.valuesExpressionContent");
			messages.add("eaap.op2.conf.manager.auth.authformula");
			messages.add("eaap.op2.conf.messageArrange.dialog.head.parsingMechanismCoding");
			messages.add("eaap.op2.conf.messageArrange.dialog.head.parsingMechanismName");
			messages.add("eaap.op2.conf.messageArrange.dialog.title.getValueExprWin");
			messages.add("eaap.op2.conf.messageArrange.menu.title.conditionEvaluator");
			messages.add("eaap.op2.conf.messageArrange.dialog.properties.expression");
			messages.add("eaap.op2.conf.messageArrange.dialog.button.cancel");
			messages.add("eaap.op2.conf.techimpl.deleteOK");
			messages.add("eaap.op2.conf.techimpl.deleteFail");
			messages.add("eaap.op2.conf.messageArrange.error.cannotDel");
			messages.add("eaap.op2.conf.messageArrange.alert.info.deleteRouteConditionWithSubNode");
			messages.add("eaap.op2.conf.messageArrange.dialog.button.query");
			messages.add("eaap.op2.conf.messageArrange.expr.exprType");
			messages.add("eaap.op2.conf.messageArrange.expr.typeOne");
			messages.add("eaap.op2.conf.messageArrange.expr.typeTwo");
			return messages;
	}
	
	
	@RequestMapping(value = "/messageFlow/getEndpointSeq")
	@ResponseBody
	public String getEndpointSeq(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception{
		JSONObject json = new JSONObject();
		String endpointId = messageFlowService.getEndpointSequence();
		json.put("endpointId", endpointId);
		return json.toString();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/messageFlow/getCreatedMessageFlowList")
	@ResponseBody
	public String getCreatedMessageFlowList(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception{
		int length = Integer.parseInt(getRequestValue(request, "length"));
		int draw = Integer.parseInt(getRequestValue(request, "draw"));
		int start = Integer.parseInt(getRequestValue(request, "start"));

		Map map = new HashMap() ;  
		String pageMessageFlowId = getRequestValue(request, "pageMessageFlowId");
		if(null != pageMessageFlowId && !"".equals(pageMessageFlowId.trim())){
			map.put("messageFlowID", pageMessageFlowId);
		}
		String pageMessageFlowName = getRequestValue(request, "pageMessageFlowName");
		if(null != pageMessageFlowName && !"".equals(pageMessageFlowName.trim())){
			map.put("messageFlowName", pageMessageFlowName);
		}
		//mysql参数  
		map.put("startPage_mysql", start);
		map.put("endPage_mysql", length);
		//oracle参数
		map.put("startPage", start+1);
		map.put("endPage", start+length);
		UserRoleInfo tU = (UserRoleInfo) request.getSession().getAttribute(WebConstants.O2P_SSO_USER_SESSION_KEY);	//SSO登录用户信息
		if(null != tU&&tU.getTenantId()!=null){
			map.put("tenantId", tU.getTenantId());		//租户ID
		}else{
			Tenant tenant = CommonUtils.getTenant(request.getCookies(),request.getSession());
			if(tenant!=null && tenant.getTenantId()!=null){
				map.put("tenantId",tenant.getTenantId());		//租户ID
				tU.setTenantId(tenant.getTenantId());
				tU.setTenantCode(tenant.getCode()==null?"":tenant.getCode().toString());	
				request.getSession().setAttribute(WebConstants.O2P_SSO_USER_SESSION_KEY, tU);
			}
		}
		int total = messageFlowService.countAllMessageFlowList(map);
		List<Map> tmpList = messageFlowService.getAllMessageFlowList(map);
		JSONObject json = new JSONObject();
		json.put("draw", draw);
		json.put("recordsTotal", length);
		json.put("recordsFiltered", total);
		JSONArray jsonArray = new JSONArray();
		if(null !=  tmpList && tmpList.size() > 0){
			for(Map itData : tmpList){
				JSONObject jsondata = new JSONObject();
				jsondata.put("message_flow_id", String.valueOf(itData.get("MESSAGE_FLOW_ID")));
				jsondata.put("message_flow_name", String.valueOf(itData.get("MESSAGE_FLOW_NAME")));
				jsonArray.add(jsondata);
			}
		}
		json.put("data", jsonArray);
		return json.toString();
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/messageFlow/getGetValueExprList")
	@ResponseBody
	public String getGetValueExprList(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception{
		int length = Integer.parseInt(getRequestValue(request, "length"));
		int draw = Integer.parseInt(getRequestValue(request, "draw"));
		int start = Integer.parseInt(getRequestValue(request, "start"));
		Map map = new HashMap() ;  
		String expr = getRequestValue(request, "expr");
		if(null != expr && !"".equals(expr.trim())){
			map.put("expr", expr);
		}
		//mysql参数  
		map.put("startPage_mysql", start);
		map.put("endPage_mysql", length);
		//oracle参数
		map.put("startPage", start+1);
		map.put("endPage", start+length);
		int total = messageFlowService.countGetValueExprList(map);
		List<Map> tmpList = messageFlowService.getGetValueExprList(map);
		JSONObject json = new JSONObject();
		json.put("draw", draw);
		json.put("recordsTotal", length);
		json.put("recordsFiltered", total);
		JSONArray jsonArray = new JSONArray();
		if(null !=  tmpList && tmpList.size() > 0){
			for(Map itData : tmpList){
				JSONObject jsondata = new JSONObject();
				jsondata.put("expr_id", String.valueOf(itData.get("EXPR_ID")));
				jsondata.put("expr", String.valueOf(itData.get("EXPR")));
				jsondata.put("cond_evaluator_code", String.valueOf(itData.get("COND_EVALUATOR_CODE")));
				jsondata.put("cond_evaluator_name", String.valueOf(itData.get("COND_EVALUATOR_NAME")));
				jsonArray.add(jsondata);
			}
		}
		json.put("data", jsonArray);
		return json.toString();
	}

	@RequestMapping(value = "/messageFlow/saveJson")
	@ResponseBody
	public String saveJson(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception{
		UserRoleInfo tU = (UserRoleInfo) request.getSession().getAttribute(WebConstants.O2P_SSO_USER_SESSION_KEY);	//SSO登录用户信息
		String jsonObject = this.getRequestValue(request, "jsonObject");
		jsonObject = jsonObject.replaceAll("&quot;", "'");
		JSONObject finalJson = JSONObject.fromObject(jsonObject);
		if(null != tU&&tU.getTenantId()!=null){
			finalJson.put("tenantId", tU.getTenantId());		//租户ID
		}else{
			Tenant tenant = CommonUtils.getTenant(request.getCookies(),request.getSession());
			if(tenant!=null && tenant.getTenantId()!=null){
				finalJson.put("tenantId", tenant.getTenantId());		//租户ID
				tU.setTenantId(tenant.getTenantId());
				tU.setTenantCode(tenant.getCode()==null?"":tenant.getCode().toString());	
				request.getSession().setAttribute(WebConstants.O2P_SSO_USER_SESSION_KEY, tU);
			}
		}
		
		String result ="";
		try{
			result = messageFlowService.saveOrUpdateMessageFlow(finalJson);
		}catch(Exception e){
			LOG.error("save or update messageflow error", e);
			result = "fail";
		}
		JSONObject json = new JSONObject();
		json.put("result", result);
		return json.toString();
	}
	@RequestMapping(value = "/messageFlow/openMessageFlow")
	@ResponseBody
	public String openMessageFlow(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception{
		String messageFlowId = this.getRequestValue(request, "messageFlowId");
		JSONObject json = messageFlowService.getFullMessageFlow(messageFlowId);
		JSONObject resultJson = new JSONObject();
		resultJson.put("messageflow", json);
		return resultJson.toString();
	}
	@RequestMapping(value = "/messageFlow/getMessageFlowSeq")
	@ResponseBody
	public String getMessageFlowSeq(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception{
		JSONObject json = new JSONObject();
		String messageFlowId = messageFlowService.getMessageFlowSeq();
		json.put("messageFlowId", messageFlowId);
		return json.toString();
	}
	@RequestMapping(value = "/messageFlow/getServiceRouteConfigSeq")
	@ResponseBody
	public String getServiceRouteConfigSeq(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception{
		JSONObject json = new JSONObject();
		String routeId = messageFlowService.getServiceRouteConfigSeq();
		json.put("routeId", routeId);
		return json.toString();
	}
	@RequestMapping(value = "/messageFlow/getRulePolicySeq")
	@ResponseBody
	public String getRulePolicySeq(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception{
		JSONObject json = new JSONObject();
		String routePolicyId = messageFlowService.getRulePolicySeq();
		json.put("routePolicyId", routePolicyId);
		return json.toString();
	}

	@RequestMapping(value = "/messageFlow/getIsShowLog")
	@ResponseBody
	public String getIsShowLog(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception{
		String endpoint_Spec_Attr_Id = this.getRequestValue(request, "endpoint_Spec_Attr_Id");
		JSONObject json = new JSONObject();
		String isLog = messageFlowService.getIsShowLog(endpoint_Spec_Attr_Id);
		json.put("isLog", isLog);
		return json.toString();
	}
	
	@RequestMapping(value = "/messageFlow/getAttrSpecList")
	@ResponseBody
	public String getAttrSpecList(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception{
		String endpointSpecId = this.getRequestValue(request, "endpointSpecId");
		JSONObject json = new JSONObject();
		JSONObject propertyFirst = new JSONObject();
		List<Map<String,Object>> attrSpecList = messageFlowService.getAttrSpecList(endpointSpecId);
		if(null !=  attrSpecList && attrSpecList.size()>0){
			for(Map<String,Object> map : attrSpecList){
				JSONObject propertySecond = new JSONObject();
				String endpointSpecAttrId = (String)map.get("ENDPOINT_SPEC_ATTR_ID");
				String isFillIn = (String)map.get("IS_FILL_IN");
				propertySecond.put("isFillIn", isFillIn);
				String attrSpecName = (String)map.get("ATTR_SPEC_NAME");
				propertySecond.put("attrSpecName", attrSpecName);
				String attrSpecCode =  (String)map.get("ATTR_SPEC_CODE");
				propertySecond.put("attrSpecCode", attrSpecCode);
				propertySecond.put(attrSpecCode, "");
				if(null != map.get("DETAIL_URL")){
					String detailUrl = (String)map.get("DETAIL_URL");
					propertySecond.put("detailUrl", detailUrl);
				}
				if(null != map.get("CHOOSE_URL")){
					String chooseUrl = (String)map.get("CHOOSE_URL");
					propertySecond.put("chooseUrl", chooseUrl);
				}
				if(null != map.get("PORTAL_URL")){
					String portalUrl = (String)map.get("PORTAL_URL");
					propertySecond.put("portalUrl", portalUrl);
				}
				if(null != map.get("DETAIL_PORTAL_URL")){
					String detailPortalUrl = (String)map.get("DETAIL_PORTAL_URL");
					propertySecond.put("detailPortalUrl", detailPortalUrl);
				}
				if(null != map.get("PAGE_IN_TYPE")){
					String pageInType = (String)map.get("PAGE_IN_TYPE");
					propertySecond.put("pageInType", pageInType);
				}
				String attrSpecId = (String)map.get("ATTR_SPEC_ID");
				propertySecond.put("attrSpecId", attrSpecId);
				propertyFirst.put(endpointSpecAttrId, propertySecond);
			}
			json.put("property", propertyFirst);
		}
		return json.toString();
	}

	@RequestMapping(value = "/messageFlow/getOptionValues")
	@ResponseBody
	public String getOptionValues(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception{
		String attrSpecId = this.getRequestValue(request, "attrSpecId");
		JSONObject json = new JSONObject();
		List<HashMap<String,Object>> list = messageFlowService.getOptionValueById(attrSpecId);
		StringBuffer option = new StringBuffer("");
		if(null !=  list && list.size() > 0){
			for(HashMap<String,Object> map  :  list){
				String value= map.get("ATTR_VALUE").toString();
				String text = map.get("ATTR_VALUE_NAME").toString();
				option.append("<option  value='");option.append(value);option.append("'>");option.append(text);option.append("</option>");
			}
		}
		json.put("optionValue", option.toString());
		return json.toString();
	}

	@RequestMapping(value = "/messageFlow/loadRouteCond")
	@ResponseBody
	public String loadRouteCond(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception{
		JSONObject json = new JSONObject();
		JSONObject father = new JSONObject();
		List<HashMap<String,Object>> list = messageFlowService.getAllRouteCond();
		if(null !=  list && list.size() > 0){
			for(HashMap<String,Object> map  :  list){
				JSONObject son = new JSONObject();
				if(null != map.get("EXPR")){
					son.put("menuname", String.valueOf(map.get("EXPR")));
				}else{
					son.put("menuname", "");
				}
				son.put("parentId", String.valueOf(map.get("UP_ROUTE_COND_ID")));
				if(null != map.get("COND_RELATION") && !"".equals(String.valueOf(map.get("COND_RELATION")))){
					son.put("realate", String.valueOf(map.get("COND_RELATION")).toLowerCase());
				}
				if(null != map.get("OPERATOR_CODE")){
					son.put("operatorCode", String.valueOf(map.get("OPERATOR_CODE")));
				}else{
					son.put("operatorCode", "");
				}
				if(null != map.get("OPERATOR_ID")){
					son.put("operatorId", String.valueOf(map.get("OPERATOR_ID")));
				}else{
					son.put("operatorId", "");
				}
				if(null != map.get("GET_VALUE_EXPR_ID")){
					son.put("exprId", String.valueOf(map.get("GET_VALUE_EXPR_ID")));
				}else{
					son.put("exprId", "");
				}
				if(null != map.get("MATCH_VALUE")){
					son.put("matchValue", String.valueOf(map.get("MATCH_VALUE")));
				}else{
					son.put("matchValue", "");
				}
				father.put(String.valueOf(map.get("ROUTE_COND_ID")), son);
			}
		}
		json.put("realate", "or");
		json.put("T1011", father.toString());
		return json.toString();
	}

	@RequestMapping(value = "/messageFlow/showRouteCondition")
	@ResponseBody
	public String showRouteCondition(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception{
		String routeCondId = this.getRequestValue(request, "routeCondId");
		JSONObject json = new JSONObject();
		String routeConditionExpr=messageFlowService.getRouteConditionExprById(routeCondId);
		json.put("result", routeConditionExpr);
		return json.toString();
	}

	@RequestMapping(value = "/messageFlow/changeRelation")
	@ResponseBody
	public String changeRelation(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception{
		String routeCondId = this.getRequestValue(request, "routeCondId");
		String relation = this.getRequestValue(request, "relation");
		Map<String,String> map = new HashMap<String,String>();
		map.put("routeCondId", routeCondId);
		map.put("relation", relation.toUpperCase());
		JSONObject json = new JSONObject();
		try{
			messageFlowService.updateRelation(map);
			json.put("result", "success");
		}catch(Exception e){
			json.put("result", "fail");
		}
		return json.toString();
	}

	@RequestMapping(value = "/messageFlow/deleteRouteCondition")
	@ResponseBody
	public String deleteRouteCondition(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception{
		String routeCondId = this.getRequestValue(request, "routeCondId");
		Map<String,String> map = new HashMap<String,String>();
		map.put("routeCondId", routeCondId);
		JSONObject json = new JSONObject();
		try{
			messageFlowService.deleteRouteCondition(map);
			String result = map.get("result");
			if("0000".equals(result)){
				json.put("result", "success");
			}else{
				json.put("result", result);
			}
		}catch(Exception e){
			json.put("result", "fail");
		}
		return json.toString();
	}

	@RequestMapping(value = "/messageFlow/addRounteCond")
	@ResponseBody
	public String addRounteCond(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception{
		String pageHidExprId = this.getRequestValue(request, "pageHidExprId");
		String pageExpr = this.getRequestValue(request, "pageExpr");
		String pageOperator = this.getRequestValue(request, "pageOperator");
		String pageMatchValue = this.getRequestValue(request, "pageMatchValue");
		String pageOperatorValue = this.getRequestValue(request, "pageOperatorValue");
		String pageUpExprId = this.getRequestValue(request, "pageUpExprId");
		String pageCondRel = this.getRequestValue(request, "pageCondRel");
		Map<String,String> map = new HashMap<String,String>();
		map.put("up_Route_Cond_Id", pageUpExprId);
		map.put("get_Value_Expr_Id", pageHidExprId);
		map.put("operator_Id", pageOperator);
		map.put("match_Value", pageMatchValue);
		map.put("route_Condition_Expr", pageExpr+pageOperatorValue+pageMatchValue);
		map.put("cond_Relation", pageCondRel);
		JSONObject json = new JSONObject();
		try{
			messageFlowService.addRounteCond(map);
			String result = map.get("result");
			if("success".equals(result)){
				json.put("result", "success");
			}else{
				json.put("result", "fail");
			}
		}catch(Exception e){
			json.put("result", "fail");
		}
		return json.toString();
	}

	@RequestMapping(value = "/messageFlow/updateRounteCond")
	@ResponseBody
	public String updateRounteCond(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception{
		String pageHidExprId = this.getRequestValue(request, "pageHidExprId");
		String pageOperator = this.getRequestValue(request, "pageOperator");
		String pageMatchValue = this.getRequestValue(request, "pageMatchValue");
		String pageRouteCondtionId = this.getRequestValue(request, "pageRouteCondtionId");
		Map<String,String> map = new HashMap<String,String>();
		map.put("get_Value_Expr_Id", pageHidExprId);
		map.put("operator_Id", pageOperator);
		map.put("match_Value", pageMatchValue);
		map.put("route_Cond_Id", pageRouteCondtionId);
		JSONObject json = new JSONObject();
		try{
			messageFlowService.updateRounteCond(map);
			String result = map.get("result");
			if("success".equals(result)){
				json.put("result", "success");
			}else{
				json.put("result", "fail");
			}
		}catch(Exception e){
			json.put("result", "fail");
		}
		return json.toString();
	}

	@RequestMapping(value = "/messageFlow/addCondEvalExpr")
	@ResponseBody
	public String addCondEvalExpr(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception{
		String pageCondEval = this.getRequestValue(request, "pageCondEval");
		String pageCondEvalExpr = this.getRequestValue(request, "pageCondEvalExpr");
		String pageExprType = this.getRequestValue(request, "pageExprType");
		Map<String,String> map = new HashMap<String,String>();
		map.put("cond_Evaluator_Id", pageCondEval);
		map.put("expr", pageCondEvalExpr);
		map.put("expr_type", pageExprType);
		JSONObject json = new JSONObject();
		try{
			messageFlowService.addCondEvalExpr(map);
			json.put("result", "success");
		}catch(Exception e){
			json.put("result", "fail");
		}
		return json.toString();
	}

	@RequestMapping(value = "/messageFlow/deleteGetValueExpr")
	@ResponseBody
	public String deleteGetValueExpr(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception{
		String pageExprId = this.getRequestValue(request, "pageExprId");
		Map<String,String> map = new HashMap<String,String>();
		map.put("expr_Id", pageExprId);
		JSONObject json = new JSONObject();
		try{
			messageFlowService.deleteGetValueExpr(map);
			String result = map.get("result");
			if("0000".equals(result)){
				json.put("result", "success");
			}else{
				json.put("result", result);
			}
		}catch(Exception e){
			json.put("result", "fail");
		}
		return json.toString();
	}
}
