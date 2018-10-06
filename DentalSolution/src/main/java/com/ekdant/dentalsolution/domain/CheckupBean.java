/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ekdant.dentalsolution.domain;

import java.util.Date;
import java.util.List;

/**
 *
 * @author Sushant Raut
 */
public class CheckupBean {

    private int checkupId;
    private int patientId;
    private String treatment;
    private String dignosis;
    private String priscription;
    private float fees;
    private float consultantingDoctorFee;
    private Date nextVisitDate;
    private Date date;
    private String dentistName;
    private String refferedBy;
    private String bp;
    private String pulse;
    private String weight;
    private PatientBean patient;
    private List<PriscriptionBean> priscriptions;

    public int getCheckupId() {
        return checkupId;
    }

    public void setCheckupId(int checkupId) {
        this.checkupId = checkupId;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    public String getDignosis() {
        return dignosis;
    }

    public void setDignosis(String dignosis) {
        this.dignosis = dignosis;
    }

    public String getPriscription() {
        return priscription;
    }

    public void setPriscription(String priscription) {
        this.priscription = priscription;
    }

    public float getFees() {
        return fees;
    }

    public void setFees(float fees) {
        this.fees = fees;
    }

    public float getConsultantingDoctorFee() {
        return consultantingDoctorFee;
    }

    public void setConsultantingDoctorFee(float consultantingDoctorFee) {
        this.consultantingDoctorFee = consultantingDoctorFee;
    }

    public Date getNextVisitDate() {
        return nextVisitDate;
    }

    public void setNextVisitDate(Date nextVisitDate) {
        this.nextVisitDate = nextVisitDate;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDentistName() {
        return dentistName;
    }

    public void setDentistName(String dentistName) {
        this.dentistName = dentistName;
    }

    public String getRefferedBy() {
        return refferedBy;
    }

    public void setRefferedBy(String refferedBy) {
        this.refferedBy = refferedBy;
    }

    public String getBp() {
        return bp;
    }

    public void setBp(String bp) {
        this.bp = bp;
    }

    public String getPulse() {
        return pulse;
    }

    public void setPulse(String pulse) {
        this.pulse = pulse;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public PatientBean getPatient() {
        return patient;
    }

    public void setPatient(PatientBean patient) {
        this.patient = patient;
    }   

    public List<PriscriptionBean> getPriscriptions() {
        return priscriptions;
    }

    public void setPriscriptions(List<PriscriptionBean> priscriptions) {
        this.priscriptions = priscriptions;
    }
    
}
