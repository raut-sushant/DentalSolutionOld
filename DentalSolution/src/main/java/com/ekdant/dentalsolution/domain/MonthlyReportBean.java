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
public class MonthlyReportBean {
    private float fees;
    private float consultingFees;
    private int opd;
    private Date date;

    public float getFees() {
        return fees;
    }

    public void setFees(float fees) {
        this.fees = fees;
    }

    public float getConsultingFees() {
        return consultingFees;
    }

    public void setConsultingFees(float consultingFees) {
        this.consultingFees = consultingFees;
    }

    public int getOpd() {
        return opd;
    }

    public void setOpd(int opd) {
        this.opd = opd;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    
    
}
