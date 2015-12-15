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
import java.awt.Image;
import java.sql.*;
import java.awt.event.*;
import javax.swing.*;
import net.proteanit.sql.DbUtils;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SuperUserFrame extends javax.swing.JFrame {

    DbConnector dbc = new DbConnector();
    Connection conn = dbc.Connects();
    ResultSet rs = null;
    PreparedStatement pst = null;
    private static String username;
    private static String firstname;
    private static Calendar cal;
    private static SimpleDateFormat dateFormat;

    /**
     * Creates new form notification
     *
     * @param username
     * @param firstname
     */
    public SuperUserFrame(String username, String firstname) {

        //get today's date
        this.dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        this.cal = Calendar.getInstance();
        String date_now = dateFormat.format(cal.getTime());

        this.username = username;
        this.firstname = firstname;
        initComponents();
        welcome.setText("Welcome, " + firstname + ": Superuser  " + username + ".     Today: " + date_now);

        pendingbooks();
        complaints();
        rareReadBook();
        lowRateBook();

    }

    //display table for pendingbooks
    private void pendingbooks() {

        try {
            String sql = "SELECT pbID,bookname,author,summary,category,uploader,request_points FROM pendingbook "
                    + "WHERE granted_points=0 AND reading_points=0 and uploader!=?  ORDER BY pbID";
            pst = conn.prepareStatement(sql);
            pst.setString(1, username);
            rs = pst.executeQuery();
            pdbooks.setModel(DbUtils.resultSetToTableModel(rs));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "pendingbooks() error:\n" + e);
        }

    }

    private void clean_pdbooks_row_detail() {
        //
        txt_id.setText("");
        txt_uploader.setText("");
        txt_bkname.setText("");
        txt_auth.setText("");
        txt_summ.setText("");
        txt_cate.setText("");
        txt_msg.setText("");
        txt_msg.setText("");
        txt_gra.setText("");
        txt_req.setText("");
        txt_read_pts.setText("");
        //pending_cover.setText("");
        pending_cover.setIcon(null);
    }

    //view the detail of each row in pending book table to the left pane
    private void pdbooks_row_detail() {

        clean_pdbooks_row_detail();

        try {

            int row = pdbooks.getSelectedRow();
            int Table_click = (int) pdbooks.getModel().getValueAt(row, 0);
            String sql = "select pbID,bookname,author,summary,category,uploader,request_points,cover from pendingbook where pbID= ?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, Table_click);
            rs = pst.executeQuery();
            int bid = 0;

            while (rs.next()) {
                bid = rs.getInt("pbid");

                txt_id.setText(rs.getString("pbID"));

                txt_uploader.setText(rs.getString("uploader"));

                txt_bkname.setText(rs.getString("bookname"));

                txt_auth.setText(rs.getString("author"));

                txt_summ.setText(rs.getString("summary"));

                txt_cate.setText(rs.getString("category"));

                txt_req.setText(rs.getString("request_points"));

                txt_gra.setText("0");

                txt_read_pts.setText("0");

                pending_cover.setIcon(null);

                byte[] imageBytes;
                Image image;
                imageBytes = rs.getBytes("cover");
                image = getToolkit().createImage(imageBytes);
                ImageIcon icon = new ImageIcon(image);
                pending_cover.setIcon(getImage(bid, "pendingbook"));

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "pdbooks_row_detail() error:\n" + e);
        }

    }

    //display table for complaints
    private void complaints() {

        String sql = "SELECT cID,username,BookID,complaint_text FROM Complaint WHERE bookID NOT IN"
                + "(SELECT bookID FROM bookinfo WHERE uploader=?) ORDER BY cID";
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, username);
            rs = pst.executeQuery();
            complaints.setModel(DbUtils.resultSetToTableModel(rs));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "complaints() error:\n" + e);
        }

    }

    private void clean_com_row_detail() {
        //clean detail on the left 
        com_id.setText("");
        com_user.setText("");
        com_uploader.setText("");
        com_bID.setText("");
        com_bookname.setText("");
        book_com_time.setText("");
        book_removed.setText("");
        reward_pts.setText("");
        com_auth.setText("");
        com_rd_cnt.setText("");
        com_rd_total.setText("");
        com_rt_cnt.setText("");
        com_rt_total.setText("");
        com_issue.setText("");
        com_msg.setText("");
        com_cover.setIcon(null);
    }

    // view the detail of each row in complaint table to the left
    private void complaints_row_detail() {

        //get data from complaint table
        try {
            int row = complaints.getSelectedRow();
            int Table_click = (int) complaints.getModel().getValueAt(row, 0);

            String sql1 = "SELECT cID,username,bookinfo.bookID AS bookID,bookinfo.cover AS cover,complaint_text FROM bookInfo INNER JOIN Complaint "
                    + "ON bookinfo.bookID=complaint.bookID WHERE complaint.cID= ?";

            pst = conn.prepareStatement(sql1);
            pst.setInt(1, Table_click);

            rs = pst.executeQuery();

            while (rs.next()) {
                com_id.setText(rs.getString("cID"));
                int bookID = Integer.parseInt(rs.getString("bookID"));
                com_bID.setText(rs.getString("bookID"));
                com_issue.setText(rs.getString("complaint_text"));
                com_user.setText(rs.getString("username"));

                byte[] imageBytes;
                Image image;
                imageBytes = rs.getBytes("cover");
                image = getToolkit().createImage(imageBytes);
                ImageIcon icon = new ImageIcon(image);
                com_cover.setIcon(getImage(bookID, "complaints"));

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "complaints_row_detail() sql1 error:\n" + e);
        }

        int bookID = Integer.parseInt(com_bID.getText());
        //get related data from bookinfo table
        try {
            String sql2 = "SELECT bookname,author,uploader,reward_points,got_complainted,reading_counts,reading_total_duration,"
                    + "rating,rating_counts FROM bookInfo WHERE bookID= ?";

            pst = conn.prepareStatement(sql2);
            pst.setInt(1, bookID);
            rs = pst.executeQuery();

            while (rs.next()) {
                com_bookname.setText(rs.getString("bookname"));
                com_auth.setText(rs.getString("author"));
                book_com_time.setText(rs.getString("got_complainted"));
                reward_pts.setText(rs.getString("reward_points"));
                com_rd_cnt.setText(rs.getString("reading_counts"));
                com_rd_total.setText(rs.getString("reading_total_duration"));
                com_rt_cnt.setText(rs.getString("rating_counts"));
                com_rt_total.setText(rs.getString("rating"));
                com_uploader.setText(rs.getString("uploader"));
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "complaints_row_detail() sql2 error:\n" + e);
        }

        String uploader = com_uploader.getText();
        try {
            //show how many time the uploader's books have been removed
            String sql3 = "SELECT book_got_removed FROM userInfo WHERE username=? ";

            pst = conn.prepareStatement(sql3);
            pst.setString(1, uploader);
            rs = pst.executeQuery();
            while (rs.next()) {
                book_removed.setText(rs.getString("book_got_removed"));
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "complaints_row_detail() sql3 error:\n" + e);
        }

    }

    public ImageIcon getImage(int bookid, String tablename) {

        Blob image = null;

        byte[] imgData = null;

        String sql = null;
        if (tablename.equals("complaints")) {
            sql = "SELECT bookname, cover FROM bookinfo  where bookid = ?";
        }
        if (tablename.equals("pendingbook")) {
            sql = "SELECT bookname, cover FROM pendingbook  where pbid = ?";
        }

        try {
            pst = conn.prepareStatement(sql);
            pst.setInt(1, bookid);
            rs = pst.executeQuery();
            while (rs.next()) {
                image = rs.getBlob("Cover");
                imgData = image.getBytes(1, (int) image.length());
            }

        } catch (Exception e) {
            //Logger.getLogger(ReviewRateFrame.class.getName()).log(Level.SEVERE, null, ex);
            e.printStackTrace();
        }
        return new ImageIcon(new ImageIcon(imgData).getImage().getScaledInstance(230, 285, java.awt.Image.SCALE_SMOOTH));
    }

    //display the table for books that have not been read for more than 1 year.
    private void rareReadBook() {

        try {
            cal.add(Calendar.YEAR, -1);
            String one_year_ago_date = dateFormat.format(cal.getTime());

            String sql = "SELECT BOOKID,bookname,last_date_read,uploader FROM bookinfo WHERE last_date_read<?"
                    + "AND bookID NOT IN (SELECT bookID FROM bookinfo WHERE uploader=?) ORDER BY bookID";
            pst = conn.prepareStatement(sql);
            pst.setString(1, one_year_ago_date);
            pst.setString(2, username);
            rs = pst.executeQuery();
            rareReadBook.setModel(DbUtils.resultSetToTableModel(rs));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "rareReadBook() error:\n" + e);
        }
    }

