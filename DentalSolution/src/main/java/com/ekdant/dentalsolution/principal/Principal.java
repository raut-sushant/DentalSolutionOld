package com.ekdant.dentalsolution.principal;
import com.ekdant.dentalsolution.appointment.Appointments;
import com.ekdant.dentalsolution.dao.AppointmentsDao;
import com.ekdant.dentalsolution.dao.ClinicDao;
import com.ekdant.dentalsolution.dao.DoctorDao;
import com.ekdant.dentalsolution.dao.LabDao;
import com.ekdant.dentalsolution.dao.PatientsDao;
import com.ekdant.dentalsolution.dao.SettingsDao;
import com.ekdant.dentalsolution.document.BackupDirectory;
import com.ekdant.dentalsolution.domain.ClinicBean;
import com.ekdant.dentalsolution.domain.DoctorBean;

import com.ekdant.dentalsolution.labwork.LabWorkTracking;
import com.ekdant.dentalsolution.masters.Cities;
import com.ekdant.dentalsolution.masters.ExpenseCategories;
import com.ekdant.dentalsolution.masters.Lab;
import com.ekdant.dentalsolution.masters.LabWorkName;
import com.ekdant.dentalsolution.masters.Medicine;
import com.ekdant.dentalsolution.masters.MedicineType;
import com.ekdant.dentalsolution.masters.PreMedicalHistory;
import com.ekdant.dentalsolution.masters.ReferedBy;
import com.ekdant.dentalsolution.masters.Settings;
import com.ekdant.dentalsolution.masters.Treatment;
import com.ekdant.dentalsolution.reports.Reports;
import com.ekdant.dentalsolution.utilities.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.TitledBorder;
/**
 *
 * @author Sushant
 */
public class Principal extends javax.swing.JFrame {
    String lookAndFeel = "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel";
    public String logedInUser = null;
    public String logedInUserType = null;
    PatientsDao patientsDao;
    AppointmentsDao appointmentsDao;
    LabDao labDao;
    DoctorDao doctorDao;
    ClinicDao clinicDao;
    SettingsDao settingsDao;

    Date today = new Date();
    
    Date yesterday = new Date();
    DateFormat dateFormat = new SimpleDateFormat("E, d MMM yyyy");
    DateFormat displayDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
    DateFormat databaseDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    String baseLocation;
    private static final String databaseFile = "ConvertedEkdant.sqlite";
    
    private final int renewalNotificationDays = 15;
    
    /** Creates new form JF_Principal
     * @param loginUser
     * @param loginUserType */
    public Principal(String loginUser, String loginUserType) {
        initComponents();
        patientsDao = new PatientsDao();
        appointmentsDao = new AppointmentsDao();
        labDao = new LabDao();
        doctorDao = new DoctorDao();
        clinicDao = new ClinicDao();
        settingsDao = new SettingsDao();
        
        startDateDC.setDate(today);
        endDateDC.setDate(today);
        this.logedInUser = loginUser;
        this.logedInUserType = loginUserType;
        initMainPage();        
        LookAndFeel();
        try {
            String path = URLDecoder.decode(this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath(), "UTF-8");
            baseLocation = path.substring(0, path.lastIndexOf("/")+1);
        } catch (UnsupportedEncodingException ex) {}
    }
    
