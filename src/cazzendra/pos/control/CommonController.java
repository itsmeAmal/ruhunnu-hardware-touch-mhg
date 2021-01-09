/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cazzendra.pos.control;

import cazzendra.pos.connection.DatabaseConnection;
import cazzendra.pos.core.Loading;
import cazzendra.pos.core.MethodStatus;
import cazzendra.pos.core.Options;
import cazzendra.pos.core.Validations;
import cazzendra.pos.daoImpl.CommonDaoImpl;
import cazzendra.pos.model.DataObject;
import cazzendra.pos.model.Grn;
import cazzendra.pos.model.Invoice;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Amal
 */
public class CommonController {

    public static void loadDataToDetailTable(JTable table, ResultSet rst, String[] columnList, String column1,
            String column2) throws SQLException {
        BigDecimal value1 = BigDecimal.ZERO;
        BigDecimal value2 = BigDecimal.ZERO;
        DefaultTableModel dtm1 = (DefaultTableModel) table.getModel();
        int rw = dtm1.getRowCount();
        for (int i = 0; i < rw; i++) {
            dtm1.removeRow(0);
        }
        while (rst.next()) {
            Object[] rowCells = new Object[columnList.length + 1];
            for (int i = 0; i < columnList.length; i++) {
                rowCells[i] = rst.getString(columnList[i]);
                if (i == columnList.length - 1) {
                    value1 = Validations.getBigDecimalFromString(rst.getString(column1));
                    value2 = Validations.getBigDecimalFromString(rst.getString(column2));
                    rowCells[i + 1] = Validations.formatWithTwoDigits(value1.multiply(value2).toString());
                }
            }
            dtm1.addRow(rowCells);
        }

    }

    public static void removeAllRowsFromTable(JTable table) {
        DefaultTableModel dtm1 = (DefaultTableModel) table.getModel();
        int rw = dtm1.getRowCount();
        for (int i = 0; i < rw; i++) {
            dtm1.removeRow(0);
        }
    }

    public static void loadDataToTable(JTable table, ResultSet rst, String[] columnList) throws SQLException {
        DefaultTableModel dtm1 = (DefaultTableModel) table.getModel();
        int rw = dtm1.getRowCount();
        for (int i = 0; i < rw; i++) {
            dtm1.removeRow(0);
        }
        while (rst.next()) {
            Object[] rowCells = new Object[columnList.length];
            for (int i = 0; i < columnList.length; i++) {
                rowCells[i] = rst.getString(columnList[i]);
            }
            dtm1.addRow(rowCells);
        }
        rst.getStatement().close();
        rst.close();
    }

    public static void loadDataToComboBox(JComboBox comboBox, ResultSet rst, String attribute) throws SQLException {
        comboBox.removeAllItems();
        while (rst.next()) {
            comboBox.addItem(rst.getString(attribute));
        }
    }

    public static java.sql.Date getCurrentJavaSqlDate() {
        java.util.Date today = new java.util.Date();
        return new java.sql.Date(today.getTime());
    }

    public static java.sql.Time getCurrentJavaSqlTime() {
        java.util.Date date = new java.util.Date();
        return new java.sql.Time(date.getTime());
    }

    public static MethodStatus addActivationKey(String date) throws SQLException, ParseException {
        return new CommonDaoImpl().addActivationKey(date);
    }

    public static void addCurrentStockRecord(String itemId, String qty, int type) throws SQLException {
        new CommonDaoImpl().addCurrentStockRecord(itemId, qty, type);
    }

    public static ResultSet getCurrentStock() throws SQLException {
        return new CommonDaoImpl().getCurrentStock();
    }

    public static ResultSet getItemProfitsByDate(String fromDate, String toDate) throws SQLException {
        return new CommonDaoImpl().getItemProfitsByDate(fromDate, toDate);
    }

    public static boolean Activation() throws SQLException {
        return new CommonDaoImpl().Actiation();
    }

    public static ResultSet getCurrentStockLowerThanReorderlevel() throws SQLException {
        return new CommonDaoImpl().getCurrentStockLowerThanReorderlevel();
    }

    public static ResultSet getCurrentStockByOneAttribute(String attribute) throws SQLException {
        return new CommonDaoImpl().getCurrentStockByOneAttribute(attribute);
    }

