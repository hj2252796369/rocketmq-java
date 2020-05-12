package com.example.rocketmq.consumer.service;

import org.apache.rocketmq.common.message.MessageExt;
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
public class ConsumerSimpleRetry implements RocketMQListener<MessageExt> {

    @Override
    public void onMessage(MessageExt messageExt) {
        //去除重试的次数
        int reconsumeTimes = messageExt.getReconsumeTimes();
        //当大于一定的值后，将消息写入数据，由单独的程序或者人工处理
        if(reconsumeTimes >= 2){
            //将消息写入数据库，之后正常返回
            System.out.println("重试次数大于两次，写入数据路");
            return ;
        }
        throw new RuntimeException(String.format("第"+reconsumeTimes+"次失败重试"));

    }
}
