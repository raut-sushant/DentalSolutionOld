package com.ekdant.dentalsolution.principal;

import com.ekdant.dentalsolution.appointment.Appointments;
import com.ekdant.dentalsolution.dao.PatientsDao;
import com.ekdant.dentalsolution.document.Documents;
import com.ekdant.dentalsolution.domain.PatientBean;
import com.ekdant.dentalsolution.view.ViewPatient;
import com.ekdant.dentalsolution.register.RegisterPatient;
import com.ekdant.dentalsolution.treatments.PatientTreatment;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Sushant
 */
public class Patients extends javax.swing.JFrame {

    DateFormat displayDateFormat = new SimpleDateFormat("d MMM yyyy");
    DateFormat databaseDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    DateFormat inputDateFormat = new SimpleDateFormat("MMM d, yyyy");
    int totalPatinetCount = 0;
    static Patients patients = null;
    public static String logedInUser = null;
    public static String logedInUserType = null;
    PatientsDao patientsDao;

    /**
     * Creates new form JF_Patients
     *
     * @param principal
     * @return
     */
    public static Patients getInstance(Principal principal) {
        logedInUser = principal.logedInUser;
        logedInUserType = principal.logedInUserType;
        if (patients == null) {
            patients = new Patients();
        }
        return patients;
    }

    private Patients() {
        patientsDao = new PatientsDao();
        initComponents();
        totalPatinetCount = patientsDao.getTotalPatientCount();
        displayPatientCountLabel(totalPatinetCount);
        populatePatientJtable(null);
        if (logedInUserType.equalsIgnoreCase("staff")) {
            staffLogin();
        }
    }

    private void staffLogin() {
        treatmentBtn.setVisible(false);
        deleteBtn.setVisible(false);
    }

    private void displayPatientCountLabel(int count) {
        displayingPatientsCountLbl.setText("Displaying " + count + " out of total " + totalPatinetCount);
    }

    private void populatePatientJtable(String searchTxt) {
        List<PatientBean> patients = patientsDao.fetchPatients(searchTxt);
        DefaultTableModel model = (DefaultTableModel) jTablePatients.getModel();
        model.setNumRows(0);

        for (PatientBean patient : patients) {
            model.addRow(new Object[]{
                patient.getPatientId(),
                patient.getCaseId(),
                patient.getName(),
                patient.getAge(),
                patient.getGender(),
                patient.getAddress(),
                patient.getCity(),
                patient.getTelephone(),
                patient.getEmail(),
                patient.getBirthDate() != null ? displayDateFormat.format(patient.getBirthDate()) : ""
            });
        }
        displayPatientCountLabel(patients.size());
    }

    private void viewPatient() throws HeadlessException {
        int selectedPatient = this.jTablePatients.getSelectedRow();
        if (selectedPatient == -1) {
            JOptionPane.showMessageDialog(null, "Please Select Patient to Update !!", "Error!", JOptionPane.ERROR_MESSAGE);
        } else {
            String patientId = this.jTablePatients.getValueAt(selectedPatient, 0).toString();
            new ViewPatient(patientId, logedInUserType).setVisible(true);
        }
    }

