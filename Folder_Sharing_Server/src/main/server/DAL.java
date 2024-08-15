/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Osama_Alathwari
 */
public class DAL {

    private final String DB_URL = "jdbc:mysql://localhost:3306/file_sharing";
    private final String USER_NAME = "root";
    private final String USER_PASS = "";
    private Connection mConnection = null;
    private PreparedStatement statement;

    private static DAL dal = new DAL();

    public static DAL getConnectDAL() {
        return dal;
    }

    public DAL() {
        try {

            mConnection = DriverManager.getConnection(DB_URL, USER_NAME, USER_PASS);
        } catch (SQLException ex) {
            Logger.getLogger(DAL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ResultSet read(String sql, Object[] values) {
        ResultSet result = null;
        try {
            statement = mConnection.prepareStatement(sql);
            if (values != null) {
                for (int i = 0; i < values.length; i++) {
                    statement.setObject(i + 1, values[i]);
                }
            }
            result = statement.executeQuery();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    public boolean write(String sql, Object[] values) {
        boolean checkExecute = false;

        try {
            statement = mConnection.prepareStatement(sql);
            if (values != null) {
                for (int i = 0; i < values.length; i++) {
                    statement.setObject(i + 1, values[i]);
                }
            }
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                checkExecute = true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return checkExecute;
    }

}
