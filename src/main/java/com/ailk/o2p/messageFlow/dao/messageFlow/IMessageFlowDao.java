package com.ailk.o2p.messageFlow.dao.messageFlow;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ailk.eaap.op2.bo.Tenant;

public interface IMessageFlowDao {

	public List<Map<String, Object>> getEndpointSpecList(Map paramMap);

	public String getEndpointSequence();

	public String getServiceRouteConfigSeq();

	public String getRulePolicySeq();

	public String getIsShowLog(String endpoint_Spec_Attr_Id);

	public List<Map<String, Object>> getData_TypeList();

	public List<Map<String, Object>> getAttrSpecList(Map paramMap);

	public List<HashMap<String, Object>> getOptionValueById(Map paramMap);

	public String getMessageFlowSeq();

	public void saveEndpoint(HashMap<String, String> endpointMap);

	public void saveEndpointAttrValue(
			HashMap<String, String> endpointAttrValueMap);

	public void addRoutePolicy(HashMap<String, String> paramRoutePolicyMap);

	public void saveService_Route_Config(
			HashMap<String, String> serviceRouteConfigMap);

	public void saveMessageFlow(HashMap<String, String> messageMap);

	public void deleteEndpointAttrValues(Map paramMap);

	public void deleteEndpoints(Map paramMap);

	public void deleteService_Route_Configs(Map paramMap);

	public void deleteMessage(Map paramMap);

	public Map<String, String> getMessageFlowByMessageFlowId(
			Map paramMap);

	public List<Map<String, Object>> getEndpointByMessageFlowId(
			Map paramMap);

	public List<Map<String, Object>> getPropertyByEndpointId(
			Map endpointParamMap);

	public List<Map<String, Object>> getServiceRouteConfigByMessageFlowId(
			Map paramMap);

	public void deleteRoutePolicy(Map paramMap);

	public int countAllMessageFlowList(Map map);

	public List<Map> getAllMessageFlowList(Map map);

	public List<Map<String, Object>> getEndpointSpecListByPortal(Map paramMap);

	public List<Map<String, Object>> getRuleStrategyList();

	public List<HashMap<String, Object>> getAllRouteCond(Map paramMap);

	public List getRouteConditionExprById(Map paramMap);

	public List getRouteCondTreeSubNodeJosn(Map paramMap);

	public void updateRelation(Map<String, String> map);

	public boolean assertUsed(Map paramMap);

	public boolean haveSub(Map paramMap);

	public void deleteRouteCondition(Map paramMap);

	public int countGetValueExprList(Map map);

	public List<Map> getGetValueExprList(Map map);

	public List<Map<String, Object>> getOperatorListList();

	public void addRounteCond(Map<String, String> map);

	public void updateRounteCond(Map<String, String> map);

	public List<Map<String, Object>> getCondEvaluatorCollectionList(Map paramMap);

	public void addCondEvalExpr(Map<String, String> map);

	public boolean getIsUserd(Map paramMap);

	public void deleteGetValueExpr(Map<String, String> map);
	
	public String queryTenantIdByCode(String tenantCode);
	
	public Tenant queryTenant(Tenant tenant);

}
