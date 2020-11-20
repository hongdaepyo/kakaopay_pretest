package com.project.sprinkle.service;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.sprinkle.domain.sprinkle.Sprinkle;
import com.project.sprinkle.dto.SprinkleSaveRequestDto;
import com.project.sprinkle.util.CommonUtil;

@Service
public class SprinkleService {
	
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private CommonUtil commonUtil;
	
	public Sprinkle[] toEntities(SprinkleSaveRequestDto dto) {
		int memberCount = dto.getMemberCount();
		Sprinkle[] sprinkleArr = new Sprinkle[memberCount];
		String token = commonUtil.generateToken();
		
		for (int i = 0; i < memberCount; i++) {
			sprinkleArr[i] = Sprinkle.builder()
								.dividedAmount(dto.getAmount() / memberCount)
								.roomId(dto.getRoomId())
								.token(token)
								.tokenSN(i)
								.sprinkleUserId(dto.getSprinkleUserId())
								.sprinkleDate(new Date().toString())
								.build();
		}
		
		return sprinkleArr;
	}
	
	public Sprinkle[] toEntities(String userId, String roomId, SprinkleSaveRequestDto dto) {
		dto.setSprinkleUserId(userId);
		dto.setRoomId(roomId);
		
		return toEntities(dto);
	}
	
	@Transactional
	public String save(Sprinkle[] sprinkleArr) {
		String token = sprinkleArr[0].getToken();
		
		for (int i = 0; i < sprinkleArr.length; i++) {
			em.persist(sprinkleArr[i]);
		}
		
		em.flush();
		em.clear();
		
		return token;
	}
}
