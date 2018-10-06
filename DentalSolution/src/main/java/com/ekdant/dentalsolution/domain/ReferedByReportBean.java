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
public class ReferedByReportBean {
    private String referedBy;
    private float totalFees;
    private int totalOPD;

    public String getReferedBy() {
        return referedBy;
    }

    public void setReferedBy(String referedBy) {
        this.referedBy = referedBy;
    }

    public float getTotalFees() {
        return totalFees;
    }

    public void setTotalFees(float totalFees) {
        this.totalFees = totalFees;
    }

    public int getTotalOPD() {
        return totalOPD;
    }

    public void setTotalOPD(int totalOPD) {
        this.totalOPD = totalOPD;
    }
    
}
