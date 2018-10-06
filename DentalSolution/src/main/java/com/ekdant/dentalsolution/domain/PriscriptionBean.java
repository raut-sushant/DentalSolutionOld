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
public class PriscriptionBean {
    
    private int id;
    private int checkupId;
    private String medicineType;
    private String medicineName;
    private String medicineStrength;
    private String frequency;
    private String duration;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCheckupId() {
        return checkupId;
    }

    public void setCheckupId(int checkupId) {
        this.checkupId = checkupId;
    }

    public String getMedicineType() {
        return medicineType;
    }

    public void setMedicineType(String medicineType) {
        this.medicineType = getNotNullValue(medicineType);
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = getNotNullValue(medicineName);
    }

    public String getMedicineStrength() {
        return medicineStrength;
    }

    public void setMedicineStrength(String medicineStrength) {
        this.medicineStrength = getNotNullValue(medicineStrength);
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = getNotNullValue(frequency);
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = getNotNullValue(duration);
    }
    
    private String getNotNullValue(String str){
        String notNullString = "";
        if(str != null){
            notNullString = str.equalsIgnoreCase("null") ? "" : str; 
        }            
        return notNullString; 
    }
}
