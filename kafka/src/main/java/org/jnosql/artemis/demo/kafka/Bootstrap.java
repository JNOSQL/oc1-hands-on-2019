package org.jnosql.artemis.demo.kafka;

import java.util.Objects;
import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.common.serialization.Serializer;

public class Bootstrap {

	private final String applicationName;
	private final String bootstrapServers;

	public Bootstrap(String applicationName, String bootstrapServers) {
		this.applicationName = Objects.requireNonNull(applicationName);
		this.bootstrapServers = Objects.requireNonNull(bootstrapServers);
	}

	private Properties getKafkaProducerProperties() {
		final Properties producerConfiguration = new Properties();
		producerConfiguration.put("client.id", applicationName + "-client");
		producerConfiguration.put("bootstrap.servers", bootstrapServers);
		producerConfiguration.put("acks", "all");
		producerConfiguration.put("delivery.timeout.ms", 30000);
		producerConfiguration.put("batch.size", 16384);
		producerConfiguration.put("linger.ms", 100);
		producerConfiguration.put("buffer.memory", 33554432);
		producerConfiguration.put("retries", 1000);
		return producerConfiguration;
	}

	public <K, V> KafkaProducer<K, V> startProducer(Serializer<K> keySerializer, Serializer<V> valueSerializer) {
		return new KafkaProducer<>(getKafkaProducerProperties(), keySerializer, valueSerializer);
	}

}
