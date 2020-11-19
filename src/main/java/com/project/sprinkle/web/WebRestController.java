package com.project.sprinkle.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("kakaopay")
public class WebRestController {
	
	@GetMapping("/hello")
	public String hello() {
		return "helloWorld";
	}
	
	@GetMapping("/sprinkleMoney")
	public String sprinkleMoney() {
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
