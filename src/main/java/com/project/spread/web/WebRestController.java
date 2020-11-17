package com.project.spread.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("kakaopay")
public class WebRestController {
	
	@GetMapping("/hello")
	public String hello() {
		return "helloWorld";
	}
	
	@GetMapping("/spreadMoney")
	public String spreadMoney() {
		return "helloWorld";
	}
	
	@GetMapping("/acceptMoney")
	public String acceptMoney() {
		return "helloWorld";
	}
	
	@GetMapping("/checkMoney")
	public String checkMoney() {
		return "helloWorld";
	}
}
