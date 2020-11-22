package com.czeffik.kafka.test.clients.helper;

import com.czeffik.kafka.test.clients.consumer.KafkaTestConsumer;
import com.czeffik.kafka.test.clients.producer.KafkaTestProducer;
import org.apache.avro.specific.SpecificRecord;

import java.time.Duration;

class KafkaTestSpecificRecordHelper<V extends SpecificRecord> extends KafkaTestHelper<V> {

    KafkaTestSpecificRecordHelper(
            final KafkaTestProducer<V> kafkaTestProducer,
            final KafkaTestConsumer<V> kafkaTestConsumer,
            final Duration duration
    ) {
        super(kafkaTestProducer, kafkaTestConsumer, duration);
    }
}
