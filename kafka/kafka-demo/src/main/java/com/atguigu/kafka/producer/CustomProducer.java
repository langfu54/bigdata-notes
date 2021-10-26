package com.atguigu.kafka.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

/**
 * @author yhm
 * @create 2020-12-10 19:40
 * 1. 创建生产者配置对象
 * 2. 添加配置信息
 * 3. 创建生产者对象
 * 4. 调用send发送消息
 * 5. 关闭资源
 */
public class CustomProducer {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        // 1. 创建kafka生产者的配置对象
        Properties properties = new Properties();

        // 2. 给kafka配置对象添加配置信息
        properties.put("bootstrap.servers","hadoop102:9092");
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"hadoop102:9092");
        // key,value序列化(必须)
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        // 设置ack
        properties.put("acks", "all");

        // 重试次数
        properties.put("retries", 3);

        // 批次大小 默认16K
        properties.put("batch.size", 16384);

        // 等待时间
        properties.put("linger.ms", 1);

        // RecordAccumulator缓冲区大小 默认32M
        properties.put("buffer.memory", 33554432);


        // 3. 创建kafka生产者对象
        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<String, String>(properties);

        // 4. 调用send方法,发送消息
        for (int i = 0; i < 10; i++) {

            // 异步发送 默认
//            kafkaProducer.send(new ProducerRecord<>("first","kafka" + i));
            // 同步发送
            kafkaProducer.send(new ProducerRecord<>("first","kafka" + i)).get();

        }

        // 5. 关闭资源
        kafkaProducer.close();
    }
}
