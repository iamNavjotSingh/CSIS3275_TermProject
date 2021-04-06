package com.CSIS3275FinalProject.ras.entity;

import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Registration implements Serializable {


    private Integer active=1;


    @Size(max = 50,min = 2)
    @NotEmpty(message="Enter first name.")
    private String firstName;

    @Size(max = 50,min = 2)
    private String lastName;

    @Id
    @Column(unique = true)
    @Email
    private String email;
    @Transient
    private MultipartFile image;

    private String fileName;

    private Integer points;
    @ManyToMany
    private List<Role> roles=new ArrayList<>();


    @OneToMany(mappedBy = "registration")
    List<Recognition> recognitions = new ArrayList<>();

    @OneToMany(mappedBy = "registration")
    List<OrderDetail> orderDetails=new ArrayList<>();

    @OneToMany(mappedBy = "registration")
    List<Token> token=new ArrayList<>();



    @Size(min = 5)
    private  String password;

    private Integer currentBadgeGold;

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    private Integer currentBadgeSilver;
    private Integer currentBadgebronze;


    public List<Token> getToken() {
        return token;
    }

    public List<Recognition> getRecognitions() {
        return recognitions;
    }

    public void setRecognitions(List<Recognition> recognitions) {
        this.recognitions = recognitions;
    }


    public void setToken(List<Token> token) {
        this.token = token;
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }


    public Integer getCurrentBadgeGold() {
        return currentBadgeGold;
    }

    public void setCurrentBadgeGold(Integer currentBadgeGold) {
        this.currentBadgeGold = currentBadgeGold;
    }

    public Integer getCurrentBadgeSilver() {
        return currentBadgeSilver;
    }

    public void setCurrentBadgeSilver(Integer currentBadgeSilver) {
        this.currentBadgeSilver = currentBadgeSilver;
    }

    public Integer getCurrentBadgebronze() {
        return currentBadgebronze;
    }

    public void setCurrentBadgebronze(Integer currentBadgebronze) {
        this.currentBadgebronze = currentBadgebronze;
    }

    public Registration() {
    }

    public Registration(@Size(max = 20, min = 2) String firstName, @Size(max = 20, min = 2) String lastName, String email, MultipartFile image, String fileName, @Size(min = 5) String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.image = image;
        this.fileName = fileName;
        this.password = password;
    }


    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }
}