    public static BigDecimal getCuttentStockByItemCode(String itemCode) throws SQLException {
        Connection con = DatabaseConnection.getDatabaseConnection();
        PreparedStatement ps = con.prepareStatement("select item_name,item_barcode, item_code,  current_stock_item_id, sum(current_stock_qty) as stock "
                + " from current_stock join item on current_stock_item_id = item_id where item_code=? group by current_stock_item_id ");
        ps.setString(1, itemCode);
        ResultSet rset = ps.executeQuery();
        BigDecimal qty = BigDecimal.ZERO;
        while (rset.next()) {
            qty = rset.getBigDecimal("stock");
        }
        return qty;
    }

    public static boolean deleteActivation() throws SQLException {
        return new CommonDaoImpl().deleteActivation();
    }

    public static String decryptActivation(String activationKey) {
        String[] arr = activationKey.split("-");
        String concatinatedYear = arr[1];
        String year = concatinatedYear.substring(0, 4);

        String concatinatedMonth = arr[3];
        String month = concatinatedMonth.substring(3, 5);

        String date = arr[4];

        String decryptedKey = year + "-" + month + "-" + date;
        return decryptedKey;
    }

    public static int GetMaxInvoiceNo() throws SQLException {
        Connection Con = DatabaseConnection.getDatabaseConnection();
        PreparedStatement Ps = Con.prepareStatement("select max(invoice_id) as max_invoice_no from invoice");
        ResultSet Rset = Ps.executeQuery();
        int InvoiceNo = 0;
        while (Rset.next()) {
            InvoiceNo = Rset.getInt("max_invoice_no");
        }
        return InvoiceNo;
    }

    public static void printInvoice(String invoiceNo, String PrintLanguage) throws SQLException, JRException {
        Invoice invoice = InvoiceControl.getInvoiceModelByInvoiceNo(invoiceNo);
        HashMap<String, Object> hm = new HashMap<>();
        hm.put("shopName", Loading.getShopName());
        hm.put("invoiceNo", invoiceNo);
        hm.put("date", invoice.getDate());
        hm.put("Address", Loading.getShopAddress());
        hm.put("ShopContact", Loading.getShopContact());
        hm.put("userName", UserControl.getUserByUserId(invoice.getUserId()).getUserName());
        hm.put("time", invoice.getTime());
        hm.put("CashPay", invoice.getCashPay());
        hm.put("ChequePay", invoice.getInvoiceChequePay());
        hm.put("InvoiceTotal", invoice.getTotal());
        hm.put("TotalDiscount", BigDecimal.ZERO);  /// should modify
        hm.put("InvoiceTotalLineDiscount", InvoiceControl.getInvoiceDiscountByInvoiceNo(Validations.getIntOrZeroFromString(invoiceNo)));
        hm.put("customerName", CustomerControl.getCustomerObjectByCustId(invoice.getCustomerId()).getCustomerName());
        Connection con = DatabaseConnection.getDatabaseConnection();
        JasperDesign jsd = null;
        if (PrintLanguage.equalsIgnoreCase(Options.LANG_ENGLISH)) {
            jsd = JRXmlLoader.load("reports\\invoicePosP.jrxml"); //src\\cazzendra\\pos\\
        } else if (PrintLanguage.equalsIgnoreCase(Options.LANG_SINHALA)) {
            jsd = JRXmlLoader.load("reports\\invoicePosP_sinhala.jrxml"); //src\\cazzendra\\pos\\
        }
        JasperReport jr = JasperCompileManager.compileReport(jsd);
        JasperPrint jp = JasperFillManager.fillReport(jr, hm, con);
//        JasperViewer jasperViewer = new JasperViewer(jp, false);
//        JasperPrintManager.printReport(jp, false);
        JasperViewer jasperViewer = new JasperViewer(jp, false);
        jasperViewer.setVisible(true);
    }

//    public static void printDayEnd(Date date, int type) throws SQLException, JRException {
//        HashMap<String, Object> hm = new HashMap<>();
//        hm.put("shopName", Loading.getShopName());
//        hm.put("invoiceNo", invoiceNo);
//        hm.put("date", invoice.getDate());
//        hm.put("userName", UserControl.getUserByUserId(invoice.getUserId()).getUserName());
//        hm.put("time", invoice.getTime());
//        hm.put("cashPay", invoice.getCashPay());
//        hm.put("invoiceDiscount", InvoiceControl.getInvoiceDiscountByInvoiceNo(Validations.getIntOrZeroFromString(invoiceNo)));
//        Connection con = DatabaseConnection.getDatabaseConnection();
//        JasperDesign jsd = JRXmlLoader.load("reports\\invoice_58.jrxml"); //src\\cazzendra\\pos\\
//        JasperReport jr = JasperCompileManager.compileReport(jsd);
//        JasperPrint jp = JasperFillManager.fillReport(jr, hm, con);
////        JasperViewer jasperViewer = new JasperViewer(jp, false);
//        JasperPrintManager.printReport(jp, false);
//        JasperViewer jasperViewer = new JasperViewer(jp, false);
//        jasperViewer.setVisible(true);
//    }
    public static void printGrn(String grnNo) throws SQLException, JRException {
        Grn grn = GrnControl.getGrnEntityByGrnId(Validations.getIntOrZeroFromString(grnNo));
        HashMap<String, Object> hm = new HashMap<>();
        hm.put("shopName", Loading.getShopName());
        hm.put("invoiceNo", grnNo);
        hm.put("date", grn.getDate());
        Connection con = DatabaseConnection.getDatabaseConnection();
        JasperDesign jsd = JRXmlLoader.load("reports\\grn_letter.jrxml"); //src\\cazzendra\\pos\\
        JasperReport jr = JasperCompileManager.compileReport(jsd);
        JasperPrint jp = JasperFillManager.fillReport(jr, hm, con);
        JasperViewer jasperViewer = new JasperViewer(jp, false);
        jasperViewer.setVisible(true);
    }

