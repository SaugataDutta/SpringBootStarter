package com.tgt.mkt.cam.config;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static com.netflix.config.ConfigurationManager.getConfigInstance;
import static com.tgt.mkt.cam.config.KafkaConstants.*;

/**
 * Created by z083387 on 8/5/17.
 */

@Slf4j
@Component
public class KafkaConfiguration {

    @Getter
    private String securityProtocol;
    @Getter
    private String sslKeystoreLocation;
    @Getter
    private String sslKeystorePassword;
    @Getter
    private String sslTruststoreLocation;
    @Getter
    private String sslTruststorePassword;

    @Getter
    private String brokerHosts;

    @PostConstruct
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
    }
}
