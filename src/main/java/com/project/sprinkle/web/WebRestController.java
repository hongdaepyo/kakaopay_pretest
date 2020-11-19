package com.project.sprinkle.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.sprinkle.domain.sprinkle.SprinkleRepository;
import com.project.sprinkle.dto.SprinkleSaveRequestDto;

import lombok.AllArgsConstructor;

@RestController("kakaopay")
@AllArgsConstructor
public class WebRestController {
	
	private SprinkleRepository sprinkleRepository;
	
	@GetMapping("/hello")
	public String hello() {
		return "helloWorld";
	}
	
	@GetMapping("/sprinkleMoney")
	public String sprinkleMoney(@RequestBody SprinkleSaveRequestDto dto) {
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
