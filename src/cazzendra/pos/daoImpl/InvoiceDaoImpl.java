/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cazzendra.pos.daoImpl;

import cazzendra.pos.connection.DatabaseConnection;
import cazzendra.pos.core.Loading;
import cazzendra.pos.core.Validations;
import cazzendra.pos.dao.InvoiceDao;
import cazzendra.pos.model.Invoice;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Amal
 */
public class InvoiceDaoImpl implements InvoiceDao {

    private String selectQuery = "select invoice_id, invoice_date, invoice_time,invoice_user_id, invoice_total, "
            + " invoice_item_qty, invoice_discount_rate, invoice_cust_id, invoice_cash_pay, "
            + " if((invoice_cash_pay+invoice_cheque_pay)>=invoice_total, invoice_total, (invoice_cash_pay+invoice_cheque_pay)) as paid_amount, "
            + " invoice_installments, invoice_installment_value, invoice_interest_rate, "
            + " invoice_document_charge, invoice_discount_by_amount, invoice_cheque_pay from invoice";

    @Override
    public int addInvoice(Invoice invoice) throws SQLException {
        Connection con = DatabaseConnection.getDatabaseConnection();
        PreparedStatement ps = con.prepareStatement("insert into invoice(invoice_id, invoice_date, invoice_time,"
                + " invoice_user_id, invoice_total, invoice_item_qty, invoice_discount_rate, invoice_cust_id, "
                + " invoice_cash_pay, invoice_installments, invoice_installment_value, invoice_interest_rate, "
                + "invoice_document_charge, invoice_discount_by_amount, invoice_cheque_pay) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        ps.setInt(1, invoice.getId());
        ps.setDate(2, invoice.getDate());
        ps.setTime(3, invoice.getTime());
        ps.setInt(4, invoice.getUserId());
        ps.setBigDecimal(5, invoice.getTotal());
        ps.setInt(6, invoice.getQty());
        ps.setBigDecimal(7, invoice.getDiscountRate());
        ps.setInt(8, invoice.getCustomerId());
        ps.setBigDecimal(9, invoice.getCashPay());
        ps.setInt(10, invoice.getInstallments());
        ps.setBigDecimal(11, invoice.getInstallmentValue());
        ps.setBigDecimal(12, invoice.getInterestRate());
        ps.setBigDecimal(13, invoice.getDocumentCharge());
        ps.setBigDecimal(14, invoice.getDiscountByAmount());
        ps.setBigDecimal(15, invoice.getInvoiceChequePay());
        ps.executeUpdate();
        ps.close();
        return invoice.getId();
    }

    @Override
    public ResultSet getAllInvoices() throws SQLException {
        return new CommonDaoImpl().getAllRecords(selectQuery);
    }

    @Override
    public ResultSet getInvoiceByOneAttribute(String attribute, String condition, String value) throws SQLException {
        return new CommonDaoImpl().getResultByAttribute(selectQuery, attribute, condition, value);
    }

    public int getnextInvoiceId() throws SQLException {
        int maxInvoiceId = (Loading.getCounterId()) * 100000;
        Connection con = DatabaseConnection.getDatabaseConnection();
        PreparedStatement ps = con.prepareStatement("select max(invoice_id) as invoice_id from invoice where invoice_id between ? and ?");
        int minvalue = Loading.getCounterId() * 100000;
        int maxValue = (Loading.getCounterId() + 1) * 100000;
        ps.setInt(1, minvalue);
        ps.setInt(2, maxValue);
        ResultSet rset = ps.executeQuery();
        while (rset.next()) {
            if (Validations.isLong(rset.getString(1))) {
                maxInvoiceId = rset.getInt(1);
            }
        }
        return ++maxInvoiceId;
    }

    public ResultSet gerInvoicesByMoreAttributes(ArrayList<String[]> attributeConditionValueList, String operator) throws SQLException {
        return new CommonDaoImpl().getResultByAttributesWithJoinOperator(selectQuery, attributeConditionValueList, operator);
    }

    public ResultSet getInvoicesByDateRange(String fromdate, String toDate) throws SQLException {
        Connection con = DatabaseConnection.getDatabaseConnection();
        PreparedStatement ps = con.prepareStatement("select invoice_id, invoice_date, "
                + " invoice_time, invoice_user_id, invoice_total, invoice_item_qty, "
                + " invoice_discount_rate, invoice_cust_id, invoice_cash_pay from invoice where invoice_date between ? and ?");
        ps.setString(1, fromdate);
        ps.setString(2, toDate);
        return ps.executeQuery();
    }

    public BigDecimal getDiscountedInvoiceProfit(Date fromDate, Date toDate) throws SQLException {
        BigDecimal profit = BigDecimal.ZERO;
        Connection con = DatabaseConnection.getDatabaseConnection();
        PreparedStatement ps = con.prepareStatement("SELECT invoice_payment_date, "
                + " sum(invoice_payment_total -invoice_payment_discounted_total) as invoice_profit, "
                + " invoice_payment_discount_value, invoice_payment_cash, invoice_payment_invoice_no "
                + " FROM `invoice_payment` where invoice_payment_date between ? and ? "
                + " group by invoice_payment_date");
        ps.setDate(1, fromDate);
        ps.setDate(2, toDate);
        ResultSet rset = ps.executeQuery();
        while (rset.next()) {
            profit = rset.getBigDecimal("invoice_profit");
        }
        return profit;
    }

    public BigDecimal GetInvoiceTotalLineDiscountByInvoiceNo(int invoiceNo) throws SQLException {
        Connection con = DatabaseConnection.getDatabaseConnection();
        PreparedStatement ps = con.prepareStatement("SELECT sum((invoice_detail_unit_price-invoice_detail_selling_price)*invoice_detail_item_qty) "
                + " as disount_amount FROM invoice_detail where invoice_detail_invoice_no=?");
        ps.setInt(1, invoiceNo);
        ResultSet rset = ps.executeQuery();
        BigDecimal value = BigDecimal.ZERO;
        while (rset.next()) {
            value = rset.getBigDecimal("disount_amount");
        }
        ps.close();
        return value;
    }

}
