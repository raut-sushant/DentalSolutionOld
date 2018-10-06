/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ekdant.dentalsolution.dao;

import com.ekdant.dentalsolution.domain.SettingsBean;
import com.ekdant.dentalsolution.utilities.ConnectionPool;
import com.ekdant.dentalsolution.utilities.Utils;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sushant Raut
 */
public class SettingsDao {
    
    ConnectionPool connection;
    
    public SettingsDao(){
        connection = ConnectionPool.getInstance();
    }

    public String getSettingValue(String keyName) {
        String settingValue = null;
        ResultSet rs = connection.getResult("SELECT VALUE FROM SETTINGS WHERE KEYNAME = '" + keyName + "' AND ACTIVEIND = 1");
        try {
            while (rs.next()) {
                settingValue = rs.getString("VALUE");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
        }
        return settingValue;
    }
    
    public List<SettingsBean> fetchSettings(){
        List<SettingsBean> settings = new ArrayList<SettingsBean>();
        ResultSet rs = connection.getResult("SELECT * FROM SETTINGS WHERE ACTIVEIND = 1 ORDER BY KEYNAME");
        try {
            while (rs.next()) {
                SettingsBean setting = new SettingsBean();
                setting.setKey(rs.getString("KEYNAME"));
                setting.setValue(rs.getString("VALUE"));
                setting.setEditable(rs.getInt("EDITABLE") > 0);
                settings.add(setting);
            }
        } catch (SQLException ex) {System.out.println(ex.getMessage());}
        return settings;
    }
    
    public boolean updateSettings(SettingsBean setting) {
        boolean success = true;
        String sql = "UPDATE SETTINGS SET VALUE = '"+setting.getValue()+"' WHERE KEYNAME = '"+setting.getKey()+"'";
        
        try {
            connection.stmt.execute(sql);
        } catch (SQLException ex) {
            success = false;
            System.out.println(ex.getMessage());
        }
         
        return success;
    }
    
    public String getMySQLPath() {
        ConnectionPool connection = ConnectionPool.getInstance();
        ResultSet rs = connection.getResult("SELECT VALUE FROM SETTINGS WHERE KEYNAME = 'DB_PATH'");
        String mySQLPath = "";
        try {
            while (rs.next()) {
                mySQLPath = rs.getString(1);
            }
        } catch (SQLException ex) {System.out.println(ex.getMessage());}
        return mySQLPath;
    }
}
