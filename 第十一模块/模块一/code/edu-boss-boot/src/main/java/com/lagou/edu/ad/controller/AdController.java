package com.lagou.edu.ad.controller;

import com.lagou.edu.dto.PromotionAdDTO;
import com.lagou.edu.dto.PromotionSpaceDTO;
import com.lagou.edu.remote.AdRemoteService;
import edu.response.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author CoderCheng
 * @date 2021/04/29 17:12
 */
@RestController
@RequestMapping("/ad")
public class AdController {

    @Autowired
    private AdRemoteService adRemoteService;

    @RequestMapping("/space/getAllSpaces")
    public ResponseDTO getAllSpace() throws Exception{
        List<PromotionSpaceDTO> dtoList = adRemoteService.getAllSpaces();
        return ResponseDTO.success(dtoList);
    }

    @PostMapping("/space/saveOrUpdate")
    public ResponseDTO saveOrUpdateSpace(@RequestBody PromotionSpaceDTO spaceDTO) {
        ResponseDTO responseDTO = adRemoteService.saveOrUpdateSpace(spaceDTO);
        return responseDTO;
    }

    @GetMapping("/space/getSpaceById")
    public ResponseDTO getSpaceById(@RequestParam("id") Integer id) {
        PromotionSpaceDTO promotionSpaceDTO = adRemoteService.getSpaceById(id);
        return ResponseDTO.success(promotionSpaceDTO);
    }

    @GetMapping("/getAllAds")
    public ResponseDTO getAllAds(){
        List<PromotionAdDTO> dtoList = adRemoteService.getAllAds();
        return ResponseDTO.success(dtoList);
    }

    @PostMapping("/saveOrUpdate")
    public ResponseDTO saveOrUpdateAd(@RequestBody PromotionAdDTO adDTO){
        ResponseDTO responseDTO = adRemoteService.saveOrUpdateAd(adDTO);
        return responseDTO;
    }

    @GetMapping("/getAdById")
    public ResponseDTO getAdById(@RequestParam("id") Integer id) {
        PromotionAdDTO promotionAdDTO = adRemoteService.getAdById(id);
        return ResponseDTO.success(promotionAdDTO);
    }
}
