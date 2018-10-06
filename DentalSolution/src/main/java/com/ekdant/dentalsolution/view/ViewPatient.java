package com.ekdant.dentalsolution.view;

import com.ekdant.dentalsolution.dao.CheckUpDao;
import com.ekdant.dentalsolution.domain.PatientBean;
import com.ekdant.dentalsolution.dao.CityDao;
import com.ekdant.dentalsolution.dao.DocumentsDao;
import com.ekdant.dentalsolution.dao.LabDao;
import com.ekdant.dentalsolution.dao.OdontogramDao;
import com.ekdant.dentalsolution.dao.PatientsDao;
import com.ekdant.dentalsolution.dao.PreMedicalHistoryDao;
import com.ekdant.dentalsolution.domain.CheckupBean;
import com.ekdant.dentalsolution.domain.LabWorkBean;
import com.ekdant.dentalsolution.domain.OdontogramBean;
import com.ekdant.dentalsolution.domain.PreMedicalHistoryBean;
import com.ekdant.dentalsolution.domain.PriscriptionBean;
import com.ekdant.dentalsolution.masters.Cities;
import com.ekdant.dentalsolution.printing.Priscription;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author Sushant
 */
public class ViewPatient extends javax.swing.JFrame {

    DateFormat displayDateFormat = new SimpleDateFormat("d MMM yyyy");
    DateFormat databaseDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    DateFormat inputDateFormat = new SimpleDateFormat("MMM d, yyyy");
    List<JCheckBox> checkBoxList = new ArrayList<JCheckBox>();
    public String logedInUserType = null;
    PatientsDao patientDao;
    CityDao cityDao;
    OdontogramDao odontogramDao;
    LabDao labDao;
    CheckUpDao checkUpDao;
    PreMedicalHistoryDao preMedicalHistoryDao;
    private boolean newCityAdded;
    DocumentsDao documentsDao;

    /**
     * Creates new form JF_AlterPatient
     *
     * @param patientId
     * @param loginUserType
     */
    public ViewPatient(String patientId, String loginUserType) {
        this.logedInUserType = loginUserType;
        patientDao = new PatientsDao();
        cityDao = new CityDao();
        odontogramDao = new OdontogramDao();
        labDao = new LabDao();
        checkUpDao = new CheckUpDao();
        documentsDao = new DocumentsDao();
        preMedicalHistoryDao = new PreMedicalHistoryDao();
        initComponents();
        loadHistory(patientId);
        loadPatientDetails(patientId);
        loadOdontogram(patientId);
        if (logedInUserType.equalsIgnoreCase("staff")) {
            staffLogin();
        }
        newCityAdded = false;
        togglePanel();
        loadUserReports(patientId);
    }

    private void staffLogin() {
        historyPane.remove(1);
        historyPane.remove(1);
    }
    
