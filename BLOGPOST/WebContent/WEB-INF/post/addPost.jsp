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
                    
<form:form name="postForm" action="${pageContext.request.contextPath}/post/insert" method="POST" modelAttribute="postVO">     
	<form:textarea cols="50" path="title" autofocus="autofocus" placeholder="Title" id="title" value="${postVO.title}"></form:textarea>	
	<form:errors path="title"/>
	<hr>
	<form:textarea cols="50" path="content" id="content" value="${postVO.content}" placeholder="Write your post..."></form:textarea>
	<form:errors path="content"/>
<%-- 	<form:input type="text" path="tags" id="tags" value="null" disabled="disabled"/> --%>
<!-- 	<input type="text" id="tagSearch"/> -->
<!-- 	<div id="divTags"></div> -->
	<input type="submit" value="POST"/> 
</form:form>
<a href="${pageContext.request.contextPath}/post/listAllPost/${sessionScope.memberId}">Back to Post List</a>
</body>
<script src="js/jquery-1.12.3.min.js"></script>
<script>
 var tag = null;
 var datas = null;
 var divTags = null;
 var xhr = null;
 var contextPath = "<%=request.getContextPath()%>";
 window.addEventListener("DOMContentLoaded", init);
 
 function init(){
// 	 tag = document.getElementById("tagSearch");
// 	 tag.addEventListener("keyup", searchTag);
	
// 	 divTags = document.getElementById("divTags");
 }
 
 function searchTag(event){	
	 var curText = event.target.value;
	 if(event.keycode === 13){

		$.ajax({
			"type":"post",
			"dataType":"json",
			"url": contextPath +"/tag/insert",
			"data": curText,
			"success": function(data) {
				console.log(data);
			}
		});
		
	 
	 console.log('searchtag');
	
	 
	divTags.innerHTML = "";
	 if(curText.length > 0){
		 xhr = new XMLHttpRequest();
		 if(xhr != null){
			 xhr.addEventListener("readystatechange", callback);
			 xhr.open("get", contextPath + "/tag/findByName/" + curText);	
			 xhr.send();
		 }
	 }
 }
 
 function callback(){	
	 console.log('callback');
	
	 if(xhr.readyState == 4){
		if(xhr.status == 200){	
			
// 			datas = JSON.parse(xhr.responseText);
// 			for(var i = 0; i<datas.length; i++){
// 				var eTag = document.createElement("label");
// 				var tTag = document.createTextNode(datas[i].tagName);
// 				eTag.appendChild(tTag);
// 				eTag.addEventListener("click", function(){
// 					console.log("form tags"+ document.postForm.tags.value.length);
// 					var tagArray = document.postForm.tags.value.split(",");
// 					if()
// 					tagArray.push(this.firstChild.nodeValue);
// 					document.postForm.tags.value = tagArray.toString();
// 				});
				
// 				var eLine = document.createElement("hr");
// 				divTags.appendChild(eTag);
// 				divTags.appendChild(eLine);
				
			}
		}
	}		
 }
 
 
</script>
</html>