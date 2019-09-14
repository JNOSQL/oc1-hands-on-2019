package org.jnosql.artemis.demo.kafka;

import org.jnosql.artemis.demo.kafka.device.TemperatureReading;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Main {

  private static final Logger logger = LoggerFactory.getLogger(Main.class);

  public static void main(String[] args) {
    String applicationName = args.length > 0 ? args[0] : UUID.randomUUID().toString();
    String deviceId = args.length > 1 ? args[1] : UUID.randomUUID().toString();
    String
        configLocation =
        args.length > 2 ? args[2] : System.getProperty("user.home") + File.separator + ".ccloud" + File.separator
                                    + "config.oc1";

    Bootstrap bootstrap = new Bootstrap(applicationName, configLocation);
    KafkaConnection connection = new KafkaConnection("temperatureReadings", bootstrap);

    final Runnable sensor = () -> {
      TemperatureReading reading = new TemperatureReading();
      reading.setTimestamp(System.currentTimeMillis());
      reading.setDeviceId(deviceId);
      reading.setTemperature((float) (Math.random() * 100 + 50));
      logger.debug("Sending {} as reading", reading);
      connection.fire(reading);
    };

    ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    final ScheduledFuture<?> sensorHandle = scheduler.scheduleAtFixedRate(sensor, 10, 10, TimeUnit.SECONDS);
    scheduler.schedule(() -> {
      sensorHandle.cancel(true);
    }, 60 * 60, TimeUnit.SECONDS);

  }
}
