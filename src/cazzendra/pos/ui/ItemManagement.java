/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cazzendra.pos.ui;

import cazzendra.pos.control.CategoryControl;
import cazzendra.pos.control.CommonController;
import cazzendra.pos.control.ItemControl;
import cazzendra.pos.control.SubCategoryControl;
import cazzendra.pos.core.Loading;
import cazzendra.pos.core.MethodStatus;
import cazzendra.pos.core.Options;
import cazzendra.pos.core.Validations;
import cloudrevel.lnguage.src.keys.LangSinhala;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
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
import net.sf.jasperreports.engine.JRException;

/**
 *
 * @author Amal
 */
public class ItemManagement extends javax.swing.JFrame {

    private String Lang;

    public ItemManagement(String Language) {
        initComponents();
        loadDataToTable();
        setDefaults();
        panel.setBackground(Loading.getColorCode());
//        HotKeys();
        LoadSubCategoryToComboBox();
        this.Lang = Language;
        SetLanguageUi();
        //-----
        lblSubCategory.setVisible(false);
        comboSubCategory.setVisible(false);
        LoadDataObjectsToCategoryCombo(); 
    }

    private void HotKeys() {
        KeyStroke ks = KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0);
        InputMap im = btnItemSearch.getInputMap(JButton.WHEN_IN_FOCUSED_WINDOW);
        im.put(ks, "C");
        btnItemSearch.getActionMap().put("C", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                itemSearch();
            }
        });
    }

    private void LoadDataObjectsToCategoryCombo() {
        try {
            ResultSet Rset = CategoryControl.GetAllCategories();
            String[] ColumnList = {"main_category_id", "main_category_name", "main_category_detail"};
            CommonController.loadDataObjectsIntoComboBox(comboMainCategory, Rset, ColumnList, "main_category_name");
        } catch (SQLException ex) {
            Logger.getLogger(ItemManagement.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void addItem() {
        int option = JOptionPane.showConfirmDialog(this, "Do you want to add this Item? ");
        if (option == JOptionPane.YES_OPTION) {
            MethodStatus status = null;
            try {
                if (Validations.isNotEmpty(txtItemName.getText().trim())
                        && Validations.isNotEmpty(txtItemCode.getText().trim())
                        && Validations.isNotEmpty(txtReorderLevel.getText().trim())
                        && Validations.isNotEmpty(txtSellingPrice.getText().trim())) {

                    if (ItemControl.isExistingItemCode(txtItemCode.getText().trim())) {
                        status = ItemControl.addItem(txtItemName.getText().trim(), txtItemCode.getText().trim(),
                                txtbarcode.getText().trim(), txtReorderLevel.getText().trim(),
                                txtSellingPrice.getText().trim(), comboMainCategory.getSelectedItem().toString(),
                                "0");

                        if (status == MethodStatus.SUCCESS) {
                            JOptionPane.showMessageDialog(this, "Item Added Successfully !", "Success", JOptionPane.PLAIN_MESSAGE);
                            clearDetail();
                            setDefaults();
                        }
                    } else {
                        txtItemCode.requestFocus();
                        txtItemCode.selectAll();
                        JOptionPane.showMessageDialog(this, "Item Code Already Exist !", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Fill all details", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                Logger.getLogger(ItemManagement.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
//            table.getTableHeader().setFont(new Font("SansSerif", Font.ITALIC, 12));
        tblItems.getColumnModel().setColumnMargin(5);
    }

    private void clearDetail() {
        txtItemCode.setText("");
        txtItemName.setText("");
        txtReorderLevel.setText("");
        txtSellingPrice.setText("");
        txtbarcode.setText("");
        txtbarcode.requestFocus();
    }

    private void loadDataToTable() {
        try {
            ResultSet rset = ItemControl.getAllItems();
            String[] columnList = {"item_id", "item_code", "item_name", "item_barcode", "item_reorder_level", "item_selling_price"};
            CommonController.loadDataToTable(tblItems, rset, columnList);
        } catch (SQLException ex) {
            Logger.getLogger(ItemManagement.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void setDefaults() {
        btnAddCategory.setVisible(false); 
//        btnItemSearch.setVisible(false);
        txtbarcode.requestFocus();
        //---- button color list-------
        List<JButton> btnList = new ArrayList<>();
        btnList.add(btnEdit);
        btnList.add(btnItemSearch);
        btnList.add(btnSave);
        btnList.add(btnAddCategory);
        Loading.customJButtonList(0, 0, 102, btnList); 
    }

    private void itemSearch() {
        ItemSearch itemSearch = new ItemSearch(this, true, Lang);
        itemSearch.setVisible(true);
    }

    private void searchByItemName() {
        try {
            ResultSet rset = ItemControl.searchItem("item_barcode", txtbarcode.getText().trim());
            String[] columnList = {"item_id", "item_code", "item_name", "item_barcode", "item_reorder_level", "item_selling_price"};
            CommonController.loadDataToTable(tblItems, rset, columnList);
        } catch (SQLException ex) {
            Logger.getLogger(ItemSearch.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void LoadSubCategoryToComboBox() {
        try {
            ResultSet Rset = SubCategoryControl.GetAllSubCategories();
            CommonController.loadDataToComboBox(comboSubCategory, Rset, "sub_category_name");
        } catch (SQLException ex) {
            Logger.getLogger(ItemManagement.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void SetLanguageUi() {

        tblItems.getTableHeader().setVisible(false);
        tblItems.getTableHeader().removeAll();
        panel.remove(pnlTblHdr);
        panel.remove(jScrollPane1);
        panel.add(pnlTblHdr, new org.netbeans.lib.awtextra.AbsoluteConstraints(321, 81, 1020, 35));
        panel.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 80, 1020, 590));
        String FolderName = "mainmenu";

        if (Lang.equalsIgnoreCase(Options.LANG_ENGLISH)) {

            new Loading().CustomLangImage(FolderName, LangSinhala.LBL_ITEM_BARCODE, lblBarcode);
            new Loading().CustomLangImage(FolderName, LangSinhala.LBL_ITEM_NAME, lblItemName);
            new Loading().CustomLangImage(FolderName, LangSinhala.LBL_ITEM_CODE, lblItemCode);
            new Loading().CustomLangImage(FolderName, LangSinhala.LBL_ITEM_MAIN_CATEGORY, lblMainCategory);
            new Loading().CustomLangImage(FolderName, LangSinhala.LBL_ITEM_SUB_CATEGORY, lblSubCategory);
            new Loading().CustomLangImage(FolderName, LangSinhala.LBL_ITEM_RE_ORDER_LEVEL, lblReorderLevel);
            new Loading().CustomLangImage(FolderName, LangSinhala.LBL_ITEM_RETAIL_PRICE, lblRetailPrice);
            new Loading().CustomLangImage(FolderName, LangSinhala.TBL_HDR_ITEM_CODE, tblHdrItemCode);
            new Loading().CustomLangImage(FolderName, LangSinhala.TBL_HDR_ITEM_NAME, tblHdrItemName);
            new Loading().CustomLangImage(FolderName, LangSinhala.TBL_HDR_ITEM_BARCODE, tblHdrBarcode);
            new Loading().CustomLangImage(FolderName, LangSinhala.TBL_HDR_ITEM_RE_ORDER_LEVEL, tblHdrReorderLevel);
            new Loading().CustomLangImage(FolderName, LangSinhala.TBL_HDR_ITEM_RETAIL_PRICE, tblHdrRetailPrice);

        } else if (Lang.equalsIgnoreCase(Options.LANG_SINHALA)) {

            new Loading().CustomLangImage(FolderName, LangSinhala.LBL_ITEM_BARCODE_SINHALA, lblBarcode);
            new Loading().CustomLangImage(FolderName, LangSinhala.LBL_ITEM_NAME_SINHALA, lblItemName);
            new Loading().CustomLangImage(FolderName, LangSinhala.LBL_ITEM_CODE_SINHALA, lblItemCode);
            new Loading().CustomLangImage(FolderName, LangSinhala.LBL_ITEM_MAIN_CATEGORY_SINHALA, lblMainCategory);
            new Loading().CustomLangImage(FolderName, LangSinhala.LBL_ITEM_SUB_CATEGORY_SINHALA, lblSubCategory);
            new Loading().CustomLangImage(FolderName, LangSinhala.LBL_ITEM_RE_ORDER_LEVEL_SINHALA, lblReorderLevel);
            new Loading().CustomLangImage(FolderName, LangSinhala.LBL_ITEM_RETAIL_PRICE_SINHALA, lblRetailPrice);
            new Loading().CustomLangImage(FolderName, LangSinhala.TBL_HDR_ITEM_CODE_SINHALA, tblHdrItemCode);
            new Loading().CustomLangImage(FolderName, LangSinhala.TBL_HDR_ITEM_NAME_SINHALA, tblHdrItemName);
            new Loading().CustomLangImage(FolderName, LangSinhala.TBL_HDR_ITEM_BARCODE_SINHALA, tblHdrBarcode);
            new Loading().CustomLangImage(FolderName, LangSinhala.TBL_HDR_ITEM_RE_ORDER_LEVEL_SINHALA, tblHdrReorderLevel);
            new Loading().CustomLangImage(FolderName, LangSinhala.TBL_HDR_ITEM_RETAIL_PRICE_SINHALA, tblHdrRetailPrice);
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

        panel = new javax.swing.JPanel();
        txtbarcode = new javax.swing.JTextField();
        txtItemCode = new javax.swing.JTextField();
        txtReorderLevel = new javax.swing.JTextField();
        txtSellingPrice = new javax.swing.JTextField();
        txtItemName = new javax.swing.JTextField();
        btnSave = new javax.swing.JButton();
        lblItemCode = new javax.swing.JLabel();
        lblBarcode = new javax.swing.JLabel();
        lblItemName = new javax.swing.JLabel();
        lblReorderLevel = new javax.swing.JLabel();
        lblRetailPrice = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblItems = new javax.swing.JTable();
        btnEdit = new javax.swing.JButton();
        btnItemSearch = new javax.swing.JButton();
        lblSubCategory = new javax.swing.JLabel();
        comboSubCategory = new javax.swing.JComboBox<>();
        comboMainCategory = new javax.swing.JComboBox<>();
        lblMainCategory = new javax.swing.JLabel();
        pnlTblHdr = new javax.swing.JPanel();
        tblHdrBarcode = new javax.swing.JLabel();
        tblHdrItemCode = new javax.swing.JLabel();
        tblHdrItemName = new javax.swing.JLabel();
        tblHdrReorderLevel = new javax.swing.JLabel();
        tblHdrRetailPrice = new javax.swing.JLabel();
        btnAddCategory = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Item Management");
        setAlwaysOnTop(true);
        setMaximumSize(new java.awt.Dimension(1360, 720));
        setMinimumSize(new java.awt.Dimension(1360, 720));
        setPreferredSize(new java.awt.Dimension(1360, 720));
        setResizable(false);
        setSize(new java.awt.Dimension(1360, 720));

        panel.setBackground(new java.awt.Color(0, 102, 102));
        panel.setMaximumSize(new java.awt.Dimension(1360, 694));
        panel.setMinimumSize(new java.awt.Dimension(1360, 694));
        panel.setPreferredSize(new java.awt.Dimension(1360, 694));
        panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtbarcode.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        txtbarcode.setToolTipText("Barcode");
        txtbarcode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtbarcodeActionPerformed(evt);
            }
        });
        txtbarcode.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbarcodeKeyReleased(evt);
            }
        });
        panel.add(txtbarcode, new org.netbeans.lib.awtextra.AbsoluteConstraints(29, 66, 266, 40));

        txtItemCode.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        txtItemCode.setToolTipText("Item Code");
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
        panel.add(txtItemCode, new org.netbeans.lib.awtextra.AbsoluteConstraints(29, 210, 266, 40));

        txtReorderLevel.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        txtReorderLevel.setToolTipText("Item Reorder Level");
        txtReorderLevel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtReorderLevelActionPerformed(evt);
            }
        });
        txtReorderLevel.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtReorderLevelKeyReleased(evt);
            }
        });
        panel.add(txtReorderLevel, new org.netbeans.lib.awtextra.AbsoluteConstraints(29, 429, 266, 40));

        txtSellingPrice.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        txtSellingPrice.setToolTipText("Item Selling Price");
        txtSellingPrice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSellingPriceActionPerformed(evt);
            }
        });
        txtSellingPrice.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSellingPriceKeyReleased(evt);
            }
        });
        panel.add(txtSellingPrice, new org.netbeans.lib.awtextra.AbsoluteConstraints(29, 502, 266, 40));

        txtItemName.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        txtItemName.setToolTipText("Item Name");
        txtItemName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtItemNameActionPerformed(evt);
            }
        });
        txtItemName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtItemNameKeyReleased(evt);
            }
        });
        panel.add(txtItemName, new org.netbeans.lib.awtextra.AbsoluteConstraints(29, 138, 266, 40));

        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cazzendra/pos/newtheme/icons/save-32.png"))); // NOI18N
        btnSave.setToolTipText("Save");
        btnSave.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });
        panel.add(btnSave, new org.netbeans.lib.awtextra.AbsoluteConstraints(215, 554, 80, 40));

        lblItemCode.setFont(new java.awt.Font("Ubuntu Medium", 0, 18)); // NOI18N
        lblItemCode.setForeground(new java.awt.Color(0, 0, 102));
        lblItemCode.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cloudrevel/language/src/mainmenu/item-code-sinhala.png"))); // NOI18N
        panel.add(lblItemCode, new org.netbeans.lib.awtextra.AbsoluteConstraints(29, 184, -1, -1));

        lblBarcode.setFont(new java.awt.Font("Ubuntu Medium", 0, 18)); // NOI18N
        lblBarcode.setForeground(new java.awt.Color(0, 0, 102));
        lblBarcode.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cloudrevel/language/src/mainmenu/item-barcode-blue-sinhala.png"))); // NOI18N
        panel.add(lblBarcode, new org.netbeans.lib.awtextra.AbsoluteConstraints(29, 40, 266, -1));

        lblItemName.setFont(new java.awt.Font("Ubuntu Medium", 0, 18)); // NOI18N
        lblItemName.setForeground(new java.awt.Color(0, 0, 102));
        lblItemName.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cloudrevel/language/src/mainmenu/item-name-sinhala.png"))); // NOI18N
        panel.add(lblItemName, new org.netbeans.lib.awtextra.AbsoluteConstraints(29, 112, -1, -1));

        lblReorderLevel.setFont(new java.awt.Font("Ubuntu Medium", 0, 18)); // NOI18N
        lblReorderLevel.setForeground(new java.awt.Color(0, 0, 102));
        lblReorderLevel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cloudrevel/language/src/mainmenu/re-order-level-sinhala.png"))); // NOI18N
        panel.add(lblReorderLevel, new org.netbeans.lib.awtextra.AbsoluteConstraints(29, 402, -1, -1));

        lblRetailPrice.setFont(new java.awt.Font("Ubuntu Medium", 0, 18)); // NOI18N
        lblRetailPrice.setForeground(new java.awt.Color(0, 0, 102));
        lblRetailPrice.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cloudrevel/language/src/mainmenu/retail-price-blue-sinhala.png"))); // NOI18N
        panel.add(lblRetailPrice, new org.netbeans.lib.awtextra.AbsoluteConstraints(29, 475, -1, -1));

        tblItems.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        tblItems.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Item Id", "Item Code", "Item Name", "Item Barcode", "Re-Order Level", "Retail Price"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
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
            tblItems.getColumnModel().getColumn(1).setMinWidth(200);
            tblItems.getColumnModel().getColumn(1).setPreferredWidth(200);
            tblItems.getColumnModel().getColumn(1).setMaxWidth(200);
            tblItems.getColumnModel().getColumn(2).setResizable(false);
            tblItems.getColumnModel().getColumn(3).setMinWidth(160);
            tblItems.getColumnModel().getColumn(3).setPreferredWidth(160);
            tblItems.getColumnModel().getColumn(3).setMaxWidth(160);
            tblItems.getColumnModel().getColumn(4).setMinWidth(140);
            tblItems.getColumnModel().getColumn(4).setPreferredWidth(140);
            tblItems.getColumnModel().getColumn(4).setMaxWidth(140);
            tblItems.getColumnModel().getColumn(5).setMinWidth(150);
            tblItems.getColumnModel().getColumn(5).setPreferredWidth(150);
            tblItems.getColumnModel().getColumn(5).setMaxWidth(150);
        }

        panel.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(323, 85, 1023, 590));

        btnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cazzendra/pos/newtheme/icons/edit-9-32.png"))); // NOI18N
        btnEdit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });
        panel.add(btnEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(1300, 20, 40, 40));

        btnItemSearch.setBackground(new java.awt.Color(255, 255, 255));
        btnItemSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cazzendra/pos/newtheme/icons/printer-3-32.png"))); // NOI18N
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
        panel.add(btnItemSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(1250, 20, 40, 40));

        lblSubCategory.setFont(new java.awt.Font("Ubuntu Medium", 0, 18)); // NOI18N
        lblSubCategory.setForeground(new java.awt.Color(0, 0, 102));
        lblSubCategory.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cloudrevel/language/src/mainmenu/sub-category-sinhala.png"))); // NOI18N
        panel.add(lblSubCategory, new org.netbeans.lib.awtextra.AbsoluteConstraints(29, 329, -1, -1));

        comboSubCategory.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        comboSubCategory.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Admin", "Manager", "User" }));
        comboSubCategory.setToolTipText("Type");
        comboSubCategory.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                comboSubCategoryPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });
        comboSubCategory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboSubCategoryActionPerformed(evt);
            }
        });
        panel.add(comboSubCategory, new org.netbeans.lib.awtextra.AbsoluteConstraints(29, 356, 220, 40));

        comboMainCategory.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        comboMainCategory.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Bearing" }));
        comboMainCategory.setToolTipText("Type");
        comboMainCategory.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                comboMainCategoryPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });
        comboMainCategory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboMainCategoryActionPerformed(evt);
            }
        });
        panel.add(comboMainCategory, new org.netbeans.lib.awtextra.AbsoluteConstraints(29, 283, 266, 40));

        lblMainCategory.setFont(new java.awt.Font("Ubuntu Medium", 0, 18)); // NOI18N
        lblMainCategory.setForeground(new java.awt.Color(0, 0, 102));
        lblMainCategory.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cloudrevel/language/src/mainmenu/main-category-sinhala.png"))); // NOI18N
        panel.add(lblMainCategory, new org.netbeans.lib.awtextra.AbsoluteConstraints(29, 256, -1, -1));

        pnlTblHdr.setBackground(new java.awt.Color(0, 0, 102));

        tblHdrBarcode.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        tblHdrBarcode.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cloudrevel/language/src/mainmenu/item-barcode-sinhala.png"))); // NOI18N

        tblHdrItemCode.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cloudrevel/language/src/mainmenu/tbl-hdr-item-code-sinhala.png"))); // NOI18N

        tblHdrItemName.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        tblHdrItemName.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cloudrevel/language/src/mainmenu/tbl-hdr-item-name-sinhala.png"))); // NOI18N

        tblHdrReorderLevel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        tblHdrReorderLevel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cloudrevel/language/src/mainmenu/tbl-hdr-reoreder-level-sinhala.png"))); // NOI18N

        tblHdrRetailPrice.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        tblHdrRetailPrice.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cloudrevel/language/src/mainmenu/tbl-hdr-retail-price-sinhala.png"))); // NOI18N

        javax.swing.GroupLayout pnlTblHdrLayout = new javax.swing.GroupLayout(pnlTblHdr);
        pnlTblHdr.setLayout(pnlTblHdrLayout);
        pnlTblHdrLayout.setHorizontalGroup(
            pnlTblHdrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTblHdrLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tblHdrItemCode, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tblHdrItemName, javax.swing.GroupLayout.PREFERRED_SIZE, 368, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tblHdrBarcode, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tblHdrReorderLevel, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tblHdrRetailPrice, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE))
        );
        pnlTblHdrLayout.setVerticalGroup(
            pnlTblHdrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tblHdrBarcode, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(tblHdrItemCode, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(tblHdrItemName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnlTblHdrLayout.createSequentialGroup()
                .addGroup(pnlTblHdrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tblHdrReorderLevel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tblHdrRetailPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        panel.add(pnlTblHdr, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 80, 1020, 35));

        btnAddCategory.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cazzendra/pos/newtheme/icons/add-folder-32.png"))); // NOI18N
        btnAddCategory.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAddCategory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddCategoryActionPerformed(evt);
            }
        });
        panel.add(btnAddCategory, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 360, 40, 40));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, 1366, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, 720, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        addItem();
        loadDataToTable();
        txtbarcode.requestFocus();
    }//GEN-LAST:event_btnSaveActionPerformed

    private void txtbarcodeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbarcodeKeyReleased
