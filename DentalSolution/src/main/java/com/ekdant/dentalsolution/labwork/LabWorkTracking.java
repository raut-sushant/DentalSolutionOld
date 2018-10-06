/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ekdant.dentalsolution.labwork;

import com.ekdant.dentalsolution.dao.LabDao;
import com.ekdant.dentalsolution.domain.LabWorkBean;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Sushant
 */
public class LabWorkTracking extends javax.swing.JFrame {
    DateFormat displayDateFormat = new SimpleDateFormat("d MMM yyyy");
    DateFormat databaseDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    DateFormat inputDateFormat = new SimpleDateFormat("MMM d, yyyy");
    JPopupMenu rightClickPopupMenu;
    JMenuItem closeOption;
    JMenuItem labWorkSubmittedOption;
    JMenuItem labWorkReceivedOption;
    int rightClickedLabWorkId;
    LabDao labDao;    
    
    /**
     * Creates new form ExpenseTracking
     */
    public LabWorkTracking() {        
        labDao = new LabDao();
        initComponents();  
        loadPopup();
        loadLabworkDetails(null);
    }
    
    private void loadPopup(){
        rightClickPopupMenu = new JPopupMenu();
        
        labWorkSubmittedOption = new JMenuItem("Lab Work Submitted");
        labWorkSubmittedOption.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
               updateLabWorkStatus(2);
            }
        });
        rightClickPopupMenu.add(labWorkSubmittedOption);
        
        labWorkReceivedOption = new JMenuItem("Lab Work Received");
        labWorkReceivedOption.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
               updateLabWorkStatus(3);
            }
        });
        rightClickPopupMenu.add(labWorkReceivedOption);
        
        closeOption = new JMenuItem("Close");
        closeOption.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
               updateLabWorkStatus(4);
            }
        });
         rightClickPopupMenu.add(closeOption);
    }
    
    private void updateLabWorkStatus(int status){
        LabWorkBean labWork = new LabWorkBean();
        labWork.setId(rightClickedLabWorkId);
        labWork.setStatus(status);
        labDao.updateLabWork(labWork);
    }
    
    private void loadLabworkDetails(String searchText) {
        List<LabWorkBean> labWorks = labDao.fetchLabWorks(searchText);
        DefaultTableModel model = (DefaultTableModel)labWorkTrackingTbl.getModel();
        model.setNumRows(0);

        for (LabWorkBean labWork : labWorks) {
            model.addRow(new Object[]{
                labWork.getId(),
                displayDateFormat.format(labWork.getCheckup().getDate()),
                getPatientDetails(labWork.getPatient().getName(), labWork.getPatient().getCaseId(), labWork.getPatient().getTelephone()),
                labWork.getWork(),
                getLabDetails(labWork.getLab().getName(), labWork.getLab().getContact()),
                getLabStatus(labWork.getStatus())
            });
        }
        
        
        labWorkTrackingTbl.setDefaultRenderer(String.class, new TableCellRenderer() {

            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                String cellValue = (String) value;
                JLabel labWorkLbl = new JLabel();
                
                if(cellValue != null && !cellValue.isEmpty()){
                    labWorkLbl.setText(cellValue);
                    labWorkLbl.setOpaque(true);
                }
                if(labWorkTrackingTbl.getValueAt(row, 5).toString().equalsIgnoreCase("Impression Done")){
                    labWorkLbl.setBackground(new Color(255, 255, 200));
                }else if(labWorkTrackingTbl.getValueAt(row, 5).toString().equalsIgnoreCase("Lab Work Submitted")){
                    labWorkLbl.setBackground(new Color(200, 200, 255));
                }else if(labWorkTrackingTbl.getValueAt(row, 5).toString().equalsIgnoreCase("Lab Work Completed")){
                    labWorkLbl.setBackground(new Color(200, 255, 200));
                }
                return labWorkLbl;
            }
        });
        
        labWorkTrackingTbl.addMouseListener(new MouseListener() {

            public void mouseClicked(MouseEvent e) {
                
            }

            public void mousePressed(MouseEvent e) {}

            public void mouseReleased(MouseEvent e) {
                rightClickedLabWorkId = Integer.parseInt(labWorkTrackingTbl.getValueAt(labWorkTrackingTbl.rowAtPoint(e.getPoint()), 0).toString()); 
                if(e.getButton() == MouseEvent.BUTTON3 ){
                   rightClickPopupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }

            public void mouseEntered(MouseEvent e) {}

            public void mouseExited(MouseEvent e) {}
        });
    }
    
    private String getPatientDetails(String patientName, String caseNumber, String contact){
        return "<html><b>"+patientName + "</b> <br/>" + 
                    caseNumber + "<br/>"+
                    contact+"</html>";
    }
    
    private String getLabDetails(String labName, String contact){
        return "<html><b>"+labName + "</b> <br/>" + 
                    contact+"</html>";
    }
    
    private String getLabStatus(int statusId){
        String statusStr = "";
        if(statusId == 1){
            statusStr = "Impression Done";
        }else if(statusId == 2){
            statusStr = "Lab Work Submitted";
        }else if(statusId == 3){
            statusStr = "Lab Work Completed";
        }else if(statusId == 4){
            statusStr = "Closed";
        }
        return statusStr;
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
        cancelBtn = new javax.swing.JButton();
        mainPanel = new javax.swing.JPanel();
        expenseHistoryPanel = new javax.swing.JPanel();
        labWorkTrackingLbl = new javax.swing.JLabel();
        searchTxt = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        labWorkTrackingTbl = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Lab work Tracking");
        setName("Treatment"); // NOI18N

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
                .addGap(309, 309, 309)
                .addComponent(cancelBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(211, Short.MAX_VALUE))
        );
        buttonPanelLayout.setVerticalGroup(
            buttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, buttonPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cancelBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        labWorkTrackingLbl.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        labWorkTrackingLbl.setForeground(new java.awt.Color(51, 51, 255));
        labWorkTrackingLbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labWorkTrackingLbl.setText("Lab Work Tracking");

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

        labWorkTrackingTbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Date", "Patient", "Lab Work", "Lab", "Status"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        labWorkTrackingTbl.setRowHeight(50);
        jScrollPane1.setViewportView(labWorkTrackingTbl);
        if (labWorkTrackingTbl.getColumnModel().getColumnCount() > 0) {
            labWorkTrackingTbl.getColumnModel().getColumn(0).setMinWidth(0);
            labWorkTrackingTbl.getColumnModel().getColumn(0).setPreferredWidth(0);
            labWorkTrackingTbl.getColumnModel().getColumn(0).setMaxWidth(0);
        }

        javax.swing.GroupLayout expenseHistoryPanelLayout = new javax.swing.GroupLayout(expenseHistoryPanel);
        expenseHistoryPanel.setLayout(expenseHistoryPanelLayout);
        expenseHistoryPanelLayout.setHorizontalGroup(
            expenseHistoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 635, Short.MAX_VALUE)
            .addGroup(expenseHistoryPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(searchTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(labWorkTrackingLbl, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        expenseHistoryPanelLayout.setVerticalGroup(
            expenseHistoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(expenseHistoryPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labWorkTrackingLbl)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(searchTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 281, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(expenseHistoryPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(expenseHistoryPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                        .addGap(0, 10, Short.MAX_VALUE))))
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

    private void cancelBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelBtnActionPerformed
        this.dispose();
    }//GEN-LAST:event_cancelBtnActionPerformed

    private void searchTxtKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchTxtKeyTyped
        loadLabworkDetails(searchTxt.getText());
    }//GEN-LAST:event_searchTxtKeyTyped

    private void searchTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchTxtActionPerformed
 
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel buttonPanel;
    private javax.swing.JButton cancelBtn;
    private javax.swing.JPanel expenseHistoryPanel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labWorkTrackingLbl;
    private javax.swing.JTable labWorkTrackingTbl;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JTextField searchTxt;
    // End of variables declaration//GEN-END:variables
 
}
