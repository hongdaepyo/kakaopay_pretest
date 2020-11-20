package com.project.sprinkle.domain.sprinkle;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SprinkleRepository extends JpaRepository<Sprinkle, Long> {
	int countByReceiverIdAndRoomIdAndToken(String userId, String roomId, String token);
	List<Sprinkle> findByUserIdAndTokenAndCreateDateBetween(String userId, String token, LocalDateTime aWeekBeforeDay, LocalDateTime nowDate);
	Sprinkle findFirstByRoomIdAndTokenAndUsedOrderByTokenSN(String roomId, String token, boolean used);
}
