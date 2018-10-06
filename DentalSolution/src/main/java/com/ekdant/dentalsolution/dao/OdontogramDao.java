/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ekdant.dentalsolution.dao;

import com.ekdant.dentalsolution.domain.OdontogramBean;
import com.ekdant.dentalsolution.utilities.ConnectionPool;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Sushant Raut
 */
public class OdontogramDao {
    
    ConnectionPool connection;
    
    public OdontogramDao(){
        connection = ConnectionPool.getInstance();
    }
    
    public OdontogramBean fetchOdontogram(int patientId) {
        OdontogramBean odontogramBean = new OdontogramBean();
        ResultSet rs = connection.getResult("SELECT * FROM odontograma WHERE PATIENTID = '"+patientId+"'");
        try {
            while (rs.next()) {     
                odontogramBean.setId(rs.getInt("cod_odontograma"));
                odontogramBean.setPatientId(rs.getInt("PATIENTID"));
                odontogramBean.setOdon18(rs.getString("18_odon"));
                odontogramBean.setOdon17(rs.getString("17_odon"));
                odontogramBean.setOdon16(rs.getString("16_odon"));
                odontogramBean.setOdon1555(rs.getString("15-55_odon"));
                odontogramBean.setOdon1454(rs.getString("14-54_odon"));
                odontogramBean.setOdon1353(rs.getString("13-53_odon"));
                odontogramBean.setOdon1252(rs.getString("12-52_odon"));
                odontogramBean.setOdon1151(rs.getString("11-51_odon"));
                odontogramBean.setOdon4181(rs.getString("41-81_odon"));
                odontogramBean.setOdon4282(rs.getString("42-82_odon"));
                odontogramBean.setOdon4383(rs.getString("43-83_odon"));
                odontogramBean.setOdon4484(rs.getString("44-84_odon"));
                odontogramBean.setOdon4585(rs.getString("45-85_odon"));
                odontogramBean.setOdon46(rs.getString("46_odon"));
                odontogramBean.setOdon47(rs.getString("47_odon"));
                odontogramBean.setOdon48(rs.getString("48_odon"));
                odontogramBean.setOdon28(rs.getString("28_odon"));
                odontogramBean.setOdon27(rs.getString("27_odon"));
                odontogramBean.setOdon26(rs.getString("26_odon"));
                odontogramBean.setOdon6525(rs.getString("65-25_odon"));
                odontogramBean.setOdon6424(rs.getString("64-24_odon"));
                odontogramBean.setOdon6323(rs.getString("63-23_odon"));
                odontogramBean.setOdon6222(rs.getString("62-22_odon"));
                odontogramBean.setOdon6121(rs.getString("61-21_odon"));
                odontogramBean.setOdon7131(rs.getString("71-31_odon"));
                odontogramBean.setOdon7232(rs.getString("72-32_odon"));
                odontogramBean.setOdon7333(rs.getString("73-33_odon"));
                odontogramBean.setOdon7434(rs.getString("74-34_odon"));
                odontogramBean.setOdon7535(rs.getString("75-35_odon"));
                odontogramBean.setOdon36(rs.getString("36_odon"));
                odontogramBean.setOdon37(rs.getString("37_odon"));
                odontogramBean.setOdon38(rs.getString("38_odon"));
            }
        } catch (SQLException ex) {
        }
        return odontogramBean;
    }
    
