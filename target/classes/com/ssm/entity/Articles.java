package com.ssm.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.ssm.until.RelativeDateFormat;

public class Articles {
    private Integer id;

    private String title;

    private String introduction;

    private String contents;

    private Integer editcontenttype;

    private Date createtime;

    private Date updatetime;

    private Integer good;

    private Integer bad;

    private Integer browses;

    private Integer userdateid;

    private Integer isturn;

    private String labels;

    private Integer stateid;
    
    private Integer typeid;

    private ArticleType articleType;
    
    public Articles(Integer id, String title, String introduction, String contents, Integer editcontenttype,
			Date createtime, Date updatetime, Integer good, Integer bad, Integer browses, Integer userdateid,
			Integer isturn, String labels, Integer stateid, Integer typeid) {
		super();
		this.id = id;
		this.title = title;
		this.introduction = introduction;
		this.contents = contents;
		this.editcontenttype = editcontenttype;
		this.createtime = createtime;
		this.updatetime = updatetime;
		this.good = good;
		this.bad = bad;
		this.browses = browses;
		this.userdateid = userdateid;
		this.isturn = isturn;
		this.labels = labels;
		this.stateid = stateid;
		this.typeid = typeid;
	}

	public ArticleType getArticleType() {
		return articleType;
	}

	public void setArticleType(ArticleType articleType) {
		this.articleType = articleType;
	}

	public Integer getTypeid() {
		return typeid;
	}

	public void setTypeid(Integer typeid) {
		this.typeid = typeid;
	}

	public Articles() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction == null ? null : introduction.trim();
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents == null ? null : contents.trim();
    }

    public Integer getEditcontenttype() {
        return editcontenttype;
    }

    public void setEditcontenttype(Integer editcontenttype) {
        this.editcontenttype = editcontenttype;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getUpdatetime() {
    	
        return RelativeDateFormat.format(updatetime);
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public Integer getGood() {
        return good;
    }

    public void setGood(Integer good) {
        this.good = good;
    }

    public Integer getBad() {
        return bad;
    }

    public void setBad(Integer bad) {
        this.bad = bad;
    }

    public Integer getBrowses() {
        return browses;
    }

    public void setBrowses(Integer browses) {
        this.browses = browses;
    }

    public Integer getUserdateid() {
        return userdateid;
    }

    public void setUserdateid(Integer userdateid) {
        this.userdateid = userdateid;
    }

    public Integer getIsturn() {
        return isturn;
    }

    public void setIsturn(Integer isturn) {
        this.isturn = isturn;
    }

    public String getLabels() {
        return labels;
    }

    public void setLabels(String labels) {
        this.labels = labels == null ? null : labels.trim();
    }

    public Integer getStateid() {
        return stateid;
    }

    public void setStateid(Integer stateid) {
        this.stateid = stateid;
    }

	@Override
	public String toString() {
		return "Articles [id=" + id + ", title=" + title + ", introduction=" + introduction + ", contents=" + contents
				+ ", editcontenttype=" + editcontenttype + ", createtime=" + createtime + ", updatetime=" + updatetime
				+ ", good=" + good + ", bad=" + bad + ", browses=" + browses + ", userdateid=" + userdateid
				+ ", isturn=" + isturn + ", labels=" + labels + ", stateid=" + stateid + ", typeid=" + typeid + "]";
	}

    
}