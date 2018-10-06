/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ekdant.dentalsolution.masters;

import com.ekdant.dentalsolution.dao.MedicineDao;
import com.ekdant.dentalsolution.domain.MedicineBean;
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
public class Medicine extends javax.swing.JDialog {

    MedicineDao medicineDao;
    List<MedicineBean> medicineList;
    /**
     * Creates new form Medicine
     */
    public Medicine() {
        medicineDao = new MedicineDao();
        this.setModal(true);
        initComponents();
        populateMedicineTable("");
        newMedicinePanel.setVisible(false);
        messagePanel.setVisible(false);
    }
    
    private void populateMedicineTable(String searchData) {
        DefaultTableModel model = (DefaultTableModel)medicineTbl.getModel();
        model.setNumRows(0);
        medicineList= new ArrayList<MedicineBean>();
        List<MedicineBean> medicines = medicineDao.fetchMedicinesForSearch(searchData);
        for(MedicineBean medicine : medicines) {
            model.addRow(new Object[]{
                medicine.getId(),
                medicine.getType(),
                medicine.getName(),
                medicine.getStrength()
            });
            medicineList.add(medicine);
        }        
    }

    private void showNewMedicine() {
        newMedicineNameTxt.setText(searchTxt.getText());
        for(String medicineType : medicineDao.fetchMedicineTypes()){
            newMedicineTypeCB.addItem(medicineType);
        }
        newMedicinePanel.setVisible(true);
        messagePanel.setVisible(false);
    }
    
    private void persistMedicine() throws HeadlessException {
        MedicineBean medicine = new MedicineBean();
        medicine.setName(newMedicineNameTxt.getText());
        medicine.setStrength(newMedicineStrengthTxt.getText());
        medicine.setType(newMedicineTypeCB.getSelectedItem().toString());
        if (medicine.getName() != null && medicine.getName().length() > 0) {
            if (!medicineDao.medicineNotPresent(medicine.getName())) {
                messageLbl.setForeground(Color.red);
                messageLbl.setText("Medicine already present!");
                messagePanel.setVisible(true);
                newMedicinePanel.setVisible(false);
            } else if (medicineDao.addMedicineIfNotPresent(medicine)) {
                messageLbl.setForeground(Color.blue);
                messageLbl.setText("Medicine added successfully!");
                messagePanel.setVisible(true);
                newMedicinePanel.setVisible(false);
                searchTxt.setText("");
                populateMedicineTable("");
            } else {
                messageLbl.setForeground(Color.red);
                messageLbl.setText("Error occored while adding medicine!");
                messagePanel.setVisible(true);
                newMedicinePanel.setVisible(false);
            }
        } else {
            messageLbl.setForeground(Color.red);
            messageLbl.setText("Medicine Name Should Not Be Empty!");
            messagePanel.setVisible(true);
            newMedicinePanel.setVisible(false);
        }
    }
        
    private void updateClicked() throws NumberFormatException {
        int totalRows = medicineTbl.getModel().getRowCount();
        medicineTbl.getCellEditor().stopCellEditing();
        for (int row = 0; row < totalRows; row++) {
            MedicineBean medicine = new MedicineBean();
            medicine.setId(Integer.parseInt(medicineTbl.getValueAt(row, 0).toString()));
            medicine.setType(medicineTbl.getValueAt(row, 1).toString());
            medicine.setName(medicineTbl.getValueAt(row, 2).toString());
            medicine.setStrength(medicineTbl.getValueAt(row, 3)!=null?medicineTbl.getValueAt(row, 3).toString():"");
            if (!medicineList.get(row).equals(medicine)) {
                updateMedicine(medicine);
            }
        }
    }
    
    private void updateMedicine(MedicineBean medicine) {
        if (medicineDao.updateMedicine(medicine)) {
            messageLbl.setForeground(Color.blue);
            messageLbl.setText("Records updated Sucessfully!");
            messagePanel.setVisible(true);
            newMedicinePanel.setVisible(false);
            populateMedicineTable(searchTxt.getText());
        } else {
            messageLbl.setForeground(Color.red);
            messageLbl.setText("Error while updating Medicine");
            messagePanel.setVisible(true);
            newMedicinePanel.setVisible(false);
        }
    }
    