//        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
//            txtItemName.requestFocus();
//        }
        searchByItemName();
    }//GEN-LAST:event_txtbarcodeKeyReleased

    private void txtItemCodeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtItemCodeKeyReleased
//        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
//            txtReorderLevel.requestFocus();
//        }
    }//GEN-LAST:event_txtItemCodeKeyReleased

    private void txtSellingPriceKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSellingPriceKeyReleased
//        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
//            btAddItem.requestFocus();
//        }
    }//GEN-LAST:event_txtSellingPriceKeyReleased

    private void txtItemNameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtItemNameKeyReleased
//        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
//            txtItemCode.requestFocus();
//        }
    }//GEN-LAST:event_txtItemNameKeyReleased

    private void txtReorderLevelKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtReorderLevelKeyReleased
//        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
//            txtSellingPrice.requestFocus();
//        }
    }//GEN-LAST:event_txtReorderLevelKeyReleased

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        int selectedItem = tblItems.getSelectedRow();
        if (selectedItem != -1) {
            int id = Validations.getIntOrZeroFromString(tblItems.getValueAt(selectedItem, 0).toString());
            new EditItem(this, true, id).setVisible(true);
            loadDataToTable();

        } else {
            JOptionPane.showMessageDialog(this, "Please select Item !", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_btnEditActionPerformed

    private void btnItemSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnItemSearchActionPerformed
        try {
            CommonController.printAllItems(Lang);
        } catch (SQLException | JRException ex) {
            Logger.getLogger(ItemManagement.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnItemSearchActionPerformed

    private void btnItemSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnItemSearchKeyReleased

    }//GEN-LAST:event_btnItemSearchKeyReleased

    private void txtbarcodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtbarcodeActionPerformed
//        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
        txtItemName.requestFocus();
//        }
        searchByItemName();
    }//GEN-LAST:event_txtbarcodeActionPerformed

    private void txtItemNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtItemNameActionPerformed
        txtItemCode.requestFocus();
    }//GEN-LAST:event_txtItemNameActionPerformed

    private void txtItemCodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtItemCodeActionPerformed
        txtReorderLevel.requestFocus();
    }//GEN-LAST:event_txtItemCodeActionPerformed

    private void txtReorderLevelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtReorderLevelActionPerformed
        txtSellingPrice.requestFocus();
    }//GEN-LAST:event_txtReorderLevelActionPerformed

    private void txtSellingPriceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSellingPriceActionPerformed
        btnSave.requestFocus();
    }//GEN-LAST:event_txtSellingPriceActionPerformed

    private void comboSubCategoryPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_comboSubCategoryPopupMenuWillBecomeInvisible

    }//GEN-LAST:event_comboSubCategoryPopupMenuWillBecomeInvisible

    private void comboSubCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboSubCategoryActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboSubCategoryActionPerformed

    private void comboMainCategoryPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_comboMainCategoryPopupMenuWillBecomeInvisible
        // TODO add your handling code here:
    }//GEN-LAST:event_comboMainCategoryPopupMenuWillBecomeInvisible

    private void comboMainCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboMainCategoryActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboMainCategoryActionPerformed

    private void btnAddCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddCategoryActionPerformed
        new CategoryManagement(Lang).setVisible(true);
        LoadSubCategoryToComboBox();
    }//GEN-LAST:event_btnAddCategoryActionPerformed

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
            java.util.logging.Logger.getLogger(ItemManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ItemManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ItemManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ItemManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ItemManagement("").setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddCategory;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnItemSearch;
    private javax.swing.JButton btnSave;
    private javax.swing.JComboBox<String> comboMainCategory;
    private javax.swing.JComboBox<String> comboSubCategory;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblBarcode;
    private javax.swing.JLabel lblItemCode;
    private javax.swing.JLabel lblItemName;
    private javax.swing.JLabel lblMainCategory;
    private javax.swing.JLabel lblReorderLevel;
    private javax.swing.JLabel lblRetailPrice;
    private javax.swing.JLabel lblSubCategory;
    private javax.swing.JPanel panel;
    private javax.swing.JPanel pnlTblHdr;
    private javax.swing.JLabel tblHdrBarcode;
    private javax.swing.JLabel tblHdrItemCode;
    private javax.swing.JLabel tblHdrItemName;
    private javax.swing.JLabel tblHdrReorderLevel;
    private javax.swing.JLabel tblHdrRetailPrice;
    private javax.swing.JTable tblItems;
    private javax.swing.JTextField txtItemCode;
    private javax.swing.JTextField txtItemName;
    private javax.swing.JTextField txtReorderLevel;
    private javax.swing.JTextField txtSellingPrice;
    private javax.swing.JTextField txtbarcode;
    // End of variables declaration//GEN-END:variables
}
