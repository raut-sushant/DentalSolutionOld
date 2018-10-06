/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ekdant.dentalsolution.dao;

import com.ekdant.dentalsolution.domain.CityBean;
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
public class CityDao {
    ConnectionPool connection;
    
    public CityDao(){
        connection = ConnectionPool.getInstance();
    }
    
    public List<CityBean> fetchCities(){
        ResultSet rs = connection.getResult("SELECT * FROM CITY WHERE ACTIVEIND = 1 ORDER BY CITYNAME");
        List<CityBean> cities = new ArrayList<CityBean>();
        try {
            while (rs.next()) {
                CityBean city = new CityBean();
                city.setId(rs.getInt("CITYID"));
                city.setName(rs.getString("CITYNAME"));
                cities.add(city);
            }
        } catch (SQLException e) {System.out.println(e.getMessage());} 
        return cities;
    }

    public boolean cityNotPresent(String newCityName) {
        int cityCount = 0;
        ResultSet rs = connection.getResult("SELECT COUNT(*) FROM CITY WHERE CITYNAME = '"+newCityName+"' AND ACTIVEIND = 1");
        try {
            while(rs.next()){
                cityCount = rs.getInt(1);
            }
        } catch (SQLException ex) { }
        return cityCount == 0;
    }
    
    public List<CityBean> fetchCities(String searchText){
        ResultSet rs = connection.getResult("SELECT * FROM CITY WHERE CITYNAME LIKE '%"+searchText+"%' AND ACTIVEIND = 1 ORDER BY CITYNAME");
        List<CityBean> cities = new ArrayList<CityBean>();
        try {
            while (rs.next()) {
                CityBean city = new CityBean();
                city.setId(rs.getInt("CITYID"));
                city.setName(rs.getString("CITYNAME"));
                cities.add(city);
            }
        } catch (SQLException e) {System.out.println(e.getMessage());} 
        return cities;
    }
    
    public void getCities(JComboBox citiesCB) {        
        List<CityBean> cities = fetchCities();
        for (CityBean city : cities){
                citiesCB.addItem(city.getName());
        }
    }
    
    public String fetchLatestCity(){
        String newlyAddedCity = "";
        try {
            ResultSet rs = connection.getResult("SELECT CITYNAME FROM CITY ORDER BY CITYID DESC");
            while (rs.next()) {
                newlyAddedCity = rs.getString("CITYNAME");
                break;
            }
        } catch (SQLException ex) {
        }
        return newlyAddedCity;
    }
    
    public boolean addCity(CityBean city){
        boolean success = true;
        try {
            String insertCitySQL = "INSERT INTO CITY ( CITYNAME, ACTIVEIND) VALUES( '" + city.getName() + "', 1)";
            connection.stmt.execute(insertCitySQL);
        } catch (SQLException ex) {
            success = false;
        }
        return success;
    }
    
    public boolean updateCity(CityBean cityBean){
        boolean success = true;
        String updateCitySQL = "UPDATE CITY SET CITYNAME = '"+cityBean.getName()+"' WHERE CITYID = "+cityBean.getId();
        try {
            connection.stmt.execute(updateCitySQL);
        } catch (SQLException ex) {
            success = false;
        }
        return success;
    }
    
    public boolean deleteCity(CityBean city){
        boolean success = true;
        String updateCitySQL = "UPDATE CITY SET ACTIVEIND = 0 WHERE CITYID = "+city.getId();
        try {
            connection.stmt.execute(updateCitySQL);
        } catch (SQLException ex) {
            success = false;
        }
        return success;
    }
}
