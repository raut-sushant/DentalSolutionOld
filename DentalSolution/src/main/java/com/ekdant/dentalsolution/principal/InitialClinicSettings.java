/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ekdant.dentalsolution.principal;

import com.ekdant.dentalsolution.dao.CityDao;
import com.ekdant.dentalsolution.dao.ClinicDao;
import com.ekdant.dentalsolution.dao.UserDao;
import com.ekdant.dentalsolution.domain.ClinicBean;
import com.ekdant.dentalsolution.domain.UserBean;
import java.awt.Color;
import java.awt.Font;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Sushant Raut
 */
public class InitialClinicSettings extends javax.swing.JFrame {
    
    ClinicDao clinicDao;
    UserDao userDao;
    CityDao cityDao;
    int clinicId = 0;
    int staffId = 0;

    /**
     * Creates new form initialSettings
     */
    public InitialClinicSettings() {
        userDao = new UserDao();
        clinicDao = new ClinicDao();
        cityDao = new CityDao();
        initComponents();
        cityDao.getCities(cityCB); 
        populateClinic();
    }
    
    private void populateClinic(){
        List<ClinicBean> clinics = clinicDao.fetchClinics();
        UserBean staff = userDao.fetchStaff();
        staffId = staff.getUserId();
        ClinicBean clinic = clinics.get(0);
        clinicId = clinic.getId();
        clinicNameTxt.setText(clinic.getName());
        clinicAddressTxt.setText(clinic.getAddress());
        clinicContactTxt.setText(clinic.getContact());
        cityCB.setSelectedItem(clinic.getCity());
        morningStartTimeCB.setSelectedItem(clinic.getMorningStartTime());
        morningEndTimeCB.setSelectedItem(clinic.getMorningEndTime());
        eveningStartTimeCB.setSelectedItem(clinic.getEveningStartTime());
        eveningEndTimeCB.setSelectedItem(clinic.getEveningEndTime());
        if(clinicId > 0){
            staffNameTxt.setText(staff.getName());
            loginIdTxt.setText(staff.getLoginId());
            loginIdTxt.setEditable(false);
        }
    }
    
    private void validateAndSaveInformation(){
        if (validateForm()) {
            ClinicBean clinic = new ClinicBean();
            clinic.setId(clinicId);
            clinic.setName(clinicNameTxt.getText());
            clinic.setAddress(clinicAddressTxt.getText());
            clinic.setContact(clinicContactTxt.getText());
            clinic.setCity(cityCB.getSelectedItem().toString());
            clinic.setMorningStartTime(morningStartTimeCB.getSelectedItem().toString());
            clinic.setMorningEndTime(morningEndTimeCB.getSelectedItem().toString());
            clinic.setEveningStartTime(eveningStartTimeCB.getSelectedItem().toString());
            clinic.setEveningEndTime(eveningEndTimeCB.getSelectedItem().toString());
            
            UserBean user = new UserBean();
            user.setUserId(staffId);
            user.setName(staffNameTxt.getText());
            user.setLoginId(loginIdTxt.getText());
            user.setPassword(passwordPwd.getText());
            user.setUserType("Staff");
     
            if(clinicId == 0){
                clinicDao.addClinic(clinic);
            }
            else{
                clinicDao.updateClinic(clinic);
            }
            
            if(staffId == 0){
                userDao.addUser(user);
                new EkDant();
                this.dispose();
            }else{
                userDao.updateUser(user);
            }
            
            msgLbl.setText("Data Saved Succefully");
            msgLbl.setFont(new Font("Vardhana", Font.PLAIN, 12));
            msgLbl.setForeground(new Color(51, 51, 255));
        }
    }
    
