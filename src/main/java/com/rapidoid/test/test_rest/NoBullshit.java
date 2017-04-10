package com.rapidoid.test.test_rest;

import org.rapidoid.annotation.Controller;
import org.rapidoid.annotation.GET;

@Controller
public class NoBullshit {
	@GET
    public String hello() {
        return "Hello, world!";
    }
}
