package com.p3solutions.kafka;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import com.p3solutions.kafka.configurations.MessageConsumerConfig;
import com.p3solutions.kafka.configurations.MessageProducerConfig;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.test.rule.EmbeddedKafkaRule;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * MessengerServiceTests
 * 
 * @author svudya
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MessengerServiceTests {

    private static final String TEMPLATE_TOPIC = "templateTopic";

    @ClassRule
    public static EmbeddedKafkaRule embeddedKafka = new EmbeddedKafkaRule(1, true, TEMPLATE_TOPIC);

    @Autowired
    MessageConsumerConfig messageConsumerConfig;

    @Autowired
    MessageProducerConfig messageProducerConfig;

    @Before
    public void setup() {
        System.setProperty("spring.kafka.bootstrap-servers", embeddedKafka.getEmbeddedKafka().getBrokersAsString());
    }

    @Test
    public void testMessageSent() throws Exception {
        // // Consumer Configuration
        // TestObject object = TestObject.builder().id("1").name("Test ID").build();
        // ConcurrentMessageListenerContainer<String, String> container = messageConsumerConfig
        //         .kafkaListenerContainerFactory().createContainer(TEMPLATE_TOPIC);
        // final BlockingQueue<ConsumerRecord<String, String>> records = new LinkedBlockingQueue<>();
        // container.setupMessageListener(new MessageListener<String, String>() {
        //     @Override
        //     public void onMessage(ConsumerRecord<String, String> record) {
        //         System.out.println(record);
        //         records.add(record);
        //     }
        // });
        // container.setBeanName("templateTests");
        // container.start();
        // // Producer Configuration
        // ProducerFactory<Object, Object> pf = messageProducerConfig.producerFactory();
        // KafkaTemplate<Object, Object> template = new KafkaTemplate<>(pf);
        // template.setDefaultTopic(TEMPLATE_TOPIC);
        // template.sendDefault(object);
        // ConsumerRecord<String, String> received = records.poll(10, TimeUnit.SECONDS);
        // assertThat(received.value(), is("{\"id\":\"1\",\"name\":\"Test ID\"}"));
    }
}