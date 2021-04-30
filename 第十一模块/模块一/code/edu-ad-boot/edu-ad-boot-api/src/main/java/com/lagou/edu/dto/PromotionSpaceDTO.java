package com.lagou.edu.dto;

import java.util.Date;
import java.util.List;


/**
 * @author CoderCheng
 * @date 2021/04/29 16:43
 */
public class PromotionSpaceDTO {

    private Integer id;

    private String name;

    private String spaceKey;

    private Date createTime;

    private Date updateTime;

    private Integer isDel;

    private List<PromotionAdDTO> adList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpaceKey() {
        return spaceKey;
    }

    public void setSpaceKey(String spaceKey) {
        this.spaceKey = spaceKey;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public List<PromotionAdDTO> getAdList() {
        return adList;
    }

    public void setAdList(List<PromotionAdDTO> adList) {
        this.adList = adList;
    }
}
