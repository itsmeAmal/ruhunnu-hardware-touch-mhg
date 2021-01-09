/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cazzendra.pos.control;

import cazzendra.pos.core.Validations;
import cazzendra.pos.daoImpl.CashierBalanceDaoImpl;
import cazzendra.pos.model.CashierBalance;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;

/**
 *
 * @author personal
 */
public class CashierBalanceControl {

    public static boolean addCashierBalance(int type, String amount, String remark) throws SQLException {
        CashierBalance cashierBalance = new CashierBalance();
        cashierBalance.setAmount(Validations.getBigDecimalOrZeroFromString(amount));
        cashierBalance.setRemark(remark);
        cashierBalance.setType(type);
        return new CashierBalanceDaoImpl().addCashierBalance(cashierBalance);
    }

    public static BigDecimal getAmountByTypeAndDate(Date date, int type) throws SQLException {
        return new CashierBalanceDaoImpl().getAmountByTypeAndDate(date, type);
    }

//    public static boolean isExistRecordForDay(Date date, int status) throws SQLException {
//        boolean returnStatus = false;
//        
//    }

}
