/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cazzendra.pos.control;

import cazzendra.pos.core.CommonConstants;
import cazzendra.pos.daoImpl.CustomerDaoImpl;
import cazzendra.pos.model.Customer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author personal
 */
public class CustomerControl {

    public static boolean addCustomer(String name, String address, String contact,
            String contact2, String email, String remark) throws SQLException {
        Customer customer = new Customer();
        customer.setCustomerName(name);
        customer.setAddress(address);
        customer.setCustomerContact1(contact);
        customer.setCustomerContact2(contact2);
        customer.setEmail(email);
        customer.setDetail(remark);
        return new CustomerDaoImpl().addCustomer(customer);
    }

    public static boolean updateCustomer(String name, String address, String contact,
            String contact2, String email, String remark, int id) throws SQLException {
        Customer customer = new Customer();
        customer.setCustomerName(name);
        customer.setAddress(address);
        customer.setCustomerContact1(contact);
        customer.setCustomerContact2(contact2);
        customer.setEmail(email);
        customer.setDetail(remark);
        customer.setId(id);
        return new CustomerDaoImpl().updateCustomer(customer);
    }

    public static ResultSet getAllCustomers() throws SQLException {
        return new CustomerDaoImpl().getAllCustomers();
    }

    public static ResultSet getCustomersByAttribute(String attribute, String condition, String value) 
            throws SQLException {
        return new CustomerDaoImpl().getCustomerByOneAttribute(attribute, condition, value);
    }

    public static ResultSet getCustomersByMoreAttribute(ArrayList<String[]> AttributeConditionValueList, 
            String operator) throws SQLException {
        return new CustomerDaoImpl().getCustomerByMoreAttributes(AttributeConditionValueList, operator);
    }
    
    public static Customer getCustomerObjectByCustId(int custId)throws SQLException{
        ResultSet rset = getCustomersByAttribute("customer_id", CommonConstants.sql.EQUAL, Integer.toString(custId));
        Customer customer = null;
        while (rset.next()) {            
             customer = new Customer();
             customer.setAddress(rset.getString("customer_address")); 
             customer.setCustomerName(rset.getString("customer_name")); 
             customer.setCustomerContact1(rset.getString("customer_contact_1")); 
             customer.setCustomerContact2(rset.getString("customer_contact_2")); 
             customer.setId(rset.getInt("customer_id")); 
             customer.setEmail(rset.getString("customer_email")); 
             customer.setDetail(rset.getString("customer_detail")); 
             customer.setStatus(rset.getInt("customer_status")); 
        }
        return customer;
    }
}
