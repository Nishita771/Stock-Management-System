/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emart.dbutil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author DELL
 */
public  class DBConnection {
   private static Connection conn=null;
   static{
       try{
           Class.forName("oracle.jdbc.OracleDriver");
           conn=DriverManager.getConnection("jdbc:oracle:thin:@//DESKTOP-EJ254C5:1521/XE","advjavabatch","mystudents");
           JOptionPane.showMessageDialog(null,"Connection opened Successfully","Success",JOptionPane.INFORMATION_MESSAGE);
       }
       catch(ClassNotFoundException ex)
       {
           JOptionPane.showMessageDialog(null,"Error in loading the driver","Failure",JOptionPane.ERROR_MESSAGE);
           ex.printStackTrace();
           System.exit(1);
       }
       catch(SQLException ex)
       {
           JOptionPane.showMessageDialog(null,"Connection not opened","Failure",JOptionPane.ERROR_MESSAGE);
           ex.printStackTrace();
           System.exit(1);
           
       }
   }
   public static Connection getConnection()
   {
       return conn;
   }
   public static void closeConnection()
   {
       try{
           conn.close();
           JOptionPane.showMessageDialog(null,"Connection Closed Successfully","Success",JOptionPane.INFORMATION_MESSAGE);
           
       }
       catch(SQLException ex)
       {
           JOptionPane.showMessageDialog(null,"Cannot close Connection" ,"DB Error!",JOptionPane.ERROR_MESSAGE);
           ex.printStackTrace();
       }
   }
}
