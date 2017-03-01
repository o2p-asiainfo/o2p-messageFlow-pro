package com.ailk.o2p.messageFlow.service.messageFlow;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ailk.eaap.op2.bo.Tenant;

import net.sf.json.JSONObject;

public interface IMessageFlowService {

	public List<Map<String, Object>> getEndpointSpecList();

	public String getEndpointSequence();

	public String getServiceRouteConfigSeq();

	public String getRulePolicySeq();

	public String getIsShowLog(String endpoint_Spec_Attr_Id);

	public List<Map<String, Object>> getData_TypeList();

	public List<Map<String, Object>> getAttrSpecList(String endpointSpecId);

	public List<HashMap<String, Object>> getOptionValueById(String attrSpecId);

	public String getMessageFlowSeq();

	public String saveOrUpdateMessageFlow(JSONObject finalJson) throws Exception;

	public JSONObject getFullMessageFlow(String messageFlowId);

	public int countAllMessageFlowList(Map map);

	public List<Map> getAllMessageFlowList(Map map);

	public List<Map<String, Object>> getEndpointSpecListByPortal();

	public List<Map<String, Object>> getRuleStrategyList();

	public List<HashMap<String, Object>> getAllRouteCond();

	public String getRouteConditionExprById(String route_Cond_Id);

	public void updateRelation(Map<String, String> map);

	public void deleteRouteCondition(Map<String, String> map);

	public int countGetValueExprList(Map map);

	public List<Map> getGetValueExprList(Map map);

	public List<Map<String, Object>> getOperatorListList();

	public void addRounteCond(Map<String, String> map);

	public void updateRounteCond(Map<String, String> map);

	public List<Map<String, Object>> getCondEvaluatorCollectionList();

	public void addCondEvalExpr(Map<String, String> map);

	public void deleteGetValueExpr(Map<String, String> map);

	
	public String queryTenantIdByCode(String tenantCode);
	
	public Tenant queryTenant(Tenant tenant);
}
