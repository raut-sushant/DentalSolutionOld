/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ekdant.dentalsolution.dao;

import com.ekdant.dentalsolution.domain.UserBean;
import com.ekdant.dentalsolution.utilities.ConnectionPool;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Sushant Raut
 */
public class UserDao {
    ConnectionPool connection;
    
    public UserDao(){
        connection = ConnectionPool.getInstance();
    }
    
    public UserBean fetchUser(String userName, String password) {
        ResultSet rs = connection.getResult("SELECT * FROM USERS WHERE LOGINID = '" + userName + "' AND PASSWORD = '" + password + "' AND ACTIVEIND = 1 ");
        UserBean user = new UserBean();
        try {
            while (rs.next()) {
                user.setUserId(rs.getInt("userId"));
                user.setName(rs.getString("name"));
                user.setLoginId(rs.getString("loginId"));
                user.setPassword(rs.getString("password"));
                user.setUserType(rs.getString("userType"));
            }
        } catch (SQLException erroSQL) {
        }
        return user;
    }
    
    public UserBean fetchDoctor() {
        ResultSet rs = connection.getResult("SELECT * FROM USERS WHERE USERTYPE = 'Doctor' AND ACTIVEIND = 1 ");
        UserBean user = new UserBean();
        try {
            while (rs.next()) {
                user.setUserId(rs.getInt("userId"));
                user.setName(rs.getString("name"));
                user.setLoginId(rs.getString("loginId"));
                user.setPassword(rs.getString("password"));
                user.setUserType(rs.getString("userType"));
            }
        } catch (SQLException erroSQL) {
        }
        return user;
    }
    
    public UserBean fetchStaff() {
        ResultSet rs = connection.getResult("SELECT * FROM USERS WHERE USERTYPE = 'Staff' AND ACTIVEIND = 1 ");
        UserBean user = new UserBean();
        try {
            while (rs.next()) {
                user.setUserId(rs.getInt("userId"));
                user.setName(rs.getString("name"));
                user.setLoginId(rs.getString("loginId"));
                user.setPassword(rs.getString("password"));
                user.setUserType(rs.getString("userType"));
            }
        } catch (SQLException errorSQL) {
        }
        return user;
    }
        
    public List<UserBean> fetchAllUsers(){
    
        ResultSet rs = connection.getResult("SELECT * FROM USERS WHERE ACTIVEIND = 1");
        List<UserBean> users = new ArrayList<UserBean>();
        try{
            while(rs.next()){
                UserBean user = new UserBean();
                user.setUserId(rs.getInt("userId"));
                user.setName(rs.getString("name"));
                user.setLoginId(rs.getString("loginId"));
                user.setPassword(rs.getString("password"));
                user.setUserType(rs.getString("userType"));
                users.add(user);
            }
        }
        catch(SQLException ex){System.out.println(ex.getMessage());}
        return users;
    }
    
    public boolean addUser(UserBean user){
        boolean success = true;
        try {
            String userInsertQuery = "INSERT INTO users (name,loginId,password,userType,ACTIVEIND) VALUES ('" + user.getName() + "','" + user.getLoginId() + "','" + user.getPassword() + "','" + user.getUserType() + "', 1)";
            connection.stmt.execute(userInsertQuery);
        } catch (Exception e) {
            success = false;
        }
        return success;
    }
    
    public boolean updateUser(UserBean user){
        boolean success = true;
        try {
            String userUpdateQuery = "UPDATE USERS SET name = '"+user.getName()+"', loginId = '"+user.getLoginId()+"', password = '"+user.getPassword()+"', userType = '"+user.getUserType()+"' WHERE userId = "+user.getUserId();
            connection.stmt.execute(userUpdateQuery);
        } catch (Exception e) {
            success = false;
        }
        return success;
    }
    
    public boolean deleteUser(UserBean user){
        boolean success = true;
        try {
            String userUpdateQuery = "UPDATE USERS SET ACTIVEIND = 0 WHERE userId = "+user.getUserId();
            connection.stmt.execute(userUpdateQuery);
        } catch (Exception e) {
            success = false;
        }
        return success;
    }
    
    public boolean userPresent(String userName) {
        int cityCount = 0;
        ResultSet rs = connection.getResult("SELECT COUNT(*) FROM USERS WHERE LOGINID = '"+userName+"' AND ACTIVEIND = 1");
        try {
            while(rs.next()){
                cityCount = rs.getInt(1);
            }
        } catch (SQLException ex) { }
        return cityCount > 0;
    }
}
