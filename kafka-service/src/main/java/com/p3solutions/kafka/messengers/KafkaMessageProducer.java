/**
 * 
 */
package com.p3solutions.kafka.messengers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

/**
 * @author saideepak
 *
 */
@Component
public class KafkaMessageProducer {

	private final Logger logger = LoggerFactory.getLogger(KafkaMessageProducer.class);

	@Autowired
	private KafkaTemplate<Object, Object> kafkaTemplate;

	@Value(value = "${kafka.bootstrapAddress}")
	private String bootstrapAddress;

	/**
	 * Caller method
	 * 
	 * @param topicName
	 * @param object
	 */
	public void caller(String topicName, Object key, Object object) {
		ListenableFuture<SendResult<Object, Object>> future = kafkaTemplate.send(topicName, key, object);

		future.addCallback(new ListenableFutureCallback<SendResult<Object, Object>>() {

			@Override
			public void onSuccess(SendResult<Object, Object> result) {
				logger.info("Sent object=[{}] with offset=[{}]", object.getClass().getName(),
						result.getRecordMetadata().offset());

			}

			@Override
			public void onFailure(Throwable ex) {
				logger.error("Unable to send object=[{}] due to : {}", object.getClass().getName(), ex.getMessage());
			}

		});
	}
}
