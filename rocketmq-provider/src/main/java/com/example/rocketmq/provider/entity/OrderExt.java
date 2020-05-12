package com.example.rocketmq.provider.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: rocketmq-java
 * @ClassName OrderExt
 * @description:
 * @author: hujie
 * @create: 2020-05-12 13:39
 **/
@Data
@NoArgsConstructor
@ToString
public class OrderExt implements Serializable {

    private String id;
    private Date createTime;
    private Long money;
    private String title;

}
