package com.rapidoid.test.test_rest;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.rapidoid.annotation.Controller;
import org.rapidoid.annotation.Header;
import org.rapidoid.annotation.POST;
import org.rapidoid.http.Req;

@Controller("/jcm")
public class MessageController {

	@Inject
	BackupService backupService;

	@Inject
	BackupReqPreprocessor backupReqPreprocessor;

	@POST("/messages/backup")
	public BackupResp save(@Header("x-user-id") String userId, @Header("x-device-key") String deviceId, 
			@Header("x-device-name") String deviceName, BackupReq req) {
		req.setUserId(userId);
		req.setDeviceId(deviceId);
		req.setDeviceName(deviceName);
		BackupResp response = null;
		try {
			backupReqPreprocessor.validateSignature(req);
			response = backupReqPreprocessor.preProcessRequest(req);
			backupService.saveMessages(req, response);

			response.setStatus(1);
			if(response.getInvalid() != null && response.getInvalid().size() > 0){
				if(response.getInvalid().size() < req.getMessages().size()){
					response.setStatus(2);
				}else{
					response.setStatus(0);
				}
			}

		} catch (AppException e) {
			//set http status code 400
			response = new BackupResp();
			response.setStatus(0);
			List<String>errors = new ArrayList<String>();
			errors.add(e.getMessage());
			response.setErrors(errors);
		}
		catch (Exception e) {
			//set http status code 500
			response = new BackupResp();
			response.setStatus(0);
			List<String>errors = new ArrayList<String>();
			errors.add(e.getMessage());
			response.setErrors(errors);
		}
		return response;
	}

	@POST("/messages/abackup")
	public BackupResp save2(@Header("x-user-id") String userId, @Header("x-device-key") String deviceId, 
			@Header("x-device-name") String deviceName, BackupReq req, Req request) {

		req.setUserId(userId);
		req.setDeviceId(deviceId);
		req.setDeviceName(deviceName);
		BackupResp response = null;
		try {
			backupReqPreprocessor.validateSignature(req);
			response = backupReqPreprocessor.preProcessRequest(req);
			request.async();
			backupService.saveMessagesAsync(req, response, request.response());
		} catch (AppException e) {
			//set http status code 400
			response = new BackupResp();
			response.setStatus(0);
			List<String>errors = new ArrayList<String>();
			errors.add(e.getMessage());
			response.setErrors(errors);
		}
		catch (Exception e) {
			//set http status code 500
			response = new BackupResp();
			response.setStatus(0);
			List<String>errors = new ArrayList<String>();
			errors.add(e.getMessage());
			response.setErrors(errors);
		}

		return response;
	}

}
