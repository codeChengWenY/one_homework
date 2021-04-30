package com.lagou.edu.user.service;

import com.lagou.edu.user.entity.PhoneVerificationCode;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author felix
 * @since 2020-11-12
 */
public interface IPhoneVerificationCodeService extends IService<PhoneVerificationCode> {

    boolean save(String telephone);

    boolean checkCode(String telephone, String code);
}
