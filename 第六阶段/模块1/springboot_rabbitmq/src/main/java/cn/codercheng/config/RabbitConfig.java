package cn.codercheng.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName RabbitConfig
 * @Description:
 * @Author CoderCheng
 * @Date 2021-01-25 17:06
 * @Version V1.0
 **/
@Configuration
public class RabbitConfig {


    /**
     * 声明普通队列
     *
     * @return
     */
    @Bean
    public Queue queue() {
        Map<String, Object> props = new HashMap<>();
        props.put("x-message-ttl", 10_000);
        props.put("x-dead-letter-exchange", "ex.dlx.shopping");
        props.put("x-dead-letter-routing-key", "key.dlx.shopping");
        return new Queue("queue.shopping", false, false, false, props);
    }


    @Bean
    public Exchange exchange() {
        return new DirectExchange("ex.shopping", false, false, null);
    }


    @Bean
    public Binding binding() {
        return new Binding("queue.shopping", Binding.DestinationType.QUEUE, "ex.shopping", "key.shopping", null);

    }


    @Bean
    public Queue queueDlx() {

        return new Queue("queue.dlx.shopping", false, false, false);

    }


    @Bean
    public Exchange exchangeDlx() {
        return new DirectExchange("ex.dlx.shopping", false, false, null);

    }

    @Bean
    public Binding bindingDlx() {
        return new Binding("queue.dlx.shopping", Binding.DestinationType.QUEUE, "ex.dlx.shopping", "key.dlx.shopping", null);

    }

}
