package com.how2java.tmall.pojo;

public class ApplyPerson  implements Comparable<ApplyPerson>{

    private String name;
    private int number;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "ApplyPerson{" +
                "name='" + name + '\'' +
                ", number=" + number +
                '}';
    }

    @Override
    public int compareTo(ApplyPerson o) {
        return o.getNumber()-this.number;
    }
}
