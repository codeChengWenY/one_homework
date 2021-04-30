package com.lagou.edu.user.api.remote;

import com.lagou.edu.user.api.dto.WeixinDTO;
import edu.response.ResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "edu-user-boot", path = "/user/weixin")
public interface UserWeixinRemoteService {


}
