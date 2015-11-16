/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ebooksharing_susan;

import java.awt.Toolkit;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

/**
 *
 * @author indrajit
 */
public class HomeFrame extends javax.swing.JFrame {

    /**
     * Creates new form ApplicationHome
     */
    public HomeFrame() {
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

        Home = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        VisitorButton = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        Login = new javax.swing.JPanel();
        UserNameLabel = new javax.swing.JLabel();
        PasswordLabel = new javax.swing.JLabel();
        UserNameTextField = new javax.swing.JTextField();
        SignInButton = new javax.swing.JButton();
        UserPassTextField = new javax.swing.JPasswordField();
        Register = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jRadioButton3 = new javax.swing.JRadioButton();
        RegisterButton = new javax.swing.JButton();
        jRadioButton4 = new javax.swing.JRadioButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        FirstNameTextField = new javax.swing.JTextField();
        LastNameTextField = new javax.swing.JTextField();
        EmailTextField = new javax.swing.JTextField();
        UserNameTextField1 = new javax.swing.JTextField();
        PasswordField1 = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        Home.setBackground(new java.awt.Color(51, 153, 0));
        Home.setSize(new java.awt.Dimension(1000, 800));

        jLabel1.setBackground(new java.awt.Color(0, 153, 102));
        jLabel1.setFont(new java.awt.Font("Lucida Grande", 1, 36)); // NOI18N
        jLabel1.setText("Welcome to EBook Sharing Application");

        VisitorButton.setText("Continue as Visitor");
        VisitorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                VisitorButtonActionPerformed(evt);
            }
        });

        jTabbedPane1.setPreferredSize(new java.awt.Dimension(400, 400));

        Login.setPreferredSize(new java.awt.Dimension(400, 400));

        UserNameLabel.setText("UserName");

        PasswordLabel.setText("PassWord");

        SignInButton.setText("Sign In");
        SignInButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SignInButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout LoginLayout = new javax.swing.GroupLayout(Login);
        Login.setLayout(LoginLayout);
        LoginLayout.setHorizontalGroup(
            LoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, LoginLayout.createSequentialGroup()
                .addContainerGap(39, Short.MAX_VALUE)
                .addGroup(LoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(UserNameLabel)
                    .addComponent(PasswordLabel))
                .addGap(18, 18, 18)
                .addGroup(LoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(UserPassTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(UserNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(56, 56, 56))
            .addGroup(LoginLayout.createSequentialGroup()
                .addGap(111, 111, 111)
                .addComponent(SignInButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        LoginLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {UserNameTextField, UserPassTextField});

        LoginLayout.setVerticalGroup(
            LoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LoginLayout.createSequentialGroup()
                .addGap(75, 75, 75)
                .addGroup(LoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(UserNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(UserNameLabel))
                .addGap(24, 24, 24)
                .addGroup(LoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(PasswordLabel)
                    .addComponent(UserPassTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addComponent(SignInButton)
                .addContainerGap(138, Short.MAX_VALUE))
        );

        LoginLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {UserNameTextField, UserPassTextField});

        jTabbedPane1.addTab("Login ", Login);

        Register.setPreferredSize(new java.awt.Dimension(400, 400));

        jLabel3.setText("Username");

        jLabel8.setText("Select User Category");

        jLabel4.setText("Password");

        jRadioButton3.setText("Super User");

        RegisterButton.setText("Register");
        RegisterButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RegisterButtonActionPerformed(evt);
            }
        });

        jRadioButton4.setText("User");

        jLabel5.setText("First Name");

        jLabel6.setText("Last Name");

        jLabel9.setText("Email Address");

        javax.swing.GroupLayout RegisterLayout = new javax.swing.GroupLayout(Register);
        Register.setLayout(RegisterLayout);
        RegisterLayout.setHorizontalGroup(
            RegisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(RegisterLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(RegisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(RegisterLayout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(24, 24, 24)
                        .addGroup(RegisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jRadioButton4)
                            .addComponent(jRadioButton3)))
                    .addGroup(RegisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(RegisterLayout.createSequentialGroup()
                            .addGroup(RegisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel5)
                                .addComponent(jLabel6)
                                .addComponent(jLabel4)
                                .addComponent(jLabel9))
                            .addGroup(RegisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(RegisterLayout.createSequentialGroup()
                                    .addGap(7, 7, 7)
                                    .addGroup(RegisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(EmailTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(LastNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(PasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(RegisterLayout.createSequentialGroup()
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(FirstNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGroup(RegisterLayout.createSequentialGroup()
                            .addComponent(jLabel3)
                            .addGap(34, 34, 34)
                            .addComponent(UserNameTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(18, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, RegisterLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(RegisterButton)
                .addGap(113, 113, 113))
        );

        RegisterLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {EmailTextField, FirstNameTextField, LastNameTextField, PasswordField1, UserNameTextField1});

        RegisterLayout.setVerticalGroup(
            RegisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(RegisterLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(RegisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(UserNameTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(RegisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(PasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(RegisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LastNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(RegisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(EmailTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(RegisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(FirstNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(RegisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(jRadioButton3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jRadioButton4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(RegisterButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        RegisterLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {EmailTextField, FirstNameTextField, LastNameTextField, PasswordField1, UserNameTextField1});

        jTabbedPane1.addTab("Registration", Register);

        javax.swing.GroupLayout HomeLayout = new javax.swing.GroupLayout(Home);
        Home.setLayout(HomeLayout);
        HomeLayout.setHorizontalGroup(
            HomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HomeLayout.createSequentialGroup()
                .addGroup(HomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(HomeLayout.createSequentialGroup()
                        .addGap(271, 271, 271)
                        .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(HomeLayout.createSequentialGroup()
                        .addGap(83, 83, 83)
                        .addComponent(jLabel1))
                    .addGroup(HomeLayout.createSequentialGroup()
                        .addGap(363, 363, 363)
                        .addComponent(VisitorButton)))
                .addContainerGap(160, Short.MAX_VALUE))
        );
        HomeLayout.setVerticalGroup(
            HomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HomeLayout.createSequentialGroup()
                .addGap(166, 166, 166)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(VisitorButton)
                .addGap(38, 38, 38)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(173, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Home, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Home, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
 
    private void VisitorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_VisitorButtonActionPerformed
        // TODO add your handling code here:
       //VisitorPage Eav = new VisitorPage("Guest", "Visitor");
       // Eav.setVisible(true);
        cancel();
    }//GEN-LAST:event_VisitorButtonActionPerformed

    private void SignInButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SignInButtonActionPerformed
        // TODO add your handling code here:

        String UserNameText = "";
        String delim = ",";
        String PassWordText = "";
        UserNameText = this.UserNameTextField.getText();
        PassWordText = this.UserPassTextField.getText();

        try {
            DbConnector dbc = new DbConnector();

            Connection conn = dbc.Connects();
            //Class.forName("org.apache.derby.jdbc.ClientDriver");
            //conn = DriverManager.getConnection("jdbc:derby://localhost:1527/UsersRegistration", "java", "java");
            Statement User_Stmt = conn.createStatement();
            String User_query = "Select * from UserInfo";
            ResultSet User_result = User_Stmt.executeQuery(User_query);
            boolean checkmatch = false;
            boolean SuperUserType = false;
            boolean UserType = false;

            String usernametogui = "";
            if (!UserNameText.isEmpty() && !PassWordText.isEmpty()) {
                while (User_result.next()) {
                    String UN = User_result.getString("UserName");
                    String PW = User_result.getString("Password");

                    if (UN.equalsIgnoreCase(UserNameText) && PW.equals(PassWordText)) {
                        checkmatch = true;
                        usernametogui = User_result.getString("firstName");
                        SuperUserType = User_result.getBoolean("is_SU");
                    }

                }

                if (checkmatch) {
                    if(SuperUserType){
                        //JOptionPane.showMessageDialog(null, "You are logged in as  Super user.");
                        cancel();
                        SuperUser su = new SuperUser(usernametogui);
                        su.setVisible(true);
                    }
                    else if(!SuperUserType){
                        //JOptionPane.showMessageDialog(null, "You are logged in as registered user.");
                        cancel();
                        RegUserPage rup = new RegUserPage("Register User", usernametogui);
                        rup.setVisible(true);
                    }
                    else{
                        cancel();
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null, "Invalid creditials, Try again.");
                    this.UserNameTextField.setText("");
                    this.UserPassTextField.setText("");
                }
            }
            else
            {
                JOptionPane.showMessageDialog(null, "All fields are required");

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());

        }
    }//GEN-LAST:event_SignInButtonActionPerformed

    private void RegisterButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RegisterButtonActionPerformed
        // TODO add your handling code here:
        //String file_name = "test.txt";
        String U_name = UserNameTextField.getText();
        String P_word = PasswordField1.getText();
        String F_name = FirstNameTextField.getText();
        String L_name = LastNameTextField.getText();
        String E_add = EmailTextField.getText();
        //String ID = IDTextField.getText();
        int points = 0;

        //////////
        //String result;
        boolean result = false;

        if (jRadioButton3.isSelected()) {
            //result = "Super User";
            result = true;
        } //else if (jRadioButton4.isSelected()) {
            //result = "User";
            //          result = false;
            //        }
        //        } else {
        //            result = "Unknown";
        //        }
        try {
            DbConnector dbc = new DbConnector();
            Connection conn = dbc.Connects();

            //            Class.forName("org.apache.derby.jdbc.ClientDriver");
            //            conn = DriverManager.getConnection("jdbc:derby://localhost:1527/UsersRegistration", "java", "java");
            Statement stmt = conn.createStatement();
            Statement UserNameStmt = conn.createStatement();
            String UserN_query = "Select * from UserInfo";
            ResultSet UserN_result = UserNameStmt.executeQuery(UserN_query);
            boolean checkUserName = false;
            boolean checkemail = false;
            while (UserN_result.next()) {
                String UN = UserN_result.getString("UserName");
                String Uemail = UserN_result.getString("email");
                if (UN.equalsIgnoreCase(U_name)) {
                    checkUserName = true;
                }
                if (Uemail.equalsIgnoreCase(E_add)) {
                    checkemail = true;
                }

            }
            if (!checkUserName) {
                if (!checkemail) {
                    String query = "Insert Into UserInfo Values ('" + U_name + "','" + P_word + "','" + F_name + "','" + L_name + "','" + E_add + "','" + result + "'," + 0 + ")";//,'" +0+"')";

                if (!U_name.isEmpty() && !P_word.isEmpty() && !F_name.isEmpty() && !L_name.isEmpty() && !E_add.isEmpty() && (jRadioButton3.isSelected() || jRadioButton4.isSelected())) {
                    if (jRadioButton3.isSelected())// && jRadioButton3.getText().equals("Super User"))
                {
                    String input = JOptionPane.showInputDialog(null, "Enter Your Access code:", "Verification", JOptionPane.OK_OPTION);
                    if (input != null) {

                        if (input.equals("Access"))//This is a hard coded Access code for Super User registration
                        {
                            JOptionPane.showMessageDialog(null, "Congratulations! Access granted, You have now Super User privilege.");
                            stmt.executeUpdate(query);
                            /*while(rs.next()){
                                System.out.println("Un"+ rs.getString("UserName")+ "UP "+ rs.getString("UserPassword")+ "FN "+ rs.getString("FirstName")+ "LN "+ rs.getString("LastName")+"email "+rs.getString("Email")+"UID "+ rs.getBigDecimal("UserID")+ "UType "+ rs.getString("UserType"));
                            }*/
                            //System.out.println("Connected to db");
                            conn.close();
                            cancel();
                            GreetingPage gp = new GreetingPage();
                            gp.setVisible(true);
                        } else {
                            final JPanel jp = new JPanel();
                            JOptionPane.showMessageDialog(jp, "your access code is wrong !!!", "Warning", JOptionPane.WARNING_MESSAGE);
                        }

                    } else {
                        final JPanel jp = new JPanel();
                        JOptionPane.showMessageDialog(jp, "you clicked cancel !!! Try again", "Cancelled", JOptionPane.WARNING_MESSAGE);
                    }
                } else {

                    stmt.executeUpdate(query);
                    conn.close();
                    //JOptionPane.showMessageDialog(null, "Congratulations, You are now Registered User.");
                    GreetingPage gp = new GreetingPage();
                    gp.setVisible(true);
                    cancel();
                }

            } else {
                JOptionPane.showMessageDialog(null, "All field needs to be filled out.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "This email address is already used.");
        }
        //cancel();
        } else {
            JOptionPane.showMessageDialog(null, "UserName already taken.");
        }
        //conn.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());

        }
    }//GEN-LAST:event_RegisterButtonActionPerformed

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
            java.util.logging.Logger.getLogger(HomeFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HomeFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HomeFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HomeFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new HomeFrame().setVisible(true);
            }
        });
    }

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField EmailTextField;
    private javax.swing.JTextField FirstNameTextField;
    private javax.swing.JPanel Home;
    private javax.swing.JTextField LastNameTextField;
    private javax.swing.JPanel Login;
    private javax.swing.JPasswordField PasswordField1;
    private javax.swing.JLabel PasswordLabel;
    private javax.swing.JPanel Register;
    private javax.swing.JButton RegisterButton;
    private javax.swing.JButton SignInButton;
    private javax.swing.JLabel UserNameLabel;
    private javax.swing.JTextField UserNameTextField;
    private javax.swing.JTextField UserNameTextField1;
    private javax.swing.JPasswordField UserPassTextField;
    private javax.swing.JButton VisitorButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JRadioButton jRadioButton4;
    private javax.swing.JTabbedPane jTabbedPane1;
    // End of variables declaration//GEN-END:variables

   public void cancel(){
        WindowEvent winClosing = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(winClosing);
    }
}