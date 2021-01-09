/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cazzendra.pos.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 4m4l
 */
public class DatabaseConnection {

    private static Connection con = null;

    public static Connection getDatabaseConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
//            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cazzendra_pos_v2", "root", Loading.getDbPassword());
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cloud_revel_billing", "root", "1234");
//            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/id7788168_pos", "id7788168_cazzendra", "910550292v");
            return con;
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return con;
    }
}
