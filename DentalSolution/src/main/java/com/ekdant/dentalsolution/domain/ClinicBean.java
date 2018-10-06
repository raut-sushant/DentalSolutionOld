/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ekdant.dentalsolution.domain;

/**
 *
 * @author Sushant Raut
 */
public class ClinicBean {
    private int id;
    private String name;
    private String address;
    private String city;
    private String contact;
    private String morningStartTime;
    private String morningEndTime;
    private String eveningStartTime;
    private String eveningEndTime;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getMorningStartTime() {
        return morningStartTime;
    }

    public void setMorningStartTime(String morningStartTime) {
        this.morningStartTime = morningStartTime;
    }

    public String getMorningEndTime() {
        return morningEndTime;
    }

    public void setMorningEndTime(String morningEndTime) {
        this.morningEndTime = morningEndTime;
    }

    public String getEveningStartTime() {
        return eveningStartTime;
    }

    public void setEveningStartTime(String eveningStartTime) {
        this.eveningStartTime = eveningStartTime;
    }

    public String getEveningEndTime() {
        return eveningEndTime;
    }

    public void setEveningEndTime(String eveningEndTime) {
        this.eveningEndTime = eveningEndTime;
    }
    
}
