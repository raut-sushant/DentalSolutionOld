/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ekdant.dentalsolution.treatments;

import com.ekdant.dentalsolution.dao.CheckUpDao;
import com.ekdant.dentalsolution.dao.ClinicDao;
import com.ekdant.dentalsolution.dao.DoctorDao;
import com.ekdant.dentalsolution.dao.DocumentsDao;
import com.ekdant.dentalsolution.dao.LabDao;
import com.ekdant.dentalsolution.dao.PatientsDao;
import com.ekdant.dentalsolution.dao.TreatmentDao;
import com.ekdant.dentalsolution.dao.MedicineDao;
import com.ekdant.dentalsolution.dao.ReferedByDao;
import com.ekdant.dentalsolution.domain.CheckupBean;
import com.ekdant.dentalsolution.domain.DoctorBean;
import com.ekdant.dentalsolution.domain.LabBean;
import com.ekdant.dentalsolution.domain.LabWorkBean;
import com.ekdant.dentalsolution.domain.LabWorkNameBean;
import com.ekdant.dentalsolution.domain.MedicineBean;
import com.ekdant.dentalsolution.domain.PatientBean;
import com.ekdant.dentalsolution.domain.PriscriptionBean;
import com.ekdant.dentalsolution.domain.ReferedByBean;
import com.ekdant.dentalsolution.domain.TreatmentBean;
import com.ekdant.dentalsolution.masters.ReferedBy;
import com.ekdant.dentalsolution.masters.Treatment;
import com.ekdant.dentalsolution.printing.Priscription;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import com.ekdant.dentalsolution.utilities.Java2sAutoTextField;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Sushant Raut
 */
public class PatientTreatment extends javax.swing.JFrame {

    final int patientId;
    DateFormat displayDateFormat = new SimpleDateFormat("d MMM yyyy");
    
    List<String> medicineType = new ArrayList<String>();
    List<String> medicines = new ArrayList<String>();
    Map<String, List<String>> medicineStrengthMap = new HashMap<String, List<String>>();
    String preMedicalHistory;
    static int patientTreatmentId = 0;
    PatientsDao patientsDao;
    TreatmentDao treatmentDao;
    DoctorDao doctorDao;
    LabDao labDao;
    MedicineDao medicineDao;
    ReferedByDao referedByDao;
    CheckUpDao checkUpDao;
    ClinicDao clinicDao;
    DocumentsDao documentsDao;
    private boolean newReferedByAdded;
    
    /**
     * Creates new form PatientTreatment
     *
     * @param patientId
     */
    public PatientTreatment(int patientId) {
        this.patientId = patientId;
        patientsDao = new PatientsDao();
        treatmentDao = new TreatmentDao();
        doctorDao = new DoctorDao();
        labDao = new LabDao();
        medicineDao = new MedicineDao();
        referedByDao = new ReferedByDao();
        checkUpDao = new CheckUpDao();
        clinicDao = new ClinicDao();
        documentsDao = new DocumentsDao();
        
        loadMedicines();
        initComponents();
        loadMedicineTypes();
        treatmentDate.setDate(new Date());
        loadDoctors();
        setFormFields();
        loadHistory();
        loadReferedBy();
        priscriptionNewTbl.setCellSelectionEnabled(true);
        showPreMedicalHistory(patientId);
        loadLabWork();
        loadLabWorkName();
        newReferedByAdded = false;
        togglePanel();
        loadUserReports(patientId);
        priscriptionTableListner();
    }
    
