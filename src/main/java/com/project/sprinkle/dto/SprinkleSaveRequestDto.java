package com.project.sprinkle.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SprinkleSaveRequestDto {
	private Long amount;
	private int memberCount;
	private String sprinkleUserId;
	private String roomId;
	
	@Builder
	public SprinkleSaveRequestDto (String userId, String roomId) {
		this.setSprinkleUserId(userId);
		this.setRoomId(roomId);
	}
}
