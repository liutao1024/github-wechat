var User = function () {
	var handleTable = function () {
		var sysUserTable = new Datatable();
		var url = Sunline.ajaxPath("auth/showAllSysUser"); 
		var editUrl;
		var userTable=$("#person_ajax");
	    var editform = $("#edit_form");
	    var sysUserRoleRUrl =  Sunline.ajaxPath("auth/showAllSysUserRole");
 		var editSysRoleUrl;
 		var userRoleTable = $("#role_auth_ajax");
 		var sysUserRoleTable = new Datatable();
 		var setRoleform = $("#setRoleModal");
 		var sendRoleData = ["regist_cd", "auth_type", "role_cd", "user_cd"];
	    var userdata;

 	 	//select字典获取	
 		var userstDict=Sunline.getDict("D_USERST");
 		var userlvDict=Sunline.getDict("D_USERLV");
 		var brchnoDict=Sunline.getDict("","/D_BRCHNO","dictId","dictName");//sys_dict
 		$("#userst").select2({
 			data:userstDict,
 			allowClear:true
 		});
 		$("#brchno").select2({
 			data:brchnoDict,
 			allowClear:true
 		});
 		$("#userlv").select2({
 			data:userlvDict,
 			allowClear:true
 		});
		//修改窗口
		var toEditModal = function(nRowA){
			//赋值
			$('#userid').attr("readOnly",true);
			$('#brchno').attr("readOnly",true);
			$('#userid').val($(nRowA[0]).text());  
			$('#userna').val($(nRowA[1]).text());
			$('#brchno').val($(nRowA[2]).text().substring($(nRowA[2]).text().indexOf("[")+1,$(nRowA[2]).text().indexOf("]"))).trigger("change");
			$('#maxert').val($(nRowA[3]).text());  
			$('#userst').val($(nRowA[4]).text().substring($(nRowA[4]).text().indexOf("[")+1,$(nRowA[4]).text().indexOf("]"))).trigger("change");
			$('#userlv').val($(nRowA[5]).text().substring($(nRowA[5]).text().indexOf("[")+1,$(nRowA[5]).text().indexOf("]"))).trigger("change");
			 editUrl = "auth/updateSysUser"; 
           	$("#editModal").modal('show');
		}
		/**
		 * 初始化sysUserTable
		 */
		sysUserTable.init({
	        src: userTable,
	        deleteData: sendData,
	        onSuccess: function (sysUserTable) {
	            
	        },
	        onError: function (sysUserTable) {
	            
	        },
	        dataTable: {  
	            "ajax": {
	                "url": url, 
	            },
	            "bDestroy" :true,
	            "bServerSide":true,
	            "columns" : [{ 
	            		"data": "userid",
	            		"sortable": false,
	            		"searchable": false
	            	},{ 
	            		"data": "userna",
		            	"sortable": false,
		            	"searchable": false
		            },{ 
		            	"data": "brchno",
		            	"sortable": false,
		            	"searchable": false,
		            	"render": function (data, type, full) {
		            	    for (var i = 0; i < brchnoDict.length; i++) {
		                          if (brchnoDict[i].id == data) {
		                            return brchnoDict[i].text;
		                          }
		                        }
		            	    return "";
		            	}
		            },{ 
		            	"data": "maxert",
		            	"sortable": false,
		            	"searchable": false
		            },
		            { 
		            	"data": "userst",
		            	"sortable": false,
		            	"searchable": false,
		            	"render": function (data, type, full) {
		            	    for (var i = 0; i < userstDict.length; i++) {
		                          if (userstDict[i].id == data) {
		                            return userstDict[i].text;
		                          }
		                        }
		            	    return "";
		            	}
		            },{ 
		            	"data": "userlv",
		            	"sortable": false,
		            	"searchable": false,
		            	"render": function (data, type, full) {
		            	    for (var i = 0; i < userlvDict.length; i++) {
		                          if (userlvDict[i].id == data) {
		                            return userlvDict[i].text;
		                          }
		                        }
		            	    return "";
		            	}
		            },
		            { "data": null,
		            	"width": "10%",
		            	"sortable": false,
		            	"searchable": false,
		            	"render": function (data, type, full) {
		            		return "<a class='edit' href='javascript:;' data-src='" + data.user_cd + "'>编辑 </a>";
		            	}
		            },{ "data": null,
		            	"width": "10%",
		            	"sortable": false,
		            	"searchable": false,
		            	"render": function (data, type, full) {
		            		return "<a class='rspword' href='javascript:;' data-src='" + data.userid + "'>重置密码</a>";
		            	}
		            },{ "data": null,
		            	"width": "10%",
		            	"sortable": false,
		            	"searchable": false,
		            	"render": function (data, type, full) {
		            		return "<a class='edit_setting' href='javascript:;' data-src='" + data.userid +","+data.brchno+ "'>配置角色</a>";
		            	}
		            },{ 
		            	"data": null,
		            	"width": "10%",
		            	"sortable": false,
		            	"searchable": false,
		            	"render": function (data, type, full) {
		            		return "<a class='deleteSysUser' href='javascript:;' data-src='" + data.userid + "'>注销</a>";
		            	}
		            }
	            ]
	        }
	    });
		var sendData = ["userid","brchno"];
		 //绑定注销事件
        sysUserTable.addBindEvent(".deleteSysUser","click",sendData, function(data){      
        	var redata={};
        	redata.userid=data.userid;
        	bootbox.confirm("确定要注销此用户么?", function(result) {
        		if(!result){
        			return;
        		}
        		Sunline.ajaxRouter("auth/deleteSysUser",redata,"DELETE",function(data,status){ 
        			$('.msg', editform).text(data.retMsg);
        			if(data.ret=="success"){
        				bootbox.alert("注销成功");
        				sysUserTable.submitFilter();
        			}else{		
        				bootbox.alert(data.msg);							
        			}
        		},function(){
        			bootbox.alert("请求失败");	
        		},"json");
        	});
        });
        sysUserTable.bindTableEdit(sendData,toEditModal);
        //绑定配置角色列表
        sysUserTable.addBindEvent(".edit_setting", "click", sendData, function(data){   
        	userdata=data;
     		sysUserRoleTable.setAjaxParam('user_cd', userdata.userid);
     		sysUserRoleTableInit(); 
        	$("#edit_setting").modal("show");   
        	var sysRoleDict=Sunline.getDict("","/role","auth_type","role_cd");
 	 		$("#sys_role").select2({
 	 			data:sysRoleDict
 	 		});
 	 		// 绑定删除用户角色事件
 	 		sysUserRoleTable.addBindEvent(".deleteSysUserRole", "click", sendRoleData, function(data) {   
 	 			var deletData = {};
 	 			deletData.regist_cd = data.regist_cd;
 	 			deletData.auth_type = data.auth_type;
 	 			deletData.role_cd = data.role_cd;
 	 			deletData.user_cd = data.user_cd;
 	 			Sunline.ajaxRouter("auth/deleteSysUserRole", deletData, "DELETE",function(data,status){ 
        			if(data.ret=="success"){
        				bootbox.alert(data.msg);
        				sysUserRoleTable.submitFilter();
        			}else{		
        				bootbox.alert(data.msg);							
        			}
        		},function(){
        			bootbox.alert("请求失败");	
        		},"json");
	 	 	});	
 	 		 // 绑定新增sysUserRole窗口
 	 		$("#add_role_btn", $("#add_role_set")).live("click", function() {
 	 			editSysRoleUrl = "auth/addSysUserRole";
 	 			setRoleform.modal('show');
 	 			return false;
 	 		});
        }); 
        
        //下拉框收回
        $("#setRoleModal").click(function(){
         	  $(".select-close-1").select2("close");
           });
        //重置密码
        sysUserTable.addBindEvent(".rspword","click", sendData, function(data){  
          var redata={};
          redata.userid=data.userid
    	  bootbox.confirm("确定要重置密码?", function(result) {
            	if(!result){
            		return;
            	}
            	Sunline.ajaxRouter("auth/updateSysUserPassWord",redata,"post",function(data,status){ 
 					$('.msg', editform).text(data.retMsg);
 					if(data.ret=="success"){
 						bootbox.alert("重置成功");
 					}else{		
 						bootbox.alert(data.msg);							
 					}
 					},function(){
 						bootbox.alert("请求失败");	
 					},"json");       	  
    	  });          	      	
        });  
        var editerror = $('.alert-danger', editform);
        var editsuccess = $('.alert-success', editform);
        var validator=editform.validate({
        	errorElement: 'span', 
        	errorClass: 'help-block help-block-error', 
        	focusInvalid: false, 
        	ignore: "",
        	rules: { //验证规则      
        		userid : 
        		{
        			required : true,
        			rangelength:[5,5],
                	number:true
                },   
                userna : {
                	required : false,
                	rangelength:[1,20]               	
                },
                brchno : 
                {
                	required : true,
                	rangelength:[2,19]	                	
                }
        	},
        	invalidHandler: function (event, validator) {              
        		editsuccess.hide();
        		editerror.show();
        		Metronic.scrollTo(editerror, -200);
             },

             errorPlacement: function (error, element) { 
            	 element.parent().append(error);
             },
             highlight: function (element) { 
            	 $(element).closest('.form-group').removeClass("has-success").addClass('has-error'); 
             },
             unhighlight: function (element) { 
                 
             },
             success: function (label, element) {
                 var icon = $(element).parent('.input-icon').children('i');
                 $(element).closest('.form-group').removeClass('has-error').addClass('has-success'); // set success class to the control group
                 icon.removeClass("fa-warning").addClass("fa-check");
             },
             submitHandler: function (form) {
        	  	/**
    	         * 提交数据,必须是json对象
    	         * 返回也必须是json对象
    	         */
 		        var data={};
 	           	$.each($("input",editform),function(i,n){    	           		
 	           		data[n.name]=n.value;
 	           	});
 	           	Sunline.ajaxRouter(editUrl,data,"post",function(data,status){ 
 					$('.msg', editform).text(data.msg);
 					if(data.ret=="success"){
 						$('.alert-success', editform).show();
 						$('.alert-danger', editform).hide(); 
 					   	$('#userid',editform).attr("readOnly",true);
 			         	$('#brchno',editform).attr("readOnly",true);
 					}else{		
 						$('.alert-success', editform).hide();
 						$('.alert-danger', editform).show(); 							
 					}
 					},function(){
 						$('.msg', editform).text("网络异常!");
 						$('.alert-success', editform).hide();
 						$('.alert-danger', editform).show(); 
 					},"json"); 
             }
         });
         //新增窗口
        $("#add_new_person").click(function(){	  
        	$('input',editform).attr("readOnly",false);
        	validator.resetForm();
          	$('.msg', editform).text("");
          	editerror.hide();
          	editsuccess.hide();      
         	$('.form-group').removeClass('has-error').removeClass("has-success");
         	$('input', editform).val("").trigger("change");
         	 editUrl = "auth/addSysUser"; 
         	$("#editModal").modal('show');	        	
        }); 
         
        //编辑表单验证结束             
        $("#btn_save").click(function(){
        	validator.resetForm();
        	$('.form-group').removeClass('has-error').removeClass("has-success");
        	$('.alert-danger', $('#edit_form')).hide();
        });
        $("#editModal").on("hide.bs.modal",function(){
        	$('#userid',editform).attr("readOnly",true); 	  
        	$('#brchno',editform).attr("readOnly",true);
        	validator.resetForm();
         	$('.msg', editform).text("");
         	editerror.hide();
         	editsuccess.hide();      
        	$('.form-group').removeClass('has-error').removeClass("has-success");
        	sysUserTable.submitFilter();       	
        });   
        	
        //关闭角色配置userrole	
        $("#edit_setting").on("hide.bs.modal",function(){
        	$("#role_auth_ajax").off("click",".delete");
        	$("#add_role_btn", $("#add_role_set")).die("click");
        	sysUserRoleTable.clearAjaxParams;       	
        }); 
        /*
 		 * 角色表单验证方法
 		 */

        var roleediterror = $('.alert-danger', setRoleform);
        var roleeditsuccess = $('.alert-success', setRoleform);
 		var roleValidator = $("#edit_role_form").validate(
 				{
 					errorElement : 'span',
 					errorClass : 'help-block help-block-error',
 					focusInvalid : false,
 					ignore : "",
 					rules : { 
 					},

 					invalidHandler : function(event, roleValidator) {
 						roleeditsuccess.hide();
 						roleediterror.show();
 						Metronic.scrollTo(roleediterror, -200);
 					},

 					errorPlacement : function(error, element) {
 						element.parent().append(error);
 					},

 					highlight : function(element) {
 						$(element).closest('.form-group').removeClass("has-success").addClass('has-error');
 					},

 					unhighlight : function(element) {

 					},

 					success : function(label, element) {
 						var icon = $(element).parent('.input-icon').children('i');
 						$(element).closest('.form-group').removeClass('has-error').addClass('has-success'); 
 						icon.removeClass("fa-warning").addClass("fa-check");
 					},
 					submitHandler : function(form) {
 				        /*
 				         * 提交数据,必须是json对象
 				         * 返回也必须是json对象
 				         */
 						var data = {};
 						var acd=$("#sys_role").select2('data');
 						data['role_cd']=acd.text.substring(0,acd.text.indexOf("["));
 						data['auth_type']=$("#sys_role").select2('data').id;
 						data.user_cd=userdata.userid;
 						Sunline.ajaxRouter(editSysRoleUrl, data, "post", function(
 								data, status) {
 							$('.msg', setRoleform).text(data.msg);
 							if (data.ret == "success") {
 								$('.alert-success', setRoleform).show();
 								$('.alert-danger', setRoleform).hide();
 								$('#registerCd', setRoleform).attr("readOnly", true);
 								$('#authType', setRoleform).attr("readOnly", true);
 								$('#roleCd', setRoleform).attr("readOnly", true);
 							} else {
 								$('.alert-success', setRoleform).hide();
 								$('.alert-danger', setRoleform).show();
 							}
 						}, function() {
 							$('.msg', setRoleform).text("请求出错!");
 							$('.alert-success', setRoleform).hide();
 							$('.alert-danger', setRoleform).show();
 						}, "json");
 					}
 				});
 	
 		// 绑定关闭处理setRoleform
 		$(".closeModal ", setRoleform).click(
 				function() {
 					roleValidator.resetForm();
 					$('.msg', setRoleform).text("");
 					roleediterror.hide();
 					roleeditsuccess.hide();
 					$('.form-group', setRoleform).removeClass('has-error').removeClass("has-success");
 					sysUserRoleTable.submitFilter();
 				}); 		
 		/**
 		 * 初始化sysUserRoleTable
 		 */
 		var sysUserRoleTableInit = function() {
 			$("#add_role_set").append("<div class='table-actions-wrapper'><span></span>"
 					+ "<button id='add_role_btn' class='btn btn-sm green table-group-action-submit'>新增</button></div>");
 			sysUserRoleTable.init({
 				src : userRoleTable,
 				deleteData : sendRoleData,
 				onSuccess : function(sysUserRoleTable) {
 					
 				},
 				onError : function(sysUserRoleTable) {
 					
 				},
 				dataTable : {
 					"ajax" : {
 						"url" : sysUserRoleRUrl, 
 					},
 					"bDestroy" : true,
 					"bServerSide" : true,
 					"columns" : [
 					             {
 					            	 "data" : "regist_cd",
 					            	 "sortable" : false,
 					            	 "searchable" : false
 					             },
 					             {
 					            	 "data" : "auth_type",
 					            	 "sortable" : false,
 					            	 "searchable" : false
 					             },
 					             {
 					            	 "data" : "role_cd",
 					            	 "sortable" : false,
 					            	 "searchable" : false
 					             },
 					             {
 					            	 "data" : "user_cd",
 					            	 "sortable" : false,
 					            	 "searchable" : false
 					             },
 					             {
 					            	 "data" : null,
 					            	 "sortable" : false,
 					            	 "searchable" : false,
 					            	 "render" : function(data, type, full) {
 					            		 return "<a class='deleteSysUserRole' href='javascript:;' data-src='"
 					            		 		+ data.regist_cd + ","
												+ data.auth_type + ","
												+ data.role_cd +","
												+ data.user_cd+"'>删除 </a>";
 					            	 }
 					             } ]
 					}
 			});	 		
 		};
	}
	return {
		init: function () {
			handleTable();     
		}
	};
}();