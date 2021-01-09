/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cazzendra.pos.dao;

import cazzendra.pos.core.MethodStatus;
import cazzendra.pos.model.GrnDetail;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Amal
 */
public interface GrnDetailDao {

    MethodStatus addgrnDetail(GrnDetail grnDetail) throws SQLException;

    ResultSet getAllGrnDetails() throws SQLException;

}
