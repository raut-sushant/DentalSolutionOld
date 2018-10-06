/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ekdant.dentalsolution.dao;

import com.ekdant.dentalsolution.domain.PatientBean;
import com.ekdant.dentalsolution.domain.PriscriptionBean;
import com.ekdant.dentalsolution.utilities.ConnectionPool;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 *
 * @author Sushant Raut
 */
public class PatientsDao {

    static ConnectionPool connection;
    DateFormat databaseDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public PatientsDao() {
        connection = ConnectionPool.getInstance();
    }

    public int getTotalPatientCount() {
        int patientCount = 0;
        ResultSet rs = connection.getResult("SELECT COUNT(*) FROM PATIENTS WHERE ACTIVEIND = 1");
        try {
            while (rs.next()) {
                patientCount = rs.getInt(1);
            }
        } catch (SQLException ex) {
        }
        return patientCount;
    }

    public int addPatient(PatientBean patient) {
        int patientId = 0;
        Calendar todayCal = new GregorianCalendar();
        Date today = new Date(todayCal.getTimeInMillis());

        String birthDateStr = null;
        try {
            birthDateStr = "'" + databaseDateFormat.format(patient.getBirthDate()) + "'";
        } catch (Exception e) {
        }

        String patientInsertQuery = "INSERT INTO PATIENTS (NAME,CASEID,BIRTHDAY,GENDER,PHOTO,ADDRESS,CITY,TELEPHONE,MOBILE,EMAIL,AGE,PREMEDICALHISTORY,CREATEDTM,ACTIVEIND) VALUES ('" + patient.getName() + "','" + patient.getCaseId() + "', " + birthDateStr + ",'" + patient.getGender() + "', NULL,'" + patient.getAddress() + "','" + patient.getCity() + "','" + patient.getTelephone() + "','" + patient.getMobile() + "','" + patient.getEmail() + "'," + patient.getAge() + ",'" + patient.getPreMedicalHistory() + "', '" + databaseDateFormat.format(today) + "', 1)";

        try {
            connection.stmt.executeUpdate(patientInsertQuery);
            ResultSet rs = connection.stmt.executeQuery("select last_insert_rowid();");
            if (rs.next()) {
                patientId = rs.getInt(1);
            }
        } catch (SQLException ex) {
        }

        return patientId;
    }

    public boolean updatePatient(PatientBean patient) {

        String birthDateStr = null;
        try {
            birthDateStr = "'" + databaseDateFormat.format(patient.getBirthDate()) + "'";
        } catch (Exception e) {
        }
        String patientUpdateQuery = "UPDATE PATIENTS SET NAME = '" + patient.getName() + "', BIRTHDAY = " + birthDateStr + ","
                + "GENDER  = '" + patient.getGender() + "', PHOTO = NULL, ADDRESS = '" + patient.getAddress() + "', "
                + "CITY = '" + patient.getCity() + "', TELEPHONE = '" + patient.getTelephone() + "',  EMAIL = '" + patient.getEmail() + "',"
                + " AGE = " + patient.getAge() + ", CASEID='" + patient.getCaseId() + "',PREMEDICALHISTORY = '" + patient.getPreMedicalHistory() + "' WHERE PATIENTID = " + patient.getPatientId();

        try {
            connection.stmt.executeUpdate(patientUpdateQuery);
        } catch (SQLException ex) {
            return false;
        }
        return true;
    }

    public int deletePatientById(int paitentId) {
        int deleteSuccess = 0;
        String deletePatientQuery = "UPDATE PATIENTS SET ACTIVEIND = 0 WHERE PATIENTID = " + paitentId;
        try {
            deleteSuccess = connection.stmt.executeUpdate(deletePatientQuery);
        } catch (SQLException ex) {
        }
        return deleteSuccess;
    }

    public List<PatientBean> fetchPatients(String searchString) {
        List<PatientBean> patients = new ArrayList<PatientBean>();
        String sql = "SELECT * FROM PATIENTS WHERE (NAME LIKE '%" + searchString + "%' OR CASEID LIKE '%" + searchString + "%' OR TELEPHONE LIKE '%" + searchString + "%' OR CITY LIKE '%" + searchString + "%') AND ACTIVEIND = 1 ORDER BY PATIENTID DESC";
        if (searchString == null || searchString.length() == 0) {
            sql = "SELECT * FROM PATIENTS WHERE ACTIVEIND = 1 ORDER BY PATIENTID DESC";
        }
        ResultSet rs = connection.getResult(sql);
        try {
            while (rs.next()) {
                PatientBean patient = new PatientBean();
                patient.setPatientId(rs.getInt("PATIENTID"));
                patient.setCaseId(rs.getString("CASEID"));
                patient.setName(rs.getString("NAME"));
                patient.setAge(rs.getInt("AGE"));
                patient.setGender(rs.getString("GENDER"));
                patient.setAddress(rs.getString("ADDRESS"));
                patient.setCity(rs.getString("CITY"));
                patient.setTelephone(rs.getString("TELEPHONE"));
                patient.setEmail(rs.getString("EMAIL"));
                if(rs.getString("BIRTHDAY")!=null)
                    patient.setBirthDate(databaseDateFormat.parse(rs.getString("BIRTHDAY")));
                patient.setPreMedicalHistory(rs.getString("PREMEDICALHISTORY"));
                patients.add(patient);
            }
        } catch (Exception e) {
        }
        return patients;
    }

