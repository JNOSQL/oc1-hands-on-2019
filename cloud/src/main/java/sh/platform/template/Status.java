package sh.platform.template;


import com.fasterxml.jackson.annotation.JsonIgnore;
import tec.units.ri.format.QuantityFormat;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;
import javax.measure.Quantity;
import javax.measure.quantity.Temperature;
import java.time.LocalDateTime;
import java.util.Objects;

public class Status  {

    private static final QuantityFormat QUANTITY_FORMAT = QuantityFormat.getInstance();

    private final LocalDateTime time;

    private final Quantity<Temperature> temperature;

    private Status(LocalDateTime time, String temperature) {
        this.time = time;
        this.temperature = QUANTITY_FORMAT.parse(temperature);
    }

    public LocalDateTime getTime() {
        return time;
    }

    public String getTemperature() {
        return QUANTITY_FORMAT.format(temperature);
    }

    @JsonIgnore
    public boolean isOverheating() {
        return temperature.getValue().doubleValue() > 100;
    }

    @Override
    public String toString() {
        return "TemperatureStatus{" +
                "time=" + time +
                ", temperature=" + temperature +
                '}';
    }

    @JsonbCreator
    public static Status of(@JsonbProperty("time") LocalDateTime time, @JsonbProperty("temperature") String temperature) {
        return new Status(Objects.requireNonNull(time, "time is required"), temperature);
    }

}
