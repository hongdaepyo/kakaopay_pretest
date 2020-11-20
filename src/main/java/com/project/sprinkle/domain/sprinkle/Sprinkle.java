package com.project.sprinkle.domain.sprinkle;

import javax.persistence.*;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Sprinkle {

	@Id
	@GeneratedValue
	private Long id; //뿌리기 아이디
	
	@Column
	private Long dividedAmount; //분배 금액
	
	@Column(length = 3, nullable = false)
	private String token;
	
	@Column(nullable = false)
	private int tokenSN;
	
	@Column(nullable = false)
	private String sprinkleUserId;
	
	@Column(nullable = false)
	private String roomId;
	
	@Column(nullable = false)
	private String sprinkleDate;
	
	@Column
	private boolean acceptedFlag;
	
	@Column(nullable = true)
	private String acceptedUserId;
	
	@Builder
	public Sprinkle(Long dividedAmount, String token, int tokenSN, String sprinkleUserId, String roomId, String sprinkleDate) {
		this.dividedAmount = dividedAmount;
		this.token = token;
		this.tokenSN = tokenSN;
		this.sprinkleUserId = sprinkleUserId;
		this.roomId = roomId;
		this.sprinkleDate = sprinkleDate;
	}
}
