package com.xiangan.platform.chainserver.common.entity.user;

import com.xiangan.platform.chainserver.common.domain.BaseEntity;

/**
 * 用户节点配置
 *
 * @Creater liuzhudong
 * @Date 2017/4/11 14:18
 * @Version 1.0
 * @Copyright
 */
public class PeerConfig extends BaseEntity {

    private static final long serialVersionUID = 8572543733931810652L;

    private String name;

    private String url;

    private String eventHub;

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

    public String getEventHub() {
        return eventHub;
    }

    public void setEventHub(String eventHub) {
        this.eventHub = eventHub;
    }
}
