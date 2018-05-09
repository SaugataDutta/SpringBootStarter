package com.tgt.mkt.cam.config;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static com.netflix.config.ConfigurationManager.getConfigInstance;
import static com.tgt.mkt.cam.config.KafkaConstants.*;

/**
 * Created by z083387 on 8/5/17.
 */

@Slf4j
@Configuration
@Getter
public class KafkaConfiguration {

    @Value("${spring.kafka.security-protocol}")
    private String securityProtocol;
    @Value("${spring.kafka.ssl.keystore-location}")
    private String sslKeystoreLocation;
    @Value("${spring.kafka.ssl.keystore-password}")
    private String sslKeystorePassword;
    @Value("${spring.kafka.ssl.truststore-location}")
    private String sslTruststoreLocation;
    @Value("${spring.kafka.ssl.truststore-password}")
    private String sslTruststorePassword;

    @Value("${spring.kafka.bootstrap-servers}")
    private String brokerHosts;

    /*@PostConstruct
    public void buildCredentials(){
        try{
            securityProtocol = getConfigInstance().getString(SECURITY_PROTOCOL);
            System.out.println("securityProtocol : "+securityProtocol);

            sslKeystoreLocation = getConfigInstance().getString(SSL_KEYSTORE_LOCATION);
            sslKeystorePassword = getConfigInstance().getString(SSL_KEYSTORE_PASSWORD);

            sslTruststoreLocation = getConfigInstance().getString(SSL_TRUSTSTORE_LOCATION);
            sslTruststorePassword = getConfigInstance().getString(SSL_TRUSTSTORE_PASSWORD);

            brokerHosts = getConfigInstance().getString(BROKER_HOSTS);
            System.out.println("brokerHosts : "+brokerHosts);
        }catch(Exception e){

        }
    }*/
}