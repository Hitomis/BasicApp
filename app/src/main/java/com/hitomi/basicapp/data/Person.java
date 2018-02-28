package com.hitomi.basicapp.data;

/**
 * Created by Hitomis on 2018/2/28 0028.
 */

public class Person {
    public String name;
    public int age;

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
