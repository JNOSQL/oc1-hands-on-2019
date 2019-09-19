package sh.platform.template;

import jakarta.nosql.mapping.keyvalue.KeyValueTemplate;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.Duration;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@ApplicationScoped
public class DeviceService {

    private static final Duration TTL = Duration.ofMinutes(5);

    @Inject
    private KeyValueTemplate template;

    @Inject
    private Set<String> devices;


    public void save(Device device) {
        this.template.put(device, TTL);
    }

    public List<String> getDevices() {
        return devices.stream().collect(Collectors.toList());
    }

    public void add(String id) {
        devices.add(id);
    }

    public Iterable<Device> getStatus() {
        return template.get(getDevices(), Device.class);
    }
}