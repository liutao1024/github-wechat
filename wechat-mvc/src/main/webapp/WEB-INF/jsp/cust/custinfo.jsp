<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/common/taglib.jsp"%>
<div class="col-md-12">
	<div class="portlet light">
		<div class="portlet-title">
			<div class="caption">
				<i class="fa fa-user font-green-sharp"></i>
				<span class="caption-subject font-green-sharp bold uppercase">客户信息</span>
				<span class="caption-helper">客户基本信息...</span>
			</div>
		</div>
		<div class="portlet-body">
			<form class="form-horizontal cust-form" id="cust-form" role="form">
				<div class="alert alert-danger display-hide">
					<button class="close" data-close="alert"></button>
					<span>输入交易参数有误</span>
				</div>
				<div class="form-group col-md-6">
					<label class="col-md-3 control-label">客户帐号</label>
					<div class="input-icon col-md-9">
						<i class="fa fa-credit-card"></i>
						<input type="text" id="custno" name="custno" class="form-control input-inline input-medium" placeholder="客户帐号">
					</div>
				</div>
				<div class="form-group col-md-6">
					<label class="col-md-3 control-label">客户名称</label>
					<div class="input-icon col-md-9">
						<i class="fa fa-book"></i>
						<input type="text" id="custna" name="custna" class="form-control input-inline input-medium" placeholder="客户名称">
					</div>
				</div>
				<div class="form-group col-md-6">
					<label class="col-md-3 control-label">证件类型</label>
					<div class="input-icon col-md-9">
						<!-- <i class="fa fa-credit-card"></i> -->
						<input type="text" id="idtftp" name="idtftp" class="form-control input-inline input-medium" placeholder="证件类型">
					</div>
				</div>
				<div class="form-group col-md-6">
					<label class="col-md-3 control-label">证件号码</label>
					<div class="input-icon col-md-9">
						<i class="fa fa-book"></i>
						<input type="text" id="idtfno" name="idtfno" class="form-control input-inline input-medium" placeholder="证件号码">
					</div>
				</div>
				<div class="cif-clear"></div>
				<div class="form-actions cust-action">
					<button type="button" class="btn blue" id="submit">查询</button>
					<button type="button" class="btn gray" id="cancle">清空</button>
				</div>
				<div class="cif-pp"></div>
			</form>
			 <div class="table-container">
				<table class="table table-striped table-bordered table-hover" id="cif_ajax" style="white-space: nowrap;">
					<thead>
						<tr role="row" class="heading">
							<th width="2%"></th>	   		
							<th width="9%">客户号</th>		
							<th width="9%">客户名称</th>
							<th width="9%">证件类型</th>
							<th width="9%">证件号码</th>													
							<th width="9%">性别</th>													
							<th width="9%">手机号码</th>
							<th width="9%">地址</th>
							<th width="9%">账户状态</th>
							<th width="9%">开户日期</th>
							<th width="15%" colspan="3">操作</th>
						</tr>
					</thead>
					<tbody>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>
<!-- 修改操作弹窗 -->
<div id="editModal" class="modal fade" tabindex="-1" data-backdrop="static" data-keyboard="false" >
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
		<h4 class="modal-title">客户信息修改</h4>
	</div>
	<div class="modal-body">
		<div class="row">
			<div class="col-md-12">
				<form class="form-horizontal mod-form" id="mod-form" role="form">
					<div class="alert alert-danger display-hide">
						<button class="close" data-close="alert"></button>
						<span>输入交易参数有误</span>
					</div>
					<div class="form-group">
						<label class="col-md-3 control-label">客户帐号</label>
						<div class="input-icon col-md-9">
							<i class="fa fa-credit-card"></i>
							<input type="text" id="e_custno" name="e_custno" class="form-control input-inline input-medium" readOnly>
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-3 control-label">客户名称</label>
						<div class="col-md-9">
							<input type="text" id="e_custna" name="e_custna" class="form-control input-inline input-medium" placeholder="客户名称" >
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-3 control-label">证件类型</label>
						<div class="input-icon col-md-9">
							<input type="hidden" id="e_idtftp" name="e_idtftp" class="form-control input-inline input-medium" placeholder="证件类型" readonly="readonly"/>
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-3 control-label">证件号码</label>
						<div class="input-icon col-md-9">
							<i class="fa fa-credit-card"></i>
							<input type="text" id="e_idtfno" name="e_idtfno" class="form-control input-inline input-medium" placeholder="证件号码" readOnly/>
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-3 control-label">性别</label>
						<div class="input-icon col-md-9">
							<input type="hidden" id="e_sextyp" name="e_sextyp" class="form-control input-inline input-medium" placeholder="性别" readonly="readonly"/>
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-3 control-label">手机号</label>
						<div class="col-md-9">
							<input type="text" id="e_teleno" name="e_teleno" class="form-control input-inline input-medium" placeholder="手机号"/>
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-3 control-label">地址</label>
						<div class="col-md-9">
							<input type="text" id="e_addres" name="e_addres" class="form-control input-inline input-medium" placeholder="地址"/>
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-3 control-label">账户状态</label>
						<div class="input-icon col-md-9">
							<input type="hidden" id="e_custst" name="e_custst" class="form-control input-inline input-medium" placeholder="账户状态"/>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	<div class="modal-footer">
		<button type="button" data-dismiss="modal" class="btn btn-default">关闭</button>
		<button type="button" id="e_save" class="btn blue">保存</button>
	</div>
