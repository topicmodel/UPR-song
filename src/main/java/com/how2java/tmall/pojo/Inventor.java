package com.how2java.tmall.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "inventor")
@JsonIgnoreProperties({ "handler", "hibernateLazyInitializer" })
public class Inventor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    private String inventorName;
    private String education;
    private String location;
    private String skills;
    private String email;
    private String university;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInventorName() {
        return inventorName;
    }

    public void setInventorName(String inventorName) {
        this.inventorName = inventorName;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    @Override
    public String toString() {
        return "Inventor{" +
                "id=" + id +
                ", inventorName='" + inventorName + '\'' +
                ", educationn='" + education + '\'' +
                ", location='" + location + '\'' +
                ", skills='" + skills + '\'' +
                ", email='" + email + '\'' +
                ", university='" + university + '\'' +
                '}';
    }
}
