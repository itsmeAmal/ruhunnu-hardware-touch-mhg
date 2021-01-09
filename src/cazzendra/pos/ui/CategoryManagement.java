/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cazzendra.pos.ui;

import cazzendra.pos.control.CategoryControl;
import cazzendra.pos.control.CommonController;
import cazzendra.pos.control.SubCategoryControl;
import cazzendra.pos.core.CommonConstants;
import cazzendra.pos.core.Loading;
import cazzendra.pos.core.Validations;
import cazzendra.pos.model.Category;
import java.awt.Color;
import java.awt.Font;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Amal
 */
public class CategoryManagement extends javax.swing.JFrame {

    private String Lang;
    private static Category CategoryGlb = new Category();

    /**
     * Creates new form ItemManagement
     *
     * @param Language
     */
    public CategoryManagement(String Language) {
        initComponents();
        loadDataToTable();
        txtSubCategoryName.requestFocus();
        panel.setBackground(Loading.getColorCode());
        setDefaults();
        this.Lang = Language;
        CategoryGlb = null;
    }

    private void loadDataToTable() {
        try {
            String[] columnList = {"sub_category_id", "sub_category_name", "sub_category_detail"};
            ResultSet rset = SubCategoryControl.GetAllSubCategories();
            CommonController.loadDataToTable(tblSubCategories, rset, columnList);
        } catch (SQLException ex) {
            Logger.getLogger(CategoryManagement.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void clearDetails() {
        txtSubCategoryName.setText(null);
        txtSubCategoryName.requestFocus();
        txtSubCategoryDetail.setText(null);
        txtMainCategory.setText(null);
    }

    private void setDefaults() {

        LoadCategoryDataToTable();

        List<JButton> btnList = new ArrayList<>();
        btnList.add(btnSaveCategory);
        btnList.add(btnEditMainCategory);
        btnList.add(btAddSubCategory);
        btnList.add(btnEditSubCategory);
        Loading.customJButtonList(Loading.getColorR(), Loading.getColorG(), Loading.getColorB(), btnList);
    }

    private void AddSubCategory() {
        int Option = JOptionPane.showConfirmDialog(this, "Do you want to add Sub Category ?");
        if (Option == JOptionPane.YES_OPTION) {
            try {
                if (txtSubCategoryName.getText().equalsIgnoreCase("") || txtSubCategoryName.getText() == null) {
                    JOptionPane.showMessageDialog(this, "Please fill all fields !");
                    return;
                }
                boolean status = SubCategoryControl.AddSubCategory(1, txtSubCategoryName.getText().trim(), txtSubCategoryDetail.getText().trim(), "Bearing");
                if (status) {
                    JOptionPane.showMessageDialog(this, "Sub Category added successfully");
                }
            } catch (SQLException ex) {
                Logger.getLogger(CategoryManagement.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void AddCategory() {
        int Option = JOptionPane.showConfirmDialog(this, "Do you want to add Main Category ?");
        if (Option == JOptionPane.YES_OPTION) {
            try {
                if (txtMainCategory.getText().equalsIgnoreCase("") || txtMainCategory.getText() == null) {
                    JOptionPane.showMessageDialog(this, "Please enter category name !");
                    return;
                }
                boolean status = CategoryControl.AddCategory(txtMainCategory.getText().trim(), "");

                if (status) {
                    JOptionPane.showMessageDialog(this, "Main Category added successfully");
                }
            } catch (SQLException ex) {
                Logger.getLogger(CategoryManagement.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void LoadCategoryDataToTable() {
        try {
            String[] ColumnList = {"main_category_id", "main_category_name"};
            ResultSet Rset = CategoryControl.GetAllCategories();
            CommonController.loadDataToTable(tblMainCategory, Rset, ColumnList);
        } catch (SQLException ex) {
            Logger.getLogger(CategoryManagement.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void SearchCategoryByName() {
        try {
            String[] ColumnList = {"main_category_id", "main_category_name"};
            ResultSet Rset = CategoryControl.GetCategoryByOneAttribute("main_category_name",
                    CommonConstants.sql.LIKE, "%" + txtSearchCategory.getText().trim() + "%");
            CommonController.loadDataToTable(tblMainCategory, Rset, ColumnList);
        } catch (SQLException ex) {
            Logger.getLogger(CategoryManagement.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void UpdateCategoryValidaton() {
        DefaultTableModel dtm = (DefaultTableModel) tblMainCategory.getModel();
        int CategoryId = 0;
        if (tblMainCategory.getSelectedRow() != -1) {
            CategoryId = Validations.getIntOrZeroFromString(dtm.getValueAt(tblMainCategory.getSelectedRow(), 0).toString());
            if (CategoryId != 0) {
                CategoryGlb = new Category();
                CategoryGlb.setId(CategoryId);
                txtMainCategory.setText(dtm.getValueAt(tblMainCategory.getSelectedRow(), 1).toString());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select category you want to update !");
        }
    }

    private void UpdateCategory() {
        int Option = JOptionPane.showConfirmDialog(this, "Do you want to update Main Category ?");
        if (Option == JOptionPane.YES_OPTION) {
            try {
                if (txtMainCategory.getText().equalsIgnoreCase("") || txtMainCategory.getText() == null) {
                    JOptionPane.showMessageDialog(this, "Please enter category name !");
                    return;
                }
                boolean status = CategoryControl.UpdateCategory(txtMainCategory.getText().trim(), "", CategoryGlb.getId());
                CategoryGlb.setId(0);
                if (status) {
                    JOptionPane.showMessageDialog(this, "Main Category updated successfully");
                }
            } catch (SQLException ex) {
                Logger.getLogger(CategoryManagement.class.getName()).log(Level.SEVERE, null, ex);
            }
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
        txtSubCategoryName = new javax.swing.JTextField();
        btAddSubCategory = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSubCategories = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        txtSubCategoryDetail = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtMainCategory = new javax.swing.JTextField();
        btnEditMainCategory = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblMainCategory = new javax.swing.JTable();
        btnSaveCategory = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        btnEditSubCategory = new javax.swing.JButton();
        txtSearchCategory = new javax.swing.JTextField();
        txtSearchSubCategory = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        btnSavePayment = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Category Management");
        setAlwaysOnTop(true);
        setMaximumSize(new java.awt.Dimension(384, 549));
        setMinimumSize(new java.awt.Dimension(384, 549));
        setResizable(false);

        panel.setMaximumSize(new java.awt.Dimension(1358, 766));
        panel.setMinimumSize(new java.awt.Dimension(1358, 766));
        panel.setPreferredSize(new java.awt.Dimension(1358, 766));
        panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtSubCategoryName.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        txtSubCategoryName.setToolTipText("User Name");
        txtSubCategoryName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSubCategoryNameActionPerformed(evt);
            }
        });
        txtSubCategoryName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSubCategoryNameKeyReleased(evt);
            }
        });
        panel.add(txtSubCategoryName, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 60, 310, 40));

        btAddSubCategory.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cazzendra/pos/newtheme/icons/save-32.png"))); // NOI18N
        btAddSubCategory.setToolTipText("Add User");
        btAddSubCategory.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btAddSubCategory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAddSubCategoryActionPerformed(evt);
            }
        });
        panel.add(btAddSubCategory, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 220, 40, 40));

        jLabel2.setFont(new java.awt.Font("Ubuntu Medium", 0, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 102));
        jLabel2.setText("Main Category");
        panel.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 110, -1, -1));

        jLabel7.setFont(new java.awt.Font("Ubuntu Medium", 0, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 102));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Search Sub Category");
        panel.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 290, 180, -1));

        tblSubCategories.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        tblSubCategories.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Sub Category Id", "Sub Category Name", "Detail"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblSubCategories.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tblSubCategories.getTableHeader().setFont(new Font("Ubuntu", Font.BOLD, 18));
        tblSubCategories.getTableHeader().setOpaque(false);
        tblSubCategories.getTableHeader().setBackground(new Color(0, 0, 102));
        tblSubCategories.getTableHeader().setForeground(new Color(255, 255, 255));
        tblSubCategories.setRowHeight(22);
        tblSubCategories.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tblSubCategories);
        if (tblSubCategories.getColumnModel().getColumnCount() > 0) {
            tblSubCategories.getColumnModel().getColumn(0).setMinWidth(0);
            tblSubCategories.getColumnModel().getColumn(0).setPreferredWidth(0);
            tblSubCategories.getColumnModel().getColumn(0).setMaxWidth(0);
            tblSubCategories.getColumnModel().getColumn(1).setResizable(false);
            tblSubCategories.getColumnModel().getColumn(2).setResizable(false);
            tblSubCategories.getColumnModel().getColumn(2).setHeaderValue("Detail");
        }

        panel.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 370, 460, 250));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cazzendra/pos/ui/icons/edit_item.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        panel.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1280, 30, 50, 50));

