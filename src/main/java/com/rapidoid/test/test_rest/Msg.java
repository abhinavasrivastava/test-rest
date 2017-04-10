package com.rapidoid.test.test_rest;

import java.util.Date;

import org.apache.commons.codec.digest.DigestUtils;

public class Msg {

	private String dvcMsgId;
	private String name;
	private String text;
	private String phoneNo;
	private Date dateTime;
	private String category = "others";
	private String msgType = "incoming";
	private String appType = "smsInbox";
	private String convId;
	private String operation = "add";
	
	private String state;
	private String hash;
	
	private boolean isValid = true;

	
	public String getDvcMsgId() {
		return dvcMsgId;
	}
	public void setDvcMsgId(String dvcMsgId) {
		this.dvcMsgId = dvcMsgId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public Date getDateTime() {
		return dateTime;
	}
	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	public String getAppType() {
		return appType;
	}
	public void setAppType(String appType) {
		this.appType = appType;
	}
	public String getConvId() {
		return convId;
	}
	public void setConvId(String convId) {
		this.convId = convId;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
		if("add".equals(operation)){
			setState("A");
		}else{
			setState("D");
		}
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getHash() {
		if(hash == null){
			hash = DigestUtils.sha1Hex(text + "|" + phoneNo + "|" + dateTime);
		}
		return hash;
	}
	public boolean isValid() {
		return isValid;
	}
	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}
	
	
}
