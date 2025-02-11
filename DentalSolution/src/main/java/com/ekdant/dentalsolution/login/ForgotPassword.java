/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ekdant.dentalsolution.login;

import com.ekdant.dentalsolution.dao.DoctorDao;
import com.ekdant.dentalsolution.dao.UserDao;
import com.ekdant.dentalsolution.domain.DoctorBean;
import com.ekdant.dentalsolution.domain.UserBean;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JOptionPane;

/**
 *
 * @author Sushant Raut
 */
public class ForgotPassword extends javax.swing.JFrame {

    DoctorDao doctorDao;
    UserDao userDao;
    /**
     * Creates new form ForgotPassword
     */
    public ForgotPassword() {
        doctorDao = new DoctorDao();
        userDao = new UserDao();
        initComponents();
        populateFields();
    }

    private void populateFields(){
        DoctorBean mainDoctor = doctorDao.fetchMainDoctor();
        headerLbl.setText("Please click below button to get your password on your registered mail id :" + mainDoctor.getEmail());        
    }
    
    private void sendPassword(){
        UserBean doctor = userDao.fetchDoctor();
        DoctorBean mainDoctor = doctorDao.fetchMainDoctor();
        String loginId = doctor.getLoginId();
        String password = doctor.getPassword();
        String emailTo = mainDoctor.getEmail();
        String emailSubject = "[EkDant-Support] Password retrival information";
        String emailBody = "<html><body>"
                + "Dear Doctor, <br><br>"
                + "Below are your password information. <br><br>"
                + "Login Id : " + loginId + "<br>"
                + "Password : " + password + "<br><br>"
                + "if you have any questions, feel free to get back to us. <br><br>"
                + "Our contact details are: <br><br>"
                + "EMail : ekdantsoftware@gmail.com<br>"
                + "Phone : 8237051105<br><br>"
                + "Thank you for giving us an opportunity to serve you. <br><br><br>"
                + "Thank you,<br>"
                + "EkDant Team <br><br>"
                + "This is an auto generated email. Do not reply to this email. "
                + "</body></html>";
        successMsgLbl.setText("Password Send to registered email id successfully");
        JOptionPane.showConfirmDialog(null,emailBody, "Retrive Password", JOptionPane.CLOSED_OPTION, JOptionPane.QUESTION_MESSAGE, new javax.swing.ImageIcon(getClass().getResource("/EkDant/icones/Calendar Confirmed.png")));
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        headerLbl = new javax.swing.JLabel();
        submitBtn = new javax.swing.JButton();
        successMsgLbl = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Forgot Password");
        setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width,Toolkit.getDefaultToolkit().getScreenSize().height-Toolkit.getDefaultToolkit().getScreenInsets(getGraphicsConfiguration()).bottom));

        headerLbl.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        headerLbl.setForeground(new java.awt.Color(51, 51, 255));

        submitBtn.setText("Retrive Password");
        submitBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submitBtnActionPerformed(evt);
            }
        });

        successMsgLbl.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        successMsgLbl.setForeground(new java.awt.Color(51, 51, 255));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(headerLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(successMsgLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(submitBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 343, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(headerLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(successMsgLbl)
                .addGap(28, 28, 28)
                .addComponent(submitBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(148, Short.MAX_VALUE))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {headerLbl, successMsgLbl});

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void submitBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submitBtnActionPerformed
        sendPassword();
    }//GEN-LAST:event_submitBtnActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel headerLbl;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton submitBtn;
    private javax.swing.JLabel successMsgLbl;
    // End of variables declaration//GEN-END:variables
}