    public static void printAllItems(String Language) throws SQLException, JRException {
        HashMap<String, Object> hm = new HashMap<>();
        hm.put("shopName", Loading.getShopName());
//        hm.put("title", "Item Report");
//        hm.put("invoiceNo", invoiceNo);
        Connection con = DatabaseConnection.getDatabaseConnection();
        JasperDesign jsd = null;
        if (Language.equalsIgnoreCase(Options.LANG_ENGLISH)) {
            jsd = JRXmlLoader.load("reports\\item_list.jrxml"); //src\\cazzendra\\pos\\
        } else {
            jsd = JRXmlLoader.load("reports\\item_list_sinhala.jrxml"); //src\\cazzendra\\pos\\
        }
        JasperReport jr = JasperCompileManager.compileReport(jsd);
        JasperPrint jp = JasperFillManager.fillReport(jr, hm, con);
        JasperViewer jasperViewer = new JasperViewer(jp, false);
        jasperViewer.setVisible(true);
    }

    public static void printReOrderItems(String Language) throws SQLException, JRException {
        HashMap<String, Object> hm = new HashMap<>();
        hm.put("shopName", Loading.getShopName());
//        hm.put("title", "Item Report");
//        hm.put("invoiceNo", invoiceNo);
        Connection con = DatabaseConnection.getDatabaseConnection();
        JasperDesign jsd = null;
        if (Language.equalsIgnoreCase(Options.LANG_ENGLISH)) {
            jsd = JRXmlLoader.load("reports\\reorder_items.jrxml"); //src\\cazzendra\\pos\\
        } else if (Language.equalsIgnoreCase(Options.LANG_SINHALA)) {
            jsd = JRXmlLoader.load("reports\\sinhala_reorder_items.jrxml"); //src\\cazzendra\\pos\\
        }
        JasperReport jr = JasperCompileManager.compileReport(jsd);
        JasperPrint jp = JasperFillManager.fillReport(jr, hm, con);
        JasperViewer jasperViewer = new JasperViewer(jp, false);
        jasperViewer.setVisible(true);
    }

    public static void printCommonReport(String reportName) throws SQLException, JRException {
        HashMap<String, Object> hm = new HashMap<>();
        hm.put("shopName", Loading.getShopName());
        Connection con = DatabaseConnection.getDatabaseConnection();
        JasperDesign jsd = JRXmlLoader.load("reports\\" + reportName + ".jrxml"); //src\\cazzendra\\pos\\
        JasperReport jr = JasperCompileManager.compileReport(jsd);
        JasperPrint jp = JasperFillManager.fillReport(jr, hm, con);
        JasperViewer jasperViewer = new JasperViewer(jp, false);
        jasperViewer.setVisible(true);
    }

