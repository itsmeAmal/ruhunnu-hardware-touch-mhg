/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cazzendra.pos.ui;

import cazzendra.pos.control.CommonController;
import cazzendra.pos.control.ItemControl;
import cazzendra.pos.core.Loading;
import cazzendra.pos.core.MethodStatus;
import cazzendra.pos.core.Validations;
import cazzendra.pos.model.Item;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.AbstractAction;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

/**
 *
 * @author personal
 */
public class PriceUpdateJFrame extends javax.swing.JFrame {

    private String Lang;
    int id;
    private Item item;

    /**
     * Creates new form PriceUpdateJFrame
     * @param Language
     */
    public PriceUpdateJFrame(String Language) {
        initComponents();
        setDefaults();
        panel.setBackground(Loading.getColorCode());
        HotKeys();
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
    }

    private void searchByItemCode() {
        try {
            if (!ItemControl.isExistingItemCode(txtItemCode.getText())) {
                item = ItemControl.getItemByItemBarcodeOrItemCode(txtItemCode.getText());
                lblItemNameValue.setText(item.getItemName());
                lblItemPurchasePriceValue.setText(Validations.formatWithTwoDigits(item.getPurchasePrice().toString()));
                lblItemRetailPriceValue.setText(Validations.formatWithTwoDigits(item.getSellingPrice().toString()));
                txtNewPrice.requestFocus();
                txtNewPrice.selectAll();
            }
        } catch (SQLException ex) {
            Logger.getLogger(PriceUpdate.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void updateItemRetailPrice() {
        try {
            MethodStatus status = ItemControl.updateItemPrice(txtNewPrice.getText(), item.getItemId());
            if (status == MethodStatus.SUCCESS) {
                JOptionPane.showConfirmDialog(this, "Price Updated Successfully !", "Success", JOptionPane.PLAIN_MESSAGE);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PriceUpdate.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void setDefaults() {
        txtNewPrice.setText("0.00");
        lblItemNameValue.setText("");
        lblItemPurchasePriceValue.setText("");
        lblItemRetailPriceValue.setText("");
        txtNewPrice.setText("");
        txtItemCode.setText("");
        Loading.customJButton(Loading.getColorR(), Loading.getColorG(), Loading.getColorB(), btnItemSearch);
        Loading.customJButton(Loading.getColorR(), Loading.getColorG(), Loading.getColorB(), btnSave);
        item = null;
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
                    Logger.getLogger(PriceUpdateJFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
                item = ItemControl.getItemByItemId(itemId);
                lblItemNameValue.setText(item.getItemName());
                txtItemCode.setText(item.getBarcode());
                lblItemPurchasePriceValue.setText(item.getPurchasePrice().toString());
                lblItemRetailPriceValue.setText(item.getSellingPrice().toString());
            } else {
                setDefaults();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Invoice.class.getName()).log(Level.SEVERE, null, ex);
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
        txtNewPrice = new javax.swing.JTextField();
        lblItemRetailPrice = new javax.swing.JLabel();
        btnSave = new javax.swing.JButton();
        txtItemCode = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        lblItemPurchasePrice = new javax.swing.JLabel();
        lblItemNameValue = new javax.swing.JLabel();
        lblItemPurchasePriceValue = new javax.swing.JLabel();
        lblItemRetailPriceValue = new javax.swing.JLabel();
        lblItemRetailPrice1 = new javax.swing.JLabel();
        btnItemSearch = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Price Update");

        txtNewPrice.setFont(new java.awt.Font("Ubuntu", 1, 18)); // NOI18N
        txtNewPrice.setForeground(new java.awt.Color(255, 0, 0));
        txtNewPrice.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtNewPrice.setToolTipText("Item Name");
        txtNewPrice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNewPriceActionPerformed(evt);
            }
        });
        txtNewPrice.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNewPriceKeyReleased(evt);
            }
        });

        lblItemRetailPrice.setFont(new java.awt.Font("Ubuntu Medium", 0, 18)); // NOI18N
        lblItemRetailPrice.setForeground(new java.awt.Color(0, 0, 102));
        lblItemRetailPrice.setText("Item Retail Price");

        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cazzendra/pos/newtheme/icons/save-32.png"))); // NOI18N
        btnSave.setToolTipText("Update Item Price");
        btnSave.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        txtItemCode.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
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

        jLabel7.setFont(new java.awt.Font("Ubuntu Medium", 0, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 102));
        jLabel7.setText("Item Code");

        lblItemPurchasePrice.setFont(new java.awt.Font("Ubuntu Medium", 0, 18)); // NOI18N
        lblItemPurchasePrice.setForeground(new java.awt.Color(0, 0, 102));
        lblItemPurchasePrice.setText("Item Last Purchase Price");

        lblItemNameValue.setFont(new java.awt.Font("Ubuntu Medium", 1, 18)); // NOI18N
        lblItemNameValue.setForeground(new java.awt.Color(255, 0, 0));
        lblItemNameValue.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        lblItemPurchasePriceValue.setFont(new java.awt.Font("Ubuntu Medium", 1, 18)); // NOI18N
        lblItemPurchasePriceValue.setForeground(new java.awt.Color(255, 0, 0));
        lblItemPurchasePriceValue.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        lblItemRetailPriceValue.setFont(new java.awt.Font("Ubuntu Medium", 1, 18)); // NOI18N
        lblItemRetailPriceValue.setForeground(new java.awt.Color(255, 0, 0));
        lblItemRetailPriceValue.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        lblItemRetailPrice1.setFont(new java.awt.Font("Ubuntu Medium", 0, 18)); // NOI18N
        lblItemRetailPrice1.setForeground(new java.awt.Color(0, 0, 102));
        lblItemRetailPrice1.setText("New Retail Price");

        btnItemSearch.setBackground(new java.awt.Color(0, 0, 102));
        btnItemSearch.setFont(new java.awt.Font("Ubuntu Medium", 1, 18)); // NOI18N
        btnItemSearch.setForeground(new java.awt.Color(255, 255, 255));
        btnItemSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cazzendra/pos/newtheme/icons/search-9-32.png"))); // NOI18N
        btnItemSearch.setToolTipText("Search");
        btnItemSearch.setBorder(null);
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

        javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblItemNameValue, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblItemRetailPrice, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblItemPurchasePrice, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtNewPrice)
                            .addComponent(lblItemPurchasePriceValue, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblItemRetailPriceValue, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblItemRetailPrice1, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panelLayout.createSequentialGroup()
                        .addComponent(txtItemCode, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnItemSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(29, Short.MAX_VALUE))
        );
        panelLayout.setVerticalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelLayout.createSequentialGroup()
                        .addComponent(txtItemCode, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblItemNameValue, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(lblItemPurchasePrice, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblItemPurchasePriceValue, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblItemRetailPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblItemRetailPriceValue, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblItemRetailPrice1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtNewPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnItemSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(293, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtNewPriceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNewPriceActionPerformed
        btnSave.requestFocus();
    }//GEN-LAST:event_txtNewPriceActionPerformed

    private void txtNewPriceKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNewPriceKeyReleased

    }//GEN-LAST:event_txtNewPriceKeyReleased

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        updateItemRetailPrice();
        setDefaults();
    }//GEN-LAST:event_btnSaveActionPerformed

