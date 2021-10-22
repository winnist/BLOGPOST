<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	.avatar{
		width:5vw;
		height:5vh;
	}

</style>

</head>

<body>

${statusMsg}
<h1>${postVO.title}</h1>

<hr>
<article>
${postVO.content}
</article>
<hr>
<h6>${postVO.postDate}</h6>
<hr>
<div id="div_errors">
</div>
<form name="commentForm" method="POST" action="${pageContext.request.contextPath}/comment/insert">
	<textarea name="content"></textarea>
	<input type="hidden" name="postVO.postId" value="${postVO.postId}"/>
	<input type="hidden" name="memberVO.memberId" value="${sessionScope.memberId}"/>
	<input type="button" name="btn_send_comment" id="btn_send_comment" value="送出"/>
</form>
<hr>
<div id="div_comments_root">
</div>

<hr>
<a href="<%=request.getContextPath()%>/post/listAllPost/${sessionScope.curScrollingPageMemberId}">Back to PostList</a>

<!-- use jquery -->
<script src="<%=request.getContextPath()%>/resources/js/jquery-1.12.3.min.js"></script>
<script>
var contextPath = "<%=request.getContextPath()%>";
$(function(){

	$('#btn_send_comment').click(function(event) {
		event.preventDefault();
		//Try1:javascript
		//var data = new FormData(document.commentForm);
		//Try2:jquery
		// use jquery's method .serizlize()
		// encode a set of form element for submission
		var formData = $('form[name="commentForm"]').serialize();
		$.ajax({
			"type":"post",
			"url": contextPath + "/comment/insert",
			"dataType":"json",
			"data": formData,
			"success":function(data){		
				if(data.status == "SUCCESS"){
					var commentObj = data.result;
					var commentsDiv = document.getElementById('div_comments_root');
					console.log(commentObj.memberVO.photo);
					displayOneComment(commentsDiv, commentObj.content, commentObj.cTime, commentObj.memberVO, commentObj.commentId, "${sessionScope.memberId}");
					
					// clear input
					$('textarea[name="content"]').val('');
				}else{
					var errorInfo;
					for (var i = 0; i < data.result.length; i++) {
						 errorInfo = i + data.result[i].code + '<br/>';
					}
					$('#div_errors').append('<label>Please correct following errors:'+errorInfo+'</label>');
				}
			}
		});
	});
});

//use javascript

window.addEventListener("DOMContentLoaded", loadComments);
	
function loadComments(){
	var xhr = new XMLHttpRequest();
	
	if(xhr != null){
		xhr.addEventListener("readystatechange", function(){
			console.log(xhr.responseText);
			if(xhr.readyState == 4){
				if(xhr.status == 200){
					console.log(xhr.responseText);
					var commentList = JSON.parse(xhr.responseText);
					var commentsDiv = document.getElementById("div_comments_root");
					
					for(var i = 0; i<commentList.length; i++){
						displayOneComment(commentsDiv, commentList[i].content, commentList[i].cTime, commentList[i].memberVO, commentList[i].commentId, "${sessionScope.memberId}");
					}
				}
			}
		});
		xhr.open("post", contextPath + "/post/listCommentsByPostId");
		xhr.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
		xhr.send("postId="+ "${postVO.postId}");
		
	}
}

function displayOneComment(commentsDiv, content, cTime, member, commentId, userMemberId){
	
	var divCommentRoot = document.createElement("div");
	
	var textContent = document.createTextNode(content);
	var textTime = document.createTextNode(cTime);
	var textMemberId = document.createTextNode(member.memberId);
	
	
	var divComment = document.createElement("div");
	divCommentRoot.id = commentId;
	
	
	var eContent = document.createElement("textarea");
	eContent.appendChild(textContent);
	eContent.setAttribute("disabled", "disabled"); 
	eContent.id = 'context' + commentId;
	
	var eTime = document.createElement("h4");
	eTime.appendChild(textTime);
	eTime.id = "time"+commentId;
	
	var eMember= document.createElement("a");
	eMember.setAttribute("href", contextPath+"/post/listAllPost/"+member.memberId);
	var imgMember = document.createElement("img");
	imgMember.src = contextPath + "/resources/img/photo_upload/"+ member.photo;
	imgMember.className = "avatar";
	eMember.appendChild(textMemberId);
	eMember.appendChild(imgMember);
	
	var eLine = document.createElement("hr");
	
	divComment.appendChild(eContent);
	divComment.appendChild(eTime);
	divComment.appendChild(eMember);
	
	if(member.memberId == userMemberId){
		var eBtnDelete = document.createElement("button");
		eBtnDelete.addEventListener("click", deleteComment);
		eBtnDelete.setAttribute('commentId', commentId);
		eBtnDelete.innerHTML = "delete";
	
		var eBtnUpdate = document.createElement("button");
		eBtnUpdate.addEventListener("click", updateComment);
		eBtnUpdate.setAttribute('commentId', commentId);
		eBtnUpdate.innerHTML = "edit";
		
		divComment.appendChild(eBtnUpdate);
		divComment.appendChild(eBtnDelete);		
	}
	
	divCommentRoot.appendChild(divComment);
	divCommentRoot.appendChild(eLine);
	commentsDiv.appendChild(divCommentRoot);
}

