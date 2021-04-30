package com.lagou.edu.user.remote;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lagou.edu.user.api.param.UserQueryParam;
import com.lagou.edu.user.api.remote.UserRemoteService;
import com.lagou.edu.user.api.dto.UserDTO;
import com.lagou.edu.user.entity.User;
import com.lagou.edu.user.service.IUserService;
import edu.util.ConvertUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
@Slf4j
public class UserRemoteServiceImpl implements UserRemoteService {


    @Autowired
    private IUserService userService;


    @PostMapping("/getUserPages")
    public Page<UserDTO> getPagesUsers(@RequestBody UserQueryParam userQueryParam) {
        String phone = userQueryParam.getPhone();
        Integer userId = userQueryParam.getUserId();
        Integer currentPage = userQueryParam.getCurrentPage();
        Integer pageSize = userQueryParam.getPageSize();
        Date startCreateTime = userQueryParam.getStartCreateTime();
        Date endCreateTime = userQueryParam.getEndCreateTime();

        Page<User> page = new Page<>(currentPage, pageSize);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        //根据电话号码查询用户
        if (StringUtils.isNotBlank(phone)) {
            queryWrapper.like("phone", phone);
        }
        if (null != startCreateTime && null != endCreateTime) {
            queryWrapper.ge("create_time", startCreateTime);
            queryWrapper.le("create_time", endCreateTime);
        }
        if (null != userId && userId > 0) {
            queryWrapper.eq("id", userId);
        }

        int count = userService.count(queryWrapper);
        queryWrapper.orderByDesc("id");
        IPage<User> selectPage = this.userService.getBaseMapper().selectPage(page, queryWrapper);

        List<UserDTO> userDTOList = new ArrayList<>();
        //获取课程对应的模块的信息
        for (User user : selectPage.getRecords()) {
            UserDTO userDTO = ConvertUtils.convert(user,UserDTO.class);
            userDTOList.add(userDTO);
        }

        Page<UserDTO> result = new Page<>();
        //分页查询结果对象属性的拷贝
        ConvertUtils.convert(selectPage, result);
        //设置分页结果对象record属性
        result.setRecords(userDTOList);
        result.setTotal(count);
        return result;
    }
}
