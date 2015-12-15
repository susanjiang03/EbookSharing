/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ebooksharing1;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author indrajit
 */
public class DbConnector {

    Connection conn;

    public Connection Connects() {
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            conn = DriverManager.getConnection("jdbc:derby://localhost:1527/UsersRegistration", "java", "java");
        } catch (Exception e) {
            System.out.println("Error");
        }
        return conn;
    }

}
