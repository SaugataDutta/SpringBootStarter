package com.tgt.mkt.cam.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tgt.mkt.cam.model.KafkaRequestObject;
import com.tgt.mkt.cam.security.AuthenticatedUser;
import com.tgt.mkt.cam.service.KafkaService;
import com.tgt.mkt.cam.utils.KafkaProducerUtil;
import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by z083387 on 8/3/17.
 */

@RestController
@Slf4j
public class KafkaController {


    @Autowired
    KafkaService kafkaService;


    @RequestMapping(path="/get-message", method= RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public KafkaRequestObject getMessageFromTopic(@RequestParam(value ="topic_name") String topic_name, @RequestParam(name = "partition") int partition, @RequestParam(name="offset") long offset) throws
            JsonProcessingException {
        KafkaRequestObject reqObj = kafkaService.readMessageFromTopic(topic_name,partition, offset);
        log.info("Reading Message from kafka Topic: {} at partition {} on offset {}",topic_name,partition,offset);
        log.info("Message Content: {}",new ObjectMapper().writeValueAsString(reqObj));
        return reqObj;
    }
    /*@RequestMapping(path="/send-message", method= RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public KafkaRequestObject sendMessageToTopic(@RequestParam String topic_name,@RequestParam(required = false) String topic_description,@Valid @RequestBody KafkaRequestObject requestObject) {
        log.info("Trying to write message to topic : "+topic_name);
        RecordMetadata responseData= kafkaService.writeMessageToTopic(topic_name,requestObject);
        log.info("Message Sent to Patition: {}, offset: {}",responseData.partition(),responseData.offset());

        return requestObject;
    }*/

    @RequestMapping(path="/send-message", method= RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public KafkaRequestObject sendMessageToTopic(@RequestBody String test) {
        String topic_name = "cgi-asset-process";
        KafkaRequestObject requestObject = KafkaRequestObject.builder().assetId("Asset-ID")
                .assetName("Asset-Name")
                .comment("Comments").build();
        log.info("Trying to write message to topic : "+topic_name);
        RecordMetadata responseData= kafkaService.writeMessageToTopic(topic_name,requestObject);
        log.info("Message Sent to Patition: {}, offset: {}",responseData.partition(),responseData.offset());

        return requestObject;
    }
}
