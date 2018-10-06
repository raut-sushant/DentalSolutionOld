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
public class TreatmentReportBean {
    private String treatmentName;
    private float totalFees;
    private int totalOPD;

    public String getTreatmentName() {
        return treatmentName;
    }

    public void setTreatmentName(String treatmentName) {
        this.treatmentName = treatmentName;
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
