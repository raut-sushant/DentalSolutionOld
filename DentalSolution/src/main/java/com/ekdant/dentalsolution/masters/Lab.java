/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ekdant.dentalsolution.masters;

import com.ekdant.dentalsolution.domain.LabBean;
import com.ekdant.dentalsolution.dao.CityDao;
import com.ekdant.dentalsolution.dao.LabDao;
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
public class Lab extends javax.swing.JDialog {

    CityDao cityDao;
    LabDao labDao;
    List<LabBean> labList = null;

    /**
     * Creates new form Lab
     */
    public Lab() {
        cityDao = new CityDao();
        labDao = new LabDao();
        this.setModal(true);
        initComponents();
        populateLabTable("");
        cityDao.getCities(citiesCB);
        messagePanel.setVisible(false);
        newLabPanel.setVisible(false);
    }

    private void populateLabTable(String searchData) {
        labList = new ArrayList<LabBean>();
        DefaultTableModel model = (DefaultTableModel) labTbl.getModel();
        model.setNumRows(0);
        List<LabBean> labs = labDao.fetchLabs(searchData);
        for (LabBean lab : labs) {
            model.addRow(new Object[]{
                lab.getId(),
                lab.getName(),
                lab.getCity(),
                lab.getContact(),
                lab.getAddress()
            });
            labList.add(lab);
        }
    }

    private void persistLab() throws HeadlessException {
        String newLabCity = citiesCB.getSelectedItem().toString().equalsIgnoreCase("select") ? "" : citiesCB.getSelectedItem().toString();

        LabBean lab = new LabBean();
        lab.setName(newLabNameTxt.getText());
        lab.setCity(newLabCity);
        lab.setContact(newLabContactTxt.getText());
        lab.setAddress(newLabAddressTxt.getText());
        if (lab.getName() != null && lab.getName().length() > 0) {
            if (labDao.labNotPresent(lab.getName())) {
                if (labDao.addLab(lab)) {
                    messageLbl.setForeground(Color.blue);
                    messageLbl.setText("Lab added successfully!");
                    newLabPanel.setVisible(false);
                    messagePanel.setVisible(true);
                    newLabPanel.setVisible(false);
                    searchTxt.setText("");
                    populateLabTable("");
                } else {
                    messageLbl.setForeground(Color.red);
                    messageLbl.setText("Error occored while adding lab!");
                    messagePanel.setVisible(true);
                    newLabPanel.setVisible(false);
                }
            } else {
                messageLbl.setForeground(Color.red);
                messageLbl.setText("Lab already present!");
                messagePanel.setVisible(true);
                newLabPanel.setVisible(false);
            }
        } else {
            messageLbl.setForeground(Color.red);
            messageLbl.setText("Lab Name Should Not Be Empty!");
            messagePanel.setVisible(true);
            newLabPanel.setVisible(false);
        }
    }

    private void showNewReferedBy() {
        messagePanel.setVisible(false);
        newLabNameTxt.setText(searchTxt.getText());
        newLabContactTxt.setText("");
        newLabAddressTxt.setText("");
        cityDao.getCities(citiesCB);
        newLabPanel.setVisible(true);
    }

    private void updateClicked() throws NumberFormatException {
        labTbl.getCellEditor().stopCellEditing();
        int totalRows = labTbl.getModel().getRowCount();
        for (int row = 0; row < totalRows; row++) {
            LabBean lab = new LabBean();
            lab.setId(Integer.parseInt(labTbl.getValueAt(row, 0).toString()));
            lab.setName(labTbl.getValueAt(row, 1).toString());
            lab.setCity(labTbl.getValueAt(row, 2) != null ? labTbl.getValueAt(row, 2).toString() : "");
            lab.setContact(labTbl.getValueAt(row, 3) != null ? labTbl.getValueAt(row, 3).toString() : "");
            lab.setAddress(labTbl.getValueAt(row, 4) != null ? labTbl.getValueAt(row, 4).toString() : "");
            if (!labList.get(row).equals(lab)) {
                updateLab(lab);
            }
        }
    }

    private void updateLab(LabBean lab) {
        if (labDao.updateLab(lab)) {
            messageLbl.setForeground(Color.blue);
            messageLbl.setText("Records updated Sucessfully!");
            messagePanel.setVisible(true);
            newLabPanel.setVisible(false);
            populateLabTable(searchTxt.getText());
        } else {
            messageLbl.setForeground(Color.red);
            messageLbl.setText("Error while updating lab");
            messagePanel.setVisible(true);
            newLabPanel.setVisible(false);
        }        
    }

