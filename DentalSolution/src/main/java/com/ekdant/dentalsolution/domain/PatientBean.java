/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ekdant.dentalsolution.domain;

import java.util.Date;

/**
 *
 * @author dinesh.mali
 */
public class PatientBean {
    private int patientId;
    private String name;
    private String caseId;
    private String gender;
    private String address;
    private String city;
    private String telephone;
    private String email;
    private String mobile;
    private int age;
    private Date birthDate;
    private Date createdDate;
    private String preMedicalHistory;
    private OdontogramBean odontogramBean;

    public OdontogramBean getOdontogramBean() {
        return odontogramBean;
    }

    public void setOdontogramBean(OdontogramBean odontogramBean) {
        this.odontogramBean = odontogramBean;
    }
        
    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getBirthDate() {
        return birthDate;
    }
    
    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getPreMedicalHistory() {
        return preMedicalHistory;
    }

    public void setPreMedicalHistory(String preMedicalHistory) {
        this.preMedicalHistory = preMedicalHistory;
    }    
}
