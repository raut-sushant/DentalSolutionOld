/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ekdant.dentalsolution.masters;

import com.ekdant.dentalsolution.dao.PreMedicalHistoryDao;
import com.ekdant.dentalsolution.domain.PreMedicalHistoryBean;
import java.awt.Color;
import java.awt.HeadlessException;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Sushant Raut
 */
public class PreMedicalHistory extends javax.swing.JDialog {

    PreMedicalHistoryDao premedicalHistoryDao;
    HashMap<Integer, String> preMedicalHistoryMap;
    /**
     * Creates new form Pre Medical History
     */
    public PreMedicalHistory() {
        premedicalHistoryDao = new PreMedicalHistoryDao();
        this.setModal(true);
        initComponents();
        messagePanel.setVisible(false);
        populatePreMedicalHistoryTable("");
    }
    
    private void populatePreMedicalHistoryTable(String searchData) {
        DefaultTableModel model = (DefaultTableModel)preMedicalHistoryTbl.getModel();
        preMedicalHistoryMap = new HashMap<Integer, String>();
        model.setNumRows(0);
        List<PreMedicalHistoryBean> preMedicalHistories = premedicalHistoryDao.fetchPreMedicalHistory(searchData);
        for (PreMedicalHistoryBean preMedicalHistory : preMedicalHistories) {
            model.addRow(new Object[]{
                preMedicalHistory.getId(),
                preMedicalHistory.getDiscription()
            });
            preMedicalHistoryMap.put(preMedicalHistory.getId(), preMedicalHistory.getDiscription());
        }
    }    
    
    private void savePreMedicalHistory() throws HeadlessException {
        String newPreMedicalHistory = searchTxt.getText();
        if(newPreMedicalHistory.isEmpty() && newPreMedicalHistory.length()>0){
            PreMedicalHistoryBean preMedicalHistory = new PreMedicalHistoryBean();
            preMedicalHistory.setDiscription(newPreMedicalHistory);
            if(premedicalHistoryDao.preMedicalHistoryNotPresent(preMedicalHistory.getDiscription())){
                premedicalHistoryDao.addPreMedicalHistory(preMedicalHistory);
                messageLbl.setForeground(Color.blue);
                messageLbl.setText("Pre Medical History added successfully!");
                messagePanel.setVisible(true);
            }
            else{
                messageLbl.setForeground(Color.red);
                messageLbl.setText("Pre Medical History already present!");
                messagePanel.setVisible(true);
            }
        }else{
            messageLbl.setForeground(Color.red);
            messageLbl.setText("Pre Medical History Should not be Empty!");
            messagePanel.setVisible(true);
        }
        searchTxt.setText("");
        populatePreMedicalHistoryTable("");
    }

    
     private void updatePreMedicalhistory() {
        boolean updateFlag = false;
        preMedicalHistoryTbl.getCellEditor().stopCellEditing();
        HashMap<Integer,String> tableCities = new HashMap<Integer, String>();
        int totalRows= preMedicalHistoryTbl.getModel().getRowCount();
        for(int row=0;row<totalRows;row++){
            if(!(preMedicalHistoryMap.get(Integer.parseInt(preMedicalHistoryTbl.getValueAt(row, 0).toString()))).equalsIgnoreCase(preMedicalHistoryTbl.getValueAt(row, 1).toString()))
            {
                PreMedicalHistoryBean preMedicalHistory = new PreMedicalHistoryBean();
                preMedicalHistory.setDiscription(preMedicalHistoryTbl.getValueAt(row, 1).toString());
                preMedicalHistory.setId(Integer.parseInt(preMedicalHistoryTbl.getValueAt(row, 0).toString()));

                if (premedicalHistoryDao.preMedicalHistoryNotPresent(preMedicalHistory.getDiscription())) {

                    if (premedicalHistoryDao.updatePreMedicalHistory(preMedicalHistory)) {
                        updateFlag = true;
                    } else {
                        messageLbl.setForeground(Color.red);
                        messageLbl.setText("Error updating Pre Medical History!!");
                        messagePanel.setVisible(true);
                    }
                }
            }
        }
        if(updateFlag){
            messageLbl.setForeground(Color.blue);
            messageLbl.setText("Pre Medical History updated successfully!");
            messagePanel.setVisible(true);
        }else{
            messageLbl.setForeground(Color.blue);
            messageLbl.setText("Nothing to Update!");
            messagePanel.setVisible(true);
        }
        searchTxt.setText("");
        populatePreMedicalHistoryTable("");
    }
     