    private void deleteMedicine() throws NumberFormatException {
        int selectedRow = medicineTbl.getSelectedRow();
        if (selectedRow == -1) {
            messageLbl.setForeground(Color.red);
            messageLbl.setText("Please Select Patient to Delete !!");
            messagePanel.setVisible(true);
            newMedicinePanel.setVisible(false);
        } else {
            String selectedId = medicineTbl.getValueAt(selectedRow, 0).toString();
            MedicineBean medicine = new MedicineBean();
            medicine.setId(Integer.parseInt(selectedId));
            if (medicineDao.deleteMedicine(medicine)) {
                messageLbl.setForeground(Color.blue);
                messageLbl.setText("Record Deleted Successfully!");
                populateMedicineTable(searchTxt.getText());
            } else {
                messageLbl.setForeground(Color.red);
                messageLbl.setText("Error in Deletion!");
            }
            messagePanel.setVisible(true);
            newMedicinePanel.setVisible(false);
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

        medicinePanel = new javax.swing.JPanel();
        searchTxt = new javax.swing.JTextField();
        addBtn = new javax.swing.JButton();
        medicineTableScrollPane = new javax.swing.JScrollPane();
        medicineTbl = new javax.swing.JTable();
        newMedicinePanel = new javax.swing.JPanel();
        newMedicineNameTxt = new javax.swing.JTextField();
        newMedicineTypeCB = new javax.swing.JComboBox();
        newMedicineStrengthTxt = new javax.swing.JTextField();
        saveNewMedicineBtn = new javax.swing.JButton();
        messagePanel = new javax.swing.JPanel();
        messageLbl = new javax.swing.JLabel();
        updateBtn = new javax.swing.JButton();
        deleteBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Medicine");

        searchTxt.setNextFocusableComponent(addBtn);
        searchTxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchTxtKeyReleased(evt);
            }
        });

        addBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/EkDant/icones/Medical Planner.png"))); // NOI18N
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

        medicineTbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Medicine Id", "Medicine Type", "Medicine Name", "Medicine Strength"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        medicineTableScrollPane.setViewportView(medicineTbl);
        if (medicineTbl.getColumnModel().getColumnCount() > 0) {
            medicineTbl.getColumnModel().getColumn(0).setMinWidth(0);
            medicineTbl.getColumnModel().getColumn(0).setPreferredWidth(0);
            medicineTbl.getColumnModel().getColumn(0).setMaxWidth(0);
        }

        newMedicineNameTxt.setNextFocusableComponent(newMedicineStrengthTxt);

        newMedicineTypeCB.setNextFocusableComponent(newMedicineNameTxt);

        newMedicineStrengthTxt.setNextFocusableComponent(saveNewMedicineBtn);

        saveNewMedicineBtn.setText("Save");
        saveNewMedicineBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveNewMedicineBtnActionPerformed(evt);
            }
        });
        saveNewMedicineBtn.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                saveNewMedicineBtnKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout newMedicinePanelLayout = new javax.swing.GroupLayout(newMedicinePanel);
        newMedicinePanel.setLayout(newMedicinePanelLayout);
        newMedicinePanelLayout.setHorizontalGroup(
            newMedicinePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(newMedicinePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(newMedicineTypeCB, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(newMedicineNameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(newMedicineStrengthTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(saveNewMedicineBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(41, Short.MAX_VALUE))
        );
        newMedicinePanelLayout.setVerticalGroup(
            newMedicinePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(newMedicinePanelLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(newMedicinePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(newMedicineNameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(newMedicineTypeCB, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(newMedicineStrengthTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(saveNewMedicineBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout messagePanelLayout = new javax.swing.GroupLayout(messagePanel);
        messagePanel.setLayout(messagePanelLayout);
        messagePanelLayout.setHorizontalGroup(
            messagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(messagePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(messageLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 523, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        messagePanelLayout.setVerticalGroup(
            messagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, messagePanelLayout.createSequentialGroup()
                .addComponent(messageLbl, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                .addContainerGap())
        );

        updateBtn.setText("Update");
        updateBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateBtnActionPerformed(evt);
            }
        });

        deleteBtn.setText("Delete");
        deleteBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout medicinePanelLayout = new javax.swing.GroupLayout(medicinePanel);
        medicinePanel.setLayout(medicinePanelLayout);
        medicinePanelLayout.setHorizontalGroup(
            medicinePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(medicinePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(medicinePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(medicineTableScrollPane)
                    .addComponent(newMedicinePanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(messagePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(medicinePanelLayout.createSequentialGroup()
                        .addGroup(medicinePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(medicinePanelLayout.createSequentialGroup()
                                .addGap(106, 106, 106)
                                .addComponent(searchTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(72, 72, 72)
                                .addComponent(addBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(medicinePanelLayout.createSequentialGroup()
                                .addComponent(updateBtn)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(deleteBtn)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        medicinePanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {addBtn, deleteBtn, updateBtn});

        medicinePanelLayout.setVerticalGroup(
            medicinePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(medicinePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(medicinePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addComponent(newMedicinePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(messagePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(medicineTableScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(medicinePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(updateBtn)
                    .addComponent(deleteBtn))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        medicinePanelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {addBtn, deleteBtn, updateBtn});

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(medicinePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(medicinePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void addBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addBtnActionPerformed
        showNewMedicine();
    }//GEN-LAST:event_addBtnActionPerformed

    private void saveNewMedicineBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveNewMedicineBtnActionPerformed
        persistMedicine();
    }//GEN-LAST:event_saveNewMedicineBtnActionPerformed

    private void saveNewMedicineBtnKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_saveNewMedicineBtnKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
             persistMedicine();
        }
    }//GEN-LAST:event_saveNewMedicineBtnKeyPressed

    private void addBtnKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_addBtnKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
             showNewMedicine();
        }
    }//GEN-LAST:event_addBtnKeyPressed

    private void searchTxtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchTxtKeyReleased
        populateMedicineTable(searchTxt.getText());
    }//GEN-LAST:event_searchTxtKeyReleased

    private void updateBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateBtnActionPerformed
        updateClicked();
    }//GEN-LAST:event_updateBtnActionPerformed

    private void deleteBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteBtnActionPerformed
        deleteMedicine();
    }//GEN-LAST:event_deleteBtnActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addBtn;
    private javax.swing.JButton deleteBtn;
    private javax.swing.JPanel medicinePanel;
    private javax.swing.JScrollPane medicineTableScrollPane;
    private javax.swing.JTable medicineTbl;
    private javax.swing.JLabel messageLbl;
    private javax.swing.JPanel messagePanel;
    private javax.swing.JTextField newMedicineNameTxt;
    private javax.swing.JPanel newMedicinePanel;
    private javax.swing.JTextField newMedicineStrengthTxt;
    private javax.swing.JComboBox newMedicineTypeCB;
    private javax.swing.JButton saveNewMedicineBtn;
    private javax.swing.JTextField searchTxt;
    private javax.swing.JButton updateBtn;
    // End of variables declaration//GEN-END:variables
}
