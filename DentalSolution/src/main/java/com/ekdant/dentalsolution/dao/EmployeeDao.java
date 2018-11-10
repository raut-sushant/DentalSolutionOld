/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ekdant.dentalsolution.dao;

import com.ekdant.dentalsolution.domain.EmployeeBean;
import com.ekdant.dentalsolution.utilities.ConnectionPool;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Sushant Raut
 */
public class EmployeeDao {
    ConnectionPool connection;
    DateFormat databaseDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    
    public EmployeeDao(){
        connection = ConnectionPool.getInstance();
    }
    
    public int getTotalEmployeeCount() {
        int employeeCount = 0;
        ResultSet rs = connection.getResult("SELECT COUNT(*) FROM EMPLOYEES WHERE ACTIVEIND = 1");
        try {
            while (rs.next()) {
                employeeCount = rs.getInt(1);
            }
        } catch (SQLException ex) {
        }
        return employeeCount;
    }
    
    public int addEmployee(EmployeeBean employee){
        int patientId = 0;
        
        String birthDateStr = null;
        try{
            birthDateStr = "'"+databaseDateFormat.format(employee.getBirthDate())+"'";
        }catch(Exception e){ }
        
        
        String patientInsertQuery = "INSERT INTO EMPLOYEES (NAME,BIRTHDAY,GENDER,PHOTO,ADDRESS,CITY,TELEPHONE,MOBILE,EMAIL,AGE,ACTIVEIND) VALUES ('"+employee.getName()+"',"+birthDateStr+",'"+employee.getGender()+"', NULL,'"+employee.getAddress()+"','"+employee.getCity()+"','"+employee.getTelephone()+"','"+employee.getMobile()+"','"+employee.getEmail()+"',"+employee.getAge()+",1)";
        
        try {
            connection.stmt.executeUpdate(patientInsertQuery);
            ResultSet rs = connection.stmt.executeQuery("select last_insert_rowid();");
            if (rs.next()) {
                patientId = rs.getInt(1);
            }
        } catch (SQLException ex) {System.out.println(ex.getMessage());}

        return patientId;
    }
    
    public boolean updateEmployee(EmployeeBean employee){
        
        String birthDateStr = null;
        try{
            birthDateStr = "'"+databaseDateFormat.format(employee.getBirthDate())+"'";        
        }catch(Exception e){ }
        String employeeUpdateQuery = "UPDATE EMPLOYEES SET NAME = '"+employee.getName()+"', BIRTHDAY = "+birthDateStr+"," +
                                        "GENDER  = '"+employee.getGender()+"', PHOTO = NULL, ADDRESS = '"+employee.getAddress()+"', " +
                                        "CITY = '"+employee.getCity()+"', TELEPHONE = '"+employee.getTelephone()+"',  EMAIL = '"+employee.getEmail()+"'," +
                                        " AGE = "+employee.getAge()+" WHERE EMPLOYEEID = "+employee.getEmployeeId();
            
        try {
            connection.stmt.executeUpdate(employeeUpdateQuery);
        } catch (SQLException ex) {
            return false;
        }
        return true;
    }
    
    public boolean deleteEmployeeById(int employeeId) {
        boolean deleteSuccess = true;
        String deleteEmployeeQuery = "UPDATE EMPLOYEES SET ACTIVEIND = 0 WHERE EMPLOYEEID = " + employeeId;
        try {
            connection.stmt.executeUpdate(deleteEmployeeQuery);
        } catch (SQLException ex) {
            deleteSuccess = false;
        }
        return deleteSuccess;
    }

    public List<EmployeeBean> fetchEmployees(String searchString) {
        List<EmployeeBean> employees = new ArrayList<EmployeeBean>();
        String sql = "SELECT * FROM EMPLOYEES WHERE NAME LIKE '%" + searchString + "%' OR TELEPHONE LIKE '%" + searchString + "%' OR CITY LIKE '%" + searchString + "%' AND ACTIVEIND = 1 ORDER BY EMPLOYEEID DESC";
        if (searchString == null || searchString.length() == 0) {
            sql = "SELECT * FROM EMPLOYEES WHERE ACTIVEIND = 1 ORDER BY EMPLOYEEID DESC";
        } 
        ResultSet rs = connection.getResult(sql);
        try{
            while (rs.next()) {
                EmployeeBean employee = new EmployeeBean();
                employee.setEmployeeId(rs.getInt("EMPLOYEEID"));
                employee.setName(rs.getString("NAME"));
                employee.setAge(rs.getInt("AGE"));
                employee.setGender(rs.getString("GENDER"));
                employee.setAddress(rs.getString("ADDRESS"));
                employee.setCity(rs.getString("CITY"));
                employee.setTelephone(rs.getString("TELEPHONE"));
                employee.setEmail(rs.getString("EMAIL"));
                if(rs.getString("BIRTHDAY").isEmpty())
                    employee.setBirthDate(databaseDateFormat.parse(rs.getString("BIRTHDAY")));
                employees.add(employee);
            }
        }catch(Exception e){System.out.println(e.getMessage());}
        return employees;
    }
}
