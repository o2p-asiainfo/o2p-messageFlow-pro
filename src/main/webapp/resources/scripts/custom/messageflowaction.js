var messageFlowJson={};//存放消息流信息的Json对象
var errorMessage={};//错误信息对象
var stateType;
// JavaScript Document
(function($){
	function init(){
		stateType = $('#stateType').val();
		changeTypeValue(stateType);
		$("#canvas").html("");
		$("#canvas").append(initSvg("drawsvg",$("#canvas").width(),$("#canvas").height()));
		var hidMessageFlowId = $('#hidMessageFlowId').val();
		if('' != hidMessageFlowId){//初使化
			getOpenMessage(hidMessageFlowId);
			changeTypeValue($('#stateType').val());
		}
		};
	$.fn.messagesport=function(option){
		var defaulto={
			startposition:[],
			fromid:"",//起点id
			toid:"",//终点id
			istemp:0,//是否是临时线
			idindex:0,//节点数索引
			isdrag:0,
			mouseX:0,
			mouseY:0,
			currid:"",
			lineself:0,
			optype:"move",
			targetId:this.attr("id"),//操作区id
			arear:this.find("#canvas"),//操作区
			};
		var opt=$.extend({},defaulto, option);
		init();
		this.curid="";
		this.delegate("#messageEndpointList li","click",function(e){
			if('new' == stateType){
				toastr.error(addOrOpenFirst);
			}else{
				var endpointSpecName = $(this).attr('endpointSpecName');
				var endpointSpecCode = $(this).attr('endpointSpecCode');
				var specDesc = $(this).attr('specDesc');
				var endpointSpecId = $(this).attr('endpointSpecId');
				var endpointId=getEndpointSeq();
				//添加消息流JSON对象
				var node = {
						    'endpointSpecId':endpointSpecId,
						    'endpointName':endpointSpecName,
						    'specificationName':endpointSpecName,
						    'specificationCode':endpointSpecCode,
						    'inputDataType':'1',
						    'outputDataType':'1',
						    'tracingFlag':'Y',
						    'dataLogFlag':'Y',
						    'endpointDesc':'',
						    'specDesc':specDesc,
						    'left':0,
						    'top':0
						    };
				messageFlowJson.messageflow.nodes[endpointId] = node;//添加一个端点操作
				messageFlowJson.messageflow.nodes[endpointId]['property'] = getAttrSpecList(endpointSpecId).property;//添加动态属性
				//面板画图操作
				var opj='<div class="messagenode" id="'+endpointId+'" endpointSpecId="'+endpointSpecId+'" style="background:url('+$(this).find("img").attr("src")+')  no-repeat center 0; padding-top:50px" ><div>'+$(this).find("span").html()+'</div></div>';
				opt.arear.append(opj);
			}
			});
		this.delegate("#operateList button","click",function(e){
			opt.optype=$(this).attr("id");
			//保存工作流
			if(opt.optype=="save"){
				saveAs();
				}
			});
		this.delegate("#canvas","mouseup",function(e){
			opt.lineself=0;opt.fromid="";
			});
		$(document).keydown({inthis:this},function(e){//键盘操作
				var This=e.data.inthis;
				if (window.ActiveXObject) {kcode=e.keyCode; }
				else{kcode=e.which;}
				switch (kcode) {
				case 46:
					$("#"+This.curid).remove();
					//删除节点操作
					delete messageFlowJson.messageflow.nodes[This.curid];
					delete errorMessage[This.curid];//删除错误信息记录
					delete messageFlowJson.messageflow.lines[This.curid];//删除线操作
					$(".line").each(function(){
						var lineId = $(this).attr('id');//当前线ID
    					if($(this).attr("from")==This.curid||$(this).attr("to")==This.curid){
    						delete messageFlowJson.messageflow.lines[lineId];//删除线操作
    						$(this).remove();
    					}
 					});
					break;
				default:
				}
				});
		this.delegate("div.messagenode","mousedown",{inthis:this},function(e){
			$(this).addClass("checked").siblings().removeClass("checked");
			var This=e.data.inthis;
			getPosition(opt.currid);	
			if(opt.optype=="line"){
				opt.istemp=1;
				opt.fromid=$(this).attr("id");
				var point1=[];
				opt.startposition[0]=parseInt($(this).css("left"))+$(".messagenode").width()*0.5;
				opt.startposition[1]=parseInt($(this).css("top"))+$(".messagenode").height()*0.5;
				var line=$(drawSvgline("templine",null,null));
				if($("#templine").length<=0){
					$("#drawsvg").append(line);
					$("#templine path").attr("d","M "+opt.startposition[0]+" "+opt.startposition[1]+" L "+opt.startposition[0]+" "+opt.startposition[1]);}
			}
			else{
				if(opt.optype=="delete"){
					opt.currid="";
					$(this).remove();
					var thisid=$(this).attr("id");
					//删除节点操作
					delete messageFlowJson.messageflow.nodes[thisid];
					delete errorMessage[thisid];//删除错误信息
					$(".line").each(function(){
						var lineId = $(this).attr('id');//当前线ID
    					if($(this).attr("from")==thisid||$(this).attr("to")==thisid){
    						delete messageFlowJson.messageflow.lines[lineId];//删除线操作
    						$(this).remove();
    					}
 					});
					
					e.stopPropagation();
				}
				if(opt.optype!="select"){
					$("#operateList a:first").addClass("checked").siblings().removeClass("checked");
					opt.optype="select";
				}
				opt.isdrag=1;
				opt.currid=$(this).attr("id");
				This.curid=$(this).attr("id");
				opt.mouseX=e.pageX-$("div[id='"+opt.currid+"'").offset().left;
				opt.mouseY=e.pageY-$("div[id='"+opt.currid+"'").offset().top;
				}
			});
		this.delegate("div.messagenode","mouseup",function(e){
			opt.isdrag=0;
			if(opt.optype=="line"){
			  if(opt.fromid!=""){
				opt.toid=$(this).attr("id");
				var num=0;
				if(opt.toid==opt.fromid&&$(this).attr("type")!="1"){
					if(opt.lineself==1){
					 opt.lineself=0;
					 $("#operateList a:first").addClass("checked").siblings().removeClass("checked");
					 opt.optype="move";
					 if($(".line[from="+opt.fromid+"][to="+opt.fromid+"]").length>0){
						 toastr.error(lineExists);
						 return;
						 }
						 var isround=0;
					 	 drawSvgline(null,opt.fromid,opt.toid,isround);
					 }
					}
				else if(opt.toid!=opt.fromid){
					var lineId = getServiceRouteConfigSeq();
					var routePolicyId  = getRulePolicySeq();
					if($(".line[from="+opt.fromid+"][to="+opt.toid+"]").length>0){toastr.error(lineExists);return;}
					var isround=0;
					if($(".line[from="+opt.toid+"][to="+opt.fromid+"]").length>0){isround=1;}
					drawSvgline(lineId,opt.fromid,opt.toid,isround);
					var line={
							'from':opt.fromid,
							'to':opt.toid,
							'synAsyn':'SYN',//ASYN
							'policy':{
								'routePolicyId':routePolicyId,
								'strategy':'1',
								'condId':''
							}
					};
					messageFlowJson.messageflow.lines[lineId]=line;//添加操作
				}
				opt.fromid="";
				opt.toid="";
				opt.istemp=0;
				$("#templine").remove();
			}}
		});
		this.delegate("#canvas","mousemove",function(e){
			
			if(opt.isdrag){
				getPosition(opt.currid);
				$("div[id='"+opt.currid+"'").css("left",e.pageX-$(this).offset().left-opt.mouseX+"px");
				$("div[id='"+opt.currid+"'").css("top",e.pageY-$(this).offset().top-opt.mouseY+"px");
				if(parseInt($("div[id='"+opt.currid+"'").css("left"))<0){
					$("div[id='"+opt.currid+"'").css("left","0");
					}
				if(parseInt($("div[id='"+opt.currid+"'").css("top"))<0){
					$("div[id='"+opt.currid+"'").css("top","2px");
					}
				if(parseInt($("div[id='"+opt.currid+"'").css("left"))>$(this).width()-$("div[id='"+opt.currid+"'").width()){
					$("div[id='"+opt.currid+"'").css("left",$(this).width()-$("div[id='"+opt.currid+"'").width()+"px");
					}
				if(parseInt($("div[id='"+opt.currid+"'").css("top"))>$(this).height()-$("div[id='"+opt.currid+"'").height()){
					$("div[id='"+opt.currid+"'").css("top",$(this).height()-$("div[id='"+opt.currid+"'").height()+"px");
					}
				$(".line").each(function(){
					opt.fromid=$(this).attr("from");
					opt.toid=$(this).attr("to");
					var fromid=opt.fromid;
					var toid=opt.toid;
					if($(this).attr("from")==$("div[id='"+opt.currid+"'").attr("id")||$(this).attr("to")==$("div[id='"+opt.currid+"'").attr("id")){
					var points=[[0,0],[0,0]];	
					if(opt.fromid==opt.toid){
						var fl=parseInt($("div[id='"+fromid+"'").css("left"));
						var ft=parseInt($("div[id='"+fromid+"'").css("top"));
						$(this).find("path").attr("d","M "+(fl+31)+" "+(ft)+" C "+(fl+31)+" "+(ft-40)+" "+(fl-60)+" "+(ft-20)+" "+(fl)+" "+(ft+22));
						}	
				    else{
						 points=getPosition(opt.fromid,opt.toid);
						 if($(".line[from="+opt.toid+"][to="+opt.fromid+"]").length<=0){ $(this).attr("isround",0);}
						 if($(this).attr("isround")==1){$(this).find("path").attr("d","M "+points[0][0]+" "+points[0][1]+" C "+(points[0][0]+points[1][0])/2+" "+points[0][1]+" "+(points[0][0]+points[1][0])/2+" "+points[0][1]+" "+points[1][0]+" "+points[1][1]+"");}
						 else{$(this).find("path").attr("d","M "+points[0][0]+" "+points[0][1]+" L "+points[1][0]+" "+points[1][1]+"");}
					}
					if($(this).find("path:eq(1)").attr("marker-end")=="url(\"#arrow2\")"){$(this).find("path:eq(1)").attr("marker-end","url(#arrow3)");}
					else{$(this).find("path:eq(1)").attr("marker-end","url(#arrow2)");}
					}
 				});
				//消息流节点移动修改
				messageFlowJson.messageflow.nodes[opt.currid].left=$("div[id='"+opt.currid+"'").css("left");//添加操作
				messageFlowJson.messageflow.nodes[opt.currid].top=$("div[id='"+opt.currid+"'").css("top");//添加操作
				}
			if(opt.istemp){
				X=e.pageX-opt.arear.offset().left;
				Y=e.pageY-opt.arear.offset().top;
				$("#templine path").attr("d","M "+opt.startposition[0]+" "+opt.startposition[1]+" L "+X+" "+Y);
				if($("#templine path:eq(1)").attr("marker-end")=="url(\"#arrow2\")"){$("#templine path:eq(1)").attr("marker-end","url(#arrow3)");}
				else{$("#templine path:eq(1)").attr("marker-end","url(#arrow2)");}
			}	
			});
		//点击节点，当前状态改为节点操作
		this.delegate("div.messagenode","click",function(e){
			$(this).addClass("active").siblings().removeClass("active");
			opt.optype="move";
			});
		//双击节点，初拟弹属性框
		this.delegate("div.messagenode","dblclick",function(e){
			var endpointId = $(this).attr('id');
			$('#pageEndpointId').val($(this).attr('id'));
			$('#pageEndpointName').val(messageFlowJson.messageflow.nodes[endpointId].endpointName);
			$('#pageSpecificationName').val(messageFlowJson.messageflow.nodes[endpointId].specificationName);
			$('#pageSpecificationCode').val(messageFlowJson.messageflow.nodes[endpointId].specificationCode);
			$('#pageInputDataType').val(messageFlowJson.messageflow.nodes[endpointId].inputDataType);
			$('#pageOutDataType').val(messageFlowJson.messageflow.nodes[endpointId].outputDataType);
			var showLogValue = getIsShowLog($(this).attr('endpointSpecId'));
		    if('Y' == showLogValue){
		    	$('#showLog').show();
		    	$('#pageTracingFlag').val(messageFlowJson.messageflow.nodes[endpointId].tracingFlag);
				$('#pageDataLogFlag').val(messageFlowJson.messageflow.nodes[endpointId].dataLogFlag);
		    }else{
		    	$('#showLog').hide();
		    }
		    $('#dyPropery').empty();//先清空
		    if(typeof(messageFlowJson.messageflow.nodes[endpointId].property) != "undefined"){
		    	//加载动态属性
			    $.each(messageFlowJson.messageflow.nodes[endpointId].property,function(key,val){
			    	var endpointSpecAttrId = key;
			    	var page_In_Type = val.pageInType;//类型
			    	var attrSpecId  = val.attrSpecId;
			    	var attrSpecCode = val.attrSpecCode;
			    	var objectId = attrSpecCode+'_'+endpointId;
			    	var initValue = messageFlowJson.messageflow.nodes[endpointId]['property'][endpointSpecAttrId][attrSpecCode];
			    	if(page_In_Type==null||page_In_Type==''||page_In_Type=='1'){////默认文本框类型
			    		var html = '<div class="row">'
						     + '<div class="col-md-10">'
						     + '<div class="form-group">'
						     + '<label class="control-label">'+val.attrSpecName+'</label>'
						     + "<input type='text' placeholder='' class='form-control' value='"+initValue+"' onchange=\"javascript:changePropertyValue('"+endpointSpecAttrId+"','"+attrSpecCode+"',this.value);\">"
						     + '</div></div></div>';
					   $('#dyPropery').append(html);
			    	}else if(page_In_Type=='2'){////有选择操作的类型
			    		var chooseUrl = val.chooseUrl;
			    		var portalUrl = val.portalUrl;
			    		var html = '<div class="row">'
						     + '<div class="col-md-10">'
						     + '<div class="form-group">'
						     + '<label class="control-label">'+val.attrSpecName+'</label>'
						     + "<input type='text' readonly placeholder='' name='"+objectId+"' class='form-control' id='"+objectId+"' value='"+initValue+"' onchange=\"javascript:changePropertyValue('"+endpointSpecAttrId+"','"+attrSpecCode+"',this.value);\">"
						     + '</div></div>'
						     + '<div class="col-md-2">'
						     + '<div class="form-group">'
						     + '<label class="control-label">&nbsp;</label>'
						     + "<input type='button' class='form-control' value='"+showChoose+"'  onclick=\"javascript:selectValueFromSpecPage('"+endpointSpecAttrId+"','"+attrSpecCode+"','"+chooseUrl+"','"+portalUrl+"');\" data-toggle='modal' data-target='#addModalAddap'>"
						     + '</div></div></div>';
					   $('#dyPropery').append(html);
			    	}else if(page_In_Type=='4'){//有查看详情类型
			    		var chooseUrl = val.chooseUrl;
			    		var detailUrl = val.detailUrl;
			    		var portalUrl = val.portalUrl;
			    		var detailPortalUrl = val.detailPortalUrl;
			    		var html = '<div class="row">'
						     + '<div class="col-md-6">'
						     + '<div class="form-group">'
						     + '<label class="control-label">'+val.attrSpecName+'</label>'
						     + "<input type='text' readonly placeholder='' class='form-control' id='"+objectId+"' value='"+initValue+"' onchange=\"javascript:changePropertyValue('"+endpointSpecAttrId+"','"+attrSpecCode+"',this.value);\">"
						     + '</div></div>'
						     + '<div class="col-md-4">'
						     + '<div class="form-group">'
						     + '<label class="control-label" style="display:block;">&nbsp;</label>'
						     + "<input type='button' class='form-control' id='firstName' value='"+showDetail+"' style='width:45%;display:inline-block;' data-toggle='modal' onclick=\"javascript:selectTechSpecPage('"+objectId+"','"+detailUrl+"','"+detailPortalUrl+"');\">&nbsp;" 
						     + "<input type='button' class='form-control'  value='"+showChoose+"' style='width:45%;display:inline-block;' data-toggle='modal' onclick=\"javascript:selectValueFromSpecPage('"+endpointSpecAttrId+"','"+attrSpecCode+"','"+chooseUrl+"','"+portalUrl+"');\" data-target='#addModalAddap'>"
						     + '</div></div></div>';
					   $('#dyPropery').append(html);
			    	}
//			    	else if(page_In_Type=='5'){//认证表达式类型
//			    		var html = '<div class="row">'
//						     + '<div class="col-md-10">'
//						     + '<div class="form-group">'
//						     + '<label class="control-label">'+val.attrSpecName+'</label>'
//						     + "<input type='text' placeholder='Chee Kin' class='form-control'  value='"+initValue+"' onchange=\"javascript:changePropertyValue('"+endpointSpecAttrId+"','"+attrSpecCode+"',this.value);\">"
//						     + '</div></div>'
//						     + '<div class="col-md-2">'
//						     + '<div class="form-group">'
//						     + '<label class="control-label">&nbsp;</label>'
//						     + '<input type="button" class="form-control" id="firstName" value="Configuration"  data-toggle="modal" data-target="#addModalAddap">'
//						     + '</div></div></div>';
//					   $('#dyPropery').append(html);
//			    	}
			    	else if(page_In_Type=='6'){//下拉列表类型
			    		var html = '<div class="row">'
						     + '<div class="col-md-10">'
						     + '<div class="form-group">'
						     + '<label class="control-label">'+val.attrSpecName+'</label>'
						     + "<select class='form-control' id='"+attrSpecId+"' onchange=\"javascript:changePropertyValue('"+endpointSpecAttrId+"','"+attrSpecCode+"',this.value);\">"
						     + '</select>'
						     + '</div></div></div>';
					   $('#dyPropery').append(html);
					   appendValue(attrSpecId);//初使化下拉框值
					   if('' !=  initValue){
						   $('#'+attrSpecId).val(initValue);
					   }else{//下拉框需要给一个默认值
						   messageFlowJson.messageflow.nodes[endpointId]['property'][endpointSpecAttrId][attrSpecCode] = $('#'+attrSpecId).val();
					   }
			    	}else{//其余默认用用文本接收
			    		var html = '<div class="row">'
						     + '<div class="col-md-10">'
						     + '<div class="form-group">'
						     + '<label class="control-label">'+val.attrSpecName+'</label>'
						     + "<input type='text' placeholder='' class='form-control' value='"+initValue+"' onchange=\"javascript:changePropertyValue('"+endpointSpecAttrId+"','"+attrSpecCode+"',this.value);\">"
						     + '</div></div></div>';
					   $('#dyPropery').append(html);
			    	}
					
				});
		    }
		    $('#pageEndpointDesc').val(messageFlowJson.messageflow.nodes[endpointId].endpointDesc);
		    $('#pageSepcDesc').val(messageFlowJson.messageflow.nodes[endpointId].specDesc);
			$("#detailModal").modal("show");
			});
		this.delegate("div.messagenode","mouseout",function(e){
			if(opt.optype=="path"&&opt.fromid!=""){opt.lineself=1;}
			});
		//选中线
		this.delegate("g","click",{inthis:this},function(e){
			var This=e.data.inthis;
			if(opt.optype=="delete"){
				var lineId = $(this).attr('id');
				delete messageFlowJson.messageflow.lines[lineId];//删除线操作
				$(this).remove();
				$("#operateList a:first").addClass("checked").siblings().removeClass("checked");
				opt.optype="move";
				}
			else{
				var lineId = $(this).attr("id");
				$('#pageLineId').val(lineId);
				$('#pageSynAsyn').val(messageFlowJson.messageflow.lines[lineId].synAsyn);//设置第一个下拉框选中值
				$('#pageRoutePolicy').val(messageFlowJson.messageflow.lines[lineId].policy.strategy);//设置第二个下拉框选中值
				var value = $('#pageRoutePolicy').val();
				   if('1' == value){
						$('#pageRouteTable').hide();
						$('#pageRouteCondition').val('');
					}else{
						$('#pageRouteTable').show();
						showRouteCondition(messageFlowJson.messageflow.lines[lineId].policy.condId);//设置路由策略显示条件
				}
				$("#lineDetailModal").modal("show");//显示路由配置界面
				setRoute.init();//路由数据重刷
				}
			This.curid=$(this).attr("id");
			});
		this.mouseup(function(e){
			$("#templine").remove();
			opt.istemp=0;
			opt.isdrag=0;
			});
		this.load(function(e){
			//alert("ccc");
			});
		//添加消息流
		$('#pageMessageFlowOK').click(function(){
			   var messageFlowName = $('#pageNewMessageFlow').val();
		       if('' == messageFlowName){
		    	   toastr.error(messageNameNotNull);
		    	   return false;
		       }else{
		    	   var messageFlowId = getMessageFlowSeq();
		    	   messageFlowJson['messageflow'] = {'id':messageFlowId,'name':messageFlowName,'nodes':{},'lines':{}};//初使化一个消息流
		    	   stateType = 'Add';
		    	   changeTypeValue(stateType);
		    	   $("#addModal").modal("hide");
		    	   $("#canvas").html(""); //清空画布
		   		   $("#canvas").append(initSvg("drawsvg",$("#canvas").width(),$("#canvas").height()));
		       }
		   });
		$('#pageCleanRouteCond').live('click',function(){
			var lineId = $('#pageLineId').val();
			messageFlowJson.messageflow.lines[lineId].policy.condId = '';
			$('#pageRouteCondition').val('');
		});
		//初始化消息流表格数据
        var oTable = $('#dt').dataTable({
            "processing": true,
            "serverSide": true,
            "ajax": "../messageFlow/getCreatedMessageFlowList.shtml",
            "autoWidth": false,
            "ordering": false,
            "searching": false,
            "columns": [{
                "orderable": false,
                "data": null,
                "defaultContent": '<span class="row-details-btn row-details-close"></span>'
            }, {
                "data": "message_flow_id"
            }, {
                "data": "message_flow_name"
            }],
            "columnDefs": [{
                "width": "36px",
                "targets": 0,
                "render": function(data, type, full, meta) {
                    var html = "<input type='radio' style='margin-left:0px;' value='"+full.message_flow_id+"' class='radio-fix' name='radiobox'/>";
                	return html;
                	
                }
            }],
            "initComplete": function() {
                App.initUniform();
            }
        });
		$('#dt_length').remove();
        jQuery('#dt_wrapper .dataTables_filter input').addClass("form-control input-small input-inline"); // modify table search input
        jQuery('#dt_wrapper .dataTables_length select').addClass("form-control input-small"); // modify table per page dropdown
        
        //查询操作
        $('#pageSeach').live('click',function(){
        	var pageMessageFlowId = $('#pageMessageFlowId').val();
        	var pageMessageFlowName = $('#pageMessageFlowName').val();
        	var url = '../messageFlow/getCreatedMessageFlowList.shtml?pageMessageFlowId='+pageMessageFlowId
        	        + '&pageMessageFlowName='+pageMessageFlowName;
        	oTable.api().ajax.url(url).load();
        });
        //弹出窗口提交按钮
        $('#messageFlowOpen').live('click',function(){
        	//判断是否有选择
            var oSelected = $("#dt>tbody input[type='radio']:checked");
            if (oSelected.size() == 1) {
            	var trselected = $("#dt>tbody input[type='radio']:checked").closest('tr');
            	var selected = oTable.api().row(trselected).data();
            	getOpenMessage(selected.message_flow_id);
            	$('#messageListModal').modal('hide');
            } else {
            	toastr.error(oneRecords);
            }
        });
        $('#open').live('click',function(){
        	oTable.api().ajax.reload();
        });
		};
	function drawLine(id,fromid,toid){
		};
	function drawSvgline(id,fromid,toid,isround){
		var thisId;
		if(id==null){thisId=fromid+"-"+toid;}
		else thisId=id;
		var line;
		var points=[[0,0],[0,0]];
		if(id!="templine"){points=getPosition(fromid,toid);}
		line=document.createElementNS("http://www.w3.org/2000/svg","g");
		var hi=document.createElementNS("http://www.w3.org/2000/svg","path");
		var path=document.createElementNS("http://www.w3.org/2000/svg","path");
		line.setAttribute("id",thisId);
		hi.setAttribute("stroke","#fff");
		hi.setAttribute("stroke-width",7);
		hi.setAttribute("fill","none");
		hi.style.opacity=0.1;
		path.setAttribute("stroke","#777");
		path.setAttribute("marker-end","url(#arrow3)");
		path.style.fill="none";
		if(id=="templine"){
			path.setAttribute("marker-end","url(#arrow1)");
			path.setAttribute("stroke","#777");
			}
		if(fromid==toid&&fromid!=null&&document.getElementById(fromid).getAttribute("type")!="oval"){
		var fl=parseInt($("div[id='"+fromid+"'").css("left"));
		var ft=parseInt($("div[id='"+fromid+"'").css("top"));
		path.setAttribute("d","M "+(fl+31)+" "+(ft)+" C "+(fl+31)+" "+(ft-40)+" "+(fl-60)+" "+(ft-20)+" "+(fl)+" "+(ft+22));
		hi.setAttribute("d","M "+(fl+31)+" "+(ft)+" C "+(fl+31)+" "+(ft-40)+" "+(fl-60)+" "+(ft-20)+" "+(fl)+" "+(ft+22));
			}
		else if(fromid!=toid){
			if(isround==1){
				path.setAttribute("d","M "+points[0][0]+" "+points[0][1]+" C "+(points[0][0]+points[1][0])/2+" "+(points[0][1]-10)+" "+(points[0][0]+points[1][0])/2+" "+(points[1][1]+20)+" "+points[1][0]+" "+points[1][1]+"");
				hi.setAttribute("d","M "+points[0][0]+" "+points[0][1]+" C "+(points[0][0]+points[1][0])/2+" "+(points[0][1]-10)+" "+(points[0][0]+points[1][0])/2+" "+(points[1][1]+20)+" "+points[1][0]+" "+points[1][1]+"");
				}
			if(isround==0){
				path.setAttribute("d","M "+points[0][0]+" "+points[0][1]+" L "+points[1][0]+" "+points[1][1]+"");
				hi.setAttribute("d","M "+points[0][0]+" "+points[0][1]+" L "+points[1][0]+" "+points[1][1]+"");
				}
			}
		if(id=="templine"){path.setAttribute("stroke-dasharray","6,5");}
		else{line.setAttribute("class","line");}
		line.appendChild(hi);
		line.appendChild(path);
		line.style.zIndex="777";
		line.setAttribute("id",thisId);
		line.setAttribute("isround",isround);
		line.setAttribute("from",fromid);
		line.setAttribute("to",toid);
		$("#drawsvg").append(line);
	}
	function getSvgMarker(id,color){
		var m=document.createElementNS("http://www.w3.org/2000/svg","marker");
		m.setAttribute("id",id);
		m.setAttribute("viewBox","0 0 8 8");
		m.setAttribute("refX",6);
		m.setAttribute("refY",4);
		m.setAttribute("markerUnits","strokeWidth");
		m.setAttribute("markerWidth",8);
		m.setAttribute("markerHeight",8);
		m.setAttribute("orient","auto");
		var path=document.createElementNS("http://www.w3.org/2000/svg","path");
		path.setAttribute("d","M 0 0 L 8 4 L 0 8 z");
		path.setAttribute("fill",color);
		path.setAttribute("stroke-width",0);
		m.appendChild(path);
		return m;
	}
	function initSvg(id,width,height){
		var elem;
		var svg=document.createElementNS("http://www.w3.org/2000/svg","svg");//可创建带有指定命名空间的元素节点
		var defs=document.createElementNS("http://www.w3.org/2000/svg","defs");
		defs.appendChild(getSvgMarker("arrow3","#777"));
		defs.appendChild(getSvgMarker("arrow2","#777"));
		defs.appendChild(getSvgMarker("arrow1","#777"));
		svg.appendChild(defs);
		svg.setAttribute("id",id);
		svg.style.width=width+"px";
		svg.style.height=height+"px";
		return svg;
	}
	function getPosition(fromid,toid){
		var Ch=50;
		var Cw=$(".messagenode").width();
		var FromLeft=parseInt($("div[id='"+fromid+"'").css("left"))+$("div[id='"+fromid+"'").width()/2-25;
		var FromTop=parseInt($("div[id='"+fromid+"'").css("top"));
		var ToLeft=parseInt( $("div[id='"+toid+"'").css("left"))+ $("div[id='"+toid+"'").width()/2-25;
		var ToTop=parseInt( $("div[id='"+toid+"'").css("top"));
		var pForm=new Array(4);
		var pTo=new Array(4);
		for (var i=0;i<4;i++) {
			pForm[i]=new Array();
			pTo[i]=new Array();
			if (i==0) {
				pForm[i][0]=FromLeft;pForm[i][1]=FromTop+Ch/2;
				pTo[i][0]=ToLeft;pTo[i][1]=ToTop+Ch/2;
			}
			if (i==1) {
				pForm[i][0]=FromLeft+25;pForm[i][1]=FromTop;
				pTo[i][0]=ToLeft+25;pTo[i][1]=ToTop;
			}
			if (i==2) {

				pForm[i][0]=FromLeft+50;pForm[i][1]=FromTop+Ch/2;
				pTo[i][0]=ToLeft+50;pTo[i][1]=ToTop+Ch/2;
			}
			if (i==3) {
				pForm[i][0]=FromLeft+25;pForm[i][1]=FromTop+Ch;
				pTo[i][0]=ToLeft+25;pTo[i][1]=ToTop+Ch;
			} 
		}
		var MinLen=999999999;
		var i_Index=0;j_Index=0;
		for (var i=0;i<4;i++)
		for (var j=0;j<4;j++) {
			var dist=Math.pow(pForm[i][0]-pTo[j][0],2)+Math.pow(pForm[i][1]-pTo[j][1],2);
			if (dist<MinLen) {
				MinLen=dist;
				i_Index=i;j_Index=j;
			}
		}
		var points=[[0,0],[0,0]];
		points[0][0]=pForm[i_Index][0];
		points[0][1]=pForm[i_Index][1];
		points[1][0]=pTo[j_Index][0];
		points[1][1]=pTo[j_Index][1];
		return points;      
		}
	function saveAs(){
		//alert(JSON.stringify(messageFlowJson));
		var flag = false;
		var nodeNum = 0;
		$.each(messageFlowJson.messageflow.nodes,function(keyNode,valNode){//端点遍历
			nodeNum ++;
			var endpointId = keyNode;
			if(!isExitNode(endpointId)){
				flag = true;
			}
		});
		if(0 != nodeNum){//说明有选择端点
			if(!flag){//说明所有端点都有线连接
				if(!isFillIn()){//说明必填验证通过
					if(checkBeginEnd()){//验证开始跟结束端点
						//alert(JSON.stringify(messageFlowJson));
						saveJson();
					}
				}else{//输出必填验证错误信息
					alertErrorMessage();
				}
			}else{
				toastr.error(dataNotComplete);
			}
		}else{
			toastr.error(noData);
		}
		
	}
	/**
	 * 得到端点的ID
	 */
	function getEndpointSeq(){
		var endpointId = '';
		$.ajax({
			type: "POST",
			async:false,
		    url: "../messageFlow/getEndpointSeq.shtml",
		    dataType:'json',
		    data:null,
			success:function(msg){
				endpointId = msg.endpointId;
           }
        });
		return endpointId;
	}
	/**
	 * 得到路由的ID
	 */
	function getServiceRouteConfigSeq(){
		var routeId = '';
		$.ajax({
			type: "POST",
			async:false,
		    url: "../messageFlow/getServiceRouteConfigSeq.shtml",
		    dataType:'json',
		    data:null,
			success:function(msg){
				routeId = msg.routeId;
           }
        });
		return routeId;
	}
	
	/**
	 * 查看是否需要显示日志配置
	 */
	function getIsShowLog(endpoint_spec_id){
		var result = '';
		$.ajax({
			type: "POST",
			async:false,
		    url: "../messageFlow/getIsShowLog.shtml",
		    dataType:'json',
		    data:{endpoint_Spec_Attr_Id:endpoint_spec_id},
			success:function(value){ 
				result = value.isLog;
              }
         });
		return result;
	}
	/**
	 * 取得端点规格的所有动态属性
	 */
	function getAttrSpecList(endpoint_spec_id){
		var result = {};
		$.ajax({
			type: "POST",
			async:false,
		    url: "../messageFlow/getAttrSpecList.shtml",
		    dataType:'json',
		    data:{endpointSpecId:endpoint_spec_id},
			success:function(value){ 
				result = value;
              }
         });
		return result;
	}
	/**
	 * 给下拉框添加值
	 */
	function appendValue(attrSpecId){
		$.ajax({
			type: "POST",
			async:false,
		    url: "../messageFlow/getOptionValues.shtml",
		    dataType:'json',
		    data:{attrSpecId:attrSpecId},
			success:function(value){ 
				option = value.optionValue; 
				if("" != option){
					$('#'+attrSpecId).append(option);
				}
              }
         });
   }
	function getOpenMessage(messageFlowId){
		   $.ajax({
				type: "POST",
				async:false,
			    url: "../messageFlow/openMessageFlow.shtml",
			    dataType:'json',
			    data:{messageFlowId:messageFlowId},
				success:function(msg){
					//alert(JSON.stringify(msg));
					$("#canvas").html(""); //清空画布
			   		$("#canvas").append(initSvg("drawsvg",$("#canvas").width(),$("#canvas").height()));
					messageFlowJson = msg;//初使化消息流Json对象
					laodMessageFlowData(msg);
					stateType = 'open';
					changeTypeValue('open');
		       }
		    });
	}
	/**
	 * 加载消息流数据
	 */
	function laodMessageFlowData(jsonObject){
		var opj;
		$.each(jsonObject.messageflow.nodes,function(key,val){
			opj='<div class="messagenode" id="'+key+'" endpointSpecId="'+val.endpointSpecId+'" style="background:url('+$('li[endpointSpecId='+val.endpointSpecId+']').find("img").attr("src")+')  no-repeat center 0; padding-top:50px;left:'+val.left+';top:'+val.top+'"><div>'+val.specificationName+'</div></div>';
			$("#canvas").append(opj);
		});
		$.each(jsonObject.messageflow.lines,function(key,val){
			drawSvgline(key,val.from,val.to,'');
		});
	}
})(jQuery);
function changeJsonValue(nodeName,value){
	var endpointId = $('#pageEndpointId').val();
	messageFlowJson.messageflow.nodes[endpointId][nodeName] = value;
}
function changeSynAsyn(value){
	var lineId = $('#pageLineId').val();
	messageFlowJson.messageflow.lines[lineId].synAsyn = value;
}
function changeRoutePolicy(value){
	var lineId = $('#pageLineId').val();
	messageFlowJson.messageflow.lines[lineId].policy.strategy = value;
	if('1' == value){
		messageFlowJson.messageflow.lines[lineId].policy.routePolicyId = '0';
		$('#pageRouteTable').hide();
	}else{
		messageFlowJson.messageflow.lines[lineId].policy.routePolicyId = getRulePolicySeq();
		$('#pageRouteTable').show();
	}
}
function changeRouteCondition(value){
	var lineId = $('#pageLineId').val();
	messageFlowJson.messageflow.lines[lineId].policy.condId = value;
}
function changeEndpointName(value){
	var endpointId = $('#pageEndpointId').val();
	$('#'+endpointId).empty();
	$('#'+endpointId).append('<div>'+value+'</div>');
}
/**
 * 修改动态属性的值
 */
