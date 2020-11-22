package com.czeffik.kafka.test.clients.helper

import com.czeffik.kafka.test.clients.consumer.KafkaTestConsumer
import com.czeffik.kafka.test.clients.message.KafkaMessage
import com.czeffik.kafka.test.clients.producer.KafkaTestProducer
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
            1 * consumer.consumeFromCurrentOffsetToTheEnd() >> messages.collect { new KafkaMessage<>(it.key, it.value) }
            _ * consumer.consumeFromCurrentOffsetToTheEnd()
            _ * consumer.assignToPartitionsAndSeekToBeginning()
    }
}
