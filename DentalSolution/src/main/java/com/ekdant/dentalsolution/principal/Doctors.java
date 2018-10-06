package com.ekdant.dentalsolution.principal;
import com.ekdant.dentalsolution.dao.DoctorDao;
import com.ekdant.dentalsolution.domain.DoctorBean;
import com.ekdant.dentalsolution.register.RegisterDoctor;
import com.ekdant.dentalsolution.view.ViewDoctor;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Sushant
 */
public class Doctors extends javax.swing.JFrame {
    DoctorDao doctorDao;
    DateFormat displayDateFormat = new SimpleDateFormat("d MMM yyyy");
    DateFormat databaseDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    DateFormat inputDateFormat = new SimpleDateFormat("MMM d, yyyy");
    int totalDoctorsCount = 0;
    /** Creates new form JF_Doctors */
    public Doctors() {  
        doctorDao = new DoctorDao();
        initComponents();
        totalDoctorsCount = doctorDao.populateTotalDoctorsCount();
        displayDoctorCountLabel(totalDoctorsCount);
        searchDoctor(null);
    }  

    private void displayDoctorCountLabel(int count) {
        displayingDoctorsCountLbl.setText("Displaying " + count + " out of total " + totalDoctorsCount);
    }
    
    public void searchDoctor(String searchText){
        List<DoctorBean> doctors = doctorDao.fetchDoctors(searchText);
        DefaultTableModel model = (DefaultTableModel) jTableDoctors.getModel();
        model.setNumRows(0);
        int count = 0;
        for (DoctorBean doctor : doctors) {
            model.addRow(new Object[]{
                doctor.getDoctorId(),
                doctor.getType(),
                doctor.getName(),
                doctor.getDegree(),
                doctor.getRegistrationNo(),
                doctor.getSpecialization(),
                doctor.getAge(),
                doctor.getGender(),
                doctor.getAddress(),
                doctor.getCity(),
                doctor.getTelephone(),
                doctor.getEmail(),
                doctor.getBirthDate() != null ? displayDateFormat.format(doctor.getBirthDate()) : ""
            });
            count++;
        }   
        displayDoctorCountLabel(count);
    }
     
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane = new javax.swing.JScrollPane();
        jTableDoctors = new javax.swing.JTable();
        searchLbl = new javax.swing.JLabel();
        searchTxt = new javax.swing.JTextField();
        addBtn = new javax.swing.JButton();
        updateBtn = new javax.swing.JButton();
        deleteBtn = new javax.swing.JButton();
        cancelBtn = new javax.swing.JButton();
        displayingDoctorsCountLbl = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Doctors");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setName("framePacientes"); // NOI18N
        setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width,Toolkit.getDefaultToolkit().getScreenSize().height-Toolkit.getDefaultToolkit().getScreenInsets(getGraphicsConfiguration()).bottom));

        jTableDoctors.setAutoCreateRowSorter(true);
        jTableDoctors.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Code", "Type", "Name", "Degree", "Registration No", "Specialization", "Age", "Gender", "Address", "City", "Telephone ", "Email", "Birth"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableDoctors.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        jTableDoctors.setNextFocusableComponent(addBtn);
        jScrollPane.setViewportView(jTableDoctors);
        if (jTableDoctors.getColumnModel().getColumnCount() > 0) {
            jTableDoctors.getColumnModel().getColumn(0).setMinWidth(0);
            jTableDoctors.getColumnModel().getColumn(0).setPreferredWidth(0);
            jTableDoctors.getColumnModel().getColumn(0).setMaxWidth(0);
            jTableDoctors.getColumnModel().getColumn(1).setPreferredWidth(50);
            jTableDoctors.getColumnModel().getColumn(2).setPreferredWidth(200);
            jTableDoctors.getColumnModel().getColumn(6).setPreferredWidth(20);
            jTableDoctors.getColumnModel().getColumn(7).setPreferredWidth(50);
            jTableDoctors.getColumnModel().getColumn(9).setPreferredWidth(50);
            jTableDoctors.getColumnModel().getColumn(10).setPreferredWidth(75);
            jTableDoctors.getColumnModel().getColumn(11).setPreferredWidth(75);
            jTableDoctors.getColumnModel().getColumn(12).setPreferredWidth(75);
        }

        searchLbl.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        searchLbl.setText("Search :");

        searchTxt.setNextFocusableComponent(jTableDoctors);
        searchTxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchTxtKeyReleased(evt);
            }
        });

        addBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/EkDant/icones/Patient Boy 1 Add.png"))); // NOI18N
        addBtn.setMnemonic('A');
        addBtn.setText("Add");
        addBtn.setNextFocusableComponent(updateBtn);
        addBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addBtnActionPerformed(evt);
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

        deleteBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/EkDant/icones/Patient Boy 1 Delete.png"))); // NOI18N
        deleteBtn.setMnemonic('D');
        deleteBtn.setText("Delete");
        deleteBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteBtnActionPerformed(evt);
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 918, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(searchLbl)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(searchTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(displayingDoctorsCountLbl)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(addBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(updateBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(deleteBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(cancelBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                .addComponent(displayingDoctorsCountLbl)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 358, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(updateBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(deleteBtn)
                    .addComponent(cancelBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void addBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addBtnActionPerformed
        new RegisterDoctor(this).setVisible(true);
    }//GEN-LAST:event_addBtnActionPerformed

    private void updateBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateBtnActionPerformed

        int selectedDoctor = this.jTableDoctors.getSelectedRow();
        if(selectedDoctor == -1) {
            JOptionPane.showMessageDialog(null,"Please Select Doctor to Update !!", "Error!", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String doctorId    = this.jTableDoctors.getValueAt(selectedDoctor, 0).toString();
        
        new ViewDoctor(this, doctorDao.fetchDoctor(Integer.parseInt(doctorId))).setVisible(true);
       
    }//GEN-LAST:event_updateBtnActionPerformed

    private void deleteBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteBtnActionPerformed

        int selectedDoctor = this.jTableDoctors.getSelectedRow();
        if(selectedDoctor == -1) {
            JOptionPane.showMessageDialog(null,"Please Select Doctor to Delete !!", "Error!", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String selectedDoctorId = this.jTableDoctors.getValueAt(selectedDoctor, 0).toString();

        String confirmationMessage = "Really delete Doctor: " + this.jTableDoctors.getValueAt(selectedDoctor, 2).toString() + " ?";
        int chosenOption = JOptionPane.showConfirmDialog(null, confirmationMessage, "Delete", JOptionPane.YES_NO_OPTION);

        if (chosenOption == JOptionPane.YES_OPTION) {
            if (doctorDao.deleteDoctorById(Integer.parseInt(selectedDoctorId))) {
                JOptionPane.showMessageDialog(null, "Doctor deleted successfully!", "Delete!", JOptionPane.INFORMATION_MESSAGE);
            }
            searchDoctor(null);
        } else {
            searchDoctor(null);
        }
    }//GEN-LAST:event_deleteBtnActionPerformed

    private void cancelBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelBtnActionPerformed
        this.dispose();
    }//GEN-LAST:event_cancelBtnActionPerformed

    private void searchTxtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchTxtKeyReleased
        this.searchDoctor(searchTxt.getText());
    }//GEN-LAST:event_searchTxtKeyReleased

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addBtn;
    private javax.swing.JButton cancelBtn;
    private javax.swing.JButton deleteBtn;
    private javax.swing.JLabel displayingDoctorsCountLbl;
    private javax.swing.JScrollPane jScrollPane;
    private javax.swing.JTable jTableDoctors;
    private javax.swing.JLabel searchLbl;
    private javax.swing.JTextField searchTxt;
    private javax.swing.JButton updateBtn;
    // End of variables declaration//GEN-END:variables

}
