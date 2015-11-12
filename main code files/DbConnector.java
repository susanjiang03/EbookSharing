/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ebooksharing;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author lingshanjiang
 */
public class DbConnector{
    
   public static Connection conn=null;
   
    public static void main(String[] args){
        Connects();
    }
    public static Connection Connects(){
        
        try{
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            conn = DriverManager.getConnection("jdbc:derby://localhost:1527/EbookSharing", "root", "123");
            System.out.println("connected to DB succefully.");
        }
        catch(ClassNotFoundException | SQLException e){
            //JOptionPane=showMessageDialog(null,e);
            System.out.println("error");
        }
       return conn;

     }
    
}
    