package com.test.cfg;

import java.util.Properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "kafka")
public class KafkaProperties {

    private String bootstrap_servers;

    private String key_serializer;

    private String value_serializer;

    private String acks;

    public Properties getProps() {
        Properties p = new Properties();
        p.put("bootstrap.servers", bootstrap_servers);
        p.put("key.serializer", key_serializer);
        p.put("value.serializer", value_serializer);
        p.put("acks", acks);
        return p;
    }

    public String getBootstrap_servers() {
        return bootstrap_servers;
    }

    public void setBootstrap_servers(String bootstrap_servers) {
        this.bootstrap_servers = bootstrap_servers;
    }

    public String getKey_serializer() {
        return key_serializer;
    }

    public void setKey_serializer(String key_serializer) {
        this.key_serializer = key_serializer;
    }

    public String getValue_serializer() {
        return value_serializer;
    }

    public void setValue_serializer(String value_serializer) {
        this.value_serializer = value_serializer;
    }

    public String getAcks() {
        return acks;
    }

    public void setAcks(String acks) {
        this.acks = acks;
    }

}
