/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ekdant.dentalsolution.masters;

import com.ekdant.dentalsolution.domain.ReferedByBean;
import com.ekdant.dentalsolution.dao.CityDao;
import com.ekdant.dentalsolution.dao.ReferedByDao;
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
public class ReferedBy extends javax.swing.JDialog {

    List<ReferedByBean> referedByList = null;
    CityDao cityDao;
    ReferedByDao referedByDao;
    boolean fromOtherFlow;
    
    public ReferedBy() {
        referedByDao = new ReferedByDao();
        cityDao = new CityDao();
        this.setModal(true);
        initComponents();
        populateReferedByTable("");
        cityDao.getCities(citiesCB);
        messagePanel.setVisible(false);
        newReferedByPanel.setVisible(false);
        fromOtherFlow = false;
    }
    
    public ReferedBy(boolean fromOtherFlow) {
        referedByDao = new ReferedByDao();
        cityDao = new CityDao();
        this.setModal(true);
        initComponents();
        populateReferedByTable("");
        cityDao.getCities(citiesCB);
        messagePanel.setVisible(false);
        newReferedByPanel.setVisible(false);
        this.fromOtherFlow = fromOtherFlow;
    }
    private void populateReferedByTable(String searchData) {
        referedByList = new ArrayList<ReferedByBean>();
        DefaultTableModel model = (DefaultTableModel) referedByTbl.getModel();
        model.setNumRows(0);
        List<ReferedByBean> refereds = referedByDao.fetchReferedBy(searchData);
        for (ReferedByBean referedBy : refereds) {
            model.addRow(new Object[]{
                referedBy.getId(),
                referedBy.getName(),
                referedBy.getCity(),
                referedBy.getContact(),
                referedBy.getAddress()
            });
            referedByList.add(referedBy);
        }        
    }    
    
    private void persistReferedBy() throws HeadlessException {
        String newReferedByName = newReferedByNameTxt.getText();
        String newReferedByCity = citiesCB.getSelectedItem().toString().equalsIgnoreCase("select") ? "" : citiesCB.getSelectedItem().toString();
        if (newReferedByName != null && newReferedByName.length() > 0) {
            ReferedByBean referedBy = new ReferedByBean();
            referedBy.setName(newReferedByName);
            referedBy.setAddress(newReferedByAddressTxt.getText());
            referedBy.setCity(newReferedByCity);
            referedBy.setContact(newReferedByContactTxt.getText());
            if (referedByDao.referedByNotPresent(referedBy.getName())) {
                if (referedByDao.addReferedBy(referedBy)) {
                    messageLbl.setForeground(Color.blue);
                    messageLbl.setText("Refered by added successfully!");
                    newReferedByPanel.setVisible(false);
                    messagePanel.setVisible(true);
                    newReferedByPanel.setVisible(false);
                    searchTxt.setText("");
                    populateReferedByTable("");
                } else {
                    messageLbl.setForeground(Color.red);
                    messageLbl.setText("Error occored while adding refered by!");
                    messagePanel.setVisible(true);
                    newReferedByPanel.setVisible(false);
                }
            } else {
                messageLbl.setForeground(Color.red);
                messageLbl.setText("Refered by already present!");
                messagePanel.setVisible(true);
                newReferedByPanel.setVisible(false);
            }
        } else {
            messageLbl.setForeground(Color.red);
            messageLbl.setText("Refered by Name Should Not Be Empty!");
            messagePanel.setVisible(true);
            newReferedByPanel.setVisible(false);
        }
        if(fromOtherFlow){
            this.dispose();
        }
    }

    private void showNewReferedBy() {
        messagePanel.setVisible(false);
        newReferedByNameTxt.setText(searchTxt.getText());
        newReferedByContactTxt.setText("");
        newReferedByAddressTxt.setText("");
        cityDao.getCities(citiesCB);
        newReferedByPanel.setVisible(true);
    }
        
    private void updateClicked() throws NumberFormatException {
        referedByTbl.getCellEditor().stopCellEditing();
        int totalRows = referedByTbl.getModel().getRowCount();
        for (int row = 0; row < totalRows; row++) {
            ReferedByBean refredBy = new ReferedByBean();
            refredBy.setId(Integer.parseInt(referedByTbl.getValueAt(row, 0).toString()));
            refredBy.setName(referedByTbl.getValueAt(row, 1).toString());
            refredBy.setCity(referedByTbl.getValueAt(row, 2)!=null?referedByTbl.getValueAt(row, 2).toString():"");
            refredBy.setContact(referedByTbl.getValueAt(row, 3)!=null?referedByTbl.getValueAt(row, 3).toString():"");
            refredBy.setAddress(referedByTbl.getValueAt(row, 4)!=null?referedByTbl.getValueAt(row, 4).toString():"");
            if (!referedByList.get(row).equals(refredBy)) {
                updateRefferedBy(refredBy);
            }
        }
    }
    
