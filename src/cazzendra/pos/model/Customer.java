/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cazzendra.pos.model;

/**
 *
 * @author personal
 */
public class Customer {
        
    private int id;
    private String customerName;
    private String customerContact1;
    private String customerContact2;
    private String address;
    private String email;
    private String detail;
    private int status;

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the customerName
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * @param customerName the customerName to set
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * @return the customerContact1
     */
    public String getCustomerContact1() {
        return customerContact1;
    }

    /**
     * @param customerContact1 the customerContact1 to set
     */
    public void setCustomerContact1(String customerContact1) {
        this.customerContact1 = customerContact1;
    }

    /**
     * @return the customerContact2
     */
    public String getCustomerContact2() {
        return customerContact2;
    }

    /**
     * @param customerContact2 the customerContact2 to set
     */
    public void setCustomerContact2(String customerContact2) {
        this.customerContact2 = customerContact2;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the detail
     */
    public String getDetail() {
        return detail;
    }

    /**
     * @param detail the detail to set
     */
    public void setDetail(String detail) {
        this.detail = detail;
    }

    /**
     * @return the status
     */
    public int getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(int status) {
        this.status = status;
    }
    
    
    
}
