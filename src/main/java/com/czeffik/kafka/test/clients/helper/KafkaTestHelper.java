package com.czeffik.kafka.test.clients.helper;

import com.czeffik.kafka.test.clients.consumer.KafkaTestConsumer;
import com.czeffik.kafka.test.clients.message.KafkaMessage;
import com.czeffik.kafka.test.clients.producer.KafkaTestProducer;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.awaitility.Awaitility;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public abstract class KafkaTestHelper<V> implements AutoCloseable {

    @NonNull
    private final KafkaTestProducer<V> kafkaTestProducer;
    @NonNull
    private final KafkaTestConsumer<V> kafkaTestConsumer;
    @Setter
    @NonNull
    private Duration duration;

    public void sendMessagesAndWaitToAppear(final Map<String, V> messages) {
        messages.forEach(this.kafkaTestProducer::send);
        waitForMessages(messages);
    }

    public void sendMessageAndWaitToAppear(final String key, final V value) {
        this.kafkaTestProducer.send(key, value);
        waitForMessage(key, value);
    }

    public List<KafkaMessage<V>> consumeExpectedNumberOfMessages(final int expectedNumberOfMessages) {
        final List<KafkaMessage<V>> consumedMessages = new ArrayList<>();
        this.kafkaTestConsumer.assignToPartitionsAndSeekToBeginning();
        Awaitility.await().atMost(duration).untilAsserted(() -> {
            consumedMessages.addAll(this.kafkaTestConsumer.consumeFromCurrentOffsetToTheEnd());
            assert consumedMessages.size() == expectedNumberOfMessages;
        });
        return consumedMessages;
    }

    public List<KafkaMessage<V>> consumeExpectedNumberOfMessagesFilteredByKey(
            final @NonNull String expectedKey,
            final int expectedNumberOfMessagesWithKey
    ) {
        final List<KafkaMessage<V>> consumedMessages = new ArrayList<>();
        this.kafkaTestConsumer.assignToPartitionsAndSeekToBeginning();
        Awaitility.await().atMost(duration).untilAsserted(() -> {
            consumedMessages.addAll(
                    this.kafkaTestConsumer.consumeFromCurrentOffsetToTheEnd().stream()
                            .filter(kafkaMessage -> expectedKey.equals(kafkaMessage.getKey()))
                            .collect(Collectors.toList())
            );
            assert (long) consumedMessages.size() == expectedNumberOfMessagesWithKey;
        });
        return consumedMessages;
    }


    public void waitForMessages(final Map<String, V> expectedMessages) {
        final List<KafkaMessage<V>> consumedMessages = new ArrayList<>();
        this.kafkaTestConsumer.assignToPartitionsAndSeekToBeginning();
        Awaitility.await().atMost(duration).untilAsserted(() -> {
            consumedMessages.addAll(this.kafkaTestConsumer.consumeFromCurrentOffsetToTheEnd());
            expectedMessages.forEach((expectedKey, expectedValue) -> {
                assert anyKey(consumedMessages, expectedKey);
                assert anyValue(consumedMessages, expectedValue);
            });
        });
    }

    public void waitForMessage(final String expectedKey, final V expectedValue) {
        this.kafkaTestConsumer.assignToPartitionsAndSeekToBeginning();
        Awaitility.await().atMost(duration).untilAsserted(() -> {
            final List<KafkaMessage<V>> consumedMessages = this.kafkaTestConsumer.consumeFromCurrentOffsetToTheEnd();
            assert anyKey(consumedMessages, expectedKey);
            assert anyValue(consumedMessages, expectedValue);
        });
    }

    private boolean anyKey(final Collection<KafkaMessage<V>> kafkaMessages, final String expectedKey) {
        return kafkaMessages.stream()
                .anyMatch(kafkaMessage -> {
                    final String kafkaMessageKey = kafkaMessage.getKey();
                    if (kafkaMessageKey == null) {
                        return kafkaMessageKey == expectedKey;
                    }
                    return kafkaMessageKey.equals(expectedKey);
                });
    }

    private boolean anyValue(final Collection<KafkaMessage<V>> kafkaMessages, final V expectedValue) {
        return kafkaMessages.stream()
                .anyMatch(kafkaMessage -> {
                    V kafkaMessageValue = kafkaMessage.getValue();
                    if (kafkaMessageValue == null) {
                        return kafkaMessageValue == expectedValue;
                    }
                    return kafkaMessageValue.equals(expectedValue);
                });
    }

    @Override
    public void close() {
        kafkaTestProducer.close();
        kafkaTestConsumer.close();
    }
}
