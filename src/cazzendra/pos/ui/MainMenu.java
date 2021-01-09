/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cazzendra.pos.ui;

import cazzendra.pos.control.CommonController;
import cazzendra.pos.core.Loading;
import cazzendra.pos.core.Options;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.KeyStroke;
import net.sf.jasperreports.engine.JRException;

/**
 *
 * @author Amal
 */
public class MainMenu extends javax.swing.JFrame {

    private String Lang;

    /**
     * @param Language
     */
    public MainMenu(String Language) {
        initComponents();
        this.Lang = Language;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now();
        lblDateTime.setText(dtf.format(now));
        setDefaults();
        panel.setBackground(Loading.getColorCode());
        pnlQuickAccess.setBackground(Loading.getColorCode());
        configurePrevilages();
        lblLoggedUser.setText(Loading.getUser().getUserName());
        HotKeys();
        lblCashieBalance.setVisible(false);
        SetLanguageUi();
        CustomizeUi();
    }

    private void SetLanguageUi() {
        String FolderName = "mainmenu";
        if (Lang.equalsIgnoreCase(Options.LANG_ENGLISH)) {
            new Loading().CustomLangImage(FolderName, "invoice", lblNameInvoice);
            new Loading().CustomLangImage(FolderName, "price-update", lblnamePriceUpdate);
            new Loading().CustomLangImage(FolderName, "grn", lblNameGrn);
            new Loading().CustomLangImage(FolderName, "invoice-history", lblInvoiceHistory);
            new Loading().CustomLangImage(FolderName, "user-management", lblNameUsrManagement);
            new Loading().CustomLangImage(FolderName, "item-management", lbItemManagement);
            new Loading().CustomLangImage(FolderName, "customer-management", lblCustomerManagement);
            new Loading().CustomLangImage(FolderName, "supplier-management", lblSupplierManagement);
            new Loading().CustomLangImage(FolderName, "category-management", lblNameCategoryManagement);
            new Loading().CustomLangImage(FolderName, "customer-account", lblCustomerAccounts);

            new Loading().CustomLangImage(FolderName, "current-stock-eng", lblCurrentStock);
            new Loading().CustomLangImage(FolderName, "re-order-english", lblReOrder);
            new Loading().CustomLangImage(FolderName, "item-report", lblItemReport);
            new Loading().CustomLangImage(FolderName, "grn-history-eng", lblGrnHistory);
            new Loading().CustomLangImage(FolderName, "sold-qty", lblSoldQty);
            new Loading().CustomLangImage(FolderName, "item-profit-eng", lblItemProfit);
//            new Loading().CustomLangImage(FolderName, "sales-and-income", lblCreditInvoices);

        } else if (Lang.equalsIgnoreCase(Options.LANG_SINHALA)) {
            new Loading().CustomLangImage(FolderName, "invoice-sinhala", lblNameInvoice);
            new Loading().CustomLangImage(FolderName, "price-update-sinhala", lblnamePriceUpdate);
            new Loading().CustomLangImage(FolderName, "grn-sinhala", lblNameGrn);
            new Loading().CustomLangImage(FolderName, "invoice-history-sinhala", lblInvoiceHistory);
            new Loading().CustomLangImage(FolderName, "user-management-sinhala", lblNameUsrManagement);
            new Loading().CustomLangImage(FolderName, "item-management-sinhala", lbItemManagement);
            new Loading().CustomLangImage(FolderName, "customer-management-sinhala", lblCustomerManagement);
            new Loading().CustomLangImage(FolderName, "supplier-management-sinhala", lblSupplierManagement);
            new Loading().CustomLangImage(FolderName, "category-management-sinhala", lblNameCategoryManagement);
            new Loading().CustomLangImage(FolderName, "customer-account-sinhala", lblCustomerAccounts);

            new Loading().CustomLangImage(FolderName, "current-stock", lblCurrentStock);
            new Loading().CustomLangImage(FolderName, "re-order", lblReOrder);
            new Loading().CustomLangImage(FolderName, "item-report-sinhala", lblItemReport);
            new Loading().CustomLangImage(FolderName, "grn-history-sinhala", lblGrnHistory);
            new Loading().CustomLangImage(FolderName, "sold-qty-sinhala", lblSoldQty);
            new Loading().CustomLangImage(FolderName, "item-profit", lblItemProfit);
            new Loading().CustomLangImage(FolderName, "sales-and-income-sinhala", lblCreditInvoices);
        }
    }

    private void setDefaults() {
        lblShopName.setText("<html>" + Loading.getSlogan() + "</html>");
        Loading.customJButton(Loading.getColorR(), Loading.getColorG(), Loading.getColorB(), btnLastPrint);
//        btnCloseApp.setContentAreaFilled(false);
//        btnCloseApp.setOpaque(true);
        btnCloseApp.setBackground(new java.awt.Color(0, 0, 102));

        lblCopyrightStatement.setText(Loading.getCopyrightStatement());
    }

