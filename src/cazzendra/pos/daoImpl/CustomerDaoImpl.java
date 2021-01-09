/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cazzendra.pos.daoImpl;

import cazzendra.pos.connection.DatabaseConnection;
import cazzendra.pos.dao.CustomerDao;
import cazzendra.pos.model.Customer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author personal
 */
public class CustomerDaoImpl implements CustomerDao{
    
    private String selectQuery = "select customer_id, customer_name, customer_contact_1, customer_contact_2, customer_address, customer_email, "
            + "customer_detail, customer_status from customer"; 

    @Override
    public boolean addCustomer(Customer customer)throws SQLException {
        Connection con = DatabaseConnection.getDatabaseConnection();
        PreparedStatement ps = con.prepareStatement("insert into customer(customer_name, customer_contact_1, customer_contact_2, customer_address,"
                + " customer_email, customer_detail) values(?,?,?,?,?,?)"); 
        ps.setString(1, customer.getCustomerName());
        ps.setString(2, customer.getCustomerContact1());
        ps.setString(3, customer.getCustomerContact2()); 
        ps.setString(4, customer.getAddress());
        ps.setString(5, customer.getEmail());
        ps.setString(6, customer.getDetail()); 
        ps.executeUpdate();
        ps.close();
        return true;
    }

    @Override
    public boolean updateCustomer(Customer customer) throws SQLException {
        Connection con = DatabaseConnection.getDatabaseConnection();
        PreparedStatement ps = con.prepareStatement("update customer set customer_name=?, customer_contact_1=?, customer_contact_2=?,"
                + " customer_address=?, customer_email=?, customer_detail=? where customer_id=?"); 
        ps.setString(1, customer.getCustomerName());
        ps.setString(2, customer.getCustomerContact1());
        ps.setString(3, customer.getCustomerContact2()); 
        ps.setString(4, customer.getAddress());
        ps.setString(5, customer.getEmail());
        ps.setString(6, customer.getDetail()); 
        ps.setInt(7, customer.getId()); 
        ps.executeUpdate();
        ps.close();
        return true;
    }

    @Override
    public boolean deleteCustomer(int customerId) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ResultSet getAllCustomers()throws SQLException {
       return new CommonDaoImpl().getAllRecords(selectQuery);
    }

    @Override
    public ResultSet getCustomerByOneAttribute(String attribute, String condition, String value) throws SQLException {
       return new CommonDaoImpl().getResultByAttribute(selectQuery, attribute, condition, value);
    }
    
    public ResultSet getCustomerByMoreAttributes(ArrayList<String[]> AttributeConditionValueList, String operator)throws SQLException{
        return new CommonDaoImpl().getResultByAttributesWithJoinOperator(selectQuery, AttributeConditionValueList, operator);
    }
}
