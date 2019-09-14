package org.jnosql.artemis.demo.se;

import org.aerogear.kafka.cdi.annotation.Consumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.json.JsonObject;

@ApplicationScoped
public class MyListenerService {
	
	private static Logger logger = LoggerFactory.getLogger(MyListenerService.class);
	
	@PostConstruct
	private void init() {
		logger.info("Listener started");
	}

    @Consumer(topics = Config.TOPIC, groupId = Config.GROUP_ID)
    public void receiver(final JsonObject message) {
        final TemperatureReading entity = JsonUtils.fromJson(message, TemperatureReading.class);
        logger.info("That's what I got from the message 1: " + entity);
    }

}
