package ebooksharing1;

import com.sun.glass.events.KeyEvent;
import java.awt.Toolkit;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author indrajit
 */
public class tabpannedAH extends javax.swing.JFrame {

    /**
     * Creates new form tabpannedAH
     */
    public tabpannedAH() {
        super("EBook Sharing Application Home Page");
        initComponents();
        EmailFormatControl();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        UserTypeRadioButton = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        VisitorButton = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        login = new javax.swing.JPanel();
        UserNameLabel = new javax.swing.JLabel();
        PasswordLabel = new javax.swing.JLabel();
        UserNameTextField1 = new javax.swing.JTextField();
        SignInButton = new javax.swing.JButton();
        UserPassTextField = new javax.swing.JPasswordField();
        jLabel10 = new javax.swing.JLabel();
        registration = new javax.swing.JPanel();
        PasswordField1 = new javax.swing.JPasswordField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        RegisterButton = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        LastNameTextField = new javax.swing.JTextField();
        EmailTextField = new javax.swing.JTextField();
        UserNameTextField = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jRadioButton3 = new javax.swing.JRadioButton();
        jRadioButton4 = new javax.swing.JRadioButton();
        FirstNameTextField = new javax.swing.JTextField();
        CancelButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(51, 153, 0));
        jPanel1.setName(""); // NOI18N

        jLabel1.setBackground(new java.awt.Color(0, 153, 102));
        jLabel1.setFont(new java.awt.Font("Lucida Grande", 1, 36)); // NOI18N
        jLabel1.setText("Welcome to EBook Sharing Application");

