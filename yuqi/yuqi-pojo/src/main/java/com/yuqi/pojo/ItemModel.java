package com.yuqi.pojo;

import java.io.Serializable;

public class ItemModel implements Serializable {
    private Long id;

    private Long uid;

    private Long goodsid;

    private Integer pf;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Long getGoodsid() {
        return goodsid;
    }

    public void setGoodsid(Long goodsid) {
        this.goodsid = goodsid;
    }

    public Integer getPf() {
        return pf;
    }

    public void setPf(Integer pf) {
        this.pf = pf;
    }
}