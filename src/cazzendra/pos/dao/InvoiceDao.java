/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cazzendra.pos.dao;

import cazzendra.pos.model.Invoice;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Amal
 */
public interface InvoiceDao {

    int addInvoice(Invoice invoice) throws SQLException;

    ResultSet getAllInvoices() throws SQLException;

    ResultSet getInvoiceByOneAttribute(String attribute, String condition, String value) throws SQLException;

}
