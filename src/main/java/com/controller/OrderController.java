package com.controller;

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

import com.contant.SystemContant;
import com.service.OrderService;

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
	
		
}