package com.example.rocketmq.consumer.service;

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
@RocketMQMessageListener(topic = "my-topic", consumerGroup = "demo-consumer-group")
public class ConsumerSimple implements RocketMQListener<String> {
    @Override
    public void onMessage(String s) {
        System.out.println("==========接受对象是字符串对象==========");
        System.out.println("接受的RocketMQ信息：  " + s);
    }
}
