/**
 * 
 */
package com.p3solutions.kafka.configurations;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.adapter.RecordFilterStrategy;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.stereotype.Component;

/**
 * @author saideepak
 *
 */
@Configuration
@EnableKafka
@Component
public class KafkaConsumerConfig {
	@Value(value = "${kafka.bootstrapAddress}")
	private String bootstrapAddress;
	
	@Value(value = "${kafka.group.name.preanalysis}")
	private String groupId;

	/**
	 * Consumer factory base Configurations
	 * 
	 * @param groupId
	 * @return
	 */
	public ConsumerFactory<Object, Object> consumerFactory(String groupId) {
		Map<String, Object> props = new HashMap<>();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
		props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		return new DefaultKafkaConsumerFactory<>(props);
	}

	/**
	 * Consumer factory for Specific group
	 * 
	 * @param groupId
	 * @return {@link ConcurrentKafkaListenerContainerFactory}
	 */
	@Bean
	public ConcurrentKafkaListenerContainerFactory<Object, Object> groupConsumerFactory() {
		ConcurrentKafkaListenerContainerFactory<Object, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory(groupId));
		return factory;
	}

	/**
	 * Consumer factory with a record filter strategy
	 * 
	 * @param groupId
	 * @param recordFilterStrategy
	 * @return {@link ConcurrentKafkaListenerContainerFactory}
	 */
	public ConcurrentKafkaListenerContainerFactory<Object, Object> filterConsumerFactory(String groupId,
			RecordFilterStrategy<Object, Object> recordFilterStrategy) {
		ConcurrentKafkaListenerContainerFactory<Object, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory(groupId));
		factory.setRecordFilterStrategy(recordFilterStrategy);
		return factory;
	}

	/**
	 * Consumer factory with a record filter strategy for String messages
	 * 
	 * @param groupId
	 * @param filterString
	 * @return {@link ConcurrentKafkaListenerContainerFactory}
	 */
	public ConcurrentKafkaListenerContainerFactory<Object, Object> stringFilterConsumerFactory(String groupId,
			String filterString) {
		ConcurrentKafkaListenerContainerFactory<Object, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory(groupId));
		factory.setRecordFilterStrategy(record -> ((String) record.value()).contains(filterString));
		return factory;
	}

	// public ConsumerFactory<String, Greeting> greetingConsumerFactory() {
	// Map<String, Object> props = new HashMap<>();
	// props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
	// props.put(ConsumerConfig.GROUP_ID_CONFIG, "greeting");
	// return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(),
	// new JsonDeserializer<>(Greeting.class));
	// }
	//
	// // Greeting based Group
	// @Bean
	// public ConcurrentKafkaListenerContainerFactory<String, Greeting>
	// greetingKafkaListenerContainerFactory() {
	// ConcurrentKafkaListenerContainerFactory<String, Greeting> factory = new
	// ConcurrentKafkaListenerContainerFactory<>();
	// factory.setConsumerFactory(greetingConsumerFactory());
	// return factory;
	// }

}
