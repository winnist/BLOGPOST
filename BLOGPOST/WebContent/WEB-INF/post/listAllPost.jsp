<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<meta name="google-signin-client_id" content="794664733369-9f9rmecr9uptjtm802r2nppp4tr3l6am.apps.googleusercontent.com">

<style>
	#memberPhoto {
		width:5vw;
		height:5vh;
	}
</style>
</head>
<body>
<c:if test="${sessionScope.memberId == sessionScope.curScrollingPageMemberId}" >
	<a href="<%=request.getContextPath()%>/post/addPost">New Post</a>
</c:if>
${actionStatus}

<a href="<%=request.getContextPath() %>/member/listOneMember/${sessionScope.memberId}">Back to profile
	<img src="<%=request.getContextPath() %>/resources/img/cat.png" title="Back to profile" alt="Back to profile" height="100">
</a>

<c:if test="${not empty sessionScope.memberId}">
	<a href="#" onclick="signOut();">google Sign out</a>
</c:if>
<table>
	<c:forEach var="postVO" items="${postsByMemberId}">
		<tr>
			<td>
			<h6>${postVO.postDate}</h6>
			<h1>${postVO.title}</h1>			
			<h4>${postVO.content}</h4>	
			
			<a href="<%=request.getContextPath() %>/post/getOneForList/${postVO.postId}">read more...</a>

		<c:if test="${postVO.memberVO.memberId == sessionScope.memberId}">
			<form action="<%=request.getContextPath()%>/post/delete" method="POST">
				<input type="hidden" name="postId" value="${postVO.postId}"/>
				<input type="hidden" name="memberId" value="${postVO.memberVO.memberId}"/>
				<input type="submit" value="刪除" onclick="return confirm('確定刪除?')"/>				
			</form>
			<form action="<%=request.getContextPath()%>/post/getOneForUpdate" method="POST">
				<input type="hidden" name="postId" value="${postVO.postId}"/>
				
				<input type="submit" value="修改" onclick="return confirm('確定修改?')"/>				
			</form>
		</c:if>
			<hr>			
			</td>
		</tr>
	</c:forEach>
</table>

</body>
<script>
var contextPath = "<%=request.getContextPath()%>";
var auth2;

function onLoad() {
	gapi.load('auth2', function() {
		  auth2 = gapi.auth2.init();
	});
}

function signOut() {
 // auth2 = gapi.auth2.getAuthInstance();
  //if use google signin, then sign out google first.
  //and then invalidate session / clear sessionScope attributes on server side,
  //then back to login page.
  if(checkGoogleSignin()){
  		auth2.signOut().then(function () {
    	console.log('User google signed out.');
    	//window.location.href = contextPath;
  		});
  }
  
  invalidateSession();
}

function invalidateSession(){
	var xhrDoSignOut = new XMLHttpRequest();
	  console.log(contextPath + 'logout');
	  xhrDoSignOut.addEventListener('readystatechange', function(){
		  if(xhrDoSignOut.readyState == 4){
			  if(xhrDoSignOut.status == 200){
				  if(xhrDoSignOut.responseText == 'success'){
				  	window.location.href = contextPath;
				  }else{
					  console.log('sign out fail');
				  }
			  }
		  }
	  });
	  xhrDoSignOut.open('get', contextPath + '/logout');
	  xhrDoSignOut.send();
}

function checkGoogleSignin(){
	if(auth2.isSignedIn.get()){
		console.log('google sign in');
		return true;
	}
		
	console.log('not google sign-in');
	return false;
}


</script>

<script src="https://apis.google.com/js/platform.js?onload=onLoad" async defer></script>

</html>