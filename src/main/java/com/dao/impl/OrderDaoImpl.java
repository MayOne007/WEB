package com.dao.impl;

import org.springframework.stereotype.Repository;

import com.dao.OrderDao;
import com.entity.Order;

import core.dao.BaseDaoImpl;

@Repository
public class OrderDaoImpl extends BaseDaoImpl<Order, Integer> implements OrderDao {

}
