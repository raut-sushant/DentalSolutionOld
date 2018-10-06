/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ekdant.dentalsolution.dao;

import com.ekdant.dentalsolution.domain.ClinicBean;
import com.ekdant.dentalsolution.utilities.ConnectionPool;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Sushant Raut
 */
public class ClinicDao {
    ConnectionPool connection;
    
    public ClinicDao(){
        connection = ConnectionPool.getInstance();
    }

    public boolean clinicSettingRequired() {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM CLINIC WHERE ACTIVEIND = 1";
        ResultSet result = connection.getResult(sql);
        try {
            while(result.next()){
                count = result.getInt(1);
            }
        } catch (SQLException ex) {System.out.println(ex.getMessage());}
        return count == 0;
    }

    public boolean addClinic(ClinicBean clinic) {
        boolean success = true;
        try {
            String insertCitySQL = "INSERT INTO CLINIC ( NAME, ADDRESS, CITY, CONTACT, MORNINGSTARTTIME, MORNINGENDTIME, EVENINGSTARTTIME, EVENINGENDTIME, ACTIVEIND) "
                    + "VALUES( '" + clinic.getName() + "', '" + clinic.getAddress()+ "', '" + clinic.getCity()+ "', '" + clinic.getContact()+ "', '" + clinic.getMorningStartTime()+ "', '" + clinic.getMorningEndTime()+ "', '" + clinic.getEveningStartTime()+ "', '" + clinic.getEveningEndTime()+ "', 1)";
            connection.stmt.execute(insertCitySQL);
        } catch (SQLException ex) { 
            success = false;
        }
        return success;
    }
    
    public boolean updateClinic(ClinicBean clinic){
        
        String clinicUpdateQuery = "UPDATE CLINIC SET NAME = '"+clinic.getName()+"', ADDRESS = '"+clinic.getAddress()+"', " +
                                        " CITY = '"+clinic.getCity()+"', CONTACT = '"+clinic.getContact()+"',  MORNINGSTARTTIME = '"+clinic.getMorningStartTime()+"'," +
                                        " MORNINGENDTIME = '"+clinic.getMorningEndTime()+"', EVENINGSTARTTIME = '"+clinic.getEveningStartTime()+"', " +
                                        " EVENINGENDTIME = '"+clinic.getEveningEndTime()+"' WHERE ID = "+clinic.getId();
            
        try {
            connection.stmt.executeUpdate(clinicUpdateQuery);
        } catch (SQLException ex) {
            return false;
        }
        return true;
    }
    
    public List<ClinicBean> fetchClinics(){
        List<ClinicBean> clinics = new ArrayList<ClinicBean>();
        ResultSet rs = connection.getResult("SELECT * FROM CLINIC WHERE ACTIVEIND = 1");
        try{
            while(rs.next()){
                ClinicBean clinic = new ClinicBean();
                clinic.setId(rs.getInt("ID"));
                clinic.setName(rs.getString("NAME"));
                clinic.setAddress(rs.getString("ADDRESS"));
                clinic.setCity(rs.getString("CITY"));
                clinic.setContact(rs.getString("CONTACT"));
                clinic.setMorningStartTime(rs.getString("MORNINGSTARTTIME"));
                clinic.setMorningEndTime(rs.getString("MORNINGENDTIME"));
                clinic.setEveningStartTime(rs.getString("EVENINGSTARTTIME"));
                clinic.setEveningEndTime(rs.getString("EVENINGENDTIME"));
                clinics.add(clinic);
            }
        }
        catch(SQLException erroSQL){System.out.println(erroSQL.getStackTrace());}
        return clinics;
    }
}
