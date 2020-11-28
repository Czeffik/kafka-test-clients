package io.github.czeffik.kafka.test.clients.message

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.header.Headers
import spock.lang.Specification

class KafkaMessageSpec extends Specification {

    def 'should throw illegal argument exception when record passed to constructor is null'() {
        when:
            new KafkaMessage<>(null)
        then:
            IllegalArgumentException exception = thrown()
            exception.message == 'record is marked non-null but is null'
    }

    def 'should set key and value based on passed record key and value'() {
        given:
            def record = Mock(ConsumerRecord)
        and:
            def key = 'SUPER KEY'
            record.key() >> key
        and:
            def value = 'GREAT VALUE'
            record.value() >> value
        when:
            def kafkaMessage = new KafkaMessage(record)
        then:
            kafkaMessage.getValue() == value
            kafkaMessage.getKey() == key
    }

    def 'should have required args constructor'() {
        given:
            def key = 'SUPER KEY'
            def value = 'GREAT VALUE'
            def topic = 'topic'
            def partition = 1
            def headers = Mock(Headers)
        when:
            def kafkaMessage = new KafkaMessage(key, value, topic, partition, headers)
        then:
            kafkaMessage.getValue() == value
            kafkaMessage.getKey() == key
            kafkaMessage.getTopic() == topic
            kafkaMessage.getPartition() == partition
            kafkaMessage.getHeaders() == headers
    }

    def 'messages with same values should be equals and has same hash code'() {
        given:
            def key = 'KEY'
            def value = 'VALUE'
            def topic = 'topic'
            def partition = 1
            def headers = Mock(Headers)
        and:
            def message1 = new KafkaMessage(key, value, topic, partition, headers)
        and:
            def message2 = new KafkaMessage(key, value, topic, partition, headers)
        expect:
            message1.hashCode() == message2.hashCode()
            message1 == message2
            !message1.is(message2)
    }
}
