package com.rabbitmq.example.delayprocessing.publisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.example.delayprocessing.pojo.EndSystems;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Random;

@Component
@Slf4j
public class RabbitPublisher {

	@Autowired
	private AmqpTemplate amqpTemplate;

	public static int count = 1;

	@Scheduled(cron = "*/10 * * * * ?")
	public void scheduleMessageToQueue() {

		long delay5 = 60*5*1000;// 5 min delay
		long delay2 = 60*2*1000;// 2 min delay
		Random random = new Random();
		String ip = "192.168.0"+random.nextInt(254);
		EndSystems endSystems = EndSystems.builder().id(count++).ip(ip).type("XOS")
				.timeStamp(Instant.now().toEpochMilli()).build();

		sendMessage(endSystems, delay5);
		sendMessage1(endSystems, delay2);
		sendMessage2(endSystems, delay5);
	}

	public void sendMessage(EndSystems endSystems, long delayTimes) {
		//Send message to delay queue
		ObjectMapper objectMapper = new ObjectMapper();
		String endObject;
		try {
			endObject = objectMapper.writeValueAsString(endSystems);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
		amqpTemplate.convertAndSend("delay_exchange", "delay_routing_key", endObject,
				message -> {
					message.getMessageProperties().setHeader("x-delay", delayTimes);
					return message;
				});
		log.info("Message published0: {} and Time Now: {} ", endObject, Instant.now());
	}

	public void sendMessage1(EndSystems endSystems, long delayTimes) {
		//Send message to delay queue
		ObjectMapper objectMapper = new ObjectMapper();
		String endObject;
		try {
			endObject = objectMapper.writeValueAsString(endSystems);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
		amqpTemplate.convertAndSend("delay_exchange", "delay_routing_key", endObject,
				message -> {
					message.getMessageProperties().setHeader("x-delay", delayTimes);
					return message;
				});
		log.info("Message published1: {} and Time Now: {} ", endObject, Instant.now());
	}

	public void sendMessage2(EndSystems endSystems, long delayTimes) {
		//Send message to delay queue
		ObjectMapper objectMapper = new ObjectMapper();
		String endObject;
		try {
			endObject = objectMapper.writeValueAsString(endSystems);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
		amqpTemplate.convertAndSend("delay_exchange", "delay_routing_key", endObject,
				message -> {
					message.getMessageProperties().setHeader("x-delay", delayTimes);
					return message;
				});
		log.info("Message published2: {} and Time Now: {} ", endObject, Instant.now());
	}


}
