/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ekdant.dentalsolution.dao;

import com.ekdant.dentalsolution.domain.TreatmentBean;
import com.ekdant.dentalsolution.utilities.ConnectionPool;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;

/**
 *
 * @author Sushant Raut
 */
public class TreatmentDao {
    
    ConnectionPool connection;
    public TreatmentDao(){
        connection = ConnectionPool.getInstance();
    }
    
    public List<TreatmentBean> fetchTreatments(){
        List<TreatmentBean> treatments = new ArrayList<TreatmentBean>();
        ResultSet rs = connection.getResult("SELECT * FROM TREATMENTS WHERE ACTIVEIND = 1");
        try {
            while(rs.next()){
                TreatmentBean treatment = new TreatmentBean();
                treatment.setTreatmentId(rs.getInt("treatmentId"));
                treatment.setTreatmentName(rs.getString("treatmentName"));
                treatment.setTreatmentDescription(rs.getString("treatmentDescription"));
                treatment.setTreatmentCharges(rs.getFloat("treatmentCharges"));
                treatments.add(treatment);
            }
        } catch (SQLException ex) {System.out.println(ex.getMessage());}        
        return treatments;
    }
    
    public List<TreatmentBean> fetchTreatments(String searchText){
        List<TreatmentBean> treatments = new ArrayList<TreatmentBean>();
        String sql = "SELECT * FROM TREATMENTS WHERE ACTIVEIND = 1 ORDER BY TREATMENTNAME";
        if(searchText != null && !searchText.isEmpty()){
            sql = "SELECT * FROM TREATMENTS WHERE TREATMENTNAME LIKE '%"+searchText+"%' AND ACTIVEIND = 1 ORDER BY TREATMENTNAME";
        }
        
        ResultSet rs = connection.getResult(sql);
        try {
            while(rs.next()){
                TreatmentBean treatment = new TreatmentBean();
                treatment.setTreatmentId(rs.getInt("treatmentId"));
                treatment.setTreatmentName(rs.getString("treatmentName"));
                treatment.setTreatmentDescription(rs.getString("treatmentDescription"));
                treatment.setTreatmentCharges(rs.getFloat("treatmentCharges"));
                treatments.add(treatment);
            }
        } catch (SQLException ex) {System.out.println(ex.getMessage());}        
        return treatments;
    }
    
    public int fetchTreatmentId(String treatmentName){
        int treatmentId = 0;
        ResultSet rs = connection.getResult("SELECT TREATMENTID FROM TREATMENTS WHERE TREATMENTNAME = '"+treatmentName+"' AND ACTIVEIND = 1");
        try {
            while(rs.next()){
                treatmentId = rs.getInt(1);
            }
        } catch (SQLException ex) { }
        return treatmentId;
    }
    
    public boolean treatmentNotPresent(String newTreatmentName) {
        String treatmentSql = "SELECT COUNT(*) FROM TREATMENTS WHERE TREATMENTNAME = '"+newTreatmentName+"' AND ACTIVEIND = 1";
        int treatmentCount = 0;
        ResultSet rs = connection.getResult(treatmentSql);
        try {
            while(rs.next()){
                treatmentCount = rs.getInt(1);
            }
        } catch (SQLException ex) {System.out.println(ex.getMessage());}
        return treatmentCount == 0;
    }
    
    public boolean addTreatment(TreatmentBean treatment) {
        boolean success = true;
        String sql = "INSERT INTO TREATMENTS ( TREATMENTNAME, TREATMENTDESCRIPTION, TREATMENTCHARGES, ACTIVEIND) VALUES( '"+treatment.getTreatmentName()+"', '"+treatment.getTreatmentDescription()+"', '"+treatment.getTreatmentCharges()+"', 1)";
        
        try {
            connection.stmt.execute(sql);
        } catch (SQLException ex) {
            success = false;
        }
         
        return success;
    }
    
    public boolean updateTreatment(TreatmentBean treatment) {
        boolean success = true;
        String sql = "UPDATE TREATMENTS SET TREATMENTNAME = '"+treatment.getTreatmentName()+"',TREATMENTDESCRIPTION = '"+treatment.getTreatmentDescription()+"',TREATMENTCHARGES = "+treatment.getTreatmentCharges()+" WHERE TREATMENTID = "+treatment.getTreatmentId();
        
        try {
            connection.stmt.execute(sql);
        } catch (SQLException ex) {
            success = false;
        }
         
        return success;
    }
    
    public boolean deleteTreatment(TreatmentBean treatment) {
        boolean success = true;
        String sql = "UPDATE TREATMENTS SET ACTIVEIND = 0 WHERE TREATMENTID = "+treatment.getTreatmentId();
        
        try {
            connection.stmt.execute(sql);
        } catch (SQLException ex) {
            success = false;
        }
         
        return success;
    }
    
    public void getTreatments(JComboBox treatmentCB) {        
        List<TreatmentBean> treatments = fetchTreatments();
        for (TreatmentBean treatment : treatments){
                treatmentCB.addItem(treatment.getTreatmentName());
        }
    }
    
    public String fetchLatestTreatment(){
        String newlyAddedTreatment = "";
        ResultSet rs = connection.getResult("SELECT * FROM TREATMENTS WHERE ACTIVEIND = 1 ORDER BY TREATMENTID DESC");
        try {
            while(rs.next()){
                newlyAddedTreatment = rs.getString("treatmentName");
                break;
            }
        } catch (SQLException ex) {
        }
        return newlyAddedTreatment;
    }
}