function changePropertyValue(endpointSpecAttrId,attrSpecCode,value){
   var endpointId = $('#pageEndpointId').val();
   messageFlowJson.messageflow.nodes[endpointId]['property'][endpointSpecAttrId][attrSpecCode] = value;
}
/**
 * 地址回调修改动态属性的值
 */
function editorPropertyValue(objectId,endpointSpecAttrId,attrSpecCode,value){
   var endpointId = $('#pageEndpointId').val();
   $('#'+objectId).val(value);
   messageFlowJson.messageflow.nodes[endpointId]['property'][endpointSpecAttrId][attrSpecCode] = value;
}
/**
 * 查看跳转页面
 * @param attrName
 * @param objectId
 * @param endpoint_Spec_Attr_Id
 * @param specialUrl
 */
function selectTechSpecPage(objectId,specialUrl,detailPortalUrl){
	var serverName = document.getElementById("serverName").value;
	var serverPort = document.getElementById("serverPort").value;
	var serTechImplId= document.getElementById(objectId).value;
	var flag  = $('#flag').val();
	var jumpUrl = "";
	if('portal' == flag){
		if(detailPortalUrl.indexOf("http://") != -1 || detailPortalUrl.indexOf("https://") != -1){
			jumpUrl = detailPortalUrl;
		}else{
			jumpUrl = "http://" + serverName + ":" + serverPort + detailPortalUrl;
		}
	}else{
		if(specialUrl.indexOf("http://") != -1 || specialUrl.indexOf("https://") != -1){
			jumpUrl = specialUrl;
		}else{
			jumpUrl = "http://" + serverName + ":" + serverPort + specialUrl;
		}
	}
	var urlParam = serTechImplId;
	if ("" != serTechImplId)
	{
		$('#addModalAddap').modal('show');
		$('#pageValueFromSpecPage').attr('src',jumpUrl+urlParam);
	}
	
}
/**
 * 跳转配置页面
 */
