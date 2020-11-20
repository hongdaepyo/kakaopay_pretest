package com.project.sprinkle.domain.sprinkle;

import java.time.LocalDateTime;

import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;

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
	private String userId;
	
	@Column(nullable = false)
	private String roomId;
	
	@CreationTimestamp
	@Column(nullable = false)
	private LocalDateTime createDate;
	
	@Column
	private boolean acceptedFlag;
	
	@Column(nullable = true)
	private String acceptedUserId;
	
	@Builder
	public Sprinkle(Long dividedAmount, String token, int tokenSN, String userId, String roomId) {
		this.dividedAmount = dividedAmount;
		this.token = token;
		this.tokenSN = tokenSN;
		this.userId = userId;
		this.roomId = roomId;
	}
	
//	public boolean isExpired() {
//		
//	}
}
