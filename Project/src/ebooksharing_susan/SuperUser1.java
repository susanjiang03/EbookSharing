package ebooksharing_susan;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author lingshanjiang
 */

import java.sql.*;
import java.awt.event.*;
import javax.swing.*;
import net.proteanit.sql.DbUtils;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;






public class SuperUser1  extends javax.swing.JFrame {
    
    Connection conn=null;
    ResultSet rs=null;
    PreparedStatement pst=null;
    
    private static String su_username;
    /**
     * Creates new form notification
     * @param SUusername
     */
    public SuperUser1(String SUusername) {
        SuperUser.su_username = SUusername;
        
        
        initComponents();
        
        //need to fix, can't show label
         JLabel header= new JLabel();
          header.setText("Welcome,Superuser  "+su_username);
          header.setLocation(0,0);
          add(header);
          
          
        conn=DbConnector.Connects();
        pendingbooks();
        complaintboard();
        //RegUserPage();
        
//        others();  
    }
    
    public SuperUser1() {
         
        initComponents();
        conn=DbConnector.Connects();
        pendingbooks();
        complaintboard();
//        others();  
    }
    
    
    
    
   
  
    //display table for pendingbooks
    private void pendingbooks(){
        
         try{
        String sql="select pbID,bookname,author,summary,uploader,request_points from pendingbook";
        pst=conn.prepareStatement(sql);
        rs=pst.executeQuery();
        pdbooks.setModel(DbUtils.resultSetToTableModel(rs));
        
    }catch(Exception e){
        JOptionPane.showMessageDialog(null, e); 
    }
    }
    
          //view the detail of each row in pending book table to the left pane
    
