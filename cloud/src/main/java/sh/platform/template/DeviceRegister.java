package sh.platform.template;

import org.aerogear.kafka.cdi.annotation.Consumer;
import sh.platform.template.config.JsonUtils;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.JsonObject;
import java.util.Set;
import java.util.logging.Logger;

import static sh.platform.template.DeviceEvents.TEMPERATURE_READING;

@ApplicationScoped
public class DeviceRegister {

    private static final Logger LOGGER = Logger.getLogger(DeviceRegister.class.getName());

    @Inject
    private DeviceService deviceService;

    @Inject
    private DevicePublisher publisher;

    @Consumer(topics = TEMPERATURE_READING, groupId = "device_register_group")
    public void receive(final JsonObject message) {
        final DeviceDTO dto = JsonUtils.fromJson(message, DeviceDTO.class);
        LOGGER.info("new device:" + dto);
        deviceService.add(dto.getId());
    }


}
