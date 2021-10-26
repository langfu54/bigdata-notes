package com.atguigu.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Properties;

/**
 * @author yhm
 * @create 2021-06-02 10:37
 */
public class CustomConsumer1 {
    public static void main(String[] args) {
        // 1. 创建配置对象
        Properties properties = new Properties();

        // 2. 给配置对象添加属性
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"hadoop102:9092");

        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");


        // 配置消费者组
        properties.put(ConsumerConfig.GROUP_ID_CONFIG,"test");

        // 修改分区分配规则
        ArrayList<String> strings1 = new ArrayList<>();
        strings1.add("org.apache.kafka.clients.consumer.RoundRobinAssignor");
        strings1.add("org.apache.kafka.clients.consumer.StickyAssignor");
        properties.put(ConsumerConfig.PARTITION_ASSIGNMENT_STRATEGY_CONFIG,strings1);

        // 3. 创建kafka消费者对象
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(properties);

        // 4. 配置主题
        ArrayList<String> strings = new ArrayList<>();
        strings.add("first1");
        consumer.subscribe(strings);

        // 5. 拉取数据
        while (true) {
            ConsumerRecords<String, String> consumerRecords = consumer.poll(Duration.ofSeconds(1));

            // 6. 读取数据
            for (ConsumerRecord<String, String> consumerRecord : consumerRecords) {
                System.out.println(consumerRecord);
            }
        }
    }
}
