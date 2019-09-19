package jakarta.nosql.demo;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import javax.enterprise.inject.se.SeContainer;
import javax.enterprise.inject.se.SeContainerInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

public class App {

	private static final Logger logger = LoggerFactory.getLogger(App.class);

	public static void main(String[] args) throws InterruptedException {
		 SLF4JBridgeHandler.removeHandlersForRootLogger();  // (since SLF4J 1.6.5)
		 SLF4JBridgeHandler.install();

		 
		String deviceId = args.length > 0 ? args[0] : UUID.randomUUID().toString();

		try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {
			DeviceService deviceService = container.select(DeviceService.class).get();
			StatusService statusService = container.select(StatusService.class).get();

			// check if device id is there
			Optional<Device> dvc = deviceService.find(deviceId);
			logger.debug("Device ID found: {}", dvc.isPresent());

			if (!dvc.isPresent()) {
				logger.debug("No Device with given ID, creating one with 0C as value");
				TemperatureStatus status = TemperatureStatus.of(LocalDateTime.now(), 0d);
				TemperatureReadings readings = new TemperatureReadings(deviceId, status);
				statusService.save(readings);

				Device device = new Device(deviceId, "name", status);
				deviceService.save(device);
				logger.debug("Device & Status created");
			} else {
				logger.info("Device found: {}", dvc.get());

				Optional<TemperatureReadings> readings = statusService.find(deviceId);

				logger.info("Status found: {}", readings.isPresent());
				if (readings.isPresent()) {
					logger.info("Device Status: {}", readings.get());
				}
			}
		}
	}
}
