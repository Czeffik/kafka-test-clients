package io.github.czeffik.kafka.test.clients.producer;

import io.github.czeffik.kafka.test.clients.message.KafkaMessage;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public abstract class KafkaTestProducer<V> implements AutoCloseable {

    private final KafkaProducer<String, V> producer;
    private final String topic;


    public void send(final KafkaMessage<V> kafkaMessage) {
        send(kafkaMessage.getKey(), kafkaMessage.getValue());
    }

    public void send(final String key, final V value) {
        try {
            sendAsync(key, value).get();
        } catch (InterruptedException | ExecutionException ex) {
            throw new SendingMessageException(ex);
        }
    }

    public Future<RecordMetadata> sendAsync(final KafkaMessage<V> kafkaMessage) {
        return sendAsync(kafkaMessage.getKey(), kafkaMessage.getValue());
    }

    public Future<RecordMetadata> sendAsync(final String key, final V value) {
        return this.producer.send(createProducerRecord(key, value));
    }

    private ProducerRecord<String, V> createProducerRecord(final String key, final V value) {
        return new ProducerRecord<>(this.topic, key, value);
    }

    @Override
    public void close() {
        producer.close();
    }

    public static class SendingMessageException extends RuntimeException {
        SendingMessageException(Exception exception) {
            super(exception);
        }
    }
}
