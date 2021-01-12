<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/common/taglib.jsp"%>
<%-- <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> --%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="${ctx}" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!--引用网络资源 <script type="text/javascript" src="http://libs.baidu.com/jquery/1.9.1/jquery.js"></script> -->
<!--不能引用web-inf下的必须是root目录下的 <script type="text/javascript" src="${ctx}/WEB-INF/global/script/jquery-1.9.1.min.js"></script> -->
<script type="text/javascript" src="${ctx}/admin/scripts/jquery/jquery-1.9.1.min.js"></script>
<title>userInfo</title>
</head>
<body>
	用户信息 昵称： ${userInfo.userna} 用户id：${userInfo.userid} 用户状态:${userInfo.status } <%-- 注册时间：
	<fmt:formatDate value="${userInfo.registerTime}" pattern="yyyy-MM-dd HH:mm:ss" />
	角色：[
	<c:forEach items="${ userInfo.acctRoles}" var="role">
	                    ${role.name }   权限[
         <c:forEach items="${ role.acctAuthorities}" var="authority">
           ${authority.name } 
         </c:forEach> ]
    </c:forEach>
	] --%>

	<br /> ajax显示全部用户信息：
	<div id="show_all_user"></div>
</body>
<script type="text/javascript">
	$.ajax({
		type : "get",
		url : "spring-mvc-maven/user/showInfos.htmls",
		dataType : "json",
		success : function(data) {
			$(data).each(
					function(i, user) {
						var p = "<p>昵称:" + user.userna + "    密码:"
								+ user.passwd + "    用户ID:"
								+ user.userid + "</p>";
						$("#show_all_user").append(p);
					});
		},
		async : true
	});
</script>
</html>