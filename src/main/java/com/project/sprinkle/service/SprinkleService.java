package com.project.sprinkle.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.sprinkle.domain.sprinkle.Sprinkle;
import com.project.sprinkle.dto.SprinkleSaveRequestDto;
import com.project.sprinkle.util.CommonUtil;

@Service
public class SprinkleService {
	
	@Autowired
	private CommonUtil commonUtil;
	
	public Sprinkle[] toEntities(SprinkleSaveRequestDto dto) {
		int memberCount = dto.getMemberCount();
		Sprinkle[] sprinkleArr = new Sprinkle[memberCount];
		for (int i = 0; i < memberCount; i++) {
			String token = commonUtil.generateToken();
			sprinkleArr[i] = Sprinkle.builder()
								.dividedAmount(dto.getAmount() / memberCount)
								.roomId(dto.getRoomId())
								.token(token)
								.tokenSN(i)
								.sprinkleUserId(dto.getSprinkleUserId())
								.build();
		}
		
		return sprinkleArr;
	}
	
	public Sprinkle[] toEntities(String userId, String roomId, SprinkleSaveRequestDto dto) {
		dto.setSprinkleUserId(userId);
		dto.setRoomId(roomId);
		
		return toEntities(dto);
	}
}
