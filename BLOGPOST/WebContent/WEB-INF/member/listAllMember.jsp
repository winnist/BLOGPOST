<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<title></title>
</head>
<body>
	<table>
		<c:forEach var="memberVO" items="${memberList}">
		<tr>
			<td>${memberVO.memberId}</td>
			<td>${memberVO.mname}</td>
			<td>${memberVO.email}</td>
			<td>${memberVO.bdate}</td>
			<td>
				<form method="post" action="<%=request.getContextPath() %>/member/delete">
					<input type="hidden" name="memberId" value="${memberVO.memberId}"/>
					<input type="submit" value="刪除" onclick="return confirm('確定刪除此會員?'+${memberVO.memberId});"/>			
				</form>
			</td>
			<td>
				<form method="post" action="<%=request.getContextPath() %>/member/getOneForUpdate">
					<input type="hidden" name="memberId" value="${memberVO.memberId}"/>
					<input type="submit" value="更新" onclick="return confirm('確定更新此會員?'+${memberVO.memberId});" />
				</form>
			</td>
		</tr>
		</c:forEach>
	</table>


</body>
</html>