/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ekdant.dentalsolution.masters;

import com.ekdant.dentalsolution.dao.ExpenseDao;
import com.ekdant.dentalsolution.domain.ExpenseCategoryBean;
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
public class ExpenseCategories extends javax.swing.JDialog {

    ExpenseDao expenseDao;
    HashMap<Integer,String> expenseCategoriesMap;
    /**
     * Creates new form ExpenseCategories
     */
    public ExpenseCategories() {
        expenseDao = new ExpenseDao();
        this.setModal(true);
        initComponents();
        messagePanel.setVisible(false);
        populateExpenseCategoriesTable("");
        initExpenseCategoriesTable();
    }

    
    private void saveExpenseCategory() throws HeadlessException {
        String newExpenseCategory = searchTxt.getText();
        if(newExpenseCategory.isEmpty() && newExpenseCategory.length()>0){
            if(expenseDao.expenseCategoryNotPresent(newExpenseCategory)){
                ExpenseCategoryBean expenseCategory = new ExpenseCategoryBean();
                expenseCategory.setName(newExpenseCategory);
                if(expenseDao.addExpenseCategory(expenseCategory)){
                    messageLbl.setForeground(Color.blue);
                    messageLbl.setText("Expense catagory added successfully!");
                    messagePanel.setVisible(true); 
                }
            } else{
                messageLbl.setForeground(Color.red);
                messageLbl.setText("Expense catagory already present!");
                messagePanel.setVisible(true); 
            }
        }else{
            messageLbl.setForeground(Color.red);
            messageLbl.setText("Expense Catagory Should Not Be Empty!");
            messagePanel.setVisible(true); 
        }
        searchTxt.setText("");
        populateExpenseCategoriesTable("");
    }
    
    
    private void populateExpenseCategoriesTable(String searchData) {
        DefaultTableModel model = (DefaultTableModel) expenseCategoryTbl.getModel();
        expenseCategoriesMap = new HashMap<Integer, String>();
        model.setNumRows(0);
        List<ExpenseCategoryBean> expenseCategories = expenseDao.fetchExpenseCategories(searchData);
        for (ExpenseCategoryBean expenseCategory : expenseCategories) {
            model.addRow(new Object[]{
                expenseCategory.getId(),
                expenseCategory.getName()
            });
            expenseCategoriesMap.put(expenseCategory.getId(), expenseCategory.getName());
        }
    }

    private void initExpenseCategoriesTable() {
        ListSelectionModel selectionModel = expenseCategoryTbl.getSelectionModel();        
        //selectionModel.
              
    }
    
    
    
     private void updateExpenseCategories() {
        boolean updateFlag = false;
        expenseCategoryTbl.getCellEditor().stopCellEditing();
        HashMap<Integer,String> tableCities = new HashMap<Integer, String>();
        int totalRows= expenseCategoryTbl.getModel().getRowCount();
        for(int row=0;row<totalRows;row++){
            if(!(expenseCategoriesMap.get(Integer.parseInt(expenseCategoryTbl.getValueAt(row, 0).toString()))).equalsIgnoreCase(expenseCategoryTbl.getValueAt(row, 1).toString()))
            {
                if(expenseDao.expenseCategoryNotPresent(expenseCategoryTbl.getValueAt(row, 1).toString())) {
                    ExpenseCategoryBean expenseCategory = new ExpenseCategoryBean();
                    expenseCategory.setId(Integer.parseInt(expenseCategoryTbl.getValueAt(row, 0).toString()));
                    expenseCategory.setName(expenseCategoryTbl.getValueAt(row, 1).toString());

                    if (expenseDao.updateExpenseCategory(expenseCategory)) {
                        updateFlag = true;
                    } else {
                        messageLbl.setForeground(Color.red);
                        messageLbl.setText("Error updating Expense Category!!");
                        messagePanel.setVisible(true);
                    }
                }
            }
        }
        if(updateFlag){
            messageLbl.setForeground(Color.blue);
            messageLbl.setText("Expense Categories updated successfully!");
            messagePanel.setVisible(true);
        }else{
             messageLbl.setForeground(Color.blue);
            messageLbl.setText("Nothing to Update!");
            messagePanel.setVisible(true);
        }
        searchTxt.setText("");
        populateExpenseCategoriesTable("");
    }
     