    private void loadUserReports(String patientId){
        jTree1.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() == 2){
                    documentsDao.downloadFile();
                }
            }            
        });
        PatientBean patient = patientDao.fetchPatientById(Integer.parseInt(patientId));
        documentsDao.displayTree(patient, jTree1);
    }

    private void loadOdontogram(String patientId) throws HeadlessException {
        OdontogramBean odontogramBean = odontogramDao.fetchOdontogram(Integer.parseInt(patientId));
        txt18.setText(odontogramBean.getOdon18());
        txt17.setText(odontogramBean.getOdon17());
        txt16.setText(odontogramBean.getOdon16());
        txt15_55.setText(odontogramBean.getOdon1555());
        txt14_54.setText(odontogramBean.getOdon1454());
        txt13_53.setText(odontogramBean.getOdon1353());
        txt12_52.setText(odontogramBean.getOdon1252());
        txt11_51.setText(odontogramBean.getOdon1151());
        txt41_81.setText(odontogramBean.getOdon4181());
        txt42_82.setText(odontogramBean.getOdon4282());
        txt43_83.setText(odontogramBean.getOdon4383());
        txt44_84.setText(odontogramBean.getOdon4484());
        txt45_85.setText(odontogramBean.getOdon4585());
        txt46.setText(odontogramBean.getOdon46());
        txt47.setText(odontogramBean.getOdon47());
        txt48.setText(odontogramBean.getOdon48());
        txt28.setText(odontogramBean.getOdon28());
        txt27.setText(odontogramBean.getOdon27());
        txt26.setText(odontogramBean.getOdon26());
        txt65_25.setText(odontogramBean.getOdon6525());
        txt64_24.setText(odontogramBean.getOdon6424());
        txt63_23.setText(odontogramBean.getOdon6323());
        txt62_22.setText(odontogramBean.getOdon6222());
        txt61_21.setText(odontogramBean.getOdon6121());
        txt71_31.setText(odontogramBean.getOdon7131());
        txt72_32.setText(odontogramBean.getOdon7232());
        txt73_33.setText(odontogramBean.getOdon7333());
        txt74_34.setText(odontogramBean.getOdon7434());
        txt75_35.setText(odontogramBean.getOdon7535());
        txt36.setText(odontogramBean.getOdon36());
        txt37.setText(odontogramBean.getOdon37());
        txt38.setText(odontogramBean.getOdon38());
        patientIdTxt.setText(odontogramBean.getPatientId()+"");
        
    }

    private void loadPatientDetails(String patientId) {
        cityDao.getCities(cityCB);
        setPremedicalHistory(patientId);
        PatientBean patient = patientDao.fetchPatientById(Integer.parseInt(patientId));
        if (patient.getBirthDate() != null) {
            birthdayDC.setDate(patient.getBirthDate());
        }
        patientIdTxt.setText(String.valueOf(patient.getPatientId()));
        nameTxt.setText(patient.getName());
        casePaperNumberTxt.setText(patient.getCaseId());
        genderCB.setSelectedItem(patient.getGender());
        addressTxt.setText(patient.getAddress());
        cityCB.setSelectedItem(patient.getCity());
        mobileTxt.setText(patient.getTelephone());
        emailTxt.setText(patient.getEmail());
        ageTxt.setText(String.valueOf(patient.getAge()));
    }

    private void setPremedicalHistory(String patientId) {
        List<String> preMedicalHistoryList = new ArrayList<String>();
        List<PreMedicalHistoryBean> preMedicalHistories = preMedicalHistoryDao.fetchPreMedicalHistory();
        for(PreMedicalHistoryBean preMedicalHistory : preMedicalHistories){
            preMedicalHistoryList.add(preMedicalHistory.getDiscription());
        }
        String preMedicalHistoryDiscription = patientDao.fetchPatientById(Integer.parseInt(patientId)).getPreMedicalHistory();

        panel = new JPanel();
        panel.setSize(250, (preMedicalHistoryList.size() / 2) * 100);
        panel.setBorder(new TitledBorder("Pre Medical History"));
        GroupLayout layout = new GroupLayout(panel);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        GroupLayout.ParallelGroup parllelLayout = layout.createParallelGroup(GroupLayout.Alignment.LEADING);
        GroupLayout.SequentialGroup seqGroup = layout.createSequentialGroup();
        String preMedicalHistoryArr[] = preMedicalHistoryDiscription != null ? preMedicalHistoryDiscription.split(",") : new String[0];
        for (String discription : preMedicalHistoryList) {
            JCheckBox checkBox = new JCheckBox(discription, isPrevioslySetHistory(preMedicalHistoryArr, discription));
            checkBoxList.add(checkBox);
            checkBox.setEnabled(false);
            parllelLayout.addComponent(checkBox);
            seqGroup.addComponent(checkBox);
        }

        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createSequentialGroup()
                        .addGroup(parllelLayout))
        );

        layout.setVerticalGroup(seqGroup);
        panel.setLayout(layout);
        panel.setEnabled(false);
        preMedicalHistoryPanel.add(panel);
    }

    public boolean isPrevioslySetHistory(String preMedicalHistoryArr[], String discription) {
        for (String previousHistory : preMedicalHistoryArr) {
            if (previousHistory.equalsIgnoreCase(discription)) {
                return true;
            }
        }
        return false;
    }

    private void loadHistory(String patinetId) {
        List<CheckupBean> checkups = checkUpDao.fetchPatientCheckup(Integer.parseInt(patinetId));
        DefaultTableModel model = (DefaultTableModel) treatmentHistoryTbl.getModel();
        model.setNumRows(0);
        for (CheckupBean checkup : checkups) {
            model.addRow(new Object[]{
                displayDateFormat.format(checkup.getDate()),
                checkup.getDentistName(),
                checkup.getTreatment(),
                checkup.getDignosis(),
                checkup.getFees(),
                checkup.getConsultantingDoctorFee(),
                checkup.getCheckupId()
            });
        }
        
        ListSelectionModel selectionModel = treatmentHistoryTbl.getSelectionModel();
        treatmentHistoryTbl.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        selectionModel.addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent e) {
                int checkupId = Integer.parseInt(treatmentHistoryTbl.getValueAt(treatmentHistoryTbl.getSelectedRow(), 6).toString());
                loadPriscriptionHistory(checkupId);
                loadCheckupDetails(checkupId);
                loadLabDetails(checkupId);
            }
        });
    }

    private void loadPriscriptionHistory(int checkupId) {
        List<PriscriptionBean> priscriptions = patientDao.fetchPriscription(checkupId);
        DefaultTableModel data = (DefaultTableModel) priscriptionHistoryTbl.getModel();
        data.setNumRows(0);
        for(PriscriptionBean priscription : priscriptions){
            data.addRow(new Object[]{
                priscription.getMedicineType(),
                priscription.getMedicineName(),
                priscription.getMedicineStrength(),
                priscription.getFrequency(),
                priscription.getDuration()
            });
        }
    }

    public void loadCheckupDetails(int checkupId) {
        CheckupBean checkup = checkUpDao.fetchCheckup(checkupId);        
        weightHistLabel.setText(" Weight :  " + (checkup.getWeight()));
        bpHistLabel.setText(" BP S/D :  " + (checkup.getBp()));
        pulseHistLabel.setText(" Pulse :  " + (checkup.getPulse()));
    }

    private void updatePatient() throws HeadlessException {
        updatePatientDetails();
        updateOdontogram();
        this.dispose();
    }

    private void updateOdontogram() throws HeadlessException {
        OdontogramBean odontogram = populateOdontogram();
        boolean success = odontogramDao.updateOdontogram(odontogram);
        if (!success) {
            JOptionPane.showMessageDialog(null, "Could not save data odontogram!", "Error!", JOptionPane.ERROR_MESSAGE);
        }
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
    
    private void updatePatientDetails() throws HeadlessException {
        if (nameTxt.getText().length() < 3) {
            JOptionPane.showMessageDialog(null, "Please enter patients name!", "Attention", JOptionPane.WARNING_MESSAGE);
        } else if (ageTxt.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter age!", "Attention", JOptionPane.WARNING_MESSAGE);
        } else if (casePaperNumberTxt.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter Case Paper Number!", "Attention", JOptionPane.WARNING_MESSAGE);
        }

        PatientBean patient = new PatientBean();
        String patientId = patientIdTxt.getText();
        if (!patientId.isEmpty()) {
            patient.setPatientId(Integer.parseInt(patientId));
        }
        patient.setName(nameTxt.getText());
        patient.setCaseId(casePaperNumberTxt.getText());
        patient.setBirthDate(birthdayDC.getDate());
        patient.setAddress(addressTxt.getText());
        String city = cityCB.getSelectedItem().toString().equalsIgnoreCase("select") ? "" : cityCB.getSelectedItem().toString();
        patient.setCity(city);
        patient.setGender(genderCB.getSelectedItem().toString());
        patient.setTelephone(mobileTxt.getText());
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

        if (patientDao.updatePatient(patient)) {
            JOptionPane.showMessageDialog(null, "Patient successfully changed!", "Success!", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Unable to change patient data!" , "Error!", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void togglePanel() {
        if (historyPanel.isShowing()) {
            printBtn.setVisible(true);
        } else {
            printBtn.setVisible(false);
        }
    }
    
    private void initCities() {
        cityDao.getCities(cityCB);
        if (newCityAdded) {
            String newlyAddedCity = cityDao.fetchLatestCity();
            cityCB.setSelectedItem(newlyAddedCity);
        }
    }

    private void validateCaseId() throws HeadlessException {
        String caseId = casePaperNumberTxt.getText();
        PatientsDao patientsDao = new PatientsDao();
        if (!caseId.isEmpty()) {
            PatientBean patient = patientsDao.fetchPatientByCaseId(Integer.parseInt(caseId));
            if (patient.getPatientId() > 0) {
                JOptionPane.showMessageDialog(null, "There is already a registered patient with this Case Paper Number!", "Attention", JOptionPane.WARNING_MESSAGE);
                this.casePaperNumberTxt.requestFocus();
            }
        }        
    }

    private void editButton() {
        nameTxt.setEnabled(true);
        genderCB.setEnabled(true);
        casePaperNumberTxt.setEnabled(true);
        ageTxt.setEnabled(true);
        birthdayDC.setEnabled(true);
        addressTxt.setEnabled(true);
        cityCB.setEnabled(true);
        mobileTxt.setEnabled(true);
        emailTxt.setEnabled(true);
        saveBtn.setEnabled(true);
        panel.setEnabled(true);
        newCityBtn.setEnabled(true);
        birthdayDC.setEnabled(true);
        for (JCheckBox checkBox : checkBoxList) {
            checkBox.setEnabled(true);
        }
        enableOdontogram();
    }

    private void enableOdontogram(){
        txt18.setEnabled(true);
        txt17.setEnabled(true);
        txt16.setEnabled(true);
        txt15_55.setEnabled(true);
        txt14_54.setEnabled(true);
        txt13_53.setEnabled(true);
        txt12_52.setEnabled(true);
        txt11_51.setEnabled(true);
        txt41_81.setEnabled(true);
        txt42_82.setEnabled(true);
        txt43_83.setEnabled(true);
        txt44_84.setEnabled(true);
        txt45_85.setEnabled(true);
        txt46.setEnabled(true);
        txt47.setEnabled(true);
        txt48.setEnabled(true);
        txt28.setEnabled(true);
        txt27.setEnabled(true);
        txt26.setEnabled(true);
        txt65_25.setEnabled(true);
        txt64_24.setEnabled(true);
        txt63_23.setEnabled(true);
        txt62_22.setEnabled(true);
        txt61_21.setEnabled(true);
        txt71_31.setEnabled(true);
        txt72_32.setEnabled(true);
        txt73_33.setEnabled(true);
        txt74_34.setEnabled(true);
        txt75_35.setEnabled(true);
        txt36.setEnabled(true);
        txt37.setEnabled(true);
        txt38.setEnabled(true);
    }
    
    private void loadLabDetails(int checkupId) {
        PatientsDao patientsDao = new PatientsDao();
        LabWorkBean labWork = labDao.fetchLabWorkDetails(checkupId);
        if (labWork.getId() > 0) {
            labWorkNameHistoryLbl.setText(labWork.getWork());
            labWorkLabHistoryLbl.setText(labWork.getLab().getName());
            labWorkHistoryStatusLbl.setText(getLabStatus(labWork.getStatus()));
            ulLbl.setText(labWork.getUl() + "  ");
            urLbl.setText("  " + labWork.getUr());
            llLbl.setText(labWork.getLl() + "  ");
            lrLbl.setText("  " + labWork.getLr());

        } else {
            labWorkNameHistoryLbl.setText("");
            labWorkLabHistoryLbl.setText("");
            labWorkHistoryStatusLbl.setText("");
            ulLbl.setText("");
            urLbl.setText("");
            llLbl.setText("");
            lrLbl.setText("");
        }        
    }

    private String getLabStatus(int statusId) {
        String statusStr = "";
        if (statusId == 1) {
            statusStr = "Impression Done";
        } else if (statusId == 2) {
            statusStr = "Lab Work Submitted";
        } else if (statusId == 3) {
            statusStr = "Lab Work Completed";
        } else if (statusId == 4) {
            statusStr = "Closed";
        }
        return statusStr;
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
    
    private void printPriscription(){
        int selectedTreatmentRow = treatmentHistoryTbl.getSelectedRow();
        if (selectedTreatmentRow == -1) {
            JOptionPane.showMessageDialog(null, "Please Select Treatment to print priscription !!", "Error!", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String selectedTreatmentId = treatmentHistoryTbl.getValueAt(selectedTreatmentRow, 6).toString();
        int patientTreatmentId = Integer.parseInt(selectedTreatmentId);
        Priscription canvas = new Priscription(patientTreatmentId);
        canvas.printPriscription();
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        historyPane = new javax.swing.JTabbedPane();
        patientDetailsPanel = new javax.swing.JPanel();
        nameLbl = new javax.swing.JLabel();
        addressTxt = new javax.swing.JTextField();
        casePaperLbl = new javax.swing.JLabel();
        birthDayLbl = new javax.swing.JLabel();
        genderLbl = new javax.swing.JLabel();
        genderCB = new javax.swing.JComboBox();
        addressLbl = new javax.swing.JLabel();
        nameTxt = new javax.swing.JTextField();
        cityLbl = new javax.swing.JLabel();
        mobileLbl = new javax.swing.JLabel();
        emailLbl = new javax.swing.JLabel();
        emailTxt = new javax.swing.JTextField();
        casePaperNumberTxt = new javax.swing.JTextField();
        mobileTxt = new javax.swing.JTextField();
        patientIdTxt = new javax.swing.JTextField();
        cityCB = new javax.swing.JComboBox();
        ageLbl = new javax.swing.JLabel();
        ageTxt = new javax.swing.JTextField();
        preMedicalHistoryPanel = new javax.swing.JPanel();
        newCityBtn = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTree1 = new javax.swing.JTree();
        birthdayDC = new com.toedter.calendar.JDateChooser();
        odontogramaPanel = new javax.swing.JPanel();
        odontogramLbl = new javax.swing.JLabel();
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
        historyPanel = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        treatmentHistoryTbl = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        priscriptionHistoryTbl = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        bpHistLabel = new javax.swing.JLabel();
        pulseHistLabel = new javax.swing.JLabel();
        weightHistLabel = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        labWorkNameHistoryLbl = new javax.swing.JLabel();
        labWorkLabHistoryLbl = new javax.swing.JLabel();
        labWorkHistoryStatusLbl = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        ulLbl = new javax.swing.JLabel();
        urLbl = new javax.swing.JLabel();
        llLbl = new javax.swing.JLabel();
        lrLbl = new javax.swing.JLabel();
        saveBtn = new javax.swing.JButton();
        cancelBtn = new javax.swing.JButton();
        editBtn = new javax.swing.JButton();
        printBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("View Patients");
        setName("Patients"); // NOI18N
        setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width,Toolkit.getDefaultToolkit().getScreenSize().height-Toolkit.getDefaultToolkit().getScreenInsets(getGraphicsConfiguration()).bottom));
        setResizable(false);
        addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                formWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
            }
        });

        historyPane.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        historyPane.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                historyPaneStateChanged(evt);
            }
        });

        patientDetailsPanel.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N

        nameLbl.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        nameLbl.setForeground(new java.awt.Color(51, 51, 255));
        nameLbl.setText("* Name:");

        addressTxt.setEnabled(false);
        addressTxt.setNextFocusableComponent(cityCB);

        casePaperLbl.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        casePaperLbl.setForeground(new java.awt.Color(51, 51, 255));
        casePaperLbl.setText("* Case Id:");

        birthDayLbl.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        birthDayLbl.setForeground(new java.awt.Color(51, 51, 255));
        birthDayLbl.setText("   Date of birth:");

        genderLbl.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        genderLbl.setForeground(new java.awt.Color(51, 51, 255));
        genderLbl.setText("* Gender:");

        genderCB.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Male", "Female" }));
        genderCB.setEnabled(false);
        genderCB.setNextFocusableComponent(ageTxt);

        addressLbl.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        addressLbl.setForeground(new java.awt.Color(51, 51, 255));
        addressLbl.setText("   Address:");

        nameTxt.setEnabled(false);
        nameTxt.setNextFocusableComponent(genderCB);

        cityLbl.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        cityLbl.setForeground(new java.awt.Color(51, 51, 255));
        cityLbl.setText("   City:");

        mobileLbl.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        mobileLbl.setForeground(new java.awt.Color(51, 51, 255));
        mobileLbl.setText("   Mobile:");

        emailLbl.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        emailLbl.setForeground(new java.awt.Color(51, 51, 255));
        emailLbl.setText("   Email:");

        emailTxt.setEnabled(false);
        emailTxt.setNextFocusableComponent(saveBtn);

        casePaperNumberTxt.setEnabled(false);
        casePaperNumberTxt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                casePaperNumberTxtFocusLost(evt);
            }
        });

        mobileTxt.setEnabled(false);
        mobileTxt.setNextFocusableComponent(emailTxt);

        patientIdTxt.setEditable(false);
        patientIdTxt.setMinimumSize(new java.awt.Dimension(0, 0));
        patientIdTxt.setPreferredSize(new java.awt.Dimension(0, 0));

        cityCB.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "select" }));
        cityCB.setEnabled(false);
        cityCB.setNextFocusableComponent(mobileTxt);

        ageLbl.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        ageLbl.setForeground(new java.awt.Color(51, 51, 255));
        ageLbl.setText("* Age:");

        ageTxt.setEnabled(false);
        ageTxt.setNextFocusableComponent(addressTxt);
        ageTxt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                ageTxtFocusLost(evt);
            }
        });

        javax.swing.GroupLayout preMedicalHistoryPanelLayout = new javax.swing.GroupLayout(preMedicalHistoryPanel);
        preMedicalHistoryPanel.setLayout(preMedicalHistoryPanelLayout);
        preMedicalHistoryPanelLayout.setHorizontalGroup(
            preMedicalHistoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 250, Short.MAX_VALUE)
        );
        preMedicalHistoryPanelLayout.setVerticalGroup(
            preMedicalHistoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        newCityBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/EkDant/icones/plus-icon.png"))); // NOI18N
        newCityBtn.setEnabled(false);
        newCityBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newCityBtnActionPerformed(evt);
            }
        });

        jScrollPane1.setBorder(javax.swing.BorderFactory.createTitledBorder("Documents"));
        jScrollPane1.setAlignmentY(1.5F);

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("root");
        jTree1.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        jTree1.setOpaque(false);
        jScrollPane1.setViewportView(jTree1);

        javax.swing.GroupLayout patientDetailsPanelLayout = new javax.swing.GroupLayout(patientDetailsPanel);
        patientDetailsPanel.setLayout(patientDetailsPanelLayout);
        patientDetailsPanelLayout.setHorizontalGroup(
            patientDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(patientDetailsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(patientDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(patientDetailsPanelLayout.createSequentialGroup()
                        .addComponent(emailLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(patientIdTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(patientDetailsPanelLayout.createSequentialGroup()
                        .addComponent(nameLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(patientDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(emailTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(mobileTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ageTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(patientDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(patientDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(genderCB, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, patientDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(nameTxt)
                                        .addComponent(casePaperNumberTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addComponent(addressTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(patientDetailsPanelLayout.createSequentialGroup()
                                    .addComponent(cityCB, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(newCityBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addComponent(casePaperLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(genderLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ageLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(patientDetailsPanelLayout.createSequentialGroup()
                        .addComponent(birthDayLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(birthdayDC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(addressLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cityLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mobileLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 5, Short.MAX_VALUE)
                .addComponent(preMedicalHistoryPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(229, Short.MAX_VALUE))
        );

        patientDetailsPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {addressTxt, emailTxt, mobileTxt});

        patientDetailsPanelLayout.setVerticalGroup(
            patientDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, patientDetailsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(patientDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(patientDetailsPanelLayout.createSequentialGroup()
                        .addComponent(patientIdTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(patientDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(patientDetailsPanelLayout.createSequentialGroup()
                                .addComponent(nameLbl)
                                .addGap(18, 18, 18)
                                .addComponent(casePaperLbl)
                                .addGap(18, 18, 18)
                                .addComponent(genderLbl))
                            .addGroup(patientDetailsPanelLayout.createSequentialGroup()
                                .addComponent(nameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(casePaperNumberTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(genderCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(patientDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ageLbl)
                            .addComponent(ageTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(patientDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(birthDayLbl)
                            .addComponent(birthdayDC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(patientDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(addressLbl)
                            .addComponent(addressTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(patientDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(newCityBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(patientDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(cityLbl)
                                .addComponent(cityCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(patientDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(mobileLbl)
                            .addComponent(mobileTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(patientDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(emailLbl)
                            .addComponent(emailTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(preMedicalHistoryPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 347, Short.MAX_VALUE))
                .addGap(358, 358, 358))
        );

        patientDetailsPanelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {addressLbl, addressTxt, ageLbl, ageTxt, birthDayLbl, casePaperLbl, casePaperNumberTxt, cityCB, cityLbl, emailLbl, emailTxt, genderCB, genderLbl, mobileLbl, mobileTxt, nameLbl, nameTxt, newCityBtn});

        historyPane.addTab("Patient Details", new javax.swing.ImageIcon(getClass().getResource("/EkDant/icones/Patient Boy 1.png")), patientDetailsPanel); // NOI18N

        odontogramLbl.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        odontogramLbl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/EkDant/images/mrtooth-odontograma_maior.png"))); // NOI18N
        odontogramLbl.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);

        txt18.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        txt18.setEnabled(false);

        txt17.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        txt17.setEnabled(false);

        txt16.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        txt16.setEnabled(false);

        txt15_55.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        txt15_55.setEnabled(false);

        txt14_54.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        txt14_54.setEnabled(false);

        txt13_53.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        txt13_53.setEnabled(false);

        txt12_52.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        txt12_52.setEnabled(false);

        txt11_51.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        txt11_51.setEnabled(false);

        txt41_81.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        txt41_81.setEnabled(false);

        txt42_82.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        txt42_82.setEnabled(false);

        txt43_83.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        txt43_83.setEnabled(false);

        txt44_84.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        txt44_84.setEnabled(false);

        txt45_85.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        txt45_85.setEnabled(false);

        txt46.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        txt46.setEnabled(false);

        txt47.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        txt47.setEnabled(false);

        txt48.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        txt48.setEnabled(false);

        txt28.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        txt28.setEnabled(false);

        txt27.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        txt27.setEnabled(false);

        txt26.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        txt26.setEnabled(false);

        txt65_25.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        txt65_25.setEnabled(false);

        txt64_24.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        txt64_24.setEnabled(false);

        txt63_23.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        txt63_23.setEnabled(false);

        txt62_22.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        txt62_22.setEnabled(false);

        txt61_21.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        txt61_21.setEnabled(false);

        txt71_31.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        txt71_31.setEnabled(false);

        txt72_32.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        txt72_32.setEnabled(false);

        txt73_33.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        txt73_33.setEnabled(false);

        txt74_34.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        txt74_34.setEnabled(false);

        txt75_35.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        txt75_35.setEnabled(false);

        txt36.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        txt36.setEnabled(false);

        txt37.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        txt37.setEnabled(false);

        txt38.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        txt38.setEnabled(false);

        javax.swing.GroupLayout odontogramaPanelLayout = new javax.swing.GroupLayout(odontogramaPanel);
        odontogramaPanel.setLayout(odontogramaPanelLayout);
        odontogramaPanelLayout.setHorizontalGroup(
            odontogramaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(odontogramaPanelLayout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addGroup(odontogramaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
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
                .addComponent(odontogramLbl)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(odontogramaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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
                .addContainerGap(463, Short.MAX_VALUE))
        );
        odontogramaPanelLayout.setVerticalGroup(
            odontogramaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(odontogramaPanelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(odontogramaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(odontogramaPanelLayout.createSequentialGroup()
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
                    .addGroup(odontogramaPanelLayout.createSequentialGroup()
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
                    .addComponent(odontogramLbl))
                .addContainerGap(215, Short.MAX_VALUE))
        );

        historyPane.addTab("Odontogram", new javax.swing.ImageIcon(getClass().getResource("/EkDant/icones/Tooth 2.png")), odontogramaPanel); // NOI18N

        treatmentHistoryTbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Date", "Doctor", "Treatment", "Dignosis", "Fees", "Consulting Docor Fees", "checkupId"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Float.class, java.lang.Float.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(treatmentHistoryTbl);
        if (treatmentHistoryTbl.getColumnModel().getColumnCount() > 0) {
            treatmentHistoryTbl.getColumnModel().getColumn(6).setMinWidth(0);
            treatmentHistoryTbl.getColumnModel().getColumn(6).setPreferredWidth(0);
            treatmentHistoryTbl.getColumnModel().getColumn(6).setMaxWidth(0);
        }

        priscriptionHistoryTbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Type", "Name", "Strength", "Frequency", "Duration"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        priscriptionHistoryTbl.setRowHeight(25);
        jScrollPane4.setViewportView(priscriptionHistoryTbl);

        bpHistLabel.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        bpHistLabel.setForeground(new java.awt.Color(51, 51, 255));
        bpHistLabel.setToolTipText("");

        pulseHistLabel.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        pulseHistLabel.setForeground(new java.awt.Color(51, 51, 255));
        pulseHistLabel.setToolTipText("");

        weightHistLabel.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        weightHistLabel.setForeground(new java.awt.Color(51, 51, 255));
        weightHistLabel.setToolTipText("");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(weightHistLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bpHistLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pulseHistLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {bpHistLabel, pulseHistLabel, weightHistLabel});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(weightHistLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(bpHistLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pulseHistLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {bpHistLabel, pulseHistLabel, weightHistLabel});

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Lab Work", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Verdana", 0, 12), new java.awt.Color(51, 51, 255))); // NOI18N

        labWorkNameHistoryLbl.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        labWorkNameHistoryLbl.setForeground(new java.awt.Color(51, 51, 255));

        labWorkLabHistoryLbl.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        labWorkLabHistoryLbl.setForeground(new java.awt.Color(51, 51, 255));

        labWorkHistoryStatusLbl.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        labWorkHistoryStatusLbl.setForeground(new java.awt.Color(51, 51, 255));

        jPanel3.setBackground(new java.awt.Color(51, 51, 255));
        jPanel3.setEnabled(false);

        ulLbl.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        ulLbl.setForeground(new java.awt.Color(51, 51, 255));
        ulLbl.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        ulLbl.setOpaque(true);

        urLbl.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        urLbl.setForeground(new java.awt.Color(51, 51, 255));
        urLbl.setOpaque(true);

        llLbl.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        llLbl.setForeground(new java.awt.Color(51, 51, 255));
        llLbl.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        llLbl.setOpaque(true);

        lrLbl.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        lrLbl.setForeground(new java.awt.Color(51, 51, 255));
        lrLbl.setOpaque(true);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ulLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(llLbl))
                .addGap(1, 1, 1)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lrLbl)
                    .addComponent(urLbl))
                .addGap(0, 0, 0))
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {llLbl, lrLbl, ulLbl, urLbl});

        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ulLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(urLbl))
                .addGap(1, 1, 1)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(llLbl)
                    .addComponent(lrLbl))
                .addGap(0, 0, 0))
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {llLbl, lrLbl, ulLbl, urLbl});

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labWorkHistoryStatusLbl)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labWorkNameHistoryLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labWorkLabHistoryLbl))
                        .addGap(18, 18, 18)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {labWorkHistoryStatusLbl, labWorkLabHistoryLbl, labWorkNameHistoryLbl});

        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(labWorkNameHistoryLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(labWorkLabHistoryLbl)
                        .addGap(18, 18, 18)
                        .addComponent(labWorkHistoryStatusLbl)))
                .addContainerGap(68, Short.MAX_VALUE))
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {labWorkHistoryStatusLbl, labWorkLabHistoryLbl, labWorkNameHistoryLbl});

        javax.swing.GroupLayout historyPanelLayout = new javax.swing.GroupLayout(historyPanel);
        historyPanel.setLayout(historyPanelLayout);
        historyPanelLayout.setHorizontalGroup(
            historyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(historyPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(historyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, historyPanelLayout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 555, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        historyPanelLayout.setVerticalGroup(
            historyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(historyPanelLayout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(historyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(36, Short.MAX_VALUE))
        );

        historyPane.addTab("Historical", new javax.swing.ImageIcon(getClass().getResource("/EkDant/icones/Calendar Statistics.png")), historyPanel); // NOI18N

        saveBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/EkDant/icones/Patient Boy 1 Check.png"))); // NOI18N
        saveBtn.setMnemonic('U');
        saveBtn.setText("Update");
        saveBtn.setEnabled(false);
        saveBtn.setNextFocusableComponent(cancelBtn);
        saveBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveBtnActionPerformed(evt);
            }
        });

        cancelBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/EkDant/icones/Patient Boy 1 Stop.png"))); // NOI18N
        cancelBtn.setMnemonic('C');
        cancelBtn.setText("Cancel");
        cancelBtn.setToolTipText("");
        cancelBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelBtnActionPerformed(evt);
            }
        });

        editBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/EkDant/icones/Patient Boy 1 Edit 1.png"))); // NOI18N
        editBtn.setMnemonic('E');
        editBtn.setText("Edit");
        editBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editBtnActionPerformed(evt);
            }
        });

        printBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/EkDant/icones/Print.png"))); // NOI18N
        printBtn.setMnemonic('C');
        printBtn.setText("Print Priscription");
        printBtn.setToolTipText("");
        printBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(editBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(saveBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(cancelBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(printBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(historyPane, javax.swing.GroupLayout.PREFERRED_SIZE, 1167, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(historyPane, javax.swing.GroupLayout.PREFERRED_SIZE, 559, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancelBtn)
                    .addComponent(saveBtn)
                    .addComponent(editBtn)
                    .addComponent(printBtn))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void saveBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveBtnActionPerformed
        updatePatient();
    }//GEN-LAST:event_saveBtnActionPerformed

    private void cancelBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelBtnActionPerformed
        this.dispose();
    }//GEN-LAST:event_cancelBtnActionPerformed

    private void editBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editBtnActionPerformed
        editButton();
    }//GEN-LAST:event_editBtnActionPerformed

    private void casePaperNumberTxtFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_casePaperNumberTxtFocusLost
        validateCaseId();
    }//GEN-LAST:event_casePaperNumberTxtFocusLost

    private void formWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowGainedFocus
        initCities();
    }//GEN-LAST:event_formWindowGainedFocus

    private void newCityBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newCityBtnActionPerformed
        newCityAdded = true;
        new Cities(true).setVisible(true);
    }//GEN-LAST:event_newCityBtnActionPerformed

    private void ageTxtFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ageTxtFocusLost
        validateAge();
    }//GEN-LAST:event_ageTxtFocusLost

    private void historyPaneStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_historyPaneStateChanged
        togglePanel();
    }//GEN-LAST:event_historyPaneStateChanged

    private void printBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printBtnActionPerformed
       printPriscription();
    }//GEN-LAST:event_printBtnActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel addressLbl;
    private javax.swing.JTextField addressTxt;
    private javax.swing.JLabel ageLbl;
    private javax.swing.JTextField ageTxt;
    private javax.swing.JLabel birthDayLbl;
    private com.toedter.calendar.JDateChooser birthdayDC;
    private javax.swing.JLabel bpHistLabel;
    private javax.swing.JButton cancelBtn;
    private javax.swing.JLabel casePaperLbl;
    private javax.swing.JTextField casePaperNumberTxt;
    private javax.swing.JComboBox cityCB;
    private javax.swing.JLabel cityLbl;
    private javax.swing.JButton editBtn;
    private javax.swing.JLabel emailLbl;
    private javax.swing.JTextField emailTxt;
    private javax.swing.JComboBox genderCB;
    private javax.swing.JLabel genderLbl;
    private javax.swing.JTabbedPane historyPane;
    private javax.swing.JPanel historyPanel;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTree jTree1;
    private javax.swing.JLabel labWorkHistoryStatusLbl;
    private javax.swing.JLabel labWorkLabHistoryLbl;
    private javax.swing.JLabel labWorkNameHistoryLbl;
    private javax.swing.JLabel llLbl;
    private javax.swing.JLabel lrLbl;
    private javax.swing.JLabel mobileLbl;
    private javax.swing.JTextField mobileTxt;
    private javax.swing.JLabel nameLbl;
    private javax.swing.JTextField nameTxt;
    private javax.swing.JButton newCityBtn;
    private javax.swing.JLabel odontogramLbl;
    private javax.swing.JPanel odontogramaPanel;
    private javax.swing.JPanel patientDetailsPanel;
    private javax.swing.JTextField patientIdTxt;
    private javax.swing.JPanel preMedicalHistoryPanel;
    private javax.swing.JButton printBtn;
    private javax.swing.JTable priscriptionHistoryTbl;
    private javax.swing.JLabel pulseHistLabel;
    private javax.swing.JButton saveBtn;
    private javax.swing.JTable treatmentHistoryTbl;
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
    private javax.swing.JLabel ulLbl;
    private javax.swing.JLabel urLbl;
    private javax.swing.JLabel weightHistLabel;
    // End of variables declaration//GEN-END:variables
    private javax.swing.JPanel panel;
}
