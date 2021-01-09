/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cazzendra.pos.dao;

import cazzendra.pos.model.Customer;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author personal
 */
public interface CustomerDao {
    
    boolean addCustomer(Customer customer) throws SQLException;
    
    boolean updateCustomer(Customer customer) throws SQLException;
    
    boolean deleteCustomer(int customerId) throws SQLException; 
    
    ResultSet getAllCustomers() throws SQLException;
    
    ResultSet getCustomerByOneAttribute(String attribute, String condition, String value) throws SQLException; 
    
}
