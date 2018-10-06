/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ekdant.dentalsolution.domain;

/**
 *
 * @author dinesh.mali
 */
public class TreatmentBean {
    int treatmentId;
    String treatmentName;
    String treatmentDescription;
    float treatmentCharges;

    public int getTreatmentId() {
        return treatmentId;
    }

    public void setTreatmentId(int treatmentId) {
        this.treatmentId = treatmentId;
    }

    public String getTreatmentName() {
        return treatmentName;
    }

    public void setTreatmentName(String treatmentName) {
        this.treatmentName = treatmentName;
    }

    public String getTreatmentDescription() {
        return treatmentDescription;
    }

    public void setTreatmentDescription(String treatmentDescription) {
        this.treatmentDescription = treatmentDescription;
    }

    public float getTreatmentCharges() {
        return treatmentCharges;
    }

    public void setTreatmentCharges(float treatmentCharges) {
        this.treatmentCharges = treatmentCharges;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TreatmentBean other = (TreatmentBean) obj;
        if (this.treatmentId != other.treatmentId) {
            return false;
        }
        if ((this.treatmentName == null) ? (other.treatmentName != null) : !this.treatmentName.equals(other.treatmentName)) {
            return false;
        }
        if ((this.treatmentDescription == null) ? (other.treatmentDescription != null) : !this.treatmentDescription.equals(other.treatmentDescription)) {
            return false;
        }
        if (Float.floatToIntBits(this.treatmentCharges) != Float.floatToIntBits(other.treatmentCharges)) {
            return false;
        }
        return true;
    }
    
}
