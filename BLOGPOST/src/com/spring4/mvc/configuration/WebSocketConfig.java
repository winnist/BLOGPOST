package com.spring4.mvc.configuration;

import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
	
	@Autowired
	ServletContext context;
	
	@Override
	public void configureMessageBroker(MessageBrokerRegistry config){
		config.enableSimpleBroker("/topic/", "/queue/");
		config.setApplicationDestinationPrefixes("/app");
		config.setUserDestinationPrefix("/secured/user");
	}
	
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry){
		//fallback options for browsers that don’t support websocket
		registry.addEndpoint("/ws").setHandshakeHandler(new DefaultHandshakeHandler(){
		public boolean beforeHandshake(
				ServerHttpRequest request,
				ServerHttpResponse response,
				WebSocketHandler wsHandler,
				Map attributes)throws Exception{			
		
			HttpSession session = ((ServletServerHttpRequest) request).getServletRequest().getSession();
			attributes.put("sessionId", session.getId());		
			return true;
		}}).withSockJS();
		
		registry.addEndpoint("/secured/room").withSockJS();
	}	
}
