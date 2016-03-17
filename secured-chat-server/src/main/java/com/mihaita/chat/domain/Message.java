package com.mihaita.chat.domain;

import java.text.DateFormat;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Message {

	private Integer id;
	private String message;
	@JsonIgnore
	private Date when;
	private ChatUser sender;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public Date getWhen() {
		return when;
	}
	public void setWhen(Date when) {
		this.when = when;
	}

	@JsonProperty("when")
	public String getSentDate() {
		DateFormat df = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL);
		return when != null ? df.format(when) : null;
	}
	public ChatUser getSender() {
		return sender;
	}
	public void setSender(ChatUser sender) {
		this.sender = sender;
	}
	
}
