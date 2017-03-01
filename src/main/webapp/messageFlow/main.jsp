<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html lang="en" class="no-js">
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<meta charset="utf-8"/>
<title>Message Flow</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1" name="viewport"/>
<meta content="" name="description"/>
<meta content="" name="author"/>
<style type="text/css">
.modal.full { top: 1%; right: 1%; left: 1%; bottom: auto; width: auto !important; height: auto !important; margin-left: 0 !important; padding: 0 !important; }
</style>
<!-- BEGIN GLOBAL MANDATORY STYLES 
<link href="http://fonts.googleapis.com/css?family=Open+Sans:400,300,600,700&subset=all" rel="stylesheet" type="text/css"/>-->
<link href="../resources/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css"/>
<link href="../resources/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
<link href="../resources/plugins/bootstrap-toastr/toastr.min.css" rel="stylesheet" type="text/css" />
<link href="../resources/plugins/uniform/css/uniform.default.css" rel="stylesheet" type="text/css"/>
<link href="../resources/plugins/bootstrap-modal/css/bootstrap-modal.css" rel="stylesheet" type="text/css" />
<link href="../resources/plugins/bootstrap-modal/css/bootstrap-modal-bs3patch.css" rel="stylesheet" type="text/css" />
<link href="../resources/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css"/>
<link href="../resources/css/process.css" rel="stylesheet" type="text/css" />
<link href="../resources/css/route.css" rel="stylesheet" type="text/css" />
<link href="../resources/css/query-builder.default.min.css" rel="stylesheet" type="text/css" />

<!-- END GLOBAL MANDATORY STYLES -->
<!-- BEGIN PAGE LEVEL PLUGIN STYLES -->

