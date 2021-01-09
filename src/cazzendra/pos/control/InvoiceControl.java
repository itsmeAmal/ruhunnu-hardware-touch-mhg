package cazzendra.pos.control;

import cazzendra.pos.core.CommonConstants;
import cazzendra.pos.core.Loading;
import cazzendra.pos.core.Validations;
import cazzendra.pos.daoImpl.InvoiceDaoImpl;
import cazzendra.pos.model.Invoice;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Amal
 */
public class InvoiceControl {

    public static int addInvoice(String Total, String ItemQty, String DiscountRate,
            String CashPay, int Id, int CustomerId, int Installments, BigDecimal InstallmentValue,
            BigDecimal InterestRate, BigDecimal DocumentCharge, BigDecimal DiscountByValue, 
            BigDecimal InvoiceChequepay) throws SQLException {
        InvoiceDaoImpl invoiceDaoImpl = new InvoiceDaoImpl();
        Invoice invoice = new Invoice();
        invoice.setId(Id);
        invoice.setDate(CommonController.getCurrentJavaSqlDate());
        invoice.setTime(CommonController.getCurrentJavaSqlTime());
        invoice.setUserId(Loading.getUser().getId());
        invoice.setTotal(Validations.getBigDecimalOrZeroFromString(Total));
        invoice.setQty(Validations.getIntOrZeroFromString(ItemQty));
        invoice.setDiscountRate(Validations.getBigDecimalOrZeroFromString(DiscountRate));
        invoice.setCustomerId(CustomerId);
        invoice.setCashPay(Validations.getBigDecimalOrZeroFromString(CashPay));
        invoice.setInstallments(Installments);
        invoice.setInstallmentValue(InstallmentValue);
        invoice.setInterestRate(InterestRate);
        invoice.setDocumentCharge(DocumentCharge);
        invoice.setDiscountByAmount(DiscountByValue);
        invoice.setInvoiceChequePay(InvoiceChequepay);
        return invoiceDaoImpl.addInvoice(invoice);
    }

    public static ResultSet getInvoicesByDateRange(String fromdate, String toDate) throws SQLException {
        return new InvoiceDaoImpl().getInvoicesByDateRange(fromdate, toDate);
    }

    public static ResultSet getInvoiceResultSetByInvoiceNo(String invoiceNo) throws SQLException {
        return new InvoiceDaoImpl().getInvoiceByOneAttribute("invoice_id", CommonConstants.sql.EQUAL, invoiceNo);
    }

    public static BigDecimal getDiscountedInvoiceProfit(Date fromDate, Date toDate) throws SQLException {
        return new InvoiceDaoImpl().getDiscountedInvoiceProfit(fromDate, toDate);
    }

    public static Invoice getInvoiceModelByInvoiceNo(String invoiceNo) throws SQLException {
        ResultSet rset = getInvoiceResultSetByInvoiceNo(invoiceNo);
        Invoice invo = null;
        while (rset.next()) {
            invo = new Invoice();
            invo.setId(rset.getInt("invoice_id"));
            invo.setDate(rset.getDate("invoice_date"));
            invo.setTime(rset.getTime("invoice_time"));
            invo.setUserId(rset.getInt("invoice_user_id"));
            invo.setTotal(rset.getBigDecimal("invoice_total"));
            invo.setQty(rset.getInt("invoice_item_qty"));
            invo.setDiscountRate(rset.getBigDecimal("invoice_discount_rate"));
            invo.setCustomerId(rset.getInt("invoice_cust_id"));
            invo.setCashPay(rset.getBigDecimal("invoice_cash_pay"));
            invo.setInstallments(rset.getInt("invoice_installments"));
            invo.setInstallmentValue(rset.getBigDecimal("invoice_installment_value"));
            invo.setInterestRate(rset.getBigDecimal("invoice_interest_rate"));
            invo.setDocumentCharge(rset.getBigDecimal("invoice_document_charge"));
            invo.setDocumentCharge(rset.getBigDecimal("invoice_document_charge"));
            invo.setDiscountByAmount(rset.getBigDecimal("invoice_discount_by_amount"));
            invo.setInvoiceChequePay(rset.getBigDecimal("invoice_cheque_pay"));
        }
        return invo;
    }

    public static BigDecimal getInvoiceDiscountByInvoiceNo(int invoiceNo) throws SQLException {
        return new InvoiceDaoImpl().GetInvoiceTotalLineDiscountByInvoiceNo(invoiceNo);
    }

}
