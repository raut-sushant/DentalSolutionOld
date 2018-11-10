/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ekdant.dentalsolution.dao;

import com.ekdant.dentalsolution.domain.CheckupBean;
import com.ekdant.dentalsolution.domain.LabBean;
import com.ekdant.dentalsolution.domain.LabWorkBean;
import com.ekdant.dentalsolution.domain.LabWorkNameBean;
import com.ekdant.dentalsolution.domain.PatientBean;
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
public class LabDao {

    ConnectionPool connection;
    DateFormat databaseDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public LabDao() {
        connection = ConnectionPool.getInstance();
    }

    public List<LabBean> fetchLabs() {
        List<LabBean> labs = new ArrayList<LabBean>();
        ResultSet rs = connection.getResult("SELECT * FROM LAB WHERE ACTIVEIND = 1");
        try {
            while (rs.next()) {
                LabBean lab = new LabBean();
                lab.setId(rs.getInt("LABID"));
                lab.setName(rs.getString("NAME"));
                lab.setCity(rs.getString("CITY"));
                lab.setContact(rs.getString("CONTACT"));
                lab.setAddress(rs.getString("ADDRESS"));
                labs.add(lab);
            }
        } catch (SQLException ex) {
        }
        return labs;
    }
    
    public List<LabBean> fetchLabs(String searchText) {
        List<LabBean> labs = new ArrayList<LabBean>();
        ResultSet rs = connection.getResult("SELECT * FROM LAB WHERE NAME LIKE '%"+searchText+"%' AND ACTIVEIND = 1 AND NAME != '' ORDER BY NAME");
        try {
            while (rs.next()) {
                LabBean lab = new LabBean();
                lab.setId(rs.getInt("LABID"));
                lab.setName(rs.getString("NAME"));
                lab.setCity(rs.getString("CITY"));
                lab.setContact(rs.getString("CONTACT"));
                lab.setAddress(rs.getString("ADDRESS"));
                labs.add(lab);
            }
        } catch (SQLException ex) {
        }
        return labs;
    }
    
    public boolean labNotPresent(String newLabName) {
        int labCount = 0;
        ResultSet rs = connection.getResult("SELECT COUNT(*) FROM LAB WHERE NAME = '"+newLabName+"' AND ACTIVEIND = 1");
        try {
            while(rs.next()){
                labCount = rs.getInt(1);
            }
        } catch (SQLException ex) {System.out.println(ex.getMessage());}
        return labCount == 0;
    }
    
    public boolean addLab(LabBean lab) {
        boolean success = true;
        try {
            connection.stmt.execute("INSERT INTO LAB ( NAME, CITY, CONTACT, ADDRESS, ACTIVEIND) VALUES( '" + lab.getName() + "', '" + lab.getCity() + "', '" + lab.getContact() + "', '" + lab.getAddress() + "', 1)");
        } catch (SQLException ex) { success = false;}
        return success;
    }
    
    public boolean updateLab(LabBean lab) {
        boolean success = true;
        String sql = "UPDATE LAB SET NAME = '" + lab.getName() + "',CITY = '" + lab.getCity() + "',CONTACT = '" + lab.getContact() + "',ADDRESS = '" + lab.getAddress() + "' WHERE ID = '" + lab.getId() + "'";
        try {
            connection.stmt.execute(sql);
        } catch (SQLException ex) {
            success = false;
        }
        return success;
    }
    
    public boolean deleteLab(LabBean lab) {
        boolean success = true;
        String deleteLabQuery = "UPDATE LAB SET ACTIVEIND = 0 WHERE LABID = " + lab.getId();
        try {
            connection.stmt.execute(deleteLabQuery);
        } catch (SQLException ex) {
            success = false;
        }
        return success;
    }

    public int fetchLabId(String labName) {
        int labId = 0;
        ResultSet rs = connection.getResult("SELECT LABID FROM LAB WHERE NAME = '" + labName + "' AND ACTIVEIND = 1 ");
        try {
            if (rs.next()) {
                labId = rs.getInt("LABID");
            }
        } catch (SQLException ex) {
        }
        return labId;
    }

    public boolean addLabWork(LabWorkBean labWork) {
        boolean success = true;
        String labWorkQuery = "INSERT INTO LABWORK ( PATIENTID, CHECKUPID, LABID, WORK, UL, UR, LL, LR, STATUS, ACTIVEIND) VALUES ( " + labWork.getPatientId() + ", " + labWork.getCheckupId() + "," + labWork.getLabId() + ",'" + labWork.getWork() + "', '" + labWork.getUl() + "', '" + labWork.getUr() + "', '" + labWork.getLl() + "', '" + labWork.getLr() + "', " + labWork.getStatus() + " , 1)";
        try {
            connection.stmt.execute(labWorkQuery);
        } catch (SQLException ex) {
            success = false;
        }
        return success;
    }

