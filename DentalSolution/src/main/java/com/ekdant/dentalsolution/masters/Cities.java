/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ekdant.dentalsolution.masters;

import com.ekdant.dentalsolution.dao.CityDao;
import com.ekdant.dentalsolution.domain.CityBean;
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
public class Cities extends javax.swing.JDialog {

    HashMap<Integer,String> cityMap;
    CityDao cityDao;
    boolean fromOtherFlow = false;
    /**
     * Creates new form Cities
     */
    
    /**
     * Creates new form Cities
     * @param fromOtherFlow
     */
    public Cities(boolean fromOtherFlow){
        cityDao = new CityDao();
        cityMap = new HashMap<Integer, String>();
        this.setModal(true);
        initComponents();
        populateCityTable("");
        this.fromOtherFlow = fromOtherFlow;
    }
    
    private void saveCity() throws HeadlessException {
        String newCityName = searchTxt.getText();
        if(newCityName.isEmpty() && newCityName.length()>0){
            if(cityDao.cityNotPresent(newCityName)){
                CityBean city = new CityBean();
                city.setName(newCityName);
                cityDao.addCity(city);
                if (fromOtherFlow) {
                    this.dispose();
                } else {
                    messageLbl.setForeground(Color.blue);
                    messageLbl.setText("City added successfully!");
                    messagePanel.setVisible(true);
                }
            } else{
                messageLbl.setForeground(Color.red);
                messageLbl.setText("City already present!");
                messagePanel.setVisible(true);
            }
        }else{
            messageLbl.setForeground(Color.red);
            messageLbl.setText("City Should Not Be Empty!");
            messagePanel.setVisible(true);
        }
        searchTxt.setText("");
        populateCityTable("");
    }
    
    private void populateCityTable(String searchData) {
        DefaultTableModel model = (DefaultTableModel)cityTbl.getModel();
        model.setNumRows(0);
        List<CityBean> cities = cityDao.fetchCities(searchData);
        for (CityBean city : cities) {
            model.addRow(new Object[]{
                city.getId(),
                city.getName(),});
            cityMap.put(city.getId(), city.getName());
        }       
    }
   
    private void updateCities() {
        boolean updateFlag = false;
        cityTbl.getCellEditor().stopCellEditing();
        int totalRows= cityTbl.getModel().getRowCount();
        for(int row=0;row<totalRows;row++){
            if(!(cityMap.get(Integer.parseInt(cityTbl.getValueAt(row, 0).toString()))).equalsIgnoreCase(cityTbl.getValueAt(row, 1).toString()))
            {
                if(cityDao.cityNotPresent(cityTbl.getValueAt(row, 1).toString())) {
                    CityBean city = new CityBean();
                    city.setId(Integer.parseInt(cityTbl.getValueAt(row, 0).toString()));
                    city.setName(cityTbl.getValueAt(row, 1).toString());
                    if (!cityDao.updateCity(city)) {
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
            messageLbl.setText("Cities updated successfully!");
            messagePanel.setVisible(true);
        }else{
             messageLbl.setForeground(Color.blue);
            messageLbl.setText("Nothing to Update!");
            messagePanel.setVisible(true);
        }
        searchTxt.setText("");
        populateCityTable("");
    }
    
    private void deleteCity() {
        int selectedRow= cityTbl.getSelectedRow();
            if(selectedRow>=0){
                CityBean city = new CityBean();
                city.setId(Integer.parseInt(cityTbl.getValueAt(selectedRow, 0).toString()));

                if (!cityDao.deleteCity(city)) {
                    messageLbl.setForeground(Color.red);
                    messageLbl.setText("Error Deleting City!!");
                    messagePanel.setVisible(true);
                } else {
                    messageLbl.setForeground(Color.blue);
                    messageLbl.setText("City Deleted Successfully !");
                    messagePanel.setVisible(true);
                }
        }else{
            messageLbl.setForeground(Color.red);
            messageLbl.setText("No City Selected to Delete!");
            messagePanel.setVisible(true);
        }
        searchTxt.setText("");
        populateCityTable("");
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
        cityTbl = new javax.swing.JTable();
        updateCityBtn = new javax.swing.JButton();
        deleteCityBtn = new javax.swing.JButton();
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

        cityTbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "City Id", "City Name"
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
        jScrollPane1.setViewportView(cityTbl);
        if (cityTbl.getColumnModel().getColumnCount() > 0) {
            cityTbl.getColumnModel().getColumn(0).setMinWidth(0);
            cityTbl.getColumnModel().getColumn(0).setPreferredWidth(0);
            cityTbl.getColumnModel().getColumn(0).setMaxWidth(0);
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
        populateCityTable(searchTxt.getText());
    }//GEN-LAST:event_searchTxtKeyReleased

    private void addBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addBtnActionPerformed
        saveCity();
    }//GEN-LAST:event_addBtnActionPerformed

    private void addBtnKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_addBtnKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
             saveCity();
        }
    }//GEN-LAST:event_addBtnKeyPressed

    private void updateCityBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateCityBtnActionPerformed
        updateCities();
    }//GEN-LAST:event_updateCityBtnActionPerformed
    
    private void searchTxtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchTxtKeyPressed
       messagePanel.setVisible(false);
    }//GEN-LAST:event_searchTxtKeyPressed

    private void deleteCityBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteCityBtnActionPerformed
        deleteCity();
    }//GEN-LAST:event_deleteCityBtnActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addBtn;
    private javax.swing.JPanel cityPanel;
    private javax.swing.JTable cityTbl;
    private javax.swing.JButton deleteCityBtn;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel messageLbl;
    private javax.swing.JPanel messagePanel;
    private javax.swing.JTextField searchTxt;
    private javax.swing.JButton updateCityBtn;
    // End of variables declaration//GEN-END:variables
}
