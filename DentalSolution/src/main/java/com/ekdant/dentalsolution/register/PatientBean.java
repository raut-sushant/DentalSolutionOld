/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ekdant.dentalsolution.register;

import com.ekdant.dentalsolution.utilities.ConnectionPool;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dinesh.mali
 */
public class PatientBean {
    int patientId;
    String name;
    String caseId;
    String gender;
    String address;
    String city;
    String telephone;
    String email;
    String mobile;
    int age;
    Date birthDate;
    Date createdDate;
    String preMedicalHistory;
        
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
    
    public static PatientBean getPatient(int patientId){
        PatientBean patient = new PatientBean();
        ConnectionPool connection = ConnectionPool.getInstance();
        DateFormat displayDateFormat = new SimpleDateFormat("d MMM yyyy");
        DateFormat databaseDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    
    
        ResultSet rs = connection.getResult("SELECT * FROM PATIENTS WHERE PATIENTID = "+patientId+" AND ACTIVEIND = 1");
        try{
            while(rs.next()){
                    patient.setPatientId(rs.getInt("PATIENTID"));
                    patient.setCaseId(rs.getString("CASEID"));
                    patient.setName(rs.getString("NAME"));
                    patient.setAge(rs.getInt("AGE"));
                    patient.setGender(rs.getString("GENDER"));
                    patient.setAddress(rs.getString("ADDRESS"));
                    patient.setCity(rs.getString("CITY"));
                    patient.setTelephone(rs.getString("TELEPHONE"));
                    patient.setMobile(rs.getString("MOBILE"));
                    patient.setEmail(rs.getString("EMAIL"));
                try {
                    patient.setBirthDate(rs.getDate("BIRTHDAY") != null ? displayDateFormat.parse(displayDateFormat.format(rs.getDate("BIRTHDAY"))) : null);
                    patient.setCreatedDate(rs.getDate("CREATEDTM") != null ? displayDateFormat.parse(displayDateFormat.format(rs.getDate("CREATEDTM"))) : null);
                } catch (ParseException ex) {
                    Logger.getLogger(PatientBean.class.getName()).log(Level.SEVERE, null, ex);
                }               
            }
        }
        catch(SQLException ex){
            Logger.getLogger(PatientBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return patient;
    }
    
}
