/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ekdant.dentalsolution.masters;

import com.ekdant.dentalsolution.dao.DocumentsDao;
import com.ekdant.dentalsolution.domain.DocumentTypeBean;
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
public class DocumentType extends javax.swing.JDialog {

    HashMap<Integer,String> documentTypeMap;
    DocumentsDao documentsDao;
    boolean fromOtherFlow = false;
    /**
     * Creates new form Cities
     */
    
    /**
     * Creates new form Cities
     * @param fromOtherFlow
     */
    public DocumentType(boolean fromOtherFlow){
        documentsDao = new DocumentsDao();
        documentTypeMap = new HashMap<Integer, String>();
        this.setModal(true);
        initComponents();
        populateDocumentTypeTable("");
        this.fromOtherFlow = fromOtherFlow;
    }
    
    private void saveDocumentType() throws HeadlessException {
        String newDocumentType = searchTxt.getText();
        if(newDocumentType.isEmpty() && newDocumentType.length()>0){
            if(documentsDao.documentTypeNotPresent(newDocumentType)){
                DocumentTypeBean documentType = new DocumentTypeBean();
                documentType.setName(newDocumentType);
                documentsDao.addDocumentType(documentType);
                if (fromOtherFlow) {
                    this.dispose();
                } else {
                    messageLbl.setForeground(Color.blue);
                    messageLbl.setText("Document Type added successfully!");
                    messagePanel.setVisible(true);
                }
            } else{
                messageLbl.setForeground(Color.red);
                messageLbl.setText("Document Type already present!");
                messagePanel.setVisible(true);
            }
        }else{
            messageLbl.setForeground(Color.red);
            messageLbl.setText("Document Type Should Not Be Empty!");
            messagePanel.setVisible(true);
        }
        searchTxt.setText("");
        populateDocumentTypeTable("");
    }
    
    private void populateDocumentTypeTable(String searchData) {
        DefaultTableModel model = (DefaultTableModel)documentTypeTbl.getModel();
        model.setNumRows(0);
        List<DocumentTypeBean> documentTypes = documentsDao.fetchDocumentTypes(searchData);
        for (DocumentTypeBean documentType : documentTypes) {
            model.addRow(new Object[]{
                documentType.getId(),
                documentType.getName(),});
            documentTypeMap.put(documentType.getId(), documentType.getName());
        }       
    }
   
    private void updateDocumentType() {
        boolean updateFlag = false;
        documentTypeTbl.getCellEditor().stopCellEditing();
        int totalRows= documentTypeTbl.getModel().getRowCount();
        for(int row=0;row<totalRows;row++){
            if(!(documentTypeMap.get(Integer.parseInt(documentTypeTbl.getValueAt(row, 0).toString()))).equalsIgnoreCase(documentTypeTbl.getValueAt(row, 1).toString()))
            {
                if(documentsDao.documentTypeNotPresent(documentTypeTbl.getValueAt(row, 1).toString())) {
                    DocumentTypeBean documentType = new DocumentTypeBean();
                    documentType.setId(Integer.parseInt(documentTypeTbl.getValueAt(row, 0).toString()));
                    documentType.setName(documentTypeTbl.getValueAt(row, 1).toString());
                    if (!documentsDao.updateDocumentType(documentType)) {
                        messageLbl.setForeground(Color.red);
                        messageLbl.setText("Error updating Cities!!");
                        messagePanel.setVisible(true);
                    }else{
                        updateFlag = true;
                    }
                }
            }
        }
        if(updateFlag){
            messageLbl.setForeground(Color.blue);
            messageLbl.setText("Document Type updated successfully!");
            messagePanel.setVisible(true);
        }else{
             messageLbl.setForeground(Color.blue);
            messageLbl.setText("Nothing to Update!");
            messagePanel.setVisible(true);
        }
        searchTxt.setText("");
        populateDocumentTypeTable("");
    }
    
    private void deleteDocumentType() {
        int selectedRow= documentTypeTbl.getSelectedRow();
            if(selectedRow>=0){
                DocumentTypeBean documentType = new DocumentTypeBean();
                documentType.setId(Integer.parseInt(documentTypeTbl.getValueAt(selectedRow, 0).toString()));

                if (!documentsDao.deleteDocumentType(documentType)) {
                    messageLbl.setForeground(Color.red);
                    messageLbl.setText("Error Deleting Document Type!!");
                    messagePanel.setVisible(true);
                } else {
                    messageLbl.setForeground(Color.blue);
                    messageLbl.setText("Document Type Deleted Successfully !");
                    messagePanel.setVisible(true);
                }
        }else{
            messageLbl.setForeground(Color.red);
            messageLbl.setText("No City Selected to Delete!");
            messagePanel.setVisible(true);
        }
        searchTxt.setText("");
        populateDocumentTypeTable("");
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
        documentTypeTbl = new javax.swing.JTable();
        updateCityBtn = new javax.swing.JButton();
        deleteCityBtn = new javax.swing.JButton();
        messagePanel = new javax.swing.JPanel();
        messageLbl = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Document Type");

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

        documentTypeTbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Document Type"
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
        jScrollPane1.setViewportView(documentTypeTbl);
        if (documentTypeTbl.getColumnModel().getColumnCount() > 0) {
            documentTypeTbl.getColumnModel().getColumn(0).setMinWidth(0);
            documentTypeTbl.getColumnModel().getColumn(0).setPreferredWidth(0);
            documentTypeTbl.getColumnModel().getColumn(0).setMaxWidth(0);
        }

        updateCityBtn.setText("Update");
        updateCityBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateCityBtnActionPerformed(evt);
            }
        });

        deleteCityBtn.setText("Delete");
        deleteCityBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteCityBtnActionPerformed(evt);
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
                        .addComponent(updateCityBtn)
                        .addGap(18, 18, 18)
                        .addComponent(deleteCityBtn))
                    .addComponent(messagePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(139, Short.MAX_VALUE))
        );

        cityPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {addBtn, deleteCityBtn, updateCityBtn});

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
                    .addComponent(updateCityBtn)
                    .addComponent(deleteCityBtn))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        cityPanelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {addBtn, deleteCityBtn, updateCityBtn});

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
        populateDocumentTypeTable(searchTxt.getText());
    }//GEN-LAST:event_searchTxtKeyReleased

    private void addBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addBtnActionPerformed
        saveDocumentType();
    }//GEN-LAST:event_addBtnActionPerformed

    private void addBtnKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_addBtnKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
             saveDocumentType();
        }
    }//GEN-LAST:event_addBtnKeyPressed

    private void updateCityBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateCityBtnActionPerformed
        updateDocumentType();
    }//GEN-LAST:event_updateCityBtnActionPerformed
    
    private void searchTxtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchTxtKeyPressed
       messagePanel.setVisible(false);
    }//GEN-LAST:event_searchTxtKeyPressed

    private void deleteCityBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteCityBtnActionPerformed
        deleteDocumentType();
    }//GEN-LAST:event_deleteCityBtnActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addBtn;
    private javax.swing.JPanel cityPanel;
    private javax.swing.JButton deleteCityBtn;
    private javax.swing.JTable documentTypeTbl;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel messageLbl;
    private javax.swing.JPanel messagePanel;
    private javax.swing.JTextField searchTxt;
    private javax.swing.JButton updateCityBtn;
    // End of variables declaration//GEN-END:variables
}