    private boolean validateForm() {
        boolean valid = true;
        if(clinicNameTxt.getText().isEmpty()){
            valid = false;
            JOptionPane.showMessageDialog(null,"Please Enter Doctor Name","ERROR", JOptionPane.ERROR_MESSAGE);
        }
        else if(clinicAddressTxt.getText().isEmpty() || clinicAddressTxt.getText().length() < 10){
            valid = false;
            JOptionPane.showMessageDialog(null,"Please Enter Doctor Mobile Number","ERROR", JOptionPane.ERROR_MESSAGE);
        }
        else if(loginIdTxt.getText().isEmpty()){
            valid = false;
            JOptionPane.showMessageDialog(null,"Please Enter valid Login Id","ERROR", JOptionPane.ERROR_MESSAGE);
        }
        else if(passwordPwd.getText().isEmpty()){
            valid = false;
            JOptionPane.showMessageDialog(null,"Please Enter valid Password","ERROR", JOptionPane.ERROR_MESSAGE);
        }
        else if(!passwordPwd.getText().equalsIgnoreCase(rePasswordPwd.getText())){
            valid = false;
            JOptionPane.showMessageDialog(null,"Password and Re-Entered Password should be same","ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return valid;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(InitialClinicSettings.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(InitialClinicSettings.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InitialClinicSettings.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InitialClinicSettings.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new InitialClinicSettings().setVisible(true);
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        doctorDetailsPanel = new javax.swing.JPanel();
        clinicNameLbl = new javax.swing.JLabel();
        clinicNameTxt = new javax.swing.JTextField();
        clinicAddressLbl = new javax.swing.JLabel();
        clinicAddressTxt = new javax.swing.JTextField();
        clinicContactLbl = new javax.swing.JLabel();
        clinicContactTxt = new javax.swing.JTextField();
        clinicCityLbl = new javax.swing.JLabel();
        cityCB = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        morningTimeLbl = new javax.swing.JLabel();
        eveningTimeLbl = new javax.swing.JLabel();
        morningStartTimeCB = new javax.swing.JComboBox();
        morningEndTimeCB = new javax.swing.JComboBox();
        eveningStartTimeCB = new javax.swing.JComboBox();
        eveningEndTimeCB = new javax.swing.JComboBox();
        loginPanel = new javax.swing.JPanel();
        loginIdTxt = new javax.swing.JTextField();
        loginIdLbl = new javax.swing.JLabel();
        passwordLbl = new javax.swing.JLabel();
        rePasswordLbl = new javax.swing.JLabel();
        passwordPwd = new javax.swing.JPasswordField();
        rePasswordPwd = new javax.swing.JPasswordField();
        staffNameLbl = new javax.swing.JLabel();
        staffNameTxt = new javax.swing.JTextField();
        buttonPanel = new javax.swing.JPanel();
        saveBtn = new javax.swing.JButton();
        cancelBtn = new javax.swing.JButton();
        msgLbl = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Clinic Details");

        clinicNameLbl.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        clinicNameLbl.setForeground(new java.awt.Color(51, 51, 255));
        clinicNameLbl.setText("Clinic Name:");

        clinicAddressLbl.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        clinicAddressLbl.setForeground(new java.awt.Color(51, 51, 255));
        clinicAddressLbl.setText("Clinic Address:");

        clinicContactLbl.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        clinicContactLbl.setForeground(new java.awt.Color(51, 51, 255));
        clinicContactLbl.setText("Contact:");

        clinicCityLbl.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        clinicCityLbl.setForeground(new java.awt.Color(51, 51, 255));
        clinicCityLbl.setText("City:");

        jLabel3.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 51, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Add Clinic Details");

        morningTimeLbl.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        morningTimeLbl.setForeground(new java.awt.Color(51, 51, 255));
        morningTimeLbl.setText("Morning Time");

        eveningTimeLbl.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        eveningTimeLbl.setForeground(new java.awt.Color(51, 51, 255));
        eveningTimeLbl.setText("Evening Time");

        morningStartTimeCB.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "06:00:00", "06:30:00", "07:00:00", "07:30:00", "08:00:00", "08:30:00", "09:00:00", "09:30:00", "10:00:00", "10:30:00", "11:00:00", "11:30:00", "12:00:00", "12:30:00", "13:00:00", "13:30:00", "14:00:00", "14:30:00", "15:00:00", "15:30:00", "16:00:00", "16:30:00", "17:00:00", "17:30:00", "18:00:00", "18:30:00", "19:00:00", "19:30:00", "20:00:00", "20:30:00", "21:00:00", "21:30:00", "22:00:00", "22:30:00", "23:00:00", "23:30:00" }));

