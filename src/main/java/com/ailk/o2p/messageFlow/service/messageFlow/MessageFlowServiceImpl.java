package com.ailk.o2p.messageFlow.service.messageFlow;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ailk.eaap.op2.bo.Tenant;
import com.ailk.o2p.messageFlow.bo.RouteCondTreeGrid;
import com.ailk.o2p.messageFlow.dao.messageFlow.IMessageFlowDao;

@Service
public class MessageFlowServiceImpl implements IMessageFlowService{

	@Autowired
	private IMessageFlowDao messageFlowDao;

	public List<Map<String, Object>> getEndpointSpecList() {
		Map paramMap=new HashMap(); 
		return messageFlowDao.getEndpointSpecList(paramMap);
	}

	public String getEndpointSequence() {
		return messageFlowDao.getEndpointSequence();
	}

	public String getServiceRouteConfigSeq() {
		return messageFlowDao.getServiceRouteConfigSeq();
	}

	public String getRulePolicySeq() {
		return messageFlowDao.getRulePolicySeq();
	}

	public String getIsShowLog(String endpoint_Spec_Attr_Id) {
		return messageFlowDao.getIsShowLog(endpoint_Spec_Attr_Id);
	}

	public List<Map<String, Object>> getData_TypeList() {
		return messageFlowDao.getData_TypeList();
	}

	public List<Map<String, Object>> getAttrSpecList(String endpointSpecId) {
		Map paramMap=new HashMap(); 
		paramMap.put("endpointSpecId", endpointSpecId);
		return messageFlowDao.getAttrSpecList(paramMap);
	}

	public List<HashMap<String, Object>> getOptionValueById(String attrSpecId) {
		Map paramMap=new HashMap(); 
		paramMap.put("attrSpecId", attrSpecId);
		return messageFlowDao.getOptionValueById(paramMap);
	}

	public String getMessageFlowSeq() {
		return messageFlowDao.getMessageFlowSeq();
	}
	
