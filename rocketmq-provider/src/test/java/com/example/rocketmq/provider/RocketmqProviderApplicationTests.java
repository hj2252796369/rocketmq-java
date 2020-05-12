package com.example.rocketmq.provider;

import com.example.rocketmq.provider.entity.OrderExt;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.UUID;

@SpringBootTest
class RocketmqProviderApplicationTests {

    @Autowired
    private ProducerSimple producerSimple;
    @Test
    void testSendSyncMsg() {
        producerSimple.sendSyncMsg("my-topic","第一条同步数据");
    }

    @Test
    void testSendAsyncMsg() throws InterruptedException {
        producerSimple.sendAsyncMsg("my-topic","第一条异步数据");
        Thread.sleep(3000);
    }

    @Test
    void testOneWay(){
        producerSimple.sendOneWayMsg("my-topic","第一条单向数据");
    }

    @Test
    void testSendObj(){
        OrderExt orderExt = new OrderExt();
        orderExt.setId("11111");
        orderExt.setMoney(122L);
        orderExt.setTitle("测试对象");
        orderExt.setCreateTime(new Date());
        producerSimple.sendMsgByJson("my-topic-obj", orderExt);
    }

    @Test
    void sendMsg(){

        try {
            testSendSyncMsg();
            testSendAsyncMsg();
            testOneWay();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    /**
     * 发送延迟信息
     */
    @Test
    void sendMsgByJsonDelay(){
        OrderExt orderExt = new OrderExt();
        orderExt.setId(UUID.randomUUID().toString());
        orderExt.setMoney(122L);
        orderExt.setTitle("测试对象");
        orderExt.setCreateTime(new Date());
        producerSimple.sendMsgByJsonDelay("my-topic-obj", orderExt);
        System.out.println("end.........");
    }

    @Test
    void sendAsyncMsgByJsonDelay() throws InterruptedException, RemotingException, MQClientException, JsonProcessingException {
        OrderExt orderExt = new OrderExt();
        orderExt.setId(UUID.randomUUID().toString());
        orderExt.setMoney(122L);
        orderExt.setTitle("测试对象");
        orderExt.setCreateTime(new Date());
        producerSimple.sendAsyncMsgByJsonDelay("my-topic-obj", orderExt);
        Thread.sleep(3000);
        System.out.println("end.........");
    }
}