<!-- END PAGE LEVEL PLUGIN STYLES -->
<!-- BEGIN THEME STYLES -->
<link href="../resources/css/style-template.css" rel="stylesheet" type="text/css"/>
<link href="../resources/css/style.css" rel="stylesheet" type="text/css"/>
<link href="../resources/css/style-responsive.css" rel="stylesheet" type="text/css"/>
<link href="../resources/css/plugins.css" rel="stylesheet" type="text/css"/>
<link href="../resources/css/themes/light.css" rel="stylesheet" type="text/css" id="style_color"/>
<link href="../resources/css/custom.css" rel="stylesheet" type="text/css"/>
<!-- END THEME STYLES -->
</head>
<!-- END HEAD -->
<!-- BEGIN BODY -->
<body class="page-sidebar-fixed">
<INPUT id=serverScheme type=hidden value="${pageContext.request.scheme}"> 
<INPUT id=serverName type=hidden value="${pageContext.request.serverName}"> 
<INPUT id=serverPort type=hidden value="${pageContext.request.serverPort}">
<input type="hidden" id="projectRootPath" value="${pageContext.request.contextPath}"/>
<input type="hidden" id="stateType" value="${stateType}">
<input type="hidden" id="hidMessageFlowId" value="${messageFlowId}">
<input type="hidden" id="orgId" value="${orgId}">
<input type="hidden" id="flag" value="${flag}">
<input type="hidden" id="isOpen" value="${isOpen}">
<input type="hidden" id="optType" value="${optType}">
<input type="hidden" id="pageLineId">
<!-- BEGIN HEADER --> 
<!-- END HEADER --> 
<!-- BEGIN CONTAINER -->
<div class="page-container" id="messageFlow">      
        <div class="toolbar" id="operateList">
          <a id="showUser" class="btn default"><i class="fa fa-user"></i>&nbsp;${userName}</a>
          <button class="btn default editView" data-toggle="modal" data-target="#addModal" id="add"><i class="fa fa-plus"></i> ${local["eaap.op2.conf.messageArrange.dialog.button.add"]}</button>
          <button class="btn default editView" data-toggle="modal" data-target="#messageListModal" id="open"><i class="fa fa-folder-open"></i> ${local["eaap.op2.conf.messageArrange.menu.title.open"]}</button>
          <button class="btn default editView" id="save" ><i class="fa fa-save"></i> ${local["eaap.op2.conf.messageArrange.menu.title.save"]}</button>
          <button class="btn default editView" id="select"><i class="fa fa-arrows"></i> ${local["eaap.op2.conf.server.supplier.choose"]}</button>
          <button class="btn default editView" id="line" ><i class="fa fa-long-arrow-right"></i> ${local["eaap.op2.conf.messageArrange.menu.title.line"]}</button>
          <button class="btn default editView" id="delete" ><i class="fa fa-trash-o"></i> ${local["eaap.op2.conf.messageArrange.menu.title.delete"]}</button>
          <button class="btn default" id="help"><i class="fa fa-question-circle"></i> ${local["eaap.op2.conf.messageArrange.menu.title.help"]}</button>
        </div>
  <!-- BEGIN SIDEBAR -->
  <div class="page-sidebar-wrapper">
    <div class="page-sidebar navbar-collapse collapse"> 
      <!-- add "navbar-no-scroll" class to disable the scrolling of the sidebar menu --> 
      <!-- BEGIN SIDEBAR MENU -->
      <ul class="page-sidebar-menu" data-auto-scroll="true" data-slide-speed="200" id="messageEndpointList">
      <c:forEach items="${endpointSpecList}" var="obj" varStatus="status">
        <c:choose>
				<c:when test="${status.first}">
					<li class="start" endpointSpecId="${obj.ENDPOINT_SPEC_ID }" endpointSpecName="${obj.ENDPOINT_SPEC_NAME}" specDesc="${obj.ENDPOINT_SPEC_DESC}" endpointSpecCode="${obj.ENDPOINT_SPEC_CODE}"> 
					<a href="javascript:;"> 
					<img src="../resources/img/tool/${obj.ENDPOINT_SPEC_PIC}" height="20"> 
					<span class="title"> ${obj.ENDPOINT_SPEC_NAME} </span> </a> </li>
				</c:when>
				<c:when test="${status.last}">
					<li class="last" endpointSpecId="${obj.ENDPOINT_SPEC_ID }" endpointSpecName="${obj.ENDPOINT_SPEC_NAME}" specDesc="${obj.ENDPOINT_SPEC_DESC}" endpointSpecCode="${obj.ENDPOINT_SPEC_CODE}"> 
					<a href="javascript:;"> 
					<img src="../resources/img/tool/${obj.ENDPOINT_SPEC_PIC}" height="20"> 
					<span class="title"> ${obj.ENDPOINT_SPEC_NAME} </span> </a> </li>
				</c:when>
			<c:otherwise>
				 <li endpointSpecId="${obj.ENDPOINT_SPEC_ID }" endpointSpecName="${obj.ENDPOINT_SPEC_NAME}" specDesc="${obj.ENDPOINT_SPEC_DESC}" endpointSpecCode="${obj.ENDPOINT_SPEC_CODE}">
				 <a href="javascript:;"> 
				 <img src="../resources/img/tool/${obj.ENDPOINT_SPEC_PIC}" height="20">
				  <span class="title"> ${obj.ENDPOINT_SPEC_NAME} </span> </a> </li>
			</c:otherwise>
		</c:choose>	
      </c:forEach>
      </ul>
      <!-- END SIDEBAR MENU --> 
    </div>
  </div>
  <!-- END SIDEBAR --> 
  <!-- BEGIN CONTENT -->
  <div class="page-content-wrapper">
    <div class="page-content">
      <div id="canvas"></div>
    </div>
  </div>
  <!-- END CONTENT --> 
</div>
<!-- END CONTAINER --> 
<!-- BEGIN FOOTER --> 
<!-- END FOOTER --> 
<!-- BEGIN JAVASCRIPTS(Load javascripts at bottom, this will reduce page load time) --> 
<!-- BEGIN CORE PLUGINS --> 
<!--[if lt IE 9]>
<script src="../resources/plugins/respond.min.js"></script>
<script src="../resources/plugins/excanvas.min.js"></script> 
<![endif]--> 
<script src="../resources/plugins/jquery-1.10.2.min.js" type="text/javascript"></script> 
<script src="../resources/plugins/jquery-migrate-1.2.1.min.js" type="text/javascript"></script> 
<script src="../resources/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script> 
<script src="../resources/plugins/jquery-slimscroll/jquery.slimscroll.min.js" type="text/javascript"></script> 
<script src="../resources/plugins/uniform/jquery.uniform.min.js" type="text/javascript"></script> 
<script src="../resources/plugins/jquery.cokie.min.js" type="text/javascript"></script> 
<script src="../resources/plugins/bootstrap-toastr/toastr.min.js" type="text/javascript" ></script>
<script src="../resources/plugins/bootstrap-modal/js/bootstrap-modal.js" type="text/javascript"></script> 
<script src="../resources/plugins/bootstrap-modal/js/bootstrap-modalmanager.js" type="text/javascript"></script> 
<!-- END CORE PLUGINS --> 
<!-- BEGIN PAGE LEVEL PLUGINS --> 

