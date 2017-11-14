package com.ssm.entity;

public class ArticleFiles {
    private Integer id;

    private Integer articleid;

    private String name;

    private String addressurl;

    private Integer grade;
    
    private Articles articles;

    public ArticleFiles(Integer id, Integer articleid, String name, String addressurl, Integer grade) {
        this.id = id;
        this.articleid = articleid;
        this.name = name;
        this.addressurl = addressurl;
        this.grade = grade;
    }

    public Articles getArticles() {
		return articles;
	}

	public void setArticles(Articles articles) {
		this.articles = articles;
	}

	public ArticleFiles() {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getAddressurl() {
        return addressurl;
    }

    public void setAddressurl(String addressurl) {
        this.addressurl = addressurl == null ? null : addressurl.trim();
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }
}