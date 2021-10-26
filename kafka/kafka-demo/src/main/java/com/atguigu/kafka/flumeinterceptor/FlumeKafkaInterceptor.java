package com.atguigu.kafka.flumeinterceptor;


import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.interceptor.Interceptor;

import java.util.List;
import java.util.Map;

/**
 * @author yhm
 * @create 2020-12-11 16:49
 * 1. 实现Interceptor接口
 * 2. 实现4个方法
 * 3. 编写intercept(Event event)方法,根据需求添加不同的key,value
 * 4. 编写intercept(List<Event> list)方法,循环调用intercept(Event event)方法
 * 5. 编写静态内部类实现 Builder 实现Builder
 */
public class FlumeKafkaInterceptor implements Interceptor {
    @Override
    public void initialize() {

    }

    /**
     * 处理单条event方法
     * @param event
     * @return
     */
    @Override
    public Event intercept(Event event) {
        // 获取标志位b
        byte b = event.getBody()[0];
        // 获取headers
        Map<String, String> headers = event.getHeaders();
        // 判断b
        if ((b >= 'A' && b <= 'Z') || (b>= 'a' && b <= 'z')){
            // b为字母
            headers.put("topic","first");
        }else if (b >= '0' && b <= '9'){
            // b为数字
            headers.put("topic","second");
        }
        // 将headers放回event
        event.setHeaders(headers);
        return event;
    }

    /**
     * 处理多条event方法
     * @param list
     * @return
     */
    @Override
    public List<Event> intercept(List<Event> list) {
        // 遍历list
        for (Event event : list) {
            // 循环调用intercept(Event event)
            intercept(event);
        }
        return list;
    }

    @Override
    public void close() {

    }

    public static class MyBuilder implements Builder{

        @Override
        public Interceptor build() {
            return new FlumeKafkaInterceptor();
        }

        @Override
        public void configure(Context context) {

        }
    }
}