<!-- END PAGE LEVEL PLUGINS --> 
<!-- BEGIN PAGE LEVEL SCRIPTS --> 
<script src="../resources/scripts/core/app.js" type="text/javascript"></script> 
<script src="../resources/scripts/custom/messageFlow.js" type="text/javascript"></script> 
<script src="../resources/scripts/custom/messageflowaction.js" type="text/javascript"></script> 
<script src="../resources/plugins/data-tables/jquery.dataTables.min.js" type="text/javascript"></script> 
<script src="../resources/plugins/data-tables/DT_bootstrap.js" type="text/javascript"></script>
<script src="../resources/scripts/custom/route.js" type="text/javascript"></script>  
<script src="../resources/scripts/custom/routeMachList.js" type="text/javascript"></script>
<script type="text/javascript" src="../resources/plugins/select2/select2.min.js"></script> 

<!-- END PAGE LEVEL SCRIPTS --> 
<script>
var addOrOpenFirst = '${local["eaap.op2.conf.messageArrange.error.addfirsts"]}';
var lineExists = '${local["eaap.op2.conf.messageArrange.error.lineExists"]}';
var messageNameNotNull = '${local["eaap.op2.conf.messageArrange.error.messageNameNotNull"]}';
var oneRecords = '${local["eaap.op2.conf.messageArrange.error.oneRecords"]}';
var dataNotComplete = '${local["eaap.op2.conf.messageArrange.error.dataNotComplete"]}';
var noData = '${local["eaap.op2.conf.messageArrange.error.noData"]}';
var oneBeginNode = '${local["eaap.op2.conf.messageArrange.error.oneBeginNode"]}';
var oneEndNode = '${local["eaap.op2.conf.messageArrange.error.oneEndNode"]}';
var showAttrSpecName = '${local["eaap.op2.conf.techimpl.attrSpecName"]}';
var showEndpoint = '${local["eaap.op2.conf.messageArrange.menu.title.endpoint"]}';
var notEmpty = '${local["eaap.op2.conf.messageArrange.error.notEmpty"]}';
var saveError = '${local["eaap.op2.conf.messageArrange.alert.info.saveErrot"]}';
var saveSuccess = '${local["eaap.op2.conf.messageArrange.alert.info.saveAndRefresh"]}';
var showChoose = '${local["eaap.op2.conf.server.supplier.choose"]}';
var showDetail = '${local["eaap.op2.conf.messageArrange.error.detail"]}';
var deleteOK = '${local["eaap.op2.conf.techimpl.deleteOK"]}';
var deleteFail = '${local["eaap.op2.conf.techimpl.deleteFail"]}';
var cannotDel = '${local["eaap.op2.conf.messageArrange.error.cannotDel"]}';
var haveSub = '${local["eaap.op2.conf.messageArrange.alert.info.deleteRouteConditionWithSubNode"]}';
jQuery(document).ready(function() {    
   App.init(); // initlayout and core plugins
   MF.init(); 
   $("#messageFlow").messagesport();
   var isOpen = $('#isOpen').val();
   if('Yes' == isOpen){
	   $('#showUser').hide();
	   $('#open').attr('disabled',false);
	   $('#open').show();
	   $('#add').attr('disabled',false);
	   $('#add').show();
   }else{
	   $('#showUser').show();
	   $('#open').attr('disabled',true);
	   $('#open').hide();
	   $('#add').attr('disabled',true);
	   $('#add').hide();
   }

   var optType = $("#optType").val();
   if(optType=="view"){		// edit / view
	   $(".editView").hide();
   }
   
   routeMachList.init();
});
/**
 * 得到路由规则的ID
 */
function getMessageFlowSeq(){
	var messageFlowId = '';
	$.ajax({
		type: "POST",
		async:false,
	    url: "../messageFlow/getMessageFlowSeq.shtml",
	    dataType:'json',
	    data:null,
		success:function(msg){
			messageFlowId = msg.messageFlowId;
       }
    });
	return messageFlowId;
}
</script> 
<!-- END JAVASCRIPTS -->
</body>
<!-- END BODY -->
<div id="addModal" class="modal fade" tabindex="-1" style="display: none;">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
    <h4 class="modal-title">${local["eaap.op2.conf.messageArrange.menu.title.messageFlow"]}</h4>
  </div>
  <div class="modal-body">
    <div class="form-body form-horizontal">
      <div class="form-group">
        <label class="col-md-5 control-label">${local["eaap.op2.conf.messageArrange.dialog.head.messageFlowName"]}:</label>
        <div class="col-md-7">
          <input type="text" id="pageNewMessageFlow"  class="form-control">
        </div>
      </div>
    </div>
  </div>
  <div class="modal-footer">
    <button type="button" class="btn btn-default" data-dismiss="modal">${local["eaap.op2.conf.messageArrange.dialog.button.close"]}</button>
    <button type="button" class="btn yellow" id="pageMessageFlowOK">${local["eaap.op2.conf.messageArrange.dialog.button.ok"]}</button>
  </div>
