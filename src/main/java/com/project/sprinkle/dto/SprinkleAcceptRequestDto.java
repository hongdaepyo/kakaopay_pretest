package com.project.sprinkle.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SprinkleAcceptRequestDto {
	private String userId;
	private String roomId;
	private String token;
	
	@Builder
	public SprinkleAcceptRequestDto (String userId, String roomId) {
		this.setUserId(userId);
		this.setRoomId(roomId);
	}
}
