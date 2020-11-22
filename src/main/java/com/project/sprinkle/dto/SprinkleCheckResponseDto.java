package com.project.sprinkle.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SprinkleCheckResponseDto {
	private long totalMoney;
	private List<ReceivedInfo> receivedInfo;
	private long totalReceivedMoney;
	private LocalDateTime createDate;
}
