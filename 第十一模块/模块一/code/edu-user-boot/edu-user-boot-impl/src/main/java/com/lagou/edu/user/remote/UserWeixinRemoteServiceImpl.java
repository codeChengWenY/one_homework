package com.lagou.edu.user.remote;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.lagou.edu.user.api.remote.UserWeixinRemoteService;
import com.lagou.edu.user.api.dto.WeixinDTO;
import com.lagou.edu.user.entity.Weixin;
import com.lagou.edu.user.service.IWeixinService;
import edu.response.ResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class UserWeixinRemoteServiceImpl implements UserWeixinRemoteService {

    @Autowired
    private IWeixinService weixinService;

    @Override
    public WeixinDTO getUserWeixinByUserId(Integer userId) {
        LambdaQueryWrapper<Weixin> wrapper = new QueryWrapper<Weixin>().lambda()
                .eq(Weixin::getUserId, userId)
                .eq(Weixin::getIsDel, false);
        List<Weixin> weixins = this.weixinService.getBaseMapper().selectList(wrapper);
        if (CollectionUtils.isEmpty(weixins)) {
            return null;
        }
        WeixinDTO dto = new WeixinDTO();
        BeanUtil.copyProperties(weixins.get(0), dto);
        return dto;
    }

    @Override
    public WeixinDTO getUserWeixinByOpenId(String openId) {
        LambdaQueryWrapper<Weixin> wrapper = new QueryWrapper<Weixin>().lambda()
                .eq(Weixin::getOpenId, openId)
                .eq(Weixin::getIsDel, false);
        List<Weixin> weixins = this.weixinService.getBaseMapper().selectList(wrapper);
        if (CollectionUtils.isEmpty(weixins)) {
            return null;
        }
        WeixinDTO dto = new WeixinDTO();
        BeanUtil.copyProperties(weixins.get(0), dto);
        return dto;
    }

    @Override
    public WeixinDTO getUserWeixinByUnionId(String unionId) {
        LambdaQueryWrapper<Weixin> wrapper = new QueryWrapper<Weixin>().lambda()
                .eq(Weixin::getUnionId, unionId)
                .eq(Weixin::getIsDel, false);
        List<Weixin> weixins = this.weixinService.getBaseMapper().selectList(wrapper);
        if (CollectionUtils.isEmpty(weixins)) {
            return null;
        }
        WeixinDTO dto = new WeixinDTO();
        BeanUtil.copyProperties(weixins.get(0), dto);
        return dto;
    }

    @Override
    public WeixinDTO saveUserWeixin(WeixinDTO weixinDTO) {
        Weixin weixin = new Weixin();
        BeanUtil.copyProperties(weixinDTO, weixin);
        weixin.setCreateTime(LocalDateTime.now());
        weixin.setUpdateTime(LocalDateTime.now());
        weixin.setIsDel(false);
        weixin.setId(null);
        boolean result = this.weixinService.save(weixin);
        log.info("微信绑定成功,微信:{}, 结果:{}", weixin, result);
        WeixinDTO dto = new WeixinDTO();
        BeanUtil.copyProperties(weixin, dto);
        return dto;
    }

    @Override
    public boolean updateUserWeixin(WeixinDTO weixinDTO) {
        Weixin weixin = new Weixin();
        BeanUtil.copyProperties(weixinDTO, weixin, "id", "create_time");
        weixin.setUpdateTime(LocalDateTime.now());
        weixin.setIsDel(false);
        boolean result = this.weixinService.updateById(weixin);
        log.info("微信绑定成功,微信:{}, 结果:{}", weixin, result);
        return true;
    }

    @Override
    public ResponseDTO<WeixinDTO> bindUserWeixin(WeixinDTO weixinDTO) {
        LambdaQueryWrapper<Weixin> wrapper = new QueryWrapper<Weixin>().lambda()
                .eq(Weixin::getUserId, weixinDTO.getUserId())
                .eq(Weixin::getUnionId, weixinDTO.getUnionId())
                .eq(Weixin::getIsDel, false);
        List<Weixin> weixins = this.weixinService.getBaseMapper().selectList(wrapper);
        if (CollectionUtils.isNotEmpty(weixins)) {
            log.info("userId:{}, unionId:{}, openId:{} 已绑定，不用处理 ", weixinDTO.getUserId(), weixinDTO.getUnionId(), weixinDTO.getOpenId());
            return ResponseDTO.response(200, "已绑定，无需处理");
        }
        // 该用户已绑定其他unionId
        Weixin userIdWeixin = this.weixinService.getBaseMapper().selectOne(new QueryWrapper<Weixin>().lambda()
                .eq(Weixin::getUserId, weixinDTO.getUserId()).eq(Weixin::getIsDel, false).orderByDesc(Weixin::getId));
        if (null != userIdWeixin && !StringUtils.equals(weixinDTO.getUnionId(), userIdWeixin.getUnionId())) {
            log.info("userId:{}, unionId:{}, openId:{} 该用户已绑定其他unionId ", weixinDTO.getUserId(), weixinDTO.getUnionId(), weixinDTO.getOpenId());
            return ResponseDTO.response(201, "该用户已绑定其他unionId");
        }
        // 该unionId已绑定其他userId
        Weixin unionIdWeixin = this.weixinService.getBaseMapper().selectOne(new QueryWrapper<Weixin>().lambda()
                .eq(Weixin::getUnionId, weixinDTO.getUnionId()).eq(Weixin::getIsDel, false).orderByDesc(Weixin::getId));
        if (null != unionIdWeixin && null != weixinDTO.getUnionId() && !weixinDTO.getUserId().equals(unionIdWeixin.getUserId())) {
            log.info("userId:{}, unionId:{}, openId:{} 该unionId已绑定其他用户 ", weixinDTO.getUserId(), weixinDTO.getUnionId(), weixinDTO.getOpenId());
            return ResponseDTO.response(202, "该unionId已绑定其他用户");
        }
        // 开始真正绑定
        Weixin weixin = new Weixin();
        BeanUtil.copyProperties(weixinDTO, weixin);
        weixin.setCreateTime(LocalDateTime.now());
        weixin.setUpdateTime(LocalDateTime.now());
        weixin.setIsDel(false);
        weixin.setId(null);
        boolean result = this.weixinService.save(weixin);
        log.info("微信绑定成功,微信:{}, 结果:{}", weixins, result);
        WeixinDTO dto = new WeixinDTO();
        BeanUtil.copyProperties(weixin, dto);
        return ResponseDTO.success(dto);
    }

    @Override
    public boolean unBindUserWeixin(Integer userId) {
        LambdaQueryWrapper<Weixin> wrapper = new QueryWrapper<Weixin>().lambda()
                .eq(Weixin::getUserId, userId)
                .eq(Weixin::getIsDel, false);
        List<Weixin> weixins = this.weixinService.getBaseMapper().selectList(wrapper);
        if (CollectionUtils.isEmpty(weixins)) {
            return true;
        }
        weixins.forEach(weixin -> {
            weixin.setIsDel(true);
            weixin.setUpdateTime(LocalDateTime.now());
            weixinService.updateById(weixin);
            log.info("用户[{}]已解绑微信[{}]", userId, weixin.getOpenId());
        });
        return true;
    }
}
