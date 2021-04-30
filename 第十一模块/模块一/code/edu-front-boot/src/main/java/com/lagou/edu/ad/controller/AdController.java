package com.lagou.edu.ad.controller;

import com.lagou.edu.dto.PromotionSpaceDTO;
import com.lagou.edu.remote.AdRemoteService;
import edu.response.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author CoderCheng
 * @date 2021/04/29 17:12
 */
@RestController
@RequestMapping("/ad")
public class AdController {

    @Autowired
    AdRemoteService adRemoteService;

    @RequestMapping("/getAdBySpaceKey")
    public ResponseDTO getAdBySpaceKey(@RequestParam("spaceKey") String[] spaceKey) throws Exception{
        List<PromotionSpaceDTO> dtoList = adRemoteService.getAdBySpaceKey(spaceKey);
        return ResponseDTO.success(dtoList);
    }
}
