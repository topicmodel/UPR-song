package com.how2java.tmall.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.how2java.tmall.dao.RightServiceOrderItemDAO;
import com.how2java.tmall.pojo.Order;
import com.how2java.tmall.pojo.RightServiceOrderItem;

@Service
public class RightServiceOrderItemService {

	@Autowired
	RightServiceOrderItemDAO rightServiceOrderItemDAO;
	@Autowired
	RightServiceImageService rightServiceImageService;
	//为订单填充知产服务订单项
	public void fill(List<Order> orders) {
		for (Order order : orders) {
			fill(order);
		}
	}

	public void fill(Order order) {
		List<RightServiceOrderItem> orderItems = listByOrder(order);
		float total = 0;
		int totalNumber = 0;
		for (RightServiceOrderItem item : orderItems) {
			total += item.getNumber() * item.getRightService().getServicePrice();
			totalNumber += item.getNumber();
			rightServiceImageService.setFirstRightServiceImage(item.getRightService());
		}
		order.setTotal(total);
		order.setTotalNumber(totalNumber);
		order.setRightServiceOrderItem(orderItems);
	}

	public List<RightServiceOrderItem> listByOrder(Order order) {
		return rightServiceOrderItemDAO.findByOrderOrderByIdDesc(order);
	}

}
