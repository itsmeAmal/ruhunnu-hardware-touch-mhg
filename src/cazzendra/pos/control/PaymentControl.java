/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cazzendra.pos.control;

import cazzendra.pos.daoImpl.PaymentDaoImpl;
import cazzendra.pos.model.Payment;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author personal
 */
public class PaymentControl {

    public static boolean AddPaymentRecord(String PaymentType, BigDecimal PaymentAmount, Date PaymentDate, 
            int ReferenceInvoiceNo, int CustomerId,
            String Detail1, String Detail2, int Status) throws SQLException {
        Payment Payment = new Payment();
        Payment.setPaymentType(PaymentType);
        Payment.setAmount(PaymentAmount);
        Payment.setDate(PaymentDate);
        Payment.setReferenceInvoiceNo(ReferenceInvoiceNo);
        Payment.setCustomerId(CustomerId);
        Payment.setDetail_1(Detail1);
        Payment.setDetail_2(Detail2);
        Payment.setStatus(Status);
        return new PaymentDaoImpl().AddPayment(Payment);
    }

    public static ResultSet GetpaymentsByAttribute(String Attribute, String Condition, String Value) throws SQLException {
        return new PaymentDaoImpl().GetPaymentByOneAttribute(Attribute, Condition, Value);
    }

    public static ResultSet ResultSetGetAllPayments() throws SQLException {
        return new PaymentDaoImpl().GetAllPaymnts();
    }
}
