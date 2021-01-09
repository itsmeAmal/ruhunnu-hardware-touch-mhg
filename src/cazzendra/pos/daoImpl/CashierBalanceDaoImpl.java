/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cazzendra.pos.daoImpl;

import cazzendra.pos.connection.DatabaseConnection;
import cazzendra.pos.dao.CashierBalanceDao;
import cazzendra.pos.model.CashierBalance;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author personal
 */
public class CashierBalanceDaoImpl implements CashierBalanceDao {

    private String selectQuery = "select cashier_balance_id, cashier_balance_date, cashier_balance_amount, "
            + " cashier_balance_description, cashier_balance_type from cashier_balance";

    @Override
    public boolean addCashierBalance(CashierBalance cashierBalance) throws SQLException {
        Connection con = DatabaseConnection.getDatabaseConnection();
        PreparedStatement ps = con.prepareStatement("insert into cashier_balance(cashier_balance_amount, "
                + "cashier_balance_description, cashier_balance_type) values (?,?,?)");
        ps.setBigDecimal(1, cashierBalance.getAmount());
        ps.setString(2, cashierBalance.getRemark());
        ps.setInt(3, cashierBalance.getType());
        ps.executeUpdate();
        ps.close();
        return true;
    }

    @Override
    public boolean updateCashierBalance(CashierBalance cashierBalance) throws SQLException {
        Connection con = DatabaseConnection.getDatabaseConnection();
        PreparedStatement ps = con.prepareStatement("update cashier_balance set cashier_balance_date, cashier_balance_amount,"
                + " cashier_balance_description, cashier_balance_type where cashier_balance_id=?");
        ps.setDate(1, cashierBalance.getDate());
        ps.setBigDecimal(2, cashierBalance.getAmount());
        ps.setString(3, cashierBalance.getRemark());
        ps.setInt(4, cashierBalance.getType());
        ps.setInt(5, cashierBalance.getId());
        ps.executeUpdate();
        ps.close();
        return true;
    }

    @Override
    public boolean deleteCashierBalance(int id) throws SQLException {
        Connection con = DatabaseConnection.getDatabaseConnection();
        PreparedStatement ps = con.prepareStatement("delete from cashier_balance where cashier_balance_id=?");
        ps.setInt(1, id);
        ps.executeUpdate();
        ps.close();
        return true;
    }

    @Override
    public ResultSet getCashierBalanceByOneAttribute(String attribute, String condition, String value) throws SQLException {
        return new CommonDaoImpl().getResultByAttribute(selectQuery, attribute, condition, value);
    }

    @Override
    public ResultSet getCashierBalanceByMoreAttributes(ArrayList<String[]> attributeConditionValueList, String condition) throws SQLException {
        return new CommonDaoImpl().getResultByAttributesWithJoinOperator(selectQuery, attributeConditionValueList, condition);
    }

    @Override
    public ResultSet getAllCashierBalanceRecords() throws SQLException {
        return new CommonDaoImpl().getAllRecords(selectQuery);
    }

    public BigDecimal getAmountByTypeAndDate(Date date, int type) throws SQLException {
        BigDecimal amount = BigDecimal.ZERO;
        Connection con = DatabaseConnection.getDatabaseConnection();
        PreparedStatement ps = con.prepareStatement("select cashier_balance_amount FROM "
                + " cashier_balance where date(cashier_balance_date)=? and cashier_balance_type=?");
        ps.setDate(1, date);
        ps.setInt(2, type);
        ResultSet rset = ps.executeQuery();
        while (rset.next()) {
            amount = rset.getBigDecimal("cashier_balance_amount");
        }
        return amount;
    }

}
