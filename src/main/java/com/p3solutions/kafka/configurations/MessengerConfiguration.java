package com.p3solutions.kafka.configurations;

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

import java.util.HashMap;
import java.util.Map;

@Configuration
@Slf4j
public class MessengerConfiguration {

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapServer;

    @Value(value = "${messenger.topics.replies-topic}")
    private String replyTopic;

    /**
     * @param objectObjectProducerFactory
     * @param stringStringConcurrentMessageListenerContainer
     * @return
     */
    @Bean(name = "stringObjectStringReplyingKafkaTemplate")
    public ReplyingKafkaTemplate<String, Object, String> stringStringStringReplyingKafkaTemplate(ProducerFactory<String, Object> objectObjectProducerFactory, ConcurrentMessageListenerContainer<String, String> stringStringConcurrentMessageListenerContainer) {
        ReplyingKafkaTemplate<String, Object, String> replyingKafkaTemplate = new ReplyingKafkaTemplate<>(objectObjectProducerFactory, stringStringConcurrentMessageListenerContainer);
        replyingKafkaTemplate.setSharedReplyTopic(true);
        return replyingKafkaTemplate;
    }

    /**
     * Producer factory for Object keys and values
     *
     * @return {@link ProducerFactory}
     */
    @Bean(name = "objectObjectProducerFactory")
    public ProducerFactory<String, Object> objectObjectProducerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        StringSerializer keySerializer = new StringSerializer();
        JsonSerializer<Object> valueSerializer = new JsonSerializer<>();
        valueSerializer.noTypeInfo();
        return new DefaultKafkaProducerFactory<>(configProps, keySerializer, valueSerializer);
    }

    /**
     * @param stringStringConcurrentKafkaListenerContainerFactory
     * @return {@link ConcurrentMessageListenerContainer}
     */
    @Bean
    public ConcurrentMessageListenerContainer<String, String> replyContainer(ConcurrentKafkaListenerContainerFactory<String, String> stringStringConcurrentKafkaListenerContainerFactory) {
        ConcurrentMessageListenerContainer<String, String> replyContainer = stringStringConcurrentKafkaListenerContainerFactory.createContainer(replyTopic);
        replyContainer.setAutoStartup(false);
        return replyContainer;
    }

    /**
     * Consumer Factory
     *
     * @return {@link ConsumerFactory}
     */
    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        log.info("Setting up consumer factory");
        return new DefaultKafkaConsumerFactory<>(getConsumerProperties(), new StringDeserializer(),
                new StringDeserializer());
    }

    public Map<String, Object> getConsumerProperties() {
        Map<String, Object> props = new HashMap<String, Object>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        props.put(ConsumerConfig.GROUP_ID_CONFIG,"default_group");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer2.class);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer2.class);
        props.put(ErrorHandlingDeserializer2.KEY_DESERIALIZER_CLASS, StringDeserializer.class);
        props.put(ErrorHandlingDeserializer2.VALUE_DESERIALIZER_CLASS, StringDeserializer.class);
        return props;
    }

    @Bean(name = "kafkaListenerContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
        log.info("setting up kafkaListenerContainerFactory");
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setMessageConverter(new StringJsonMessageConverter());
        factory.setReplyTemplate(kafkaTemplate());
        return factory;
    }

    /**
     * Template for Sending message Object keys and values
     *
     * @return {@link KafkaTemplate}
     */
    @Bean(name = "stringObjectKafkaTemplate")
    public KafkaTemplate<String, Object> kafkaTemplate() {
        return new KafkaTemplate<>(objectObjectProducerFactory());
    }

}
