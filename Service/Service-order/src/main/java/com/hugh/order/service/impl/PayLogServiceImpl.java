package com.hugh.order.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.wxpay.sdk.WXPayUtil;
import com.hugh.order.entity.Order;
import com.hugh.order.entity.PayLog;
import com.hugh.order.mapper.PayLogMapper;
import com.hugh.order.service.OrderService;
import com.hugh.order.service.PayLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hugh.order.utils.HttpClient;
import com.hugh.servicebase.exception.SchoolException;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 支付日志表 服务实现类
 * </p>
 *
 * @author hugh
 * @since 2021-01-22
 */
@Service
public class PayLogServiceImpl extends ServiceImpl<PayLogMapper, PayLog> implements PayLogService {

    @Autowired
    private OrderService orderService;

    @Override
    public Map createNative(String orderNo) {
        try{
            QueryWrapper<Order> queryWrapper = new QueryWrapper();
            queryWrapper.eq("order_no",orderNo);
            Order order = orderService.getOne(queryWrapper);

            Map map = new HashMap();
            // 设置支付参数
            map.put("appid", "wx74862e0dfcf69954");
            map.put("mch_id", "1558950191");
            map.put("nonce_str", WXPayUtil.generateNonceStr());
            map.put("body", order.getCourseTitle());
            map.put("out_trade_no", orderNo);
            map.put("total_fee", order.getTotalFee().multiply(new BigDecimal("100")).longValue()+"");
            map.put("spbill_create_ip", "127.0.0.1");
            map.put("notify_url", "http://guli.shop/api/order/weixinPay/weixinNotify\n");
            map.put("trade_type", "NATIVE");

            // 访问微信提供的二维码生成地址
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");
            client.setXmlParam(WXPayUtil.generateSignedXml(map, "T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
            client.setHttps(true);  // 开启Https请求
            client.post();
            // 返回第三方数据
            String xml = client.getContent();
            // 对XML数据进行解析
            Map<String,String> resultMap = WXPayUtil.xmlToMap(xml);

            // 结果封装
            Map xmlMap = new HashMap();
            xmlMap.put("out_trade_no", orderNo);
            xmlMap.put("course_id", order.getCourseId());
            xmlMap.put("total_fee", order.getTotalFee());
            xmlMap.put("result_code", resultMap.get("result_code"));    // 二维码状态码
            xmlMap.put("code_url", resultMap.get("code_url"));

            //微信支付二维码2小时过期，可采取2小时未支付取消订单
            //redisTemplate.opsForValue().set(orderNo, map, 120, TimeUnit.MINUTES);
            return xmlMap;

        }catch (Exception e){
            throw new SchoolException(20001,"生成二维码失败");
        }
    }

    @Override
    public Map<String, String> getPayStatus(String orderNo) {
        try {
            //  封装参数
            Map map = new HashMap<>();
            map.put("appid", "wx74862e0dfcf69954");
            map.put("mch_id", "1558950191");
            map.put("out_trade_no", orderNo);
            map.put("nonce_str", WXPayUtil.generateNonceStr());
            //  设置请求
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/orderquery");
            client.setXmlParam(WXPayUtil.generateSignedXml(map, "T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
            client.setHttps(true);
            client.post();
            //  返回第三方的数据
            String xml = client.getContent();
            Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);
            //  转成Map返回
            return resultMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void updateOrderStatus(Map<String, String> statusMap) {
        String orderNo = statusMap.get("out_trade_no");

        QueryWrapper<Order> queryWrapper = new QueryWrapper();
        queryWrapper.eq("order_no",orderNo);
        Order order = orderService.getOne(queryWrapper);

        // 更新订单状态
        order.setStatus(1);
        orderService.updateById(order);

        // 新增支付记录
        PayLog payLog = new PayLog();
        payLog.setOrderNo(orderNo);
        payLog.setPayTime(new Date());
        payLog.setPayType(1);//支付类型
        payLog.setTotalFee(order.getTotalFee());//总金额(分)
        payLog.setTradeState(statusMap.get("trade_state"));//支付状态
        payLog.setTransactionId(statusMap.get("transaction_id"));
        payLog.setAttr(JSONObject.toJSONString(statusMap));
        baseMapper.insert(payLog);//插入到支付日志表
    }
}
