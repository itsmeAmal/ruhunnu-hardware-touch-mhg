/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cazzendra.pos.ui;

import cazzendra.pos.control.CommonController;
import cazzendra.pos.control.CustomerControl;
import cazzendra.pos.control.InvoiceControl;
import cazzendra.pos.control.InvoiceDetailControl;
import cazzendra.pos.control.ItemControl;
import cazzendra.pos.core.Loading;
import cazzendra.pos.core.MethodStatus;
import cazzendra.pos.core.Options;
import cazzendra.pos.core.Validations;
import cazzendra.pos.daoImpl.InvoiceDaoImpl;
import cazzendra.pos.model.Customer;
import cazzendra.pos.model.Item;
import cloudrevel.lnguage.src.keys.LangSinhala;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.AbstractAction;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRException;

public class Invoice extends javax.swing.JFrame {

    private Item item;
    int selectedCustId = 0;
    Customer customer = new Customer();
    private String Lang;
    private String PrintLanguage;

    public Invoice(String Language) {
        initComponents();
        setDefaults();
        panel.setBackground(Loading.getColorCode());
        HotKeys();
        this.Lang = Language;
        SetLanguageUi();

        lblPrintLanguage.setVisible(false);
        comboPrintLanguage.setVisible(false);
    }

    private void HotKeys() {
        KeyStroke ks = KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0);
        InputMap im = btnItemSearch.getInputMap(JButton.WHEN_IN_FOCUSED_WINDOW);
        im.put(ks, "C");
        btnItemSearch.getActionMap().put("C", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                itemSearchAndSetDetails();
            }
        });
        KeyStroke ab = KeyStroke.getKeyStroke(KeyEvent.VK_ADD, 0);
        InputMap inputMapb = btnAddInvoice.getInputMap(JButton.WHEN_IN_FOCUSED_WINDOW);
        inputMapb.put(ab, "B");
        btnAddInvoice.getActionMap().put("B", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveAction();
            }
        });
        KeyStroke abb = KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0);
        InputMap inputMapc = btnAddInvoice.getInputMap(JButton.WHEN_IN_FOCUSED_WINDOW);
        inputMapc.put(abb, "A");
        btnAddInvoice.getActionMap().put("A", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                customerSearch();
            }
        });
    }

    private void setDefaults() {
        tblInvoice.getTableHeader().setVisible(false);
        tblInvoice.getTableHeader().removeAll();

        txtItemQty.setText("0");
        lblInvoiceDiscountedTotal.setText("0.00");
        txtItemCode.requestFocus();
        //---------button colr setting------------
        btnItemSearch.setContentAreaFilled(false);
        btnItemSearch.setOpaque(true);
        btnItemSearch.setBackground(new java.awt.Color(0, 0, 102));

        btnAddItem.setContentAreaFilled(false);
        btnAddItem.setOpaque(true);
        btnAddItem.setBackground(new java.awt.Color(0, 0, 102));

        btnAddInvoice.setContentAreaFilled(false);
        btnAddInvoice.setOpaque(true);
        btnAddInvoice.setBackground(new java.awt.Color(0, 0, 102));

        btnDeleteItem.setContentAreaFilled(false);
        btnDeleteItem.setOpaque(true);
        btnDeleteItem.setBackground(new java.awt.Color(0, 0, 102));

        comboUnit.setOpaque(true);
        comboUnit.setBackground(new java.awt.Color(0, 0, 102));

        List<JButton> btnList = new ArrayList<>();
        btnList.add(btnItemSearch);
        btnList.add(btnAddItem);
        btnList.add(btnAddInvoice);
        btnList.add(btnDeleteItem);
        btnList.add(btnCustomerSearch);

        Loading.customJButtonList(Loading.getColorR(), Loading.getColorG(), Loading.getColorB(), btnList);
    }

    private void itemSearchAndSetDetails() {
        try {
            ItemSearch itemSearch = new ItemSearch(this, true, Lang);
            itemSearch.setVisible(true);
            int itemId = itemSearch.selectedItem();
            if (itemId != 0) {
                try {
                    CommonController.beepAlart();
                } catch (LineUnavailableException | UnsupportedAudioFileException | IOException ex) {
                    Logger.getLogger(Invoice.class.getName()).log(Level.SEVERE, null, ex);
                }

                item = ItemControl.getItemByItemId(itemId);
                lblItemName.setText(item.getItemName());
                txtItemCode.setText(item.getBarcode());
                txtPurchasePrice.setText(item.getPurchasePrice().toString());
                txtRetailPrice.setText(item.getSellingPrice().toString());
                txtItemCode.requestFocus();
                txtItemCode.selectAll();
                lblAvailableQty.setText(CommonController.getCuttentStockByItemCode(item.getItemCode()).toString());
            }
        } catch (SQLException ex) {
            Logger.getLogger(Invoice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void itemSelectByBarcodeAndSetDetails() {
        try {
            if (ItemControl.getItemByItemBarcodeOrItemCode(txtItemCode.getText().trim()) == null) {
                txtItemCode.requestFocus();
                return;
            }
            item = ItemControl.getItemByItemBarcodeOrItemCode(txtItemCode.getText().trim());
            try {
                CommonController.beepAlart();
            } catch (LineUnavailableException | UnsupportedAudioFileException | IOException ex) {
                Logger.getLogger(Invoice.class.getName()).log(Level.SEVERE, null, ex);
            }
            lblItemName.setText(item.getItemName());
            txtItemCode.setText(item.getBarcode());
            txtPurchasePrice.setText(item.getPurchasePrice() == null ? BigDecimal.ZERO.toString() : item.getPurchasePrice().toString());
            txtRetailPrice.setText(item.getSellingPrice() == null ? BigDecimal.ZERO.toString() : item.getSellingPrice().toString());
            txtItemCode.requestFocus();
            txtItemCode.selectAll();
            lblAvailableQty.setText(CommonController.getCuttentStockByItemCode(item.getItemCode()).toString());
        } catch (SQLException ex) {
            Logger.getLogger(Invoice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void addItemToTable() {
        BigDecimal qtyBd = Validations.getBigDecimalOrZeroFromString(txtQty.getText());
        BigDecimal priceBd = Validations.getBigDecimalOrZeroFromString(txtRetailPrice.getText());
        BigDecimal total = qtyBd.multiply(priceBd);
        ItemControl.addRowToInvoiceTable(tblInvoice, Integer.toString(item.getItemId()),
                item.getItemName(), item.getItemCode(), item.getPurchasePrice().toString(),
                txtRetailPrice.getText(), txtQty.getText(), comboUnit.getSelectedItem().toString(),
                Validations.formatWithTwoDigits(total.toString()), "0", item.getSellingPrice().toString());
        item = null;
    }

    private void clearItemDetails() {
        txtItemCode.setText(null);
        txtQty.setText(null);
        txtPurchasePrice.setText(null);
        txtRetailPrice.setText(null);
        comboUnit.setSelectedIndex(0);
        lblItemName.setText("");
        lblAvailableQty.setText("");
        item = null;
    }

    private void InvoiceTotal() {
        BigDecimal grandTotal = BigDecimal.ZERO;
        for (int i = 0; i < tblInvoice.getRowCount(); i++) {
            BigDecimal itemTotal = Validations.getBigDecimalOrZeroFromString(tblInvoice.getValueAt(i, 8).toString());
            grandTotal = grandTotal.add(itemTotal);
        }
        lblInvoiceDiscountedTotal.setText(Validations.formatWithTwoDigits(grandTotal.toString()));
    }

    private void deleteRow() {
        int selectedRow = tblInvoice.getSelectedRow();
        if (selectedRow != -1) {
            DefaultTableModel dtm = (DefaultTableModel) tblInvoice.getModel();
            dtm.removeRow(selectedRow);
        } else {
            JOptionPane.showMessageDialog(this, "Select record to delete !", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void calculateRowCount() {
        DefaultTableModel dtm = (DefaultTableModel) tblInvoice.getModel();
        txtItemQty.setText(Integer.toString(dtm.getRowCount()));
    }

    private void clearAll() {
        txtItemQty.setText("0");
        lblInvoiceDiscountedTotal.setText("0.00");
        DefaultTableModel dtm = (DefaultTableModel) tblInvoice.getModel();
        dtm.setRowCount(0);
        clearItemDetails();
        lblItemName.setText("");
        comboCustomer.removeAllItems();
    }

    private void saveAction() {
        int invoiceNo = 0;
        DefaultTableModel dtm = (DefaultTableModel) tblInvoice.getModel();
        if (dtm.getRowCount() > 0) {
            int nextInvoiceId = 0;
            try {
                nextInvoiceId = new InvoiceDaoImpl().getnextInvoiceId();
            } catch (SQLException ex) {
                Logger.getLogger(Invoice.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (selectedCustId == 0) {
                JOptionPane.showMessageDialog(this, "Please select customer !");
                return;
            }
            InvoicePayment invoicePayment = new InvoicePayment(this, true, nextInvoiceId,
                    lblInvoiceDiscountedTotal.getText(), selectedCustId, Lang);
            invoicePayment.setVisible(true);
            MethodStatus status = null;
            status = invoicePayment.getStatus();
            txtItemCode.requestFocus();
            MethodStatus invoiceStatus = null;
            BigDecimal cashPay = invoicePayment.cashPay();
            if (status == MethodStatus.SUCCESS) {
                try {
                    invoiceNo = InvoiceControl.addInvoice(lblInvoiceDiscountedTotal.getText(), txtItemQty.getText(), "0",
                            cashPay.toString(), nextInvoiceId, customer.getId(), invoicePayment.GetInstallments(),
                            invoicePayment.GetInstallmentValue(), invoicePayment.GetInterestRate(),
                            invoicePayment.GetDocumentCharge(), BigDecimal.ZERO, invoicePayment.GetChequePaymentValue());
                } catch (SQLException ex) {
                    Logger.getLogger(Invoice.class.getName()).log(Level.SEVERE, null, ex);
                }
                for (int i = 0; i < tblInvoice.getRowCount(); i++) {
                    try {
                        invoiceStatus = InvoiceDetailControl.addInvoiceDetail(invoiceNo,
                                tblInvoice.getValueAt(i, 0).toString(),
                                tblInvoice.getValueAt(i, 6).toString(),
                                tblInvoice.getValueAt(i, 7).toString(),
                                tblInvoice.getValueAt(i, 9).toString(),
                                tblInvoice.getValueAt(i, 5).toString(),
                                "0", tblInvoice.getValueAt(i, 3).toString());
                    } catch (SQLException ex) {
                        Logger.getLogger(Invoice.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    try {
                        CommonController.addCurrentStockRecord(tblInvoice.getValueAt(i, 0).toString(), tblInvoice.getValueAt(i, 6).toString(), 2);
                    } catch (SQLException ex) {
                        Logger.getLogger(Invoice.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                printInvoice(Integer.toString(invoiceNo));
            }
            if (invoiceNo != 0) {
                clearAll();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Pls add at least one item !", "Error!", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void printInvoice(String invoiceNo) {
        try {
            CommonController.printInvoice(invoiceNo, comboPrintLanguage.getSelectedItem().toString());
        } catch (SQLException | JRException ex) {
            Logger.getLogger(Invoice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void customerSearch() {
        CustomerSearch custSearch = new CustomerSearch(this, true, Lang);
        custSearch.setVisible(true);
        selectedCustId = custSearch.selectedCustomer();
        comboCustomer.removeAllItems();
        if (selectedCustId != 0) {
            try {
                customer = CustomerControl.getCustomerObjectByCustId(selectedCustId);
                comboCustomer.addItem(customer.getCustomerName());
            } catch (SQLException ex) {
                Logger.getLogger(Invoice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void SetLanguageUi() {
        String FolderName = "mainmenu";
        if (Lang.equalsIgnoreCase(Options.LANG_ENGLISH)) {
            new Loading().CustomLangImage(FolderName, "invoice-large", lblInvoiceBig);
            new Loading().CustomLangImage(FolderName, "item-code", lblItemCode);
            new Loading().CustomLangImage(FolderName, "qty", lblQty);
            new Loading().CustomLangImage(FolderName, "last-purchase-price", lblLastPurchasePrice);
            new Loading().CustomLangImage(FolderName, "selling-price", lblSellingPrice);
            new Loading().CustomLangImage(FolderName, "available-qty", lblAvailableQtyName);
            new Loading().CustomLangImage(FolderName, "customer", lblCustomerName);
            new Loading().CustomLangImage(FolderName, "tbl-hdr-item-code", tblItemCode);
            new Loading().CustomLangImage(FolderName, "tbl-hdr-item-name", tblItemName);
            new Loading().CustomLangImage(FolderName, "tbl-hdr-selling-price", tblSellingPrice);
            new Loading().CustomLangImage(FolderName, "qty-tbl", tblQty);
            new Loading().CustomLangImage(FolderName, "unit", tblUnit);
            new Loading().CustomLangImage(FolderName, "tbl-hdr-total", tblTotal);
            new Loading().CustomLangImage(FolderName, "invoice-total-large", lblInvoiceTotal);
            new Loading().CustomLangImage(FolderName, "item-qty", lblItemQty);
            new Loading().CustomLangImage(FolderName, LangSinhala.LBL_PRINT_LANGUAGE, lblPrintLanguage);

        } else if (Lang.equalsIgnoreCase(Options.LANG_SINHALA)) {
            new Loading().CustomLangImage(FolderName, "invoice-large-sinhala", lblInvoiceBig);
            new Loading().CustomLangImage(FolderName, "item-code-sinhala", lblItemCode);
            new Loading().CustomLangImage(FolderName, "qty-sinhala", lblQty);
            new Loading().CustomLangImage(FolderName, "last-purchase-price-sinhala", lblLastPurchasePrice);
            new Loading().CustomLangImage(FolderName, "selling-price-sinhala", lblSellingPrice);
            new Loading().CustomLangImage(FolderName, "available-qty-sinhala", lblAvailableQtyName);
            new Loading().CustomLangImage(FolderName, "customer-sinhala", lblCustomerName);
            new Loading().CustomLangImage(FolderName, "tbl-hdr-item-code-sinhala", tblItemCode);
            new Loading().CustomLangImage(FolderName, "tbl-hdr-item-name-sinhala", tblItemName);
            new Loading().CustomLangImage(FolderName, "tbl-hdr-selling-price-sinhala", tblSellingPrice);
            new Loading().CustomLangImage(FolderName, "qty-tbl-sinhala", tblQty);
            new Loading().CustomLangImage(FolderName, "unit-sinhala", tblUnit);
            new Loading().CustomLangImage(FolderName, "tbl-hdr-total-sinhala", tblTotal);
            new Loading().CustomLangImage(FolderName, "invoice-total-large-sinhala", lblInvoiceTotal);
            new Loading().CustomLangImage(FolderName, "item-qty-sinhala", lblItemQty);
            new Loading().CustomLangImage(FolderName, LangSinhala.LBL_PRINT_LANGUAGE_SINHALA, lblPrintLanguage);
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
        jPanel2 = new javax.swing.JPanel();
        tblItemCode = new javax.swing.JLabel();
        tblSellingPrice = new javax.swing.JLabel();
        tblTotal = new javax.swing.JLabel();
        tblUnit = new javax.swing.JLabel();
        tblQty = new javax.swing.JLabel();
        tblItemName = new javax.swing.JLabel();
        txtPurchasePrice = new javax.swing.JTextField();
        txtItemCode = new javax.swing.JTextField();
        txtRetailPrice = new javax.swing.JTextField();
        txtQty = new javax.swing.JTextField();
        comboUnit = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblInvoice = new javax.swing.JTable();
        btnItemSearch = new javax.swing.JButton();
        lblItemName = new javax.swing.JLabel();
        lblLastPurchasePrice = new javax.swing.JLabel();
        txtItemQty = new javax.swing.JTextField();
        btnAddInvoice = new javax.swing.JButton();
        btnAddItem = new javax.swing.JButton();
        lblAvailableQty = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        lblInvoiceDiscountedTotal = new javax.swing.JLabel();
        lblInvoiceTotal = new javax.swing.JLabel();
        lblItemQty = new javax.swing.JLabel();
        lblSellingPrice = new javax.swing.JLabel();
        lblItemCode = new javax.swing.JLabel();
        comboCustomer = new javax.swing.JComboBox<>();
        btnCustomerSearch = new javax.swing.JButton();
        lblQty = new javax.swing.JLabel();
        lblCustomerName = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        lblInvoiceBig = new javax.swing.JLabel();
        lblPrintLanguage = new javax.swing.JLabel();
        comboPrintLanguage = new javax.swing.JComboBox<>();
        btnDeleteItem = new javax.swing.JButton();
        lblAvailableQtyName = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Invoice");
        setAlwaysOnTop(true);
        setMaximumSize(new java.awt.Dimension(1024, 694));
        setMinimumSize(new java.awt.Dimension(1024, 694));
        setResizable(false);
        setSize(new java.awt.Dimension(1024, 694));

        jPanel1.setMaximumSize(new java.awt.Dimension(1024, 694));
        jPanel1.setMinimumSize(new java.awt.Dimension(1024, 694));
        jPanel1.setPreferredSize(new java.awt.Dimension(1024, 694));

        panel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panel.setMaximumSize(new java.awt.Dimension(1360, 728));
        panel.setMinimumSize(new java.awt.Dimension(1360, 728));
        panel.setPreferredSize(new java.awt.Dimension(1360, 728));
        panel.setRequestFocusEnabled(false);
        panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(0, 0, 102));

        tblItemCode.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cloudrevel/language/src/mainmenu/tbl-hdr-item-code-sinhala.png"))); // NOI18N

        tblSellingPrice.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cloudrevel/language/src/mainmenu/tbl-hdr-selling-price-sinhala.png"))); // NOI18N

        tblTotal.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        tblTotal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cloudrevel/language/src/mainmenu/tbl-hdr-total-sinhala.png"))); // NOI18N

        tblUnit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cloudrevel/language/src/mainmenu/unit-sinhala.png"))); // NOI18N

        tblQty.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cloudrevel/language/src/mainmenu/qty-tbl-sinhala.png"))); // NOI18N

        tblItemName.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cloudrevel/language/src/mainmenu/tbl-hdr-item-name-sinhala.png"))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(tblItemCode, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tblItemName, javax.swing.GroupLayout.DEFAULT_SIZE, 365, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tblSellingPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tblQty, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tblUnit, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tblTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tblTotal, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
            .addComponent(tblSellingPrice, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(tblItemCode, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(tblUnit, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(tblQty, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(tblItemName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        panel.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 290, 1000, 35));

        txtPurchasePrice.setEditable(false);
        txtPurchasePrice.setBackground(new java.awt.Color(204, 204, 204));
        txtPurchasePrice.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        txtPurchasePrice.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        panel.add(txtPurchasePrice, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 140, 202, 40));

        txtItemCode.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        txtItemCode.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtItemCode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtItemCodeActionPerformed(evt);
            }
        });
        txtItemCode.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtItemCodeKeyReleased(evt);
            }
        });
        panel.add(txtItemCode, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, 230, 40));

        txtRetailPrice.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        txtRetailPrice.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtRetailPrice.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtRetailPriceFocusLost(evt);
            }
        });
        txtRetailPrice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtRetailPriceActionPerformed(evt);
            }
        });
        txtRetailPrice.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtRetailPriceKeyReleased(evt);
            }
        });
        panel.add(txtRetailPrice, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 140, 200, 40));

        txtQty.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        txtQty.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtQty.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtQtyFocusLost(evt);
            }
        });
        txtQty.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtQtyActionPerformed(evt);
            }
        });
        txtQty.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtQtyKeyReleased(evt);
            }
        });
        panel.add(txtQty, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 140, 160, 40));

        comboUnit.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        comboUnit.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pcs" }));
        comboUnit.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                comboUnitPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });
        comboUnit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                comboUnitKeyReleased(evt);
            }
        });
        panel.add(comboUnit, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 140, 65, 40));

        tblInvoice.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        tblInvoice.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Item Id", "Item Code", "Item Name", "Purchase Price", "Discount Rate", "Selling Price", "Qty", "Unit", "Total", "MRP"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblInvoice.setToolTipText("Invoice");
        tblInvoice.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tblInvoice.getTableHeader().setFont(new Font("Ubuntu", Font.BOLD, 18));
        tblInvoice.getTableHeader().setOpaque(false);
        tblInvoice.getTableHeader().setBackground(new Color(0, 0, 102));
        tblInvoice.getTableHeader().setForeground(new Color(255, 255, 255));
        tblInvoice.setRowHeight(22);
        jScrollPane1.setViewportView(tblInvoice);
        if (tblInvoice.getColumnModel().getColumnCount() > 0) {
            tblInvoice.getColumnModel().getColumn(0).setMinWidth(0);
            tblInvoice.getColumnModel().getColumn(0).setPreferredWidth(0);
            tblInvoice.getColumnModel().getColumn(0).setMaxWidth(0);
            tblInvoice.getColumnModel().getColumn(1).setMinWidth(150);
            tblInvoice.getColumnModel().getColumn(1).setPreferredWidth(150);
            tblInvoice.getColumnModel().getColumn(1).setMaxWidth(150);
            tblInvoice.getColumnModel().getColumn(3).setMinWidth(0);
            tblInvoice.getColumnModel().getColumn(3).setPreferredWidth(0);
            tblInvoice.getColumnModel().getColumn(3).setMaxWidth(0);
            tblInvoice.getColumnModel().getColumn(4).setMinWidth(0);
            tblInvoice.getColumnModel().getColumn(4).setPreferredWidth(0);
            tblInvoice.getColumnModel().getColumn(4).setMaxWidth(0);
            tblInvoice.getColumnModel().getColumn(5).setMinWidth(150);
            tblInvoice.getColumnModel().getColumn(5).setPreferredWidth(150);
            tblInvoice.getColumnModel().getColumn(5).setMaxWidth(150);
            tblInvoice.getColumnModel().getColumn(6).setMinWidth(50);
            tblInvoice.getColumnModel().getColumn(6).setPreferredWidth(50);
            tblInvoice.getColumnModel().getColumn(6).setMaxWidth(50);
            tblInvoice.getColumnModel().getColumn(7).setMinWidth(50);
            tblInvoice.getColumnModel().getColumn(7).setPreferredWidth(50);
            tblInvoice.getColumnModel().getColumn(7).setMaxWidth(50);
            tblInvoice.getColumnModel().getColumn(8).setMinWidth(200);
            tblInvoice.getColumnModel().getColumn(8).setPreferredWidth(200);
            tblInvoice.getColumnModel().getColumn(8).setMaxWidth(200);
            tblInvoice.getColumnModel().getColumn(9).setMinWidth(0);
            tblInvoice.getColumnModel().getColumn(9).setPreferredWidth(0);
            tblInvoice.getColumnModel().getColumn(9).setMaxWidth(0);
        }

        panel.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(8, 292, 1000, 310));

        btnItemSearch.setBackground(new java.awt.Color(0, 0, 102));
        btnItemSearch.setFont(new java.awt.Font("Ubuntu Medium", 1, 18)); // NOI18N
        btnItemSearch.setForeground(new java.awt.Color(255, 255, 255));
        btnItemSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cazzendra/pos/newtheme/icons/search-9-32.png"))); // NOI18N
        btnItemSearch.setToolTipText("Search");
        btnItemSearch.setBorder(null);
        btnItemSearch.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnItemSearch.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                btnItemSearchFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                btnItemSearchFocusLost(evt);
            }
        });
        btnItemSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnItemSearchActionPerformed(evt);
            }
        });
        btnItemSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                btnItemSearchKeyReleased(evt);
            }
        });
        panel.add(btnItemSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 140, 40, 40));

        lblItemName.setFont(new java.awt.Font("Ubuntu Medium", 1, 18)); // NOI18N
        lblItemName.setForeground(new java.awt.Color(0, 0, 102));
        lblItemName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        panel.add(lblItemName, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 190, 290, 30));

        lblLastPurchasePrice.setFont(new java.awt.Font("Ubuntu Medium", 0, 18)); // NOI18N
        lblLastPurchasePrice.setForeground(new java.awt.Color(0, 0, 102));
        lblLastPurchasePrice.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cloudrevel/language/src/mainmenu/last-purchase-price-sinhala.png"))); // NOI18N
        panel.add(lblLastPurchasePrice, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 110, 200, 20));

        txtItemQty.setEditable(false);
        txtItemQty.setBackground(new java.awt.Color(204, 204, 204));
        txtItemQty.setFont(new java.awt.Font("Ubuntu", 1, 24)); // NOI18N
        txtItemQty.setForeground(new java.awt.Color(255, 51, 51));
        txtItemQty.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        panel.add(txtItemQty, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 230, 140, 40));

        btnAddInvoice.setBackground(new java.awt.Color(0, 0, 102));
        btnAddInvoice.setFont(new java.awt.Font("Ubuntu Medium", 1, 18)); // NOI18N
        btnAddInvoice.setForeground(new java.awt.Color(255, 255, 255));
        btnAddInvoice.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cazzendra/pos/newtheme/icons/cash-receiving-32.png"))); // NOI18N
        btnAddInvoice.setToolTipText("Invoice Payment");
        btnAddInvoice.setBorder(null);
        btnAddInvoice.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAddInvoice.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                btnAddInvoiceFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                btnAddInvoiceFocusLost(evt);
            }
        });
        btnAddInvoice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddInvoiceActionPerformed(evt);
            }
        });
        panel.add(btnAddInvoice, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 230, 120, 40));

        btnAddItem.setBackground(new java.awt.Color(0, 0, 102));
        btnAddItem.setFont(new java.awt.Font("Ubuntu Medium", 1, 18)); // NOI18N
        btnAddItem.setForeground(new java.awt.Color(255, 255, 255));
        btnAddItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cazzendra/pos/newtheme/icons/add-folder-32.png"))); // NOI18N
        btnAddItem.setToolTipText("Add to table");
        btnAddItem.setBorder(null);
        btnAddItem.setBorderPainted(false);
        btnAddItem.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAddItem.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                btnAddItemFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                btnAddItemFocusLost(evt);
            }
        });
        btnAddItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddItemActionPerformed(evt);
            }
        });
        panel.add(btnAddItem, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 230, 40, 40));

        lblAvailableQty.setFont(new java.awt.Font("Ubuntu Medium", 1, 14)); // NOI18N
        lblAvailableQty.setForeground(new java.awt.Color(0, 0, 102));
        lblAvailableQty.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblAvailableQty.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 102)));
        panel.add(lblAvailableQty, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 230, 130, 40));

        jPanel3.setBackground(new java.awt.Color(0, 0, 102));
        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lblInvoiceDiscountedTotal.setBackground(new java.awt.Color(255, 255, 255));
        lblInvoiceDiscountedTotal.setFont(new java.awt.Font("Ubuntu Medium", 1, 48)); // NOI18N
        lblInvoiceDiscountedTotal.setForeground(new java.awt.Color(255, 255, 255));
        lblInvoiceDiscountedTotal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblInvoiceDiscountedTotal.setText("0.00");

        lblInvoiceTotal.setFont(new java.awt.Font("Ubuntu Medium", 0, 36)); // NOI18N
        lblInvoiceTotal.setForeground(new java.awt.Color(255, 255, 255));
        lblInvoiceTotal.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblInvoiceTotal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cloudrevel/language/src/mainmenu/invoice-total-large-sinhala.png"))); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(270, Short.MAX_VALUE)
                .addComponent(lblInvoiceTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 421, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblInvoiceDiscountedTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(lblInvoiceDiscountedTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(lblInvoiceTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        panel.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 618, 1000, -1));

        lblItemQty.setFont(new java.awt.Font("Ubuntu Medium", 0, 18)); // NOI18N
        lblItemQty.setForeground(new java.awt.Color(0, 0, 102));
        lblItemQty.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cloudrevel/language/src/mainmenu/item-qty-sinhala.png"))); // NOI18N
        panel.add(lblItemQty, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 200, 140, -1));

        lblSellingPrice.setFont(new java.awt.Font("Ubuntu Medium", 0, 18)); // NOI18N
        lblSellingPrice.setForeground(new java.awt.Color(0, 0, 102));
        lblSellingPrice.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cloudrevel/language/src/mainmenu/selling-price-sinhala.png"))); // NOI18N
        panel.add(lblSellingPrice, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 110, 190, -1));

        lblItemCode.setFont(new java.awt.Font("Ubuntu Medium", 0, 18)); // NOI18N
        lblItemCode.setForeground(new java.awt.Color(0, 0, 102));
        lblItemCode.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cloudrevel/language/src/mainmenu/item-code-sinhala.png"))); // NOI18N
        panel.add(lblItemCode, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 230, 25));

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
        panel.add(comboCustomer, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 230, 220, 40));

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
        btnCustomerSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                btnCustomerSearchKeyReleased(evt);
            }
        });
        panel.add(btnCustomerSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 230, 40, 40));

        lblQty.setFont(new java.awt.Font("Ubuntu Medium", 0, 18)); // NOI18N
        lblQty.setForeground(new java.awt.Color(0, 0, 102));
        lblQty.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cloudrevel/language/src/mainmenu/qty-sinhala.png"))); // NOI18N
        panel.add(lblQty, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 110, -1, 25));

        lblCustomerName.setFont(new java.awt.Font("Ubuntu Medium", 0, 18)); // NOI18N
        lblCustomerName.setForeground(new java.awt.Color(0, 0, 102));
        lblCustomerName.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cloudrevel/language/src/mainmenu/customer-sinhala.png"))); // NOI18N
        panel.add(lblCustomerName, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 200, -1, 20));

        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 102)));
        jPanel4.setOpaque(false);

        lblInvoiceBig.setFont(new java.awt.Font("Ubuntu Medium", 1, 36)); // NOI18N
        lblInvoiceBig.setForeground(new java.awt.Color(0, 0, 102));
        lblInvoiceBig.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cloudrevel/language/src/mainmenu/invoice-large-sinhala.png"))); // NOI18N

        lblPrintLanguage.setFont(new java.awt.Font("Ubuntu Medium", 0, 18)); // NOI18N
        lblPrintLanguage.setForeground(new java.awt.Color(0, 0, 102));
        lblPrintLanguage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cloudrevel/language/src/mainmenu/print-language-sinhala.png"))); // NOI18N

        comboPrintLanguage.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        comboPrintLanguage.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "English", "Sinhala" }));
        comboPrintLanguage.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                comboPrintLanguagePopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });
        comboPrintLanguage.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                comboPrintLanguageKeyReleased(evt);
            }
        });

        btnDeleteItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cazzendra/pos/newtheme/icons/delete-32.png"))); // NOI18N
        btnDeleteItem.setToolTipText("Remove Item");
        btnDeleteItem.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDeleteItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteItemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblInvoiceBig, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 538, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblPrintLanguage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(comboPrintLanguage, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDeleteItem, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(lblInvoiceBig))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnDeleteItem, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(lblPrintLanguage)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(comboPrintLanguage, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        panel.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 1000, -1));

        lblAvailableQtyName.setFont(new java.awt.Font("Ubuntu Medium", 0, 18)); // NOI18N
        lblAvailableQtyName.setForeground(new java.awt.Color(0, 0, 102));
        lblAvailableQtyName.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cloudrevel/language/src/mainmenu/available-qty-sinhala.png"))); // NOI18N
        panel.add(lblAvailableQtyName, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 240, 140, 20));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, 1024, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, 694, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnItemSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnItemSearchActionPerformed
        itemSearchAndSetDetails();
    }//GEN-LAST:event_btnItemSearchActionPerformed

    private void txtQtyKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtQtyKeyReleased
        BigDecimal qty = Validations.getBigDecimalOrZeroFromString(txtQty.getText());
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (qty.compareTo(BigDecimal.ZERO) == 1) {
                txtQty.setText(Validations.getBigDecimalOrZeroFromString(txtQty.getText()).toString());
                txtRetailPrice.requestFocus();
                txtRetailPrice.selectAll();
            }
        }
    }//GEN-LAST:event_txtQtyKeyReleased

    private void comboUnitPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_comboUnitPopupMenuWillBecomeInvisible

    }//GEN-LAST:event_comboUnitPopupMenuWillBecomeInvisible

    private void txtRetailPriceKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRetailPriceKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (item != null && Validations.isNotEmpty(txtQty.getText())
                    && Validations.isNotEmpty(txtRetailPrice.getText())) {
                addItemToTable();
                clearItemDetails();
                txtItemCode.requestFocus();
                InvoiceTotal();
            }
        }
        calculateRowCount();
    }//GEN-LAST:event_txtRetailPriceKeyReleased

    private void btnAddItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddItemActionPerformed
        if (Validations.isNotEmpty(txtItemCode.getText()) && Validations.isNotEmpty(txtQty.getText())
                && Validations.isNotEmpty(txtRetailPrice.getText())) {
            addItemToTable();
            clearItemDetails();
            txtItemCode.requestFocus();
            InvoiceTotal();
        }
    }//GEN-LAST:event_btnAddItemActionPerformed

    private void txtRetailPriceFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtRetailPriceFocusLost

    }//GEN-LAST:event_txtRetailPriceFocusLost

    private void txtRetailPriceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtRetailPriceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtRetailPriceActionPerformed

    private void comboUnitKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_comboUnitKeyReleased
        txtRetailPrice.requestFocus();
        txtRetailPrice.selectAll();
    }//GEN-LAST:event_comboUnitKeyReleased

    private void btnDeleteItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteItemActionPerformed
        deleteRow();
        InvoiceTotal();
        calculateRowCount();
    }//GEN-LAST:event_btnDeleteItemActionPerformed

    private void btnItemSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnItemSearchKeyReleased

    }//GEN-LAST:event_btnItemSearchKeyReleased

    private void txtQtyFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtQtyFocusLost

    }//GEN-LAST:event_txtQtyFocusLost

    private void txtQtyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtQtyActionPerformed

    }//GEN-LAST:event_txtQtyActionPerformed

    private void txtItemCodeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtItemCodeKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            itemSelectByBarcodeAndSetDetails();
            if (item != null) {
                txtQty.requestFocus();
            }
        }
    }//GEN-LAST:event_txtItemCodeKeyReleased

    private void btnAddInvoiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddInvoiceActionPerformed
        saveAction();
    }//GEN-LAST:event_btnAddInvoiceActionPerformed

    private void txtItemCodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtItemCodeActionPerformed
        if (item != null) {
            txtQty.requestFocus();
        }
    }//GEN-LAST:event_txtItemCodeActionPerformed

    private void comboCustomerPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_comboCustomerPopupMenuWillBecomeInvisible
        // TODO add your handling code here:
    }//GEN-LAST:event_comboCustomerPopupMenuWillBecomeInvisible

    private void comboCustomerKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_comboCustomerKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_comboCustomerKeyReleased

    private void btnCustomerSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCustomerSearchActionPerformed
        customerSearch();
    }//GEN-LAST:event_btnCustomerSearchActionPerformed

    private void btnCustomerSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnCustomerSearchKeyReleased
        Loading.customJButton(255, 0, 0, btnCustomerSearch);
    }//GEN-LAST:event_btnCustomerSearchKeyReleased

    private void comboPrintLanguagePopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_comboPrintLanguagePopupMenuWillBecomeInvisible
        // TODO add your handling code here:
    }//GEN-LAST:event_comboPrintLanguagePopupMenuWillBecomeInvisible

    private void comboPrintLanguageKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_comboPrintLanguageKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_comboPrintLanguageKeyReleased

    private void btnItemSearchFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnItemSearchFocusGained
        Loading.customJButton(255, 0, 0, btnItemSearch);
    }//GEN-LAST:event_btnItemSearchFocusGained

    private void btnAddItemFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnAddItemFocusGained
        Loading.customJButton(255, 0, 0, btnAddItem);
    }//GEN-LAST:event_btnAddItemFocusGained

    private void btnAddInvoiceFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnAddInvoiceFocusGained
        Loading.customJButton(255, 0, 0, btnAddInvoice);
    }//GEN-LAST:event_btnAddInvoiceFocusGained

    private void btnItemSearchFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnItemSearchFocusLost
        Loading.customJButton(Loading.getColorR(), Loading.getColorG(), Loading.getColorB(), btnItemSearch);
    }//GEN-LAST:event_btnItemSearchFocusLost

    private void btnAddItemFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnAddItemFocusLost
        Loading.customJButton(Loading.getColorR(), Loading.getColorG(), Loading.getColorB(), btnAddItem);
    }//GEN-LAST:event_btnAddItemFocusLost

    private void btnAddInvoiceFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnAddInvoiceFocusLost
        Loading.customJButton(Loading.getColorR(), Loading.getColorG(), Loading.getColorB(), btnAddInvoice);
    }//GEN-LAST:event_btnAddInvoiceFocusLost

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
            java.util.logging.Logger.getLogger(Invoice.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Invoice.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Invoice.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Invoice.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Invoice("").setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddInvoice;
    private javax.swing.JButton btnAddItem;
    private javax.swing.JButton btnCustomerSearch;
    private javax.swing.JButton btnDeleteItem;
    private javax.swing.JButton btnItemSearch;
    private javax.swing.JComboBox<String> comboCustomer;
    private javax.swing.JComboBox<String> comboPrintLanguage;
    private javax.swing.JComboBox<String> comboUnit;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblAvailableQty;
    private javax.swing.JLabel lblAvailableQtyName;
    private javax.swing.JLabel lblCustomerName;
    private javax.swing.JLabel lblInvoiceBig;
    private javax.swing.JLabel lblInvoiceDiscountedTotal;
    private javax.swing.JLabel lblInvoiceTotal;
    private javax.swing.JLabel lblItemCode;
    private javax.swing.JLabel lblItemName;
    private javax.swing.JLabel lblItemQty;
    private javax.swing.JLabel lblLastPurchasePrice;
    private javax.swing.JLabel lblPrintLanguage;
    private javax.swing.JLabel lblQty;
    private javax.swing.JLabel lblSellingPrice;
    private javax.swing.JPanel panel;
    private javax.swing.JTable tblInvoice;
    private javax.swing.JLabel tblItemCode;
    private javax.swing.JLabel tblItemName;
    private javax.swing.JLabel tblQty;
    private javax.swing.JLabel tblSellingPrice;
    private javax.swing.JLabel tblTotal;
    private javax.swing.JLabel tblUnit;
    private javax.swing.JTextField txtItemCode;
    private javax.swing.JTextField txtItemQty;
    private javax.swing.JTextField txtPurchasePrice;
    private javax.swing.JTextField txtQty;
    private javax.swing.JTextField txtRetailPrice;
    // End of variables declaration//GEN-END:variables
}
