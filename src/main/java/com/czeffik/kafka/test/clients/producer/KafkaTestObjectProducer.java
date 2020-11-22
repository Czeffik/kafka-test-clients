package com.czeffik.kafka.test.clients.producer;

import org.apache.kafka.clients.producer.KafkaProducer;

class KafkaTestObjectProducer<V> extends KafkaTestProducer<V> {

    KafkaTestObjectProducer(
            final KafkaProducer<String, V> producer,
            final String topic
    ) {
        super(producer, topic);
    }
}
