/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ebooksharing_susan;

import ebooksharing_injit.*;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.JPanel;

/**
 *
 * @author indrajit
 */
public class BookUploadform extends javax.swing.JFrame {

    String Cover_filepath = "";
    String Book_filepath = "";

    /**
     * Creates new form Registrationform
     */
    private static String username = "";
    private static String status = "";
    public BookUploadform(String status, String username) {
        this.username = username;
        this.status = status;
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        BookSubmitButton = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        BookAuthorTextField = new javax.swing.JTextField();
        UploaderNameTextField = new javax.swing.JTextField();
        BookNameTextField = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        CancelButton = new javax.swing.JButton();
        BookBrowseButton = new javax.swing.JButton();
        BrowseCoverpageButton = new javax.swing.JButton();
        UploaderNameLabel = new javax.swing.JLabel();
        UploadcoverPageLabel = new javax.swing.JLabel();
        UploadBookLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        BookSummaryTextArea = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        pointsTextField = new javax.swing.JTextField();
        coverpagepathprint = new javax.swing.JLabel();
        coverpagepathprintLabel = new javax.swing.JLabel();
        bookpathprintlabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(51, 153, 0));
        jPanel1.setToolTipText("");

        jLabel1.setText("Book Name");

        BookSubmitButton.setText("Submit");
        BookSubmitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BookSubmitButtonActionPerformed(evt);
            }
        });

        jLabel3.setText("Book Author");

        jLabel4.setText("Book Summary");

        jLabel7.setText("Please enter the book details");

        CancelButton.setText("Cancel");
        CancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CancelButtonActionPerformed(evt);
            }
        });

        BookBrowseButton.setText("Browse");
        BookBrowseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BookBrowseButtonActionPerformed(evt);
            }
        });

        BrowseCoverpageButton.setText("Browse");
        BrowseCoverpageButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BrowseCoverpageButtonActionPerformed(evt);
            }
        });

        UploaderNameLabel.setText("User Name");

        UploadcoverPageLabel.setText("Upload CoverPage");

        UploadBookLabel.setText("Upload Book");

        BookSummaryTextArea.setColumns(20);
        BookSummaryTextArea.setRows(5);
        jScrollPane1.setViewportView(BookSummaryTextArea);

        jLabel2.setText("Points deserve");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(301, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(UploadBookLabel)
                        .addComponent(CancelButton))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2)
                            .addComponent(UploadcoverPageLabel)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(UploaderNameLabel)
                                .addGap(3, 3, 3))
                            .addComponent(jLabel1))
                        .addGap(7, 7, 7)))
                .addGap(86, 86, 86)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pointsTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(BookBrowseButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(bookpathprintlabel, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(BookSubmitButton)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(BookAuthorTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(BrowseCoverpageButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(coverpagepathprintLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 436, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(BookNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(UploaderNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(125, 125, 125))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(coverpagepathprint)
                        .addGap(316, 316, 316))))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(423, 423, 423)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(UploaderNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(UploaderNameLabel))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BookNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BookAuthorTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(pointsTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BrowseCoverpageButton)
                    .addComponent(UploadcoverPageLabel)
                    .addComponent(coverpagepathprint)
                    .addComponent(coverpagepathprintLabel))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(UploadBookLabel)
                    .addComponent(BookBrowseButton)
                    .addComponent(bookpathprintlabel))
                .addGap(19, 19, 19)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CancelButton)
                    .addComponent(BookSubmitButton))
                .addGap(103, 103, 103))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(40, 40, 40))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel1.getAccessibleContext().setAccessibleName("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //String result = new String(); 
    private void BookSubmitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BookSubmitButtonActionPerformed
        // TODO add your handling code here:
        String B_name = BookNameTextField.getText();
        String A_name = BookAuthorTextField.getText();
        String B_summary = BookSummaryTextArea.getText();
        String Uploadtext = UploaderNameTextField.getText();
        String uploader_name = UploaderNameTextField.getText();
        String requestedPoints = pointsTextField.getText();
        int point = Integer.parseInt(requestedPoints);
        try {
            DbConnector dbc = new DbConnector();
            Connection conn = dbc.Connects();

            String sql = "INSERT INTO PendingBook (uploader, bookname, cover, author, summary, bookfile,  request_points, granted_points) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, " + 0 + ")";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, uploader_name);
            stmt.setString(2, B_name);
            File Cover_image = new File(Cover_filepath);
            FileInputStream Cover_page = new FileInputStream(Cover_image);
            stmt.setBinaryStream(3, Cover_page, (int) Cover_image.length());
            stmt.setString(4, A_name);
            stmt.setString(5, B_summary);
            File Book_image = new File(Book_filepath);
            FileInputStream Book_fis = new FileInputStream(Book_image);
            stmt.setBinaryStream(6, Book_fis, (int) Book_image.length());
            stmt.setInt(7, point);
            //stmt.setInt(8, B_summary);
            if (!B_name.isEmpty() && !A_name.isEmpty() && !B_summary.isEmpty() && !Uploadtext.isEmpty() && !uploader_name.isEmpty() && !Cover_filepath.equals("") && !Book_filepath.equals("") && !requestedPoints.isEmpty()) {

                Statement User_Stmt = conn.createStatement();
                String User_query = "Select bookname, author, uploader from PendingBook";
                ResultSet User_result = User_Stmt.executeQuery(User_query);
                boolean checkmatch = false;
                boolean usernotfound = false;
                boolean copyrightcheck = false;

                boolean UT = false;
//                if (!UserNameText.isEmpty() && !PassWordText.isEmpty()) {
                while (User_result.next()) {
                    String BookN = User_result.getString("bookname");
                    String AuthorN = User_result.getString("author");
                    String uploaderN = User_result.getString("uploader");//should find uploader username from UserInfo table for now its from Bookpending

                    if (BookN.equalsIgnoreCase(B_name) && AuthorN.equalsIgnoreCase(A_name) && uploaderN.equalsIgnoreCase(uploader_name)) {
                        checkmatch = true;
                    }
                    if (BookN.equalsIgnoreCase(B_name) && AuthorN.equalsIgnoreCase(A_name) && !uploader_name.equals(uploaderN)) {
                        copyrightcheck = true;

                        //cancel();
                    }
//                    if (!uploader_name.equals(uploaderN)) {
//                        usernotfound = true;
//
//                    }
                }
//                if (usernotfound) {
//                    JOptionPane.showMessageDialog(null, "UserName not registered");
//                    //cancel();
//                } else {
                if (checkmatch) {
                    JOptionPane.showMessageDialog(null, "Repeated submission book is prohibited");
                    //cancel();

                }
                else if (copyrightcheck) {
                    final JPanel panel = new JPanel();
                    showMessageDialog(panel, "Copyright Issue", "Warning",
                            JOptionPane.WARNING_MESSAGE);
                } else {
                    //}
                    stmt.execute();

                    conn.commit();
                    conn.close();
                    JOptionPane.showMessageDialog(null, "Book submission succcessful.", "Congratulations",  JOptionPane.OK_OPTION);
                    cancel();
                    ApplicationHome AH = new ApplicationHome();
                    AH.setVisible(true);
                }
                //}
            } else {
                JOptionPane.showMessageDialog(null, "All field needs to be filled out.");
            }
            Cover_page.close();
            Book_fis.close();
        } catch (SQLException | HeadlessException | IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());

        }

    }//GEN-LAST:event_BookSubmitButtonActionPerformed


    private void CancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CancelButtonActionPerformed
        // TODO add your handling code here:
        cancel();
        RegUserPage AH = new RegUserPage(status, username);
        AH.setVisible(true);
    }//GEN-LAST:event_CancelButtonActionPerformed


    private void BrowseCoverpageButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BrowseCoverpageButtonActionPerformed
        // TODO add your handling code here:

        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(null);
        File file = chooser.getSelectedFile();
        Cover_filepath = file.getAbsolutePath();
        coverpagepathprintLabel.setText(Cover_filepath);

    }//GEN-LAST:event_BrowseCoverpageButtonActionPerformed

    private void BookBrowseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BookBrowseButtonActionPerformed
        // TODO add your handling code here:
        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(null);
        File file = chooser.getSelectedFile();
        Book_filepath = file.getAbsolutePath();
        bookpathprintlabel.setText(Book_filepath);
    }//GEN-LAST:event_BookBrowseButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(BookUploadform.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BookUploadform.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BookUploadform.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BookUploadform.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BookUploadform(status, username).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField BookAuthorTextField;
    private javax.swing.JButton BookBrowseButton;
    private javax.swing.JTextField BookNameTextField;
    private javax.swing.JButton BookSubmitButton;
    private javax.swing.JTextArea BookSummaryTextArea;
    private javax.swing.JButton BrowseCoverpageButton;
    private javax.swing.JButton CancelButton;
    private javax.swing.JLabel UploadBookLabel;
    private javax.swing.JLabel UploadcoverPageLabel;
    private javax.swing.JLabel UploaderNameLabel;
    private javax.swing.JTextField UploaderNameTextField;
    private javax.swing.JLabel bookpathprintlabel;
    private javax.swing.JLabel coverpagepathprint;
    private javax.swing.JLabel coverpagepathprintLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField pointsTextField;
    // End of variables declaration//GEN-END:variables

    public void cancel() {
        WindowEvent winClosing = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(winClosing);
    }
}
