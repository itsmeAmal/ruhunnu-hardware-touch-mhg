/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cazzendra.pos.dao;

import cazzendra.pos.model.Payment;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author personal
 */
public interface PaymentDao {

    boolean AddPayment(Payment Payment) throws SQLException;

    ResultSet GetAllPaymnts() throws SQLException;

    ResultSet GetPaymentByOneAttribute(String Attribute, String Condition, String Value) throws SQLException;

}
