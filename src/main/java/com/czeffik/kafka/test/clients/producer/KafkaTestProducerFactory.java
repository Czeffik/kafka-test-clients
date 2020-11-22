package com.czeffik.kafka.test.clients.producer;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.specific.SpecificRecord;
import org.apache.kafka.clients.producer.KafkaProducer;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class KafkaTestProducerFactory {

    public static <V extends GenericRecord> KafkaTestProducer<V> createGenericRecordProducer(
            final KafkaProducer<String, V> producer,
            final String topic
    ) {
        return new KafkaTestGenericRecordProducer<>(
                producer,
                topic
        );
    }

    public static <V extends SpecificRecord> KafkaTestProducer<V> createSpecificRecordProducer(
            final KafkaProducer<String, V> producer,
            final String topic
    ) {
        return new KafkaTestSpecificRecordProducer<>(
                producer,
                topic
        );
    }

    public static <V> KafkaTestProducer<V> createProducer(
            final KafkaProducer<String, V> producer,
            final String topic
    ) {
        return new KafkaTestObjectProducer<>(
                producer,
                topic
        );
    }
}
