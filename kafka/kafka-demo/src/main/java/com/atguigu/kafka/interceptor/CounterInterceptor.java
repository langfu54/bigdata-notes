package com.atguigu.kafka.interceptor;

import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Map;

/**
 * @author yhm
 * @create 2020-12-11 15:11
 * 1. 实现ProducerInterceptor接口
 * 2. 实现方法onSend,onAcknowledgement,close,configure
 * 3. 声明属性记录发送成功失败次数
 * 4. 通过返回ack是否出错判断成功失败次数
 * 5. 在close方法中打印成功次数和失败次数
 */
public class CounterInterceptor implements ProducerInterceptor<String,String> {
    // 声明属性 成功次数和失败次数
    private Integer success = 0;
    private Integer failed = 0;

    /**
     * 向下发送消息
     *
     * @param record 消息本身
     * @return
     */
    @Override
    public ProducerRecord<String, String> onSend(ProducerRecord<String, String> record) {
        return record;
    }

    /**
     * ack回调信息
     *
     * @param metadata  消息元数据
     * @param exception 返回的异常
     */
    @Override
    public void onAcknowledgement(RecordMetadata metadata, Exception exception) {
        if (exception == null){
            success++;
        }else{
            failed++;
        }
    }

    /**
     * 关闭
     */
    @Override
    public void close() {
        // 将成功次数和失败次数打印到控制台
        System.out.println("成功次数" + success);
        System.out.println("失败次数" + failed);
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