</div>
<!-- <div id="addModalAddap" class="modal  container fade" tabindex="-1" style="display: none;"> -->
<div id="addModalAddap" class="modal full fade" tabindex="-1" style="display: none;">
<%--   <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
    <h4 class="modal-title">${local["eaap.op2.conf.messageArrange.error.pageConfig"]}</h4>
  </div> --%>
  <!-- <div class="modal-body"> -->
  <div class="">
    <!-- <div class="form-body form-horizontal"> -->
    <div class="">
      <!-- <iframe name="OpenartDialog14375362425313" id="pageValueFromSpecPage" src="" frameborder="0" style="border: 0px currentColor; border-image: none; width: 100%; height: 520px;" allowtransparency="true"></iframe> -->
      <iframe name="OpenartDialog14375362425313" id="pageValueFromSpecPage" src="" frameborder="0" style="border: 0px currentColor; border-image: none; width: 100%; height: 650px;" allowtransparency="true"></iframe>
      <input type="hidden" class="btn btn-default" id="closeButton" data-dismiss="modal"/>
    </div>
  </div>
</div>
<div id="detailModal" class="modal container fade" tabindex="-1" style="display: none;">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
    <h4 class="modal-title">${local["eaap.op2.conf.messageArrange.tab.title.endpointProperty"]}</h4>
  </div>
  <div class="modal-body">
    <div class="form-body">
      <div class="row">
        <div class="col-md-6">
          <div class="form-group">
            <label class="control-label">${local["eaap.op2.conf.messageArrange.tab.properties.endpointID"]}</label>
            <input type="text"  readonly class="form-control" id="pageEndpointId">
          </div>
        </div>
        <!--/span-->
        <div class="col-md-6">
          <div class="form-group">
            <label class="control-label"> ${local["eaap.op2.conf.messageArrange.tab.properties.endpointName"]}</label>
            <input type="text" placeholder="Transformer" class="form-control" id="pageEndpointName" onkeyup="javascript:changeEndpointName(this.value);" onchange="javascript:changeJsonValue('endpointName',this.value);">
          </div>
        </div>
        <!--/span-->
      </div>
      <div class="row">
        <div class="col-md-6">
          <div class="form-group">
            <label class="control-label">${local["eaap.op2.conf.messageArrange.tab.properties.specificationName"]} </label>
            <input type="text" readonly  class="form-control" id="pageSpecificationName">
          </div>
        </div>
        <!--/span-->
        <div class="col-md-6">
          <div class="form-group">
            <label class="control-label"> ${local["eaap.op2.conf.messageArrange.tab.properties.specificationCode"]} </label>
            <input type="text" readonly class="form-control" id="pageSpecificationCode">
          </div>
        </div>
        <!--/span--> 
      </div>
      <div class="row">
        <div class="col-md-6">
          <div class="form-group">
            <label class="control-label">${local["eaap.op2.conf.messageArrange.tab.properties.inputDataType"]} </label>
            <select class="form-control" id="pageInputDataType" onchange="javascript:changeJsonValue('inputDataType',this.value);">
              <c:forEach items="${in_Data_TypeList}" var="obj" varStatus="status">
                <option value="${obj.DATA_TYPE_ID}">${obj.DATA_TYPE_NAME}</option>
              </c:forEach>
            </select>
          </div>
        </div>
        <!--/span-->
        <div class="col-md-6">
          <div class="form-group">
            <label class="control-label">${local["eaap.op2.conf.messageArrange.tab.properties.outputDataType"]} </label>
            <select class="form-control" id="pageOutDataType" onchange="javascript:changeJsonValue('outputDataType',this.value);">
             <c:forEach items="${in_Data_TypeList}" var="obj" varStatus="status">
                <option value="${obj.DATA_TYPE_ID}">${obj.DATA_TYPE_NAME}</option>
              </c:forEach>	
            </select>
          </div>
        </div>
        <!--/span-->
      </div>
      <div class="row" id="showLog" style="display:none;">
        <div class="col-md-6">
          <div class="form-group">
            <label class="control-label">${local["eaap.op2.conf.messageArrange.tab.properties.allowInputTrace"]}</label>
            <select class="form-control" id="pageTracingFlag" onchange="javascript:changeJsonValue('tracingFlag',this.value);">
              <option value="Y">${local["eaap.op2.conf.messageArrange.tab.properties.allow"]}</option>
              <option value="N">${local["eaap.op2.conf.messageArrange.tab.properties.notAllow"]}</option>
            </select>
          </div>
        </div>
        <!--/span-->
        <div class="col-md-6">
          <div class="form-group">
            <label class="control-label">${local["eaap.op2.conf.messageArrange.tab.properties.allowInputDataLog"]}</label>
            <select class="form-control" id="pageDataLogFlag" onchange="javascript:changeJsonValue('dataLogFlag',this.value);">
              <option value="Y">${local["eaap.op2.conf.messageArrange.tab.properties.allow"]}</option>
              <option value="N">${local["eaap.op2.conf.messageArrange.tab.properties.notAllow"]}</option>
            </select>
          </div>
        </div>
    </div>
    <div id="dyPropery"><%--动态属性展示区 --%>
    </div>
    <div class="row">
        <div class="col-md-12">
          <div class="form-group">
            <label class="control-label">${local["eaap.op2.conf.messageArrange.tab.properties.endpointDescription"]}</label>
            <input type="text" placeholder="" class="form-control" id="pageEndpointDesc" onchange="javascript:changeJsonValue('endpointDesc',this.value);">
          </div>
        </div>
    </div>    
    <div class="row">
        <div class="col-md-12">
          <div class="form-group">
            <label class="control-label">${local["eaap.op2.conf.messageArrange.tab.properties.specificationDescription"]}</label>
            <textarea  class="form-control" readonly id="pageSepcDesc" rows="5"></textarea>
          </div>
        </div>
    </div>
  </div>
  <div class="modal-footer">
    <button type="button" class="btn btn-default" data-dismiss="modal">${local["eaap.op2.conf.messageArrange.dialog.button.close"]}</button>
    <button type="button" class="btn yellow editView" data-dismiss="modal">${local["eaap.op2.conf.messageArrange.dialog.button.ok"]}</button>
  </div>
