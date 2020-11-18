package com.project.spread.domain.spread;

import javax.persistence.*;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Spread {

	@Id
	private Long id; //뿌리기 아이디
	private Long dividedAmount; //분배 금액
	
	@Column(length = 3, nullable = false)
	private String token;
	
	@Column(nullable = false)
	private int tokenSN;
	
	@Column(nullable = false)
	private String spreadUserId;
	
	@Column(nullable = false)
	private String roomId;
	
	@Column(nullable = false)
	private String spreadDate;
	
	@Column
	private boolean acceptedFlag;
	
	public Spread(Long dividedAmount, ) {
		
	}
}
