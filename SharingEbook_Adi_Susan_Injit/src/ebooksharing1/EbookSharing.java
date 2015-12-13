/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ebooksharing1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author indrajit
 */
public class EbookSharing {
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
      //  WritetoDB wdb = new WritetoDB();
     //  wdb.Write();
//        DbConnector dbc = new DbConnector();
//            Connection conn = dbc.Connects();
//            String sql = "Update BookInfo SET reading_points = ?";
//            
//            PreparedStatement statement;
//        try {
//            statement = conn.prepareStatement(sql);
//            statement.setInt(1, 1);
//            statement.executeUpdate();
//        } catch (SQLException ex) {
//            Logger.getLogger(EbookSharing.class.getName()).log(Level.SEVERE, null, ex);
//        }
            
        tabpannedAH tbah = new tabpannedAH();
         tbah.setVisible(true);
    }
    
}
