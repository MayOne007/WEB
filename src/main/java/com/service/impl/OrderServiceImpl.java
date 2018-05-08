package com.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.activemq.sender.OrderSender;
import com.dao.OrderDao;
import com.entity.Order;
import com.service.OrderService;
import com.util.GsonUtil;

import core.service.BaseServiceImpl;

@Service
public class OrderServiceImpl extends BaseServiceImpl<Order, Integer> implements OrderService {
	
	@Autowired
	OrderDao orderDao;
	
	@Autowired
	OrderSender orderSender;

	@SuppressWarnings("rawtypes")
	@Override
	public void add(Map order) {
		orderSender.sendText("add", GsonUtil.toJson(order));
	}

}
