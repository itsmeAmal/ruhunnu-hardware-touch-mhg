/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cazzendra.pos.daoImpl;

import cazzendra.pos.connection.DatabaseConnection;
import cazzendra.pos.core.MethodStatus;
import cazzendra.pos.dao.InvoicePaymentDao;
import cazzendra.pos.model.InvoicePayment;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Amal
 */
public class InvoicePaymentDaoImpl implements InvoicePaymentDao {

    private String selectQuery = "select invoice_payment_id, invoice_payment_date, "
            + "invoice_payment_total, invoice_payment_discount_rate, invoice_payment_discount_value, "
            + "invoice_payment_cash, invoice_payment_discounted_total, invoice_payment_invoice_no, "
            + "invoice_payment_customer_id, invoice_payment_description, "
            + "if(invoice_payment_cash>invoice_payment_discounted_total, invoice_payment_cheque, "
            + "invoice_payment_discounted_total), (invoice_payment_cash-invoice_payment_discounted_total) "
            + "as cust_paid_amount from invoice_payment";

    @Override
    public MethodStatus addInvoicePayment(InvoicePayment invoicePayment) throws SQLException {
        Connection con = DatabaseConnection.getDatabaseConnection();
        PreparedStatement ps = con.prepareStatement("insert into invoice_payment (invoice_payment_date, invoice_payment_total, "
                + " invoice_payment_discount_rate, invoice_payment_discount_value, invoice_payment_cash, invoice_payment_discounted_total, "
                + " invoice_payment_invoice_no, invoice_payment_customer_id, invoice_payment_description, invoice_payment_cheque) "
                + " values (?,?,?,?,?,?,?,?,?,?)");
        ps.setDate(1, invoicePayment.getDate());
        ps.setBigDecimal(2, invoicePayment.getTotal());
        ps.setBigDecimal(3, invoicePayment.getDiscountrate());
        ps.setBigDecimal(4, invoicePayment.getValue());
        ps.setBigDecimal(5, invoicePayment.getCash());
        ps.setBigDecimal(6, invoicePayment.getDiscountedTotal());
        ps.setInt(7, invoicePayment.getInvoiceNo());
        ps.setInt(8, invoicePayment.getCustomerId());
        ps.setString(9, "Payment for invoice no : " + Integer.toString(invoicePayment.getInvoiceNo()));
        ps.setBigDecimal(10, invoicePayment.getChequePayment());
        ps.executeUpdate();
        ps.close();
        return MethodStatus.SUCCESS;
    }

    public ResultSet getAllInvoicePayments() throws SQLException {
        return new CommonDaoImpl().getAllRecords(selectQuery);
    }

    public ResultSet getInvoicePaymentByOneAttribute(String attribute, String condition, String value) throws SQLException {
        return new CommonDaoImpl().getResultByAttribute(selectQuery, attribute, condition, value);
    }
}