      private void pdbooks_row_detail(){
          
          txt_msg.setText("");
          txt_gra.setText("'");
          txt_read_pts.setText("");
          
          try{
            int row=pdbooks.getSelectedRow();
            int Table_click = (int) pdbooks.getModel().getValueAt(row,0);
            String sql="select pbID,bookname,author,summary,uploader,request_points from pendingbook where  pbID= ?";
            pst=conn.prepareStatement(sql);
            pst.setInt(1,Table_click) ;
            rs=pst.executeQuery();

            while(rs.next()){

                txt_id.setText(rs.getString("pbID"));

                txt_uploader.setText(rs.getString("uploader"));

                txt_bkname.setText(rs.getString("bookname"));

                txt_auth.setText(rs.getString("author"));

                txt_summ.setText(rs.getString("summary"));

                txt_req.setText(rs.getString("request_points"));

            }

        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
          
          
      }
      
    //display table for complaints
      private void complaints(){
          
        String sql="SELECT Complaint.username,BookInfo.BookName,Complaint.complaint_text \n" +
"FROM Complaint,BookInfo WHERE Complaint.BookID=Bookinfo.BookID";
         try{
        pst=conn.prepareStatement(sql);
        rs=pst.executeQuery();
        complaints.setModel(DbUtils.resultSetToTableModel(rs));
        
    }catch(Exception e){
        JOptionPane.showMessageDialog(null, e); 
    }
        
    }
      
      // view the detail of each row in complaint table to the left
      private void complaints_row_detail(){
          
            try{
            int row=complaints.getSelectedRow();
            int Table_click = (int) complaints.getModel().getValueAt(row,0);
            String sql="SELECT Complaint.cID,Complaint.username,BookInfo.bookID,BookInfo.BookName,BookInfo.author,Complaint.complaint_text \n" +
",BookInfo.uploader FROM Complaint INNER JOIN bookInfo on COMPLAINT.bookid=bookinfo.BOOKID WHERE cID= ?";
            
            
            pst=conn.prepareStatement(sql);
            pst.setInt(1,Table_click) ;
            rs=pst.executeQuery();

            while(rs.next()){

                com_usr.setText(rs.getString("username"));

                com_bID.setText(rs.getString("BookID"));

                com_bname.setText(rs.getString("bookname"));

                com_auth.setText(rs.getString("author"));

                com_issue.setText(rs.getString("complaint_text"));
                
                String uploader=rs.getString("uploader");
                 com_uploader.setText(uploader);
                
                String sql1="SELECT got_complainted FROM userInfo WHERE username=?";
                try{
                
                pst=conn.prepareStatement(sql);
                pst.setString(1,uploader);
                 rs=pst.executeQuery();
                 
                }catch(Exception e){
                    JOptionPane.showMessageDialog(null,e);
                }
                
                com_time.setText(rs.getString("got_complainted"));

            }

        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
      }
      
      

      private void review_rating(){
          
          try{
        String sql="SELECT bookID,username,review_text,rating From review_rating";
        pst=conn.prepareStatement(sql);
        rs=pst.executeQuery();
        review_rating.setModel(DbUtils.resultSetToTableModel(rs));
        
    }catch(Exception e){
        JOptionPane.showMessageDialog(null, e); 
    }
          
      } 
      
      
      

      
      
      
      
      public void intValidation(java.awt.event.KeyEvent evt)
      {
           char c=evt.getKeyChar();
          if(!(Character.isDigit(c))||(c==KeyEvent.VK_BACK_SPACE)||c==KeyEvent.VK_DELETE){
             evt.consume();
             getToolkit().beep();
          }
           
      }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        SuperUser = new javax.swing.JTabbedPane();
        NotificationCenter = new javax.swing.JTabbedPane();
        pendingbooksPane = new javax.swing.JPanel();
        complaintboardPane = new javax.swing.JPanel();
        unpopularbooksPane = new javax.swing.JPanel();
        passwordrequestPane = new javax.swing.JPanel();
        Report = new javax.swing.JPanel();
        RegisterUser = new javax.swing.JTabbedPane();
        Browse = new javax.swing.JPanel();
        uploading = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        InviteUsers = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        Logout = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout pendingbooksPaneLayout = new javax.swing.GroupLayout(pendingbooksPane);
        pendingbooksPane.setLayout(pendingbooksPaneLayout);
        pendingbooksPaneLayout.setHorizontalGroup(
            pendingbooksPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1347, Short.MAX_VALUE)
        );
        pendingbooksPaneLayout.setVerticalGroup(
            pendingbooksPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 729, Short.MAX_VALUE)
        );

        NotificationCenter.addTab("Pending  Books", pendingbooksPane);

