package com.ssm.entity;

import java.util.Date;

public class Comments {
    private Integer id;

    private Integer articleid;

    private Integer userdataid;

    private String contents;

    private Date createtime;
    
    private UsersData usersData;

    public Comments(Integer id, Integer articleid, Integer userdataid, String contents, Date createtime) {
        this.id = id;
        this.articleid = articleid;
        this.userdataid = userdataid;
        this.contents = contents;
        this.createtime = createtime;
    }
    
    
    public UsersData getUsersData() {
		return usersData;
	}


	public void setUsersData(UsersData usersData) {
		this.usersData = usersData;
	}


	public Comments() {
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

    public Integer getUserdataid() {
        return userdataid;
    }

    public void setUserdataid(Integer userdataid) {
        this.userdataid = userdataid;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents == null ? null : contents.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
}