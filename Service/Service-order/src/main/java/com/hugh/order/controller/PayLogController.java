package com.hugh.order.controller;


import com.hugh.order.service.PayLogService;
import com.hugh.util.ReturnMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author hugh
 * @since 2021-01-22
 */
@Api(description = "支付记录接口")
@CrossOrigin
@RestController
@RequestMapping("/edu/pay-log")
public class PayLogController {

    @Autowired
    private PayLogService payLogService;

    @GetMapping("getNative/{orderNo}")
    public ReturnMessage createNative(@PathVariable String orderNo){
        Map map = payLogService.createNative(orderNo);
        return ReturnMessage.ok().data(map);
    }

    @ApiOperation("查询支付状态")
    @GetMapping("getPayStatus/{orderNo}")
    public ReturnMessage getPayStatus(@PathVariable String orderNo){
        // 查询支付状态
        Map<String,String> statusMap = payLogService.getPayStatus(orderNo);
        if(statusMap==null){
            return ReturnMessage.ng().message("支付出错");
        }else if(statusMap.get("trade_state").equals("SUCCESS")){
            // 添加支付记录到支付记录表，更新订单状态
            payLogService.updateOrderStatus(statusMap);
            return ReturnMessage.ok().message("支付成功");
        }else{
            return ReturnMessage.pash();
        }
    }
}

