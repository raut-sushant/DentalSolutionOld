/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ekdant.dentalsolution.dao;

import com.ekdant.dentalsolution.domain.CheckupBean;
import com.ekdant.dentalsolution.utilities.ConnectionPool;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Sushant Raut
 */
public class CheckUpDao {
    ConnectionPool connection;
    PatientsDao patientsDao;
    DateFormat databaseDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    
    public CheckUpDao(){
        connection = ConnectionPool.getInstance();
        patientsDao = new PatientsDao();
    }
    
    public CheckupBean fetchCheckup(int checkupId){
        ResultSet rs = connection.getResult("SELECT * FROM CHECKUP WHERE CHECKUPID =" + checkupId + " AND ACTIVEIND = 1");
        CheckupBean checkup = new CheckupBean();
        try{
            while(rs.next()){
                checkup.setCheckupId(rs.getInt("checkupid"));
                checkup.setPatientId(rs.getInt("patientId"));
                checkup.setTreatment(rs.getString("treatment"));
                checkup.setDignosis(rs.getString("dignosis"));
                checkup.setPriscription(rs.getString("priscription"));
                checkup.setFees(rs.getFloat("fees"));
                checkup.setConsultantingDoctorFee(rs.getFloat("consultantingDoctorFee"));
                if(rs.getString("nextVisitDate")!=null)
                    checkup.setNextVisitDate(databaseDateFormat.parse(rs.getString("nextVisitDate")));
                if(rs.getString("date")!=null)
                    checkup.setDate(databaseDateFormat.parse(rs.getString("date")));
                checkup.setDentistName(rs.getString("dentistName"));
                checkup.setRefferedBy(rs.getString("refferedBy"));
                checkup.setBp(rs.getString("bp"));
                checkup.setPulse(rs.getString("pulse"));
                checkup.setWeight(rs.getString("weight"));
                checkup.setPatient(patientsDao.fetchPatientById(rs.getInt("patientId")));
                checkup.setPriscriptions(patientsDao.fetchPriscription(checkupId));
            }
        } catch (Exception e) {
        }
        return checkup;
    }
    
    public List<CheckupBean> fetchPatientCheckup(int patientId){
        List<CheckupBean> checkups = new ArrayList<CheckupBean>();
        ResultSet rs = connection.getResult("SELECT * FROM CHECKUP WHERE PATIENTID =" + patientId + " AND ACTIVEIND = 1 ORDER BY CHECKUPID DESC");
        try{
            while(rs.next()){
                CheckupBean checkup = new CheckupBean();
                checkup.setCheckupId(rs.getInt("checkupid"));
                checkup.setPatientId(rs.getInt("patientId"));
                checkup.setTreatment(rs.getString("treatment"));
                checkup.setDignosis(rs.getString("dignosis"));
                checkup.setPriscription(rs.getString("priscription"));
                checkup.setFees(rs.getFloat("fees"));
                checkup.setConsultantingDoctorFee(rs.getFloat("consultantingDoctorFee"));
                if(rs.getString("nextVisitDate")!=null)
                    checkup.setNextVisitDate(databaseDateFormat.parse(rs.getString("nextVisitDate")));
                if(rs.getString("date")!=null)
                    checkup.setDate(databaseDateFormat.parse(rs.getString("date")));
                checkup.setDentistName(rs.getString("dentistName"));
                checkup.setRefferedBy(rs.getString("refferedBy"));
                checkup.setBp(rs.getString("bp"));
                checkup.setPulse(rs.getString("pulse"));
                checkup.setWeight(rs.getString("weight"));
                checkup.setPatient(patientsDao.fetchPatientById(rs.getInt("patientId")));
                checkups.add(checkup);
            }
        } catch (Exception e) {
        }
        return checkups;
    }
    
    public List<CheckupBean> fetchCheckup(Date date){
        List<CheckupBean> checkups = new ArrayList<CheckupBean>();
        if(date == null){
            return checkups;
        }
        ResultSet rs = connection.getResult("SELECT * FROM CHECKUP WHERE DATE(DATE) =  '"+databaseDateFormat.format(date)+"' AND ACTIVEIND = 1 ORDER BY CHECKUPID DESC");
        try{
            while(rs.next()){
                CheckupBean checkup = new CheckupBean();
                checkup.setCheckupId(rs.getInt("checkupid"));
                checkup.setPatientId(rs.getInt("patientId"));
                checkup.setTreatment(rs.getString("treatment"));
                checkup.setDignosis(rs.getString("dignosis"));
                checkup.setPriscription(rs.getString("priscription"));
                checkup.setFees(rs.getFloat("fees"));
                checkup.setConsultantingDoctorFee(rs.getFloat("consultantingDoctorFee"));
                if(rs.getString("nextVisitDate")!=null)
                    checkup.setNextVisitDate(databaseDateFormat.parse(rs.getString("nextVisitDate")));
                if(rs.getString("date")!=null)
                    checkup.setDate(databaseDateFormat.parse(rs.getString("date")));
                checkup.setDentistName(rs.getString("dentistName"));
                checkup.setRefferedBy(rs.getString("refferedBy"));
                checkup.setBp(rs.getString("bp"));
                checkup.setPulse(rs.getString("pulse"));
                checkup.setWeight(rs.getString("weight"));
                checkup.setPatient(patientsDao.fetchPatientById(rs.getInt("patientId")));
                checkups.add(checkup);
            }
        } catch (Exception e) {
        }
        return checkups;
    }
    
    public int addCheckup(CheckupBean checkup){
        int patientTreatmentId = 0;
        String nextVisitDateStr = null;
        try{
            nextVisitDateStr = "'" + databaseDateFormat.format(checkup.getNextVisitDate()) + "'";
        }catch(Exception e){ }
                
        String dateStr = databaseDateFormat.format(checkup.getDate());
        String insertCheckUp = "INSERT INTO CHECKUP(patientId, treatment, dignosis, priscription, fees, consultantingDoctorFee, nextVisitDate, date, dentistName, refferedBy, bp, pulse, weight, activeind) "
                    + "VALUES (" + checkup.getPatientId() + ",'" + checkup.getTreatment() + "','" + checkup.getDignosis() + "',''," + checkup.getFees() + "," + checkup.getConsultantingDoctorFee() + ", " + nextVisitDateStr + ",'" + dateStr + "','" + checkup.getDentistName() + "','" + checkup.getRefferedBy() + "','" + checkup.getBp() + "','" + checkup.getPulse() + "','" + checkup.getWeight() + "', 1)";
        try{
            connection.stmt.executeUpdate(insertCheckUp);
            ResultSet rs = connection.stmt.executeQuery("select last_insert_rowid();");
            if (rs.next()) {
                patientTreatmentId = rs.getInt(1);
            }
        }catch(Exception e){System.out.println(e.getMessage());}
        return patientTreatmentId;
    }  
    
    public boolean deleteCheckup(CheckupBean checkup) {
        boolean success = true;
        try {
            String deleteTreatmentQuery = "UPDATE CHECKUP SET ACTIVEIND = 0 WHERE CHECKUPID = "+checkup.getCheckupId();
            connection.stmt.executeUpdate(deleteTreatmentQuery);            
        } catch (SQLException ex) { success = false; }
        return success;
    }
    
}
