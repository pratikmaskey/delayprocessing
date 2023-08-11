package com.rabbitmq.example.delayprocessing.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.time.Instant;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EndSystems implements Serializable {

	Integer id;
	String ip;
	String type;
	Long timeStamp;
}
