package com.rapidoid.test.test_rest;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.rapidoid.http.Resp;

import com.datastax.driver.core.BatchStatement;
import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;


public class MsgDaoImpl {

	@Inject
	CassandraConnectionFactory cassandraConnectionFactory;
	String sql;
	Session session;
	PreparedStatement insertMsgPS;
	PreparedStatement updateMsgPS;
	PreparedStatement insertActivitiesPS;

	String insertMsgSql = "INSERT INTO messages_by_users "
			+ "(user_id,msg_hash,msg_time,address,app_type,category,conv_id,device_msg_id,last_updated_tx_stamp,msg_type,name,state,text) "
			+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";

	String updateMsgSql = "UPDATE messages_by_users SET created_tx_stamp = created_tx_stamp+?, device_key = device_key+? "
			+ "WHERE user_id =? AND msg_hash = ?";

	//	String insertActivitiesSql = "INSERT INTO activities_by_devices (user_id,device_key,device_name,last_backup_time,last_msg_time) "
	//			+ "VALUES(?,?,?,?,?)";

	String insertActivitiesSql = "INSERT INTO activities_by_devices (user_id,device_key,last_backup_time,last_msg_time) "
			+ "VALUES(?,?,?,?)";

	@PostConstruct
	public void initialize(){
		session = cassandraConnectionFactory.getSession();
		insertMsgPS = session.prepare(insertMsgSql);
		updateMsgPS = session.prepare(updateMsgSql);
		insertActivitiesPS = session.prepare(insertActivitiesSql);
	}

	public void saveMessages(BackupReq req){
		String userId = req.getUserId();
		String dvcId = req.getDeviceId();
		String dvcName = req.getDeviceName();
		BatchStatement insertBatchStmt = new BatchStatement();
		BatchStatement updateBatchStmt = new BatchStatement();

		List<Date>updateDate = new ArrayList<Date>();
		updateDate.add(req.getBackupTime());
		List<String>dvcs = new ArrayList<String>();
		dvcs.add(dvcId);

		for(Msg message:req.getMessages()){
			if(message.isValid()){
				insertBatchStmt.add(insertMsgPS.bind(
						userId,
						message.getHash(),
						message.getDateTime(),
						message.getPhoneNo(),
						message.getAppType(),
						message.getCategory(),
						message.getConvId(),
						message.getDvcMsgId(),
						req.getBackupTime(),
						message.getMsgType(),
						message.getName(),
						message.getState(),
						message.getText()
						));

				updateBatchStmt.add(updateMsgPS.bind(
						updateDate,
						dvcs,
						userId,
						message.getHash()
						));
			}
		}
		session.execute(insertBatchStmt);
		session.execute(updateBatchStmt);

		//BoundStatement bound = insertActivitiesPS.bind(userId, dvcId, dvcName, req.getBackupTime(), req.getBackupTime());
		BoundStatement bound = insertActivitiesPS.bind(userId, dvcId, req.getBackupTime(), req.getBackupTime());
		session.execute(bound);
	}



	public void saveMessagesAsync(final BackupReq req, final BackupResp resp, final Resp response){
		final String userId = req.getUserId();
		final String dvcId = req.getDeviceId();
		final String dvcName = req.getDeviceName();
		BatchStatement insertBatchStmt = new BatchStatement();
		final BatchStatement updateBatchStmt = new BatchStatement();

		List<Date>updateDate = new ArrayList<Date>();
		updateDate.add(req.getBackupTime());
		List<String>dvcs = new ArrayList<String>();
		dvcs.add(dvcId);

		for(Msg message:req.getMessages()){
			if(message.isValid()){
				insertBatchStmt.add(insertMsgPS.bind(
						userId,
						message.getHash(),
						message.getDateTime(),
						message.getPhoneNo(),
						message.getAppType(),
						message.getCategory(),
						message.getConvId(),
						message.getDvcMsgId(),
						req.getBackupTime(),
						message.getMsgType(),
						message.getName(),
						message.getState(),
						message.getText()
						));

				updateBatchStmt.add(updateMsgPS.bind(
						updateDate,
						dvcs,
						userId,
						message.getHash()
						));
			}
		}
		ListenableFuture<ResultSet> resultSet = session.executeAsync(insertBatchStmt);
		Futures.addCallback(resultSet, new FutureCallback<ResultSet>() {
			public void onSuccess(ResultSet resultSet) {
				ListenableFuture<ResultSet> rs2 = session.executeAsync(updateBatchStmt);
				Futures.addCallback(rs2, new FutureCallback<ResultSet>() {
					@Override
					public void onSuccess(ResultSet result) {
						//BoundStatement bound = insertActivitiesPS.bind(userId, dvcId, dvcName, req.getBackupTime(), req.getBackupTime());
						BoundStatement bound = insertActivitiesPS.bind(userId, dvcId, req.getBackupTime(), req.getBackupTime());
						ListenableFuture<ResultSet> rs3 = session.executeAsync(bound);
						Futures.addCallback(rs3, new FutureCallback<ResultSet>() {
							@Override
							public void onSuccess(ResultSet result) {
								resp.setStatus(1);
								if(resp.getInvalid() != null && resp.getInvalid().size() > 0){
									if(resp.getInvalid().size() < req.getMessages().size()){
										resp.setStatus(2);
									}else{
										resp.setStatus(0);
									}
								}
								response.result(resp);
								response.done();
							}

							@Override
							public void onFailure(Throwable t) {

							}
						});
					}

					@Override
					public void onFailure(Throwable t) {
						// TODO Auto-generated method stub
					}
				});
			}

			public void onFailure(Throwable t) {
				System.out.printf("Failed to retrieve the version: %s%n",
						t.getMessage());
			}
		});
	}
}
