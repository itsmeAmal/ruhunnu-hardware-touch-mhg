/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cazzendra.pos.control;

import cazzendra.pos.core.MethodStatus;
import cazzendra.pos.core.Validations;
import cazzendra.pos.daoImpl.GrnDetailDaoImpl;
import cazzendra.pos.model.GrnDetail;
import java.sql.SQLException;
import javax.swing.JTable;

/**
 *
 * @author Amal
 */
public class GrnDetailControl {

    public static MethodStatus addGrnDetail(JTable table, int grnId) throws SQLException {
        GrnDetail grnDetail = new GrnDetail();
        GrnDetailDaoImpl detailDaoImpl = new GrnDetailDaoImpl();
        for (int i = 0; i < table.getRowCount(); i++) {
            grnDetail.setGrnId(grnId);
            grnDetail.setId(detailDaoImpl.getNextGrnId());
            grnDetail.setItemId(Validations.getIntOrZeroFromString(table.getValueAt(i, 0).toString()));
            grnDetail.setPurchasePrice(Validations.getBigDecimalOrZeroFromString(table.getValueAt(i, 4).toString()));
            grnDetail.setSellingPrice(Validations.getBigDecimalOrZeroFromString(table.getValueAt(i, 6).toString()));
            grnDetail.setQty(Validations.getBigDecimalOrZeroFromString(table.getValueAt(i, 7).toString()));
            grnDetail.setUnit(table.getValueAt(i, 8).toString());
            grnDetail.setDiscountRate(Validations.getBigDecimalOrZeroFromString(table.getValueAt(i, 5).toString()));
            detailDaoImpl.addgrnDetail(grnDetail);
            updateItemPrice(table.getValueAt(i, 0).toString(),
                    table.getValueAt(i, 6).toString(), table.getValueAt(i, 4).toString());
            CommonController.addCurrentStockRecord(table.getValueAt(i, 0).toString(), table.getValueAt(i, 7).toString(), 1);
        }
        return MethodStatus.SUCCESS;
    }

    public static MethodStatus updateItemPrice(String itemId, String sellingPriice, String purchasePrice) throws SQLException {
        return new GrnDetailDaoImpl().updateItemPrice(itemId, sellingPriice, purchasePrice);
    }
}
