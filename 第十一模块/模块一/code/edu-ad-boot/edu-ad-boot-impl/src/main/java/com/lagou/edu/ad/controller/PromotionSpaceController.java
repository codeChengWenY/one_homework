package com.lagou.edu.ad.controller;


import com.lagou.edu.ad.entity.PromotionSpace;
import com.lagou.edu.ad.service.IPromotionSpaceService;
import com.lagou.edu.ad.service.impl.PromotionAdServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author felix
 * @since 2020-11-10
 */
@RestController
@RequestMapping("/ad/space")
public class PromotionSpaceController {

    @Autowired
    IPromotionSpaceService promotionAdService;

    @RequestMapping(value = "/getAllSpaces")
    public List<PromotionSpace> getAllSpaces(){
        return promotionAdService.list();
    }
}
