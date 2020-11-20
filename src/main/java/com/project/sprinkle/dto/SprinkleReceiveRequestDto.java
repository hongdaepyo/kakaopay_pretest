package com.project.sprinkle.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SprinkleReceiveRequestDto {
	private String userId;
	private String roomId;
	private String token;
	
	@Builder
	public SprinkleReceiveRequestDto (String userId, String roomId) {
		this.setUserId(userId);
		this.setRoomId(roomId);
	}
}
