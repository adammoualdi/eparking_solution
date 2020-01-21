package com.parkingapp.server;

import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {

	@Test
	void contextLoads() {
		System.out.println("TRUE");
		assertTrue(true);
	}

	@Test
	void checkWorks() {
		assertTrue(true);
	}

}
