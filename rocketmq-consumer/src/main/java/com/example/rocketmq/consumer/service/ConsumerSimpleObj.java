package com.example.rocketmq.consumer.service;

import com.example.rocketmq.consumer.entity.OrderExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @program: rocketmq-java
 * @ClassName ConsumerSimple
 * @description:
 * @author: hujie
 * @create: 2020-05-12 11:54
 **/
@Component
@RocketMQMessageListener(topic = "my-topic-obj", consumerGroup = "demo-consumer-group-obj")
public class ConsumerSimpleObj implements RocketMQListener<OrderExt> {

    /**
     * 接受到信息调用此方法
     * @param orderExt
     */
    @Override
    public void onMessage(OrderExt orderExt) {
        System.out.println("============接受对象信息============");
        System.out.println(orderExt.toString());
    }
}
