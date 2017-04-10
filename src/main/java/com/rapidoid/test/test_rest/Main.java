package com.rapidoid.test.test_rest;

import org.rapidoid.setup.App;

/**
 * Hello world!
 *
 */
public class Main 
{
	 public static void main(String[] args) {
		 App.bootstrap(args);
		 
//		 On.get("/").json((Req req) -> Jobs.schedule(() -> {
//			    req.response().result("OK").done();
//			 
//			}, 1, TimeUnit.SECONDS));
//		 
//		 On.post("/jcm/messages/testAsync").json((Req req, BackupReq backupReq) -> Jobs.schedule(() -> {
//			 	System.out.println(backupReq);
//			    req.response().result("OK").done();
//			}, 1, TimeUnit.SECONDS));
		 
	    }
}
