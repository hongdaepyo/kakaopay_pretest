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
import com.project.sprinkle.dto.SprinkleReceiveRequestDto;
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
	public Map<String, Object> check(String userId, String roomId, String token) {
		log.info("check started");
		Map<String, Object> result = null;
		
		LocalDateTime aWeekBeforeDate = LocalDateTime.now().minusDays(A_WEEK_DAYS);
		
		List<Sprinkle> sprinkleList = sprinkleRepository.findByUserIdAndTokenAndCreateDateBetween(userId, token, aWeekBeforeDate, LocalDateTime.now());
		log.info("sprinkle Count = {}", sprinkleList.size());
		
		if (sprinkleList.size() > 0) {
			result = getCheckResult(sprinkleList);
		}
		
		log.info("check ended");
		return result;
	}
	
	private Map<String, Object> getCheckResult(List<Sprinkle> list) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		List<Map<String, Object>> receivedInfoList = new ArrayList<Map<String,Object>>();
		LocalDateTime createDate = null;
		long totalSum = 0;
		long receivedSum = 0;
		
		int len = list.size();
		for (int i = 0; i < len; i++) {
			Sprinkle sprinkle = list.get(i);
			if (i == 0) {
				createDate = sprinkle.getCreateDate();
			}
			
			totalSum += sprinkle.getDividedAmount();
			
			if (sprinkle.isUsed()) {
				receivedSum += sprinkle.getDividedAmount();
				
				Map<String, Object> receivedMap = new HashMap<String, Object>();
				receivedMap.put("receivedAmount", sprinkle.getDividedAmount());
				receivedMap.put("receiverId", sprinkle.getReceiverId());
				
				receivedInfoList.add(receivedMap);
			}
		}
		
		map.put("createDate", createDate);
		map.put("totalSum", totalSum);
		map.put("receivedSum", receivedSum);
		map.put("receivedInfo", receivedInfoList);
		
		return map;
	}
}
