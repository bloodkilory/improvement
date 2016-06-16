package com.block.eight;

import java.math.BigDecimal;

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

    private BigDecimal sal;

    public BigDecimal getSal() {
        return sal;
    }

    public void setSal(BigDecimal sal) {
        this.sal = sal;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        People people = (People) o;

        if (!id.equals(people.id)) return false;
        if (name != null ? !name.equals(people.name) : people.name != null) return false;
        return job != null ? job.equals(people.job) : people.job == null;

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (job != null ? job.hashCode() : 0);
        return result;
    }
}
