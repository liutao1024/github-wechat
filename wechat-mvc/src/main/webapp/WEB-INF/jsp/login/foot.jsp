<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%	//通过Java代码获取当前日期,并展示当前日期 
	Date date = new Date();
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd");
	String dateString = simpleDateFormat.format(date);
%>
<%=dateString%> &copy; Bank综合管理系统   刘涛 技术支持
