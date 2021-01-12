<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/common/taglib.jsp"%>
<div class="col-md-6">
	<div class="portlet light">
		<div class="portlet-title">
			<div class="caption">
				<i class="fa fa-cogs font-green-sharp"></i> <span
					class="caption-subject font-green-sharp bold uppercase">菜单管理</span>
			</div>
			<div class="inputs">
				<div class="portlet-input input-inline input-medium ">
					<div class="input-icon right">
						<i class="icon-magnifier"></i> <input type="text"  placeholder="菜单名称查询" 
							class="form-control form-control-solid" id="selectSysAuth">
					</div>
				</div>
			</div>
		</div>
		<div class="portlet-body">
		<div class="row">
				<div class="col-md-9">
					<button type="button" id="add_main_menu" class="btn blue">新增主菜单</button>
				</div>
			</div>
			<div id="sys_auth_tree" class="tree-demo"></div>			
		</div>
	</div>
</div>
<div class="col-md-6">
	<div class="portlet light">
		<div class="portlet-title">
			<div class="caption">
				<i class="fa fa-cogs font-green-sharp"></i> <span
					class="caption-subject font-green-sharp bold uppercase">菜单详细信息</span>
			</div>
		</div>
		<div class="portlet-body" id="sysAuthForm">
			<form class="form-horizontal" role="form" id="update_form">
				<div class="form-body">
					<div class="alert alert-danger display-hide">
						<button class="close" data-close="alert"></button>
						输入有误,请检查下面表单！<span class="msg"></span>
					</div>
					<div class="alert alert-success display-hide">
						<button class="close" data-close="alert"></button>
						表单提交成功！<span class="msg"></span>
					</div>
					<div class="form-group">
						<label class="col-md-3 control-label">菜单编号</label>
						<div class="col-md-9">
							<input type="text" class="form-control" readOnly maxlength="19"
								placeholder="唯一标识菜单编号" id="auth_cd" name="auth_cd">
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-3 control-label">菜单名称</label>
						<div class="col-md-9">
							<input type="text" class="form-control" maxlength="50"
								placeholder="菜单名称" id="menu_name" name="menu_name">
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-md-3">菜单层级</label>
						<div class="col-md-9">
							<input type="text" class="form-control" maxlength="1" readOnly
								placeholder="菜单层级" id="rank" name="rank">
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-md-3">父级</label>
						<div class="col-md-9">
							<input type="text" class="form-control" maxlength="19" readOnly
								placeholder="父级菜单编号" id="parent_auth_cd" name="parent_auth_cd">
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-3 control-label">菜单图标</label>
						<div class="col-md-9">
							<input type="text" class="form-control" maxlength="19"
								placeholder="Example:fa fa-th" id="iconfg" name="iconfg">
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-3 control-label">菜单url</label>
						<div class="col-md-9">
							<input type="text" class="form-control" maxlength="100"
								placeholder="Example:/cust/period" id="auth_url" name="auth_url">
						</div>
					</div>
				</div>
				<div class="form-actions">
					<div class="row">
						<div class="col-md-offset-3 col-md-9">
							<button type="button" id="update_button" class="btn blue">保存</button>
							<button type="button" id="clear_update" class="btn red">清空</button>
						</div>
					</div>
				</div>
			</form>
		</div>
	</div>
</div>
<!-- edit modal -->
<div id="myModal" class="modal fade" tabindex="-1"
	data-backdrop="static" data-keyboard="false">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal"
			aria-hidden="true"></button>
		<i class="fa fa-cogs font-green-sharp"></i> <span
			class="caption-subject font-green-sharp bold uppercase">新增菜单详情</span>
	</div>
	<div class="modal-body">
		<div class="row">
			<div class="col-md-12">
				<form class="form-horizontal" role="form" id="myForm">
					<div class="form-body">
						<div class="alert alert-danger display-hide">
							<button class="close" data-close="alert"></button>
							输入有误,请检查下面表单！返回信息 ：<span class="msg"></span>
						</div>
						<div class="alert alert-success display-hide">
							<button class="close" data-close="alert"></button>
							表单提交成功！返回信息 ：<span class="msg"></span>
						</div>
						<div class="form-group">
							<label class="col-md-3 control-label">菜单编号</label>
							<div class="col-md-9">
								<div>
									<input type="text" id="add_auth_cd" name="auth_cd"
										class="form-control input-medium" readOnly maxlength="19"
										placeholder="输入编号" />

								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-3 control-label">菜单名称</label>
							<div class="col-md-9">
								<input type="text" class="form-control  input-medium" maxlength="19"
									placeholder="输入菜单名称" id="add_menu_name" name="menu_name" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-3 control-label">菜单层级</label>
							<div class="col-md-9">
								<input type="text" readOnly class="form-control  input-medium" maxlength="19" 
									placeholder="菜单层级" id="add_rank" name="rank" />
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-md-3">父级</label>
							<div class="col-md-9">
								<input type="text" class="form-control input-medium" readOnly
									placeholder="输入父级" id="add_parent_auth_cd" name="parent_auth_cd" />
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-md-3">菜单图标</label>
							<div class="col-md-9">
								<input type="text" class="form-control input-medium"
									placeholder="Example:fa fa-th" name="iconfg">
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-3 control-label">菜单url</label>
							<div class="col-md-9">
								<input type="text" class="form-control input-medium" 
								placeholder="Example:/cust/period" name="auth_url">
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-3 control-label">菜单排序</label>
							<div class="col-md-9">
								<input type="text" class="form-control input-medium" placeholder="输入菜单排序"
									 id="add_sortno" name="sortno">
							</div>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	<div class="modal-footer">
		<button type="button" data-dismiss="modal" class="btn btn-default">关闭</button>
		<button type="button" class="btn blue" id="btn_save_edit">保存</button>
	</div>
</div>
<script src="${ctx}/pages/system/meun.js"></script>
<script>
	jQuery(document).ready(function() {
		TheMeun.init();
	});
</script>