function selectValueFromSpecPage(endpointSpecAttrId,attrSpecCode,chooseUrl,portalUrl){
	var endpointId = $('#pageEndpointId').val();
	var serverScheme=$('#serverScheme').val();
	var serverName = $('#serverName').val();
	var serverPort = $('#serverPort').val();
	var flag  = $('#flag').val();
	var orgId = $('#orgId').val();
	var objectId = attrSpecCode+'_'+endpointId;
	var specialUrl = "";
	if('portal' == flag){
		if(portalUrl.indexOf("http://") != -1 || portalUrl.indexOf("https://") != -1){
			specialUrl = portalUrl;
		}else{
			specialUrl = serverScheme+"://" + serverName + ":" + serverPort + portalUrl;
		}
	}else{
		if(chooseUrl.indexOf("http://") != -1 || chooseUrl.indexOf("https://") != -1){
			specialUrl = chooseUrl;
		}else{
			specialUrl = serverScheme+"://" + serverName + ":" + serverPort + chooseUrl;
		}
	}
	var urlParam = "pageState=pageState&attrName=attr_value&objectId="+objectId+"&endpoint_Spec_Attr_Id="+endpointSpecAttrId+"&attrSpecCode="+attrSpecCode+"&newState=new";
	if (typeof($('input[name^=transformer_rule_id]').val()) != "undefined")
	{
		var moreSource = '';
		$.each(messageFlowJson.messageflow.nodes,function(key,val){
			if('' == moreSource){
				moreSource = key+","+val.endpointName;
			}else{
				moreSource = moreSource + ":" + key+","+val.endpointName;
			}
		});
		urlParam += "&transformerRuleId="+$('#'+objectId).val()+"&moreSource="+moreSource;
	}
	if('portal' == flag){
		urlParam += "&orgId="+orgId;
	}
	$('#pageValueFromSpecPage').attr('src',specialUrl+urlParam);
}
/**
 * 改变状态
 * @param type
 */
