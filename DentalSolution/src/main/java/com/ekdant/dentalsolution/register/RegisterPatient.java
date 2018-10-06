package com.ekdant.dentalsolution.register;
import com.ekdant.dentalsolution.dao.CityDao;
import com.ekdant.dentalsolution.dao.OdontogramDao;
import com.ekdant.dentalsolution.dao.PatientsDao;
import com.ekdant.dentalsolution.dao.PreMedicalHistoryDao;
import com.ekdant.dentalsolution.domain.OdontogramBean;
import com.ekdant.dentalsolution.domain.PatientBean;
import com.ekdant.dentalsolution.domain.PreMedicalHistoryBean;
import com.ekdant.dentalsolution.masters.Cities;
import com.ekdant.dentalsolution.principal.Patients;
import com.ekdant.dentalsolution.treatments.PatientTreatment;
import com.ekdant.dentalsolution.utilities.*;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.border.TitledBorder;
/**
 *
 * @author Sushant
 */
public class RegisterPatient extends javax.swing.JFrame {
    Patients patient;
    DateFormat displayDateFormat = new SimpleDateFormat("d MMM yyyy");
    DateFormat databaseDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    DateFormat inputDateFormat = new SimpleDateFormat("MMM d, yyyy");
    List<JCheckBox> checkBoxList = new ArrayList<JCheckBox>();
    PatientsDao patientsDao;
    PreMedicalHistoryDao preMedicalHistoryDao;
    CityDao cityDao;
    OdontogramDao odontogramDao;
    boolean newCityAdded;
    
    /** Creates new form JF_RegisterPatient
     * @param pacientFrm */
    public RegisterPatient(Patients pacientFrm) {
        patient = pacientFrm;
        patientsDao = new PatientsDao();
        preMedicalHistoryDao = new PreMedicalHistoryDao();
        cityDao = new CityDao();
        odontogramDao = new OdontogramDao();
        initComponents();
        setPreMedicalHistory();
        cityDao.getCities(citiesCB); 
        verifyLicense();
        newCityAdded = false;
    }
    
    private void verifyLicense(){
        String message = "";
        if(activationRequired()){
            message = "Please Activate License for using this software. Please refer Help";
            saveBtn.setVisible(false);
        }
        licenseMessageLbl.setText(message);
    }
    
    private boolean activationRequired(){
        return demoExpired() && Utils.demoVersion();
    }
    
    private boolean demoExpired(){
        return patientsDao.getTotalPatientCount() > 25;
    }
        
    private void setPreMedicalHistory(){
        
        List<String> preMedicalHistoryList = new ArrayList<String>();
        List<PreMedicalHistoryBean> preMedicalHistories = preMedicalHistoryDao.fetchPreMedicalHistory();
        for(PreMedicalHistoryBean preMedicalHistory : preMedicalHistories){
            preMedicalHistoryList.add(preMedicalHistory.getDiscription());
        }
        JPanel panel = new JPanel();
        panel.setSize(300,(preMedicalHistoryList.size()/2)*100);
        panel.setBorder(new TitledBorder("Pre Medical History"));
        GroupLayout layout = new GroupLayout(panel);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        GroupLayout.ParallelGroup parllelLayout = layout.createParallelGroup(GroupLayout.Alignment.LEADING);
        GroupLayout.SequentialGroup seqGroup = layout.createSequentialGroup();
        for(String discription: preMedicalHistoryList){
            JCheckBox checkBox = new JCheckBox(discription,false);
            checkBox.setFocusable(false);
            checkBoxList.add(checkBox);
            parllelLayout.addComponent(checkBox);
            seqGroup.addComponent(checkBox);
          }

        layout.setHorizontalGroup(layout.createSequentialGroup()
           .addGroup(layout.createSequentialGroup()
              .addGroup(parllelLayout))    
        );
        
        layout.setVerticalGroup(seqGroup);
        panel.setLayout(layout);        
        historyPanel.add(panel);
    }
    
