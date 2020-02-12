package com.p3solutions.kafka.configurations;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

/**
 * ProducerConfig
 */
@Configuration
public class MessageProducerConfig {

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapServer;

    /**
     * Producer factory for All Objects
     * 
     * @return {@link ProducerFactory}
     */
    @Bean
    public ProducerFactory<Object, Object> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        JsonSerializer<Object> keySerializer = new JsonSerializer<>();
        keySerializer.forKeys();
        keySerializer.noTypeInfo();

        JsonSerializer<Object> valueSerializer = new JsonSerializer<>();
        valueSerializer.noTypeInfo();
        return new DefaultKafkaProducerFactory<>(configProps, keySerializer, valueSerializer);
    }

    /**
     * Template for Sending message
     * 
     * @return {@link KafkaTemplate}
     */
    @Bean
    public KafkaTemplate<Object, Object> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

}