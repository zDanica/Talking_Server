package edu.zh.mq.consumer;

import javax.jms.Queue;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.listener.MessageListenerContainer;
import org.springframework.jms.listener.adapter.MessageListenerAdapter;
import org.springframework.jms.support.converter.SimpleMessageConverter;

@Configuration("cmqconfig")
public class ActiveMQConfig {
	public static final String QUEUE_HELLO = "queue.hello";
	
	public CachingConnectionFactory connectionFactory(ActiveMQConnectionFactory connectionFactory) {
		CachingConnectionFactory factory = new CachingConnectionFactory(connectionFactory);
		return factory;
	}
	
	@Bean(name="chelloQueue")
	public Queue helloQueue() {
		return new ActiveMQQueue(QUEUE_HELLO);
	}
	
	@Bean
	public ConsumerListener textConsumerListener() {
		return new ConsumerListener();
	}
	
	@Bean(name="ctextMessageListenerAdapter")
	public MessageListenerAdapter messageListenerAdapter() {
		MessageListenerAdapter adapter = new MessageListenerAdapter();
		adapter.setMessageConverter(new SimpleMessageConverter());
		adapter.setDelegate(textConsumerListener());
		return adapter;
	}
	
	@Bean
	public MessageListenerContainer messageListenerContainer(ActiveMQConnectionFactory connectionFactory) {
		DefaultMessageListenerContainer container = new DefaultMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setDestination(helloQueue());
		container.setConcurrentConsumers(3);//控制同时启动几个concurrent listener threads
		container.setMessageListener(messageListenerAdapter());
		return container;
	}
}