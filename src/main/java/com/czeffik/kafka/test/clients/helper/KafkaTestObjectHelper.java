package com.czeffik.kafka.test.clients.helper;

import com.czeffik.kafka.test.clients.consumer.KafkaTestConsumer;
import com.czeffik.kafka.test.clients.producer.KafkaTestProducer;

import java.time.Duration;

class KafkaTestObjectHelper<V> extends KafkaTestHelper<V> {

    KafkaTestObjectHelper(
            final KafkaTestProducer<V> kafkaTestProducer,
            final KafkaTestConsumer<V> kafkaTestConsumer,
            final Duration duration
    ) {
        super(kafkaTestProducer, kafkaTestConsumer, duration);
    }
}
