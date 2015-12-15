/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ebooksharing1;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author lingshanjiang
 */
public class RESET_TABLES {
      public static void main(String args[]) throws SQLException {
         
       DbConnector dbc = new DbConnector();
       Connection conn = dbc.Connects();
       Statement stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
       
       String delete_pendingbook="DELETE FROM pendingbook";
       String delete_complaints="DELETE FROM review_rating";
       String delete_readinghistory="DELETE FROM readinghistory";
       String delete_message="DELETE FROM message";
       String delete_invitation="DELETE FROM invitation";
       String delete_sharedbooks="DELETE FROM sharedbooks";
       String delete_complaint="DELETE FROM complaint";
       String delete_bookinfo="DELETE FROM  bookinfo";
       String delete_userinfo="DELETE FROM  userinfo";
       String delete_blacklist="DELETE FROM blacklist";
               
       conn.setAutoCommit(false);
      //stmt.addBatch():
      
      //drop
      stmt.addBatch(delete_pendingbook);
      stmt.addBatch(delete_complaints);
      stmt.addBatch(delete_readinghistory);
      stmt.addBatch(delete_message);
      stmt.addBatch(delete_invitation);
      stmt.addBatch(delete_sharedbooks);
      stmt.addBatch(delete_complaint);
      stmt.addBatch(delete_bookinfo);
      stmt.addBatch(delete_userinfo);
      stmt.addBatch(delete_blacklist);
    
     
      stmt.executeBatch();
      conn.commit();
      
      System.out.println("Reset Table pendingbook");
      System.out.println("Reset  Table complaint");
      System.out.println("Reset Table readinghistory");
      System.out.println("Reset  Table to message");
      System.out.println("Reset  Table inviatation");
      System.out.println("Reset  Table sharedbooks");
      System.out.println("Reset  Table complaint");
      System.out.println("Reset  Table blacklist");
      System.out.println("Reset  Table bookinfo");
      System.out.println("Reset  Table userinfo");
      System.out.println("Run Sucessfully.\nBatch executed.\nAll tables Reset.");
     }
    
}
