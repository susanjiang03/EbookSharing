/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ebooksharing_susan;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author lingshanjiang
 */
public class DbConnector{
  
  
   static  String db="EbookSharing";
   static  String username="root";
   static  String password="123";
   
   public static Connection conn=null;
   
    public static void main(String[] args){
        Connects();
    }
    public static Connection Connects(){
        
   
        try{
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            conn = DriverManager.getConnection("jdbc:derby://localhost:1527/"+db,username,password);
            System.out.println("connected to DB succefully.");
        }
        catch(ClassNotFoundException | SQLException e){
            //JOptionPane=showMessageDialog(null,e);
            System.out.println("error");
        }
       return conn;
    }
  
     /*public static Connection conn=null;
   
   public static final String DRIVER="org.apache.derby.jdbc.EmbeddedDriver";
   public static final String JDBC_URL="jdbc:derby:EmbeddedEbook;create=True";
   
    public DbConnector(){
        connect();
        //frame();
    }
    
    public static Connection connect(){
        
        try{
        
        Class.forName(DRIVER);
        conn=DriverManager.getConnection(JDBC_URL);
       System.out.println("connected to DB succefully.");
        
        }catch(Exception e){
            System.out.println(e);
        }
        return conn;
    }
    
    
    public static void main(String[] args){
        connect();
    }
    */
}
    