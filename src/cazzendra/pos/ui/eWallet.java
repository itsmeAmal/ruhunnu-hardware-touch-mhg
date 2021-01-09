
package cazzendra.pos.ui;
import cazzendra.pos.control.CommonController;
import cazzendra.pos.control.CustomerControl;
import cazzendra.pos.core.CommonConstants;
import cazzendra.pos.core.Loading;
import cazzendra.pos.core.Validations;
import cazzendra.pos.daoImpl.InvoiceDaoImpl;
import cazzendra.pos.daoImpl.InvoicePaymentDaoImpl;
import cazzendra.pos.model.Customer;
import java.awt.Color;
import java.awt.Font;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Amal
 */
public class eWallet extends javax.swing.JFrame {
    
    private String Lang;
    
    private int selectedCustId = 0;
    private Customer customer = new Customer();
    private BigDecimal accountBalance = BigDecimal.ZERO;
    
    public eWallet(String Language) {
        initComponents();
        panel.setBackground(Loading.getColorCode());
        Loading.customJButton(Loading.getColorR(), Loading.getColorG(), Loading.getColorB(), btnCustomerSearch);
        Loading.customJButton(Loading.getColorR(), Loading.getColorG(), Loading.getColorB(), btnSavePayment);
        txtDate.setDate(CommonController.getCurrentJavaSqlDate());
        this.Lang = Language;
        SetUi();
    }
    
    private void loadDataToTablePaymentsTableAndSetOtherDetails() {
        int SelectedInvoiceNo = tblInvoice.getSelectedRow();
        if (SelectedInvoiceNo != -1) {
            String InvoiceNo = tblInvoice.getValueAt(SelectedInvoiceNo, 0).toString();
            lblInvoiceNo.setText(InvoiceNo);
            try {
                String[] columnList = {"invoice_payment_id", "invoice_payment_date", "invoice_payment_discounted_total",
                    "cust_paid_amount", "invoice_payment_cash",
                    "invoice_payment_discount_rate", "invoice_payment_discount_value",
                    "invoice_payment_invoice_no", "invoice_payment_customer_id",
                    "invoice_payment_description"};
                ResultSet rset = new InvoicePaymentDaoImpl().getInvoicePaymentByOneAttribute("invoice_payment_invoice_no",
                        CommonConstants.sql.EQUAL, InvoiceNo);
                CommonController.loadDataToTable(tblInvPayments, rset, columnList);
            } catch (SQLException ex) {
                Logger.getLogger(eWallet.class.getName()).log(Level.SEVERE, null, ex);
            }
            txtInitialPayment.setText(tblInvoice.getValueAt(SelectedInvoiceNo, 8).toString());
            txtInstallments.setText(tblInvoice.getValueAt(SelectedInvoiceNo, 9).toString());
            txtInstallmentValue.setText(tblInvoice.getValueAt(SelectedInvoiceNo, 10).toString());
            txtInterestRate.setText(tblInvoice.getValueAt(SelectedInvoiceNo, 11).toString());
            txtDocumentCharge.setText(tblInvoice.getValueAt(SelectedInvoiceNo, 12).toString());
        }
    }
    
