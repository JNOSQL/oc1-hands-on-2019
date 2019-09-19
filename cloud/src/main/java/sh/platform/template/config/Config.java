package sh.platform.template.config;


import org.aerogear.kafka.cdi.annotation.KafkaConfig;

@KafkaConfig(bootstrapServers = "#{kafka_host}:#{kafka_port}")
//@KafkaConfig(bootstrapServers = "localhost:9092")
public class Config {

}
