package jakarta.nosql.demo;

import jakarta.nosql.mapping.Database;
import jakarta.nosql.mapping.DatabaseType;
import jakarta.nosql.mapping.keyvalue.KeyValueTemplate;

import javax.inject.Inject;
import java.util.Optional;

public class DeviceService {

    @Inject
    @Database(value = DatabaseType.KEY_VALUE, provider = BucketManagerProducer.DEVICE_BUCKET)
    private KeyValueTemplate template;


    public void save(Device device) {
        this.template.put(device);
    }

    public Optional<Device> find(String deviceId) {
        return template.get(deviceId, Device.class);
    }
}
