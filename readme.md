# Kafka test clients


### Kafka consumer sample config

```java
    import org.apache.kafka.clients.consumer.ConsumerConfig;
    import org.apache.kafka.clients.consumer.OffsetResetStrategy;
    import org.apache.kafka.common.serialization.StringDeserializer;
    
    import java.util.Properties;


    <V> KafkaConsumer<String, V> createConsumer(){
        return new KafkaConsumer<>(consumerProperties());
    }

    <V> Properties consumerProperties(){
        final Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "URL_TO_KAFKA_BOOTSTRAP_SERVERS");
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "TEST_GROUP_ID");
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, OffsetResetStrategy.EARLIEST.name().toLowerCase());
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        //Without AVRO
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, YourObjectDeserializer.class);

        //With AVRO - it will depend on which schema registry you are using:
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class);
        properties.put("schema.registry.url", "URL_TO_SCHEMA_REGISTRY");
        properties.put("specific.avro.reader", true);

        return properties;
    }
```

### Kafka producer sample config
```java
    import org.apache.kafka.clients.producer.KafkaProducer;
    import org.apache.kafka.clients.producer.ProducerConfig;
    import org.apache.kafka.common.serialization.StringSerializer;
    
    import java.util.Properties;

    <V> KafkaProducer<String, V> createProducer() {
        return new KafkaProducer<>(producerProperties());
    }

    <V> Properties producerProperties() {
        final Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "URL_TO_KAFKA_BOOTSTRAP_SERVERS");
        properties.put(ProducerConfig.RETRIES_CONFIG, 0);
        properties.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, false);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, YourObjectSeserializer.class);

        //With AVRO - it will depend on which schema registry you are using:
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class);
        properties.put("schema.registry.url", "URL_TO_SCHEMA_REGISTRY");
        properties.put("specific.avro.reader", true);

        return properties;
    }
```