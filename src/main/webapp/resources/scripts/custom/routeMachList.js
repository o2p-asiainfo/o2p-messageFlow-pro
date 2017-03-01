var routeMachList = function() {
    //表格数据
    var handleDataTable = function() {
        //初始化表格数据
        var oTableDt2 = $('#dt2').dataTable({
            "processing": true,
            "serverSide": true,
            "ajax": "../messageFlow/getGetValueExprList.shtml",
            "autoWidth": false,
            "ordering": false,
            "searching": false,
            "columns": [{
                "orderable": false,
                "data": null,
                "defaultContent": '<span class="row-details-btn row-details-close"></span>'
            }, {
                "data": "expr_id"
            }, {
                "data": "expr"
            }, {
                "data": "cond_evaluator_code"
            }, {
                "data": "cond_evaluator_name"
            }],
            "columnDefs": [{
                "width": "36px",
                "targets": 0,
                "render": function(data, type, full, meta) {
                    var html = "<input type='radio' value='"+full.expr_id+"' class='radio-fix' name='radiobox'/>";
                	return html;
                }
            }],
            "initComplete": function() {
                App.initUniform();
            }
        });
        $('#dt2_length').remove();
        jQuery('#dt_wrapper .dataTables_filter input').addClass("form-control input-small input-inline"); // modify table search input
        jQuery('#dt_wrapper .dataTables_length select').addClass("form-control input-small"); // modify table per page dropdown
        //查询操作
        $(document).on("click","#pageDt2Seach",function(){
        	var pageSeachExpr = $('#pageSeachExpr').val();
        	var url = '../messageFlow/getGetValueExprList.shtml?expr='+pageSeachExpr;
        	oTableDt2.api().ajax.url(url).load();
        });
        //点击添加
		$("#chooseRcondition").live('click',function(){
			oTableDt2.api().ajax.reload();
			$("#routecondition").hide();
			$("#routemachlistcont").show();
			});
		$("#chooseroutecondition").live('click',function(){
			var oSelected = $("#dt2>tbody input[type='radio']:checked");
            if (oSelected.size() == 1) {
            	var trselected = $("#dt2>tbody input[type='radio']:checked").closest('tr');
            	var selected = oTableDt2.api().row(trselected).data();
            	$('#pageHidExprId').val(selected.expr_id);
            	$('#pageExpr').val(selected.expr);
            	$("#routecondition").show();
    			$("#routemachlistcont").hide();
            }else{
            	toastr.error(oneRecords);
            }
			});
		$('#pageAddRouteCondition').live('click',function(){
			var pageRouteCondtionId = $('#pageRouteCondtionId').val();
			if('' == pageRouteCondtionId){
				var pageHidExprId = $('#pageHidExprId').val();
				var pageExpr = $('#pageExpr').val();
				var pageOperator = $('#pageOperator').val();
				var pageMatchValue = $('#pageMatchValue').val();
				var pageUpExprId = $('#pageUpExprId').val();
				var obj = $('#pageOperator')[0];
				var pageOperatorValue = obj.options[obj.selectedIndex].text;//获取文本
				$.ajax({
	    			type: "POST",
	    			async:false,
	    		    url: "../messageFlow/addRounteCond.shtml",
	    		    dataType:'json',
	    		    data:{
	    		    	pageHidExprId:pageHidExprId,
	    		    	pageExpr:pageExpr,
	    		    	pageOperator:pageOperator,
	    		    	pageMatchValue:pageMatchValue,
	    		    	pageOperatorValue:pageOperatorValue,
	    		    	pageUpExprId:pageUpExprId
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
			}else{
				var pageHidExprId = $('#pageHidExprId').val();
				var pageOperator = $('#pageOperator').val();
				var pageMatchValue = $('#pageMatchValue').val();
				$.ajax({
	    			type: "POST",
	    			async:false,
	    		    url: "../messageFlow/updateRounteCond.shtml",
	    		    dataType:'json',
	    		    data:{
	    		    	pageHidExprId:pageHidExprId,
	    		    	pageOperator:pageOperator,
	    		    	pageMatchValue:pageMatchValue,
	    		    	pageRouteCondtionId:pageRouteCondtionId
	    		    	},
	    			success:function(msg){
	    				if('success' == msg.result){
	    					$('#pageRouteCondtionId').val('');
	    					setRoute.init();//路由数据重刷
	    					$("#messageRouteModal").modal("hide");
	    					toastr.success(saveSuccess);
	    				}else{
	    					toastr.error(saveError);
	    				}
	    	         }
	    	     });
			}
		});
        $("#pageCondEvalOK").live('click',function() {
            var pageCondEval = $('#pageCondEval').val();
            var pageCondEvalExpr = $('#pageCondEvalExpr').val();
            var pageExprType = $('#pageExprType').val();
            $.ajax({
    			type: "POST",
    			async:false,
    		    url: "../messageFlow/addCondEvalExpr.shtml",
    		    dataType:'json',
    		    data:{
    		    	pageCondEval:pageCondEval,
    		    	pageCondEvalExpr:pageCondEvalExpr,
    		    	pageExprType:pageExprType
    		    	},
    			success:function(msg){
    				if('success' == msg.result){
    					$("#getvalueexprwin").modal("hide");
    					oTableDt2.api().ajax.reload();
    					toastr.success(saveSuccess);
    				}else{
    					toastr.error(saveError);
    				}
    	         }
    	     });
        });
        $('#btn-del').live('click',function(){
        	//判断是否有选择
            var oSelected = $("#dt2>tbody input[type='radio']:checked");
            if (oSelected.size() == 1) {
            	var trselected = $("#dt2>tbody input[type='radio']:checked").closest('tr');
            	var selected = oTableDt2.api().row(trselected).data();
            	$.ajax({
        			type: "POST",
        			async:false,
        		    url: "../messageFlow/deleteGetValueExpr.shtml",
        		    dataType:'json',
        		    data:{pageExprId:selected.expr_id},
        			success:function(msg){
        				if(msg.result == 'success'){
        					oTableDt2.api().ajax.reload();
        					toastr.success(deleteOK);
        				}else if(msg.code == '0001'){
        					toastr.error(cannotDel);
        				}else{
        					toastr.error(deleteFail);
        				}
        	         }
        	     });
            }else{
            	toastr.error(oneRecords);
            }
        });

        /**
         * 重置表单
         */
        function resetFrom() {
            $('form').each(function(index) {
                $('form')[index].reset();
                $('form').find('.help-block').remove();
                $('form').find('.form-group').removeClass('has-error');
            });
        }

    };

    return {
        init: function() {
			 handleDataTable();
        },
    };

} ();



