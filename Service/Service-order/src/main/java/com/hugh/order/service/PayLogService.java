package com.hugh.order.service;

import com.hugh.order.entity.PayLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author hugh
 * @since 2021-01-22
 */
public interface PayLogService extends IService<PayLog> {

    Map createNative(String orderNo);

    Map<String, String> getPayStatus(String orderNo);

    void updateOrderStatus(Map<String, String> statusMap);
}
