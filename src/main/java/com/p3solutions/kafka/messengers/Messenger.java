package com.p3solutions.kafka.messengers;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import lombok.extern.slf4j.Slf4j;

/**
 * KafkaMessageProducer
 */
@Slf4j
@Component
public class Messenger {

    private KafkaTemplate<String, Object> messageTemplate;

    public Messenger(@Qualifier("stringObjectKafkaTemplate") KafkaTemplate<String, Object> messageTemplate) {
        this.messageTemplate = messageTemplate;
    }

    public void send(String topicName, String key, Object value) {
        ListenableFuture<SendResult<String, Object>> future = messageTemplate.send(topicName, key, value);
        future.addCallback(new ListenableFutureCallback<>() {

            @Override
            public void onSuccess(SendResult<String, Object> result) {
                log.info("Sent Object=[{}] with offset=[{}]", value.getClass().getName(),
                        result.getRecordMetadata().offset());
            }

            @Override
            public void onFailure(Throwable exception) {
                log.error("Unable to send Object=[{}] due to : {}", value.getClass().getName(), exception.getMessage());
            }

        });
    }

}