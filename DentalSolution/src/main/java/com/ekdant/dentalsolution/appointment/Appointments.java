/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ekdant.dentalsolution.appointment;

import com.ekdant.dentalsolution.dao.AppointmentsDao;
import com.ekdant.dentalsolution.dao.DoctorDao;
import com.ekdant.dentalsolution.dao.PatientsDao;
import com.ekdant.dentalsolution.dao.TreatmentDao;
import com.ekdant.dentalsolution.domain.AppointmentBean;
import com.ekdant.dentalsolution.domain.DoctorBean;
import com.ekdant.dentalsolution.domain.PatientBean;
import com.ekdant.dentalsolution.domain.TimeslotBean;
import com.ekdant.dentalsolution.domain.TreatmentBean;
import com.ekdant.dentalsolution.register.RegisterPatient;
import com.sun.java.swing.plaf.windows.WindowsBorders;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author Sushant Raut
 */
public class Appointments extends javax.swing.JFrame {

    Map<String, AppointmentBean> appointmentMap = new HashMap<String, AppointmentBean>();
    Date startDateForAppointment;
    DateFormat databaseDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    DateFormat displayDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
    int selectedSlotId = -1;
    int rightClickedSlotId = -1;
    String rightClickedDate = "";
    String selectedDate = "";
    int patientId = 0;
    String clinicStartTime="MORNING_START_TIME";
    String clinicEndTime="MORNING_END_TIME";
    JPopupMenu rightClickPopupMenu;    
    JMenuItem patientArrivedOption;
    JMenuItem cancelAppointmentOption;
    JMenuItem checkupDoneOption;
    static Appointments appointmentsFrm = null;
    PatientsDao patientsDao;
    DoctorDao doctorDao;
    AppointmentsDao appointmentsDao;
    TreatmentDao treatmentDao;
    
    public static Appointments getInstance(int patientId){
        if(appointmentsFrm == null || patientId!=0){
            appointmentsFrm = new Appointments(patientId);
        }
        return appointmentsFrm;
    }
    
    /**
     * Creates new form Appointments
     * @param patientId
     */
    private Appointments(int patientId) {
        startDateForAppointment = new Date();
        this.patientId = patientId;
        patientsDao = new PatientsDao();
        doctorDao = new DoctorDao();
        appointmentsDao = new AppointmentsDao();
        treatmentDao = new TreatmentDao();
        initComponents();
        loadPatients();
        loadDoctors();
        loadTreatments();
        morningRadiobutton.setSelected(true);
        showAppointments(startDateForAppointment);        
    }
    
    private void showAppointments(Date startDate){
        startDateForAppointment = startDate;
        showAppointmentDateRange(startDate);
        loadAppointments(startDate);
        loadSlots();
    }
    
    private void loadPatients() {
        PatientBean patient = patientsDao.fetchPatientById(patientId);
        if(patient.getPatientId() > 0){
            DefaultTableModel model = (DefaultTableModel) patientsTbl.getModel();
            model.setNumRows(0);

            model.addRow(new Object[]{
                patient.getPatientId(),
                patient.getCaseId(),
                patient.getName(),
                patient.getAge() + "/" + patient.getGender(),
                patient.getCity(),
                patient.getTelephone()
            });
            patientsTbl.selectAll();
        }
    }
    
    public void SearchPatient(){
        List<PatientBean> patients = patientsDao.fetchPatients(patientTxt.getText());
        DefaultTableModel model = (DefaultTableModel)patientsTbl.getModel();
        model.setNumRows(0);

        for (PatientBean patient : patients) {
            model.addRow(new Object[]{
                patient.getPatientId(),
                patient.getCaseId(),
                patient.getName(),
                patient.getAge() + "/" + patient.getGender(),
                patient.getCity(),
                patient.getTelephone()
            });
        }
    }
   
