package com.czeffik.kafka.test.clients.helper;

import com.czeffik.kafka.test.clients.consumer.KafkaTestConsumer;
import com.czeffik.kafka.test.clients.producer.KafkaTestProducer;
import org.apache.avro.generic.GenericRecord;

import java.time.Duration;

class KafkaTestGenericRecordHelper<V extends GenericRecord> extends KafkaTestHelper<V> {

    KafkaTestGenericRecordHelper(
            final KafkaTestProducer<V> kafkaTestProducer,
            final KafkaTestConsumer<V> kafkaTestConsumer,
            final Duration duration
    ) {
        super(kafkaTestProducer, kafkaTestConsumer, duration);
    }
}
