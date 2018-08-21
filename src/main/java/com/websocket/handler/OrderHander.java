package com.websocket.handler;
import java.io.IOException;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import com.contant.SystemContant;

public class OrderHander implements WebSocketHandler {
    private static final Logger logger = Logger.getLogger(OrderHander.class);

    public static final Vector <WebSocketSession> users = new Vector <>();

    //初次链接成功执行
    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
        logger.info("链接成功......");
        users.add(webSocketSession);
        /*String id = (String) webSocketSession.getAttributes().get(SystemContant.WEBSOCKET_ID);
        if(id!= null){
            webSocketSession.sendMessage(new TextMessage(id + ""));
        }*/
    }

    //接受消息处理消息
    @Override
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) throws Exception {
    	String id = (String) webSocketSession.getAttributes().get(SystemContant.WEBSOCKET_ID);
    	logger.info("接收信息..."+webSocketMessage.getPayload());
    	sendMessageToUser(id,new TextMessage(webSocketMessage.getPayload() + ""));
    }

    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {
        if(webSocketSession.isOpen()){
            webSocketSession.close();
        }
        logger.info("链接出错，关闭链接......");
        users.remove(webSocketSession);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {
        logger.info("链接关闭......");
        users.remove(webSocketSession);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }


    /**
     * 给某个用户发送消息
     *
     * @param userName
     * @param message
     */
    public void sendMessageToUser(String id, TextMessage message) {
        for (WebSocketSession user : users) {
            if (user.getAttributes().get(SystemContant.WEBSOCKET_ID).equals(id)) {
                try {
                    if (user.isOpen()) {
                        user.sendMessage(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
               // break;
            }
        }
    }
    
    /**
     * 给某个用户发送订单提交成功消息
     *
     * @param id
     * @param message
     */
    public static void sendAddMessage(String id, String message) {
    	TextMessage textMessage = new TextMessage(message);
        for (WebSocketSession user : users) {
            //if (user.getAttributes().get(SystemContant.WEBSOCKET_ID).equals(id)) {
                try {
                    if (user.isOpen()) {
                        user.sendMessage(textMessage);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //break;
            //}
        }
    }
}