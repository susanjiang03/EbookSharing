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
public class CREATE_TABLES {
    
    public static void main(String args[]) throws SQLException {
        
       DbConnector dbc = new DbConnector();
       Connection conn = dbc.Connects();
       Statement stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
       
       String create_userinfo="CREATE TABLE  UserInfo\n" +
"(\n" +
"uID SMALLINT NOT NULL PRIMARY KEY\n" +
"GENERATED ALWAYS AS IDENTITY,\n" +
"username VARCHAR(20) NOT NULL UNIQUE,\n" +
"password VARCHAR(20) ,\n" +
"email    VARCHAR(50) ,\n" +
"firstname VARCHAR(20),\n" +
"lastname  VARCHAR(20),\n" +
"is_SU BOOLEAN DEFAULT FALSE,\n" +
"point_balance SMALLINT DEFAULT 0,\n" +
"book_got_removed SMALLINT DEFAULT 0,\n" +
"in_blacklist BOOLEAN DEFAULT FALSE\n" +
")";
       
  
       String create_bookinfo="CREATE TABLE  BookInfo\n" +
"(\n" +
"bookID  SMALLINT NOT NULL PRIMARY KEY\n" +
"GENERATED ALWAYS AS IDENTITY\n" +
"(START WITH 1, INCREMENT BY 1),\n" +
"bookname VARCHAR(100) ,\n" +
"cover BLOB ,\n" +
"author VARCHAR(50),\n" +
"summary VARCHAR(1000),\n" +
"category VARCHAR(20),\n" +
"bookfile BLOB,\n" +
"uploader VARCHAR(20) ,\n" +
"reward_points SMALLINT DEFAULT 0 ,\n" +
"reading_points SMALLINT DEFAULT 0,\n" +
"reading_counts SMALLINT DEFAULT 0,\n" +
"last_date_read DATE,\n" +
"reading_total_duration BIGINT DEFAULT 0,\n" +
"rating FLOAT DEFAULT 0.0,\n" +
"rating_counts SMALLINT DEFAULT 0,\n" +
"got_complainted SMALLINT DEFAULT 0,\n" +
"FOREIGN KEY (uploader) REFERENCES UserInfo(username)\n" +
")";
      
       
       String create_reviewrating="CREATE TABLE Review_Rating(\n" +
"rrID INT NOT NULL PRIMARY KEY\n" +
"GENERATED ALWAYS AS IDENTITY\n" +
"(START WITH 1, INCREMENT BY 1),\n" +
"bookID SMALLINT ,\n" +
"username VARCHAR(20),\n" +
"review_text VARCHAR(1000),\n" +
"rating SMALLINT,\n" +
"FOREIGN KEY (username) REFERENCES UserInfo(username),\n" +
"FOREIGN KEY (bookID) REFERENCES BookInfo(bookID)\n" +
")";   
      
             
       
     String create_pendingbook="CREATE TABLE  PendingBook(\n" +
"pbID INT NOT NULL PRIMARY KEY\n" +
"GENERATED ALWAYS AS IDENTITY\n" +
"(START WITH 1, INCREMENT BY 1),\n" +
"uploader VARCHAR(20) ,\n" +
"bookname VARCHAR(50) ,\n" +
"cover BLOB,\n" +
"author VARCHAR(50),\n" +
"summary VARCHAR(1000),\n" +
"category VARCHAR(200),\n" +
"bookfile BLOB ,\n" +
"request_points SMALLINT DEFAULT 0,\n" +
"granted_points SMALLINT DEFAULT 0,\n" +
"reading_points SMALLINT DEFAULT 0,\n" +
"FOREIGN KEY(uploader) REFERENCES UserInfo(username)\n" +
")";
       
       
       String create_complaints="CREATE TABLE complaint(\n" +
"cID INT NOT NULL PRIMARY KEY\n" +
"GENERATED ALWAYS AS IDENTITY\n" +
"(START WITH 1, INCREMENT BY 1),\n" +
"username VARCHAR(20),\n" +
"bookID SMALLINT,\n" +
"complaint_text VARCHAR(1000),\n" +
"FOREIGN KEY(username) REFERENCES UserInfo(username),\n" +
"FOREIGN KEY(bookID) REFERENCES BookInfo(bookID)\n" +
")";
       
       
       String create_readinghistory="CREATE TABLE  ReadingHistory(\n" +
"rhID INT NOT NULL PRIMARY KEY\n" +
"GENERATED ALWAYS AS IDENTITY\n" +
"(START WITH 1, INCREMENT BY 1),\n" +
"username VARCHAR(20) ,\n" +
"bookID SMALLINT,\n" +
"bookName VARCHAR(100),\n" +
"category VARCHAR(20),\n" +
"reading_duration INT,\n" +
"FOREIGN KEY (username) REFERENCES UserInfo(username)\n" +
")";
       
       String create_invitation="CREATE TABLE Invitation(\n" +
"iID INT NOT NULL PRIMARY KEY\n" +
"GENERATED ALWAYS AS IDENTITY\n" +
"(START WITH 1, INCREMENT BY 1),\n" +
"inviter VARCHAR(20),\n" +
"invitee VARCHAR(20),\n" +
"bookID SMALLINT,\n" +
"sharing_points SMALLINT,\n" +
"status VARCHAR(20),\n" +              
"FOREIGN KEY(bookID) REFERENCES BookInfo(bookID),\n" +
"FOREIGN KEY(inviter) REFERENCES UserInfo(username),\n" +
"FOREIGN KEY(invitee) REFERENCES UserInfo(username)\n" +
")";
       
       
       String create_message="CREATE TABLE Message(\n" +
"mID INT NOT NULL PRIMARY KEY\n" +
"GENERATED ALWAYS AS IDENTITY\n" +
"(START WITH 1, INCREMENT BY 1),\n" +
"sender VARCHAR(20) ,\n" +
"receiver VARCHAR(20) ,\n" +
"message_txt VARCHAR(500) ,\n" +
"FOREIGN KEY(sender) REFERENCES UserInfo(username),\n" +
"FOREIGN KEY(receiver) REFERENCES UserInfo(username)\n" +
")";
       
       String create_sharedbooks="CREATE TABLE sharedbooks(\n" +
"sID INT NOT NULL PRIMARY KEY\n" +
"GENERATED ALWAYS AS IDENTITY\n" +
"(START WITH 1, INCREMENT BY 1),\n" +
"username VARCHAR(20) ,\n" +
"bookid SMALLINT ,\n" +
"shared_time INT ,\n" +
"FOREIGN KEY(username) REFERENCES UserInfo(username),\n" +
"FOREIGN KEY(bookid) REFERENCES bookInfo(bookID)\n" +
")";
       
        String create_blacklist="CREATE TABLE blacklist(\n" +
"blID INT NOT NULL PRIMARY KEY\n" +
"GENERATED ALWAYS AS IDENTITY\n" +
"(START WITH 1, INCREMENT BY 1),\n" +
"email VARCHAR(50) \n" +
")";
       
        conn.setAutoCommit(false);
      //stmt.addBatch():
      //create
      stmt.addBatch(create_userinfo);  
      stmt.addBatch(create_bookinfo);
      stmt.addBatch(create_reviewrating);
      stmt.addBatch(create_readinghistory);
      stmt.addBatch(create_pendingbook);
      stmt.addBatch(create_message);
      stmt.addBatch(create_invitation);
      stmt.addBatch(create_sharedbooks);
      stmt.addBatch(create_complaints);
      stmt.addBatch(create_blacklist);
      
      stmt.executeBatch();
      conn.commit();
      
    
      
      System.out.println("CREATE  TABLE userinfo");
      System.out.println("CREATE  TABLE bookinfo");
      System.out.println("CREATE  TABLE blacklist");
      System.out.println("CREATE  TABLE reviewrating");
      System.out.println("CREATE  TABLE readinghistory");
      System.out.println("CREATE  TABLE pendingbook");
      System.out.println("CREATE  TABLE to message");
      System.out.println("CREATE  TABLE inviatation"); 
      System.out.println("CREATE  TABLE Sharedbooks"); 
      System.out.println("CREATE  TABLE complaint");
       
      System.out.println("Run Sucessfully.\nBatch executed.\nALL TABLES CREATE.");
    
}
}
