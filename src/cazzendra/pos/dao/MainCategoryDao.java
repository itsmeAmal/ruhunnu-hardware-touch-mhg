
package cazzendra.pos.dao;

import cazzendra.pos.model.MainCategory;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author personal
 */
public interface MainCategoryDao {
    
    public boolean AddMainCategory(MainCategory MainCategory) throws SQLException;
    
    public boolean UpdateMainCategory(MainCategory MainCategory)throws SQLException;
    
    public boolean DeleteMainCategory(int MainCategoryId)throws SQLException; 
    
    public ResultSet GetMainCategoryResultByOneAttribute(String Attribute, String Condition, String Value)throws SQLException;
    
    public ResultSet GetMainCategoryResultsetByMoreAttributes(ArrayList<String[]> AttributeConditionValueList, String Operator)throws SQLException;
    
    public ResultSet GetAllMainCategories()throws SQLException;

}