    public static void DateRangeForCreditInvoices(String reportName, java.util.Date FromDate, java.util.Date Todate)
            throws SQLException, JRException {
        HashMap<String, Object> hm = new HashMap<>();
        hm.put("shopName", Loading.getShopName());
        hm.put("FromDate", Validations.getSqlDateByUtilDate(FromDate));
        hm.put("ToDate", Validations.getSqlDateByUtilDate(Todate));
        Connection con = DatabaseConnection.getDatabaseConnection();
        JasperDesign jsd = JRXmlLoader.load("reports\\" + reportName + ".jrxml"); //src\\cazzendra\\pos\\
        JasperReport jr = JasperCompileManager.compileReport(jsd);
        JasperPrint jp = JasperFillManager.fillReport(jr, hm, con);
        JasperViewer jasperViewer = new JasperViewer(jp, false);
        jasperViewer.setVisible(true);
    }

    public static ResultSet getMostSoldItemsByDateRange(Date fromDate, Date toDate) throws SQLException {
        return new CommonDaoImpl().getMostSoldItemsByDateRange(fromDate, toDate);
    }

    public static ResultSet getMostSoldItemByDateRangeAndItemName(Date fromDate, Date toDate, String tradeName) throws SQLException {
        return new CommonDaoImpl().getMostSoldItemByDateRangeAndItemName(fromDate, toDate, tradeName);
    }

    public static void beepAlart() throws LineUnavailableException, UnsupportedAudioFileException, IOException {
        Clip clip = AudioSystem.getClip();
        clip.open(AudioSystem.getAudioInputStream(new File("audio\\beep-08b.wav")));
        clip.start();
    }

    public static void AutoBackupDB() throws URISyntaxException, IOException, InterruptedException {

        String uerAccName = System.getProperty("user.name");

        //server configuration
        String dbName = Loading.getDbName();
        String dbUser = "root";
        String dbPass = Loading.getDbPassword();

//        String backupName = CommonController.getCurrentJavaSqlDate().toString() + "-" + CommonController.getCurrentJavaSqlTime().toString() + "-" + "backup.sql";
        //making the directory
        String folderPath = "C:\\Users\\" + uerAccName + "\\Dropbox" + "\\backup";
        File f1 = new File(folderPath);
        f1.mkdir();

        //backup @ the location already made 
//        String savePath = "\"" + jarDir + "\\backup\\" + "backup.sql\"";
        String savePath = "\"" + "C:\\Users\\" + uerAccName + "\\Dropbox" + "\\backup\\" + "backup.sql\"";
        System.out.println(savePath);

        //String executeCmd = "mysqldump -u " + dbUser + " -p" + dbPass + " " + dbName + " -r \""
//                + new File(path).getAbsolutePath() + "\"";
//        Process runtimeProcess = Runtime.getRuntime().exec(executeCmd);
        String executeCmd = "mysqldump -u" + dbUser + " -p" + dbPass + " --database " + dbName + " -r " + savePath;
        Process runtimeProcess = Runtime.getRuntime().exec(executeCmd);
        int processComplete = runtimeProcess.waitFor();
        //JOptionPane.showMessageDialog(null, "Process ID !" + processComplete);
        if (processComplete == 0) {
            JOptionPane.showMessageDialog(null, "Backup Successful !");
        } else {
            JOptionPane.showMessageDialog(null, "Backup Fail !");
        }
    }

    public static void loadDataObjectsIntoComboBox(JComboBox comboBox, ResultSet rst, String[] columnList,
            String defaultAttribute) throws SQLException {
        comboBox.removeAllItems();
        while (rst.next()) {
            DataObject object = new DataObject(defaultAttribute);
            for (int i = 0; i < columnList.length; i++) {
                object.addProperty(columnList[i], rst.getString(columnList[i]));
            }
            comboBox.addItem(object);
        }
    }
}

//String uerAccName = System.getProperty("user.name");
