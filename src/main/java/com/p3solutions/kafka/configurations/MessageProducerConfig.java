package com.p3solutions.kafka.configurations;

import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer2;
import org.springframework.kafka.support.serializer.JsonSerializer;

/**
 * ProducerConfig
 */
@Configuration
@Slf4j
public class MessageProducerConfig {

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapServer;

    @Value(value = "${messenger.group.name.group-name}")
    private String groupName;

    /**
     * Producer factory for All Objects
     *
     * @return {@link ProducerFactory}
     */
    @Bean(name = "objectObjectProducerFactory")
    public ProducerFactory<Object, Object> objectObjectProducerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        JsonSerializer<Object> keySerializer = new JsonSerializer<>();
        keySerializer.forKeys();
        keySerializer.noTypeInfo();
        JsonSerializer<Object> valueSerializer = new JsonSerializer<>();
        valueSerializer.noTypeInfo();
        return new DefaultKafkaProducerFactory<>(configProps, keySerializer, valueSerializer);
    }

    @Bean(name = "stringStringProducerFactory")
    public ProducerFactory<String, String> stringStringProducerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        StringSerializer keySerializer = new StringSerializer();
        StringSerializer valueSerializer = new StringSerializer();
        return new DefaultKafkaProducerFactory<>(configProps, keySerializer, valueSerializer);
    }

    /**
     * Template for Sending message
     *
     * @return {@link KafkaTemplate}
     */
    @Bean(name = "objectObjectKafkaTemplate")
    public KafkaTemplate<Object, Object> kafkaTemplate() {
        return new KafkaTemplate<>(objectObjectProducerFactory());
    }

    @Bean(name = "stringStringKafkaTemplate")
    public KafkaTemplate<String, String> stringKafkaTemplate() {
        return new KafkaTemplate<>(stringStringProducerFactory());
    }

    @Bean(name = "stringStringConcurrentMessageListenerContainer")
    public ConcurrentMessageListenerContainer<String, String> repliesContainer() {
        ConcurrentMessageListenerContainer<String, String> repliesContainer =
                kafkaListenerContainerFactory().createContainer("replies");
        repliesContainer.getContainerProperties().setGroupId("repliesGroup");
        repliesContainer.setAutoStartup(false);
        return repliesContainer;
    }

    @Bean(name = "customKafkaListenerContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
        log.info("setting up kafkaListenerContainerFactory");
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setReplyTemplate(stringKafkaTemplate());
        return factory;
    }
    public ConsumerFactory<String, String> consumerFactory() {
        log.info("Setting up consumer factory");
        return new DefaultKafkaConsumerFactory<>(getConsumerProperties(), new StringDeserializer(),
                new StringDeserializer());
    }

    public Map<String, Object> getConsumerProperties() {
        Map<String, Object> props = new HashMap<String, Object>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupName);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer2.class);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer2.class);
        props.put(ErrorHandlingDeserializer2.KEY_DESERIALIZER_CLASS, StringDeserializer.class);
        props.put(ErrorHandlingDeserializer2.VALUE_DESERIALIZER_CLASS, StringDeserializer.class);
        return props;
    }

    @Bean(name = "stringStringStringReplyingKafkaTemplate")
    public ReplyingKafkaTemplate<String, String, String> stringStringStringReplyingKafkaTemplate(ProducerFactory<String, String> stringStringProducerFactory, ConcurrentMessageListenerContainer<String, String> stringStringConcurrentMessageListenerContainer) {
        stringStringConcurrentMessageListenerContainer.getContainerProperties();
        return new ReplyingKafkaTemplate<>(stringStringProducerFactory, stringStringConcurrentMessageListenerContainer);
    }

}