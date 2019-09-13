package jakarta.nosql.demo;

import jakarta.nosql.mapping.Entity;
import jakarta.nosql.mapping.Id;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;
import java.util.Objects;

@Entity
public class Device {

    @Id
    private String id;

    private String name;

    private TemperatureStatus lastStatus;

    Device() {
    }

    @JsonbCreator
    public Device(@JsonbProperty("id") String id, @JsonbProperty("name") String name, @JsonbProperty("lastStatus") TemperatureStatus lastStatus) {
        this.id = id;
        this.name = name;
        this.lastStatus = lastStatus;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public TemperatureStatus getLastStatus() {
        return lastStatus;
    }

    public void update(TemperatureStatus temperature) {
        this.lastStatus = temperature;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Device device = (Device) o;
        return Objects.equals(id, device.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Device{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", lastStatus=" + lastStatus +
                '}';
    }
}
