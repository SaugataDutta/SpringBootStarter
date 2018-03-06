package com.tgt.mkt.cam.config;

/**
 * Created by z083387 on 8/5/17.
 */
public class KafkaConstants {
    public static final String SECURITY_PROTOCOL="security.protocol";
    public static final String SSL_TRUSTSTORE_LOCATION="ssl.truststore.location";
    public static final String SSL_TRUSTSTORE_PASSWORD="ssl.truststore.password";
    public static final String SSL_KEYSTORE_LOCATION="ssl.keystore.location";
    public static final String SSL_KEYSTORE_PASSWORD="ssl.keystore.password";
    public static final String BROKER_HOSTS="broker.hosts";
    public static final String SERIALIZER_CLASS = "org.apache.kafka.common.serialization.StringSerializer";
    public static final String DESERIALIZER_CALSS = "org.apache.kafka.common.serialization.StringDeserializer";
    public static final String KAFKA_ACKNOWLEDGE = "all";
    public static final int KAFKA_RETRIES = 0;
    public static final String KAFKA_CONSUMER_GROUP = "SimpleConsumer";
}
