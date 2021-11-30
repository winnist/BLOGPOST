package com.model.message;

import java.sql.Timestamp;

import javax.persistence.Entity;


public class MessageVO {
	//memberVO's variable memberId
	private Integer sender;
	private String content;
	public Timestamp sendTime;

	public Integer getSender() {
		return sender;
	}

	public void setSender(Integer sender) {
		this.sender = sender;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Timestamp getSendTime() {
		return sendTime;
	}

	public void setSendTime(Timestamp sendTime) {
		this.sendTime = sendTime;
	}
	
	
	
}
