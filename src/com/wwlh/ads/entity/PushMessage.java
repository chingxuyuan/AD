package com.wwlh.ads.entity;

public class PushMessage {
	private int id;
	private String topic;
	private String content;
	private int advertId;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getAdvertId() {
		return advertId;
	}
	public void setAdvertId(int advertId) {
		this.advertId = advertId;
	}




}
