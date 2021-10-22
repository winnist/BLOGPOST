<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">


<style>

	html, body, textarea {
 	 box-sizing: border-box;
	}

	html, body {
  		margin: 0;
  		height: 100%;
	}

	

	#title {
		width:100vw;
		height:10vh;
		fontsize:10vw;
	}
	
	#content {
		width:100vw;
		height:50vh;
		fontsize:5vw;
	}
</style>
</head>
<body>
                    
<form:form action="${pageContext.request.contextPath}/post/update" method="POST" modelAttribute="postVO">     
	<form:textarea cols="50" path="title" autofocus="autofocus" placeholder="Title" id="title" value="${postVO.title}"></form:textarea>	
	<form:errors path="title"/>
	<hr>
	<form:textarea cols="50" path="content" id="content" value="${postVO.content}" placeholder="Write your post..."></form:textarea>
	<form:errors path="content"/>
	<form:hidden path="postId" value="${postVO.postId}"/>
	<form:hidden path="memberVO.memberId" value="${postVO.memberVO.memberId}"/>
	<form:errors path="memberVO"/>
	<form:hidden path="postDate" value="${postVO.postDate}"/>
	<input type="submit" value="POST" > 
</form:form>
</body>
</html>