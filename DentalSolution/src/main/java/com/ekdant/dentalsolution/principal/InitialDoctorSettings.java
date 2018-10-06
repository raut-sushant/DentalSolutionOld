/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ekdant.dentalsolution.principal;

import com.ekdant.dentalsolution.dao.DoctorDao;
import com.ekdant.dentalsolution.dao.UserDao;
import com.ekdant.dentalsolution.domain.DoctorBean;
import com.ekdant.dentalsolution.domain.UserBean;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JOptionPane;

/**
 *
 * @author Sushant Raut
 */
public class InitialDoctorSettings extends javax.swing.JFrame {
    
    DoctorDao doctorDao;
    UserDao userDao;
    int doctorId = 0;
    int userId = 0;

    /**
     * Creates new form initialSettings
     */
    public InitialDoctorSettings() {
        doctorDao = new DoctorDao();
        userDao = new UserDao();
        initComponents();
        populateDoctorProfile();
    }
    
    private void populateDoctorProfile(){
        DoctorBean mainDoctor = doctorDao.fetchMainDoctor();
        doctorId = mainDoctor.getDoctorId();
        if(doctorId > 0){
            UserBean doctorUser = userDao.fetchDoctor();
            String gender = mainDoctor.getGender();
            String abbr = gender.equals("Male") ? "Mr" : "Mrs";
            userId = doctorUser.getUserId();
            abbrCom.setSelectedItem(abbr);
            doctorNameTxt.setText(mainDoctor.getName());
            doctorMobileTxt.setText(mainDoctor.getTelephone());
            doctorEmailTxt.setText(mainDoctor.getEmail());
            profDegreeTxt.setText(mainDoctor.getDegree());
            medRegNoTxt.setText(mainDoctor.getRegistrationNo());
            practiceCB.setSelectedItem(mainDoctor.getSpecialization());
            if(userId > 0){
                loginIdTxt.setText(doctorUser.getName());
                loginIdTxt.setEditable(false);
            }
        }
    }
    