        txtSubCategoryDetail.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        txtSubCategoryDetail.setToolTipText("User Name");
        txtSubCategoryDetail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSubCategoryDetailActionPerformed(evt);
            }
        });
        txtSubCategoryDetail.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSubCategoryDetailKeyReleased(evt);
            }
        });
        panel.add(txtSubCategoryDetail, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 220, 310, 40));

        jLabel3.setFont(new java.awt.Font("Ubuntu Medium", 0, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 102));
        jLabel3.setText("Sub Category Detail");
        panel.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 190, -1, -1));

        txtMainCategory.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        txtMainCategory.setToolTipText("");
        txtMainCategory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMainCategoryActionPerformed(evt);
            }
        });
        txtMainCategory.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtMainCategoryKeyReleased(evt);
            }
        });
        panel.add(txtMainCategory, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, 300, 40));

        btnEditMainCategory.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cazzendra/pos/newtheme/icons/edit-9-32.png"))); // NOI18N
        btnEditMainCategory.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEditMainCategory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditMainCategoryActionPerformed(evt);
            }
        });
        panel.add(btnEditMainCategory, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 160, 40, 40));

        tblMainCategory.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        tblMainCategory.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Category Id", "Category Name"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblMainCategory.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tblMainCategory.setRowHeight(22);
        tblMainCategory.getTableHeader().setFont(new Font("Ubuntu", Font.BOLD, 18));
        tblMainCategory.getTableHeader().setOpaque(false);
        tblMainCategory.getTableHeader().setBackground(new Color(0, 0, 102));
        tblMainCategory.getTableHeader().setForeground(new Color(255, 255, 255));
        tblMainCategory.getTableHeader().setReorderingAllowed(false);
        tblMainCategory.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblMainCategoryMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblMainCategory);
        if (tblMainCategory.getColumnModel().getColumnCount() > 0) {
            tblMainCategory.getColumnModel().getColumn(0).setMinWidth(0);
            tblMainCategory.getColumnModel().getColumn(0).setPreferredWidth(0);
            tblMainCategory.getColumnModel().getColumn(0).setMaxWidth(0);
            tblMainCategory.getColumnModel().getColumn(1).setResizable(false);
        }

        panel.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 210, 460, 410));

        btnSaveCategory.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cazzendra/pos/newtheme/icons/save-32.png"))); // NOI18N
        btnSaveCategory.setToolTipText("Add User");
        btnSaveCategory.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSaveCategory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveCategoryActionPerformed(evt);
            }
        });
        panel.add(btnSaveCategory, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 60, 40, 40));

        panel.add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 140, 310, 40));

        jLabel4.setFont(new java.awt.Font("Ubuntu Medium", 0, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 102));
        jLabel4.setText("Sub Category Name");
        panel.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 30, -1, -1));

        btnEditSubCategory.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cazzendra/pos/newtheme/icons/edit-9-32.png"))); // NOI18N
        btnEditSubCategory.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        panel.add(btnEditSubCategory, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 220, 40, 40));

        txtSearchCategory.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        txtSearchCategory.setToolTipText("");
        txtSearchCategory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchCategoryActionPerformed(evt);
            }
        });
        txtSearchCategory.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchCategoryKeyReleased(evt);
            }
        });
        panel.add(txtSearchCategory, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 160, 300, 40));

        txtSearchSubCategory.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        txtSearchSubCategory.setToolTipText("User Name");
        txtSearchSubCategory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchSubCategoryActionPerformed(evt);
            }
        });
        txtSearchSubCategory.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchSubCategoryKeyReleased(evt);
            }
        });
        panel.add(txtSearchSubCategory, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 320, 300, 40));

        jLabel8.setFont(new java.awt.Font("Ubuntu Medium", 0, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 102));
        jLabel8.setText("Main Category Name");
        panel.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, -1, -1));

        jLabel9.setFont(new java.awt.Font("Ubuntu Medium", 0, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 102));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel9.setText("Search Main Category");
        panel.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 130, 200, -1));

        btnSavePayment.setBackground(new java.awt.Color(0, 0, 102));
        btnSavePayment.setFont(new java.awt.Font("Ubuntu Medium", 1, 18)); // NOI18N
        btnSavePayment.setForeground(new java.awt.Color(255, 255, 255));
        btnSavePayment.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cazzendra/pos/newtheme/icons/facebook-like-32.png"))); // NOI18N
        btnSavePayment.setToolTipText("Add payment");
        btnSavePayment.setBorder(null);
        btnSavePayment.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSavePayment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSavePaymentActionPerformed(evt);
            }
        });
        panel.add(btnSavePayment, new org.netbeans.lib.awtextra.AbsoluteConstraints(892, 640, 100, 40));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, 517, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, 694, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btAddSubCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAddSubCategoryActionPerformed
        AddSubCategory();
        loadDataToTable();
        clearDetails();
    }//GEN-LAST:event_btAddSubCategoryActionPerformed

    private void txtSubCategoryNameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSubCategoryNameKeyReleased

    }//GEN-LAST:event_txtSubCategoryNameKeyReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

    }//GEN-LAST:event_jButton1ActionPerformed

    private void txtSubCategoryNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSubCategoryNameActionPerformed

    }//GEN-LAST:event_txtSubCategoryNameActionPerformed

    private void txtSubCategoryDetailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSubCategoryDetailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSubCategoryDetailActionPerformed

    private void txtSubCategoryDetailKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSubCategoryDetailKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSubCategoryDetailKeyReleased

    private void txtMainCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMainCategoryActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMainCategoryActionPerformed

    private void txtMainCategoryKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMainCategoryKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMainCategoryKeyReleased

    private void btnSaveCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveCategoryActionPerformed
        if (CategoryGlb == null) {
            try {
                System.out.println(CategoryControl.IsCategoryNameExist(txtMainCategory.getText().trim()));
                if (CategoryControl.IsCategoryNameExist(txtMainCategory.getText().trim())) {
                    JOptionPane.showMessageDialog(this, "Already registered category !", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                AddCategory();
                CategoryGlb = null;
            } catch (SQLException ex) {
                Logger.getLogger(CategoryManagement.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                if (CategoryControl.IsCategoryNameExist(txtMainCategory.getText().trim())) {
                    JOptionPane.showMessageDialog(this, "Same category name!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                UpdateCategory();
                CategoryGlb = null;
            } catch (SQLException ex) {
                Logger.getLogger(CategoryManagement.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        LoadCategoryDataToTable();
        clearDetails();
    }//GEN-LAST:event_btnSaveCategoryActionPerformed

    private void txtSearchCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchCategoryActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchCategoryActionPerformed

    private void txtSearchCategoryKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchCategoryKeyReleased
        SearchCategoryByName();
    }//GEN-LAST:event_txtSearchCategoryKeyReleased

    private void txtSearchSubCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchSubCategoryActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchSubCategoryActionPerformed

    private void txtSearchSubCategoryKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchSubCategoryKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchSubCategoryKeyReleased

    private void btnSavePaymentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSavePaymentActionPerformed

    }//GEN-LAST:event_btnSavePaymentActionPerformed

    private void tblMainCategoryMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblMainCategoryMouseClicked
        DefaultTableModel dtm = (DefaultTableModel) tblMainCategory.getModel();
        if (tblMainCategory.getSelectedRow() != -1) {

        }
    }//GEN-LAST:event_tblMainCategoryMouseClicked

    private void btnEditMainCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditMainCategoryActionPerformed
        UpdateCategoryValidaton();
    }//GEN-LAST:event_btnEditMainCategoryActionPerformed

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
            java.util.logging.Logger.getLogger(CategoryManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CategoryManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CategoryManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CategoryManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new CategoryManagement("").setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btAddSubCategory;
    private javax.swing.JButton btnEditMainCategory;
    private javax.swing.JButton btnEditSubCategory;
    private javax.swing.JButton btnSaveCategory;
    private javax.swing.JButton btnSavePayment;
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel panel;
    private javax.swing.JTable tblMainCategory;
    private javax.swing.JTable tblSubCategories;
    private javax.swing.JTextField txtMainCategory;
    private javax.swing.JTextField txtSearchCategory;
    private javax.swing.JTextField txtSearchSubCategory;
    private javax.swing.JTextField txtSubCategoryDetail;
    private javax.swing.JTextField txtSubCategoryName;
    // End of variables declaration//GEN-END:variables
}
