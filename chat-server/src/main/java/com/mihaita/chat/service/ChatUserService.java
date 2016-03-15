package com.mihaita.chat.service;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.mihaita.chat.domain.ChatUser;

@Service
public class ChatUserService {
	private static final Logger log = Logger.getLogger(ChatUserService.class);

	private final Map<String, ChatUser> onlineUsers = new ConcurrentHashMap<>();
	
	public Collection<ChatUser> getOnlineUsers() {
		return onlineUsers.values();
	}
	
	public ChatUser get(String sessionId) {
		return onlineUsers.get(sessionId);
	}
	
	public void login(String sessionId, ChatUser user) {
		onlineUsers.put(sessionId, user);
	}
	
	public void logout(String sessionId) {
		onlineUsers.remove(sessionId);
	}
	
}