function changeTypeValue(type){
	if('new' == type){
		$('#save').attr('disabled',true);
		$('#select').attr('disabled',true);
		$('#line').attr('disabled',true);
		$('#delete').attr('disabled',true);
	}else{
		$('#save').attr('disabled',false);
		$('#select').attr('disabled',false);
		$('#line').attr('disabled',false);
		$('#delete').attr('disabled',false);
	}
}
/**
 * 查看是否存在端点
 * @param enpointId
 */
function isExitNode(endpointId){
	var flag = false;
	$.each(messageFlowJson.messageflow.lines,function(keyLine,valLine){//线遍历
		var from = valLine.from;
		var to = valLine.to;
		if(from == endpointId || to == endpointId){
			flag = true;
		}
	});
	return flag;
}
/**
 * 查看必填属性值是否为空
 */
function isFillIn(){
	var flag = false;
	$.each(messageFlowJson.messageflow.nodes,function(keyNode,valNode){//端点遍历
		if(typeof(messageFlowJson.messageflow.nodes[keyNode].property) != "undefined"){//说明存在动态属性
			$.each(messageFlowJson.messageflow.nodes[keyNode].property,function(keyLine,valLine){//端点遍历
				var dyKey = valLine.attrSpecCode;//动态属性的key值
				var dyValue = valLine[dyKey];//动态属性的value值
				var isFillIn = valLine.isFillIn;//是否必填
				if('Y' == isFillIn && '' == dyValue){
					var endpointName = messageFlowJson.messageflow.nodes[keyNode].endpointName;
					var attrSpecName = messageFlowJson.messageflow.nodes[keyNode].property[keyLine].attrSpecName;
					errorMessage[keyNode] = {'endpointName':endpointName,'attrSpecName':attrSpecName};
					flag = true;
				}
			});
		}
	});
	return flag;
}
/**
 * 检查开始跟结束端点正确性
 * 要求:一个消息流必需要用一个开始端点跟一个结束端点
 */
