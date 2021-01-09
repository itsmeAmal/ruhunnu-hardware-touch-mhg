/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cazzendra.pos.dao;

import cazzendra.pos.model.Supplier;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public interface SupplierDao {
    
    public boolean AddSupplier(Supplier Supplier)throws SQLException; 
    
    public boolean UpdateSupplier(Supplier Supplier)throws SQLException;
    
    public boolean DeleteSupplier(int SupplierId)throws SQLException;
    
    public ResultSet GetSupplierResultsetGetOneAttribute(String Attrbute, String Condition, String Value)throws SQLException;
    
    public ResultSet GetSupplierByMoreAttributes(ArrayList<String[]> AttributeConditionValue, String Operator)throws SQLException;
    
    public ResultSet GetAllSuppliers()throws SQLException;

}
