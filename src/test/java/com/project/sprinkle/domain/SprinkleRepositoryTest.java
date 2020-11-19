package com.project.sprinkle.domain;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.project.sprinkle.domain.sprinkle.Sprinkle;
import com.project.sprinkle.domain.sprinkle.SprinkleRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class SprinkleRepositoryTest {

	@Autowired
	SprinkleRepository sprinkleRepository;
	
	@AfterEach
	public void cleanup() {
		sprinkleRepository.deleteAll();
	}
	
	@Test
	public void insertSprinkleAndRead() {
		//given
		sprinkleRepository.save(Sprinkle.builder()
				.dividedAmount((long)100)
				.token("ABC")
				.tokenSN(1)
				.sprinkleUserId("00001")
				.sprinkleDate(new Date().toString())
				.roomId("1")
				.build());
		
		//when
		List<Sprinkle> sprinkleList = sprinkleRepository.findAll();
		
		//then
		Sprinkle sprinkle = sprinkleList.get(0);
		assertEquals(sprinkle.getToken(), "ABC");
		assertEquals(sprinkle.getSprinkleUserId(), "00001");
	}
}
