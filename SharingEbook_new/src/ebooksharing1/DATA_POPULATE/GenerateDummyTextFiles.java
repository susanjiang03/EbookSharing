/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ebooksharing1.DATA_POPULATE;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;

/**
 *
 * @author lingshanjiang
 */
public class GenerateDummyTextFiles {
    
    public static void main (String args[]) throws IOException {
        
        /*
      
        File thisfile=new File(".");
         String   currentDirectory=thisfile.getAbsolutePath();
         System.out.println ("Current dir : " + thisfile.getCanonicalPath());
    
        */
        
        //set how many files you want to generate
        int number_of_files=50;
       //characters
         String c="abcdef ghijklm nopq rst uvw xyz";
    
       //generate files 
        for(int file=1;file<=number_of_files;file++){
            
     
                //initial dummy_text of the file
                String  dummy_text="";
     
                //random num in 8000,10000
                int num=(int)(Math.random()*8000+2000);
                
               //append random character to random_word
                for(int i=1;i<num;i++){
                  
                    dummy_text+=c.charAt((int)(Math.random()*(c.length())));
                } 
        
                 //write files  bookfile_file.txt
              String fileName="bookfile_"+file+".txt";
              
               try {  
                
                BufferedWriter fw=new BufferedWriter(new FileWriter(fileName)); 
                //write dummy_text to file:
                fw.write(dummy_text);
                fw.close();
                //System.out.println("Write bookfile_"+file+"  sucessfully:\n"+dummy_text);
                System.out.println("Write bookfile_"+file+"  sucessfully.");
                
            
            }catch(IOException e){
                e.printStackTrace();
            }
        
                }
                    
                }
            }
        
    

