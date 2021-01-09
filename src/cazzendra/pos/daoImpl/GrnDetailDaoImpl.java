/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cazzendra.pos.daoImpl;

import cazzendra.pos.connection.DatabaseConnection;
import cazzendra.pos.core.Loading;
import cazzendra.pos.core.MethodStatus;
import cazzendra.pos.core.Validations;
import cazzendra.pos.dao.GrnDetailDao;
import cazzendra.pos.model.GrnDetail;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Amal
 */
public class GrnDetailDaoImpl implements GrnDetailDao {

    private String selectQuery = "select grn_detail_id, grn_detail_grn_id, grn_detail_item_id, grn_detail_item_purchase_price, grn_detail_item_selling_price, grn_detail_qty, grn_detail_unit, grn_detail_discount_rate from grn_detail";

    @Override
    public MethodStatus addgrnDetail(GrnDetail grnDetail) throws SQLException {
        Connection con = DatabaseConnection.getDatabaseConnection();
        PreparedStatement ps = con.prepareStatement("insert into grn_detail(grn_detail_id, grn_detail_grn_id, "
                + " grn_detail_item_id, grn_detail_item_purchase_price, grn_detail_item_selling_price,"
                + " grn_detail_qty, grn_detail_unit, grn_detail_discount_rate) values (?,?,?,?,?,?,?,?)");
        ps.setInt(1, grnDetail.getId());
        ps.setInt(2, grnDetail.getGrnId());
        ps.setInt(3, grnDetail.getItemId());
        ps.setBigDecimal(4, grnDetail.getPurchasePrice());
        ps.setBigDecimal(5, grnDetail.getSellingPrice());
        ps.setBigDecimal(6, grnDetail.getQty());
        ps.setString(7, grnDetail.getUnit());
        ps.setBigDecimal(8, grnDetail.getDiscountRate());
        ps.executeUpdate();
        ps.close();
        return MethodStatus.SUCCESS;
    }

    @Override
    public ResultSet getAllGrnDetails() throws SQLException {
        return new CommonDaoImpl().getAllRecords(selectQuery);
    }

    public int getNextGrnId() throws SQLException {
        int maxGrnDetailId = (Loading.getCounterId()) * 100000;
        Connection con = DatabaseConnection.getDatabaseConnection();
        PreparedStatement ps = con.prepareStatement("select max(grn_detail_id) as grn_detail_id from grn_detail where grn_detail_id between ? and ?");
        int minvalue = Loading.getCounterId() * 100000;
        int maxValue = (Loading.getCounterId() + 1) * 100000;
        ps.setInt(1, minvalue);
        ps.setInt(2, maxValue);
        ResultSet rset = ps.executeQuery();
        while (rset.next()) {
            if (Validations.isLong(rset.getString(1))) {
                maxGrnDetailId = rset.getInt(1);
            }
        }
        return ++maxGrnDetailId;
    }

    public MethodStatus updateItemPrice(String itemId, String sellingPriice, String purchasePrice) throws SQLException {
        Connection con = DatabaseConnection.getDatabaseConnection();
        PreparedStatement ps = con.prepareStatement("update item set item_selling_price=?, item_purchasing_price=? where item_id=?");
        ps.setBigDecimal(1, Validations.getBigDecimalOrZeroFromString(sellingPriice));
        ps.setBigDecimal(2, Validations.getBigDecimalOrZeroFromString(purchasePrice));
        ps.setInt(3, Validations.getIntOrZeroFromString(itemId));
        ps.executeUpdate();
        ps.close();
        return MethodStatus.SUCCESS;
    }
}
