package org.jnosql.artemis.demo.kafka.client;

import java.time.Duration;
import java.util.UUID;

import org.jnosql.artemis.demo.kafka.Bootstrap;
import org.jnosql.artemis.demo.kafka.KafkaConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DeviceEventReceiver {

	private static final Logger logger = LoggerFactory.getLogger(DeviceEventReceiver.class);

	public static void main(String[] args) {
		String applicationName = args.length > 0 ? args[0] : UUID.randomUUID().toString();
		String configLocation = args.length > 1 ? args[1] : "config.oc1";

		Bootstrap bootstrap = new Bootstrap(applicationName, configLocation);
		KafkaConnection connection = new KafkaConnection("temperatureReadings", bootstrap);

		while (true) {
			connection.poll(Duration.ofDays(1), (temperature) -> logger.info("New record: {}", temperature));
		}

	}
}
