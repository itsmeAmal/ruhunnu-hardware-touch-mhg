/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cazzendra.pos.ui;

import cazzendra.pos.control.CategoryControl;
import cazzendra.pos.control.CommonController;
import cazzendra.pos.control.ItemControl;
import cazzendra.pos.core.Loading;
import cazzendra.pos.core.MethodStatus;
import cazzendra.pos.core.Validations;
import cazzendra.pos.model.Item;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Amal
 */
public class EditItem extends javax.swing.JDialog {

    int id;

    /**
     * Creates new form EditItem
     *
     * @param parent
     * @param modal
     * @param itemId
     */
    public EditItem(java.awt.Frame parent, boolean modal, int itemId) {
        super(parent, modal);
        initComponents();
        this.id = itemId;
        panel.setBackground(Loading.getColorCode());
        LoadCategoryToCategoryCombo();
        setDetails();
    }

    private void setDetails() {
        try {
            Item item = ItemControl.getItemByItemId(id);
            txtbarcode.setText(item.getBarcode());
            txtItemName.setText(item.getItemName());
            txtReorderLevel.setText(Integer.toString(item.getReOrderLevel()));
            txtItemCode.setText(item.getItemCode());
            Loading.customJButton(Loading.getColorR(), Loading.getColorG(), Loading.getColorB(), btnUpdate);
            comboMainCategory.setSelectedItem(item.getCategory());
        } catch (SQLException ex) {
            Logger.getLogger(EditItem.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

//    private void LoadDataObjectsToCategoryCombo() {
//        try {
//            ResultSet Rset = CategoryControl.GetAllCategories();
//            String[] ColumnList = {"main_category_id", "main_category_name", "main_category_detail"};
//            CommonController.loadDataObjectsIntoComboBox(comboMainCategory, Rset, ColumnList, "main_category_name");
//        } catch (SQLException ex) {
//            Logger.getLogger(EditItem.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    private void LoadCategoryToCategoryCombo() {
        try {
            ResultSet Rset = CategoryControl.GetAllCategories();
            CommonController.loadDataToComboBox(comboMainCategory, Rset, "main_category_name");
        } catch (SQLException ex) {
            Logger.getLogger(EditItem.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtbarcode = new javax.swing.JTextField();
        txtItemName = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtItemCode = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtReorderLevel = new javax.swing.JTextField();
        btnUpdate = new javax.swing.JButton();
        comboMainCategory = new javax.swing.JComboBox<>();
        lblMainCategory = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Edit Item");
        setResizable(false);

        jLabel2.setFont(new java.awt.Font("Ubuntu Medium", 0, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 102));
        jLabel2.setText("Barcode");

        txtbarcode.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        txtbarcode.setToolTipText("Item Name");
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

        txtItemName.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        txtItemName.setToolTipText("Item Name");
        txtItemName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtItemNameKeyReleased(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Ubuntu Medium", 0, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 102));
        jLabel3.setText("Item Name");

        jLabel1.setFont(new java.awt.Font("Ubuntu Medium", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 102));
        jLabel1.setText("Item Code");

        txtItemCode.setEditable(false);
        txtItemCode.setBackground(new java.awt.Color(204, 204, 204));
        txtItemCode.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        txtItemCode.setToolTipText("Item Code");
        txtItemCode.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtItemCodeKeyReleased(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Ubuntu Medium", 0, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 102));
        jLabel4.setText("Re-Order Level");

        txtReorderLevel.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        txtReorderLevel.setToolTipText("Item Reorder Level");
        txtReorderLevel.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtReorderLevelKeyReleased(evt);
            }
        });

        btnUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cazzendra/pos/newtheme/icons/save-32.png"))); // NOI18N
        btnUpdate.setToolTipText("Add Item");
        btnUpdate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

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

        lblMainCategory.setFont(new java.awt.Font("Ubuntu Medium", 0, 18)); // NOI18N
        lblMainCategory.setForeground(new java.awt.Color(0, 0, 102));
        lblMainCategory.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cloudrevel/language/src/mainmenu/main-category-sinhala.png"))); // NOI18N

        javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lblMainCategory)
                        .addComponent(comboMainCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel4)
                            .addComponent(jLabel1)
                            .addComponent(txtItemCode)
                            .addComponent(txtbarcode)
                            .addComponent(jLabel2)
                            .addComponent(txtItemName)
                            .addComponent(jLabel3)
                            .addComponent(txtReorderLevel, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(36, Short.MAX_VALUE))
        );
        panelLayout.setVerticalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLayout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtbarcode, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtItemName, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblMainCategory)
                .addGap(7, 7, 7)
                .addComponent(comboMainCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtItemCode, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtReorderLevel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(197, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtbarcodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtbarcodeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtbarcodeActionPerformed

    private void txtbarcodeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbarcodeKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtItemName.requestFocus();
        }
    }//GEN-LAST:event_txtbarcodeKeyReleased

    private void txtItemNameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtItemNameKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtItemCode.requestFocus();
        }
    }//GEN-LAST:event_txtItemNameKeyReleased

    private void txtItemCodeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtItemCodeKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtReorderLevel.requestFocus();
        }
    }//GEN-LAST:event_txtItemCodeKeyReleased

    private void txtReorderLevelKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtReorderLevelKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtReorderLevel.setText(Integer.toString(Validations.getIntOrZeroFromString(txtReorderLevel.getText())));
            btnUpdate.requestFocus();
        }
    }//GEN-LAST:event_txtReorderLevelKeyReleased

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        try {
            MethodStatus success = ItemControl.updateItem(id, txtbarcode.getText(), txtItemName.getText(),
                    txtReorderLevel.getText(), comboMainCategory.getSelectedItem().toString());
            if (success == MethodStatus.SUCCESS) {
                JOptionPane.showMessageDialog(this, "Item updated successfully !", "Success", JOptionPane.PLAIN_MESSAGE);
                this.dispose();
            }
        } catch (SQLException ex) {
            Logger.getLogger(EditItem.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void comboMainCategoryPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_comboMainCategoryPopupMenuWillBecomeInvisible
        // TODO add your handling code here:
    }//GEN-LAST:event_comboMainCategoryPopupMenuWillBecomeInvisible

    private void comboMainCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboMainCategoryActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboMainCategoryActionPerformed

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
            java.util.logging.Logger.getLogger(EditItem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EditItem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EditItem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EditItem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                EditItem dialog = new EditItem(new javax.swing.JFrame(), true, 1);
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
    private javax.swing.JButton btnUpdate;
    private javax.swing.JComboBox<String> comboMainCategory;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel lblMainCategory;
    private javax.swing.JPanel panel;
    private javax.swing.JTextField txtItemCode;
    private javax.swing.JTextField txtItemName;
    private javax.swing.JTextField txtReorderLevel;
    private javax.swing.JTextField txtbarcode;
    // End of variables declaration//GEN-END:variables
}
