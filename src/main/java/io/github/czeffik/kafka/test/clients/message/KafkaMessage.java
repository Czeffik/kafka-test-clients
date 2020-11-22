package io.github.czeffik.kafka.test.clients.message;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;

@Getter
@EqualsAndHashCode
@RequiredArgsConstructor
public class KafkaMessage<V> {
    private final String key;
    private final V value;

    public KafkaMessage(@NonNull ConsumerRecord<String, V> record) {
        this.key = record.key();
        this.value = record.value();
    }
}
