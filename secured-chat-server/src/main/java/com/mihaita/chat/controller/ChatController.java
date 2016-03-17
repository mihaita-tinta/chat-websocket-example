package com.mihaita.chat.controller;

import java.security.Principal;
import java.util.Collection;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import com.mihaita.chat.domain.ChatUser;
import com.mihaita.chat.domain.Message;
import com.mihaita.chat.service.ChatUserService;
import com.mihaita.chat.service.MessageService;

@Controller
public class ChatController {
	private static final Logger log = Logger.getLogger(ChatController.class);
	
	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;
	
	@Autowired
	private ChatUserService chatUserService;

	@Autowired
	private MessageService messageService;
	@SubscribeMapping("/chat.participants")
	public Collection<ChatUser> retrieveParticipants() {
		return chatUserService.getOnlineUsers();
	}
	@SubscribeMapping("/current.user")
	public String getCurrentUser(Principal principal) {
		return principal.getName();
	}
	
	@SubscribeMapping("/chat.messages")
	public Collection<Message> get() {
		return messageService.getMessages();
	}
	/**
	 * An @SendTo annotation is not strictly required
	 * By default the message will be sent to the same destination as the incoming message but with an additional prefix ("/topic" by default). 
	 * @param headers
	 * @param message
	 * @param principal
	 * @return
	 */
	@MessageMapping("/chat.message")
	public Message filterMessage(@Headers MessageHeaders headers, @Payload Message message) {
		messageService.add(message);
		return message;
	}
	
	@MessageMapping("/chat.message/{username}")
	public Message privateMessage(@Headers MessageHeaders headers, @Payload Message message,
									@DestinationVariable("username") String username) {

		simpMessagingTemplate.convertAndSendToUser(username, "/queue/private.message", message);
		return message;
	}
	

	@MessageExceptionHandler
	public void handleProfanity(Principal principal, Exception e) {
		simpMessagingTemplate
	        .convertAndSendToUser(principal.getName(), "/queue/errors", 
	        						e);
	}
	
	
}