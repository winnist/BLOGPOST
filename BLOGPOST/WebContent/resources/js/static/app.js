var stompClient = null;
var CONTEXT_PATH = $('#contextPathHolder').attr('data-contextPath');

function setConnected(connected){
	
	$("#connect").prop("disabled", connected);
	
	if(connected){
		$("#conversation").show();
	}else{
		$("#conversation").hide();
	}
}

function connect(){
	console.log(ctx);
	console.log(CONTEXT_PATH+"123");
	//use SockJS implementation instead of the browser's native implementation
	var socket = new SockJS(CONTEXT_PATH+'/ws');
	//constructor
	stompClient = Stomp.over(socket);
	
	//~(void) connect(headers, connectCallback) 
	//headers:optional
	stompClient.connect({}, function(frame){
		
		setConnected(true);
		console.log('connected:'+frame);
		//# (Object) subscribe(destination, callback, headers = {})
		stompClient.subscribe('/topic/public', function(msg){
			addMessage(JSON.parse(msg.body));
		});
		
	});
}


function disconnect(){
	if(stompClient !== null){
		//# (void) disconnect(disconnectCallback, headers = {})
		stompClient.disconnect();
	}
	setConnected(false);
	console.log("Disconnected");
}

function sendMessage(){
	//# (void) send(destination, headers = {}, body = '')
	console.log($('#sender').val());
	stompClient.send("/app/hello", {}, JSON.stringify({'sender': $('#sender').val(), 'content':$('#content').val()}));
	
	$('#content').val('');

}

function addMessage(messageVO){
	console.log('msg add'+ messageVO);
	var UTCTime = new Date(messageVO.sendTime);
	var timeDisplay = UTCTime.toLocaleTimeString();
	var trId = messageVO.sender+messageVO.sendTime;
	$("#msgArea").append("<tr id='"+trId+"'><td><img alt='avatar'  style='width:5vw; border-radius: 100vw'>"+messageVO.sender+":"+messageVO.content+"&nbsp&nbsp"+timeDisplay+"</td></tr>");
	
	$.ajax({
		"type":"get",
		"url":CONTEXT_PATH+"/member/getMemberPhoto",
		"data":{"memberId": messageVO.sender},
		"success":function(data){
			console.log('add msg photo'+data);
			var imgSelector = "#"+trId+" td img";
			console.log("imgSelector"+imgSelector);
			$(imgSelector).attr("src", CONTEXT_PATH + "/resources/img/photo_upload/"+data);
		}
	});	
}

$(function(){
//
//	$("form").on('submit', function(e){
//		e.preventDefault();
//	}); 
	$("#connect").click(function(){ connect();});
	$("#disconnect").click(function(){ disconnect();});
	$("#send").click(function(){ sendMessage();});
	
	
});