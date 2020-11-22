package com.project.sprinkle.util;

import java.security.SecureRandom;
import java.util.Arrays;

import org.springframework.stereotype.Component;

@Component
public class CommonUtil {
	private SecureRandom random = new SecureRandom();
	final private String ENGLISH_UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"; 
	final private String ENGLISH_LOWER = ENGLISH_UPPER.toLowerCase(); 
	final private String NUMBER = "0123456789";
	final private String SOURCE = ENGLISH_UPPER + ENGLISH_LOWER + NUMBER;
	final private int TOKEN_LENGTH = 3;
	
	
	public String generateToken() {
		StringBuilder sb = new StringBuilder();
		int length = TOKEN_LENGTH;
		
		for (int i = 0; i < length; i++) {
			sb.append(SOURCE.charAt(random.nextInt(SOURCE.length())));
		}
		
		return sb.toString();
	}
	
	public long[] generateDivideMoneyAmount(long totalAmount, int memberCount) {
		long[] moneyArr = new long[memberCount];
		
		long remainMoney = totalAmount;
		for (int i = 0; i < memberCount && remainMoney > 0; i++) {
			long randomMoney = Math.abs(random.nextLong() % remainMoney);
			
			if (i == memberCount - 1) {
				randomMoney = remainMoney;
			}
			
			moneyArr[i] += randomMoney;
			remainMoney -= randomMoney;
		}
		
		shuffleMoney(moneyArr, memberCount);
		
		return moneyArr;
	}
	
	private void shuffleMoney(long[] money, int count) {
		long temp;
		for (int i = 0; i < count; i++) {
			int random1 = Math.abs(random.nextInt()) % count;
			int random2 = Math.abs(random.nextInt()) % count;
			
			temp = money[random1];
			money[random1] = money[random2];
			money[random2] = temp;
		}
	}
}
