/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cazzendra.pos.ui;

import cazzendra.pos.control.CommonController;
import cazzendra.pos.control.CustomerControl;
import cazzendra.pos.control.InvoicePaymentControl;
import cazzendra.pos.control.PaymentControl;
import cazzendra.pos.core.Loading;
import cazzendra.pos.core.MethodStatus;
import cazzendra.pos.core.Options;
import cazzendra.pos.core.Validations;
import cazzendra.pos.model.Payment;
import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Amal
 */
public class InvoicePayment extends javax.swing.JDialog {

    /**
     * @param parent
     * @param modal
     */
    private int invoiceNumber;
    private String invTotal;
    private int custId = 0;
    MethodStatus status = null;
    BigDecimal InvoiceCashPay = BigDecimal.ZERO;

    private String Lang;

    public InvoicePayment(java.awt.Frame parent, boolean modal, int invoiceNo, String total, int customerId, String Language) {
        super(parent, modal);
        initComponents();
        this.invoiceNumber = invoiceNo;
        this.invTotal = total;
        MainPanel.setBackground(Loading.getColorCode());
        this.custId = customerId;
        setDefaults();
        this.Lang = Language;
        SetLanguageUi();
        readConfigFile();
        CustomizeUi();
    }

    private void calculateDiscount() {
        BigDecimal afterDiscountTotal = Validations.getBigDecimalOrZeroFromString(invTotal).subtract(
                Validations.getBigDecimalOrZeroFromString(txtDiscountValue.getText()));
        txtAfterDiscount.setText(Validations.formatWithTwoDigits(afterDiscountTotal.toString()));
    }