    private void configurePrevilages() {
        lblGrn.setVisible(false);
        lblInvoice.setVisible(false);
        lblItemManagement.setVisible(false);
        lblPriceUpdate.setVisible(false);
        lblUserManagement.setVisible(false);

        lblNameGrn.setVisible(false);
        lblNameInvoice.setVisible(false);
        lbItemManagement.setVisible(false);
        lblnamePriceUpdate.setVisible(false);
        lblInvoiceHistoryIcon.setVisible(false);

        if (Loading.getUser().getType().equals(Options.getAdmin())) {
            lblGrn.setVisible(true);
            lblInvoice.setVisible(true);
            lblItemManagement.setVisible(true);
            lblPriceUpdate.setVisible(true);
            lblUserManagement.setVisible(true);

            lblNameGrn.setVisible(true);
            lblNameInvoice.setVisible(true);
            lbItemManagement.setVisible(true);
            lblnamePriceUpdate.setVisible(true);
            lblInvoiceHistoryIcon.setVisible(true);

        } else if (Loading.getUser().getType().equals(Options.getManager())) {
            lblGrn.setVisible(false);
            lblInvoice.setVisible(false);
            lblItemManagement.setVisible(false);
            lblPriceUpdate.setVisible(false);
            lblUserManagement.setVisible(false);

            lblNameGrn.setVisible(false);
            lblNameInvoice.setVisible(false);
            lbItemManagement.setVisible(false);
            lblnamePriceUpdate.setVisible(false);
            lblInvoiceHistoryIcon.setVisible(false);

        } else if (Loading.getUser().getType().equals(Options.getUser())) {
            lblGrn.setVisible(false);
            lblInvoice.setVisible(true);
            lblItemManagement.setVisible(false);
            lblPriceUpdate.setVisible(false);
            lblUserManagement.setVisible(false);

            lblNameGrn.setVisible(false);
            lblNameInvoice.setVisible(true);
            lbItemManagement.setVisible(false);
            lblnamePriceUpdate.setVisible(false);
            lblInvoiceHistoryIcon.setVisible(false);
        }
    }

    private void CustomizeUi() {

        panel.remove(pnlQuickAccess);
        panel.add(pnlQuickAccess, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, 910, 580));

        int LabelLeft = 8;
        int IconLeft = 38;
        int IconTop = 10;
        int LabelTop = 145;
        int LabelHeight = 50;
        int IconHeight = 130;
        int LabelWidth = 184;
        int IconLeftGap = 50;
        int IconTopGap = 20;

        pnlQuickAccess.remove(lblInvoice);
        pnlQuickAccess.remove(lblNameInvoice);
        pnlQuickAccess.remove(lblPriceUpdate);
        pnlQuickAccess.remove(lblnamePriceUpdate);
        pnlQuickAccess.remove(lblGrn);
        pnlQuickAccess.remove(lblNameGrn);
        pnlQuickAccess.remove(lblInvoiceHistoryIcon);
        pnlQuickAccess.remove(lblInvoiceHistory);
        pnlQuickAccess.remove(lblCategoryManagement);
        pnlQuickAccess.remove(lblNameCategoryManagement);
        pnlQuickAccess.remove(lblItemManagement);
        pnlQuickAccess.remove(lbItemManagement);
        pnlQuickAccess.remove(lblCustomerManagementIcon);
        pnlQuickAccess.remove(lblCustomerManagement);
        pnlQuickAccess.remove(lblUserManagement);
        pnlQuickAccess.remove(lblNameUsrManagement);
        pnlQuickAccess.remove(lblCustomerAcc);
        pnlQuickAccess.remove(lblCustomerAccounts);

        PanelReports.remove(lblReOrder);
        PanelReports.remove(lblCurrentStock);
        PanelReports.remove(lblGrnHistory);
        PanelReports.remove(lblItemReport);
        PanelReports.remove(lblSoldQty);
        PanelReports.remove(lblItemProfit);
        PanelReports.remove(lblCreditInvoices);
        PanelReports.remove(lblCashieBalance);

        int Left = 6;
        int Top = 10;
        int Width = 270;
        int Height = 40;
        int Gap = 50;

        panel.remove(PanelReports);
        panel.remove(lblShopName);
        panel.remove(lblCloudRevelLogo);
        panel.remove(lblCopyrightStatement);

        panel.add(PanelReports, new org.netbeans.lib.awtextra.AbsoluteConstraints(1040, 300, 280, 390));

