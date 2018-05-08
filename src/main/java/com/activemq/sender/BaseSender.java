package com.activemq.sender;

import java.io.Serializable;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.log4j.Logger;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import com.util.GsonUtil;

/**
 * 消息发送者
 */
public class BaseSender<T> {
	
	public final Logger logger = Logger.getLogger(this.getClass());
	
	private String prefix;

	public void setPrefix(String prefix) {
		if (prefix == null || prefix.trim().equals(""))
			this.prefix = "";
		else
			this.prefix = prefix.trim() + ".";
	}

	public String getPrefix() {
		return prefix;
	}

	@Resource(name = "activemqQueueTemplate")
	private JmsTemplate sender;

	public void sendText(String queueName, String message) {
		logger.info(String.format("发送到消息队列：%s\n%s", prefix + queueName, message));
		sender.send(prefix + queueName, new TextMessage(message));
	}
	
	public void sendObject(String queueName, ObjectMessage obj) {
		logger.info(String.format("发送到消息队列：%s\n%s", prefix + queueName, GsonUtil.toJson(obj)));
		sender.send(prefix + queueName, obj);
	}

}

class TextMessage implements MessageCreator {

	private String message;

	public TextMessage(String message) {
		this.message = message;
	}

	@Override
	public Message createMessage(Session session) throws JMSException {
		return session.createTextMessage(message);
	}

}

class ObjectMessage implements MessageCreator {

	private Serializable message;

	public ObjectMessage(Serializable obj) {
		this.message = obj;
	}

	@Override
	public Message createMessage(Session session) throws JMSException {
		return session.createObjectMessage(message);
	}

}
