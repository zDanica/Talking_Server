package edu.zh.mq.consumer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.zh.config.Conversation;

@Component("consumerListener")
public class ConsumerListener{
	Logger log = LogManager.getLogger(getClass());

	@Autowired
	Conversation conversation;
	
	public void handleMessage(String message) {
		log.info("TextMessage: {}", message);
		String backMsg = conversation.sendMsg(message);
		log.info("BackMessage: {}", backMsg);
	}
}