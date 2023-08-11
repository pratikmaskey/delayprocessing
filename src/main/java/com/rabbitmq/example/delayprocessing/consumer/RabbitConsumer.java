package com.rabbitmq.example.delayprocessing.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.example.delayprocessing.pojo.EndSystems;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Slf4j
@Component
public class RabbitConsumer {

	@RabbitListener(queues = "delay_queue")
	public void consumeDataRefinementMessages(String message) {

		ObjectMapper objectMapper = new ObjectMapper();
		EndSystems endSystems;
		try {
			 endSystems = objectMapper.readValue(message, EndSystems.class);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}

		log.info("Consuming Message: {}", message.toString());
		log.info("Message time stamp: {} and Current Time: {} ",Instant.ofEpochMilli(endSystems.getTimeStamp()), Instant.now());
	}
}