     private void deleteExpenseCategory() {
        int selectedRow= expenseCategoryTbl.getSelectedRow();
            if(selectedRow>=0){
                ExpenseCategoryBean expenseCategory = new ExpenseCategoryBean();
                expenseCategory.setId(Integer.parseInt(expenseCategoryTbl.getValueAt(selectedRow, 0).toString()));
                if (expenseDao.deleteExpenseCategory(expenseCategory)) {
                    messageLbl.setForeground(Color.blue);
                    messageLbl.setText("Expense Category Deleted Successfully !");
                    messagePanel.setVisible(true);
                } else {
                    messageLbl.setForeground(Color.red);
                    messageLbl.setText("Error Deleting Expense Category!!");
                    messagePanel.setVisible(true);
                }
        }else{
            messageLbl.setForeground(Color.red);
            messageLbl.setText("No Category Selected to Delete!");
            messagePanel.setVisible(true);
        }
        searchTxt.setText("");
        populateExpenseCategoriesTable("");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        expenseCategoryPanel = new javax.swing.JPanel();
        searchTxt = new javax.swing.JTextField();
        addBtn = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        expenseCategoryTbl = new javax.swing.JTable();
        messagePanel = new javax.swing.JPanel();
        messageLbl = new javax.swing.JLabel();
        updateBtn = new javax.swing.JButton();
        deleteBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Expense Categories");

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

        expenseCategoryTbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Expense Category"
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
        jScrollPane1.setViewportView(expenseCategoryTbl);
        if (expenseCategoryTbl.getColumnModel().getColumnCount() > 0) {
            expenseCategoryTbl.getColumnModel().getColumn(0).setMinWidth(0);
            expenseCategoryTbl.getColumnModel().getColumn(0).setPreferredWidth(0);
            expenseCategoryTbl.getColumnModel().getColumn(0).setMaxWidth(0);
        }

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

        javax.swing.GroupLayout expenseCategoryPanelLayout = new javax.swing.GroupLayout(expenseCategoryPanel);
        expenseCategoryPanel.setLayout(expenseCategoryPanelLayout);
        expenseCategoryPanelLayout.setHorizontalGroup(
            expenseCategoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(expenseCategoryPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(expenseCategoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(expenseCategoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(expenseCategoryPanelLayout.createSequentialGroup()
                            .addComponent(searchTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(72, 72, 72)
                            .addComponent(addBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jScrollPane1)
                        .addComponent(messagePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(expenseCategoryPanelLayout.createSequentialGroup()
                        .addComponent(updateBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(deleteBtn)))
                .addContainerGap(139, Short.MAX_VALUE))
        );

        expenseCategoryPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {addBtn, deleteBtn, updateBtn});

        expenseCategoryPanelLayout.setVerticalGroup(
            expenseCategoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(expenseCategoryPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(expenseCategoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(messagePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addGroup(expenseCategoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(updateBtn)
                    .addComponent(deleteBtn))
                .addContainerGap())
        );

        expenseCategoryPanelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {addBtn, deleteBtn, updateBtn});

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(expenseCategoryPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(expenseCategoryPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void searchTxtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchTxtKeyReleased
        populateExpenseCategoriesTable(searchTxt.getText());
    }//GEN-LAST:event_searchTxtKeyReleased

    private void addBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addBtnActionPerformed
        saveExpenseCategory();
    }//GEN-LAST:event_addBtnActionPerformed

    private void addBtnKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_addBtnKeyPressed
         if(evt.getKeyCode() == KeyEvent.VK_ENTER){
             saveExpenseCategory();
        }
    }//GEN-LAST:event_addBtnKeyPressed

    private void updateBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateBtnActionPerformed
        updateExpenseCategories();
    }//GEN-LAST:event_updateBtnActionPerformed

    private void searchTxtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchTxtKeyPressed
        messagePanel.setVisible(false);
    }//GEN-LAST:event_searchTxtKeyPressed

    private void deleteBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteBtnActionPerformed
        deleteExpenseCategory();
    }//GEN-LAST:event_deleteBtnActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addBtn;
    private javax.swing.JButton deleteBtn;
    private javax.swing.JPanel expenseCategoryPanel;
    private javax.swing.JTable expenseCategoryTbl;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel messageLbl;
    private javax.swing.JPanel messagePanel;
    private javax.swing.JTextField searchTxt;
    private javax.swing.JButton updateBtn;
    // End of variables declaration//GEN-END:variables
}