    private void readConfigFile() {
        try {
            File myObj = new File("config.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                txtInterestRate.setText(data);
                data = myReader.nextLine();
                txtDocumentCharge.setText(data);
            }
            myReader.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Invoice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void setDefaults() {
        txtDate.setDate(CommonController.getCurrentJavaSqlDate());
        lblInvoiceTotal.setText(invTotal);
        txtAfterDiscount.setText(invTotal);
        txtDiscountValue.setText("0");
        txtDiscountValue.selectAll();
        txtPaidAmountCash.requestFocus();
        Loading.customJButton(Loading.getColorR(), Loading.getColorG(), Loading.getColorB(), btnSave);
        Loading.customJButton(Loading.getColorR(), Loading.getColorG(), Loading.getColorB(), btnChequeList);
        try {
            lblCustomerName.setText(CustomerControl.getCustomerObjectByCustId(custId).getCustomerName());
        } catch (SQLException ex) {
            Logger.getLogger(InvoicePayment.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void calculatePaidAmount() {
        BigDecimal balance = Validations.getBigDecimalOrZeroFromString(txtPaidAmountCash.getText())
                .add(Validations.getBigDecimalOrZeroFromString(txtChequePay.getText().trim()))
                .subtract(Validations.getBigDecimalOrZeroFromString(txtAfterDiscount.getText()));
        txtBalance.setText(Validations.formatWithTwoDigits(balance.toString()));
    }

    public MethodStatus getStatus() {
        return status;
    }

    public BigDecimal cashPay() {
        return Validations.getBigDecimalOrZeroFromString(txtPaidAmountCash.getText().trim());
    }

//    public BigDecimal cashPay() {
//        if (Validations.getBigDecimalOrZeroFromString(txtPaidAmount.getText().trim()).compareTo(Validations.getBigDecimalOrZeroFromString(invTotal)) == 1) {
//            InvoiceCashPay = Validations.getBigDecimalOrZeroFromString(invTotal);
//        } else {
//            InvoiceCashPay = Validations.getBigDecimalOrZeroFromString(txtPaidAmount.getText().trim());
//        }
//        return InvoiceCashPay;
//    }
    public int GetInstallments() {
        return Validations.getIntOrZeroFromString(comboInstallments.getSelectedItem().toString());
    }

    public BigDecimal GetInstallmentValue() {
        return Validations.getBigDecimalOrZeroFromString(txtInstallmentValue.getText().trim());
    }

    public BigDecimal GetInterestRate() {
        return Validations.getBigDecimalOrZeroFromString(txtInterestRate.getText().trim());
    }

    public BigDecimal GetDocumentCharge() {
        return Validations.getBigDecimalOrZeroFromString(txtDocumentCharge.getText().trim());
    }

    public BigDecimal GetChequePaymentValue() {
        return Validations.getBigDecimalOrZeroFromString(txtChequePay.getText().trim());
    }

    private void SetLanguageUi() {
        String FolderName = "mainmenu";
        if (Lang.equalsIgnoreCase(Options.LANG_ENGLISH)) {
            new Loading().CustomLangImage(FolderName, "cash", lblCash);
            new Loading().CustomLangImage(FolderName, "discount", lblDiscount);
            new Loading().CustomLangImage(FolderName, "after-discount-price", lblAfterDiscountTotal);
            new Loading().CustomLangImage(FolderName, "balance", lblBalance);
        } else if (Lang.equalsIgnoreCase(Options.LANG_SINHALA)) {
            new Loading().CustomLangImage(FolderName, "cash-sinhala", lblCash);
            new Loading().CustomLangImage(FolderName, "discount-sinhala", lblDiscount);
            new Loading().CustomLangImage(FolderName, "after-discount-price-sinhala", lblAfterDiscountTotal);
            new Loading().CustomLangImage(FolderName, "balance-sinhala", lblBalance);
        }
    }

    private void CalculateInstallmentValue() {

        BigDecimal Interest = (new BigDecimal("100").add(new BigDecimal((txtInterestRate.getText().trim())))
                .divide(new BigDecimal("100")));

        BigDecimal TotalPayable = ((Validations.getBigDecimalOrZeroFromString(txtBalance.getText().trim())
                .multiply(new BigDecimal("-1"))).multiply(Interest))
                .add(Validations.getBigDecimalOrZeroFromString(txtDocumentCharge.getText().trim()));

        BigDecimal InstallmentValue = TotalPayable.divide(Validations.getBigDecimalOrZeroFromString(comboInstallments.getSelectedItem().toString()));

        txtInstallmentValue.setText(Validations.formatWithTwoDigits(InstallmentValue.toString()));
    }

    private void CustomizeUi() {
        MainPanel.remove(lblCustomerName);
        MainPanel.remove(lblRs);
        MainPanel.remove(lblInvoiceTotal);
        MainPanel.remove(txtDate);
        MainPanel.remove(lblCash);
        MainPanel.remove(lblDiscount);
        MainPanel.remove(txtPaidAmountCash);
        MainPanel.remove(txtDiscountValue);
        MainPanel.remove(lblDiscRateRs);
        MainPanel.remove(lblAfterDiscountTotal);
        MainPanel.remove(txtAfterDiscount);
        MainPanel.remove(lblBalance);
        MainPanel.remove(txtBalance);
        MainPanel.remove(lblInterestRate);
        MainPanel.remove(lblDocCharge);
        MainPanel.remove(txtInterestRate);
        MainPanel.remove(txtDocumentCharge);
        MainPanel.remove(lblInstallments);
        MainPanel.remove(lblInstallmentValue);
        MainPanel.remove(comboInstallments);
        MainPanel.remove(txtInstallmentValue);
        MainPanel.remove(lblChequepay);
        MainPanel.remove(txtChequePay);
        MainPanel.remove(btnChequeList);

        MainPanel.add(lblCustomerName, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 500, 40));
        MainPanel.add(lblRs, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 50, 50, 45));
        MainPanel.add(lblInvoiceTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 50, 390, 45));
        MainPanel.add(txtDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(39, 128, 270, 38));
        MainPanel.add(lblCash, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 100, 210, -1));
        MainPanel.add(txtPaidAmountCash, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 128, 210, 40));
        MainPanel.add(lblAfterDiscountTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(39, 250, 224, -1));
        MainPanel.add(txtAfterDiscount, new org.netbeans.lib.awtextra.AbsoluteConstraints(39, 281, 500, 40));
        MainPanel.add(lblBalance, new org.netbeans.lib.awtextra.AbsoluteConstraints(39, 333, 226, -1));
        MainPanel.add(txtBalance, new org.netbeans.lib.awtextra.AbsoluteConstraints(39, 359, 500, -1));
        MainPanel.add(lblChequepay, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 170, 210, 30));
        MainPanel.add(txtChequePay, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 200, 210, 40));
