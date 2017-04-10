package com.rapidoid.test.test_rest;

import javax.inject.Inject;

import org.rapidoid.http.Resp;

public class BackupService{
	
	@Inject
	private MsgDaoImpl msgDaoImpl;
	
	public BackupResp saveMessages(BackupReq backupReq, BackupResp resp) throws Exception {
		try{
			msgDaoImpl.saveMessages(backupReq);
		}catch(Exception e){
			e.printStackTrace();
		}
		return resp;
	}
	
	public void saveMessagesAsync(BackupReq backupReq, BackupResp response, Resp resp) throws Exception {
		try{
			msgDaoImpl.saveMessagesAsync(backupReq, response, resp);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
