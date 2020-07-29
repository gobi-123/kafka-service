package com.p3solutions.kafka;

import org.apache.kafka.common.KafkaException;
import org.springframework.kafka.test.rule.EmbeddedKafkaRule;

public final class EmbeddedKafkaHolder {

    private static EmbeddedKafkaRule embeddedKafka = new EmbeddedKafkaRule(1, false);
    private static boolean started;

    public static EmbeddedKafkaRule getEmbeddedKafka() {
        if (!started) {
            try {
                embeddedKafka.before();
            }
            catch (Exception e) {
                throw new KafkaException(e);
            }
            started = true;
        }
        return embeddedKafka;
    }

    private EmbeddedKafkaHolder() {
        super();
    }

}
