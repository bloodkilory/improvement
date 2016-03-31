package com.block.eight;

/**
 * Created by bloodkilory on 2016/3/30.
 */
public class People {

    public People() {
    }

    public People(Integer id, String name, String job) {
        this.id = id;
        this.name = name;
        this.job = job;
    }

    private Integer id;

    private String name;

    private String job;

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
        this.name = name;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    @Override
    public String toString() {
        return "People{" + "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", job='" + job + '\'' +
                '}';
    }
}
