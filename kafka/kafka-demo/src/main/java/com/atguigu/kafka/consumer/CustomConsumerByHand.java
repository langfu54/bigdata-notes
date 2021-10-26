package com.atguigu.kafka.consumer;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;

import java.util.Arrays;
import java.util.Map;
import java.util.Properties;

/**
 * @author yhm
 * @create 2020-12-11 13:55
 * 1. 修改自动提交offset为手动
 * 2. 在业务代码完成之后手动提交offset
 */
public class CustomConsumerByHand {

    public static void main(String[] args) {
        // 1. 创建kafka消费者配置类
        Properties properties = new Properties();
        // 2. 添加配置参数
        // 添加连接
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "hadoop102:9092");

        // 配置序列化 必须
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        // 配置消费者组
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "test");
        // 是否自动提交offset
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        // 提交offset的时间周期
        properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");

        //3. 创建kafka消费者
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);

        //4. 设置消费主题  形参是列表
        consumer.subscribe(Arrays.asList("first"));

        //5. 消费数据
        while (true){
            // 每100ms消费一次数据
            ConsumerRecords<String, String> consumerRecords = consumer.poll(100);
            for (ConsumerRecord<String, String> record : consumerRecords) {
                System.out.printf("partition = %d,offset = %d,key = %s,value = %s%n",record.partition(),record.offset(),record.key(),record.value());
            }
            // 同步提交offset
            //consumer.commitSync();

            // 异步提交offset
            consumer.commitAsync(new OffsetCommitCallback() {
                /**
                 * 回调函数输出
                 * @param offsets   offset信息
                 * @param exception 异常
                 */
                @Override
                public void onComplete(Map<TopicPartition, OffsetAndMetadata> offsets, Exception exception) {
                    // 如果出现异常打印
                    if (exception != null ){
                        System.err.println("Commit failed for " + offsets);
                    }
                }
            });
        }

    }
}
