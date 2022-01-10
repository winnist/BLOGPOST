<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Profile</title>
<link href="${pageContext.request.contextPath}/webjars/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<script src="${pageContext.request.contextPath}/webjars/jquery/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/webjars/sockjs-client/sockjs.min.js"></script>
<script src="${pageContext.request.contextPath}/webjars/stomp-websocket/stomp.min.js"></script>

 <link id="contextPathHolder" data-contextPath="${pageContext.request.contextPath}"/>
 <script>
 	var ctx = '${pageContext.request.contextPath}' 
 </script>
<script src="${pageContext.request.contextPath}/resources/js/static/app.js" type="text/javascript"></script>
</head>
<body>
${actionStatus}
<hr>
Profile
<img alt="avatar" src="${pageContext.request.contextPath}/resources/img/photo_upload/${memberVO.photo}" height="100"/>
姓名:${memberVO.mname}
E-mail:${memberVO.email}
生日:${memberVO.bdate}
<a href="mailto:${memberVO.email}">寄信</a>
<c:choose>
<c:when test="${sessionScope.memberId == memberVO.memberId}">
<form method="POST" action="<%=request.getContextPath() %>/member/getOneForUpdate">
	<input type="hidden" name="memberId" value="${memberVO.memberId}">
	<input type="submit" value="修改" onclick="return confirm('確定修改?')">
</form>
<form method="POST" action="<%=request.getContextPath() %>/member/delete">
	<input type="hidden" name="memberId" value="${memberVO.memberId}">
	<input type="submit" value="刪除" onclick="return confirm('確定永久刪除會員? 所有文章也會一同刪除?')">
</form>
</c:when>
<c:otherwise>
 <button id="connect" class="btn btn-default" type="button">
 發送訊息</button>


</c:otherwise>
</c:choose>
<div id="conversation" class="row"  style="display:none">
	<div class="col-md-12">
		<table class="table table-striped">
			<thead>
				<tr><th scope="col">${memberVO.mname}</th></tr>
			</thead>
			<tbody id="msgArea"></tbody>
		</table>
		
	<form  class="col-md-6">
		<div class="form-group">
			<input type="hidden" id="sender" name="sender"  class="form-control" value="${sessionScope.memberId}">
			<input type="text" id="content" name="content">		
			<button id="send" class="btn btn-default" type="button">Send</button>
		</div>
		<div><button id="disconnect" class="btn btn-default" type="button">Disconnect</button></div>
	</form>
	</div>
</div>


<a href="<%=request.getContextPath()%>/post/listAllPost/${memberVO.memberId}">Back to posts</a>
</body>
</html>