</div>
<!-- MessageFlowList -->
<div id="messageListModal" class="modal container fade" tabindex="-1" style="display: none;">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
    <h4 class="modal-title">${local["eaap.op2.conf.messageArrange.dialog.title.messageflowlist"]}</h4>
  </div>
  <div class="modal-body">
    <div>

	<div class="margin-bottom-20">
    <div class="box s-protlet-theme">
      <div class="portlet-body">
      	<div class="panel-body" style="border-bottom:1px solid #dfdfdf; margin-bottom:20px">
  	<form class="form-horizontal" role="form">
         <div class="form-group">
           <label class="col-md-2 control-label"  style="text-align:left">${local["eaap.op2.conf.messageArrange.dialog.head.messageFlowID"]}:</label>
           <div class="col-md-3" ><input class="form-control" id="pageMessageFlowId"  placeholder="Enter text"></div>
           <label class="col-md-2 control-label" style="text-align:left">${local["eaap.op2.conf.messageArrange.dialog.head.messageFlowName"]}:</label>
           <div class="col-md-3">
           <input class="form-control" id="pageMessageFlowName"  placeholder="Enter text">
           </div>
           <div class="col-md-2">
           	<input class="btn btn-warning" type="button" value='${local["eaap.op2.conf.messageArrange.dialog.button.query"]}' id="pageSeach">
           </div>
         </div>
        </form>	
        </div>
        <table class="table table-bordered  nowrap-ingore group-check th-center" id="dt">
          <thead>
            <tr>
              <th width="5%">${local["eaap.op2.conf.server.supplier.choose"]}</th>
              <th width="45%">${local["eaap.op2.conf.messageArrange.dialog.head.messageFlowID"]}</th>
              <th width="50%">${local["eaap.op2.conf.messageArrange.dialog.head.messageFlowName"]}</th>
            </tr>
          </thead>
          <tbody>
          </tbody>
        </table>
      </div>
    </div>
  </div>

  </div>
  <div class="modal-footer">
    <button type="button" class="btn btn-default" data-dismiss="modal">${local["eaap.op2.conf.messageArrange.dialog.button.close"]}</button>
    <button type="button" class="btn yellow" id="messageFlowOpen">${local["eaap.op2.conf.messageArrange.menu.title.open"]}</button>
  </div>