    private void priscriptionTableListner(){
        priscriptionNewTbl.addMouseListener(new MouseListener() {

            public void mouseClicked(MouseEvent e) {
                if(priscriptionNewTbl.columnAtPoint(e.getPoint()) == 5 && priscriptionNewTbl.rowAtPoint(e.getPoint()) >= 0){
                    int row = priscriptionNewTbl.rowAtPoint(e.getPoint());
                    System.out.print(row);
                    ((DefaultTableModel)priscriptionNewTbl.getModel()).removeRow(row);
                }
            }

            public void mousePressed(MouseEvent e) {}

            public void mouseReleased(MouseEvent e) {}

            public void mouseEntered(MouseEvent e) {}

            public void mouseExited(MouseEvent e) {}
        });
        
        priscriptionNewTbl.setDefaultRenderer(String.class, new TableCellRenderer() {

            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                String cellValue = (String) value;
                final int selectedRow = row;
                JLabel appointmentLbl = new JLabel();
                if (column == 5) {
                    JButton deleteBtn1 = new JButton(new javax.swing.ImageIcon(getClass().getResource("/EkDant/icones/cross.gif")));
                    deleteBtn1.setToolTipText("Delete this row");
                    deleteBtn1.setOpaque(true);
                    return deleteBtn1;
                }else{
                    if (cellValue != null && !cellValue.isEmpty()) {
                        appointmentLbl.setText(cellValue);                    
                    }                
                    return appointmentLbl;
                }
            }
        });
    }

    private void togglePanel() {
        if (newTreatmentPanel.isShowing()) {
            saveBtn.setVisible(true);
            saveprintBtn.setVisible(true);
            printBtn.setVisible(false);
            deleteBtn.setVisible(false);
            patientTreatmentId = 0;
        } else if (treatmentHistoryPanel.isShowing()) {
            deleteBtn.setVisible(true);
            saveBtn.setVisible(false);
            saveprintBtn.setVisible(false);
            printBtn.setVisible(true);
        }
    }
    
    private void loadUserReports(int patientId){
        jTree1.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() == 2){
                    documentsDao.downloadFile();
                }
            }            
        });
        PatientBean patient = patientsDao.fetchPatientById(patientId);
        documentsDao.displayTree(patient, jTree1);
    }

    private void setFormFields() {
        PatientBean patient = patientsDao.fetchPatientById(patientId);
        String birthDay = patient.getBirthDate() != null ? displayDateFormat.format(patient.getBirthDate()) : "";
        patientName.setText(patient.getName());
        gender.setText(patient.getAge() + " / " + patient.getGender());
        mobileNo.setText(patient.getTelephone());
        address.setText(patient.getAddress());
        dob.setText(birthDay);
        casePaperNo.setText(patient.getCaseId());
        city.setText(patient.getCity());
        preMedicalHistory = patient.getPreMedicalHistory();
        deleteBtn.setVisible(false);
        loadTreatments();
    }

    private void loadTreatments() throws HeadlessException {
        List<TreatmentBean> treatments = treatmentDao.fetchTreatments();
        treatmentList.removeAll();
        for (TreatmentBean treatment : treatments) {
            treatmentList.addItem(treatment.getTreatmentName());
        }
    }

    private void loadHistory() {
        List<CheckupBean> checkups = checkUpDao.fetchPatientCheckup(patientId);
        DefaultTableModel data = (DefaultTableModel) treatmentHistoryTbl.getModel();
        data.setNumRows(0);

        for (CheckupBean checkup : checkups) {
            data.addRow(new Object[]{
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
        showPreMedicalHistory(patientId);
        this.repaint();
    }

    private void loadLabDetails(int checkupId) {
        LabWorkBean labWork = labDao.fetchLabWorkDetails(checkupId);
        if (labWork.getId() > 0) {
            labWorkHistoryLbl.setText(labWork.getWork());
            labHistoryLbl.setText(labWork.getLab().getName());
            labWorkStatusHistoryLbl.setText(getLabStatus(labWork.getStatus()));
            ulHistoryLbl.setText(labWork.getUl() + "  ");
            urHistoryLbl.setText("  " + labWork.getUr());
            llHistoryLbl.setText(labWork.getLl() + "  ");
            lrHistoryLbl.setText("  " + labWork.getLr());
        } else {
            labWorkHistoryLbl.setText("");
            labHistoryLbl.setText("");
            labWorkStatusHistoryLbl.setText("");
            ulHistoryLbl.setText("");
            urHistoryLbl.setText("");
            llHistoryLbl.setText("");
            lrHistoryLbl.setText("");
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

    private void loadDoctors() {
        List<DoctorBean> doctors = doctorDao.fetchDoctors();
        for (DoctorBean doctor : doctors) {
            this.doctorCB.addItem(doctor.getName());
        }
    }

    private void loadLabWork() {
        List<LabBean> labs = labDao.fetchLabs();
        this.labCB.addItem("Select");
        for (LabBean lab : labs) {
            this.labCB.addItem(lab.getName());
        }
    }

    private void loadLabWorkName() {
        List<LabWorkNameBean> labWorkNames = labDao.fetchLabWorkNames();
        this.labWorkCB.addItem("Select");
        for (LabWorkNameBean labWorkName : labWorkNames) {
            this.labWorkCB.addItem(labWorkName.getName());
        }
    }

    private void loadReferedBy() {
        List<ReferedByBean> referedBy = referedByDao.fetchReferedBy();
        for (ReferedByBean refered : referedBy) {
            reffByCB.addItem(refered.getName());
        }
    }

    private void loadMedicines() {
        List<MedicineBean> medicineBeans = medicineDao.fetchMedicines();
        medicines.add("");
        for (MedicineBean medicine : medicineBeans) {
            medicines.add(medicine.getName());
        }
    }

    private void loadMedicineTypes() {
        List<String> medicineTypes = medicineDao.fetchMedicineTypes();
        for (String medicineType1 : medicineTypes) {
            medicineType.add(medicineType1);
            addPriscriptionTypeCB.addItem(medicineType1);
        }
    }

    private boolean isConsultingDoctorSelected(String doctorName) {
        List<DoctorBean> doctors = doctorDao.fetchDoctors();
        for (DoctorBean doctor : doctors) {
            if (doctor.getName().equalsIgnoreCase(doctorName) && !doctor.getType().equalsIgnoreCase("MAIN")) {
                return true;
            }
        }
        return false;
    }

    private void loadPriscriptionHistory(int checkupId) {
        List<PriscriptionBean> priscriptions = patientsDao.fetchPriscription(checkupId);
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

    private void showPreMedicalHistory(int patientId) {
        JLabel preMedHistLabel[] = {pmHistoryLabel1, pmHistoryLabel2, pmHistoryLabel3, pmHistoryLabel4, pmHistoryLabel5};
        String preMedicalHistoryArray[];
        int arraySize = preMedHistLabel.length;
        if (preMedicalHistory != null) {
            preMedicalHistoryArray = preMedicalHistory.split(",");
            String preMedHistLblString[] = new String[arraySize];
            for (int i = 0; i < arraySize; i++) {
                preMedHistLblString[i] = "<html><ul style=\"padding:0px 10px 0px 10px;margin: 0px;\">";
            }
            int index = 1;
            for (String discription : preMedicalHistoryArray) {
                preMedHistLblString[index - 1] += "<li>" + discription + "</li>";
                if ((index % arraySize) == 0) {
                    index = 1;
                } else {
                    index++;
                }
            }
            for (int i = 0; i < arraySize; i++) {
                preMedHistLblString[i] += "</ul><html>";
                preMedHistLabel[i].setText(preMedHistLblString[i]);
            }
        }
    }

    private void deleteCheckup() {
        int selectedTreatmentRow = treatmentHistoryTbl.getSelectedRow();
        if (selectedTreatmentRow == -1) {
            JOptionPane.showMessageDialog(null, "Please Select Treatment to Delete !!", "Error!", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String selectedTreatmentId = treatmentHistoryTbl.getValueAt(selectedTreatmentRow, 6).toString();

        try {
            String confirmationMessage = "Really want to delete Treatment?";
            int chosenOption = JOptionPane.showConfirmDialog(null, confirmationMessage, "Delete", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, new javax.swing.ImageIcon(getClass().getResource("/EkDant/icones/Treatment Delete.png")));

            if (chosenOption == JOptionPane.YES_OPTION) {
                CheckupBean checkup = new CheckupBean();
                checkup.setCheckupId(Integer.parseInt(selectedTreatmentId));
                if (checkUpDao.deleteCheckup(checkup)) {
                    JOptionPane.showMessageDialog(null, "Treatment deleted successfully!", "Delete!", JOptionPane.INFORMATION_MESSAGE);
                }
                loadHistory();
            }
        } catch (HeadlessException errorSQL) {
            JOptionPane.showMessageDialog(null, "Error in deletion", "Error!", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException errorSQL) {
            JOptionPane.showMessageDialog(null, "Error in deletion", "Error!", JOptionPane.ERROR_MESSAGE);
        }
    }

    private int saveCheckup() {   
        int checkupId = 0;
        if(!labWorkCB.getSelectedItem().toString().equalsIgnoreCase("Select") && labCB.getSelectedItem().toString().equalsIgnoreCase("Select")){
            JOptionPane.showMessageDialog(null, "Please select Lab", "Error!", JOptionPane.ERROR_MESSAGE);
        }else{
            CheckupBean checkup = populateCheckup();
            checkupId = checkUpDao.addCheckup(checkup);
            patientTreatmentId = checkupId;
            
            savePriscription(patientTreatmentId);
            saveLabWork(patientTreatmentId);
            this.dispose();
        }
        return checkupId;
    }

    private CheckupBean populateCheckup() {
        CheckupBean checkup = new CheckupBean();
        String fee = fees.getText().length() == 0 ? "0" : fees.getText();
        String consultingDocFee = consultingDocFeeTxt.getText().length() == 0 ? "0" : consultingDocFeeTxt.getText();
        String refferedBy = reffByCB.getSelectedItem().toString().equalsIgnoreCase("select") ? "" : reffByCB.getSelectedItem().toString();
        Date date = new Date();
        if (treatmentDate.getDate() != null) {
            date = treatmentDate.getDate();
        }
        checkup.setPatientId(patientId);
        checkup.setTreatment(treatmentList.getSelectedItem().toString());
        checkup.setDignosis(dignosisSummery.getText());
        checkup.setFees(Float.parseFloat(fee));
        checkup.setConsultantingDoctorFee(Float.parseFloat(consultingDocFee));
        checkup.setRefferedBy(refferedBy);
        checkup.setDentistName(doctorCB.getSelectedItem().toString());
        checkup.setBp(bpText.getText());
        checkup.setPulse(pulseText.getText());
        checkup.setWeight(weightText.getText());
        checkup.setDate(date);
        checkup.setNextVisitDate(nextVisitDate.getDate());
        return checkup;
    }

    private int getLabId() {
        return labDao.fetchLabId(labCB.getSelectedItem().toString());
    }

    private void saveLabWork(int treatmentId) {
        if (!labWorkCB.getSelectedItem().toString().equalsIgnoreCase("Select")) {
            int labId = getLabId();
            LabWorkBean labWork = new LabWorkBean();
            labWork.setCheckupId(treatmentId);
            labWork.setPatientId(patientId);
            labWork.setLabId(labId);
            labWork.setWork(labWorkCB.getSelectedItem().toString());
            labWork.setLl(llTxt.getText());
            labWork.setLr(lrTxt.getText());
            labWork.setUl(ulTxt.getText());
            labWork.setUr(urTxt.getText());
            labWork.setStatus(1);
            labDao.addLabWork(labWork);
        }
    }
    
    private void savePrintPriscription() {
        int checkupId = saveCheckup();
        Priscription canvas = new Priscription(checkupId);
        canvas.printPriscription();
    }
    
    private void printPriscription(){
        int selectedTreatmentRow = treatmentHistoryTbl.getSelectedRow();
        if (selectedTreatmentRow == -1) {
            patientTreatmentId = 0;
            JOptionPane.showMessageDialog(null, "Please Select Treatment to print priscription !!", "Error!", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String selectedTreatmentId = treatmentHistoryTbl.getValueAt(selectedTreatmentRow, 6).toString();
        patientTreatmentId = Integer.parseInt(selectedTreatmentId);
        Priscription canvas = new Priscription(patientTreatmentId);
        canvas.printPriscription();
    }

    private void savePriscription(int treatmentId) {
        int totalRows = priscriptionNewTbl.getModel().getRowCount();
        if (priscriptionNewTbl.getCellEditor() != null) {
            priscriptionNewTbl.getCellEditor().stopCellEditing();
        }
        for (int row = 0; row < totalRows; row++) {
            PriscriptionBean priscription = new PriscriptionBean();
            priscription.setCheckupId(treatmentId);
            priscription.setMedicineType((String) priscriptionNewTbl.getValueAt(row, 0));
            priscription.setMedicineName((String) priscriptionNewTbl.getValueAt(row, 1));
            priscription.setMedicineStrength((String) priscriptionNewTbl.getValueAt(row, 2));
            priscription.setFrequency((String) priscriptionNewTbl.getValueAt(row, 3));
            priscription.setDuration((String) priscriptionNewTbl.getValueAt(row, 4));
            if (priscription.getMedicineType() != null && priscription.getMedicineType().length() > 0 && priscription.getMedicineName().length() > 0) {
                MedicineBean medicine = new MedicineBean();
                medicine.setName(priscription.getMedicineName());
                medicine.setType(priscription.getMedicineType());
                medicine.setStrength(priscription.getMedicineStrength());
                medicineDao.addMedicineIfNotPresent(medicine);
                patientsDao.persistPriscription(priscription);
            }
        }
    }

    private void doctorChanged() {
        if (isConsultingDoctorSelected(doctorCB.getSelectedItem().toString())) {
            consultingDocFeeLbl.setVisible(true);
            consultingDocFeeTxt.setVisible(true);
        } else {
            consultingDocFeeLbl.setVisible(false);
            consultingDocFeeTxt.setVisible(false);
        }
    }
    
    private void populateReferedBy() {
        referedByDao.populateReferedBy(reffByCB);
        if(newReferedByAdded){
            String newlyAddedCity = referedByDao.fetchLatestReferedBy();
            reffByCB.setSelectedItem(newlyAddedCity);
        }
    }
    
    private void addPriscriptionRow(){
        String newMedicineType = addPriscriptionTypeCB.getSelectedItem().toString();
        String medicineName = addPriscriptionMedicineNameTxt.getText();
        String medicineStrength = addPriscriptionMedicineStrengthTxt.getText();
        String medicineFrequency = addPriscriptionMedicineFrequencyCB.getSelectedItem().toString();
        String medicineDuration = addPriscriptionMedicineDurationTxt.getText();
        String medicineCondition = addPriscriptionMedicineConditionBox.isSelected() ? "BM" : "AM";
        if(!medicineName.isEmpty()){
            DefaultTableModel data = (DefaultTableModel) priscriptionNewTbl.getModel();
            data.addRow(new Object[]{
                newMedicineType,
                medicineName,
                medicineStrength,
                medicineFrequency +"  "+ medicineCondition,
                medicineDuration,
                ""
            });
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        patientDetailsPanel = new javax.swing.JPanel();
        nameLbl = new javax.swing.JLabel();
        patientName = new javax.swing.JLabel();
        casePaperLbl = new javax.swing.JLabel();
        casePaperNo = new javax.swing.JLabel();
        birthDayLbl = new javax.swing.JLabel();
        dob = new javax.swing.JLabel();
        addressLbl = new javax.swing.JLabel();
        address = new javax.swing.JLabel();
        mobileLbl = new javax.swing.JLabel();
        mobileNo = new javax.swing.JLabel();
        cityLbl = new javax.swing.JLabel();
        city = new javax.swing.JLabel();
        medicalHistoryHistPanel = new javax.swing.JPanel();
        pmHistoryLabel1 = new javax.swing.JLabel();
        pmHistoryLabel4 = new javax.swing.JLabel();
        pmHistoryLabel2 = new javax.swing.JLabel();
        pmHistoryLabel3 = new javax.swing.JLabel();
        pmHistoryLabel5 = new javax.swing.JLabel();
        preMedicalHistoryLbl = new javax.swing.JLabel();
        ageGenderLbl = new javax.swing.JLabel();
        gender = new javax.swing.JLabel();
        patientTreatmentPanel = new javax.swing.JTabbedPane();
        newTreatmentPanel = new javax.swing.JPanel();
        dignosisSummaryLbl = new javax.swing.JLabel();
        nextVisitDateLbl = new javax.swing.JLabel();
        feesLbl = new javax.swing.JLabel();
        fees = new javax.swing.JTextField();
        treatmentLbl = new javax.swing.JLabel();
        treatmentList = new javax.swing.JComboBox();
        doctorLbl = new javax.swing.JLabel();
        doctorCB = new javax.swing.JComboBox();
        reffByLbl = new javax.swing.JLabel();
        reffByCB = new javax.swing.JComboBox();
        consultingDocFeeLbl = new javax.swing.JLabel();
        consultingDocFeeTxt = new javax.swing.JTextField();
        dignosisSummery = new javax.swing.JTextField();
        priscriptionLbl = new javax.swing.JLabel();
        bpLabel = new javax.swing.JLabel();
        bpText = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        pulseText = new javax.swing.JTextField();
        weightLabel = new javax.swing.JLabel();
        weightText = new javax.swing.JTextField();
        newReferedByBtn = new javax.swing.JButton();
        newTreatmentBtn = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTree1 = new javax.swing.JTree();
        jPanel5 = new javax.swing.JPanel();
        labWorkLbl = new javax.swing.JLabel();
        labWorkCB = new javax.swing.JComboBox();
        labLbl = new javax.swing.JLabel();
        labCB = new javax.swing.JComboBox();
        jPanel1 = new javax.swing.JPanel();
        ulTxt = new javax.swing.JTextField();
        urTxt = new javax.swing.JTextField();
        llTxt = new javax.swing.JTextField();
        lrTxt = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        priscriptionNewTbl = new javax.swing.JTable();
        addPriscriptionTypeCB = new javax.swing.JComboBox();
        addPriscriptionMedicineNameTxt = new Java2sAutoTextField(medicines);
        addPriscriptionBtn = new javax.swing.JButton();
        addPriscriptionMedicineStrengthTxt = new javax.swing.JTextField();
        addPriscriptionMedicineFrequencyCB = new javax.swing.JComboBox();
        addPriscriptionMedicineConditionBox = new javax.swing.JCheckBox();
        addPriscriptionMedicineDurationTxt = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        nextVisitDate = new com.toedter.calendar.JDateChooser();
        treatmentDate = new com.toedter.calendar.JDateChooser();
        treatmentHistoryPanel = new javax.swing.JPanel();
        treatmentHistoryScrollPane = new javax.swing.JScrollPane();
        treatmentHistoryTbl = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        priscriptionHistoryTbl = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        labWorkHistoryLbl = new javax.swing.JLabel();
        labHistoryLbl = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        ulHistoryLbl = new javax.swing.JLabel();
        urHistoryLbl = new javax.swing.JLabel();
        llHistoryLbl = new javax.swing.JLabel();
        lrHistoryLbl = new javax.swing.JLabel();
        labWorkStatusHistoryLbl = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        bpHistLabel = new javax.swing.JLabel();
        weightHistLabel = new javax.swing.JLabel();
        pulseHistLabel = new javax.swing.JLabel();
        buttonPanel = new javax.swing.JPanel();
        saveBtn = new javax.swing.JButton();
        cancelBtn = new javax.swing.JButton();
        deleteBtn = new javax.swing.JButton();
        saveprintBtn = new javax.swing.JButton();
        printBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Patient Check Up");
        setName("Treatment"); // NOI18N
        setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width,Toolkit.getDefaultToolkit().getScreenSize().height-Toolkit.getDefaultToolkit().getScreenInsets(getGraphicsConfiguration()).bottom));
        addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                formWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
            }
        });

        patientDetailsPanel.setBackground(new java.awt.Color(204, 204, 255));

        nameLbl.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        nameLbl.setText("Name: ");

        casePaperLbl.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        casePaperLbl.setText("Case Paper :");

        birthDayLbl.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        birthDayLbl.setText(" Date of birth:");
        birthDayLbl.setAlignmentY(0.0F);

        addressLbl.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        addressLbl.setText("Adress: ");

        mobileLbl.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        mobileLbl.setText("Mobile: ");

        cityLbl.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        cityLbl.setText("City:");

        medicalHistoryHistPanel.setBackground(new java.awt.Color(204, 204, 255));
        medicalHistoryHistPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        pmHistoryLabel1.setBackground(new java.awt.Color(204, 204, 255));
        pmHistoryLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        pmHistoryLabel1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        pmHistoryLabel1.setAlignmentY(0.0F);
        pmHistoryLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        pmHistoryLabel1.setVerticalTextPosition(javax.swing.SwingConstants.TOP);

        pmHistoryLabel4.setBackground(new java.awt.Color(204, 204, 255));
        pmHistoryLabel4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        pmHistoryLabel4.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        pmHistoryLabel4.setAlignmentY(0.0F);
        pmHistoryLabel4.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        pmHistoryLabel4.setVerticalTextPosition(javax.swing.SwingConstants.TOP);

        pmHistoryLabel2.setBackground(new java.awt.Color(204, 204, 255));
        pmHistoryLabel2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        pmHistoryLabel2.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        pmHistoryLabel2.setAlignmentY(0.0F);
        pmHistoryLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        pmHistoryLabel2.setVerticalTextPosition(javax.swing.SwingConstants.TOP);

        pmHistoryLabel3.setBackground(new java.awt.Color(204, 204, 255));
        pmHistoryLabel3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        pmHistoryLabel3.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        pmHistoryLabel3.setAlignmentY(0.0F);
        pmHistoryLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        pmHistoryLabel3.setVerticalTextPosition(javax.swing.SwingConstants.TOP);

        pmHistoryLabel5.setBackground(new java.awt.Color(204, 204, 255));
        pmHistoryLabel5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        pmHistoryLabel5.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        pmHistoryLabel5.setAlignmentY(0.0F);
        pmHistoryLabel5.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        pmHistoryLabel5.setVerticalTextPosition(javax.swing.SwingConstants.TOP);

        javax.swing.GroupLayout medicalHistoryHistPanelLayout = new javax.swing.GroupLayout(medicalHistoryHistPanel);
        medicalHistoryHistPanel.setLayout(medicalHistoryHistPanelLayout);
        medicalHistoryHistPanelLayout.setHorizontalGroup(
            medicalHistoryHistPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, medicalHistoryHistPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(pmHistoryLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(pmHistoryLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pmHistoryLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pmHistoryLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pmHistoryLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        medicalHistoryHistPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {pmHistoryLabel1, pmHistoryLabel2, pmHistoryLabel3, pmHistoryLabel4, pmHistoryLabel5});

        medicalHistoryHistPanelLayout.setVerticalGroup(
            medicalHistoryHistPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, medicalHistoryHistPanelLayout.createSequentialGroup()
                .addGroup(medicalHistoryHistPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(pmHistoryLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pmHistoryLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pmHistoryLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pmHistoryLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pmHistoryLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0))
        );

        medicalHistoryHistPanelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {pmHistoryLabel1, pmHistoryLabel2, pmHistoryLabel3, pmHistoryLabel4, pmHistoryLabel5});

        preMedicalHistoryLbl.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        preMedicalHistoryLbl.setText("Pre Medical History:");
        preMedicalHistoryLbl.setToolTipText("");

        ageGenderLbl.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ageGenderLbl.setText("Age/Sex: ");

        javax.swing.GroupLayout patientDetailsPanelLayout = new javax.swing.GroupLayout(patientDetailsPanel);
        patientDetailsPanel.setLayout(patientDetailsPanelLayout);
        patientDetailsPanelLayout.setHorizontalGroup(
            patientDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(patientDetailsPanelLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(patientDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(patientDetailsPanelLayout.createSequentialGroup()
                        .addGroup(patientDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(casePaperLbl, javax.swing.GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE)
                            .addComponent(nameLbl, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(patientDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(casePaperNo, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                            .addComponent(patientName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(patientDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(mobileLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(addressLbl, javax.swing.GroupLayout.DEFAULT_SIZE, 57, Short.MAX_VALUE))
                        .addGap(9, 9, 9)
                        .addGroup(patientDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(patientDetailsPanelLayout.createSequentialGroup()
                                .addComponent(mobileNo, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(ageGenderLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(gender, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(address, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(patientDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cityLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(birthDayLbl, javax.swing.GroupLayout.DEFAULT_SIZE, 89, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(patientDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(dob, javax.swing.GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE)
                            .addComponent(city, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(patientDetailsPanelLayout.createSequentialGroup()
                        .addComponent(preMedicalHistoryLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(medicalHistoryHistPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 1086, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );

        patientDetailsPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {casePaperLbl, nameLbl});

        patientDetailsPanelLayout.setVerticalGroup(
            patientDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(patientDetailsPanelLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(patientDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(patientDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(casePaperLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(casePaperNo, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(mobileLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(patientDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(birthDayLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ageGenderLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(mobileNo, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(dob, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(gender, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(patientDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nameLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(patientName, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(patientDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(address, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(addressLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(patientDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cityLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(city, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                .addGroup(patientDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(medicalHistoryHistPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(preMedicalHistoryLbl))
                .addGap(0, 0, 0))
        );

        patientDetailsPanelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {address, addressLbl, ageGenderLbl, birthDayLbl, casePaperLbl, casePaperNo, city, cityLbl, dob, gender, mobileLbl, mobileNo, nameLbl, patientName, preMedicalHistoryLbl});

        patientTreatmentPanel.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        patientTreatmentPanel.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                patientTreatmentPanelStateChanged(evt);
            }
        });

        newTreatmentPanel.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        newTreatmentPanel.setPreferredSize(new java.awt.Dimension(1274, 420));

        dignosisSummaryLbl.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        dignosisSummaryLbl.setForeground(new java.awt.Color(51, 51, 255));
        dignosisSummaryLbl.setText("Dignosis Summery");

        nextVisitDateLbl.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        nextVisitDateLbl.setForeground(new java.awt.Color(51, 51, 255));
        nextVisitDateLbl.setText("Next Visit Date");

        feesLbl.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        feesLbl.setForeground(new java.awt.Color(51, 51, 255));
        feesLbl.setText("Fees");

        fees.setMinimumSize(new java.awt.Dimension(6, 24));
        fees.setNextFocusableComponent(consultingDocFeeTxt);
        fees.setPreferredSize(new java.awt.Dimension(6, 24));

        treatmentLbl.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        treatmentLbl.setForeground(new java.awt.Color(51, 51, 255));
        treatmentLbl.setText("Treatment ");

        treatmentList.setNextFocusableComponent(dignosisSummery);

        doctorLbl.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        doctorLbl.setForeground(new java.awt.Color(51, 51, 255));
        doctorLbl.setText("Doctor");

        doctorCB.setNextFocusableComponent(reffByCB);
        doctorCB.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                doctorCBItemStateChanged(evt);
            }
        });

        reffByLbl.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        reffByLbl.setForeground(new java.awt.Color(51, 51, 255));
        reffByLbl.setText("Reffered By");

        reffByCB.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "select" }));
        reffByCB.setNextFocusableComponent(treatmentList);

        consultingDocFeeLbl.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        consultingDocFeeLbl.setForeground(new java.awt.Color(51, 51, 255));
        consultingDocFeeLbl.setText("Consulting Doctor Fees");

        consultingDocFeeTxt.setNextFocusableComponent(saveBtn);

        dignosisSummery.setNextFocusableComponent(priscriptionNewTbl);

        priscriptionLbl.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        priscriptionLbl.setForeground(new java.awt.Color(51, 51, 255));
        priscriptionLbl.setText("Priscription");

        bpLabel.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        bpLabel.setForeground(new java.awt.Color(51, 51, 255));
        bpLabel.setText("BP S/D:");

        bpText.setNextFocusableComponent(pulseText);

        jLabel1.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 255));
        jLabel1.setText("Pulse:");

        pulseText.setNextFocusableComponent(dignosisSummery);

        weightLabel.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        weightLabel.setForeground(new java.awt.Color(51, 51, 255));
        weightLabel.setText("Weight");
        weightLabel.setToolTipText("");

        weightText.setNextFocusableComponent(bpText);

        newReferedByBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/EkDant/icones/plus-icon.png"))); // NOI18N
        newReferedByBtn.setFocusable(false);
        newReferedByBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newReferedByBtnActionPerformed(evt);
            }
        });

        newTreatmentBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/EkDant/icones/plus-icon.png"))); // NOI18N
        newTreatmentBtn.setFocusable(false);
        newTreatmentBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newTreatmentBtnActionPerformed(evt);
            }
        });

        jScrollPane3.setBorder(javax.swing.BorderFactory.createTitledBorder("Documents"));

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("root");
        jTree1.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        jTree1.setOpaque(false);
        jScrollPane3.setViewportView(jTree1);

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Lab Work"));

        labWorkLbl.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        labWorkLbl.setForeground(new java.awt.Color(51, 51, 255));
        labWorkLbl.setText("Lab Work");

        labWorkCB.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                labWorkCBItemStateChanged(evt);
            }
        });

        labLbl.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        labLbl.setForeground(new java.awt.Color(51, 51, 255));
        labLbl.setText("Lab");

        jPanel1.setBackground(new java.awt.Color(102, 102, 102));

        ulTxt.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        llTxt.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(ulTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(urTxt))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(llTxt)
                        .addGap(0, 0, 0)
                        .addComponent(lrTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {llTxt, lrTxt, ulTxt, urTxt});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ulTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(urTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(llTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lrTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {llTxt, lrTxt, ulTxt, urTxt});

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(labLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(labWorkLbl, javax.swing.GroupLayout.DEFAULT_SIZE, 63, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labWorkCB, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labCB, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel5Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {labCB, labWorkCB});

        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labWorkCB, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(labWorkLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(labCB, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(labLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {labCB, labLbl, labWorkCB, labWorkLbl});

        priscriptionNewTbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Type", "Name", "Strength", "Frequency", "Duration", ""
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        priscriptionNewTbl.setNextFocusableComponent(fees);
        priscriptionNewTbl.setRowHeight(25);
        jScrollPane2.setViewportView(priscriptionNewTbl);
        if (priscriptionNewTbl.getColumnModel().getColumnCount() > 0) {
            priscriptionNewTbl.getColumnModel().getColumn(0).setPreferredWidth(50);
            priscriptionNewTbl.getColumnModel().getColumn(1).setPreferredWidth(150);
            priscriptionNewTbl.getColumnModel().getColumn(5).setPreferredWidth(20);
        }

        addPriscriptionBtn.setText("Add");
        addPriscriptionBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addPriscriptionBtnActionPerformed(evt);
            }
        });

        addPriscriptionMedicineFrequencyCB.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0   -   0   -   1", "1   -   0   -   0", "1   -   0   -   1", "1   -   1   -   1", "0   -   1   -   0", "0   -   1   -   1", "1   -   1   -   0" }));

        addPriscriptionMedicineConditionBox.setText("BM");

        addPriscriptionMedicineDurationTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addPriscriptionMedicineDurationTxtActionPerformed(evt);
            }
        });

        jLabel2.setText("Days");

        javax.swing.GroupLayout newTreatmentPanelLayout = new javax.swing.GroupLayout(newTreatmentPanel);
        newTreatmentPanel.setLayout(newTreatmentPanelLayout);
        newTreatmentPanelLayout.setHorizontalGroup(
            newTreatmentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(newTreatmentPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(newTreatmentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dignosisSummaryLbl, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
                    .addComponent(treatmentLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(doctorLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(priscriptionLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(nextVisitDateLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(newTreatmentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(newTreatmentPanelLayout.createSequentialGroup()
                        .addComponent(nextVisitDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(138, 138, 138)
                        .addComponent(feesLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(fees, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(consultingDocFeeLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(consultingDocFeeTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(newTreatmentPanelLayout.createSequentialGroup()
                        .addComponent(addPriscriptionTypeCB, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(addPriscriptionMedicineNameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(addPriscriptionMedicineStrengthTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(addPriscriptionMedicineFrequencyCB, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(addPriscriptionMedicineConditionBox)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(addPriscriptionMedicineDurationTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(53, 53, 53)
                        .addComponent(addPriscriptionBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 57, Short.MAX_VALUE))
                    .addComponent(dignosisSummery)
                    .addGroup(newTreatmentPanelLayout.createSequentialGroup()
                        .addGroup(newTreatmentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(doctorCB, 0, 286, Short.MAX_VALUE)
                            .addComponent(treatmentList, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(newTreatmentBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(47, 47, 47)
                        .addGroup(newTreatmentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(newTreatmentPanelLayout.createSequentialGroup()
                                .addComponent(weightLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(weightText, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(50, 50, 50)
                                .addComponent(bpLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(bpText, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(newTreatmentPanelLayout.createSequentialGroup()
                                .addComponent(reffByLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(reffByCB, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(newTreatmentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(newTreatmentPanelLayout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(pulseText, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(newTreatmentPanelLayout.createSequentialGroup()
                                .addComponent(newReferedByBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(treatmentDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jScrollPane2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(newTreatmentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 297, Short.MAX_VALUE)
                    .addComponent(jScrollPane3))
                .addContainerGap())
        );

        newTreatmentPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {dignosisSummaryLbl, doctorLbl, feesLbl, nextVisitDateLbl, priscriptionLbl, treatmentLbl});

        newTreatmentPanelLayout.setVerticalGroup(
            newTreatmentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(newTreatmentPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(newTreatmentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(newTreatmentPanelLayout.createSequentialGroup()
                        .addGroup(newTreatmentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(newTreatmentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(doctorLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(doctorCB, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(reffByLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(reffByCB, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, newTreatmentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(treatmentDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(newReferedByBtn)))
                        .addGap(18, 18, 18)
                        .addGroup(newTreatmentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(newTreatmentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(treatmentLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(treatmentList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, newTreatmentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(weightLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(weightText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(bpText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel1)
                                .addComponent(pulseText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(bpLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(newTreatmentBtn, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(18, 18, 18)
                        .addGroup(newTreatmentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(dignosisSummaryLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(dignosisSummery, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(newTreatmentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(addPriscriptionBtn)
                            .addComponent(addPriscriptionMedicineNameTxt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(newTreatmentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(addPriscriptionTypeCB, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(priscriptionLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(addPriscriptionMedicineStrengthTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(addPriscriptionMedicineFrequencyCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(addPriscriptionMedicineConditionBox)
                                .addComponent(addPriscriptionMedicineDurationTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel2)))
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, newTreatmentPanelLayout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(newTreatmentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(newTreatmentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(nextVisitDateLbl, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(consultingDocFeeTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(newTreatmentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(fees, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(feesLbl)
                            .addComponent(consultingDocFeeLbl)))
                    .addComponent(nextVisitDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        newTreatmentPanelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {consultingDocFeeLbl, dignosisSummaryLbl, doctorLbl, feesLbl, jLabel1, nextVisitDateLbl, priscriptionLbl, reffByLbl, treatmentLbl, weightLabel});

        newTreatmentPanelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {addPriscriptionBtn, addPriscriptionMedicineConditionBox, addPriscriptionMedicineDurationTxt, addPriscriptionMedicineFrequencyCB, addPriscriptionMedicineNameTxt, addPriscriptionMedicineStrengthTxt, addPriscriptionTypeCB, bpText, consultingDocFeeTxt, dignosisSummery, doctorCB, fees, jLabel2, newReferedByBtn, newTreatmentBtn, pulseText, reffByCB, treatmentList, weightText});

        patientTreatmentPanel.addTab("Treatment", new javax.swing.ImageIcon(getClass().getResource("/EkDant/icones/Treatment Add.png")), newTreatmentPanel); // NOI18N

        treatmentHistoryTbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Date", "Doctor", "Treatment", "Dignosis", "Fees", "Consulting Docor Fees", "checkupId"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Float.class, java.lang.Float.class, java.lang.Integer.class
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
        treatmentHistoryScrollPane.setViewportView(treatmentHistoryTbl);
        if (treatmentHistoryTbl.getColumnModel().getColumnCount() > 0) {
            treatmentHistoryTbl.getColumnModel().getColumn(0).setPreferredWidth(25);
            treatmentHistoryTbl.getColumnModel().getColumn(4).setPreferredWidth(15);
            treatmentHistoryTbl.getColumnModel().getColumn(5).setPreferredWidth(45);
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
        if (priscriptionHistoryTbl.getColumnModel().getColumnCount() > 0) {
            priscriptionHistoryTbl.getColumnModel().getColumn(0).setPreferredWidth(50);
            priscriptionHistoryTbl.getColumnModel().getColumn(1).setPreferredWidth(150);
            priscriptionHistoryTbl.getColumnModel().getColumn(2).setPreferredWidth(30);
            priscriptionHistoryTbl.getColumnModel().getColumn(3).setPreferredWidth(50);
            priscriptionHistoryTbl.getColumnModel().getColumn(4).setPreferredWidth(30);
        }

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Lab Work", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Verdana", 0, 12), new java.awt.Color(51, 51, 255))); // NOI18N

        labWorkHistoryLbl.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        labWorkHistoryLbl.setForeground(new java.awt.Color(51, 51, 255));

        labHistoryLbl.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        labHistoryLbl.setForeground(new java.awt.Color(51, 51, 255));

        jPanel3.setBackground(new java.awt.Color(51, 51, 255));

        ulHistoryLbl.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        ulHistoryLbl.setForeground(new java.awt.Color(51, 51, 255));
        ulHistoryLbl.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        ulHistoryLbl.setOpaque(true);

        urHistoryLbl.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        urHistoryLbl.setForeground(new java.awt.Color(51, 51, 255));
        urHistoryLbl.setOpaque(true);

        llHistoryLbl.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        llHistoryLbl.setForeground(new java.awt.Color(51, 51, 255));
        llHistoryLbl.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        llHistoryLbl.setOpaque(true);

        lrHistoryLbl.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        lrHistoryLbl.setForeground(new java.awt.Color(51, 51, 255));
        lrHistoryLbl.setOpaque(true);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ulHistoryLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(llHistoryLbl))
                .addGap(1, 1, 1)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lrHistoryLbl)
                    .addComponent(urHistoryLbl))
                .addGap(0, 0, 0))
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {llHistoryLbl, lrHistoryLbl, ulHistoryLbl, urHistoryLbl});

        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ulHistoryLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(urHistoryLbl))
                .addGap(1, 1, 1)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(llHistoryLbl)
                    .addComponent(lrHistoryLbl))
                .addGap(0, 0, 0))
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {llHistoryLbl, lrHistoryLbl, ulHistoryLbl, urHistoryLbl});

        labWorkStatusHistoryLbl.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        labWorkStatusHistoryLbl.setForeground(new java.awt.Color(51, 51, 255));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labWorkStatusHistoryLbl)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labHistoryLbl)
                            .addComponent(labWorkHistoryLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(30, Short.MAX_VALUE))
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {labHistoryLbl, labWorkHistoryLbl, labWorkStatusHistoryLbl});

        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(labWorkHistoryLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(labHistoryLbl))
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(labWorkStatusHistoryLbl)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {labHistoryLbl, labWorkHistoryLbl, labWorkStatusHistoryLbl});

        bpHistLabel.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        bpHistLabel.setForeground(new java.awt.Color(51, 51, 255));

        weightHistLabel.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        weightHistLabel.setForeground(new java.awt.Color(51, 51, 255));

        pulseHistLabel.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        pulseHistLabel.setForeground(new java.awt.Color(51, 51, 255));
        pulseHistLabel.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pulseHistLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bpHistLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(weightHistLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel4Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {bpHistLabel, pulseHistLabel, weightHistLabel});

        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(weightHistLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(bpHistLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(pulseHistLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel4Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {bpHistLabel, pulseHistLabel, weightHistLabel});

        javax.swing.GroupLayout treatmentHistoryPanelLayout = new javax.swing.GroupLayout(treatmentHistoryPanel);
        treatmentHistoryPanel.setLayout(treatmentHistoryPanelLayout);
        treatmentHistoryPanelLayout.setHorizontalGroup(
            treatmentHistoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(treatmentHistoryPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(treatmentHistoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(treatmentHistoryScrollPane)
                    .addGroup(treatmentHistoryPanelLayout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 690, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        treatmentHistoryPanelLayout.setVerticalGroup(
            treatmentHistoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(treatmentHistoryPanelLayout.createSequentialGroup()
                .addComponent(treatmentHistoryScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(treatmentHistoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(treatmentHistoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(169, Short.MAX_VALUE))
        );

        patientTreatmentPanel.addTab("History", new javax.swing.ImageIcon(getClass().getResource("/EkDant/icones/Treatment Plan.png")), treatmentHistoryPanel); // NOI18N

        saveBtn.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        saveBtn.setForeground(new java.awt.Color(51, 51, 255));
        saveBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/EkDant/icones/Treatment Completed.png"))); // NOI18N
        saveBtn.setMnemonic('S');
        saveBtn.setText("Save");
        saveBtn.setNextFocusableComponent(cancelBtn);
        saveBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveBtnActionPerformed(evt);
            }
        });

        cancelBtn.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        cancelBtn.setForeground(new java.awt.Color(51, 51, 255));
        cancelBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/EkDant/icones/Treatment Delete.png"))); // NOI18N
        cancelBtn.setMnemonic('C');
        cancelBtn.setText("Cancel");
        cancelBtn.setPreferredSize(new java.awt.Dimension(91, 33));
        cancelBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelBtnActionPerformed(evt);
            }
        });

        deleteBtn.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        deleteBtn.setForeground(new java.awt.Color(51, 51, 255));
        deleteBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/EkDant/icones/Treatment Delete.png"))); // NOI18N
        deleteBtn.setMnemonic('D');
        deleteBtn.setText("Delete");
        deleteBtn.setNextFocusableComponent(cancelBtn);
        deleteBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteBtnActionPerformed(evt);
            }
        });

        saveprintBtn.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        saveprintBtn.setForeground(new java.awt.Color(51, 51, 255));
        saveprintBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/EkDant/icones/Print.png"))); // NOI18N
        saveprintBtn.setText("Save and Print Priscription");
        saveprintBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveprintBtnActionPerformed(evt);
            }
        });

        printBtn.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        printBtn.setForeground(new java.awt.Color(51, 51, 255));
        printBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/EkDant/icones/Print.png"))); // NOI18N
        printBtn.setText("Print Priscription");
        printBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout buttonPanelLayout = new javax.swing.GroupLayout(buttonPanel);
        buttonPanel.setLayout(buttonPanelLayout);
        buttonPanelLayout.setHorizontalGroup(
            buttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buttonPanelLayout.createSequentialGroup()
                .addGap(161, 161, 161)
                .addComponent(saveBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(deleteBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(cancelBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(saveprintBtn)
                .addGap(18, 18, 18)
                .addComponent(printBtn)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        buttonPanelLayout.setVerticalGroup(
            buttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, buttonPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(buttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(saveBtn)
                    .addComponent(cancelBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(deleteBtn))
                .addContainerGap())
            .addGroup(buttonPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(buttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(saveprintBtn)
                    .addComponent(printBtn))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        buttonPanelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {cancelBtn, saveprintBtn});

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(buttonPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(patientTreatmentPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 1327, Short.MAX_VALUE)
            .addComponent(patientDetailsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(patientDetailsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(patientTreatmentPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 498, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        patientTreatmentPanel.getAccessibleContext().setAccessibleName("chekUp");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void saveBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveBtnActionPerformed
        saveCheckup();
    }//GEN-LAST:event_saveBtnActionPerformed

    private void cancelBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelBtnActionPerformed
        this.dispose();
    }//GEN-LAST:event_cancelBtnActionPerformed

    private void doctorCBItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_doctorCBItemStateChanged
        doctorChanged();
    }//GEN-LAST:event_doctorCBItemStateChanged

    private void deleteBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteBtnActionPerformed
        deleteCheckup();
    }//GEN-LAST:event_deleteBtnActionPerformed

    private void newReferedByBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newReferedByBtnActionPerformed
        newReferedByAdded = true;
        new ReferedBy(true).setVisible(true);
    }//GEN-LAST:event_newReferedByBtnActionPerformed

    private void patientTreatmentPanelStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_patientTreatmentPanelStateChanged
        togglePanel();
    }//GEN-LAST:event_patientTreatmentPanelStateChanged

    private void labWorkCBItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_labWorkCBItemStateChanged
        
    }//GEN-LAST:event_labWorkCBItemStateChanged

    private void saveprintBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveprintBtnActionPerformed
        savePrintPriscription();
    }//GEN-LAST:event_saveprintBtnActionPerformed

    private void formWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowGainedFocus
        populateReferedBy();
        loadTreatments();
    }//GEN-LAST:event_formWindowGainedFocus

    private void newTreatmentBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newTreatmentBtnActionPerformed
        new Treatment(true).setVisible(true);
        populateReferedBy();
    }//GEN-LAST:event_newTreatmentBtnActionPerformed

    private void printBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printBtnActionPerformed
        printPriscription();
    }//GEN-LAST:event_printBtnActionPerformed

    private void addPriscriptionBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addPriscriptionBtnActionPerformed
        addPriscriptionRow();
    }//GEN-LAST:event_addPriscriptionBtnActionPerformed

    private void addPriscriptionMedicineDurationTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addPriscriptionMedicineDurationTxtActionPerformed
        addPriscriptionRow();
    }//GEN-LAST:event_addPriscriptionMedicineDurationTxtActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addPriscriptionBtn;
    private javax.swing.JCheckBox addPriscriptionMedicineConditionBox;
    private javax.swing.JTextField addPriscriptionMedicineDurationTxt;
    private javax.swing.JComboBox addPriscriptionMedicineFrequencyCB;
    private javax.swing.JTextField addPriscriptionMedicineNameTxt;
    private javax.swing.JTextField addPriscriptionMedicineStrengthTxt;
    private javax.swing.JComboBox addPriscriptionTypeCB;
    private javax.swing.JLabel address;
    private javax.swing.JLabel addressLbl;
    private javax.swing.JLabel ageGenderLbl;
    private javax.swing.JLabel birthDayLbl;
    private javax.swing.JLabel bpHistLabel;
    private javax.swing.JLabel bpLabel;
    private javax.swing.JTextField bpText;
    private javax.swing.JPanel buttonPanel;
    private javax.swing.JButton cancelBtn;
    private javax.swing.JLabel casePaperLbl;
    private javax.swing.JLabel casePaperNo;
    private javax.swing.JLabel city;
    private javax.swing.JLabel cityLbl;
    private javax.swing.JLabel consultingDocFeeLbl;
    private javax.swing.JTextField consultingDocFeeTxt;
    private javax.swing.JButton deleteBtn;
    private javax.swing.JLabel dignosisSummaryLbl;
    private javax.swing.JTextField dignosisSummery;
    private javax.swing.JLabel dob;
    private javax.swing.JComboBox doctorCB;
    private javax.swing.JLabel doctorLbl;
    private javax.swing.JTextField fees;
    private javax.swing.JLabel feesLbl;
    private javax.swing.JLabel gender;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTree jTree1;
    private javax.swing.JComboBox labCB;
    private javax.swing.JLabel labHistoryLbl;
    private javax.swing.JLabel labLbl;
    private javax.swing.JComboBox labWorkCB;
    private javax.swing.JLabel labWorkHistoryLbl;
    private javax.swing.JLabel labWorkLbl;
    private javax.swing.JLabel labWorkStatusHistoryLbl;
    private javax.swing.JLabel llHistoryLbl;
    private javax.swing.JTextField llTxt;
    private javax.swing.JLabel lrHistoryLbl;
    private javax.swing.JTextField lrTxt;
    private javax.swing.JPanel medicalHistoryHistPanel;
    private javax.swing.JLabel mobileLbl;
    private javax.swing.JLabel mobileNo;
    private javax.swing.JLabel nameLbl;
    private javax.swing.JButton newReferedByBtn;
    private javax.swing.JButton newTreatmentBtn;
    private javax.swing.JPanel newTreatmentPanel;
    private com.toedter.calendar.JDateChooser nextVisitDate;
    private javax.swing.JLabel nextVisitDateLbl;
    private javax.swing.JPanel patientDetailsPanel;
    private javax.swing.JLabel patientName;
    private javax.swing.JTabbedPane patientTreatmentPanel;
    private javax.swing.JLabel pmHistoryLabel1;
    private javax.swing.JLabel pmHistoryLabel2;
    private javax.swing.JLabel pmHistoryLabel3;
    private javax.swing.JLabel pmHistoryLabel4;
    private javax.swing.JLabel pmHistoryLabel5;
    private javax.swing.JLabel preMedicalHistoryLbl;
    private javax.swing.JButton printBtn;
    private javax.swing.JTable priscriptionHistoryTbl;
    private javax.swing.JLabel priscriptionLbl;
    private javax.swing.JTable priscriptionNewTbl;
    private javax.swing.JLabel pulseHistLabel;
    private javax.swing.JTextField pulseText;
    private javax.swing.JComboBox reffByCB;
    private javax.swing.JLabel reffByLbl;
    private javax.swing.JButton saveBtn;
    private javax.swing.JButton saveprintBtn;
    private com.toedter.calendar.JDateChooser treatmentDate;
    private javax.swing.JPanel treatmentHistoryPanel;
    private javax.swing.JScrollPane treatmentHistoryScrollPane;
    private javax.swing.JTable treatmentHistoryTbl;
    private javax.swing.JLabel treatmentLbl;
    private javax.swing.JComboBox treatmentList;
    private javax.swing.JLabel ulHistoryLbl;
    private javax.swing.JTextField ulTxt;
    private javax.swing.JLabel urHistoryLbl;
    private javax.swing.JTextField urTxt;
    private javax.swing.JLabel weightHistLabel;
    private javax.swing.JLabel weightLabel;
    private javax.swing.JTextField weightText;
    // End of variables declaration//GEN-END:variables
}
