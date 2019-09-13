package jakarta.nosql.demo;

import jakarta.nosql.mapping.keyvalue.KeyValueTemplate;
import org.eclipse.jnosql.artemis.DatabaseQualifier;

import javax.enterprise.inject.se.SeContainer;
import javax.enterprise.inject.se.SeContainerInitializer;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class App {

    public static void main(String[] args) throws InterruptedException {

        try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {

            String deviceId = UUID.randomUUID().toString();
            TemperatureStatus status = TemperatureStatus.of(LocalDateTime.now(), 12D);
            Device device = new Device(deviceId, "name", status);
            TemperatureReadings readings = new TemperatureReadings(deviceId, status);

            KeyValueTemplate template =
                    container.select(KeyValueTemplate.class).get();

            template.put(device);
            template.put(readings, Duration.ofMinutes(5L));
            System.out.println(template.get(deviceId, Device.class));
            System.out.println(template.get(deviceId, TemperatureReadings.class));


        }
    }
}
