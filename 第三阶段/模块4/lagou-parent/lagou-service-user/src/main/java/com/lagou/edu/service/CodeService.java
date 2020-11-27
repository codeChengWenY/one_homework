package com.lagou.edu.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @ClassName CodeService
 * @Description:
 * @Author CoderCheng
 * @Date 2020-11-27 11:34
 * @Version V1.0
 **/
@FeignClient(value = "lagou-service-code", path = "/code")
public interface CodeService {

    @GetMapping(value = "/validate/{email}/{code}")
    Integer validate(@PathVariable("email") String email,
                     @PathVariable("code") String code);

}
