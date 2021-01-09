/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cazzendra.pos.daoImpl;

import cazzendra.pos.connection.DatabaseConnection;
import cazzendra.pos.core.Loading;
import cazzendra.pos.core.Validations;
import cazzendra.pos.dao.GrnDao;
import cazzendra.pos.model.Grn;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Amal
 */
public class GrnDaoImpl implements GrnDao {

    private String selectQuery = "select grn_id, grn_date, grn_time, "
            + " grn_cash_pay, grn_card_pay, grn_cheque_pay, grn_preview_id,"
            + " grn_supplier_id, grn_total, grn_discount_rate, grn_item_qty"
            + " from grn";

    @Override
    public int addGrn(Grn grn) throws SQLException {
        Connection con = DatabaseConnection.getDatabaseConnection();
        PreparedStatement ps = con.prepareStatement("insert into grn (grn_id, grn_date,"
                + " grn_time, grn_cash_pay, grn_card_pay, grn_cheque_pay, "
                + " grn_preview_id, grn_supplier_id, grn_total, "
                + " grn_discount_rate, grn_item_qty) values (?,?,?,?,?,?,?,?,?,?,?)");

        ps.setInt(1, grn.getGrnId());
        ps.setDate(2, grn.getDate());
        ps.setTime(3, grn.getTime());
        ps.setBigDecimal(4, grn.getCashPay());
        ps.setBigDecimal(5, grn.getCardPay());
        ps.setBigDecimal(6, grn.getChequePay());
        ps.setString(7, grn.getPreviewId());
        ps.setInt(8, grn.getSupplierId());
        ps.setBigDecimal(9, grn.getTotal());
        ps.setBigDecimal(10, grn.getDiscountRate());
        ps.setInt(11, grn.getItemQty());
        return ps.executeUpdate();
    }

    @Override
    public ResultSet getAllGrn() throws SQLException {
        return new CommonDaoImpl().getAllRecords(selectQuery);
    }

    @Override
    public int getNextGrnId() throws SQLException {
        int maxGrnId = (Loading.getCounterId()) * 100000;
        Connection con = DatabaseConnection.getDatabaseConnection();
        PreparedStatement ps = con.prepareStatement("select max(grn_id) as grn_id from grn where grn_id between ? and ?");
        int minvalue = Loading.getCounterId() * 100000;
        int maxValue = (Loading.getCounterId() + 1) * 100000;
        ps.setInt(1, minvalue);
        ps.setInt(2, maxValue);
        ResultSet rset = ps.executeQuery();
        while (rset.next()) {
            if (Validations.isLong(rset.getString(1))) {
                maxGrnId = rset.getInt(1);
            }
        }
        return ++maxGrnId;
    }

    public ResultSet getGrnByMoreAttributes(ArrayList<String[]> attributeConditonValueList, String operator) throws SQLException {
        return new CommonDaoImpl().getResultByAttributesWithJoinOperator(selectQuery, attributeConditonValueList, operator);
    }

    public ResultSet getResultSetByOneAttribute(String attribute, String condition, String value) throws SQLException {
        return new CommonDaoImpl().getResultByAttribute(selectQuery, attribute, condition, value);
    }

}
