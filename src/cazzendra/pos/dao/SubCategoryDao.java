
package cazzendra.pos.dao;

import cazzendra.pos.model.SubCategory;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public interface SubCategoryDao {
    
    public boolean AddSubCategory(SubCategory SubCategory) throws SQLException;
    
    public boolean UpdateSubCategory(SubCategory SubCategory)throws SQLException;
    
    public boolean DeleteSubCategory(int SubCategoryId)throws SQLException; 
    
    public ResultSet GetSubCategoryResultByOneAttribute(String Attribute, String Condition, String Value)throws SQLException;
    
    public ResultSet GetSubCategoryResultsetByMoreAttributes(ArrayList<String[]> AttributeConditionValueList, String Operator)throws SQLException;
    
    public ResultSet GetAllSubCategories()throws SQLException;
    
}
