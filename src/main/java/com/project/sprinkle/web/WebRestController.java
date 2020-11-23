package com.project.sprinkle.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.sprinkle.domain.sprinkle.Sprinkle;
import com.project.sprinkle.dto.SprinkleCheckResponseDto;
import com.project.sprinkle.dto.SprinkleReceiveRequestDto;
import com.project.sprinkle.dto.SprinkleSaveRequestDto;
import com.project.sprinkle.error.exception.CheckFailedException;
import com.project.sprinkle.service.CheckService;
import com.project.sprinkle.service.ReceiveService;
import com.project.sprinkle.service.SprinkleService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@AllArgsConstructor
@Log4j2
@RequestMapping("/kakaopay/")
public class WebRestController {
	
	private final SprinkleService sprinkleService;
	private final ReceiveService receiveService;
	private final CheckService checkService;
	
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
	public ResponseEntity<Long> receiveMoney(@RequestBody SprinkleReceiveRequestDto dto, HttpServletRequest request) {
		log.info("receiveMoney started");
		
		String userId = request.getHeader("X-USER-ID");
		String roomId = request.getHeader("X-ROOM-ID");
		
		log.info("userId = {}, roomId = {}, token = {}", userId, roomId, dto.getToken());
		
		long amount = receiveService.receive(userId, roomId, dto);
		log.info("received money = {}", amount);
		
		log.info("receiveMoney ended");
		return ResponseEntity.ok(amount);
	}
	
	@GetMapping(value = "/checkMoney/{token}")
	public ResponseEntity<SprinkleCheckResponseDto> checkMoney(@PathVariable String token, HttpServletRequest request) {
		log.info("checkMoney started");
		
		String userId = request.getHeader("X-USER-ID");
		
		log.info("userId = {}, token = {}", userId, token);
		
		if (!token.matches("[a-zA-z0-9]{3}")) {
			throw new CheckFailedException("Invalid token Error");
		}
		
		SprinkleCheckResponseDto dto = checkService.check(userId, token);
		
		log.info("checkMoney ended");
		return ResponseEntity.ok(dto);
	}
}
