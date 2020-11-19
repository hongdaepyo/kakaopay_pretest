package com.project.sprinkle.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SprinkleSaveRequestDto {
	private Long dividedAmount;
	private String sprinkleUserId;
	private String roomId;
}
