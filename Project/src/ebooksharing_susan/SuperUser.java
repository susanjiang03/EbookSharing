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






public class SuperUser  extends javax.swing.JFrame {
    
    Connection conn=null;
    ResultSet rs=null;
    PreparedStatement pst=null;
    
    private static String su_username;
    /**
     * Creates new form notification
     * @param SUusername
     */
    public SuperUser(String SUusername) {
        SuperUser.su_username = SUusername;
        
        
        initComponents();
        
        //need to fix, can't show label
         JLabel header= new JLabel();
          header.setText("Welcome,Superuser  "+su_username);
          header.setLocation(0,0);
          add(header);
          
          
        conn=DbConnector.Connects();
        pendingbooks();
        complaints();
        //RegUserPage();
        
//        others();  
    }
    
    public SuperUser() {
         
        initComponents();
        conn=DbConnector.Connects();
        pendingbooks();
        complaints();
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

        jTabbedPane2 = new javax.swing.JTabbedPane();
        NotificationCenter = new javax.swing.JTabbedPane();
        pendingbooksPane = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        txt_id = new javax.swing.JTextPane();
        l_pbid = new javax.swing.JLabel();
        lgrant = new javax.swing.JLabel();
        lmsg = new javax.swing.JLabel();
        Pending = new javax.swing.JButton();
        lreq = new javax.swing.JLabel();
        Decline = new javax.swing.JButton();
        lbkname = new javax.swing.JLabel();
        lautho = new javax.swing.JLabel();
        lsumm = new javax.swing.JLabel();
        txt_req = new javax.swing.JTextPane();
        txt_bkname = new javax.swing.JTextPane();
        Approve = new javax.swing.JButton();
        txt_gra = new javax.swing.JEditorPane();
        txt_auth = new javax.swing.JTextPane();
        luploader = new javax.swing.JLabel();
        txt_uploader = new javax.swing.JTextPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        txt_summ = new javax.swing.JTextArea();
        lgrant1 = new javax.swing.JLabel();
        txt_read_pts = new javax.swing.JEditorPane();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        pdbooks_ScrollPane = new javax.swing.JScrollPane();
        pdbooks = new javax.swing.JTable();
        clear = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        txt_msg = new javax.swing.JTextArea();
        viewbook = new javax.swing.JButton();
        complaintboardPane = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        l_com_usr = new javax.swing.JLabel();
        com_bID = new javax.swing.JTextField();
        l_com_bname = new javax.swing.JLabel();
        l_com_txt = new javax.swing.JLabel();
        l_com_uploader = new javax.swing.JLabel();
        l_com_bID = new javax.swing.JLabel();
        com_uploader = new javax.swing.JTextField();
        com_bname = new javax.swing.JTextField();
        com_usr = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        com_issue = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        ComplaintPane = new javax.swing.JScrollPane();
        complaints = new javax.swing.JTable();
        l_com_bname1 = new javax.swing.JLabel();
        com_auth = new javax.swing.JTextField();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTextPane2 = new javax.swing.JTextPane();
        l_com_uploader1 = new javax.swing.JLabel();
        l_com_txt1 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        com_msg = new javax.swing.JTextArea();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        com_time = new javax.swing.JTextField();
        jButton5 = new javax.swing.JButton();
        Total_rating = new javax.swing.JLabel();
        com_uploader1 = new javax.swing.JTextField();
        com_uploader3 = new javax.swing.JTextField();
        jScrollPane8 = new javax.swing.JScrollPane();
        com_txt2 = new javax.swing.JTextArea();
        com_uploader4 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        review_rating = new javax.swing.JTable();
        reviewer = new javax.swing.JLabel();
        rating_counts = new javax.swing.JLabel();
        cleanComMsg = new javax.swing.JButton();
        unpopularbooksPane = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        passwordrequestPane = new javax.swing.JPanel();
        Report = new javax.swing.JPanel();
        Browse = new javax.swing.JPanel();
        uploading = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        BookAuthorTextField = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        BookSubmitButton = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        UploadBookLabel = new javax.swing.JLabel();
        UploadcoverPageLabel = new javax.swing.JLabel();
        BrowseCoverpageButton = new javax.swing.JButton();
        BookBrowseButton = new javax.swing.JButton();
        BookNameTextField = new javax.swing.JTextField();
        pointsTextField = new javax.swing.JTextField();
        jScrollPane9 = new javax.swing.JScrollPane();
        BookSummaryTextArea = new javax.swing.JTextArea();
        coverpagepathprintLabel = new javax.swing.JLabel();
        bookpathprintlabel = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        Logout = new javax.swing.JButton();
        Messenger = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Lucida Grande", 0, 24)); // NOI18N
        jLabel1.setText("Pending Books");

        jPanel1.setDoubleBuffered(false);
        jPanel1.setEnabled(false);
        jPanel1.setFocusTraversalKeysEnabled(false);
        jPanel1.setFocusable(false);

        txt_id.setEditable(false);

        l_pbid.setText("pbID");

        lgrant.setText("Granted Points");

        lmsg.setText("Message");

        Pending.setText("Pending");
        Pending.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PendingActionPerformed(evt);
            }
        });

        lreq.setText("Request Points");

        Decline.setText("Decline");
        Decline.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeclineActionPerformed(evt);
            }
        });

        lbkname.setText("Book Name");

        lautho.setText("Author");

        lsumm.setText("Summary");

        txt_req.setEditable(false);
        txt_req.setDragEnabled(false);

        txt_bkname.setEditable(false);

        Approve.setText("Approve");
        Approve.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ApproveActionPerformed(evt);
            }
        });

        txt_gra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_graKeyTyped(evt);
            }
        });

        txt_auth.setEditable(false);

        luploader.setText("Uploader");

        txt_uploader.setEditable(false);

        txt_summ.setEditable(false);
        txt_summ.setColumns(20);
        txt_summ.setLineWrap(true);
        txt_summ.setRows(5);
        txt_summ.setDragEnabled(false);
        jScrollPane3.setViewportView(txt_summ);

        lgrant1.setText("Reading Points");

        txt_read_pts.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_read_ptsKeyTyped(evt);
            }
        });

        jScrollPane5.setViewportView(jTextPane1);

        pdbooks.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "pbID", "", "Title 3", "Title 4"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        pdbooks.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pdbooksMouseClicked(evt);
            }
        });
        pdbooks.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                pdbooksKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                pdbooksKeyReleased(evt);
            }
        });
        pdbooks_ScrollPane.setViewportView(pdbooks);
        if (pdbooks.getColumnModel().getColumnCount() > 0) {
            pdbooks.getColumnModel().getColumn(0).setPreferredWidth(15);
        }

        clear.setText("Clear Message");
        clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearActionPerformed(evt);
            }
        });

        txt_msg.setColumns(20);
        txt_msg.setLineWrap(true);
        txt_msg.setRows(5);
        txt_msg.setAutoscrolls(false);
        jScrollPane2.setViewportView(txt_msg);

        viewbook.setText("View Book");
        viewbook.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewbookActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lsumm)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(2, 2, 2)
                                        .addComponent(l_pbid))
                                    .addComponent(lautho))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lbkname)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(71, 71, 71)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(txt_bkname, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(txt_id, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(luploader)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txt_uploader, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(txt_auth))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(viewbook, javax.swing.GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE)
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(lmsg)
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane2))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lreq)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addComponent(txt_req, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(23, 23, 23)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(lgrant)
                                        .addGap(18, 18, 18)
                                        .addComponent(lgrant1))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(txt_gra, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(txt_read_pts, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(clear, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Decline, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Approve, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Pending, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addComponent(pdbooks_ScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 473, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {Approve, Decline, Pending, clear});

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {txt_gra, txt_read_pts, txt_req});

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {txt_id, txt_uploader});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(pdbooks_ScrollPane)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(l_pbid)
                                    .addComponent(luploader)
                                    .addComponent(txt_uploader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_id, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lbkname)
                                    .addComponent(txt_bkname, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(6, 6, 6)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lautho, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_auth, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lsumm)
                                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(viewbook)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lgrant1)
                                    .addComponent(lgrant, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lreq))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(txt_read_pts, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txt_req, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(txt_gra, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lmsg, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Approve)
                                .addGap(9, 9, 9)
                                .addComponent(Pending)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Decline)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(clear)
                                .addGap(11, 11, 11)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)))
                .addGap(0, 0, 0))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {Approve, Decline, Pending, clear});

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {txt_gra, txt_read_pts, txt_req});

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {txt_id, txt_uploader});

        javax.swing.GroupLayout pendingbooksPaneLayout = new javax.swing.GroupLayout(pendingbooksPane);
        pendingbooksPane.setLayout(pendingbooksPaneLayout);
        pendingbooksPaneLayout.setHorizontalGroup(
            pendingbooksPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pendingbooksPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(857, Short.MAX_VALUE))
            .addGroup(pendingbooksPaneLayout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        pendingbooksPaneLayout.setVerticalGroup(
            pendingbooksPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pendingbooksPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        NotificationCenter.addTab("Pending  Books", pendingbooksPane);

        jLabel2.setFont(new java.awt.Font("Lucida Grande", 0, 24)); // NOI18N
        jLabel2.setText("Complaint Board");

        l_com_usr.setText("Username");

        com_bID.setEditable(false);

        l_com_bname.setText("Book Name");

        l_com_txt.setText("Message");

        l_com_uploader.setText("Uploader");

        l_com_bID.setText("Book ID");

        com_uploader.setEditable(false);

        com_bname.setEditable(false);

        com_usr.setEditable(false);

        com_issue.setEditable(false);
        com_issue.setColumns(20);
        com_issue.setLineWrap(true);
        com_issue.setRows(5);
        jScrollPane4.setViewportView(com_issue);

        jButton1.setText("Comfirm As a Regular Issue");
        jButton1.setPreferredSize(new java.awt.Dimension(160, 30));

        jButton2.setText("Comfirm As Not A Issue");
        jButton2.setPreferredSize(new java.awt.Dimension(160, 30));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Comfirm As a Serious Issue");
        jButton3.setMinimumSize(new java.awt.Dimension(160, 30));

        complaints.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        complaints.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                complaintsMouseClicked(evt);
            }
        });
        complaints.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                complaintsKeyPressed(evt);
            }
        });
        ComplaintPane.setViewportView(complaints);

        l_com_bname1.setText("Author");

        com_auth.setEditable(false);

        jScrollPane6.setViewportView(jTextPane2);

        l_com_uploader1.setText("Time Being Complainted:");

        l_com_txt1.setText("Issue");

        com_msg.setColumns(20);
        com_msg.setLineWrap(true);
        com_msg.setRows(5);
        jScrollPane7.setViewportView(com_msg);

        jButton7.setText("Send Message to the uploader");
        jButton7.setPreferredSize(new java.awt.Dimension(160, 30));

        jButton8.setText("Reply Issue to the user");
        jButton8.setPreferredSize(new java.awt.Dimension(160, 30));

        jButton9.setText("View Book");

        com_time.setEditable(false);

        jButton5.setText("View Rating and Review of This Book");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        Total_rating.setText("Total Rating");

        com_uploader1.setEditable(false);

        com_uploader3.setEditable(false);

        com_txt2.setEditable(false);
        com_txt2.setColumns(20);
        com_txt2.setLineWrap(true);
        com_txt2.setRows(5);
        jScrollPane8.setViewportView(com_txt2);

        com_uploader4.setEditable(false);

        review_rating.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(review_rating);

        reviewer.setText("Reviewer");

        rating_counts.setText("Rating Counts");

        cleanComMsg.setText("Clear Message Box");
        cleanComMsg.setPreferredSize(new java.awt.Dimension(160, 30));
        cleanComMsg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cleanComMsgActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addComponent(cleanComMsg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(l_com_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)
                                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 399, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(jPanel5Layout.createSequentialGroup()
                                    .addComponent(l_com_txt1, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(jScrollPane4))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel5Layout.createSequentialGroup()
                                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                                    .addComponent(l_com_usr, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addGap(12, 12, 12))
                                                .addComponent(l_com_bID, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(l_com_bname, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(l_com_bname1, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(l_com_uploader, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(com_bID, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(com_usr, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(com_uploader, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(com_auth, javax.swing.GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE)
                                                .addComponent(com_bname)))
                                        .addGroup(jPanel5Layout.createSequentialGroup()
                                            .addComponent(l_com_uploader1)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(com_time, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGap(18, 18, 18)
                                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanel5Layout.createSequentialGroup()
                                            .addGap(6, 6, 6)
                                            .addComponent(jButton9))))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(reviewer)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(com_uploader3, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(Total_rating, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(rating_counts))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(com_uploader4, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(com_uploader1, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ComplaintPane, javax.swing.GroupLayout.PREFERRED_SIZE, 497, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton5))
                        .addGap(5, 5, 5)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(l_com_usr, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(com_usr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(com_bID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(l_com_bID, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(com_bname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(l_com_bname, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(com_auth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(l_com_bname1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(l_com_uploader, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(com_uploader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(l_com_uploader1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(com_time, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton9))
                .addGap(9, 9, 9)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(l_com_txt1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(l_com_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cleanComMsg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(11, Short.MAX_VALUE))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(ComplaintPane, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Total_rating, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(com_uploader1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(com_uploader4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rating_counts, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(com_uploader3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(reviewer, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        javax.swing.GroupLayout complaintboardPaneLayout = new javax.swing.GroupLayout(complaintboardPane);
        complaintboardPane.setLayout(complaintboardPaneLayout);
        complaintboardPaneLayout.setHorizontalGroup(
            complaintboardPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(complaintboardPaneLayout.createSequentialGroup()
                .addGroup(complaintboardPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(complaintboardPaneLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(complaintboardPaneLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        complaintboardPaneLayout.setVerticalGroup(
            complaintboardPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(complaintboardPaneLayout.createSequentialGroup()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 35, Short.MAX_VALUE))
        );

        NotificationCenter.addTab("Complaint Board", complaintboardPane);

        jLabel3.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        jLabel3.setText("Books that have been rated under 5.");

        jLabel4.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        jLabel4.setText("Books that have not been read for more than 1 year.");

        javax.swing.GroupLayout unpopularbooksPaneLayout = new javax.swing.GroupLayout(unpopularbooksPane);
        unpopularbooksPane.setLayout(unpopularbooksPaneLayout);
        unpopularbooksPaneLayout.setHorizontalGroup(
            unpopularbooksPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(unpopularbooksPaneLayout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(unpopularbooksPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 474, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 329, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(549, Short.MAX_VALUE))
        );
        unpopularbooksPaneLayout.setVerticalGroup(
            unpopularbooksPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(unpopularbooksPaneLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(269, 269, 269)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(297, Short.MAX_VALUE))
        );

        NotificationCenter.addTab("Unpopular Books", unpopularbooksPane);

        javax.swing.GroupLayout passwordrequestPaneLayout = new javax.swing.GroupLayout(passwordrequestPane);
        passwordrequestPane.setLayout(passwordrequestPaneLayout);
        passwordrequestPaneLayout.setHorizontalGroup(
            passwordrequestPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1061, Short.MAX_VALUE)
        );
        passwordrequestPaneLayout.setVerticalGroup(
            passwordrequestPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 667, Short.MAX_VALUE)
        );

        NotificationCenter.addTab("Password Rest", passwordrequestPane);

        javax.swing.GroupLayout ReportLayout = new javax.swing.GroupLayout(Report);
        Report.setLayout(ReportLayout);
        ReportLayout.setHorizontalGroup(
            ReportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1061, Short.MAX_VALUE)
        );
        ReportLayout.setVerticalGroup(
            ReportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 667, Short.MAX_VALUE)
        );

        NotificationCenter.addTab("Summary Report", Report);

        jTabbedPane2.addTab("Notification Center", NotificationCenter);

        javax.swing.GroupLayout BrowseLayout = new javax.swing.GroupLayout(Browse);
        Browse.setLayout(BrowseLayout);
        BrowseLayout.setHorizontalGroup(
            BrowseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1082, Short.MAX_VALUE)
        );
        BrowseLayout.setVerticalGroup(
            BrowseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 713, Short.MAX_VALUE)
        );

        jTabbedPane2.addTab("Browse E-Books", Browse);

        jLabel5.setText("Points deserve");

        jLabel6.setText("Book Summary");

        jLabel7.setText("Book Author");

        BookSubmitButton.setText("Submit");
        BookSubmitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BookSubmitButtonActionPerformed(evt);
            }
        });

        jLabel8.setText("Book Name");

        UploadBookLabel.setText("Upload Book");

        UploadcoverPageLabel.setText("Upload CoverPage");

        BrowseCoverpageButton.setText("Browse");
        BrowseCoverpageButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BrowseCoverpageButtonActionPerformed(evt);
            }
        });

        BookBrowseButton.setText("Browse");
        BookBrowseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BookBrowseButtonActionPerformed(evt);
            }
        });

        BookSummaryTextArea.setColumns(20);
        BookSummaryTextArea.setRows(5);
        jScrollPane9.setViewportView(BookSummaryTextArea);

        javax.swing.GroupLayout uploadingLayout = new javax.swing.GroupLayout(uploading);
        uploading.setLayout(uploadingLayout);
        uploadingLayout.setHorizontalGroup(
            uploadingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, uploadingLayout.createSequentialGroup()
                .addContainerGap(353, Short.MAX_VALUE)
                .addGroup(uploadingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(uploadingLayout.createSequentialGroup()
                        .addGroup(uploadingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(UploadcoverPageLabel)
                            .addComponent(UploadBookLabel)
                            .addComponent(jLabel5))
                        .addGap(61, 61, 61)
                        .addGroup(uploadingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(uploadingLayout.createSequentialGroup()
                                .addComponent(BookBrowseButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(bookpathprintlabel, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(BookSubmitButton)
                            .addGroup(uploadingLayout.createSequentialGroup()
                                .addComponent(BrowseCoverpageButton)
                                .addGap(18, 18, 18)
                                .addComponent(coverpagepathprintLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(pointsTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(uploadingLayout.createSequentialGroup()
                        .addGroup(uploadingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8))
                        .addGap(61, 61, 61)
                        .addGroup(uploadingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(uploadingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(BookAuthorTextField)
                                .addComponent(BookNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(169, 169, 169))
        );

        uploadingLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {UploadBookLabel, UploadcoverPageLabel, jLabel5, jLabel6, jLabel7, jLabel8});

        uploadingLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {BookBrowseButton, BookSubmitButton, BrowseCoverpageButton});

        uploadingLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {bookpathprintlabel, coverpagepathprintLabel});

        uploadingLayout.setVerticalGroup(
            uploadingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(uploadingLayout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(uploadingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BookNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addGap(18, 18, 18)
                .addGroup(uploadingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BookAuthorTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(18, 18, 18)
                .addGroup(uploadingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(uploadingLayout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(uploadingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(pointsTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(uploadingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(uploadingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(BrowseCoverpageButton)
                        .addComponent(UploadcoverPageLabel))
                    .addComponent(coverpagepathprintLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(126, 126, 126)
                .addGroup(uploadingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(uploadingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(UploadBookLabel)
                        .addComponent(BookBrowseButton))
                    .addComponent(bookpathprintlabel, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17)
                .addComponent(BookSubmitButton)
                .addGap(46, 46, 46))
        );

        uploadingLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {BookBrowseButton, BookSubmitButton, BrowseCoverpageButton});

        uploadingLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {BookAuthorTextField, BookNameTextField});

        uploadingLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {bookpathprintlabel, coverpagepathprintLabel});

        jTabbedPane2.addTab("Upload Books", uploading);

        jButton4.setText("My Profile");
        jButton4.setFocusable(false);
        jButton4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        Logout.setText("Log Out");
        Logout.setFocusable(false);
        Logout.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Logout.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        Logout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LogoutActionPerformed(evt);
            }
        });

        Messenger.setText("Messenger");
        Messenger.setFocusable(false);
        Messenger.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Messenger.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        Messenger.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MessengerActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Messenger, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Logout, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton4)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(Messenger)
                        .addComponent(Logout)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 53, Short.MAX_VALUE)
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void pdbooksMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pdbooksMouseClicked
        // TODO add your handling code here: 
       pdbooks_row_detail();
    }//GEN-LAST:event_pdbooksMouseClicked

    private void ApproveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ApproveActionPerformed
        // TODO add your handling code here:
     
        //if select no row
       int row=pdbooks.getSelectedRow();
       if(row<0){
           JOptionPane.showMessageDialog(null,"Please select a book.");
           return;
          }        
       
       //if not assign reading point for the book
       
        String read_pts_txt=txt_read_pts.getText();
           if(read_pts_txt.length()==0)  {
            JOptionPane.showMessageDialog(null,"Please assgin the reading points for this book.");
            return;
        }
          
         int read_pts=Integer.parseInt(read_pts_txt);
         
        try{
            
            //====1. insert book to bookinfo
            String sql="INSERT INTO BookInfo (uploader,bookname,author,summary,reward_points,reading_points)"
                    + "VALUES(?,?,?,?,?,?)";
            pst=conn.prepareStatement(sql);
            
            String username=txt_uploader.getText();
            pst.setString(1,username);
            
            String bkname=txt_bkname.getText();
            pst.setString(2,bkname);
            
            pst.setString(3,txt_auth.getText());
            pst.setString(4,txt_summ.getText());
            
            int award_pts=Integer.parseInt(txt_req.getText());
            pst.setInt(5,award_pts);
            
            pst.setInt(6,read_pts);
 
            pst.execute();
            
            
            //2.===delete from pending books`
            String sql2="DELETE FROM pendingbook WHERE pbID= ?";
            pst=conn.prepareStatement(sql2);
            pst.setString(1, txt_id.getText());
            
            pst.execute();
            
            
            //3.====add points to the uploader's account;
            String sql3="UPDATE userInfo SET point_balance=point_balance+ ?  WHERE username=?";
            pst=conn.prepareStatement(sql3);
            pst.setInt(1,award_pts);
            pst.setString(2, username);
            pst.execute();
            
            
            //4.====send message to the uploader;
            String comment=txt_msg.getText();
            String msg="You book   "+bkname+"  has been approved.You are awaded   "+award_pts+"    points."+" The reading points for this"
                    + " book is"+read_pts+".";
            
            if (!comment.trim().equals(""))
                msg+="Comment: "+comment+".";
            
            String sql4="INSERT INTO Message(sender,receiver,message_txt) VALUES(?,?,?)";
           
            pst=conn.prepareStatement(sql4);
            pst.setString(1,su_username);
            pst.setString(2, username);
            pst.setString(3, msg);
            
            
            pst.execute();
            
            JOptionPane.showMessageDialog(null, "The book has been approved");
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_ApproveActionPerformed

        
    private void pdbooksKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_pdbooksKeyPressed
        // TODO add your handling code here:
         if(evt.getKeyCode()==KeyEvent.VK_DOWN||evt.getKeyCode()==KeyEvent.VK_UP)
        
            pdbooks_row_detail();

    }//GEN-LAST:event_pdbooksKeyPressed

    private void PendingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PendingActionPerformed
        // TODO add your handling code here:
        
        //if no row selected
        int row=pdbooks.getSelectedRow();
       if(row<0){
           JOptionPane.showMessageDialog(null,"Please select a book.");
           return;
       }
       
        int pbID=Integer.parseInt(txt_id.getText());
        String username=txt_uploader.getText();
        int req_points=Integer.parseInt(txt_req.getText());
        
        String gra_pts_txt=txt_gra.getText();
        int granted_points=Integer.parseInt(gra_pts_txt);
        
        String comment=txt_msg.getText();
        String bkname=txt_bkname.getText();
        String msg="";
        
        //if no valid granted point or no reason:
  
        if( (granted_points>req_points) || 
                
              ((gra_pts_txt.length()==0)&&(comment.length()==0)))
        {
            JOptionPane.showMessageDialog
                (null, "Please enter granted points lower than requested points"+ txt_req.getText()+
                    ". \n Or leave user "+ username+"  message about the pending decision with other reasons.");
            return;
                   
        }
        
        
       //1. book is pending because of lower award points
        
        //update granted points to pending book table
        if(granted_points>0){
            
            msg+="Your book /"+bkname  +"/  will be approved if you accept  "+granted_points+"  award points.";
        
           try{
           String sql0="UPDATE PendingBook SET granted_points=? WHERE pbID= ?";
           pst=conn.prepareStatement(sql0);
           pst.setInt(1,granted_points);
           pst.setInt(2,pbID);
           pst.execute();
          
        
          }catch(Exception e){
             JOptionPane.showMessageDialog(null,e);
       }
                
        } 
        //2. book is pending for other reason in the message box.
            msg+=comment;
        
        //Insert message to table to info user later.
       try{
        String sql="INSERT INTO Message(receiver,message_txt) VALUES(?,?)";
        
        pst=conn.prepareStatement(sql);
        pst.setString(1, username);
        pst.setString(2, msg);
        //
        pst.execute();
        
         }catch(Exception e){
           JOptionPane.showMessageDialog(null, e);
       }
       
        //dialog message
       JOptionPane.showMessageDialog(null,"This book is pending.");
 
    }//GEN-LAST:event_PendingActionPerformed

    private void txt_graKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_graKeyTyped
        // TODO add your handling code here:
       intValidation(evt);
    }//GEN-LAST:event_txt_graKeyTyped

    private void txt_read_ptsKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_read_ptsKeyTyped
        // TODO add your handling code here:
        intValidation(evt);
    }//GEN-LAST:event_txt_read_ptsKeyTyped

    private void complaintsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_complaintsKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==KeyEvent.VK_UP||evt.getKeyCode()==KeyEvent.VK_DOWN )

        complaints_row_detail();

    }//GEN-LAST:event_complaintsKeyPressed

    private void complaintsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_complaintsMouseClicked
        // TODO add your handling code here:
        complaints_row_detail();
    }//GEN-LAST:event_complaintsMouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

    private void LogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LogoutActionPerformed
        // TODO add your handling code here:
        cancel();
        ApplicationHome home = new ApplicationHome();
        home.setVisible(true);
    }//GEN-LAST:event_LogoutActionPerformed

    private void MessengerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MessengerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_MessengerActionPerformed

    private void pdbooksKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_pdbooksKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_pdbooksKeyReleased

    private void DeclineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeclineActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_DeclineActionPerformed

    private void clearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearActionPerformed
        // TODO add your handling code here:
         txt_msg.setText("");
          txt_gra.setText("'");
          txt_read_pts.setText("");
    }//GEN-LAST:event_clearActionPerformed

    
    
    /**
 *
 * @author indrajit
 */
    
      private static String Cover_filepath ;
      private static String Book_filepath  ;
    //
    private void BookSubmitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BookSubmitButtonActionPerformed
        // TODO add your handling code here:
        String B_name = BookNameTextField.getText();
        String A_name = BookAuthorTextField.getText();
        String B_summary = BookSummaryTextArea.getText();
        //String Uploadtext = UploaderNameTextField.getText();
        //String uploader_name = UploaderNameTextField.getText();
        String requestedPoints = pointsTextField.getText();
        int point = Integer.parseInt(requestedPoints);
        try {
            DbConnector dbc = new DbConnector();
            Connection conn = dbc.Connects();

            String sql = "INSERT INTO PendingBook (uploader, bookname, cover, author, summary, bookfile,  request_points, granted_points) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?, " + 0 + ")";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, su_username);
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
            if (!B_name.isEmpty() && !A_name.isEmpty() && !B_summary.isEmpty()  && !Cover_filepath.equals("") && !Book_filepath.equals("") && !requestedPoints.isEmpty()) {

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

    private void cleanComMsgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cleanComMsgActionPerformed
        // TODO add your handling code here:
        com_msg.setText("");
    }//GEN-LAST:event_cleanComMsgActionPerformed

    private void viewbookActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewbookActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_viewbookActionPerformed

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
            java.util.logging.Logger.getLogger(SuperUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SuperUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SuperUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SuperUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
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
                new SuperUser(su_username).setVisible(true);
             
             }
        });
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Approve;
    private javax.swing.JTextField BookAuthorTextField;
    private javax.swing.JButton BookBrowseButton;
    private javax.swing.JTextField BookNameTextField;
    private javax.swing.JButton BookSubmitButton;
    private javax.swing.JTextArea BookSummaryTextArea;
    private javax.swing.JPanel Browse;
    private javax.swing.JButton BrowseCoverpageButton;
    private javax.swing.JScrollPane ComplaintPane;
    private javax.swing.JButton Decline;
    private javax.swing.JButton Logout;
    private javax.swing.JButton Messenger;
    private javax.swing.JTabbedPane NotificationCenter;
    private javax.swing.JButton Pending;
    private javax.swing.JPanel Report;
    private javax.swing.JLabel Total_rating;
    private javax.swing.JLabel UploadBookLabel;
    private javax.swing.JLabel UploadcoverPageLabel;
    private javax.swing.JLabel bookpathprintlabel;
    private javax.swing.JButton cleanComMsg;
    private javax.swing.JButton clear;
    private javax.swing.JTextField com_auth;
    private javax.swing.JTextField com_bID;
    private javax.swing.JTextField com_bname;
    private javax.swing.JTextArea com_issue;
    private javax.swing.JTextArea com_msg;
    private javax.swing.JTextField com_time;
    private javax.swing.JTextArea com_txt2;
    private javax.swing.JTextField com_uploader;
    private javax.swing.JTextField com_uploader1;
    private javax.swing.JTextField com_uploader3;
    private javax.swing.JTextField com_uploader4;
    private javax.swing.JTextField com_usr;
    private javax.swing.JPanel complaintboardPane;
    private javax.swing.JTable complaints;
    private javax.swing.JLabel coverpagepathprintLabel;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTextPane jTextPane1;
    private javax.swing.JTextPane jTextPane2;
    private javax.swing.JLabel l_com_bID;
    private javax.swing.JLabel l_com_bname;
    private javax.swing.JLabel l_com_bname1;
    private javax.swing.JLabel l_com_txt;
    private javax.swing.JLabel l_com_txt1;
    private javax.swing.JLabel l_com_uploader;
    private javax.swing.JLabel l_com_uploader1;
    private javax.swing.JLabel l_com_usr;
    private javax.swing.JLabel l_pbid;
    private javax.swing.JLabel lautho;
    private javax.swing.JLabel lbkname;
    private javax.swing.JLabel lgrant;
    private javax.swing.JLabel lgrant1;
    private javax.swing.JLabel lmsg;
    private javax.swing.JLabel lreq;
    private javax.swing.JLabel lsumm;
    private javax.swing.JLabel luploader;
    private javax.swing.JPanel passwordrequestPane;
    private javax.swing.JTable pdbooks;
    private javax.swing.JScrollPane pdbooks_ScrollPane;
    private javax.swing.JPanel pendingbooksPane;
    private javax.swing.JTextField pointsTextField;
    private javax.swing.JLabel rating_counts;
    private javax.swing.JTable review_rating;
    private javax.swing.JLabel reviewer;
    private javax.swing.JTextPane txt_auth;
    private javax.swing.JTextPane txt_bkname;
    private javax.swing.JEditorPane txt_gra;
    private javax.swing.JTextPane txt_id;
    private javax.swing.JTextArea txt_msg;
    private javax.swing.JEditorPane txt_read_pts;
    private javax.swing.JTextPane txt_req;
    private javax.swing.JTextArea txt_summ;
    private javax.swing.JTextPane txt_uploader;
    private javax.swing.JPanel unpopularbooksPane;
    private javax.swing.JPanel uploading;
    private javax.swing.JButton viewbook;
    // End of variables declaration//GEN-END:variables

 
  public void cancel(){
        WindowEvent winClosing = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(winClosing);
}
  
}