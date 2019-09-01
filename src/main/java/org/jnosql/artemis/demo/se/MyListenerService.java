package org.jnosql.artemis.demo.se;

import org.aerogear.kafka.cdi.annotation.Consumer;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MyListenerService {

    @Consumer(topics = "myTopic", groupId = "myGroupID")
    public void receiver(final String message) {
        System.out.println("That's what I got: " + message);
    }

}