function checkBeginEnd(){
	var flag = false;
	var begin = 0;
	var end = 0;
	$.each(messageFlowJson.messageflow.nodes,function(key,val){//端点遍历
		var specCode = val.specificationCode;
		if('BEGIN' == specCode){
			begin++;
		}else if('END' == specCode){
			end++;
		}
	});
	if(1==begin && 1==end){
		flag = true;
	}else{
		if(1 != begin){
			toastr.error(oneBeginNode);
			return flag;
		}
		if(1 != end){
			toastr.error(oneEndNode);
			return flag;
		}
	}
	return flag;
}
/**
 * 打印错误信息集
 */
function alertErrorMessage(){
	var error='';
	$.each(errorMessage,function(key,val){
		error += showEndpoint+':"'+val.endpointName+'",'+showAttrSpecName+':"'+val.attrSpecName + '" '+notEmpty +'\r\n';
	});
	toastr.error(error);
}
/**
 * 得到路由规则的ID
 */
function getRulePolicySeq(){
	var routePolicyId = '';
	$.ajax({
		type: "POST",
		async:false,
	    url: "../messageFlow/getRulePolicySeq.shtml",
	    dataType:'json',
	    data:null,
		success:function(msg){
			routePolicyId = msg.routePolicyId;
       }
    });
	return routePolicyId;
}
/**
 * 最终对象保存操作
 */
function saveJson(){
	$.ajax({
		type: "POST",
		async:true,
	    url: "../messageFlow/saveJson.shtml",
	    dataType:'json',
	    data:{jsonObject:JSON.stringify(messageFlowJson)},
		success:function(value){ 
			if('success' == value.result){
				toastr.success(saveSuccess);
			}else{
				toastr.error(saveError);
			}
          }
     });
}
