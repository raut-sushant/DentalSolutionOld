package com.ekdant.dentalsolution.principal;
import com.ekdant.dentalsolution.dao.EmployeeDao;
import com.ekdant.dentalsolution.domain.EmployeeBean;
import com.ekdant.dentalsolution.register.RegisterEmployee;
import com.ekdant.dentalsolution.view.ViewEmployee;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Sushant
 */
public class Employees extends javax.swing.JFrame {
    EmployeeDao employeeDao;
    DateFormat displayDateFormat = new SimpleDateFormat("d MMM yyyy");
    DateFormat databaseDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    DateFormat inputDateFormat = new SimpleDateFormat("MMM d, yyyy");
    
    /** Creates new form JF_Employees */
    public Employees() {     
        employeeDao = new EmployeeDao();
        initComponents();
        displayEmployeeCountLabel(employeeDao.getTotalEmployeeCount());
        searchEmployee(null);
    }  
    
    private void displayEmployeeCountLabel(int count) {
        displayingEmployeeCountLbl.setText("Displaying " + count + " out of total " + employeeDao.getTotalEmployeeCount());
    }

    public void searchEmployee(String searchText){
        List<EmployeeBean> employees = employeeDao.fetchEmployees(searchText);
        DefaultTableModel model = (DefaultTableModel)jTableEmployees.getModel();
        model.setNumRows(0);
        int count = 0;
        for (EmployeeBean employee : employees) {
            model.addRow(new Object[]{
                employee.getEmployeeId(),
                employee.getName(),
                employee.getAge(),
                employee.getGender(),
                employee.getAddress(),
                employee.getCity(),
                employee.getTelephone(),
                employee.getEmail(),
                employee.getBirthDate() != null ? displayDateFormat.format(employee.getBirthDate()) : ""
            });
            count++;
        }        
        displayEmployeeCountLabel(count);
    }
     
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane = new javax.swing.JScrollPane();
        jTableEmployees = new javax.swing.JTable();
        searchLbl = new javax.swing.JLabel();
        searchTxt = new javax.swing.JTextField();
        addBtn = new javax.swing.JButton();
        updateBtn = new javax.swing.JButton();
        deleteBtn = new javax.swing.JButton();
        treatmentBtn = new javax.swing.JButton();
        cancelBtn = new javax.swing.JButton();
        displayingEmployeeCountLbl = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Employees");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setName("framePacientes"); // NOI18N
        setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width,Toolkit.getDefaultToolkit().getScreenSize().height-Toolkit.getDefaultToolkit().getScreenInsets(getGraphicsConfiguration()).bottom));

        jTableEmployees.setAutoCreateRowSorter(true);
        jTableEmployees.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Code", "Name", "Age", "Gender", "Address", "City", "Telephone ", "Email", "Birth"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableEmployees.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        jTableEmployees.setNextFocusableComponent(addBtn);
        jScrollPane.setViewportView(jTableEmployees);
        if (jTableEmployees.getColumnModel().getColumnCount() > 0) {
            jTableEmployees.getColumnModel().getColumn(0).setMinWidth(0);
            jTableEmployees.getColumnModel().getColumn(0).setPreferredWidth(0);
            jTableEmployees.getColumnModel().getColumn(0).setMaxWidth(0);
            jTableEmployees.getColumnModel().getColumn(1).setPreferredWidth(250);
            jTableEmployees.getColumnModel().getColumn(2).setPreferredWidth(20);
            jTableEmployees.getColumnModel().getColumn(3).setPreferredWidth(50);
            jTableEmployees.getColumnModel().getColumn(5).setPreferredWidth(100);
            jTableEmployees.getColumnModel().getColumn(6).setPreferredWidth(75);
            jTableEmployees.getColumnModel().getColumn(7).setPreferredWidth(100);
            jTableEmployees.getColumnModel().getColumn(8).setPreferredWidth(100);
        }

        searchLbl.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        searchLbl.setText("Search :");

        searchTxt.setNextFocusableComponent(jTableEmployees);
        searchTxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchTxtKeyReleased(evt);
            }
        });

        addBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/EkDant/icones/Patient Boy 1 Add.png"))); // NOI18N
        addBtn.setMnemonic('A');
        addBtn.setText("Add");
        addBtn.setNextFocusableComponent(updateBtn);
        addBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addBtnActionPerformed(evt);
            }
        });

        updateBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/EkDant/icones/Patient Boy 1 Edit 1.png"))); // NOI18N
        updateBtn.setMnemonic('V');
        updateBtn.setText("View");
        updateBtn.setNextFocusableComponent(deleteBtn);
        updateBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateBtnActionPerformed(evt);
            }
        });

        deleteBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/EkDant/icones/Patient Boy 1 Delete.png"))); // NOI18N
        deleteBtn.setMnemonic('D');
        deleteBtn.setText("Delete");
        deleteBtn.setNextFocusableComponent(treatmentBtn);
        deleteBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteBtnActionPerformed(evt);
            }
        });

        treatmentBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/EkDant/icones/Patient Boy 1.png"))); // NOI18N
        treatmentBtn.setMnemonic('T');
        treatmentBtn.setText("Treatment");
        treatmentBtn.setNextFocusableComponent(cancelBtn);
        treatmentBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                treatmentBtnActionPerformed(evt);
            }
        });

        cancelBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/EkDant/icones/Patient Boy 1 Stop.png"))); // NOI18N
        cancelBtn.setMnemonic('C');
        cancelBtn.setText("Cancel");
        cancelBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 918, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(searchLbl)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(searchTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(addBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(updateBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(deleteBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(treatmentBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(cancelBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(displayingEmployeeCountLbl))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchLbl)
                    .addComponent(searchTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(displayingEmployeeCountLbl)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 358, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(updateBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(deleteBtn)
                    .addComponent(treatmentBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cancelBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void addBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addBtnActionPerformed
        new RegisterEmployee(this).setVisible(true);
    }//GEN-LAST:event_addBtnActionPerformed

    private void updateBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateBtnActionPerformed

        int selectedEmployee = this.jTableEmployees.getSelectedRow();
        if(selectedEmployee == -1) {
            JOptionPane.showMessageDialog(null,"Please Select employee to Update !!", "Error!", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String employeeId   = this.jTableEmployees.getValueAt(selectedEmployee, 0).toString();
        String name        = this.jTableEmployees.getValueAt(selectedEmployee, 1).toString();
        String age         = this.jTableEmployees.getValueAt(selectedEmployee, 2).toString();
        String gender      = this.jTableEmployees.getValueAt(selectedEmployee, 3).toString();
        String address     = this.jTableEmployees.getValueAt(selectedEmployee, 4).toString();
        String city        = this.jTableEmployees.getValueAt(selectedEmployee, 5).toString();
        String telephone   = this.jTableEmployees.getValueAt(selectedEmployee, 6).toString();
        String email       = this.jTableEmployees.getValueAt(selectedEmployee, 7).toString();
        String birthDay    = this.jTableEmployees.getValueAt(selectedEmployee, 8).toString();

        new ViewEmployee(this, employeeId, name, birthDay, gender, address, city, telephone, email, age ).setVisible(true);
       
    }//GEN-LAST:event_updateBtnActionPerformed

    private void deleteBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteBtnActionPerformed

        int selectedEmployee = this.jTableEmployees.getSelectedRow();
        if(selectedEmployee == -1) {
            JOptionPane.showMessageDialog(null,"Please Select Employee to Delete !!", "Error!", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String confirmationMessage = "Really delete employee: " + this.jTableEmployees.getValueAt(selectedEmployee, 1).toString() + " ?";
        int chosenOption = JOptionPane.showConfirmDialog(null, confirmationMessage, "Delete", JOptionPane.YES_NO_OPTION);

        if (chosenOption == JOptionPane.YES_OPTION) {
            String selectedEmployeeId = this.jTableEmployees.getValueAt(selectedEmployee, 0).toString();
            if (employeeDao.deleteEmployeeById(selectedEmployee)) {
                JOptionPane.showMessageDialog(null, "Employee deleted successfully!", "Delete!", JOptionPane.INFORMATION_MESSAGE);
                searchEmployee(null);
            } else {
                JOptionPane.showMessageDialog(null, "Error in deletion", "Error!", JOptionPane.ERROR_MESSAGE);
            }            
        }         
    }//GEN-LAST:event_deleteBtnActionPerformed

    private void treatmentBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_treatmentBtnActionPerformed
        int SelectedRow = this.jTableEmployees.getSelectedRow();
        if(SelectedRow == -1) {
            JOptionPane.showMessageDialog(null,"Please Select Employee!!", "Error!", JOptionPane.ERROR_MESSAGE);
        }        
    }//GEN-LAST:event_treatmentBtnActionPerformed

    private void cancelBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelBtnActionPerformed
        this.dispose();
    }//GEN-LAST:event_cancelBtnActionPerformed

    private void searchTxtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchTxtKeyReleased
        this.searchEmployee(searchTxt.getText());
    }//GEN-LAST:event_searchTxtKeyReleased

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addBtn;
    private javax.swing.JButton cancelBtn;
    private javax.swing.JButton deleteBtn;
    private javax.swing.JLabel displayingEmployeeCountLbl;
    private javax.swing.JScrollPane jScrollPane;
    private javax.swing.JTable jTableEmployees;
    private javax.swing.JLabel searchLbl;
    private javax.swing.JTextField searchTxt;
    private javax.swing.JButton treatmentBtn;
    private javax.swing.JButton updateBtn;
    // End of variables declaration//GEN-END:variables

}
