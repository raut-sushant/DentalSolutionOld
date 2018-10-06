package com.ekdant.dentalsolution.register;
import com.ekdant.dentalsolution.dao.CityDao;
import com.ekdant.dentalsolution.dao.DoctorDao;
import com.ekdant.dentalsolution.domain.CityBean;
import com.ekdant.dentalsolution.domain.DoctorBean;
import com.ekdant.dentalsolution.masters.Cities;
import com.ekdant.dentalsolution.principal.Doctors;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
/**
 *
 * @author Sushant
 */
public class RegisterDoctor extends javax.swing.JFrame {
    Doctors doctor;
    DateFormat displayDateFormat = new SimpleDateFormat("d MMM yyyy");
    DateFormat databaseDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    DateFormat inputDateFormat = new SimpleDateFormat("MMM d, yyyy");
    List<JCheckBox> checkBoxList = new ArrayList<JCheckBox>();
    CityDao cityDao;
    DoctorDao doctorDao;
    private boolean newCityAdded;
    
    /** Creates new form JF_RegisterDoctor
     * @param doctorFrm */
    public RegisterDoctor(Doctors doctorFrm) {
        doctor = doctorFrm;
        initComponents();
        cityDao = new CityDao();
        doctorDao = new DoctorDao();
        cityDao.getCities(citiesCB);
        newCityAdded = false;
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainTabbedPane = new javax.swing.JTabbedPane();
        doctorDetailsPanel = new javax.swing.JPanel();
        nameLbl = new javax.swing.JLabel();
        addressTxt = new javax.swing.JTextField();
        birthdayLbl = new javax.swing.JLabel();
        genderLbl = new javax.swing.JLabel();
        genderCB = new javax.swing.JComboBox();
        addressLbl = new javax.swing.JLabel();
        nameTxt = new javax.swing.JTextField();
        cityLbl = new javax.swing.JLabel();
        telephoneLbl = new javax.swing.JLabel();
        lblEmail = new javax.swing.JLabel();
        emailTxt = new javax.swing.JTextField();
        telTxt = new javax.swing.JTextField();
        doctorIdTxt = new javax.swing.JTextField();
        citiesCB = new javax.swing.JComboBox();
        ageLbl = new javax.swing.JLabel();
        ageTxt = new javax.swing.JTextField();
        newCityBtn = new javax.swing.JButton();
        regNoLbl = new javax.swing.JLabel();
        regNoTxt = new javax.swing.JTextField();
        degreeLbl = new javax.swing.JLabel();
        degreeTxt = new javax.swing.JTextField();
        specializationLbl = new javax.swing.JLabel();
        specializationTxt = new javax.swing.JTextField();
        birthdayDC = new com.toedter.calendar.JDateChooser();
        saveBtn = new javax.swing.JButton();
        cancelBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Doctors");
        setExtendedState(2);
        setName("Paciente"); // NOI18N
        setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width,Toolkit.getDefaultToolkit().getScreenSize().height-Toolkit.getDefaultToolkit().getScreenInsets(getGraphicsConfiguration()).bottom));
        setResizable(false);
        addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                formWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
            }
        });

        mainTabbedPane.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        mainTabbedPane.setPreferredSize(getMaximumSize());

        doctorDetailsPanel.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        doctorDetailsPanel.setPreferredSize(new java.awt.Dimension(900, 650));

        nameLbl.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        nameLbl.setForeground(new java.awt.Color(51, 51, 255));
        nameLbl.setText("* Name:");

        addressTxt.setNextFocusableComponent(citiesCB);

        birthdayLbl.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        birthdayLbl.setForeground(new java.awt.Color(51, 51, 255));
        birthdayLbl.setText("   Date of Birth:");

        genderLbl.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        genderLbl.setForeground(new java.awt.Color(51, 51, 255));
        genderLbl.setText("   Gender:");

        genderCB.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Male", "Female" }));
        genderCB.setNextFocusableComponent(addressTxt);

        addressLbl.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        addressLbl.setForeground(new java.awt.Color(51, 51, 255));
        addressLbl.setText("   Address:");

        nameTxt.setNextFocusableComponent(ageTxt);

        cityLbl.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        cityLbl.setForeground(new java.awt.Color(51, 51, 255));
        cityLbl.setText("   City :");

        telephoneLbl.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        telephoneLbl.setForeground(new java.awt.Color(51, 51, 255));
        telephoneLbl.setText("   Mobile:");

        lblEmail.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        lblEmail.setForeground(new java.awt.Color(51, 51, 255));
        lblEmail.setText("   Email:");

        emailTxt.setNextFocusableComponent(saveBtn);

        telTxt.setNextFocusableComponent(emailTxt);

        doctorIdTxt.setEditable(false);
        doctorIdTxt.setMaximumSize(new java.awt.Dimension(0, 0));
        doctorIdTxt.setMinimumSize(new java.awt.Dimension(0, 0));
        doctorIdTxt.setName(""); // NOI18N
        doctorIdTxt.setPreferredSize(new java.awt.Dimension(0, 0));

        citiesCB.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "select" }));
        citiesCB.setNextFocusableComponent(telTxt);

        ageLbl.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        ageLbl.setForeground(new java.awt.Color(51, 51, 255));
        ageLbl.setText("* Age: ");

        ageTxt.setNextFocusableComponent(regNoTxt);
        ageTxt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                ageTxtFocusLost(evt);
            }
        });

        newCityBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/EkDant/icones/plus-icon.png"))); // NOI18N
        newCityBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newCityBtnActionPerformed(evt);
            }
        });

        regNoLbl.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        regNoLbl.setForeground(new java.awt.Color(51, 51, 255));
        regNoLbl.setText("* Reg No:");

        regNoTxt.setNextFocusableComponent(degreeTxt);

        degreeLbl.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        degreeLbl.setForeground(new java.awt.Color(51, 51, 255));
        degreeLbl.setText("   Degree:");

        degreeTxt.setNextFocusableComponent(specializationTxt);

        specializationLbl.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        specializationLbl.setForeground(new java.awt.Color(51, 51, 255));
        specializationLbl.setText("   Specialization:");

        specializationTxt.setNextFocusableComponent(genderCB);

        javax.swing.GroupLayout doctorDetailsPanelLayout = new javax.swing.GroupLayout(doctorDetailsPanel);
        doctorDetailsPanel.setLayout(doctorDetailsPanelLayout);
        doctorDetailsPanelLayout.setHorizontalGroup(
            doctorDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(doctorDetailsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(doctorDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(addressLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(doctorDetailsPanelLayout.createSequentialGroup()
                        .addGroup(doctorDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cityLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(telephoneLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblEmail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(32, 32, 32)
                        .addGroup(doctorDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(telTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(emailTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(doctorDetailsPanelLayout.createSequentialGroup()
                        .addGroup(doctorDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(doctorDetailsPanelLayout.createSequentialGroup()
                                .addGroup(doctorDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(nameLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(ageLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(birthdayLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(32, 32, 32)
                                .addGroup(doctorDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(doctorDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(doctorDetailsPanelLayout.createSequentialGroup()
                                            .addGap(146, 146, 146)
                                            .addComponent(doctorIdTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(ageTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(nameTxt, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                                        .addComponent(addressTxt)
                                        .addGroup(doctorDetailsPanelLayout.createSequentialGroup()
                                            .addComponent(citiesCB, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(newCityBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)))
                                    .addComponent(birthdayDC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(doctorDetailsPanelLayout.createSequentialGroup()
                                .addGap(142, 142, 142)
                                .addComponent(genderCB, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(genderLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(50, 50, 50)
                        .addGroup(doctorDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(doctorDetailsPanelLayout.createSequentialGroup()
                                .addComponent(regNoLbl)
                                .addGap(32, 32, 32)
                                .addComponent(regNoTxt))
                            .addGroup(doctorDetailsPanelLayout.createSequentialGroup()
                                .addComponent(degreeLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(32, 32, 32)
                                .addComponent(degreeTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(doctorDetailsPanelLayout.createSequentialGroup()
                                .addComponent(specializationLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(32, 32, 32)
                                .addComponent(specializationTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(120, Short.MAX_VALUE))
        );

        doctorDetailsPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {addressLbl, ageLbl, birthdayLbl, cityLbl, degreeLbl, genderLbl, lblEmail, nameLbl, regNoLbl, specializationLbl, telephoneLbl});

        doctorDetailsPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {addressTxt, degreeTxt, emailTxt, nameTxt, regNoTxt, specializationTxt, telTxt});

        doctorDetailsPanelLayout.setVerticalGroup(
            doctorDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, doctorDetailsPanelLayout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(doctorIdTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(doctorDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(doctorDetailsPanelLayout.createSequentialGroup()
                        .addGroup(doctorDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(doctorDetailsPanelLayout.createSequentialGroup()
                                .addGroup(doctorDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(nameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(nameLbl))
                                .addGap(18, 18, 18)
                                .addGroup(doctorDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(ageTxt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(ageLbl))
                                .addGap(18, 18, 18)
                                .addGroup(doctorDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(genderLbl)
                                    .addComponent(genderCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(doctorDetailsPanelLayout.createSequentialGroup()
                                .addGroup(doctorDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(regNoLbl)
                                    .addComponent(regNoTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(doctorDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(degreeLbl)
                                    .addComponent(degreeTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(doctorDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(specializationLbl)
                                    .addComponent(specializationTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(18, 18, 18)
                        .addComponent(birthdayLbl))
                    .addComponent(birthdayDC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(doctorDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addressLbl)
                    .addComponent(addressTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(doctorDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(doctorDetailsPanelLayout.createSequentialGroup()
                        .addGroup(doctorDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(citiesCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(newCityBtn))
                        .addGap(0, 38, Short.MAX_VALUE))
                    .addComponent(cityLbl, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(doctorDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(telephoneLbl)
                    .addComponent(telTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(doctorDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEmail)
                    .addComponent(emailTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(143, 143, 143))
        );

        doctorDetailsPanelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {addressLbl, addressTxt, ageLbl, ageTxt, birthdayLbl, citiesCB, cityLbl, degreeLbl, degreeTxt, emailTxt, genderCB, genderLbl, lblEmail, nameLbl, nameTxt, newCityBtn, regNoLbl, regNoTxt, specializationLbl, specializationTxt, telTxt, telephoneLbl});

        mainTabbedPane.addTab("Doctors Detail", new javax.swing.ImageIcon(getClass().getResource("/EkDant/icones/Patient Boy 1.png")), doctorDetailsPanel); // NOI18N

        saveBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/EkDant/icones/Patient Boy 1 Check.png"))); // NOI18N
        saveBtn.setMnemonic('S');
        saveBtn.setText("Save");
        saveBtn.setNextFocusableComponent(cancelBtn);
        saveBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        cancelBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/EkDant/icones/Patient Boy 1 Stop.png"))); // NOI18N
        cancelBtn.setMnemonic('C');
        cancelBtn.setText("Cancel");
        cancelBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(saveBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(cancelBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(597, Short.MAX_VALUE))
                    .addComponent(mainTabbedPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(mainTabbedPane, javax.swing.GroupLayout.PREFERRED_SIZE, 558, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(saveBtn)
                    .addComponent(cancelBtn))
                .addGap(29, 29, 29))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed

            String Name     = nameTxt.getText();
            String age      = ageTxt.getText();
           
            if(Name.length() < 3){
                JOptionPane.showMessageDialog(null,"Please enter name!","Attention", JOptionPane.WARNING_MESSAGE);
            }
            else if(age.length() < 1){
                JOptionPane.showMessageDialog(null,"Please enter age!","Attention", JOptionPane.WARNING_MESSAGE);
            }
            else{
                String city     = citiesCB.getSelectedItem().toString().equalsIgnoreCase("select") ? "" : citiesCB.getSelectedItem().toString();
                
                DoctorBean doctorBean = new DoctorBean();
                doctorBean.setName(Name);
                doctorBean.setBirthDate(birthdayDC.getDate());
                doctorBean.setGender(genderCB.getSelectedItem().toString());
                doctorBean.setAge(Integer.parseInt(age));
                doctorBean.setTelephone(telTxt.getText());
                doctorBean.setMobile("");
                doctorBean.setCity(city);
                doctorBean.setAddress(addressTxt.getText());
                doctorBean.setEmail(emailTxt.getText());
                doctorBean.setType("Coulsuntant");
                
                doctorIdTxt.setText("" + doctorDao.addDoctor(doctorBean));
                
                insertNewCity(city);
                this.dispose();
                doctor.dispose();
                new Doctors().setVisible(true);
            }
        
    }//GEN-LAST:event_btnSaveActionPerformed

    private void insertNewCity(String city){
        if(cityDao.cityNotPresent(city)){
            CityBean newCity = new CityBean();
            newCity.setName(city);
            cityDao.addCity(newCity);
        }
    }
    
    private void populateNewCity(){
        cityDao.getCities(citiesCB);
        if(newCityAdded){
            String newlyAddedCity = cityDao.fetchLatestCity();
            citiesCB.setSelectedItem(newlyAddedCity);
        }
    }
    
    private void validateAge() throws HeadlessException {
        String ageStr = ageTxt.getText();
        if (!ageStr.isEmpty()) {
            int age = 0;
            try {
                age = Integer.parseInt(ageTxt.getText());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Please enter valid age!", "Attention", JOptionPane.WARNING_MESSAGE);
                ageTxt.requestFocus();
            }
        }
        else{
            JOptionPane.showMessageDialog(null, "Please enter age!", "Attention", JOptionPane.WARNING_MESSAGE);
            ageTxt.requestFocus();
        }
    }
    
    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCancelActionPerformed

    private void newCityBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newCityBtnActionPerformed
        newCityAdded = true;
        new Cities(true).setVisible(true);
    }//GEN-LAST:event_newCityBtnActionPerformed

    private void formWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowGainedFocus
        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowGainedFocus

    private void ageTxtFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ageTxtFocusLost
        validateAge();
    }//GEN-LAST:event_ageTxtFocusLost


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel addressLbl;
    private javax.swing.JTextField addressTxt;
    private javax.swing.JLabel ageLbl;
    private javax.swing.JTextField ageTxt;
    private com.toedter.calendar.JDateChooser birthdayDC;
    private javax.swing.JLabel birthdayLbl;
    private javax.swing.JButton cancelBtn;
    private javax.swing.JComboBox citiesCB;
    private javax.swing.JLabel cityLbl;
    private javax.swing.JLabel degreeLbl;
    private javax.swing.JTextField degreeTxt;
    private javax.swing.JPanel doctorDetailsPanel;
    private javax.swing.JTextField doctorIdTxt;
    private javax.swing.JTextField emailTxt;
    private javax.swing.JComboBox genderCB;
    private javax.swing.JLabel genderLbl;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JTabbedPane mainTabbedPane;
    private javax.swing.JLabel nameLbl;
    private javax.swing.JTextField nameTxt;
    private javax.swing.JButton newCityBtn;
    private javax.swing.JLabel regNoLbl;
    private javax.swing.JTextField regNoTxt;
    private javax.swing.JButton saveBtn;
    private javax.swing.JLabel specializationLbl;
    private javax.swing.JTextField specializationTxt;
    private javax.swing.JTextField telTxt;
    private javax.swing.JLabel telephoneLbl;
    // End of variables declaration//GEN-END:variables
}