</div>
<!--node end-->
<!-- nodedetail -->
<div id="lineDetailModal" class="modal container fade" tabindex="-1" style="display: none;">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
    <h4 class="modal-title">${local["eaap.op2.conf.messageArrange.tab.properties.routePolicy"]}</h4>
  </div>
  <div class="modal-body">
    <div class="form-body">
      <div class="row">
        <div class="col-md-6">
          <div class="form-group">
            <label class="control-label"> ${local["eaap.op2.conf.messageArrange.tab.properties.messageRouteMethod"]}：</label>
            <select class="form-control" id="pageSynAsyn" onchange="javascript:changeSynAsyn(this.value);">		
				<option value="SYN">${local["eaap.op2.conf.messageArrange.tab.properties.synchronization"]}
    			</option>
    			<option value="ASYN">${local["eaap.op2.conf.messageArrange.tab.properties.asynchronization"]}
    			</option>				
            </select>
          </div>
        </div>
        <!--/span-->
        <div class="col-md-6">
          <div class="form-group">
            <label class="control-label">${local["eaap.op2.conf.messageArrange.tab.properties.routePolicy"]}：</label>
            <select class="form-control" id="pageRoutePolicy" onchange="javascript:changeRoutePolicy(this.value);">
              	<c:forEach items="${ruleStrategyList}" var="obj" varStatus="status">
                <option value="${obj.RULE_STRATEGY_ID}">${obj.RULE_STRATEGY_CODE}</option>
              </c:forEach>	
            </select>
          </div>
        </div>
        <!--/span-->
      </div>
      <div class="row">
        <div class="col-md-11">
          <div class="form-group">
            <label class="control-label">${local["eaap.op2.conf.messageArrange.tab.properties.routeCondition"]}：</label>
            <textarea  placeholder="" class="form-control" readonly id="pageRouteCondition" rows="3"></textarea>
          </div>
        </div>
        <!--/span-->
        <div class="col-md-1">
          <div class="form-group">
            <label class="control-label">&nbsp; </label>
            <input type="button" class="form-control margin-top-10" id="pageCleanRouteCond" value="clear">
          </div>
        </div>
       
        <!--/span-->
      </div>
      <div id="pageRouteTable" style="display:none;">
       <div class="btn-group group-conditions  group-top"><span class="col-tit-1">${local["eaap.op2.conf.messageArrange.dialog.head.id"]}</span><span class="col-tit-2">${local["eaap.op2.conf.messageArrange.tab.properties.routeCondition"]}</span><span  class="col-tit-3">${local["eaap.op2.conf.messageArrange.dialog.properties.comparisonOperators"]}</span><span  class="col-tit-4">${local["eaap.op2.conf.messageArrange.dialog.properties.matchValue"]}</span><span class="col-tit-5">${local["eaap.op2.conf.messageArrange.menu.title.operate"]}</span></div> 
      <div class="query-builder" id="builder-basic">
	<dl class="rules-group-container">   
    	<dt class="rules-group-header">       	     
        	<div class="btn-group pull-right group-actions"> 
            	<div class="selectlist operateroute"><span>${local["eaap.op2.conf.messageArrange.top.menu.addRouteCondition"]}</span><ul><li id="pageParentAddRule">${local["eaap.op2.conf.messageArrange.top.menu.addRouteCondition"]}</li><li id="pageParentAddGroup">${local["eaap.op2.conf.messageArrange.top.menu.addGroup"]}</li></ul></div>   
            	<!--<button class="btn btn-xs btn-success" type="button" data-add="rule"><i class="glyphicon glyphicon-plus"></i>Add rule </button>                <button class="btn btn-xs btn-success" type="button" data-add="group"><i class="glyphicon glyphicon-plus-sign"></i> Add group</button>
                -->           </div>     
            <div class="btn-group group-conditions">
            	<label class="btn btn-xs btn-primary active">${local["eaap.op2.conf.messageArrange.dialog.properties.and"]}</label>                
                <label class="btn btn-xs btn-primary">${local["eaap.op2.conf.messageArrange.dialog.properties.or"]}</label>
            </div>        
            <div class="error-container" data-toggle="tooltip"><i class="glyphicon glyphicon-warning-sign"></i></div>        
        </dt>   
        <dd class="rules-group-body">
        	<ul class="rules-list" id="builder-container">
            	<li class="rule-container">   
                	<div class="rule-header">     
                    	<div class="btn-group pull-right rule-actions">       
                        	<button class="btn btn-xs btn-danger" type="button" data-delete="rule"><i class="glyphicon glyphicon-remove"></i>${local["eaap.op2.conf.messageArrange.menu.title.delete"]}</button>     
                        </div>   
                    </div>        
                    <div class="error-container"><i class="glyphicon glyphicon-warning-sign"></i></div><div class="rule-filter-container">
                    	<select class="form-control">   
                        	<option>------</option>             
                            <option>Name</option>
                        </select>
                    </div>   
                    <div class="rule-operator-container">
                    	<select class="form-control">        
                        	<option >equal</option>        
                            <option>not equal</option>
                        </select>
                    </div>   
                    <div class="rule-value-container">
                    	<input class="form-control" >
                    </div> 
                </li>
                <dl class="rules-group-container">   
                	<dt class="rules-group-header">     
                    	<div class="btn-group pull-right group-actions">
                        	
                        	<!--<button class="btn btn-xs btn-success" type="button" data-add="rule"><i class="glyphicon glyphicon-plus"></i> Add rule       </button>                	<button class="btn btn-xs btn-success" type="button" data-add="group"><i class="glyphicon glyphicon-plus-sign"></i> Add group</button>                   <button class="btn btn-xs btn-danger" type="button" data-delete="group"><i class="glyphicon glyphicon-remove"></i> Delete</button>-->            		</div>     
                        <div class="btn-group group-conditions fatherstate">
                        	<label class="btn btn-xs btn-primary active">${local["eaap.op2.conf.messageArrange.dialog.properties.and"]}</label>                			
                            <label class="btn btn-xs btn-primary">${local["eaap.op2.conf.messageArrange.dialog.properties.or"]}</label></div>          
                        <div class="error-container" data-toggle="tooltip">
                        	<i class="glyphicon glyphicon-warning-sign"></i>
                        </div>
                    </dt>   
                    <dd class="rules-group-body">
                    	<ul class="rules-list">
                        	<li class="rule-container" id="rulemodal">
                            	<div class="rule-header">
                                	<div class="btn-group pull-right rule-actions">
                                    	<button class="btn btn-xs btn-danger" type="button" data-delete="rule"><i class="glyphicon glyphicon-remove"></i> ${local["eaap.op2.conf.messageArrange.menu.title.delete"]}</button>     
                                    </div>
                                </div>        
                                <div class="error-container"><i class="glyphicon glyphicon-warning-sign"></i></div>
                                <div class="rule-filter-container">
                                	<select class="form-control">   
                                    	<option>------</option>            
                                        <option>Name</option>             
                                        <option>Category</option>
                                    </select>
                                </div>   
                                <div class="rule-value-container">
                                	<select  class="form-control">
                                    	<option>Books</option> 
                                        <option>Movies</option>
                                    </select>
                                </div> 
                            </li>
                        </ul>   
                    </dd> 
            	</dl>
            </ul>   
        </dd> 
    </dl>