        morningEndTimeCB.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "06:00:00", "06:30:00", "07:00:00", "07:30:00", "08:00:00", "08:30:00", "09:00:00", "09:30:00", "10:00:00", "10:30:00", "11:00:00", "11:30:00", "12:00:00", "12:30:00", "13:00:00", "13:30:00", "14:00:00", "14:30:00", "15:00:00", "15:30:00", "16:00:00", "16:30:00", "17:00:00", "17:30:00", "18:00:00", "18:30:00", "19:00:00", "19:30:00", "20:00:00", "20:30:00", "21:00:00", "21:30:00", "22:00:00", "22:30:00", "23:00:00", "23:30:00" }));

        eveningStartTimeCB.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "06:00:00", "06:30:00", "07:00:00", "07:30:00", "08:00:00", "08:30:00", "09:00:00", "09:30:00", "10:00:00", "10:30:00", "11:00:00", "11:30:00", "12:00:00", "12:30:00", "13:00:00", "13:30:00", "14:00:00", "14:30:00", "15:00:00", "15:30:00", "16:00:00", "16:30:00", "17:00:00", "17:30:00", "18:00:00", "18:30:00", "19:00:00", "19:30:00", "20:00:00", "20:30:00", "21:00:00", "21:30:00", "22:00:00", "22:30:00", "23:00:00", "23:30:00" }));

        eveningEndTimeCB.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "06:00:00", "06:30:00", "07:00:00", "07:30:00", "08:00:00", "08:30:00", "09:00:00", "09:30:00", "10:00:00", "10:30:00", "11:00:00", "11:30:00", "12:00:00", "12:30:00", "13:00:00", "13:30:00", "14:00:00", "14:30:00", "15:00:00", "15:30:00", "16:00:00", "16:30:00", "17:00:00", "17:30:00", "18:00:00", "18:30:00", "19:00:00", "19:30:00", "20:00:00", "20:30:00", "21:00:00", "21:30:00", "22:00:00", "22:30:00", "23:00:00", "23:30:00" }));

        javax.swing.GroupLayout doctorDetailsPanelLayout = new javax.swing.GroupLayout(doctorDetailsPanel);
        doctorDetailsPanel.setLayout(doctorDetailsPanelLayout);
        doctorDetailsPanelLayout.setHorizontalGroup(
            doctorDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(doctorDetailsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(doctorDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(doctorDetailsPanelLayout.createSequentialGroup()
                        .addGroup(doctorDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, doctorDetailsPanelLayout.createSequentialGroup()
                                .addComponent(clinicContactLbl)
                                .addGap(18, 18, 18)
                                .addComponent(clinicContactTxt))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, doctorDetailsPanelLayout.createSequentialGroup()
                                .addGroup(doctorDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, doctorDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(morningTimeLbl)
                                        .addComponent(clinicCityLbl))
                                    .addComponent(eveningTimeLbl))
                                .addGroup(doctorDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(doctorDetailsPanelLayout.createSequentialGroup()
                                        .addGap(20, 20, 20)
                                        .addGroup(doctorDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(cityCB, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(doctorDetailsPanelLayout.createSequentialGroup()
                                                .addComponent(morningStartTimeCB, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(morningEndTimeCB, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addGroup(doctorDetailsPanelLayout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(eveningStartTimeCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(eveningEndTimeCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, doctorDetailsPanelLayout.createSequentialGroup()
                                .addGroup(doctorDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(clinicAddressLbl)
                                    .addComponent(clinicNameLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(doctorDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(clinicNameTxt)
                                    .addComponent(clinicAddressTxt))))
                        .addContainerGap(41, Short.MAX_VALUE))))
        );

        doctorDetailsPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {clinicAddressLbl, clinicCityLbl, clinicContactLbl, clinicNameLbl, eveningTimeLbl, morningTimeLbl});

        doctorDetailsPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {eveningEndTimeCB, eveningStartTimeCB, morningEndTimeCB, morningStartTimeCB});

        doctorDetailsPanelLayout.setVerticalGroup(
            doctorDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(doctorDetailsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addGroup(doctorDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(clinicNameLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(clinicNameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(doctorDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(clinicAddressLbl)
                    .addComponent(clinicAddressTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(doctorDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(clinicContactLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(clinicContactTxt))
                .addGap(18, 18, 18)
                .addGroup(doctorDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(clinicCityLbl)
                    .addComponent(cityCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(doctorDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(doctorDetailsPanelLayout.createSequentialGroup()
                        .addGroup(doctorDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(morningTimeLbl)
                            .addComponent(morningStartTimeCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(doctorDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(eveningTimeLbl)
                            .addComponent(eveningStartTimeCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(doctorDetailsPanelLayout.createSequentialGroup()
                        .addComponent(morningEndTimeCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(eveningEndTimeCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(52, Short.MAX_VALUE))
        );

        doctorDetailsPanelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {cityCB, clinicAddressLbl, clinicAddressTxt, clinicCityLbl, clinicContactLbl, clinicContactTxt, clinicNameLbl, clinicNameTxt, eveningEndTimeCB, eveningStartTimeCB, eveningTimeLbl, jLabel3, morningEndTimeCB, morningStartTimeCB, morningTimeLbl});

        loginPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Staff Login Details", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Verdana", 1, 12), new java.awt.Color(51, 51, 255))); // NOI18N

        loginIdLbl.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        loginIdLbl.setForeground(new java.awt.Color(51, 51, 255));
        loginIdLbl.setText("Staff Login Id:");

        passwordLbl.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        passwordLbl.setForeground(new java.awt.Color(51, 51, 255));
        passwordLbl.setText("Staff Password:");

        rePasswordLbl.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        rePasswordLbl.setForeground(new java.awt.Color(51, 51, 255));
        rePasswordLbl.setText("Re-Enter Staff Password:");

        staffNameLbl.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        staffNameLbl.setForeground(new java.awt.Color(51, 51, 255));
        staffNameLbl.setText("Staff Name:");

        javax.swing.GroupLayout loginPanelLayout = new javax.swing.GroupLayout(loginPanel);
        loginPanel.setLayout(loginPanelLayout);
        loginPanelLayout.setHorizontalGroup(
            loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(loginPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(loginPanelLayout.createSequentialGroup()
                        .addComponent(loginIdLbl)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(loginIdTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(loginPanelLayout.createSequentialGroup()
                        .addComponent(passwordLbl)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(passwordPwd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(loginPanelLayout.createSequentialGroup()
                        .addComponent(rePasswordLbl)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rePasswordPwd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(loginPanelLayout.createSequentialGroup()
                        .addComponent(staffNameLbl)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(staffNameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(74, Short.MAX_VALUE))
        );

        loginPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {loginIdLbl, passwordLbl, rePasswordLbl, staffNameLbl});

        loginPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {loginIdTxt, passwordPwd, rePasswordPwd});

        loginPanelLayout.setVerticalGroup(
            loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(loginPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(staffNameLbl)
                    .addComponent(staffNameTxt, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(loginIdLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(loginIdTxt, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(passwordLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(passwordPwd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rePasswordLbl)
                    .addComponent(rePasswordPwd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(45, Short.MAX_VALUE))
        );

        loginPanelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {loginIdLbl, loginIdTxt, passwordLbl, passwordPwd, rePasswordLbl, rePasswordPwd, staffNameLbl});

        saveBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/EkDant/icones/Chief of Staff 2b Add.png"))); // NOI18N
        saveBtn.setText("Save");
        saveBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveBtnActionPerformed(evt);
            }
        });

        cancelBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/EkDant/icones/Chief of Staff 2b Stop.png"))); // NOI18N
        cancelBtn.setText("Cancel");
        cancelBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout buttonPanelLayout = new javax.swing.GroupLayout(buttonPanel);
        buttonPanel.setLayout(buttonPanelLayout);
        buttonPanelLayout.setHorizontalGroup(
            buttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buttonPanelLayout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addComponent(saveBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(cancelBtn)
                .addGap(18, 18, 18)
                .addComponent(msgLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        buttonPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {cancelBtn, saveBtn});

        buttonPanelLayout.setVerticalGroup(
            buttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buttonPanelLayout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(buttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(saveBtn)
                    .addComponent(cancelBtn)
                    .addComponent(msgLbl))
                .addContainerGap(40, Short.MAX_VALUE))
        );

        buttonPanelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {cancelBtn, msgLbl, saveBtn});

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(buttonPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addComponent(doctorDetailsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(loginPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(doctorDetailsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addComponent(loginPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(buttonPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cancelBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelBtnActionPerformed
        this.dispose();
    }//GEN-LAST:event_cancelBtnActionPerformed

    private void saveBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveBtnActionPerformed
        validateAndSaveInformation();
    }//GEN-LAST:event_saveBtnActionPerformed
 
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel buttonPanel;
    private javax.swing.JButton cancelBtn;
    private javax.swing.JComboBox cityCB;
    private javax.swing.JLabel clinicAddressLbl;
    private javax.swing.JTextField clinicAddressTxt;
    private javax.swing.JLabel clinicCityLbl;
    private javax.swing.JLabel clinicContactLbl;
    private javax.swing.JTextField clinicContactTxt;
    private javax.swing.JLabel clinicNameLbl;
    private javax.swing.JTextField clinicNameTxt;
    private javax.swing.JPanel doctorDetailsPanel;
    private javax.swing.JComboBox eveningEndTimeCB;
    private javax.swing.JComboBox eveningStartTimeCB;
    private javax.swing.JLabel eveningTimeLbl;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel loginIdLbl;
    private javax.swing.JTextField loginIdTxt;
    private javax.swing.JPanel loginPanel;
    private javax.swing.JComboBox morningEndTimeCB;
    private javax.swing.JComboBox morningStartTimeCB;
    private javax.swing.JLabel morningTimeLbl;
    private javax.swing.JLabel msgLbl;
    private javax.swing.JLabel passwordLbl;
    private javax.swing.JPasswordField passwordPwd;
    private javax.swing.JLabel rePasswordLbl;
    private javax.swing.JPasswordField rePasswordPwd;
    private javax.swing.JButton saveBtn;
    private javax.swing.JLabel staffNameLbl;
    private javax.swing.JTextField staffNameTxt;
    // End of variables declaration//GEN-END:variables
}
