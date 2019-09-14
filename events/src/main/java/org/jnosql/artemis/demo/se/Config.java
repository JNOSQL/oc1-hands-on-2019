package org.jnosql.artemis.demo.se;


import org.aerogear.kafka.cdi.annotation.KafkaConfig;

@KafkaConfig()
public class Config {

    static final String TOPIC = "temperatureReadings";

    static final String GROUP_ID = "group";
}
