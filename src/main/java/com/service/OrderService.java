package com.service;

import java.util.Map;

import com.entity.Order;

import core.service.BaseService;

public interface OrderService extends BaseService<Order, Integer> {
	
	@SuppressWarnings("rawtypes")
	public void add(Map order);
	
}
