/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cazzendra.pos.control;

import cazzendra.pos.core.MethodStatus;
import cazzendra.pos.core.Validations;
import cazzendra.pos.daoImpl.InvoicePaymentDaoImpl;
import cazzendra.pos.model.InvoicePayment;
import java.sql.SQLException;

/**
 *
 * @author Amal
 */
public class InvoicePaymentControl {

    public static MethodStatus addInvoicePayment(String invTotal, String diasountRate, String discountedTotal,
            String cash, int invoiceNo, int customerId, String ChequePayment) throws SQLException {
        InvoicePayment invoicePayment = new InvoicePayment();
        invoicePayment.setDate(CommonController.getCurrentJavaSqlDate());
        invoicePayment.setTotal(Validations.getBigDecimalOrZeroFromString(invTotal));
        invoicePayment.setDiscountrate(Validations.getBigDecimalOrZeroFromString(diasountRate));
        invoicePayment.setDiscountedTotal(Validations.getBigDecimalOrZeroFromString(discountedTotal));
        invoicePayment.setValue(Validations.getBigDecimalOrZeroFromString(diasountRate));
        invoicePayment.setCash(Validations.getBigDecimalOrZeroFromString(cash));
        invoicePayment.setInvoiceNo(invoiceNo);
        invoicePayment.setCustomerId(customerId);
        invoicePayment.setChequePayment(Validations.getBigDecimalOrZeroFromString(ChequePayment));
        return new InvoicePaymentDaoImpl().addInvoicePayment(invoicePayment);
    }
}
