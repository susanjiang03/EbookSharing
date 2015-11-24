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
public class SuperUser extends Users{
    public SuperUser(String userName, String passWord, String FirstN, String LastN, String Em, String ID, String UT){
        super(userName, passWord, FirstN, LastN, Em, ID, UT);
    }
    @Override
    public boolean accessibletotheApp(){
        return true;
    }
    
    public boolean hasAccesstoOtherUsersProfile(){
        return true;
    }
    
}
