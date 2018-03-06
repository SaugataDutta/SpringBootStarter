package com.tgt.mkt.cam.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tgt.mkt.cam.config.KafkaConfiguration;
import com.tgt.mkt.cam.model.KafkaRequestObject;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.config.SslConfigs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;
import java.util.Random;

import static com.tgt.mkt.cam.config.KafkaConstants.DESERIALIZER_CALSS;
import static com.tgt.mkt.cam.config.KafkaConstants.KAFKA_CONSUMER_GROUP;
import static com.tgt.mkt.cam.config.KafkaConstants.SERIALIZER_CLASS;

@Slf4j
@Component
public class KafkaConsumerUtil {

    @Getter
    KafkaConfiguration kafkaConfiguration;

    @Autowired
    public KafkaConsumerUtil(KafkaConfiguration kafkaConfiguration){
        this.kafkaConfiguration = kafkaConfiguration;
    }

    public KafkaRequestObject getMessage(String topicName, int partition, long offset){

        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfiguration.getBrokerHosts());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, KAFKA_CONSUMER_GROUP);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "30000");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, DESERIALIZER_CALSS);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, DESERIALIZER_CALSS);

        //configure the following three settings for SSL Encryption
        props.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, kafkaConfiguration.getSecurityProtocol());
        props.put(SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG, kafkaConfiguration.getSslTruststoreLocation());
        props.put(SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG,  kafkaConfiguration.getSslTruststorePassword());

        // configure the following three settings for SSL Authentication
        props.put(SslConfigs.SSL_KEYSTORE_LOCATION_CONFIG, kafkaConfiguration.getSslKeystoreLocation());
        props.put(SslConfigs.SSL_KEYSTORE_PASSWORD_CONFIG, kafkaConfiguration.getSslKeystorePassword());

        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);

        log.info("Subscribed to topic : {}",topicName);
        TopicPartition topicPartition = new TopicPartition(topicName, partition);
        consumer.assign(Arrays.asList(topicPartition));
        consumer.poll(0);
        consumer.seek(topicPartition,offset);


        KafkaRequestObject responseData = null;
        while(true) {
            ConsumerRecords<String, String> records = consumer.poll(100);

            for (ConsumerRecord<String, String> record : records) {
                System.out.printf("offset = %d, key = %s, value = %s\n",
                        record.offset(), record.key(), record.value());
                try {
                    responseData = new ObjectMapper().readValue(record.value(), KafkaRequestObject.class);
                    if(record.offset()==offset)return responseData;;
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }

    }
}