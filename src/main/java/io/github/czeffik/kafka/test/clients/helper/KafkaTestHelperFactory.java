package io.github.czeffik.kafka.test.clients.helper;

import io.github.czeffik.kafka.test.clients.consumer.KafkaTestConsumer;
import io.github.czeffik.kafka.test.clients.producer.KafkaTestProducer;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.specific.SpecificRecord;

import java.time.Duration;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class KafkaTestHelperFactory {

    public static <V extends GenericRecord> KafkaTestHelper<V> createGenericRecordHelper(
            final @NonNull KafkaTestProducer<V> kafkaTestProducer,
            final @NonNull KafkaTestConsumer<V> kafkaTestConsumer,
            final @NonNull Duration duration
    ) {
        return new KafkaTestGenericRecordHelper<>(
                kafkaTestProducer,
                kafkaTestConsumer,
                duration
        );
    }

    public static <V extends SpecificRecord> KafkaTestHelper<V> createSpecificRecordHelper(
            final @NonNull KafkaTestProducer<V> kafkaTestProducer,
            final @NonNull KafkaTestConsumer<V> kafkaTestConsumer,
            final @NonNull Duration duration
    ) {
        return new KafkaTestSpecificRecordHelper<>(
                kafkaTestProducer,
                kafkaTestConsumer,
                duration
        );
    }

    public static <V> KafkaTestHelper<V> createHelper(
            final @NonNull KafkaTestProducer<V> kafkaTestProducer,
            final @NonNull KafkaTestConsumer<V> kafkaTestConsumer,
            final @NonNull Duration duration
    ) {
        return new KafkaTestObjectHelper<>(
                kafkaTestProducer,
                kafkaTestConsumer,
                duration
        );
    }
}
