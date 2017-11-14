package com.ssm.entity;

public class UsersData {
    private Integer id;

    private Integer usersid;

    private String userimage;

    private String name;

    private Integer occupationid;

    private String introduction;

    private Integer articlesall;

    private Integer acommentsall;

    private Integer allturns;

    private Integer experience;

    private Integer grade;
    
    private Occupations occupations;

    public UsersData(Integer id, Integer usersid, String userimage, String name, Integer occupationid, String introduction, Integer articlesall, Integer acommentsall, Integer allturns, Integer experience, Integer grade) {
        this.id = id;
        this.usersid = usersid;
        this.userimage = userimage;
        this.name = name;
        this.occupationid = occupationid;
        this.introduction = introduction;
        this.articlesall = articlesall;
        this.acommentsall = acommentsall;
        this.allturns = allturns;
        this.experience = experience;
        this.grade = grade;
    }

    public Occupations getOccupations() {
		return occupations;
	}

	public void setOccupations(Occupations occupations) {
		this.occupations = occupations;
	}

	public UsersData() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUsersid() {
        return usersid;
    }

    public void setUsersid(Integer usersid) {
        this.usersid = usersid;
    }

    public String getUserimage() {
        return userimage;
    }

    public void setUserimage(String userimage) {
        this.userimage = userimage == null ? null : userimage.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getOccupationid() {
        return occupationid;
    }

    public void setOccupationid(Integer occupationid) {
        this.occupationid = occupationid;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction == null ? null : introduction.trim();
    }

    public Integer getArticlesall() {
        return articlesall;
    }

    public void setArticlesall(Integer articlesall) {
        this.articlesall = articlesall;
    }

    public Integer getAcommentsall() {
        return acommentsall;
    }

    public void setAcommentsall(Integer acommentsall) {
        this.acommentsall = acommentsall;
    }

    public Integer getAllturns() {
        return allturns;
    }

    public void setAllturns(Integer allturns) {
        this.allturns = allturns;
    }

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }
}