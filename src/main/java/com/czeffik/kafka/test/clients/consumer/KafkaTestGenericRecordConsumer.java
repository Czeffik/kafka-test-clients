package com.czeffik.kafka.test.clients.consumer;

import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;


class KafkaTestGenericRecordConsumer<V extends GenericRecord> extends KafkaTestConsumer<V> {

    KafkaTestGenericRecordConsumer(
            final KafkaConsumer<String, V> consumer,
            final String topic,
            final Duration duration
    ) {
        super(consumer, topic, duration);
    }
}