</div>
</div>
  </div>
  <div class="modal-footer">
    <button type="button" class="btn btn-default" data-dismiss="modal">${local["eaap.op2.conf.messageArrange.dialog.button.close"]}</button>
    <button type="button" class="btn yellow" data-dismiss="modal">${local["eaap.op2.conf.messageArrange.dialog.button.ok"]}</button>
  </div>
</div>
<!--node end-->

<!-- nodedetail -->
<div id="messageRouteModal" class="modal fade container" tabindex="-1" style="display:none;">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
    <h4 class="modal-title">${local["eaap.op2.conf.messageArrange.tab.properties.routePolicy"]}</h4>
  </div>
  <div class="modal-body">
    <div>

	<div class="margin-bottom-20">
    <div class="box s-protlet-theme">
      <div class="portlet-body" id="routecondition">
        <table class="table table-bordered  nowrap-ingore group-check th-center" >
          <tbody>
          	<tr><td class="text-right" width="35%">${local["eaap.op2.conf.messageArrange.tab.properties.routeCondition"]}: </td>
          	<td  width="65%"> 
          	<input type="hidden" id="pageHidExprId">
          	<input type="hidden" id="pageUpExprId">
          	<input type="hidden" id="pageRouteCondtionId">
          	<input class="form-control"  readonly id="pageExpr" style="width:620px; float:left">
          	&nbsp;
          	<button type="button" class="btn btn-default"  id="chooseRcondition">${local["eaap.op2.conf.server.supplier.choose"]}</button>
          	</td>
          	</tr>
          	<tr>
            <td class="text-right"  width="35%">${local["eaap.op2.conf.messageArrange.dialog.properties.comparisonOperators"]}: </td>
            <td  width="65%">
            <select  class="form-control" id="pageOperator"  style="width:100px; float:left">
            <c:forEach items="${operatorList}" var="obj" varStatus="status">
                <option value="${obj.OPERATOR_ID}">${obj.OPERATOR_CODE}</option>
              </c:forEach>
            </select>
            </td>
            </tr>
            <tr>
            <td class="text-right"  width="35%">${local["eaap.op2.conf.messageArrange.dialog.properties.matchValue"]}: </td>
            <td  width="65%">
            <input class="form-control" id="pageMatchValue"></td>
            </tr>
          </tbody>
        </table>
        <div class="modal-footer">
    	<button type="button" class="btn btn-default" data-dismiss="modal">${local["eaap.op2.conf.messageArrange.dialog.button.close"]}</button>
    	<button type="button" class="btn yellow" id="pageAddRouteCondition">${local["eaap.op2.conf.messageArrange.dialog.button.ok"]}</button>
  	  </div>
      </div>
      
      <div class="portlet-body" id="routemachlistcont" style="display:none">
      	<div class="panel-body">
  	<form class="form-horizontal" role="form">
         <div class="form-group">
           <label class="col-md-3 control-label"  style="text-align:left">${local["eaap.op2.conf.messageArrange.dialog.properties.valuesExpressionContent"]}:</label>
           <div class="col-md-3" >
           <input class="form-control" id="pageSeachExpr"  placeholder="Enter text">
           </div>
           <div class="col-md-2">
           	<input class="btn btn-warning" type="button" value='${local["eaap.op2.conf.messageArrange.dialog.button.query"]}' id="pageDt2Seach">
           </div>
         </div>
        </form>	
        </div>
        <div class="portlet-title">
        	<div class="actions tableoperate">
                   <button class="btn btn-default" id="btn-add" data-toggle="modal" data-target="#getvalueexprwin"><i class="fa fa-plus"></i>${local["eaap.op2.conf.messageArrange.dialog.button.add"]}</button>
                  <button class="btn btn-default" id="btn-del"><i class="fa fa-trash-o"></i>${local["eaap.op2.conf.messageArrange.menu.title.delete"]}</button>
       		</div>
       </div>
        <table class="table table-bordered  nowrap-ingore group-check th-center" id="dt2">
          <thead>
            <tr>
              <th width="2%"></th>
              <th width="8%">${local["eaap.op2.conf.messageArrange.dialog.head.id"]}</th>
              <th width="40%">${local["eaap.op2.conf.manager.auth.authformula"]}</th>
              <th width="25%">${local["eaap.op2.conf.messageArrange.dialog.head.parsingMechanismCoding"]} </th>
              <th width="25%">${local["eaap.op2.conf.messageArrange.dialog.head.parsingMechanismName"]} </th>
            </tr>
          </thead>
          <tbody>
          </tbody>
        </table>
        <div class="modal-footer">
			<button type="button" class="btn yellow" id="chooseroutecondition">${local["eaap.op2.conf.messageArrange.dialog.button.ok"]}</button>
  		</div>
      </div>
    </div>
  </div>

  </div>
  
