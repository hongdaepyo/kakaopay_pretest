package com.project.sprinkle.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.sprinkle.domain.sprinkle.Sprinkle;
import com.project.sprinkle.domain.sprinkle.SprinkleRepository;
import com.project.sprinkle.dto.SprinkleSaveRequestDto;
import com.project.sprinkle.service.SprinkleService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class WebRestController {
	
	private SprinkleRepository sprinkleRepository;
	private SprinkleService sprinkleService;
	
	@GetMapping("/hello")
	public String hello() {
		return "helloWorld";
	}
	
	@PostMapping(value = "/sprinkle", produces="application/json;charset=utf-8")
	public String sprinkleMoney(@RequestBody SprinkleSaveRequestDto dto, HttpServletRequest request) {
		String userId = request.getHeader("X-USER-ID");
		String roomId = request.getHeader("X-ROOM-ID");
		
		Sprinkle[] sprinkleArr = sprinkleService.toEntities(userId, roomId, dto);
		
		// 저장하고 토큰 리턴하는 로직 구현해야함.
		
		return "true";
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
