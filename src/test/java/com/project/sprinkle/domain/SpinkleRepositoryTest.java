package com.project.sprinkle.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.project.sprinkle.domain.sprinkle.SprinkleRepository;

@SpringBootTest
public class SpinkleRepositoryTest {

	@Autowired
	SprinkleRepository sprinkleRepository;
}
