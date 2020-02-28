package com.p3solutions.kafka.topic_manager;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

/**
 * Topics
 */
@Configuration
public class Topics {

    @Bean
    public NewTopic topic1() {
        return TopicBuilder.name("DEFAULT_TOPIC").partitions(1).replicas(1).compact().build();
    }

}