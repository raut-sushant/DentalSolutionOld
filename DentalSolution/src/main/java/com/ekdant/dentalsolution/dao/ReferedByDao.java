/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ekdant.dentalsolution.dao;

import com.ekdant.dentalsolution.domain.ReferedByBean;
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
public class ReferedByDao {
    ConnectionPool connection;
    
    public ReferedByDao(){
        connection = ConnectionPool.getInstance();
    }

    public List<ReferedByBean> fetchReferedBy() {
        List<ReferedByBean> referedBy = new ArrayList<ReferedByBean>();
        ResultSet rs = connection.getResult("SELECT * FROM REFEREDBY WHERE NAME != '' AND ACTIVEIND = 1");
        try {
            while (rs.next()) {
                ReferedByBean refered = new ReferedByBean();
                refered.setId(rs.getInt("ID"));
                refered.setName(rs.getString("NAME"));
                refered.setCity(rs.getString("CITY"));
                refered.setContact(rs.getString("CONTACT"));
                refered.setAddress(rs.getString("ADDRESS"));
                referedBy.add(refered);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return referedBy;
    }
    
    public List<ReferedByBean> fetchReferedBy(String searchText) {
        List<ReferedByBean> referedBy = new ArrayList<ReferedByBean>();
        String sql = "SELECT * FROM REFEREDBY WHERE ACTIVEIND = 1 ORDER BY NAME";
        if(searchText != null && !searchText.isEmpty()){
            sql = "SELECT * FROM REFEREDBY WHERE NAME LIKE '%"+searchText+"%' AND ACTIVEIND = 1 AND NAME != '' ORDER BY NAME";
        }
        ResultSet rs = connection.getResult(sql);
        try {
            while (rs.next()) {
                ReferedByBean refered = new ReferedByBean();
                refered.setId(rs.getInt("ID"));
                refered.setName(rs.getString("NAME"));
                refered.setCity(rs.getString("CITY"));
                refered.setContact(rs.getString("CONTACT"));
                refered.setAddress(rs.getString("ADDRESS"));
                referedBy.add(refered);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return referedBy;
    }
    
    public boolean referedByNotPresent(String newReferedByName) {
        int referedByCount = 0;
        ResultSet rs = connection.getResult("SELECT COUNT(*) FROM REFEREDBY WHERE NAME = '"+newReferedByName+"' AND ACTIVEIND = 1");
        try {
            while(rs.next()){
                referedByCount = rs.getInt(1);
            }
        } catch (SQLException ex) {System.out.println(ex.getMessage());}
        return referedByCount == 0;
    }
    
    public boolean addReferedBy(ReferedByBean referedBy) {
        boolean success = true;
        String sql = "INSERT INTO REFEREDBY ( NAME, CITY, CONTACT, ADDRESS, ACTIVEIND) VALUES( '"+referedBy.getName()+"', '"+referedBy.getCity()+"', '"+referedBy.getContact()+"', '"+referedBy.getAddress()+"', 1)";
        
        try {
            connection.stmt.execute(sql);
        } catch (SQLException ex) {
            success = false;
            System.out.println(ex.getMessage());
        }
         
        return success;
    }
    
    public boolean updateReferedBy(ReferedByBean referedBy) {
        boolean success = true;
        String sql = "UPDATE REFEREDBY SET NAME = '"+referedBy.getName()+"',CITY = '"+referedBy.getCity()+"',CONTACT = '"+referedBy.getContact()+"',ADDRESS = '"+referedBy.getAddress()+"' WHERE ID = '"+referedBy.getId()+"'";
        
        try {
            connection.stmt.execute(sql);
        } catch (SQLException ex) {
            success = false;
            System.out.println(ex.getMessage());
        }
         
        return success;
    }
    
    public boolean deleteReferedBy(ReferedByBean referedBy) {
        boolean success = true;
        String sql = "UPDATE REFEREDBY SET ACTIVEIND = 0 WHERE ID = "+referedBy.getId();
        
        try {
            connection.stmt.execute(sql);
        } catch (SQLException ex) {
            success = false;
            System.out.println(ex.getMessage());
        }
         
        return success;
    }

    public void populateReferedBy(JComboBox reffByCB) {
        List<ReferedByBean> referes = fetchReferedBy();
        for (ReferedByBean referedBy : referes){
                reffByCB.addItem(referedBy.getName());
        }
    }

    public String fetchLatestReferedBy() {
        String newlyAddedReferedBy = "";
        try {
            ResultSet rs = connection.getResult("SELECT NAME FROM REFEREDBY ORDER BY ID DESC");
            while (rs.next()) {
                newlyAddedReferedBy = rs.getString("NAME");
                break;
            }
        } catch (SQLException ex) {System.out.println(ex.getMessage());}
        return newlyAddedReferedBy;
    }
    
}
