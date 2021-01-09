/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cazzendra.pos.dao;

import cazzendra.pos.model.CashierBalance;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author personal
 */
public interface CashierBalanceDao {

    public boolean addCashierBalance(CashierBalance cashierBalance) throws SQLException;

    public boolean updateCashierBalance(CashierBalance cashierBalance) throws SQLException;

    public boolean deleteCashierBalance(int id) throws SQLException;

    ResultSet getCashierBalanceByOneAttribute(String attribute, String condition, String value) throws SQLException;

    ResultSet getCashierBalanceByMoreAttributes(ArrayList<String[]> attributeConditionValueList, String condition) throws SQLException;
    
    ResultSet getAllCashierBalanceRecords()throws SQLException;
    
}
