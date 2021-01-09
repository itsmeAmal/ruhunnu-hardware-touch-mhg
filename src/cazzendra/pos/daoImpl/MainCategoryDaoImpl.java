/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cazzendra.pos.daoImpl;

import cazzendra.pos.connection.DatabaseConnection;
import cazzendra.pos.dao.MainCategoryDao;
import cazzendra.pos.model.MainCategory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author personal
 */
public class MainCategoryDaoImpl implements MainCategoryDao {

    private String SelectQuery = "select main_category_id, main_category_name, main_category_detail, main_category_status "
            + " from main_category";

    @Override
    public boolean AddMainCategory(MainCategory MainCategory) throws SQLException {
        Connection con = DatabaseConnection.getDatabaseConnection();
        PreparedStatement ps = con.prepareStatement("insert into main_category(main_category_name, "
                + "main_category_detail, main_category_status) values (?,?,?)");
        ps.setString(1, MainCategory.getName());
        ps.setString(2, MainCategory.getDetail());
        ps.setInt(2, MainCategory.getStatus());
        ps.executeUpdate();
        ps.close();
        return true;
    }

    @Override
    public boolean UpdateMainCategory(MainCategory MainCategory) throws SQLException {
        Connection con = DatabaseConnection.getDatabaseConnection();
        PreparedStatement ps = con.prepareStatement("update main_category set main_category_name=?, "
                + "main_category_detail=?, main_category_status=? where main_category_id=?");
        ps.setString(1, MainCategory.getName());
        ps.setString(2, MainCategory.getDetail());
        ps.setInt(2, MainCategory.getStatus());
        ps.executeUpdate();
        ps.close();
        return true;
    }

    @Override
    public boolean DeleteMainCategory(int MainCategoryId) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ResultSet GetMainCategoryResultByOneAttribute(String Attribute, String Condition, String Value) throws SQLException {
        return new CommonDaoImpl().getResultByAttribute(SelectQuery, Attribute, Condition, Value);
    }

    @Override
    public ResultSet GetMainCategoryResultsetByMoreAttributes(ArrayList<String[]> AttributeConditionValueList, String Operator) throws SQLException {
        return new CommonDaoImpl().getResultByAttributesWithJoinOperator(SelectQuery, AttributeConditionValueList, Operator);
    }

    @Override
    public ResultSet GetAllMainCategories() throws SQLException {
        return new CommonDaoImpl().getAllRecords(SelectQuery);
    }

}
