package com.tgt.mkt.cam.config;


import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.ClientNetworkConfig;
import com.hazelcast.core.HazelcastInstance;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HazelcastCacheConfig {

    @Value("${hazelcast.host}")
    private String hzcastHost;
    @Value("${hazelcast.port}")
    private String hzcastPort;
    @Value("${hazelcast.name}")
    private String hzcastName;
    @Value("${hazelcast.password}")
    private String hzcastPassword;


    @Bean
    public HazelcastInstance hazelcastInstance() {
        ClientNetworkConfig networkConfig = new ClientNetworkConfig()
                .addAddress(hzcastHost+":"+hzcastPort);
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.setNetworkConfig(networkConfig).getGroupConfig().setName(hzcastName)
                .setPassword(hzcastPassword);
        return HazelcastClient.newHazelcastClient(clientConfig);
    }
}
