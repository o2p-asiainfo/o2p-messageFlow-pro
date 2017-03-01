var josnObj={};
var setRoute=function(){
	var loadRoute=function(e){
		$.ajax({
			type: "POST",
			async:false,
		    url: "../messageFlow/loadRouteCond.shtml",
		    dataType:'json',
		    data:null,
			success:function(value){ 
				josnObj = value;
				//alert(JSON.stringify(josnObj));
	          }
	     });
	};
	var dargConfig=function(){
		var josnObj1=josnObj.T1011;
    	$("#builder-container").empty();
		var state=josnObj.realate;
		
		if(state=="and"){
			$(".fatherstate label:first").addClass("active").siblings().removeClass("active");
			}
		else{
			$(".fatherstate  label:last").addClass("active").siblings().removeClass("active");
			}
		var parid;
    	$.each(josnObj1,function(key,val){
			if(val.parentId=="0"){
				parid=val.parentId;
				$("#builder-container").attr("objid",parid);
			} 		
    	});	
		findChild("0");
	};
	
	var findChild=function(e){
		var josnObj2=josnObj.T1011;
		var hasChild=false;
		var meth;
		var modelgroup=$("#groupmodal").clone(true);
		modelgroup.show().removeAttr("id");
		var modelrule=$("#rulemodal").clone(true);
		modelrule.show().removeAttr("id");
		$.each(josnObj2,function(key,val){
			if(val.parentId==e){
				hasChild=true;	
				meth=haveChild(key);
				if(val.realate!=undefined){
				var text='<dl class="rules-group-container" realate="'+val.realate+'" valueId="'+key+'"><dt class="rules-group-header"><div class="btn-group pull-right group-actions"><div class="selectlist operateroute"><span>operate</span><ul><li flag="add">Add Route Condition</li><li flag="group">Add Group</li><li flag="set">Set As Route Condition</li><li flag="delete">Delete Group</li></ul></div></div>';
				if(val.realate=="and"){
					text+='<label class="btn btn-xs btn-primary  active">AND</label><label class="btn btn-xs btn-primary">OR</label>';
					}
				else{
					text+='<label class="btn btn-xs btn-primary">AND</label><label class="btn btn-xs btn-primary active">OR</label>';
					}
				text+='</div><div class="error-container" data-toggle="tooltip"><i class="glyphicon glyphicon-warning-sign"></i><div class="btn-group group-conditions"></div></dt><dd class="rules-group-body"><ul class="rules-list"   objid="'+key+'"></ul></dd></dl>';
					$("ul.rules-list[objid="+e+"]").append(text);
					findChild(key);	
					}
				else{
					var newgrouprule=modelrule;
					newgrouprule.attr("objid",key);
					var str='<li class="rule-container" objValue="'+val.matchValue+'" exprId="'+val.exprId+'" objName="'+val.menuname+'" operId="'+val.operatorId+'" objid="'+key+'"><div class="rule-header"><div class="btn-group pull-right rule-actions"><div class="selectlist operateroute"><span>operate</span><ul><li flag="modify">Modify Route Condition</li><li flag="delete">Delete Route Condition</li><li flag="set">Set As Route Condition</li></ul></div></div></div><div class="error-container"><i class="glyphicon glyphicon-warning-sign"></i></div><div class="rule-filter-container"></div><div class="rule-value-container"><span class="routeid">'+key+'</span><span class="routecondition">'+val.menuname+'</span><span class="comparison">'+val.operatorCode+'</span><span class="matchvalues">'+val.matchValue+'</span></div></li>';
					$("ul.rules-list[objid="+e+"]").append(str);
					newgrouprule=null;
					}	
				}
				
    	});
		};
	var haveChild=function(e){
		var josnObj3=josnObj.T1011;
		var ishaschilid=false;
		$.each(josnObj3,function(key,val){
			if(val.parentId==e){
				ishaschilid=true;
			}
    	});
		return ishaschilid;
		};
	return {
        init: function(e){
             loadRoute(e);
			 dargConfig();
        },
    };
}();
function deleteGroup(e){
	}
function deleteRule(e){
	}
function addGroup(e){
	}
function addRule(e){
	}
/**
 * 设置父节点关系
 * @param id
 * @param relation
 */
function setContion(id,relation){
	$.ajax({
		type: "POST",
		async:false,
	    url: "../messageFlow/changeRelation.shtml",
	    dataType:'json',
	    data:{routeCondId:id,
	    	relation:relation
	    	},
		success:function(value){ 
			if('success'==value.result){
				toastr.success(saveSuccess);
			}else{
				setRoute.init();
				toastr.error(saveError);
			}
          }
     });
	}
