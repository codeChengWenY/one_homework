package com.lagou.edu.user.api.remote;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lagou.edu.user.api.dto.UserDTO;
import com.lagou.edu.user.api.param.UserQueryParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "edu-user-boot", path = "/user")
public interface UserRemoteService {



    @PostMapping("/getUserPages")
    Page<UserDTO> getPagesUsers(@RequestBody UserQueryParam param);

}
