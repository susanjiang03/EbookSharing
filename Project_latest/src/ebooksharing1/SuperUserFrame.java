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
import java.awt.HeadlessException;
import java.sql.*;
import java.awt.event.*;
import javax.swing.*;
import net.proteanit.sql.DbUtils;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
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
        SuperUserFrame.dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SuperUserFrame.cal = Calendar.getInstance();
        String date_now = dateFormat.format(cal.getTime());

        SuperUserFrame.username = username;
        SuperUserFrame.firstname = firstname;
        initComponents();
        welcome.setText("Welcome,Superuser  " + username + " :" + firstname + ".     Today: " + date_now);

        pendingbooks();
        complaints();
        rareReadBook();
        lowRateBook();
        populateContributedTable();
        populatependingContributedTable();
        populateSentMessageTable();
        populateReceiveMessageTable();
        populateDropDownCombo();
        populateUserDetails();
        populateInvitationSentTable();
        populateInvitationReceivedTable();
        populateReadBookTable();

    }

    public SuperUserFrame() {
        initComponents();
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
    }

    //view the detail of each row in pending book table to the left pane
    private void pdbooks_row_detail() {

        clean_pdbooks_row_detail();

        try {
            int row = pdbooks.getSelectedRow();
            int Table_click = (int) pdbooks.getModel().getValueAt(row, 0);
            String sql = "select pbID,bookname,author,summary,category,uploader,request_points from pendingbook where pbID= ?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, Table_click);
            rs = pst.executeQuery();

            while (rs.next()) {

                txt_id.setText(rs.getString("pbID"));

                txt_uploader.setText(rs.getString("uploader"));

                txt_bkname.setText(rs.getString("bookname"));

                txt_auth.setText(rs.getString("author"));

                txt_summ.setText(rs.getString("summary"));

                txt_cate.setText(rs.getString("category"));

                txt_req.setText(rs.getString("request_points"));

                txt_gra.setText("0");

                txt_read_pts.setText("0");

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
        com_bname.setText("");
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
    }

    // view the detail of each row in complaint table to the left
    private void complaints_row_detail() {

        //get data from complaint table
        try {
            int row = complaints.getSelectedRow();
            int Table_click = (int) complaints.getModel().getValueAt(row, 0);

            String sql1 = "SELECT cID,username,bookID,complaint_text FROM Complaint  WHERE cID= ?";

            pst = conn.prepareStatement(sql1);
            pst.setInt(1, Table_click);

            rs = pst.executeQuery();

            while (rs.next()) {
                com_id.setText(rs.getString("cID"));
                com_bID.setText(rs.getString("bookID"));
                com_issue.setText(rs.getString("complaint_text"));
                com_user.setText(rs.getString("username"));

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
                com_bname.setText(rs.getString("bookname"));
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
    }

    //display the detail of the bookinfo, including the uploader and how many books have been removed
    private void lowrate_row_detail() {

        try {
            //get and set data from bookinfo 
            String sql1 = "SELECT BookID,bookname,rating,rating_counts,reward_points,uploader FROM bookInfo WHERE BookID= ?";
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
        JOptionPane.showMessageDialog(null, "Remove all records related to book " + bookID + "  from Table " + Table + ".");
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
            String sql = "UPDATE userInfo set in_blacklist=True WHERE username=? ";
            pst = conn.prepareStatement(sql);
            pst.setString(1, user);
            pst.execute();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Blacklist() error:\n" + e);
            return;
        }

        JOptionPane.showMessageDialog(null, "The user " + user + " has been rejected from the system and put in blacklist.");
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
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        UsersRegistrationPUEntityManager = java.beans.Beans.isDesignTime() ? null : javax.persistence.Persistence.createEntityManagerFactory("UsersRegistrationPU").createEntityManager();
        bookinfoQuery1 = java.beans.Beans.isDesignTime() ? null : UsersRegistrationPUEntityManager.createQuery("SELECT b FROM Bookinfo b");
        bookinfoList1 = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : bookinfoQuery1.getResultList();
        Logout = new javax.swing.JButton();
        SuperUser = new javax.swing.JTabbedPane();
        NotificationCenter = new javax.swing.JTabbedPane();
        pendingbooksPane = new javax.swing.JPanel();
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
        RestPbooks = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        txt_msg = new javax.swing.JTextArea();
        viewbook = new javax.swing.JButton();
        l_cate = new javax.swing.JLabel();
        txt_cate = new javax.swing.JTextPane();
        RefreshPbooks = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
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
        ComRegIssue = new javax.swing.JButton();
        ComNotIssue = new javax.swing.JButton();
        ComSeriousIssue = new javax.swing.JButton();
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
        ViewBook = new javax.swing.JButton();
        book_removed = new javax.swing.JTextField();
        RestCom = new javax.swing.JButton();
        book_com_time = new javax.swing.JTextField();
        l_com_uploader3 = new javax.swing.JLabel();
        l_com_uploader2 = new javax.swing.JLabel();
        com_rd_cnt = new javax.swing.JTextField();
        l_com_uploader4 = new javax.swing.JLabel();
        com_rt_cnt = new javax.swing.JTextField();
        com_rt_total = new javax.swing.JTextField();
        l_com_uploader5 = new javax.swing.JLabel();
        com_rd_total = new javax.swing.JTextField();
        l_com_uploader6 = new javax.swing.JLabel();
        l_com_user = new javax.swing.JLabel();
        com_user = new javax.swing.JTextField();
        com_id = new javax.swing.JTextField();
        l_com_id = new javax.swing.JLabel();
        reward_pts = new javax.swing.JTextField();
        l_com_bID1 = new javax.swing.JLabel();
        RefreshCom = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
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
        rareread_pts_label = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        RefreshRareRead = new javax.swing.JButton();
        rareread_bookname = new javax.swing.JTextField();
        rareread_bookname_label = new javax.swing.JLabel();
        rareread_cnts = new javax.swing.JTextField();
        rareread_pts_label1 = new javax.swing.JLabel();
        rareread_duration = new javax.swing.JTextField();
        rareread_pts_label2 = new javax.swing.JLabel();
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
        SharedBooksTab = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        BooksListLabel = new javax.swing.JLabel();
        jScrollPane9 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        RateSelectedBookButton = new javax.swing.JButton();
        ReadSelectedBookButton = new javax.swing.JButton();
        jScrollPane10 = new javax.swing.JScrollPane();
        SummaryTextArea = new javax.swing.JTextArea();
        InviteButton2 = new javax.swing.JButton();
        jScrollPane11 = new javax.swing.JScrollPane();
        ReviewDisplayTable = new javax.swing.JTable();
        UserNameComboBox = new javax.swing.JComboBox();
        jLabel14 = new javax.swing.JLabel();
        sharingtimeTextField = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        Complaint = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        BookSubmitButton = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        BookAuthorTextField = new javax.swing.JTextField();
        BookNameTextField = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        CancelButton = new javax.swing.JButton();
        BookBrowseButton = new javax.swing.JButton();
        BrowseCoverpageButton = new javax.swing.JButton();
        UploadcoverPageLabel = new javax.swing.JLabel();
        UploadBookLabel = new javax.swing.JLabel();
        jScrollPane12 = new javax.swing.JScrollPane();
        BookSummaryTextArea = new javax.swing.JTextArea();
        jLabel9 = new javax.swing.JLabel();
        pointsTextField = new javax.swing.JTextField();
        coverpagepathprint = new javax.swing.JLabel();
        coverpagepathprintLabel = new javax.swing.JLabel();
        bookpathprintlabel = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jScrollPane13 = new javax.swing.JScrollPane();
        SentMessageTable = new javax.swing.JTable();
        jScrollPane14 = new javax.swing.JScrollPane();
        ReceivedMessage = new javax.swing.JTable();
        jScrollPane15 = new javax.swing.JScrollPane();
        MessageTextArea = new javax.swing.JTextArea();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        MessageSendButton = new javax.swing.JButton();
        ClearMessage = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox();
        jPanel5 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jScrollPane16 = new javax.swing.JScrollPane();
        InvitationSentTable = new javax.swing.JTable();
        jScrollPane17 = new javax.swing.JScrollPane();
        InvitationReceivedTable = new javax.swing.JTable();
        AcceptInvitationButton = new javax.swing.JButton();
        DeclineInvitationButton = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        FirstNameLabel = new javax.swing.JLabel();
        LastNameLabel = new javax.swing.JLabel();
        FNameTextField = new javax.swing.JTextField();
        LastNameTextField = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jScrollPane18 = new javax.swing.JScrollPane();
        BookContributedByUserTable = new javax.swing.JTable();
        EmailTextField = new javax.swing.JTextField();
        PointEarnedTextField = new javax.swing.JTextField();
        BookGotRemovedTextfield = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jScrollPane19 = new javax.swing.JScrollPane();
        BookPendingUserTable = new javax.swing.JTable();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        UserTypeTextField = new javax.swing.JTextField();
        jScrollPane20 = new javax.swing.JScrollPane();
        ReadBookTable = new javax.swing.JTable();
        ReadBooksLabel = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
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
            public void keyReleased(java.awt.event.KeyEvent evt) {
                pdbooksKeyReleased(evt);
            }
        });
        pdbooks_ScrollPane.setViewportView(pdbooks);
        if (pdbooks.getColumnModel().getColumnCount() > 0) {
            pdbooks.getColumnModel().getColumn(0).setPreferredWidth(5);
        }

        RestPbooks.setText("Reset");
        RestPbooks.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RestPbooksActionPerformed(evt);
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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(pendingbooksPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(viewbook, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pendingbooksPaneLayout.createSequentialGroup()
                        .addGroup(pendingbooksPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lautho)
                            .addComponent(lbkname)
                            .addGroup(pendingbooksPaneLayout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addComponent(l_pbid))
                            .addGroup(pendingbooksPaneLayout.createSequentialGroup()
                                .addComponent(lsumm)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(46, 46, 46)
                                .addGroup(pendingbooksPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pendingbooksPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pendingbooksPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lgrant)
                                            .addComponent(txt_gra, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pendingbooksPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lreq)
                                            .addGroup(pendingbooksPaneLayout.createSequentialGroup()
                                                .addGap(6, 6, 6)
                                                .addComponent(txt_req, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pendingbooksPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(lgrant1, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pendingbooksPaneLayout.createSequentialGroup()
                                                .addGap(3, 3, 3)
                                                .addComponent(txt_read_pts, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addGroup(pendingbooksPaneLayout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addGroup(pendingbooksPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(RestPbooks, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(Decline, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(Approve, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(Pending, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addGroup(pendingbooksPaneLayout.createSequentialGroup()
                                .addComponent(lmsg)
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 323, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addComponent(pdbooks_ScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 689, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(369, 369, 369))
        );

        pendingbooksPaneLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {Approve, Decline, Pending, RestPbooks});

        pendingbooksPaneLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {txt_gra, txt_read_pts, txt_req});

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
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
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
                        .addComponent(txt_read_pts, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(Approve)
                        .addGap(9, 9, 9)
                        .addComponent(Pending)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Decline)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(RestPbooks))
                    .addComponent(pdbooks_ScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 595, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(pendingbooksPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lmsg, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pendingbooksPaneLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {Approve, Decline, Pending, RestPbooks});

        pendingbooksPaneLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {txt_gra, txt_read_pts, txt_req});

        NotificationCenter.addTab("Pending  Books", pendingbooksPane);

        complaintboardPane.setBackground(new java.awt.Color(51, 153, 0));

        com_bID.setEditable(false);

        l_com_bname.setText("Book Name");

        l_com_txt.setText("Message");

        l_com_uploader.setText("Book Uploader");

        l_com_bID.setText("Book ID");

        com_uploader.setEditable(false);

        com_bname.setEditable(false);

        com_issue.setEditable(false);
        com_issue.setColumns(20);
        com_issue.setLineWrap(true);
        com_issue.setRows(5);
        jScrollPane4.setViewportView(com_issue);

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

        ComSeriousIssue.setText("Comfirm As a Serious Issue");
        ComSeriousIssue.setMinimumSize(new java.awt.Dimension(160, 30));
        ComSeriousIssue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComSeriousIssueActionPerformed(evt);
            }
        });

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

        l_com_bname1.setText("Author");

        com_auth.setEditable(false);

        jScrollPane6.setViewportView(jTextPane2);

        l_com_uploader1.setText("Books Got  Removed:");

        l_com_txt1.setText("Issue");

        com_msg.setColumns(20);
        com_msg.setLineWrap(true);
        com_msg.setRows(5);
        jScrollPane7.setViewportView(com_msg);

        ViewBook.setText("View Book");
        ViewBook.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ViewBookActionPerformed(evt);
            }
        });

        book_removed.setEditable(false);

        RestCom.setText("Reset");
        RestCom.setPreferredSize(new java.awt.Dimension(160, 30));
        RestCom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RestComActionPerformed(evt);
            }
        });

        book_com_time.setEditable(false);

        l_com_uploader3.setText("Times Got Complainted:");

        l_com_uploader2.setText("Reading Count");

        com_rd_cnt.setEditable(false);

        l_com_uploader4.setText("Rating Count");

        com_rt_cnt.setEditable(false);

        com_rt_total.setEditable(false);

        l_com_uploader5.setText("Total Rating");

        com_rd_total.setEditable(false);

        l_com_uploader6.setText("Total  Duration");

        l_com_user.setText("Complainter");

        com_user.setEditable(false);

        com_id.setEditable(false);

        l_com_id.setText("CID");

        reward_pts.setEditable(false);

        l_com_bID1.setText("Reward Points");

        RefreshCom.setFont(new java.awt.Font("Lucida Grande", 0, 24)); // NOI18N
        RefreshCom.setText("Complaints");
        RefreshCom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RefreshComActionPerformed(evt);
            }
        });

        jLabel2.setText("Click to Refresh ");

        javax.swing.GroupLayout complaintboardPaneLayout = new javax.swing.GroupLayout(complaintboardPane);
        complaintboardPane.setLayout(complaintboardPaneLayout);
        complaintboardPaneLayout.setHorizontalGroup(
            complaintboardPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(complaintboardPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(complaintboardPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(complaintboardPaneLayout.createSequentialGroup()
                        .addGroup(complaintboardPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(complaintboardPaneLayout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addGroup(complaintboardPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, complaintboardPaneLayout.createSequentialGroup()
                                        .addComponent(ComSeriousIssue, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(RestCom, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(complaintboardPaneLayout.createSequentialGroup()
                                        .addComponent(l_com_txt)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jScrollPane7))
                                    .addGroup(complaintboardPaneLayout.createSequentialGroup()
                                        .addComponent(ComNotIssue, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(ComRegIssue, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(complaintboardPaneLayout.createSequentialGroup()
                                .addComponent(l_com_txt1, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 439, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(complaintboardPaneLayout.createSequentialGroup()
                        .addGroup(complaintboardPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(complaintboardPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, complaintboardPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(com_bname, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(complaintboardPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, complaintboardPaneLayout.createSequentialGroup()
                                            .addComponent(l_com_uploader)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(com_uploader, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(l_com_uploader1)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(book_removed, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, complaintboardPaneLayout.createSequentialGroup()
                                            .addComponent(l_com_bID, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(com_bID, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(l_com_uploader3)
                                            .addGap(2, 2, 2)
                                            .addComponent(book_com_time, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(l_com_bname, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(l_com_bname1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, complaintboardPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, complaintboardPaneLayout.createSequentialGroup()
                                                .addComponent(RefreshCom, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jLabel2))
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, complaintboardPaneLayout.createSequentialGroup()
                                                .addGap(13, 13, 13)
                                                .addComponent(l_com_id, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(com_id, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(l_com_user, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(com_user, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addComponent(com_auth, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, complaintboardPaneLayout.createSequentialGroup()
                                    .addComponent(l_com_bID1, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(reward_pts, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, complaintboardPaneLayout.createSequentialGroup()
                                .addGroup(complaintboardPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(complaintboardPaneLayout.createSequentialGroup()
                                        .addComponent(l_com_uploader2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(com_rd_cnt, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(complaintboardPaneLayout.createSequentialGroup()
                                        .addComponent(l_com_uploader4)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(com_rt_cnt)))
                                .addGap(18, 18, 18)
                                .addGroup(complaintboardPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(l_com_uploader5)
                                    .addComponent(l_com_uploader6))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(complaintboardPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(com_rd_total, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)
                                    .addComponent(com_rt_total))))
                        .addGroup(complaintboardPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(complaintboardPaneLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(ViewBook)
                                .addGap(53, 53, 53))
                            .addGroup(complaintboardPaneLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addComponent(ComplaintPane, javax.swing.GroupLayout.PREFERRED_SIZE, 778, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        complaintboardPaneLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {ComNotIssue, ComRegIssue, ComSeriousIssue, RestCom});

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
                        .addGroup(complaintboardPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(complaintboardPaneLayout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addGroup(complaintboardPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(com_id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(l_com_id, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(l_com_user, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(com_user, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(1, 1, 1)
                                .addGroup(complaintboardPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(l_com_uploader, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(com_uploader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(book_removed, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(l_com_uploader1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(complaintboardPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(complaintboardPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(l_com_bID, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(com_bID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(complaintboardPaneLayout.createSequentialGroup()
                                        .addGap(1, 1, 1)
                                        .addGroup(complaintboardPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(book_com_time, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(l_com_uploader3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                                .addGroup(complaintboardPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(l_com_bID1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(reward_pts, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(complaintboardPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(com_bname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(l_com_bname, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(complaintboardPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(com_auth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(l_com_bname1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(complaintboardPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(l_com_uploader2, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                                    .addGroup(complaintboardPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(l_com_uploader6, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(com_rd_total, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(com_rd_cnt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(complaintboardPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(l_com_uploader4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(com_rt_cnt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(l_com_uploader5, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(com_rt_total, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(complaintboardPaneLayout.createSequentialGroup()
                                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(ViewBook)))
                        .addGap(14, 14, 14)
                        .addGroup(complaintboardPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(l_com_txt1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(complaintboardPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(l_com_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(complaintboardPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ComNotIssue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ComRegIssue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(ComplaintPane))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(complaintboardPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(RestCom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ComSeriousIssue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15))
        );

        NotificationCenter.addTab("Complaint Board", complaintboardPane);

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

        rareread_pts_label.setText("Reading Points");

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

        javax.swing.GroupLayout RareReadPaneLayout = new javax.swing.GroupLayout(RareReadPane);
        RareReadPane.setLayout(RareReadPaneLayout);
        RareReadPaneLayout.setHorizontalGroup(
            RareReadPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(RareReadPaneLayout.createSequentialGroup()
                .addGroup(RareReadPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(RareReadPaneLayout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 474, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(RareReadPaneLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 587, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(RareReadPaneLayout.createSequentialGroup()
                        .addGroup(RareReadPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(RareReadPaneLayout.createSequentialGroup()
                                .addGap(62, 62, 62)
                                .addComponent(rareread_bookid, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(RareReadPaneLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(l_com_id1, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rareread_bookname_label, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rareread_bookname, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(RareReadPaneLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(RemoveRareRead)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(RefreshRareRead))
                    .addGroup(RareReadPaneLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(RareReadPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(rareread_pts_label, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                            .addComponent(l_com_user2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(RareReadPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(RareReadPaneLayout.createSequentialGroup()
                                .addComponent(last_date_read, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(34, 34, 34)
                                .addComponent(l_com_user1, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(2, 2, 2)
                                .addComponent(rareread_uploader, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(RareReadPaneLayout.createSequentialGroup()
                                .addComponent(rareread_pts, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rareread_pts_label1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rareread_cnts, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rareread_pts_label2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rareread_duration)))))
                .addContainerGap(67, Short.MAX_VALUE))
        );
        RareReadPaneLayout.setVerticalGroup(
            RareReadPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(RareReadPaneLayout.createSequentialGroup()
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 428, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
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
                    .addComponent(rareread_pts_label, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rareread_cnts, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rareread_pts_label1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rareread_pts_label2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rareread_duration, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(RareReadPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(RemoveRareRead)
                    .addComponent(RefreshRareRead))
                .addContainerGap(118, Short.MAX_VALUE))
        );

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
                        .addComponent(lowrate_bookname, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(58, Short.MAX_VALUE))
        );
        lowratePaneLayout.setVerticalGroup(
            lowratePaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(lowratePaneLayout.createSequentialGroup()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 426, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(lowratePaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(RemoveLowRate)
                    .addComponent(RefreshLowRate))
                .addContainerGap())
        );

        javax.swing.GroupLayout unpopularbooksPaneLayout = new javax.swing.GroupLayout(unpopularbooksPane);
        unpopularbooksPane.setLayout(unpopularbooksPaneLayout);
        unpopularbooksPaneLayout.setHorizontalGroup(
            unpopularbooksPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, unpopularbooksPaneLayout.createSequentialGroup()
                .addComponent(RareReadPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lowratePane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(254, 254, 254))
        );
        unpopularbooksPaneLayout.setVerticalGroup(
            unpopularbooksPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(unpopularbooksPaneLayout.createSequentialGroup()
                .addGroup(unpopularbooksPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(unpopularbooksPaneLayout.createSequentialGroup()
                        .addComponent(lowratePane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 6, Short.MAX_VALUE))
                    .addComponent(RareReadPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        NotificationCenter.addTab("Unpopular Books", unpopularbooksPane);

        SuperUser.addTab("Notification Center", NotificationCenter);

        jPanel1.setBackground(new java.awt.Color(51, 153, 0));
        jPanel1.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(153, 51, 0)));
        jPanel1.setPreferredSize(new java.awt.Dimension(1202, 635));

        BooksListLabel.setFont(new java.awt.Font("Lucida Grande", 1, 24)); // NOI18N
        BooksListLabel.setText("List of books ");

        jTable1.setRowHeight(200);

        org.jdesktop.swingbinding.JTableBinding jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, bookinfoList1, jTable1);
        org.jdesktop.swingbinding.JTableBinding.ColumnBinding columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${bookid}"));
        columnBinding.setColumnName("Bookid");
        columnBinding.setColumnClass(Integer.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${image}"));
        columnBinding.setColumnName("Cover");
        columnBinding.setColumnClass(javax.swing.ImageIcon.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${bookname}"));
        columnBinding.setColumnName("Bookname");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${author}"));
        columnBinding.setColumnName("Author");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${uploader}"));
        columnBinding.setColumnName("Uploader");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${rewardPoints}"));
        columnBinding.setColumnName("Reward Points");
        columnBinding.setColumnClass(Short.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${readingPoints}"));
        columnBinding.setColumnName("Reading Points");
        columnBinding.setColumnClass(Short.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${readingCounts}"));
        columnBinding.setColumnName("Reading Counts");
        columnBinding.setColumnClass(Short.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${lastDateRead}"));
        columnBinding.setColumnName("Last Date Read");
        columnBinding.setColumnClass(java.util.Date.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${readingTotalDuration}"));
        columnBinding.setColumnName("Reading Total Duration");
        columnBinding.setColumnClass(java.math.BigInteger.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${rating}"));
        columnBinding.setColumnName("Rating");
        columnBinding.setColumnClass(Double.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${ratingCounts}"));
        columnBinding.setColumnName("Rating Counts");
        columnBinding.setColumnClass(Short.class);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jTable1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTable1KeyPressed(evt);
            }
        });
        jScrollPane9.setViewportView(jTable1);

        jLabel12.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        jLabel12.setText("Review of the selected book");

        jLabel13.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        jLabel13.setText("Summary");

        RateSelectedBookButton.setText("Rate/Review Book");
        RateSelectedBookButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RateSelectedBookButtonActionPerformed(evt);
            }
        });

        ReadSelectedBookButton.setText("Read selected Book");
        ReadSelectedBookButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ReadSelectedBookButtonActionPerformed(evt);
            }
        });

        SummaryTextArea.setColumns(20);
        SummaryTextArea.setRows(5);
        jScrollPane10.setViewportView(SummaryTextArea);

        InviteButton2.setText("Invite to read book");
        InviteButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                InviteButton2ActionPerformed(evt);
            }
        });

        ReviewDisplayTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane11.setViewportView(ReviewDisplayTable);

        UserNameComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel14.setText("Share Time");

        jLabel21.setText("with User");

        Complaint.setText("Complaint");
        Complaint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComplaintActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 889, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(Complaint)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel14)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(sharingtimeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(51, 51, 51)
                                .addComponent(jLabel21)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(UserNameComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(33, 33, 33)
                                .addComponent(InviteButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 207, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13)
                            .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ReadSelectedBookButton)
                            .addComponent(RateSelectedBookButton, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(34, 34, 34))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(BooksListLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 1102, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BooksListLabel)
                    .addComponent(jLabel13))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(ReadSelectedBookButton))
                    .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(RateSelectedBookButton)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(InviteButton2)
                        .addComponent(UserNameComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel14)
                        .addComponent(sharingtimeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel21)
                        .addComponent(Complaint)))
                .addGap(26, 26, 26)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(91, Short.MAX_VALUE))
        );

        SharedBooksTab.addTab("Browse Book", jPanel1);

        jPanel2.setBackground(new java.awt.Color(51, 153, 0));
        jPanel2.setToolTipText("");

        jLabel1.setText("Book Name");

        BookSubmitButton.setText("Submit");
        BookSubmitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BookSubmitButtonActionPerformed(evt);
            }
        });

        jLabel6.setText("Book Author");

        jLabel7.setText("Book Summary");

        jLabel8.setFont(new java.awt.Font("Lucida Grande", 1, 24)); // NOI18N
        jLabel8.setText("Please enter the book details");

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

        UploadcoverPageLabel.setText("Upload CoverPage");

        UploadBookLabel.setText("Upload Book");

        BookSummaryTextArea.setColumns(20);
        BookSummaryTextArea.setRows(5);
        jScrollPane12.setViewportView(BookSummaryTextArea);

        jLabel9.setText("Points deserve");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(1036, Short.MAX_VALUE)
                .addComponent(coverpagepathprint)
                .addGap(316, 316, 316))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(351, 351, 351)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(jLabel9)
                            .addComponent(UploadcoverPageLabel)
                            .addComponent(UploadBookLabel))
                        .addGap(88, 88, 88)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(BrowseCoverpageButton)
                                    .addComponent(BookBrowseButton))
                                .addGap(26, 26, 26)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(bookpathprintlabel, javax.swing.GroupLayout.PREFERRED_SIZE, 383, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(coverpagepathprintLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 436, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(BookAuthorTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(BookNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(pointsTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(343, 343, 343)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 405, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(433, 433, 433)
                        .addComponent(CancelButton)
                        .addGap(18, 18, 18)
                        .addComponent(BookSubmitButton)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(BookNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(BookAuthorTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(29, 29, 29)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(pointsTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel9)))
                            .addComponent(jLabel7))
                        .addGap(42, 42, 42)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(BrowseCoverpageButton)
                            .addComponent(coverpagepathprintLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(UploadcoverPageLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(BookBrowseButton)
                        .addComponent(UploadBookLabel))
                    .addComponent(bookpathprintlabel, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(42, 42, 42)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BookSubmitButton)
                    .addComponent(CancelButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(coverpagepathprint)
                .addGap(218, 218, 218))
        );

        SharedBooksTab.addTab("Book Upload", jPanel2);

        jPanel3.setBackground(new java.awt.Color(51, 153, 0));

        jLabel10.setFont(new java.awt.Font("Lucida Grande", 1, 24)); // NOI18N
        jLabel10.setText("Your messages ");

        jLabel17.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        jLabel17.setText("Sent Messages");

        jLabel18.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        jLabel18.setText("Message Inbox");

        SentMessageTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Receiver", "Message"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        SentMessageTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                SentMessageTablesentmessageTableRowMouseclick(evt);
            }
        });
        jScrollPane13.setViewportView(SentMessageTable);

        ReceivedMessage.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Sender", "Message"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        ReceivedMessage.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ReceivedMessageInboxTableRowMouseClicked(evt);
            }
        });
        jScrollPane14.setViewportView(ReceivedMessage);

        MessageTextArea.setColumns(20);
        MessageTextArea.setRows(5);
        jScrollPane15.setViewportView(MessageTextArea);

        jLabel19.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        jLabel19.setText("Write message");

        jLabel20.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        jLabel20.setText("To");

        MessageSendButton.setText("Send");
        MessageSendButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MessageSendButtonActionPerformed(evt);
            }
        });

        ClearMessage.setText("Clear Message Area");
        ClearMessage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ClearMessageActionPerformed(evt);
            }
        });

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jScrollPane13, javax.swing.GroupLayout.DEFAULT_SIZE, 523, Short.MAX_VALUE)
                                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 368, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jScrollPane14))
                            .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 151, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel19)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel20)
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                            .addComponent(ClearMessage)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(MessageSendButton, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(jScrollPane15, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 515, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 119, Short.MAX_VALUE))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addGap(0, 1111, Short.MAX_VALUE))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(94, 94, 94)
                .addComponent(jLabel19)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane15, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ClearMessage)
                    .addComponent(MessageSendButton))
                .addGap(170, 170, 170))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jLabel10)
                .addGap(19, 19, 19)
                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane13, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane14, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        SharedBooksTab.addTab("Messages", jPanel3);

        jPanel5.setBackground(new java.awt.Color(51, 153, 0));

        jLabel23.setFont(new java.awt.Font("Lucida Grande", 1, 24)); // NOI18N
        jLabel23.setText("Invitation Received ");

        jLabel24.setFont(new java.awt.Font("Lucida Grande", 1, 24)); // NOI18N
        jLabel24.setText("Invitation Sent");

        InvitationSentTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "You Invited", "Shared Points", "Shared_status"
            }
        ));
        jScrollPane16.setViewportView(InvitationSentTable);

        InvitationReceivedTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Invited By", "Shared Points", "Shared_Status"
            }
        ));
        jScrollPane17.setViewportView(InvitationReceivedTable);

        AcceptInvitationButton.setText("Accept Invitation");
        AcceptInvitationButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AcceptInvitationButtonActionPerformed(evt);
            }
        });

        DeclineInvitationButton.setText("Decline Invitation");
        DeclineInvitationButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeclineInvitationButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(68, 68, 68)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(AcceptInvitationButton)
                        .addGap(51, 51, 51)
                        .addComponent(DeclineInvitationButton)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel23)
                            .addComponent(jScrollPane17, javax.swing.GroupLayout.PREFERRED_SIZE, 496, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 148, Short.MAX_VALUE)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane16, javax.swing.GroupLayout.PREFERRED_SIZE, 486, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel24))
                        .addContainerGap(154, Short.MAX_VALUE))))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(104, 104, 104)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(jLabel24))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane16, javax.swing.GroupLayout.DEFAULT_SIZE, 264, Short.MAX_VALUE)
                    .addComponent(jScrollPane17, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(54, 54, 54)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AcceptInvitationButton)
                    .addComponent(DeclineInvitationButton))
                .addContainerGap(144, Short.MAX_VALUE))
        );

        SharedBooksTab.addTab("Invitations", jPanel5);

        jPanel4.setBackground(new java.awt.Color(51, 153, 0));

        FirstNameLabel.setText("First Name:");

        LastNameLabel.setText("Last Name:");

        jLabel11.setText("Email");

        jLabel15.setText("Point Earned");

        jLabel16.setText("# of Books Removed");

        jLabel22.setFont(new java.awt.Font("Lucida Grande", 1, 24)); // NOI18N
        jLabel22.setText("The list of books you contributed");

        BookContributedByUserTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "BookName", "Author", "Ratings"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane18.setViewportView(BookContributedByUserTable);

        jLabel25.setFont(new java.awt.Font("Lucida Grande", 1, 36)); // NOI18N
        jLabel25.setText("Your Details");

        jLabel26.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        jLabel26.setText("Approved books in selves");

        BookPendingUserTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "BookName", "Author", "RequestedPoints"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane19.setViewportView(BookPendingUserTable);

        jLabel27.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        jLabel27.setText("Pending Books");

        jLabel28.setText("User Type");

        ReadBookTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Book Id", "Time in sec"
            }
        ));
        jScrollPane20.setViewportView(ReadBookTable);

        ReadBooksLabel.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        ReadBooksLabel.setText("List of Books You Read");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(66, 66, 66)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel11)
                            .addComponent(LastNameLabel)
                            .addComponent(jLabel15)
                            .addComponent(jLabel16)
                            .addComponent(FirstNameLabel)
                            .addComponent(jLabel28))
                        .addGap(66, 66, 66)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(PointEarnedTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 306, Short.MAX_VALUE)
                            .addComponent(BookGotRemovedTextfield)
                            .addComponent(EmailTextField)
                            .addComponent(LastNameTextField)
                            .addComponent(FNameTextField)
                            .addComponent(UserTypeTextField)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(59, 59, 59)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel26)
                            .addComponent(jLabel22)
                            .addComponent(jScrollPane18, javax.swing.GroupLayout.PREFERRED_SIZE, 527, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(61, 61, 61)
                        .addComponent(jLabel25)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 137, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ReadBooksLabel)
                    .addComponent(jScrollPane20, javax.swing.GroupLayout.PREFERRED_SIZE, 472, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane19, javax.swing.GroupLayout.PREFERRED_SIZE, 489, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel27))
                .addContainerGap(140, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(ReadBooksLabel))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(FirstNameLabel)
                            .addComponent(FNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(LastNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(LastNameLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(EmailTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel15)
                            .addComponent(PointEarnedTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(BookGotRemovedTextfield, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(UserTypeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel28)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jScrollPane20, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addComponent(jLabel22)
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(jLabel27))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane19, javax.swing.GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE)
                    .addComponent(jScrollPane18, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(84, 84, 84))
        );

        SharedBooksTab.addTab("User Profile", jPanel4);

        jPanel6.setBackground(new java.awt.Color(51, 153, 0));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1352, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 643, Short.MAX_VALUE)
        );

        SharedBooksTab.addTab("SharedBooks", jPanel6);

        SuperUser.addTab("Reguar Use", SharedBooksTab);

        welcome.setFont(new java.awt.Font("Lucida Grande", 1, 24)); // NOI18N
        welcome.setText("Welcome, Super User ");

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
                .addComponent(SuperUser, javax.swing.GroupLayout.PREFERRED_SIZE, 735, Short.MAX_VALUE))
        );

        bindingGroup.bind();

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

    private void ViewBookActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ViewBookActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ViewBookActionPerformed

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
        clean_com_row_detail();
        complaints();
    }//GEN-LAST:event_ComSeriousIssueActionPerformed

    private void ComNotIssueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComNotIssueActionPerformed
        // TODO add your handling code here:
        SelectValidation("complaints");

        //1.send message to user who complaint this book.
        String msg = com_msg.getText();
        String bookname = com_bname.getText();
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
        String bookname = com_bname.getText();
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

    private void viewbookActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewbookActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_viewbookActionPerformed

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

        try {
            //====1. insert book to bookinfo
            String sql = "INSERT INTO BookInfo (uploader,bookname,author,summary,category,reward_points,reading_points)"
                    + "VALUES(?,?,?,?,?,?,?)";
            pst = conn.prepareStatement(sql);

            String upload_username = txt_uploader.getText();
            pst.setString(1, upload_username);

            String bkname = txt_bkname.getText();
            pst.setString(2, bkname);

            pst.setString(3, txt_auth.getText());
            pst.setString(4, txt_summ.getText());

            pst.setString(5, txt_cate.getText());
            int award_pts = Integer.parseInt(txt_req.getText());
            pst.setInt(6, award_pts);

            int read_pts = Integer.parseInt(read_pts_txt);
            pst.setInt(7, read_pts);
            pst.execute();

            //2.===delete from pending books`
            int pbID = Integer.parseInt(txt_id.getText());
            DeleteRecord("pendingbook", "pbID", pbID);

            //3.====add points to the uploader's account;
            String sql3 = "UPDATE userInfo SET point_balance=point_balance+ ?  WHERE username=?";
            pst = conn.prepareStatement(sql3);
            pst.setInt(1, award_pts);
            pst.setString(2, upload_username);
            pst.execute();

            //4.====send message to the uploader;
            String comment = txt_msg.getText();
            String msg = "From Super User \n: You book   [" + bkname + "]  has been approved.\nYou are awaded   " + award_pts + "    points.\n" + " The reading points for this"
                    + " book is" + read_pts + ".";

            if (!comment.trim().equals("")) {
                msg += "\nComment: " + comment + ".";
            }

            SendMessage(username, upload_username, msg);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
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

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:

        displaySummary();
        populateReviewtable();
        //jTable1.setEnabled(false);
    }//GEN-LAST:event_jTable1MouseClicked

    private void populateReviewtable() {
        ResultSet rs = null;
        PreparedStatement pst = null;

        try {
            DbConnector dbc = new DbConnector();
            Connection conn = dbc.Connects();
            int row = jTable1.getSelectedRow();
            int rowNum = (int) jTable1.getModel().getValueAt(row, 0);
            String Sql = "Select bookid AS BookID, username AS Reviewer, review_text AS Reviews, rating AS Ratings FROM Review_rating WHERE bookid = ?";
            pst = conn.prepareStatement(Sql);
            pst.setInt(1, rowNum);
            rs = pst.executeQuery();
            ReviewDisplayTable.setModel(DbUtils.resultSetToTableModel(rs));
            ReviewDisplayTable.setEnabled(false);
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void displaySummary() {
        ResultSet rs = null;
        PreparedStatement pst = null;
        //SummaryTextArea.setText("");

        try {
            DbConnector dbc = new DbConnector();
            Connection conn = dbc.Connects();
            int row = jTable1.getSelectedRow();
            int rowNum = (int) jTable1.getModel().getValueAt(row, 0);
            String sql = "SELECT summary FROM BookInfo where BookInfo.bookid = ?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, rowNum);
            rs = pst.executeQuery();

            while (rs.next()) {

                SummaryTextArea.setText(rs.getString("summary"));
                SummaryTextArea.setEditable(false);
                SummaryTextArea.setLineWrap(true);

            }
            conn.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    private void jTable1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable1KeyPressed
        // TODO add your handling code here:
        displaySummary();
        populateReviewtable();
    }//GEN-LAST:event_jTable1KeyPressed

    private void RateSelectedBookButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RateSelectedBookButtonActionPerformed
        // TODO add your handling code here:

        /*
         public ImageIcon getImage(){
         return new ImageIcon(new ImageIcon(cover).getImage().getScaledInstance(100, 150, java.awt.Image.SCALE_SMOOTH));}
         */
        int row = jTable1.getSelectedRow();
        if (row != -1) {
            int bid = (int) jTable1.getModel().getValueAt(row, 0);
            int readduration = get_user_reading_duration();
            if (readduration > 0) {
                ReviewRateFrame bo = new ReviewRateFrame(bid, username);
                bo.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "You haven't read this book yet!", "warning", JOptionPane.WARNING_MESSAGE);
            }

        } else {
            JOptionPane.showMessageDialog(null, "No book selected");
        }
    }//GEN-LAST:event_RateSelectedBookButtonActionPerformed

    private int get_user_reading_duration() {
        ResultSet rs = null;
        PreparedStatement pst = null;
        int read_duration = 0;

        DbConnector dbc = new DbConnector();
        Connection conn = dbc.Connects();
        int row = jTable1.getSelectedRow();
        int rowNum = (int) jTable1.getModel().getValueAt(row, 0);

        String sql = "SELECT reading_duration FROM READINGHISTORY  WHERE ReadingHistory.Bookid = ? AND ReadingHistory.username = ?";
        try {
            pst = conn.prepareStatement(sql);
            pst.setInt(1, rowNum);
            pst.setString(2, username);
            rs = pst.executeQuery();
            while (rs.next()) {
                read_duration = rs.getInt("reading_duration");
            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(tabpannedUserPage.class.getName()).log(Level.SEVERE, null, ex);
        }
        return read_duration;

    }
    private void ReadSelectedBookButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ReadSelectedBookButtonActionPerformed
        // TODO add your handling code here:
        int row = jTable1.getSelectedRow();
        if (row != -1) {
            int bid = (int) jTable1.getModel().getValueAt(row, 0);

            try {
                //int users_total_point = get_points_available();
                passBookID();
                //            if (users_total_point > 0) {
                //                //passBookID();
                //                //BookOpened bo = new BookOpened(bid);
                //                //bo.setVisible(true);
                //            } else {
                //                JOptionPane.showMessageDialog(null, "Sorry You don't have enough points");
                //            }
            } catch (SQLException ex) {
                Logger.getLogger(tabpannedUserPage.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(tabpannedUserPage.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            JOptionPane.showMessageDialog(null, "No book selected");
        }
    }//GEN-LAST:event_ReadSelectedBookButtonActionPerformed

    private void passBookID() throws SQLException, IOException {
        int row = jTable1.getSelectedRow();
        if (row != -1) {
            int bid = (int) jTable1.getModel().getValueAt(row, 0);

            DbConnector dbc = new DbConnector();
            Connection conn = dbc.Connects();
            //int row = SentMessageTable.getSelectedRow();
            //int rowNum = (int) SentMessageTable.getModel().getValueAt(row, 0);
            String sql = "SELECT POINT_BALANCE FROM UserInfo  WHERE username = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, username);
            ResultSet rs = pst.executeQuery();
            //JFrame frame = new JFrame("Purchase Time for the Book");
            //String time = JOptionPane.showInputDialog(frame, "For how long would you rent the book? (in seconds)");
            int user_points = 0;
            int book_points = 0;

            while (rs.next()) {
                user_points = rs.getInt("POINT_BALANCE");
            }
            JFrame frame = new JFrame("Purchase Time for the Book");
            String time = JOptionPane.showInputDialog(frame, "For how long would you rent the book? (in seconds)");
            int time_purchased = Integer.parseInt(time);
            String sql2 = "SELECT READING_POINTS FROM BOOKINFO WHERE BOOKID = ?";
            PreparedStatement pst2 = conn.prepareStatement(sql2);
            pst2.setInt(1, bid);
            ResultSet rs2 = pst2.executeQuery();
            while (rs2.next()) {
                book_points = rs2.getInt("READING_POINTS");
            }
            if (user_points > (time_purchased * book_points)) {
                user_points = user_points - (time_purchased * book_points);
                String sql3 = "UPDATE USERINFO SET POINT_BALANCE = ? WHERE USERNAME = ?";
                PreparedStatement statement = conn.prepareStatement(sql3);
                statement.setInt(1, user_points);

                statement.setString(2, username);
                statement.executeUpdate();
                String sql7 = "SELECT READING_COUNTS FROM BOOKINFO where BOOKID = ?";
                PreparedStatement pst7 = conn.prepareStatement(sql7);
                pst7.setInt(1, bid);
                ResultSet rs7 = pst7.executeQuery();
                int reading_count = 0;
                while (rs.next()) {
                    reading_count = rs7.getInt("READING_COUTNS");

                }
                reading_count++;

                String sql8 = "UPDATE BOOKINFO SET READING_COUNTS = ? WHERE BOOKID = ?";
                PreparedStatement pst8 = conn.prepareStatement(sql8);
                pst8.setInt(1, reading_count++);
                pst8.setInt(2, bid);
                pst8.executeUpdate();

                Date date = new Date();//Calendar.getInstance().getTime();
                java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                String sql0 = "UPDATE BOOKINFO SET LAST_DATE_READ = ? WHERE BOOKID = ? ";
                PreparedStatement st = conn.prepareStatement(sql0);
                st.setDate(1, sqlDate);
                st.setInt(2, bid);
                st.executeUpdate();

                BookOpened bo = new BookOpened(bid, time_purchased);
                bo.setVisible(true);

                String sql5 = "SELECT READING_DURATION FROM READINGHISTORY WHERE USERNAME = ? AND BOOKID = ?";
                PreparedStatement statement1 = conn.prepareStatement(sql5);
                statement1.setString(1, username);
                statement1.setInt(2, bid);

                ResultSet rs1 = statement1.executeQuery();
                if (rs1.next() == false) {
                    String sql6 = "INSERT INTO READINGHISTORY (USERNAME, BOOKID) VALUES (?, ?)";
                    PreparedStatement pst6 = conn.prepareStatement(sql6);
                    pst6.setString(1, username);
                    pst6.setInt(2, bid);
                    pst6.execute();

                }

            } else {
                JOptionPane.showMessageDialog(null, "Not enough points");
            }
            String sql4 = "SELECT READING_DURATION FROM READINGHISTORY WHERE BOOKID = ? AND USERNAME = ?";
            PreparedStatement pst3 = conn.prepareStatement(sql4);
            pst3.setInt(1, bid);
            pst3.setString(2, username);
            ResultSet rs3 = pst3.executeQuery();

            int reading_history = 0;
            while (rs3.next()) {
                reading_history = rs3.getInt("READING_DURATION");
            }
            reading_history += time_purchased;
            String sql5 = "UPDATE READINGHISTORY SET READING_DURATION = ? WHERE BOOKID =? AND USERNAME = ?";
            PreparedStatement statement = conn.prepareStatement(sql5);
            statement.setInt(1, reading_history);
            statement.setInt(2, bid);
            statement.setString(3, username);
            statement.executeUpdate();

            String sql7 = "SELECT READING_COUNTS FROM BOOKINFO where BOOKID = ?";
            PreparedStatement pst7 = conn.prepareStatement(sql7);
            pst7.setInt(1, bid);
            ResultSet rs7 = pst7.executeQuery();

        } else {
            JOptionPane.showMessageDialog(null, "No book selected");
        }

    }

    private int get_points_available() {
        ResultSet rs = null;
        PreparedStatement pst = null;
        int user_point = 0;
        try {
            DbConnector dbc = new DbConnector();
            Connection conn = dbc.Connects();

            //int row = SentMessageTable.getSelectedRow();
            //int rowNum = (int) SentMessageTable.getModel().getValueAt(row, 0);
            String sql = "SELECT point_balance FROM UserInfo  WHERE username = ?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, username);
            rs = pst.executeQuery();
            while (rs.next()) {
                user_point = rs.getInt("point_balance");
            }

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user_point;
    }
    private void InviteButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_InviteButton2ActionPerformed
        // TODO add your handling code here:
        String user = (String) UserNameComboBox.getSelectedItem();
        String sharingtime = sharingtimeTextField.getText();

        int row = jTable1.getSelectedRow();

        if (!user.equals("Please Select...") && row != -1 && !sharingtime.isEmpty()) {
            int stime = Integer.parseInt(sharingtime);
            if ((stime / 10) <= (get_points_available() / 2)) {
                int bid = (int) jTable1.getModel().getValueAt(row, 0);
                InserttoInvitationTable(user, bid, stime);
                JOptionPane.showMessageDialog(null, "Invitation sent to " + user);
            } else {
                JOptionPane.showMessageDialog(null, "Seems like you don't have enough points to share.", "Warning", JOptionPane.WARNING_MESSAGE);

            }
            sharingtimeTextField.setText("");
            UserNameComboBox.setSelectedItem("Please Select...");
            //JOptionPane.showMessageDialog(null, "user: " + user + ",  bid: " + bid + ", time sharing: " + stime);
        } else {
            JOptionPane.showMessageDialog(null, "Please select the valid user and fill out the time you want to share.\nSelect the book.");
        }
    }//GEN-LAST:event_InviteButton2ActionPerformed

    private void ComplaintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComplaintActionPerformed
        // TODO add your handling code here:
        int row = jTable1.getSelectedRow();
        if (row != -1) {
            int bid = (int) jTable1.getModel().getValueAt(row, 0);
            ComplaintUserPage co = new ComplaintUserPage(bid, username);
            co.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "No book selected");
        }
    }//GEN-LAST:event_ComplaintActionPerformed

    private void BookSubmitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BookSubmitButtonActionPerformed
        // TODO add your handling code here:
        submit();
        clearTextField();

    }//GEN-LAST:event_BookSubmitButtonActionPerformed

    private void CancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CancelButtonActionPerformed
        // TODO add your handling code here:
        clearTextField();
    }//GEN-LAST:event_CancelButtonActionPerformed

    private void clearTextField() {
        BookNameTextField.setText("");
        BookAuthorTextField.setText("");
        BookSummaryTextArea.setText("");
        pointsTextField.setText("");
        coverpagepathprintLabel.setText("");
        bookpathprintlabel.setText("");
    }

    private void submit() {
        String B_name = BookNameTextField.getText();
        String A_name = BookAuthorTextField.getText();
        String B_summary = BookSummaryTextArea.getText();
        //String Uploadtext = BookSummaryTextArea.getText();
        //String uploader_name = UploaderNameTextField.getText();
        String requestedPoints = pointsTextField.getText();

        try {

            if (!B_name.isEmpty() && !A_name.isEmpty() && !B_summary.isEmpty() && !Cover_filepath.isEmpty() && !Book_filepath.isEmpty() && !requestedPoints.isEmpty()) // && !uploader_name.isEmpty()
            {
                DbConnector dbc = new DbConnector();
                Connection conn = dbc.Connects();

                String sql = "INSERT INTO PendingBook (uploader, bookname, cover, author, summary, bookfile,  request_points, granted_points) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, " + 0 + ")";

                PreparedStatement stmt = conn.prepareStatement(sql);
                //stmt.setString(1, uploader_name);
                stmt.setString(1, username);
                stmt.setString(2, B_name);
                File Cover_image = new File(Cover_filepath);
                FileInputStream Cover = new FileInputStream(Cover_image);
                stmt.setBinaryStream(3, Cover, (int) Cover_image.length());
                stmt.setString(4, A_name);
                stmt.setString(5, B_summary);
                File Book_image = new File(Book_filepath);
                FileInputStream Book = new FileInputStream(Book_image);
                stmt.setBinaryStream(6, Book, (int) Book_image.length());
                int point = Integer.parseInt(requestedPoints);
                stmt.setInt(7, point);

                Statement User_Stmt = conn.createStatement();
                String User_query = "Select bookname, author, uploader from PendingBook";
                ResultSet User_result = User_Stmt.executeQuery(User_query);
                boolean checkmatch = false;
                //boolean copyrightcheck = false;

                boolean UT = false;
                //                if (!UserNameText.isEmpty() && !PassWordText.isEmpty()) {
                while (User_result.next()) {
                    String BookN = User_result.getString("bookname");
                    String AuthorN = User_result.getString("author");
                    //String uploaderN = User_result.getString("uploader");//should find uploader username from UserInfo table for now its from Bookpending

                    if (BookN.equalsIgnoreCase(B_name) && AuthorN.equalsIgnoreCase(A_name))// && uploaderN.equalsIgnoreCase(uploader_name))
                    {
                        checkmatch = true;
                        //copyrightcheck = true;
                    }
                }
                if (checkmatch) {
                    JOptionPane.showMessageDialog(null, "Repeated submission book is prohibited");

                } else {
                    stmt.execute();
                    conn.commit();
                    conn.close();
                    JOptionPane.showMessageDialog(null, "Book submission succcessful.");
                }
                Cover.close();
                Book.close();
            } else {
                JOptionPane.showMessageDialog(null, "All fields required.", "Warning", JOptionPane.WARNING_MESSAGE);
            }

        } catch (SQLException | HeadlessException | IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());

        }
    }

    private void InserttoInvitationTable(String user, int bid, int stime) {
        DbConnector dbc = new DbConnector();
        Connection conn = dbc.Connects();
        String sql = "INSERT INTO INVITATION (inviter, invitee, bookid, sharing_points, Status) "
                + "VALUES (?, ?, ?, ?, ?)";//, ?, ?, " + 0 + ")";

        PreparedStatement stmt;
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, user);
            stmt.setInt(3, bid);
            stmt.setInt(4, stime);
            stmt.setString(5, "Pending");
            stmt.execute();
        } catch (SQLException ex) {
            Logger.getLogger(tabpannedUserPage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void BookBrowseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BookBrowseButtonActionPerformed
        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(null);
        File file = chooser.getSelectedFile();
        Book_filepath = file.getAbsolutePath();
        bookpathprintlabel.setText(Book_filepath);
    }//GEN-LAST:event_BookBrowseButtonActionPerformed

    private void BrowseCoverpageButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BrowseCoverpageButtonActionPerformed
        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(null);
        File file = chooser.getSelectedFile();
        Cover_filepath = file.getAbsolutePath();
        coverpagepathprintLabel.setText(Cover_filepath);
    }//GEN-LAST:event_BrowseCoverpageButtonActionPerformed

    private void SentMessageTablesentmessageTableRowMouseclick(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SentMessageTablesentmessageTableRowMouseclick
        // TODO add your handling code here:
        ResultSet rs = null;
        PreparedStatement pst = null;
        MessageTextArea.setText("");

        try {
            DbConnector dbc = new DbConnector();
            Connection conn = dbc.Connects();
            int row = SentMessageTable.getSelectedRow();
            int rowNum = (int) SentMessageTable.getModel().getValueAt(row, 0);
            String sql = "SELECT message_txt FROM Message  where Message.MID = ?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, rowNum);
            rs = pst.executeQuery();

            while (rs.next()) {

                MessageTextArea.setText(rs.getString("Message_txt"));
                //MessageTextArea.setEditable(false);
                MessageTextArea.setLineWrap(true);

            }
            conn.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_SentMessageTablesentmessageTableRowMouseclick

    private void ReceivedMessageInboxTableRowMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ReceivedMessageInboxTableRowMouseClicked
        // TODO add your handling code here:
        ResultSet rs = null;
        PreparedStatement pst = null;
        MessageTextArea.setText("");

        try {
            DbConnector dbc = new DbConnector();
            Connection conn = dbc.Connects();
            int row = ReceivedMessage.getSelectedRow();
            int rowNum = (int) ReceivedMessage.getModel().getValueAt(row, 0);
            String sql = "SELECT message_txt FROM Message  where Message.MID = ?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, rowNum);
            rs = pst.executeQuery();

            while (rs.next()) {

                MessageTextArea.setText(rs.getString("Message_txt"));
                //MessageTextArea.setEditable(false);
                MessageTextArea.setLineWrap(true);

            }
            conn.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_ReceivedMessageInboxTableRowMouseClicked

    private void MessageSendButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MessageSendButtonActionPerformed

        // TODO add your handling code here:
        //this button checks the points availabel and if ok sends message to the selected user to read the book
        String receiver = (String) jComboBox1.getSelectedItem();
        String message = MessageTextArea.getText();
        DbConnector dbc = new DbConnector();
        Connection conn = dbc.Connects();

        //int row = jTable1.getSelectedRow();
        //int rowNum = (int) jTable1.getModel().getValueAt(row, 0);
        String Sql = "INSERT INTO Message (Sender, Receiver, message_txt) "
                + "VALUES (?, ?, ?)";
        try {
            PreparedStatement stmt = conn.prepareStatement(Sql);
            //stmt.setString(1, uploader_name);
            if (receiver.equals("Please Select...") || message.isEmpty() || message.equals("Write your message here")) {
                JOptionPane.showMessageDialog(null, "Please Check the fields", "Input Error!", JOptionPane.WARNING_MESSAGE);
            } else {
                stmt.setString(1, username);
                stmt.setString(2, receiver);
                stmt.setString(3, message);
                stmt.execute();
                MessageTextArea.setText("");
                jComboBox1.setSelectedItem("Please Select...");
                JOptionPane.showMessageDialog(null, "Message sent");

                conn.commit();
                conn.close();
            }

        } catch (SQLException ex) {
            Logger.getLogger(tabpannedUserPage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_MessageSendButtonActionPerformed

    private void ClearMessageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ClearMessageActionPerformed
        // TODO add your handling code here:
        MessageTextArea.setText("");
        jComboBox1.setSelectedItem("Please Select...");
    }//GEN-LAST:event_ClearMessageActionPerformed

    private void AcceptInvitationButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AcceptInvitationButtonActionPerformed
        // TODO add your handling code here:
        //accepting_Invitation();
        ResultSet rs = null;
        PreparedStatement pst = null;
        String inviting_User = "";
        String invited_User = "";
        int bid = 0;
        int stime = 0;

        String status = "";
        int rowNum = 0;
        DbConnector dbc = new DbConnector();
        Connection conn = dbc.Connects();

        int row = InvitationReceivedTable.getSelectedRow();

        if (row != -1) {
            //int
            rowNum = (int) InvitationReceivedTable.getModel().getValueAt(row, 0);

            //            work_on_InvitationTable(rowNum);
            String sql = "SELECT * FROM Invitation  WHERE IID = ?";
            try {
                pst = conn.prepareStatement(sql);
                pst.setInt(1, rowNum);
                //pst.setString(2, username);
                rs = pst.executeQuery();
                while (rs.next()) {
                    inviting_User = rs.getString("inviter");
                    invited_User = rs.getString("Invitee");
                    bid = rs.getInt("bookid");
                    stime = rs.getInt("sharing_points");
                    status = rs.getString("status");

                }

            } catch (SQLException ex) {
                Logger.getLogger(tabpannedUserPage.class.getName()).log(Level.SEVERE, null, ex);
            }
            // }
            if (status.equals("Accepted")) {
                JOptionPane.showMessageDialog(null, "You can't accept twice");
            } else {

                try {
                    deduct_points_of_inviter(inviting_User, stime, bid);

                    update_shared_book_status_in_invitation_table(rowNum);
                    populateInvitationReceivedTable();
                    JOptionPane.showMessageDialog(null, "Book has been accepted");
                } catch (SQLException ex) {
                    Logger.getLogger(tabpannedUserPage.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            //            int row2 = InvitationSentTable.getSelectedRow();
            //            int rowNum = (int) InvitationSentTable.getModel().getValueAt(row2, 0);
            JOptionPane.showMessageDialog(null, "Please select the book first.");
            //work_on_InvitationTable(rowNum);
        }

    }//GEN-LAST:event_AcceptInvitationButtonActionPerformed

    private void populateInvitationReceivedTable() {
        ResultSet rs = null;
        PreparedStatement pst = null;

        try {
            DbConnector dbc = new DbConnector();
            Connection conn = dbc.Connects();
            //int row = jTable1.getSelectedRow();
            //int rowNum = (int) jTable1.getModel().getValueAt(row, 0);
            String Sql = "Select IID AS IID, inviter AS Invited_By, Bookid AS BOOK_ID, sharing_points AS SharedTime, status AS Shared_Status FROM INVITATION WHERE invitee = ?";
            pst = conn.prepareStatement(Sql);
            pst.setString(1, username);
            rs = pst.executeQuery();
//            while (rs.next()) {
//                if (rs.getBoolean("Shared_Status")) {
//                    rs.updateString("Shared_Status", "Accepted");
//                }
//            }
            InvitationReceivedTable.setModel(DbUtils.resultSetToTableModel(rs));
            //InvitationReceivedTable.setEnabled(false);
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void populateContributedTable() {
        ResultSet rs = null;
        PreparedStatement pst = null;
        DbConnector dbc = new DbConnector();
        Connection conn = dbc.Connects();
        //int row = jTable1.getSelectedRow();
        //int rowNum = (int) jTable1.getModel().getValueAt(row, 0);
        String Sql = "Select bookName AS BookName, author as Author, rating as Ratings  FROM BookInfo WHERE uploader = ?";
        try {
            pst = conn.prepareStatement(Sql);
            pst.setString(1, username);
            rs = pst.executeQuery();
            BookContributedByUserTable.setModel(DbUtils.resultSetToTableModel(rs));
            BookContributedByUserTable.setEnabled(false);
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(tabpannedUserPage.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void populateDropDownCombo() {

        ResultSet rs = null;
        PreparedStatement pst = null;

        jComboBox1.removeAllItems();
        UserNameComboBox.removeAllItems();
        jComboBox1.addItem("Please Select...");
        UserNameComboBox.addItem("Please Select...");

        try {
            DbConnector dbc = new DbConnector();
            Connection conn = dbc.Connects();
            String sql = "SELECT DISTINCT username FROM UserInfo";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {
                if (!rs.getString("username").equals(username)) {

                    jComboBox1.addItem(rs.getString("username"));
                    UserNameComboBox.addItem(rs.getString("username"));
                }
            }

        } catch (SQLException sqle) {
            System.out.println(sqle);
        }

    }

    private void populateSentMessageTable() {
        ResultSet rs = null;
        PreparedStatement pst = null;
        try {
            DbConnector dbc = new DbConnector();
            Connection conn = dbc.Connects();
            String Sql = "Select MID AS MessageID, Receiver AS Receiver, Message_txt AS Message FROM Message WHERE Sender = ?";
            pst = conn.prepareStatement(Sql);
            pst.setString(1, username);
            rs = pst.executeQuery();
            SentMessageTable.setModel(DbUtils.resultSetToTableModel(rs));
            //SentMessageTable.setEnabled(false);
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void populateReceiveMessageTable() {
        ResultSet rs = null;
        PreparedStatement pst = null;
        try {
            DbConnector dbc = new DbConnector();
            Connection conn = dbc.Connects();
            String Sql = "Select MID AS MessageID, Sender AS Sender, Message_txt AS Message FROM Message WHERE Receiver = ?";
            pst = conn.prepareStatement(Sql);
            pst.setString(1, username);
            rs = pst.executeQuery();
            ReceivedMessage.setModel(DbUtils.resultSetToTableModel(rs));
            //ReceivedMessage.setEnabled(false);
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //BookPendingUserTable
    private void populateReadBookTable() {
        ResultSet rs = null;
        PreparedStatement pst = null;

        try {
            DbConnector dbc = new DbConnector();
            Connection conn = dbc.Connects();
            String Sql = "Select BookID, Reading_duration AS Time_read_in_sec  FROM READINGHISTORY WHERE username = ?";
            pst = conn.prepareStatement(Sql);
            pst.setString(1, username);
            rs = pst.executeQuery();
            ReadBookTable.setModel(DbUtils.resultSetToTableModel(rs));
            ReadBookTable.setEnabled(false);
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void populateInvitationSentTable() {
        ResultSet rs = null;
        PreparedStatement pst = null;

        try {
            DbConnector dbc = new DbConnector();
            Connection conn = dbc.Connects();
            //int row = jTable1.getSelectedRow();
            //int rowNum = (int) jTable1.getModel().getValueAt(row, 0);
            String Sql = "Select IID AS IID, invitee AS You_Invited,Bookid AS BOOK_ID, sharing_points AS SharedTime, status AS Shared_Status FROM INVITATION WHERE inviter = ?";
            pst = conn.prepareStatement(Sql);
            pst.setString(1, username);
            rs = pst.executeQuery();
//            while (rs.next()) {
//                if (rs.getBoolean("Shared_Status")) {
//                    rs.toString().replace("true", "Accepted");
//                    //rs.("Shared_Status", "Accepted");
//                }else{
//                    rs.toString().replace("false", "Declined");
//                }
//                    
//            }
            InvitationSentTable.setModel(DbUtils.resultSetToTableModel(rs));
            InvitationSentTable.setEnabled(false);
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void populateUserDetails() {
        ResultSet rs = null;
        PreparedStatement pst = null;
        FNameTextField.setText("");
        LastNameTextField.setText("");
        EmailTextField.setText("");
        PointEarnedTextField.setText("");
        BookGotRemovedTextfield.setText("");

        try {
            DbConnector dbc = new DbConnector();
            Connection conn = dbc.Connects();
            //int row = SentMessageTable.getSelectedRow();
            //int rowNum = (int) SentMessageTable.getModel().getValueAt(row, 0);
            String sql = "SELECT * FROM UserInfo  WHERE username = ?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, username);
            rs = pst.executeQuery();

            while (rs.next()) {
                FNameTextField.setText(rs.getString("firstname"));
                FNameTextField.setEnabled(false);
                LastNameTextField.setText(rs.getString("lastname"));
                LastNameTextField.setEnabled(false);
                EmailTextField.setText(rs.getString("email"));
                EmailTextField.setEnabled(false);
                PointEarnedTextField.setText(rs.getString("point_balance"));
                PointEarnedTextField.setEnabled(false);
                BookGotRemovedTextfield.setText(rs.getString("book_got_removed"));
                BookGotRemovedTextfield.setEditable(false);

                if (rs.getBoolean("is_SU")) {
                    UserTypeTextField.setText("SuperUser");
                } else {
                    UserTypeTextField.setText("User");
                }//MessageTextArea.setLineWrap(true);

            }
            conn.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
   
    private void update_shared_book_status_in_invitation_table(int Row_num) {
        DbConnector dbc = new DbConnector();
        Connection conn = dbc.Connects();
        String sql = "UPDATE INVITATION SET Status = ? WHERE IID= ?";//, ?, ?, " + 0 + ")";

        PreparedStatement stmt;
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, "Accepted");
            stmt.setInt(2, Row_num);

            stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(tabpannedUserPage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void deduct_points_of_inviter(String inviter, int sharing_time, int bid) throws SQLException {
        int new_point_balance = 0;
//        int sharing_points = 0;
        int old_point_balance = 0;
        //       ResultSet rs = null;
        //PreparedStatement pst = null;
        PreparedStatement pst2 = null;
        DbConnector dbc = new DbConnector();
        Connection conn = dbc.Connects();

        String sql1 = "SELECT READING_POINTS FROM BOOKINFO WHERE BID = ?";
        PreparedStatement pst1 = conn.prepareStatement(sql1);
        pst1.setInt(1, bid);
        ResultSet rs = pst1.executeQuery();
        int asking_points = 0;
        while (rs.next()) {
            asking_points = rs.getInt("READING_POINTS");

        }
    }
    private void DeclineInvitationButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeclineInvitationButtonActionPerformed
        // TODO add your handling code here:
        int dialogResult = JOptionPane.showConfirmDialog(null, "Selecting Yes will delete the invitation from the list.\nDo you still want to decline the Invitation?", "Confirmation", JOptionPane.YES_NO_OPTION);
        //        ResultSet rs = null;
        //        PreparedStatement pst = null;
        //        String inviting_User = "";
        //        String invited_User = "";
        //        int bid = 0;
        //        int stime = 0;
        //
        //        String status = "";
        //        int rowNum = 0;
        //        DbConnector dbc = new DbConnector();
        //        Connection conn = dbc.Connects();
        //
        //        int row = InvitationReceivedTable.getSelectedRow();
        //
        //        if (row != -1) {
        //            //int
        //            rowNum = (int) InvitationReceivedTable.getModel().getValueAt(row, 0);
        //
        ////            work_on_InvitationTable(rowNum);
        //            String sql = "SELECT * FROM Invitation  WHERE IID = ?";
    }//GEN-LAST:event_DeclineInvitationButtonActionPerformed

        private void populatependingContributedTable() {
        ResultSet rs = null;
        PreparedStatement pst = null;

        try {
            DbConnector dbc = new DbConnector();
            Connection conn = dbc.Connects();
            //int row = jTable1.getSelectedRow();
            //int rowNum = (int) jTable1.getModel().getValueAt(row, 0);
            String Sql = "Select bookName AS BookName, author as Author, request_Points as RequestedPoints  FROM pendingbook WHERE uploader = ?";
            pst = conn.prepareStatement(Sql);
            pst.setString(1, username);
            rs = pst.executeQuery();
            BookPendingUserTable.setModel(DbUtils.resultSetToTableModel(rs));
            BookPendingUserTable.setEnabled(false);
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     *
     * @author indrajit
     */
    private static String Cover_filepath;
    private static String Book_filepath;

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
                new SuperUserFrame(username, firstname).setVisible(true);

            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AcceptInvitationButton;
    private javax.swing.JButton Approve;
    private javax.swing.JTextField BookAuthorTextField;
    private javax.swing.JButton BookBrowseButton;
    private javax.swing.JTable BookContributedByUserTable;
    private javax.swing.JTextField BookGotRemovedTextfield;
    private javax.swing.JTextField BookNameTextField;
    private javax.swing.JTable BookPendingUserTable;
    private javax.swing.JButton BookSubmitButton;
    private javax.swing.JTextArea BookSummaryTextArea;
    private javax.swing.JLabel BooksListLabel;
    private javax.swing.JButton BrowseCoverpageButton;
    private javax.swing.JButton CancelButton;
    private javax.swing.JButton ClearMessage;
    private javax.swing.JButton ComNotIssue;
    private javax.swing.JButton ComRegIssue;
    private javax.swing.JButton ComSeriousIssue;
    private javax.swing.JButton Complaint;
    private javax.swing.JScrollPane ComplaintPane;
    private javax.swing.JButton Decline;
    private javax.swing.JButton DeclineInvitationButton;
    private javax.swing.JTextField EmailTextField;
    private javax.swing.JTextField FNameTextField;
    private javax.swing.JLabel FirstNameLabel;
    private javax.swing.JTable InvitationReceivedTable;
    private javax.swing.JTable InvitationSentTable;
    private javax.swing.JButton InviteButton2;
    private javax.swing.JLabel LastNameLabel;
    private javax.swing.JTextField LastNameTextField;
    private javax.swing.JButton Logout;
    private javax.swing.JButton MessageSendButton;
    private javax.swing.JTextArea MessageTextArea;
    private javax.swing.JTabbedPane NotificationCenter;
    private javax.swing.JButton Pending;
    private javax.swing.JTextField PointEarnedTextField;
    private javax.swing.JPanel RareReadPane;
    private javax.swing.JButton RateSelectedBookButton;
    private javax.swing.JTable ReadBookTable;
    private javax.swing.JLabel ReadBooksLabel;
    private javax.swing.JButton ReadSelectedBookButton;
    private javax.swing.JTable ReceivedMessage;
    private javax.swing.JButton RefreshCom;
    private javax.swing.JButton RefreshLowRate;
    private javax.swing.JButton RefreshPbooks;
    private javax.swing.JButton RefreshRareRead;
    private javax.swing.JButton RemoveLowRate;
    private javax.swing.JButton RemoveRareRead;
    private javax.swing.JButton RestCom;
    private javax.swing.JButton RestPbooks;
    private javax.swing.JTable ReviewDisplayTable;
    private javax.swing.JTable SentMessageTable;
    private javax.swing.JTabbedPane SharedBooksTab;
    private javax.swing.JTextArea SummaryTextArea;
    private javax.swing.JTabbedPane SuperUser;
    private javax.swing.JLabel UploadBookLabel;
    private javax.swing.JLabel UploadcoverPageLabel;
    private javax.swing.JComboBox UserNameComboBox;
    private javax.swing.JTextField UserTypeTextField;
    private javax.persistence.EntityManager UsersRegistrationPUEntityManager;
    private javax.swing.JButton ViewBook;
    private javax.swing.JTextField book_com_time;
    private javax.swing.JTextField book_removed;
    private java.util.List<ebooksharing1.Bookinfo> bookinfoList1;
    private javax.persistence.Query bookinfoQuery1;
    private javax.swing.JLabel bookpathprintlabel;
    private javax.swing.JTextField com_auth;
    private javax.swing.JTextField com_bID;
    private javax.swing.JTextField com_bname;
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
    private javax.swing.JLabel coverpagepathprint;
    private javax.swing.JLabel coverpagepathprintLabel;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JScrollPane jScrollPane16;
    private javax.swing.JScrollPane jScrollPane17;
    private javax.swing.JScrollPane jScrollPane18;
    private javax.swing.JScrollPane jScrollPane19;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane20;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextPane jTextPane1;
    private javax.swing.JTextPane jTextPane2;
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
    private javax.swing.JTextField lowrate_uploader;
    private javax.swing.JLabel lowrate_uploader_label;
    private javax.swing.JTextField lowrating;
    private javax.swing.JTextField lowrating_counts;
    private javax.swing.JLabel lreq;
    private javax.swing.JLabel lsumm;
    private javax.swing.JLabel luploader;
    private javax.swing.JTable pdbooks;
    private javax.swing.JScrollPane pdbooks_ScrollPane;
    private javax.swing.JPanel pendingbooksPane;
    private javax.swing.JTextField pointsTextField;
    private javax.swing.JTable rareReadBook;
    private javax.swing.JTextField rareread_bookid;
    private javax.swing.JTextField rareread_bookname;
    private javax.swing.JLabel rareread_bookname_label;
    private javax.swing.JTextField rareread_cnts;
    private javax.swing.JTextField rareread_duration;
    private javax.swing.JTextField rareread_pts;
    private javax.swing.JLabel rareread_pts_label;
    private javax.swing.JLabel rareread_pts_label1;
    private javax.swing.JLabel rareread_pts_label2;
    private javax.swing.JTextField rareread_uploader;
    private javax.swing.JTextField reward_pts;
    private javax.swing.JTextField sharingtimeTextField;
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
    private javax.swing.JButton viewbook;
    private javax.swing.JLabel welcome;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    public void cancel() {
        WindowEvent winClosing = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(winClosing);
    }

}