     private void savePatient() throws HeadlessException {
        if (nameTxt.getText().length() < 3) {
            JOptionPane.showMessageDialog(null, "Please enter your name!", "Attention", JOptionPane.WARNING_MESSAGE);
        } else if (casePaperNumberTxt.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Please enter your Case Paper Number!", "Attention", JOptionPane.WARNING_MESSAGE);
        } else if (ageTxt.getText().length() < 1) {
            JOptionPane.showMessageDialog(null, "Please enter age!", "Attention", JOptionPane.WARNING_MESSAGE);
        }
        
        PatientBean patient = populatePatient();
        int patientId = patientsDao.addPatient(patient);
        patientIdTxt.setText(""+patientId);
        insertOdontogram(populateOdontogram());
        this.dispose();
        new PatientTreatment(patientId).setVisible(true);
        
    }

    private PatientBean populatePatient(){
       int patientId = 0;
       if (!patientIdTxt.getText().isEmpty()) {
           patientId = Integer.parseInt(patientIdTxt.getText());
       }
       PatientBean patient = new PatientBean();
       patient.setPatientId(patientId);
       patient.setName(nameTxt.getText());
       patient.setCaseId(casePaperNumberTxt.getText());
       patient.setBirthDate(birthdayDC.getDate());
       patient.setGender(genderCB.getSelectedItem().toString());
       patient.setAddress(addressTxt.getText());
       String city = citiesCB.getSelectedItem().toString().equalsIgnoreCase("select") ? "" : citiesCB.getSelectedItem().toString();
       patient.setCity(city);
       patient.setTelephone(telTxt.getText());
       patient.setMobile("");
       patient.setEmail(emailTxt.getText());
       patient.setAge(Integer.parseInt(ageTxt.getText()));
       String preMedicalHistory = "";
       for (JCheckBox chkBox : checkBoxList) {
           if (chkBox.isSelected()) {
               if (preMedicalHistory.isEmpty()) {
                   preMedicalHistory = chkBox.getText();
               } else {
                   preMedicalHistory += "," + chkBox.getText();
               }
           }
       }
       patient.setPreMedicalHistory(preMedicalHistory);
       return patient;
   }
    
   private OdontogramBean populateOdontogram(){
        OdontogramBean odontogram = new OdontogramBean();
        odontogram.setName(nameTxt.getText());
        odontogram.setPatientId(Integer.parseInt(patientIdTxt.getText()));
        odontogram.setOdon18(txt18.getText());
        odontogram.setOdon17(txt17.getText());
        odontogram.setOdon16(txt16.getText());
        odontogram.setOdon1555(txt15_55.getText());
        odontogram.setOdon1454(txt14_54.getText());
        odontogram.setOdon1353(txt13_53.getText());
        odontogram.setOdon1252(txt12_52.getText());
        odontogram.setOdon1151(txt11_51.getText());
        odontogram.setOdon4181(txt41_81.getText());
        odontogram.setOdon4282(txt42_82.getText());
        odontogram.setOdon4383(txt43_83.getText());
        odontogram.setOdon4484(txt44_84.getText());
        odontogram.setOdon4585(txt45_85.getText());
        odontogram.setOdon46(txt46.getText());
        odontogram.setOdon47(txt47.getText());
        odontogram.setOdon48(txt48.getText());
        odontogram.setOdon28(txt28.getText());
        odontogram.setOdon27(txt27.getText());
        odontogram.setOdon26(txt26.getText());
        odontogram.setOdon6525(txt65_25.getText());
        odontogram.setOdon6424(txt64_24.getText());
        odontogram.setOdon6323(txt63_23.getText());
        odontogram.setOdon6222(txt62_22.getText());
        odontogram.setOdon6121(txt61_21.getText());
        odontogram.setOdon7131(txt71_31.getText());
        odontogram.setOdon7232(txt72_32.getText());
        odontogram.setOdon7333(txt73_33.getText());
        odontogram.setOdon7434(txt74_34.getText());
        odontogram.setOdon7535(txt75_35.getText());
        odontogram.setOdon36(txt36.getText());
        odontogram.setOdon37(txt37.getText());
        odontogram.setOdon38(txt38.getText());
        return odontogram;
    }
   