</div>
<!-- 关联产品弹窗 -->
<div id="releInfoModal" class="modal fade bs-modal-sm" tabindex="-1" data-backdrop="static" data-keyboard="false" data-width = "700">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
			<h4 class="modal-title">关联产品信息</h4>
		</div>
		<div class="modal-body">
			<table id="releInfo" class="table table-striped table-bordered table-hover"></table>
		</div>
		<div class="modal-footer">
			<button type="button" data-dismiss="modal" class="btn btn-default">关闭</button>
		</div>
</div>
<!-- 绑卡 -->
<div id="outCaModal" class="modal fade out" tabindex="-1" data-backdrop="static" data-keyboard="false" data-width="960">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
			<h4 class="modal-title">绑定卡信息</h4>
			<div class=" col-md-12"></div>
		</div>
		<div class="modal-body" id="edit_load">
		</div>
		<div class="modal-footer col-md-12" id="btn_cont">
		    <button type='button'  class='btn btn-default closeModal' data-dismiss="modal">关闭</button>
		</div>
</div>
	
<div id="myModal" class="modal fade bs-modal-sm" tabindex="-1" data-backdrop="static" data-keyboard="false" data-width = "300">
	<div class="modal-body">
		<img alt="" src="${ctx}/admin/images/global/img/loading-spinner-grey.gif"><span>处理中</span>
	</div>
</div>
<!-- 交易信息查询弹窗 -->
<div id="tranModal" class="modal fade bs-modal-sm" tabindex="-1" data-backdrop="static" data-keyboard="false" data-width = "1200">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
		<h4 class="modal-title">客户交易明细</h4>
	</div>
	<div class="modal-body">
		<form class="form-horizontal cust-tran-form" id="cust-tran-form" role="form">
				<div class="alert alert-danger display-hide">
					<button class="close" data-close="alert"></button>
					<span>输入交易参数有误</span>
				</div>
				<div class="form-group col-md-5">
					<label class="control-label col-md-3">帐号</label>
					<div class="input-icon col-md-9">
						<i class="fa fa-credit-card"></i>
						<input type="text" id="tran_custac" name="tran_custac" class="form-control input-inline input-medium" placeholder="输入电子帐号" readOnly>
					</div>
				</div>
				<div class="form-group col-md-5">
					<label class="control-label col-md-4">记账日期</label>
					<div class="input-icon col-md-8">
						<div class="input-group input-large date-picker input-daterange"  data-date-format="yyyymmdd">
							<input type="text" class="form-control" id="trandt_from" name="from" placeholder="输入起始日期">
							<span class="input-group-addon">
							到 </span>
							<input type="text" class="form-control" id="trandt_to" name="to" placeholder="输入结束日期">
						</div>
					</div>
				</div>
				<div class="cif-clear"></div>
				<div class="form-actions cust-action">
					<button type="button" class="btn blue" id="tran_qry">查询</button>
					<button type="button" class="btn gray" id="tran_cancle">清空</button>
				</div>
				<div class="cif-pp"></div>
			</form>
		<div class="table-container">
				<table class="table table-striped table-bordered table-hover" id="cif_tran_ajax">
					<thead>
						<tr role="row" class="heading">
							<th width="8%">记账日期</th>	
							<th width="8%">交易时间</th>
							<th width="12%">平台交易时间</th>
							<th width="8%">交易金额</th>													
							<th width="6%">交易渠道</th>
							<th width="8%">可用余额</th>
							<th width="8%">摘要描述</th>
							<th width="12%">流水号</th>
							<th width="8%">资金流动类型</th>
							<th width="6%">借贷标志</th>
							<th width="6%">交易标志</th>
							<th width="8%">交易名称</th>	
						</tr>
					</thead>
					<tbody>
					</tbody>
				</table>
			</div>
	</div>
	<div class="modal-footer">
		<button type="button" data-dismiss="modal" class="btn btn-default">关闭</button>
	</div>
</div>

<script src="${ctx}/admin/scripts/jquery-validation/js/acdInput.js" type="text/javascript"></script>
<script src="${ctx}/admin/styles/bootstrap-datepicker/js/bootstrap-datepicker.js" type="text/javascript" ></script>
<script src="${ctx}/admin/styles/bootstrap-datepicker/js/locales/bootstrap-datepicker.zh-CN.js" type="text/javascript" ></script>
<script src="${ctx}/pages/cust/custinfo.js"></script>
<script>
	jQuery(document).ready(function() {    
		Custinfo.init();
	});
</script>