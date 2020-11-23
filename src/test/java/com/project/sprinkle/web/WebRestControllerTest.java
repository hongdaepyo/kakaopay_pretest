package com.project.sprinkle.web;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.project.sprinkle.dto.SprinkleCheckResponseDto;

import lombok.extern.log4j.Log4j2;

@Log4j2
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class WebRestControllerTest {
	@Autowired
	private TestRestTemplate restTemplate;
	
	private static String testToken;
	
	private HttpHeaders getHeader(String userId, String roomId) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-USER-ID", userId);
		headers.add("X-ROOM-ID", roomId);
		
		return headers;
	}
	
	@Test
	@Order(1)
	public void sprinkleMoneyOK() {
		HttpHeaders headers = getHeader("00001", "ABCDE");
		
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("amount", 200000);
		map.put("memberCount", 5);
		
		HttpEntity<Map<String, Integer>> request = new HttpEntity<Map<String, Integer>>(map, headers);
		
		ResponseEntity<String> response = restTemplate.postForEntity("/kakaopay/sprinkle", request, String.class);
		
		log.info(response);
		
		testToken = response.getBody().toString();
		
		assertThat(response).isNotNull();
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).hasSize(3);
		assertThat(response.getBody()).matches("[a-zA-Z0-9]{3}");
	}
	
	@Test
	@Order(2)
	public void receiveMoneyOK() {
		// 뿌리기 받기
		HttpHeaders headers = getHeader("00002", "ABCDE");
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("token", testToken);
		
		HttpEntity<Map<String, String>> request = new HttpEntity<Map<String, String>>(map, headers);
		
		ResponseEntity<Long> response = restTemplate.exchange("/kakaopay/sprinkle", HttpMethod.PUT, request, Long.class);
		
		log.info("received money = {}", response);
		
		assertThat(response).isNotNull();
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isGreaterThanOrEqualTo(0);
		assertThat(response.getBody()).asString().matches("[0-9]+");
	}
	
	@Test
	@Order(3)
	public void checkMoneyOk() {
		HttpHeaders headers = getHeader("00001", "ABCDE");
		
		HttpEntity<Map<String, String>> request = new HttpEntity<Map<String, String>>(null, headers);
		
		ResponseEntity<SprinkleCheckResponseDto> response = restTemplate.exchange("/kakaopay/checkMoney/" + testToken, HttpMethod.GET, request, SprinkleCheckResponseDto.class);
		
		log.info(response);
		
		assertThat(response).isNotNull();
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody().getTotalMoney()).isEqualTo(200000);
		assertThat(response.getBody().getReceivedInfo().get(0).getReceiverId()).isEqualTo("00002");
	}
}
