package org.jnosql.artemis.demo.kafka;

import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.jnosql.artemis.demo.kafka.device.TemperatureReading;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

	private static final Logger logger = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) {
		String applicationName = args.length > 0 ? args[0] : UUID.randomUUID().toString();
		String deviceId = args.length > 1 ? args[1] : UUID.randomUUID().toString();
		String bootstrapServers = args.length > 2 ? args[2] : "kafka:9092";

		Bootstrap bootstrap = new Bootstrap(applicationName, bootstrapServers);
		KafkaConnection connection = new KafkaConnection("temperatureReadings", bootstrap);

		final Runnable sensor = new Runnable() {
			public void run() {
				TemperatureReading reading = new TemperatureReading();
				reading.setTimestamp(System.currentTimeMillis());
				reading.setDeviceId(deviceId);
				reading.setTemperature((float) (Math.random() * 100 + 50));
				logger.debug("Sending {} as reading", reading);
				connection.fire(reading);
			}
		};

		ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

		final ScheduledFuture<?> sensorHandle = scheduler.scheduleAtFixedRate(sensor, 10, 10, TimeUnit.SECONDS);
		scheduler.schedule(new Runnable() {
			public void run() {
				sensorHandle.cancel(true);
			}
		}, 60 * 60, TimeUnit.SECONDS);

	}
}
