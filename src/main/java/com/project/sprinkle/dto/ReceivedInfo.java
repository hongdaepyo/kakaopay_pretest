package com.project.sprinkle.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReceivedInfo {
	private String receiverId;
	private long receivedMoney;
}