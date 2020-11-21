package com.project.sprinkle.domain.sprinkle;

import java.time.LocalDateTime;

import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;

import com.project.sprinkle.error.exception.ExpiredException;
import com.project.sprinkle.error.exception.UsedTokenException;
import com.project.sprinkle.error.exception.UserIdEqualsReceiverIdException;

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
	@Column(nullable = false, updatable = false)
	private LocalDateTime createDate;
	
	@Column
	private boolean used;
	
	@Column(nullable = true)
	private String receiverId;
	
	@Builder
	public Sprinkle(Long dividedAmount, String token, int tokenSN, String userId, String roomId) {
		this.dividedAmount = dividedAmount;
		this.token = token;
		this.tokenSN = tokenSN;
		this.userId = userId;
		this.roomId = roomId;
	}
	
	public void use(String receiverId) {
		verifyUserIdEqualsReceiverId(receiverId);
		verifyExpiration();
		verifyUsed();
		this.used = true;
		this.receiverId = receiverId;
	}
	
	private void verifyUserIdEqualsReceiverId(String receiverId) {
		if (userId.equals(receiverId)) {
			throw new UserIdEqualsReceiverIdException(receiverId + " is creator.");
		}
	}
	
	private void verifyExpiration() {
		if (isExpired()) {
			throw new ExpiredException("token is expired.");
		}
	}
	
	private void verifyUsed() {
		if (used) {
			throw new UsedTokenException("token is already used.");
		}
	}
	
	public boolean isExpired() {
		int tenMiniute = 10;
		return LocalDateTime.now().isAfter(createDate.plusMinutes(tenMiniute));
	}
}
