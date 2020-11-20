package com.project.sprinkle.domain.sprinkle;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SprinkleRepository extends JpaRepository<Sprinkle, Long> {
	int countByAcceptedUserIdAndRoomIdAndToken(String userId, String roomId, String token);
	Sprinkle findFirstByRoomIdAndTokenAndUsedOrderByTokenSN(String roomId, String token, boolean used);
	List<Sprinkle> findAll();
}
