/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ekdant.dentalsolution.dao;

import com.ekdant.dentalsolution.utilities.AES;
import com.ekdant.dentalsolution.utilities.ConnectionPool;
import com.ekdant.dentalsolution.utilities.Utils;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Sushant Raut
 */
public class TokensDao {

    ConnectionPool connection;
    DateFormat databaseDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    
    public TokensDao(){
        connection = ConnectionPool.getInstance();
    }
    
    public String getTokenValue(String keyName) {
        String settingValue = null;
        ResultSet rs = connection.getResult("SELECT VALUE FROM TOKENS WHERE KEYNAME = '" + keyName + "' AND ACTIVEIND = 1");
        try {
            while (rs.next()) {
                settingValue = rs.getString("VALUE");
            }
        } catch (SQLException ex) {}
        return settingValue;
    }

    public void setToken(String token, String tokenValue) {
        try {
            connection.stmt.execute("UPDATE TOKENS SET VALUE = '" + tokenValue + "' WHERE KEYNAME = '" + token + "'");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error while activation", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public String fetchCurrentExpirationDate(){
        String expirationDate = null;
        try {
            expirationDate = AES.decrypt(getTokenByte("K4")).trim();
        } catch (Exception ex) {System.out.println(ex.getMessage());}
        return expirationDate;
    }
    
    public Date currentExpirationDate(){
        Date currentExpirationDate = new Date();
        try {
            currentExpirationDate = databaseDateFormat.parse(fetchCurrentExpirationDate());
        } catch (ParseException ex) {System.out.println(ex.getMessage());}
        return currentExpirationDate;
    }
    
    public String fetchSerialNumber(){
        String expirationDate = "";
        try {
            expirationDate = AES.decrypt(getTokenByte("K1"));
        } catch (Exception ex) {System.out.println(ex.getMessage());}
        return expirationDate.trim();
    }

    public byte[] getTokenByte(String token) {
        String decryptedStr = getTokenValue(token);
        return Utils.getByteArray(decryptedStr);
    }
    
    public String getDecryptedTokenValue(String token){
        String value = null;
        try{
            value = AES.decrypt(getTokenByte(token));
        }catch(Exception e){System.out.println(e.getMessage());}
        return value;
    }
}
