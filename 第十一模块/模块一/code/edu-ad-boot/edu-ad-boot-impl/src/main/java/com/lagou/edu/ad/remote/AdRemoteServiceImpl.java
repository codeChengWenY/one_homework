package com.lagou.edu.ad.remote;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lagou.edu.ad.entity.PromotionAd;
import com.lagou.edu.ad.entity.PromotionSpace;
import com.lagou.edu.ad.service.IPromotionAdService;
import com.lagou.edu.ad.service.IPromotionSpaceService;
import com.lagou.edu.dto.PromotionAdDTO;
import com.lagou.edu.dto.PromotionSpaceDTO;
import com.lagou.edu.remote.AdRemoteService;
import edu.response.ResponseDTO;
import edu.util.ConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author CoderCheng
 * @date 2021/04/29 17:12
 */
@RestController
@RequestMapping("/ad")
public class AdRemoteServiceImpl implements AdRemoteService {

    @Autowired
    IPromotionAdService promotionAdService;

    @Autowired
    IPromotionSpaceService promotionSpaceService;

    @GetMapping("/space/getAllSpaces")
    public List<PromotionSpaceDTO> getAllSpaces() {
        List<PromotionSpace> list = promotionSpaceService.list();
        return ConvertUtils.convertList(list,PromotionSpaceDTO.class);
    }

    @GetMapping("/getAdBySpaceKey")
    public List<PromotionSpaceDTO> getAdBySpaceKey(String[] spaceKey) {
        List<PromotionSpaceDTO> promotionSpaceDTOList = new ArrayList<>();

        for (String key : spaceKey) {
            //获取spaceKey对应的广告位
            QueryWrapper<PromotionSpace> spaceQueryWrapper = new QueryWrapper<>();
            spaceQueryWrapper.eq("spaceKey",key);
            PromotionSpace promotionSpace = promotionSpaceService.getOne(spaceQueryWrapper);

            //获取该space对应的所有的广告
            QueryWrapper<PromotionAd> adQueryWrapper = new QueryWrapper<>();
            adQueryWrapper.eq("spaceId",promotionSpace.getId());
            //状态为上线状态
            adQueryWrapper.eq("status",1);
            //有效期内
            Date now = new Date();
            adQueryWrapper.lt("startTime",now);
            adQueryWrapper.gt("endTime",now);

            List<PromotionAd> adList = promotionAdService.list(adQueryWrapper);
            //属性拷贝
            PromotionSpaceDTO promotionSpaceDTO = ConvertUtils.convert(promotionSpace, PromotionSpaceDTO.class);
            List<PromotionAdDTO> promotionAdDTOList = ConvertUtils.convertList(adList, PromotionAdDTO.class);
            promotionSpaceDTO.setAdList(promotionAdDTOList);

            promotionSpaceDTOList.add(promotionSpaceDTO);
        }

        return promotionSpaceDTOList;
    }

    @PostMapping("/space/saveOrUpdate")
    public ResponseDTO saveOrUpdateSpace(@RequestBody PromotionSpaceDTO spaceDTO) {
        PromotionSpace entity = ConvertUtils.convert(spaceDTO, PromotionSpace.class);

        if(entity.getId() == null){
            entity.setCreateTime(new Date());
            entity.setUpdateTime(new Date());
            entity.setIsDel(0);
        }else {
            entity.setUpdateTime(new Date());
        }

        ResponseDTO responseDTO = null;
        try {
            promotionSpaceService.saveOrUpdate(entity);
            responseDTO = ResponseDTO.success();
        }catch (Exception e){
            responseDTO = ResponseDTO.ofError(e.getMessage());
            e.printStackTrace();
        }
        return responseDTO;
    }

    @GetMapping("/space/getSpaceById")
    public PromotionSpaceDTO getSpaceById(@RequestParam("id") Integer id) {
        PromotionSpace promotionSpace = promotionSpaceService.getById(id);
        return ConvertUtils.convert(promotionSpace,PromotionSpaceDTO.class);
    }

    @GetMapping("/getAllAds")
    public List<PromotionAdDTO> getAllAds() {
        List<PromotionAd> promotionAdList = promotionAdService.list();
        return ConvertUtils.convertList(promotionAdList,PromotionAdDTO.class);
    }

    @PostMapping("/saveOrUpdate")
    public ResponseDTO saveOrUpdateAd(@RequestBody PromotionAdDTO adDTO) {
        PromotionAd entity = ConvertUtils.convert(adDTO, PromotionAd.class);

        System.out.println(adDTO.getStartTime());

        if(entity.getId() == null){
            entity.setStatus(1);
            entity.setCreateTime(new Date());
            entity.setUpdateTime(new Date());

        }else {
            entity.setUpdateTime(new Date());
        }

        ResponseDTO responseDTO = null;
        try {
            promotionAdService.saveOrUpdate(entity);
            responseDTO = ResponseDTO.success();
        }catch (Exception e){
            responseDTO = ResponseDTO.ofError(e.getMessage());
            e.printStackTrace();
        }
        return responseDTO;
    }

    @GetMapping("/getAdById")
    public PromotionAdDTO getAdById(@RequestParam("id") Integer id) {
        PromotionAd promotionAd = promotionAdService.getById(id);
        return ConvertUtils.convert(promotionAd,PromotionAdDTO.class);
    }
}