//        MainPanel.add(btnChequeList, new org.netbeans.lib.awtextra.AbsoluteConstraints(253, 200, 50, 40));

        if (Loading.isEnableDiscountForTotalInvoiceByValue()) {
            MainPanel.add(lblDiscount, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 176, 100, -1));
            MainPanel.add(txtDiscountValue, new org.netbeans.lib.awtextra.AbsoluteConstraints(331, 200, 160, 40));
            MainPanel.add(lblDiscRateRs, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 200, 39, 40));
        }

        if (Loading.isEnableHirePurchase()) {
            MainPanel.add(lblInterestRate, new org.netbeans.lib.awtextra.AbsoluteConstraints(39, 410, 200, 30));
            MainPanel.add(lblDocCharge, new org.netbeans.lib.awtextra.AbsoluteConstraints(257, 410, 282, 30));
            MainPanel.add(txtInterestRate, new org.netbeans.lib.awtextra.AbsoluteConstraints(39, 446, 200, 40));
            MainPanel.add(txtDocumentCharge, new org.netbeans.lib.awtextra.AbsoluteConstraints(257, 446, 282, 40));
            MainPanel.add(lblInstallments, new org.netbeans.lib.awtextra.AbsoluteConstraints(39, 498, 260, 30));
            MainPanel.add(lblInstallmentValue, new org.netbeans.lib.awtextra.AbsoluteConstraints(317, 498, 222, 30));
            MainPanel.add(comboInstallments, new org.netbeans.lib.awtextra.AbsoluteConstraints(39, 534, 260, 40));
            MainPanel.add(txtInstallmentValue, new org.netbeans.lib.awtextra.AbsoluteConstraints(317, 534, 222, 40));
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

        MainPanel = new javax.swing.JPanel();
        lblRs = new javax.swing.JLabel();
        lblInvoiceTotal = new javax.swing.JLabel();
        txtDate = new com.toedter.calendar.JDateChooser();
        lblCash = new javax.swing.JLabel();
        txtPaidAmountCash = new javax.swing.JTextField();
        lblDiscount = new javax.swing.JLabel();
        txtDiscountValue = new javax.swing.JTextField();
        lblDiscRateRs = new javax.swing.JLabel();
        lblAfterDiscountTotal = new javax.swing.JLabel();
        txtAfterDiscount = new javax.swing.JTextField();
        lblBalance = new javax.swing.JLabel();
        txtBalance = new javax.swing.JTextField();
        txtInstallmentValue = new javax.swing.JTextField();
        lblInstallments = new javax.swing.JLabel();
        txtDocumentCharge = new javax.swing.JTextField();
        txtInterestRate = new javax.swing.JTextField();
        lblInterestRate = new javax.swing.JLabel();
        lblDocCharge = new javax.swing.JLabel();
        lblInstallmentValue = new javax.swing.JLabel();
        comboInstallments = new javax.swing.JComboBox<>();
        btnSave = new javax.swing.JButton();
        lblCustomerName = new javax.swing.JLabel();
        txtChequePay = new javax.swing.JTextField();
        lblChequepay = new javax.swing.JLabel();
        btnChequeList = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Invoice Payment");
        setMinimumSize(new java.awt.Dimension(334, 694));
        setResizable(false);
        setSize(new java.awt.Dimension(334, 694));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        MainPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblRs.setFont(new java.awt.Font("Ubuntu Medium", 1, 30)); // NOI18N
        lblRs.setForeground(new java.awt.Color(0, 0, 102));
        lblRs.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblRs.setText("Rs.");
        lblRs.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        MainPanel.add(lblRs, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 50, 50, 45));

        lblInvoiceTotal.setFont(new java.awt.Font("Ubuntu Medium", 1, 48)); // NOI18N
        lblInvoiceTotal.setForeground(new java.awt.Color(0, 0, 102));
        lblInvoiceTotal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblInvoiceTotal.setText("0.00");
        MainPanel.add(lblInvoiceTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 50, 390, 45));

        txtDate.setToolTipText("Date");
        txtDate.setDateFormatString("yyyy-MM-dd");
        txtDate.setEnabled(false);
        txtDate.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        MainPanel.add(txtDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(39, 128, 270, 38));

        lblCash.setFont(new java.awt.Font("Ubuntu Medium", 0, 18)); // NOI18N
        lblCash.setForeground(new java.awt.Color(0, 0, 102));
        lblCash.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cloudrevel/language/src/mainmenu/cash-sinhala.png"))); // NOI18N
        MainPanel.add(lblCash, new org.netbeans.lib.awtextra.AbsoluteConstraints(335, 100, 210, -1));

        txtPaidAmountCash.setFont(new java.awt.Font("Ubuntu Medium", 0, 19)); // NOI18N
        txtPaidAmountCash.setForeground(new java.awt.Color(0, 0, 102));
        txtPaidAmountCash.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtPaidAmountCash.setToolTipText("Paid Amount");
        txtPaidAmountCash.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPaidAmountCashActionPerformed(evt);
            }
        });
        txtPaidAmountCash.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPaidAmountCashKeyReleased(evt);
            }
        });
        MainPanel.add(txtPaidAmountCash, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 130, 210, 40));

        lblDiscount.setFont(new java.awt.Font("Ubuntu Medium", 0, 18)); // NOI18N
        lblDiscount.setForeground(new java.awt.Color(0, 0, 102));
        lblDiscount.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cloudrevel/language/src/mainmenu/discount-sinhala.png"))); // NOI18N
        MainPanel.add(lblDiscount, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 180, 120, -1));

        txtDiscountValue.setFont(new java.awt.Font("Ubuntu Medium", 0, 19)); // NOI18N
        txtDiscountValue.setForeground(new java.awt.Color(0, 0, 102));
        txtDiscountValue.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtDiscountValue.setToolTipText("Discount");
        txtDiscountValue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDiscountValueActionPerformed(evt);
            }
        });
        txtDiscountValue.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtDiscountValueKeyReleased(evt);
            }
        });
        MainPanel.add(txtDiscountValue, new org.netbeans.lib.awtextra.AbsoluteConstraints(331, 200, 160, 40));

        lblDiscRateRs.setFont(new java.awt.Font("Ubuntu Medium", 0, 18)); // NOI18N
        lblDiscRateRs.setForeground(new java.awt.Color(0, 0, 102));
        lblDiscRateRs.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblDiscRateRs.setText("(Rs)");
        MainPanel.add(lblDiscRateRs, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 200, 39, 40));

        lblAfterDiscountTotal.setFont(new java.awt.Font("Ubuntu Medium", 0, 18)); // NOI18N
        lblAfterDiscountTotal.setForeground(new java.awt.Color(0, 0, 102));
        lblAfterDiscountTotal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cloudrevel/language/src/mainmenu/after-discount-price-sinhala.png"))); // NOI18N
        MainPanel.add(lblAfterDiscountTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 260, 224, -1));

        txtAfterDiscount.setEditable(false);
        txtAfterDiscount.setFont(new java.awt.Font("Ubuntu Medium", 0, 19)); // NOI18N
        txtAfterDiscount.setForeground(new java.awt.Color(0, 0, 102));
        txtAfterDiscount.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtAfterDiscount.setToolTipText("After Discount Total");
        txtAfterDiscount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAfterDiscountActionPerformed(evt);
            }
        });
        txtAfterDiscount.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtAfterDiscountKeyReleased(evt);
            }
        });
        MainPanel.add(txtAfterDiscount, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 290, 500, 40));

        lblBalance.setFont(new java.awt.Font("Ubuntu Medium", 0, 18)); // NOI18N
        lblBalance.setForeground(new java.awt.Color(0, 0, 102));
        lblBalance.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cloudrevel/language/src/mainmenu/balance-sinhala.png"))); // NOI18N
        MainPanel.add(lblBalance, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 340, 226, -1));

        txtBalance.setEditable(false);
        txtBalance.setFont(new java.awt.Font("Ubuntu", 1, 26)); // NOI18N
        txtBalance.setForeground(new java.awt.Color(0, 0, 102));
        txtBalance.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtBalance.setText("0.00");
        txtBalance.setToolTipText("Balance");
        txtBalance.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBalanceActionPerformed(evt);
            }
        });
        txtBalance.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBalanceKeyReleased(evt);
            }
        });
        MainPanel.add(txtBalance, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 360, 500, -1));

        txtInstallmentValue.setEditable(false);
        txtInstallmentValue.setFont(new java.awt.Font("Ubuntu Medium", 0, 19)); // NOI18N
        txtInstallmentValue.setForeground(new java.awt.Color(0, 0, 102));
        txtInstallmentValue.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        MainPanel.add(txtInstallmentValue, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 540, 222, 40));

        lblInstallments.setFont(new java.awt.Font("Ubuntu Medium", 0, 19)); // NOI18N
        lblInstallments.setForeground(new java.awt.Color(0, 0, 102));
        lblInstallments.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblInstallments.setText("Installments");
        MainPanel.add(lblInstallments, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 500, 260, 30));

        txtDocumentCharge.setFont(new java.awt.Font("Ubuntu Medium", 0, 19)); // NOI18N
        txtDocumentCharge.setForeground(new java.awt.Color(0, 0, 102));
        txtDocumentCharge.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        MainPanel.add(txtDocumentCharge, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 450, 282, 40));

        txtInterestRate.setFont(new java.awt.Font("Ubuntu Medium", 0, 19)); // NOI18N
        txtInterestRate.setForeground(new java.awt.Color(0, 0, 102));
        txtInterestRate.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        MainPanel.add(txtInterestRate, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 450, 200, 40));

        lblInterestRate.setFont(new java.awt.Font("Ubuntu Medium", 0, 19)); // NOI18N
        lblInterestRate.setForeground(new java.awt.Color(0, 0, 102));
        lblInterestRate.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblInterestRate.setText("Interest Rate");
        MainPanel.add(lblInterestRate, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 410, 200, 30));

        lblDocCharge.setFont(new java.awt.Font("Ubuntu Medium", 0, 19)); // NOI18N
        lblDocCharge.setForeground(new java.awt.Color(0, 0, 102));
        lblDocCharge.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblDocCharge.setText("Document Charge");
        MainPanel.add(lblDocCharge, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 410, 282, 30));

        lblInstallmentValue.setFont(new java.awt.Font("Ubuntu Medium", 0, 19)); // NOI18N
        lblInstallmentValue.setForeground(new java.awt.Color(0, 0, 102));
        lblInstallmentValue.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblInstallmentValue.setText("Installment Value");
        MainPanel.add(lblInstallmentValue, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 510, 222, 30));

        comboInstallments.setFont(new java.awt.Font("Ubuntu Medium", 0, 19)); // NOI18N
        comboInstallments.setForeground(new java.awt.Color(0, 0, 102));
        comboInstallments.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30" }));
        comboInstallments.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
                comboInstallmentsPopupMenuWillBecomeVisible(evt);
            }
        });
        comboInstallments.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboInstallmentsActionPerformed(evt);
            }
        });
        MainPanel.add(comboInstallments, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 540, 260, 40));

        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cazzendra/pos/newtheme/icons/cash-receiving-32.png"))); // NOI18N
        btnSave.setToolTipText("Add Payment");
        btnSave.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSave.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                btnSaveFocusGained(evt);
            }
        });
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });
        MainPanel.add(btnSave, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 620, 500, 40));

        lblCustomerName.setFont(new java.awt.Font("Ubuntu Medium", 1, 24)); // NOI18N
        lblCustomerName.setForeground(new java.awt.Color(0, 0, 102));
        lblCustomerName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCustomerName.setText("Customer");
        MainPanel.add(lblCustomerName, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 500, 40));

        txtChequePay.setFont(new java.awt.Font("Ubuntu Medium", 0, 19)); // NOI18N
        txtChequePay.setForeground(new java.awt.Color(0, 0, 102));
        txtChequePay.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtChequePay.setToolTipText("Paid Amount");
        txtChequePay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtChequePayActionPerformed(evt);
            }
        });
        txtChequePay.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtChequePayKeyReleased(evt);
            }
        });
        MainPanel.add(txtChequePay, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 200, 210, 40));

        lblChequepay.setFont(new java.awt.Font("Ubuntu Medium", 0, 19)); // NOI18N
        lblChequepay.setForeground(new java.awt.Color(0, 0, 102));
        lblChequepay.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblChequepay.setText("Cheque Pay");
        MainPanel.add(lblChequepay, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 170, 210, 30));

        btnChequeList.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cazzendra/pos/newtheme/icons/add-folder-32.png"))); // NOI18N
        btnChequeList.setToolTipText("Add Cheques");
        btnChequeList.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnChequeList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChequeListActionPerformed(evt);
            }
        });
        MainPanel.add(btnChequeList, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 200, 50, 40));

        getContentPane().add(MainPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 580, 700));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtPaidAmountCashActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPaidAmountCashActionPerformed
        txtDiscountValue.requestFocus();
    }//GEN-LAST:event_txtPaidAmountCashActionPerformed

    private void txtPaidAmountCashKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPaidAmountCashKeyReleased
        calculateDiscount();
        calculatePaidAmount();
    }//GEN-LAST:event_txtPaidAmountCashKeyReleased

    private void txtAfterDiscountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAfterDiscountActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAfterDiscountActionPerformed

    private void txtBalanceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBalanceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBalanceActionPerformed

    private void txtBalanceKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBalanceKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBalanceKeyReleased

    private void txtAfterDiscountKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAfterDiscountKeyReleased
    }//GEN-LAST:event_txtAfterDiscountKeyReleased

    private void txtDiscountValueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDiscountValueActionPerformed
        btnSave.requestFocus();
    }//GEN-LAST:event_txtDiscountValueActionPerformed

    private void txtDiscountValueKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDiscountValueKeyReleased
        calculateDiscount();
        calculatePaidAmount();
    }//GEN-LAST:event_txtDiscountValueKeyReleased

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        try {
            calculateDiscount();
            calculatePaidAmount();
            status = InvoicePaymentControl.addInvoicePayment(invTotal, txtDiscountValue.getText(),
                    txtAfterDiscount.getText(), txtPaidAmountCash.getText(), invoiceNumber, custId, txtChequePay.getText().trim());

            if (!txtPaidAmountCash.getText().trim().equalsIgnoreCase("")
                    && (txtPaidAmountCash.getText().trim() != null)
                    && (Validations.getBigDecimalOrZeroFromString(txtPaidAmountCash.getText()).compareTo(BigDecimal.ZERO) == 1)) {
                PaymentControl.AddPaymentRecord(Payment.CASH,
                        Validations.getBigDecimalOrZeroFromString(txtPaidAmountCash.getText()),
                        CommonController.getCurrentJavaSqlDate(),
                        invoiceNumber, custId, "", "", 1);
            }
            if (!txtChequePay.getText().trim().equalsIgnoreCase("")
                    && (txtChequePay.getText().trim() != null)
                    && (Validations.getBigDecimalOrZeroFromString(txtChequePay.getText()).compareTo(BigDecimal.ZERO) == 1)) {
                PaymentControl.AddPaymentRecord(Payment.CHEQUE,
                        Validations.getBigDecimalOrZeroFromString(txtChequePay.getText()),
                        CommonController.getCurrentJavaSqlDate(),
                        invoiceNumber, custId, "", "", 1);
            }

            if (status == MethodStatus.SUCCESS) {
                this.dispose();
            }
        } catch (SQLException ex) {
            Logger.getLogger(InvoicePayment.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void comboInstallmentsPopupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_comboInstallmentsPopupMenuWillBecomeVisible

    }//GEN-LAST:event_comboInstallmentsPopupMenuWillBecomeVisible

    private void comboInstallmentsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboInstallmentsActionPerformed
        txtInstallmentValue.setText(Validations.formatWithTwoDigits(Validations.getBigDecimalOrZeroFromString(txtBalance.getText().trim())
                .divide(Validations.getBigDecimalOrZeroFromString(comboInstallments.getSelectedItem().toString()))
                .multiply(new BigDecimal("-1")).toString()));
        CalculateInstallmentValue();
    }//GEN-LAST:event_comboInstallmentsActionPerformed

    private void txtChequePayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtChequePayActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtChequePayActionPerformed

    private void txtChequePayKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtChequePayKeyReleased
        calculateDiscount();
        calculatePaidAmount();
    }//GEN-LAST:event_txtChequePayKeyReleased

    private void btnSaveFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnSaveFocusGained
        Loading.customJButton(255, 0, 0, btnSave);
    }//GEN-LAST:event_btnSaveFocusGained

    private void btnChequeListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChequeListActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnChequeListActionPerformed

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
            java.util.logging.Logger.getLogger(InvoicePayment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(InvoicePayment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InvoicePayment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InvoicePayment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                InvoicePayment dialog = new InvoicePayment(new javax.swing.JFrame(), true, 1, "", 0, "");
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
    private javax.swing.JPanel MainPanel;
    private javax.swing.JButton btnChequeList;
    private javax.swing.JButton btnSave;
    private javax.swing.JComboBox<String> comboInstallments;
    private javax.swing.JLabel lblAfterDiscountTotal;
    private javax.swing.JLabel lblBalance;
    private javax.swing.JLabel lblCash;
    private javax.swing.JLabel lblChequepay;
    private javax.swing.JLabel lblCustomerName;
    private javax.swing.JLabel lblDiscRateRs;
    private javax.swing.JLabel lblDiscount;
    private javax.swing.JLabel lblDocCharge;
    private javax.swing.JLabel lblInstallmentValue;
    private javax.swing.JLabel lblInstallments;
    private javax.swing.JLabel lblInterestRate;
    private javax.swing.JLabel lblInvoiceTotal;
    private javax.swing.JLabel lblRs;
    private javax.swing.JTextField txtAfterDiscount;
    private javax.swing.JTextField txtBalance;
    private javax.swing.JTextField txtChequePay;
    private com.toedter.calendar.JDateChooser txtDate;
    private javax.swing.JTextField txtDiscountValue;
    private javax.swing.JTextField txtDocumentCharge;
    private javax.swing.JTextField txtInstallmentValue;
    private javax.swing.JTextField txtInterestRate;
    private javax.swing.JTextField txtPaidAmountCash;
    // End of variables declaration//GEN-END:variables
}
