package ebooksharing1;

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
import java.awt.Toolkit;
import java.awt.event.WindowEvent;






public class SuperUserFrame  extends javax.swing.JFrame {
    
    DbConnector dbc = new DbConnector();
    Connection conn = dbc.Connects();
    ResultSet rs=null;
    PreparedStatement pst=null;
    private static String username;
    private static String firstname;
    /**
     * Creates new form notification
     * @param username
     * @param firstname
     */
    public SuperUserFrame(String username,String firstname) {
        SuperUserFrame.username = username;
        SuperUserFrame.firstname=firstname;  
        initComponents();
        welcome.setText("Welcome,Superuser  "+username+" :"+firstname+".");
       
        pendingbooks();
        complaints();
        //RegUserPage();
        
//        others();  
    }
    
    public SuperUserFrame() {  
        initComponents();
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
          txt_gra.setText("");
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

        Logout = new javax.swing.JButton();
        SuperUser = new javax.swing.JTabbedPane();
        NotificationCenter = new javax.swing.JTabbedPane();
        pendingbooksPane = new javax.swing.JPanel();
        pendingbooklabel = new javax.swing.JLabel();
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
        com_bID = new javax.swing.JTextField();
        l_com_bname = new javax.swing.JLabel();
        l_com_txt = new javax.swing.JLabel();
        l_com_uploader = new javax.swing.JLabel();
        l_com_bID = new javax.swing.JLabel();
        com_uploader = new javax.swing.JTextField();
        com_bname = new javax.swing.JTextField();
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
        jButton9 = new javax.swing.JButton();
        com_time = new javax.swing.JTextField();
        cleanComMsg = new javax.swing.JButton();
        com_time1 = new javax.swing.JTextField();
        l_com_uploader3 = new javax.swing.JLabel();
        l_com_uploader2 = new javax.swing.JLabel();
        com_uploader2 = new javax.swing.JTextField();
        l_com_uploader4 = new javax.swing.JLabel();
        com_uploader5 = new javax.swing.JTextField();
        com_uploader6 = new javax.swing.JTextField();
        l_com_uploader5 = new javax.swing.JLabel();
        com_uploader7 = new javax.swing.JTextField();
        l_com_uploader6 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        unpopularbooksPane = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        passwordrequestPane = new javax.swing.JPanel();
        Report = new javax.swing.JPanel();
        RegisterUser = new javax.swing.JTabbedPane();
        Browse = new javax.swing.JPanel();
        InviteUsers = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        welcome = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(51, 153, 0));
        setBounds(new java.awt.Rectangle(51, 153, 0, 0));

