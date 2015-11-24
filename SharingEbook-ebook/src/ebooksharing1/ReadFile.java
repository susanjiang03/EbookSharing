/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ebooksharing1;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 *
 * @author indrajit
 */
public class ReadFile {
    String path;
    public ReadFile(String file_path){
        path = file_path;
    }
    BufferedReader bufRead = null;
    public BufferedReader OpenFile(){
       try{   
            FileReader inputFile = new FileReader(path);
            bufRead = new BufferedReader(inputFile);
            
            //String line;
            
//            while((line = bufRead.readLine()) != null){
//                //System.out.println(line);
//            }
                   return bufRead;

        }
        catch(Exception e)
        {
            System.out.println("Error"+ e.getMessage());
       
        }
                   //bufRead.close();
              return bufRead;


    }
    
}
