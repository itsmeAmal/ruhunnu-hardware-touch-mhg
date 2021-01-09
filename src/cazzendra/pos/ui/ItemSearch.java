/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cazzendra.pos.ui;

import cazzendra.pos.control.CommonController;
import cazzendra.pos.control.ItemControl;
import cazzendra.pos.core.CommonConstants;
import cazzendra.pos.core.Loading;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.KeyStroke;

/**
 *
 * @author Amal
 */
public class ItemSearch extends javax.swing.JDialog {

    private String Lang;

    /**
     * Creates new form ItemSearch
     *
     * @param parent
     * @param modal
     * @param Language
     */
    public ItemSearch(java.awt.Frame parent, boolean modal, String Language) {
        super(parent, modal);
        initComponents();
        loadDataToTable();
        panel.setBackground(Loading.getColorCode());
        setHotKey();
        txtSearchAttribute.requestFocus();
        Loading.customJButton(0, 0, 102, btnSelect);
        this.Lang = Language;
        SetLanguageUi();
    }

    private void setHotKey() {

        KeyStroke tab = KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0);
        InputMap inputMap2 = btnSearch.getInputMap(JButton.WHEN_IN_FOCUSED_WINDOW);
        inputMap2.put(tab, "B");

        btnSearch.getActionMap().put("B", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                okButtonAction();
            }
        });

        KeyStroke tab2 = KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0);
        InputMap inputMap3 = btnSearch.getInputMap(JButton.WHEN_IN_FOCUSED_WINDOW);
        inputMap3.put(tab2, "B");

        btnSearch.getActionMap().put("B", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                tblItems.requestFocus();
            }
        });

        KeyStroke tab4 = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
        InputMap inputMap4 = btnSearch.getInputMap(JButton.WHEN_IN_FOCUSED_WINDOW);
        inputMap4.put(tab4, "B");

        btnSelect.getActionMap().put("B", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                okButtonAction();
            }
        });

        createKeybindings(tblItems);
    }

    private void createKeybindings(JTable table) {
        table.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "Enter");
        table.getActionMap().put("Enter", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                okButtonAction();
            }
        });
    }

    private int loadDataToTable() {
        try {
            ResultSet rset = ItemControl.getAllItems();
            String[] columnList = {"item_id", "item_code", "item_name", "item_barcode"};
            CommonController.loadDataToTable(tblItems, rset, columnList);
        } catch (SQLException ex) {
            Logger.getLogger(ItemSearch.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public int selectedItem() {
        int itemId = 0;
        int selectedRow = tblItems.getSelectedRow();
        if (selectedRow != -1) {
            itemId = Validations.getIntOrZeroFromString(tblItems.getValueAt(selectedRow, 0).toString());
        } else {
            tblItems.requestFocus();
        }
        return itemId;
    }

    private void searchByMoreAttributes() {
        try {
            ArrayList<String[]> attributeConditionValueList = new ArrayList<>();

            String[] acv1 = {"item_name", CommonConstants.sql.LIKE, "%" + txtSearchAttribute.getText().trim() + "%"};
            attributeConditionValueList.add(acv1);

            String[] acv2 = {"item_barcode", CommonConstants.sql.LIKE, "%" + txtSearchAttribute.getText().trim() + "%"};
            attributeConditionValueList.add(acv2);

            String[] acv3 = {"item_code", CommonConstants.sql.LIKE, "%" + txtSearchAttribute.getText().trim() + "%"};
            attributeConditionValueList.add(acv3);

            ResultSet rset = ItemControl.getItemsByMoreAttributes(attributeConditionValueList, CommonConstants.sql.OR);

            String[] columnList = {"item_id", "item_code", "item_name", "item_barcode"};

            CommonController.loadDataToTable(tblItems, rset, columnList);

        } catch (SQLException ex) {
            Logger.getLogger(ItemSearch.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void okButtonAction() {
        int itemId = selectedItem();
        if (itemId != 0) {
            this.dispose();
        }
    }

    private void SetLanguageUi() {
        tblItems.getTableHeader().setVisible(false);
        tblItems.getTableHeader().removeAll();
        panel.remove(pnlTblHdr);
        panel.remove(jScrollPane1);
        panel.add(pnlTblHdr, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 124, 840, 35));
        panel.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 126, 840, 350));
        String FolderName = "mainmenu";
        if (Lang.equalsIgnoreCase(Options.LANG_ENGLISH)) {
            new Loading().CustomLangImage(FolderName, LangSinhala.LABEL_ITEM_SEARCH_SEARCH_BY_ITEM_NAME_ITEM_BARCODE, lblItemName);
            new Loading().CustomLangImage(FolderName, LangSinhala.TBL_HDR_ITEM_CODE, tblItemCode);
            new Loading().CustomLangImage(FolderName, LangSinhala.TBL_HDR_ITEM_NAME, tblItemName);
            new Loading().CustomLangImage(FolderName, LangSinhala.TBL_HDR_ITEM_BARCODE, tblTotal);

        } else if (Lang.equalsIgnoreCase(Options.LANG_SINHALA)) {
            new Loading().CustomLangImage(FolderName, LangSinhala.LABEL_SINHALA_ITEM_SEARCH_SEARCH_BY_ITEM_NAME_ITEM_BARCODE, lblItemName);
            new Loading().CustomLangImage(FolderName, LangSinhala.TBL_HDR_ITEM_CODE_SINHALA, tblItemCode);
            new Loading().CustomLangImage(FolderName, LangSinhala.TBL_HDR_ITEM_NAME_SINHALA, tblItemName);
            new Loading().CustomLangImage(FolderName, LangSinhala.TBL_HDR_ITEM_BARCODE_SINHALA, tblTotal);
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
        pnlTblHdr = new javax.swing.JPanel();
        tblTotal = new javax.swing.JLabel();
        tblItemCode = new javax.swing.JLabel();
        tblItemName = new javax.swing.JLabel();
        txtSearchAttribute = new javax.swing.JTextField();
        btnSelect = new javax.swing.JButton();
        lblItemName = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblItems = new javax.swing.JTable();
        btnSearch = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Item Search");
        setAlwaysOnTop(true);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setResizable(false);

        panel.setMaximumSize(new java.awt.Dimension(1024, 720));
        panel.setMinimumSize(new java.awt.Dimension(1024, 720));
        panel.setPreferredSize(new java.awt.Dimension(1024, 720));
        panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlTblHdr.setBackground(new java.awt.Color(0, 0, 102));

        tblTotal.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        tblTotal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cloudrevel/language/src/mainmenu/item-barcode-sinhala.png"))); // NOI18N

        tblItemCode.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cloudrevel/language/src/mainmenu/tbl-hdr-item-code-sinhala.png"))); // NOI18N

        tblItemName.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cloudrevel/language/src/mainmenu/tbl-hdr-item-name-sinhala.png"))); // NOI18N

        javax.swing.GroupLayout pnlTblHdrLayout = new javax.swing.GroupLayout(pnlTblHdr);
        pnlTblHdr.setLayout(pnlTblHdrLayout);
        pnlTblHdrLayout.setHorizontalGroup(
            pnlTblHdrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTblHdrLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tblItemCode, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tblItemName, javax.swing.GroupLayout.PREFERRED_SIZE, 444, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                .addComponent(tblTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        pnlTblHdrLayout.setVerticalGroup(
            pnlTblHdrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tblTotal, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
            .addComponent(tblItemCode, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(tblItemName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        panel.add(pnlTblHdr, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 120, 840, 35));

        txtSearchAttribute.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        txtSearchAttribute.setToolTipText("Search by Item Code, Item Name or Item Barcode");
        txtSearchAttribute.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchAttributeKeyReleased(evt);
            }
        });
        panel.add(txtSearchAttribute, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 60, 450, 40));

        btnSelect.setBackground(new java.awt.Color(255, 255, 255));
        btnSelect.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cazzendra/pos/newtheme/icons/facebook-like-32.png"))); // NOI18N
        btnSelect.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSelect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelectActionPerformed(evt);
            }
        });
        panel.add(btnSelect, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 490, 80, 40));

        lblItemName.setFont(new java.awt.Font("Ubuntu Medium", 0, 18)); // NOI18N
        lblItemName.setForeground(new java.awt.Color(0, 0, 102));
        lblItemName.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cloudrevel/language/src/mainmenu/item-search-by-name-item-code-barcode-sinhala.png"))); // NOI18N
        panel.add(lblItemName, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 30, -1, -1));

        tblItems.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        tblItems.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Item Id ", "Item Code", "Item Name", "Item Barcode"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
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
        tblItems.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tblItemsFocusLost(evt);
            }
        });
        tblItems.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tblItemsKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(tblItems);
        if (tblItems.getColumnModel().getColumnCount() > 0) {
            tblItems.getColumnModel().getColumn(0).setMinWidth(0);
            tblItems.getColumnModel().getColumn(0).setPreferredWidth(0);
            tblItems.getColumnModel().getColumn(0).setMaxWidth(0);
            tblItems.getColumnModel().getColumn(1).setMinWidth(150);
            tblItems.getColumnModel().getColumn(1).setPreferredWidth(150);
            tblItems.getColumnModel().getColumn(1).setMaxWidth(150);
            tblItems.getColumnModel().getColumn(3).setMinWidth(200);
            tblItems.getColumnModel().getColumn(3).setPreferredWidth(200);
            tblItems.getColumnModel().getColumn(3).setMaxWidth(200);
        }

        panel.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(36, 126, 840, 350));

        btnSearch.setBackground(new java.awt.Color(255, 255, 255));
        btnSearch.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        btnSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cazzendra/pos/ui/icons/search_32x32.png"))); // NOI18N
        panel.add(btnSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 390, 40, 40));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, 908, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, 558, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnSelectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelectActionPerformed
        okButtonAction();
    }//GEN-LAST:event_btnSelectActionPerformed

    private void tblItemsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblItemsKeyPressed
//        switch (evt.getKeyCode()) {
//            case KeyEvent.VK_ENTER:
//                createKeybindings(tblItems);
//                break;
//
//            case KeyEvent.VK_UP:
//                break;
//
//            case KeyEvent.VK_DOWN:
//                break;
//
//            case KeyEvent.VK_LEFT:
//                txtSearchAttribute.requestFocus();
//                break;
//
//            case KeyEvent.VK_RIGHT:
//                txtSearchAttribute.requestFocus();
//                break;
//
//            default:
////                  txtItemName.setText(txtItemName.getText() + evt.getKeyChar());
//                txtSearchAttribute.requestFocus();
//        }
    }//GEN-LAST:event_tblItemsKeyPressed

    private void txtSearchAttributeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchAttributeKeyReleased
        searchByMoreAttributes();
    }//GEN-LAST:event_txtSearchAttributeKeyReleased

    private void tblItemsFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tblItemsFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_tblItemsFocusLost

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
            java.util.logging.Logger.getLogger(ItemSearch.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ItemSearch.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ItemSearch.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ItemSearch.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ItemSearch dialog = new ItemSearch(new javax.swing.JFrame(), true, "");
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
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnSelect;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblItemName;
    private javax.swing.JPanel panel;
    private javax.swing.JPanel pnlTblHdr;
    private javax.swing.JLabel tblItemCode;
    private javax.swing.JLabel tblItemName;
    private javax.swing.JTable tblItems;
    private javax.swing.JLabel tblTotal;
    private javax.swing.JTextField txtSearchAttribute;
    // End of variables declaration//GEN-END:variables
}
