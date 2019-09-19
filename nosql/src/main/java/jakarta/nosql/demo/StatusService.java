package jakarta.nosql.demo;

import jakarta.nosql.mapping.Database;
import jakarta.nosql.mapping.DatabaseType;
import jakarta.nosql.mapping.keyvalue.KeyValueTemplate;

import javax.inject.Inject;
import java.time.Duration;
import java.util.Optional;

public class StatusService {

	private static final Duration DEFAULT_DURATION = Duration.ofMinutes(5L);

	@Inject
	@Database(value = DatabaseType.KEY_VALUE, provider = BucketManagerProducer.STATUS_BUCKET)
	private KeyValueTemplate template;

	public void save(TemperatureReadings temperature) {
		this.template.put(temperature, DEFAULT_DURATION);
	}

	public Optional<TemperatureReadings> find(String deviceId) {
		return template.get(deviceId, TemperatureReadings.class);
	}

}
