var Custinfo = function(){
	var sextypDict=Sunline.getDict("D_SEXTYP");
	var idtftpDict=Sunline.getDict("D_IDTFTP");
	var custstDict=Sunline.getDict("D_CUSTST");
	
	var grid = new Datatable();
	var tran_grid = new Datatable();
	var re_grid;
	var isF = true;
	var o_ecctno;
	var o_addr;
	var o_email;
	var o_acctst;
	var isNotF = true;
	
	$("#idtftp").select2({
		data : idtftpDict,
		allowClear:true
	});
	$("#e_idtftp").select2({
		data : idtftpDict,
		allowClear:true
	});
	$("#e_custst").select2({
		data : custstDict,
		allowClear:true
	});
	$("#e_sextyp").select2({
		data : sextypDict,
		allowClear:true
	});
	var formartDict = function(dict,value){
		for(var i=0 ; i<dict.length ; i++){
			if(value == dict[i].dictId){
				return dict[i].dictName;
			}
			if(value == dict[i].dictName){
				return dict[i].dictId;
			}
			if(value == dict[i].dictText){
				return dict[i].dictId;
			}
		}
		return value;
	};
	var formartM = function(value){
		value = value.toString();
		if(value.toString().indexOf('.') == -1){
			return value+".00";
		}else if(value.split('.')[1].length == 1) {
			return value+'0';
		} else {
			return value;
		}
	};
	var formartTime = function(time){
		if(time.length == 8){
			return time.substr(0,1)+":"+time.substr(1,2)+":"+time.substr(3,2);
		}
		return time.substr(0,2)+":"+time.substr(2,2)+":"+time.substr(4,2);
	};
	
//	var content = $('#edit_load');//配置商户关联信息子页面
	var handlerTable = function(){
		
		var editForm = function(nRowA){
			$('#e_custno').val($(nRowA[1]).text());
			$('#e_custna').val($(nRowA[2]).text());
//			$('#e_idtftp').select2("val",formartDict(idtftpDict,$(nRowA[3]).text()));
			$('#e_idtftp').val($(nRowA[3]).text().substring($(nRowA[3]).text().indexOf("[")+1,$(nRowA[3]).text().indexOf("]"))).trigger("change");
			$('#e_idtfno').val($(nRowA[4]).text());
//			$('#e_sextyp').select2("val",formartDict(sextypDict,$(nRowA[5]).text()));
			$('#e_sextyp').val($(nRowA[5]).text().substring($(nRowA[5]).text().indexOf("[")+1,$(nRowA[5]).text().indexOf("]"))).trigger("change");
			$('#e_teleno').val($(nRowA[6]).text());
			$('#e_addres').val($(nRowA[7]).text());
//			$('#e_custst').select2("val",formartDict(custstDict,$(nRowA[8]).text()));
			$('#e_custst').val($(nRowA[8]).text().substring($(nRowA[8]).text().indexOf("[")+1,$(nRowA[8]).text().indexOf("]"))).trigger("change");
           	$("#editModal").modal('show');
		};
		var url = Sunline.ajaxPath("cust/custinfo");
		grid.setAjaxParam("custno","");
		grid.setAjaxParam("custna","");
		grid.setAjaxParam("idtftp","");
		grid.setAjaxParam("idtfno","");
		grid.init({
	        src: $("#cif_ajax"),
	        deleteData: sendData,
	        onSuccess: function (grid) {
	        	$('.table-container .alert-danger').css("display","none");
	        },
	        onError: function (grid) {
	        	console.info("It is error!");
	        },
	        dataTable: {  
	            "ajax": {
	                "url": url,
	            },
	            "bDestroy" : true,
				"bServerSide" : true,
	            "columns" : [{
				            	"data": "null",
				            	"sortable": false,
				            	"searchable": false,
				            	"width": "2%",
				            	"render": function (data, type, full) {
				            		return '<input type="checkbox" class="checkboxes" value="1"/>';
				            	}
			            	},{     
				            	"data": "custno",
				            	"sortable": false,
				            	"searchable": false
				            },{     
				            	"data": "custna",
				            	"sortable": false,
				            	"searchable": false,
				            },{     
				            	"data": "idtftp",
				            	"sortable": false,
				            	"searchable": false,
				            	"render": function (data, type, full) {
				            	    for (var i = 0; i < idtftpDict.length; i++) {
				                          if (idtftpDict[i].id == data) {
				                            return idtftpDict[i].text;
				                          }
				                        }
				            	    return "";
				            	}
				            },{ 
				            	"data": "idtfno",
				            	"sortable": false,
				            	"searchable": false
				            },{ 
				            	"data": "sextyp",
				            	"sortable": false,
				            	"searchable": false,
				            	"render": function (data, type, full) {
				            	    for (var i = 0; i < sextypDict.length; i++) {
				                          if (sextypDict[i].id == data) {
				                            return sextypDict[i].text;
				                          }
				                        }
				            	    return "";
				            	}
				            },{ 
				            	"data": "teleno",
				            	"sortable": false,
				            	"searchable": false
				            },{ 
				            	"data": "addres",
				            	"sortable": false,
				            	"searchable": false
				            },{ 
				            	"data": "custst",
				            	"sortable": false,
				            	"searchable": false,
				            	"render": function (data, type, full) {
				            	    for (var i = 0; i < custstDict.length; i++) {
				                          if (custstDict[i].id == data) {
				                            return custstDict[i].text;
				                          }
				                        }
				            	    return "";
				            	}
				            },{ 
				            	"data": "opendt",
				            	"sortable": false,
				            	"searchable": false
				            },{ 
				            	"data": null,
				            	"sortable": false,
				            	"searchable": false,
				            	"render": function (data, type, full) {
				            		return "<a class='outCa' href='javascript:;' data-src='" + data.ecctno+ "'>绑定卡号</a>";
				            	}
				            },{ "data": "null",
				            	"sortable": false,
				            	"searchable": false,
				            	"render": function (data, type, full) {
				            		return "<a class='edit' href='javascript:;' data-src='" + data + "'>编辑 </a>";
				            	}
				            },{ 
				            	"data": null,
				            	"sortable": false,
				            	"searchable": false,
				            	"render": function (data, type, full) {
				            		return "<a class='releInfo' href='javascript:;' data-src='" + data.ecctno+ "'>关联产品</a>";
				            	}
				            }]
	        }
	    });			
		$(".table-group-actions").append("<button id='tran_btn' class='btn btn-sm blue table-group-action-submit'><i class='fa icon-cloud-download'></i>&nbsp;查询交易信息</button></div>");
		$(".table-group-actions").append("<button id='f_btn' class='btn btn-sm lightblue table-group-action-submit'><i class='fa icon-lock'></i>&nbsp;账户冻结明细</button></div>");
		var sendData = ["custno"];
        grid.bindTableEdit(sendData,editForm);
        //关联产品
        grid.addBindEvent(".releInfo", "click", sendData,
				function(data) {
			// 显示配置窗口
        	showReleInfo(data);
			$("#releInfoModal").modal("show");
		});
        //绑卡信息
        grid.addBindEvent(".outCa", "click", ["custno"],
				function(data) {
        	console.log(data);
        	content.html('');
    	    name="cifOutCa";
    	    
            $.ajax({
                type: "GET",
                url: "../cust/"+name,
                dataType: "html",
                success: function(res) 
                {    
                    content.html(res);
                    content.ready(function(){               	
                    	  Metronic.initUniform();
                    	  try{      
                    		  Cabind.init(data.ecctno);
                    	  }catch(e){
                    		  bootbox.alert("子页面加载失败！");
                    	  }
                    });             
                },
                error: function(xhr, ajaxOptions, thrownError)
                {
                },
                async: false
            });
            $("#outCaModal").modal('show');
		});   
	};
	
	//关联产品
	var showReleInfo = function(data) {
		var tabData = [];
		var dataSet = [];
		var input={};
		input.ecctno = data.ecctno;
		console.log(input);
    	Sunline.ajaxRouter(
	        	"cust/releinfo",
	        	input,
	        	"POST",
	        	function(redata){
	        		console.log(redata);
	        		if(redata.retCode == '0000'){
	        			var dataList=redata.accsinfo;
	        			for(var i=0 ; i<dataList.length ; i++){
	        				tabData=[dataList[i].prodcd,dataList[i].prodna,formartM(dataList[i].onlnbl),e_prodtp(dataList[i].prodtp)];
		        			dataSet.push(tabData);
	        			}
	        			console.log(dataSet);
	        			if(isF){
	        				re_grid = $('#releInfo').DataTable({
	        					data: dataSet,
	        					paging: false,
	        					searching: false,
	        					ordering: false,
	        					info: false,
	        					columns: [
	        					          { title: "产品编号" },
	        					          { title: "产品名称" },
	        					          { title: "余额" },
	        					          { title: "产品类别" },				   
	        					          ]
	        				});
	        				isF = false;
	        			}else{
	        				re_grid.clear().draw();
	        				for(var i=0;i<dataSet.length;i++){
	        					re_grid.row.add(dataSet[i]).draw(false);
	        				}
	        			}	        				        			
        		} else {
        			bootbox.alert(redata.retMsg);
        		}
	        	},
	        	function(redata){
	        		console.info(redata);
	        		bootbox.alert("交易异常,请检查网络设置或重新登录"); 
	        	},
	        	"json",
	        	false); 
	}
	var handlerForm = function(){
		jQuery.validator.addMethod("id_no", function(value, element, param) {
			if((!Sunline.isNull(value)) && param == true){
				return IdCardValidate(value);
			}
			return true;
		}, $.validator.format("证件号码输入有误"));
		$('#cust-form').validate({
			errorElement: 'span', //default input error message container
            errorClass: 'help-block', // default input error message class
            focusInvalid: false, // do not focus the last invalid input
            rules:{
            	custac: {
                    required: false
            	},
            	telecd : {
            		required: false,
            		rangelength : [11,11]
            	}
            },
            messages: {
            	checkdate: {
                    required: "对账日期必填"
                },
            	telecd : {
            		rangelength : "手机号码位数不正确"
            	}
            },

            invalidHandler: function (event, validator) { //display error alert on form submit   
                $('.alert-danger', $('#cust-form')).show();
            },

            highlight: function (element) { // hightlight error inputs
                $(element).closest('.form-group').addClass('has-error'); // set error class to the control group
            },

            success: function (label) {
                label.closest('.form-group').removeClass('has-error');
                label.remove();
            },
            submitHandler: function (form) {
            },
            errorPlacement: function (error, element) {
                error.insertAfter(element.closest('.input-icon'));
            }
           
		});
		$('#mod-form').validate({
			errorElement: 'span', //default input error message container
            errorClass: 'help-block', // default input error message class
            focusInvalid: false, // do not focus the last invalid input
            rules:{
            	//m_addr: {
                  //  required: true
            	//},
            	//m_email : {
            	//	required: true
            	//},
            	m_acctst: {
                    required: true
                    //isidtp : true
            	}
            },
            messages: {
            	checkdate: {
                    required: "对账日期必填"
                }
            },

            invalidHandler: function (event, validator) { //display error alert on form submit   
                $('.alert-danger', $('#mod-form')).show();
            },

            highlight: function (element) { // hightlight error inputs
                $(element).closest('.form-group').addClass('has-error'); // set error class to the control group
            },

            success: function (label) {
                label.closest('.form-group').removeClass('has-error');
                label.remove();
            },
            errorPlacement: function (error, element) {
                error.insertAfter(element.closest('.input-icon'));
            }
		});
		jQuery.validator.addMethod("istruedt", function(value, element, param) { 
			if(param==true){
				console.info($('#trandt_from').val() + value);
				return parseInt($('#trandt_from').val()) > parseInt($('#trandt_to').val());
			}
			return true;
			
		}, $.validator.format("起始日期不能大于结束日期"));
		$('#cust-tran-form').validate({
			errorElement: 'span', //default input error message container
            errorClass: 'help-block', // default input error message class
            focusInvalid: false, // do not focus the last invalid input
            rules:{
            	tran_custac: {
                    required: true
            	},
            	from : {
            		required: false
            		//istruedt : true
            	},
            	to: {
                    required: false
                    //istruedt : true
            	}
            },
            messages: {
            	tran_custac: {
                    required: "电子账户必填"
                }
            },

            invalidHandler: function (event, validator) { //display error alert on form submit   
                $('.alert-danger', $('#cust-tran-form')).show();
            },

            highlight: function (element) { // hightlight error inputs
                $(element).closest('.form-group').addClass('has-error'); // set error class to the control group
            },

            success: function (label) {
                label.closest('.form-group').removeClass('has-error');
                label.remove();
            },
            errorPlacement: function (error, element) {
                error.insertAfter(element.closest('.input-icon'));
            }
		});
	};
	var handlerOperator = function(){
		if (jQuery().datepicker) {
            $('.date-picker').datepicker({
                rtl: Metronic.isRTL(),
                orientation: "left",
                autoclose: true,
                language: 'zh-CN'
            });
            //$('body').removeClass("modal-open"); // fix bug when inline picker is used in modal
        }
		//查询
		$('#submit').click(function(){
			if(!$('#cust-form').validate().form()){
				return;
			}
			grid.setAjaxParam("custno",$('#custno').val());
			grid.setAjaxParam("custna",$('#custna').val());
			grid.setAjaxParam("idtftp",$('#idtftp').select2("val"));
			grid.setAjaxParam("idtfno",$('#idtfno').val());
			grid.submitFilter();
		});
		//取消
		$('#cancle').click(function(){
			$('#custno').val("");
			$('#custna').val("");
			$('#idtftp').select2("val","");
			$('#idtfno').val("");
		});
		$('#tran_cancle').click(function(){
			$('#trandt_from').val("");
			$('#trandt_to').val("");
		});
		//修改保存
		$('#e_save').click(function(){
			if(!$('#mod-form').validate().form()){
				return;
			}
			var input = {};
			custno = $('#e_custno').val();
			input.custno = $('#e_custno').val();
			input.custna = $('#e_custna').val();
			input.idtftp = $('#e_idtftp').select2("val");
			input.idtfno = $('#e_idtfno').val();
			input.sextyp = $('#e_sextyp').select2("val");
			input.custst = $('#e_custst').select2("val");
			input.teleno = $('#e_teleno').val();
			input.addres = $('#e_addres').val();
//			alert(custno);
			$("#myModal").modal('show');
			Sunline.ajaxRouter(
		         	"cust/update", 
		         	 input,
		         	"post",
		             function(redata){
		         		$("#myModal").modal('hide');
		         		if(redata.retCode!='0000'){
		         			bootbox.alert(redata.retMsg);
		         			return;
		         		}
		         		bootbox.alert("账户["+ custno +"]信息维护成功！",function(){
		         			$('#e_custno').val("");
		         			$('#e_custna').val("");
		         			$('#e_custst').select2("val","");
		         			$('#e_idtftp').select2("val","");
		        			$('#e_idtfno').val("");
		        			$('#e_addres').val("");
		        			$('#e_teleno').val("");
		                   	$("#editModal").modal('hide');
		                   	grid.submitFilter();
		         		});
		         	},
		         	function(redata){
		         		//console.info("error:",redata);
		         		$("#myModal").modal('hide');
		         		bootbox.alert("网络异常");
		         	},"json");
		});
		
		//交易明细
		$('#tran_btn').click(function(){
			
			var rows = grid.getSelectedRows();
			if(rows.length != 1){
				bootbox.alert("请选择一条信息");
				return;
			}
			var ecctno = $(rows[0].children()[1]).text();
			$('#tran_custac').val(ecctno);
			var url1 = Sunline.ajaxPath("cust/cutrif");
			console.info(isNotF);
			if(isNotF){
				tran_grid.setAjaxParam("ecctno",ecctno);
				tran_grid.setAjaxParam("from","");
				tran_grid.setAjaxParam("to","");
				tran_grid.setAjaxParam("eccttp","");
				tran_grid.setAjaxParam("crcycd","01");
				tran_grid.init({
			        src: $("#cif_tran_ajax"),
			        deleteData: sendData,
			        onSuccess: function (grid) {
			            // execute some code after table records loaded
			        	$('.cif_tran_ajax_wrapper .alert-danger').css("display","none");
			        },
			        onError: function (grid) {
			            // execute some code on network or other general error
			        	//$('.cif_tran_ajax_wrapper .alert-danger').css("display","none");
			        	//console.info("It is error!");
			        },
			        dataTable: { // here you can define a typical datatable settings from http://datatables.net/usage/options 
			            "ajax": {
			                "url": url1, // ajax source
			            },
			            "columns" : [{
				            	"data": "trandt",
				            	"sortable": false,
				            	"searchable": false
			            	},{     
				            	"data": "trantm",
				            	"sortable": false,
				            	"searchable": false,
				            	"render" : function(data,type,full){
				            		return formartTime(data);
				            	}
				            },{ 
				            	"data": "remark",
				            	"sortable": false,
				            	"searchable": false
				            },{     
				            	"data": "tranam",
				            	"sortable": false,
				            	"searchable": false,
				            	"render" : function(data,type,full){
				            		return formartM(data);
				            	}
				            },{ 
				            	"data": "chnlno",
				            	"sortable": false,
				            	"searchable": false
				            },{ 
				            	"data": "avalbl",
				            	"sortable": false,
				            	"searchable": false,
				            	"render" : function(data,type,full){
				            		return formartM(data);
				            	}
				            },{ 
				            	"data": "smryds",
				            	"sortable": false,
				            	"searchable": false
				            },{ 
				            	"data": "jnlseq",
				            	"sortable": false,
				            	"searchable": false
				            },{ 
				            	"data": "flowtp",
				            	"sortable": false,
				            	"searchable": false,
				            	"render" : function(data,type,full){
				            		 for (var i = 0; i < flowtpDict.length; i++) {
				                          if (flowtpDict[i].id == data) {
				                            return flowtpDict[i].dictName;
				                          }
				                        }
				            	    return data;
				            	}
				            },{ 
				            	"data": "amntcd",
				            	"sortable": false,
				            	"searchable": false,
				            	"render" : function(data,type,full){
				            		 for (var i = 0; i < amntcdDict.length; i++) {
				                          if (amntcdDict[i].id == data) {
				                            return amntcdDict[i].dictName;
				                          }
				                        }
				            	    return data;
				            	}
				            },{ 
				            	"data": "transt",
				            	"sortable": false,
				            	"searchable": false,
				            	"render" : function(data,type,full){
				            		 for (var i = 0; i < transtDict.length; i++) {
				                          if (transtDict[i].id == data) {
				                            return transtDict[i].dictName;
				                          }
				                        }
				            	    return data;
				            	}
				            },{ 
				            	"data": "prcsid",
				            	"sortable": false,
				            	"searchable": false
				            }
			            ]
			        }
			    });
				var sendData = ["transq"];
				isNotF = false;
			} else {
				console.info(tran_grid.gettableContainer());
				console.info(tran_grid.getDataTable());
				console.info(tran_grid.getTable());
				tran_grid.setAjaxParam("ecctno",ecctno);
				tran_grid.setAjaxParam("from",$('#trandt_from').val());
				tran_grid.setAjaxParam("to",$('#trandt_to').val());
				tran_grid.setAjaxParam("eccttp","");
				tran_grid.setAjaxParam("crcycd","01");
				tran_grid.submitFilter();
			}
			$("#tranModal").modal('show');
		});
		
		//交易查询
		$('#tran_qry').click(function(){
			if(!$('#cust-tran-form').validate().form()){
				return;
			}
			tran_grid.setAjaxParam("ecctno",$('#tran_custac').val());
			tran_grid.setAjaxParam("from",$('#trandt_from').val());
			tran_grid.setAjaxParam("to",$('#trandt_to').val());
			tran_grid.setAjaxParam("eccttp","");
			tran_grid.setAjaxParam("crcycd","01");
			tran_grid.submitFilter();
		});
	};
	
	return {
		init : function(){
			handlerTable();
			handlerForm();
			handlerOperator();
		}
	}
}()