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

    @Before
    public void setUp(){

    }

    @Test
    public void shouldSendMessageToCorrectTopic(){

    }

    @Test
    public void shouldReportErrorIfSendingFailed(){

    }
}