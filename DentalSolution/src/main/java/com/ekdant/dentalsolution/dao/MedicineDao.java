/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ekdant.dentalsolution.dao;

import com.ekdant.dentalsolution.domain.MedicineBean;
import com.ekdant.dentalsolution.utilities.ConnectionPool;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Sushant Raut
 */
public class MedicineDao {
    ConnectionPool connection;
    public MedicineDao(){
        connection = ConnectionPool.getInstance();
    }
    
    public List<MedicineBean> fetchMedicines(){
        List<MedicineBean> medicines = new ArrayList<MedicineBean>();
        ResultSet rs = connection.getResult("SELECT * FROM MEDICINES WHERE ACTIVEIND = 1");
        try{
            while(rs.next()){
                MedicineBean medicine = new MedicineBean();
                medicine.setId(rs.getInt("ID"));
                medicine.setName(rs.getString("NAME"));
                medicine.setStrength(rs.getString("STRENGTH"));
                medicine.setType(rs.getString("TYPE"));
                medicines.add(medicine);
            }
        }catch(SQLException e){System.out.println(e.getMessage());}
        return medicines;
    }
    
    public List<MedicineBean> fetchMedicinesForSearch(String searchText){
        List<MedicineBean> medicines = new ArrayList<MedicineBean>();
        String sql = "";
        if(searchText == null || searchText.isEmpty()){
            sql = "SELECT * FROM MEDICINES WHERE ACTIVEIND = 1";
        }else{
            sql = "SELECT * FROM MEDICINES WHERE NAME LIKE '%"+searchText+"%' AND ACTIVEIND = 1 ORDER BY NAME";
        }
        ResultSet rs = connection.getResult(sql);
        try{
            while(rs.next()){
                MedicineBean medicine = new MedicineBean();
                medicine.setId(rs.getInt("ID"));
                medicine.setName(rs.getString("NAME"));
                medicine.setStrength(rs.getString("STRENGTH"));
                medicine.setType(rs.getString("TYPE"));
                medicines.add(medicine);
            }
        }catch(SQLException ex){System.out.println(ex.getMessage());}
        return medicines;
    }
    
    public boolean medicineExist(MedicineBean medicine ) {
        int medicineCount = 0;
        String sql = "SELECT COUNT(*) FROM MEDICINES WHERE TYPE = '"+medicine.getType()+"' AND NAME = '"+medicine.getName()+"' AND STRENGTH = '"+medicine.getStrength()+"' AND ACTIVEIND = 1";
        ResultSet rs = connection.getResult(sql);
        try {    
            while(rs.next()){
                medicineCount = rs.getInt(1);
            }
        } catch (SQLException ex) {System.out.println(ex.getMessage());}
        return medicineCount > 0;
    }
    
    public boolean addMedicineIfNotPresent(MedicineBean medicine) {
        boolean success = true;
        if(!medicineExist(medicine)){
            String sql = "INSERT INTO MEDICINES  (TYPE ,NAME,STRENGTH  ) values ( '"+medicine.getType()+"', '"+medicine.getName()+"',  '"+medicine.getStrength()+"');";
            
            try {
                connection.stmt.execute(sql);
            } catch (SQLException ex) { success = false;}
        }           
        return success;
    }
    
    public boolean updateMedicine(MedicineBean medicine) {
        boolean success = true;
        String sql = "UPDATE MEDICINES SET NAME = '"+medicine.getName()+"',STRENGTH = '"+medicine.getStrength()+"' WHERE ID = "+medicine.getId();
        
        try {
            connection.stmt.execute(sql);
        } catch (SQLException ex) {
            success = false;
        }
         
        return success;
    }
    
    public boolean deleteMedicine(MedicineBean medicine) {
        boolean success = true;
        String sql = "UPDATE MEDICINES SET ACTIVEIND = 0 WHERE ID = "+medicine.getId();        
        try {
            connection.stmt.execute(sql);
        } catch (SQLException ex) {
            success = false;
        }
         
        return success;
    }
    
    public boolean medicineNotPresent(String newMedicineName) {
        int medicineCount = 0;
        ResultSet rs = connection.getResult("SELECT COUNT(*) FROM MEDICINES WHERE NAME = '"+newMedicineName+"' AND ACTIVEIND = 1");
        try {
            while(rs.next()){
                medicineCount = rs.getInt(1);
            }
        } catch (SQLException ex) { }
        return medicineCount == 0;
    }
    
    public List<String> fetchMedicineTypes(){
        List<String> medicineTypes = new ArrayList<String>();
        ResultSet rs = connection.getResult("SELECT * FROM MEDICINETYPE WHERE ACTIVEIND = 1");
        try{
            while(rs.next()){
                medicineTypes.add(rs.getString("MEDICINETYPE"));
            }
        }catch(SQLException ex){System.out.println(ex.getMessage());}
        return medicineTypes;
    }
    
    public List<String> fetchMedicineTypesForSearch(String searchText){
        List<String> medicineTypes = new ArrayList<String>();
        String sql = "SELECT * FROM MEDICINETYPE WHERE ACTIVEIND = 1";
        if(searchText == null || searchText.isEmpty()){
            sql = "SELECT * FROM MEDICINETYPE WHERE MEDICINETYPE LIKE '%"+searchText+"%' AND ACTIVEIND = 1 ORDER BY MEDICINETYPE";
        }
        else{
        }
        ResultSet rs = connection.getResult(sql);
        try{
            while(rs.next()){
                medicineTypes.add(rs.getString("MEDICINETYPE"));
            }
        }catch(SQLException ex){System.out.println(ex.getMessage());}
        return medicineTypes;
    }
    
    public boolean medicineTypeNotPresent(String medicineType) {
        int medicineTypeCount = 0;
        ResultSet rs = connection.getResult("SELECT COUNT(*) FROM MEDICINETYPE WHERE MEDICINETYPE = '"+medicineType+"' AND ACTIVEIND = 1");
        try {
            while(rs.next()){
                medicineTypeCount = rs.getInt(1);
            }
        } catch (SQLException ex) {System.out.println(ex.getMessage());}
        return medicineTypeCount == 0;
    }
    
    public boolean insertMedicineType(String medicineType) {
        boolean success = true;
        String sql = "INSERT INTO MEDICINETYPE (MEDICINETYPE, ACTIVEIND) VALUES('"+medicineType+"', 1)";
        
        try {
            connection.stmt.execute(sql);
        } catch (SQLException ex) {
            success = false;
        }
         
        return success;
    }
    
    public boolean updateMedicineType(String oldMedicineType, String newMedicineType) {
        boolean success = true;
        String sql = "UPDATE MEDICINETYPE SET MEDICINETYPE = '"+newMedicineType+"' WHERE MEDICINETYPE = '"+oldMedicineType+"'";
        
        try {
            connection.stmt.execute(sql);
        } catch (SQLException ex) {
            success = false;
        }
         
        return success;
    }
    
    public boolean deleteMedicineType(String medicineType) {
        boolean success = true;
        String sql = "UPDATE MEDICINETYPE SET ACTIVEIND = 0 WHERE MEDICINETYPE = '"+medicineType+"'";
        
        try {
            connection.stmt.execute(sql);
        } catch (SQLException ex) {
            success = false;
        }
         
        return success;
    }
}
