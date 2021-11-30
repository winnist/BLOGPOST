package com.websocket;

import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.Session;

import javax.websocket.server.ServerEndpoint;

//@ServerEndpoint(value="/chat/{memberId}")
public class ChatEndpoint {

	private Session session;
	
	private static Set<ChatEndpoint> chatEndpoints
	 = new CopyOnWriteArraySet<>();
	
	private static HashMap<String, String> users = new HashMap<>();
	
	public void onOpen(Session session){
		
	}
			
}
