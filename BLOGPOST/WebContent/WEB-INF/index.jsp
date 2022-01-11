<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
<link rel="stylesheet" href="https://cdn.datatables.net/autofill/2.3.9/css/autoFill.dataTables.min.css">

</head>
<body>
<H2>Blog</H2>
<table id="myTable" class="table table-bordered table-striped table-hover" >
	<thead>
		<tr>
			<td>Title</td>
			<td>PostDate</td>
			<td>Content</td>	
			<td>Read</td>		
		</tr>
	</thead>
	<tbody>
	
	</tbody>
</table>

<table id="myTable2" class="table table-bordered table-striped table-hover" >
	<thead></thead>
	<tbody>
	
	</tbody>
</table>
<script src="${pageContext.request.contextPath}/webjars/jquery/jquery.min.js"></script>
<script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.11.3/js/jquery.dataTables.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>

<script>
var contextPath = '${pageContext.request.contextPath}';
	$(document).ready( function () {
    	$('#myTable').DataTable({
    		"ajax": {
    			"url": contextPath+"/post/getAllPosts",
    			"type": "get",   			 			
    		},
    		"columns": [
    			{ "data": "title"},
    			{ "data": "postDate"},
    			{ "data": "content"},
    			{ "data": 'postId',
    			  render: function(data, type, row){
    				 return "<input type='button' onclick='readPost(" + data + ")'  value='READ' />"; 
    			  }
    			}
    		]    		
    	});
    	
    	
//     	$.getJSON(contextPath+"/post/getAllPosts", {}, function(data){
//     		console.log(data);
//     		$('#myTable2>tbody').empty();
//     		$.each(data, function(index, value){    			
//     			console.log(index+'---'+value);    			
//     			var cell1 = $('<td></td>').text(value.title);
//     			var cell2 = $('<td></td>').text(value.content);
//     			var row = $('<tr></tr>').append([cell1, cell2]);
    			
//     			$('#myTable2>tbody').append(row);
//     		});
//     	});
	});
	
	function readPost(id){
		console.log(id);
		$.ajax({
			
			"url": contextPath+"/post/getOneURLForList/"+id,
			"type": "get",
			"success": function(val){
				console.log('success'+val);
				window.location.href=val;
			}
		});
	}
</script>

</body>
</html>