    private void txtItemCodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtItemCodeActionPerformed
        searchByItemCode();
    }//GEN-LAST:event_txtItemCodeActionPerformed

    private void txtItemCodeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtItemCodeKeyReleased

    }//GEN-LAST:event_txtItemCodeKeyReleased

    private void btnItemSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnItemSearchActionPerformed
        itemSearchAndSetDetails();
    }//GEN-LAST:event_btnItemSearchActionPerformed

    private void btnItemSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnItemSearchKeyReleased

    }//GEN-LAST:event_btnItemSearchKeyReleased

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
            java.util.logging.Logger.getLogger(PriceUpdateJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PriceUpdateJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PriceUpdateJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PriceUpdateJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PriceUpdateJFrame("Lang").setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnItemSearch;
    private javax.swing.JButton btnSave;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel lblItemNameValue;
    private javax.swing.JLabel lblItemPurchasePrice;
    private javax.swing.JLabel lblItemPurchasePriceValue;
    private javax.swing.JLabel lblItemRetailPrice;
    private javax.swing.JLabel lblItemRetailPrice1;
    private javax.swing.JLabel lblItemRetailPriceValue;
    private javax.swing.JPanel panel;
    private javax.swing.JTextField txtItemCode;
    private javax.swing.JTextField txtNewPrice;
    // End of variables declaration//GEN-END:variables
}