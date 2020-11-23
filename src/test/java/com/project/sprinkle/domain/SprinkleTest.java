package com.project.sprinkle.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.project.sprinkle.domain.sprinkle.Sprinkle;
import com.project.sprinkle.domain.sprinkle.SprinkleRepository;
import com.project.sprinkle.error.exception.ExpiredException;
import com.project.sprinkle.error.exception.UsedTokenException;
import com.project.sprinkle.error.exception.UserIdEqualsReceiverIdException;

import lombok.extern.log4j.Log4j2;

@Log4j2
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class SprinkleTest {
	
	@Autowired
	SprinkleRepository sprinkleRepository;
	
	private Sprinkle sprinkle;
	
	@BeforeEach
	public void saveSprinkle() {
		sprinkleRepository.save(Sprinkle.builder()
				.dividedAmount((long)100000)
				.token("ABC")
				.tokenSN(1)
				.userId("00001")
				.roomId("1")
				.build());
		
		sprinkle = sprinkleRepository.findAll().get(0);
	}
	
	@AfterEach
	public void deleteSprinkle() {
		sprinkleRepository.deleteAll();
	}
	
	@Test
	public void verifyUserIdEqualsReceiverIdFailTest() {
		String receiverId = "00001";
		
		try {
			sprinkle.use(receiverId);
		} catch (UserIdEqualsReceiverIdException ex) {
			log.info(ex.getMessage());
			assertThat(ex.getMessage()).contains("is creator.");
		}
	}
	
	@Test
	public void verifyExpirationFailTest() {
		String receiverId = "00002";
		
		// 토큰이 생성된지 10분이 지난 것처럼 세팅.
		sprinkle.setCreateDate(LocalDateTime.now().minusMinutes(11));
		
		
		try {
			sprinkle.use(receiverId);
		} catch (ExpiredException ex) {
			log.info(ex.getMessage());
			assertThat(ex.getMessage()).isEqualTo("token is expired.");
		}
	}
	
	@Test
	public void verifyUsedFailTest() {
		String receiverId = "00002";
		
		// 토큰이 이미 사용된 것처럼 세팅.
		sprinkle.setUsed(true);
		
		try {
			sprinkle.use(receiverId);
		} catch (UsedTokenException ex) {
			log.info(ex.getMessage());
			assertThat(ex.getMessage()).isEqualTo("token is already used.");
		}
	}
}
