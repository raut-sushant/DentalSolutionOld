/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ekdant.dentalsolution.masters;

import com.ekdant.dentalsolution.dao.LabDao;
import com.ekdant.dentalsolution.domain.LabWorkNameBean;
import java.awt.Color;
import java.awt.HeadlessException;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.List;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Sushant Raut
 */
public class LabWorkName extends javax.swing.JDialog {

    LabDao labDao;
    HashMap<Integer,String> labWorkNameMap;
    /**
     * Creates new form Cities
     */
    public LabWorkName() {
        labDao = new LabDao();
        labWorkNameMap = new HashMap<Integer, String>();
        this.setModal(true);
        initComponents();
        populateLabWorkNameTable("");
        initLabWorkNameTable();
        
    }
    
    private void saveLabWorkName() throws HeadlessException {
        String newLabWorkName = searchTxt.getText();
        if(newLabWorkName.isEmpty() && newLabWorkName.length()>0){
            if(labDao.labWorkNameNotPresent(newLabWorkName)){
                LabWorkNameBean labWorkName = new LabWorkNameBean();
                labWorkName.setName(newLabWorkName);
                labDao.addLabWorkName(labWorkName);
                messageLbl.setForeground(Color.blue);
                messageLbl.setText( "Lab Work Name added successfully!");
                messagePanel.setVisible(true);
            } else{
                messageLbl.setForeground(Color.red);
                messageLbl.setText("Lab Work Name already present!");
                messagePanel.setVisible(true);
            }
        }else{
            messageLbl.setForeground(Color.red);
            messageLbl.setText("Lab Work Name Should Not Be Empty!");
            messagePanel.setVisible(true);
        }
        searchTxt.setText("");
        populateLabWorkNameTable("");
    }
    
    private void populateLabWorkNameTable(String searchData) {
        DefaultTableModel model = (DefaultTableModel)labWorkNameTbl.getModel();
        model.setNumRows(0);
        List<LabWorkNameBean> labWorkNames = labDao.fetchLabWorkNames(searchData);
        for(LabWorkNameBean labWorkName : labWorkNames) {
            model.addRow(new Object[]{
                labWorkName.getId(),
                labWorkName.getName(),});
            labWorkNameMap.put(labWorkName.getId(), labWorkName.getName());
        }        
    }
    
    private void initLabWorkNameTable() {
        ListSelectionModel selectionModel = labWorkNameTbl.getSelectionModel();
        //selectionModel.
              
    }
    
    private void updateCities() {
        boolean updateFlag = false;
        labWorkNameTbl.getCellEditor().stopCellEditing();
        int totalRows= labWorkNameTbl.getModel().getRowCount();
        for(int row=0;row<totalRows;row++){
            if (!(labWorkNameMap.get(Integer.parseInt(labWorkNameTbl.getValueAt(row, 0).toString()))).equalsIgnoreCase(labWorkNameTbl.getValueAt(row, 1).toString())) {
                if (labDao.labWorkNameNotPresent(labWorkNameTbl.getValueAt(row, 1).toString())) {
                    LabWorkNameBean labWorkName = new LabWorkNameBean();
                    labWorkName.setId(Integer.parseInt(labWorkNameTbl.getValueAt(row, 0).toString()));
                    labWorkName.setName(labWorkNameTbl.getValueAt(row, 1).toString());

                    if (labDao.updateLabWorkName(labWorkName)) {
                        updateFlag = true;
                    } else {
                        messageLbl.setForeground(Color.red);
                        messageLbl.setText("Error updating Lab Work Name!!");
                        messagePanel.setVisible(true);
                    }
                }
            }
        }
        if(updateFlag){
            messageLbl.setForeground(Color.blue);
            messageLbl.setText("Lab Work Name updated successfully!");
            messagePanel.setVisible(true);
        }else{
             messageLbl.setForeground(Color.blue);
            messageLbl.setText("Nothing to Update!");
            messagePanel.setVisible(true);
        }
        searchTxt.setText("");
        populateLabWorkNameTable("");
    }
    
