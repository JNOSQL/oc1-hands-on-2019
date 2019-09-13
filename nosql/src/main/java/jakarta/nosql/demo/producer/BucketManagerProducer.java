package jakarta.nosql.demo.producer;

import jakarta.nosql.keyvalue.BucketManager;
import org.eclipse.jnosql.diana.redis.keyvalue.RedisBucketManagerFactory;
import org.eclipse.jnosql.diana.redis.keyvalue.RedisConfiguration;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;

@ApplicationScoped
class BucketManagerProducer {

    private static final String BUCKET = "olympus";

    private RedisConfiguration configuration;

    private RedisBucketManagerFactory managerFactory;

    @PostConstruct
    public void init() {
        configuration = new RedisConfiguration();
        managerFactory = configuration.get();
    }


    @Produces
    public BucketManager getManager() {
        return managerFactory.getBucketManager(BUCKET);
    }

    public void destroy(@Disposes BucketManager manager) {
        manager.close();
    }

}
