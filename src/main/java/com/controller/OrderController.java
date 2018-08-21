package com.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.contant.SystemContant;
import com.service.OrderService;
import com.websocket.handler.OrderHander;

@Controller("orderController")
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private OrderService orderService;
	
	@RequestMapping(value="list", method = RequestMethod.GET)
	public Object index(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("/order/list");
		return mv;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ResponseBody
	@RequestMapping(value="add", method = RequestMethod.POST)
	public Object add(HttpServletRequest request) {
		Map order = new HashMap();
		order.put(SystemContant.WEBSOCKET_ID, request.getSession().getId());
		order.put("orderCode",UUID.randomUUID().toString().replaceAll("-", ""));
		orderService.add(order);
		
		Map rt = new HashMap();
		rt.put("isSuccess", true);
		return rt;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ResponseBody
	@RequestMapping(value="push", method = RequestMethod.POST)
	public Object push(String msg, String sessionId) {	
		for (WebSocketSession user : OrderHander.users) {
				if(user.getAttributes().get(SystemContant.WEBSOCKET_ID).equals(sessionId)) {
					try {
	                    if (user.isOpen()) {
	                        user.sendMessage(new TextMessage(msg));
	                    }
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
				}         
        }
		
		Map rt = new HashMap();
		rt.put("isSuccess", true);
		return rt;
	}
		
}