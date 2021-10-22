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
<h1>${message}</h1>
	<h1>加入會員 ${actionStatus}</h1>
<!-- 	After spring version 5, commandName was removed,  should use modelAttribute -->
		<form:form action="${pageContext.request.contextPath}/member/insert" method="POST" modelAttribute="memberVO">
			<table>
			<form:errors path="*" cssClass="errorblock" element="div" />
				<tr>
					<td width="10%">name:</td>
					<td width="35%"><form:input type="text" path="mname" id="mname" placeholder="Your name appears on your Profile page"/></td>
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
					<td width="35%"><form:input type="text" path="bdate" id="bdate" placeholder="格式:yyyy-MM-dd"/></td>
					<td width="55%"><form:errors path="bdate" cssClass="error" ></form:errors>
				</tr>
<!-- 				<tr> -->
<!-- 					<td> -->
<%-- 						<form:select path="mname" items="${memberList}" itemValue="memberId" itemLabel="email"/> --%>
					
<!-- 					</td> -->
<!-- 				</tr> -->
			</table>		
			<input type="submit" value="註冊"/>
		</form:form>
	<hr/>
	<table>
<%-- 		<c:forEach var="memberVO" items="${memberList}"> --%>
<!-- 		<tr> -->
<%-- 			<td>${memberVO.memberId}</td> --%>
<%-- 			<td>${memberVO.mname}</td> --%>
<%-- 			<td>${memberVO.email}</td> --%>
<%-- 			<td>${memberVO.bdate}</td> --%>
<!-- 			<td> -->
<%-- 				<form method="post" action="<%=request.getContextPath() %>/member/delete"> --%>
<%-- 					<input type="hidden" name="memberId" value="${memberVO.memberId}"/> --%>
<%-- 					<input type="submit" value="刪除" onclick="return confirm('確定刪除此會員?'+${memberVO.memberId});"/>			 --%>
<%-- 				</form> --%>
<!-- 			</td> -->
<!-- 			<td> -->
<%-- 				<form method="post" action="<%=request.getContextPath() %>/member/getOneForUpdate"> --%>
<%-- 					<input type="hidden" name="memberId" value="${memberVO.memberId}"/> --%>
<%-- 					<input type="submit" value="更新" onclick="return confirm('確定更新此會員?'+${memberVO.memberId});" /> --%>
<%-- 				</form> --%>
<!-- 			</td> -->
<!-- 		</tr> -->
<%-- 		</c:forEach> --%>
	</table>

<div id="displayDiv">
</div>
<script>
 var xhr;
 var contexPath = "<%=request.getContextPath()%>";
	/*window.addEventListener("load", LoadAllMember);
	window.addEventListener("load", LoadAllMember2);
	
	var displayDiv = document.getElementById("displayDiv");
	
	function LoadAllMember() {
		xhr = new XMLHttpRequest();
		if(xhr != null){
			
			xhr.addEventListener("readystatechange", callback);
			xhr.open("get", contexPath + "/member/getAllMemberJsonStr");
			xhr.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
			xhr.send();
		}else{
			alert("您的瀏覽器不支援Ajax功能!!");
		}
	}
	
	function callback(){
		if(xhr.readyState == 4){
			if(xhr.status == 200){
				var datas = xhr.responseText;
				console.log("MemberLIST"+datas);
					                
				var memberList = JSON.parse(datas);
				console.log("MemberLIST"+memberList);
				for(var i=0; i<memberList.length; i++){
						var mnameText = memberList[i].mname;
						var emailText = memberList[i].email;
						
						var tMname = document.createTextNode(mnameText);
						var tEmail = document.createTextNode(emailText);
						
						var eMname = document.createElement("h3")
						eMname.appendChild(tMname);
 						
						var eEmail = document.createElement("h3")
						eEmail.appendChild(tEmail);
						
						var eLine = document.createElement("hr");
						
						var btnDelete = document.createElement("button");
						btnDelete.addEventListener("click", doDelete);
						btnDelete.id = memberList[i].memberId;
						btnDelete.innerHTML = "delete";
						
						var btnUpdate = document.createElement("button");
						btnUpdate.addEventListener("click", doUpdate);
						btnUpdate.id = memberList[i].memberId;
						btnUpdate.innerHTML = "edit";
						
						displayDiv.appendChild(eMname);
						displayDiv.appendChild(eEmail);
						displayDiv.appendChild(eLine);
						displayDiv.appendChild(btnDelete);
						displayDiv.appendChild(btnUpdate);
						
				}
			}
		}
	}
	
	function doDelete(){
		if(confirm("是否確定要刪除"+this.id)){
			var xhrDoDelete = new XMLHttpRequest();
			xhrDoDelete.addEventListener("readystatechange", function() {
				if(xhrDoDelete.readyState == 4){
					console.log("delete:"+xhrDoDelete.status+"----"+xhrDoDelete.responseText);
					if(xhrDoDelete.status == 200){
						window.location.href = xhrDoDelete.responseText;
					}
				}
			});			
			xhrDoDelete.open("POST", contexPath + "/member/delete");
			xhrDoDelete.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
		
			xhrDoDelete.send("memberId="+this.id);
		}
	}
	
	function doUpdate(){
		if(confirm("是否確定要修改會員資料?")+this.id){
			var xhrDoUpdate = new XMLHttpRequest();
			xhrDoUpdate.addEventListener("readystatechange", function() {
				console.log("update:"+xhrDoUpdate.readystate+"--"+xhrDoUpdate.status+"---"+xhrDoUpdate.responseText);
				if(xhrDoUpdate.readyState == 4){
					
					if(xhrDoUpdate.status == 200){
						
					
						window.location.href = xhrDoUpdate.responseText;
						
					}
				}
			});
			xhrDoUpdate.open("POST", contexPath + "/member/getOneForUpdate");
			xhrDoUpdate.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
			console.log(this.id);
			xhrDoUpdate.send("memberId="+this.id);
		}
	}
	
	function LoadAllMember2() {
		var xhrMemberList = new XMLHttpRequest();
	
		if(xhrMemberList != null){
			xhrMemberList.addEventListener("readystatechange", function(){
				if(xhrMemberList.readyState == 4){
					if(xhrMemberList.status == 200){
						var datas = xhrMemberList.responseText;
						console.log("str2"+datas);
					}
				}
			});
			
			xhrMemberList.open("get", contexPath + "/member/getAllMemberJsonStr2");
			xhrMemberList.send();
		}else{
			alert();
		}
	}
	*/
	
</script>
</body>
</html>
