package org.jnosql.artemis.demo.kafka;

import java.io.ByteArrayInputStream;
import java.util.Map;
import java.util.Objects;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import javax.json.bind.config.PropertyNamingStrategy;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.jnosql.artemis.demo.kafka.device.TemperatureReading;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KafkaConnection {

	private static final Logger logger = LoggerFactory.getLogger(KafkaConnection.class);

	private final Serializer<TemperatureReading> serializer = new Serializer<TemperatureReading>() {

		@Override
		public void configure(Map<String, ?> configs, boolean isKey) {
			// NOOP
		}

		@Override
		public byte[] serialize(String topic, TemperatureReading data) {
			return jsonb.toJson(data).getBytes();
		}

		@Override
		public void close() {
			// NOOP
		}

	};

	private final Deserializer<TemperatureReading> deserializer = new Deserializer<TemperatureReading>() {

		@Override
		public void configure(Map<String, ?> configs, boolean isKey) {
			// NOOP
		}

		@Override
		public TemperatureReading deserialize(String topic, byte[] data) {
			return jsonb.fromJson(new ByteArrayInputStream(data), TemperatureReading.class);
		}

		@Override
		public void close() {
			// NOOP
		}

	};

	private final Jsonb jsonb;

	private final KafkaProducer<String, TemperatureReading> producer;

	private final String topic;

	public KafkaConnection(String topic, Bootstrap bootstrap) {
		this.topic = Objects.requireNonNull(topic);
		JsonbConfig config = new JsonbConfig()
				.withPropertyNamingStrategy(PropertyNamingStrategy.LOWER_CASE_WITH_UNDERSCORES);
		jsonb = JsonbBuilder.create(config);
		producer = bootstrap.startProducer(new StringSerializer(), serializer);
	}

	public void fire(TemperatureReading value) {
		logger.debug("Fire {}: {}", value.getClass().getSimpleName(), value);
		producer.send(new ProducerRecord<>(topic, value.getDeviceId(), value),
				(metadata, exception) -> logger.debug("Event sent with metadata {}", metadata, exception));
	}

}
