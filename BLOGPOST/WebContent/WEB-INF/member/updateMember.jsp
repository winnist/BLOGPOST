<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<style>
	.avatarWidth {
		width: 100px; 
		border-radius: 10px;
	}
	
	.
</style>
</head>

<body>
${actionStatus}
<hr>
<img src="${pageContext.request.contextPath}/resources/img/photo_upload/${memberVO.photo}" class="avatarWidth">

<form:form action="${pageContext.request.contextPath}/member/update" method="post"  enctype="multipart/form-data" modelAttribute="memberVO">
	名字:<form:input path="mname" value="${memberVO.mname}"/>
		<form:errors path="mname"/>
	Email:<form:input path="email" value="${memberVO.email}"  readonly="true" /> 
		<form:errors path="email"/>
	生日:<form:input type="text" path="bdate"  placeholder="格式:yyyy-MM-dd"/>
		<form:errors path="bdate"/>
		<form:input type="hidden" path="memberId" value="${memberVO.memberId}"/>
		<form:input type="hidden" path="photo" value="${memberVO.photo}"/>
	Icon:
		
	
  
<%--   	<form action="${pageContext.request.contextPath}/resource/uploadImg" method="post" > --%>
        <input type="file" name="image" id="image"  accept="image/*"/>
        <img id="avatar" height="100"/>
<!--   		<input type="submit" value="上傳" id="btnUploadImg"> -->
<%--   	</form> --%>
	<input type="submit" value="修改" onclick="return confirm('確定更新?'+${memberVO.memberId});"/>
</form:form>
<hr>
<script>
var ePhoto = document.getElementById("image");
ePhoto.addEventListener("change", handleFile, false);
function handleFile() {	
	var imgAvatar = document.getElementById("avatar");
	// event.target.file[0]
	imgAvatar.src = URL.createObjectURL(this.files[0]);
	imgAvatar.height = 60;
	imgAvatar.onload = function() {
		//argument could put ---this or imgAvatar
		URL.revokeObjectURL(this.src);
	}	
};
</script>
</body>
</html>