    private void loadDoctors() {
        List<DoctorBean> doctors = doctorDao.fetchDoctors();
        for (DoctorBean doctor : doctors) {
            this.doctorCB.addItem(doctor.getName());
        }        
    }
    
    
    private void loadSlots() {
       
        DefaultTableModel model = (DefaultTableModel)appointmentTbl.getModel();
        model.setNumRows(0);
        rightClickPopupMenu = new JPopupMenu();
        patientArrivedOption = new JMenuItem("Patient Arrived");
        patientArrivedOption.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
               updateAppointmtntStatus(2);
            }
        });
        rightClickPopupMenu.add(patientArrivedOption);
        cancelAppointmentOption = new JMenuItem("Cancel Appointment");
        cancelAppointmentOption.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
               updateAppointmtntStatus(3);
            }
        });
        rightClickPopupMenu.add(cancelAppointmentOption);
        checkupDoneOption = new JMenuItem("Checkup Done");
        checkupDoneOption.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
               updateAppointmtntStatus(4);
            }
        });
        rightClickPopupMenu.add(checkupDoneOption);
        
        final String dates[] = addAppointmentTableHeader();
        
        appointmentTbl.addMouseListener(new MouseListener() {

            public void mouseClicked(MouseEvent e) {
                if(e.getButton() != MouseEvent.BUTTON3 ){
                    selectedSlotId = Integer.parseInt(appointmentTbl.getValueAt(appointmentTbl.getSelectedRow(), 0).toString()); 
                    selectedDate = dates[appointmentTbl.getSelectedColumn()-2];
                }
            }

            public void mousePressed(MouseEvent e) {}

            public void mouseReleased(MouseEvent e) {
                rightClickedSlotId = Integer.parseInt(appointmentTbl.getValueAt(appointmentTbl.rowAtPoint(e.getPoint()), 0).toString()); 
                rightClickedDate = dates[appointmentTbl.columnAtPoint(e.getPoint())-2];
                 if(e.getButton() == MouseEvent.BUTTON3 
                         && (appointmentTbl.getValueAt(appointmentTbl.rowAtPoint(e.getPoint()), appointmentTbl.columnAtPoint(e.getPoint())) != null 
                            && !appointmentTbl.getValueAt(appointmentTbl.rowAtPoint(e.getPoint()), appointmentTbl.columnAtPoint(e.getPoint())).toString().isEmpty())){
                   rightClickPopupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }

            public void mouseEntered(MouseEvent e) {}

            public void mouseExited(MouseEvent e) {}
        });
        
        appointmentTbl.setDefaultRenderer(String.class, new TableCellRenderer() {

            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                String cellValue = (String) value;
                JLabel appointmentLbl = new JLabel();
                boolean pastSlot = false;
                if (column > 1) {
                    pastSlot = isPastSlot(dates[column - 2], Integer.parseInt(appointmentTbl.getValueAt(row, 0).toString()));
                }
                appointmentLbl.setBorder(new WindowsBorders.ProgressBarBorder(Color.LIGHT_GRAY, Color.lightGray));
                if (pastSlot) {
                    appointmentLbl.setBackground(new Color(225, 225, 225));
                    appointmentLbl.setOpaque(true);
                }
                if (cellValue != null && !cellValue.isEmpty()) {
                    appointmentLbl.setText(cellValue);
                    if (column == 1) {
                        appointmentLbl.setBackground(Color.LIGHT_GRAY);
                        appointmentLbl.setBorder(new WindowsBorders.ProgressBarBorder(Color.GRAY, Color.GRAY));
                    } else {
                        int slotId = Integer.parseInt(appointmentTbl.getValueAt(row, 0).toString());
                        String date = dates[column - 2];
                        int status = getAppointmentStatus(slotId, date);
                        if (status == 1) {
                            appointmentLbl.setBackground(Color.CYAN);
                        } else if (status == 2) {
                            appointmentLbl.setBackground(Color.YELLOW);
                        } else if (status == 3) {
                            appointmentLbl.setBackground(Color.GREEN);
                        } else {
                            appointmentLbl.setBackground(Color.MAGENTA);
                        }
                        selectedSlotId = -1;
                    }
                    appointmentLbl.setToolTipText(cellValue);
                    appointmentLbl.setPreferredSize(new Dimension(20, 20));
                    appointmentLbl.setOpaque(true);
                }
                if (!pastSlot && isSelected && column != 1 && (cellValue == null || cellValue.isEmpty())) {
                    appointmentLbl.setBackground(Color.BLUE);
                    appointmentLbl.setOpaque(true);
                }
                return appointmentLbl;
            }

        });

        List<TimeslotBean> timeslots = appointmentsDao.fetchTimeSlots(clinicStartTime, clinicEndTime);
        for (TimeslotBean timeslot : timeslots) {
            int appointmentId = timeslot.getId();
            model.addRow(new Object[]{
                appointmentId,
                timeslot.getStartTime() + " - " + timeslot.getEndTime(),
                showAppointment(appointmentMap.get(appointmentId + "_" + dates[0])),
                showAppointment(appointmentMap.get(appointmentId + "_" + dates[1])),
                showAppointment(appointmentMap.get(appointmentId + "_" + dates[2])),
                showAppointment(appointmentMap.get(appointmentId + "_" + dates[3])),
                showAppointment(appointmentMap.get(appointmentId + "_" + dates[4])),
                showAppointment(appointmentMap.get(appointmentId + "_" + dates[5])),
                showAppointment(appointmentMap.get(appointmentId + "_" + dates[6]))
            });
        }        
    }
    
    
    private void updateAppointmtntStatus(int status) {
        try {
            AppointmentBean appointment = new AppointmentBean();
            appointment.setSlotId(rightClickedSlotId);
            appointment.setAppointmentDate(databaseDateFormat.parse(rightClickedDate));
            appointment.setStatus(status);
            appointmentsDao.updateAppointment(appointment);
        } catch (ParseException ex) {}
    }
    
    private int getAppointmentStatus(int slotId, String appointmentDate) {        
        return appointmentsDao.fetchAppointmentStatus(slotId, appointmentDate);
    }
    
    private boolean isPastSlot(String date, int slotId) {
        boolean pastSlot = false;
        try {
            if(databaseDateFormat.parse(date).before(new Date())){
                if(date.equalsIgnoreCase(databaseDateFormat.format(new Date())) && ((new Date().getHours()*4) + (new Date().getMinutes()/15)) > slotId ){
                    return true;
                }
                else if(date.equalsIgnoreCase(databaseDateFormat.format(new Date())) && ((new Date().getHours()*4) + (new Date().getMinutes()/15)) < slotId ){
                    return false;
                }
                return true;
            }    
        } catch (ParseException ex) {}
        return pastSlot;
    }

    private String[] addAppointmentTableHeader() {
        String dates[] = new String[7];
        JTableHeader th= appointmentTbl.getTableHeader();
        TableColumnModel columnModel = th.getColumnModel();
        for(int i = 0; i < 7; i++){
            Calendar calender = new GregorianCalendar();
            calender.setTime(startDateForAppointment);
            calender.add(Calendar.DATE, i);
            dates[i] = databaseDateFormat.format(calender.getTime());
            TableColumn tc =  columnModel.getColumn(i+2);
            tc.setHeaderValue("<html>"+calender.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG_FORMAT, Locale.ENGLISH)+" <br> "+displayDateFormat.format(calender.getTime())+"</html>");
        }
        th.setPreferredSize(new Dimension((int)(appointmentTbl.getWidth()/8), 40));
        th.repaint();
        return dates;
    }

    private void loadAppointments(Date startDate) {
        appointmentMap = new HashMap<String, AppointmentBean>();
        Calendar calender = new GregorianCalendar();
        calender.setTime(startDate);
        calender.add(Calendar.DATE, 7);
        Date endDate = calender.getTime();
        List<AppointmentBean> appointments = appointmentsDao.fetchAppointmentDetails(databaseDateFormat.format(startDate), databaseDateFormat.format(endDate), doctorCB.getSelectedItem().toString());
        for(AppointmentBean appointment : appointments){
            appointmentMap.put(appointment.getSlotId()+"_"+databaseDateFormat.format(appointment.getAppointmentDate()), appointment);
        }       
    }

    private Object showAppointment(AppointmentBean appointment) {
        if(null != appointment){
             return "<html><b>"+appointment.getPatient().getName() + "</b> <br/>" + 
                    appointment.getPatient().getCaseId() + "<br/>"+
                    appointment.getPatient().getTelephone()+"</html>";
        }
        return "";
        
    }

    private void showAppointmentDateRange(Date startDateForAppointment) {
        Calendar calender = new GregorianCalendar();
        calender.setTime(startDateForAppointment);
        calender.add(Calendar.DATE, 6);
        appointmentTitleLabel.setText("<html>Appontment From <b>"+displayDateFormat.format(startDateForAppointment)+" - " +displayDateFormat.format(calender.getTime()) + "</b></html>");
    }

    private void saveAppointment(String appointmentDate, int appointmentSlot, int patientId, String doctorName, String treatmentName, String notes) {
        try {
            int doctorId = getDoctorId(doctorName);
            int treatmentId = getTreatmentId(treatmentName);
            DoctorBean doctor = new DoctorBean();
            doctor.setDoctorId(doctorId);
            doctor.setName(doctorName);
            
            TreatmentBean treatment = new TreatmentBean();
            treatment.setTreatmentId(treatmentId);
            treatment.setTreatmentName(treatmentName);
            
            PatientBean patient = new PatientBean();
            patient.setPatientId(patientId);
            
            AppointmentBean appointment = new AppointmentBean();
            appointment.setPatient(patient);
            appointment.setDoctor(doctor);
            appointment.setTreatment(treatment);
            appointment.setAppointmentDate(databaseDateFormat.parse(appointmentDate));
            appointment.setSlotId(appointmentSlot);
            appointment.setComment(notes);
            
            appointmentsDao.addAppointment(appointment);
            JOptionPane.showMessageDialog(null,"Appointment booked succesfully","INFO", JOptionPane.INFORMATION_MESSAGE);
            DefaultTableModel model = (DefaultTableModel)patientsTbl.getModel();
            model.setNumRows(0);
            patientTxt.setText("");
            treatmentCB.setSelectedIndex(0);
            notesTxt.setText("");
            showAppointments(startDateForAppointment);
        } catch (ParseException ex) {}
    }
    
    private int getDoctorId(String doctorName) {
        return doctorDao.fetchDoctorId(doctorName);
    }

    private int getTreatmentId(String treatmentName) {        
        return treatmentDao.fetchTreatmentId(treatmentName);
    }
    
    private void loadTreatments() throws HeadlessException {
        List<TreatmentBean> treatments = treatmentDao.fetchTreatments();
        treatmentCB.removeAll();
        treatmentCB.addItem("Select Treatment");
        for (TreatmentBean treatment : treatments) {
            treatmentCB.addItem(treatment.getTreatmentName());
        }
    }     
     
    private String appointmentConfirmationMessage(){
        int selectedPatientRow = patientsTbl.getSelectedRow();
        String selectedTreatment = (treatmentCB.getSelectedIndex() == 0) ? "" : treatmentCB.getSelectedItem().toString();
        String message = "<html>"
                + "<b>Are you sure to book appointment:</b><br>"
                + "<table><tr><td><br><b>Doctor   :</b></td><td>"+doctorCB.getSelectedItem().toString()+"</td>"
                + "</tr><tr><td><br><b>Patient  :</b></td><td>"+patientsTbl.getValueAt(selectedPatientRow, 2).toString()+"</td>"
                + "</tr><tr><td><br><b>Treatment:</b></td><td>"+selectedTreatment+"</td>"
                + "</tr><tr><td><br><b>Date     :</b></td><td>"+selectedDate+"</td>"
                + "</tr><tr><td><br><b>Time     :</b></td><td>"+getStartTimeofSlot(selectedSlotId)+"</td>"
                + "</tr></table></html>";
        
        return message;
    }

    private String getStartTimeofSlot(int selectedSlotId) {        
        return appointmentsDao.fetchStartDateForSlot(selectedSlotId);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        patientLbl = new javax.swing.JLabel();
        patientTxt = new javax.swing.JTextField();
        doctorLbl = new javax.swing.JLabel();
        doctorCB = new javax.swing.JComboBox();
        treatmentLbl = new javax.swing.JLabel();
        treatmentCB = new javax.swing.JComboBox();
        notesLbl = new javax.swing.JLabel();
        notesTxt = new javax.swing.JTextField();
        appointmentTitleLabel = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        patientsTbl = new javax.swing.JTable();
        saveBtn = new javax.swing.JButton();
        cancelBtn = new javax.swing.JButton();
        morningRadiobutton = new javax.swing.JRadioButton();
        eveningRadioButton = new javax.swing.JRadioButton();
        jButton2 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        appointmentTbl = new javax.swing.JTable();
        nextButton = new javax.swing.JButton();
        prevButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Appointment");
        setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width,Toolkit.getDefaultToolkit().getScreenSize().height-Toolkit.getDefaultToolkit().getScreenInsets(getGraphicsConfiguration()).bottom));

        patientLbl.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        patientLbl.setForeground(new java.awt.Color(51, 51, 255));
        patientLbl.setText("* Patient:");

        patientTxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                patientTxtKeyTyped(evt);
            }
        });

        doctorLbl.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        doctorLbl.setForeground(new java.awt.Color(51, 51, 255));
        doctorLbl.setText("* Doctor:");

        doctorCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                doctorCBActionPerformed(evt);
            }
        });

        treatmentLbl.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        treatmentLbl.setForeground(new java.awt.Color(51, 51, 255));
        treatmentLbl.setText("Treatment:");

        notesLbl.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        notesLbl.setForeground(new java.awt.Color(51, 51, 255));
        notesLbl.setText("Notes:");

        appointmentTitleLabel.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        appointmentTitleLabel.setForeground(new java.awt.Color(51, 51, 255));

        patientsTbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Patinet Id", "Case Paper Number", "Name", "Age/Gender", "City", "Contact"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        patientsTbl.setPreferredSize(new Dimension((Toolkit.getDefaultToolkit().getScreenSize().width)/2,100));
        patientsTbl.setRowHeight(25);
        jScrollPane2.setViewportView(patientsTbl);
        if (patientsTbl.getColumnModel().getColumnCount() > 0) {
            patientsTbl.getColumnModel().getColumn(0).setMinWidth(0);
            patientsTbl.getColumnModel().getColumn(0).setPreferredWidth(0);
            patientsTbl.getColumnModel().getColumn(0).setMaxWidth(0);
        }

        saveBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/EkDant/icones/Calendar Confirmed.png"))); // NOI18N
        saveBtn.setText("Save");
        saveBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveBtnActionPerformed(evt);
            }
        });

        cancelBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/EkDant/icones/Calendar Delete.png"))); // NOI18N
        cancelBtn.setText("Cancel");
        cancelBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelBtnActionPerformed(evt);
            }
        });

        morningRadiobutton.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        morningRadiobutton.setForeground(new java.awt.Color(51, 51, 255));
        morningRadiobutton.setText("Morning");
        morningRadiobutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                morningRadiobuttonActionPerformed(evt);
            }
        });

        eveningRadioButton.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        eveningRadioButton.setForeground(new java.awt.Color(51, 51, 255));
        eveningRadioButton.setText("Evening");
        eveningRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eveningRadioButtonActionPerformed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/EkDant/icones/plus-icon.png"))); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(notesLbl, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(treatmentLbl, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE)
                            .addComponent(doctorLbl, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(treatmentCB, 0, 132, Short.MAX_VALUE)
                            .addComponent(doctorCB, 0, 175, Short.MAX_VALUE)
                            .addComponent(notesTxt)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(saveBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(cancelBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(patientLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(patientTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 159, Short.MAX_VALUE)
                        .addComponent(appointmentTitleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 453, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(46, 46, 46)
                        .addComponent(morningRadiobutton)
                        .addGap(18, 18, 18)
                        .addComponent(eveningRadioButton)
                        .addGap(151, 151, 151)))
                .addContainerGap())
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {doctorCB, treatmentCB});

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {notesLbl, treatmentLbl});

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {cancelBtn, saveBtn});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton2)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(doctorLbl)
                                .addComponent(doctorCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(patientLbl)
                                .addComponent(patientTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(treatmentLbl)
                            .addComponent(treatmentCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(notesLbl)
                            .addComponent(notesTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(appointmentTitleLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(saveBtn)
                                .addComponent(cancelBtn))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(morningRadiobutton)
                                .addComponent(eveningRadioButton)))))
                .addContainerGap())
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {notesLbl, treatmentLbl});

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {cancelBtn, saveBtn});

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jButton2, patientLbl, patientTxt});

        appointmentTbl.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        appointmentTbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "SlotId", "Time Slot", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        appointmentTbl.setCellSelectionEnabled(true);
        appointmentTbl.setRowHeight(50);
        appointmentTbl.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        appointmentTbl.getTableHeader().setResizingAllowed(false);
        appointmentTbl.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(appointmentTbl);
        appointmentTbl.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        if (appointmentTbl.getColumnModel().getColumnCount() > 0) {
            appointmentTbl.getColumnModel().getColumn(0).setMinWidth(0);
            appointmentTbl.getColumnModel().getColumn(0).setPreferredWidth(0);
            appointmentTbl.getColumnModel().getColumn(0).setMaxWidth(0);
        }

        nextButton.setText(">>");
        nextButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextButtonActionPerformed(evt);
            }
        });

        prevButton.setText("<< ");
        prevButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                prevButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(prevButton)
                .addGap(0, 0, 0)
                .addComponent(jScrollPane1)
                .addGap(0, 0, 0)
                .addComponent(nextButton))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1)
                    .addComponent(nextButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(prevButton, javax.swing.GroupLayout.PREFERRED_SIZE, 484, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void nextButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextButtonActionPerformed
        Calendar calender = new GregorianCalendar();
        calender.setTime(startDateForAppointment);
        calender.add(Calendar.DATE, 6);
        showAppointments(calender.getTime());
    }//GEN-LAST:event_nextButtonActionPerformed

    private void prevButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_prevButtonActionPerformed
        Calendar calender = new GregorianCalendar();
        calender.setTime(startDateForAppointment);
        calender.add(Calendar.DATE, -6);
        showAppointments(calender.getTime());
    }//GEN-LAST:event_prevButtonActionPerformed
   
    private void doctorCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_doctorCBActionPerformed
        showAppointments(startDateForAppointment);       
    }//GEN-LAST:event_doctorCBActionPerformed

    private void patientTxtKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_patientTxtKeyTyped
        SearchPatient();
    }//GEN-LAST:event_patientTxtKeyTyped

    private void saveBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveBtnActionPerformed
        int selectedPatientRow = patientsTbl.getSelectedRow();
        
        if(selectedPatientRow == -1){
            JOptionPane.showMessageDialog(null,"Select Patient for appointment","ERROR", JOptionPane.ERROR_MESSAGE);
        }else if(selectedSlotId == -1){
            JOptionPane.showMessageDialog(null,"Select slot for appointment","ERROR", JOptionPane.ERROR_MESSAGE);
        }else if(selectedDate.isEmpty()){
            JOptionPane.showMessageDialog(null,"Select slot for appointment","ERROR", JOptionPane.ERROR_MESSAGE);
        }else{
            int bookAppointmentChosenOption = JOptionPane.showConfirmDialog(null,appointmentConfirmationMessage(), "Book Appointment", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, new javax.swing.ImageIcon(getClass().getResource("/EkDant/icones/Calendar Confirmed.png")));
            if(bookAppointmentChosenOption == JOptionPane.YES_OPTION){
                int selectedPatientId = Integer.parseInt(patientsTbl.getValueAt(selectedPatientRow, 0).toString());
                saveAppointment(selectedDate, selectedSlotId, selectedPatientId, doctorCB.getSelectedItem().toString(), treatmentCB.getSelectedItem().toString(), notesTxt.getText());
            }
        }
    }//GEN-LAST:event_saveBtnActionPerformed
    
    private void cancelBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelBtnActionPerformed
        this.dispose();
    }//GEN-LAST:event_cancelBtnActionPerformed

    private void morningRadiobuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_morningRadiobuttonActionPerformed
        eveningRadioButton.setSelected(false);
        clinicStartTime="MORNING_START_TIME";
        clinicEndTime="MORNING_END_TIME";
        showAppointments(startDateForAppointment);  
    }//GEN-LAST:event_morningRadiobuttonActionPerformed

    private void eveningRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eveningRadioButtonActionPerformed
        morningRadiobutton.setSelected(false);
        clinicStartTime="EVENING_START_TIME";
        clinicEndTime="EVENING_END_TIME";
        showAppointments(startDateForAppointment);  
    }//GEN-LAST:event_eveningRadioButtonActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        new RegisterPatient(null).setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed
       
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable appointmentTbl;
    private javax.swing.JLabel appointmentTitleLabel;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JButton cancelBtn;
    private javax.swing.JComboBox doctorCB;
    private javax.swing.JLabel doctorLbl;
    private javax.swing.JRadioButton eveningRadioButton;
    private javax.swing.JButton jButton2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JRadioButton morningRadiobutton;
    private javax.swing.JButton nextButton;
    private javax.swing.JLabel notesLbl;
    private javax.swing.JTextField notesTxt;
    private javax.swing.JLabel patientLbl;
    private javax.swing.JTextField patientTxt;
    private javax.swing.JTable patientsTbl;
    private javax.swing.JButton prevButton;
    private javax.swing.JButton saveBtn;
    private javax.swing.JComboBox treatmentCB;
    private javax.swing.JLabel treatmentLbl;
    // End of variables declaration//GEN-END:variables

}
