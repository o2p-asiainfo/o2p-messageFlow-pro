package com.ailk.o2p.messageFlow.dao.messageFlow;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.ailk.eaap.op2.bo.Tenant;
import com.linkage.rainbow.dao.SqlMapDAO;

@Repository
public class MessageFlowDaoImpl implements IMessageFlowDao {

	@Resource(name="iBatisSqlMapDAO")
	private SqlMapDAO sqlMapDao;

	public List<Map<String, Object>> getEndpointSpecList(Map paramMap) {
		
		return sqlMapDao.queryForList("eaap-op2-messagearrange-endpoint.getEndpointSpecList", paramMap);
	}

	public String getEndpointSequence() {
		return (String)sqlMapDao.queryForObject("eaap-op2-messagearrange-endpoint.getEndpointSequence", null); 
	}

	public String getServiceRouteConfigSeq() {
		return (String)sqlMapDao.queryForObject("eaap-op2-messagearrange-servicerouteconfig.getServiceRouteConfigSequence", null); 
	}

	public String getRulePolicySeq() {
		return (String)sqlMapDao.queryForObject("eaap-op2-messagearrange-servicerouteconfig.getRulePolicySequence", null); 
	}

	public String getIsShowLog(String endpoint_Spec_Attr_Id) {
		return (String)sqlMapDao.queryForObject("eaap-op2-messagearrange-messageflow.getEnableLog", endpoint_Spec_Attr_Id);
	}

	public List<Map<String, Object>> getData_TypeList() {
		return  (List)this.sqlMapDao.queryForList("eaap-op2-messagearrange-endpoint.getData_TypeList", null);
	}

	public List<Map<String, Object>> getAttrSpecList(Map paramMap) {
		return (List)this.sqlMapDao.queryForList("eaap-op2-messagearrange-endpoint.getAttrSpecListByEndpointSpecId", paramMap);
	}

	public List<HashMap<String, Object>> getOptionValueById(Map paramMap) {
		return sqlMapDao.queryForList("eaap-op2-messagearrange-messageflow.getOptionValueById", paramMap);
	}

	public String getMessageFlowSeq() {
		return (String)sqlMapDao.queryForObject("eaap-op2-messagearrange-messageflow.getMessageFlowSequence", null);
	}

	public void saveEndpoint(HashMap<String, String> endpointMap) {
		sqlMapDao.insert("eaap-op2-messagearrange-endpoint.saveEndpoint", endpointMap);
	}

	public void saveEndpointAttrValue(
			HashMap<String, String> endpointAttrValueMap) {
		sqlMapDao.insert("eaap-op2-messagearrange-endpoint.saveEndpointAttrValue", endpointAttrValueMap);
	}

	public void addRoutePolicy(HashMap<String, String> paramRoutePolicyMap) {
		sqlMapDao.insert("eaap-op2-messagearrange-servicerouteconfig.addRoutePolicy", paramRoutePolicyMap);
	}

	public void saveService_Route_Config(
			HashMap<String, String> serviceRouteConfigMap) {
		sqlMapDao.insert("eaap-op2-messagearrange-servicerouteconfig.saveService_Route_Config", serviceRouteConfigMap);
	}

	public void saveMessageFlow(HashMap<String, String> messageMap) {
		sqlMapDao.insert("eaap-op2-messagearrange-messageflow.saveMessageFlow", messageMap);
	}

	public void deleteEndpointAttrValues(Map paramMap) {
		sqlMapDao.delete("eaap-op2-messagearrange-endpoint.deleteEndpointAttrValues", paramMap);
	}

	public void deleteEndpoints(Map paramMap) {
		sqlMapDao.delete("eaap-op2-messagearrange-endpoint.deleteEndpoints", paramMap);
	}

	public void deleteService_Route_Configs(Map paramMap) {
		sqlMapDao.delete("eaap-op2-messagearrange-servicerouteconfig.deleteService_Route_Configs", paramMap);
	}

	public void deleteMessage(Map paramMap) {
		sqlMapDao.delete("eaap-op2-messagearrange-messageflow.deleteMessage", paramMap);
	}

	public Map<String, String> getMessageFlowByMessageFlowId(
			Map paramMap) {
		return (Map<String, String>)sqlMapDao.queryForObject("eaap-op2-messagearrange-messageflow.getMessageFlowByMessage_Flow_Id", paramMap);
	}

	public List<Map<String, Object>> getEndpointByMessageFlowId(
			Map paramMap) {
		return sqlMapDao.queryForList("eaap-op2-messagearrange-messageflow.getEndpointByMessageFlowId", paramMap);
	}

	public List<Map<String, Object>> getPropertyByEndpointId(
			Map endpointParamMap) {
		return sqlMapDao.queryForList("eaap-op2-messagearrange-messageflow.getPropertyByEndpointId", endpointParamMap);
	}

	public List<Map<String, Object>> getServiceRouteConfigByMessageFlowId(
			Map paramMap) {
		return sqlMapDao.queryForList("eaap-op2-messagearrange-messageflow.getServiceRouteConfigByMessageFlowId", paramMap);
	}

