package com.p3solutions.kafka;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.core.IsEqual.equalToObject;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProducerConfigTests {

    @Autowired
    ApplicationContext context;

    @Test
    public void shouldCreateProducerFactoryBean() {
        Object objectObjectProducerFactory = context.getBean("objectObjectProducerFactory");
        assertEquals(DefaultKafkaProducerFactory.class, objectObjectProducerFactory.getClass());
//        Object stringStringProducerFactory = context.getBean("stringStringProducerFactory");
//        assertEquals(DefaultKafkaProducerFactory.class, stringStringProducerFactory.getClass());
    }

    @Test
    public void shouldCreateKafkaTemplateBean() {
        Object objectObjectKafkaTemplate = context.getBean("stringObjectKafkaTemplate");
        assertEquals(KafkaTemplate.class, objectObjectKafkaTemplate.getClass());
        Object stringStringStringReplyingKafkaTemplate = context.getBean("stringObjectStringReplyingKafkaTemplate");
        assertEquals(ReplyingKafkaTemplate.class, stringStringStringReplyingKafkaTemplate.getClass());
    }
}
