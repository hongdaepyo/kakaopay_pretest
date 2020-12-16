package com.project.sprinkle.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.sprinkle.domain.sprinkle.Sprinkle;
import com.project.sprinkle.dto.SprinkleSaveRequestDto;
import com.project.sprinkle.util.CommonUtil;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class SprinkleService {
	
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private CommonUtil commonUtil;
	
	public Sprinkle[] toEntities(SprinkleSaveRequestDto dto) {
		int memberCount = dto.getMemberCount();
		Sprinkle[] sprinkleArr = new Sprinkle[memberCount];
		String token = commonUtil.generateToken();
		long[] moneyArr = commonUtil.generateDivideMoneyAmount(dto.getAmount(), memberCount);
		
		log.debug("token = {}", token);
		
		for (int i = 0; i < memberCount; i++) {
			sprinkleArr[i] = Sprinkle.builder()
								.dividedAmount(moneyArr[i])
								.roomId(dto.getRoomId())
								.token(token)
								.tokenSN(i)
								.userId(dto.getUserId())
								.build();
		}
		
		return sprinkleArr;
	}
	
	public Sprinkle[] toEntities(String userId, String roomId, SprinkleSaveRequestDto dto) {
		dto.setUserId(userId);
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
