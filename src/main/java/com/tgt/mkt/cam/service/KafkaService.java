package com.tgt.mkt.cam.service;

import com.tgt.mkt.cam.model.KafkaRequestObject;
import com.tgt.mkt.cam.utils.KafkaConsumerUtil;
import com.tgt.mkt.cam.utils.KafkaProducerUtil;
import lombok.Getter;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by z083387 on 8/5/17.
 */

@Service
public class KafkaService {

    /*
    @Getter
    KafkaProducerUtil kafkaProducerUtil;

    @Getter
    KafkaConsumerUtil kafkaConsumerUtil;

    @Autowired
    public KafkaService(KafkaProducerUtil kafkaProducerUtil, KafkaConsumerUtil kafkaConsumerUtil){
        this.kafkaProducerUtil = kafkaProducerUtil;
        this.kafkaConsumerUtil = kafkaConsumerUtil;
    }
    */
    public RecordMetadata writeMessageToTopic(String topicName, KafkaRequestObject requestObject){
        //RecordMetadata responseData = kafkaProducerUtil.sendMessage(topicName,requestObject);
        RecordMetadata responseData = null;
        return responseData;
    }

    public KafkaRequestObject readMessageFromTopic(String topicName, int partition, long  offset){
        //KafkaRequestObject responseData = kafkaConsumerUtil.getMessage(topicName,partition,offset);
        KafkaRequestObject responseData = KafkaRequestObject.builder().assetId("Asset-ID")
                .assetName("Asset-Name")
                .comment("Comments").build();
        return responseData;
    }
}
