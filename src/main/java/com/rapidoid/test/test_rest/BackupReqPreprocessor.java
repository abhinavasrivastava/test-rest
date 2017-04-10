package com.rapidoid.test.test_rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class BackupReqPreprocessor {
	
	public void validateSignature(BackupReq req) throws AppException{
		if(req == null || req.getMessages() == null || req.getMessages().size() == 0){
			throw new AppException(401, "no message was provided in messages array");
		}
		if(req.getMessages().size() > 100){
			throw new AppException(402, "batch limit can not exceed 100");
		}
	}
	
	public BackupResp preProcessRequest(BackupReq req){
		BackupResp resp = new BackupResp();
		long max = 0;
		List<Map<String, Object>>successMsgs = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>>invalid = new ArrayList<Map<String, Object>>();
		resp.setInvalid(invalid);
		for(Msg msg : req.getMessages()){
			try {
				if(isValid(msg)){
					Map<String, Object>map = new HashMap<String, Object>();
					map.put("dvcMsgId", msg.getDvcMsgId());
					map.put("serMsgId", msg.getHash());
					successMsgs.add(map);
					
					if(msg.getDateTime().getTime() > max){
						max = msg.getDateTime().getTime();
					}
				}
			} catch (InvalidMessageException e) {
				msg.setValid(false);
				Map<String, Object>error = new HashMap<String, Object>();
				error.put("code", e.getCode());
				error.put("dvcMsgId", e.getDvcMsgId());
				error.put("error", e.getMessage());
				invalid.add(error);
			}
		}
		resp.setSuccess(successMsgs);
		resp.setLastMsgTime(max);
		resp.setLastBackupTime(req.getBackupTime().getTime());
		return resp;
	}
	
	private boolean isValid(Msg msg) throws InvalidMessageException{
		if(StringUtils.isBlank(msg.getDvcMsgId())){
			throw new InvalidMessageException(101, msg.getDvcMsgId(), "dvcMsgId not provided");
		}
		if(StringUtils.isBlank(msg.getPhoneNo())){
			throw new InvalidMessageException(102, msg.getDvcMsgId(), "Please provide valid phoneNo");
		}
		if(StringUtils.isBlank(msg.getText())){
			throw new InvalidMessageException(103, msg.getDvcMsgId(), "Please pass valid text field");
		}
		if(msg.getDateTime() == null){
			throw new InvalidMessageException(104, msg.getDvcMsgId(), "Please pass valid dateTime");
		}
		if(StringUtils.isBlank(msg.getMsgType())){
			throw new InvalidMessageException(105, msg.getDvcMsgId(), "Please pass valid msgType");
		}
		if(StringUtils.isBlank(msg.getOperation()) || (!"add".equals(msg.getOperation()) && !"delete".equals(msg.getOperation()))){
			throw new InvalidMessageException(106, msg.getDvcMsgId(), "Please pass valid operation");
		}
		return true;
	}

}