     private void deletePreMedicalHistory() {
         int selectedRow = preMedicalHistoryTbl.getSelectedRow();
         if (selectedRow >= 0) {
             PreMedicalHistoryBean preMedicalHistory = new PreMedicalHistoryBean();
             preMedicalHistory.setId(Integer.parseInt(preMedicalHistoryTbl.getValueAt(selectedRow, 0).toString()));
             if (premedicalHistoryDao.deletePreMedicalHistory(preMedicalHistory)) {
                 messageLbl.setForeground(Color.blue);
                 messageLbl.setText("Pre Medical History Deleted Successfully !");
                 messagePanel.setVisible(true);
             } else {
                 messageLbl.setForeground(Color.red);
                 messageLbl.setText("Error Deleting Pre Medical History!!");
                 messagePanel.setVisible(true);
             }

         } else {
             messageLbl.setForeground(Color.red);
             messageLbl.setText("No Category Selected to Delete!");
             messagePanel.setVisible(true);
         }
         searchTxt.setText("");
         populatePreMedicalHistoryTable("");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        preMedicalHistoryPanel = new javax.swing.JPanel();
        searchTxt = new javax.swing.JTextField();
        addBtn = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        preMedicalHistoryTbl = new javax.swing.JTable();
        messagePanel = new javax.swing.JPanel();
        messageLbl = new javax.swing.JLabel();
        updateBtn = new javax.swing.JButton();
        deleteBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Pre Medical History");

        searchTxt.setNextFocusableComponent(addBtn);
        searchTxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                searchTxtKeyPressed(evt);
            }
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

        preMedicalHistoryTbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Pre Medical History"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(preMedicalHistoryTbl);
        if (preMedicalHistoryTbl.getColumnModel().getColumnCount() > 0) {
            preMedicalHistoryTbl.getColumnModel().getColumn(0).setMinWidth(0);
            preMedicalHistoryTbl.getColumnModel().getColumn(0).setPreferredWidth(0);
            preMedicalHistoryTbl.getColumnModel().getColumn(0).setMaxWidth(0);
        }

        javax.swing.GroupLayout messagePanelLayout = new javax.swing.GroupLayout(messagePanel);
        messagePanel.setLayout(messagePanelLayout);
        messagePanelLayout.setHorizontalGroup(
            messagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        messagePanelLayout.setVerticalGroup(
            messagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
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

        javax.swing.GroupLayout preMedicalHistoryPanelLayout = new javax.swing.GroupLayout(preMedicalHistoryPanel);
        preMedicalHistoryPanel.setLayout(preMedicalHistoryPanelLayout);
        preMedicalHistoryPanelLayout.setHorizontalGroup(
            preMedicalHistoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(preMedicalHistoryPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(preMedicalHistoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(preMedicalHistoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(preMedicalHistoryPanelLayout.createSequentialGroup()
                            .addComponent(searchTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(72, 72, 72)
                            .addComponent(addBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jScrollPane1)
                        .addGroup(preMedicalHistoryPanelLayout.createSequentialGroup()
                            .addComponent(messageLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 442, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(messagePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(preMedicalHistoryPanelLayout.createSequentialGroup()
                        .addComponent(updateBtn)
                        .addGap(18, 18, 18)
                        .addComponent(deleteBtn)))
                .addContainerGap(94, Short.MAX_VALUE))
        );

        preMedicalHistoryPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {addBtn, deleteBtn, updateBtn});

        preMedicalHistoryPanelLayout.setVerticalGroup(
            preMedicalHistoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(preMedicalHistoryPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(preMedicalHistoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(preMedicalHistoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(messagePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(messageLbl, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(preMedicalHistoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(updateBtn)
                    .addComponent(deleteBtn))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        preMedicalHistoryPanelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {addBtn, deleteBtn, updateBtn});

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(preMedicalHistoryPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(preMedicalHistoryPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void searchTxtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchTxtKeyReleased
        populatePreMedicalHistoryTable(searchTxt.getText());
    }//GEN-LAST:event_searchTxtKeyReleased

    private void addBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addBtnActionPerformed
        savePreMedicalHistory();
    }//GEN-LAST:event_addBtnActionPerformed

    private void addBtnKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_addBtnKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
             savePreMedicalHistory();
        }
    }//GEN-LAST:event_addBtnKeyPressed

    private void deleteBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteBtnActionPerformed
        deletePreMedicalHistory();
    }//GEN-LAST:event_deleteBtnActionPerformed

    private void searchTxtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchTxtKeyPressed
        messagePanel.setVisible(false);
    }//GEN-LAST:event_searchTxtKeyPressed

    private void updateBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateBtnActionPerformed
        updatePreMedicalhistory();
    }//GEN-LAST:event_updateBtnActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addBtn;
    private javax.swing.JButton deleteBtn;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel messageLbl;
    private javax.swing.JPanel messagePanel;
    private javax.swing.JPanel preMedicalHistoryPanel;
    private javax.swing.JTable preMedicalHistoryTbl;
    private javax.swing.JTextField searchTxt;
    private javax.swing.JButton updateBtn;
    // End of variables declaration//GEN-END:variables
}
