package com.lagou.edu.filter;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

/**
 * 定义全局过滤器，会对所有路由生效
 */
@Slf4j
@Component  // 让容器扫描到，等同于注册了
@RefreshScope
public class RegisterFilter implements GlobalFilter, Ordered {

    @Value("${gateway.register.max.number}")
    private Integer registerMaxNumber;

    @Value("${gateway.register.time}")
    private Integer registerTime;

    private Cache<String, Integer> cache;


    @PostConstruct
    public void init() {
        log.info("初始化cache对象：time:{}", registerTime);
        cache = CacheBuilder.newBuilder().expireAfterAccess(registerTime, TimeUnit.MINUTES).build();
    }


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();
        String clientIp = request.getRemoteAddress().getHostString();

        log.info("request ip:{}; path:{}", clientIp, path);
        log.info("gateway.register.max.number:{}", registerMaxNumber);

        Integer requestSize = cache.getIfPresent(clientIp + path);
        if (requestSize == null) {
            requestSize = 0;
        }
        requestSize++;
        cache.put(clientIp + path, requestSize);
        log.info("url [{}] {}分钟的请求次数为{}", path, registerTime, requestSize);
        if (requestSize > registerMaxNumber) {
            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(HttpStatus.UNAUTHORIZED); // 状态码
            log.debug("=====>IP:{}，已超过请求次数，将被拒绝访问！", clientIp);
            String data = "Request be denied!";
            DataBuffer wrap = response.bufferFactory().wrap(data.getBytes());
            return response.writeWith(Mono.just(wrap));
        }
        return chain.filter(exchange);
    }


    public void setRegisterTime(Integer registerTime) {
        log.info("time has changed:{}", registerTime);
        this.registerTime = registerTime;
        this.init();
    }

    /**
     * 返回值表示当前过滤器的顺序(优先级)，数值越小，优先级越高
     *
     * @return
     */
    @Override
    public int getOrder() {
        return 0;
    }
}
