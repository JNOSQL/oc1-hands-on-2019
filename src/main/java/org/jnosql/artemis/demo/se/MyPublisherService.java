package org.jnosql.artemis.demo.se;

import org.aerogear.kafka.SimpleKafkaProducer;
import org.aerogear.kafka.cdi.annotation.Producer;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MyPublisherService {

    @Producer
    private SimpleKafkaProducer<Integer, String> producer;

    public void hello() {
        producer.send("myTopic", "My Message");
    }

}
