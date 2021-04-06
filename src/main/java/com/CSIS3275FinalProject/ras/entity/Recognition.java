package com.CSIS3275FinalProject.ras.entity;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Recognition
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer recognitionId;
    private String userEmail;
    private String message;
    private String karma;
    private String badgeType;
    private int flag=1;

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public Integer getRecognitionId() {
        return recognitionId;
    }

    public void setRecognitionId(Integer recognitionId) {
        this.recognitionId = recognitionId;
    }

    @CreationTimestamp
    private Date date;

    @ManyToOne
    Registration registration;

    public Recognition() {
    }

    public Recognition(String userEmail, String message, String karma, String badgeType, Date date,  Registration registration) {
        this.userEmail = userEmail;
        this.message = message;
        this.karma = karma;
        this.badgeType = badgeType;
        this.date = date;
        this.registration = registration;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Registration getRegistration() {
        return registration;
    }

    public void setRegistration(Registration registration) {
        this.registration = registration;
    }


    public String getBadgeType() {
        return badgeType;
    }

    public void setBadgeType(String badgeType) {
        this.badgeType = badgeType;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getKarma() {
        return karma;
    }

    public void setKarma(String karma) {
        this.karma = karma;
    }

}
