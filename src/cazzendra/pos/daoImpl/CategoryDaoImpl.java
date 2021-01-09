/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cazzendra.pos.daoImpl;

import cazzendra.pos.connection.DatabaseConnection;
import cazzendra.pos.dao.CategoryDao;
import cazzendra.pos.model.Category;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author personal
 */
public class CategoryDaoImpl implements CategoryDao {

    private String SelectQuery = "select main_category_id, main_category_name, main_category_detail, main_category_status from main_category";

    @Override
    public boolean AddCategory(Category Category) throws SQLException {
        Connection con = DatabaseConnection.getDatabaseConnection();
        PreparedStatement ps = con.prepareStatement("insert into main_category (main_category_name, main_category_detail) values (?,?)");
        ps.setString(1, Category.getCategoryName());
        ps.setString(2, Category.getCategoryDetail());
        ps.executeUpdate();
        ps.close();
        return true;
    }

    @Override
    public boolean UpdateCategory(Category Category) throws SQLException {
        Connection con = DatabaseConnection.getDatabaseConnection();
        PreparedStatement ps = con.prepareStatement("update main_category set main_category_name=?, main_category_detail=? where main_category_id=?");
        ps.setString(1, Category.getCategoryName());
        ps.setString(2, Category.getCategoryDetail());
        ps.setInt(3, Category.getId());
        ps.executeUpdate();
        ps.close();
        return true;
    }

    @Override
    public boolean DeleteCategory(int CategoryId) throws SQLException {
        Connection con = DatabaseConnection.getDatabaseConnection();
        PreparedStatement ps = con.prepareStatement("delete from main_category where main_category_id=?");
        ps.setInt(1, CategoryId);
        ps.executeUpdate();
        ps.close();
        return true;
    }

    @Override
    public ResultSet GetAllCategories() throws SQLException {
        return new CommonDaoImpl().getAllRecords(SelectQuery);
    }

    @Override
    public ResultSet GetCategoryByOneAttribute(String Attribute, String Condition, String Value) throws SQLException {
       return new CommonDaoImpl().getResultByAttribute(SelectQuery, Attribute, Condition, Value);
    }

    @Override
    public ResultSet GetCategoriesByMoreAttribute(ArrayList<String[]> AttributeConditionValueList, String Operator) throws SQLException {
        return new CommonDaoImpl().getResultByAttributesWithJoinOperator(SelectQuery, AttributeConditionValueList, Operator);
    }

}
