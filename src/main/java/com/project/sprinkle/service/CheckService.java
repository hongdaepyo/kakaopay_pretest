package com.project.sprinkle.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.sprinkle.domain.sprinkle.Sprinkle;
import com.project.sprinkle.domain.sprinkle.SprinkleRepository;
import com.project.sprinkle.dto.ReceivedInfo;
import com.project.sprinkle.dto.SprinkleCheckResponseDto;
import com.project.sprinkle.error.exception.CheckFailedException;
import com.project.sprinkle.util.CommonUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class CheckService {
	
	final private CommonUtil commonUtil;
	
	final private SprinkleRepository sprinkleRepository;
	
	private int A_WEEK_DAYS = 7;
	
	@Transactional
	public SprinkleCheckResponseDto check(String userId, String token) {
		log.info("check started");
		SprinkleCheckResponseDto result = null;
		
		LocalDateTime aWeekBeforeDate = LocalDateTime.now().minusDays(A_WEEK_DAYS);
		
		List<Sprinkle> sprinkleList = sprinkleRepository.findByUserIdAndTokenAndCreateDateBetween(userId, token, aWeekBeforeDate, LocalDateTime.now());
		log.info("sprinkle Count = {}", sprinkleList.size());
		
		if (sprinkleList.size() > 0) {
			result = getCheckResult(sprinkleList);
		} else if (!token.matches("[a-zA-Z0-9]{3}")) {
			throw new CheckFailedException("Invalid token Error");
		} else {
			throw new CheckFailedException("There is no result. Sprinkle check can be done for 7 days.");
		}
		
		log.info("check ended");
		return result;
	}
	
	private SprinkleCheckResponseDto getCheckResult(List<Sprinkle> list) {
		SprinkleCheckResponseDto dto = new SprinkleCheckResponseDto();
		List<ReceivedInfo> receivedInfoList = new ArrayList<ReceivedInfo>();
		
		LocalDateTime createDate = null;
		long totalMoney = 0;
		long receivedSum = 0;
		
		int len = list.size();
		for (int i = 0; i < len; i++) {
			Sprinkle sprinkle = list.get(i);
			if (i == 0) {
				createDate = sprinkle.getCreateDate();
			}
			
			totalMoney += sprinkle.getDividedAmount();
			
			if (sprinkle.isUsed()) {
				ReceivedInfo receivedInfo = new ReceivedInfo();
				receivedSum += sprinkle.getDividedAmount();
				
				receivedInfo.setReceiverId(sprinkle.getReceiverId());
				receivedInfo.setReceivedMoney(sprinkle.getDividedAmount());
				
				receivedInfoList.add(receivedInfo);
			}
		}
		
		dto.setCreateDate(createDate);
		dto.setReceivedInfo(receivedInfoList);
		dto.setTotalMoney(totalMoney);
		dto.setTotalReceivedMoney(receivedSum);
		
		return dto;
	}
}