    private void deletePatient() throws NumberFormatException, HeadlessException {
        int selectedPatient = this.jTablePatients.getSelectedRow();
        if (selectedPatient == -1) {
            JOptionPane.showMessageDialog(null, "Please Select Patient to Delete !!", "Error!", JOptionPane.ERROR_MESSAGE);
        } else {
            String selectedPatientId = this.jTablePatients.getValueAt(selectedPatient, 0).toString();
            try {
                String confirmationMessage = "Really delete patient: " + this.jTablePatients.getValueAt(selectedPatient, 2).toString() + " ?";
                int chosenOption = JOptionPane.showConfirmDialog(null, confirmationMessage, "Delete", JOptionPane.YES_NO_OPTION);

                if (chosenOption == JOptionPane.YES_OPTION) {

                    int deleteSuccess = patientsDao.deletePatientById(Integer.parseInt(selectedPatientId));

                    if (deleteSuccess == 1) {
                        JOptionPane.showMessageDialog(null, "Patient deleted successfully!", "Delete!", JOptionPane.INFORMATION_MESSAGE);
                    }
                    displayPatientCountLabel(patientsDao.getTotalPatientCount());
                    populatePatientJtable(null);
                }
            } catch (HeadlessException errorSQL) {
                JOptionPane.showMessageDialog(null, "Error in deletion", "Error!", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void patientTreatment() throws HeadlessException {
        int SelectedRow = this.jTablePatients.getSelectedRow();
        if (SelectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Please Select Patient!!", "Error!", JOptionPane.ERROR_MESSAGE);
        } else {
            String patientId = this.jTablePatients.getValueAt(SelectedRow, 0).toString();
            new PatientTreatment(Integer.parseInt(patientId)).setVisible(true);
        }
    }

    private void showAppointment() throws NumberFormatException, HeadlessException {
        int SelectedRow = this.jTablePatients.getSelectedRow();
        if (SelectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Please Select Patient!!", "Error!", JOptionPane.ERROR_MESSAGE);
        } else {
            String patientId = this.jTablePatients.getValueAt(SelectedRow, 0).toString();
            Appointments appointment = Appointments.getInstance(Integer.parseInt(patientId));
            appointment.setVisible(true);
            appointment.setState(JFrame.NORMAL);
        }
    }
    
    private void documents(){
        int SelectedRow = this.jTablePatients.getSelectedRow();
        if (SelectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Please Select Patient!!", "Error!", JOptionPane.ERROR_MESSAGE);
        } else {
            String patientId = this.jTablePatients.getValueAt(SelectedRow, 0).toString();
            Documents document = Documents.getInstance(Integer.parseInt(patientId));
            document.populateReports();
            document.setVisible(true);
            document.setState(JFrame.NORMAL);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        jScrollPane = new javax.swing.JScrollPane();
        jTablePatients = new javax.swing.JTable();
        searchLbl = new javax.swing.JLabel();
        searchTxt = new javax.swing.JTextField();
        displayingPatientsCountLbl = new javax.swing.JLabel();
        buttonsPanel = new javax.swing.JPanel();
        addBtn = new javax.swing.JButton();
        cancelBtn = new javax.swing.JButton();
        treatmentBtn = new javax.swing.JButton();
        deleteBtn = new javax.swing.JButton();
        appointmentBtn = new javax.swing.JButton();
        updateBtn = new javax.swing.JButton();
        documentBtn = new javax.swing.JButton();
        patientInfoLbl = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Patients");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setName("framePacientes"); // NOI18N
        setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width,Toolkit.getDefaultToolkit().getScreenSize().height-Toolkit.getDefaultToolkit().getScreenInsets(getGraphicsConfiguration()).bottom));
        addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                formWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
            }
        });

        jTablePatients.setAutoCreateRowSorter(true);
        jTablePatients.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Code", "Case ID", "Name", "Age", "Gender", "Address", "City", "Telephone ", "Email", "Birth"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTablePatients.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        jTablePatients.setNextFocusableComponent(addBtn);

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, patientInfoLbl, org.jdesktop.beansbinding.ObjectProperty.create(), jTablePatients, org.jdesktop.beansbinding.BeanProperty.create("selectedElement"));
        bindingGroup.addBinding(binding);

        jScrollPane.setViewportView(jTablePatients);
        if (jTablePatients.getColumnModel().getColumnCount() > 0) {
            jTablePatients.getColumnModel().getColumn(0).setMinWidth(0);
            jTablePatients.getColumnModel().getColumn(0).setPreferredWidth(0);
            jTablePatients.getColumnModel().getColumn(0).setMaxWidth(0);
            jTablePatients.getColumnModel().getColumn(1).setPreferredWidth(50);
            jTablePatients.getColumnModel().getColumn(2).setPreferredWidth(250);
            jTablePatients.getColumnModel().getColumn(3).setPreferredWidth(20);
            jTablePatients.getColumnModel().getColumn(4).setPreferredWidth(50);
            jTablePatients.getColumnModel().getColumn(6).setPreferredWidth(100);
            jTablePatients.getColumnModel().getColumn(7).setPreferredWidth(75);
            jTablePatients.getColumnModel().getColumn(8).setPreferredWidth(100);
            jTablePatients.getColumnModel().getColumn(9).setPreferredWidth(100);
        }

        searchLbl.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        searchLbl.setForeground(new java.awt.Color(51, 51, 255));
        searchLbl.setText("Search :");