    public boolean updateLabWork(LabWorkBean labWork) {
        boolean success = true;
        String labWorkQuery = "UPDATE LABWORK SET STATUS = " + labWork.getStatus() + " WHERE ID = " + labWork.getId() + " AND ACTIVEIND = 1 ";
        try {
            connection.stmt.execute(labWorkQuery);
        } catch (SQLException ex) {
            success = false;
        }
        return success;
    }

    public List<LabWorkBean> fetchLabWorks(String searchText) {
        List<LabWorkBean> labWorks = new ArrayList<LabWorkBean>();
        String query = "";
        if(searchText == null || searchText.isEmpty())
            query = "SELECT LW.ID AS LABWORKID, C.checkUpId AS CHECKUPID, C.date AS CHECKUPDATE, P.PATIENTID AS PATIENTID, P.NAME AS PATIENTNAME, P.CASEID AS CASENO, P.TELEPHONE AS PATIENTMOBILE, L.LABID AS LABID, L.NAME AS LABNAME, L.CONTACT AS LABCONTACT, LW.WORK AS LABWORK, LW.STATUS AS WORKSTATUS  FROM LABWORK LW, CHECKUP C, PATIENTS P, LAB L WHERE LW.LABID = L.LABID AND LW.PATIENTID = P.PATIENTID AND LW.CHECKUPID = C.checkUpId AND C.ACTIVEIND = 1 AND LW.ACTIVEIND = 1 ORDER BY LW.ID DESC";
        else
            query = "SELECT LW.ID AS LABWORKID, C.checkUpId AS CHECKUPID, C.date AS CHECKUPDATE, P.PATIENTID AS PATIENTID, P.NAME AS PATIENTNAME, P.CASEID AS CASENO, P.TELEPHONE AS PATIENTMOBILE, L.LABID AS LABID, L.NAME AS LABNAME, L.CONTACT AS LABCONTACT, LW.WORK AS LABWORK, LW.STATUS AS WORKSTATUS  FROM LABWORK LW, CHECKUP C, PATIENTS P, LAB L WHERE LW.WORK LIKE '%"+searchText+"%' AND  LW.LABID = L.LABID AND LW.PATIENTID = P.PATIENTID AND LW.CHECKUPID = C.checkUpId AND C.ACTIVEIND = 1 AND LW.ACTIVEIND = 1 ORDER BY LW.ID DESC";
        ResultSet rs = connection.getResult(query);
        try {
            while (rs.next()) {
                LabWorkBean labWork = new LabWorkBean();
                PatientBean patient = new PatientBean();
                LabBean lab = new LabBean();
                CheckupBean checkup = new CheckupBean();
                labWork.setId(rs.getInt("LABWORKID"));
                labWork.setWork(rs.getString("LABWORK"));
                labWork.setStatus(rs.getInt("WORKSTATUS"));

                checkup.setCheckupId(rs.getInt("CHECKUPID"));
                if(rs.getString("CHECKUPDATE").isEmpty())
                    checkup.setDate(databaseDateFormat.parse(rs.getString("CHECKUPDATE")));
                
                patient.setPatientId(rs.getInt("PATIENTID"));
                patient.setName(rs.getString("PATIENTNAME"));
                patient.setCaseId(rs.getString("CASENO"));
                patient.setTelephone(rs.getString("PATIENTMOBILE"));

                lab.setId(rs.getInt("LABID"));
                lab.setName(rs.getString("LABNAME"));
                lab.setName(rs.getString("LABCONTACT"));

                labWork.setPatient(patient);
                labWork.setCheckup(checkup);
                labWork.setLab(lab);
                labWorks.add(labWork);
            }
        } catch (Exception e) { }
        return labWorks;
    }
    
    public LabWorkBean fetchLabWorkDetails(int checkupId){
        ResultSet rs = connection.getResult("SELECT * FROM LABWORK LW, LAB L WHERE LW.CHECKUPID = " + checkupId + " AND LW.LABID = L.LABID AND LW.ACTIVEIND = 1");
        LabWorkBean labWork = new LabWorkBean();
        LabBean lab = new LabBean();
        try{
            while (rs.next()) {
                labWork.setId(rs.getInt("ID"));
                labWork.setPatientId(rs.getInt("PATIENTID"));
                labWork.setCheckupId(rs.getInt("CHECKUPID"));
                labWork.setWork(rs.getString("WORK"));
                labWork.setUl(rs.getString("UL"));
                labWork.setUr(rs.getString("UR"));
                labWork.setLl(rs.getString("LL"));
                labWork.setLr(rs.getString("LR"));
                labWork.setStatus(rs.getInt("STATUS"));
                lab.setId(rs.getInt("LABID"));
                lab.setName(rs.getString("NAME"));
                lab.setCity(rs.getString("CITY"));
                lab.setContact(rs.getString("CONTACT"));
                lab.setAddress(rs.getString("ADDRESS"));
                labWork.setLab(lab);
            }
        }catch(Exception e){System.out.println(e.getMessage().toString());}
        return labWork;
    }
    
