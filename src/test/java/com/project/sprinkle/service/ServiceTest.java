package com.project.sprinkle.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.project.sprinkle.domain.sprinkle.Sprinkle;
import com.project.sprinkle.dto.SprinkleCheckResponseDto;
import com.project.sprinkle.dto.SprinkleReceiveRequestDto;
import com.project.sprinkle.dto.SprinkleSaveRequestDto;

import lombok.extern.log4j.Log4j2;

@Log4j2
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ServiceTest {
	@Autowired
	SprinkleService sprinkleService;
	
	@Autowired
	ReceiveService receiveService;
	
	@Autowired
	CheckService checkService;
	
	private static String testToken;
	
	@Test
	public void sprinkleSaveOk() {
		SprinkleSaveRequestDto dto = new SprinkleSaveRequestDto();
		dto.setAmount(500000L);
		dto.setMemberCount(20);
		dto.setRoomId("testroom");
		dto.setUserId("12345");
		
		Sprinkle[] sprinkleArr = sprinkleService.toEntities(dto);
		
		assertThat(sprinkleArr).isNotEmpty();
		assertThat(sprinkleArr[0].getUserId()).isEqualTo("12345");
		
		String token = sprinkleService.save(sprinkleArr);
		log.info(token);
		
		testToken = token;
		
		assertThat(token).hasSize(3);
		assertThat(token).matches("[a-zA-Z0-9]{3}");
	}
	
	@Test
	public void receiveOK() {
		SprinkleReceiveRequestDto dto = new SprinkleReceiveRequestDto();
		
		String userId = "01010";
		String roomId = "testroom";
		
		dto.setUserId(userId);
		dto.setRoomId(userId);
		dto.setToken(testToken);
		
		long receivedMoney = receiveService.receive(userId, roomId, dto);
		log.info("received money = " + receivedMoney);
		
		assertThat(receivedMoney).isGreaterThanOrEqualTo(receivedMoney);
		assertThat(receivedMoney).asString().matches("[0-9]+");
	}
	
	@Test
	public void checkMoneyOK() {
		String userId = "12345";
		String roomId = "testroom";
		
		SprinkleCheckResponseDto dto = checkService.check(userId, roomId, testToken);
		log.info(dto);
		
		assertThat(dto.getTotalMoney()).isEqualTo(500000);
		assertThat(dto.getReceivedInfo().get(0).getReceiverId()).isEqualTo("01010");
	}
}
