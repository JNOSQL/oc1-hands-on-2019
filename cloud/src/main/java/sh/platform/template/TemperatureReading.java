package sh.platform.template;

import org.aerogear.kafka.cdi.annotation.Consumer;
import sh.platform.template.config.JsonUtils;
import tec.units.ri.format.QuantityFormat;
import tec.units.ri.quantity.Quantities;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.measure.Quantity;
import javax.measure.quantity.Temperature;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.TimeZone;
import java.util.logging.Logger;

import static java.time.Instant.ofEpochMilli;
import static java.time.LocalDateTime.ofInstant;
import static sh.platform.template.DeviceEvents.TEMPERATURE_READING;
import static tec.units.ri.unit.Units.CELSIUS;

@ApplicationScoped
public class TemperatureReading {

    private static final Logger LOGGER = Logger.getLogger(TemperatureReading.class.getName());

    private static final ZoneId UTC = TimeZone.getTimeZone("UTC").toZoneId();

    @Inject
    private DeviceService deviceService;

    @Inject
    private DevicePublisher publisher;

    @Consumer(topics = TEMPERATURE_READING, groupId = "temperature_reading_group")
    public void receive(final JsonObject message) {
        final DeviceDTO dto = JsonUtils.fromJson(message, DeviceDTO.class);
        LOGGER.info("receive new temperature event: " + dto);
        Device device = toDevice(dto);
        if (device.isOverheating()) {
            LOGGER.info("temperature is over, fire an event: " + dto);
            publisher.over(dto);
        }
        deviceService.save(device);
    }

    private Device toDevice(DeviceDTO dto) {
        return new Device(dto.getId(), dto.getId(), toStatus(dto));
    }

    private Status toStatus(DeviceDTO dto) {
        LocalDateTime time = ofInstant(ofEpochMilli(dto.getTimestamp()), UTC);
        Quantity<Temperature> temperature = Quantities.getQuantity(dto.getTemperature(), CELSIUS);
        return Status.of(time, QuantityFormat.getInstance().format(temperature));
    }

}
