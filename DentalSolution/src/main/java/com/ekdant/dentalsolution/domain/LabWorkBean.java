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
public class LabWorkBean {
    
    private int id;
    private int patientId;
    private int checkupId;
    private int labId;
    private String work;
    private String ul;
    private String ur;
    private String ll;
    private String lr;
    private int status;
    private LabBean lab;
    private PatientBean patient;
    private CheckupBean checkup;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public int getCheckupId() {
        return checkupId;
    }

    public void setCheckupId(int checkupId) {
        this.checkupId = checkupId;
    }

    public int getLabId() {
        return labId;
    }

    public void setLabId(int labId) {
        this.labId = labId;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public String getUl() {
        return ul;
    }

    public void setUl(String ul) {
        this.ul = ul;
    }

    public String getUr() {
        return ur;
    }

    public void setUr(String ur) {
        this.ur = ur;
    }

    public String getLl() {
        return ll;
    }

    public void setLl(String ll) {
        this.ll = ll;
    }

    public String getLr() {
        return lr;
    }

    public void setLr(String lr) {
        this.lr = lr;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public LabBean getLab() {
        return lab;
    }

    public void setLab(LabBean lab) {
        this.lab = lab;
    }   

    public PatientBean getPatient() {
        return patient;
    }

    public void setPatient(PatientBean patient) {
        this.patient = patient;
    }

    public CheckupBean getCheckup() {
        return checkup;
    }

    public void setCheckup(CheckupBean checkup) {
        this.checkup = checkup;
    }
   
    
}
