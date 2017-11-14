package com.ssm.entity;

import java.util.Date;

public class Turns {
    private Integer id;

    private Integer articleid;

    private Integer turnuserid;

    private Date createtime;

    public Turns(Integer id, Integer articleid, Integer turnuserid, Date createtime) {
        this.id = id;
        this.articleid = articleid;
        this.turnuserid = turnuserid;
        this.createtime = createtime;
    }

    public Turns() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getArticleid() {
        return articleid;
    }

    public void setArticleid(Integer articleid) {
        this.articleid = articleid;
    }

    public Integer getTurnuserid() {
        return turnuserid;
    }

    public void setTurnuserid(Integer turnuserid) {
        this.turnuserid = turnuserid;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
}