function deleteComment(event){
	var divComment = event.target.parentNode;
	console.log(divComment);
	var commentId = divComment.parentNode.id;
	if(confirm('確定刪除?')){
		var xhr = new XMLHttpRequest();
		if(xhr != null){
			xhr.addEventListener("readystatechange", function() {
				if(xhr.readyState == 4){
					console.log('delete comment success'+xhr.responseText);
					if(xhr.status == 200){
						alert('刪除成功');
						console.log('show node name' + divComment.nodeName);
						//remove divCommentRoot
						divComment.parentNode.parentNode.removeChild(divComment.parentNode);
					}
				}
			});
			xhr.open("post", contextPath + "/comment/delete");
			xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;chartset=UTF-8");
			xhr.send('commentId=' + commentId);
		}
	}
}

function updateComment(event){
	//copy old
	var divComment = event.target.parentNode;
	console.log(divComment.parentNode);
	var commentId = divComment.parentNode.id;
	

	var textareaOrigin =  document.getElementById('context'+commentId);
	var textareaUpdate = textareaOrigin.cloneNode();
	textareaUpdate.removeAttribute("disabled");
	textareaUpdate.id = 'update' + textareaOrigin.id;
	//create new update area
	var divUpdate = document.createElement("div");
	
	var btnSendUpdate = document.createElement("button");
	btnSendUpdate.innerHTML = "Send";	
	btnSendUpdate.onclick = sendUpdateComment;
	
	var btnCancelUpdate = document.createElement("button");
	btnCancelUpdate.innerHTML = "Cancel";
	btnCancelUpdate.onclick = cancelUpdate;
	
	// append to root
	divUpdate.appendChild(textareaUpdate);
	divUpdate.appendChild(btnSendUpdate);
	divUpdate.appendChild(btnCancelUpdate);
	console.log(divComment.parentNode);
	divComment.parentNode.insertBefore(divUpdate, divComment);
	divComment.style.display="none";
}

function cancelUpdate(event){
	var divUpdate = event.target.parentNode;
	divUpdate.nextSibling.style.display="block";
	divUpdate.parentNode.removeChild(divUpdate);
}

function sendUpdateComment(event){
	var divUpdate = event.target.parentNode;
	var commentId = divUpdate.parentNode.id;
	var updatecontent = document.getElementById("updatecontext"+commentId).value;
	console.log(updatecontent);
	divUpdate.setAttribute('disabled', 'disabled');
	$.ajax({
		"type":"post",
		"url":contextPath + "/comment/update",
		"dataType":'json',
		"data": {"commentId":commentId, "content":updatecontent},
		"success":function(data){
			console.log('success' + data.result.content);
			if(data.status == "SUCCESS"){
				var textareaOrigin =  document.getElementById('context'+commentId);
				var timeOrigin = document.getElementById('time'+commentId);
				textareaOrigin.value = data.result.content;
				
				timeOrigin.value = data.result.cTime;
				divUpdate.nextSibling.style.display="block";
				divUpdate.parentNode.removeChild(divUpdate);
				
			}else{
				divUpdate.removeAttribute('disabled');
				var errorInfo;
				for(var i = 0; i<data.result.length; i++){
					 errorInfo = i + data.result[i].code + "<br/>";					
				}
				$('#div_errors').append("<label>請更正以下:" + errorInfo + "</label>")
				
			}
			
		}
	});
}
	

</script>

</body>

</html>