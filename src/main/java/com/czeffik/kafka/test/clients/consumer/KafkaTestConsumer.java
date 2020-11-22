package com.czeffik.kafka.test.clients.consumer;

import com.czeffik.kafka.test.clients.message.KafkaMessage;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public abstract class KafkaTestConsumer<V> implements AutoCloseable {

    private final KafkaConsumer<String, V> consumer;
    private final String topic;
    private final Duration duration;
    private final AtomicBoolean alreadyAssigned = new AtomicBoolean(false);

    /**
     * Method consume all messages from beginning offset to end offset.
     * End offset is read once - after this method is called.
     *
     * @return List with consumed messages from beginning to end offset
     */
    public List<KafkaMessage<V>> consumeAll() {
        final List<TopicPartition> topicPartitions = getTopicPartitions();

        assignToPartitionsAndSeekToBeginning(topicPartitions);

        return consumeFromCurrentOffsetToTheEnd(topicPartitions);
    }

    public List<KafkaMessage<V>> consumeFromCurrentOffsetToTheEnd() {
        final List<TopicPartition> topicPartitions = getTopicPartitions();
        return consumeFromCurrentOffsetToTheEnd(topicPartitions);
    }

    public void assignToPartitionsAndSeekToBeginning() {
        final List<TopicPartition> topicPartitions = getTopicPartitions();
        assignToPartitionsAndSeekToBeginning(topicPartitions);
    }

    public void assignToPartitionsAndSeekToEnd() {
        final List<TopicPartition> topicPartitions = getTopicPartitions();
        assignToPartitionsAndSeekToEnd(topicPartitions);
    }

    public void assignToPartitions() {
        final List<TopicPartition> topicPartitions = getTopicPartitions();
        assignToPartitions(topicPartitions);
    }

    private List<KafkaMessage<V>> consumeFromCurrentOffsetToTheEnd(final List<TopicPartition> topicPartitions) {
        final Map<TopicPartition, Long> endOffsets = consumer.endOffsets(topicPartitions);

        final List<KafkaMessage<V>> consumedMessages = new ArrayList<>();

        topicPartitions.forEach(topicPartition -> {
            final long topicPartitionEndOffset = endOffsets.get(topicPartition);

            final List<KafkaMessage<V>> messagesConsumedFromTopicPartition = readTopicPartition(
                    topicPartition,
                    topicPartitionEndOffset
            );

            consumedMessages.addAll(messagesConsumedFromTopicPartition);
        });

        return consumedMessages;
    }

    private List<KafkaMessage<V>> readTopicPartition(
            final TopicPartition topicPartition,
            final long topicPartitionEndOffset
    ) {
        final List<KafkaMessage<V>> messagesConsumedFromTopicPartition = new ArrayList<>();
        while (thereAreMessagesToReadFromPartition(topicPartition, topicPartitionEndOffset)) {
            consumer.poll(duration)
                    .forEach(consumerRecord -> messagesConsumedFromTopicPartition.add(new KafkaMessage<>(consumerRecord)));
        }
        return messagesConsumedFromTopicPartition;
    }

    private boolean thereAreMessagesToReadFromPartition(final TopicPartition topicPartition, final long endOffset) {
        return consumer.position(topicPartition) < endOffset;
    }

    private void assignToPartitionsAndSeekToBeginning(final List<TopicPartition> topicPartitions) {
        assignToPartitions(topicPartitions);
        seekToBeginning(topicPartitions);
    }

    private void assignToPartitionsAndSeekToEnd(final List<TopicPartition> topicPartitions) {
        assignToPartitions(topicPartitions);
        seekToEnd(topicPartitions);
    }

    private void seekToBeginning(final List<TopicPartition> topicPartitions) {
        consumer.seekToBeginning(topicPartitions);
    }

    private void seekToEnd(final List<TopicPartition> topicPartitions) {
        consumer.seekToEnd(topicPartitions);
    }

    private void assignToPartitions(final List<TopicPartition> topicPartitions) {
        if (!this.alreadyAssigned.get()) {
            consumer.assign(topicPartitions);
            this.alreadyAssigned.set(true);
        }
    }

    private List<TopicPartition> getTopicPartitions() {
        return consumer.partitionsFor(topic)
                .stream()
                .map(partitionInfo -> new TopicPartition(partitionInfo.topic(), partitionInfo.partition()))
                .collect(Collectors.toList());
    }

    @Override
    public void close() {
        consumer.close();
    }
}
