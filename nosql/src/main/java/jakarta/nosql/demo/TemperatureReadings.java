package jakarta.nosql.demo;

import jakarta.nosql.mapping.Entity;
import jakarta.nosql.mapping.Id;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;
import java.util.Objects;

@Entity
public class TemperatureReadings {

    @Id
    private String id;

    private TemperatureStatus status;

    @Deprecated
    TemperatureReadings() {
    }

    @JsonbCreator
    public TemperatureReadings(@JsonbProperty("id") String id, @JsonbProperty("status") TemperatureStatus status) {
        this.id = Objects.requireNonNull(id, "id is required");
        this.status = Objects.requireNonNull(status, "status is required");
    }

    public String getId() {
        return id;
    }

    public TemperatureStatus getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TemperatureReadings that = (TemperatureReadings) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TemperatureReadings{" +
                "id='" + id + '\'' +
                ", status=" + status +
                '}';
    }
}
