package io.github.czeffik.kafka.test.clients.consumer;

import org.apache.avro.specific.SpecificRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;

class KafkaTestSpecificRecordConsumer<V extends SpecificRecord> extends KafkaTestConsumer<V> {

    KafkaTestSpecificRecordConsumer(
            final KafkaConsumer<String, V> consumer,
            final String topic,
            final Duration duration
    ) {
        super(consumer, topic, duration);
    }
}
