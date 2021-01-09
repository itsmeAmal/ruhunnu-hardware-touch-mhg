/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cazzendra.pos.dao;

import cazzendra.pos.model.Category;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author personal
 */
public interface CategoryDao {
    
    public boolean AddCategory(Category Category)throws SQLException;
    
    public boolean UpdateCategory(Category Category)throws SQLException;
    
    public boolean DeleteCategory(int CategoryId)throws SQLException;
    
    public ResultSet GetAllCategories()throws SQLException;
    
    public ResultSet GetCategoryByOneAttribute(String Attribute, String Condition, String Value)throws SQLException;
    
    public ResultSet GetCategoriesByMoreAttribute(ArrayList<String[]> AttributeConditionValueList, String Operator)throws SQLException;
    
}
