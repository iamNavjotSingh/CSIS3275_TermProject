package com.CSIS3275FinalProject.ras.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer roleId;

    private String role;

    private Integer gold;

    private Integer silver;

    private Integer bronze;

    public Integer getGold() {
        return gold;
    }

    public void setGold(Integer gold) {
        this.gold = gold;
    }

    public Role(String role, Integer gold, Integer silver, Integer bronze) {
        this.role = role;
        this.gold = gold;
        this.silver = silver;
        this.bronze = bronze;
    }

    public Integer getSilver() {
        return silver;
    }

    public void setSilver(Integer silver) {
        this.silver = silver;
    }

    public Integer getBronze() {
        return bronze;
    }

    public void setBronze(Integer bronze) {
        this.bronze = bronze;
    }

    @ManyToMany(mappedBy = "roles")
    List<Registration> registrations=new ArrayList<>();

    @Override
    public String toString() {
        return "Role{" +
                "roleId=" + roleId +
                ", role='" + role + '\'' +
                ", gold=" + gold +
                ", silver=" + silver +
                ", bronze=" + bronze +
                ", registrations=" + registrations +
                '}';
    }

    public List<Registration> getRegistrations() {
        return registrations;
    }

    public Role() {
    }

    public Role(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setRegistrations(List<Registration> registrations) {
        this.registrations = registrations;
    }

}
