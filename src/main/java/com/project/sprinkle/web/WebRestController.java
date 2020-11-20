package com.project.sprinkle.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.sprinkle.domain.sprinkle.Sprinkle;
import com.project.sprinkle.domain.sprinkle.SprinkleRepository;
import com.project.sprinkle.dto.SprinkleAcceptRequestDto;
import com.project.sprinkle.dto.SprinkleSaveRequestDto;
import com.project.sprinkle.service.AcceptService;
import com.project.sprinkle.service.SprinkleService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@AllArgsConstructor
@Log4j2
public class WebRestController {
	
	private SprinkleRepository sprinkleRepository;
	private SprinkleService sprinkleService;
	private AcceptService acceptService;
	
	@GetMapping("/hello")
	public String hello() {
		return "helloWorld";
	}
	
	@PostMapping(value = "/sprinkle", produces="application/json;charset=utf-8")
	public ResponseEntity<String> sprinkleMoney(@RequestBody SprinkleSaveRequestDto dto, HttpServletRequest request) {
		log.info("sprinkleMoney started");
		
		String userId = request.getHeader("X-USER-ID");
		String roomId = request.getHeader("X-ROOM-ID");
		
		log.info("userId = {}, roomId = {}", userId, roomId);
		
		Sprinkle[] sprinkleArr = sprinkleService.toEntities(userId, roomId, dto);
		
		String returnToken = sprinkleService.save(sprinkleArr);
		
		log.info("sprinkleMoney ended");
		return ResponseEntity.ok(returnToken);
	}
	
	@PutMapping(value= "/sprinkle", consumes="application/json;charset=utf-8")
	public long acceptMoney(@RequestBody SprinkleAcceptRequestDto dto, HttpServletRequest request) {
		log.info("acceptMoney started");
		
		String userId = request.getHeader("X-USER-ID");
		String roomId = request.getHeader("X-ROOM-ID");
		
		log.info("userId = {}, roomId = {}, token = {}", userId, roomId, dto.getToken());
		
		long amount = acceptService.accept(userId, roomId, dto);
		log.info("received money = {}", amount);
		
		
		log.info("acceptMoney ended");
		return amount;
	}
	
	@GetMapping("/checkMoney")
	public String checkMoney() {
		log.info("checkMoney started");
		log.info("checkMoney ended");
		return "helloWorld";
	}
}
