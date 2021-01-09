/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cazzendra.pos.ui;

import cazzendra.pos.control.CommonController;
import cazzendra.pos.core.CommonConstants;
import cazzendra.pos.core.Loading;
import cazzendra.pos.core.Validations;
import cazzendra.pos.daoImpl.GrnDaoImpl;
import java.awt.Color;
import java.awt.Font;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JRException;

public class GrnHistory extends javax.swing.JFrame {

    public GrnHistory() {
        initComponents();
        panel.setBackground(Loading.getColorCode());
        loadAndSearchdata();
        Loading.customJButton(Loading.getColorR(), Loading.getColorG(), Loading.getColorB(), btnPrint); 
    }

    private void loadAndSearchdata() {

        ArrayList<String[]> attributeConditionValueList = new ArrayList<>();

        String[] acv1 = {"grn_id", CommonConstants.sql.LIKE, "%" + txtSearchAttribute.getText().trim() + "%"};
        attributeConditionValueList.add(acv1);

        String[] acv2 = {"grn_date", CommonConstants.sql.LIKE, "%" + txtSearchAttribute.getText().trim() + "%"};
        attributeConditionValueList.add(acv2);

        String[] acv3 = {"grn_total", CommonConstants.sql.LIKE, "%" + txtSearchAttribute.getText().trim() + "%"};
        attributeConditionValueList.add(acv3);

        String[] columnList = {"grn_date", "grn_id", "grn_item_qty", "grn_total"};

        try {
            ResultSet rset = new GrnDaoImpl().getGrnByMoreAttributes(attributeConditionValueList, CommonConstants.sql.OR);
            CommonController.loadDataToTable(tblGrnHistory, rset, columnList);
        } catch (SQLException ex) {
            Logger.getLogger(GrnHistory.class.getName()).log(Level.SEVERE, null, ex);
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

        jPanel1 = new javax.swing.JPanel();
        panel = new javax.swing.JPanel();
        txtSearchAttribute = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblGrnHistory = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        btnPrint = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("GRN History");
        setMaximumSize(new java.awt.Dimension(1024, 694));
        setMinimumSize(new java.awt.Dimension(1024, 694));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setMaximumSize(new java.awt.Dimension(1360, 728));
        jPanel1.setMinimumSize(new java.awt.Dimension(1360, 728));
        jPanel1.setPreferredSize(new java.awt.Dimension(1360, 728));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panel.setMaximumSize(new java.awt.Dimension(1360, 768));
        panel.setMinimumSize(new java.awt.Dimension(1360, 768));
        panel.setPreferredSize(new java.awt.Dimension(1360, 768));
        panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtSearchAttribute.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        txtSearchAttribute.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtSearchAttribute.setToolTipText("Search GRN by Date, GRN No and amount");
        txtSearchAttribute.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchAttributeKeyReleased(evt);
            }
        });
        panel.add(txtSearchAttribute, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 340, 40));

        tblGrnHistory.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        tblGrnHistory.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Date", "GRN No", "Item Qty", "Total Amount"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblGrnHistory.setToolTipText("Invoice");
        tblGrnHistory.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tblGrnHistory.getTableHeader().setFont(new Font("Ubuntu", Font.BOLD, 18));
        tblGrnHistory.getTableHeader().setOpaque(false);
        tblGrnHistory.getTableHeader().setBackground(new Color(0, 0, 102));
        tblGrnHistory.getTableHeader().setForeground(new Color(255, 255, 255));
        tblGrnHistory.setRowHeight(22);
        tblGrnHistory.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tblGrnHistory);
        if (tblGrnHistory.getColumnModel().getColumnCount() > 0) {
            tblGrnHistory.getColumnModel().getColumn(0).setMinWidth(200);
            tblGrnHistory.getColumnModel().getColumn(0).setPreferredWidth(200);
            tblGrnHistory.getColumnModel().getColumn(0).setMaxWidth(200);
            tblGrnHistory.getColumnModel().getColumn(1).setMinWidth(200);
            tblGrnHistory.getColumnModel().getColumn(1).setPreferredWidth(200);
            tblGrnHistory.getColumnModel().getColumn(1).setMaxWidth(200);
            tblGrnHistory.getColumnModel().getColumn(2).setMinWidth(150);
            tblGrnHistory.getColumnModel().getColumn(2).setPreferredWidth(150);
            tblGrnHistory.getColumnModel().getColumn(2).setMaxWidth(150);
            tblGrnHistory.getColumnModel().getColumn(3).setResizable(false);
        }

        panel.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 112, 990, 530));

        jLabel8.setFont(new java.awt.Font("Ubuntu Medium", 0, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 102));
        jLabel8.setText("Search GRN by Date, GRN No and amount");
        panel.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

        btnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cazzendra/pos/newtheme/icons/printer-3-32.png"))); // NOI18N
        btnPrint.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintActionPerformed(evt);
            }
        });
        panel.add(btnPrint, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 50, 80, 40));

        jPanel1.add(panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1023, 694));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1024, 694));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtSearchAttributeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchAttributeKeyReleased
        loadAndSearchdata();
    }//GEN-LAST:event_txtSearchAttributeKeyReleased

    private void btnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintActionPerformed
        int selectedRow = tblGrnHistory.getSelectedRow();
        if (selectedRow != -1) {
            try {
                int grnId = Validations.getIntOrZeroFromString(tblGrnHistory.getValueAt(selectedRow, 1).toString());
                CommonController.printGrn(Integer.toString(grnId));
            } catch (SQLException | JRException ex) {
                Logger.getLogger(GrnHistory.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btnPrintActionPerformed

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
            java.util.logging.Logger.getLogger(GrnHistory.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GrnHistory.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GrnHistory.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GrnHistory.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GrnHistory().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnPrint;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panel;
    private javax.swing.JTable tblGrnHistory;
    private javax.swing.JTextField txtSearchAttribute;
    // End of variables declaration//GEN-END:variables
}