    private void deleteLabWorkName() {
        int selectedRow= labWorkNameTbl.getSelectedRow();
            if(selectedRow>=0){
                LabWorkNameBean labWorkName = new LabWorkNameBean();
                labWorkName.setId(Integer.parseInt(labWorkNameTbl.getValueAt(selectedRow, 0).toString()));
                if (labDao.deleteLabWorkName(labWorkName)) {
                    messageLbl.setForeground(Color.blue);
                    messageLbl.setText("Lab Work Name Deleted Successfully !");
                    messagePanel.setVisible(true);
                } else {
                    messageLbl.setForeground(Color.red);
                    messageLbl.setText("Error Deleting Lab Work Name!!");
                    messagePanel.setVisible(true);
                }
   
        }else{
            messageLbl.setForeground(Color.red);
            messageLbl.setText("No Lab Work Name Selected to Delete!");
            messagePanel.setVisible(true);
        }
        searchTxt.setText("");
        populateLabWorkNameTable("");
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cityPanel = new javax.swing.JPanel();
        searchTxt = new javax.swing.JTextField();
        addBtn = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        labWorkNameTbl = new javax.swing.JTable();
        updateBtn = new javax.swing.JButton();
        deleteBtn = new javax.swing.JButton();
        messagePanel = new javax.swing.JPanel();
        messageLbl = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cities");

        searchTxt.setNextFocusableComponent(addBtn);
        searchTxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                searchTxtKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchTxtKeyReleased(evt);
            }
        });

        addBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/EkDant/icones/Cities.jpg"))); // NOI18N
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

        labWorkNameTbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "City Id", "Lab Work Name"
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
        jScrollPane1.setViewportView(labWorkNameTbl);
        if (labWorkNameTbl.getColumnModel().getColumnCount() > 0) {
            labWorkNameTbl.getColumnModel().getColumn(0).setMinWidth(0);
            labWorkNameTbl.getColumnModel().getColumn(0).setPreferredWidth(0);
            labWorkNameTbl.getColumnModel().getColumn(0).setMaxWidth(0);
        }

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

        javax.swing.GroupLayout messagePanelLayout = new javax.swing.GroupLayout(messagePanel);
        messagePanel.setLayout(messagePanelLayout);
        messagePanelLayout.setHorizontalGroup(
            messagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(messagePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(messageLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        messagePanelLayout.setVerticalGroup(
            messagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(messagePanelLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(messageLbl, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout cityPanelLayout = new javax.swing.GroupLayout(cityPanel);
        cityPanel.setLayout(cityPanelLayout);
        cityPanelLayout.setHorizontalGroup(
            cityPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cityPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(cityPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(cityPanelLayout.createSequentialGroup()
                        .addComponent(searchTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(72, 72, 72)
                        .addComponent(addBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1)
                    .addGroup(cityPanelLayout.createSequentialGroup()
                        .addComponent(updateBtn)
                        .addGap(18, 18, 18)
                        .addComponent(deleteBtn))
                    .addComponent(messagePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(139, Short.MAX_VALUE))
        );

        cityPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {addBtn, deleteBtn, updateBtn});

        cityPanelLayout.setVerticalGroup(
            cityPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cityPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(cityPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addComponent(messagePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(cityPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(updateBtn)
                    .addComponent(deleteBtn))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        cityPanelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {addBtn, deleteBtn, updateBtn});

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cityPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cityPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void searchTxtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchTxtKeyReleased
        populateLabWorkNameTable(searchTxt.getText());
    }//GEN-LAST:event_searchTxtKeyReleased

    private void addBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addBtnActionPerformed
        saveLabWorkName();
    }//GEN-LAST:event_addBtnActionPerformed

    private void addBtnKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_addBtnKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
             saveLabWorkName();
        }
    }//GEN-LAST:event_addBtnKeyPressed

    private void updateBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateBtnActionPerformed
        updateCities();
    }//GEN-LAST:event_updateBtnActionPerformed
    
    private void searchTxtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchTxtKeyPressed
       messagePanel.setVisible(false);
    }//GEN-LAST:event_searchTxtKeyPressed

    private void deleteBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteBtnActionPerformed
        deleteLabWorkName();
    }//GEN-LAST:event_deleteBtnActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addBtn;
    private javax.swing.JPanel cityPanel;
    private javax.swing.JButton deleteBtn;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable labWorkNameTbl;
    private javax.swing.JLabel messageLbl;
    private javax.swing.JPanel messagePanel;
    private javax.swing.JTextField searchTxt;
    private javax.swing.JButton updateBtn;
    // End of variables declaration//GEN-END:variables
}
