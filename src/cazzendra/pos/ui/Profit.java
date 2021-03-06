/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cazzendra.pos.ui;

import cazzendra.pos.control.CommonController;
import cazzendra.pos.control.InvoiceControl;
import cazzendra.pos.core.Loading;
import cazzendra.pos.core.Validations;
import java.awt.Color;
import java.awt.Font;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Amal
 */
public class Profit extends javax.swing.JDialog {

    BigDecimal total = BigDecimal.ZERO;
    BigDecimal invoiceDiscounts = BigDecimal.ZERO;
    BigDecimal totalProfit = BigDecimal.ZERO;

    /**
     * Creates new form SalesReport
     *
     * @param parent
     * @param modal
     */
    public Profit(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setDefaults();
        panel.setBackground(Loading.getColorCode());
        lblTotalCost.setVisible(false);
        lblProfit.setVisible(false);
        Loading.customJButton(Loading.getColorR(), Loading.getColorG(), Loading.getColorB(), btnPrint);
    }

    private void setDefaults() {
        CalFromDate.setDate(CommonController.getCurrentJavaSqlDate());
        CalToDate.setDate(CommonController.getCurrentJavaSqlDate());
    }

    private void loadDataByGivenDateRange() {
        try {
            ResultSet rset = CommonController.getItemProfitsByDate(Validations.getSqlDateByUtilDate(CalFromDate.getDate()).toString(), Validations.getSqlDateByUtilDate(CalToDate.getDate()).toString());
            String[] columnList = {"item_name", "invoice_detail_selling_price", "invoice_detail_purchase_price", "invoice_detail_item_qty", "profit"};
            CommonController.loadDataToTable(tblInvoices, rset, columnList);
        } catch (SQLException ex) {
            Logger.getLogger(Profit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void analytics() {
        lblTotalCost.setText(Integer.toString(tblInvoices.getRowCount()));
        for (int i = 0; i < tblInvoices.getRowCount(); i++) {
            total = total.add(Validations.getBigDecimalOrZeroFromString(tblInvoices.getValueAt(i, 4).toString()));
        }
        lblTotalCost.setText(Validations.formatWithTwoDigits(total.toString()));
        loadProfitByDateRange();
        netProfit();
//        total = BigDecimal.ZERO;        
//        for (int i = 0; i < tblInvoices.getRowCount(); i++) {
//            totalProfit =  totalProfit.
//        }
        
    }

    private void loadProfitByDateRange() {
        try {
            invoiceDiscounts = InvoiceControl.getDiscountedInvoiceProfit(Validations.getSqlDateByUtilDate(CalFromDate.getDate()),
                    Validations.getSqlDateByUtilDate(CalFromDate.getDate()));
            lblProfit.setText(Validations.formatWithTwoDigits(invoiceDiscounts.toString()));
        } catch (SQLException ex) {
            Logger.getLogger(Profit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void netProfit() {
        DefaultTableModel dtm = (DefaultTableModel) tblInvoices.getModel();
        for (int i = 0; i < tblInvoices.getRowCount(); i++) { 
            totalProfit = totalProfit.add(Validations.getBigDecimalOrZeroFromString(dtm.getValueAt(i, 4).toString())); 
        }
         lblNetProfit.setText(Validations.formatWithTwoDigits(totalProfit.toString()));
         totalProfit = BigDecimal.ZERO;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel = new javax.swing.JPanel();
        CalFromDate = new com.toedter.calendar.JDateChooser();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        CalToDate = new com.toedter.calendar.JDateChooser();
        btnPrint = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblInvoices = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        lblTotalCost = new javax.swing.JLabel();
        lblProfit = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        lblNetProfit = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Item Profit Analytics");
        setMaximumSize(new java.awt.Dimension(1024, 694));
        setMinimumSize(new java.awt.Dimension(1024, 694));
        setResizable(false);

        panel.setMaximumSize(new java.awt.Dimension(1024, 694));
        panel.setMinimumSize(new java.awt.Dimension(1024, 694));
        panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        CalFromDate.setToolTipText("Date");
        CalFromDate.setDateFormatString("yyyy-MM-dd");
        CalFromDate.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        panel.add(CalFromDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(19, 46, 237, 38));

        jLabel6.setFont(new java.awt.Font("Ubuntu Medium", 0, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 102));
        jLabel6.setText("From Date");
        panel.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(19, 17, 215, -1));

        jLabel7.setFont(new java.awt.Font("Ubuntu Medium", 0, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 102));
        jLabel7.setText("To Date");
        panel.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(308, 17, 215, -1));

        CalToDate.setToolTipText("Date");
        CalToDate.setDateFormatString("yyyy-MM-dd");
        CalToDate.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        CalToDate.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                CalToDateKeyReleased(evt);
            }
        });
        panel.add(CalToDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(308, 46, 234, 38));

        btnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cazzendra/pos/newtheme/icons/search-9-32.png"))); // NOI18N
        btnPrint.setToolTipText("Search");
        btnPrint.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintActionPerformed(evt);
            }
        });
        panel.add(btnPrint, new org.netbeans.lib.awtextra.AbsoluteConstraints(923, 44, 80, 40));

        tblInvoices.setFont(new java.awt.Font("Ubuntu", 0, 16)); // NOI18N
        tblInvoices.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Item Name", "Selling Price", "Purchasing Price", "Sold Qty", "Item Profit"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblInvoices.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tblInvoices.getTableHeader().setFont(new Font("Ubuntu", Font.BOLD, 18));
        tblInvoices.getTableHeader().setOpaque(false);
        tblInvoices.getTableHeader().setBackground(new Color(0, 0, 102));
        tblInvoices.getTableHeader().setForeground(new Color(255, 255, 255));
        tblInvoices.setRowHeight(22);
        jScrollPane1.setViewportView(tblInvoices);
        if (tblInvoices.getColumnModel().getColumnCount() > 0) {
            tblInvoices.getColumnModel().getColumn(1).setMinWidth(150);
            tblInvoices.getColumnModel().getColumn(1).setPreferredWidth(150);
            tblInvoices.getColumnModel().getColumn(1).setMaxWidth(150);
            tblInvoices.getColumnModel().getColumn(2).setMinWidth(200);
            tblInvoices.getColumnModel().getColumn(2).setPreferredWidth(200);
            tblInvoices.getColumnModel().getColumn(2).setMaxWidth(200);
            tblInvoices.getColumnModel().getColumn(3).setMinWidth(150);
            tblInvoices.getColumnModel().getColumn(3).setPreferredWidth(150);
            tblInvoices.getColumnModel().getColumn(3).setMaxWidth(150);
            tblInvoices.getColumnModel().getColumn(4).setMinWidth(150);
            tblInvoices.getColumnModel().getColumn(4).setPreferredWidth(150);
            tblInvoices.getColumnModel().getColumn(4).setMaxWidth(150);
        }

        panel.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(19, 95, 984, 509));

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 102)));
        jPanel2.setOpaque(false);
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblTotalCost.setFont(new java.awt.Font("Ubuntu", 1, 24)); // NOI18N
        lblTotalCost.setForeground(new java.awt.Color(255, 255, 255));
        lblTotalCost.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblTotalCost.setText("0");
        jPanel2.add(lblTotalCost, new org.netbeans.lib.awtextra.AbsoluteConstraints(2, 42, 151, -1));

        lblProfit.setFont(new java.awt.Font("Ubuntu", 1, 24)); // NOI18N
        lblProfit.setForeground(new java.awt.Color(255, 255, 255));
        lblProfit.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblProfit.setText("0");
        jPanel2.add(lblProfit, new org.netbeans.lib.awtextra.AbsoluteConstraints(171, 42, 148, -1));

        jLabel8.setFont(new java.awt.Font("Ubuntu Medium", 1, 24)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 102));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("Net Profit");
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 10, 300, -1));

        lblNetProfit.setFont(new java.awt.Font("Ubuntu Medium", 1, 24)); // NOI18N
        lblNetProfit.setForeground(new java.awt.Color(0, 0, 102));
        lblNetProfit.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblNetProfit.setText("0");
        jPanel2.add(lblNetProfit, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 40, 291, -1));

        panel.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(19, 610, 990, 80));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintActionPerformed
//        printReport();
        loadDataByGivenDateRange();
        analytics();
        netProfit();
    }//GEN-LAST:event_btnPrintActionPerformed

    private void CalToDateKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CalToDateKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_CalToDateKeyReleased

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
            java.util.logging.Logger.getLogger(Profit.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Profit.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Profit.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Profit.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Profit dialog = new Profit(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser CalFromDate;
    private com.toedter.calendar.JDateChooser CalToDate;
    private javax.swing.JButton btnPrint;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblNetProfit;
    private javax.swing.JLabel lblProfit;
    private javax.swing.JLabel lblTotalCost;
    private javax.swing.JPanel panel;
    private javax.swing.JTable tblInvoices;
    // End of variables declaration//GEN-END:variables
}
