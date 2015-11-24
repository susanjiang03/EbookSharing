/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ebooksharing1;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 *
 * @author indrajit
 */
public class WritetoDB {



    String bookPath = "/Users/indrajit/NetBeansProjects/EbookLogin_1/books&images/book.txt";
    String bookCover = "/Users/indrajit/NetBeansProjects/EbookLogin_1/books&images/Java.jpg";

    public void Write() {

        try {
//            Class.forName("org.apache.derby.jdbc.ClientDriver");
//            Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/UsersRegistration", "java", "java");
            DbConnector dbc = new DbConnector();
            Connection conn = dbc.Connects();
            Date x = new Date();
            java.sql.Date sqlDate = new java.sql.Date(x.getTime());

            String sql = "INSERT INTO BookInfo (bookname, cover, author, summary, bookfile, uploader, award_points, reading_point, read_counts, last_date_read, rating, rating_counts) values (?, ?, ?, ?, ?, ?,?,?,?,?,?,?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            //statement.setString(1, "3886");
            //statement.setInt(1, 1);
            statement.setString(1, "SoftwareEngineering");
            InputStream inputBookCover = new FileInputStream(new File(bookCover));
            statement.setBlob(2, inputBookCover);
            statement.setString(3, "Engineer");
            statement.setString(4, "This book will earn you lot of money.");
            InputStream inputBookfile = new FileInputStream(new File(bookPath));
            statement.setBlob(5, inputBookfile);
            statement.setString(6, "guruni");
            statement.setInt(7, 20);
            statement.setInt(8, 30);
            statement.setInt(9, 66);
            statement.setDate(10, sqlDate);
            statement.setInt(11, 8);
            statement.setInt(12, 60);

            int row = statement.executeUpdate();
            if (row > 0) {
                System.out.println("Data Inserted.");
            }
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {

        }

    }
}
