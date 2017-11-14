package com.ssm.entity;

public class ArticlesCategoryR {
    private Integer id;

    private Integer categoryid;

    private Integer articleid;

    public ArticlesCategoryR(Integer id, Integer categoryid, Integer articleid) {
        this.id = id;
        this.categoryid = categoryid;
        this.articleid = articleid;
    }

    public ArticlesCategoryR() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(Integer categoryid) {
        this.categoryid = categoryid;
    }

    public Integer getArticleid() {
        return articleid;
    }

    public void setArticleid(Integer articleid) {
        this.articleid = articleid;
    }
}