/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ekdant.dentalsolution.masters;

import com.ekdant.dentalsolution.dao.TreatmentDao;
import com.ekdant.dentalsolution.domain.TreatmentBean;
import java.awt.Color;
import java.awt.HeadlessException;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Sushant Raut
 */
public class Treatment extends javax.swing.JDialog {

    TreatmentDao treatmentDao;
    List<TreatmentBean> treatmentList;
    boolean fromOtherFlow;
    /**
     * Creates new form Treatment
     */
    public Treatment() {
        treatmentDao = new TreatmentDao();
        this.setModal(true);
        initComponents();
        populateTreatmentTable("");
        newTreatmentPanel.setVisible(false);
        this.fromOtherFlow = false;
    }
    
    public Treatment(boolean fromOtherFlow) {
        treatmentDao = new TreatmentDao();
        this.setModal(true);
        initComponents();
        populateTreatmentTable("");
        newTreatmentPanel.setVisible(false);
        this.fromOtherFlow = fromOtherFlow;
    }
   
    private void populateTreatmentTable(String searchData) {
        DefaultTableModel model = (DefaultTableModel) treatmentTbl.getModel();
        treatmentList = new ArrayList<TreatmentBean>();
        model.setNumRows(0);
        List<TreatmentBean> treatments = treatmentDao.fetchTreatments(searchData);
        for (TreatmentBean treatment : treatments) {
            model.addRow(new Object[]{
                treatment.getTreatmentId(),
                treatment.getTreatmentName(),
                treatment.getTreatmentDescription(),
                treatment.getTreatmentCharges()
            });
            treatmentList.add(treatment);
        }
    }

    private void showNewTreatment() {
        newTreatmentNameTxt.setText(searchTxt.getText());
        newTreatmentPanel.setVisible(true);
        messagePanel.setVisible(false);
    }
    
    private void persistTreatment() throws HeadlessException {
        String newTreatmentName = newTreatmentNameTxt.getText();
        if(newTreatmentName.isEmpty() && newTreatmentName.length()>0){
            if(treatmentDao.treatmentNotPresent(newTreatmentName)){
                TreatmentBean treatment = new TreatmentBean();
                treatment.setTreatmentName(newTreatmentName);
                treatment.setTreatmentDescription(newTreatmentDiscriptionTxt.getText());
                treatment.setTreatmentCharges(Float.parseFloat(newTreatmentChargeTxt.getText()));

                if (treatmentDao.addTreatment(treatment)) {
                    messageLbl.setForeground(Color.blue);
                    messageLbl.setText("Treatment added successfully!");
                    messagePanel.setVisible(true);
                    newTreatmentPanel.setVisible(false);
                    searchTxt.setText("");
                    populateTreatmentTable("");
                } else {
                    messageLbl.setForeground(Color.red);
                    messageLbl.setText("Error occored while adding treatment!");
                    messagePanel.setVisible(true);
                    newTreatmentPanel.setVisible(false);
                }                
            }
            else{
                messageLbl.setForeground(Color.red);
                messageLbl.setText("Treatment already present!");
                messagePanel.setVisible(true);
                newTreatmentPanel.setVisible(false);
            }
        }else{
            messageLbl.setForeground(Color.red);
            messageLbl.setText("Treatment Name Should not Be Blank!");
            messagePanel.setVisible(true);
            newTreatmentPanel.setVisible(false);
        }
        if(fromOtherFlow){
            this.dispose();
        }
    }
    
    private void updateClicked() throws NumberFormatException {
        treatmentTbl.getCellEditor().stopCellEditing();
        int totalRows = treatmentTbl.getModel().getRowCount();
        for (int row = 0; row < totalRows; row++) {
            TreatmentBean treatment = new TreatmentBean();
            treatment.setTreatmentId(Integer.parseInt(treatmentTbl.getValueAt(row, 0).toString()));
            treatment.setTreatmentName(treatmentTbl.getValueAt(row, 1).toString());
            treatment.setTreatmentDescription(treatmentTbl.getValueAt(row, 2)!=null?treatmentTbl.getValueAt(row, 2).toString():"");
            treatment.setTreatmentCharges(treatmentTbl.getValueAt(row, 3)!=null?Float.parseFloat(treatmentTbl.getValueAt(row, 3).toString()):0.0f);
            if (!treatmentList.get(row).equals(treatment)) {
                updateTreatment(treatment);
            }
        }
    }
    
