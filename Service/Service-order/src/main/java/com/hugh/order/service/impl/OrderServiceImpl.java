package com.hugh.order.service.impl;

import com.hugh.order.client.EduClient;
import com.hugh.order.client.UserClient;
import com.hugh.order.entity.Order;
import com.hugh.order.mapper.OrderMapper;
import com.hugh.order.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hugh.order.utils.OrderUtil;
import com.hugh.servicebase.exception.SchoolException;
import com.hugh.util.vo.CourseOrderVo;
import com.hugh.util.vo.MemberVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author hugh
 * @since 2021-01-22
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private EduClient eduClient;
    @Autowired
    private UserClient userClient;

    @Override
    public String createOrder(String courseId, String userId) {

        // 远程调用获取课程和用户信息
        CourseOrderVo course = eduClient.getCourseForOrderById(courseId);
        MemberVo member = userClient.getUserInfo(userId);

        Order order = new Order();

        order.setCourseCover(course.getCover());
        order.setCourseId(courseId);
        order.setCourseTitle(course.getTitle());
        order.setMobile(member.getMobile());
        order.setMemberId(userId);
        order.setNickname(member.getNickname());
        order.setTeacherName(course.getTeacherName());
        order.setTotalFee(course.getPrice());

        order.setOrderNo(OrderUtil.getOrderNo());
        int count = baseMapper.insert(order);
        if (count==1){
            return order.getOrderNo();
        }else{
            throw new SchoolException(20001,"新建订单失败");
        }
    }
}
