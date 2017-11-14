package com.ssm.entity;

import java.util.Date;

public class Assess {
	
	//默认评价为0
	public static Integer ASSESS_TYPE_DEFAULT=0;
	//赞为1
	public static Integer ASSESS_TYPE_GOOD=1;
	//踩为2
	public static Integer ASSESS_TYPE_BAD=2;
	
    private Integer id;

    private Integer articleId;

    private Integer usersDataId;

    private Integer assessType;

    private Date assessDate;

    public Assess(Integer id, Integer articleId, Integer usersDataId, Integer assessType, Date assessDate) {
        this.id = id;
        this.articleId = articleId;
        this.usersDataId = usersDataId;
        this.assessType = assessType;
        this.assessDate = assessDate;
    }

    public Assess() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public Integer getUsersDataId() {
        return usersDataId;
    }

    public void setUsersDataId(Integer usersDataId) {
        this.usersDataId = usersDataId;
    }

    public Integer getAssessType() {
        return assessType;
    }

    public void setAssessType(Integer assessType) {
        this.assessType = assessType;
    }

    public Date getAssessDate() {
        return assessDate;
    }

    public void setAssessDate(Date assessDate) {
        this.assessDate = assessDate;
    }
}