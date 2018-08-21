package com.websocket.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;

import com.contant.SystemContant;

public class OrderInterceptor implements org.springframework.web.socket.server.HandshakeInterceptor {

    //初次握手访问前
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Map<String, Object> map) throws Exception {
    	if (request instanceof ServletServerHttpRequest) {
            	HttpServletRequest servletRequest = ((ServletServerHttpRequest) request).getServletRequest();
            	String id = servletRequest.getSession().getId();      
                map.put(SystemContant.WEBSOCKET_ID,id);
                servletRequest.getSession().setAttribute(SystemContant.WEBSOCKET_ID, "111"); 
        }
        return true;
    }

     //初次握手访问后
    @Override
    public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {

    }
}