package com.project.sprinkle.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SprinkleSaveRequestDto {
	private Long amount;
	private int memberCount;
	private String userId;
	private String roomId;
	
	@Builder
	public SprinkleSaveRequestDto (String userId, String roomId) {
		this.setUserId(userId);
		this.setRoomId(roomId);
	}
}
