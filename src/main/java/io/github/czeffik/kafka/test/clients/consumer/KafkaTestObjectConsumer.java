package io.github.czeffik.kafka.test.clients.consumer;

import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;

class KafkaTestObjectConsumer<V> extends KafkaTestConsumer<V> {

    KafkaTestObjectConsumer(
            final KafkaConsumer<String, V> consumer,
            final String topic,
            final Duration duration
    ) {
        super(consumer, topic, duration);
    }
}