	private HashMap<String,String> getEndpointMap(JSONObject endpoint,String endpointId,String messageFlowId){
		 String endpointSpecId = endpoint.getString("endpointSpecId");
		 String endpointName = endpoint.getString("endpointName");
		 String inputDataType = endpoint.getString("inputDataType");
		 String outputDataType = endpoint.getString("outputDataType");
		 String tracingFlag = endpoint.getString("tracingFlag");
		 String dataLogFlag = endpoint.getString("dataLogFlag");
		 String endpointDesc = endpoint.getString("endpointDesc");
		 String left = endpoint.getString("left");
		 String top = endpoint.getString("top");
		 //添加端点数据
		 HashMap<String,String> endpointMap=new HashMap<String,String>();
			endpointMap.put("endpoint_Id", endpointId);
			endpointMap.put("message_Flow_Id", messageFlowId);
			endpointMap.put("endpoint_Spec_Id", endpointSpecId);
			endpointMap.put("in_Data_Type_Id", inputDataType);
			endpointMap.put("out_Data_Type_Id", outputDataType);
			endpointMap.put("endpoint_Name", endpointName);
			endpointMap.put("endpoint_Code", endpointId);
			endpointMap.put("enable_In_Trace", tracingFlag);
			endpointMap.put("enable_Out_Trace", "Y");
			endpointMap.put("enable_In_Log", dataLogFlag);
			endpointMap.put("enable_Out_Log", "Y");
			endpointMap.put("state", "A");
			endpointMap.put("lastest_date", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
			endpointMap.put("endpoint_Desc", endpointDesc);
			endpointMap.put("map_Code", left+";"+top);
			return endpointMap;
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	public String saveOrUpdateMessageFlow(JSONObject finalJson) throws Exception{
		String result = "fail";
		JSONObject first = (JSONObject)finalJson.get("messageflow");//获取第一层消息流信息
		String messageFlowId = first.getString("id");
		String messageFlowName = first.getString("name");
		String tenantId=null;
		if( finalJson.containsKey("tenantId")){
			tenantId= finalJson.getString("tenantId");			//租户ID
		}
		String fromEndpointId = "";
		//先清空之前的数据
		Map paramMap=new HashMap(); 
		paramMap.put("messageFlowId", messageFlowId);
		messageFlowDao.deleteEndpointAttrValues(paramMap);
		
		messageFlowDao.deleteEndpoints(paramMap);
		messageFlowDao.deleteRoutePolicy(paramMap);
		messageFlowDao.deleteService_Route_Configs(paramMap);
		messageFlowDao.deleteMessage(paramMap);
		
		JSONObject nodes = (JSONObject)first.get("nodes");//获取端点信息
		 Iterator it = nodes.keys();
		 while(it.hasNext()){
			 String endpointId = (String)it.next();
			 JSONObject endpoint = (JSONObject)nodes.get(endpointId);//得到一个端点对象
			 String specificationCode = endpoint.getString("specificationCode");
			 if("BEGIN".equals(specificationCode)){
				 fromEndpointId = endpointId;
			 }
			 //添加端点数据
			 HashMap<String,String> endpointMap = this.getEndpointMap(endpoint, endpointId, messageFlowId);
			 endpointMap.put("tenantId",tenantId);
			 messageFlowDao.saveEndpoint(endpointMap);
			 if(null != endpoint.get("property")){
				 JSONObject property = (JSONObject)endpoint.get("property");//得到端点的动态属性对象
				 Iterator itProperty = property.keys();
				 while(itProperty.hasNext()){
					 String endpointSpecAttrId = (String)itProperty.next();
					 JSONObject propertyObject = (JSONObject)property.get(endpointSpecAttrId);//得到一个动态属性对象
					 String attrSpecCode = propertyObject.getString("attrSpecCode");
					 String dyPropertyValue = propertyObject.getString(attrSpecCode);
					 
					    HashMap<String,String> endpointAttrValueMap=new HashMap<String,String>();
						endpointAttrValueMap.put("endpoint_Id", endpointId);
						endpointAttrValueMap.put("attr_Value", dyPropertyValue);
						endpointAttrValueMap.put("endpoint_Spec_Attr_Id", endpointSpecAttrId);
						endpointAttrValueMap.put("long_Attr_Value", "");
						endpointAttrValueMap.put("tenantId",tenantId);
						messageFlowDao.saveEndpointAttrValue(endpointAttrValueMap);
				 }
			 }
		 }
		 //针对线操作
		 JSONObject lines = (JSONObject)first.get("lines");//获取端点信息
		 Iterator itLines = lines.keys();
		 while(itLines.hasNext()){
			 String routeId = (String)itLines.next();
			 JSONObject route = (JSONObject)lines.get(routeId);//得到一个路由配置对象 
			 String from = route.getString("from");
			 String to = route.getString("to");
			 String synAsyn = route.getString("synAsyn");
			 JSONObject routePolicy = (JSONObject)route.get("policy");//得到一个路由规则对象 
			 String routePolicyId = routePolicy.getString("routePolicyId");
			 String strategy = routePolicy.getString("strategy");
			 String condId = routePolicy.getString("condId");
			 if(!"1".equals(strategy)){
				 //添加路由规则数据
				 HashMap<String,String>  paramRoutePolicyMap =  new HashMap<String,String>();
				 paramRoutePolicyMap.put("route_Policy_Id", routePolicyId);
				 paramRoutePolicyMap.put("rule_Strategy_Id", strategy);
				 paramRoutePolicyMap.put("route_Cond_Id", condId);
				 paramRoutePolicyMap.put("tenantId",tenantId);
				 messageFlowDao.addRoutePolicy(paramRoutePolicyMap);
			 }else{
				 routePolicyId = "0";
			 }
			 //添加路由配置数据
			 HashMap<String,String> serviceRouteConfigMap=new HashMap<String,String>();
			 serviceRouteConfigMap.put("route_Id",routeId);
			 serviceRouteConfigMap.put("route_Policy_Id",routePolicyId);
			 serviceRouteConfigMap.put("message_Flow_Id",messageFlowId);
			 serviceRouteConfigMap.put("from_Endpoint_Id", from);
			 serviceRouteConfigMap.put("to_Endpoint_Id",to);
			 serviceRouteConfigMap.put("syn_Asyn",synAsyn);
			 serviceRouteConfigMap.put("state","A");
			 serviceRouteConfigMap.put("lastest_Date",new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
			 serviceRouteConfigMap.put("map_Code", "");
			 serviceRouteConfigMap.put("tenantId",tenantId);
			 messageFlowDao.saveService_Route_Config(serviceRouteConfigMap);
		 }
		 //添加消息流数据
		 HashMap<String,String> messageMap=new HashMap<String,String>();
		 messageMap.put("message_Flow_Id",messageFlowId);
		 messageMap.put("message_Flow_Name",messageFlowName);
		 messageMap.put("first_Endpoint_Id",fromEndpointId);
		 messageMap.put("lastest_Time",new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		 messageMap.put("state","A");
		 messageMap.put("descriptor", "");
		 messageMap.put("tenantId",tenantId);
		 messageFlowDao.saveMessageFlow(messageMap);
		 result = "success";
		return result;
	}

	public JSONObject getFullMessageFlow(String messageFlowId) {
		JSONObject messageFlow = new JSONObject();
		if(null != messageFlowId && !"".equals(messageFlowId)){
			//查询消息流数据
			Map paramMap=new HashMap(); 
			paramMap.put("messageFlowId", messageFlowId);
			Map<String,String> mapMessageFlow = messageFlowDao.getMessageFlowByMessageFlowId(paramMap);
			if(null != mapMessageFlow){
				String id = mapMessageFlow.get("MESSAGE_FLOW_ID");
				String name = mapMessageFlow.get("MESSAGE_FLOW_NAME");
				messageFlow.put("id", id);
				messageFlow.put("name", name);
			}
			JSONObject nodes = new JSONObject();
			//查询端点数据
			List<Map<String,Object>> listEndpoint = messageFlowDao.getEndpointByMessageFlowId(paramMap);
			if(null != listEndpoint && listEndpoint.size()>0){
				for(Map<String,Object> param : listEndpoint){
					JSONObject endpoint = new JSONObject();
					String endpointId = String.valueOf(param.get("ENDPOINT_ID"));
					endpoint.put("endpointSpecId", String.valueOf(param.get("ENDPOINT_SPEC_ID")));
					if(null != param.get("ENDPOINT_NAME")){
						endpoint.put("endpointName", String.valueOf(param.get("ENDPOINT_NAME")));
					}else{
						endpoint.put("endpointName", "");
					}
					endpoint.put("specificationName", String.valueOf(param.get("ENDPOINT_SPEC_NAME")));
					endpoint.put("specificationCode", String.valueOf(param.get("ENDPOINT_SPEC_CODE")));
					endpoint.put("inputDataType", String.valueOf(param.get("IN_DATA_TYPE_ID")));
					endpoint.put("outputDataType", String.valueOf(param.get("OUT_DATA_TYPE_ID")));
					endpoint.put("tracingFlag", String.valueOf(param.get("ENABLE_IN_TRACE")));
					endpoint.put("dataLogFlag", String.valueOf(param.get("ENABLE_IN_LOG")));
					endpoint.put("endpointDesc", String.valueOf(param.get("ENDPOINT_DESC")));
					endpoint.put("specDesc", String.valueOf(param.get("ENDPOINT_SPEC_DESC")));
					if(null != param.get("MAP_CODE") && !"".equals(String.valueOf(param.get("MAP_CODE")))){
						String location = String.valueOf(param.get("MAP_CODE"));
						String[] leftTop  = location.split(";");
						if(leftTop.length > 0){
							endpoint.put("left", leftTop[0]);
							endpoint.put("top", leftTop[1]);
						}
					}
					//查看端点动态属性
					Map endpointParamMap=new HashMap(); 
					endpointParamMap.put("endpointId", endpointId);
					List<Map<String,Object>> listProperty= messageFlowDao.getPropertyByEndpointId(endpointParamMap);
					if(null != listProperty && listProperty.size()>0){
						JSONObject property = new JSONObject();
						for(Map<String,Object> propertyParam  : listProperty){
							JSONObject innerProperty = this.getInnerProperty(propertyParam);
							String endpointSpecAttrId = String.valueOf(propertyParam.get("ENDPOINT_SPEC_ATTR_ID"));
							property.put(endpointSpecAttrId, innerProperty);
						}
						endpoint.put("property", property);
					}
					nodes.put(endpointId, endpoint);
				}
			}
			messageFlow.put("nodes", nodes);//添加端点对象
			JSONObject lines = new JSONObject();
			List<Map<String,Object>> listLines= messageFlowDao.getServiceRouteConfigByMessageFlowId(paramMap);
			if(null != listLines && listLines.size()>0){ 
				for(Map<String,Object> lineParam : listLines){
					JSONObject innerLines = this.getInnerLines(lineParam);
					String routeId = String.valueOf(lineParam.get("ROUTE_ID"));
					lines.put(routeId, innerLines);
				}
			}
			messageFlow.put("lines", lines);//添加路由对象
		}
		return messageFlow;
	}
	
	private JSONObject getInnerProperty(Map<String,Object> propertyParam){
		JSONObject innerProperty = new JSONObject();
		innerProperty.put("isFillIn", String.valueOf(propertyParam.get("IS_FILL_IN")));
		innerProperty.put("attrSpecName", String.valueOf(propertyParam.get("ATTR_SPEC_NAME")));
		innerProperty.put("attrSpecCode", String.valueOf(propertyParam.get("ATTR_SPEC_CODE")));
		if(null != propertyParam.get("ATTR_VALUE")){
			innerProperty.put(String.valueOf(propertyParam.get("ATTR_SPEC_CODE")), String.valueOf(propertyParam.get("ATTR_VALUE")));
		}else{
			innerProperty.put(String.valueOf(propertyParam.get("ATTR_SPEC_CODE")), "");
		}
		if(null != propertyParam.get("DETAIL_URL")){
			innerProperty.put("detailUrl", String.valueOf(propertyParam.get("DETAIL_URL")));
		}else{
			innerProperty.put("detailUrl", "");
		}
		if(null != propertyParam.get("CHOOSE_URL")){
			innerProperty.put("chooseUrl", String.valueOf(propertyParam.get("CHOOSE_URL")));
		}else{
			innerProperty.put("chooseUrl", "");
		}
		if(null != propertyParam.get("PORTAL_URL")){
			innerProperty.put("portalUrl", String.valueOf(propertyParam.get("PORTAL_URL")));
		}else{
			innerProperty.put("portalUrl", "");
		}
		if(null != propertyParam.get("DETAIL_PORTAL_URL")){
			innerProperty.put("detailPortalUrl", String.valueOf(propertyParam.get("DETAIL_PORTAL_URL")));
		}else{
			innerProperty.put("detailPortalUrl", "");
		}
		innerProperty.put("pageInType", String.valueOf(propertyParam.get("PAGE_IN_TYPE")));
		innerProperty.put("attrSpecId", String.valueOf(propertyParam.get("ATTR_SPEC_ID")));
		return innerProperty;
	}
	
	private JSONObject getInnerLines(Map<String,Object> lineParam){
		JSONObject innerLines = new JSONObject();
		innerLines.put("from", String.valueOf(lineParam.get("FROM_ENDPOINT_ID")));
		innerLines.put("to", String.valueOf(lineParam.get("TO_ENDPOINT_ID")));
		innerLines.put("synAsyn", String.valueOf(lineParam.get("SYN_ASYN")));
		JSONObject policy = new JSONObject();
		policy.put("routePolicyId", String.valueOf(lineParam.get("ROUTE_POLICY_ID")));
		policy.put("strategy", String.valueOf(lineParam.get("RULE_STRATEGY_ID")));
		if(null != lineParam.get("ROUTE_COND_ID")){
			policy.put("condId", String.valueOf(lineParam.get("ROUTE_COND_ID")));
		}else{
			policy.put("condId", "");
		}
		innerLines.put("policy", policy);
		return innerLines;
	}

	public int countAllMessageFlowList(Map map) {
		return messageFlowDao.countAllMessageFlowList(map);
	}

	public List<Map> getAllMessageFlowList(Map map) {
		return messageFlowDao.getAllMessageFlowList(map);
	}

	public List<Map<String, Object>> getEndpointSpecListByPortal() {
		Map paramMap=new HashMap();
		return messageFlowDao.getEndpointSpecListByPortal(paramMap);
	}

	public List<Map<String, Object>> getRuleStrategyList() {
		return messageFlowDao.getRuleStrategyList();
	}

	public List<HashMap<String, Object>> getAllRouteCond() {
		Map paramMap = new HashMap();
		return messageFlowDao.getAllRouteCond(paramMap);
	}

	public String getRouteConditionExprById(String route_Cond_Id) {
		Map paramMap = new HashMap();
		paramMap.put("route_Cond_Id", route_Cond_Id);
		List resultList = messageFlowDao.getRouteConditionExprById(paramMap);
		String exprStr = "";
		if(resultList.size()>0){
			RouteCondTreeGrid routeCondTreeGrid =(RouteCondTreeGrid)resultList.get(0);   

            String condRelation				= routeCondTreeGrid.getCond_Relation();
            String routeConditionExpr = routeCondTreeGrid.getRoute_Condition_Expr();
            String haveSub						= routeCondTreeGrid.getHave_Sub();
            int subNum = Integer.valueOf(haveSub);
    		if(subNum>0){
    			exprStr = getRouteConditionExprAllChildren(route_Cond_Id, condRelation);
    		}else{
        		exprStr = routeConditionExpr;
    		}
		}
		return exprStr;
	}
	private String getRouteConditionExprAllChildren(String id, String condRelation){
		Map paramMap = new HashMap();
		paramMap.put("up_Route_Cond_Id", id);
		List resultList = messageFlowDao.getRouteCondTreeSubNodeJosn(paramMap);
		StringBuffer exprStr = new StringBuffer("");
		for(int i=0; i<resultList.size(); i++){
			RouteCondTreeGrid routeCondTreeGrid =(RouteCondTreeGrid)resultList.get(i);   
	        String routeCondId				= routeCondTreeGrid.getId();
            String condRelationOther				= routeCondTreeGrid.getCond_Relation();
            String routeConditionExpr = routeCondTreeGrid.getRoute_Condition_Expr();
            String haveSub						= routeCondTreeGrid.getHave_Sub();
            int subNum = Integer.valueOf(haveSub);
			if(i>0){
				exprStr.append(" ");exprStr.append(condRelation);exprStr.append(" ");
			}
    		if(subNum>0){
    			//获取子节点信息
        		String childrenExpr = getRouteConditionExprAllChildren(routeCondId, condRelationOther);
        		exprStr.append("(");exprStr.append(childrenExpr);exprStr.append(")");
    		}else{
    			exprStr.append(routeConditionExpr);
    		}
		}
        return exprStr.toString();
	}

	public void updateRelation(Map<String, String> map) {
		messageFlowDao.updateRelation(map);
	}

	public void deleteRouteCondition(Map<String, String> map) {
		String routeCondId = map.get("routeCondId");
		Map paramMap=new HashMap();
		paramMap.put("routeCondId", routeCondId);
		boolean isUsed = messageFlowDao.assertUsed(paramMap);
		if(!isUsed){
			boolean haveSub = messageFlowDao.haveSub(paramMap);
		    if(!haveSub){
		    	messageFlowDao.deleteRouteCondition(paramMap);
		    	map.put("result", "0000");//成功
		    }else{
		    	map.put("result", "0002");//有子节点不能删除
		    }
		}else{
			map.put("result", "0001");//已经被使用了
		}
	}

	public int countGetValueExprList(Map map) {
		return messageFlowDao.countGetValueExprList(map);
	}

	public List<Map> getGetValueExprList(Map map) {
		return messageFlowDao.getGetValueExprList(map);
	}

	public List<Map<String, Object>> getOperatorListList() {
		return messageFlowDao.getOperatorListList();
	}

	public void addRounteCond(Map<String, String> map) {
		try{
			messageFlowDao.addRounteCond(map);
			map.put("result", "success");
		}catch(Exception e){
			map.put("result", "fail");
		}
	}

	public void updateRounteCond(Map<String, String> map) {
		try{
			messageFlowDao.updateRounteCond(map);
			map.put("result", "success");
		}catch(Exception e){
			map.put("result", "fail");
		}
	}

	public List<Map<String, Object>> getCondEvaluatorCollectionList() {
		Map paramMap = new HashMap();
		return messageFlowDao.getCondEvaluatorCollectionList(paramMap);
	}

	public void addCondEvalExpr(Map<String, String> map) {
		messageFlowDao.addCondEvalExpr(map);
	}

	public void deleteGetValueExpr(Map<String, String> map) {
		String exprId  = map.get("expr_Id");
		Map paramMap = new HashMap();
		paramMap.put("exprId", exprId);
		boolean isUsed = messageFlowDao.getIsUserd(paramMap);
		if(isUsed){
			map.put("result", "0001");//被使用
		}else{
			messageFlowDao.deleteGetValueExpr(map);
			map.put("result", "0000");
		}
	}
	
	public String queryTenantIdByCode(String tenantCode){
		return messageFlowDao.queryTenantIdByCode(tenantCode);
	}
	public Tenant queryTenant(Tenant tenant){
		return messageFlowDao.queryTenant(tenant);
	}
	
}