$(function(){
	$(".rule-value-container select").live("change",function(){
		if($(this).val()=="1"){$("#messageRouteModal").modal("show");}
	   
	   });
	$('#pageParentAddRule').live('click',function(){
		$("#messageRouteModal").modal("show");
		var curid='0';//父类ID默认为0
		$('#pageUpExprId').val(curid);
		$('#pageHidExprId').val('');
		$('#pageExpr').val('');
		$('#pageMatchValue').val('');
		$("#routecondition").show();
		$("#routemachlistcont").hide();
	});
	$('#pageParentAddGroup').live('click',function(){
		var curid='0';//父类ID默认为0
		$.ajax({
			type: "POST",
			async:false,
		    url: "../messageFlow/addRounteCond.shtml",
		    dataType:'json',
		    data:{
		    	pageCondRel:'OR',
		    	pageUpExprId:curid
		    	},
			success:function(msg){
				if('success' == msg.result){
					setRoute.init();//路由数据重刷
					$("#messageRouteModal").modal("hide");
					toastr.success(saveSuccess);
				}else{
					toastr.error(saveError);
				}
	         }
	     });
	})
	$(".group-actions .operateroute ul li").live("click",function(){
		if($(this).attr('flag')=="add"){
			$("#messageRouteModal").modal("show");
			var curid=$(this).closest(".rules-group-container").attr("valueId");
			$('#pageUpExprId').val(curid);
			$('#pageHidExprId').val('');
			$('#pageExpr').val('');
			$('#pageMatchValue').val('');
			$("#routecondition").show();
			$("#routemachlistcont").hide();
			}
		else if($(this).attr('flag')=="group"){
			var curid=$(this).closest(".rules-group-container").attr("valueId");
			$.ajax({
    			type: "POST",
    			async:false,
    		    url: "../messageFlow/addRounteCond.shtml",
    		    dataType:'json',
    		    data:{
    		    	pageCondRel:'OR',
    		    	pageUpExprId:curid
    		    	},
    			success:function(msg){
    				if('success' == msg.result){
    					setRoute.init();//路由数据重刷
    					$("#messageRouteModal").modal("hide");
    					toastr.success(saveSuccess);
    				}else{
    					toastr.error(saveError);
    				}
    	         }
    	     });
			}
		else if($(this).attr('flag')=="delete"){
			var curid=$(this).closest(".rules-group-container").attr("valueId");
			$.ajax({
				type: "POST",
				async:false,
			    url: "../messageFlow/deleteRouteCondition.shtml",
			    dataType:'json',
			    data:{routeCondId:curid},
				success:function(value){ 
					if('success'==value.result){
						setRoute.init();//路由数据重刷
						toastr.success(deleteOK);
					}else{
						if('0001'== value.result){
							toastr.error(cannotDel);
						}else if('0002'== value.result){
							toastr.error(haveSub);
						}else{
							toastr.error(deleteFail);
						}
					}
		          }
		     });
			//deleteGroup(curid);
			}
		else if($(this).attr('flag')=="set"){
			var curid=$(this).closest(".rules-group-container").attr("valueId");
			var realate=$(this).closest(".rules-group-container").attr("realate");
			//alert('set father '+curid);
			//alert('realate '+realate);
			showRouteCondition(curid);
			changeRouteCondition(curid);
		}
	   });
	$(".rule-actions .selectlist ul li").live("click",function(){
		if($(this).attr('flag')=="delete"){
			//var curid=$(this).closest(".rules-list").attr("objid");
			var curid=$(this).closest(".rule-container").attr("objid");
			$.ajax({
				type: "POST",
				async:false,
			    url: "../messageFlow/deleteRouteCondition.shtml",
			    dataType:'json',
			    data:{routeCondId:curid},
				success:function(value){ 
					if('success'==value.result){
						//$(this).closest(".rule-container").remove();
						setRoute.init();//路由数据重刷
						toastr.success(deleteOK);
					}else{
						if('0001'== value.result){
							toastr.error(cannotDel);
						}else if('0002'== value.result){
							toastr.error(haveSub);
						}else{
							toastr.error(deleteFail);
						}
					}
		          }
		     });
			//deleteRule(curid);
			}
		else if($(this).attr('flag')=="modify"){
			var curid=$(this).closest(".rule-container").attr("objid");
			var objValue = $(this).closest(".rule-container").attr("objValue");
			var exprId = $(this).closest(".rule-container").attr("exprId");
			var objName = $(this).closest(".rule-container").attr("objName");
			var operId = $(this).closest(".rule-container").attr("operId");
			$('#pageHidExprId').val(exprId);
			$('#pageExpr').val(objName);
			$('#pageOperator').val(operId);
			$('#pageMatchValue').val(objValue);
			$('#pageRouteCondtionId').val(curid);
			$("#messageRouteModal").modal("show");
			}
		else if($(this).attr('flag')=="set"){
			var curid=$(this).closest(".rule-container").attr("objid");
			//alert("set "+curid);
			showRouteCondition(curid);
			changeRouteCondition(curid);
			}
	   });
	$("label.btn-primary").live("click",function(){
		var curid=$(this).closest("dt").siblings("dd").children("ul.rules-list").attr("objid");;
		$(this).addClass("active").siblings().removeClass("active");
		if($(this).html()=="OR"){
			setContion(curid,'or');
			}
		else if($(this).html()=="AND"){
			setContion(curid,'and');
			}
		});
	$(".selectlist span").live("click",function(){
		$(this).next().show();
		$(this).closest(".selectlist").css("z-index","11");
		});
	$(".selectlist ul li").live("click",function(){
		$(this).parent().siblings("span").html($(this).html());
		$(this).parent().hide();
		$(this).closest(".selectlist").css("z-index","10");
		});
	$(".selectlist ul").live("mouseleave",function(){
		$(this).hide();
		$(this).closest(".selectlist").css("z-index","10");
		});			
	});
/**
 * 设置显示路由策略表达式
 */
function showRouteCondition(routeCondId){
	$.ajax({
		type: "POST",
		async:false,
	    url: "../messageFlow/showRouteCondition.shtml",
	    dataType:'json',
	    data:{routeCondId:routeCondId},
		success:function(value){ 
			$('#pageRouteCondition').val(value.result);
          }
     });
}