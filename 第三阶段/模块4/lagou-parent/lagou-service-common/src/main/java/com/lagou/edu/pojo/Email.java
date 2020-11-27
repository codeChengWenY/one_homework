package com.lagou.edu.pojo;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;


/**
 * @ClassName Email
 * @Description:
 * @Author CoderCheng
 * @Date 2020-11-24 11:50
 * @Version V1.0
 **/

@Data
@Entity
@Table(name = "lagou_auth_code")
public class Email {
    @Id
    private int id;

    private String email;

    private String code;

    private Date createtime;


    private Date expiretime;


}
