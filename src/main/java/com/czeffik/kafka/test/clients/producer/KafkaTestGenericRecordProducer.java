package com.czeffik.kafka.test.clients.producer;

import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.producer.KafkaProducer;

class KafkaTestGenericRecordProducer<V extends GenericRecord> extends KafkaTestProducer<V> {

    KafkaTestGenericRecordProducer(
            final KafkaProducer<String, V> producer,
            final String topic
    ) {
        super(producer, topic);
    }
}
