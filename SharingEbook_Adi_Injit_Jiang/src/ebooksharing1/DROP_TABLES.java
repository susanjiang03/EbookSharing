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
public class DROP_TABLES {
      public static void main(String args[]) throws SQLException {
         
       DbConnector dbc = new DbConnector();
       Connection conn = dbc.Connects();
       Statement stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
       
       String drop_pendingbook="DROP Table pendingbook";
       String drop_complaints="Drop Table review_rating";
       String drop_readinghistory="Drop Table readinghistory";
       String drop_message="DROP TABLE message";
       String drop_invitation="Drop table invitation";
       String drop_sharedbooks="Drop table sharedbooks";
       String drop_complaint="DROP TABLE complaint";
       String drop_bookinfo="DROP TABLE bookinfo";
       String drop_userinfo="DROP TABLE userinfo";
       String drop_blacklist="DROP TABLE blacklist";
               
       
       conn.setAutoCommit(false);
      //stmt.addBatch():
      
      //drop
      stmt.addBatch(drop_pendingbook);
      stmt.addBatch(drop_complaints);
      stmt.addBatch(drop_readinghistory);
      stmt.addBatch(drop_message);
      stmt.addBatch(drop_invitation);
      stmt.addBatch(drop_blacklist);
      stmt.addBatch(drop_sharedbooks);
      stmt.addBatch(drop_complaint);
      stmt.addBatch(drop_bookinfo);
      stmt.addBatch(drop_userinfo);
      
    
     
      stmt.executeBatch();
      conn.commit();
      
      System.out.println("Drop Table pendingbook");
      System.out.println("Drop Table complaint");
      System.out.println("Drop Table readinghistory");
      //System.out.println("Drop Table to message");
      System.out.println("Drop Table inviatation");
      System.out.println("Drop Table complaint");
      System.out.println("Drop Table bookinfo");
      System.out.println("Drop Table userinfo");
      System.out.println("Drop Table blacklist");
      System.out.println("Run Sucessfully.\nBatch executed.\nAll tables drop.");
     }
    
}