    private void exit() throws HeadlessException {
        int exitMessageChosenOption = JOptionPane.showConfirmDialog(null,"Really want to quit?", "Exit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, new javax.swing.ImageIcon(getClass().getResource("/EkDant/icones/Gnome-Application-Exit-48.png")));
        if(exitMessageChosenOption == JOptionPane.YES_OPTION){
            System.exit(0);
        }
    }
    
     private void LookAndFeel(){
        //Look And Feel
        System.setProperty(
            "Quaqua.tabLayoutPolicy","wrap"
         );
         try {            
            UIManager.setLookAndFeel(lookAndFeel);            
            SwingUtilities.updateComponentTreeUI(this);
        }
        catch(ClassNotFoundException error) {
            JOptionPane.showMessageDialog(null,"Error matching downloads theme : "+error);
        } catch (IllegalAccessException error) {
            JOptionPane.showMessageDialog(null,"Error matching downloads theme : "+error);
        } catch (InstantiationException error) {
            JOptionPane.showMessageDialog(null,"Error matching downloads theme : "+error);
        } catch (UnsupportedLookAndFeelException error) {
            JOptionPane.showMessageDialog(null,"Error matching downloads theme : "+error);
        }    
    }

    private void initMainPage() {
        
        Calendar calendarYesterday = new GregorianCalendar();
        calendarYesterday.setTime(new Date());
        calendarYesterday.add(Calendar.DATE, -1);
        loadLoginDetails();       
        loadLicenseDetails();
        todayStatastics();        
        yesterdayStatastics(calendarYesterday);        
        statasticsForPeriod();
        if(logedInUserType.equalsIgnoreCase("staff")){
            staffLogin();
        }
    }
    
    private void staffLogin(){
        reportsBtn.setVisible(false);
        reportMenu.setVisible(false);
        expenseBtn.setVisible(false);
        financialMenu.setVisible(false);
        settingsMenu.setVisible(false);
        doctorItem.setVisible(false);
        employeeItem.setVisible(false);
    }

    private void statasticsForPeriod() {
        TitledBorder titledBorder = new TitledBorder("Custom Statastics");
        titledBorder.setTitleColor(new Color(51, 51, 255));
        titledBorder.setTitleFont(new Font("Tahoma", Font.BOLD, 16));
        customInfoPanel.setBorder(titledBorder);
        if(startDateDC.getDate() != null && endDateDC.getDate() != null){
            newPatientsPeriodTxt.setText(""+patientsDao.getNewPatientCount(startDateDC.getDate(), endDateDC.getDate()));
            patientsVisitPeriodTxt.setText(""+patientsDao.getPatientVisit(startDateDC.getDate(), endDateDC.getDate()));
        }
    }

    private void yesterdayStatastics(Calendar calendarYesterday) {
        TitledBorder titledBorder = new TitledBorder(dateFormat.format(new Date(calendarYesterday.getTimeInMillis())));
        titledBorder.setTitleColor(new Color(51, 51, 255));
        titledBorder.setTitleFont(new Font("Tahoma", Font.BOLD, 16));
        yesterdayInfoPanel.setBorder(titledBorder);
        patientsVisitYesterdayTxt.setText(""+patientsDao.getPatientVisit(new Date(calendarYesterday.getTimeInMillis()), new Date(calendarYesterday.getTimeInMillis())));
        newPatientsYesterdayTxt.setText(""+patientsDao.getNewPatientCount(new Date(calendarYesterday.getTimeInMillis()), new Date(calendarYesterday.getTimeInMillis())));
    }

    private void todayStatastics() {
        TitledBorder titledBorder = new TitledBorder(dateFormat.format(today));
        titledBorder.setTitleColor(new Color(51, 51, 255));
        titledBorder.setTitleFont(new Font("Tahoma", Font.BOLD, 16));
        todayInfoPanel.setBorder(titledBorder);
        newPatientsTodayTxt.setText(""+patientsDao.getNewPatientCount(today, today));
        patientsVisitTodayTxt.setText(""+patientsDao.getPatientVisit(today, today));
        scheduledAppointmentsTxt.setText(""+appointmentsDao.getAppointmentCount(today));
        labWorkPendingTxt.setText("" + labDao.getLabWorkCount(today, 1));
        labWorkSubmittedTxt.setText("" + labDao.getLabWorkCount(today, 2));
        labWorkReceivedTxt.setText("" + labDao.getLabWorkCount(today, 3));
    }

    private void loadLoginDetails() {        
        String userName = (logedInUser);
        userLbl.setText("Welcome, "+userName);
        DoctorBean mainDoctor = doctorDao.fetchMainDoctor();
        List<ClinicBean> clinics = clinicDao.fetchClinics();
        docNameTxt.setText("Dr " + mainDoctor.getName());
        degreeLbl.setText(mainDoctor.getDegree());
        regNoTxt.setText("Reg No. " + mainDoctor.getRegistrationNo()); 
        setTitle(clinics.get(0).getName());
        clinicHeaderLbl.setText(clinics.get(0).getName());
        dateLbl.setText(dateFormat.format(today));
    }
    
    private void loadLicenseDetails(){
        String message = "";
        if(Utils.demoVersion()){
            message = "Demo Version: you can use this up to 25 patients";
        }
        
        if(Utils.activationExpiring(renewalNotificationDays)){
            message = "Activation is going to expire in "+Utils.activationRemaining()+" days, please activate your software.";
        }
        
        if(Utils.activationExpired()){
            message = "Activation Expired, please activate your software.";
        }
        licenseUpdateMessageLbl.setText(message);                
    }
    
    private void backUp(){
        String fileName = "ekDantBackup_"+displayDateFormat.format(today)+".sqlite";
        File sourceFile = new File(baseLocation+"\\"+databaseFile);
        File backupFile1 = new File(settingsDao.getMySQLPath() + "\\" + fileName);
        
        InputStream is = null;
        OutputStream os = null;
        try{
            try {
                is = new FileInputStream(sourceFile);
                os = new FileOutputStream(backupFile1);
                byte[] buffer = new byte[1024];
                int length;
                while ((length = is.read(buffer)) > 0) {
                    os.write(buffer, 0, length);
                }
            } catch(Exception e){
                System.out.println(e);
            }finally {
                is.close();
                os.close();
            }
        }catch(IOException ioe){
            
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

        footerPanel = new javax.swing.JPanel();
        userLbl = new javax.swing.JLabel();
        dateLbl = new javax.swing.JLabel();
        todayInfoPanel = new javax.swing.JPanel();
        newPatientTodayLbl = new javax.swing.JLabel();
        newPatientsTodayTxt = new javax.swing.JLabel();
        patientVisitTodayLbl = new javax.swing.JLabel();
        patientsVisitTodayTxt = new javax.swing.JLabel();
        scheduledAppointmentsLbl = new javax.swing.JLabel();
        scheduledAppointmentsTxt = new javax.swing.JLabel();
        labWorkPendingLbl = new javax.swing.JLabel();
        labWorkPendingTxt = new javax.swing.JLabel();
        labWorkSubmittedLbl = new javax.swing.JLabel();
        labWorkSubmittedTxt = new javax.swing.JLabel();
        labWorkReceivedLbl = new javax.swing.JLabel();
        labWorkReceivedTxt = new javax.swing.JLabel();
        mainButtonPanel = new javax.swing.JPanel();
        exitBtn = new javax.swing.JButton();
        backupBtn = new javax.swing.JButton();
        reportsBtn = new javax.swing.JButton();
        appointmentBtn = new javax.swing.JButton();
        patientsBtn = new javax.swing.JButton();
        labBtn = new javax.swing.JButton();
        expenseBtn = new javax.swing.JButton();
        yesterdayInfoPanel = new javax.swing.JPanel();
        newPatientsYesterdayLbl = new javax.swing.JLabel();
        newPatientsYesterdayTxt = new javax.swing.JLabel();
        patientsVisitYesterdayLbl = new javax.swing.JLabel();
        patientsVisitYesterdayTxt = new javax.swing.JLabel();
        customInfoPanel = new javax.swing.JPanel();
        newPatientsPeriodLbl = new javax.swing.JLabel();
        patientsVisitPeriodTxt = new javax.swing.JLabel();
        newPatientsPeriodTxt = new javax.swing.JLabel();
        patientsVisitPeriodLbl = new javax.swing.JLabel();
        startDateLbl = new javax.swing.JLabel();
        endDateLbl = new javax.swing.JLabel();
        startDateDC = new com.toedter.calendar.JDateChooser();
        endDateDC = new com.toedter.calendar.JDateChooser();
        doctorDetailsPanel = new javax.swing.JPanel();
        docNameTxt = new javax.swing.JLabel();
        regNoTxt = new javax.swing.JLabel();
        degreeLbl = new javax.swing.JLabel();
        licenseUpdateMessageLbl = new javax.swing.JLabel();
        clinicDetailsPanel = new javax.swing.JPanel();
        clinicHeaderLbl = new javax.swing.JLabel();
        jMenuBar = new javax.swing.JMenuBar();
        profileMenu = new javax.swing.JMenu();
        myProfileItem = new javax.swing.JMenuItem();
        myClinicItem = new javax.swing.JMenuItem();
        backupDirectoryItem = new javax.swing.JMenuItem();
        registerMenu = new javax.swing.JMenu();
        patientsItem = new javax.swing.JMenuItem();
        doctorItem = new javax.swing.JMenuItem();
        employeeItem = new javax.swing.JMenuItem();
        appointmentItem = new javax.swing.JMenuItem();
        financialMenu = new javax.swing.JMenu();
        expenseTrackingItem = new javax.swing.JMenuItem();
        reportMenu = new javax.swing.JMenu();
        reportItem = new javax.swing.JMenuItem();
        themeMenu = new javax.swing.JMenu();
        jItemMetal = new javax.swing.JMenuItem();
        jItemMotif = new javax.swing.JMenuItem();
        jItemNimbus = new javax.swing.JMenuItem();
        jItemLiquid = new javax.swing.JMenuItem();
        jItemGTK = new javax.swing.JMenuItem();
        jItemWindows = new javax.swing.JMenuItem();
        settingsMenu = new javax.swing.JMenu();
        masterDataSubMenu = new javax.swing.JMenu();
        treatmentItem = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        preMedicalHistoryItem = new javax.swing.JMenuItem();
        medicineTypesItem = new javax.swing.JMenuItem();
        medicinesItem = new javax.swing.JMenuItem();
        jSeparator = new javax.swing.JPopupMenu.Separator();
        labWorkNameItem = new javax.swing.JMenuItem();
        labItem = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        referedByItem = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        cityMasterItem = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        expenseCategoryItem = new javax.swing.JMenuItem();
        settingsItem = new javax.swing.JMenuItem();
        helpMenu = new javax.swing.JMenu();
        helpItem = new javax.swing.JMenuItem();
        exitMenu = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Shree Ram Dental");
        setBackground(new java.awt.Color(226, 225, 225));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setName("framePrincipal"); // NOI18N
        setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width,Toolkit.getDefaultToolkit().getScreenSize().height-Toolkit.getDefaultToolkit().getScreenInsets(getGraphicsConfiguration()).bottom));
        setResizable(false);
        addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                formFocusGained(evt);
            }
        });
        addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                formWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
            }
        });

        footerPanel.setBackground(javax.swing.UIManager.getDefaults().getColor("ComboBox.disabledForeground"));
        footerPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        footerPanel.setForeground(new java.awt.Color(38, 126, 226));

        userLbl.setText("Welcome, ");

        dateLbl.setText("Date: ");

        javax.swing.GroupLayout footerPanelLayout = new javax.swing.GroupLayout(footerPanel);
        footerPanel.setLayout(footerPanelLayout);
        footerPanelLayout.setHorizontalGroup(
            footerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(footerPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(userLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(dateLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39))
        );
        footerPanelLayout.setVerticalGroup(
            footerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(footerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(userLbl)
                .addComponent(dateLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        todayInfoPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        newPatientTodayLbl.setBackground(new java.awt.Color(255, 255, 255));
        newPatientTodayLbl.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        newPatientTodayLbl.setForeground(new java.awt.Color(51, 51, 255));
        newPatientTodayLbl.setText("New Patients Registered Today");

        newPatientsTodayTxt.setBackground(new java.awt.Color(0, 51, 255));
        newPatientsTodayTxt.setForeground(new java.awt.Color(255, 255, 153));
        newPatientsTodayTxt.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        newPatientsTodayTxt.setText("0");
        newPatientsTodayTxt.setFocusable(false);
        newPatientsTodayTxt.setOpaque(true);
        newPatientsTodayTxt.setRequestFocusEnabled(false);

        patientVisitTodayLbl.setBackground(new java.awt.Color(255, 255, 255));
        patientVisitTodayLbl.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        patientVisitTodayLbl.setForeground(new java.awt.Color(51, 51, 255));
        patientVisitTodayLbl.setText("Patients Visits Today");

        patientsVisitTodayTxt.setBackground(new java.awt.Color(0, 51, 255));
        patientsVisitTodayTxt.setForeground(new java.awt.Color(255, 255, 153));
        patientsVisitTodayTxt.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        patientsVisitTodayTxt.setText("0");
        patientsVisitTodayTxt.setFocusable(false);
        patientsVisitTodayTxt.setOpaque(true);
        patientsVisitTodayTxt.setRequestFocusEnabled(false);

        scheduledAppointmentsLbl.setBackground(new java.awt.Color(255, 255, 255));
        scheduledAppointmentsLbl.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        scheduledAppointmentsLbl.setForeground(new java.awt.Color(51, 51, 255));
        scheduledAppointmentsLbl.setText("Scheduled Appointments");

        scheduledAppointmentsTxt.setBackground(new java.awt.Color(0, 51, 255));
        scheduledAppointmentsTxt.setForeground(new java.awt.Color(255, 255, 153));
        scheduledAppointmentsTxt.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        scheduledAppointmentsTxt.setText("0");
        scheduledAppointmentsTxt.setFocusable(false);
        scheduledAppointmentsTxt.setOpaque(true);
        scheduledAppointmentsTxt.setRequestFocusEnabled(false);

        labWorkPendingLbl.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        labWorkPendingLbl.setForeground(new java.awt.Color(51, 51, 255));
        labWorkPendingLbl.setText("Lab Work Pending");

        labWorkPendingTxt.setBackground(new java.awt.Color(0, 51, 255));
        labWorkPendingTxt.setForeground(new java.awt.Color(255, 255, 153));
        labWorkPendingTxt.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labWorkPendingTxt.setText("0");
        labWorkPendingTxt.setOpaque(true);

        labWorkSubmittedLbl.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        labWorkSubmittedLbl.setForeground(new java.awt.Color(51, 51, 255));
        labWorkSubmittedLbl.setText("Lab Work Submitted");

        labWorkSubmittedTxt.setBackground(new java.awt.Color(0, 51, 255));
        labWorkSubmittedTxt.setForeground(new java.awt.Color(255, 255, 153));
        labWorkSubmittedTxt.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labWorkSubmittedTxt.setText("0");
        labWorkSubmittedTxt.setOpaque(true);

        labWorkReceivedLbl.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        labWorkReceivedLbl.setForeground(new java.awt.Color(51, 51, 255));
        labWorkReceivedLbl.setText("Lab Work Received");

        labWorkReceivedTxt.setBackground(new java.awt.Color(0, 51, 255));
        labWorkReceivedTxt.setForeground(new java.awt.Color(255, 255, 153));
        labWorkReceivedTxt.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labWorkReceivedTxt.setText("0");
        labWorkReceivedTxt.setOpaque(true);

        javax.swing.GroupLayout todayInfoPanelLayout = new javax.swing.GroupLayout(todayInfoPanel);
        todayInfoPanel.setLayout(todayInfoPanelLayout);
        todayInfoPanelLayout.setHorizontalGroup(
            todayInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(todayInfoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(todayInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, todayInfoPanelLayout.createSequentialGroup()
                        .addGroup(todayInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(patientVisitTodayLbl, javax.swing.GroupLayout.DEFAULT_SIZE, 247, Short.MAX_VALUE)
                            .addComponent(scheduledAppointmentsLbl, javax.swing.GroupLayout.DEFAULT_SIZE, 247, Short.MAX_VALUE)
                            .addComponent(newPatientTodayLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18))
                    .addGroup(todayInfoPanelLayout.createSequentialGroup()
                        .addGroup(todayInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labWorkReceivedLbl)
                            .addGroup(todayInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(labWorkPendingLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(labWorkSubmittedLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(18, 18, 18)))
                .addGroup(todayInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, todayInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(newPatientsTodayTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(patientsVisitTodayTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(scheduledAppointmentsTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(labWorkReceivedTxt)
                    .addGroup(todayInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(labWorkSubmittedTxt)
                        .addComponent(labWorkPendingTxt, javax.swing.GroupLayout.Alignment.TRAILING)))
                .addContainerGap(34, Short.MAX_VALUE))
        );

        todayInfoPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {labWorkPendingLbl, labWorkReceivedLbl, labWorkSubmittedLbl, patientVisitTodayLbl, scheduledAppointmentsLbl});

        todayInfoPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {labWorkPendingTxt, labWorkReceivedTxt, labWorkSubmittedTxt, newPatientsTodayTxt, patientsVisitTodayTxt, scheduledAppointmentsTxt});

        todayInfoPanelLayout.setVerticalGroup(
            todayInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(todayInfoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(todayInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(newPatientTodayLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(newPatientsTodayTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(todayInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(patientVisitTodayLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(patientsVisitTodayTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(todayInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scheduledAppointmentsLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(scheduledAppointmentsTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(todayInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labWorkPendingLbl)
                    .addComponent(labWorkPendingTxt))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(todayInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labWorkSubmittedLbl)
                    .addComponent(labWorkSubmittedTxt))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(todayInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labWorkReceivedLbl)
                    .addComponent(labWorkReceivedTxt)))
        );

        todayInfoPanelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {labWorkPendingLbl, labWorkPendingTxt, labWorkReceivedLbl, labWorkReceivedTxt, labWorkSubmittedLbl, labWorkSubmittedTxt, newPatientTodayLbl, newPatientsTodayTxt, patientVisitTodayLbl, patientsVisitTodayTxt, scheduledAppointmentsLbl, scheduledAppointmentsTxt});

        exitBtn.setBackground(new java.awt.Color(152, 172, 181));
        exitBtn.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        exitBtn.setForeground(new java.awt.Color(9, 68, 158));
        exitBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/EkDant/icones/Gnome-Application-Exit-48.png"))); // NOI18N
        exitBtn.setMnemonic('x');
        exitBtn.setText("Exit");
        exitBtn.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        exitBtn.setMargin(new java.awt.Insets(2, 2, 2, 2));
        exitBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitBtnActionPerformed(evt);
            }
        });

        backupBtn.setBackground(new java.awt.Color(152, 172, 181));
        backupBtn.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        backupBtn.setForeground(new java.awt.Color(9, 68, 158));
        backupBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/EkDant/icones/backup.png"))); // NOI18N
        backupBtn.setMnemonic('B');
        backupBtn.setText("Backup");
        backupBtn.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        backupBtn.setMargin(new java.awt.Insets(2, 2, 2, 2));
        backupBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backupBtnActionPerformed(evt);
            }
        });

        reportsBtn.setBackground(new java.awt.Color(152, 172, 181));
        reportsBtn.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        reportsBtn.setForeground(new java.awt.Color(9, 68, 158));
        reportsBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/EkDant/icones/Report 48.png"))); // NOI18N
        reportsBtn.setMnemonic('R');
        reportsBtn.setText("Reports");
        reportsBtn.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        reportsBtn.setMargin(new java.awt.Insets(2, 2, 2, 2));
        reportsBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reportsBtnActionPerformed(evt);
            }
        });

        appointmentBtn.setBackground(new java.awt.Color(152, 172, 181));
        appointmentBtn.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        appointmentBtn.setForeground(new java.awt.Color(9, 68, 158));
        appointmentBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/EkDant/icones/Appointment.jpg"))); // NOI18N
        appointmentBtn.setMnemonic('A');
        appointmentBtn.setText("Appointment");
        appointmentBtn.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        appointmentBtn.setMargin(new java.awt.Insets(2, 2, 2, 2));
        appointmentBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                appointmentBtnActionPerformed(evt);
            }
        });

        patientsBtn.setBackground(new java.awt.Color(152, 172, 181));
        patientsBtn.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        patientsBtn.setForeground(new java.awt.Color(9, 68, 158));
        patientsBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/EkDant/icones/Patient Boy 1-48.png"))); // NOI18N
        patientsBtn.setMnemonic('P');
        patientsBtn.setText("Patients");
        patientsBtn.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        patientsBtn.setMargin(new java.awt.Insets(2, 2, 2, 2));
        patientsBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                patientsBtnActionPerformed(evt);
            }
        });

        labBtn.setBackground(new java.awt.Color(152, 172, 181));
        labBtn.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        labBtn.setForeground(new java.awt.Color(9, 68, 158));
        labBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/EkDant/icones/lab.jpg"))); // NOI18N
        labBtn.setMnemonic('L');
        labBtn.setText("Lab Work");
        labBtn.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        labBtn.setMargin(new java.awt.Insets(2, 2, 2, 2));
        labBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                labBtnActionPerformed(evt);
            }
        });

        expenseBtn.setBackground(new java.awt.Color(152, 172, 181));
        expenseBtn.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        expenseBtn.setForeground(new java.awt.Color(9, 68, 158));
        expenseBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/EkDant/icones/expense1.jpg"))); // NOI18N
        expenseBtn.setMnemonic('E');
        expenseBtn.setText("Expenses");
        expenseBtn.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        expenseBtn.setMargin(new java.awt.Insets(2, 2, 2, 2));
        expenseBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                expenseBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout mainButtonPanelLayout = new javax.swing.GroupLayout(mainButtonPanel);
        mainButtonPanel.setLayout(mainButtonPanelLayout);
        mainButtonPanelLayout.setHorizontalGroup(
            mainButtonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainButtonPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainButtonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(patientsBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(appointmentBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(expenseBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(reportsBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(backupBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(exitBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        mainButtonPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {appointmentBtn, backupBtn, exitBtn, expenseBtn, labBtn, patientsBtn, reportsBtn});

        mainButtonPanelLayout.setVerticalGroup(
            mainButtonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainButtonPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(patientsBtn)
                .addGap(18, 18, 18)
                .addComponent(appointmentBtn)
                .addGap(18, 18, 18)
                .addComponent(expenseBtn)
                .addGap(18, 18, 18)
                .addComponent(labBtn)
                .addGap(18, 18, 18)
                .addComponent(reportsBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(backupBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(exitBtn)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        mainButtonPanelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {appointmentBtn, backupBtn, exitBtn, expenseBtn, labBtn, patientsBtn, reportsBtn});

        yesterdayInfoPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        newPatientsYesterdayLbl.setBackground(new java.awt.Color(255, 255, 255));
        newPatientsYesterdayLbl.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        newPatientsYesterdayLbl.setForeground(new java.awt.Color(51, 51, 255));
        newPatientsYesterdayLbl.setText("New Patients Registered Yesterday");

        newPatientsYesterdayTxt.setBackground(new java.awt.Color(0, 51, 255));
        newPatientsYesterdayTxt.setForeground(new java.awt.Color(255, 255, 153));
        newPatientsYesterdayTxt.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        newPatientsYesterdayTxt.setText("0");
        newPatientsYesterdayTxt.setFocusable(false);
        newPatientsYesterdayTxt.setOpaque(true);
        newPatientsYesterdayTxt.setRequestFocusEnabled(false);

        patientsVisitYesterdayLbl.setBackground(new java.awt.Color(255, 255, 255));
        patientsVisitYesterdayLbl.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        patientsVisitYesterdayLbl.setForeground(new java.awt.Color(51, 51, 255));
        patientsVisitYesterdayLbl.setText("Patients Visits Yesterday");

        patientsVisitYesterdayTxt.setBackground(new java.awt.Color(0, 51, 255));
        patientsVisitYesterdayTxt.setForeground(new java.awt.Color(255, 255, 153));
        patientsVisitYesterdayTxt.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        patientsVisitYesterdayTxt.setText("0");
        patientsVisitYesterdayTxt.setFocusable(false);
        patientsVisitYesterdayTxt.setOpaque(true);
        patientsVisitYesterdayTxt.setRequestFocusEnabled(false);

        javax.swing.GroupLayout yesterdayInfoPanelLayout = new javax.swing.GroupLayout(yesterdayInfoPanel);
        yesterdayInfoPanel.setLayout(yesterdayInfoPanelLayout);
        yesterdayInfoPanelLayout.setHorizontalGroup(
            yesterdayInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(yesterdayInfoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(yesterdayInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(patientsVisitYesterdayLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(newPatientsYesterdayLbl, javax.swing.GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(yesterdayInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(newPatientsYesterdayTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(patientsVisitYesterdayTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 41, Short.MAX_VALUE))
        );
        yesterdayInfoPanelLayout.setVerticalGroup(
            yesterdayInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, yesterdayInfoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(yesterdayInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(newPatientsYesterdayLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(newPatientsYesterdayTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(yesterdayInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(patientsVisitYesterdayLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(patientsVisitYesterdayTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(154, Short.MAX_VALUE))
        );

        customInfoPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        newPatientsPeriodLbl.setBackground(new java.awt.Color(255, 255, 255));
        newPatientsPeriodLbl.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        newPatientsPeriodLbl.setForeground(new java.awt.Color(51, 51, 255));
        newPatientsPeriodLbl.setText("New Patients Registered");

        patientsVisitPeriodTxt.setBackground(new java.awt.Color(0, 51, 255));
        patientsVisitPeriodTxt.setForeground(new java.awt.Color(255, 255, 153));
        patientsVisitPeriodTxt.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        patientsVisitPeriodTxt.setText("0");
        patientsVisitPeriodTxt.setFocusable(false);
        patientsVisitPeriodTxt.setOpaque(true);
        patientsVisitPeriodTxt.setRequestFocusEnabled(false);

        newPatientsPeriodTxt.setBackground(new java.awt.Color(0, 51, 255));
        newPatientsPeriodTxt.setForeground(new java.awt.Color(255, 255, 153));
        newPatientsPeriodTxt.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        newPatientsPeriodTxt.setText("0");
        newPatientsPeriodTxt.setFocusable(false);
        newPatientsPeriodTxt.setOpaque(true);
        newPatientsPeriodTxt.setRequestFocusEnabled(false);

        patientsVisitPeriodLbl.setBackground(new java.awt.Color(255, 255, 255));
        patientsVisitPeriodLbl.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        patientsVisitPeriodLbl.setForeground(new java.awt.Color(51, 51, 255));
        patientsVisitPeriodLbl.setText("Patients Visits");

        startDateLbl.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        startDateLbl.setForeground(new java.awt.Color(51, 51, 255));
        startDateLbl.setText("Start Date");

        endDateLbl.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        endDateLbl.setForeground(new java.awt.Color(51, 51, 255));
        endDateLbl.setText("End Date");

        javax.swing.GroupLayout customInfoPanelLayout = new javax.swing.GroupLayout(customInfoPanel);
        customInfoPanel.setLayout(customInfoPanelLayout);
        customInfoPanelLayout.setHorizontalGroup(
            customInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, customInfoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(customInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, customInfoPanelLayout.createSequentialGroup()
                        .addGroup(customInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(patientsVisitPeriodLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(newPatientsPeriodLbl, javax.swing.GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(patientsVisitPeriodTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, customInfoPanelLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 80, Short.MAX_VALUE)
                        .addGroup(customInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(newPatientsPeriodTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(customInfoPanelLayout.createSequentialGroup()
                                .addGroup(customInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(customInfoPanelLayout.createSequentialGroup()
                                        .addComponent(startDateLbl)
                                        .addGap(41, 41, 41))
                                    .addGroup(customInfoPanelLayout.createSequentialGroup()
                                        .addComponent(startDateDC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)))
                                .addGroup(customInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(endDateLbl)
                                    .addComponent(endDateDC, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(26, 26, 26))))))
        );
        customInfoPanelLayout.setVerticalGroup(
            customInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, customInfoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(customInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(startDateLbl, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(endDateLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(customInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(startDateDC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(endDateDC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(customInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(newPatientsPeriodLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(newPatientsPeriodTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(customInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(patientsVisitPeriodLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(patientsVisitPeriodTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(53, 53, 53))
        );

        docNameTxt.setFont(new java.awt.Font("Verdana", 1, 24)); // NOI18N
        docNameTxt.setForeground(new java.awt.Color(51, 51, 255));

        regNoTxt.setFont(new java.awt.Font("Verdana", 0, 18)); // NOI18N
        regNoTxt.setForeground(new java.awt.Color(51, 51, 255));

        degreeLbl.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        degreeLbl.setForeground(new java.awt.Color(51, 51, 255));
        degreeLbl.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        degreeLbl.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        javax.swing.GroupLayout doctorDetailsPanelLayout = new javax.swing.GroupLayout(doctorDetailsPanel);
        doctorDetailsPanel.setLayout(doctorDetailsPanelLayout);
        doctorDetailsPanelLayout.setHorizontalGroup(
            doctorDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, doctorDetailsPanelLayout.createSequentialGroup()
                .addGap(138, 138, 138)
                .addGroup(doctorDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(doctorDetailsPanelLayout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(regNoTxt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(doctorDetailsPanelLayout.createSequentialGroup()
                        .addComponent(docNameTxt, javax.swing.GroupLayout.DEFAULT_SIZE, 82, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(degreeLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(98, 98, 98))
        );
        doctorDetailsPanelLayout.setVerticalGroup(
            doctorDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(doctorDetailsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(doctorDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(docNameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(degreeLbl))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(regNoTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        doctorDetailsPanelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {degreeLbl, docNameTxt});

        licenseUpdateMessageLbl.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        licenseUpdateMessageLbl.setForeground(new java.awt.Color(255, 51, 51));

        clinicHeaderLbl.setFont(new java.awt.Font("Verdana", 1, 24)); // NOI18N
        clinicHeaderLbl.setForeground(new java.awt.Color(51, 51, 255));
        clinicHeaderLbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout clinicDetailsPanelLayout = new javax.swing.GroupLayout(clinicDetailsPanel);
        clinicDetailsPanel.setLayout(clinicDetailsPanelLayout);
        clinicDetailsPanelLayout.setHorizontalGroup(
            clinicDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(clinicDetailsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(clinicHeaderLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        clinicDetailsPanelLayout.setVerticalGroup(
            clinicDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(clinicDetailsPanelLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(clinicHeaderLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jMenuBar.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jMenuBar.setAlignmentX(2.5F);
        jMenuBar.setAlignmentY(2.0F);

        profileMenu.setText("    Profile    ");

        myProfileItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/EkDant/icones/Dentist Blue.png"))); // NOI18N
        myProfileItem.setText("My Profile");
        myProfileItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                myProfileItemActionPerformed(evt);
            }
        });
        profileMenu.add(myProfileItem);

        myClinicItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/EkDant/icones/Hospital.png"))); // NOI18N
        myClinicItem.setText("My Clinic");
        myClinicItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                myClinicItemActionPerformed(evt);
            }
        });
        profileMenu.add(myClinicItem);

        backupDirectoryItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/EkDant/icones/Document-icon.png"))); // NOI18N
        backupDirectoryItem.setText("Data Backup Directory");
        backupDirectoryItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backupDirectoryItemActionPerformed(evt);
            }
        });
        profileMenu.add(backupDirectoryItem);

        jMenuBar.add(profileMenu);

        registerMenu.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        registerMenu.setText("     Register     ");

        patientsItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_MASK));
        patientsItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/EkDant/icones/Patient Boy 1.png"))); // NOI18N
        patientsItem.setText("Patients");
        patientsItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                patientsItemActionPerformed(evt);
            }
        });
        registerMenu.add(patientsItem);

        doctorItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.CTRL_MASK));
        doctorItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/EkDant/icones/Dentist.png"))); // NOI18N
        doctorItem.setText("Doctors");
        doctorItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                doctorItemActionPerformed(evt);
            }
        });
        registerMenu.add(doctorItem);

        employeeItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_MASK));
        employeeItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/EkDant/icones/Hygienist 2 Green.png"))); // NOI18N
        employeeItem.setText("Employees");
        employeeItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                employeeItemActionPerformed(evt);
            }
        });
        registerMenu.add(employeeItem);

        appointmentItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_MASK));
        appointmentItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/EkDant/icones/Appointment 2.png"))); // NOI18N
        appointmentItem.setText("Appointment");
        appointmentItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                appointmentItemActionPerformed(evt);
            }
        });
        registerMenu.add(appointmentItem);

        jMenuBar.add(registerMenu);

        financialMenu.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        financialMenu.setText("     Financial     ");

        expenseTrackingItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.CTRL_MASK));
        expenseTrackingItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/EkDant/icones/expense.png"))); // NOI18N
        expenseTrackingItem.setText("eXpenses");
        expenseTrackingItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                expenseTrackingItemActionPerformed(evt);
            }
        });
        financialMenu.add(expenseTrackingItem);

        jMenuBar.add(financialMenu);

        reportMenu.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        reportMenu.setText("     Reports     ");

        reportItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.CTRL_MASK));
        reportItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/EkDant/icones/Calendar.png"))); // NOI18N
        reportItem.setText("Reports");
        reportItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reportItemActionPerformed(evt);
            }
        });
        reportMenu.add(reportItem);

        jMenuBar.add(reportMenu);

        themeMenu.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        themeMenu.setText("     Theme     ");

        jItemMetal.setText("Metal");
        jItemMetal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jItemMetalActionPerformed(evt);
            }
        });
        themeMenu.add(jItemMetal);

        jItemMotif.setText("Motif");
        jItemMotif.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jItemMotifActionPerformed(evt);
            }
        });
        themeMenu.add(jItemMotif);

        jItemNimbus.setText("Nimbus");
        jItemNimbus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jItemNimbusActionPerformed(evt);
            }
        });
        themeMenu.add(jItemNimbus);

        jItemLiquid.setText("Liquid");
        jItemLiquid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jItemLiquidActionPerformed(evt);
            }
        });
        themeMenu.add(jItemLiquid);

        jItemGTK.setText("GTK");
        jItemGTK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jItemGTKActionPerformed(evt);
            }
        });
        themeMenu.add(jItemGTK);

        jItemWindows.setText("Windows");
        jItemWindows.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jItemWindowsActionPerformed(evt);
            }
        });
        themeMenu.add(jItemWindows);

        jMenuBar.add(themeMenu);

        settingsMenu.setText("     Settings     ");

        masterDataSubMenu.setText("Master Data");

        treatmentItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_T, java.awt.event.InputEvent.CTRL_MASK));
        treatmentItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/EkDant/icones/Treatment.png"))); // NOI18N
        treatmentItem.setText("Treatments");
        treatmentItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                treatmentItemActionPerformed(evt);
            }
        });
        masterDataSubMenu.add(treatmentItem);
        masterDataSubMenu.add(jSeparator1);

        preMedicalHistoryItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/EkDant/icones/Medical Planner.png"))); // NOI18N
        preMedicalHistoryItem.setText("Pre Medical History");
        preMedicalHistoryItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                preMedicalHistoryItemActionPerformed(evt);
            }
        });
        masterDataSubMenu.add(preMedicalHistoryItem);

        medicineTypesItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/EkDant/icones/Medicine Types 1.jpg"))); // NOI18N
        medicineTypesItem.setText("Medicine Types");
        medicineTypesItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                medicineTypesItemActionPerformed(evt);
            }
        });
        masterDataSubMenu.add(medicineTypesItem);

        medicinesItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/EkDant/icones/Medicines.jpg"))); // NOI18N
        medicinesItem.setText("Medicines");
        medicinesItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                medicinesItemActionPerformed(evt);
            }
        });
        masterDataSubMenu.add(medicinesItem);
        masterDataSubMenu.add(jSeparator);

        labWorkNameItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/EkDant/icones/labName.png"))); // NOI18N
        labWorkNameItem.setText("Lab Work Name");
        labWorkNameItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                labWorkNameItemActionPerformed(evt);
            }
        });
        masterDataSubMenu.add(labWorkNameItem);

        labItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/EkDant/icones/Teeth and Gums.png"))); // NOI18N
        labItem.setText("Lab");
        labItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                labItemActionPerformed(evt);
            }
        });
        masterDataSubMenu.add(labItem);
        masterDataSubMenu.add(jSeparator4);

        referedByItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/EkDant/icones/Doctor Directory 3.png"))); // NOI18N
        referedByItem.setText("Refered By");
        referedByItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                referedByItemActionPerformed(evt);
            }
        });
        masterDataSubMenu.add(referedByItem);
        masterDataSubMenu.add(jSeparator2);

        cityMasterItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/EkDant/icones/cities.png"))); // NOI18N
        cityMasterItem.setText("City");
        cityMasterItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cityMasterItemActionPerformed(evt);
            }
        });
        masterDataSubMenu.add(cityMasterItem);
        masterDataSubMenu.add(jSeparator3);

        expenseCategoryItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/EkDant/icones/expense.png"))); // NOI18N
        expenseCategoryItem.setText("Expense Category");
        expenseCategoryItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                expenseCategoryItemActionPerformed(evt);
            }
        });
        masterDataSubMenu.add(expenseCategoryItem);

        settingsItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/EkDant/icones/setting.png"))); // NOI18N
        settingsItem.setText("Settings");
        settingsItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                settingsItemActionPerformed(evt);
            }
        });
        masterDataSubMenu.add(settingsItem);

        settingsMenu.add(masterDataSubMenu);

        jMenuBar.add(settingsMenu);

        helpMenu.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        helpMenu.setText("      Help     ");

        helpItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/EkDant/images/proline-logo_ico.png"))); // NOI18N
        helpItem.setText("Help");
        helpItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                helpItemActionPerformed(evt);
            }
        });
        helpMenu.add(helpItem);

        jMenuBar.add(helpMenu);

        exitMenu.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        exitMenu.setText("     Exit     ");
        exitMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                exitMenuMouseClicked(evt);
            }
        });
        jMenuBar.add(exitMenu);

        setJMenuBar(jMenuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(mainButtonPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 102, Short.MAX_VALUE)
                                .addComponent(licenseUpdateMessageLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 1034, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(clinicDetailsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(todayInfoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(yesterdayInfoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(doctorDetailsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(customInfoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))))))
                    .addComponent(footerPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(doctorDetailsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(clinicDetailsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(81, 81, 81)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(todayInfoPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(yesterdayInfoPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(customInfoPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(licenseUpdateMessageLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(mainButtonPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(footerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void exitMenuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exitMenuMouseClicked
        exit();
    }//GEN-LAST:event_exitMenuMouseClicked

    private void patientsItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_patientsItemActionPerformed
        Patients.getInstance(this).setVisible(true);
    }//GEN-LAST:event_patientsItemActionPerformed

    private void jItemWindowsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jItemWindowsActionPerformed
        lookAndFeel = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
        LookAndFeel();
    }//GEN-LAST:event_jItemWindowsActionPerformed

    private void jItemNimbusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jItemNimbusActionPerformed
        lookAndFeel = "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel";
        LookAndFeel();
    }//GEN-LAST:event_jItemNimbusActionPerformed

    private void jItemMetalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jItemMetalActionPerformed
        lookAndFeel = "javax.swing.plaf.metal.MetalLookAndFeel";
        LookAndFeel();
    }//GEN-LAST:event_jItemMetalActionPerformed

    private void jItemMotifActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jItemMotifActionPerformed
        lookAndFeel = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
        LookAndFeel();
    }//GEN-LAST:event_jItemMotifActionPerformed

    private void jItemGTKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jItemGTKActionPerformed
        lookAndFeel = "com.sun.java.swing.plaf.gtk.GTKLookAndFeel";
        LookAndFeel();
    }//GEN-LAST:event_jItemGTKActionPerformed

    private void jItemLiquidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jItemLiquidActionPerformed
        lookAndFeel = "com.birosoft.liquid.LiquidLookAndFeel";
        LookAndFeel();
    }//GEN-LAST:event_jItemLiquidActionPerformed

    private void helpItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_helpItemActionPerformed
         new About().setVisible(true);
    }//GEN-LAST:event_helpItemActionPerformed

    private void exitBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitBtnActionPerformed
        exit();
    }//GEN-LAST:event_exitBtnActionPerformed

    private void employeeItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_employeeItemActionPerformed
        new Employees().setVisible(true);
    }//GEN-LAST:event_employeeItemActionPerformed

    private void treatmentItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_treatmentItemActionPerformed
        new Treatment().setVisible(true);
    }//GEN-LAST:event_treatmentItemActionPerformed

    private void doctorItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_doctorItemActionPerformed
         new Doctors().setVisible(true);
    }//GEN-LAST:event_doctorItemActionPerformed

    private void patientsBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_patientsBtnActionPerformed
        Patients patient = Patients.getInstance(this);
        patient.setVisible(true);
        patient.setState(JFrame.NORMAL);
    }//GEN-LAST:event_patientsBtnActionPerformed

    private void appointmentBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_appointmentBtnActionPerformed
        Appointments appointment = Appointments.getInstance(0);
        appointment.setVisible(true);
        appointment.setState(JFrame.NORMAL);
    }//GEN-LAST:event_appointmentBtnActionPerformed

    private void reportsBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reportsBtnActionPerformed
        new Reports().setVisible(true);
    }//GEN-LAST:event_reportsBtnActionPerformed

    private void reportItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reportItemActionPerformed
        new Reports().setVisible(true);
    }//GEN-LAST:event_reportItemActionPerformed

    private void backupBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backupBtnActionPerformed
        backUp();       
    }//GEN-LAST:event_backupBtnActionPerformed

    private void preMedicalHistoryItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_preMedicalHistoryItemActionPerformed
        new PreMedicalHistory().setVisible(true);
    }//GEN-LAST:event_preMedicalHistoryItemActionPerformed

    private void cityMasterItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cityMasterItemActionPerformed
        new Cities(false).setVisible(true);
    }//GEN-LAST:event_cityMasterItemActionPerformed

    private void medicineTypesItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_medicineTypesItemActionPerformed
        new MedicineType().setVisible(true);
    }//GEN-LAST:event_medicineTypesItemActionPerformed

    private void medicinesItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_medicinesItemActionPerformed
        new Medicine().setVisible(true);
    }//GEN-LAST:event_medicinesItemActionPerformed

    private void referedByItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_referedByItemActionPerformed
        new ReferedBy().setVisible(true);
    }//GEN-LAST:event_referedByItemActionPerformed

    private void settingsItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_settingsItemActionPerformed
        new Settings().setVisible(true);
    }//GEN-LAST:event_settingsItemActionPerformed

    private void labBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_labBtnActionPerformed
        new LabWorkTracking().setVisible(true);
    }//GEN-LAST:event_labBtnActionPerformed

    private void expenseBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_expenseBtnActionPerformed
        //new ExpenseTracking().setVisible(true);
    }//GEN-LAST:event_expenseBtnActionPerformed

    private void formFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_formFocusGained
        
    }//GEN-LAST:event_formFocusGained

    private void formWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowGainedFocus
        initMainPage();
    }//GEN-LAST:event_formWindowGainedFocus

    private void expenseCategoryItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_expenseCategoryItemActionPerformed
        new ExpenseCategories().setVisible(true);
    }//GEN-LAST:event_expenseCategoryItemActionPerformed

    private void appointmentItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_appointmentItemActionPerformed
       Appointments appointment = Appointments.getInstance(0);
        appointment.setVisible(true);
        appointment.setState(JFrame.NORMAL);
    }//GEN-LAST:event_appointmentItemActionPerformed

    private void labItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_labItemActionPerformed
        new Lab().setVisible(true);
    }//GEN-LAST:event_labItemActionPerformed

    private void labWorkNameItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_labWorkNameItemActionPerformed
        new LabWorkName().setVisible(true);
    }//GEN-LAST:event_labWorkNameItemActionPerformed

    private void startDateDCInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_startDateDCInputMethodTextChanged
        statasticsForPeriod();
    }//GEN-LAST:event_startDateDCInputMethodTextChanged

    private void myProfileItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_myProfileItemActionPerformed
        new InitialDoctorSettings().setVisible(true);
    }//GEN-LAST:event_myProfileItemActionPerformed

    private void myClinicItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_myClinicItemActionPerformed
        new InitialClinicSettings().setVisible(true);
    }//GEN-LAST:event_myClinicItemActionPerformed

    private void expenseTrackingItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_expenseTrackingItemActionPerformed
        //new ExpenseTracking().setVisible(true);
    }//GEN-LAST:event_expenseTrackingItemActionPerformed

    private void backupDirectoryItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backupDirectoryItemActionPerformed
        new BackupDirectory().setVisible(true);
    }//GEN-LAST:event_backupDirectoryItemActionPerformed
  
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton appointmentBtn;
    private javax.swing.JMenuItem appointmentItem;
    private javax.swing.JButton backupBtn;
    private javax.swing.JMenuItem backupDirectoryItem;
    private javax.swing.JMenuItem cityMasterItem;
    private javax.swing.JPanel clinicDetailsPanel;
    private javax.swing.JLabel clinicHeaderLbl;
    private javax.swing.JPanel customInfoPanel;
    private javax.swing.JLabel dateLbl;
    private javax.swing.JLabel degreeLbl;
    private javax.swing.JLabel docNameTxt;
    private javax.swing.JPanel doctorDetailsPanel;
    private javax.swing.JMenuItem doctorItem;
    private javax.swing.JMenuItem employeeItem;
    private com.toedter.calendar.JDateChooser endDateDC;
    private javax.swing.JLabel endDateLbl;
    private javax.swing.JButton exitBtn;
    private javax.swing.JMenu exitMenu;
    private javax.swing.JButton expenseBtn;
    private javax.swing.JMenuItem expenseCategoryItem;
    private javax.swing.JMenuItem expenseTrackingItem;
    private javax.swing.JMenu financialMenu;
    private javax.swing.JPanel footerPanel;
    private javax.swing.JMenuItem helpItem;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JMenuItem jItemGTK;
    private javax.swing.JMenuItem jItemLiquid;
    private javax.swing.JMenuItem jItemMetal;
    private javax.swing.JMenuItem jItemMotif;
    private javax.swing.JMenuItem jItemNimbus;
    private javax.swing.JMenuItem jItemWindows;
    private javax.swing.JMenuBar jMenuBar;
    private javax.swing.JPopupMenu.Separator jSeparator;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JButton labBtn;
    private javax.swing.JMenuItem labItem;
    private javax.swing.JMenuItem labWorkNameItem;
    private javax.swing.JLabel labWorkPendingLbl;
    private javax.swing.JLabel labWorkPendingTxt;
    private javax.swing.JLabel labWorkReceivedLbl;
    private javax.swing.JLabel labWorkReceivedTxt;
    private javax.swing.JLabel labWorkSubmittedLbl;
    private javax.swing.JLabel labWorkSubmittedTxt;
    private javax.swing.JLabel licenseUpdateMessageLbl;
    private javax.swing.JPanel mainButtonPanel;
    private javax.swing.JMenu masterDataSubMenu;
    private javax.swing.JMenuItem medicineTypesItem;
    private javax.swing.JMenuItem medicinesItem;
    private javax.swing.JMenuItem myClinicItem;
    private javax.swing.JMenuItem myProfileItem;
    private javax.swing.JLabel newPatientTodayLbl;
    private javax.swing.JLabel newPatientsPeriodLbl;
    private javax.swing.JLabel newPatientsPeriodTxt;
    private javax.swing.JLabel newPatientsTodayTxt;
    private javax.swing.JLabel newPatientsYesterdayLbl;
    private javax.swing.JLabel newPatientsYesterdayTxt;
    private javax.swing.JLabel patientVisitTodayLbl;
    private javax.swing.JButton patientsBtn;
    private javax.swing.JMenuItem patientsItem;
    private javax.swing.JLabel patientsVisitPeriodLbl;
    private javax.swing.JLabel patientsVisitPeriodTxt;
    private javax.swing.JLabel patientsVisitTodayTxt;
    private javax.swing.JLabel patientsVisitYesterdayLbl;
    private javax.swing.JLabel patientsVisitYesterdayTxt;
    private javax.swing.JMenuItem preMedicalHistoryItem;
    private javax.swing.JMenu profileMenu;
    private javax.swing.JMenuItem referedByItem;
    private javax.swing.JLabel regNoTxt;
    private javax.swing.JMenu registerMenu;
    private javax.swing.JMenuItem reportItem;
    private javax.swing.JMenu reportMenu;
    private javax.swing.JButton reportsBtn;
    private javax.swing.JLabel scheduledAppointmentsLbl;
    private javax.swing.JLabel scheduledAppointmentsTxt;
    private javax.swing.JMenuItem settingsItem;
    private javax.swing.JMenu settingsMenu;
    private com.toedter.calendar.JDateChooser startDateDC;
    private javax.swing.JLabel startDateLbl;
    private javax.swing.JMenu themeMenu;
    private javax.swing.JPanel todayInfoPanel;
    private javax.swing.JMenuItem treatmentItem;
    private javax.swing.JLabel userLbl;
    private javax.swing.JPanel yesterdayInfoPanel;
    // End of variables declaration//GEN-END:variables
}

