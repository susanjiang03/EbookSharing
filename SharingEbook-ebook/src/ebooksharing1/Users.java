/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ebooksharing1;

/**
 *
 * @author indrajit
 */
public class Users {
    private String UserName;
    private String Password;
    private String FirstName;
    private String LastName;
    private String Email;
    private String ID;
    private String UserType;
    
    public Users(String UN, String PW, String FN, String LN, String Eml, String id, String UT){
        this.UserName = UN;
        this.Password = PW;
        this.FirstName = FN;
        this.LastName = LN;
        this.Email = Eml;
        this.ID = id;
        this.UserType = UT;
    }
    public boolean accessibletotheApp(){
        return true;
    }
    
    public String getUserName(){
        return UserName;
    }
//    public void setUserName(String UN){
//        this.UserName = UN;
//    }
    public String getPassWord(){
        return Password;
    }
    public String getUserType(){
        return UserType;
    }
    public void setPassWord(String PassW){
        this.Password = PassW;
    }
    
}
