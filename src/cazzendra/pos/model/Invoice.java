/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cazzendra.pos.model;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;

/**
 *
 * @author Amal
 */
public class Invoice {

    //invoice_installments, invoice_installment_value, invoice_interest_rate, invoice_document_charge
    
    private int Id;
    private Date Date;
    private Time Time;
    private int UserId;
    private BigDecimal Total;
    private int Qty;
    private BigDecimal DiscountRate;
    private int CustomerId;
    private BigDecimal CashPay;
    private int Installments;
    private BigDecimal InstallmentValue;
    private BigDecimal InterestRate;
    private BigDecimal DocumentCharge;
    private BigDecimal DiscountByAmount;
    private BigDecimal InvoiceChequePay;

    /**
     * @return the Id
     */
    public int getId() {
        return Id;
    }

    /**
     * @param id the Id to set
     */
    public void setId(int id) {
        this.Id = id;
    }

    /**
     * @return the Date
     */
    public Date getDate() {
        return Date;
    }

    /**
     * @param date the Date to set
     */
    public void setDate(Date date) {
        this.Date = date;
    }

    /**
     * @return the Time
     */
    public Time getTime() {
        return Time;
    }

    /**
     * @param time the Time to set
     */
    public void setTime(Time time) {
        this.Time = time;
    }

    /**
     * @return the UserId
     */
    public int getUserId() {
        return UserId;
    }

    /**
     * @param userId the UserId to set
     */
    public void setUserId(int userId) {
        this.UserId = userId;
    }

    /**
     * @return the Total
     */
    public BigDecimal getTotal() {
        return Total;
    }

    /**
     * @param total the Total to set
     */
    public void setTotal(BigDecimal total) {
        this.Total = total;
    }

    /**
     * @return the Qty
     */
    public int getQty() {
        return Qty;
    }

    /**
     * @param qty the Qty to set
     */
    public void setQty(int qty) {
        this.Qty = qty;
    }

    /**
     * @return the DiscountRate
     */
    public BigDecimal getDiscountRate() {
        return DiscountRate;
    }

    /**
     * @param discountRate the DiscountRate to set
     */
    public void setDiscountRate(BigDecimal discountRate) {
        this.DiscountRate = discountRate;
    }

    /**
     * @return the CustomerId
     */
    public int getCustomerId() {
        return CustomerId;
    }

    /**
     * @param customerId the CustomerId to set
     */
    public void setCustomerId(int customerId) {
        this.CustomerId = customerId;
    }

    /**
     * @return the CashPay
     */
    public BigDecimal getCashPay() {
        return CashPay;
    }

    /**
     * @param cashPay the CashPay to set
     */
    public void setCashPay(BigDecimal cashPay) {
        this.CashPay = cashPay;
    }

    /**
     * @return the Installments
     */
    public int getInstallments() {
        return Installments;
    }

    /**
     * @param installments the Installments to set
     */
    public void setInstallments(int installments) {
        this.Installments = installments;
    }

    /**
     * @return the InstallmentValue
     */
    public BigDecimal getInstallmentValue() {
        return InstallmentValue;
    }

    /**
     * @param installmentValue the InstallmentValue to set
     */
    public void setInstallmentValue(BigDecimal installmentValue) {
        this.InstallmentValue = installmentValue;
    }

    /**
     * @return the InterestRate
     */
    public BigDecimal getInterestRate() {
        return InterestRate;
    }

    /**
     * @param interestRate the InterestRate to set
     */
    public void setInterestRate(BigDecimal interestRate) {
        this.InterestRate = interestRate;
    }

    /**
     * @return the DocumentCharge
     */
    public BigDecimal getDocumentCharge() {
        return DocumentCharge;
    }

    /**
     * @param documentCharge the DocumentCharge to set
     */
    public void setDocumentCharge(BigDecimal documentCharge) {
        this.DocumentCharge = documentCharge;
    }

    /**
     * @return the DiscountByAmount
     */
    public BigDecimal getDiscountByAmount() {
        return DiscountByAmount;
    }

    /**
     * @param DiscountByAmount the DiscountByAmount to set
     */
    public void setDiscountByAmount(BigDecimal DiscountByAmount) {
        this.DiscountByAmount = DiscountByAmount;
    }

    /**
     * @return the InvoiceChequePay
     */
    public BigDecimal getInvoiceChequePay() {
        return InvoiceChequePay;
    }

    /**
     * @param InvoiceChequePay the InvoiceChequePay to set
     */
    public void setInvoiceChequePay(BigDecimal InvoiceChequePay) {
        this.InvoiceChequePay = InvoiceChequePay;
    }

}
