package com.atguigu.kafka.interceptor;

import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Map;

/**
 * @author yhm
 * @create 2020-12-11 14:55
 * 1. 实现ProducerInterceptor接口
 * 2. 实现方法onSend,onAcknowledgement,close,configure
 * 3. 在onSend方法中给消息添加时间戳前缀
 */
public class TimeInterceptor implements ProducerInterceptor<String, String> {

    /**
     * 向下发送消息
     *
     * @param record 消息本身
     * @return
     */
    @Override
    public ProducerRecord<String, String> onSend(ProducerRecord<String, String> record) {
        // 给消息添加时间戳
        String value = System.currentTimeMillis() + record.value().toString();
        // 返回一个新的消息,保留原有的主题分区,修改消息value
        return new ProducerRecord<String,String>(record.topic(), record.partition(), record.timestamp(), record.key(), value);

    }

    /**
     * ack回调信息
     *
     * @param metadata  消息元数据
     * @param exception 返回的异常
     */
    @Override
    public void onAcknowledgement(RecordMetadata metadata, Exception exception) {

    }

    /**
     * 关闭
     */
    @Override
    public void close() {

    }

    /**
     * 配置
     *
     * @param configs
     */
    @Override
    public void configure(Map<String, ?> configs) {

    }
}
