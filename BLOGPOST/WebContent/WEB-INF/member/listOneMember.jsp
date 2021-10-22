<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
${actionStatus}
<hr>
Profile
<img alt="avatar" src="${pageContext.request.contextPath}/resources/img/photo_upload/${memberVO.photo}" height="100"/>
姓名:${memberVO.mname}
E-mail:${memberVO.email}
生日:${memberVO.bdate}
<form method="POST" action="<%=request.getContextPath() %>/member/getOneForUpdate">
	<input type="hidden" name="memberId" value="${memberVO.memberId}">
	<input type="submit" value="修改" onclick="return confirm('確定修改?')">
</form>
<form method="POST" action="<%=request.getContextPath() %>/member/delete">
	<input type="hidden" name="memberId" value="${memberVO.memberId}">
	<input type="submit" value="刪除" onclick="return confirm('確定永久刪除會員? 所有文章也會一同刪除?')">
</form>
<a href="<%=request.getContextPath()%>/post/listAllPost/${memberVO.memberId}">Back to posts</a>
</body>
</html>