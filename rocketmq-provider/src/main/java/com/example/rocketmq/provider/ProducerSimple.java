package com.example.rocketmq.provider;

import com.example.rocketmq.provider.entity.OrderExt;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;

/**
 * @program: rocketmq-java
 * @ClassName ProducerSimple
 * @description:
 * @author: hujie
 * @create: 2020-05-12 11:25
 **/
@Component
public class ProducerSimple {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    /**
     * 发送同步信息
     * @param topic
     * @param msg
     */
    public void sendSyncMsg(String topic, String msg){
        rocketMQTemplate.syncSend(topic, msg);
    }


    /**
     * 异步发送信息
     * @param topic
     * @param msg
     */
    public void sendAsyncMsg(String topic, String msg){
        rocketMQTemplate.asyncSend(topic, msg, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                //成功调用
                System.out.println("发送成功");
            }

            @Override
            public void onException(Throwable throwable) {
                //失败调用
                System.out.println("发送失败");
            }
        });
    }

    /**
     * 发送单向消息
     * @param topic
     * @param msg
     */
    public void  sendOneWayMsg(String topic, String msg){
        rocketMQTemplate.sendOneWay(topic, msg);
    }


    /**
     * 消息体为对象(自定义传送的消息体)
     * @param topic
     * @param orderExt
     */
    public void sendMsgByJson(String topic, OrderExt orderExt){
        //同步消息，将Object转为json
        rocketMQTemplate.convertAndSend(topic, orderExt);
        System.out.println("send msg:  " + orderExt.toString());
    }


//  ================  发送延迟消息===============

    /**
     * 同步延迟
     * @param topic
     * @param orderExt
     */
    public void sendMsgByJsonDelay(String topic, OrderExt orderExt){
        //发送同步消息，消息内容将OrderExt转为json(这里Message使用的是SPring的Message)
        Message<OrderExt> messageBuilder = MessageBuilder.withPayload(orderExt).build();
        //指定发送超时时间和延迟等级
        rocketMQTemplate.syncSend(topic, messageBuilder, 2000, 3);

        System.out.println("send msg:  " + orderExt.toString());
    }


    /**
     * 异步延迟
     */
    public void sendAsyncMsgByJsonDelay(String topic, OrderExt orderExt) throws JsonProcessingException, RemotingException, MQClientException, InterruptedException {
        //异步延迟发送数据，其中使用的Message使用的是RocketMQ自带的消息体
        String json = rocketMQTemplate.getObjectMapper().writeValueAsString(orderExt);
        org.apache.rocketmq.common.message.Message message = new org.apache.rocketmq.common.message.Message(topic, json.getBytes(Charset.forName("utf-8")));
        //设置延迟等级
        message.setDelayTimeLevel(3);

        //发送异步消息
        rocketMQTemplate.getProducer().send(message, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                System.out.println("异步延迟发送消息成功");
            }

            @Override
            public void onException(Throwable throwable) {
                System.out.println("异步延迟发送消息失败");
            }
        });

        System.out.println("send msg:  " + orderExt.toString());
    }
}
