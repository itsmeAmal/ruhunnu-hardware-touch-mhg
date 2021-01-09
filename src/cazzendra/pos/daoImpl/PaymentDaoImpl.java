/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cazzendra.pos.daoImpl;

import cazzendra.pos.connection.DatabaseConnection;
import cazzendra.pos.dao.PaymentDao;
import cazzendra.pos.model.Payment;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author personal
 */
public class PaymentDaoImpl implements PaymentDao {

    private String SelectQuery = "select payment_id, payment_type_string, payment_amount, payment_date, "
            + " payment_reference_invoice_no, payment_customer_id, "
            + " payment_detail_1, payment_detail_2, payment_status from payment";

    @Override
    public boolean AddPayment(Payment Payment) throws SQLException {
        Connection Con = DatabaseConnection.getDatabaseConnection();
        PreparedStatement Ps = Con.prepareStatement("insert into payment(payment_type_string, payment_amount,"
                + " payment_date, payment_reference_invoice_no, payment_customer_id, "
                + " payment_detail_1, payment_detail_2, payment_status) "
                + " values(?,?,?,?,?,?,?,?)");
        Ps.setString(1, Payment.getPaymentType());
        Ps.setBigDecimal(2, Payment.getAmount());
        Ps.setDate(3, Payment.getDate());
        Ps.setInt(4, Payment.getReferenceInvoiceNo());
        Ps.setInt(5, Payment.getCustomerId());
        Ps.setString(6, Payment.getDetail_1());
        Ps.setString(7, Payment.getDetail_2());
        Ps.setInt(8, Payment.getStatus());
        Ps.executeUpdate();
        Ps.close();
        return true;
    }

    @Override
    public ResultSet GetAllPaymnts() throws SQLException {
        return new CommonDaoImpl().getAllRecords(SelectQuery);
    }

    @Override
    public ResultSet GetPaymentByOneAttribute(String Attribute, String Condition, String Value) throws SQLException {
        return new CommonDaoImpl().getResultByAttribute(SelectQuery, Attribute, Condition, Value);
    }

}
