/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cazzendra.pos.daoImpl;

import cazzendra.pos.connection.DatabaseConnection;
import cazzendra.pos.dao.SubCategoryDao;
import cazzendra.pos.model.SubCategory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author personal
 */
public class SubCategoryDaoImpl implements SubCategoryDao {

    private String SelectQuery = "select sub_category_id, sub_category_name, sub_category_detail, sub_category_status, "
            + "sub_category_main_category_id, sub_category_main_category_name from sub_category";

    @Override
    public boolean AddSubCategory(SubCategory SubCategory) throws SQLException {
        Connection con = DatabaseConnection.getDatabaseConnection();
        PreparedStatement ps = con.prepareStatement("insert into sub_category (sub_category_name, sub_category_detail, "
                + "sub_category_main_category_id, sub_category_main_category_name) values (?,?,?,?);");
        ps.setString(1, SubCategory.getName());
        ps.setString(2, SubCategory.getDetail());
        ps.setInt(3, SubCategory.getMainCategoryId());
        ps.setString(4, SubCategory.getMainCategoryName());
        ps.executeUpdate();
        ps.close();
        return true;
    }

    @Override
    public boolean UpdateSubCategory(SubCategory SubCategory) throws SQLException {
        Connection con = DatabaseConnection.getDatabaseConnection();
        PreparedStatement ps = con.prepareStatement("update sub_category set "
                + "sub_category_name=?, sub_category_detail=?, sub_category_main_category_id=?, "
                + "sub_category_main_category_name=? where sub_category_id=?");
        ps.setString(1, SubCategory.getName());
        ps.setString(2, SubCategory.getDetail());
        ps.setInt(3, SubCategory.getMainCategoryId());
        ps.setString(4, SubCategory.getMainCategoryName());
        ps.setInt(5, SubCategory.getId()); 
        ps.executeUpdate();
        ps.close();
        return true;
    }

    @Override
    public boolean DeleteSubCategory(int SubCategoryId) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ResultSet GetSubCategoryResultByOneAttribute(String Attribute, String Condition, String Value) throws SQLException {
        return new CommonDaoImpl().getResultByAttribute(SelectQuery, Attribute, Condition, Value);
    }

    @Override
    public ResultSet GetSubCategoryResultsetByMoreAttributes(ArrayList<String[]> AttributeConditionValueList, String Operator) throws SQLException {
        return new CommonDaoImpl().getResultByAttributesWithJoinOperator(SelectQuery, AttributeConditionValueList, Operator);
    }

    @Override
    public ResultSet GetAllSubCategories() throws SQLException {
        return new CommonDaoImpl().getAllRecords(SelectQuery);
    }

}