        searchTxt.setNextFocusableComponent(jTablePatients);
        searchTxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchTxtKeyReleased(evt);
            }
        });

        displayingPatientsCountLbl.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        displayingPatientsCountLbl.setForeground(new java.awt.Color(51, 51, 255));

        addBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/EkDant/icones/Patient Boy 1 Add.png"))); // NOI18N
        addBtn.setMnemonic('A');
        addBtn.setText("Add");
        addBtn.setNextFocusableComponent(updateBtn);
        addBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addBtnActionPerformed(evt);
            }
        });

        cancelBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/EkDant/icones/Patient Boy 1 Stop.png"))); // NOI18N
        cancelBtn.setMnemonic('C');
        cancelBtn.setText("Cancel");
        cancelBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelBtnActionPerformed(evt);
            }
        });

        treatmentBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/EkDant/icones/Patient Boy 1.png"))); // NOI18N
        treatmentBtn.setMnemonic('T');
        treatmentBtn.setText("Treatment");
        treatmentBtn.setNextFocusableComponent(cancelBtn);
        treatmentBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                treatmentBtnActionPerformed(evt);
            }
        });

        deleteBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/EkDant/icones/Patient Boy 1 Delete.png"))); // NOI18N
        deleteBtn.setMnemonic('D');
        deleteBtn.setText("Delete");
        deleteBtn.setNextFocusableComponent(treatmentBtn);
        deleteBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteBtnActionPerformed(evt);
            }
        });

        appointmentBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/EkDant/icones/Patient Boy 2 Search.png"))); // NOI18N
        appointmentBtn.setMnemonic('C');
        appointmentBtn.setText("Appointment");
        appointmentBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                appointmentBtnActionPerformed(evt);
            }
        });

        updateBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/EkDant/icones/Patient Boy 1 Edit 1.png"))); // NOI18N
        updateBtn.setMnemonic('V');
        updateBtn.setText("View");
        updateBtn.setNextFocusableComponent(deleteBtn);
        updateBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateBtnActionPerformed(evt);
            }
        });

        documentBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/EkDant/icones/Document-icon.png"))); // NOI18N
        documentBtn.setMnemonic('C');
        documentBtn.setText("Documents");
        documentBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                documentBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout buttonsPanelLayout = new javax.swing.GroupLayout(buttonsPanel);
        buttonsPanel.setLayout(buttonsPanelLayout);
        buttonsPanelLayout.setHorizontalGroup(
            buttonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buttonsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(addBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(updateBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(deleteBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(treatmentBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(appointmentBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(documentBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(cancelBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38))
        );

        buttonsPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {addBtn, appointmentBtn, cancelBtn, deleteBtn, treatmentBtn, updateBtn});

        buttonsPanelLayout.setVerticalGroup(
            buttonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buttonsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(buttonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(updateBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(deleteBtn)
                    .addComponent(treatmentBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cancelBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(appointmentBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(documentBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        patientInfoLbl.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        patientInfoLbl.setForeground(new java.awt.Color(51, 51, 255));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1107, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(searchLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(searchTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(displayingPatientsCountLbl)
                            .addComponent(patientInfoLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(buttonsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchLbl)
                    .addComponent(searchTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(displayingPatientsCountLbl)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(patientInfoLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addComponent(buttonsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {searchLbl, searchTxt});

        bindingGroup.bind();

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void addBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addBtnActionPerformed
        new RegisterPatient(this).setVisible(true);
    }//GEN-LAST:event_addBtnActionPerformed

    private void updateBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateBtnActionPerformed
        viewPatient();
    }//GEN-LAST:event_updateBtnActionPerformed

    private void deleteBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteBtnActionPerformed
        deletePatient();
    }//GEN-LAST:event_deleteBtnActionPerformed

    private void treatmentBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_treatmentBtnActionPerformed
        patientTreatment();
    }//GEN-LAST:event_treatmentBtnActionPerformed
    private void cancelBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelBtnActionPerformed
        this.dispose();
    }//GEN-LAST:event_cancelBtnActionPerformed

    private void searchTxtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchTxtKeyReleased
        this.populatePatientJtable(searchTxt.getText());
    }//GEN-LAST:event_searchTxtKeyReleased

    private void appointmentBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_appointmentBtnActionPerformed
        showAppointment();
    }//GEN-LAST:event_appointmentBtnActionPerformed

    private void formWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowGainedFocus
        populatePatientJtable(null);
    }//GEN-LAST:event_formWindowGainedFocus

    private void documentBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_documentBtnActionPerformed
        documents();
    }//GEN-LAST:event_documentBtnActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addBtn;
    private javax.swing.JButton appointmentBtn;
    private javax.swing.JPanel buttonsPanel;
    private javax.swing.JButton cancelBtn;
    private javax.swing.JButton deleteBtn;
    private javax.swing.JLabel displayingPatientsCountLbl;
    private javax.swing.JButton documentBtn;
    private javax.swing.JScrollPane jScrollPane;
    private javax.swing.JTable jTablePatients;
    private javax.swing.JLabel patientInfoLbl;
    private javax.swing.JLabel searchLbl;
    private javax.swing.JTextField searchTxt;
    private javax.swing.JButton treatmentBtn;
    private javax.swing.JButton updateBtn;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

}
