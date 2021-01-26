package com.hugh.order.mapper;

import com.hugh.order.entity.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 订单 Mapper 接口
 * </p>
 *
 * @author hugh
 * @since 2021-01-22
 */
@Repository
public interface OrderMapper extends BaseMapper<Order> {

}
