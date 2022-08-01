package cn.itcast.order.service;

import cn.itcast.order.mapper.OrderMapper;
import cn.itcast.order.pojo.Order;
import cn.itcast.order.pojo.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@Service
public class OrderService {

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private RestTemplate restTemplate;

    public Order queryOrderById(Long orderId) {
        // 1.查询订单
        Order order = orderMapper.findById(orderId);

        // 2.发送请求，查询user对象！
        // String url = "http://localhost:8081/user/" + order.getUserId();
        // 利用在eureka中注册的对象代替硬编码的地址
        // eureka注册的相当于就是一个一个实例和地址之间的映射关系！
        String url = "http://userservice/user/" + order.getUserId();
        User user = restTemplate.getForObject(url, User.class);

        // 3.将user对象发给order对象
        order.setUser(user);

        // 4.返回
        return order;
    }
}
