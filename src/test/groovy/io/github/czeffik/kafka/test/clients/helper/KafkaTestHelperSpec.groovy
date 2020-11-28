package io.github.czeffik.kafka.test.clients.helper

import io.github.czeffik.kafka.test.clients.consumer.KafkaTestConsumer
import io.github.czeffik.kafka.test.clients.producer.KafkaTestProducer
import io.github.czeffik.kafka.test.clients.message.KafkaMessage
import org.apache.kafka.common.header.Headers
import spock.lang.Specification

import java.time.Duration

class KafkaTestHelperSpec extends Specification {

    static final DURATION_VALUE = 200
    KafkaTestProducer<String> producer = Mock()
    KafkaTestConsumer<String> consumer = Mock()
    def duration = Duration.ofMillis(DURATION_VALUE)

    def helper = KafkaTestHelperFactory.createHelper(
            producer,
            consumer,
            duration
    )

    def 'should send messages and wait to appear'() {
        given:
            def topic = 'topic'
            def partition = 1
            def headers = Mock(Headers)
        and:
            def messages = [
                    'ke1': 'value1',
                    'ke2': 'value2'
            ]
        when:
            helper.sendMessagesAndWaitToAppear(messages)
        then:
            1 * producer.send('ke1', 'value1')
            1 * producer.send('ke2', 'value2')
            _ * producer.send(_)
        and:
            1 * consumer.assignToPartitionsAndSeekToBeginning()
            1 * consumer.consumeFromCurrentOffsetToTheEnd() >> messages.collect { new KafkaMessage<>(it.key, it.value, topic, partition, headers) }
            _ * consumer.consumeFromCurrentOffsetToTheEnd()
            _ * consumer.assignToPartitionsAndSeekToBeginning()
    }
}
