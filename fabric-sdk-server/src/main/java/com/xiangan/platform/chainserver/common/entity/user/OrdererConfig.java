package com.xiangan.platform.chainserver.common.entity.user;

import com.xiangan.platform.chainserver.common.domain.BaseEntity;

/**
 * TODO
 *
 * @Creater liuzhudong
 * @Date 2017/4/11 14:23
 * @Version 1.0
 * @Copyright
 */
public class OrdererConfig extends BaseEntity {
    private static final long serialVersionUID = -3836066209024939508L;

    private String name;

    private String url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