</div>
<!-- messageRouteModal end -->
<!-- etvalueexprwin -->
<div id="getvalueexprwin" class="modal fade" tabindex="-1" style="widht:600px;display:none;">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
    <h4 class="modal-title">${local["eaap.op2.conf.messageArrange.dialog.title.getValueExprWin"]}</h4>
  </div>
  <div class="modal-body">
    <div>

	<div class="margin-bottom-20">
    <div class="box s-protlet-theme">
      <div class="portlet-body">
        <table class="table table-bordered  nowrap-ingore group-check th-center" >
          <tbody>
          	<tr><td class="text-right" width="40%">${local["eaap.op2.conf.messageArrange.menu.title.conditionEvaluator"]}: </td>
          	<td  width="60%">
          	 <select  class="form-control" id="pageCondEval">
          	   <c:forEach items="${condEvaluatorCollectionList}" var="obj" varStatus="status">
                <option value="${obj.COND_EVALUATOR_ID}">${obj.COND_EVALUATOR_NAME}</option>
              </c:forEach>
          	 </select>
          	 </td></tr>
          	 <tr><td class="text-right" width="40%">Expression Type: </td>
          	<td  width="60%">
          	 <select  class="form-control" id="pageExprType">
                <option value="1">JSON,XML Analysis</option>
                <option value="2">OGNL Expression</option>
          	 </select>
          	 </td></tr>
            <tr><td class="text-right"  width="40%">${local["eaap.op2.conf.messageArrange.dialog.properties.expression"]}: </td>
            <td  width="60%">
            <input class="form-control" id="pageCondEvalExpr">
            </td></tr>
          </tbody>
        </table>
      </div>
      <div class="modal-footer">
    	<button type="button" class="btn btn-default" data-dismiss="modal">${local["eaap.op2.conf.messageArrange.dialog.button.cancel"]}</button>
    	<button type="button" class="btn yellow" id="pageCondEvalOK">${local["eaap.op2.conf.messageArrange.dialog.button.ok"]}</button>
  	  </div>
    </div>
  </div>

  </div>
</div>
<!--etvalueexprwin end-->
</html>