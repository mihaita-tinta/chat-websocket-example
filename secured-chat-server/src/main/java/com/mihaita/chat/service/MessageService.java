package com.mihaita.chat.service;

import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.mihaita.chat.domain.Message;

@Service
public class MessageService {
	private static final Logger log = Logger.getLogger(MessageService.class);
	private final Map<Integer, Message> messages = new ConcurrentHashMap<>();
	private static final AtomicInteger counter = new AtomicInteger();
	
	public Collection<Message> getMessages() {
		return messages.values();
	}
	
	public Message add(Message message) {
		message.setId(counter.incrementAndGet());
		message.setWhen(new Date());
		messages.put(message.getId(), message);
		
		return message;
		
	}
	
}
