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
public class Grn {

    private int grnId;
    private Date date;
    private Time time;
    private BigDecimal cashPay;
    private BigDecimal cardPay;
    private BigDecimal chequePay;
    private String previewId;
    private int supplierId;
    private BigDecimal total;
    private BigDecimal discountRate;
    private int itemQty;

    /**
     * @return the grnId
     */
    public int getGrnId() {
        return grnId;
    }

    /**
     * @param grnId the grnId to set
     */
    public void setGrnId(int grnId) {
        this.grnId = grnId;
    }

    /**
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * @return the time
     */
    public Time getTime() {
        return time;
    }

    /**
     * @param time the time to set
     */
    public void setTime(Time time) {
        this.time = time;
    }

    /**
     * @return the cashPay
     */
    public BigDecimal getCashPay() {
        return cashPay;
    }

    /**
     * @param cashPay the cashPay to set
     */
    public void setCashPay(BigDecimal cashPay) {
        this.cashPay = cashPay;
    }

    /**
     * @return the cardPay
     */
    public BigDecimal getCardPay() {
        return cardPay;
    }

    /**
     * @param cardPay the cardPay to set
     */
    public void setCardPay(BigDecimal cardPay) {
        this.cardPay = cardPay;
    }

    /**
     * @return the chequePay
     */
    public BigDecimal getChequePay() {
        return chequePay;
    }

    /**
     * @param chequePay the chequePay to set
     */
    public void setChequePay(BigDecimal chequePay) {
        this.chequePay = chequePay;
    }

    /**
     * @return the previewId
     */
    public String getPreviewId() {
        return previewId;
    }

    /**
     * @param previewId the previewId to set
     */
    public void setPreviewId(String previewId) {
        this.previewId = previewId;
    }

    /**
     * @return the supplierId
     */
    public int getSupplierId() {
        return supplierId;
    }

    /**
     * @param supplierId the supplierId to set
     */
    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    /**
     * @return the total
     */
    public BigDecimal getTotal() {
        return total;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    /**
     * @return the discountRate
     */
    public BigDecimal getDiscountRate() {
        return discountRate;
    }

    /**
     * @param discountRate the discountRate to set
     */
    public void setDiscountRate(BigDecimal discountRate) {
        this.discountRate = discountRate;
    }

    /**
     * @return the itemQty
     */
    public int getItemQty() {
        return itemQty;
    }

    /**
     * @param itemQty the itemQty to set
     */
    public void setItemQty(int itemQty) {
        this.itemQty = itemQty;
    }

}
