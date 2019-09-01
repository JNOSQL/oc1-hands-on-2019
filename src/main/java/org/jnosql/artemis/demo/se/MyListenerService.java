package org.jnosql.artemis.demo.se;

import org.aerogear.kafka.cdi.annotation.Consumer;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.json.JsonObject;

@ApplicationScoped
public class MyListenerService {

    @PostConstruct
    public void init() {
        System.out.println("start ok");
    }
    @Consumer(topics = Config.TOPIC, groupId = Config.GROUP_ID)
    public void receiver(final JsonObject message) {
        final MyEntity entity = JsonUtils.fromJson(message, MyEntity.class);
        System.out.println("That's what I got from the message 1: " + entity);
    }

}
