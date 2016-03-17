package com.mihaita.chat.service;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.mihaita.chat.domain.ChatUser;

@Service
public class PresenceEventListener {
	private static final Logger log = Logger.getLogger(PresenceEventListener.class);
	private static final String LOGIN = "/topic/chat.login";
	private static final String LOGOUT = "/topic/chat.logout";
		
	private SimpMessagingTemplate messagingTemplate;
	
	private final String loginDestination = LOGIN;
	
	private final String logoutDestination = LOGOUT;
	private final ChatUserService userService;
	
	@Autowired
	public PresenceEventListener(SimpMessagingTemplate messagingTemplate, ChatUserService userService) {
		this.messagingTemplate = messagingTemplate;
		this.userService = userService;
	}
		
	@EventListener
	private void handleSessionConnected(SessionConnectEvent event) {
		
		SimpMessageHeaderAccessor headers = SimpMessageHeaderAccessor.wrap(event.getMessage());
		String username = headers.getUser().getName();

		ChatUser user = new ChatUser();
		user.setLastLogin(new Date());
		user.setUsername(username);
		
		messagingTemplate.convertAndSend(loginDestination, user);
		
		userService.login(headers.getSessionId(), user);
	}
	
	@EventListener
	private void handleSessionDisconnect(SessionDisconnectEvent event) {

		ChatUser user = userService.get(event.getSessionId());
		if (user != null) {
			messagingTemplate.convertAndSend(logoutDestination, user);
			userService.logout(event.getSessionId());
		}
	}
}