    public PatientBean fetchPatientById(int patientId) {
        String sql = "SELECT * FROM PATIENTS WHERE PATIENTID = " + patientId + " AND ACTIVEIND = 1";
        ResultSet rs = connection.getResult(sql);
        PatientBean patient = new PatientBean();
        try {
            while (rs.next()) {
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
                if(rs.getString("BIRTHDAY")!=null)
                    patient.setBirthDate(databaseDateFormat.parse(rs.getString("BIRTHDAY")));
                patient.setPreMedicalHistory(rs.getString("PREMEDICALHISTORY"));
            }
        } catch (Exception e) {
        }
        return patient;
    }

    public PatientBean fetchPatientByCaseId(int caseId) {
        String sql = "SELECT * FROM PATIENTS WHERE CASEID = " + caseId + " AND ACTIVEIND = 1";
        ResultSet rs = connection.getResult(sql);
        PatientBean patient = new PatientBean();
        try {
            while (rs.next()) {
                patient.setPatientId(rs.getInt("PATIENTID"));
                patient.setCaseId(rs.getString("CASEID"));
                patient.setName(rs.getString("NAME"));
                patient.setAge(rs.getInt("AGE"));
                patient.setGender(rs.getString("GENDER"));
                patient.setAddress(rs.getString("ADDRESS"));
                patient.setCity(rs.getString("CITY"));
                patient.setTelephone(rs.getString("TELEPHONE"));
                patient.setEmail(rs.getString("EMAIL"));
                if(rs.getString("BIRTHDAY")!=null)
                    patient.setBirthDate(databaseDateFormat.parse(rs.getString("BIRTHDAY")));
                patient.setPreMedicalHistory(rs.getString("PREMEDICALHISTORY"));
            }
        } catch (Exception e) {
        }
        return patient;
    }

    public int getNewPatientCount(java.util.Date startDate, java.util.Date endDate) {
        ResultSet rs = connection.getResult("SELECT COUNT(*) FROM PATIENTS WHERE CAST(CREATEDTM AS DATE) BETWEEN '" + databaseDateFormat.format(startDate) + "' AND '" + databaseDateFormat.format(endDate) + "' AND ACTIVEIND = 1");
        int count = 0;
        try {
            while (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException ex) {
        }
        return count;
    }

    public int getPatientVisit(java.util.Date startDate, java.util.Date endDate) {
        ResultSet rs = connection.getResult("SELECT COUNT(*) FROM CHECKUP WHERE CAST(DATE AS DATE) BETWEEN '" + databaseDateFormat.format(startDate) + "' AND '" + databaseDateFormat.format(endDate) + "' AND ACTIVEIND = 1");
        int count = 0;
        try {
            while (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException ex) {
        }
        return count;
    }

    public List<PriscriptionBean> fetchPriscription(int checkupId) {
        ResultSet rs = connection.getResult("SELECT * FROM PRISCRIPTION WHERE CHECKUPID =" + checkupId);
        List<PriscriptionBean> priscriptions = new ArrayList<PriscriptionBean>();
        try {
            while (rs.next()) {
                PriscriptionBean priscription = new PriscriptionBean();
                priscription.setId(rs.getInt("ID"));
                priscription.setCheckupId(rs.getInt("CHECKUPID"));
                priscription.setMedicineType(rs.getString("MEDICINETYPE"));
                priscription.setMedicineName(rs.getString("MEDICINENAME"));
                priscription.setMedicineStrength(rs.getString("MEDICINESTRENGTH"));
                priscription.setFrequency(rs.getString("FREQUENCY"));
                priscription.setDuration(rs.getString("DURATION"));
                priscriptions.add(priscription);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return priscriptions;
    }

    public boolean persistPriscription(PriscriptionBean priscription) {
        boolean success = true;
        String sql = "INSERT INTO PRISCRIPTION (CHECKUPID, MEDICINETYPE, MEDICINENAME, MEDICINESTRENGTH, FREQUENCY, DURATION) VALUES(" + priscription.getCheckupId() + ", '" + priscription.getMedicineType() + "', '" + priscription.getMedicineName() + "', '" + priscription.getMedicineStrength() + "', '" + priscription.getFrequency() + "', '" + priscription.getDuration() + "')";
        try {
            connection.stmt.execute(sql);
        } catch (SQLException ex) {
            success = false;
        }
        return success;
    }

}
