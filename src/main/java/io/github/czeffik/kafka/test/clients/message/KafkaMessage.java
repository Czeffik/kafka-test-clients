package io.github.czeffik.kafka.test.clients.message;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Headers;

@Getter
@EqualsAndHashCode
@RequiredArgsConstructor
public class KafkaMessage<V> {
    private final String key;
    private final V value;
    private final String topic;
    private final int partition;
    private final Headers headers;

    public KafkaMessage(@NonNull ConsumerRecord<String, V> record) {
        this.key = record.key();
        this.value = record.value();
        this.topic =record.topic();
        this.partition =record.partition();
        this.headers =record.headers();
    }
}
