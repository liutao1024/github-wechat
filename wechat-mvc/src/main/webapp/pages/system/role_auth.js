var sysRoleAuth= function() {

	var handleTable = function(roledata) {
		var authgrid = new Datatable();
		var url = Sunline.ajaxPath("auth/showSysRoleAuth");
		var editUrl;
		var table = $("#role_auth_ajax");
		var setAuthform = $("#setAuthModal");
		var authTypeDict = Sunline.getDict("D_AUTHTP");
		var authCdDict = Sunline.getDict(roledata.auth_type, "/auth", "auth_cd", "menu_name");
		$("#add_auth_cd").select2({
			data : authCdDict,
			allowClear : true,
			placeholder : "请选择"
		});
		$("#add_auth_type").select2({
			data : authTypeDict,
			allowClear : true,
			placeholder : "请选择"
		});
		/**
		 * 初始化table
		 */
		if (!Sunline.isNull(roledata.regist_cd)) {
			authgrid.setAjaxParam('regist_cd', roledata.regist_cd);
		}
		if (!Sunline.isNull(roledata.auth_type)) {
			authgrid.setAjaxParam('auth_type', roledata.auth_type);
		}
		if (!Sunline.isNull(roledata.role_cd)) {
			authgrid.setAjaxParam('role_cd', roledata.role_cd);
		}
		authgrid.init({
					src : table,
					deleteData : sendData,
					onSuccess : function(authgrid) {
					},
					onError : function(authgrid) {
					},
					dataTable : { // here you can define a typical
						"ajax" : {
							"url" : url, // ajax source
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
									"searchable" : false,
									"render" : function(data, type, full) {
										for (var i = 0; i < authTypeDict.length; i++) {
											if (authTypeDict[i].id == data) {
												return authTypeDict[i].text;
											}
										}
										return data;
									}
								},
								{
									"data" : "role_cd",
									"sortable" : false,
									"searchable" : false
								},
								{
									"data" : "auth_cd",
									"sortable" : false,
									"searchable" : false,
									"render" : function(data, type, full) {
										for (var i = 0; i < authCdDict.length; i++) {
											if (authCdDict[i].id == data) {
												return authCdDict[i].text;
											}
										}
										return data;
									}
								},
								{
									"data" : null,
									"sortable" : false,
									"searchable" : false,
									"render" : function(data, type, full) {
										return "<a class='delete' href='javascript:;' data-src='"
												+ data.regist_cd
												+ ","
												+ data.auth_type
												+ ","
												+ data.role_cd
												+ ","
												+ data.auth_cd + "'>删除 </a>";
									}
								} ]
					}
				});
		var sendData = [ "regist_cd", "auth_type", "role_cd", "auth_cd" ];
		// 绑定删除事件
		authgrid.bindTableDelete(sendData);
		
		//下拉框回收
		$("#setAuthModal").click(function(){
		   	  $(".select-close-1").select2("close");
		     });
		
		// 新增窗口
		$("#add_Auth_btn", $("#add_btn_set")).bind("click", function() {
			$(".alert-success", $('form', setAuthform)).hide();
			$('.alert-danger', $('form', setAuthform)).hide();
			$('#add_regist_cd', setAuthform).val(roledata.regist_cd);
			$('#add_auth_type', setAuthform).select2("val", roledata.auth_type);
			$('#add_role_cd', setAuthform).val(roledata.role_cd);
			editUrl = "auth/addSysRoleAuth";
			setAuthform.modal('show');
			setAuthform.on("hide.bs.modal", function() {
				$(".alert-success", $('form', setAuthform)).hide();
				$('.alert-danger', $('form', setAuthform)).hide();
				$(".msg", $('form', setAuthform)).text("");
				authgrid.submitFilter();
			});
			return false;
		});

		$("#subAuth_btn").click(function() {
			$("#edit_auth_form").submit();
		});

		/**
		 * 表单验证方法
		 */
		var roleValid = Sunline.getValidate($("form", setAuthform), 
				function(form) {
					var data = {};
					$.each($("input", setAuthform), function(i, n) {
						//from表单中input标签的id和实体类的变量不一致,在此处理为一致的
						data[(n.name).substring(4)] = n.value;
					});
					Sunline.ajaxRouter(
							editUrl, //请求URL
							data, //数据
							"post", //请求类型
							function(data, status) {//success函数
								$('.msg', setAuthform).text(data.msg);
								if (data.ret == "success") {
									$('.alert-success', setAuthform).show();
									$('.alert-danger', setAuthform).hide();
									$('#add_regist_cd', setAuthform).attr("readOnly", true);
									$('#add_auth_type', setAuthform).attr("readOnly", true);
									$('#add__role_cd', setAuthform).attr("readOnly", true);
								} else {
									$('.alert-success', setAuthform).hide();
									$('.alert-danger', setAuthform).show();
								}
							}, 
							function() {//errror函数
								$('.msg', setAuthform).text("请求出错!");
								$('.alert-success', setAuthform).hide();
								$('.alert-danger', setAuthform).show();
							}, 
							"json"//数据类型
							);
				},
				{ // 验证规则
					add_regist_cd : {
						required : true,
						rangelength : [ 2, 19 ]
					},
					add_auth_type : {
						required : true,
						maxlength : 1,
						number : true
					},
					add_role_cd : {
						required : true,
						rangelength : [ 2, 19 ]
					},
					add_auth_cd : {
						required : true,
						rangelength : [ 2, 19 ]
					}
				});
	}

	return {
		init : function(data) {
			handleTable(data);
		}
	}
}();