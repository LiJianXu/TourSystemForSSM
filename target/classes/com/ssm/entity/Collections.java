package com.ssm.entity;

import java.util.Date;

public class Collections {
    private Integer id;

    private Integer articleid;

    private Integer userdateid;

    private Date createtime;

    private String articletitle;
    
    private ArticleType articleType;

    public Collections(Integer id, Integer articleid, Integer userdateid, Date createtime, String articletitle) {
        this.id = id;
        this.articleid = articleid;
        this.userdateid = userdateid;
        this.createtime = createtime;
        this.articletitle = articletitle;
    }
    
    
    public ArticleType getArticleType() {
		return articleType;
	}


	public void setArticleType(ArticleType articleType) {
		this.articleType = articleType;
	}


	public Collections() {
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

    public Integer getUserdateid() {
        return userdateid;
    }

    public void setUserdateid(Integer userdateid) {
        this.userdateid = userdateid;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getArticletitle() {
        return articletitle;
    }

    public void setArticletitle(String articletitle) {
        this.articletitle = articletitle == null ? null : articletitle.trim();
    }
}