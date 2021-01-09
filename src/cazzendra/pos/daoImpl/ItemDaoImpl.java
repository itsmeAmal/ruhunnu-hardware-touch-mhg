/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cazzendra.pos.daoImpl;

import cazzendra.pos.connection.DatabaseConnection;
import cazzendra.pos.core.Loading;
import cazzendra.pos.core.MethodStatus;
import cazzendra.pos.core.Validations;
import cazzendra.pos.dao.ItemDao;
import cazzendra.pos.model.Item;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Amal
 */
public class ItemDaoImpl implements ItemDao {

    private String selectQuery = "select item_id, item_name, item_code, "
            + " item_barcode, item_reorder_level, item_status_1, item_status_2,"
            + " item_status_3, item_selling_price, item_category, item_sub_category, item_purchasing_price "
            + " from item";

    @Override
    public MethodStatus addItem(Item item) throws SQLException {
        Connection con = DatabaseConnection.getDatabaseConnection();
        PreparedStatement ps = con.prepareStatement("insert into item(item_id, "
                + " item_name, item_code, item_barcode, item_reorder_level,"
                + " item_selling_price, item_category, item_sub_category) "
                + " values (?,?,?,?,?,?,?,?)");
        ps.setInt(1, item.getItemId());
        ps.setString(2, item.getItemName());
        ps.setString(3, item.getItemCode());
        ps.setString(4, item.getBarcode());
        ps.setInt(5, item.getReOrderLevel());
        ps.setBigDecimal(6, item.getSellingPrice());
        ps.setString(7, item.getCategory());
        ps.setString(8, item.getSubCategory());
        ps.executeUpdate();
        ps.close();
        return MethodStatus.SUCCESS;
    }

    @Override
    public MethodStatus updateItem(Item item) throws SQLException {
        Connection con = DatabaseConnection.getDatabaseConnection();
        PreparedStatement ps = con.prepareStatement("update item set item_name=?, item_barcode=?, item_reorder_level=?, item_category=? where item_id=?");
        ps.setString(1, item.getItemName());
        ps.setString(2, item.getBarcode());
        ps.setInt(3, item.getReOrderLevel());
        ps.setString(4, item.getCategory()); 
        ps.setInt(5, item.getItemId());
        ps.executeUpdate();
        ps.close();
        return MethodStatus.SUCCESS;
    }

    @Override
    public boolean removeItem(int itemId) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ResultSet getAllItems() throws SQLException {
        return new CommonDaoImpl().getAllRecords(selectQuery);
    }

    @Override
    public ResultSet getItemByOneAttribute(String attribute, String condition, String value) throws SQLException {
        return new CommonDaoImpl().getResultByAttribute(selectQuery, attribute, condition, value);
    }

    public int getNextItemId() throws SQLException {
        int maxItemId = (Loading.getCounterId()) * 1000000;
        Connection con = DatabaseConnection.getDatabaseConnection();
        PreparedStatement ps = con.prepareStatement("select max(item_id) as item_id from item where item_id between ? and ?");
        int minvalue = Loading.getCounterId() * 1000000;
        int maxValue = (Loading.getCounterId() + 1) * 1000000;
        ps.setInt(1, minvalue);
        ps.setInt(2, maxValue);
        ResultSet rset = ps.executeQuery();
        while (rset.next()) {
            if (Validations.isLong(rset.getString(1))) {
                maxItemId = rset.getInt(1);
            }
        }
        return ++maxItemId;
    }

    public MethodStatus updateItemPrice(String retailPrice, int itemId) throws SQLException {
        Connection con = DatabaseConnection.getDatabaseConnection();
        PreparedStatement ps = con.prepareStatement("update item set item_selling_price=? where item_id=?");
        ps.setBigDecimal(1, Validations.getBigDecimalOrZeroFromString(retailPrice));
        ps.setInt(2, itemId);
        ps.executeUpdate();
        return MethodStatus.SUCCESS;
    }

    public ResultSet getItemsByMoreAttributes(ArrayList<String[]> attributeConditionValueList, String operator) throws SQLException {
        return new CommonDaoImpl().getResultByAttributesWithJoinOperator(selectQuery, attributeConditionValueList, operator);
    }
}
