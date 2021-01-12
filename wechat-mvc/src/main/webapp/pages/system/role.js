var sysRole = function() {
	var authTypeDict = Sunline.getDict("D_AUTHTP");
	var rolecontent = $('.inbox-content');
	var handleTable = function() {
		var rolegrid = new Datatable();
		var url = Sunline.ajaxPath("auth/showAllSysRole");
		var editUrl;
		var table = $("#role_ajax");
		var editform = $("#edit_form");
		/**
		 * 获取字典
		 */
		$("#auth_type").select2({
			data : authTypeDict,
			allowClear : true,
			placeholder : "请选择"
		});
		$("#q_authType").select2({
			data : authTypeDict,
			allowClear : true,
			placeholder : "请选择"
		});
		
		// 修改窗口
		var toEditModal = function(nRowA) {
			// 赋值
			$('#regist_cd', editform).attr("readOnly", true);
			$('#auth_type', editform).attr("readOnly", true);
			$('#role_cd', editform).attr("readOnly", true);
			$('#regist_cd').val($(nRowA[0]).text());
			$('#auth_type').val($(nRowA[1]).text().substring($(nRowA[1]).text().indexOf("[") + 1, $(nRowA[1]).text().indexOf("]"))).trigger("change");
			$('#role_cd').val($(nRowA[2]).text());
			$('#role_name').val($(nRowA[3]).text());
			editUrl = "auth/updateSysRole";
			$("#editModal").modal('show');
			$("#editModal").on("hide.bs.modal", function() {
				$(".alert-success", $('form', $("#editModal"))).hide();
				$('.alert-danger', $('form', $("#editModal"))).hide();
				$(".msg", $('form', $("#editModal"))).text("");
				rolegrid.submitFilter();
			});
		}
		
		/*
		 * 初始化table
		 */
		rolegrid.init({
					src : table,
					deleteData : sendData,
					onSuccess : function(rolegrid) {
					},
					onError : function(rolegrid) {
					},
					dataTable : { // here you can define a typical datatable
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
									"data" : "role_name",
									"sortable" : false,
									"searchable" : false
								},
								{
									"data" : null,
									"sortable" : false,
									"searchable" : false,
									"render" : function(data, type, full) {
										return "<a class='edit' href='javascript:;' data-src='"
												+ data.regist_cd
												+ ","
												+ data.auth_type
												+ ","
												+ data.role_cd + "'>编辑 </a>";
									}
								},
								{
									"data" : null,
									"sortable" : false,
									"searchable" : false,
									"render" : function(data, type, full) {
										return "<a class='edit_setting' href='javascript:;' data-src='"
												+ data.regist_cd
												+ ","
												+ data.auth_type
												+ ","
												+ data.role_cd + "'>配置权限</a>";
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
												+ data.role_cd + "'>删除 </a>";
									}
								} ]
					}
				});

		var sendData = [ "regist_cd", "auth_type", "role_cd" ];
		// 绑定删除事件
		rolegrid.bindTableDelete(sendData);
		// 绑定编辑事件按钮
		rolegrid.bindTableEdit(sendData, toEditModal);
		// 绑定权限设置列表
		rolegrid.addBindEvent(".edit_setting", "click", sendData,
				function(data) {
						// 显示配置窗口
						loadSubPage(data);
						$("#edit_setting").modal("show");
						$("#editModal").on("hide.bs.modal", function() {
							rolegrid.submitFilter();
						});
					}
		);

		// 新增窗口
		$("#add_btn").bind("click", function() {
			$('input', editform).attr("readOnly", false);
			$('input', editform).val("");
			$('input[name="regist_cd"]', editform).attr("readOnly", true);
			$("#regist_cd").val($.cookie("registCd"));
			editUrl = "auth/addSysRole";
			$("#editModal").modal('show');
			$("#editModal").on("hide.bs.modal", function() {
				$(".alert-success", $('form', $("#editModal"))).hide();
				$('.alert-danger', $('form', $("#editModal"))).hide();
				$(".msg", $('form', $("#editModal"))).text("");
				rolegrid.submitFilter();
			});
			return false;
		});

		/*
		 * 表单验证方法
		 */
		var roleValid = Sunline.getValidate($("form", "#editModal"), function(form) {
			var data = {};
			$.each($("input", editform), function(i, n) {
				data[n.name] = n.value;
			});
			Sunline.ajaxRouter(editUrl, data, "post", function(data, status) {
				$('.msg', editform).text(data.msg);
				if (data.ret == "success") {
					$('.alert-success', editform).show();
					$('.alert-danger', editform).hide();
					$('#regist_cd', editform).attr("readOnly", true);
					$('#auth_type', editform).attr("readOnly", true);
					$('#role_cd', editform).attr("readOnly", true);
				} else {
					$('.alert-success', editform).hide();
					$('.alert-danger', editform).show();
				}
			}, function() {
				$('.msg', editform).text("请求出错!");
				$('.alert-success', editform).hide();
				$('.alert-danger', editform).show();
			}, "json");
		}, { // 验证规则
			regist_cd : {
				required : true,
				rangelength : [ 2, 19 ]
			}/*,
			authType : {
				required : true,
				maxlength : 1,
				number : true
			}*/,
			roleCd : {
				required : true,
				rangelength : [ 2, 19 ]
			},
			roleName : {
				required : false,
				rangelength : [ 1, 50 ]
			},
			queryAuth : {
				required : false,
				maxlength : 1,
				number : true
			},
		});
	}

	var loadSubPage = function(data) {
		rolecontent.html('');
		$.ajax({
			type : "GET",
			//妈的类似于一个菜单请求需要显示一个Controller返回一个ModelAndView
			url : Sunline.getBasePath() + "/menuUrl/system/role_auth",
			dataType : "html",
			success : function(res) {
				rolecontent.html(res);
				rolecontent.ready(function() {
					sysRoleAuth.init(data);
					Metronic.initUniform();
				});
			},
			error : function(xhr, ajaxOptions, thrownError) {
			},
			async : false
		});
	}


	return {
		init : function() {
			handleTable();
		}

	};
}();