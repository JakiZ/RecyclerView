package com.jaki.recyclerview.simple_usage;

import java.io.Serializable;

public class SimpleUsageBean implements Serializable{

    private int id;
    private String name;
    private float score;
    //true -> male; false -> female;
    private boolean gender;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "SimpleUsageBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", score=" + score +
                ", gender=" + gender +
                '}';
    }
}
