/*
 * @(#)RouteCondTreeGrid.java        2013-7-4
 *
 * Copyright (c) 2013 asiainfo-linkage
 * All rights reserved.
 *
 */

package com.ailk.o2p.messageFlow.bo;

/**
 * 类名称<br>
 * 这里是类的描述区域，概括出该的主要功能或者类的使用范围、注意事项等
 * <p>
 * @version 1.0
 * @author Administrator 2013-7-4
 * <hr>
 * 修改记录
 * <hr>
 * 1、修改人员:    修改时间:<br>       
 *    修改内容:
 * <hr>
 */

public class RouteCondTreeGrid {
	
	private String id;
	
	private String route_Condition_Expr;
	
	private String cond_Relation;
	
	private String get_Value_Expr_Id;
	
	private String have_Sub;
	
	private String operatorChar;
	
	private String match_Value;
	
	private Integer tenantId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRoute_Condition_Expr() {
		return route_Condition_Expr;
	}

	public void setRoute_Condition_Expr(String routeConditionExpr) {
		this.route_Condition_Expr = routeConditionExpr;
	}

	public String getCond_Relation() {
		return cond_Relation;
	}

	public void setCond_Relation(String condRelation) {
		this.cond_Relation = condRelation;
	}

	public String getHave_Sub() {
		return have_Sub;
	}

	public void setHave_Sub(String haveSub) {
		this.have_Sub = haveSub;
	}

	public String getOperatorChar() {
		return operatorChar;
	}

	public void setOperatorChar(String operatorChar) {
		this.operatorChar = operatorChar;
	}

	public String getMatch_Value() {
		return match_Value;
	}

	public void setMatch_Value(String matchValue) {
		this.match_Value = matchValue;
	}

	public String getGet_Value_Expr_Id() {
		return get_Value_Expr_Id;
	}

	public void setGet_Value_Expr_Id(String getValueExpr_Id) {
		this.get_Value_Expr_Id = getValueExpr_Id;
	}

	public Integer getTenantId() {
		return tenantId;
	}

	public void setTenantId(Integer tenantId) {
		this.tenantId = tenantId;
	}
	
	
}
