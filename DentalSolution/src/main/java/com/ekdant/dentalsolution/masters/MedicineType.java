/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ekdant.dentalsolution.masters;

import com.ekdant.dentalsolution.dao.MedicineDao;
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
public class MedicineType extends javax.swing.JDialog {

    MedicineDao medicineDao;
    List<String> medicineTypeList;
    /**
     * Creates new form Medicine Type
     */
    public MedicineType() {
        medicineDao = new MedicineDao();
        this.setModal(true);
        initComponents();
        populateMedicineTypeTable("");
    }

    
    private void populateMedicineTypeTable(String searchData) {
        DefaultTableModel model = (DefaultTableModel) medicineTypeTbl.getModel();
        model.setNumRows(0);
        medicineTypeList = new ArrayList<String>();
        List<String> medicineTypes = medicineDao.fetchMedicineTypesForSearch(searchData);
        for (String medicineType : medicineTypes) {
            model.addRow(new Object[]{
                medicineType
            });
            medicineTypeList.add(medicineType);
        }
    }

    
    
    private void saveMedicineType() throws HeadlessException {
        String newMedicineType = searchTxt.getText();
        if(newMedicineType.isEmpty() && newMedicineType.length()>0){
            if(medicineDao.medicineTypeNotPresent(newMedicineType)) {
                if (medicineDao.insertMedicineType(newMedicineType)) {
                    messageLbl.setForeground(Color.blue);
                    messageLbl.setText("Medicine Type added successfully!!");
                    messagePanel.setVisible(true);
                }
            }
            else{
                messageLbl.setForeground(Color.red);
                messageLbl.setText("Medicine Type already present!");
                messagePanel.setVisible(true);
            }
        }else{
            messageLbl.setForeground(Color.red);
            messageLbl.setText("Medicine Type Should Not Be Empty!");
            messagePanel.setVisible(true);
        }
        searchTxt.setText("");
        populateMedicineTypeTable("");
    }
        
    private void updateMedicineType() {
        boolean updateFlag = false;
        medicineTypeTbl.getCellEditor().stopCellEditing();
        int totalRows= medicineTypeTbl.getModel().getRowCount();
        for(int row=0;row<totalRows;row++){
            if(!(medicineTypeList.get(row).equalsIgnoreCase(medicineTypeTbl.getValueAt(row, 0).toString())))
            {
                String oldMedicineType = medicineTypeList.get(row);
                String newMedicineType = medicineTypeTbl.getValueAt(row, 0).toString();
                if(medicineDao.medicineTypeNotPresent(newMedicineType)){
                    if(medicineDao.updateMedicineType(oldMedicineType, newMedicineType)){
                        updateFlag = true;
                    }else{
                        messageLbl.setForeground(Color.red);
                        messageLbl.setText("Error updating Medicine Type!!");
                        messagePanel.setVisible(true);
                    }
                }
            }
        }
        if(updateFlag){
            messageLbl.setForeground(Color.blue);
            messageLbl.setText("Medicine Type updated successfully!");
            messagePanel.setVisible(true);
        }else{
             messageLbl.setForeground(Color.blue);
            messageLbl.setText("Nothing to Update!");
            messagePanel.setVisible(true);
        }
        searchTxt.setText("");
        populateMedicineTypeTable("");
    }
     
     private void deleteMedicineType() {
         int selectedRow = medicineTypeTbl.getSelectedRow();
         if (selectedRow >= 0) {
             String medicineType = medicineTypeTbl.getValueAt(selectedRow, 0).toString();
             if (medicineDao.deleteMedicineType(medicineType)) {
                 messageLbl.setForeground(Color.blue);
                 messageLbl.setText("Medicine Type Deleted Successfully !");
                 messagePanel.setVisible(true);
             } else {
                 messageLbl.setForeground(Color.red);
                 messageLbl.setText("Error Deleting Medicine Type!!");
                 messagePanel.setVisible(true);
             }

         } else {
             messageLbl.setForeground(Color.red);
             messageLbl.setText("No Medicine Type Selected to Delete!");
             messagePanel.setVisible(true);
         }
         searchTxt.setText("");
         populateMedicineTypeTable("");
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        medicineTypePanel = new javax.swing.JPanel();
        searchTxt = new javax.swing.JTextField();
        addBtn = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        medicineTypeTbl = new javax.swing.JTable();
        messagePanel = new javax.swing.JPanel();
        messageLbl = new javax.swing.JLabel();
        updateBtn = new javax.swing.JButton();
        deleteBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Medicine Type");

        searchTxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchTxtKeyReleased(evt);
            }
        });

        addBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/EkDant/icones/Medicine Types 1.jpg"))); // NOI18N
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

        medicineTypeTbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Medicine Type"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(medicineTypeTbl);

        javax.swing.GroupLayout messagePanelLayout = new javax.swing.GroupLayout(messagePanel);
        messagePanel.setLayout(messagePanelLayout);
        messagePanelLayout.setHorizontalGroup(
            messagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(messagePanelLayout.createSequentialGroup()
                .addComponent(messageLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        messagePanelLayout.setVerticalGroup(
            messagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(messageLbl, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
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

        javax.swing.GroupLayout medicineTypePanelLayout = new javax.swing.GroupLayout(medicineTypePanel);
        medicineTypePanel.setLayout(medicineTypePanelLayout);
        medicineTypePanelLayout.setHorizontalGroup(
            medicineTypePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(medicineTypePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(medicineTypePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(medicineTypePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(medicineTypePanelLayout.createSequentialGroup()
                            .addComponent(searchTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(72, 72, 72)
                            .addComponent(addBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jScrollPane1)
                        .addComponent(messagePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(medicineTypePanelLayout.createSequentialGroup()
                        .addComponent(updateBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(deleteBtn)))
                .addContainerGap(139, Short.MAX_VALUE))
        );

        medicineTypePanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {addBtn, deleteBtn, updateBtn});

        medicineTypePanelLayout.setVerticalGroup(
            medicineTypePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(medicineTypePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(medicineTypePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(messagePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(medicineTypePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(updateBtn)
                    .addComponent(deleteBtn))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        medicineTypePanelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {addBtn, deleteBtn, updateBtn});

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(medicineTypePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(medicineTypePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void addBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addBtnActionPerformed
        saveMedicineType();
    }//GEN-LAST:event_addBtnActionPerformed

    private void searchTxtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchTxtKeyReleased
        populateMedicineTypeTable(searchTxt.getText());
    }//GEN-LAST:event_searchTxtKeyReleased

    private void addBtnKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_addBtnKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
             saveMedicineType();
        }
    }//GEN-LAST:event_addBtnKeyPressed

    private void updateBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateBtnActionPerformed
       updateMedicineType();
    }//GEN-LAST:event_updateBtnActionPerformed

    private void deleteBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteBtnActionPerformed
        deleteMedicineType();
    }//GEN-LAST:event_deleteBtnActionPerformed
 
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addBtn;
    private javax.swing.JButton deleteBtn;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel medicineTypePanel;
    private javax.swing.JTable medicineTypeTbl;
    private javax.swing.JLabel messageLbl;
    private javax.swing.JPanel messagePanel;
    private javax.swing.JTextField searchTxt;
    private javax.swing.JButton updateBtn;
    // End of variables declaration//GEN-END:variables
}
