package com.lagou.edu.controller.service.impl;

import com.lagou.edu.controller.service.IEmailFeignClient;
import com.lagou.edu.controller.service.IEmailService;
import com.lagou.edu.dao.EMailDao;
import com.lagou.edu.pojo.Email;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

/**
 * @ClassName EmailServiceImpl
 * @Description:
 * @Author CoderCheng
 * @Date 2020-11-24 11:56
 * @Version V1.0
 **/

@Service
public class EmailServiceImpl implements IEmailService {


    @Autowired
    private IEmailFeignClient emailFeignClient;

    @Autowired
    private EMailDao eMailDao;

    @Override
    public boolean sendEMail(String email, String code) {
        //  存到数据库
        Email email1 = new Email();
        email1.setEmail(email);
        email1.setCode(code);
        email1.setCreatetime(new Date());
        email1.setExpiretime(DateUtils.addSeconds(new Date(), 600));
        eMailDao.save(email1);
        emailFeignClient.sendEmail(email, code);
        return true;
    }

    @Override
    public Integer verifyEmailCode(String email, String code) {
        Email email1 = eMailDao.getlastCodeByEmail(email);
        if (Objects.nonNull(email1)) {
            String codeDb = email1.getCode();
            if (System.currentTimeMillis()>email1.getExpiretime().getTime()) {
                 return  2;
            } else {
                if (codeDb.equals(code)) {
                    return 0;
                }else {
                    return 1;
                }
            }
        }
        return 0;
    }

    //返回的是字符串型的时间，输入的
    //是String day, int x
    public static String addDateMinut(String day, int x) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 24小时制
        //引号里面个格式也可以是 HH:mm:ss或者HH:mm等等，很随意的，不过在主函数调用时，要和输入的变
        //量day格式一致
        Date date = null;
        try {
            date = format.parse(day);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (date == null)
            return "";
        System.out.println("front:" + format.format(date)); //显示输入的日期
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MINUTE, x);// 24小时制
        date = cal.getTime();
        System.out.println("after:" + format.format(date));  //显示更新后的日期
        cal = null;
        return format.format(date);

    }


    public static void main(String[] args) {


        System.out.println(DateUtils.addSeconds(new Date(), 300));

    }

}