        javax.swing.GroupLayout complaintboardPaneLayout = new javax.swing.GroupLayout(complaintboardPane);
        complaintboardPane.setLayout(complaintboardPaneLayout);
        complaintboardPaneLayout.setHorizontalGroup(
            complaintboardPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1347, Short.MAX_VALUE)
        );
        complaintboardPaneLayout.setVerticalGroup(
            complaintboardPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 729, Short.MAX_VALUE)
        );

        NotificationCenter.addTab("Complaint Board", complaintboardPane);

        javax.swing.GroupLayout unpopularbooksPaneLayout = new javax.swing.GroupLayout(unpopularbooksPane);
        unpopularbooksPane.setLayout(unpopularbooksPaneLayout);
        unpopularbooksPaneLayout.setHorizontalGroup(
            unpopularbooksPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1347, Short.MAX_VALUE)
        );
        unpopularbooksPaneLayout.setVerticalGroup(
            unpopularbooksPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 729, Short.MAX_VALUE)
        );

        NotificationCenter.addTab("Unpopular Books", unpopularbooksPane);

        javax.swing.GroupLayout passwordrequestPaneLayout = new javax.swing.GroupLayout(passwordrequestPane);
        passwordrequestPane.setLayout(passwordrequestPaneLayout);
        passwordrequestPaneLayout.setHorizontalGroup(
            passwordrequestPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1347, Short.MAX_VALUE)
        );
        passwordrequestPaneLayout.setVerticalGroup(
            passwordrequestPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 729, Short.MAX_VALUE)
        );

        NotificationCenter.addTab("Password Rest", passwordrequestPane);

        javax.swing.GroupLayout ReportLayout = new javax.swing.GroupLayout(Report);
        Report.setLayout(ReportLayout);
        ReportLayout.setHorizontalGroup(
            ReportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1347, Short.MAX_VALUE)
        );
        ReportLayout.setVerticalGroup(
            ReportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 729, Short.MAX_VALUE)
        );

        NotificationCenter.addTab("Summary Report", Report);

        SuperUser.addTab("Notification Center", NotificationCenter);

        javax.swing.GroupLayout BrowseLayout = new javax.swing.GroupLayout(Browse);
        Browse.setLayout(BrowseLayout);
        BrowseLayout.setHorizontalGroup(
            BrowseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1347, Short.MAX_VALUE)
        );
        BrowseLayout.setVerticalGroup(
            BrowseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 729, Short.MAX_VALUE)
        );

        RegisterUser.addTab("Browse E-Books", Browse);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 625, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 690, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout uploadingLayout = new javax.swing.GroupLayout(uploading);
        uploading.setLayout(uploadingLayout);
        uploadingLayout.setHorizontalGroup(
            uploadingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(uploadingLayout.createSequentialGroup()
                .addGap(397, 397, 397)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(325, Short.MAX_VALUE))
        );
        uploadingLayout.setVerticalGroup(
            uploadingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(uploadingLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        RegisterUser.addTab("Upload Books", uploading);

        javax.swing.GroupLayout InviteUsersLayout = new javax.swing.GroupLayout(InviteUsers);
        InviteUsers.setLayout(InviteUsersLayout);
        InviteUsersLayout.setHorizontalGroup(
            InviteUsersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1347, Short.MAX_VALUE)
        );
        InviteUsersLayout.setVerticalGroup(
            InviteUsersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 729, Short.MAX_VALUE)
        );

        RegisterUser.addTab("Invite Users", InviteUsers);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1347, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 729, Short.MAX_VALUE)
        );

        RegisterUser.addTab("Messenger", jPanel3);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1347, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 729, Short.MAX_VALUE)
        );

        RegisterUser.addTab("My Profile", jPanel4);

        SuperUser.addTab("Regular Use", RegisterUser);

        Logout.setText("Log Out");
        Logout.setFocusable(false);
        Logout.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Logout.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        Logout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LogoutActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(SuperUser, javax.swing.GroupLayout.PREFERRED_SIZE, 1389, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(315, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(Logout, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(169, 169, 169))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Logout)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addComponent(SuperUser, javax.swing.GroupLayout.PREFERRED_SIZE, 821, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

        
    private void LogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LogoutActionPerformed
        // TODO add your handling code here:
        cancel();
        HomeFrame home = new HomeFrame();
        home.setVisible(true);
    }//GEN-LAST:event_LogoutActionPerformed

    
    
    /**
 *
 * @author indrajit
 */
    
      private static String Cover_filepath ;
      private static String Book_filepath  ;
    
       
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
            java.util.logging.Logger.getLogger(SuperUser1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SuperUser1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SuperUser1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SuperUser1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new SuperUser1(su_username).setVisible(true);
             
             }
        });
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Browse;
    private javax.swing.JPanel InviteUsers;
    private javax.swing.JButton Logout;
    private javax.swing.JTabbedPane NotificationCenter;
    private javax.swing.JTabbedPane RegisterUser;
    private javax.swing.JPanel Report;
    private javax.swing.JTabbedPane SuperUser;
    private javax.swing.JPanel complaintboardPane;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel passwordrequestPane;
    private javax.swing.JPanel pendingbooksPane;
    private javax.swing.JPanel unpopularbooksPane;
    private javax.swing.JPanel uploading;
    // End of variables declaration//GEN-END:variables

 
  public void cancel(){
        WindowEvent winClosing = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(winClosing);
}
  
}