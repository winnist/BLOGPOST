package com.controller.message;

import java.sql.Timestamp;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import com.model.message.MessageVO;

@Controller
public class MessageController {

	@MessageMapping("/hello")
	@SendTo("/topic/public")
	public MessageVO sendMessage(MessageVO messageVO){
		System.out.println("topic-hello"+messageVO.getContent());
		messageVO.setContent(HtmlUtils.htmlEscape(messageVO.getContent()));
		System.out.println("topic-hello"+messageVO.getContent());
		messageVO.setSendTime(new Timestamp(System.currentTimeMillis()));
		return messageVO;
	}
	
	
}
