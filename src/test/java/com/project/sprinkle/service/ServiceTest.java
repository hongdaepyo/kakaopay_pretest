package com.project.sprinkle.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.project.sprinkle.domain.sprinkle.Sprinkle;
import com.project.sprinkle.dto.SprinkleCheckResponseDto;
import com.project.sprinkle.dto.SprinkleReceiveRequestDto;
import com.project.sprinkle.dto.SprinkleSaveRequestDto;
import com.project.sprinkle.error.exception.AlreadyReceivedTokenException;
import com.project.sprinkle.error.exception.CheckFailedException;
import com.project.sprinkle.error.exception.NotExistReceivableSprinkleException;

import lombok.extern.log4j.Log4j2;

@Log4j2
@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class ServiceTest {
	@Autowired
	SprinkleService sprinkleService;
	
	@Autowired
	ReceiveService receiveService;
	
	@Autowired
	CheckService checkService;
	
	private static String testToken;
	
	@Test
	@Order(1)
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
	@Order(2)
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
	@Order(3)
	public void receivedTokenFailTest() {
		SprinkleReceiveRequestDto dto = new SprinkleReceiveRequestDto();
		
		String userId = "01010";
		String roomId = "testroom";
		
		dto.setUserId(userId);
		dto.setRoomId(userId);
		dto.setToken(testToken);
		
		try {
			long receivedMoney = receiveService.receive(userId, roomId, dto);
		} catch (AlreadyReceivedTokenException ex) {
			log.info(ex.getMessage());
			assertThat(ex.getMessage()).contains("already received");
		}
	}
	
	@Test
	@Order(4)
	public void notExistReceivableSprinkleTest() {
		SprinkleReceiveRequestDto dto = new SprinkleReceiveRequestDto();
		
		String userId = "01010";
		String roomId = "testroom";
		
		dto.setUserId(userId);
		dto.setRoomId(userId);
		dto.setToken("as");
		
		try {
			long receivedMoney = receiveService.receive(userId, roomId, dto);
		} catch (NotExistReceivableSprinkleException ex) {
			log.info(ex.getMessage());
			assertThat(ex.getMessage()).contains("There is no receivable sprinkle");
		}
	}
	
	@Test
	public void checkMoneyOK() {
		String userId = "12345";
		
		SprinkleCheckResponseDto dto = checkService.check(userId, testToken);
		log.info(dto);
		
		assertThat(dto.getTotalMoney()).isEqualTo(500000);
		assertThat(dto.getReceivedInfo().get(0).getReceiverId()).isEqualTo("01010");
	}
	
	@Test
	public void checkMoneyNoResultTest() {
		String userId = "11111";
		
		try {
			SprinkleCheckResponseDto dto = checkService.check(userId, testToken);
			log.info(dto);
		} catch (CheckFailedException ex) {
			log.info(ex.getMessage());
			assertThat(ex.getMessage()).containsIgnoringCase("There is no result");
		}
	}
	
	@Test
	public void checkMoneyInvalidTokenTest() {
		String userId = "12345";
		String invalidToken = "A";
		try {
			SprinkleCheckResponseDto dto = checkService.check(userId, invalidToken);
			log.info(dto);
		} catch (CheckFailedException ex) {
			log.info(ex.getMessage());
			assertThat(ex.getMessage()).isEqualTo("Invalid token Error");
		}
	}
}
