/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cazzendra.pos.control;

import cazzendra.pos.core.CommonConstants;
import cazzendra.pos.core.Validations;
import cazzendra.pos.daoImpl.GrnDaoImpl;
import cazzendra.pos.daoImpl.ItemDaoImpl;
import cazzendra.pos.model.Grn;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Amal
 */
public class GrnControl {

    public static int addGrn(String cashpay, String cardPay,
            String chequePay, String previewId, int supplierId, BigDecimal total,
            String discRate, int itemQty) throws SQLException {
        GrnDaoImpl daoImpl = new GrnDaoImpl();
        Grn grn = new Grn();
        int grnId = daoImpl.getNextGrnId();
        grn.setGrnId(grnId);
        grn.setDate(CommonController.getCurrentJavaSqlDate());
        grn.setTime(CommonController.getCurrentJavaSqlTime());
        grn.setCashPay(Validations.getBigDecimalOrZeroFromString(cashpay));
        grn.setCardPay(Validations.getBigDecimalOrZeroFromString(cardPay));
        grn.setPreviewId(previewId);
        grn.setSupplierId(supplierId);
        grn.setTotal(total);
        grn.setDiscountRate(Validations.getBigDecimalOrZeroFromString(discRate));
        grn.setItemQty(itemQty);
        daoImpl.addGrn(grn);
        return grnId;
    }

    public static ResultSet getAllItems() throws SQLException {
        return new ItemDaoImpl().getAllItems();
    }

    public static void addRowToGrnTable(JTable table, String itemId,
            String itemCode, String tradeName, String purchasePrice,
            String discountRate, String sellingPrice, String qty, String unit,
            String total, String itemName) {
        DefaultTableModel dtm = (DefaultTableModel) table.getModel();
        Object[] obj = {itemId, itemCode, tradeName, itemName, purchasePrice, discountRate, sellingPrice, qty, unit, total};
        dtm.addRow(obj);
    }

    public static Grn getGrnEntityByGrnId(int grnId) throws SQLException {
        ResultSet rset = new GrnDaoImpl().getResultSetByOneAttribute("grn_id", CommonConstants.sql.EQUAL, Integer.toString(grnId));
        Grn grn = null;
        while (rset.next()) {
            grn = new Grn();
            grn.setGrnId(rset.getInt("grn_id"));
            grn.setCardPay(rset.getBigDecimal("grn_card_pay"));
            grn.setCashPay(rset.getBigDecimal("grn_cash_pay"));
            grn.setChequePay(rset.getBigDecimal("grn_cheque_pay"));
            grn.setDate(rset.getDate("grn_date"));
            grn.setDiscountRate(rset.getBigDecimal("grn_discount_rate"));
            grn.setItemQty(rset.getInt("grn_item_qty"));
            grn.setTime(rset.getTime("grn_time"));
            grn.setPreviewId(rset.getString("grn_preview_id"));
            grn.setItemQty(rset.getInt("grn_supplier_id"));
            grn.setTotal(rset.getBigDecimal("grn_supplier_id"));
        }
        return grn;
    }
}
