package com.atguigu.kafka.partitioner;

import org.apache.kafka.clients.producer.*;

import java.util.Properties;

/**
 * @author yhm
 * @create 2020-12-11 10:32
 * 1. 创建生产者配置对象
 * 2. 添加配置信息
 * 3. 创建生产者对象
 * 4. 调用send发送消息
 * 5. 关闭资源
 */
public class CustomProducerCallback {

    public static void main(String[] args) throws InterruptedException {
        // 1. 创建kafka生产者的配置对象
        Properties properties = new Properties();

        // 2. 给kafka配置对象添加配置信息
        properties.put("bootstrap.servers", "hadoop102:9092");
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "hadoop102:9092");
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

        // 配置自定义分区器
        properties.put(ProducerConfig.PARTITIONER_CLASS_CONFIG,"com.atguigu.kafka.partitioner.MyPartitioner");


        // 3. 创建kafka生产者对象
        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<String, String>(properties);

        // 4. 调用send方法,发送消息
        for (int i = 0; i < 10; i++) {

            // 添加回调
            kafkaProducer.send(new ProducerRecord<>("first", "atguigu" + i), new Callback() {
                // 该方法在Producer收到ack时调用，为异步调用
                @Override
                public void onCompletion(RecordMetadata metadata, Exception exception) {

                    if (exception == null) {
                        // 没有异常,输出信息到控制台
                        System.out.println("partition->" + metadata.partition() + "  " +  "success->" + metadata.offset());
                    } else {
                        // 出现异常打印
                        exception.printStackTrace();
                    }
                }
            });

        }

        // 5. 关闭资源
        kafkaProducer.close();
    }
}
