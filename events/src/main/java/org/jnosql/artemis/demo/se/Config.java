package org.jnosql.artemis.demo.se;


import org.aerogear.kafka.cdi.annotation.KafkaConfig;

@KafkaConfig(bootstrapServers = "kafka:9092")
public class Config {

    static final String TOPIC = "temperatureReadings";

    static final String GROUP_ID = "group";
}
