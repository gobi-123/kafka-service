/**
 * 
 */
package com.p3solutions.kafka.messengers;

import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;

import org.springframework.kafka.support.KafkaHeaders;

import com.p3solutions.kafka.objects.Greeting;

import lombok.Getter;

/**
 * @author saideepak
 *
 */
public class KafkaMessageConsumer {
	private final Logger logger = LoggerFactory.getLogger(KafkaMessageConsumer.class);

	@Getter
	private CountDownLatch latch = new CountDownLatch(3);

	@Getter
	private CountDownLatch partitionLatch = new CountDownLatch(2);

	@Getter
	private CountDownLatch filterLatch = new CountDownLatch(2);

	@Getter
	private CountDownLatch greetingLatch = new CountDownLatch(1);

	@KafkaListener(topics = "${kafka.topic.name.preanalysis}", groupId = "foo", containerFactory = "fooKafkaListenerContainerFactory")
	public void listenGroupFoo(String message) {
		logger.info("Received Messasge in group 'foo': {}", message);
		this.getLatch().countDown();
	}

	@KafkaListener(topics = "${kafka.topic.name.preanalysis}", groupId = "bar", containerFactory = "barKafkaListenerContainerFactory")
	public void listenGroupBar(String message) {
		logger.info("Received Messasge in group 'bar': {}", message);
		latch.countDown();
	}

	@KafkaListener(topics = "${kafka.topic.name.preanalysis}", containerFactory = "headersKafkaListenerContainerFactory")
	public void listenWithHeaders(@Payload String message, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
		logger.info("Received Messasge: {} from partition: {}", message, partition);
		latch.countDown();
	}

	@KafkaListener(topicPartitions = @TopicPartition(topic = "${kafka.topic.name.preanalysis}", partitions = { "0",
			"3" }))
	public void listenToParition(@Payload String message, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
		logger.info("Received Message: {} from partition: {}", message, partition);
		this.partitionLatch.countDown();
	}

	@KafkaListener(topics = "${kafka.topic.name.preanalysis}", containerFactory = "filterKafkaListenerContainerFactory")
	public void listenWithFilter(String message) {
		logger.info("Recieved Message in filtered listener: {}", message);
		this.filterLatch.countDown();
	}

	@KafkaListener(topics = "${kafka.topic.name.preanalysis}", containerFactory = "greetingKafkaListenerContainerFactory")
	public void greetingListener(Greeting greeting) {
		logger.info("Recieved greeting message: {}", greeting.getMsg());
		this.greetingLatch.countDown();
	}
}
