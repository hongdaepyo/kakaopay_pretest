package com.project.sprinkle.util;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import lombok.extern.log4j.Log4j2;

@Log4j2
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class CommonUtilTest {
	@Autowired
	CommonUtil commonUtil;
	
	@Test
	public void generateTokenTest() {
		String token = commonUtil.generateToken();
		
		log.info(token);
		
		assertThat(token).isInstanceOf(String.class);
		assertThat(token).hasSize(3);
		assertThat(token).matches("[a-zA-Z0-9]{3}");
	}
	
	@Test
	public void generateDivideMoneyAmountTest() {
		long totalAmount = 10000000;
		int memberCount = 30;
		
		long[] moneyArr = commonUtil.generateDivideMoneyAmount(totalAmount, memberCount);
		
		assertThat(moneyArr).isNotEmpty();
		assertThat(moneyArr).hasSize(30);
		assertThat(moneyArr).isInstanceOf(long[].class);
		
		long resultSum = 0;
		for (int i = 0; i < memberCount; i++) {
			resultSum += moneyArr[i];
		}
		assertThat(resultSum).isEqualTo(totalAmount);
	}
}
