/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cazzendra.pos.control;

import cazzendra.pos.daoImpl.CategoryDaoImpl;
import cazzendra.pos.model.Category;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author personal
 */
public class CategoryControl {

    public static boolean AddCategory(String CategoryName, String Detail) throws SQLException {
        Category Cate = new Category();
        Cate.setCategoryDetail(Detail);
        Cate.setCategoryName(CategoryName);
        return new CategoryDaoImpl().AddCategory(Cate);
    }

    public static ResultSet GetAllCategories() throws SQLException {
        return new CategoryDaoImpl().GetAllCategories();
    }

    public static boolean UpdateCategory(String CategoryName, String Detail, int CategoryId) throws SQLException {
        Category Cate = new Category();
        Cate.setCategoryDetail(Detail);
        Cate.setCategoryName(CategoryName);
        Cate.setId(CategoryId);
        return new CategoryDaoImpl().UpdateCategory(Cate);
    }

    public static ResultSet GetCategoryByOneAttribute(String Attribute, String Condition, String Value) throws SQLException {
        return new CategoryDaoImpl().GetCategoryByOneAttribute(Attribute, Condition, Value);
    }

    public static ResultSet GetCategoryByMoreAttributes(ArrayList<String[]> AttributeConditionValueList, String Operator) throws SQLException {
        return new CategoryDaoImpl().GetCategoriesByMoreAttribute(AttributeConditionValueList, Operator);
    }

    public static boolean IsCategoryNameExist(String CategoryName) throws SQLException {
        boolean status = false;
        ResultSet Rset = GetAllCategories();
        while (Rset.next()) {             
            if(CategoryName.equalsIgnoreCase(Rset.getString("main_category_name"))){ 
                status = true;
                return status;
            }
        }
        return status;
    }

}
