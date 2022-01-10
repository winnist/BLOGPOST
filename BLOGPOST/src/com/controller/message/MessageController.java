package com.controller.message;

import java.security.Principal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import com.model.message.MessageVO;

@Controller
public class MessageController {

	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;
	
	@MessageMapping("/hello")
	@SendTo("/topic/public")
	public MessageVO sendMessage(MessageVO messageVO){
		
		messageVO.setContent(HtmlUtils.htmlEscape(messageVO.getContent()));
		messageVO.setSendTime(new Timestamp(System.currentTimeMillis()));
		return messageVO;
	}
	
	
	@MessageMapping("/secured/room")
	public void sendSpecific(@Payload Message msg, Principal user,			
	@Header("simpSessionId")String sessionId) throws Exception{
		OutputMessage out = new OutputMessage(
		msg.getFrom(),
		msg.getText(),
		new SimpleDateFormat("HH:mm").format(new Date()));
				);
		
	}
}
