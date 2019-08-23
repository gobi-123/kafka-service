/**
 * 
 */
package com.p3solutions.kafka.configurations;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

/**
 * @author saideepak
 *
 */
@Configuration
public class KafkaTopicConfig {

	@Value(value = "${kafka.bootstrapAddress}")
	private String bootstrapAddress;

	@Value(value = "${kafka.topic.name.preanalysis}")
	private String preAnalysisTopic;

	@Value(value = "${kafka.topic.name.workspace-update}")
	private String workspaceUpdateTopic;

	@Value(value = "${kafka.topic.name.send-email}")
	private String sendEmailTopic;

	/**
	 * Kafka Admin setup with bootstrap address
	 * 
	 * @return {@link KafkaAdmin}
	 */
	@Bean
	public KafkaAdmin kafkaAdmin() {
		Map<String, Object> configs = new HashMap<>();
		configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
		return new KafkaAdmin(configs);
	}

	@Bean
	public NewTopic preAnalysisTopic() {
		return new NewTopic(preAnalysisTopic, 1, (short) 1);
	}

	@Bean
	public NewTopic workspaceUpdate() {
		return new NewTopic(workspaceUpdateTopic, 1, (short) 1);
	}

	@Bean
	public NewTopic sendEmail() {
		return new NewTopic(sendEmailTopic, 1, (short) 1);
	}

}
