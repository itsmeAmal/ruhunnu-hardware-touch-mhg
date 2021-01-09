/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cazzendra.pos.dao;

import cazzendra.pos.core.MethodStatus;
import cazzendra.pos.model.Item;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Amal
 */
public interface ItemDao {

    MethodStatus addItem(Item item) throws SQLException;

    MethodStatus updateItem(Item item) throws SQLException;

    boolean removeItem(int itemId) throws SQLException;

    ResultSet getAllItems() throws SQLException;

    ResultSet getItemByOneAttribute(String attribute, String condition, String value) throws SQLException;

}
