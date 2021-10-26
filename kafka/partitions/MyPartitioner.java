package com.atguigu.kafka.partitions;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;

import java.util.Map;

/**
 * @author yhm
 * @create 2021-06-01 15:40
 *
 *  1. 继承接口Partitioner
 *  2. 实现接口的抽象方法
 */
public class MyPartitioner implements Partitioner {

    @Override
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
        // 需求: 如果消息中包含hello 发往0号分区,不包含发往1号分区
        // 获取消息
        String log = value.toString();
        // 判断包含hello
        return log.contains("hello") ? 0:1;
    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> configs) {

    }
}
