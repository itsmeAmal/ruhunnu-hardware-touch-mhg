/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cazzendra.pos.ui;

import cazzendra.pos.control.CommonController;
import cazzendra.pos.control.GrnControl;
import cazzendra.pos.control.GrnDetailControl;
import cazzendra.pos.control.ItemControl;
import cazzendra.pos.core.Loading;
import cazzendra.pos.core.Validations;
import cazzendra.pos.model.Item;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.table.DefaultTableModel;

public class GRN extends javax.swing.JFrame {

    private Item item;
    private String Lang;

    public GRN(String Language) {
        initComponents();
        setDefaults();
        panel.setBackground(Loading.getColorCode());
        HotKeys();
        txtItemCode.requestFocus();
        this.Lang = Language;
    }

    private void HotKeys() {
        KeyStroke ks = KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0);
        InputMap im = btnItemSearch.getInputMap(JButton.WHEN_IN_FOCUSED_WINDOW);
        im.put(ks, "C");
        btnItemSearch.getActionMap().put("C", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                itemSearchAndSetDetails();
            }
        });
        KeyStroke ab = KeyStroke.getKeyStroke(KeyEvent.VK_ADD, 0);
        InputMap inputMapb = btnSave.getInputMap(JButton.WHEN_IN_FOCUSED_WINDOW);
        inputMapb.put(ab, "B");
        btnSave.getActionMap().put("B", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                saveAction();
            }
        });
    }

    private void setDefaults() {
        txtItemQty.setText("0");
        lblItemTotal.setText("0.00");
        txtPurchasePrice.setText("0.00");
        txtRetailPrice.setText("0.00");

        List<JButton> btnList = new ArrayList<>();
        btnList.add(btnItemSearch);
        btnList.add(btnDelete);
        btnList.add(btnAddItem);
        btnList.add(btnSave);
        Loading.customJButtonList(Loading.getColorR(), Loading.getColorG(), Loading.getColorB(), btnList);
    }

    private void itemSearchAndSetDetails() {
        try {
            ItemSearch itemSearch = new ItemSearch(this, true, Lang);
            itemSearch.setVisible(true);
            int itemId = itemSearch.selectedItem();
            if (itemId != 0) {
                item = ItemControl.getItemByItemId(itemId);
                lblItemName.setText(item.getItemName());
                txtItemCode.setText(item.getItemCode());
                txtPurchasePrice.setText(item.getPurchasePrice().toString());
                txtRetailPrice.setText(item.getSellingPrice().toString());
                txtItemCode.requestFocus();
                txtItemCode.selectAll();
                lblQty.setText(CommonController.getCuttentStockByItemCode(item.getItemCode()).toString());
            }
        } catch (SQLException ex) {
            Logger.getLogger(GRN.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void addItemToTable() {
        BigDecimal qtyBd = Validations.getBigDecimalOrZeroFromString(txtQty.getText());
        BigDecimal priceBd = Validations.getBigDecimalOrZeroFromString(txtPurchasePrice.getText());
        BigDecimal total = qtyBd.multiply(priceBd);
        GrnControl.addRowToGrnTable(tblItems, Integer.toString(item.getItemId()), String.valueOf(item.getItemCode()),
                item.getBarcode(), txtPurchasePrice.getText(), "0", txtRetailPrice.getText(), txtQty.getText(),
                (String) comboUnit.getSelectedItem(), total.toString(), item.getItemName());
    }

    private void clearItemDetails() {
        txtItemCode.setText(null);
        txtQty.setText(null);
        txtPurchasePrice.setText(null);
        txtRetailPrice.setText(null);
        comboUnit.setSelectedIndex(0);
        lblQty.setText("");
        lblItemName.setText("");
        item = null;
    }

    private void grnTotal() {
        BigDecimal grandTotal = BigDecimal.ZERO;
        for (int i = 0; i < tblItems.getRowCount(); i++) {
            BigDecimal itemTotal = Validations.getBigDecimalOrZeroFromString(tblItems.getValueAt(i, 9).toString());
            grandTotal = grandTotal.add(itemTotal);
        }
        lblItemTotal.setText(Validations.formatWithTwoDigits(grandTotal.toString()));
    }

    private void deleteRow() {
        int selectedRow = tblItems.getSelectedRow();
        if (selectedRow != -1) {
            DefaultTableModel dtm = (DefaultTableModel) tblItems.getModel();
            dtm.removeRow(selectedRow);
        } else {
            JOptionPane.showMessageDialog(this, "Select record to delete !", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void calculateRowCount() {
        DefaultTableModel dtm = (DefaultTableModel) tblItems.getModel();
        txtItemQty.setText(Integer.toString(dtm.getRowCount()));
    }

    private void clearAll() {
        txtItemQty.setText("0");
        lblItemTotal.setText("0.00");
        DefaultTableModel dtm = (DefaultTableModel) tblItems.getModel();
        dtm.setRowCount(0);
        txtItemCode.requestFocus();
        clearItemDetails();
    }

    private void saveAction() {
        try {
            int grnNo = 0;
            DefaultTableModel dtm = (DefaultTableModel) tblItems.getModel();
            if (dtm.getRowCount() > 0) {
                int option = JOptionPane.showConfirmDialog(this, "Add this GRN ?");
                if (option == JOptionPane.YES_OPTION) {
                    grnNo = GrnControl.addGrn(lblItemTotal.getText(), "0", "0", "0", 0,
                            Validations.getBigDecimalOrZeroFromString(lblItemTotal.getText()), "0",
                            Validations.getIntOrZeroFromString(txtItemQty.getText()));
                }
                if (grnNo != 0) {
                    GrnDetailControl.addGrnDetail(tblItems, grnNo);
                    clearAll();
                    JOptionPane.showMessageDialog(this, "GRN added successfully !", "Success", JOptionPane.PLAIN_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Pls add at least one item !", "Error!", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            Logger.getLogger(GRN.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void itemSelectByBarcodeAndSetDetails() {
        try {
            if (ItemControl.getItemByItemBarcodeOrItemCode(txtItemCode.getText().trim()) == null) {
                txtItemCode.requestFocus();
                return;
            }
            item = ItemControl.getItemByItemBarcodeOrItemCode(txtItemCode.getText().trim());
            lblItemName.setText(item.getItemName());
            txtItemCode.setText(item.getBarcode());
            txtPurchasePrice.setText(item.getPurchasePrice() == null ? BigDecimal.ZERO.toString() : item.getPurchasePrice().toString());
            txtRetailPrice.setText(item.getSellingPrice() == null ? BigDecimal.ZERO.toString() : item.getSellingPrice().toString());
            txtItemCode.requestFocus();
            txtItemCode.selectAll();
            lblQty.setText(CommonController.getCuttentStockByItemCode(item.getItemCode()).toString());
        } catch (SQLException ex) {
            Logger.getLogger(GRN.class.getName()).log(Level.SEVERE, null, ex);
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
        txtPurchasePrice = new javax.swing.JTextField();
        txtItemCode = new javax.swing.JTextField();
        txtRetailPrice = new javax.swing.JTextField();
        txtQty = new javax.swing.JTextField();
        comboUnit = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblItems = new javax.swing.JTable();
        btnItemSearch = new javax.swing.JButton();
        lblItemName = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtItemQty = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        btnSave = new javax.swing.JButton();
        btnAddItem = new javax.swing.JButton();
        lblItemTotal = new javax.swing.JLabel();
        btnDelete = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        lblQty = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Goods Receive Note");
        setMaximumSize(new java.awt.Dimension(1360, 720));
        setMinimumSize(new java.awt.Dimension(1360, 720));
        setResizable(false);
        setSize(new java.awt.Dimension(1360, 720));

        jPanel1.setMaximumSize(new java.awt.Dimension(1360, 720));
        jPanel1.setMinimumSize(new java.awt.Dimension(1360, 720));
        jPanel1.setPreferredSize(new java.awt.Dimension(1360, 720));

        panel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panel.setMaximumSize(new java.awt.Dimension(1360, 720));
        panel.setMinimumSize(new java.awt.Dimension(1360, 720));
        panel.setPreferredSize(new java.awt.Dimension(1360, 720));

        txtPurchasePrice.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        txtPurchasePrice.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtPurchasePrice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPurchasePriceActionPerformed(evt);
            }
        });

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

        txtRetailPrice.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        txtRetailPrice.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtRetailPrice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtRetailPriceActionPerformed(evt);
            }
        });

        txtQty.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        txtQty.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtQty.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtQtyActionPerformed(evt);
            }
        });

        comboUnit.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        comboUnit.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pcs", "kg" }));
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

        tblItems.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        tblItems.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Item Id", "Item Code", "Barcode", "Item Name", "Pur.Price", "Discount Rate", "Selling Price", "Qty", "Unit", "Total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblItems.setToolTipText("Invoice");
        tblItems.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tblItems.getTableHeader().setFont(new Font("Ubuntu", Font.BOLD, 18));
        tblItems.getTableHeader().setOpaque(false);
        tblItems.getTableHeader().setBackground(new Color(0, 0, 102));
        tblItems.getTableHeader().setForeground(new Color(255, 255, 255));
        tblItems.setRowHeight(22);
        tblItems.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tblItems);
        if (tblItems.getColumnModel().getColumnCount() > 0) {
            tblItems.getColumnModel().getColumn(0).setMinWidth(0);
            tblItems.getColumnModel().getColumn(0).setPreferredWidth(0);
            tblItems.getColumnModel().getColumn(0).setMaxWidth(0);
            tblItems.getColumnModel().getColumn(1).setMinWidth(100);
            tblItems.getColumnModel().getColumn(1).setPreferredWidth(100);
            tblItems.getColumnModel().getColumn(1).setMaxWidth(100);
            tblItems.getColumnModel().getColumn(2).setMinWidth(120);
            tblItems.getColumnModel().getColumn(2).setPreferredWidth(120);
            tblItems.getColumnModel().getColumn(2).setMaxWidth(120);
            tblItems.getColumnModel().getColumn(4).setMinWidth(120);
            tblItems.getColumnModel().getColumn(4).setPreferredWidth(120);
            tblItems.getColumnModel().getColumn(4).setMaxWidth(120);
            tblItems.getColumnModel().getColumn(5).setMinWidth(0);
            tblItems.getColumnModel().getColumn(5).setPreferredWidth(0);
            tblItems.getColumnModel().getColumn(5).setMaxWidth(0);
            tblItems.getColumnModel().getColumn(6).setMinWidth(120);
            tblItems.getColumnModel().getColumn(6).setPreferredWidth(120);
            tblItems.getColumnModel().getColumn(6).setMaxWidth(120);
            tblItems.getColumnModel().getColumn(7).setMinWidth(50);
            tblItems.getColumnModel().getColumn(7).setPreferredWidth(50);
            tblItems.getColumnModel().getColumn(7).setMaxWidth(50);
            tblItems.getColumnModel().getColumn(8).setMinWidth(0);
            tblItems.getColumnModel().getColumn(8).setPreferredWidth(0);
            tblItems.getColumnModel().getColumn(8).setMaxWidth(0);
            tblItems.getColumnModel().getColumn(9).setMinWidth(120);
            tblItems.getColumnModel().getColumn(9).setPreferredWidth(120);
            tblItems.getColumnModel().getColumn(9).setMaxWidth(120);
        }

        btnItemSearch.setBackground(new java.awt.Color(255, 255, 255));
        btnItemSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cazzendra/pos/newtheme/icons/search-9-32.png"))); // NOI18N
        btnItemSearch.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
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

        lblItemName.setFont(new java.awt.Font("Ubuntu Medium", 0, 14)); // NOI18N
        lblItemName.setForeground(new java.awt.Color(0, 0, 102));

        jLabel4.setFont(new java.awt.Font("Ubuntu Medium", 0, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 102));
        jLabel4.setText("Retail Price");

        jLabel6.setFont(new java.awt.Font("Ubuntu Medium", 0, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 102));
        jLabel6.setText("Item Qty");

        txtItemQty.setEditable(false);
        txtItemQty.setBackground(new java.awt.Color(204, 204, 204));
        txtItemQty.setFont(new java.awt.Font("Ubuntu", 1, 24)); // NOI18N
        txtItemQty.setForeground(new java.awt.Color(255, 51, 51));
        txtItemQty.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel7.setFont(new java.awt.Font("Ubuntu Medium", 0, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 102));
        jLabel7.setText("GRN Total");

        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cazzendra/pos/newtheme/icons/save-32.png"))); // NOI18N
        btnSave.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnAddItem.setBackground(new java.awt.Color(255, 255, 255));
        btnAddItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cazzendra/pos/newtheme/icons/add-folder-32.png"))); // NOI18N
        btnAddItem.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAddItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddItemActionPerformed(evt);
            }
        });

        lblItemTotal.setFont(new java.awt.Font("Ubuntu Medium", 0, 24)); // NOI18N
        lblItemTotal.setForeground(new java.awt.Color(255, 0, 0));
        lblItemTotal.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblItemTotal.setText("0.00");

        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cazzendra/pos/newtheme/icons/delete-32.png"))); // NOI18N
        btnDelete.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Ubuntu Medium", 0, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 102));
        jLabel8.setText("Purchase Price");

        lblQty.setFont(new java.awt.Font("Ubuntu", 0, 12)); // NOI18N
        lblQty.setForeground(new java.awt.Color(0, 0, 102));
        lblQty.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelLayout.createSequentialGroup()
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelLayout.createSequentialGroup()
                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(panelLayout.createSequentialGroup()
                                    .addContainerGap()
                                    .addComponent(txtItemQty, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btnAddItem, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(panelLayout.createSequentialGroup()
                                    .addGap(8, 8, 8)
                                    .addComponent(lblItemName, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(2, 2, 2)
                                    .addComponent(lblQty, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelLayout.createSequentialGroup()
                                    .addContainerGap()
                                    .addComponent(txtItemCode, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btnItemSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(24, 24, 24))
                                .addGroup(panelLayout.createSequentialGroup()
                                    .addContainerGap()
                                    .addComponent(txtQty, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(panelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(comboUnit, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jLabel4)
                                            .addComponent(txtRetailPrice)
                                            .addComponent(jLabel6)
                                            .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                    .addComponent(lblItemTotal, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 262, Short.MAX_VALUE)))
                                            .addComponent(txtPurchasePrice)))
                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1047, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(17, 17, 17))
        );
        panelLayout.setVerticalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelLayout.createSequentialGroup()
                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnItemSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtItemCode, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblItemName, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panelLayout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(lblQty, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtQty, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(comboUnit, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtPurchasePrice, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtRetailPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtItemQty, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnAddItem, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblItemTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 618, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, 694, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 694, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnItemSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnItemSearchActionPerformed
        itemSearchAndSetDetails();
    }//GEN-LAST:event_btnItemSearchActionPerformed

    private void txtItemCodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtItemCodeActionPerformed
        if (item != null) {
            txtQty.requestFocus();
        }
    }//GEN-LAST:event_txtItemCodeActionPerformed

    private void comboUnitPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_comboUnitPopupMenuWillBecomeInvisible

    }//GEN-LAST:event_comboUnitPopupMenuWillBecomeInvisible

    private void btnAddItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddItemActionPerformed
        if (Validations.isNotEmpty(txtItemCode.getText()) && Validations.isNotEmpty(txtQty.getText())
                && Validations.isNotEmpty(txtRetailPrice.getText())) {
            addItemToTable();
            calculateRowCount();
            clearItemDetails();
            txtItemCode.requestFocus();
            grnTotal();
        }
    }//GEN-LAST:event_btnAddItemActionPerformed

    private void txtRetailPriceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtRetailPriceActionPerformed
        btnAddItem.requestFocus();
    }//GEN-LAST:event_txtRetailPriceActionPerformed

    private void comboUnitKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_comboUnitKeyReleased
        txtRetailPrice.requestFocus();
        txtRetailPrice.selectAll();
    }//GEN-LAST:event_comboUnitKeyReleased

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        deleteRow();
        grnTotal();
        calculateRowCount();
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnItemSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnItemSearchKeyReleased

    }//GEN-LAST:event_btnItemSearchKeyReleased

    private void txtItemCodeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtItemCodeKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            itemSelectByBarcodeAndSetDetails();
            if (item != null) {
                txtQty.requestFocus();
            }
        }
    }//GEN-LAST:event_txtItemCodeKeyReleased

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        saveAction();
    }//GEN-LAST:event_btnSaveActionPerformed

    private void txtQtyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtQtyActionPerformed
        if (Validations.getIntOrZeroFromString(txtQty.getText()) > 0) {
            txtPurchasePrice.requestFocus();
            txtPurchasePrice.selectAll();
        }

    }//GEN-LAST:event_txtQtyActionPerformed

    private void txtPurchasePriceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPurchasePriceActionPerformed
        txtRetailPrice.requestFocus();
        txtRetailPrice.selectAll();
    }//GEN-LAST:event_txtPurchasePriceActionPerformed

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
            java.util.logging.Logger.getLogger(GRN.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GRN.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GRN.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GRN.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GRN("").setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddItem;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnItemSearch;
    private javax.swing.JButton btnSave;
    private javax.swing.JComboBox<String> comboUnit;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblItemName;
    private javax.swing.JLabel lblItemTotal;
    private javax.swing.JLabel lblQty;
    private javax.swing.JPanel panel;
    private javax.swing.JTable tblItems;
    private javax.swing.JTextField txtItemCode;
    private javax.swing.JTextField txtItemQty;
    private javax.swing.JTextField txtPurchasePrice;
    private javax.swing.JTextField txtQty;
    private javax.swing.JTextField txtRetailPrice;
    // End of variables declaration//GEN-END:variables
}
