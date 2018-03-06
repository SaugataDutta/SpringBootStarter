/**
 * Created by z083387 on 8/3/17.
 */

package com.tgt.mkt.cam.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tgt.mkt.cam.config.KafkaConfiguration;
import com.tgt.mkt.cam.model.KafkaRequestObject;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.config.SslConfigs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static com.tgt.mkt.cam.config.KafkaConstants.KAFKA_ACKNOWLEDGE;
import static com.tgt.mkt.cam.config.KafkaConstants.KAFKA_RETRIES;
import static com.tgt.mkt.cam.config.KafkaConstants.SERIALIZER_CLASS;

@Slf4j
@Component
public class KafkaProducerUtil {

    @Getter
    KafkaConfiguration kafkaConfiguration;

    @Autowired
    public KafkaProducerUtil(KafkaConfiguration kafkaConfiguration){
        this.kafkaConfiguration = kafkaConfiguration;
    }

    public RecordMetadata sendMessage(String topicName, KafkaRequestObject requestObject) {

        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfiguration.getBrokerHosts());

        //configure the following settings for SSL Encryption
        props.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, kafkaConfiguration.getSecurityProtocol());
        props.put(SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG, kafkaConfiguration.getSslTruststoreLocation());
        props.put(SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG, kafkaConfiguration.getSslTruststorePassword());

        // configure the following settings for SSL Authentication
        props.put(SslConfigs.SSL_KEYSTORE_LOCATION_CONFIG, kafkaConfiguration.getSslKeystoreLocation());
        props.put(SslConfigs.SSL_KEYSTORE_PASSWORD_CONFIG, kafkaConfiguration.getSslKeystorePassword());
        //props.put(SslConfigs.SSL_KEY_PASSWORD_CONFIG, "dam_Certificate1234");

        props.put(ProducerConfig.ACKS_CONFIG, KAFKA_ACKNOWLEDGE);
        props.put(ProducerConfig.RETRIES_CONFIG, KAFKA_RETRIES);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, SERIALIZER_CLASS);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, SERIALIZER_CLASS);

        Producer<String, String> producer = new KafkaProducer<String, String>(props);
        TestCallback callback = new TestCallback();
        Random rnd = new Random();
        String messageBody = null;
        try {
            messageBody = new ObjectMapper().writeValueAsString(requestObject);
        } catch (JsonProcessingException e) {
            log.error("Error While Parsing JSON during pushing to kafka {}", e.getMessage());
        }
        log.info("Trying to write message to topic : "+topicName);
        ProducerRecord<String, String> data = new ProducerRecord<String, String>(
                topicName, "Asset-Event-",messageBody );
        RecordMetadata responseData = null;
        try {
            responseData = producer.send(data, callback).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        producer.close();
        return responseData;
    }


    private static class TestCallback implements Callback {
        @Override
        public void onCompletion(RecordMetadata recordMetadata, Exception e) {
            if (e != null) {
                log.error("Error while producing message to topic :" + recordMetadata);
                log.error(e.getMessage());
            }
        }
    }

}