    private void updateTreatment(TreatmentBean treatment) {
        if (treatmentDao.updateTreatment(treatment)) {
            messageLbl.setForeground(Color.blue);
            messageLbl.setText("Records updated Sucessfully!");
            messagePanel.setVisible(true);
            newTreatmentPanel.setVisible(false);
            populateTreatmentTable(searchTxt.getText());
        } else {
            messageLbl.setForeground(Color.red);
            messageLbl.setText("Error while updating ReferedBy");
            messagePanel.setVisible(true);
            newTreatmentPanel.setVisible(false);
        }        
    }
    
    private void deleteTreatment() throws NumberFormatException {
        int selectedRow = treatmentTbl.getSelectedRow();
        if (selectedRow == -1) {
            messageLbl.setForeground(Color.red);
            messageLbl.setText("Please Select Patient to Delete !!");
            messagePanel.setVisible(true);
            newTreatmentPanel.setVisible(false);
        } else {
            String selectedId = treatmentTbl.getValueAt(selectedRow, 0).toString();
            TreatmentBean treatment = new TreatmentBean();
            treatment.setTreatmentId(Integer.parseInt(selectedId));
            if (treatmentDao.deleteTreatment(treatment)) {
                messageLbl.setForeground(Color.blue);
                messageLbl.setText("Record Deleted Successfully!");
                populateTreatmentTable(searchTxt.getText());
            } else {
                messageLbl.setForeground(Color.red);
                messageLbl.setText("Error in Deletion!");
            }

            messagePanel.setVisible(true);
            newTreatmentPanel.setVisible(false);           
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

        treatmentPanel = new javax.swing.JPanel();
        searchTxt = new javax.swing.JTextField();
        addBtn = new javax.swing.JButton();
        treatmentTableScrollPane = new javax.swing.JScrollPane();
        treatmentTbl = new javax.swing.JTable();
        newTreatmentPanel = new javax.swing.JPanel();
        newTreatmentNameTxt = new javax.swing.JTextField();
        newTreatmentChargeTxt = new javax.swing.JTextField();
        saveNewTreatmentBtn = new javax.swing.JButton();
        newTreatmentDiscriptionTxt = new javax.swing.JTextField();
        updateBtn = new javax.swing.JButton();
        deleteBtn = new javax.swing.JButton();
        messagePanel = new javax.swing.JPanel();
        messageLbl = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Treatments");

        searchTxt.setNextFocusableComponent(addBtn);
        searchTxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchTxtKeyReleased(evt);
            }
        });

        addBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/EkDant/icones/Treatment Add.png"))); // NOI18N
        addBtn.setText("Add");
        addBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addBtnActionPerformed(evt);
            }
        });
        addBtn.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                addBtnKeyPressed(evt);
            }
        });

        treatmentTbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Treatment Id", "Treatment Name", "Treatment Discription", "Treatment Charge"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        treatmentTableScrollPane.setViewportView(treatmentTbl);
        if (treatmentTbl.getColumnModel().getColumnCount() > 0) {
            treatmentTbl.getColumnModel().getColumn(0).setMinWidth(0);
            treatmentTbl.getColumnModel().getColumn(0).setPreferredWidth(0);
            treatmentTbl.getColumnModel().getColumn(0).setMaxWidth(0);
        }

        newTreatmentNameTxt.setNextFocusableComponent(newTreatmentDiscriptionTxt);

        newTreatmentChargeTxt.setNextFocusableComponent(saveNewTreatmentBtn);

        saveNewTreatmentBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/EkDant/icones/Treatment Completed.png"))); // NOI18N
        saveNewTreatmentBtn.setText("Save");
        saveNewTreatmentBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveNewTreatmentBtnActionPerformed(evt);
            }
        });
        saveNewTreatmentBtn.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                saveNewTreatmentBtnKeyPressed(evt);
            }
        });

        newTreatmentDiscriptionTxt.setNextFocusableComponent(newTreatmentChargeTxt);

        javax.swing.GroupLayout newTreatmentPanelLayout = new javax.swing.GroupLayout(newTreatmentPanel);
        newTreatmentPanel.setLayout(newTreatmentPanelLayout);
        newTreatmentPanelLayout.setHorizontalGroup(
            newTreatmentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(newTreatmentPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(newTreatmentNameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(newTreatmentDiscriptionTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(newTreatmentChargeTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(saveNewTreatmentBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(65, Short.MAX_VALUE))
        );
        newTreatmentPanelLayout.setVerticalGroup(
            newTreatmentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(newTreatmentPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(newTreatmentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(newTreatmentNameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(newTreatmentChargeTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(saveNewTreatmentBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(newTreatmentDiscriptionTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        updateBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/EkDant/icones/Treatment Completed.png"))); // NOI18N
        updateBtn.setText("Update");
        updateBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateBtnActionPerformed(evt);
            }
        });

        deleteBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/EkDant/icones/Treatment Delete.png"))); // NOI18N
        deleteBtn.setText("Delete");
        deleteBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout messagePanelLayout = new javax.swing.GroupLayout(messagePanel);
        messagePanel.setLayout(messagePanelLayout);
        messagePanelLayout.setHorizontalGroup(
            messagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(messagePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(messageLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        messagePanelLayout.setVerticalGroup(
            messagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(messagePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(messageLbl, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout treatmentPanelLayout = new javax.swing.GroupLayout(treatmentPanel);
        treatmentPanel.setLayout(treatmentPanelLayout);
        treatmentPanelLayout.setHorizontalGroup(
            treatmentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(treatmentPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(treatmentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(treatmentTableScrollPane)
                    .addComponent(newTreatmentPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(treatmentPanelLayout.createSequentialGroup()
                        .addGroup(treatmentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(treatmentPanelLayout.createSequentialGroup()
                                .addGap(106, 106, 106)
                                .addComponent(searchTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(72, 72, 72)
                                .addComponent(addBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(treatmentPanelLayout.createSequentialGroup()
                                .addComponent(updateBtn)
                                .addGap(18, 18, 18)
                                .addComponent(deleteBtn)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(messagePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        treatmentPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {addBtn, deleteBtn, updateBtn});

        treatmentPanelLayout.setVerticalGroup(
            treatmentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(treatmentPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(treatmentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addComponent(newTreatmentPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(messagePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(treatmentTableScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(treatmentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(updateBtn)
                    .addComponent(deleteBtn))
                .addContainerGap())
        );

        treatmentPanelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {addBtn, deleteBtn, updateBtn});

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(treatmentPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(treatmentPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void searchTxtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchTxtKeyReleased
        populateTreatmentTable(searchTxt.getText());
    }//GEN-LAST:event_searchTxtKeyReleased

    private void addBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addBtnActionPerformed
        showNewTreatment();
    }//GEN-LAST:event_addBtnActionPerformed

    private void saveNewTreatmentBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveNewTreatmentBtnActionPerformed
        persistTreatment();
    }//GEN-LAST:event_saveNewTreatmentBtnActionPerformed

    private void saveNewTreatmentBtnKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_saveNewTreatmentBtnKeyPressed
       if(evt.getKeyCode() == KeyEvent.VK_ENTER){
             persistTreatment();
        }
    }//GEN-LAST:event_saveNewTreatmentBtnKeyPressed

    private void addBtnKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_addBtnKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
             showNewTreatment();
        }
    }//GEN-LAST:event_addBtnKeyPressed

    private void updateBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateBtnActionPerformed
       updateClicked();
    }//GEN-LAST:event_updateBtnActionPerformed

    private void deleteBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteBtnActionPerformed
       deleteTreatment();
    }//GEN-LAST:event_deleteBtnActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addBtn;
    private javax.swing.JButton deleteBtn;
    private javax.swing.JLabel messageLbl;
    private javax.swing.JPanel messagePanel;
    private javax.swing.JTextField newTreatmentChargeTxt;
    private javax.swing.JTextField newTreatmentDiscriptionTxt;
    private javax.swing.JTextField newTreatmentNameTxt;
    private javax.swing.JPanel newTreatmentPanel;
    private javax.swing.JButton saveNewTreatmentBtn;
    private javax.swing.JTextField searchTxt;
    private javax.swing.JPanel treatmentPanel;
    private javax.swing.JScrollPane treatmentTableScrollPane;
    private javax.swing.JTable treatmentTbl;
    private javax.swing.JButton updateBtn;
    // End of variables declaration//GEN-END:variables
}
