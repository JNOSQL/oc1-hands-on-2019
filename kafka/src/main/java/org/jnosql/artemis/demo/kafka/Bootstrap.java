package org.jnosql.artemis.demo.kafka;

import java.io.FileReader;
import java.util.Objects;
import java.util.Properties;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Bootstrap {

	private static final Logger logger = LoggerFactory.getLogger(Bootstrap.class);

	private final String applicationName;
	private final String configFile;

	public Bootstrap(String applicationName, String configFile) {
		this.applicationName = Objects.requireNonNull(applicationName);
		this.configFile = Objects.requireNonNull(configFile);
	}

	private Properties getKafkaProperties() {
		final Properties producerConfiguration = loadConfig(configFile);
		producerConfiguration.putIfAbsent("client.id", applicationName + "-client");
		producerConfiguration.putIfAbsent("group.id", applicationName + "-group");
		producerConfiguration.putIfAbsent("acks", "all");
		producerConfiguration.putIfAbsent("delivery.timeout.ms", 30000);
		producerConfiguration.putIfAbsent("batch.size", 16384);
		producerConfiguration.putIfAbsent("linger.ms", 100);
		producerConfiguration.putIfAbsent("buffer.memory", 33554432);
		producerConfiguration.putIfAbsent("retries", 1000);
		return producerConfiguration;
	}

	private Properties loadConfig(String configFile) {
		Properties props = new Properties();
		try (FileReader fileReader = new FileReader(configFile)) {
			props.load(fileReader);
		} catch (Exception e) {
			logger.warn("Loading defaults from {} failed", configFile, e);
		}
		return props;
	}

	public <K, V> KafkaProducer<K, V> startProducer(Serializer<K> keySerializer, Serializer<V> valueSerializer) {
		return new KafkaProducer<>(getKafkaProperties(), keySerializer, valueSerializer);
	}

	public <K, V> KafkaConsumer<K, V> startConsumer(Deserializer<K> keyDeserializer,
			Deserializer<V> valueDeserializer) {
		return new KafkaConsumer<K, V>(getKafkaProperties(), keyDeserializer, valueDeserializer);
	}

}
