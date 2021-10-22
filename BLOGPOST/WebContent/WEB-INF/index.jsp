<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>



<!DOCTYPE html>
<html>
<head>

<title>Insert title here</title>
<meta name="google-signin-client_id" content="xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx">
</head>

${memberVO.memberId}
<body>
<h1>${loginMsg}</h1>
${message}
<div class="g-signin2" data-onsuccess="onSignIn"></div>

<form action="${pageContext.request.contextPath}/login" method="POST">
	<table>
		<tr>
			<td width="10%">帳號:</td>
			<td width="55%"><input type="email" name="email" id="email"/></td>
		</tr>
		<tr>
			<td width="10%">密碼:</td>
			<td width="50%"><input type="password" name="password" id="password" placeholder="請輸入密碼"/></td>
	
		</tr>
	
	</table>
	
		<input type="submit" value="登入" />
		
		<hr>
		<a href="<%=request.getContextPath()%>/member/addMember">註冊</a>
</form>

<c:if test="{not empty sessionScope.memberId}">
<a href="#" onclick="signOut();">Sign out</a>
</c:if>


</body>
<script>
var contextPath = '<%=request.getContextPath()%>';
function onSignIn(googleUser) {
	var profile = googleUser.getBasicProfile();
	console.log('ID: ' + profile.getId()); // Do not send to your backend! Use an ID token instead.
	console.log('Name: ' + profile.getName());
	console.log('Image URL: ' + profile.getImageUrl());
	console.log('Email: ' + profile.getEmail()); // This is null if the 'email' scope is not present.
	var id_token = googleUser.getAuthResponse().id_token;
	var xhr = new XMLHttpRequest();
	xhr.open('POST',contextPath+'/signInGoogle');
	xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
	xhr.onload = function(){
		console.log(xhr.responseText);
		if(xhr.responseText != null){
			window.location.href = contextPath + "/"+ xhr.responseText;
		}
	};
	xhr.send('idtoken='+id_token);
}

function checkIfLogin(){
	if(auth2.isSignedIn.get()){
		console.log('google sign in');
	}else{
		console.log('not google sign-in');
	}
}
	
function signOut() {
    var auth2 = gapi.auth2.getAuthInstance();
    auth2.signOut().then(function () {
      console.log('User signed out.');
    });
  }
</script>
<script src="https://apis.google.com/js/platform.js?onload=init" async defer></script>
</html>