    private void LoadInvoiceDetailsToTable() {
        try {
            ResultSet Rset = new InvoiceDaoImpl().getInvoiceByOneAttribute("invoice_cust_id",
                    CommonConstants.sql.EQUAL, Integer.toString(selectedCustId));
            String[] ColumnList = {"invoice_id", "invoice_date", "invoice_time", "invoice_user_id",
                "invoice_total", "invoice_item_qty", "invoice_discount_rate", "invoice_cust_id", "paid_amount",
                "invoice_installments", "invoice_installment_value", "invoice_interest_rate", "invoice_document_charge"};
            CommonController.loadDataToTable(tblInvoice, Rset, ColumnList);
        } catch (SQLException ex) {
            Logger.getLogger(eWallet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void SearchInvoiceNo(String SearchValue) throws SQLException {
        ArrayList<String[]> AttributeConditionValueList = new ArrayList<>();
        
        String[] ACV1 = {"invoice_id", CommonConstants.sql.LIKE, "%" + SearchValue + "%"};
        AttributeConditionValueList.add(ACV1);
        
        String[] ACV2 = {"invoice_cust_id", CommonConstants.sql.EQUAL, Integer.toString(selectedCustId)}; 
        AttributeConditionValueList.add(ACV2);
        
        ResultSet Rset = new InvoiceDaoImpl().gerInvoicesByMoreAttributes(AttributeConditionValueList, CommonConstants.sql.AND);
        
        String[] ColumnList = {"invoice_id", "invoice_date", "invoice_time", "invoice_user_id",
            "invoice_total", "invoice_item_qty", "invoice_discount_rate", "invoice_cust_id", "paid_amount",
            "invoice_installments", "invoice_installment_value", "invoice_interest_rate", "invoice_document_charge"};
        CommonController.loadDataToTable(tblInvoice, Rset, ColumnList);
    }
    
    private void customerSearch() {
        CustomerSearch custSearch = new CustomerSearch(this, true, Lang);
        custSearch.setVisible(true);
        selectedCustId = custSearch.selectedCustomer();
        comboCustomer.removeAllItems();
        if (selectedCustId != 0) {
            try {
                customer = CustomerControl.getCustomerObjectByCustId(selectedCustId);
            } catch (SQLException ex) {
                Logger.getLogger(eWallet.class.getName()).log(Level.SEVERE, null, ex);
            }
            comboCustomer.addItem(customer.getCustomerName());
            LoadInvoiceDetailsToTable();
        }
    }
    
    private void calculateCustomerEWalletBalance() {
        accountBalance = BigDecimal.ZERO;
        DefaultTableModel dtm = (DefaultTableModel) tblInvPayments.getModel();
        for (int i = 0; i < dtm.getRowCount(); i++) {
            accountBalance = accountBalance.add(Validations.getBigDecimalOrZeroFromString(dtm.getValueAt(i, 3).toString()));
        }
        lblAccountBalance.setText(accountBalance.toString());
    }
    
    private void SetUi() {
        if (!Loading.isEnableHirePurchase()) {
            lblInstallments.setVisible(false);
            txtInstallments.setVisible(false);
            lblInstallmentValue.setVisible(false);
            txtInstallmentValue.setVisible(false);
            lblPaymentHistory5.setVisible(false);
            txtInterestRate.setVisible(false);
            lblPaymentHistory6.setVisible(false);
            txtDocumentCharge.setVisible(false);
        }
    }
    
    private void SetDefaults() {
        DefaultTableModel dtmInvoice = (DefaultTableModel) tblInvoice.getModel();
        dtmInvoice.setRowCount(0);
        DefaultTableModel dtmInvoiceDetails = (DefaultTableModel) tblInvPayments.getModel();
        dtmInvoiceDetails.setRowCount(0);
        txtInitialPayment.setText(null);
        txtInstallments.setText(null);
        txtInstallmentValue.setText(null);
        txtInterestRate.setText(null);
        txtDocumentCharge.setText(null);
        lblAccountBalance.setText("0.00");
        lblInvoiceNo.setText(null);
    }
    
    private void TblInvoiceMouseClick() {
        loadDataToTablePaymentsTableAndSetOtherDetails();
        calculateCustomerEWalletBalance();
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
        jScrollPane1 = new javax.swing.JScrollPane();
        tblInvoice = new javax.swing.JTable();
        comboCustomer = new javax.swing.JComboBox<>();
        lblInvoiceNo = new javax.swing.JLabel();
        btnCustomerSearch = new javax.swing.JButton();
        lblAccountBalance = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        txtAmount = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        txtDate = new com.toedter.calendar.JDateChooser();
        jLabel14 = new javax.swing.JLabel();
        btnSavePayment = new javax.swing.JButton();
        comboCustomer1 = new javax.swing.JComboBox<>();
        lblAccBalanceTxt = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        lblPaymentHistory = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblInvPayments = new javax.swing.JTable();
        lblSearchBy = new javax.swing.JLabel();
        txtSearchByInvNoOrDate = new javax.swing.JTextField();
        pnlInstallmentDetails = new javax.swing.JPanel();
        lblInstallments = new javax.swing.JLabel();
        lblPaymentHistory3 = new javax.swing.JLabel();
        lblInstallmentValue = new javax.swing.JLabel();
        lblPaymentHistory5 = new javax.swing.JLabel();
        lblPaymentHistory6 = new javax.swing.JLabel();
        txtInitialPayment = new javax.swing.JTextField();
        txtInstallments = new javax.swing.JTextField();
        txtInstallmentValue = new javax.swing.JTextField();
        txtInterestRate = new javax.swing.JTextField();
        txtDocumentCharge = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Customer Account");
        setAlwaysOnTop(true);
        setMaximumSize(new java.awt.Dimension(1360, 700));
        setMinimumSize(new java.awt.Dimension(1360, 700));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panel.setMaximumSize(new java.awt.Dimension(1024, 694));
        panel.setMinimumSize(new java.awt.Dimension(1024, 694));
        panel.setPreferredSize(new java.awt.Dimension(1024, 694));
        panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblInvoice.setFont(new java.awt.Font("Ubuntu", 0, 16)); // NOI18N
        tblInvoice.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Invoice No", "Date", "Time", "User Id", "Invoice Total", "Item Qty", "Disc. Rate", "Cust. Id", "Initial Payment", "Installments", "Installment Value", "Interest Rate", "Document Charge"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblInvoice.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tblInvoice.getTableHeader().setFont(new Font("Ubuntu", Font.BOLD, 18));
        tblInvoice.getTableHeader().setOpaque(false);
        tblInvoice.getTableHeader().setBackground(new Color(0, 0, 102));
        tblInvoice.getTableHeader().setForeground(new Color(255, 255, 255));
        tblInvoice.setRowHeight(22);
        tblInvoice.getTableHeader().setReorderingAllowed(false);
        tblInvoice.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblInvoiceMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblInvoiceMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tblInvoice);
        if (tblInvoice.getColumnModel().getColumnCount() > 0) {
            tblInvoice.getColumnModel().getColumn(0).setMinWidth(150);
            tblInvoice.getColumnModel().getColumn(0).setPreferredWidth(150);
            tblInvoice.getColumnModel().getColumn(0).setMaxWidth(150);
            tblInvoice.getColumnModel().getColumn(1).setMinWidth(150);
            tblInvoice.getColumnModel().getColumn(1).setPreferredWidth(150);
            tblInvoice.getColumnModel().getColumn(1).setMaxWidth(150);
            tblInvoice.getColumnModel().getColumn(2).setMinWidth(0);
            tblInvoice.getColumnModel().getColumn(2).setPreferredWidth(0);
            tblInvoice.getColumnModel().getColumn(2).setMaxWidth(0);
            tblInvoice.getColumnModel().getColumn(3).setMinWidth(0);
            tblInvoice.getColumnModel().getColumn(3).setPreferredWidth(0);
            tblInvoice.getColumnModel().getColumn(3).setMaxWidth(0);
            tblInvoice.getColumnModel().getColumn(4).setMinWidth(200);
            tblInvoice.getColumnModel().getColumn(4).setPreferredWidth(200);
            tblInvoice.getColumnModel().getColumn(4).setMaxWidth(200);
            tblInvoice.getColumnModel().getColumn(5).setMinWidth(0);
            tblInvoice.getColumnModel().getColumn(5).setPreferredWidth(0);
            tblInvoice.getColumnModel().getColumn(5).setMaxWidth(0);
            tblInvoice.getColumnModel().getColumn(6).setMinWidth(0);
            tblInvoice.getColumnModel().getColumn(6).setPreferredWidth(0);
            tblInvoice.getColumnModel().getColumn(6).setMaxWidth(0);
            tblInvoice.getColumnModel().getColumn(7).setMinWidth(0);
            tblInvoice.getColumnModel().getColumn(7).setPreferredWidth(0);
            tblInvoice.getColumnModel().getColumn(7).setMaxWidth(0);
            tblInvoice.getColumnModel().getColumn(9).setMinWidth(0);
            tblInvoice.getColumnModel().getColumn(9).setPreferredWidth(0);
            tblInvoice.getColumnModel().getColumn(9).setMaxWidth(0);
            tblInvoice.getColumnModel().getColumn(10).setMinWidth(0);
            tblInvoice.getColumnModel().getColumn(10).setPreferredWidth(0);
            tblInvoice.getColumnModel().getColumn(10).setMaxWidth(0);
            tblInvoice.getColumnModel().getColumn(11).setMinWidth(0);
            tblInvoice.getColumnModel().getColumn(11).setPreferredWidth(0);
            tblInvoice.getColumnModel().getColumn(11).setMaxWidth(0);
            tblInvoice.getColumnModel().getColumn(12).setMinWidth(0);
            tblInvoice.getColumnModel().getColumn(12).setPreferredWidth(0);
            tblInvoice.getColumnModel().getColumn(12).setMaxWidth(0);
        }

        panel.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, 690, 290));

        comboCustomer.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        comboCustomer.setMaximumRowCount(4);
        comboCustomer.setToolTipText("Customer");
        comboCustomer.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                comboCustomerPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });
        comboCustomer.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                comboCustomerKeyReleased(evt);
            }
        });
        panel.add(comboCustomer, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 250, 40));

        lblInvoiceNo.setFont(new java.awt.Font("Ubuntu Light", 1, 36)); // NOI18N
        lblInvoiceNo.setForeground(new java.awt.Color(0, 0, 102));
        lblInvoiceNo.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblInvoiceNo.setText("Invoice No");
        panel.add(lblInvoiceNo, new org.netbeans.lib.awtextra.AbsoluteConstraints(1100, 110, 230, 40));

        btnCustomerSearch.setBackground(new java.awt.Color(0, 0, 102));
        btnCustomerSearch.setFont(new java.awt.Font("Ubuntu Medium", 1, 18)); // NOI18N
        btnCustomerSearch.setForeground(new java.awt.Color(255, 255, 255));
        btnCustomerSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cazzendra/pos/newtheme/icons/search-9-32.png"))); // NOI18N
        btnCustomerSearch.setToolTipText("Search");
        btnCustomerSearch.setBorder(null);
        btnCustomerSearch.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCustomerSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCustomerSearchActionPerformed(evt);
            }
        });
        panel.add(btnCustomerSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 50, 40, 40));

        lblAccountBalance.setFont(new java.awt.Font("Ubuntu Medium", 0, 48)); // NOI18N
        lblAccountBalance.setForeground(new java.awt.Color(204, 0, 0));
        lblAccountBalance.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblAccountBalance.setText("0.00");
        panel.add(lblAccountBalance, new org.netbeans.lib.awtextra.AbsoluteConstraints(1070, 50, 260, 50));

        jLabel13.setFont(new java.awt.Font("Ubuntu Medium", 0, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 0, 102));
        jLabel13.setText("Customer");
        panel.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 102)));
        jPanel1.setOpaque(false);

        txtAmount.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        txtAmount.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabel15.setFont(new java.awt.Font("Ubuntu Medium", 0, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(0, 0, 102));
        jLabel15.setText("Amount");

        jLabel16.setFont(new java.awt.Font("Ubuntu Medium", 0, 18)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(0, 0, 102));
        jLabel16.setText("Date");

        txtDate.setToolTipText("Date");
        txtDate.setDateFormatString("yyyy-MM-dd");
        txtDate.setEnabled(false);
        txtDate.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel14.setFont(new java.awt.Font("Ubuntu Medium", 0, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 0, 102));
        jLabel14.setText("Payment Type");

        btnSavePayment.setBackground(new java.awt.Color(0, 0, 102));
        btnSavePayment.setFont(new java.awt.Font("Ubuntu Medium", 1, 18)); // NOI18N
        btnSavePayment.setForeground(new java.awt.Color(255, 255, 255));
        btnSavePayment.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cazzendra/pos/newtheme/icons/save-32.png"))); // NOI18N
        btnSavePayment.setToolTipText("Add payment");
        btnSavePayment.setBorder(null);
        btnSavePayment.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSavePayment.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                btnSavePaymentFocusGained(evt);
            }
        });
        btnSavePayment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSavePaymentActionPerformed(evt);
            }
        });

        comboCustomer1.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        comboCustomer1.setMaximumRowCount(4);
        comboCustomer1.setToolTipText("Customer");
        comboCustomer1.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                comboCustomer1PopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });
        comboCustomer1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                comboCustomer1KeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(txtDate, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(349, 349, 349)
                .addComponent(btnSavePayment, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(comboCustomer1, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDate, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(comboCustomer1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSavePayment, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        panel.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 460, 600, 230));

        lblAccBalanceTxt.setFont(new java.awt.Font("Ubuntu Medium", 0, 18)); // NOI18N
        lblAccBalanceTxt.setForeground(new java.awt.Color(0, 0, 102));
        lblAccBalanceTxt.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblAccBalanceTxt.setText("Due Balance");
        panel.add(lblAccBalanceTxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(1120, 20, 210, -1));

        jLabel17.setFont(new java.awt.Font("Ubuntu Medium", 0, 36)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(0, 0, 102));
        jLabel17.setText("Rs. ");
        panel.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 50, -1, 50));

        lblPaymentHistory.setFont(new java.awt.Font("Ubuntu Medium", 0, 18)); // NOI18N
        lblPaymentHistory.setForeground(new java.awt.Color(0, 0, 102));
        lblPaymentHistory.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblPaymentHistory.setText("Payment History for Invoice : ");
        panel.add(lblPaymentHistory, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 110, 270, 40));

        tblInvPayments.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tblInvPayments.getTableHeader().setFont(new Font("Ubuntu", Font.BOLD, 18));
        tblInvPayments.getTableHeader().setOpaque(false);
        tblInvPayments.getTableHeader().setBackground(new Color(0, 0, 102));
        tblInvPayments.getTableHeader().setForeground(new Color(255, 255, 255));
        tblInvPayments.setRowHeight(22);
        tblInvPayments.setFont(new java.awt.Font("Ubuntu", 0, 16)); // NOI18N
        tblInvPayments.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id", "Payment Date", "Disc. Total", "Due Amount", "Paid Amount", "discount rate", "discount value", "invoice no", "customer id", "Description"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblInvPayments.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(tblInvPayments);
        if (tblInvPayments.getColumnModel().getColumnCount() > 0) {
            tblInvPayments.getColumnModel().getColumn(0).setMinWidth(0);
            tblInvPayments.getColumnModel().getColumn(0).setPreferredWidth(0);
            tblInvPayments.getColumnModel().getColumn(0).setMaxWidth(0);
            tblInvPayments.getColumnModel().getColumn(1).setMinWidth(150);
            tblInvPayments.getColumnModel().getColumn(1).setPreferredWidth(150);
            tblInvPayments.getColumnModel().getColumn(1).setMaxWidth(150);
            tblInvPayments.getColumnModel().getColumn(2).setMinWidth(0);
            tblInvPayments.getColumnModel().getColumn(2).setPreferredWidth(0);
            tblInvPayments.getColumnModel().getColumn(2).setMaxWidth(0);
            tblInvPayments.getColumnModel().getColumn(3).setMinWidth(0);
            tblInvPayments.getColumnModel().getColumn(3).setPreferredWidth(0);
            tblInvPayments.getColumnModel().getColumn(3).setMaxWidth(0);
            tblInvPayments.getColumnModel().getColumn(4).setMinWidth(150);
            tblInvPayments.getColumnModel().getColumn(4).setPreferredWidth(150);
            tblInvPayments.getColumnModel().getColumn(4).setMaxWidth(150);
            tblInvPayments.getColumnModel().getColumn(5).setMinWidth(0);
            tblInvPayments.getColumnModel().getColumn(5).setPreferredWidth(0);
            tblInvPayments.getColumnModel().getColumn(5).setMaxWidth(0);
            tblInvPayments.getColumnModel().getColumn(6).setMinWidth(0);
            tblInvPayments.getColumnModel().getColumn(6).setPreferredWidth(0);
            tblInvPayments.getColumnModel().getColumn(6).setMaxWidth(0);
            tblInvPayments.getColumnModel().getColumn(7).setMinWidth(0);
            tblInvPayments.getColumnModel().getColumn(7).setPreferredWidth(0);
            tblInvPayments.getColumnModel().getColumn(7).setMaxWidth(0);
            tblInvPayments.getColumnModel().getColumn(8).setMinWidth(0);
            tblInvPayments.getColumnModel().getColumn(8).setPreferredWidth(0);
            tblInvPayments.getColumnModel().getColumn(8).setMaxWidth(0);
        }

        panel.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 170, 600, 280));

        lblSearchBy.setFont(new java.awt.Font("Ubuntu Medium", 0, 18)); // NOI18N
        lblSearchBy.setForeground(new java.awt.Color(0, 0, 102));
        lblSearchBy.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblSearchBy.setText("Search by Invoice No");
        panel.add(lblSearchBy, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, 260, 40));

        txtSearchByInvNoOrDate.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        txtSearchByInvNoOrDate.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtSearchByInvNoOrDate.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchByInvNoOrDateKeyReleased(evt);
            }
        });
        panel.add(txtSearchByInvNoOrDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 100, 250, 40));

        pnlInstallmentDetails.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 102)));
        pnlInstallmentDetails.setOpaque(false);
        pnlInstallmentDetails.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblInstallments.setFont(new java.awt.Font("Ubuntu Medium", 0, 18)); // NOI18N
        lblInstallments.setForeground(new java.awt.Color(0, 0, 102));
        lblInstallments.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblInstallments.setText("Installments");
        pnlInstallmentDetails.add(lblInstallments, new org.netbeans.lib.awtextra.AbsoluteConstraints(7, 44, 160, 30));

        lblPaymentHistory3.setFont(new java.awt.Font("Ubuntu Medium", 0, 18)); // NOI18N
        lblPaymentHistory3.setForeground(new java.awt.Color(0, 0, 102));
        lblPaymentHistory3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblPaymentHistory3.setText("Initial Payment");
        pnlInstallmentDetails.add(lblPaymentHistory3, new org.netbeans.lib.awtextra.AbsoluteConstraints(7, 7, 160, 30));

        lblInstallmentValue.setFont(new java.awt.Font("Ubuntu Medium", 0, 18)); // NOI18N
        lblInstallmentValue.setForeground(new java.awt.Color(0, 0, 102));
        lblInstallmentValue.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblInstallmentValue.setText("Installment Value");
        pnlInstallmentDetails.add(lblInstallmentValue, new org.netbeans.lib.awtextra.AbsoluteConstraints(7, 81, 160, 30));

        lblPaymentHistory5.setFont(new java.awt.Font("Ubuntu Medium", 0, 18)); // NOI18N
        lblPaymentHistory5.setForeground(new java.awt.Color(0, 0, 102));
        lblPaymentHistory5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblPaymentHistory5.setText("Interest Rate");
        pnlInstallmentDetails.add(lblPaymentHistory5, new org.netbeans.lib.awtextra.AbsoluteConstraints(7, 118, 160, 30));

        lblPaymentHistory6.setFont(new java.awt.Font("Ubuntu Medium", 0, 18)); // NOI18N
        lblPaymentHistory6.setForeground(new java.awt.Color(0, 0, 102));
        lblPaymentHistory6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblPaymentHistory6.setText("Ducument Charge");
        pnlInstallmentDetails.add(lblPaymentHistory6, new org.netbeans.lib.awtextra.AbsoluteConstraints(7, 155, 160, 30));

        txtInitialPayment.setEditable(false);
        txtInitialPayment.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        txtInitialPayment.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        pnlInstallmentDetails.add(txtInitialPayment, new org.netbeans.lib.awtextra.AbsoluteConstraints(173, 8, 200, 30));

        txtInstallments.setEditable(false);
        txtInstallments.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        txtInstallments.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        pnlInstallmentDetails.add(txtInstallments, new org.netbeans.lib.awtextra.AbsoluteConstraints(173, 45, 200, 30));

        txtInstallmentValue.setEditable(false);
        txtInstallmentValue.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        txtInstallmentValue.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        pnlInstallmentDetails.add(txtInstallmentValue, new org.netbeans.lib.awtextra.AbsoluteConstraints(173, 82, 200, 30));

        txtInterestRate.setEditable(false);
        txtInterestRate.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        txtInterestRate.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        pnlInstallmentDetails.add(txtInterestRate, new org.netbeans.lib.awtextra.AbsoluteConstraints(173, 119, 200, 30));

        txtDocumentCharge.setEditable(false);
        txtDocumentCharge.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        txtDocumentCharge.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        pnlInstallmentDetails.add(txtDocumentCharge, new org.netbeans.lib.awtextra.AbsoluteConstraints(173, 156, 200, 30));

        panel.add(pnlInstallmentDetails, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 450, 690, 240));

        getContentPane().add(panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1360, 700));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void comboCustomerPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_comboCustomerPopupMenuWillBecomeInvisible
        // TODO add your handling code here:
    }//GEN-LAST:event_comboCustomerPopupMenuWillBecomeInvisible

    private void comboCustomerKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_comboCustomerKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_comboCustomerKeyReleased

    private void btnCustomerSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCustomerSearchActionPerformed
        SetDefaults();
        customerSearch();
    }//GEN-LAST:event_btnCustomerSearchActionPerformed

    private void btnSavePaymentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSavePaymentActionPerformed
    }//GEN-LAST:event_btnSavePaymentActionPerformed

    private void tblInvoiceMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblInvoiceMouseClicked
        TblInvoiceMouseClick();
    }//GEN-LAST:event_tblInvoiceMouseClicked

    private void tblInvoiceMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblInvoiceMouseReleased
        TblInvoiceMouseClick();
    }//GEN-LAST:event_tblInvoiceMouseReleased

    private void btnSavePaymentFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnSavePaymentFocusGained
        Loading.customJButton(255, 0, 0, btnSavePayment);
    }//GEN-LAST:event_btnSavePaymentFocusGained

    private void txtSearchByInvNoOrDateKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchByInvNoOrDateKeyReleased
        try { 
            SearchInvoiceNo(txtSearchByInvNoOrDate.getText().trim());
        } catch (SQLException ex) {
            Logger.getLogger(eWallet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_txtSearchByInvNoOrDateKeyReleased

    private void comboCustomer1PopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_comboCustomer1PopupMenuWillBecomeInvisible
        // TODO add your handling code here:
    }//GEN-LAST:event_comboCustomer1PopupMenuWillBecomeInvisible

    private void comboCustomer1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_comboCustomer1KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_comboCustomer1KeyReleased

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
            java.util.logging.Logger.getLogger(eWallet.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(eWallet.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(eWallet.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(eWallet.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new eWallet("").setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCustomerSearch;
    private javax.swing.JButton btnSavePayment;
    private javax.swing.JComboBox<String> comboCustomer;
    private javax.swing.JComboBox<String> comboCustomer1;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblAccBalanceTxt;
    private javax.swing.JLabel lblAccountBalance;
    private javax.swing.JLabel lblInstallmentValue;
    private javax.swing.JLabel lblInstallments;
    private javax.swing.JLabel lblInvoiceNo;
    private javax.swing.JLabel lblPaymentHistory;
    private javax.swing.JLabel lblPaymentHistory3;
    private javax.swing.JLabel lblPaymentHistory5;
    private javax.swing.JLabel lblPaymentHistory6;
    private javax.swing.JLabel lblSearchBy;
    private javax.swing.JPanel panel;
    private javax.swing.JPanel pnlInstallmentDetails;
    private javax.swing.JTable tblInvPayments;
    private javax.swing.JTable tblInvoice;
    private javax.swing.JTextField txtAmount;
    private com.toedter.calendar.JDateChooser txtDate;
    private javax.swing.JTextField txtDocumentCharge;
    private javax.swing.JTextField txtInitialPayment;
    private javax.swing.JTextField txtInstallmentValue;
    private javax.swing.JTextField txtInstallments;
    private javax.swing.JTextField txtInterestRate;
    private javax.swing.JTextField txtSearchByInvNoOrDate;
    // End of variables declaration//GEN-END:variables
}
