package com.project.sprinkle.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.sprinkle.domain.sprinkle.Sprinkle;
import com.project.sprinkle.domain.sprinkle.SprinkleRepository;
import com.project.sprinkle.dto.SprinkleReceiveRequestDto;
import com.project.sprinkle.error.exception.AlreadyReceivedTokenException;
import com.project.sprinkle.error.exception.NotExistReceivableSprinkleException;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class ReceiveService {
	
	final private SprinkleRepository sprinkleRepository;
	
	@Transactional
	public long receive(String userId, String roomId, SprinkleReceiveRequestDto dto) {
		long result = -1;
		
		int receivedCount = sprinkleRepository.countByReceiverIdAndRoomIdAndToken(userId, roomId, dto.getToken());
		log.info("received Count = {}", receivedCount);
		
		if (receivedCount == 0) {
			Sprinkle sprinkle = sprinkleRepository.findFirstByRoomIdAndTokenAndUsedOrderByTokenSN(roomId, dto.getToken(), false);
			if (sprinkle != null) {
				sprinkle.use(userId);
				result = sprinkle.getDividedAmount();
			} else {
				throw new NotExistReceivableSprinkleException("There is no receivable sprinkle");
			}
		} else {
			throw new AlreadyReceivedTokenException("This user is already received");
		}
		
		return result;
	}
}
