/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cazzendra.pos.control;

import cazzendra.pos.daoImpl.SubCategoryDaoImpl;
import cazzendra.pos.model.SubCategory;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author personal
 */
public class SubCategoryControl {

    public static ResultSet GetAllSubCategories() throws SQLException {
        return new SubCategoryDaoImpl().GetAllSubCategories();
    }

    public static boolean AddSubCategory(int MainCategoryId, String SubCategoryName,
            String SubCategoryDetail, String MainCategoryName)
            throws SQLException {
        SubCategory SubCategory = new SubCategory();
        SubCategory.setDetail(SubCategoryDetail);
        SubCategory.setMainCategoryId(MainCategoryId);
        SubCategory.setMainCategoryName(MainCategoryName);
        SubCategory.setName(SubCategoryName);
        return new SubCategoryDaoImpl().AddSubCategory(SubCategory);
    }

    public static boolean UpdateSubCategory(int MainCategoryId, String SubCategoryName,
            String SubCategoryDetail, String MainCategoryName) throws SQLException {
        SubCategory SubCategory = new SubCategory();
        SubCategory.setDetail(SubCategoryDetail);
        SubCategory.setMainCategoryId(MainCategoryId);
        SubCategory.setMainCategoryName(MainCategoryName);
        SubCategory.setName(SubCategoryName);
        return new SubCategoryDaoImpl().UpdateSubCategory(SubCategory);
    }

    public static ResultSet GetSubCategoryByOneAttribute(String Attribute,
            String Condition, String Value) throws SQLException {
        return new SubCategoryDaoImpl().GetSubCategoryResultByOneAttribute(Attribute, Condition, Value);
    }

}
