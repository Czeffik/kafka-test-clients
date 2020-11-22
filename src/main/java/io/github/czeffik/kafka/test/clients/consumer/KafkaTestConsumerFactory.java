package io.github.czeffik.kafka.test.clients.consumer;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.specific.SpecificRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class KafkaTestConsumerFactory {

    public static <V extends GenericRecord> KafkaTestConsumer<V> createGenericRecordConsumer(
            final KafkaConsumer<String, V> kafkaConsumer,
            final String topic,
            final Duration pollDuration
    ) {
        return new KafkaTestGenericRecordConsumer<>(
                kafkaConsumer,
                topic,
                pollDuration
        );
    }

    public static <V extends SpecificRecord> KafkaTestConsumer<V> createSpecificRecordConsumer(
            final KafkaConsumer<String, V> kafkaConsumer,
            final String topic,
            final Duration pollDuration
    ) {
        return new KafkaTestSpecificRecordConsumer<>(
                kafkaConsumer,
                topic,
                pollDuration
        );
    }

    public static <V> KafkaTestConsumer<V> createConsumer(
            final KafkaConsumer<String, V> kafkaConsumer,
            final String topic,
            final Duration pollDuration
    ) {
        return new KafkaTestObjectConsumer<>(
                kafkaConsumer,
                topic,
                pollDuration
        );
    }
}
