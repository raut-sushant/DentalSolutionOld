/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ekdant.dentalsolution.dao;

import com.ekdant.dentalsolution.domain.ActivationBean;
import com.ekdant.dentalsolution.utilities.ConnectionPool;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 *
 * @author Sushant Raut
 */
public class ActivationDao {
    ConnectionPool connection;
    DateFormat databaseDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    
    public ActivationDao(){
        connection = ConnectionPool.getInstance();
    }
    
    public boolean addActivation(ActivationBean activation){
        boolean success = true;
        Calendar todayCal = new GregorianCalendar();
        Date today = new Date(todayCal.getTimeInMillis());

        String startDateStr = null;
        String endDateStr = null;
        try {
            startDateStr = "'" + databaseDateFormat.format(activation.getStartDate()) + "'";
        } catch (Exception e) {
        }
        try {
            endDateStr = "'" + databaseDateFormat.format(activation.getEndDate()) + "'";
        } catch (Exception e) {
        }
        String sql = "INSERT INTO ACTIVATION( ACTIVATIONKEY, ACTIVATIONDATE, STARTDATE, ENDDATE, ACTIVATIONDAYS, ACTIVEIND) VALUES ( '"+activation.getActivationFileKey()+"', '" + databaseDateFormat.format(today) + "', " + startDateStr + ", " + endDateStr + ", " + activation.getActivationDays() + ", 1)";
        try {
            connection.stmt.execute(sql);
        } catch (SQLException ex) {
            success = false;
        }
        return success;
    }
    
    public boolean activationPresent(String activationFileKey){
        boolean activationPresent = false;
        try {
            ResultSet rs = connection.getResult("SELECT ACTIVATIONKEY FROM ACTIVATION WHERE ACTIVEIND = 1");
            while (rs.next()) {
                if(activationFileKey.equalsIgnoreCase(rs.getString("ACTIVATIONKEY"))){
                    activationPresent = true;
                }                
            }
        } catch (SQLException ex) {System.out.println(ex.getMessage());}
        return activationPresent;
    }
}