    private void validateAndSaveInformation(){
        if (validateForm()) {

            String gender = abbrCom.getSelectedItem().toString().equalsIgnoreCase("Mr") ? "Male" : "Female";
            DoctorBean doctor = new DoctorBean();
            doctor.setDoctorId(doctorId);
            doctor.setName(doctorNameTxt.getText());
            doctor.setTelephone(doctorMobileTxt.getText());
            doctor.setMobile(doctorMobileTxt.getText());
            doctor.setEmail(doctorEmailTxt.getText());
            doctor.setType("Main");
            doctor.setGender(gender);
            doctor.setDegree(profDegreeTxt.getText());
            doctor.setRegistrationNo(medRegNoTxt.getText());
            doctor.setSpecialization(practiceCB.getSelectedItem().toString());

            UserBean user = new UserBean();
            user.setName(doctorNameTxt.getText());
            user.setLoginId(loginIdTxt.getText());
            user.setPassword(passwordPwd.getText());
            user.setUserType("Doctor");            
            
            if(doctorId == 0){
                doctorDao.addDoctor(doctor);
            }else{
                doctorDao.updateDoctor(doctor);
            }
            
            if(userId == 0){
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
        if(doctorNameTxt.getText().isEmpty()){
            valid = false;
            JOptionPane.showMessageDialog(null,"Please Enter Doctor Name","ERROR", JOptionPane.ERROR_MESSAGE);
        }
        else if(doctorMobileTxt.getText().isEmpty() || doctorMobileTxt.getText().length() < 10){
            valid = false;
            JOptionPane.showMessageDialog(null,"Please Enter Doctor Mobile Number","ERROR", JOptionPane.ERROR_MESSAGE);
        }
        else if(doctorEmailTxt.getText().isEmpty()){
            valid = false;
            JOptionPane.showMessageDialog(null,"Please Enter Doctor Email ID","ERROR", JOptionPane.ERROR_MESSAGE);
        }
        else if(profDegreeTxt.getText().isEmpty()){
            valid = false;
            JOptionPane.showMessageDialog(null,"Please Enter Profetional Degree(s) of Doctor","ERROR", JOptionPane.ERROR_MESSAGE);
        }
        else if(medRegNoTxt.getText().isEmpty()){
            valid = false;
            JOptionPane.showMessageDialog(null,"Please Enter Medical Registration number of Doctor","ERROR", JOptionPane.ERROR_MESSAGE);
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
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        doctorDetailsPanel = new javax.swing.JPanel();
        doctorNameLbl = new javax.swing.JLabel();
        doctorNameTxt = new javax.swing.JTextField();
        doctorMobileLbl = new javax.swing.JLabel();
        doctorMobileTxt = new javax.swing.JTextField();
        doctorEmailLbl = new javax.swing.JLabel();
        doctorEmailTxt = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        practiceLbl = new javax.swing.JLabel();
        practiceCB = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        profDegreeLbl = new javax.swing.JLabel();
        profDegreeTxt = new javax.swing.JTextField();
        medRegNoLbl = new javax.swing.JLabel();
        medRegNoTxt = new javax.swing.JTextField();
        abbrCom = new javax.swing.JComboBox();
        loginPanel = new javax.swing.JPanel();
        loginIdTxt = new javax.swing.JTextField();
        loginIdLbl = new javax.swing.JLabel();
        passwordLbl = new javax.swing.JLabel();
        rePasswordLbl = new javax.swing.JLabel();
        passwordPwd = new javax.swing.JPasswordField();
        rePasswordPwd = new javax.swing.JPasswordField();
        buttonPanel = new javax.swing.JPanel();
        saveBtn = new javax.swing.JButton();
        cancelBtn = new javax.swing.JButton();
        msgLbl = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Doctor Details");

        doctorNameLbl.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        doctorNameLbl.setForeground(new java.awt.Color(51, 51, 255));
        doctorNameLbl.setText("Doctor Name:");

        doctorMobileLbl.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        doctorMobileLbl.setForeground(new java.awt.Color(51, 51, 255));
        doctorMobileLbl.setText("Doctor Mobile:");

        doctorEmailLbl.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        doctorEmailLbl.setForeground(new java.awt.Color(51, 51, 255));
        doctorEmailLbl.setText("Doctor Email:");

        jLabel1.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 255));
        jLabel1.setText("Dr.");

        practiceLbl.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        practiceLbl.setForeground(new java.awt.Color(51, 51, 255));
        practiceLbl.setText("Practice:");

        practiceCB.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Dentist", "General Practitioner" }));

        jLabel3.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 51, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Add Doctors Details");

        profDegreeLbl.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        profDegreeLbl.setForeground(new java.awt.Color(51, 51, 255));
        profDegreeLbl.setText("Professional Degree(s):");

        medRegNoLbl.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        medRegNoLbl.setForeground(new java.awt.Color(51, 51, 255));
        medRegNoLbl.setText("Medical Registration No:");

        abbrCom.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Mr", "Ms" }));

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
                                .addComponent(doctorNameLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(abbrCom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(doctorNameTxt))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, doctorDetailsPanelLayout.createSequentialGroup()
                                .addComponent(doctorEmailLbl)
                                .addGap(18, 18, 18)
                                .addComponent(doctorEmailTxt))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, doctorDetailsPanelLayout.createSequentialGroup()
                                .addComponent(doctorMobileLbl)
                                .addGap(18, 18, 18)
                                .addComponent(doctorMobileTxt))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, doctorDetailsPanelLayout.createSequentialGroup()
                                .addGroup(doctorDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, doctorDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(profDegreeLbl)
                                        .addComponent(practiceLbl))
                                    .addComponent(medRegNoLbl))
                                .addGap(20, 20, 20)
                                .addGroup(doctorDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(medRegNoTxt)
                                    .addComponent(practiceCB, javax.swing.GroupLayout.Alignment.LEADING, 0, 189, Short.MAX_VALUE)
                                    .addComponent(profDegreeTxt, javax.swing.GroupLayout.Alignment.LEADING))))
                        .addContainerGap(18, Short.MAX_VALUE))))
        );

        doctorDetailsPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {doctorEmailLbl, doctorMobileLbl, doctorNameLbl, medRegNoLbl, practiceLbl, profDegreeLbl});

        doctorDetailsPanelLayout.setVerticalGroup(
            doctorDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(doctorDetailsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addGroup(doctorDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(doctorNameLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(doctorNameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(abbrCom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(doctorDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(doctorMobileLbl)
                    .addComponent(doctorMobileTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(doctorDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(doctorEmailLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(doctorEmailTxt))
                .addGap(18, 18, 18)
                .addGroup(doctorDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(practiceLbl)
                    .addComponent(practiceCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(doctorDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(profDegreeLbl)
                    .addComponent(profDegreeTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(doctorDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(medRegNoLbl)
                    .addComponent(medRegNoTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(51, Short.MAX_VALUE))
        );

        doctorDetailsPanelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {abbrCom, doctorEmailLbl, doctorEmailTxt, doctorMobileLbl, doctorMobileTxt, doctorNameLbl, doctorNameTxt, jLabel1, jLabel3, medRegNoLbl, medRegNoTxt, practiceCB, practiceLbl, profDegreeLbl, profDegreeTxt});

        loginPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Login Details", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Verdana", 1, 12), new java.awt.Color(51, 51, 255))); // NOI18N

        loginIdLbl.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        loginIdLbl.setForeground(new java.awt.Color(51, 51, 255));
        loginIdLbl.setText("Login Id:");

        passwordLbl.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        passwordLbl.setForeground(new java.awt.Color(51, 51, 255));
        passwordLbl.setText("Password:");

        rePasswordLbl.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        rePasswordLbl.setForeground(new java.awt.Color(51, 51, 255));
        rePasswordLbl.setText("Re-Enter Password:");

        javax.swing.GroupLayout loginPanelLayout = new javax.swing.GroupLayout(loginPanel);
        loginPanel.setLayout(loginPanelLayout);
        loginPanelLayout.setHorizontalGroup(
            loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(loginPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(loginPanelLayout.createSequentialGroup()
                        .addComponent(loginIdLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(loginIdTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(loginPanelLayout.createSequentialGroup()
                        .addComponent(passwordLbl)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(passwordPwd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(loginPanelLayout.createSequentialGroup()
                        .addComponent(rePasswordLbl)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rePasswordPwd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(74, Short.MAX_VALUE))
        );

        loginPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {loginIdLbl, passwordLbl, rePasswordLbl});

        loginPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {loginIdTxt, passwordPwd, rePasswordPwd});

        loginPanelLayout.setVerticalGroup(
            loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(loginPanelLayout.createSequentialGroup()
                .addContainerGap()
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

        loginPanelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {loginIdLbl, loginIdTxt, passwordLbl, passwordPwd, rePasswordLbl, rePasswordPwd});

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
                .addComponent(msgLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)
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
    private javax.swing.JComboBox abbrCom;
    private javax.swing.JPanel buttonPanel;
    private javax.swing.JButton cancelBtn;
    private javax.swing.JPanel doctorDetailsPanel;
    private javax.swing.JLabel doctorEmailLbl;
    private javax.swing.JTextField doctorEmailTxt;
    private javax.swing.JLabel doctorMobileLbl;
    private javax.swing.JTextField doctorMobileTxt;
    private javax.swing.JLabel doctorNameLbl;
    private javax.swing.JTextField doctorNameTxt;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel loginIdLbl;
    private javax.swing.JTextField loginIdTxt;
    private javax.swing.JPanel loginPanel;
    private javax.swing.JLabel medRegNoLbl;
    private javax.swing.JTextField medRegNoTxt;
    private javax.swing.JLabel msgLbl;
    private javax.swing.JLabel passwordLbl;
    private javax.swing.JPasswordField passwordPwd;
    private javax.swing.JComboBox practiceCB;
    private javax.swing.JLabel practiceLbl;
    private javax.swing.JLabel profDegreeLbl;
    private javax.swing.JTextField profDegreeTxt;
    private javax.swing.JLabel rePasswordLbl;
    private javax.swing.JPasswordField rePasswordPwd;
    private javax.swing.JButton saveBtn;
    // End of variables declaration//GEN-END:variables
}
