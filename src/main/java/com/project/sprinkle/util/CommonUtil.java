package com.project.sprinkle.util;

import java.security.SecureRandom;

import org.springframework.stereotype.Component;

@Component
public class CommonUtil {
	private SecureRandom random = new SecureRandom();
	final private String ENGLISH_UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"; 
	final private String ENGLISH_LOWER = ENGLISH_UPPER.toLowerCase(); 
	final private String NUMBER = "0123456789";
	final private String SOURCE = ENGLISH_UPPER + ENGLISH_LOWER + NUMBER;
	
	
	public String generateToken() {
		StringBuilder sb = new StringBuilder();
		int length = 3;
		
		for (int i = 0; i < length; i++) {
			sb.append(SOURCE.charAt(random.nextInt(SOURCE.length())));
		}
		
		return sb.toString();
	}
}
