package com.czeffik.kafka.test.clients.producer;

import org.apache.avro.specific.SpecificRecord;
import org.apache.kafka.clients.producer.KafkaProducer;

class KafkaTestSpecificRecordProducer<V extends SpecificRecord> extends KafkaTestProducer<V> {

    KafkaTestSpecificRecordProducer(
            final KafkaProducer<String, V> producer,
            final String topic
    ) {
        super(producer, topic);
    }
}
