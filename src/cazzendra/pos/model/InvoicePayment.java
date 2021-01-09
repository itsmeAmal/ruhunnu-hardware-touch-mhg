/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cazzendra.pos.model;

import java.math.BigDecimal;
import java.sql.Date;

/**
 *
 * @author Amal
 */
public class InvoicePayment {

    private int Id;
    private Date Date;
    private BigDecimal Total;
    private BigDecimal DiscountRate;
    private BigDecimal Value;
    private BigDecimal Cash;
    private BigDecimal DiscountedTotal;
    private int InvoiceNo;
    private int CustomerId;
    private String Description;
    private BigDecimal ChequePayment;

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
     * @return the DiscountRate
     */
    public BigDecimal getDiscountrate() {
        return DiscountRate;
    }

    /**
     * @param discountrate the DiscountRate to set
     */
    public void setDiscountrate(BigDecimal discountrate) {
        this.DiscountRate = discountrate;
    }

    /**
     * @return the Value
     */
    public BigDecimal getValue() {
        return Value;
    }

    /**
     * @param value the Value to set
     */
    public void setValue(BigDecimal value) {
        this.Value = value;
    }

    /**
     * @return the Cash
     */
    public BigDecimal getCash() {
        return Cash;
    }

    /**
     * @param cash the Cash to set
     */
    public void setCash(BigDecimal cash) {
        this.Cash = cash;
    }

    /**
     * @return the DiscountedTotal
     */
    public BigDecimal getDiscountedTotal() {
        return DiscountedTotal;
    }

    /**
     * @param discountedTotal the DiscountedTotal to set
     */
    public void setDiscountedTotal(BigDecimal discountedTotal) {
        this.DiscountedTotal = discountedTotal;
    }

    /**
     * @return the InvoiceNo
     */
    public int getInvoiceNo() {
        return InvoiceNo;
    }

    /**
     * @param invoiceNo the InvoiceNo to set
     */
    public void setInvoiceNo(int invoiceNo) {
        this.InvoiceNo = invoiceNo;
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
     * @return the Description
     */
    public String getDescription() {
        return Description;
    }

    /**
     * @param description the Description to set
     */
    public void setDescription(String description) {
        this.Description = description;
    }

    /**
     * @return the ChequePayment
     */
    public BigDecimal getChequePayment() {
        return ChequePayment;
    }

    /**
     * @param ChequePayment the ChequePayment to set
     */
    public void setChequePayment(BigDecimal ChequePayment) {
        this.ChequePayment = ChequePayment;
    }

}
