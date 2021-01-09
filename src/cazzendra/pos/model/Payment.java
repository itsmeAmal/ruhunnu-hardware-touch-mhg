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
 * @author personal
 */
public class Payment {

    private int Id;
    private String PaymentType;
    private BigDecimal Amount;
    private Date Date;
    private int ReferenceInvoiceNo;
    private int CustomerId;
    private String Detail_1;
    private String Detail_2;
    private int Status;

    public static String CASH = "Cash";
    public static String CHEQUE = "Cheque";

    /**
     * @return the Id
     */
    public int getId() {
        return Id;
    }

    /**
     * @param Id the Id to set
     */
    public void setId(int Id) {
        this.Id = Id;
    }

    /**
     * @return the PaymentType
     */
    public String getPaymentType() {
        return PaymentType;
    }

    /**
     * @param PaymentType the PaymentType to set
     */
    public void setPaymentType(String PaymentType) {
        this.PaymentType = PaymentType;
    }

    /**
     * @return the Amount
     */
    public BigDecimal getAmount() {
        return Amount;
    }

    /**
     * @param Amount the Amount to set
     */
    public void setAmount(BigDecimal Amount) {
        this.Amount = Amount;
    }

    /**
     * @return the Date
     */
    public Date getDate() {
        return Date;
    }

    /**
     * @param Date the Date to set
     */
    public void setDate(Date Date) {
        this.Date = Date;
    }

    /**
     * @return the ReferenceInvoiceNo
     */
    public int getReferenceInvoiceNo() {
        return ReferenceInvoiceNo;
    }

    /**
     * @param ReferenceInvoiceNo the ReferenceInvoiceNo to set
     */
    public void setReferenceInvoiceNo(int ReferenceInvoiceNo) {
        this.ReferenceInvoiceNo = ReferenceInvoiceNo;
    }

    /**
     * @return the CustomerId
     */
    public int getCustomerId() {
        return CustomerId;
    }

    /**
     * @param CustomerId the CustomerId to set
     */
    public void setCustomerId(int CustomerId) {
        this.CustomerId = CustomerId;
    }

    /**
     * @return the Detail_1
     */
    public String getDetail_1() {
        return Detail_1;
    }

    /**
     * @param Detail_1 the Detail_1 to set
     */
    public void setDetail_1(String Detail_1) {
        this.Detail_1 = Detail_1;
    }

    /**
     * @return the Detail_2
     */
    public String getDetail_2() {
        return Detail_2;
    }

    /**
     * @param Detail_2 the Detail_2 to set
     */
    public void setDetail_2(String Detail_2) {
        this.Detail_2 = Detail_2;
    }

    /**
     * @return the Status
     */
    public int getStatus() {
        return Status;
    }

    /**
     * @param Status the Status to set
     */
    public void setStatus(int Status) {
        this.Status = Status;
    }
}
