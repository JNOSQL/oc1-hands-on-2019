package sh.platform.template.config;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonWriter;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Objects;
import javax.json.bind.config.PropertyVisibilityStrategy;

public final class JsonUtils {

    private static final Jsonb JSONB;
    static {
        JsonbConfig config = new JsonbConfig().withPropertyVisibilityStrategy(new PrivateVisibilityStrategy());
        JSONB = JsonbBuilder.newBuilder().withConfig(config).build();
    }

    private JsonUtils() {
    }

    public static <T> JsonObject toJson(T entity) {
        Objects.requireNonNull(entity, "entity is required");
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        JSONB.toJson(entity, output);
        InputStream stream = new ByteArrayInputStream(output.toByteArray());
        JsonReader reader = Json.createReader(stream);
        return reader.readObject();
    }
    public static <T> T fromJson(JsonObject jsonObject, Class<T> type) {
        Objects.requireNonNull(jsonObject, "jsonObject is required");
        Objects.requireNonNull(type, "type is required");
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        final JsonWriter writer = Json.createWriter(output);
        writer.write(jsonObject);
        InputStream stream = new ByteArrayInputStream(output.toByteArray());
        return JSONB.fromJson(stream, type);
    }
    static class PrivateVisibilityStrategy implements PropertyVisibilityStrategy {
        @Override
        public boolean isVisible(Field field) {
            return true;
        }
        @Override
        public boolean isVisible(Method method) {
            return true;
        }
    }
}