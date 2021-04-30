package com.lagou.edu.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lagou.edu.user.entity.PhoneVerificationCode;
import com.lagou.edu.user.exception.ExpireCodeRuntimeException;
import com.lagou.edu.user.exception.IncorrectCodeRuntimteException;
import com.lagou.edu.user.mapper.PhoneVerificationCodeMapper;
import com.lagou.edu.user.service.IPhoneVerificationCodeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.util.DateUtil;
import edu.util.RandomUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author felix
 * @since 2020-11-12
 */
@Service
public class PhoneVerificationCodeServiceImpl extends ServiceImpl<PhoneVerificationCodeMapper, PhoneVerificationCode> implements IPhoneVerificationCodeService {

    @Override
    public boolean save(String telephone) {

        //获取动态验证码
        String randomNumber = RandomUtil.getRandomNumber(6);
        //发送验证码
        System.out.println("发送验证码");

        PhoneVerificationCode verificationCode = new PhoneVerificationCode();
        verificationCode.setPhone(telephone);   //设置电话号码
        verificationCode.setVerificationCode(randomNumber);
        verificationCode.setCreateTime(LocalDateTime.now());
        verificationCode.setIsCheck(false);
        verificationCode.setCheckTimes(0);

        //保存验证码
        boolean res = this.save(verificationCode);
        return res;
    }

    @Override
    public boolean checkCode(String telephone, String code) {

        //判断验证码是否存在
        QueryWrapper<PhoneVerificationCode> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone",telephone);
        queryWrapper.eq("verification_code",code);
        PhoneVerificationCode verificationCode = this.getBaseMapper().selectOne(queryWrapper);

        if(verificationCode == null){
            throw new IncorrectCodeRuntimteException("验证码错误");
        }
        //判断验证码是否过期
        Date now = new Date();
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = verificationCode.getCreateTime().atZone(zoneId);//Combines this date-time with a time-zone to create a  ZonedDateTime.
        Date date = Date.from(zdt.toInstant());
        Date expireDate = DateUtil.rollByMinutes(date,5);
        if(now.getTime() > expireDate.getTime()){
            throw new ExpireCodeRuntimeException("验证码失效,重新发送");
        }

        verificationCode.setIsCheck(true);
        int checkTimes = verificationCode.getCheckTimes()+1;
        verificationCode.setCheckTimes(checkTimes);

        return this.updateById(verificationCode);
    }
}
