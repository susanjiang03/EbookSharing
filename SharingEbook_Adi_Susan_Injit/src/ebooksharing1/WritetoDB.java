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
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author indrajit
 */
public class WritetoDB {



    String bookPath = "/Users/aditimajithia/desktop/RC.txt";
    String bookCover = "/Users/aditimajithia/desktop/IPHONE.jpg";

    public void Write() {

        try {
//            Class.forName("org.apache.derby.jdbc.ClientDriver");
//            Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/UsersRegistration", "java", "java");
            DbConnector dbc = new DbConnector();
            Connection conn = dbc.Connects();
            Date x = new Date();
            java.sql.Date sqlDate = new java.sql.Date(x.getTime());
            /*
            INSERT INTO BOOKINFO(uploader,bookname,author,summary,last_date_read,reading_total_duration,reading_counts,rating,rating_counts,
            reward_points,reading_points) VALUES('user1','magnis dis parturient montes, nascetur ridiculus mus. Proin vel arcu','Sebastian 
            Oneil','ultrices. Duis volutpat nunc sit amet metus. Aliquam erat volutpat. Nulla facilisis. Suspendisse commodo tincidunt nibh. 
            Phasellus nulla. Integer vulputate, risus a ultricies adipiscing, enim mi tempor lorem, eget mollis lectus pede et risus. Quisque 
            libero lacus, varius et, euismod et, commodo at, libero. Morbi accumsan laoreet ipsum. Curabitur consequat, lectus sit amet luctus'
            '07/13/2015',5592,97,9,294,838,88);
            */

            String sql = "INSERT INTO BookInfo (uploader,bookname,Cover, author,summary,BookFile,last_date_read,reading_total_duration,reading_counts,rating,rating_counts,\n" +
"            reward_points,reading_points, category) values (?, ?, ?, ?, ?, ?,?,?,?,?,?,?,?,?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            //statement.setString(1, "3886");
            //statement.setInt(1, 1);
            statement.setString(1, "user1");
            statement.setString(2, "sprint");
            InputStream inputBookCover = new FileInputStream(new File(bookCover));
            statement.setBlob(3, inputBookCover);
            statement.setString(4, "marcelo");
            statement.setString(5, "BEST wireless company");
            InputStream inputBookfile = new FileInputStream(new File(bookPath));
            statement.setBlob(6, inputBookfile);
            //statement.setString(7, "07/13/2015");
            statement.setDate(7, sqlDate);
            BigDecimal bd = new BigDecimal(2000);
            statement.setBigDecimal(8, bd);
            statement.setInt(9, 30);
            statement.setFloat(10, 66);  
            statement.setInt(11, 8);
            statement.setInt(12, 60);
            statement.setInt(13, 1);
            statement.setString(14, "Novel");

            //statement.
            
            
            String sql2 = "update bookinfo set reading_total_duration = " + 0;
            PreparedStatement statement2 = conn.prepareStatement(sql2);
            statement2.executeUpdate();
            
            
            


//            int row = statement.executeUpdate();
//            if (row > 0) {
//                System.out.println("Data Inserted.");
//            }
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {

        }

    }
}
