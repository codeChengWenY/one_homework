package com.lagou.edu.dao;

import com.lagou.edu.pojo.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @ClassName IEMailMapper
 * @Description:
 * @Author CoderCheng
 * @Date 2020-11-24 12:00
 * @Version V1.0
 **/
public interface EMailDao extends JpaRepository<Email, Long> {



    @Query(value = "select * from lagou_auth_code where email=?1 order by createtime desc limit 1" ,nativeQuery = true)
    Email getlastCodeByEmail(String  email);
}
