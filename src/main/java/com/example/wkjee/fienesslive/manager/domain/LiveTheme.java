package com.example.wkjee.fienesslive.manager.domain;

/**
 * Created by wkj_pc on 2017/8/22.
 */
public class LiveTheme {
    private Integer ltid;
    private String lttheme;
    private Integer uid;
    private Boolean islive;

    public Boolean getIslive() {
        return islive;
    }

    public void setIslive(Boolean islive) {
        this.islive = islive;
    }

    public Integer getLtid() {
        return ltid;
    }

    public void setLtid(Integer ltid) {
        this.ltid = ltid;
    }

    public String getLttheme() {
        return lttheme;
    }

    public void setLttheme(String lttheme) {
        this.lttheme = lttheme;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }
}