        panel.add(lblShopName, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 149, 308, 60));

        panel.add(lblCloudRevelLogo, new org.netbeans.lib.awtextra.AbsoluteConstraints(1184, 101, 140, 147));

        panel.add(lblCopyrightStatement, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 670, 1340, 20));

        PanelReports.add(lblReOrder, new org.netbeans.lib.awtextra.AbsoluteConstraints(Left, Top, Width, Height));
        Top += Gap;
        PanelReports.add(lblCurrentStock, new org.netbeans.lib.awtextra.AbsoluteConstraints(Left, Top, Width, Height));
        Top += Gap;
        PanelReports.add(lblGrnHistory, new org.netbeans.lib.awtextra.AbsoluteConstraints(Left, Top, Width, Height));
        Top += Gap;
        PanelReports.add(lblItemReport, new org.netbeans.lib.awtextra.AbsoluteConstraints(Left, Top, Width, Height));
        Top += Gap;
        PanelReports.add(lblSoldQty, new org.netbeans.lib.awtextra.AbsoluteConstraints(Left, Top, Width, Height));
        Top += Gap;
        PanelReports.add(lblItemProfit, new org.netbeans.lib.awtextra.AbsoluteConstraints(Left, Top, Width, Height));
        Top += Gap;
        PanelReports.add(lblCreditInvoices, new org.netbeans.lib.awtextra.AbsoluteConstraints(Left, Top, Width, Height));
        Top += Gap;
        PanelReports.add(lblCashieBalance, new org.netbeans.lib.awtextra.AbsoluteConstraints(Left, Top, Width, Height));

        pnlQuickAccess.add(lblInvoice, new org.netbeans.lib.awtextra.AbsoluteConstraints(IconLeft, IconTop, IconHeight, IconHeight));
        pnlQuickAccess.add(lblNameInvoice, new org.netbeans.lib.awtextra.AbsoluteConstraints(LabelLeft, LabelTop, LabelWidth, LabelHeight));

        IconLeft = IconLeft + IconHeight + IconLeftGap;
        LabelLeft = LabelLeft - 1 + LabelWidth;
        pnlQuickAccess.add(lblInvoiceHistoryIcon, new org.netbeans.lib.awtextra.AbsoluteConstraints(IconLeft, IconTop, IconHeight, IconHeight));
        pnlQuickAccess.add(lblInvoiceHistory, new org.netbeans.lib.awtextra.AbsoluteConstraints(LabelLeft, LabelTop, LabelWidth, LabelHeight));

        IconLeft = IconLeft + IconHeight + IconLeftGap;
        LabelLeft = LabelLeft - 1 + LabelWidth;
        pnlQuickAccess.add(lblCustomerAcc, new org.netbeans.lib.awtextra.AbsoluteConstraints(IconLeft, IconTop, IconHeight, IconHeight));
        pnlQuickAccess.add(lblCustomerAccounts, new org.netbeans.lib.awtextra.AbsoluteConstraints(LabelLeft, LabelTop, LabelWidth, LabelHeight));

        IconLeft = IconLeft + IconHeight + IconLeftGap;
        LabelLeft = LabelLeft - 1 + LabelWidth;
        pnlQuickAccess.add(lblPriceUpdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(IconLeft, IconTop, IconHeight, IconHeight));
        pnlQuickAccess.add(lblnamePriceUpdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(LabelLeft, LabelTop, LabelWidth, LabelHeight));

        IconLeft = IconLeft + IconHeight + IconLeftGap;
        LabelLeft = LabelLeft - 1 + LabelWidth;
        pnlQuickAccess.add(lblGrn, new org.netbeans.lib.awtextra.AbsoluteConstraints(IconLeft, IconTop, IconHeight, IconHeight));
        pnlQuickAccess.add(lblNameGrn, new org.netbeans.lib.awtextra.AbsoluteConstraints(LabelLeft, LabelTop, LabelWidth, LabelHeight));

        //second line
        IconLeft = 40;
        LabelLeft = 10;

        IconTop = IconTop + 50 + LabelTop + IconTopGap;
        LabelTop = LabelTop - 10 + IconTop;

        pnlQuickAccess.add(lblCategoryManagement, new org.netbeans.lib.awtextra.AbsoluteConstraints(IconLeft, IconTop, IconHeight, IconHeight));
        pnlQuickAccess.add(lblNameCategoryManagement, new org.netbeans.lib.awtextra.AbsoluteConstraints(LabelLeft, LabelTop, LabelWidth, LabelHeight));

        IconLeft = IconLeft + IconHeight + IconLeftGap;
        LabelLeft = LabelLeft - 1 + LabelWidth;
        pnlQuickAccess.add(lblItemManagement, new org.netbeans.lib.awtextra.AbsoluteConstraints(IconLeft, IconTop, IconHeight, IconHeight));
        pnlQuickAccess.add(lbItemManagement, new org.netbeans.lib.awtextra.AbsoluteConstraints(LabelLeft, LabelTop, LabelWidth, LabelHeight));

        IconLeft = IconLeft + IconHeight + IconLeftGap;
        LabelLeft = LabelLeft - 1 + LabelWidth;
        pnlQuickAccess.add(lblCustomerManagementIcon, new org.netbeans.lib.awtextra.AbsoluteConstraints(IconLeft, IconTop, IconHeight, IconHeight));
        pnlQuickAccess.add(lblCustomerManagement, new org.netbeans.lib.awtextra.AbsoluteConstraints(LabelLeft, LabelTop, LabelWidth, LabelHeight));

        IconLeft = IconLeft + IconHeight + IconLeftGap;
        LabelLeft = LabelLeft - 1 + LabelWidth;
        pnlQuickAccess.add(lblUserManagement, new org.netbeans.lib.awtextra.AbsoluteConstraints(IconLeft, IconTop, IconHeight, IconHeight));
        pnlQuickAccess.add(lblNameUsrManagement, new org.netbeans.lib.awtextra.AbsoluteConstraints(LabelLeft, LabelTop, LabelWidth, LabelHeight));

        IconLeft = IconLeft + IconHeight + IconLeftGap;
        LabelLeft = LabelLeft - 1 + LabelWidth;
        pnlQuickAccess.add(lblSullierManagementImage, new org.netbeans.lib.awtextra.AbsoluteConstraints(IconLeft, IconTop, IconHeight, IconHeight));
        pnlQuickAccess.add(lblSupplierManagement, new org.netbeans.lib.awtextra.AbsoluteConstraints(LabelLeft, LabelTop, LabelWidth, LabelHeight));

    }

    private void HotKeys() {
        KeyStroke ks = KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0);
        InputMap im = btnCloseApp.getInputMap(JButton.WHEN_IN_FOCUSED_WINDOW);
        im.put(ks, "C");
        btnCloseApp.getActionMap().put("C", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                new Invoice(Lang).setVisible(true);
            }
        });
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
        pnlQuickAccess = new javax.swing.JPanel();
        lblInvoice = new javax.swing.JLabel();
        lblNameInvoice = new javax.swing.JLabel();
        lblPriceUpdate = new javax.swing.JLabel();
        lblnamePriceUpdate = new javax.swing.JLabel();
        lblGrn = new javax.swing.JLabel();
        lblInvoiceHistoryIcon = new javax.swing.JLabel();
        lblNameGrn = new javax.swing.JLabel();
        lblInvoiceHistory = new javax.swing.JLabel();
        lblNameCategoryManagement = new javax.swing.JLabel();
        lblCategoryManagement = new javax.swing.JLabel();
        lblItemManagement = new javax.swing.JLabel();
        lbItemManagement = new javax.swing.JLabel();
        lblCustomerManagementIcon = new javax.swing.JLabel();
        lblCustomerManagement = new javax.swing.JLabel();
        lblUserManagement = new javax.swing.JLabel();
        lblNameUsrManagement = new javax.swing.JLabel();
        lblSullierManagementImage = new javax.swing.JLabel();
        lblSupplierManagement = new javax.swing.JLabel();
        lblCustomerAcc = new javax.swing.JLabel();
        lblCustomerAccounts = new javax.swing.JLabel();
        lblShopName = new javax.swing.JLabel();
        lblCloudRevelLogo = new javax.swing.JLabel();
        PanelReports = new javax.swing.JPanel();
        lblReOrder = new javax.swing.JLabel();
        lblCurrentStock = new javax.swing.JLabel();
        lblGrnHistory = new javax.swing.JLabel();
        lblItemReport = new javax.swing.JLabel();
        lblSoldQty = new javax.swing.JLabel();
        lblItemProfit = new javax.swing.JLabel();
        lblCreditInvoices = new javax.swing.JLabel();
        lblCashieBalance = new javax.swing.JLabel();
        lblnamePriceUpdate2 = new javax.swing.JLabel();
        lblLoggedUser = new javax.swing.JLabel();
        lblDateTime = new javax.swing.JLabel();
        btnCloseApp = new javax.swing.JLabel();
        btnLastPrint = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        lblCopyrightStatement = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Main Menu");
        setBackground(new java.awt.Color(0, 0, 102));
        setMinimumSize(new java.awt.Dimension(1368, 718));
        setResizable(false);
        setSize(new java.awt.Dimension(1368, 718));

        panel.setMaximumSize(new java.awt.Dimension(1360, 600));
        panel.setMinimumSize(new java.awt.Dimension(1360, 600));
        panel.setPreferredSize(new java.awt.Dimension(1360, 600));
        panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlQuickAccess.setOpaque(false);
        pnlQuickAccess.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblInvoice.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblInvoice.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cazzendra/pos/ui/icons/invoice.png"))); // NOI18N
        lblInvoice.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblInvoice.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblInvoice.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblInvoiceMouseClicked(evt);
            }
        });
        pnlQuickAccess.add(lblInvoice, new org.netbeans.lib.awtextra.AbsoluteConstraints(33, 6, 130, 130));

        lblNameInvoice.setFont(new java.awt.Font("Ubuntu Medium", 1, 18)); // NOI18N
        lblNameInvoice.setForeground(new java.awt.Color(0, 0, 102));
        lblNameInvoice.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNameInvoice.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cloudrevel/language/src/mainmenu/invoice-sinhala.png"))); // NOI18N
        pnlQuickAccess.add(lblNameInvoice, new org.netbeans.lib.awtextra.AbsoluteConstraints(13, 142, 170, 51));

        lblPriceUpdate.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        lblPriceUpdate.setForeground(new java.awt.Color(255, 255, 255));
        lblPriceUpdate.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPriceUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cazzendra/pos/ui/icons/price.png"))); // NOI18N
        lblPriceUpdate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblPriceUpdate.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblPriceUpdate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblPriceUpdateMouseClicked(evt);
            }
        });
        pnlQuickAccess.add(lblPriceUpdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 10, 130, 130));

        lblnamePriceUpdate.setFont(new java.awt.Font("Ubuntu Medium", 1, 18)); // NOI18N
        lblnamePriceUpdate.setForeground(new java.awt.Color(0, 0, 102));
        lblnamePriceUpdate.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblnamePriceUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cloudrevel/language/src/mainmenu/price-update-sinhala.png"))); // NOI18N
        lblnamePriceUpdate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblnamePriceUpdateMouseClicked(evt);
            }
        });
        pnlQuickAccess.add(lblnamePriceUpdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 140, -1, 51));

        lblGrn.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblGrn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cazzendra/pos/ui/icons/grn.png"))); // NOI18N
        lblGrn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblGrn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblGrn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblGrnMouseClicked(evt);
            }
        });
        pnlQuickAccess.add(lblGrn, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 10, 130, 130));

        lblInvoiceHistoryIcon.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        lblInvoiceHistoryIcon.setForeground(new java.awt.Color(255, 255, 255));
        lblInvoiceHistoryIcon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblInvoiceHistoryIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cazzendra/pos/ui/icons/reports.png"))); // NOI18N
        lblInvoiceHistoryIcon.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblInvoiceHistoryIcon.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblInvoiceHistoryIcon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblInvoiceHistoryIconMouseClicked(evt);
            }
        });
        pnlQuickAccess.add(lblInvoiceHistoryIcon, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 10, 130, 130));

        lblNameGrn.setFont(new java.awt.Font("Ubuntu Medium", 1, 18)); // NOI18N
        lblNameGrn.setForeground(new java.awt.Color(0, 0, 102));
        lblNameGrn.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNameGrn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cloudrevel/language/src/mainmenu/grn-sinhala.png"))); // NOI18N
        pnlQuickAccess.add(lblNameGrn, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 150, -1, 51));

        lblInvoiceHistory.setFont(new java.awt.Font("Ubuntu Medium", 1, 18)); // NOI18N
        lblInvoiceHistory.setForeground(new java.awt.Color(0, 0, 102));
        lblInvoiceHistory.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblInvoiceHistory.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cloudrevel/language/src/mainmenu/invoice-history-sinhala.png"))); // NOI18N
        lblInvoiceHistory.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblInvoiceHistoryMouseClicked(evt);
            }
        });
        pnlQuickAccess.add(lblInvoiceHistory, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 150, 130, 51));

        lblNameCategoryManagement.setFont(new java.awt.Font("Ubuntu Medium", 1, 18)); // NOI18N
        lblNameCategoryManagement.setForeground(new java.awt.Color(0, 0, 102));
        lblNameCategoryManagement.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNameCategoryManagement.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cloudrevel/language/src/mainmenu/category-management-sinhala.png"))); // NOI18N
        lblNameCategoryManagement.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblNameCategoryManagementMouseClicked(evt);
            }
        });
        pnlQuickAccess.add(lblNameCategoryManagement, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 341, 180, 50));

        lblCategoryManagement.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        lblCategoryManagement.setForeground(new java.awt.Color(255, 255, 255));
        lblCategoryManagement.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCategoryManagement.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cazzendra/pos/ui/icons/sub_category_100x100.png"))); // NOI18N
        lblCategoryManagement.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblCategoryManagement.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblCategoryManagement.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblCategoryManagementMouseClicked(evt);
            }
        });
        pnlQuickAccess.add(lblCategoryManagement, new org.netbeans.lib.awtextra.AbsoluteConstraints(33, 205, 130, 130));

        lblItemManagement.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblItemManagement.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cazzendra/pos/ui/icons/item_management.png"))); // NOI18N
        lblItemManagement.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblItemManagement.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblItemManagement.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblItemManagementMouseClicked(evt);
            }
        });
        pnlQuickAccess.add(lblItemManagement, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 200, 130, 130));

        lbItemManagement.setFont(new java.awt.Font("Ubuntu Medium", 1, 18)); // NOI18N
        lbItemManagement.setForeground(new java.awt.Color(0, 0, 102));
        lbItemManagement.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbItemManagement.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cloudrevel/language/src/mainmenu/item-management-sinhala.png"))); // NOI18N
        pnlQuickAccess.add(lbItemManagement, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 340, 179, 51));

        lblCustomerManagementIcon.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        lblCustomerManagementIcon.setForeground(new java.awt.Color(255, 255, 255));
        lblCustomerManagementIcon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCustomerManagementIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cazzendra/pos/ui/icons/customer_new.png"))); // NOI18N
        lblCustomerManagementIcon.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblCustomerManagementIcon.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblCustomerManagementIcon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblCustomerManagementIconMouseClicked(evt);
            }
        });
        pnlQuickAccess.add(lblCustomerManagementIcon, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 200, 130, 130));

        lblCustomerManagement.setFont(new java.awt.Font("Ubuntu Medium", 1, 18)); // NOI18N
        lblCustomerManagement.setForeground(new java.awt.Color(0, 0, 102));
        lblCustomerManagement.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCustomerManagement.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cloudrevel/language/src/mainmenu/customer-management-sinhala.png"))); // NOI18N
        lblCustomerManagement.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblCustomerManagementMouseClicked(evt);
            }
        });
        pnlQuickAccess.add(lblCustomerManagement, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 340, -1, -1));

        lblUserManagement.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        lblUserManagement.setForeground(new java.awt.Color(255, 255, 255));
        lblUserManagement.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblUserManagement.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cazzendra/pos/ui/icons/userManagement.png"))); // NOI18N
        lblUserManagement.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblUserManagement.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblUserManagement.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblUserManagementMouseClicked(evt);
            }
        });
        pnlQuickAccess.add(lblUserManagement, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 200, 130, 130));

        lblNameUsrManagement.setFont(new java.awt.Font("Ubuntu Medium", 1, 18)); // NOI18N
        lblNameUsrManagement.setForeground(new java.awt.Color(0, 0, 102));
        lblNameUsrManagement.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNameUsrManagement.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cloudrevel/language/src/mainmenu/user-management-sinhala.png"))); // NOI18N
        lblNameUsrManagement.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblNameUsrManagementMouseClicked(evt);
            }
        });
        pnlQuickAccess.add(lblNameUsrManagement, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 340, -1, -1));

        lblSullierManagementImage.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        lblSullierManagementImage.setForeground(new java.awt.Color(255, 255, 255));
        lblSullierManagementImage.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSullierManagementImage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cazzendra/pos/ui/icons/supplier.png"))); // NOI18N
        lblSullierManagementImage.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblSullierManagementImage.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblSullierManagementImage.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSullierManagementImageMouseClicked(evt);
            }
        });
        pnlQuickAccess.add(lblSullierManagementImage, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 230, 100, 100));

        lblSupplierManagement.setFont(new java.awt.Font("Ubuntu Medium", 1, 18)); // NOI18N
        lblSupplierManagement.setForeground(new java.awt.Color(0, 0, 102));
        lblSupplierManagement.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSupplierManagement.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cloudrevel/language/src/mainmenu/supplier-management-sinhala.png"))); // NOI18N
        lblSupplierManagement.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSupplierManagementMouseClicked(evt);
            }
        });
        pnlQuickAccess.add(lblSupplierManagement, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 340, 100, -1));

        lblCustomerAcc.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCustomerAcc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cazzendra/pos/ui/icons/customer_account_new.png"))); // NOI18N
        lblCustomerAcc.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblCustomerAcc.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblCustomerAcc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblCustomerAccMouseClicked(evt);
            }
        });
        pnlQuickAccess.add(lblCustomerAcc, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 30, 124, 110));

        lblCustomerAccounts.setFont(new java.awt.Font("Ubuntu Medium", 1, 18)); // NOI18N
        lblCustomerAccounts.setForeground(new java.awt.Color(0, 0, 102));
        lblCustomerAccounts.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCustomerAccounts.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cloudrevel/language/src/mainmenu/customer-account-sinhala.png"))); // NOI18N
        pnlQuickAccess.add(lblCustomerAccounts, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 150, 124, 40));

        lblShopName.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        lblShopName.setForeground(new java.awt.Color(0, 0, 102));
        lblShopName.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        pnlQuickAccess.add(lblShopName, new org.netbeans.lib.awtextra.AbsoluteConstraints(896, 149, 308, 60));

        lblCloudRevelLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cazzendra/pos/ui/logo/cloud-revel-logo.png"))); // NOI18N
        pnlQuickAccess.add(lblCloudRevelLogo, new org.netbeans.lib.awtextra.AbsoluteConstraints(1210, 101, 140, 147));

        panel.add(pnlQuickAccess, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 1000, 570));

        PanelReports.setOpaque(false);
        PanelReports.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblReOrder.setBackground(new java.awt.Color(0, 0, 102));
        lblReOrder.setFont(new java.awt.Font("Ubuntu Medium", 1, 18)); // NOI18N
        lblReOrder.setForeground(new java.awt.Color(255, 255, 255));
        lblReOrder.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblReOrder.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cloudrevel/language/src/mainmenu/re-order.png"))); // NOI18N
        lblReOrder.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblReOrder.setMaximumSize(new java.awt.Dimension(270, 40));
        lblReOrder.setMinimumSize(new java.awt.Dimension(270, 40));
        lblReOrder.setOpaque(true);
        lblReOrder.setPreferredSize(new java.awt.Dimension(270, 40));
        lblReOrder.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblReOrderMouseClicked(evt);
            }
        });
        PanelReports.add(lblReOrder, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 16, 270, 40));

        lblCurrentStock.setBackground(new java.awt.Color(0, 0, 102));
        lblCurrentStock.setFont(new java.awt.Font("Ubuntu Medium", 1, 18)); // NOI18N
        lblCurrentStock.setForeground(new java.awt.Color(255, 255, 255));
        lblCurrentStock.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCurrentStock.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cloudrevel/language/src/mainmenu/current-stock.png"))); // NOI18N
        lblCurrentStock.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblCurrentStock.setOpaque(true);
        lblCurrentStock.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblCurrentStockMouseClicked(evt);
            }
        });
        PanelReports.add(lblCurrentStock, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 270, 40));

        lblGrnHistory.setBackground(new java.awt.Color(0, 0, 102));
        lblGrnHistory.setFont(new java.awt.Font("Ubuntu Medium", 1, 18)); // NOI18N
        lblGrnHistory.setForeground(new java.awt.Color(255, 255, 255));
        lblGrnHistory.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblGrnHistory.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cloudrevel/language/src/mainmenu/grn-history-sinhala.png"))); // NOI18N
        lblGrnHistory.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblGrnHistory.setOpaque(true);
        lblGrnHistory.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblGrnHistoryMouseClicked(evt);
            }
        });
        PanelReports.add(lblGrnHistory, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, -1, -1));

        lblItemReport.setBackground(new java.awt.Color(0, 0, 102));
        lblItemReport.setFont(new java.awt.Font("Ubuntu Medium", 1, 18)); // NOI18N
        lblItemReport.setForeground(new java.awt.Color(255, 255, 255));
        lblItemReport.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblItemReport.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cloudrevel/language/src/mainmenu/item-report-sinhala.png"))); // NOI18N
        lblItemReport.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblItemReport.setOpaque(true);
        lblItemReport.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblItemReportMouseClicked(evt);
            }
        });
        PanelReports.add(lblItemReport, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, -1, -1));

        lblSoldQty.setBackground(new java.awt.Color(0, 0, 102));
        lblSoldQty.setFont(new java.awt.Font("Ubuntu Medium", 1, 18)); // NOI18N
        lblSoldQty.setForeground(new java.awt.Color(255, 255, 255));
        lblSoldQty.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSoldQty.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cloudrevel/language/src/mainmenu/sold-qty-sinhala.png"))); // NOI18N
        lblSoldQty.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblSoldQty.setOpaque(true);
        lblSoldQty.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSoldQtyMouseClicked(evt);
            }
        });
        PanelReports.add(lblSoldQty, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, -1, -1));

        lblItemProfit.setBackground(new java.awt.Color(0, 102, 153));
        lblItemProfit.setFont(new java.awt.Font("Ubuntu Medium", 1, 18)); // NOI18N
        lblItemProfit.setForeground(new java.awt.Color(255, 255, 255));
        lblItemProfit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblItemProfit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cloudrevel/language/src/mainmenu/item-profit.png"))); // NOI18N
        lblItemProfit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblItemProfit.setOpaque(true);
        lblItemProfit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblItemProfitMouseClicked(evt);
            }
        });
        PanelReports.add(lblItemProfit, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, -1, -1));

        lblCreditInvoices.setBackground(new java.awt.Color(0, 102, 153));
        lblCreditInvoices.setFont(new java.awt.Font("Ubuntu Medium", 1, 18)); // NOI18N
        lblCreditInvoices.setForeground(new java.awt.Color(255, 255, 255));
        lblCreditInvoices.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCreditInvoices.setText("CREDIT INVOICES");
        lblCreditInvoices.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblCreditInvoices.setOpaque(true);
        lblCreditInvoices.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblCreditInvoicesMouseClicked(evt);
            }
        });
        PanelReports.add(lblCreditInvoices, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, 270, 40));

        lblCashieBalance.setBackground(new java.awt.Color(0, 0, 102));
        lblCashieBalance.setFont(new java.awt.Font("Ubuntu Medium", 1, 18)); // NOI18N
        lblCashieBalance.setForeground(new java.awt.Color(255, 255, 255));
        lblCashieBalance.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCashieBalance.setText("   CASHIER BALANCE");
        lblCashieBalance.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblCashieBalance.setOpaque(true);
        lblCashieBalance.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblCashieBalanceMouseClicked(evt);
            }
        });
        PanelReports.add(lblCashieBalance, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, 270, 40));

        panel.add(PanelReports, new org.netbeans.lib.awtextra.AbsoluteConstraints(1040, 300, 280, 390));

        lblnamePriceUpdate2.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        lblnamePriceUpdate2.setForeground(new java.awt.Color(255, 255, 255));
        lblnamePriceUpdate2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cazzendra/pos/ui/icons/logged_user.png"))); // NOI18N
        lblnamePriceUpdate2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblnamePriceUpdate2MouseClicked(evt);
            }
        });
        panel.add(lblnamePriceUpdate2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1180, 20, -1, -1));

        lblLoggedUser.setFont(new java.awt.Font("Ubuntu Medium", 0, 18)); // NOI18N
        lblLoggedUser.setForeground(new java.awt.Color(255, 0, 0));
        lblLoggedUser.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblLoggedUser.setText("User Name");
        lblLoggedUser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblLoggedUserMouseClicked(evt);
            }
        });
        panel.add(lblLoggedUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 20, 170, 40));

        lblDateTime.setFont(new java.awt.Font("Ubuntu Medium", 0, 36)); // NOI18N
        lblDateTime.setForeground(new java.awt.Color(0, 0, 102));
        lblDateTime.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblDateTime.setText("######");
        panel.add(lblDateTime, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 300, 50));

        btnCloseApp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cazzendra/pos/newtheme/icons/exit-button-40x40.png"))); // NOI18N
        btnCloseApp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCloseAppMouseClicked(evt);
            }
        });
        panel.add(btnCloseApp, new org.netbeans.lib.awtextra.AbsoluteConstraints(1280, 20, 50, 50));

        btnLastPrint.setBackground(new java.awt.Color(255, 255, 255));
        btnLastPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cazzendra/pos/newtheme/icons/printer-3-32.png"))); // NOI18N
        btnLastPrint.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLastPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLastPrintActionPerformed(evt);
            }
        });
        btnLastPrint.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                btnLastPrintKeyReleased(evt);
            }
        });
        panel.add(btnLastPrint, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 20, 40, 40));

        jLabel7.setFont(new java.awt.Font("Ubuntu Medium", 0, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 102));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Last Print");
        panel.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 30, 120, -1));

        lblCopyrightStatement.setFont(new java.awt.Font("Ubuntu Light", 0, 14)); // NOI18N
        lblCopyrightStatement.setForeground(new java.awt.Color(0, 0, 102));
        lblCopyrightStatement.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCopyrightStatement.setText("######");
        panel.add(lblCopyrightStatement, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 680, 1340, 20));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, 1368, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addGap(0, 6, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 1366, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, 704, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(83, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 582, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(53, Short.MAX_VALUE)))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void lblInvoiceMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblInvoiceMouseClicked
        new Invoice(Lang).setVisible(true);
    }//GEN-LAST:event_lblInvoiceMouseClicked

    private void lblItemManagementMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblItemManagementMouseClicked
        new ItemManagement(Lang).setVisible(true);
    }//GEN-LAST:event_lblItemManagementMouseClicked

    private void lblCreditInvoicesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCreditInvoicesMouseClicked
        new DateRangeForCreditInvoicesJDialog(this, true).setVisible(true);
    }//GEN-LAST:event_lblCreditInvoicesMouseClicked

    private void lblGrnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblGrnMouseClicked
        new GRN(Lang).setVisible(true);
    }//GEN-LAST:event_lblGrnMouseClicked

    private void lblCurrentStockMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCurrentStockMouseClicked
        new CurrentStockJFrame().setVisible(true);
    }//GEN-LAST:event_lblCurrentStockMouseClicked

    private void lblItemProfitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblItemProfitMouseClicked
        new Profit(this, true).setVisible(true);
    }//GEN-LAST:event_lblItemProfitMouseClicked

    private void lblReOrderMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblReOrderMouseClicked
        try {
            //        new ReorderItems(this, true).setVisible(true); this is 100% working UI
            CommonController.printReOrderItems(Lang);
        } catch (SQLException | JRException ex) {
            Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_lblReOrderMouseClicked

    private void lblUserManagementMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblUserManagementMouseClicked
        new UserManagement().setVisible(true);
    }//GEN-LAST:event_lblUserManagementMouseClicked

    private void lblPriceUpdateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblPriceUpdateMouseClicked
        new PriceUpdateJFrame(Lang).setVisible(true);
    }//GEN-LAST:event_lblPriceUpdateMouseClicked

    private void lblInvoiceHistoryIconMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblInvoiceHistoryIconMouseClicked
