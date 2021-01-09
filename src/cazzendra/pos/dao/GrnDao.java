/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cazzendra.pos.dao;

import cazzendra.pos.model.Grn;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Amal
 */
public interface GrnDao {

    int addGrn(Grn grn) throws SQLException;

    ResultSet getAllGrn() throws SQLException;

    int getNextGrnId() throws SQLException;
}
