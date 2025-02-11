/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ekdant.dentalsolution.expense;

import com.ekdant.dentalsolution.dao.ExpenseDao;
import com.ekdant.dentalsolution.domain.ExpenseBean;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Sushant
 */
public class ExpenseTracking extends javax.swing.JFrame {
    DateFormat displayDateFormat = new SimpleDateFormat("d MMM yyyy");
    DateFormat databaseDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    DateFormat inputDateFormat = new SimpleDateFormat("MMM d, yyyy");
    ExpenseDao expenseDao;
    
    
    /**
     * Creates new form ExpenseTracking
     */
    public ExpenseTracking() {    

        expenseDao = new ExpenseDao();
        initComponents();  
        loadExpenseCategories();
        loadExpenseHistory(null);
    }
    
    private void loadExpenseCategories(){
        List<String> expenseCategories = expenseDao.fetchExpenseCategories();
        for (String expenseCategory : expenseCategories) {
            categoryCB.addItem(expenseCategory);
        } 
    }
    
    private void persistExpense(String category, String amount, String notes, String date) {
        try {
            ExpenseBean expense = new ExpenseBean();
            expense.setId(0);
            expense.setExpenseCategory(category);
            expense.setAmount(Integer.parseInt(amount));
            expense.setDate(databaseDateFormat.parse(date));
            expense.setNotes(notes);
            if(expenseDao.addExpense(expense)){
                JOptionPane.showMessageDialog(null, "Expense added succesfully", "Info!", JOptionPane.INFORMATION_MESSAGE);
            }else{        
                JOptionPane.showMessageDialog(null, "Error while adding expense", "Error!", JOptionPane.ERROR_MESSAGE);
            }
            loadExpenseHistory(null);
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(null, "Error while adding expense", "Error!", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void loadExpenseHistory(String searchText) {
        List<ExpenseBean> expenses = expenseDao.fetchExpenses(searchText);
        DefaultTableModel model = (DefaultTableModel)expenseHistoryTbl.getModel();
        model.setNumRows(0);

        for (ExpenseBean expense : expenses) {
            model.addRow(new Object[]{
                expense.getId(),
                displayDateFormat.format(expense.getDate()),
                expense.getExpenseCategory(),
                expense.getNotes(),
                expense.getAmount()
            });
        }
    }
    
    private boolean validateFields() {
        boolean validate = true;
        if(amountTxt.getText().isEmpty()){
            JOptionPane.showMessageDialog(null,"Please enter amouont","Warning", JOptionPane.WARNING_MESSAGE);
        }
        return validate;
    }
    
     
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonPanel = new javax.swing.JPanel();
        saveBtn = new javax.swing.JButton();
        cancelBtn = new javax.swing.JButton();
        mainPanel = new javax.swing.JPanel();
        newExpensePanel = new javax.swing.JPanel();
        addExpenseLbl = new javax.swing.JLabel();
        categoryLbl = new javax.swing.JLabel();
        amountLbl = new javax.swing.JLabel();
        notesLbl = new javax.swing.JLabel();
        dateLbl = new javax.swing.JLabel();
        categoryCB = new javax.swing.JComboBox();
        amountTxt = new javax.swing.JTextField();
        notesTxt = new javax.swing.JTextField();
        dateDP = new com.toedter.calendar.JDateChooser();
        expenseHistoryPanel = new javax.swing.JPanel();
        expenseHistoryLbl = new javax.swing.JLabel();
        searchTxt = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        expenseHistoryTbl = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Expense Tracking");
        setName("Treatment"); // NOI18N

        saveBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/EkDant/icones/Treatment Completed.png"))); // NOI18N
        saveBtn.setMnemonic('S');
        saveBtn.setText("Save");
        saveBtn.setNextFocusableComponent(cancelBtn);
        saveBtn.setPreferredSize(new java.awt.Dimension(91, 33));
        saveBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveBtnActionPerformed(evt);
            }
        });

        cancelBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/EkDant/icones/Treatment Delete.png"))); // NOI18N
        cancelBtn.setMnemonic('C');
        cancelBtn.setText("Cancel");
        cancelBtn.setPreferredSize(new java.awt.Dimension(91, 33));
        cancelBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout buttonPanelLayout = new javax.swing.GroupLayout(buttonPanel);
        buttonPanel.setLayout(buttonPanelLayout);
        buttonPanelLayout.setHorizontalGroup(
            buttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buttonPanelLayout.createSequentialGroup()
                .addGap(247, 247, 247)
                .addComponent(saveBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(cancelBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        buttonPanelLayout.setVerticalGroup(
            buttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, buttonPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(buttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(saveBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cancelBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        addExpenseLbl.setBackground(new java.awt.Color(255, 255, 255));
        addExpenseLbl.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        addExpenseLbl.setForeground(new java.awt.Color(51, 51, 255));
        addExpenseLbl.setText("Add your Expense");

        categoryLbl.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        categoryLbl.setForeground(new java.awt.Color(51, 51, 255));
        categoryLbl.setText("Category:");

        amountLbl.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        amountLbl.setForeground(new java.awt.Color(51, 51, 255));
        amountLbl.setText("Amount");

        notesLbl.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        notesLbl.setForeground(new java.awt.Color(51, 51, 255));
        notesLbl.setText("Notes");

        dateLbl.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        dateLbl.setForeground(new java.awt.Color(51, 51, 255));
        dateLbl.setText("Date");

        javax.swing.GroupLayout newExpensePanelLayout = new javax.swing.GroupLayout(newExpensePanel);
        newExpensePanel.setLayout(newExpensePanelLayout);
        newExpensePanelLayout.setHorizontalGroup(
            newExpensePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(newExpensePanelLayout.createSequentialGroup()
                .addGroup(newExpensePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(newExpensePanelLayout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addComponent(addExpenseLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(newExpensePanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(newExpensePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(dateLbl, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(notesLbl, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(amountLbl, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(categoryLbl, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(newExpensePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(notesTxt)
                            .addComponent(categoryCB, 0, 130, Short.MAX_VALUE)
                            .addComponent(amountTxt)
                            .addComponent(dateDP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(85, Short.MAX_VALUE))
        );
        newExpensePanelLayout.setVerticalGroup(
            newExpensePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(newExpensePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(addExpenseLbl)
                .addGap(52, 52, 52)
                .addGroup(newExpensePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(newExpensePanelLayout.createSequentialGroup()
                        .addGroup(newExpensePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(categoryLbl)
                            .addComponent(categoryCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(amountLbl))
                    .addComponent(amountTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(newExpensePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(notesLbl)
                    .addComponent(notesTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(newExpensePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(dateLbl)
                    .addComponent(dateDP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(188, Short.MAX_VALUE))
        );

        newExpensePanelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {amountLbl, amountTxt, categoryCB, categoryLbl, dateLbl, notesLbl, notesTxt});

        expenseHistoryLbl.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        expenseHistoryLbl.setForeground(new java.awt.Color(51, 51, 255));
        expenseHistoryLbl.setText("Expense History");

        searchTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchTxtActionPerformed(evt);
            }
        });
        searchTxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                searchTxtKeyTyped(evt);
            }
        });

        expenseHistoryTbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Date", "Category", "Notes", "Amount"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(expenseHistoryTbl);
        if (expenseHistoryTbl.getColumnModel().getColumnCount() > 0) {
            expenseHistoryTbl.getColumnModel().getColumn(0).setMinWidth(0);
            expenseHistoryTbl.getColumnModel().getColumn(0).setPreferredWidth(0);
            expenseHistoryTbl.getColumnModel().getColumn(0).setMaxWidth(0);
        }

        javax.swing.GroupLayout expenseHistoryPanelLayout = new javax.swing.GroupLayout(expenseHistoryPanel);
        expenseHistoryPanel.setLayout(expenseHistoryPanelLayout);
        expenseHistoryPanelLayout.setHorizontalGroup(
            expenseHistoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(expenseHistoryPanelLayout.createSequentialGroup()
                .addGroup(expenseHistoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(expenseHistoryPanelLayout.createSequentialGroup()
                        .addGap(109, 109, 109)
                        .addComponent(expenseHistoryLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(expenseHistoryPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 438, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(expenseHistoryPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(searchTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        expenseHistoryPanelLayout.setVerticalGroup(
            expenseHistoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(expenseHistoryPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(expenseHistoryLbl)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(searchTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 313, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(newExpensePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(expenseHistoryPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(expenseHistoryPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(newExpensePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(buttonPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(mainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(buttonPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void saveBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveBtnActionPerformed
        if(validateFields()){
            persistExpense(categoryCB.getSelectedItem().toString(), amountTxt.getText(), notesTxt.getText(), databaseDateFormat.format(dateDP.getDate()));
        }
    }//GEN-LAST:event_saveBtnActionPerformed

    private void cancelBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelBtnActionPerformed
        this.dispose();
    }//GEN-LAST:event_cancelBtnActionPerformed

    private void searchTxtKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchTxtKeyTyped
        loadExpenseHistory(searchTxt.getText());
    }//GEN-LAST:event_searchTxtKeyTyped

    private void searchTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchTxtActionPerformed


    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ExpenseTracking.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ExpenseTracking.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ExpenseTracking.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ExpenseTracking.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ExpenseTracking().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel addExpenseLbl;
    private javax.swing.JLabel amountLbl;
    private javax.swing.JTextField amountTxt;
    private javax.swing.JPanel buttonPanel;
    private javax.swing.JButton cancelBtn;
    private javax.swing.JComboBox categoryCB;
    private javax.swing.JLabel categoryLbl;
    private com.toedter.calendar.JDateChooser dateDP;
    private javax.swing.JLabel dateLbl;
    private javax.swing.JLabel expenseHistoryLbl;
    private javax.swing.JPanel expenseHistoryPanel;
    private javax.swing.JTable expenseHistoryTbl;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JPanel newExpensePanel;
    private javax.swing.JLabel notesLbl;
    private javax.swing.JTextField notesTxt;
    private javax.swing.JButton saveBtn;
    private javax.swing.JTextField searchTxt;
    // End of variables declaration//GEN-END:variables
 
}
