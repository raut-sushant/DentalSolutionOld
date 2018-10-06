/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ekdant.dentalsolution.reports;

import com.ekdant.dentalsolution.dao.CheckUpDao;
import com.ekdant.dentalsolution.dao.PatientsDao;
import com.ekdant.dentalsolution.dao.ReferedByDao;
import com.ekdant.dentalsolution.dao.ReportsDao;
import com.ekdant.dentalsolution.dao.TreatmentDao;
import com.ekdant.dentalsolution.domain.CheckupBean;
import com.ekdant.dentalsolution.domain.MonthlyReportBean;
import com.ekdant.dentalsolution.domain.ReferedByBean;
import com.ekdant.dentalsolution.domain.ReferedByReportBean;
import com.ekdant.dentalsolution.domain.TreatmentBean;
import com.ekdant.dentalsolution.domain.TreatmentReportBean;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Sushant
 */
public class Reports extends javax.swing.JFrame {

    /**
     * Creates new form Reports
     */
    PatientsDao patientsDao;
    CheckUpDao checkUpDao;
    TreatmentDao treatmentDao;
    ReportsDao reportsDao;
    ReferedByDao referedByDao;
    DateFormat displayDateFormat = new SimpleDateFormat("d MMM yyyy");
    DateFormat databaseDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    DateFormat inputDateFormat = new SimpleDateFormat("MMM d, yyyy");
    String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

    public Reports() {
        patientsDao = new PatientsDao();
        checkUpDao = new CheckUpDao();
        treatmentDao = new TreatmentDao();
        reportsDao = new ReportsDao();
        referedByDao = new ReferedByDao();
        initComponents();        
    }

    private void dateReport() throws HeadlessException {

        List<CheckupBean> checkups = checkUpDao.fetchCheckup(dateCB.getDate());
        DefaultTableModel model = (DefaultTableModel) dailyReportTable.getModel();

        model.setNumRows(0);

        for (CheckupBean checkup : checkups) {
            model.addRow(new Object[]{
                checkup.getPatient().getCaseId(),
                checkup.getPatient().getName(),
                checkup.getTreatment(),
                checkup.getFees(),
                checkup.getConsultantingDoctorFee()
            });
        }
        model.addRow(new Object[]{"<html><b>Total</b></html>", "", "", getTotalFees(checkups), getTotalConsultingFees(checkups)});
    }

    private float getTotalFees(List<CheckupBean> checkups) {
        float fees = 0;
        for (CheckupBean checkup : checkups) {
            fees += checkup.getFees();
        }
        return fees;
    }

    private float getTotalConsultingFees(List<CheckupBean> checkups) {
        float consultingFees = 0;
        for (CheckupBean checkup : checkups) {
            consultingFees += checkup.getConsultantingDoctorFee();
        }
        return consultingFees;
    }

    private void loadPIChart() {
        int totalRows = yearReportTable.getModel().getRowCount();
        //String [] months = {"January","February","March","April","May","June","July","August","September","October","November","December"};

//        ObservableList<PieChart.Data> data = FXCollections.observableArrayList(
//        new PieChart.Data("January", Double.parseDouble(yearReportTable.getValueAt(0, 4).toString())),
//        new PieChart.Data("February", Double.parseDouble(yearReportTable.getValueAt(1, 4).toString())),
//        new PieChart.Data("March", Double.parseDouble(yearReportTable.getValueAt(2, 4).toString())),
//        new PieChart.Data("April", Double.parseDouble(yearReportTable.getValueAt(3, 4).toString())),
//        new PieChart.Data("May", Double.parseDouble(yearReportTable.getValueAt(4, 4).toString())),
//        new PieChart.Data("June", Double.parseDouble(yearReportTable.getValueAt(5, 4).toString())),
//        new PieChart.Data("July", Double.parseDouble(yearReportTable.getValueAt(6, 4).toString())),
//        new PieChart.Data("August", Double.parseDouble(yearReportTable.getValueAt(7, 4).toString())),
//        new PieChart.Data("September", Double.parseDouble(yearReportTable.getValueAt(8, 4).toString())),
//        new PieChart.Data("October", Double.parseDouble(yearReportTable.getValueAt(9, 4).toString())),
//        new PieChart.Data("November", Double.parseDouble(yearReportTable.getValueAt(10, 4).toString())),
//        new PieChart.Data("December", Double.parseDouble(yearReportTable.getValueAt(11, 4).toString())));
//        PieChart pieChart = new PieChart(data);
        //chartPanel.add(pieChart.snapshot(null, null), new Integer(0));
    }

