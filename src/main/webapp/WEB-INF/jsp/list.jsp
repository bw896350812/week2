<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<table>
	<tr>
		<td>编号</td>
		<td>商品名称</td>
		<td>价格</td>
		<td>售出比例</td>
	</tr>
	<c:forEach items="${goodsList }" var="goods" >
	<tr>
		<td>${goods.gid}</td>
		<td>${goods.gname }</td>
		<td>${goods.price }</td>
		<td>${goods.percent }</td>
	</tr>
	</c:forEach>

	<tr>
		<td colspan="5">
			<a href="list?cpage=1">首页</a>
			<a href="list?cpage=${cpage-1 }">上一页</a>
			<a href="list?cpage=${cpage+1 }">下一页</a>
			<a href="list?cpage=${pages }">尾页</a>
		</td>
	</tr>
</table>
</body>
</html>