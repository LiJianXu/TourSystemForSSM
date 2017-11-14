package com.ssm.entity;

public class Occupations {
    private Integer id;

    private String name;

    public Occupations(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Occupations() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
}