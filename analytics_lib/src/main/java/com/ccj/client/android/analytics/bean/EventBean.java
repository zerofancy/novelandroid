package com.ccj.client.android.analytics.bean;


import com.ccj.client.android.analytics.db.annotations.Table;
import com.ccj.client.android.analytics.db.annotations.Transient;

import java.io.Serializable;

/**
 * Created by chenchangjun on 18/2/8.
 */

@Table(name = "eventlist")
public class EventBean implements Serializable{

    @Transient
    private static final long serialVersionUID = 9009411034336334765L;

    private int id;

    private String it;//日志时间戳
    private String sid;//访问结束的标志:不活动状态超过15分钟；由客户端生成
    private String hnb; //当前页面在一次访问中的第几次数据请求；与session_id关联，当session_id变化时重新计数，从1开始
    private String  v; //当前值为“1”。只有出现不向后兼容的更改时，此值才会改变。
    private String did;//设备id
    private Long uid;
    private String  el;//事件标签

    private String ecp;//自定义map 存储

    public EventBean() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIt() {
        return it;
    }

    public void setIt(String it) {
        this.it = it;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getHnb() {
        return hnb;
    }

    public void setHnb(String hnb) {
        this.hnb = hnb;
    }

    public String getV() {
        return v;
    }

    public void setV(String v) {
        this.v = v;
    }

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public String getEl() {
        return el;
    }

    public void setEl(String el) {
        this.el = el;
    }

    public String getEcp() {
        return ecp;
    }

    public void setEcp(String ecp) {
        this.ecp = ecp;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        return "EventBean{" +
                "id=" + id +
                ", it='" + it + '\'' +
                ", sid='" + sid + '\'' +
                ", hnb='" + hnb + '\'' +
                ", v='" + v + '\'' +
                ", did='" + did + '\'' +
                ", uid=" + uid +
                ", el='" + el + '\'' +
                ", ecp='" + ecp + '\'' +
                '}';
    }
}