        VisitorButton.setText("Continue as Visitor");
        VisitorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                VisitorButtonActionPerformed(evt);
            }
        });

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ebooksharing1/banner3.jpg"))); // NOI18N

        login.setBackground(new java.awt.Color(204, 204, 204));
        login.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        login.setPreferredSize(new java.awt.Dimension(963, 920));

        UserNameLabel.setText("UserName");

        PasswordLabel.setText("PassWord");

        UserNameTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                UserNameTextField1KeyReleased(evt);
            }
        });

        SignInButton.setText("Sign In");
        SignInButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SignInButtonActionPerformed(evt);
            }
        });
        SignInButton.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SignInButtonKeyPressed(evt);
            }
        });

        UserPassTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                UserPassTextFieldKeyPressed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Lucida Grande", 1, 24)); // NOI18N
        jLabel10.setText("Please Enter Your credentials ");

        javax.swing.GroupLayout loginLayout = new javax.swing.GroupLayout(login);
        login.setLayout(loginLayout);
        loginLayout.setHorizontalGroup(
            loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, loginLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(UserNameLabel)
                    .addComponent(PasswordLabel))
                .addGap(45, 45, 45)
                .addGroup(loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(UserNameTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(UserPassTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(86, 86, 86))
            .addGroup(loginLayout.createSequentialGroup()
                .addGroup(loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(loginLayout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jLabel10))
                    .addGroup(loginLayout.createSequentialGroup()
                        .addGap(160, 160, 160)
                        .addComponent(SignInButton)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        loginLayout.setVerticalGroup(
            loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(loginLayout.createSequentialGroup()
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(UserNameTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(UserNameLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(UserPassTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(PasswordLabel))
                .addGap(36, 36, 36)
                .addComponent(SignInButton)
                .addContainerGap(76, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Login", login);

        registration.setBackground(new java.awt.Color(204, 204, 204));
        registration.setToolTipText("");
        registration.setPreferredSize(new java.awt.Dimension(970, 920));

        jLabel3.setText("Username");

        jLabel4.setText("Password");

        RegisterButton.setText("Register me");
        RegisterButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RegisterButtonActionPerformed(evt);
            }
        });

        jLabel5.setText("First Name");

        jLabel6.setText("Last Name");

        jLabel7.setText("Email Address");

        jLabel9.setText("Select User Category");

        UserTypeRadioButton.add(jRadioButton3);
        jRadioButton3.setText("Super User");

        UserTypeRadioButton.add(jRadioButton4);
        jRadioButton4.setText("User");

        javax.swing.GroupLayout registrationLayout = new javax.swing.GroupLayout(registration);
        registration.setLayout(registrationLayout);
        registrationLayout.setHorizontalGroup(
            registrationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, registrationLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(registrationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(74, 74, 74)
                .addGroup(registrationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(PasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(UserNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LastNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(EmailTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jRadioButton3)
                    .addComponent(jRadioButton4)
                    .addComponent(FirstNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(146, 146, 146))
            .addGroup(registrationLayout.createSequentialGroup()
                .addGap(142, 142, 142)
                .addComponent(RegisterButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        registrationLayout.setVerticalGroup(
            registrationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(registrationLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(registrationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(UserNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(registrationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(PasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(registrationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(FirstNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(registrationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(LastNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(registrationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(EmailTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(registrationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButton3)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(RegisterButton)
                .addContainerGap(7, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Registration", registration);

        CancelButton.setText("Exit Application");
        CancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CancelButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 959, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(106, 106, 106)
                .addComponent(jLabel1)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(256, 256, 256)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 441, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(VisitorButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(CancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(VisitorButton)
                    .addComponent(CancelButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(113, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void VisitorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_VisitorButtonActionPerformed
        // TODO add your handling code here:
        VisitorPage Eav = new VisitorPage("Guest", "Visitor");
        Eav.setVisible(true);
        cancel();
    }//GEN-LAST:event_VisitorButtonActionPerformed

    private void EmailFormatControl() {
        EmailTextField.setText("abc@example.com");
        EmailTextField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                EmailTextField.setText("");
            }
        });
    }
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
            //Statement stmt = conn.createStatement();
            Statement UserNameStmt = conn.createStatement();
            String UserN_query = "Select * from UserInfo";
            ResultSet UserN_result = UserNameStmt.executeQuery(UserN_query);
            boolean checkUserNameexist = false;
            boolean checkemailexist = false;
            while (UserN_result.next()) {
                String UN = UserN_result.getString("UserName");
                String Uemail = UserN_result.getString("email");
                if (UN.equalsIgnoreCase(U_name)) {
                    checkUserNameexist = true;
                }
                if (Uemail.equalsIgnoreCase(E_add) || E_add.equalsIgnoreCase("abc@example.com")) {
                    checkemailexist = true;
                }

            }
            Statement UserEmailStmt = conn.createStatement();
            String UserEmail_query = "SELECT email From BLACKLIST";
            ResultSet UserEmail_rs = UserEmailStmt.executeQuery(UserEmail_query);
            boolean BlackListedEmailFound = false;
            while (UserEmail_rs.next()) {
                if (UserEmail_rs.getString("email").equalsIgnoreCase(E_add)) {
                    BlackListedEmailFound = true;
                }
            }
            if (!U_name.isEmpty() && !P_word.isEmpty() && !F_name.isEmpty() && !L_name.isEmpty() && !E_add.isEmpty() && (jRadioButton3.isSelected() || jRadioButton4.isSelected())) {
                if (!checkUserNameexist) {
                    if (!checkemailexist) {
                        if (!BlackListedEmailFound) {
                            String query = "Insert Into UserInfo (username,password,email,firstname,lastname,point_balance,is_SU) Values ('" + U_name.toLowerCase() + "','" + P_word + "','" + E_add + "','" + F_name + "','" + L_name + "'," + 0 + "," + result + ")";
//                            String query = "Insert Into UserInfo (username,password,email,firstname,lastname,is_SU) Values (?,?,?,?,?,?)";
//                            PreparedStatement pst = null;
//                            pst = conn.prepareStatement(query);
//                            pst.setString(1, U_name.toLowerCase());
//                            pst.setString(2, P_word);
//                            pst.setString(3, E_add);
//                            pst.setString(4, F_name);
//                            pst.setString(5, L_name);
//                            pst.setBoolean(6, result);
//                            pst.execute();
                            //conn.close();
                            //"//," + 0+")";//,'" +0+"')";

                            //if (!U_name.isEmpty() && !P_word.isEmpty() && !F_name.isEmpty() && !L_name.isEmpty() && !E_add.isEmpty() && (jRadioButton3.isSelected() || jRadioButton4.isSelected())) {
                            if (jRadioButton3.isSelected())// && jRadioButton3.getText().equals("Super User"))
                            {
                                String input = JOptionPane.showInputDialog(null, "Enter Your Access code:", "Verification", JOptionPane.OK_OPTION);
                                if (input != null) {

                                    if (input.equalsIgnoreCase("Access"))//This is a hard coded Access code for Super User registration
                                    {
                                        JOptionPane.showMessageDialog(null, "Congratulations! Access granted, You have now Super User privilege.");
                                        //stmt.executeUpdate(query);
                                        UserNameStmt.executeUpdate(query);
                                        conn.close();
                                        cancel();
                                        GreetingPage gp = new GreetingPage();
                                        gp.setVisible(true);
                                    } else {
                                        //final JPanel jp = new JPanel();
                                        JOptionPane.showMessageDialog(null, "your access code is wrong !!!", "Warning", JOptionPane.WARNING_MESSAGE);
                                    }
                                }
//                            } else {
//                                //final JPanel jp = new JPanel();
//                                JOptionPane.showMessageDialog(null, "you clicked cancel !!! Try again", "Cancelled", JOptionPane.WARNING_MESSAGE);
//                            }
                            } else {

                                //stmt.executeUpdate(query);
                                UserNameStmt.executeUpdate(query);
                                conn.close();
                                //JOptionPane.showMessageDialog(null, "Congratulations, You are now Registered User.");
                                GreetingPage gp = new GreetingPage();
                                gp.setVisible(true);
                                cancel();
                            }

                        } else {
                            JOptionPane.showMessageDialog(null, "This is a black listed Email.\nYou can't register with this email address.", "Warning", JOptionPane.WARNING_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "This email address is not valid.");
                    }
                    //cancel();
                } else {
                    JOptionPane.showMessageDialog(null, "UserName already taken.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "All field needs to be filled out.");
            }
            //conn.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());

        }
    }//GEN-LAST:event_RegisterButtonActionPerformed

    private void CancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CancelButtonActionPerformed
        // TODO add your handling code here:
        JOptionPane.showMessageDialog(null, "Thank You for Using Ebook Application.");
        cancel();

        //tabpannedAH AH = new tabpannedAH();
        //AH.setVisible(true);
    }//GEN-LAST:event_CancelButtonActionPerformed

    private void SignInButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SignInButtonActionPerformed
        // TODO add your handling code here:

        login_access_check();
    }//GEN-LAST:event_SignInButtonActionPerformed

    
    
    
    private void SignInButtonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SignInButtonKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            login_access_check();
        }

    }//GEN-LAST:event_SignInButtonKeyPressed

    private void UserPassTextFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_UserPassTextFieldKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            login_access_check();
        }
        
    }//GEN-LAST:event_UserPassTextFieldKeyPressed

    private void UserNameTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_UserNameTextField1KeyReleased
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            login_access_check();
        }
    }//GEN-LAST:event_UserNameTextField1KeyReleased

    
    private void login_access_check(){
                

            String UserNameText = "";
            //String delim = ",";
            String PassWordText = "";
            UserNameText = this.UserNameTextField1.getText();
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
                boolean blacklisted = false;

                String usernametogui = "";
                String firstNametogui = "";
                if (!UserNameText.isEmpty() && !PassWordText.isEmpty()) {
                    while (User_result.next()) {
                        String UN = User_result.getString("userName");
                        String PW = User_result.getString("password");

                        if (UN.equalsIgnoreCase(UserNameText) && PW.equals(PassWordText)) {
                            checkmatch = true;
                            usernametogui = User_result.getString("userName");
                            firstNametogui = User_result.getString("firstName");
                            SuperUserType = User_result.getBoolean("is_SU");
                            blacklisted = User_result.getBoolean("in_blacklist");
                        }

                    }

                    if (checkmatch) {
                        if (SuperUserType) {
                            //JOptionPane.showMessageDialog(null, "You are logged in as  Super user.");
                            cancel();
                            //RegUserPage rup = new RegUserPage("SuperUser", usernametogui);
                            SuperUserFrame rup = new SuperUserFrame(usernametogui, firstNametogui);
                            rup.setVisible(true);
                        } else if (!SuperUserType && !blacklisted) {
                            //JOptionPane.showMessageDialog(null, "You are logged in as registered user.");
                            cancel();
                            //RegUserPage rup = new RegUserPage("Register User", usernametogui);
                            tabpannedUserPage rup = new tabpannedUserPage("User", firstNametogui, usernametogui);
                            rup.setVisible(true);
                        } else {
                            JOptionPane.showMessageDialog(null, "You are blacklisted, can't login.", "warning", JOptionPane.WARNING_MESSAGE);
                            //this.UserNameTextField.setText("");
                            this.UserPassTextField.setText("");
                            //cancel();
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid creditials, Try again.");
                        //this.UserNameTextField.setText("");
                        this.UserPassTextField.setText("");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "All fields are required.");

                }
            } catch (Exception e) {
                System.out.println(e.getMessage());

            }
        
    }
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
            java.util.logging.Logger.getLogger(tabpannedAH.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(tabpannedAH.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(tabpannedAH.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(tabpannedAH.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new tabpannedAH().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton CancelButton;
    private javax.swing.JTextField EmailTextField;
    private javax.swing.JTextField FirstNameTextField;
    private javax.swing.JTextField LastNameTextField;
    private javax.swing.JPasswordField PasswordField1;
    private javax.swing.JLabel PasswordLabel;
    private javax.swing.JButton RegisterButton;
    private javax.swing.JButton SignInButton;
    private javax.swing.JLabel UserNameLabel;
    private javax.swing.JTextField UserNameTextField;
    private javax.swing.JTextField UserNameTextField1;
    private javax.swing.JPasswordField UserPassTextField;
    private javax.swing.ButtonGroup UserTypeRadioButton;
    private javax.swing.JButton VisitorButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JRadioButton jRadioButton4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JPanel login;
    private javax.swing.JPanel registration;
    // End of variables declaration//GEN-END:variables

    public void cancel() {
        WindowEvent winClosing = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(winClosing);
    }

}
