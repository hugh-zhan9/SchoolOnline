package com.hugh.order.service;

import com.hugh.order.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author hugh
 * @since 2021-01-22
 */
public interface OrderService extends IService<Order> {

    String createOrder(String courseId, String userId);
}
