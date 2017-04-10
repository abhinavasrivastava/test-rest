package com.rapidoid.test.test_rest;

import java.util.List;
import java.util.Map;

public class BackupResp {
	private int status;
	private List<String>errors;
	private long lastBackupTime;
	private long lastMsgTime;
	private List<Map<String, Object>>success;
	private List<Map<String, Object>>invalid;
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public List<String> getErrors() {
		return errors;
	}
	public void setErrors(List<String> errors) {
		this.errors = errors;
	}
	public long getLastBackupTime() {
		return lastBackupTime;
	}
	public void setLastBackupTime(long lastBackupTime) {
		this.lastBackupTime = lastBackupTime;
	}
	public long getLastMsgTime() {
		return lastMsgTime;
	}
	public void setLastMsgTime(long lastMsgTime) {
		this.lastMsgTime = lastMsgTime;
	}
	public List<Map<String, Object>> getSuccess() {
		return success;
	}
	public void setSuccess(List<Map<String, Object>> success) {
		this.success = success;
	}
	public List<Map<String, Object>> getInvalid() {
		return invalid;
	}
	public void setInvalid(List<Map<String, Object>> invalid) {
		this.invalid = invalid;
	}
	
}
