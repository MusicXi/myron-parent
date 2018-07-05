<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/common/taglibs.jsp"%>
<html lang="en">
<head>
    <!-- FISH -->
	<%@ include file="/common/head.fish.inc.jsp" %>
	<link rel="stylesheet" href="${'$'}{ctx}/fishdemo/styles/index.css">
    <title>${dsTable.tableName}管理</title>
    <script type="text/javascript" src="${'$'}{ctx}/common/jslibs/fish-desktop/js/fish-desktop-require.js" data-main="${dsTable.propertyTableName}"></script>
</head>
<body></body>
</html>