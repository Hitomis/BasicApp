package com.hitomi.basicapp;

/**
 * Created by hitomi on 2016/12/19.
 */

public class McModel {

    private String name;

    private int age;

    private boolean sex;

    private float weight;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "McModel{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", sex=" + sex +
                ", weight=" + weight +
                '}';
    }
}