    private void insertOdontogram(OdontogramBean odontogram) throws HeadlessException {
        if (!odontogramDao.insertOdontogram(odontogram)) {
            JOptionPane.showMessageDialog(null, "Could not save data odontogram!", "Error!", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void validateCaseId() throws HeadlessException {
        String caseId = casePaperNumberTxt.getText();
        if (!caseId.isEmpty()) {
            PatientBean patient = patientsDao.fetchPatientByCaseId(Integer.parseInt(caseId));
            if (patient.getPatientId() != 0) {
                JOptionPane.showMessageDialog(null, "There is already a registered patient with this Case Paper Number!", "Attention", JOptionPane.WARNING_MESSAGE);
                this.casePaperNumberTxt.requestFocus();
            }
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
    
    private void loadCities(boolean prePopulateWithLatestCity) {
        cityDao.getCities(citiesCB);
        if(newCityAdded && prePopulateWithLatestCity){
            String newlyAddedCity = cityDao.fetchLatestCity();
            citiesCB.setSelectedItem(newlyAddedCity);
        }
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
        patientDetailsPanel = new javax.swing.JPanel();
        patientIdTxt = new javax.swing.JTextField();
        historyPanel = new javax.swing.JPanel();
        detailsPanel = new javax.swing.JPanel();
        genderCB = new javax.swing.JComboBox();
        addressTxt = new javax.swing.JTextField();
        casePaperNumberTxt = new javax.swing.JTextField();
        ageTxt = new javax.swing.JTextField();
        addCityBtn = new javax.swing.JButton();
        emailTxt = new javax.swing.JTextField();
        birthdayLbl = new javax.swing.JLabel();
        casePaperNumberLbl = new javax.swing.JLabel();
        nameTxt = new javax.swing.JTextField();
        addressLbl = new javax.swing.JLabel();
        cityLbl = new javax.swing.JLabel();
        telephoneLbl = new javax.swing.JLabel();
        telTxt = new javax.swing.JTextField();
        ageLbl = new javax.swing.JLabel();
        citiesCB = new javax.swing.JComboBox();
        lblEmail = new javax.swing.JLabel();
        nameLbl = new javax.swing.JLabel();
        genderLbl = new javax.swing.JLabel();
        birthdayDC = new com.toedter.calendar.JDateChooser();
        licenseMessageLbl = new javax.swing.JLabel();
        odontogramPanel = new javax.swing.JPanel();
        odontoLbl = new javax.swing.JLabel();
        txt18 = new javax.swing.JTextField();
        txt17 = new javax.swing.JTextField();
        txt16 = new javax.swing.JTextField();
        txt15_55 = new javax.swing.JTextField();
        txt14_54 = new javax.swing.JTextField();
        txt13_53 = new javax.swing.JTextField();
        txt12_52 = new javax.swing.JTextField();
        txt11_51 = new javax.swing.JTextField();
        txt41_81 = new javax.swing.JTextField();
        txt42_82 = new javax.swing.JTextField();
        txt43_83 = new javax.swing.JTextField();
        txt44_84 = new javax.swing.JTextField();
        txt45_85 = new javax.swing.JTextField();
        txt46 = new javax.swing.JTextField();
        txt47 = new javax.swing.JTextField();
        txt48 = new javax.swing.JTextField();
        txt28 = new javax.swing.JTextField();
        txt27 = new javax.swing.JTextField();
        txt26 = new javax.swing.JTextField();
        txt65_25 = new javax.swing.JTextField();
        txt64_24 = new javax.swing.JTextField();
        txt63_23 = new javax.swing.JTextField();
        txt62_22 = new javax.swing.JTextField();
        txt61_21 = new javax.swing.JTextField();
        txt71_31 = new javax.swing.JTextField();
        txt72_32 = new javax.swing.JTextField();
        txt73_33 = new javax.swing.JTextField();
        txt74_34 = new javax.swing.JTextField();
        txt75_35 = new javax.swing.JTextField();
        txt36 = new javax.swing.JTextField();
        txt37 = new javax.swing.JTextField();
        txt38 = new javax.swing.JTextField();
        saveBtn = new javax.swing.JButton();
        cancelBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Patients");
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

        patientDetailsPanel.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        patientDetailsPanel.setPreferredSize(new java.awt.Dimension(900, 650));

        patientIdTxt.setEditable(false);
        patientIdTxt.setMaximumSize(new java.awt.Dimension(0, 0));
        patientIdTxt.setMinimumSize(new java.awt.Dimension(0, 0));
        patientIdTxt.setName(""); // NOI18N
        patientIdTxt.setPreferredSize(new java.awt.Dimension(0, 0));

        javax.swing.GroupLayout historyPanelLayout = new javax.swing.GroupLayout(historyPanel);
        historyPanel.setLayout(historyPanelLayout);
        historyPanelLayout.setHorizontalGroup(
            historyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 301, Short.MAX_VALUE)
        );
        historyPanelLayout.setVerticalGroup(
            historyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        genderCB.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Male", "Female" }));
        genderCB.setNextFocusableComponent(citiesCB);

        addressTxt.setNextFocusableComponent(genderCB);

        casePaperNumberTxt.setNextFocusableComponent(addressTxt);
        casePaperNumberTxt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                casePaperNumberTxtFocusLost(evt);
            }
        });

        ageTxt.setNextFocusableComponent(casePaperNumberTxt);
        ageTxt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                ageTxtFocusLost(evt);
            }
        });

        addCityBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/EkDant/icones/plus-icon.png"))); // NOI18N
        addCityBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addCityBtnActionPerformed(evt);
            }
        });

        emailTxt.setNextFocusableComponent(saveBtn);

        birthdayLbl.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        birthdayLbl.setForeground(new java.awt.Color(51, 51, 255));
        birthdayLbl.setText("   Date of Birth:");

        casePaperNumberLbl.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        casePaperNumberLbl.setForeground(new java.awt.Color(51, 51, 255));
        casePaperNumberLbl.setText("* Case Paper Number:");

        nameTxt.setNextFocusableComponent(ageTxt);

        addressLbl.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        addressLbl.setForeground(new java.awt.Color(51, 51, 255));
        addressLbl.setText("   Address:");

        cityLbl.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        cityLbl.setForeground(new java.awt.Color(51, 51, 255));
        cityLbl.setText("   City :");

        telephoneLbl.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        telephoneLbl.setForeground(new java.awt.Color(51, 51, 255));
        telephoneLbl.setText("   Mobile:");

        telTxt.setNextFocusableComponent(emailTxt);

        ageLbl.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        ageLbl.setForeground(new java.awt.Color(51, 51, 255));
        ageLbl.setText("* Age: ");

        citiesCB.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "select" }));
        citiesCB.setNextFocusableComponent(telTxt);

        lblEmail.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        lblEmail.setForeground(new java.awt.Color(51, 51, 255));
        lblEmail.setText("   Email:");

        nameLbl.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        nameLbl.setForeground(new java.awt.Color(51, 51, 255));
        nameLbl.setText("* Name:");

        genderLbl.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        genderLbl.setForeground(new java.awt.Color(51, 51, 255));
        genderLbl.setText("* Gender:");

        javax.swing.GroupLayout detailsPanelLayout = new javax.swing.GroupLayout(detailsPanel);
        detailsPanel.setLayout(detailsPanelLayout);
        detailsPanelLayout.setHorizontalGroup(
            detailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(detailsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(detailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(telephoneLbl, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cityLbl, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(casePaperNumberLbl, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(birthdayLbl, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ageLbl, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(nameLbl, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(addressLbl, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(genderLbl, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(detailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(citiesCB, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(detailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(emailTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(genderCB, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(telTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(detailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(addressTxt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(casePaperNumberTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(nameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(ageTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(addCityBtn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(birthdayDC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        detailsPanelLayout.setVerticalGroup(
            detailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(detailsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(detailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(nameLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(nameTxt))
                .addGap(18, 18, 18)
                .addGroup(detailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ageTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ageLbl))
                .addGap(18, 18, 18)
                .addGroup(detailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(birthdayLbl)
                    .addComponent(birthdayDC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(detailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(casePaperNumberTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(casePaperNumberLbl))
                .addGap(18, 18, 18)
                .addGroup(detailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addressTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addressLbl))
                .addGap(18, 18, 18)
                .addGroup(detailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(genderLbl)
                    .addComponent(genderCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(detailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cityLbl)
                    .addComponent(citiesCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addCityBtn))
                .addGap(18, 18, 18)
                .addGroup(detailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(telTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(telephoneLbl))
                .addGap(18, 18, 18)
                .addGroup(detailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEmail)
                    .addComponent(emailTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        detailsPanelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {addCityBtn, addressLbl, addressTxt, ageLbl, ageTxt, birthdayLbl, casePaperNumberLbl, casePaperNumberTxt, citiesCB, cityLbl, emailTxt, genderCB, genderLbl, lblEmail, nameLbl, nameTxt, telTxt, telephoneLbl});

        licenseMessageLbl.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        licenseMessageLbl.setForeground(new java.awt.Color(255, 51, 51));

        javax.swing.GroupLayout patientDetailsPanelLayout = new javax.swing.GroupLayout(patientDetailsPanel);
        patientDetailsPanel.setLayout(patientDetailsPanelLayout);
        patientDetailsPanelLayout.setHorizontalGroup(
            patientDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(patientDetailsPanelLayout.createSequentialGroup()
                .addGroup(patientDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(patientDetailsPanelLayout.createSequentialGroup()
                        .addGap(299, 299, 299)
                        .addComponent(patientIdTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(patientDetailsPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(detailsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(historyPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(patientDetailsPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(licenseMessageLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(143, Short.MAX_VALUE))
        );
        patientDetailsPanelLayout.setVerticalGroup(
            patientDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, patientDetailsPanelLayout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(patientIdTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(patientDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(historyPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(detailsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(licenseMessageLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(69, Short.MAX_VALUE))
        );

        mainTabbedPane.addTab("Patient Details", new javax.swing.ImageIcon(getClass().getResource("/EkDant/icones/Patient Boy 1.png")), patientDetailsPanel); // NOI18N

        odontoLbl.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        odontoLbl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/EkDant/images/mrtooth-odontograma_maior.png"))); // NOI18N
        odontoLbl.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);

        txt18.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N

        txt17.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N

        txt16.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N

        txt15_55.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N

        txt14_54.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N

        txt13_53.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N

        txt12_52.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N

        txt11_51.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N

        txt41_81.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N

        txt42_82.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N

        txt43_83.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N

        txt44_84.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N

        txt45_85.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N

        txt46.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N

        txt47.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N

        txt48.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N

        txt28.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N

        txt27.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N

        txt26.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N

        txt65_25.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N

        txt64_24.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N

        txt63_23.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N

        txt62_22.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N

        txt61_21.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N

        txt71_31.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N

        txt72_32.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N

        txt73_33.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N

        txt74_34.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N

        txt75_35.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N

        txt36.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N

        txt37.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N

        txt38.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N

        javax.swing.GroupLayout odontogramPanelLayout = new javax.swing.GroupLayout(odontogramPanel);
        odontogramPanel.setLayout(odontogramPanelLayout);
        odontogramPanelLayout.setHorizontalGroup(
            odontogramPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(odontogramPanelLayout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addGroup(odontogramPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txt18, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt17, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt16, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt15_55, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt14_54, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt13_53, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt12_52, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt11_51, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt41_81, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt42_82, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt43_83, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt44_84, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt45_85, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt46, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt47, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt48, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(odontoLbl)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(odontogramPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt38, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt37, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt36, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt75_35, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt74_34, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt73_33, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt72_32, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt71_31, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt61_21, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt62_22, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt63_23, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt64_24, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt65_25, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt26, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt27, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt28, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(146, Short.MAX_VALUE))
        );
        odontogramPanelLayout.setVerticalGroup(
            odontogramPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(odontogramPanelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(odontogramPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(odontogramPanelLayout.createSequentialGroup()
                        .addComponent(txt18, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt17, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt16, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt15_55, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt14_54, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt13_53, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt12_52, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt11_51, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt41_81, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt42_82, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt43_83, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt44_84, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt45_85, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt46, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt47, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txt48, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(odontogramPanelLayout.createSequentialGroup()
                        .addComponent(txt28, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt27, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt26, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt65_25, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt64_24, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt63_23, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt62_22, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt61_21, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt71_31, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt72_32, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt73_33, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt74_34, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt75_35, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt36, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt37, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txt38, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(odontoLbl))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        mainTabbedPane.addTab("Odontogram", new javax.swing.ImageIcon(getClass().getResource("/EkDant/icones/Tooth 2.png")), odontogramPanel); // NOI18N

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
                        .addContainerGap(578, Short.MAX_VALUE))
                    .addComponent(mainTabbedPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(mainTabbedPane, javax.swing.GroupLayout.DEFAULT_SIZE, 594, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(saveBtn)
                    .addComponent(cancelBtn))
                .addGap(29, 29, 29))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        savePatient();
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCancelActionPerformed

    private void casePaperNumberTxtFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_casePaperNumberTxtFocusLost
        validateCaseId();
    }//GEN-LAST:event_casePaperNumberTxtFocusLost

    private void addCityBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addCityBtnActionPerformed
        newCityAdded = true;
        new Cities(true).setVisible(true);
    }//GEN-LAST:event_addCityBtnActionPerformed

    private void formWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowGainedFocus
        loadCities(true);
    }//GEN-LAST:event_formWindowGainedFocus

    private void ageTxtFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ageTxtFocusLost
        validateAge();
    }//GEN-LAST:event_ageTxtFocusLost

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addCityBtn;
    private javax.swing.JLabel addressLbl;
    private javax.swing.JTextField addressTxt;
    private javax.swing.JLabel ageLbl;
    private javax.swing.JTextField ageTxt;
    private com.toedter.calendar.JDateChooser birthdayDC;
    private javax.swing.JLabel birthdayLbl;
    private javax.swing.JButton cancelBtn;
    private javax.swing.JLabel casePaperNumberLbl;
    private javax.swing.JTextField casePaperNumberTxt;
    private javax.swing.JComboBox citiesCB;
    private javax.swing.JLabel cityLbl;
    private javax.swing.JPanel detailsPanel;
    private javax.swing.JTextField emailTxt;
    private javax.swing.JComboBox genderCB;
    private javax.swing.JLabel genderLbl;
    private javax.swing.JPanel historyPanel;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel licenseMessageLbl;
    private javax.swing.JTabbedPane mainTabbedPane;
    private javax.swing.JLabel nameLbl;
    private javax.swing.JTextField nameTxt;
    private javax.swing.JLabel odontoLbl;
    private javax.swing.JPanel odontogramPanel;
    private javax.swing.JPanel patientDetailsPanel;
    private javax.swing.JTextField patientIdTxt;
    private javax.swing.JButton saveBtn;
    private javax.swing.JTextField telTxt;
    private javax.swing.JLabel telephoneLbl;
    private javax.swing.JTextField txt11_51;
    private javax.swing.JTextField txt12_52;
    private javax.swing.JTextField txt13_53;
    private javax.swing.JTextField txt14_54;
    private javax.swing.JTextField txt15_55;
    private javax.swing.JTextField txt16;
    private javax.swing.JTextField txt17;
    private javax.swing.JTextField txt18;
    private javax.swing.JTextField txt26;
    private javax.swing.JTextField txt27;
    private javax.swing.JTextField txt28;
    private javax.swing.JTextField txt36;
    private javax.swing.JTextField txt37;
    private javax.swing.JTextField txt38;
    private javax.swing.JTextField txt41_81;
    private javax.swing.JTextField txt42_82;
    private javax.swing.JTextField txt43_83;
    private javax.swing.JTextField txt44_84;
    private javax.swing.JTextField txt45_85;
    private javax.swing.JTextField txt46;
    private javax.swing.JTextField txt47;
    private javax.swing.JTextField txt48;
    private javax.swing.JTextField txt61_21;
    private javax.swing.JTextField txt62_22;
    private javax.swing.JTextField txt63_23;
    private javax.swing.JTextField txt64_24;
    private javax.swing.JTextField txt65_25;
    private javax.swing.JTextField txt71_31;
    private javax.swing.JTextField txt72_32;
    private javax.swing.JTextField txt73_33;
    private javax.swing.JTextField txt74_34;
    private javax.swing.JTextField txt75_35;
    // End of variables declaration//GEN-END:variables
}