//        new Reports().setVisible(true);
        new InvoiceHistory(this, true).setVisible(true);
    }//GEN-LAST:event_lblInvoiceHistoryIconMouseClicked

    private void lblLoggedUserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblLoggedUserMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_lblLoggedUserMouseClicked

    private void lblnamePriceUpdateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblnamePriceUpdateMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_lblnamePriceUpdateMouseClicked

    private void lblnamePriceUpdate2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblnamePriceUpdate2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_lblnamePriceUpdate2MouseClicked

    private void lblNameUsrManagementMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblNameUsrManagementMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_lblNameUsrManagementMouseClicked

    private void lblInvoiceHistoryMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblInvoiceHistoryMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_lblInvoiceHistoryMouseClicked

    private void lblItemReportMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblItemReportMouseClicked
        try {
            CommonController.printAllItems(Lang);
        } catch (SQLException | JRException ex) {
            Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_lblItemReportMouseClicked

    private void lblSoldQtyMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSoldQtyMouseClicked
        new SellQtyAnalize(this, true).setVisible(true);
    }//GEN-LAST:event_lblSoldQtyMouseClicked

    private void lblCashieBalanceMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCashieBalanceMouseClicked
        new CashierBalance(this, true).setVisible(true);
    }//GEN-LAST:event_lblCashieBalanceMouseClicked

    private void lblGrnHistoryMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblGrnHistoryMouseClicked
        new GrnHistory().setVisible(true);
    }//GEN-LAST:event_lblGrnHistoryMouseClicked

    private void lblCategoryManagementMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCategoryManagementMouseClicked
        new CategoryManagement(Lang).setVisible(true);
    }//GEN-LAST:event_lblCategoryManagementMouseClicked

    private void lblNameCategoryManagementMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblNameCategoryManagementMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_lblNameCategoryManagementMouseClicked

    private void lblSullierManagementImageMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSullierManagementImageMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_lblSullierManagementImageMouseClicked

    private void lblSupplierManagementMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSupplierManagementMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_lblSupplierManagementMouseClicked

    private void lblCustomerManagementMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCustomerManagementMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_lblCustomerManagementMouseClicked

    private void lblCustomerManagementIconMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCustomerManagementIconMouseClicked
        new CustomerManagement().setVisible(true);
    }//GEN-LAST:event_lblCustomerManagementIconMouseClicked

    private void lblCustomerAccMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCustomerAccMouseClicked
        new eWallet(Lang).setVisible(true);
    }//GEN-LAST:event_lblCustomerAccMouseClicked

    private void btnCloseAppMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCloseAppMouseClicked
        try {
            boolean status = CommonController.Activation();
            if (status) {
                try {
                    CommonController.AutoBackupDB();
                } catch (URISyntaxException | IOException | InterruptedException ex) {
                    Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
                }
                new Login().setVisible(true);
                this.dispose();
            } else {
                new ActivationUI().setVisible(true);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnCloseAppMouseClicked

    private void btnLastPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastPrintActionPerformed
        try {
            CommonController.printInvoice(Integer.toString(CommonController.GetMaxInvoiceNo()), "English");
        } catch (SQLException | JRException ex) {
            Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnLastPrintActionPerformed

    private void btnLastPrintKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnLastPrintKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_btnLastPrintKeyReleased

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
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainMenu.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainMenu.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainMenu.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainMenu.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainMenu("test param").setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanelReports;
    private javax.swing.JLabel btnCloseApp;
    private javax.swing.JButton btnLastPrint;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel lbItemManagement;
    private javax.swing.JLabel lblCashieBalance;
    private javax.swing.JLabel lblCategoryManagement;
    private javax.swing.JLabel lblCloudRevelLogo;
    private javax.swing.JLabel lblCopyrightStatement;
    private javax.swing.JLabel lblCreditInvoices;
    private javax.swing.JLabel lblCurrentStock;
    private javax.swing.JLabel lblCustomerAcc;
    private javax.swing.JLabel lblCustomerAccounts;
    private javax.swing.JLabel lblCustomerManagement;
    private javax.swing.JLabel lblCustomerManagementIcon;
    private javax.swing.JLabel lblDateTime;
    private javax.swing.JLabel lblGrn;
    private javax.swing.JLabel lblGrnHistory;
    private javax.swing.JLabel lblInvoice;
    private javax.swing.JLabel lblInvoiceHistory;
    private javax.swing.JLabel lblInvoiceHistoryIcon;
    private javax.swing.JLabel lblItemManagement;
    private javax.swing.JLabel lblItemProfit;
    private javax.swing.JLabel lblItemReport;
    private javax.swing.JLabel lblLoggedUser;
    private javax.swing.JLabel lblNameCategoryManagement;
    private javax.swing.JLabel lblNameGrn;
    private javax.swing.JLabel lblNameInvoice;
    private javax.swing.JLabel lblNameUsrManagement;
    private javax.swing.JLabel lblPriceUpdate;
    private javax.swing.JLabel lblReOrder;
    private javax.swing.JLabel lblShopName;
    private javax.swing.JLabel lblSoldQty;
    private javax.swing.JLabel lblSullierManagementImage;
    private javax.swing.JLabel lblSupplierManagement;
    private javax.swing.JLabel lblUserManagement;
    private javax.swing.JLabel lblnamePriceUpdate;
    private javax.swing.JLabel lblnamePriceUpdate2;
    private javax.swing.JPanel panel;
    private javax.swing.JPanel pnlQuickAccess;
    // End of variables declaration//GEN-END:variables
}
