package sh.platform.template.config;

import jakarta.nosql.keyvalue.BucketManager;
import org.eclipse.jnosql.diana.redis.keyvalue.RedisBucketManagerFactory;
import org.eclipse.jnosql.diana.redis.keyvalue.RedisConfiguration;
import sh.platform.config.Config;
import sh.platform.config.Redis;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import java.util.Set;

@ApplicationScoped
public class RedisConfig {

    public static final String DEVICE_BUCKET = "device";

    private RedisBucketManagerFactory bucketFactory;

    @PostConstruct
    public void init() {
        Config config = new Config();
        final Redis redis = config.getCredential("redis", Redis::new);
        RedisConfiguration configuration = new RedisConfiguration();
        bucketFactory = configuration.get(redis.get());
    }

    @Produces
    @ApplicationScoped
    BucketManager getManagerDevice() {
        return bucketFactory.getBucketManager(DEVICE_BUCKET);
    }

    @Produces
    @ApplicationScoped
    Set<String> getDevices() {
        return bucketFactory.getSet("devices", String.class);
    }


    void destroy(@Disposes @Any BucketManager manager) {
        manager.close();
    }

}
