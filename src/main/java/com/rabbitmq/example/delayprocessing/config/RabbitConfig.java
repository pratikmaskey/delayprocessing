package com.rabbitmq.example.delayprocessing.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitConfig {

	@Bean
	CustomExchange delayExchange(){
		Map<String, Object> args = new HashMap<>();
		args.put("x-delayed-type", "direct");
		return new CustomExchange("delay_exchange", "x-delayed-message", true, false, args);
	}

	@Bean
	public Queue delayQueue(){
		return new Queue("delay_queue");
	}

	@Bean
	public Binding delayBinding(CustomExchange delayExchange, Queue delayQueue){
		return BindingBuilder
				.bind(delayQueue)
				.to(delayExchange)
				.with("delay_routing_key")
				.noargs();
	}

/*	@Bean
	public MessageConverter jsonMessageConverter(){
		return new Jackson2JsonMessageConverter();
	}

	@Bean
	public AmqpTemplate mqTemplate(ConnectionFactory connectionFactory){
		final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(jsonMessageConverter());
		return rabbitTemplate;
	}*/

}
