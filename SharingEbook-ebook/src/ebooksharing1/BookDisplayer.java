/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ebooksharing1;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

/**
 *
 * @author indrajit
 */
public class BookDisplayer {

    public void DisplaysBook() {
        DbConnector dbc = new DbConnector();
        Connection conn = dbc.Connects();
        try {

            PreparedStatement pt = conn.prepareStatement("SELECT BookCover FROM Books");// WHERE Books.=?");
            ResultSet rs = null;
            if (pt.execute()) {
                rs = pt.getResultSet();
                JOptionPane.showMessageDialog(null, "succes", "executed query", JOptionPane.PLAIN_MESSAGE);
            } else {
                System.err.println("select failed");
            }

            while (rs.next()) {
                Blob b_c = rs.getBlob("BookCover");
                
                //System.out.println(bc);
//                item_price = rs.getInt(2);
//                jTextField1.setText("" + item_price);//displaying product price in a jTextField1
//                jTextField2.setText("" + item_id);//displaying product id in a jTextField2    

            }

        } catch (Exception e) {

        }

    }
}