    public boolean insertOdontogram(OdontogramBean odontogram){
        boolean success = true;
        String odontoInsert = "INSERT INTO odontograma (PATIENTID,nome_pac,'18_odon','17_odon','16_odon','15-55_odon','14-54_odon','13-53_odon','12-52_odon','11-51_odon','41-81_odon','42-82_odon','43-83_odon','44-84_odon','45-85_odon','46_odon','47_odon','48_odon','28_odon','27_odon','26_odon','65-25_odon','64-24_odon','63-23_odon','62-22_odon','61-21_odon','71-31_odon','72-32_odon','73-33_odon','74-34_odon','75-35_odon','36_odon','37_odon','38_odon') VALUES (" 
                    +odontogram.getPatientId()+",'"+odontogram.getName()+"'," 
                    + "'" + odontogram.getOdon18() + "',"
                    + "'" + odontogram.getOdon17() + "',"
                    + "'" + odontogram.getOdon16() + "',"
                    + "'" + odontogram.getOdon1555()+ "',"
                    + "'" + odontogram.getOdon1454()+ "',"
                    + "'" + odontogram.getOdon1353()+ "',"
                    + "'" + odontogram.getOdon1252()+ "',"
                    + "'" + odontogram.getOdon1151()+ "',"
                    + "'" + odontogram.getOdon4181()+ "',"
                    + "'" + odontogram.getOdon4282()+ "',"
                    + "'" + odontogram.getOdon4383()+ "',"
                    + "'" + odontogram.getOdon4484()+ "',"
                    + "'" + odontogram.getOdon4585()+ "',"
                    + "'" + odontogram.getOdon46() + "',"
                    + "'" + odontogram.getOdon47() + "',"
                    + "'" + odontogram.getOdon48() + "',"
                    + "'" + odontogram.getOdon28() + "',"
                    + "'" + odontogram.getOdon27() + "',"
                    + "'" + odontogram.getOdon26() + "',"
                    + "'" + odontogram.getOdon6525()+ "',"
                    + "'" + odontogram.getOdon6424()+ "',"
                    + "'" + odontogram.getOdon6323()+ "',"
                    + "'" + odontogram.getOdon6222()+ "',"
                    + "'" + odontogram.getOdon6121()+ "',"
                    + "'" + odontogram.getOdon7131()+ "',"
                    + "'" + odontogram.getOdon7232()+ "',"
                    + "'" + odontogram.getOdon7333()+ "',"
                    + "'" + odontogram.getOdon7434()+ "',"
                    + "'" + odontogram.getOdon7535()+ "',"
                    + "'" + odontogram.getOdon36() + "',"
                    + "'" + odontogram.getOdon37() + "',"
                    + "'" + odontogram.getOdon38() + "' )";
        try {
            connection.stmt.execute(odontoInsert);
        } catch (SQLException ex) {
            success = false;
        }
        return success;
    }
    
    public boolean updateOdontogram(OdontogramBean odontogram){
        boolean success = true;
        String odontoUpdate = "UPDATE odontograma SET "
                    + "`18_odon` = '" + odontogram.getOdon18() + "',"
                    + "`17_odon` = '" + odontogram.getOdon17() + "',"
                    + "`16_odon` = '" + odontogram.getOdon16() + "',"
                    + "`15-55_odon` = '" + odontogram.getOdon1555()+ "',"
                    + "`14-54_odon` = '" + odontogram.getOdon1454()+ "',"
                    + "`13-53_odon` = '" + odontogram.getOdon1353()+ "',"
                    + "`12-52_odon` = '" + odontogram.getOdon1252()+ "',"
                    + "`11-51_odon` = '" + odontogram.getOdon1151()+ "',"
                    + "`41-81_odon` = '" + odontogram.getOdon4181()+ "',"
                    + "`42-82_odon` = '" + odontogram.getOdon4282()+ "',"
                    + "`43-83_odon` = '" + odontogram.getOdon4383()+ "',"
                    + "`44-84_odon` = '" + odontogram.getOdon4484()+ "',"
                    + "`45-85_odon` = '" + odontogram.getOdon4585()+ "',"
                    + "`46_odon` = '" + odontogram.getOdon46() + "',"
                    + "`47_odon` = '" + odontogram.getOdon47() + "',"
                    + "`48_odon` = '" + odontogram.getOdon48() + "',"
                    + "`28_odon` = '" + odontogram.getOdon28() + "',"
                    + "`27_odon` = '" + odontogram.getOdon27() + "',"
                    + "`26_odon` = '" + odontogram.getOdon26() + "',"
                    + "`65-25_odon` = '" + odontogram.getOdon6525()+ "',"
                    + "`64-24_odon` = '" + odontogram.getOdon6424()+ "',"
                    + "`63-23_odon` = '" + odontogram.getOdon6323()+ "',"
                    + "`62-22_odon` = '" + odontogram.getOdon6222()+ "',"
                    + "`61-21_odon` = '" + odontogram.getOdon6121()+ "',"
                    + "`71-31_odon` = '" + odontogram.getOdon7131()+ "',"
                    + "`72-32_odon` = '" + odontogram.getOdon7232()+ "',"
                    + "`73-33_odon` = '" + odontogram.getOdon7333()+ "',"
                    + "`74-34_odon` = '" + odontogram.getOdon7434()+ "',"
                    + "`75-35_odon` = '" + odontogram.getOdon7535()+ "',"
                    + "`36_odon` = '" + odontogram.getOdon36() + "',"
                    + "`37_odon` = '" + odontogram.getOdon37() + "',"
                    + "`38_odon` = '" + odontogram.getOdon38() + "' "
                    + "WHERE PATIENTID = " + odontogram.getPatientId();

        try {
            connection.stmt.execute(odontoUpdate);
        } catch (SQLException ex) {
            success = false;
        }
        return success;
    }
}
