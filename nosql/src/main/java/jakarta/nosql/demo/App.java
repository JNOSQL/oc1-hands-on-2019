package jakarta.nosql.demo;

import javax.enterprise.inject.se.SeContainer;
import javax.enterprise.inject.se.SeContainerInitializer;
import java.time.LocalDateTime;
import java.util.UUID;

public class App {

    public static void main(String[] args) throws InterruptedException {

        try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {

            String deviceId = UUID.randomUUID().toString();
            TemperatureStatus status = TemperatureStatus.of(LocalDateTime.now(), 12D);
            Device device = new Device(deviceId, "name", status);
            TemperatureReadings readings = new TemperatureReadings(deviceId, status);

            DeviceService deviceService =
                    container.select(DeviceService.class)
                            .get();

            StatusService statusService =
                    container.select(StatusService.class)
                            .get();

            deviceService.save(device);
            statusService.save(readings);
            System.out.println(deviceService.find(deviceId));
            System.out.println(statusService.find(deviceId));
        }
    }
}