//clean the detail of rare read book info when remove.
    private void clean_rareread_row_detail() {
        rareread_bookid.setText("");
        last_date_read.setText("");
        rareread_uploader.setText("");
        rareread_pts.setText("");
        rareread_summary.setText("");
    }

    //show detail of rare read book when select the table.
    private void rareread_row_detail() {

        try {
            int row = rareReadBook.getSelectedRow();
            int Table_click = (int) rareReadBook.getModel().getValueAt(row, 0);
            String sql = "SELECT * FROM bookInfo WHERE bookID=?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, Table_click);
            rs = pst.executeQuery();

            while (rs.next()) {

                rareread_bookid.setText(rs.getString("bookID"));

                rareread_bookname.setText(rs.getString("bookname"));

                last_date_read.setText(rs.getString("last_date_read"));

                rareread_pts.setText(rs.getString("reading_points"));

                rareread_cnts.setText(rs.getString("reading_counts"));

                rareread_duration.setText(rs.getString("reading_total_duration"));

                rareread_uploader.setText(rs.getString("uploader"));

                rareread_summary.setText(rs.getString("summary"));

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "rareread_row_detail() error:\n" + e);
        }

    }

//display a table of books that have been rated lower than 5.
    private void lowRateBook() {

        try {
            String sql = "SELECT BOOKID,bookname,rating,rating_counts,reward_points,uploader FROM bookInfo WHERE rating<? AND bookID not in "
                    + "(SELECT bookID FROM bookinfo WHERE uploader=?) ORDER BY bookID";
            pst = conn.prepareStatement(sql);
            pst.setDouble(1, 5.0);
            pst.setString(2, username);
            rs = pst.executeQuery();
            lowRateBook.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "lowRateBook() error:\n" + e);
        }
    }