    private void downloadToExcel(String reportName) {
//        try {
//            FileOutputStream fileOut = new FileOutputStream(reportName+".xls");
//            HSSFWorkbook workbook = new HSSFWorkbook();
//            HSSFSheet sheet = workbook.createSheet(reportName);
//            
//            TableModel model=treatmentReportTbl.getModel();
//            HSSFRow header = sheet.createRow(0);
//            HSSFCellStyle headerStyle = workbook.createCellStyle();
//            headerStyle.setFillBackgroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
//            header.setRowStyle(headerStyle);
//            for (int i=0;i<model.getColumnCount();i++){
//                HSSFCell cell = header.createCell(i);
//                cell.setCellValue(model.getColumnName(i));
//            }
//            for (int i=0;i<model.getRowCount();i++){  
//                HSSFRow row = sheet.createRow(i+1);
//                for (int j=0;j<model.getColumnCount();j++){
//                    String cellValue = "";
//                    try{
//                        cellValue = model.getValueAt(i,j).toString();
//                    }catch(Exception e){}
//                    HSSFCell cell = row.createCell(j);
//                    cell.setCellValue(cellValue);
//                    
//                }
//            }
//            workbook.write(fileOut);
//            fileOut.flush();
//            fileOut.close();
//        } catch (IOException ex) {
//            Logger.getLogger(Reports.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

    private void displayTreatmentReport() {
        
        Map<String, Float> opdMap = new HashMap<String, Float>();
        Map<String, Float> feesMap = new HashMap<String, Float>();
        Map<String, Float> retriveMap = new HashMap<String, Float>();
        DefaultTableModel treatmentReportModel = (DefaultTableModel) treatmentReportTbl.getModel();
        treatmentReportModel.setNumRows(0);
        String key;
        String keyTotal;
        float totalOPD = 0;
        float totalFees = 0;

        try {
            for (int mnt = 0; mnt < months.length; mnt++) {
                String startDate;
                String endDate;
                String yearSelected = this.treatmentReportYearDropDown.getSelectedItem().toString();
                if (mnt < 9) {
                    startDate = yearSelected + "-0" + (mnt + 1) + "-01";
                    endDate = yearSelected + "-0" + (mnt + 1) + "-31";
                } else {
                    startDate = yearSelected + "-" + (mnt + 1) + "-01";
                    endDate = yearSelected + "-" + (mnt + 1) + "-31";
                }
                List<TreatmentReportBean> treatmentReports = reportsDao.fetchTreatmentReport(databaseDateFormat.parse(startDate), databaseDateFormat.parse(endDate));
                float treatmentOpdTotal = 0;
                float treatmentFeesTotal = 0;
                for (TreatmentReportBean treatmentReport : treatmentReports) {
                    key = treatmentReport.getTreatmentName() + "_" + months[mnt];
                    treatmentOpdTotal += treatmentReport.getTotalOPD();
                    treatmentFeesTotal += treatmentReport.getTotalFees();
                    opdMap.put(key, new Float(treatmentReport.getTotalOPD()));
                    feesMap.put(key, treatmentReport.getTotalFees());
                    keyTotal = treatmentReport.getTreatmentName() + "_Total";
                    opdMap.put(keyTotal, opdMap.get(keyTotal) == null ? treatmentReport.getTotalOPD() : opdMap.get(keyTotal) + treatmentReport.getTotalOPD());
                    feesMap.put(keyTotal, feesMap.get(keyTotal) == null ? treatmentReport.getTotalFees() : feesMap.get(keyTotal) + treatmentReport.getTotalFees());
                }
                key = months[mnt] + "_Total";
                opdMap.put(key, treatmentOpdTotal);
                feesMap.put(key, treatmentFeesTotal);
                totalOPD += treatmentOpdTotal;
                totalFees += treatmentFeesTotal;
            }
            opdMap.put("Total", totalOPD);
            feesMap.put("Total", totalFees);

            List<TreatmentBean> treatments = treatmentDao.fetchTreatments();
            ButtonModel selectedType = this.treatmentReportType.getSelection();
            if (this.treatmentReportByOPDRadioBtn.isSelected()) {
                retriveMap = opdMap;
            } else {
                retriveMap = feesMap;
            }
            for (TreatmentBean treatment : treatments) {

                treatmentReportModel.addRow(new Object[]{
                    treatment.getTreatmentName(),
                    retriveMap.get(treatment.getTreatmentName() + "_" + "January"),
                    retriveMap.get(treatment.getTreatmentName() + "_" + "February"),
                    retriveMap.get(treatment.getTreatmentName() + "_" + "March"),
                    retriveMap.get(treatment.getTreatmentName() + "_" + "April"),
                    retriveMap.get(treatment.getTreatmentName() + "_" + "May"),
                    retriveMap.get(treatment.getTreatmentName() + "_" + "June"),
                    retriveMap.get(treatment.getTreatmentName() + "_" + "July"),
                    retriveMap.get(treatment.getTreatmentName() + "_" + "August"),
                    retriveMap.get(treatment.getTreatmentName() + "_" + "September"),
                    retriveMap.get(treatment.getTreatmentName() + "_" + "October"),
                    retriveMap.get(treatment.getTreatmentName() + "_" + "November"),
                    retriveMap.get(treatment.getTreatmentName() + "_" + "December"),
                    retriveMap.get(treatment.getTreatmentName() + "_Total")});
            }
        } catch (ParseException ex) {}

        treatmentReportModel.addRow(new Object[]{"TOTAL",
            retriveMap.get("January_Total"),
            retriveMap.get("February_Total"),
            retriveMap.get("March_Total"),
            retriveMap.get("April_Total"),
            retriveMap.get("May_Total"),
            retriveMap.get("June_Total"),
            retriveMap.get("July_Total"),
            retriveMap.get("August_Total"),
            retriveMap.get("September_Total"),
            retriveMap.get("October_Total"),
            retriveMap.get("November_Total"),
            retriveMap.get("December_Total"),
            retriveMap.get("Total")});
    }

    private void displayRefferedByReport() {
        Map<String, Float> opdMap = new HashMap<String, Float>();
        Map<String, Float> feesMap = new HashMap<String, Float>();
        Map<String, Float> retriveMap = new HashMap<String, Float>();
        DefaultTableModel refferedByReportModel = (DefaultTableModel) refferedByTbl.getModel();
        refferedByReportModel.setNumRows(0);
        String key;
        String keyTotal;
        float totalOPD = 0;
        float totalFees = 0;

        try {
            for (int mnt = 0; mnt < months.length; mnt++) {
                String startDate;
                String endDate;
                String yearSelected = this.refferedByReportYearCB.getSelectedItem().toString();
                if (mnt < 9) {
                    startDate = yearSelected + "-0" + (mnt + 1) + "-01";
                    endDate = yearSelected + "-0" + (mnt + 1) + "-31";
                } else {
                    startDate = yearSelected + "-" + (mnt + 1) + "-01";
                    endDate = yearSelected + "-" + (mnt + 1) + "-31";
                }
                List<ReferedByReportBean> referedByReports = reportsDao.fetchReferedByReport(databaseDateFormat.parse(startDate), databaseDateFormat.parse(endDate));
                float treatmentOpdTotal = 0;
                float treatmentFeesTotal = 0;
                for(ReferedByReportBean referedByReport : referedByReports) {
                    key = referedByReport.getReferedBy() + "_" + months[mnt];
                    treatmentOpdTotal += referedByReport.getTotalOPD();
                    treatmentFeesTotal += referedByReport.getTotalFees();
                    opdMap.put(key, new Float(referedByReport.getTotalOPD()));
                    feesMap.put(key, referedByReport.getTotalFees());
                    keyTotal = referedByReport.getReferedBy() + "_Total";
                    opdMap.put(keyTotal, opdMap.get(keyTotal) == null ? referedByReport.getTotalOPD() : opdMap.get(keyTotal) + referedByReport.getTotalOPD());
                    feesMap.put(keyTotal, feesMap.get(keyTotal) == null ? referedByReport.getTotalFees() : feesMap.get(keyTotal) + referedByReport.getTotalFees());
                }
                key = months[mnt] + "_Total";
                opdMap.put(key, treatmentOpdTotal);
                feesMap.put(key, treatmentFeesTotal);
                totalOPD += treatmentOpdTotal;
                totalFees += treatmentFeesTotal;
            }
            opdMap.put("Total", totalOPD);
            feesMap.put("Total", totalFees);

            List<ReferedByBean> referedBys = referedByDao.fetchReferedBy();
            ButtonModel selectedType = this.refferedByReportType.getSelection();
            if (this.refferedByOPDRadio.isSelected()) {
                retriveMap = opdMap;
            } else {
                retriveMap = feesMap;
            }
            for (ReferedByBean referedBy : referedBys) {
                final String referedByName = referedBy.getName();

                refferedByReportModel.addRow(new Object[]{
                    referedByName, retriveMap.get(referedByName + "_" + "January"),
                    retriveMap.get(referedByName + "_" + "February"),
                    retriveMap.get(referedByName + "_" + "March"),
                    retriveMap.get(referedByName + "_" + "April"),
                    retriveMap.get(referedByName + "_" + "May"),
                    retriveMap.get(referedByName + "_" + "June"),
                    retriveMap.get(referedByName + "_" + "July"),
                    retriveMap.get(referedByName + "_" + "August"),
                    retriveMap.get(referedByName + "_" + "September"),
                    retriveMap.get(referedByName + "_" + "October"),
                    retriveMap.get(referedByName + "_" + "November"),
                    retriveMap.get(referedByName + "_" + "December"),
                    retriveMap.get(referedByName + "_Total")});
            }
        } catch (ParseException ex) {}

        refferedByReportModel.addRow(new Object[]{"TOTAL",
            retriveMap.get("January_Total"),
            retriveMap.get("February_Total"),
            retriveMap.get("March_Total"),
            retriveMap.get("April_Total"),
            retriveMap.get("May_Total"),
            retriveMap.get("June_Total"),
            retriveMap.get("July_Total"),
            retriveMap.get("August_Total"),
            retriveMap.get("September_Total"),
            retriveMap.get("October_Total"),
            retriveMap.get("November_Total"),
            retriveMap.get("December_Total"),
            retriveMap.get("Total")});
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
            java.util.logging.Logger.getLogger(Reports.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Reports.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Reports.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Reports.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Reports().setVisible(true);
            }
        });
    }

    private void monthlyReport() throws HeadlessException, NumberFormatException {
        int selectedMonth = month.getSelectedIndex();
        int selectedYear = Integer.parseInt(year.getSelectedItem().toString());
        float totalAmount = 0.0f;
        float totalConsultaionDoctorAmount = 0.0f;
        DefaultTableModel monthlyReportModel = (DefaultTableModel) monthReportTable.getModel();
        String startDate = selectedYear + "-" + (selectedMonth + 1) + "-01";
        String endDate = selectedYear + "-" + (selectedMonth + 1) + "-31";
        monthlyReportModel.setNumRows(0);

        try {
            List<MonthlyReportBean> monthlyReports = reportsDao.fetchMonthlyReport(databaseDateFormat.parse(startDate), databaseDateFormat.parse(endDate));
            for (MonthlyReportBean monthlyReport : monthlyReports) {
                monthlyReportModel.addRow(new Object[]{
                    displayDateFormat.format(monthlyReport.getDate()),
                    patientsDao.getNewPatientCount(monthlyReport.getDate(), monthlyReport.getDate()),
                    monthlyReport.getOpd(),
                    monthlyReport.getFees(),
                    monthlyReport.getConsultingFees()
                });
                totalAmount += monthlyReport.getFees();
                totalConsultaionDoctorAmount += monthlyReport.getConsultingFees();
            }
            monthlyReportModel.addRow(new Object[]{"<html><b>Total</b></html>", "", "", totalAmount, totalConsultaionDoctorAmount});
        } catch (ParseException ex) {
            Logger.getLogger(Reports.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void yearlyReport() throws NumberFormatException, HeadlessException {
        int selectedYear = Integer.parseInt(y_year.getSelectedItem().toString());
        float totalAmount = 0.0f;
        float totalConsultationDoctorAmount = 0.0f;
        DefaultTableModel yearlyReportModel = (DefaultTableModel) yearReportTable.getModel();
        yearlyReportModel.setNumRows(0);
        String startDate;
        String endDate;
        try {
            for (int i = 0; i < months.length; i++) {
                if (i < 9) {
                    startDate = selectedYear + "-0" + (i + 1) + "-01";
                    endDate = selectedYear + "-0" + (i + 1) + "-31";
                } else {
                    startDate = selectedYear + "-" + (i + 1) + "-01";
                    endDate = selectedYear + "-" + (i + 1) + "-31";
                }

                List<MonthlyReportBean> monthlyReports = reportsDao.fetchYearlyReport(databaseDateFormat.parse(startDate), databaseDateFormat.parse(endDate));
                
                for (MonthlyReportBean monthlyReport : monthlyReports) {

                    yearlyReportModel.addRow(new Object[]{
                        months[i],
                        patientsDao.getNewPatientCount(new Date(selectedYear - 1900, i, 01), new Date(selectedYear - 1900, i, 31)),
                        monthlyReport.getOpd(),
                        monthlyReport.getFees(),
                        monthlyReport.getConsultingFees()
                    });

                    totalAmount += monthlyReport.getFees();
                    totalConsultationDoctorAmount += monthlyReport.getConsultingFees();
                }
            }
            yearlyReportModel.addRow(new Object[]{"<html><b>Total</b></html>", "", "", totalAmount, totalConsultationDoctorAmount});
        } catch (ParseException ex) {}
        loadPIChart();
    }

    private void yearlyReportTableAction() throws NumberFormatException {
        int SelectedRow = this.yearReportTable.getSelectedRow();
        int selectedYear = Integer.parseInt(this.y_year.getSelectedItem().toString());
        if (SelectedRow != 12) {
            this.month.setSelectedIndex(SelectedRow);
            this.year.setSelectedItem("" + selectedYear);
            this.jTabbedPane1.setSelectedComponent(this.monthlyReportPanel);
            this.monthlyReportPanel.setVisible(true);
            this.monthlyReportBtn.doClick();
        }
    }

    private void monthlyReportTableAction() {
        int selectedRow = this.monthReportTable.getSelectedRow();

        if ((selectedRow + 1) != this.monthReportTable.getRowCount()) {

            String selectedDate = this.monthReportTable.getValueAt(selectedRow, 0).toString();
            try {
                this.dateCB.setDate(displayDateFormat.parse(selectedDate));
            } catch (ParseException ex) { }

            this.jTabbedPane1.setSelectedComponent(this.dailyReportPanel);
            try {
                dateReport();
            } catch (HeadlessException e) {
            }
            this.dailyReportPanel.setVisible(true);
            //this.reportBtn.doClick();
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

        reportTypeGroup = new javax.swing.ButtonGroup();
        treatmentReportType = new javax.swing.ButtonGroup();
        refferedByReportType = new javax.swing.ButtonGroup();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        dailyReportPanel = new javax.swing.JPanel();
        dateLbl = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        dailyReportTable = new javax.swing.JTable();
        dailyReportBtn = new javax.swing.JButton();
        dateCB = new com.toedter.calendar.JDateChooser();
        monthlyReportPanel = new javax.swing.JPanel();
        monthSelectLbl = new javax.swing.JLabel();
        month = new javax.swing.JComboBox();
        year = new javax.swing.JComboBox();
        jScrollPane2 = new javax.swing.JScrollPane();
        monthReportTable = new javax.swing.JTable();
        monthlyReportBtn = new javax.swing.JButton();
        yearlyReportPanel = new javax.swing.JPanel();
        yearSelectLbl = new javax.swing.JLabel();
        y_year = new javax.swing.JComboBox();
        yearlyReportBtn = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        yearReportTable = new javax.swing.JTable();
        chartPanel = new javax.swing.JPanel();
        treatmentReportPanel = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        treatmentReportTbl = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        treatmentReportByFeesRadioBtn = new javax.swing.JRadioButton();
        treatmentReportByOPDRadioBtn = new javax.swing.JRadioButton();
        jPanel3 = new javax.swing.JPanel();
        yearReportLbl = new javax.swing.JLabel();
        treatmentReportYearDropDown = new javax.swing.JComboBox();
        treatmentReportBtn = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        refferedByPanel = new javax.swing.JPanel();
        RefferedByControllPanel = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        refferedByFeesRadio = new javax.swing.JRadioButton();
        refferedByOPDRadio = new javax.swing.JRadioButton();
        jPanel5 = new javax.swing.JPanel();
        refferedByReportYearLbl = new javax.swing.JLabel();
        refferedByReportYearCB = new javax.swing.JComboBox();
        refferedByViewReportBtn = new javax.swing.JButton();
        printReferedByBtn = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        refferedByTbl = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Reports");
        setExtendedState(2);
        setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width,Toolkit.getDefaultToolkit().getScreenSize().height-Toolkit.getDefaultToolkit().getScreenInsets(getGraphicsConfiguration()).bottom));

        jTabbedPane1.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N

        dateLbl.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        dateLbl.setForeground(new java.awt.Color(51, 51, 255));
        dateLbl.setText("Select Date :");

        dailyReportTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Case No", "Name", "Treatment", "Fees", "Consulting Docor Fees"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Float.class, java.lang.Float.class
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
        dailyReportTable.setRowSorter(new TableRowSorter(dailyReportTable.getModel()
        ));
        jScrollPane1.setViewportView(dailyReportTable);

        dailyReportBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/EkDant/icones/Medical Invoice 3D Search.png"))); // NOI18N
        dailyReportBtn.setText("View Report");
        dailyReportBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dailyReportBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout dailyReportPanelLayout = new javax.swing.GroupLayout(dailyReportPanel);
        dailyReportPanel.setLayout(dailyReportPanelLayout);
        dailyReportPanelLayout.setHorizontalGroup(
            dailyReportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dailyReportPanelLayout.createSequentialGroup()
                .addGroup(dailyReportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dailyReportPanelLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(dateLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(dateCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(82, 82, 82)
                        .addComponent(dailyReportBtn))
                    .addGroup(dailyReportPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 675, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(100, 100, 100))
        );
        dailyReportPanelLayout.setVerticalGroup(
            dailyReportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dailyReportPanelLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(dailyReportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dailyReportBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dateLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dateCB, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(301, Short.MAX_VALUE))
        );

        dailyReportPanelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {dailyReportBtn, dateLbl});

        jTabbedPane1.addTab("Daily Report", new javax.swing.ImageIcon(getClass().getResource("/EkDant/icones/Report24_daily.png")), dailyReportPanel); // NOI18N

        monthSelectLbl.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        monthSelectLbl.setForeground(new java.awt.Color(51, 51, 255));
        monthSelectLbl.setText("Select Month :");

        month.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" }));

        year.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "2013", "2014", "2015", "2016", "2017", "2018", "2019", "2020" }));

        monthReportTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Date", "New OPD", "OPD", "Fees", "Consulting Docor Fees"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Float.class, java.lang.Float.class
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
        monthReportTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                monthReportTableMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(monthReportTable);

        monthlyReportBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/EkDant/icones/Medical Invoice 3D Search.png"))); // NOI18N
        monthlyReportBtn.setText("View Report");
        monthlyReportBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                monthlyReportBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout monthlyReportPanelLayout = new javax.swing.GroupLayout(monthlyReportPanel);
        monthlyReportPanel.setLayout(monthlyReportPanelLayout);
        monthlyReportPanelLayout.setHorizontalGroup(
            monthlyReportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(monthlyReportPanelLayout.createSequentialGroup()
                .addGroup(monthlyReportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(monthlyReportPanelLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(monthSelectLbl)
                        .addGap(18, 18, 18)
                        .addComponent(month, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(year, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(54, 54, 54)
                        .addComponent(monthlyReportBtn))
                    .addGroup(monthlyReportPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 570, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        monthlyReportPanelLayout.setVerticalGroup(
            monthlyReportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(monthlyReportPanelLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(monthlyReportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(monthSelectLbl)
                    .addGroup(monthlyReportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(month, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(year, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(monthlyReportBtn)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(223, 223, 223))
        );

        monthlyReportPanelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {month, monthSelectLbl, monthlyReportBtn, year});

        jTabbedPane1.addTab("Monthly Report", new javax.swing.ImageIcon(getClass().getResource("/EkDant/icones/Report24_monthly_1.png")), monthlyReportPanel); // NOI18N

        yearSelectLbl.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        yearSelectLbl.setForeground(new java.awt.Color(51, 51, 255));
        yearSelectLbl.setText("Select Year :");

        y_year.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "2013", "2014", "2015", "2016", "2017", "2018", "2019", "2020" }));

        yearlyReportBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/EkDant/icones/Medical Invoice 3D Search.png"))); // NOI18N
        yearlyReportBtn.setText("View Report");
        yearlyReportBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                yearlyReportBtnActionPerformed(evt);
            }
        });

        yearReportTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Month", "New OPD", "OPD", "Fees", "Consulting Docor Fees"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Float.class, java.lang.Float.class
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
        yearReportTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                yearReportTableMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(yearReportTable);

        javax.swing.GroupLayout chartPanelLayout = new javax.swing.GroupLayout(chartPanel);
        chartPanel.setLayout(chartPanelLayout);
        chartPanelLayout.setHorizontalGroup(
            chartPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 615, Short.MAX_VALUE)
        );
        chartPanelLayout.setVerticalGroup(
            chartPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout yearlyReportPanelLayout = new javax.swing.GroupLayout(yearlyReportPanel);
        yearlyReportPanel.setLayout(yearlyReportPanelLayout);
        yearlyReportPanelLayout.setHorizontalGroup(
            yearlyReportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(yearlyReportPanelLayout.createSequentialGroup()
                .addGroup(yearlyReportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(yearlyReportPanelLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(yearSelectLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(y_year, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(106, 106, 106)
                        .addComponent(yearlyReportBtn))
                    .addGroup(yearlyReportPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 570, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(chartPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        yearlyReportPanelLayout.setVerticalGroup(
            yearlyReportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(yearlyReportPanelLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(yearlyReportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(yearSelectLbl)
                    .addComponent(yearlyReportBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(y_year, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(yearlyReportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(chartPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane3))
                .addContainerGap())
        );

        yearlyReportPanelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {y_year, yearSelectLbl, yearlyReportBtn});

        jTabbedPane1.addTab("Yearly Report", new javax.swing.ImageIcon(getClass().getResource("/EkDant/icones/Report24_yearly.png")), yearlyReportPanel); // NOI18N

        treatmentReportTbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Treatment", "January", "February", "March", "April", "May", "June", "July ", "August", "September", "October", "November", "December", "Total"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Float.class, java.lang.Float.class, java.lang.Float.class, java.lang.Float.class, java.lang.Float.class, java.lang.Float.class, java.lang.Float.class, java.lang.Float.class, java.lang.Float.class, java.lang.Float.class, java.lang.Float.class, java.lang.Float.class, java.lang.Float.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(treatmentReportTbl);
        if (treatmentReportTbl.getColumnModel().getColumnCount() > 0) {
            treatmentReportTbl.getColumnModel().getColumn(0).setPreferredWidth(250);
        }

        jPanel1.setBackground(new java.awt.Color(219, 228, 255));

        treatmentReportType.add(treatmentReportByFeesRadioBtn);
        treatmentReportByFeesRadioBtn.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        treatmentReportByFeesRadioBtn.setForeground(new java.awt.Color(51, 51, 255));
        treatmentReportByFeesRadioBtn.setSelected(true);
        treatmentReportByFeesRadioBtn.setText("Fees");
        treatmentReportByFeesRadioBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                treatmentReportByFeesRadioBtnActionPerformed(evt);
            }
        });

        treatmentReportType.add(treatmentReportByOPDRadioBtn);
        treatmentReportByOPDRadioBtn.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        treatmentReportByOPDRadioBtn.setForeground(new java.awt.Color(51, 51, 255));
        treatmentReportByOPDRadioBtn.setText("OPD");
        treatmentReportByOPDRadioBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                treatmentReportByOPDRadioBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(treatmentReportByFeesRadioBtn)
                .addGap(63, 63, 63)
                .addComponent(treatmentReportByOPDRadioBtn)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(treatmentReportByFeesRadioBtn)
                    .addComponent(treatmentReportByOPDRadioBtn))
                .addContainerGap())
        );

        jPanel3.setBackground(new java.awt.Color(204, 204, 204));

        yearReportLbl.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        yearReportLbl.setForeground(new java.awt.Color(51, 51, 255));
        yearReportLbl.setText("Report For Year");

        treatmentReportYearDropDown.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "2014", "2015", "2016", "2017", "2018", "2019", "2020" }));

        treatmentReportBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/EkDant/icones/Medical Invoice Flat Search.png"))); // NOI18N
        treatmentReportBtn.setText("View Report");
        treatmentReportBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                treatmentReportBtnActionPerformed(evt);
            }
        });

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/EkDant/icones/Print.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(yearReportLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(treatmentReportYearDropDown, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(treatmentReportBtn)
                .addGap(18, 18, 18)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(yearReportLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(treatmentReportYearDropDown)
                    .addComponent(treatmentReportBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1)))
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jButton1, treatmentReportBtn, treatmentReportYearDropDown, yearReportLbl});

        javax.swing.GroupLayout treatmentReportPanelLayout = new javax.swing.GroupLayout(treatmentReportPanel);
        treatmentReportPanel.setLayout(treatmentReportPanelLayout);
        treatmentReportPanelLayout.setHorizontalGroup(
            treatmentReportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(treatmentReportPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(treatmentReportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(treatmentReportPanelLayout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 1215, Short.MAX_VALUE))
                .addContainerGap())
        );
        treatmentReportPanelLayout.setVerticalGroup(
            treatmentReportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, treatmentReportPanelLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(treatmentReportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(217, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Treatment Report", new javax.swing.ImageIcon(getClass().getResource("/EkDant/icones/Medical Invoice Flat Statis.png")), treatmentReportPanel); // NOI18N

        jPanel4.setBackground(new java.awt.Color(219, 228, 255));

        refferedByReportType.add(refferedByFeesRadio);
        refferedByFeesRadio.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        refferedByFeesRadio.setForeground(new java.awt.Color(51, 51, 255));
        refferedByFeesRadio.setSelected(true);
        refferedByFeesRadio.setText("Fees");
        refferedByFeesRadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refferedByFeesRadioActionPerformed(evt);
            }
        });

        refferedByReportType.add(refferedByOPDRadio);
        refferedByOPDRadio.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        refferedByOPDRadio.setForeground(new java.awt.Color(51, 51, 255));
        refferedByOPDRadio.setText("OPD");
        refferedByOPDRadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refferedByOPDRadioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(refferedByFeesRadio)
                .addGap(63, 63, 63)
                .addComponent(refferedByOPDRadio)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(refferedByFeesRadio)
                    .addComponent(refferedByOPDRadio))
                .addContainerGap())
        );

        jPanel5.setBackground(new java.awt.Color(204, 204, 204));

        refferedByReportYearLbl.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        refferedByReportYearLbl.setForeground(new java.awt.Color(51, 51, 255));
        refferedByReportYearLbl.setText("Report For Year");

        refferedByReportYearCB.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "2014", "2015", "2016", "2017", "2018", "2019", "2020" }));

        refferedByViewReportBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/EkDant/icones/Medical Invoice Flat Search.png"))); // NOI18N
        refferedByViewReportBtn.setText("View Report");
        refferedByViewReportBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refferedByViewReportBtnActionPerformed(evt);
            }
        });

        printReferedByBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/EkDant/icones/Print.png"))); // NOI18N
        printReferedByBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printReferedByBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(refferedByReportYearLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(refferedByReportYearCB, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(refferedByViewReportBtn)
                .addGap(18, 18, 18)
                .addComponent(printReferedByBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(refferedByReportYearLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(refferedByReportYearCB)
                    .addComponent(refferedByViewReportBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(printReferedByBtn)))
        );

        javax.swing.GroupLayout RefferedByControllPanelLayout = new javax.swing.GroupLayout(RefferedByControllPanel);
        RefferedByControllPanel.setLayout(RefferedByControllPanelLayout);
        RefferedByControllPanelLayout.setHorizontalGroup(
            RefferedByControllPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(RefferedByControllPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(592, Short.MAX_VALUE))
        );
        RefferedByControllPanelLayout.setVerticalGroup(
            RefferedByControllPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(RefferedByControllPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(RefferedByControllPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        refferedByTbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Reffered By", "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December", "Total"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane5.setViewportView(refferedByTbl);
        if (refferedByTbl.getColumnModel().getColumnCount() > 0) {
            refferedByTbl.getColumnModel().getColumn(0).setPreferredWidth(250);
        }

        javax.swing.GroupLayout refferedByPanelLayout = new javax.swing.GroupLayout(refferedByPanel);
        refferedByPanel.setLayout(refferedByPanelLayout);
        refferedByPanelLayout.setHorizontalGroup(
            refferedByPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(refferedByPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(refferedByPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(refferedByPanelLayout.createSequentialGroup()
                        .addComponent(jScrollPane5)
                        .addContainerGap())
                    .addComponent(RefferedByControllPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        refferedByPanelLayout.setVerticalGroup(
            refferedByPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(refferedByPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(RefferedByControllPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 565, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(137, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Reffered By Report", refferedByPanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void dailyReportBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dailyReportBtnActionPerformed
        dateReport();
    }//GEN-LAST:event_dailyReportBtnActionPerformed

    private void monthlyReportBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_monthlyReportBtnActionPerformed
        monthlyReport();
    }//GEN-LAST:event_monthlyReportBtnActionPerformed

    private void yearlyReportBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_yearlyReportBtnActionPerformed
        yearlyReport();
    }//GEN-LAST:event_yearlyReportBtnActionPerformed

    private void yearReportTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_yearReportTableMouseClicked
        yearlyReportTableAction();
    }//GEN-LAST:event_yearReportTableMouseClicked

    private void monthReportTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_monthReportTableMouseClicked
        monthlyReportTableAction();
    }//GEN-LAST:event_monthReportTableMouseClicked

    private void treatmentReportByOPDRadioBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_treatmentReportByOPDRadioBtnActionPerformed
        displayTreatmentReport();
    }//GEN-LAST:event_treatmentReportByOPDRadioBtnActionPerformed

    private void treatmentReportBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_treatmentReportBtnActionPerformed
        displayTreatmentReport();
    }//GEN-LAST:event_treatmentReportBtnActionPerformed

    private void treatmentReportByFeesRadioBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_treatmentReportByFeesRadioBtnActionPerformed
        displayTreatmentReport();
    }//GEN-LAST:event_treatmentReportByFeesRadioBtnActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        downloadToExcel("treatmentReport");
    }//GEN-LAST:event_jButton1ActionPerformed

    private void refferedByFeesRadioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refferedByFeesRadioActionPerformed
        displayRefferedByReport();
    }//GEN-LAST:event_refferedByFeesRadioActionPerformed

    private void refferedByOPDRadioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refferedByOPDRadioActionPerformed
        displayRefferedByReport();
    }//GEN-LAST:event_refferedByOPDRadioActionPerformed

    private void refferedByViewReportBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refferedByViewReportBtnActionPerformed
        displayRefferedByReport();
    }//GEN-LAST:event_refferedByViewReportBtnActionPerformed

    private void printReferedByBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printReferedByBtnActionPerformed
        
    }//GEN-LAST:event_printReferedByBtnActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel RefferedByControllPanel;
    private javax.swing.JPanel chartPanel;
    private javax.swing.JButton dailyReportBtn;
    private javax.swing.JPanel dailyReportPanel;
    private javax.swing.JTable dailyReportTable;
    private com.toedter.calendar.JDateChooser dateCB;
    private javax.swing.JLabel dateLbl;
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JComboBox month;
    private javax.swing.JTable monthReportTable;
    private javax.swing.JLabel monthSelectLbl;
    private javax.swing.JButton monthlyReportBtn;
    private javax.swing.JPanel monthlyReportPanel;
    private javax.swing.JButton printReferedByBtn;
    private javax.swing.JRadioButton refferedByFeesRadio;
    private javax.swing.JRadioButton refferedByOPDRadio;
    private javax.swing.JPanel refferedByPanel;
    private javax.swing.ButtonGroup refferedByReportType;
    private javax.swing.JComboBox refferedByReportYearCB;
    private javax.swing.JLabel refferedByReportYearLbl;
    private javax.swing.JTable refferedByTbl;
    private javax.swing.JButton refferedByViewReportBtn;
    private javax.swing.ButtonGroup reportTypeGroup;
    private javax.swing.JButton treatmentReportBtn;
    private javax.swing.JRadioButton treatmentReportByFeesRadioBtn;
    private javax.swing.JRadioButton treatmentReportByOPDRadioBtn;
    private javax.swing.JPanel treatmentReportPanel;
    private javax.swing.JTable treatmentReportTbl;
    private javax.swing.ButtonGroup treatmentReportType;
    private javax.swing.JComboBox treatmentReportYearDropDown;
    private javax.swing.JComboBox y_year;
    private javax.swing.JComboBox year;
    private javax.swing.JLabel yearReportLbl;
    private javax.swing.JTable yearReportTable;
    private javax.swing.JLabel yearSelectLbl;
    private javax.swing.JButton yearlyReportBtn;
    private javax.swing.JPanel yearlyReportPanel;
    // End of variables declaration//GEN-END:variables
}
