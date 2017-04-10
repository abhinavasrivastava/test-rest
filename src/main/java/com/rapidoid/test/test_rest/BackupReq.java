package com.rapidoid.test.test_rest;

import java.util.Date;
import java.util.List;

public class BackupReq {
	private String userId;
	private String deviceId;
	private String deviceName;
	private List<Msg>messages;
	
	private Date backupTime = new Date();
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	public List<Msg> getMessages() {
		return messages;
	}
	public void setMessages(List<Msg> messages) {
		this.messages = messages;
	}
	public Date getBackupTime() {
		return backupTime;
	}
	
}