//clean the deatail of book when remove books.
    private void clean_lowrate_row_detail() {
        lowrate_bookid.setText("");
        lowrate_bookname.setText("");
        lowrating_counts.setText("");
        lowrating.setText("");
        lowrate_pts.setText("");
        lowrate_uploader.setText("");
        lowrate_removed.setText("");
        lowrate_summary.setText("");
    }

    //display the detail of the bookinfo, including the uploader and how many books have been removed
    private void lowrate_row_detail() {

        try {
            //get and set data from bookinfo 
            String sql1 = "SELECT * FROM bookInfo WHERE BookID= ?";
            int row = lowRateBook.getSelectedRow();
            int Table_click = (int) lowRateBook.getModel().getValueAt(row, 0);
            pst = conn.prepareStatement(sql1);
            pst.setInt(1, Table_click);
            rs = pst.executeQuery();

            while (rs.next()) {

                lowrate_bookid.setText(rs.getString("bookID"));

                lowrate_bookname.setText(rs.getString("bookname"));

                lowrate_uploader.setText(rs.getString("uploader"));

                lowrate_pts.setText(rs.getString("reward_points"));

                lowrating.setText(rs.getString("rating"));

                lowrating_counts.setText(rs.getString("rating_counts"));

                lowrate_summary.setText(rs.getString("summary"));

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "lowrate_row_ detail() sql1 error:\n" + e);
        }

        //get data about the uploader how many books get removed:
        try {
            String sql2 = "SELECT book_got_removed FROM userinfo WHERE username=?";
            pst = conn.prepareStatement(sql2);
            pst.setString(1, lowrate_uploader.getText());
            rs = pst.executeQuery();
            while (rs.next()) {
                lowrate_removed.setText(rs.getString("book_got_removed"));
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "low_rate_detail() sql2  error:\n" + e);
        }

    }

    //verify input field for reading points or  granted points is integer
    public void intValidation(java.awt.event.KeyEvent evt) {
        char c = evt.getKeyChar();
        if (!(Character.isDigit(c)) || (c == KeyEvent.VK_BACK_SPACE) || c == KeyEvent.VK_DELETE) {
            evt.consume();
            getToolkit().beep();
        }
    }

    //verify a row is select 
    private void SelectValidation(String table) {

        int row = -1;
        switch (table) {
            case "pdbooks":
                row = pdbooks.getSelectedRow();
                break;

            case "complaints":
                row = complaints.getSelectedRow();
                break;

            case "rareReadBook":
                row = rareReadBook.getSelectedRow();
                break;

            case "lowRateBook":
                row = lowRateBook.getSelectedRow();
                break;
        }

        if (row < 0) {
            JOptionPane.showMessageDialog(null, "Please Select a row.");
            return;
        }
    }

    public void SendMessage(String sender, String reciever, String message) {

        try {
            String sql = "INSERT INTO Message(sender,receiver,message_txt) VALUES(?,?,?)";

            pst = conn.prepareStatement(sql);
            pst.setString(1, sender);
            pst.setString(2, reciever);
            pst.setString(3, message);
            pst.execute();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "SendMessage() error:\n" + e);
            return;
        }

        JOptionPane.showMessageDialog(null, "User " + reciever + " will recieve message as following: \n" + message);

    }

    //this function delete a data row from table, 3 para, table name, id colum name, and id
    public void DeleteRecord(String table, String IDcolumn, int ID) {

        try {
            String sql = "DELETE FROM " + table + "  WHERE " + IDcolumn + "= ?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, ID);
            pst.execute();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "DeleteRecord() error:\n" + e);
            return;
        }
    }

    //Delete book from table
    private void DeleteBookFromTables(String Table, int bookID) {
        try {
            String sql = "DELETE FROM " + Table + " WHERE bookID=?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, bookID);
            pst.execute();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "DeleteBookFromTables() error:\n" + e);
            return;
        }
        // JOptionPane.showMessageDialog(null, "Remove all records related to book " + bookID + "  from Table " + Table + ".");
    }

    private void RemoveBook(int bookID) {
        //1)delete book for relate tables
        DeleteBookFromTables("review_rating", bookID);
        DeleteBookFromTables("readinghistory", bookID);
        DeleteBookFromTables("invitation", bookID);
        DeleteBookFromTables("complaint", bookID);
        DeleteBookFromTables("bookInfo", bookID);

        JOptionPane.showMessageDialog(null, "All records relate to this book have been removed from the system.");
    }

    private void DeductPoints(String user, int points) {

        //2)deduct points
        try {
            String sql = "UPDATE userInfo SET point_balance=point_balance - ? WHERE username= ?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, points);
            pst.setString(2, user);
            pst.execute();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "DeductPoints() error:\n" + e);
            return;
        }
        JOptionPane.showMessageDialog(null, points + " point has been deducted from the user " + user + "'s account.");

        String msg = "From Super User:\n" + points + " point has been deducted from your account.";
        //3)send message to the uploader            
        SendMessage(username, user, msg);
    }

    private void AddOneBookRemoved(String user) {

        try {
            String sql1 = "UPDATE userinfo SET book_got_removed=book_got_removed+1 WHERE username= ? ";
            pst = conn.prepareStatement(sql1);
            pst.setString(1, user);
            pst.execute();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "AddOneBookRemoved() error:\n" + e);
            return;
        }

        JOptionPane.showMessageDialog(null, "Add one to book_got_removed to user" + user + ".");
    }
    //put user in blacklist

    private void Blacklist(String user) {

        try {

            try {
                String sql = "UPDATE userInfo set in_blacklist=True WHERE username=? ";
                pst = conn.prepareStatement(sql);
                pst.setString(1, user);
                pst.execute();

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Blacklist() error:\n" + e);
                return;
            }
            String sql0 = "select email from userinfo where username = ?";
            pst = conn.prepareStatement(sql0);
            pst.setString(1, user);
            rs = pst.executeQuery();
            while (rs.next()) {
                String email = rs.getString("email");
                String sql1 = "Insert into blacklist (email) values (?)";
                pst = conn.prepareStatement(sql1);
                pst.setString(1, user);
                pst.execute();
            }

            JOptionPane.showMessageDialog(null, "The user " + user + " has been rejected from the system and put in blacklist.");
        } catch (SQLException ex) {
            Logger.getLogger(SuperUserFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        UsersRegistrationPUEntityManager = java.beans.Beans.isDesignTime() ? null : javax.persistence.Persistence.createEntityManagerFactory("UsersRegistrationPU").createEntityManager();
        bookinfoQuery1 = java.beans.Beans.isDesignTime() ? null : UsersRegistrationPUEntityManager.createQuery("SELECT b FROM Bookinfo b");
        bookinfoList1 = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : bookinfoQuery1.getResultList();
        Logout = new javax.swing.JButton();
        SuperUser = new javax.swing.JTabbedPane();
        NotificationCenter = new javax.swing.JTabbedPane();
        pendingbooksPane = new javax.swing.JPanel();
        txt_id = new javax.swing.JTextPane();
        l_pbid = new javax.swing.JLabel();
        lmsg = new javax.swing.JLabel();
        lbkname = new javax.swing.JLabel();
        lautho = new javax.swing.JLabel();
        lsumm = new javax.swing.JLabel();
        txt_bkname = new javax.swing.JTextPane();
        txt_auth = new javax.swing.JTextPane();
        luploader = new javax.swing.JLabel();
        txt_uploader = new javax.swing.JTextPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        txt_summ = new javax.swing.JTextArea();
        pdbooks_ScrollPane = new javax.swing.JScrollPane();
        pdbooks = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        txt_msg = new javax.swing.JTextArea();
        l_cate = new javax.swing.JLabel();
        txt_cate = new javax.swing.JTextPane();
        RefreshPbooks = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        PendingViewBook = new javax.swing.JButton();
        pending_cover = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        lgrant = new javax.swing.JLabel();
        txt_read_pts = new javax.swing.JEditorPane();
        txt_gra = new javax.swing.JEditorPane();
        lreq = new javax.swing.JLabel();
        lgrant1 = new javax.swing.JLabel();
        txt_req = new javax.swing.JTextPane();
        jPanel3 = new javax.swing.JPanel();
        Pending = new javax.swing.JButton();
        RestPbooks = new javax.swing.JButton();
        Decline = new javax.swing.JButton();
        Approve = new javax.swing.JButton();
        complaintboardPane = new javax.swing.JPanel();
        ComplaintPane = new javax.swing.JScrollPane();
        complaints = new javax.swing.JTable();
        RefreshCom = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        com_cover = new javax.swing.JLabel();
        ComViewBook = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        RestCom = new javax.swing.JButton();
        ComSeriousIssue = new javax.swing.JButton();
        ComRegIssue = new javax.swing.JButton();
        ComNotIssue = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        l_com_bname = new javax.swing.JLabel();
        com_rd_total = new javax.swing.JTextField();
        com_id = new javax.swing.JTextField();
        com_rt_cnt = new javax.swing.JTextField();
        jScrollPane7 = new javax.swing.JScrollPane();
        com_msg = new javax.swing.JTextArea();
        l_com_id = new javax.swing.JLabel();
        com_auth = new javax.swing.JTextField();
        com_rd_cnt = new javax.swing.JTextField();
        l_com_bID1 = new javax.swing.JLabel();
        l_com_bname1 = new javax.swing.JLabel();
        l_com_txt = new javax.swing.JLabel();
        l_com_uploader3 = new javax.swing.JLabel();
        book_com_time = new javax.swing.JTextField();
        l_com_uploader4 = new javax.swing.JLabel();
        l_com_uploader = new javax.swing.JLabel();
        l_com_uploader5 = new javax.swing.JLabel();
        l_com_uploader1 = new javax.swing.JLabel();
        l_com_uploader6 = new javax.swing.JLabel();
        l_com_user = new javax.swing.JLabel();
        book_removed = new javax.swing.JTextField();
        com_rt_total = new javax.swing.JTextField();
        com_bookname = new javax.swing.JTextField();
        l_com_uploader2 = new javax.swing.JLabel();
        l_com_bID = new javax.swing.JLabel();
        reward_pts = new javax.swing.JTextField();
        com_bID = new javax.swing.JTextField();
        l_com_txt1 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        com_issue = new javax.swing.JTextArea();
        com_user = new javax.swing.JTextField();
        com_uploader = new javax.swing.JTextField();
        unpopularbooksPane = new javax.swing.JPanel();
        RareReadPane = new javax.swing.JPanel();
        rareread_bookid = new javax.swing.JTextField();
        rareread_uploader = new javax.swing.JTextField();
        l_com_user2 = new javax.swing.JLabel();
        RemoveRareRead = new javax.swing.JButton();
        last_date_read = new javax.swing.JTextField();
        l_com_user1 = new javax.swing.JLabel();
        rareread_pts = new javax.swing.JTextField();
        jScrollPane8 = new javax.swing.JScrollPane();
        rareReadBook = new javax.swing.JTable();
        l_com_id1 = new javax.swing.JLabel();
        rareread_pts_sum = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        RefreshRareRead = new javax.swing.JButton();
        rareread_bookname = new javax.swing.JTextField();
        rareread_bookname_label = new javax.swing.JLabel();
        rareread_cnts = new javax.swing.JTextField();
        rareread_pts_label1 = new javax.swing.JLabel();
        rareread_duration = new javax.swing.JTextField();
        rareread_pts_label2 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        rareread_summary = new javax.swing.JTextArea();
        rareread_pts_label3 = new javax.swing.JLabel();
        lowratePane = new javax.swing.JPanel();
        l_com_id4 = new javax.swing.JLabel();
        RemoveLowRate = new javax.swing.JButton();
        lowrate_pts = new javax.swing.JTextField();
        lowrate_bookid = new javax.swing.JTextField();
        lowrating = new javax.swing.JTextField();
        lowrate_uploader = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        lowRateBook = new javax.swing.JTable();
        lowrate_uploader_label = new javax.swing.JLabel();
        lowrate_bookid_label = new javax.swing.JLabel();
        lowrate_removed = new javax.swing.JTextField();
        l_com_user4 = new javax.swing.JLabel();
        l_com_id6 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lowrating_counts = new javax.swing.JTextField();
        l_com_user5 = new javax.swing.JLabel();
        RefreshLowRate = new javax.swing.JButton();
        lowrate_bookid_label1 = new javax.swing.JLabel();
        lowrate_bookname = new javax.swing.JTextField();
        rareread_pts_sum1 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        lowrate_summary = new javax.swing.JTextArea();
        welcome = new javax.swing.JLabel();
        RegularUse = new javax.swing.JButton();

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

        txt_id.setEditable(false);

        l_pbid.setText("pbID");

        lmsg.setText("Message");

        lbkname.setText("Book Name");

        lautho.setText("Author");

        lsumm.setText("Summary");

        txt_bkname.setEditable(false);

        txt_auth.setEditable(false);

        luploader.setText("Uploader");

        txt_uploader.setEditable(false);

        txt_summ.setEditable(false);
        txt_summ.setColumns(20);
        txt_summ.setLineWrap(true);
        txt_summ.setRows(5);
        txt_summ.setDragEnabled(false);
        jScrollPane3.setViewportView(txt_summ);

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
            public void keyReleased(java.awt.event.KeyEvent evt) {
                pdbooksKeyReleased(evt);
            }
        });
        pdbooks_ScrollPane.setViewportView(pdbooks);
        if (pdbooks.getColumnModel().getColumnCount() > 0) {
            pdbooks.getColumnModel().getColumn(0).setPreferredWidth(5);
        }

        txt_msg.setColumns(20);
        txt_msg.setLineWrap(true);
        txt_msg.setRows(5);
        txt_msg.setAutoscrolls(false);
        jScrollPane2.setViewportView(txt_msg);

        l_cate.setText("Category");

        txt_cate.setEditable(false);

        RefreshPbooks.setFont(new java.awt.Font("Lucida Grande", 0, 24)); // NOI18N
        RefreshPbooks.setText("Pending Books");
        RefreshPbooks.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RefreshPbooksActionPerformed(evt);
            }
        });

        jLabel5.setText("Click to Refresh ");

        PendingViewBook.setText("View Book");
        PendingViewBook.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PendingViewBookActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(PendingViewBook, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(pending_cover, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pending_cover, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addComponent(PendingViewBook))
        );

        lgrant.setText("Granted Points");

        txt_read_pts.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_read_ptsKeyTyped(evt);
            }
        });

        txt_gra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_graKeyTyped(evt);
            }
        });

        lreq.setText("Request Points");

        lgrant1.setText("Reading Points");

        txt_req.setEditable(false);
        txt_req.setDragEnabled(false);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lgrant)
                        .addComponent(txt_gra, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lreq)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGap(6, 6, 6)
                            .addComponent(txt_req, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(lgrant1, javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                            .addGap(3, 3, 3)
                            .addComponent(txt_read_pts, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {txt_gra, txt_read_pts, txt_req});

        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
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
                .addComponent(txt_read_pts, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {txt_gra, txt_read_pts, txt_req});

        Pending.setText("Pending");
        Pending.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PendingActionPerformed(evt);
            }
        });

        RestPbooks.setText("Reset");
        RestPbooks.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RestPbooksActionPerformed(evt);
            }
        });

        Decline.setText("Decline");
        Decline.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeclineActionPerformed(evt);
            }
        });

        Approve.setText("Approve");
        Approve.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ApproveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(RestPbooks, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Decline, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Approve, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Pending, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {Approve, Decline, Pending, RestPbooks});

        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Approve)
                .addGap(9, 9, 9)
                .addComponent(Pending)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Decline)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(RestPbooks)
                .addContainerGap(52, Short.MAX_VALUE))
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {Approve, Decline, Pending, RestPbooks});

        javax.swing.GroupLayout pendingbooksPaneLayout = new javax.swing.GroupLayout(pendingbooksPane);
        pendingbooksPane.setLayout(pendingbooksPaneLayout);
        pendingbooksPaneLayout.setHorizontalGroup(
            pendingbooksPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pendingbooksPaneLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(pendingbooksPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pendingbooksPaneLayout.createSequentialGroup()
                        .addGroup(pendingbooksPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pendingbooksPaneLayout.createSequentialGroup()
                                .addComponent(lsumm)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pendingbooksPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(pendingbooksPaneLayout.createSequentialGroup()
                                    .addGap(71, 71, 71)
                                    .addGroup(pendingbooksPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(pendingbooksPaneLayout.createSequentialGroup()
                                            .addComponent(txt_id, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(luploader)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(txt_uploader, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(txt_auth)
                                        .addComponent(txt_bkname)))
                                .addGroup(pendingbooksPaneLayout.createSequentialGroup()
                                    .addComponent(l_cate)
                                    .addGap(18, 18, 18)
                                    .addComponent(txt_cate, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pendingbooksPaneLayout.createSequentialGroup()
                                    .addComponent(RefreshPbooks, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jLabel5)
                                    .addGap(45, 45, 45)))
                            .addComponent(lautho)
                            .addComponent(lbkname)
                            .addGroup(pendingbooksPaneLayout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addComponent(l_pbid)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(20, 20, 20))
                    .addGroup(pendingbooksPaneLayout.createSequentialGroup()
                        .addComponent(lmsg)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(42, 42, 42)))
                .addComponent(pdbooks_ScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 650, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(324, 324, 324))
        );
        pendingbooksPaneLayout.setVerticalGroup(
            pendingbooksPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pendingbooksPaneLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(pendingbooksPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(RefreshPbooks, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(28, 28, 28)
                .addGroup(pendingbooksPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pendingbooksPaneLayout.createSequentialGroup()
                        .addComponent(pdbooks_ScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 609, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(13, Short.MAX_VALUE))
                    .addGroup(pendingbooksPaneLayout.createSequentialGroup()
                        .addGroup(pendingbooksPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(pendingbooksPaneLayout.createSequentialGroup()
                                .addGroup(pendingbooksPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pendingbooksPaneLayout.createSequentialGroup()
                                        .addComponent(l_pbid)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(lbkname)
                                        .addGap(6, 6, 6)
                                        .addComponent(lautho, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(pendingbooksPaneLayout.createSequentialGroup()
                                        .addGroup(pendingbooksPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txt_id, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txt_uploader, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(luploader))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txt_bkname, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(6, 6, 6)
                                        .addComponent(txt_auth, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(pendingbooksPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txt_cate, javax.swing.GroupLayout.DEFAULT_SIZE, 24, Short.MAX_VALUE)
                                    .addComponent(l_cate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(32, 32, 32)
                                .addGroup(pendingbooksPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lsumm)
                                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(pendingbooksPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lmsg, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(92, 92, 92))))
        );

        NotificationCenter.addTab("Pending  Books", pendingbooksPane);

        complaintboardPane.setBackground(new java.awt.Color(0, 204, 51));

        complaints.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "CID", "USERNAME", "BOOKID", "BOOKNAME", "ISSUE"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        complaints.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                complaintsMouseClicked(evt);
            }
        });
        complaints.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                complaintsKeyReleased(evt);
            }
        });
        ComplaintPane.setViewportView(complaints);
        if (complaints.getColumnModel().getColumnCount() > 0) {
            complaints.getColumnModel().getColumn(0).setPreferredWidth(2);
            complaints.getColumnModel().getColumn(1).setPreferredWidth(5);
            complaints.getColumnModel().getColumn(2).setPreferredWidth(5);
            complaints.getColumnModel().getColumn(3).setPreferredWidth(15);
            complaints.getColumnModel().getColumn(4).setPreferredWidth(25);
        }

        RefreshCom.setFont(new java.awt.Font("Lucida Grande", 0, 24)); // NOI18N
        RefreshCom.setText("Complaints");
        RefreshCom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RefreshComActionPerformed(evt);
            }
        });

        jLabel2.setText("Click to Refresh ");

        ComViewBook.setText("View Book");
        ComViewBook.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComViewBookActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(61, 61, 61)
                .addComponent(ComViewBook)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(com_cover, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(148, 148, 148))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(com_cover, javax.swing.GroupLayout.DEFAULT_SIZE, 283, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ComViewBook)
                .addContainerGap())
        );

        RestCom.setText("Reset");
        RestCom.setPreferredSize(new java.awt.Dimension(160, 30));
        RestCom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RestComActionPerformed(evt);
            }
        });

        ComSeriousIssue.setText("Comfirm As a Serious Issue");
        ComSeriousIssue.setMinimumSize(new java.awt.Dimension(160, 30));
        ComSeriousIssue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComSeriousIssueActionPerformed(evt);
            }
        });

        ComRegIssue.setText("Comfirm As a Regular Issue");
        ComRegIssue.setPreferredSize(new java.awt.Dimension(160, 30));
        ComRegIssue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComRegIssueActionPerformed(evt);
            }
        });

        ComNotIssue.setText("Comfirm As Not A Issue");
        ComNotIssue.setPreferredSize(new java.awt.Dimension(160, 30));
        ComNotIssue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComNotIssueActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ComNotIssue, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ComRegIssue, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ComSeriousIssue, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(RestCom, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel5Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {ComNotIssue, ComRegIssue, ComSeriousIssue, RestCom});

        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(ComNotIssue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ComRegIssue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ComSeriousIssue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(RestCom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        l_com_bname.setText("Book Name");

        com_rd_total.setEditable(false);

        com_id.setEditable(false);

        com_rt_cnt.setEditable(false);

        com_msg.setColumns(20);
        com_msg.setLineWrap(true);
        com_msg.setRows(5);
        jScrollPane7.setViewportView(com_msg);

        l_com_id.setText("CID");

        com_auth.setEditable(false);

        com_rd_cnt.setEditable(false);

        l_com_bID1.setText("Reward Points");

        l_com_bname1.setText("Author");

        l_com_txt.setText("Message");

        l_com_uploader3.setText("Times Got Complainted:");

        book_com_time.setEditable(false);

        l_com_uploader4.setText("Rating Count");

        l_com_uploader.setText("Book Uploader");

        l_com_uploader5.setText("Total Rating");

        l_com_uploader1.setText("Books Got  Removed:");

        l_com_uploader6.setText("Total  Duration");

        l_com_user.setText("Complainter");

        book_removed.setEditable(false);

        com_rt_total.setEditable(false);

        com_bookname.setEditable(false);

        l_com_uploader2.setText("Reading Count");

        l_com_bID.setText("Book ID");

        reward_pts.setEditable(false);

        com_bID.setEditable(false);

        l_com_txt1.setText("Issue");

        com_issue.setEditable(false);
        com_issue.setColumns(20);
        com_issue.setLineWrap(true);
        com_issue.setRows(5);
        jScrollPane4.setViewportView(com_issue);

        com_user.setEditable(false);

        com_uploader.setEditable(false);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(l_com_txt1, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(l_com_txt))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(l_com_uploader4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(com_rt_cnt, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(l_com_uploader5)
                        .addGap(31, 31, 31)
                        .addComponent(com_rt_total, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(l_com_uploader)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(com_uploader, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(l_com_uploader1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(book_removed, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(l_com_bID, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(com_bID, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(l_com_uploader3)
                        .addGap(2, 2, 2)
                        .addComponent(book_com_time, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(l_com_id, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(com_id, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(l_com_user, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(com_user, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(l_com_bID1, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(reward_pts, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(l_com_bname, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(com_bookname, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(l_com_bname1, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(com_auth, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 344, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel6Layout.createSequentialGroup()
                            .addComponent(l_com_uploader2)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(com_rd_cnt, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(l_com_uploader6)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(com_rd_total, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 346, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(com_id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(l_com_id, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(l_com_user, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(com_user, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(l_com_uploader, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(com_uploader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(book_removed, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(l_com_uploader1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(l_com_bID, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(com_bID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(book_com_time, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(l_com_uploader3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(l_com_bID1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(reward_pts, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(l_com_bname, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(com_bookname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(l_com_bname1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(com_auth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(l_com_uploader2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(l_com_uploader6, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(com_rd_total, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(com_rd_cnt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(l_com_uploader4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(com_rt_cnt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(l_com_uploader5, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(com_rt_total, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(l_com_txt1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(l_com_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout complaintboardPaneLayout = new javax.swing.GroupLayout(complaintboardPane);
        complaintboardPane.setLayout(complaintboardPaneLayout);
        complaintboardPaneLayout.setHorizontalGroup(
            complaintboardPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(complaintboardPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addGroup(complaintboardPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(ComplaintPane, javax.swing.GroupLayout.PREFERRED_SIZE, 694, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, complaintboardPaneLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(RefreshCom, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addGap(1119, 1119, 1119))
        );
        complaintboardPaneLayout.setVerticalGroup(
            complaintboardPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(complaintboardPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(complaintboardPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(RefreshCom, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(complaintboardPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(complaintboardPaneLayout.createSequentialGroup()
                        .addGroup(complaintboardPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(complaintboardPaneLayout.createSequentialGroup()
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, complaintboardPaneLayout.createSequentialGroup()
                        .addComponent(ComplaintPane)
                        .addGap(38, 38, 38))))
        );

        NotificationCenter.addTab("Complaint Board", complaintboardPane);

        RareReadPane.setBackground(new java.awt.Color(0, 255, 153));

        rareread_bookid.setEditable(false);

        rareread_uploader.setEditable(false);

        l_com_user2.setText("Last Date Read");

        RemoveRareRead.setText("Remove this Book");
        RemoveRareRead.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RemoveRareReadActionPerformed(evt);
            }
        });

        last_date_read.setEditable(false);

        l_com_user1.setText("Uploader");

        rareread_pts.setEditable(false);

        rareReadBook.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "BookID", "Book Name", "Last Date Read", "Uploader"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
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
        rareReadBook.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rareReadBookMouseClicked(evt);
            }
        });
        rareReadBook.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                rareReadBookKeyReleased(evt);
            }
        });
        jScrollPane8.setViewportView(rareReadBook);

        l_com_id1.setText("BOOKID");

        rareread_pts_sum.setText("Summary");

        jLabel4.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        jLabel4.setText("Books that have not been read for more than 1 year.");

        RefreshRareRead.setText("Refresh the Table");
        RefreshRareRead.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RefreshRareReadActionPerformed(evt);
            }
        });

        rareread_bookname.setEditable(false);

        rareread_bookname_label.setText("BOOKName");

        rareread_cnts.setEditable(false);

        rareread_pts_label1.setText("Reading Counts");

        rareread_duration.setEditable(false);

        rareread_pts_label2.setText("Total Reading Duration");

        rareread_summary.setEditable(false);
        rareread_summary.setColumns(20);
        rareread_summary.setLineWrap(true);
        rareread_summary.setRows(5);
        rareread_summary.setAutoscrolls(false);
        jScrollPane5.setViewportView(rareread_summary);

        rareread_pts_label3.setText("Reading Points");

        javax.swing.GroupLayout RareReadPaneLayout = new javax.swing.GroupLayout(RareReadPane);
        RareReadPane.setLayout(RareReadPaneLayout);
        RareReadPaneLayout.setHorizontalGroup(
            RareReadPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(RareReadPaneLayout.createSequentialGroup()
                .addGroup(RareReadPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(RareReadPaneLayout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 474, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(RareReadPaneLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 587, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(RareReadPaneLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(RemoveRareRead)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(RefreshRareRead))
                    .addGroup(RareReadPaneLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(RareReadPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(RareReadPaneLayout.createSequentialGroup()
                                .addGroup(RareReadPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(RareReadPaneLayout.createSequentialGroup()
                                        .addGap(56, 56, 56)
                                        .addComponent(rareread_bookid, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(l_com_id1, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(rareread_bookname_label, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rareread_bookname, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(RareReadPaneLayout.createSequentialGroup()
                                .addGroup(RareReadPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(RareReadPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(rareread_pts_sum, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(l_com_user2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(rareread_pts_label3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(RareReadPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane5)
                                    .addGroup(RareReadPaneLayout.createSequentialGroup()
                                        .addComponent(last_date_read, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(34, 34, 34)
                                        .addComponent(l_com_user1, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(2, 2, 2)
                                        .addComponent(rareread_uploader, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addGroup(RareReadPaneLayout.createSequentialGroup()
                                        .addComponent(rareread_pts, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(rareread_pts_label1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(rareread_cnts, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(rareread_pts_label2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(rareread_duration)))))))
                .addContainerGap(67, Short.MAX_VALUE))
        );
        RareReadPaneLayout.setVerticalGroup(
            RareReadPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(RareReadPaneLayout.createSequentialGroup()
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(RareReadPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rareread_bookid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(l_com_id1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rareread_bookname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rareread_bookname_label, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(RareReadPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(l_com_user2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(last_date_read, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(l_com_user1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rareread_uploader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(RareReadPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rareread_pts, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rareread_cnts, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rareread_pts_label1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rareread_pts_label2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rareread_duration, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rareread_pts_label3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(RareReadPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(RareReadPaneLayout.createSequentialGroup()
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(RareReadPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(RemoveRareRead)
                            .addComponent(RefreshRareRead)))
                    .addComponent(rareread_pts_sum, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lowratePane.setBackground(new java.awt.Color(0, 255, 153));

        l_com_id4.setText("Books Got Removed");

        RemoveLowRate.setText("Remove this Book");
        RemoveLowRate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RemoveLowRateActionPerformed(evt);
            }
        });

        lowrate_pts.setEditable(false);

        lowrate_bookid.setEditable(false);

        lowrating.setEditable(false);

        lowrate_uploader.setEditable(false);

        lowRateBook.setModel(new javax.swing.table.DefaultTableModel(
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
        lowRateBook.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lowRateBookMouseClicked(evt);
            }
        });
        lowRateBook.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                lowRateBookKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(lowRateBook);

        lowrate_uploader_label.setText("Uploader");

        lowrate_bookid_label.setText("BOOKID");

        lowrate_removed.setEditable(false);

        l_com_user4.setText("Rating");

        l_com_id6.setText("Reward Points");

        jLabel3.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        jLabel3.setText("Books that have been rated under 5.");

        lowrating_counts.setEditable(false);

        l_com_user5.setText("Rating Counts");

        RefreshLowRate.setText("Refresh the Table");
        RefreshLowRate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RefreshLowRateActionPerformed(evt);
            }
        });

        lowrate_bookid_label1.setText("BOOKName");

        lowrate_bookname.setEditable(false);

        rareread_pts_sum1.setText("Summary");

        lowrate_summary.setEditable(false);
        lowrate_summary.setColumns(20);
        lowrate_summary.setLineWrap(true);
        lowrate_summary.setRows(5);
        lowrate_summary.setAutoscrolls(false);
        jScrollPane6.setViewportView(lowrate_summary);

        javax.swing.GroupLayout lowratePaneLayout = new javax.swing.GroupLayout(lowratePane);
        lowratePane.setLayout(lowratePaneLayout);
        lowratePaneLayout.setHorizontalGroup(
            lowratePaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(lowratePaneLayout.createSequentialGroup()
                .addComponent(RemoveLowRate)
                .addGap(18, 18, 18)
                .addComponent(RefreshLowRate)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(lowratePaneLayout.createSequentialGroup()
                .addGroup(lowratePaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, lowratePaneLayout.createSequentialGroup()
                        .addComponent(rareread_pts_sum1, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane6))
                    .addGroup(lowratePaneLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(lowratePaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 329, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 637, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(lowratePaneLayout.createSequentialGroup()
                                .addGroup(lowratePaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(lowratePaneLayout.createSequentialGroup()
                                        .addComponent(l_com_id6, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lowrate_pts, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(lowrate_uploader_label, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lowrate_uploader, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(lowratePaneLayout.createSequentialGroup()
                                        .addComponent(lowrate_bookid_label, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lowrate_bookid, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(l_com_user4, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lowrating, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(l_com_user5, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(lowratePaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(lowratePaneLayout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addComponent(l_com_id4, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lowrate_removed, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(lowrating_counts, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(lowratePaneLayout.createSequentialGroup()
                                .addComponent(lowrate_bookid_label1)
                                .addGap(24, 24, 24)
                                .addComponent(lowrate_bookname, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        lowratePaneLayout.setVerticalGroup(
            lowratePaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(lowratePaneLayout.createSequentialGroup()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(lowratePaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lowrate_bookid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lowrate_bookid_label, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(l_com_user4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lowrating, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(l_com_user5, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lowrating_counts, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(lowratePaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lowrate_bookname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lowrate_bookid_label1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(lowratePaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lowrate_uploader_label, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lowrate_removed, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(l_com_id4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lowrate_uploader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(l_com_id6, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lowrate_pts, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(lowratePaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rareread_pts_sum1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(lowratePaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(RemoveLowRate)
                    .addComponent(RefreshLowRate))
                .addContainerGap(85, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout unpopularbooksPaneLayout = new javax.swing.GroupLayout(unpopularbooksPane);
        unpopularbooksPane.setLayout(unpopularbooksPaneLayout);
        unpopularbooksPaneLayout.setHorizontalGroup(
            unpopularbooksPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, unpopularbooksPaneLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(RareReadPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(lowratePane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(233, 233, 233))
        );
        unpopularbooksPaneLayout.setVerticalGroup(
            unpopularbooksPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(RareReadPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lowratePane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        NotificationCenter.addTab("Unpopular Books", unpopularbooksPane);

        SuperUser.addTab("Notification Center", NotificationCenter);

        welcome.setFont(new java.awt.Font("Lucida Grande", 1, 24)); // NOI18N
        welcome.setText("Welcome, Super User ");

        RegularUse.setText("Regular Use");
        RegularUse.setFocusable(false);
        RegularUse.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        RegularUse.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        RegularUse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RegularUseActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(welcome, javax.swing.GroupLayout.PREFERRED_SIZE, 1038, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(RegularUse, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
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
                    .addComponent(welcome)
                    .addComponent(RegularUse))
                .addGap(18, 18, 18)
                .addComponent(SuperUser, javax.swing.GroupLayout.PREFERRED_SIZE, 735, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void LogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LogoutActionPerformed
        // TODO add your handling code here:
        cancel();
        tabpannedAH home = new tabpannedAH();
        home.setVisible(true);
    }//GEN-LAST:event_LogoutActionPerformed

    private void RefreshLowRateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RefreshLowRateActionPerformed
        // TODO add your handling code here:
        clean_lowrate_row_detail();
        lowRateBook();
    }//GEN-LAST:event_RefreshLowRateActionPerformed

    private void lowRateBookKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lowRateBookKeyReleased
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_DOWN || evt.getKeyCode() == KeyEvent.VK_UP) {
            lowrate_row_detail();
        }
    }//GEN-LAST:event_lowRateBookKeyReleased

    private void lowRateBookMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lowRateBookMouseClicked
        // TODO add your handling code here:
        lowrate_row_detail();
    }//GEN-LAST:event_lowRateBookMouseClicked

    private void RemoveLowRateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RemoveLowRateActionPerformed
        // TODO add your handling code here:

        //verify selection
        SelectValidation("lowRateBook");
        //get the bookid
        int bookID = Integer.parseInt(lowrate_bookid.getText());
        //get the bookname
        String bookname = lowrate_bookname.getText();
        //get the uploader's name
        String uploader = lowrate_uploader.getText();
        //get the times the uplaoder's books got removed.
        int book_got_removed = Integer.parseInt(lowrate_removed.getText());
        //get the reward points;
        int reward_points = Integer.parseInt(lowrate_pts.getText());

        //remove the book
        JOptionPane.showMessageDialog(null, "This book will be removed because it has been rated under 5.");
        RemoveBook(bookID);
        clean_lowrate_row_detail();
        lowRateBook();

        //if book_got_removed is already 4,
        //put the uploader in blacklist and remove the book.
        //add one to book_got_removed for the uplaoder, deduct the reward points
        String msg = "You book [" + bookname + "] has been removed because it has been rated under 5.";
        SendMessage(username, uploader, msg);
        AddOneBookRemoved(uploader);
        DeductPoints(uploader, reward_points);

        if (book_got_removed == 4) {
            JOptionPane.showMessageDialog(null, "This user has 5 books got removed already.");
            Blacklist(uploader);
        }
    }//GEN-LAST:event_RemoveLowRateActionPerformed

    private void RefreshRareReadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RefreshRareReadActionPerformed
        // TODO add your handling code here:
        clean_rareread_row_detail();
        rareReadBook();
    }//GEN-LAST:event_RefreshRareReadActionPerformed

    private void rareReadBookKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_rareReadBookKeyReleased
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_DOWN || evt.getKeyCode() == KeyEvent.VK_UP) {
            rareread_row_detail();
        }
    }//GEN-LAST:event_rareReadBookKeyReleased

    private void rareReadBookMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rareReadBookMouseClicked
        // TODO add your handling code here:
        rareread_row_detail();
    }//GEN-LAST:event_rareReadBookMouseClicked

    private void RemoveRareReadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RemoveRareReadActionPerformed
        // TODO add your handling code here:

        //verify selection
        SelectValidation("rareReadBook");

        //get the bookid
        int bookID = Integer.parseInt(rareread_bookid.getText());
        //get the uploader's name
        String uploader = rareread_uploader.getText();
        //get the times the uplaoder's books got removed.
        int book_got_removed = Integer.parseInt(rareread_pts.getText());

        //remove the book
        JOptionPane.showMessageDialog(null, "This book will be removed because it has not been read for 1 year");
        RemoveBook(bookID);

        //only deduct 5 points from uploader, not add one book_got_remove to us
        DeductPoints(uploader, 5);
        rareReadBook();
    }//GEN-LAST:event_RemoveRareReadActionPerformed

    private void RefreshComActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RefreshComActionPerformed
        // TODO add your handling code here:
        clean_com_row_detail();
        complaints();
    }//GEN-LAST:event_RefreshComActionPerformed

    private void RestComActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RestComActionPerformed
        // TODO add your handling code here:
        com_msg.setText("");
    }//GEN-LAST:event_RestComActionPerformed

    private void ComViewBookActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComViewBookActionPerformed
        // TODO add your handling code here:
        SelectValidation("complaints");
        int bookID = Integer.parseInt(com_bID.getText());
        String bookname = com_bookname.getText();
        new SuperUserCheckBook("complaints", bookID, bookname).setVisible(true);
    }//GEN-LAST:event_ComViewBookActionPerformed

    private void complaintsKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_complaintsKeyReleased
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_UP || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            complaints_row_detail();
        }
    }//GEN-LAST:event_complaintsKeyReleased

    private void complaintsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_complaintsMouseClicked
        // TODO add your handling code here:
        complaints_row_detail();
    }//GEN-LAST:event_complaintsMouseClicked

    private void ComSeriousIssueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComSeriousIssueActionPerformed
        // TODO add your handling code here:

        //if not select
        SelectValidation("complaints");

        JOptionPane.showMessageDialog(null, "This complaint has been confirm as serious issue.");
        //remove book
        int bookID = Integer.parseInt(com_bID.getText());
        String upload_username = com_uploader.getText();
        RemoveBook(bookID);
        AddOneBookRemoved(upload_username);
        //put uploader in blacklist
        Blacklist(upload_username);
        //System.out.println(upload_username);
        clean_com_row_detail();
        complaints();
    }//GEN-LAST:event_ComSeriousIssueActionPerformed

    private void ComNotIssueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComNotIssueActionPerformed
        // TODO add your handling code here:
        SelectValidation("complaints");

        //1.send message to user who complaint this book.
        String msg = com_msg.getText();
        String bookname = com_bookname.getText();
        String complainter = com_user.getText();
        msg += "This book [" + bookname + "] has been confirmed with no issue.";

        SendMessage(username, complainter, msg);

        //2.delete this from  complaint table
        int cid = Integer.parseInt(com_id.getText());
        DeleteRecord("complaint", "cID", cid);

        JOptionPane.showMessageDialog(null, "This complaint has been ignored");
        complaints();
        clean_com_row_detail();
    }//GEN-LAST:event_ComNotIssueActionPerformed

    private void ComRegIssueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComRegIssueActionPerformed
        // TODO add your handling code here:
        //if no select
        SelectValidation("complaints");

        JOptionPane.showMessageDialog(null, "This complaint has been confirm as regular issue.");

        int bookID = Integer.parseInt(com_bID.getText());
        int reward_points = Integer.parseInt(reward_pts.getText());
        int got_complainted = Integer.parseInt(book_com_time.getText());
        int book_got_removed = Integer.parseInt(book_removed.getText());
        String upload_username = com_uploader.getText();
        String bookname = com_bookname.getText();
        String msg = com_msg.getText();

        //1. if the book has 2 complaints already, will be removed automatically,
        //the point B for this book when the RU contributed this book will be deducted
        //from his/her account with additional -100 points as penalty
        if (got_complainted == 2) {

            JOptionPane.showMessageDialog(null, " This book will be removed"
                    + "because it has 3 complaints");

            RemoveBook(bookID);

            //deduct points
            int deduct_points = reward_points + 100;
            DeductPoints(upload_username, deduct_points);

            //and send message to user
            String msg1 = "From Super User: \nYour book [ " + bookname + " ] has been removed because of 3 complaints.";
            SendMessage(username, upload_username, msg1);

            /*.An RU whose books are removed  5 times or who contributed one copyrighted book will be ejected
             from the system and put in the blacklist who can never register again. */
            //1. if the uploader's book_got_remove is already 4, put the uploader in blacklist
            if (book_got_removed == 4) {
                JOptionPane.showMessageDialog(null, "This user will be put in black list because his books got removed 5 times.");
                Blacklist(upload_username);
            }

        } else {
            //4.add one complaint to the book
            try {
                String sql4 = "UPDATE bookInfo SET got_complainted=got_complainted+1 WHERE bookID=?";
                pst = conn.prepareStatement(sql4);
                pst.setInt(1, bookID);
                pst.execute();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "ConfirmRegIssue() sql4 error:\n" + e);
                return;
            }

            //5.send message to user
            msg = "From Super User:\nYou book [" + bookname + "]  got one complaint.\n" + msg;
            SendMessage(username, upload_username, msg);

        }
        //4.delete this record;
        int cid = Integer.parseInt(com_id.getText());
        DeleteRecord("complaint", "cID", cid);

        clean_com_row_detail();
        complaints();
    }//GEN-LAST:event_ComRegIssueActionPerformed

    private void RefreshPbooksActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RefreshPbooksActionPerformed
        // TODO add your handling code here:
        clean_pdbooks_row_detail();
        pendingbooks();
    }//GEN-LAST:event_RefreshPbooksActionPerformed

    private void PendingViewBookActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PendingViewBookActionPerformed
        // TODO add your handling code here:
        SelectValidation("pdbooks");
        int row = pdbooks.getSelectedRow();
        int rowID = (int) pdbooks.getModel().getValueAt(row, 0);
        String bookname = txt_bkname.getText();
        new SuperUserCheckBook("pendingbook", rowID, bookname).setVisible(true);
    }//GEN-LAST:event_PendingViewBookActionPerformed

    private void RestPbooksActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RestPbooksActionPerformed
        // TODO add your handling code here:
        txt_msg.setText("");
        txt_gra.setText("0");
        txt_read_pts.setText("0");
    }//GEN-LAST:event_RestPbooksActionPerformed

    private void pdbooksKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_pdbooksKeyReleased
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_DOWN || evt.getKeyCode() == KeyEvent.VK_UP) {
            pdbooks_row_detail();
        }
    }//GEN-LAST:event_pdbooksKeyReleased

    private void pdbooksMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pdbooksMouseClicked
        // TODO add your handling code here:
        pdbooks_row_detail();
    }//GEN-LAST:event_pdbooksMouseClicked

    private void txt_read_ptsKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_read_ptsKeyTyped
        // TODO add your handling code here:
        intValidation(evt);
    }//GEN-LAST:event_txt_read_ptsKeyTyped

    private void txt_graKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_graKeyTyped
        // TODO add your handling code here:
        intValidation(evt);
    }//GEN-LAST:event_txt_graKeyTyped

    private void ApproveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ApproveActionPerformed
        // TODO add your handling code here:

        //if select no row
        SelectValidation("pdbooks");
        //if not assign reading point for the book
        String read_pts_txt = txt_read_pts.getText();

        int reading_points = 0;
        if (read_pts_txt.length() > 0) {
            reading_points = Integer.parseInt(read_pts_txt);
        }

        if (read_pts_txt.length() == 0 || reading_points == 0) {
            JOptionPane.showMessageDialog(null, "Please assgin the reading points for this book.");
            return;
        }

        //0.get the bookfile and cover
        try {
            int pbid = Integer.parseInt(txt_id.getText());
            String sql0 = "SELECT * FROM pendingbook WHERE pbid=?";
            pst = conn.prepareStatement(sql0);
            pst.setInt(1, pbid);
            rs = pst.executeQuery();
            while (rs.next()) {

                try {

            //====1. insert book to bookinfo
                    // String sql = "INSERT INTO BookInfo (uploader,bookname,author,summary,category,reward_points,reading_points,bookfile,cover)"
                    //+ "VALUES(?,?,?,?,?,?,?,(SELECT bookfile FROM pendingbook WHERE pbid=?),?)";
                    String sql = "INSERT INTO BookInfo (uploader,bookname,author,summary,category,reward_points,reading_points,bookfile,cover)"
                            + "VALUES(?,?,?,?,?,?,?,?,?)";

                    String uploader = rs.getString("uploader");
                    String bookname = rs.getString("bookname");
                    String author = rs.getString("author");
                    String summary = rs.getString("summary");
                    String category = rs.getString("category");
                    int reward_points = Integer.parseInt(rs.getString("request_points"));

                    pst = conn.prepareStatement(sql);
                    pst.setString(1, uploader);
                    pst.setString(2, bookname);
                    pst.setString(3, author);
                    pst.setString(4, summary);
                    pst.setString(5, rs.getString("category"));
                    pst.setInt(6, reward_points);
                    pst.setInt(7, reading_points);

                    int pos = 1;
                    int len = 10;

                    Blob bookfile = rs.getBlob("BOOKFILE");
                    byte[] array = bookfile.getBytes(1, (int) bookfile.length());
                    InputStream input = new ByteArrayInputStream(array);
                    pst.setBinaryStream(8, input, (int) bookfile.length());

           // pst.setInt(8,pbid);
//                    byte[] cover_bytes;
//                    cover_bytes = rs.getBytes("cover");
//                    pst.setBinaryStream(9, new ByteArrayInputStream(cover_bytes), cover_bytes.length);
                    Blob coverpage = rs.getBlob("Cover");
                    byte[] cover_array = coverpage.getBytes(1, (int) coverpage.length());
                    InputStream cover_input = new ByteArrayInputStream(cover_array);
                    pst.setBinaryStream(9, cover_input, (int) coverpage.length());

                    pst.execute();

                    //2.===delete from pending books`
                    int pbID = Integer.parseInt(txt_id.getText());
                    DeleteRecord("pendingbook", "pbID", pbID);

                    //3.====add points to the uploader's account;
                    String sql3 = "UPDATE userInfo SET point_balance=point_balance+ ?  WHERE username=?";
                    pst = conn.prepareStatement(sql3);
                    pst.setInt(1, reward_points);
                    pst.setString(2, uploader);
                    pst.execute();

                    //4.====send message to the uploader;
                    String comment = txt_msg.getText();
                    String msg = "From Super User \n: You book   [" + bookname + "]  has been approved.\nYou are awaded   " + reward_points + "    points.\n" + " The reading points for this"
                            + " book is" + reading_points + ".";

                    if (!comment.trim().equals("")) {
                        msg += "\nComment: " + comment + ".";
                    }

                    SendMessage(username, uploader, msg);

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "ApproveBook sql error:\n" + e);
                    return;
                }

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ApproveBook sql0 error:\n" + e);
            return;
        }

        JOptionPane.showMessageDialog(null, "The book has been approved");
        clean_pdbooks_row_detail();
        pendingbooks();
    }//GEN-LAST:event_ApproveActionPerformed

    private void DeclineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeclineActionPerformed
        // TODO add your handling code here:

        //if not select
        SelectValidation("pdbooks");

        try {
            //1.===delete from pending books`
            int pbID = Integer.parseInt(txt_id.getText());
            DeleteRecord("pendingbook", "pbID", pbID);

            //2.====send message to the uploader;
            String upload_username = txt_uploader.getText();
            String bkname = txt_bkname.getText();
            String comment = txt_msg.getText();
            String msg = "From Super User \n:" + comment + "\nYou book   [" + bkname + "]  has been declined.";

            SendMessage(username, upload_username, msg);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            return;
        }

        JOptionPane.showMessageDialog(null, "The book has been declined");
        clean_pdbooks_row_detail();
        pendingbooks();
    }//GEN-LAST:event_DeclineActionPerformed

    private void PendingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PendingActionPerformed
        // TODO add your handling code here:
        //if no row selected
        SelectValidation("pdbooks");

        int pbID = Integer.parseInt(txt_id.getText());
        String comment = txt_msg.getText();
        String bkname = txt_bkname.getText();
        String msg = "From Super User :\n";

        String upload_username = txt_uploader.getText();
        int req_points = Integer.parseInt(txt_req.getText());

        String granted_pts_txt = txt_gra.getText();
        int granted_points = 0;
        if (granted_pts_txt.length() > 0) {
            granted_points = Integer.parseInt(granted_pts_txt);
        }

        String read_pts_txt = txt_read_pts.getText();
        int reading_points = 0;
        if (read_pts_txt.length() > 0) {
            reading_points = Integer.parseInt(read_pts_txt);
        }

        //if no valid granted point , if no reading point:
        if (granted_points > req_points || granted_points == 0 || granted_pts_txt.length() == 0
                || read_pts_txt.length() == 0 || reading_points == 0) {
            JOptionPane.showMessageDialog(null, "Please make sure both enter positive granted points lower than requested points " + txt_req.getText()
                    + ". \n and positive reading points for  the pending decision.");
            return;
        }

        // book is pending because of lower award points
        if (granted_points > 0 && granted_points < req_points) {
            msg += comment;
            msg += "\nYour book [" + bkname + "]  will be approved if you accept  " + granted_points + "  award points.";

            try {
                //1.update granted points and reading points to pending book table

                String sql0 = "UPDATE PendingBook SET granted_points=? ,reading_points=? WHERE pbID= ?";
                pst = conn.prepareStatement(sql0);
                pst.setInt(1, granted_points);
                pst.setInt(2, reading_points);
                pst.setInt(3, pbID);
                pst.execute();

                //2. send message to uploader
                SendMessage(username, upload_username, msg);

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
                return;
            }

        }
        //dialog message
        JOptionPane.showMessageDialog(null, "This book is pending, waiting for uploader's decision.");
        clean_pdbooks_row_detail();
        pendingbooks();
    }//GEN-LAST:event_PendingActionPerformed

    private void RegularUseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RegularUseActionPerformed
        // TODO add your handling code here:

        new tabpannedUserPage("Super User", firstname, username).setVisible(true);
        cancel();

    }//GEN-LAST:event_RegularUseActionPerformed

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
                new SuperUserFrame(username, firstname).setVisible(true);

            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Approve;
    private javax.swing.JButton ComNotIssue;
    private javax.swing.JButton ComRegIssue;
    private javax.swing.JButton ComSeriousIssue;
    private javax.swing.JButton ComViewBook;
    private javax.swing.JScrollPane ComplaintPane;
    private javax.swing.JButton Decline;
    private javax.swing.JButton Logout;
    private javax.swing.JTabbedPane NotificationCenter;
    private javax.swing.JButton Pending;
    private javax.swing.JButton PendingViewBook;
    private javax.swing.JPanel RareReadPane;
    private javax.swing.JButton RefreshCom;
    private javax.swing.JButton RefreshLowRate;
    private javax.swing.JButton RefreshPbooks;
    private javax.swing.JButton RefreshRareRead;
    private javax.swing.JButton RegularUse;
    private javax.swing.JButton RemoveLowRate;
    private javax.swing.JButton RemoveRareRead;
    private javax.swing.JButton RestCom;
    private javax.swing.JButton RestPbooks;
    private javax.swing.JTabbedPane SuperUser;
    private javax.persistence.EntityManager UsersRegistrationPUEntityManager;
    private javax.swing.JTextField book_com_time;
    private javax.swing.JTextField book_removed;
    private java.util.List<ebooksharing1.Bookinfo> bookinfoList1;
    private javax.persistence.Query bookinfoQuery1;
    private javax.swing.JTextField com_auth;
    private javax.swing.JTextField com_bID;
    private javax.swing.JTextField com_bookname;
    private javax.swing.JLabel com_cover;
    private javax.swing.JTextField com_id;
    private javax.swing.JTextArea com_issue;
    private javax.swing.JTextArea com_msg;
    private javax.swing.JTextField com_rd_cnt;
    private javax.swing.JTextField com_rd_total;
    private javax.swing.JTextField com_rt_cnt;
    private javax.swing.JTextField com_rt_total;
    private javax.swing.JTextField com_uploader;
    private javax.swing.JTextField com_user;
    private javax.swing.JPanel complaintboardPane;
    private javax.swing.JTable complaints;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JLabel l_cate;
    private javax.swing.JLabel l_com_bID;
    private javax.swing.JLabel l_com_bID1;
    private javax.swing.JLabel l_com_bname;
    private javax.swing.JLabel l_com_bname1;
    private javax.swing.JLabel l_com_id;
    private javax.swing.JLabel l_com_id1;
    private javax.swing.JLabel l_com_id4;
    private javax.swing.JLabel l_com_id6;
    private javax.swing.JLabel l_com_txt;
    private javax.swing.JLabel l_com_txt1;
    private javax.swing.JLabel l_com_uploader;
    private javax.swing.JLabel l_com_uploader1;
    private javax.swing.JLabel l_com_uploader2;
    private javax.swing.JLabel l_com_uploader3;
    private javax.swing.JLabel l_com_uploader4;
    private javax.swing.JLabel l_com_uploader5;
    private javax.swing.JLabel l_com_uploader6;
    private javax.swing.JLabel l_com_user;
    private javax.swing.JLabel l_com_user1;
    private javax.swing.JLabel l_com_user2;
    private javax.swing.JLabel l_com_user4;
    private javax.swing.JLabel l_com_user5;
    private javax.swing.JLabel l_pbid;
    private javax.swing.JTextField last_date_read;
    private javax.swing.JLabel lautho;
    private javax.swing.JLabel lbkname;
    private javax.swing.JLabel lgrant;
    private javax.swing.JLabel lgrant1;
    private javax.swing.JLabel lmsg;
    private javax.swing.JTable lowRateBook;
    private javax.swing.JPanel lowratePane;
    private javax.swing.JTextField lowrate_bookid;
    private javax.swing.JLabel lowrate_bookid_label;
    private javax.swing.JLabel lowrate_bookid_label1;
    private javax.swing.JTextField lowrate_bookname;
    private javax.swing.JTextField lowrate_pts;
    private javax.swing.JTextField lowrate_removed;
    private javax.swing.JTextArea lowrate_summary;
    private javax.swing.JTextField lowrate_uploader;
    private javax.swing.JLabel lowrate_uploader_label;
    private javax.swing.JTextField lowrating;
    private javax.swing.JTextField lowrating_counts;
    private javax.swing.JLabel lreq;
    private javax.swing.JLabel lsumm;
    private javax.swing.JLabel luploader;
    private javax.swing.JTable pdbooks;
    private javax.swing.JScrollPane pdbooks_ScrollPane;
    private javax.swing.JLabel pending_cover;
    private javax.swing.JPanel pendingbooksPane;
    private javax.swing.JTable rareReadBook;
    private javax.swing.JTextField rareread_bookid;
    private javax.swing.JTextField rareread_bookname;
    private javax.swing.JLabel rareread_bookname_label;
    private javax.swing.JTextField rareread_cnts;
    private javax.swing.JTextField rareread_duration;
    private javax.swing.JTextField rareread_pts;
    private javax.swing.JLabel rareread_pts_label1;
    private javax.swing.JLabel rareread_pts_label2;
    private javax.swing.JLabel rareread_pts_label3;
    private javax.swing.JLabel rareread_pts_sum;
    private javax.swing.JLabel rareread_pts_sum1;
    private javax.swing.JTextArea rareread_summary;
    private javax.swing.JTextField rareread_uploader;
    private javax.swing.JTextField reward_pts;
    private javax.swing.JTextPane txt_auth;
    private javax.swing.JTextPane txt_bkname;
    private javax.swing.JTextPane txt_cate;
    private javax.swing.JEditorPane txt_gra;
    private javax.swing.JTextPane txt_id;
    private javax.swing.JTextArea txt_msg;
    private javax.swing.JEditorPane txt_read_pts;
    private javax.swing.JTextPane txt_req;
    private javax.swing.JTextArea txt_summ;
    private javax.swing.JTextPane txt_uploader;
    private javax.swing.JPanel unpopularbooksPane;
    private javax.swing.JLabel welcome;
    // End of variables declaration//GEN-END:variables

    public void cancel() {
        WindowEvent winClosing = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(winClosing);
    }

}
