package sh.platform.template;

import org.aerogear.kafka.SimpleKafkaProducer;
import org.aerogear.kafka.cdi.annotation.Producer;
import sh.platform.template.config.JsonUtils;

import javax.enterprise.context.ApplicationScoped;
import javax.json.JsonObject;

import java.util.logging.Logger;

import static sh.platform.template.DeviceEvents.DEVICE_OVER_TEMPERATURE;
import static sh.platform.template.DeviceEvents.TEMPERATURE_READING;

@ApplicationScoped
class DevicePublisher {

    private static final Logger LOGGER = Logger.getLogger(DevicePublisher.class.getName());

    @Producer
    private SimpleKafkaProducer<Integer, JsonObject> producer;

    @Producer
    private SimpleKafkaProducer<Integer, String> deviceUpdate;

    public void read(DeviceDTO deviceDTO) {
        producer.send(TEMPERATURE_READING, JsonUtils.toJson(deviceDTO));
        LOGGER.info("new read event fire: " + deviceDTO);
    }

    public void over(DeviceDTO deviceDTO) {
        producer.send(DEVICE_OVER_TEMPERATURE, JsonUtils.toJson(deviceDTO));
        LOGGER.info("new over event fire: " + deviceDTO);
    }

}
