package com.stip.net.kafka.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.stip.net.kafka.dto.SampleMessage;

/**
*
* @Title:
* @date：2018年11月12日-下午1:33:32
* @author：cja
*
*/
@Service
public class KafkaProducer{
	
	@Autowired
	private KafkaTemplate<Object, SampleMessage> kafkaTemplate;

	/**
	 * 开奖消息生产者
	 * @param message
	 */
	public void send(SampleMessage message) {
		kafkaTemplate.send("start_prize", message);
		System.out.println("Sent sample message [" + message + "]");
	}
	
}
