/*
 * @(#)ITreeNode.java        2013-6-21
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
 * @author Administrator 2013-6-21
 * <hr>
 * 修改记录
 * <hr>
 * 1、修改人员:    修改时间:<br>       
 *    修改内容:
 * <hr>
 */

public interface ITreeNode {
	/**
	 * 获取节点id
	 * @return String
	 */
	public String getNode();               //获取节点id
	/**
	 * 获取节点对应的url
	 * @return String
	 */
	public String getUrl();                //获取节点对应的url
	/**
	 * 获取节点文本信息
	 * @return String
	 */
	public String getText();               //获取节点文本信息
	/**
	 * 获取父节点id
	 * @return String
	 */
	public String getF_Node();             //获取父节点id
	/**
	 * 获取节点类型
	 * @return String
	 */
	public String getNode_Type();          //获取节点类型
	/**
	 * 获取节点状态
	 * @return String
	 */
	public String getState();              //获取节点状态
	/**
	 * 获取节点图标
	 * @return String
	 */
	public String getIcon();               //获取节点图标
	/**
	 * 获取节点打开时的图标
	 * @return String
	 */
	public String getOpenIcon();           //获取节点打开时的图标
	/**
	 * 获取节点文本前面图标
	 * @return String
	 */
	public String getBeforeIcon();         //获取节点文本前面图标
	/**
	 * 获取节点文本后面图标
	 * @return String
	 */
	public String getAfterIcon();          //获取节点文本后面图标
	/**
	 * 获取第2个checkbox对应的node id
	 * @return String
	 */
	public String getSecond_Node();         //获取第2个checkbox对应的node id
	/**
	 * 判断节点是否有子节点
	 * @return String
	 */
	public String getHave_Sub();           //判断节点是否有子节点
}
