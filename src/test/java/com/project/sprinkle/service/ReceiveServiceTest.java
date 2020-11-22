package com.project.sprinkle.service;

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
public class ReceiveServiceTest {
	
	final private CommonUtil commonUtil;
	
	final private SprinkleRepository sprinkleRepository;
	
	@Transactional
	public long receive(String userId, String roomId, SprinkleReceiveRequestDto dto) {
		log.info("receive started");
		long result = -1;
		
		int receivedCount = sprinkleRepository.countByReceiverIdAndRoomIdAndToken(userId, roomId, dto.getToken());
		log.info("received Count = {}", receivedCount);
		
		if (receivedCount == 0) {
			Sprinkle sprinkle = sprinkleRepository.findFirstByRoomIdAndTokenAndUsedOrderByTokenSN(roomId, dto.getToken(), false);
			if (sprinkle != null) {
				sprinkle.use(userId);
				result = sprinkle.getDividedAmount();
			}
		}
		
		log.info("receive ended");
		return result;
	}
}
