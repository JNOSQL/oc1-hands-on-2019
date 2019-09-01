package org.jnosql.artemis.demo.se;


import org.aerogear.kafka.cdi.annotation.KafkaConfig;

@KafkaConfig(bootstrapServers = "#{KAFKA_SERVICE_HOST}:#{KAFKA_SERVICE_PORT}")
public class Config {

    static final String TOPIC = "topic";

    static final String GROUP_ID = "group";
}