    public List<LabWorkNameBean> fetchLabWorkNames() {
        List<LabWorkNameBean> labWorkNames = new ArrayList<LabWorkNameBean>();
        ResultSet rs = connection.getResult("SELECT * FROM LABWORKNAME WHERE ACTIVEIND = 1");
        try {
            while (rs.next()) {
                LabWorkNameBean labWorkName = new LabWorkNameBean();
                labWorkName.setId(rs.getInt("ID"));
                labWorkName.setName(rs.getString("NAME"));
                labWorkNames.add(labWorkName);
            }
        } catch (SQLException ex) {
        }
        return labWorkNames;
    }
    
    public List<LabWorkNameBean> fetchLabWorkNames(String searchText) {
        List<LabWorkNameBean> labWorkNames = new ArrayList<LabWorkNameBean>();
        String sql = "SELECT * FROM LABWORKNAME WHERE NAME LIKE '%"+searchText+"%' AND ACTIVEIND = 1 ORDER BY NAME"; 
        if(searchText == null || searchText.isEmpty()){
            sql = "SELECT * FROM LABWORKNAME WHERE ACTIVEIND = 1 ORDER BY NAME";
        }
                
        ResultSet rs = connection.getResult(sql);
        try {
            while (rs.next()) {
                LabWorkNameBean labWorkName = new LabWorkNameBean();
                labWorkName.setId(rs.getInt("ID"));
                labWorkName.setName(rs.getString("NAME"));
                labWorkNames.add(labWorkName);
            }
        } catch (SQLException ex) {
        }
        return labWorkNames;
    }
    
    public boolean labWorkNameNotPresent(String newLabWorkName) {
        int labWorkNameCount = 0;
        ResultSet rs = connection.getResult("SELECT COUNT(*) FROM LABWORKNAME WHERE NAME = '"+newLabWorkName+"' AND ACTIVEIND = 1");
        try {
            while(rs.next()){
                labWorkNameCount = rs.getInt(1);
            }
        } catch (SQLException ex) {System.out.println(ex.getMessage());}
        return labWorkNameCount == 0;
    }
    
    public boolean addLabWorkName(LabWorkNameBean labWorkName) {
        boolean success = true;
        String insertLabWorkNameSQL = "INSERT INTO LABWORKNAME ( NAME, ACTIVEIND) VALUES( '"+labWorkName.getName()+"', 1)";
        try {
            connection.stmt.execute(insertLabWorkNameSQL);
            
        } catch (SQLException ex) { success = false; }
        return success;
    }
    
    public boolean updateLabWorkName(LabWorkNameBean labWorkName) {
        boolean success = true;
        String updateLabWorkNameSQL = "UPDATE LABWORKNAME SET NAME = '"+labWorkName.getName()+"' WHERE ID =" + labWorkName.getId();
        try {
            connection.stmt.execute(updateLabWorkNameSQL);
            
        } catch (SQLException ex) { success = false; }
        return success;
    }
    
    public boolean deleteLabWorkName(LabWorkNameBean labWorkName) {
        boolean success = true;
        String deleteLabWorkNameSQL = "UPDATE LABWORKNAME SET ACTIVEIND = 0 WHERE ID =" + labWorkName.getId();
        try {
            connection.stmt.execute(deleteLabWorkNameSQL);
            
        } catch (SQLException ex) { success = false; }
        return success;
    }
    
    public int getLabWorkCount(Date date, int status) {
        ResultSet rs = connection.getResult("SELECT COUNT(*) FROM LABWORK LW, CHECKUP C WHERE C.CHECKUPID = LW.CHECKUPID AND C.DATE = '"+databaseDateFormat.format(date)+"' AND LW.STATUS = "+status+" AND LW.ACTIVEIND = 1 AND C.ACTIVEIND = 1");
        int count = 0;
        try {
            while(rs.next()){
               count = rs.getInt(1);
            }
        } catch (SQLException ex) {System.out.println(ex.getMessage());}
        return count;
    }
}