    private void deleteLab() throws NumberFormatException {
        int selectedRow = labTbl.getSelectedRow();
        if (selectedRow == -1) {
            messageLbl.setForeground(Color.red);
            messageLbl.setText("Please Select Lab to Delete !!");
            messagePanel.setVisible(true);
            newLabPanel.setVisible(false);
        } else {
            String selectedId = labTbl.getValueAt(selectedRow, 0).toString();
            LabBean lab = new LabBean();
            lab.setId(Integer.parseInt(selectedId));
            if (labDao.deleteLab(lab)) {
                messageLbl.setForeground(Color.blue);
                messageLbl.setText("Record deleted successfully!");
                populateLabTable(searchTxt.getText());
            } else {
                messageLbl.setForeground(Color.red);
                messageLbl.setText("Error in deletion!");
            }
            messagePanel.setVisible(true);
            newLabPanel.setVisible(false);

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
        labTbl = new javax.swing.JTable();
        newLabPanel = new javax.swing.JPanel();
        newLabNameTxt = new javax.swing.JTextField();
        newLabContactTxt = new javax.swing.JTextField();
        newLabAddressTxt = new javax.swing.JTextField();
        citiesCB = new javax.swing.JComboBox();
        saveNewLab = new javax.swing.JButton();
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
        setTitle("Lab");
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

        labTbl.setModel(new javax.swing.table.DefaultTableModel(
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
        labTbl.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                labTblKeyTyped(evt);
            }
        });
        referedByTableScrollPane.setViewportView(labTbl);
        if (labTbl.getColumnModel().getColumnCount() > 0) {
            labTbl.getColumnModel().getColumn(0).setMinWidth(0);
            labTbl.getColumnModel().getColumn(0).setPreferredWidth(0);
            labTbl.getColumnModel().getColumn(0).setMaxWidth(0);
        }

        newLabNameTxt.setName(""); // NOI18N

        newLabContactTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newLabContactTxtActionPerformed(evt);
            }
        });

        newLabAddressTxt.setNextFocusableComponent(saveNewLab);

        saveNewLab.setIcon(new javax.swing.ImageIcon(getClass().getResource("/EkDant/icones/Dentist Check.png"))); // NOI18N
        saveNewLab.setText("Save");
        saveNewLab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveNewLabActionPerformed(evt);
            }
        });
        saveNewLab.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                saveNewLabKeyPressed(evt);
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

        javax.swing.GroupLayout newLabPanelLayout = new javax.swing.GroupLayout(newLabPanel);
        newLabPanel.setLayout(newLabPanelLayout);
        newLabPanelLayout.setHorizontalGroup(
            newLabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(newLabPanelLayout.createSequentialGroup()
                .addGroup(newLabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE)
                    .addComponent(newLabNameTxt, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE))
                .addGroup(newLabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(newLabPanelLayout.createSequentialGroup()
                        .addComponent(citiesCB, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(newLabContactTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(newLabAddressTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(saveNewLab, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(newLabPanelLayout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        newLabPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {citiesCB, jLabel2});

        newLabPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel3, newLabContactTxt});

        newLabPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel4, newLabAddressTxt});

        newLabPanelLayout.setVerticalGroup(
            newLabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, newLabPanelLayout.createSequentialGroup()
                .addGap(0, 11, Short.MAX_VALUE)
                .addGroup(newLabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(newLabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(jLabel3)
                        .addComponent(jLabel4))
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0)
                .addGroup(newLabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(newLabNameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(newLabContactTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(newLabAddressTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(citiesCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(saveNewLab, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        newLabPanelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {citiesCB, newLabNameTxt});

        newLabPanelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel1, jLabel2, jLabel3, jLabel4});

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
                        .addComponent(newLabPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                .addComponent(newLabPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
        populateLabTable(searchTxt.getText());
    }//GEN-LAST:event_searchTxtKeyReleased

    private void addBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addBtnActionPerformed
        showNewReferedBy();
    }//GEN-LAST:event_addBtnActionPerformed

    private void saveNewLabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveNewLabActionPerformed
        persistLab();
    }//GEN-LAST:event_saveNewLabActionPerformed

    private void saveNewLabKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_saveNewLabKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            persistLab();
        }
    }//GEN-LAST:event_saveNewLabKeyPressed

    private void addBtnKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_addBtnKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            showNewReferedBy();
        }
    }//GEN-LAST:event_addBtnKeyPressed

    private void newLabContactTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newLabContactTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_newLabContactTxtActionPerformed

    private void updateBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateBtnActionPerformed
        updateClicked();
    }//GEN-LAST:event_updateBtnActionPerformed

    private void deleteBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteBtnActionPerformed
        deleteLab();
    }//GEN-LAST:event_deleteBtnActionPerformed

    private void labTblKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_labTblKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_labTblKeyTyped

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addBtn;
    private javax.swing.JComboBox citiesCB;
    private javax.swing.JButton deleteBtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTable labTbl;
    private javax.swing.JLabel messageLbl;
    private javax.swing.JPanel messagePanel;
    private javax.swing.JTextField newLabAddressTxt;
    private javax.swing.JTextField newLabContactTxt;
    private javax.swing.JTextField newLabNameTxt;
    private javax.swing.JPanel newLabPanel;
    private javax.swing.JPanel referedByPanel;
    private javax.swing.JScrollPane referedByTableScrollPane;
    private javax.swing.JButton saveNewLab;
    private javax.swing.JTextField searchTxt;
    private javax.swing.JButton updateBtn;
    // End of variables declaration//GEN-END:variables
}
