package com.ekdant.dentalsolution.utilities;
import java.sql.*;
import javax.swing.*;
/**
 *
 * @author Sushant
 */
public class ConnectionPool {
    final private static String driver = "org.sqlite.JDBC"; // MySQL JDBC driver
    final private static String mydatabase = "EkDant";
    final private static String ip = "localhost";
    final private static String port = "3306";
    final private static String url = "jdbc:sqlite:c:/EkDant/ConvertedEkdant.sqlite" ;
    final private static String username = "root";
    final private static String password = "root";
    private static ConnectionPool connection;
    private static Connection conn = null;
    public Statement stmt = null;
    
    public static ConnectionPool getInstance(){
        if(connection == null){
            try {
                Class.forName(driver);
                connection = new ConnectionPool();
                
                connection.conn = DriverManager.getConnection(url, username, password);
            } catch (ClassNotFoundException ex) {System.out.println(ex.getMessage());} 
            catch (SQLException ex) {System.out.println(ex.getMessage());}
            System.out.println("Connected :D");
        }
        return connection;
    }

    public void CloseDB(){

    }
    
    public ResultSet getResult(String sql){
        ResultSet result = null;
        try{           
            stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            result   = stmt.executeQuery(sql);
        }
        catch(SQLException erroSQL){
             JOptionPane.showMessageDialog(null,"The SQL command not work: "+erroSQL+",  SQL past: "+sql);
        }
        return result;
    }

    public void QuerySQL(String sql){
        ResultSet rs;
        try{           
            stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            rs   = stmt.executeQuery(sql);
        }
        catch(SQLException erroSQL){
             JOptionPane.showMessageDialog(null,"The SQL command not work: "+erroSQL+",  SQL past: "+sql);
        }
    }
}