        Logout.setText("Log Out");
        Logout.setFocusable(false);
        Logout.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Logout.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        Logout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LogoutActionPerformed(evt);
            }
        });

        pendingbooksPane.setBackground(new java.awt.Color(51, 153, 0));

        pendingbooklabel.setFont(new java.awt.Font("Lucida Grande", 0, 24)); // NOI18N
        pendingbooklabel.setText("Pending Books");

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
                "pbID", "Uploader", "Book Name", "Summary"
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

        javax.swing.GroupLayout pendingbooksPaneLayout = new javax.swing.GroupLayout(pendingbooksPane);
        pendingbooksPane.setLayout(pendingbooksPaneLayout);
        pendingbooksPaneLayout.setHorizontalGroup(
            pendingbooksPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pendingbooksPaneLayout.createSequentialGroup()
                .addGap(122, 122, 122)
                .addGroup(pendingbooksPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pendingbooksPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(lautho)
                        .addComponent(lbkname)
                        .addGroup(pendingbooksPaneLayout.createSequentialGroup()
                            .addGap(71, 71, 71)
                            .addGroup(pendingbooksPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(txt_bkname, javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pendingbooksPaneLayout.createSequentialGroup()
                                    .addComponent(txt_id, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(luploader)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txt_uploader, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(txt_auth, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pendingbooksPaneLayout.createSequentialGroup()
                            .addComponent(lsumm)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(pendingbooksPaneLayout.createSequentialGroup()
                            .addGap(2, 2, 2)
                            .addGroup(pendingbooksPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(pendingbooklabel, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(l_pbid))))
                    .addGroup(pendingbooksPaneLayout.createSequentialGroup()
                        .addComponent(lmsg)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(pendingbooksPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pendingbooksPaneLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pendingbooksPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pendingbooksPaneLayout.createSequentialGroup()
                                .addGroup(pendingbooksPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(clear, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(Decline, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(Approve, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(Pending, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(viewbook, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(pendingbooksPaneLayout.createSequentialGroup()
                        .addGroup(pendingbooksPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pendingbooksPaneLayout.createSequentialGroup()
                                .addGap(45, 45, 45)
                                .addGroup(pendingbooksPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(pendingbooksPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lgrant)
                                        .addComponent(txt_gra, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(pendingbooksPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lreq)
                                        .addGroup(pendingbooksPaneLayout.createSequentialGroup()
                                            .addGap(6, 6, 6)
                                            .addComponent(txt_req, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(pendingbooksPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(lgrant1, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pendingbooksPaneLayout.createSequentialGroup()
                                            .addGap(3, 3, 3)
                                            .addComponent(txt_read_pts, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addGroup(pendingbooksPaneLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pdbooks_ScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 689, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(369, 369, 369))
        );

        pendingbooksPaneLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {Approve, Decline, Pending, clear});

        pendingbooksPaneLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {txt_gra, txt_read_pts, txt_req});

        pendingbooksPaneLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {txt_id, txt_uploader});

        pendingbooksPaneLayout.setVerticalGroup(
            pendingbooksPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pendingbooksPaneLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(pendingbooklabel, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addGroup(pendingbooksPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pendingbooksPaneLayout.createSequentialGroup()
                        .addGroup(pendingbooksPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pendingbooksPaneLayout.createSequentialGroup()
                                .addGroup(pendingbooksPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pendingbooksPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(pendingbooksPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(l_pbid)
                                            .addComponent(luploader))
                                        .addComponent(txt_id, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(txt_uploader, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(pendingbooksPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lbkname)
                                    .addComponent(txt_bkname, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(6, 6, 6)
                                .addGroup(pendingbooksPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lautho, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_auth, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(40, 40, 40)
                                .addGroup(pendingbooksPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 371, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lsumm)))
                            .addGroup(pendingbooksPaneLayout.createSequentialGroup()
                                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(11, 11, 11)
                                .addComponent(viewbook)
                                .addGap(18, 18, 18)
                                .addComponent(lreq)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_req, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lgrant, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_gra, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lgrant1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_read_pts, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(20, 20, 20)
                        .addGroup(pendingbooksPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lmsg, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(pdbooks_ScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 595, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pendingbooksPaneLayout.createSequentialGroup()
                        .addComponent(Approve)
                        .addGap(9, 9, 9)
                        .addComponent(Pending)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Decline)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(clear)
                        .addGap(14, 14, 14)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pendingbooksPaneLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {Approve, Decline, Pending, clear});

        pendingbooksPaneLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {txt_gra, txt_read_pts, txt_req});

        NotificationCenter.addTab("Pending  Books", pendingbooksPane);

        complaintboardPane.setBackground(new java.awt.Color(51, 153, 0));

        com_bID.setEditable(false);

        l_com_bname.setText("Book Name");

        l_com_txt.setText("Message");

        l_com_uploader.setText("Uploader");

        l_com_bID.setText("Book ID");

        com_uploader.setEditable(false);

        com_bname.setEditable(false);

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

        l_com_uploader1.setText("Times Got  Complainted:");

        l_com_txt1.setText("Issue");

        com_msg.setColumns(20);
        com_msg.setLineWrap(true);
        com_msg.setRows(5);
        jScrollPane7.setViewportView(com_msg);

        jButton9.setText("View Book");

        com_time.setEditable(false);

        cleanComMsg.setText("Clear Message Box");
        cleanComMsg.setPreferredSize(new java.awt.Dimension(160, 30));
        cleanComMsg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cleanComMsgActionPerformed(evt);
            }
        });

        com_time1.setEditable(false);

        l_com_uploader3.setText("Times Got Complainted:");

        l_com_uploader2.setText("Reading Count");

        com_uploader2.setEditable(false);

        l_com_uploader4.setText("Rating Count");

        com_uploader5.setEditable(false);

        com_uploader6.setEditable(false);

        l_com_uploader5.setText("Total Rating");

        com_uploader7.setEditable(false);

        l_com_uploader6.setText("Total Reading Duration");

        jLabel2.setFont(new java.awt.Font("Lucida Grande", 0, 24)); // NOI18N
        jLabel2.setText("Complaint Board");

        javax.swing.GroupLayout complaintboardPaneLayout = new javax.swing.GroupLayout(complaintboardPane);
        complaintboardPane.setLayout(complaintboardPaneLayout);
        complaintboardPaneLayout.setHorizontalGroup(
            complaintboardPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, complaintboardPaneLayout.createSequentialGroup()
                .addContainerGap(106, Short.MAX_VALUE)
                .addGroup(complaintboardPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(complaintboardPaneLayout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addGroup(complaintboardPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(complaintboardPaneLayout.createSequentialGroup()
                                .addGroup(complaintboardPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(complaintboardPaneLayout.createSequentialGroup()
                                        .addComponent(l_com_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(30, 30, 30)
                                        .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 399, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(complaintboardPaneLayout.createSequentialGroup()
                                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(cleanComMsg, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(complaintboardPaneLayout.createSequentialGroup()
                                        .addComponent(l_com_uploader3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(com_time1, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(48, 48, 48)
                                        .addGroup(complaintboardPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(complaintboardPaneLayout.createSequentialGroup()
                                                .addComponent(l_com_uploader, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(com_uploader, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(complaintboardPaneLayout.createSequentialGroup()
                                                .addComponent(l_com_uploader1)
                                                .addGap(18, 18, 18)
                                                .addComponent(com_time, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addGroup(complaintboardPaneLayout.createSequentialGroup()
                                        .addComponent(l_com_txt1, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 399, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(complaintboardPaneLayout.createSequentialGroup()
                                        .addGroup(complaintboardPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(l_com_bID, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(l_com_bname, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(l_com_bname1, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(12, 12, 12)
                                        .addGroup(complaintboardPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(com_bID, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(com_auth, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(com_bname, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(complaintboardPaneLayout.createSequentialGroup()
                                        .addComponent(l_com_uploader2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(com_uploader2, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(l_com_uploader6)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(com_uploader7, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(complaintboardPaneLayout.createSequentialGroup()
                                        .addComponent(l_com_uploader4)
                                        .addGap(21, 21, 21)
                                        .addComponent(com_uploader5, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(l_com_uploader5)
                                        .addGap(21, 21, 21)
                                        .addComponent(com_uploader6, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(27, 27, 27)
                                .addGroup(complaintboardPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(ComplaintPane, javax.swing.GroupLayout.PREFERRED_SIZE, 651, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton9)))
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(complaintboardPaneLayout.createSequentialGroup()
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(66, 66, 66))
        );

        complaintboardPaneLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {cleanComMsg, jButton1, jButton2, jButton3});

        complaintboardPaneLayout.setVerticalGroup(
            complaintboardPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(complaintboardPaneLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(complaintboardPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(complaintboardPaneLayout.createSequentialGroup()
                        .addComponent(ComplaintPane, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton9)
                        .addGap(14, 14, 14))
                    .addGroup(complaintboardPaneLayout.createSequentialGroup()
                        .addGroup(complaintboardPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(com_bID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(l_com_bID, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(l_com_uploader, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(com_uploader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(complaintboardPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(complaintboardPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(l_com_uploader3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(com_time1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(complaintboardPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(com_time, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(l_com_uploader1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(complaintboardPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(com_bname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(l_com_bname, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(complaintboardPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(com_auth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(l_com_bname1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(complaintboardPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(l_com_uploader2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(com_uploader2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(l_com_uploader6, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(com_uploader7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(complaintboardPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(l_com_uploader4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(com_uploader5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(l_com_uploader5, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(com_uploader6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(46, 46, 46)
                        .addGroup(complaintboardPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(l_com_txt1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(complaintboardPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(complaintboardPaneLayout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addComponent(l_com_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(complaintboardPaneLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(complaintboardPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(complaintboardPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cleanComMsg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(66, 66, 66))
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
                .addContainerGap(840, Short.MAX_VALUE))
        );
        unpopularbooksPaneLayout.setVerticalGroup(
            unpopularbooksPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(unpopularbooksPaneLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(269, 269, 269)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        NotificationCenter.addTab("Unpopular Books", unpopularbooksPane);

        javax.swing.GroupLayout passwordrequestPaneLayout = new javax.swing.GroupLayout(passwordrequestPane);
        passwordrequestPane.setLayout(passwordrequestPaneLayout);
        passwordrequestPaneLayout.setHorizontalGroup(
            passwordrequestPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1352, Short.MAX_VALUE)
        );
        passwordrequestPaneLayout.setVerticalGroup(
            passwordrequestPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 643, Short.MAX_VALUE)
        );

        NotificationCenter.addTab("Password Rest", passwordrequestPane);

        javax.swing.GroupLayout ReportLayout = new javax.swing.GroupLayout(Report);
        Report.setLayout(ReportLayout);
        ReportLayout.setHorizontalGroup(
            ReportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1352, Short.MAX_VALUE)
        );
        ReportLayout.setVerticalGroup(
            ReportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 643, Short.MAX_VALUE)
        );

        NotificationCenter.addTab("Summary Report", Report);

        SuperUser.addTab("Notification Center", NotificationCenter);

        javax.swing.GroupLayout BrowseLayout = new javax.swing.GroupLayout(Browse);
        Browse.setLayout(BrowseLayout);
        BrowseLayout.setHorizontalGroup(
            BrowseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1352, Short.MAX_VALUE)
        );
        BrowseLayout.setVerticalGroup(
            BrowseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 643, Short.MAX_VALUE)
        );

        RegisterUser.addTab("Browse E-Books", Browse);

        javax.swing.GroupLayout InviteUsersLayout = new javax.swing.GroupLayout(InviteUsers);
        InviteUsers.setLayout(InviteUsersLayout);
        InviteUsersLayout.setHorizontalGroup(
            InviteUsersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1352, Short.MAX_VALUE)
        );
        InviteUsersLayout.setVerticalGroup(
            InviteUsersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 643, Short.MAX_VALUE)
        );

        RegisterUser.addTab("Invite Users", InviteUsers);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1352, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 643, Short.MAX_VALUE)
        );

        RegisterUser.addTab("Messenger", jPanel3);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1352, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 643, Short.MAX_VALUE)
        );

        RegisterUser.addTab("My Profile", jPanel4);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1352, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 643, Short.MAX_VALUE)
        );

        RegisterUser.addTab("Recommandation", jPanel1);

        SuperUser.addTab("Regular Use", RegisterUser);

        welcome.setFont(new java.awt.Font("Lucida Grande", 1, 24)); // NOI18N
        welcome.setText("Welcome, Super User ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(welcome, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(Logout, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(169, 169, 169))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(SuperUser, javax.swing.GroupLayout.PREFERRED_SIZE, 1394, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(304, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Logout)
                    .addComponent(welcome))
                .addGap(18, 18, 18)
                .addComponent(SuperUser, javax.swing.GroupLayout.DEFAULT_SIZE, 735, Short.MAX_VALUE))
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
            pst.setString(1,username);
            pst.setString(2, username);
            pst.setString(3, msg);
            
            
            pst.execute();
            
            JOptionPane.showMessageDialog(null, "The book has been approved");
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
        
        pendingbooks();
        
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

    private void LogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LogoutActionPerformed
        // TODO add your handling code here:
        cancel();
        tabpannedAH home = new tabpannedAH();
        home.setVisible(true);
    }//GEN-LAST:event_LogoutActionPerformed

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
            java.util.logging.Logger.getLogger(SuperUserFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SuperUserFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SuperUserFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SuperUserFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new SuperUserFrame(username,firstname).setVisible(true);
             
             }
        });
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Approve;
    private javax.swing.JPanel Browse;
    private javax.swing.JScrollPane ComplaintPane;
    private javax.swing.JButton Decline;
    private javax.swing.JPanel InviteUsers;
    private javax.swing.JButton Logout;
    private javax.swing.JTabbedPane NotificationCenter;
    private javax.swing.JButton Pending;
    private javax.swing.JTabbedPane RegisterUser;
    private javax.swing.JPanel Report;
    private javax.swing.JTabbedPane SuperUser;
    private javax.swing.JButton cleanComMsg;
    private javax.swing.JButton clear;
    private javax.swing.JTextField com_auth;
    private javax.swing.JTextField com_bID;
    private javax.swing.JTextField com_bname;
    private javax.swing.JTextArea com_issue;
    private javax.swing.JTextArea com_msg;
    private javax.swing.JTextField com_time;
    private javax.swing.JTextField com_time1;
    private javax.swing.JTextField com_uploader;
    private javax.swing.JTextField com_uploader2;
    private javax.swing.JTextField com_uploader5;
    private javax.swing.JTextField com_uploader6;
    private javax.swing.JTextField com_uploader7;
    private javax.swing.JPanel complaintboardPane;
    private javax.swing.JTable complaints;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JTextPane jTextPane1;
    private javax.swing.JTextPane jTextPane2;
    private javax.swing.JLabel l_com_bID;
    private javax.swing.JLabel l_com_bname;
    private javax.swing.JLabel l_com_bname1;
    private javax.swing.JLabel l_com_txt;
    private javax.swing.JLabel l_com_txt1;
    private javax.swing.JLabel l_com_uploader;
    private javax.swing.JLabel l_com_uploader1;
    private javax.swing.JLabel l_com_uploader2;
    private javax.swing.JLabel l_com_uploader3;
    private javax.swing.JLabel l_com_uploader4;
    private javax.swing.JLabel l_com_uploader5;
    private javax.swing.JLabel l_com_uploader6;
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
    private javax.swing.JLabel pendingbooklabel;
    private javax.swing.JPanel pendingbooksPane;
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
    private javax.swing.JButton viewbook;
    private javax.swing.JLabel welcome;
    // End of variables declaration//GEN-END:variables

 
  public void cancel(){
        WindowEvent winClosing = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(winClosing);
}
  
}