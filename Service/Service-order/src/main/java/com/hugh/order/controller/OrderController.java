package com.hugh.order.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hugh.order.entity.Order;
import com.hugh.order.service.OrderService;
import com.hugh.util.JwtUtils;
import com.hugh.util.ReturnMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLTransactionRollbackException;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author hugh
 * @since 2021-01-22
 */
@Api(description = "订单控制接口")
@RestController
@RequestMapping("/edu/order")
@CrossOrigin
public class OrderController {

    @Autowired
    private OrderService orderService;

    @ApiOperation("订单生成接口")
    @PostMapping("createOrder/{courseId}")
    public ReturnMessage createOrder(@PathVariable String courseId, HttpServletRequest request){
        String userId = JwtUtils.getMemberIdByJwtToken(request);
        String orderNo = orderService.createOrder(courseId,userId);
        return ReturnMessage.ok().data("orderId",orderNo);
    }

    @ApiOperation("根据订单号查询订单信息")
    @GetMapping("getOrderInfo/{orderNo}")
    public ReturnMessage getOrderInfo (@PathVariable String orderNo){
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("order_no",orderNo);
        Order order = orderService.getOne(queryWrapper);
        return ReturnMessage.ok().data("order",order);
    }

    @ApiOperation("获取订单支付状态")
    @GetMapping("getOrderStatus/{courseId}/{userId}")
    public boolean getOrderStatus(@PathVariable("courseId") String courseId,@PathVariable("userId") String userId){
        QueryWrapper<Order> queryWrapper = new QueryWrapper();
        queryWrapper.eq("course_id",courseId);
        queryWrapper.eq("member_id",userId);
        queryWrapper.eq("status",1);
        int count = orderService.count(queryWrapper);
        if (count>0){
            return true;
        }else{
            return false;
        }

    }
}

