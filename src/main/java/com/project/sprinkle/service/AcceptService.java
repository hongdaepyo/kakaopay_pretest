package com.project.sprinkle.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.sprinkle.domain.sprinkle.SprinkleRepository;
import com.project.sprinkle.dto.SprinkleAcceptRequestDto;
import com.project.sprinkle.util.CommonUtil;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class AcceptService {
	
	@Autowired
	private CommonUtil commonUtil;
	
	private SprinkleRepository sprinkleRepository;
	
	@Transactional
	public long accept(String userId, String roomId, SprinkleAcceptRequestDto dto) {
		log.info("accept started");
		// 제한 조건 : 누구에게도 할당되지 않은 토큰, 뿌리기 당 한 사용자는 한번, 자신이 뿌리기한 건은 받기 불가, 
		// 뿌리기가 호출된 대화방과 동일한 대화방 사용자만, 뿌린지 10분 이내인 요청만
		
//		sprinkleRepository..getDividedAmount();
		
		log.info("accept ended");
		return (long) 0;
	}
}