    private void updateRefferedBy(ReferedByBean refredBy) {       
        if (referedByDao.updateReferedBy(refredBy)) {
            messageLbl.setForeground(Color.blue);
            messageLbl.setText("Records updated Sucessfully!");
            messagePanel.setVisible(true);
            newReferedByPanel.setVisible(false);
            populateReferedByTable(searchTxt.getText());
        } else {
            messageLbl.setForeground(Color.red);
            messageLbl.setText("Error while updating ReferedBy");
            messagePanel.setVisible(true);
            newReferedByPanel.setVisible(false);
        }        
    }
    
    private void deleteReferedBy() throws NumberFormatException {
        int selectedRow = referedByTbl.getSelectedRow();
        if(selectedRow == -1) {
            messageLbl.setForeground(Color.red);
            messageLbl.setText("Please Select Patient to Delete !!");
            messagePanel.setVisible(true);
            newReferedByPanel.setVisible(false);
        }else{
            String selectedId = referedByTbl.getValueAt(selectedRow, 0).toString();
            ReferedByBean referedBy = new ReferedByBean();
            referedBy.setId(Integer.parseInt(selectedId));
            if (referedByDao.deleteReferedBy(referedBy)) {
                messageLbl.setForeground(Color.blue);
                messageLbl.setText("Record deleted successfully!");
                populateReferedByTable(searchTxt.getText());
            } else {
                messageLbl.setForeground(Color.red);
                messageLbl.setText("Error in deletion!");
            }
            messagePanel.setVisible(true);
            newReferedByPanel.setVisible(false);          
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

        referedByPanel = new javax.swing.JPanel();
        searchTxt = new javax.swing.JTextField();
        addBtn = new javax.swing.JButton();
        referedByTableScrollPane = new javax.swing.JScrollPane();
        referedByTbl = new javax.swing.JTable();
        newReferedByPanel = new javax.swing.JPanel();
        newReferedByNameTxt = new javax.swing.JTextField();
        newReferedByContactTxt = new javax.swing.JTextField();
        newReferedByAddressTxt = new javax.swing.JTextField();
        citiesCB = new javax.swing.JComboBox();
        saveNewReferedByBtn = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        messagePanel = new javax.swing.JPanel();
        messageLbl = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        updateBtn = new javax.swing.JButton();
        deleteBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Refered By");
        setAlwaysOnTop(true);
        setModalExclusionType(java.awt.Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
        setResizable(false);

        searchTxt.setNextFocusableComponent(addBtn);
        searchTxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchTxtKeyReleased(evt);
            }
        });

        addBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/EkDant/icones/Doctor Directory 3.png"))); // NOI18N
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

        referedByTbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Name", "City", "Contact", "Address"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, false, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        referedByTbl.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                referedByTblKeyTyped(evt);
            }
        });
        referedByTableScrollPane.setViewportView(referedByTbl);
        if (referedByTbl.getColumnModel().getColumnCount() > 0) {
            referedByTbl.getColumnModel().getColumn(0).setMinWidth(0);
            referedByTbl.getColumnModel().getColumn(0).setPreferredWidth(0);
            referedByTbl.getColumnModel().getColumn(0).setMaxWidth(0);
        }

        newReferedByNameTxt.setName(""); // NOI18N

        newReferedByContactTxt.setNextFocusableComponent(newReferedByAddressTxt);
        newReferedByContactTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newReferedByContactTxtActionPerformed(evt);
            }
        });

        newReferedByAddressTxt.setNextFocusableComponent(saveNewReferedByBtn);

        saveNewReferedByBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/EkDant/icones/Dentist Check.png"))); // NOI18N
        saveNewReferedByBtn.setText("Save");
        saveNewReferedByBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveNewReferedByBtnActionPerformed(evt);
            }
        });
        saveNewReferedByBtn.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                saveNewReferedByBtnKeyPressed(evt);
            }
        });

        jLabel4.setText("Adress");
        jLabel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        jLabel3.setText("Contact");
        jLabel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        jLabel2.setText("City");
        jLabel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        jLabel1.setText("Name");
        jLabel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        javax.swing.GroupLayout newReferedByPanelLayout = new javax.swing.GroupLayout(newReferedByPanel);
        newReferedByPanel.setLayout(newReferedByPanelLayout);
        newReferedByPanelLayout.setHorizontalGroup(
            newReferedByPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(newReferedByPanelLayout.createSequentialGroup()
                .addGroup(newReferedByPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE)
                    .addComponent(newReferedByNameTxt, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE))
                .addGroup(newReferedByPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(newReferedByPanelLayout.createSequentialGroup()
                        .addComponent(citiesCB, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(newReferedByContactTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(newReferedByAddressTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(saveNewReferedByBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(newReferedByPanelLayout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        newReferedByPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {citiesCB, jLabel2});

        newReferedByPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel3, newReferedByContactTxt});

        newReferedByPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel4, newReferedByAddressTxt});

        newReferedByPanelLayout.setVerticalGroup(
            newReferedByPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, newReferedByPanelLayout.createSequentialGroup()
                .addGap(0, 11, Short.MAX_VALUE)
                .addGroup(newReferedByPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(newReferedByPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(jLabel3)
                        .addComponent(jLabel4))
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0)
                .addGroup(newReferedByPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(newReferedByNameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(newReferedByContactTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(newReferedByAddressTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(citiesCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(saveNewReferedByBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        newReferedByPanelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {citiesCB, newReferedByNameTxt});

        newReferedByPanelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel1, jLabel2, jLabel3, jLabel4});

        messageLbl.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        javax.swing.GroupLayout messagePanelLayout = new javax.swing.GroupLayout(messagePanel);
        messagePanel.setLayout(messagePanelLayout);
        messagePanelLayout.setHorizontalGroup(
            messagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(messagePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(messageLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        messagePanelLayout.setVerticalGroup(
            messagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(messageLbl, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
        );

        updateBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/EkDant/icones/Dentist Edit.png"))); // NOI18N
        updateBtn.setText("Update");
        updateBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateBtnActionPerformed(evt);
            }
        });

        deleteBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/EkDant/icones/Dentist Delete.png"))); // NOI18N
        deleteBtn.setText("Delete");
        deleteBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(updateBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(deleteBtn)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {deleteBtn, updateBtn});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(deleteBtn)
                    .addComponent(updateBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {deleteBtn, updateBtn});

        javax.swing.GroupLayout referedByPanelLayout = new javax.swing.GroupLayout(referedByPanel);
        referedByPanel.setLayout(referedByPanelLayout);
        referedByPanelLayout.setHorizontalGroup(
            referedByPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(referedByPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(referedByPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(referedByPanelLayout.createSequentialGroup()
                        .addComponent(newReferedByPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(referedByPanelLayout.createSequentialGroup()
                        .addGroup(referedByPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(referedByPanelLayout.createSequentialGroup()
                                .addGap(106, 106, 106)
                                .addComponent(searchTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(72, 72, 72)
                                .addComponent(addBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(referedByTableScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 663, Short.MAX_VALUE)
                            .addComponent(messagePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        referedByPanelLayout.setVerticalGroup(
            referedByPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(referedByPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(referedByPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(newReferedByPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(messagePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(referedByTableScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(referedByPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(referedByPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void searchTxtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchTxtKeyReleased
        messagePanel.setVisible(false);
        populateReferedByTable(searchTxt.getText());
    }//GEN-LAST:event_searchTxtKeyReleased

    private void addBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addBtnActionPerformed
        showNewReferedBy();
    }//GEN-LAST:event_addBtnActionPerformed

    private void saveNewReferedByBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveNewReferedByBtnActionPerformed
        persistReferedBy();
    }//GEN-LAST:event_saveNewReferedByBtnActionPerformed

    private void saveNewReferedByBtnKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_saveNewReferedByBtnKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
             persistReferedBy();
        }
    }//GEN-LAST:event_saveNewReferedByBtnKeyPressed

    private void addBtnKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_addBtnKeyPressed
     if(evt.getKeyCode() == KeyEvent.VK_ENTER){
             showNewReferedBy();
        }
    }//GEN-LAST:event_addBtnKeyPressed

    private void newReferedByContactTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newReferedByContactTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_newReferedByContactTxtActionPerformed

    private void updateBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateBtnActionPerformed
        updateClicked();
    }//GEN-LAST:event_updateBtnActionPerformed

    private void deleteBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteBtnActionPerformed
        deleteReferedBy();
    }//GEN-LAST:event_deleteBtnActionPerformed

    private void referedByTblKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_referedByTblKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_referedByTblKeyTyped

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addBtn;
    private javax.swing.JComboBox citiesCB;
    private javax.swing.JButton deleteBtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel messageLbl;
    private javax.swing.JPanel messagePanel;
    private javax.swing.JTextField newReferedByAddressTxt;
    private javax.swing.JTextField newReferedByContactTxt;
    private javax.swing.JTextField newReferedByNameTxt;
    private javax.swing.JPanel newReferedByPanel;
    private javax.swing.JPanel referedByPanel;
    private javax.swing.JScrollPane referedByTableScrollPane;
    private javax.swing.JTable referedByTbl;
    private javax.swing.JButton saveNewReferedByBtn;
    private javax.swing.JTextField searchTxt;
    private javax.swing.JButton updateBtn;
    // End of variables declaration//GEN-END:variables
}
