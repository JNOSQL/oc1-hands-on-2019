package org.jnosql.artemis.demo.kafka;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Objects;
import java.util.Properties;

import io.confluent.devx.kafka.config.ConfigLoader;

public class Bootstrap {

  private final String applicationName;
  private final String configFile;

  public Bootstrap(String applicationName, String configFile) {
    this.applicationName = Objects.requireNonNull(applicationName);
    this.configFile = Objects.requireNonNull(configFile);
  }

  private Properties getKafkaProducerProperties() {
    final Properties producerConfiguration = ConfigLoader
        .loadConfig(configFile);
    producerConfiguration.putIfAbsent("client.id", applicationName + "-client");
    producerConfiguration.putIfAbsent("acks", "all");
    producerConfiguration.putIfAbsent("delivery.timeout.ms", 30000);
    producerConfiguration.putIfAbsent("batch.size", 16384);
    producerConfiguration.putIfAbsent("linger.ms", 100);
    producerConfiguration.putIfAbsent("buffer.memory", 33554432);
    producerConfiguration.putIfAbsent("retries", 1000);
    return producerConfiguration;
  }

  public <K, V> KafkaProducer<K, V> startProducer(Serializer<K> keySerializer, Serializer<V> valueSerializer) {
    return new KafkaProducer<>(getKafkaProducerProperties(), keySerializer, valueSerializer);
  }
  
  public <K, V> KafkaConsumer<K, V> startConsumer(Deserializer<K> keyDeserializer, Deserializer<V> valueDeserializer) {
	  return new KafkaConsumer<K, V>(getKafkaProducerProperties(), keyDeserializer, valueDeserializer);
  }

}
