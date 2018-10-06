/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ekdant.dentalsolution.principal;


import com.ekdant.dentalsolution.login.Login;
import com.ekdant.dentalsolution.dao.ClinicDao;
import com.ekdant.dentalsolution.dao.DoctorDao;
import com.ekdant.dentalsolution.dao.SettingsDao;
import com.ekdant.dentalsolution.document.BackupDirectory;

/**
 *
 * @author Sushant Raut
 */
public class EkDant {
    public static final boolean showOdontogram = false;
    DoctorDao doctorDao;
    ClinicDao clinicDao; 
    SettingsDao settingsDao;
    
    public EkDant(){
        doctorDao = new DoctorDao();
        clinicDao = new ClinicDao();
        settingsDao = new SettingsDao();
        if(doctorDao.doctorSettingsRequired()){
            new InitialDoctorSettings().setVisible(true);
        }else if(clinicDao.clinicSettingRequired()){
            new InitialClinicSettings().setVisible(true);
        }else if(settingsDao.getSettingValue("DB_PATH").isEmpty()){
            new BackupDirectory().setVisible(true);
        }else{
            new Login().setVisible(true);
        }
    }   
    
}
