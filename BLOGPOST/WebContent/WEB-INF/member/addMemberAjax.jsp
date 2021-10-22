<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>新增會員*</title>
<%-- <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/error.css" /> --%>
</head>
<body>
	<h1>加入會員 ${InsertStatus}</h1>
	
		<form:form action="insert" method="POST" modelAttribute="memberVO">
			<table>
				<tr>
					<td width="10%">name:</td>
					<td width="35%"><form:input path="mname" id="mname" placeholder="Your name appears on your Profile page"/></td>
					<td width="55%"><form:errors path="mname" ></form:errors>
				</tr>
				<tr>
					<td width="10%">email:</td>
					<td width="35%"><form:input type="email" path="email" id="email"/></td>
					<td width="55%"><form:errors path="email" ></form:errors>
				</tr>
				
				<tr>
					<td width="10%">password:</td>
					<td width="35%"><form:password path="password" id="password"/></td>
					<td width="55%"><form:errors path="password" ></form:errors>
				</tr>
				
				<tr>
					<td width="10%">birthday:</td>
					<td width="35%"><form:input type="date" path="bdate" id="bdate" placeholder="格式:yyyy-MM-dd"/></td>
					<td width="55%"><form:errors path="bdate" ></form:errors>
				</tr>
			</table>		
			<input type="submit" value="註冊"/>
		</form:form>
	<hr/>
	<table>
		<c:forEach var="memberVO" items="${memberList}">
		<tr>
			<td>${memberVO.memberId}</td>
			<td>${memberVO.mname}</td>
			<td>${memberVO.email}</td>
			<td>${memberVO.bdate}</td>
			<td><button type="" value="刪除"></button></td>
		</tr>
		</c:forEach>
	</table>
</body>
</html>
