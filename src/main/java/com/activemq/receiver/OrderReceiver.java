package com.activemq.receiver;

import java.util.HashMap;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

import com.contant.SystemContant;
import com.entity.Order;
import com.service.OrderService;
import com.util.GsonUtil;
import com.websocket.handler.OrderHander;

public class OrderReceiver extends BaseReceiver{	

	@Autowired
	@Qualifier("activemqConnectionFactory")
	ActiveMQConnectionFactory connectionFactory;
	
	@Autowired
	OrderService orderService;
	
	/**
	 * 处理添加订单的信息
	 * @return
	 */
	@Bean
	public DefaultMessageListenerContainer add() {		
		String prefix = super.getPrefix();
		String queueName = "add";
		DefaultMessageListenerContainer container = new DefaultMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setDestinationName(prefix+queueName);
		container.setMessageListener(new MessageListener() {
			@SuppressWarnings({ "rawtypes", "unchecked" })
			@Override
			public void onMessage(Message message) {
				TextMessage textMessage = (TextMessage) message;
				try {
					logger.info(String.format("从队列接收消息：%s\n%s", prefix + queueName, textMessage.getText()));
					Map orderData = GsonUtil.fromJson(textMessage.getText(), Map.class);
					
					Order order = new Order();
					order.setOrderCode(orderData.get("orderCode").toString());
					orderService.save(order);
					
					String websocket_id = orderData.get(SystemContant.WEBSOCKET_ID).toString();
					Map rt = new HashMap();
					rt.put("type", "add");
					rt.put("isSuccess", true);
					OrderHander.sendAddMessage(websocket_id, GsonUtil.toJson(rt));
				} catch (JMSException e) {
					logger.error("JMS error", e);
				}
			}					
		});
		return container;
	}
	
	
	

}
