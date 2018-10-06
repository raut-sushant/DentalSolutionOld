/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ekdant.dentalsolution.domain;

import java.util.Date;

/**
 *
 * @author Sushant Raut
 */
public class ActivationBean {

    private int id;
    private String activationFileKey;
    private Date activationDate;
    private Date startDate;
    private Date endDate;
    private int activationDays;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getActivationFileKey() {
        return activationFileKey;
    }

    public void setActivationFileKey(String activationFileKey) {
        this.activationFileKey = activationFileKey;
    }

    public Date getActivationDate() {
        return activationDate;
    }

    public void setActivationDate(Date activationDate) {
        this.activationDate = activationDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    
    public int getActivationDays() {
        return activationDays;
    }

    public void setActivationDays(int activationDays) {
        this.activationDays = activationDays;
    }
    
    
}
