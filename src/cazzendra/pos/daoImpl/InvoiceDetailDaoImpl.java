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
import cazzendra.pos.dao.InvoiceDetailDao;
import cazzendra.pos.model.InvoiceDetail;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Amal
 */
public class InvoiceDetailDaoImpl implements InvoiceDetailDao {

    private String selectQuery = "select invoice_detail_id, invoice_detail_invoice_no, "
            + " invoice_detail_item_id, invoice_detail_item_qty, invoice_detail_unit, "
            + " invoice_detail_unit_price, invoice_detail_selling_price, invoice_detail_discount_rate, "
            + " invoice_detail_purchase_price from invoice_detail";

    @Override

    public MethodStatus addInvoiceDetail(InvoiceDetail invoiceDetail) throws SQLException {
        Connection con = DatabaseConnection.getDatabaseConnection();
        PreparedStatement ps = con.prepareStatement("insert into invoice_detail (invoice_detail_id, "
                + " invoice_detail_invoice_no, invoice_detail_item_id, invoice_detail_item_qty, "
                + " invoice_detail_unit, invoice_detail_unit_price, invoice_detail_selling_price, "
                + " invoice_detail_discount_rate, invoice_detail_purchase_price) values (?,?,?,?,?,?,?,?,?)");
        ps.setInt(1, invoiceDetail.getId());
        ps.setInt(2, invoiceDetail.getInvoiceId());
        ps.setInt(3, invoiceDetail.getItemId());
        ps.setBigDecimal(4, invoiceDetail.getItemQty());
        ps.setString(5, invoiceDetail.getUnit());
        ps.setBigDecimal(6, invoiceDetail.getMRP());
        ps.setBigDecimal(7, invoiceDetail.getSellingPrice());
        ps.setBigDecimal(8, invoiceDetail.getDiscountRate());
        ps.setBigDecimal(9, invoiceDetail.getPurchasePrice());
        ps.executeUpdate();
        ps.close();
        return MethodStatus.SUCCESS;
    }

    @Override
    public ResultSet getAllInvoices() throws SQLException {
        return new CommonDaoImpl().getAllRecords(selectQuery);
    }

    @Override
    public ResultSet getInvoiceDetailByOneAttribute(String attribute, String condition, String value) throws SQLException {
        return new CommonDaoImpl().getResultByAttribute(selectQuery, attribute, condition, value);
    }

    public int getNextInvoiceDetailId() throws SQLException {
        int maxInvoiceDetailId = (Loading.getCounterId()) * 100000;
        Connection con = DatabaseConnection.getDatabaseConnection();
        PreparedStatement ps = con.prepareStatement("select max(invoice_detail_id) as invoice_detail_id from invoice_detail where invoice_detail_id between ? and ?");
        int minvalue = Loading.getCounterId() * 100000;
        int maxValue = (Loading.getCounterId() + 1) * 100000;
        ps.setInt(1, minvalue);
        ps.setInt(2, maxValue);
        ResultSet rset = ps.executeQuery();
        while (rset.next()) {
            if (Validations.isLong(rset.getString(1))) {
                maxInvoiceDetailId = rset.getInt(1);
            }
        }
        return ++maxInvoiceDetailId;
    }
}