	public void deleteRoutePolicy(Map paramMap) {
		sqlMapDao.delete("eaap-op2-messagearrange-messageflow.deleteRoute_Policy", paramMap);
	}

	public int countAllMessageFlowList(Map map) {
		return (Integer)sqlMapDao.queryForObject("eaap-op2-messagearrange-messageflow.countAllMessageFlowList", map);
	}

	public List<Map> getAllMessageFlowList(Map map) {
		return sqlMapDao.queryForList("eaap-op2-messagearrange-messageflow.getCreatedMessageFlowList", map);
	}

	public List<Map<String, Object>> getEndpointSpecListByPortal(Map paramMap) {
		return sqlMapDao.queryForList("eaap-op2-messagearrange-endpoint.getEndpointSpecListByPortal", paramMap);
	}

	public List<Map<String, Object>> getRuleStrategyList() {
		return sqlMapDao.queryForList("eaap-op2-messagearrange-servicerouteconfig.getRuleStrategyList", null);
	}

	public List<HashMap<String, Object>> getAllRouteCond(Map paramMap) {
		return sqlMapDao.queryForList("eaap-op2-messagearrange-servicerouteconfig.getAllRouteCond", paramMap);
	}

	public List getRouteConditionExprById(Map paramMap) {
		return this.sqlMapDao.queryForList("eaap-op2-messagearrange-servicerouteconfig.getRouteConditionExprById", paramMap);
	}

	public List getRouteCondTreeSubNodeJosn(Map paramMap) {
		return this.sqlMapDao.queryForList("eaap-op2-messagearrange-servicerouteconfig.getRouteCondTreeSubNodeJosn", paramMap);
	}

	public void updateRelation(Map<String, String> map) {
		sqlMapDao.update("eaap-op2-messagearrange-servicerouteconfig.updateRelation", map);
	}

	public boolean assertUsed(Map paramMap) {
		Integer num = (Integer)sqlMapDao.queryForObject("eaap-op2-messagearrange-servicerouteconfig.assertUsed", paramMap);
		return num > 0;
	}

	public boolean haveSub(Map paramMap) {
		Integer num = (Integer)sqlMapDao.queryForObject("eaap-op2-messagearrange-servicerouteconfig.haveSub", paramMap);
		return num > 0;
	}

	public void deleteRouteCondition(Map paramMap) {
		sqlMapDao.delete("eaap-op2-messagearrange-servicerouteconfig.deleteRouteCondition", paramMap);
	}

	public int countGetValueExprList(Map map) {
		return (Integer)sqlMapDao.queryForObject("eaap-op2-messagearrange-servicerouteconfig.countGetValueExprList", map);
	}

	public List<Map> getGetValueExprList(Map map) {
		return sqlMapDao.queryForList("eaap-op2-messagearrange-servicerouteconfig.getGetValueExprList", map);
	}

	public List<Map<String, Object>> getOperatorListList() {
		return sqlMapDao.queryForList("eaap-op2-messagearrange-servicerouteconfig.getComparisonOperatorCollectionList", null);
	}

	public void addRounteCond(Map<String, String> map) {
		String routeCondId = (String)sqlMapDao.queryForObject("eaap-op2-messagearrange-servicerouteconfig.getRouteConditSequence", null);
		map.put("route_Cond_Id", routeCondId);
		sqlMapDao.insert("eaap-op2-messagearrange-servicerouteconfig.addRounteCond", map);
	}

	public void updateRounteCond(Map<String, String> map) {
		sqlMapDao.update("eaap-op2-messagearrange-servicerouteconfig.updateRouteCond", map);
	}

	public List<Map<String, Object>> getCondEvaluatorCollectionList(Map paramMap) {
		return sqlMapDao.queryForList("eaap-op2-messagearrange-servicerouteconfig.getCondEvaluatorCollectionList", paramMap);
	}

	public void addCondEvalExpr(Map<String, String> map) {
		sqlMapDao.insert("eaap-op2-messagearrange-servicerouteconfig.addGetValueExpr", map);
	}

	public boolean getIsUserd(Map paramMap) {
		Integer num  = (Integer)sqlMapDao.queryForObject("eaap-op2-messagearrange-servicerouteconfig.getIsUserd", paramMap);
		return num >0;
	}

	public void deleteGetValueExpr(Map<String, String> map) {
		sqlMapDao.delete("eaap-op2-messagearrange-servicerouteconfig.deleteGetValueExpr", map);
	}
	
	public String queryTenantIdByCode(String tenantCode){
		return  (String) sqlMapDao.queryForObject("eaap-op2-messagearrange-messageflow.queryTenantIdByCode", tenantCode);
	}
	public Tenant queryTenant(Tenant tenant){
		return  (Tenant) sqlMapDao.queryForObject("eaap-op2-messagearrange-messageflow.queryTenant", tenant);
	}
}
