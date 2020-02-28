package com.p3solutions.kafka;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class KafkaServiceApplicationTests {

	@Autowired
	private ApplicationContext context;

	@Test
	public void contextLoads() {
		assertNotNull(context);
	}

}
