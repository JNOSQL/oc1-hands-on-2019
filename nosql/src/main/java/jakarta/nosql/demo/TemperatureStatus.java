package jakarta.nosql.demo;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;
import java.time.LocalDateTime;
import java.util.Objects;

public class TemperatureStatus {

    private final LocalDateTime time;

    private final double temperature;

    private TemperatureStatus(LocalDateTime time, double temperature) {
        this.time = time;
        this.temperature = temperature;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public double getTemperature() {
        return temperature;
    }

    @Override
    public String toString() {
        return "TemperatureStatus{" +
                "time=" + time +
                ", temperature=" + temperature +
                '}';
    }

    @JsonbCreator
    public static TemperatureStatus of(@JsonbProperty("time")  LocalDateTime time, @JsonbProperty("temperature")  double temperature) {
        return new TemperatureStatus(Objects.requireNonNull(time, "time is required"), temperature);
    }

}
