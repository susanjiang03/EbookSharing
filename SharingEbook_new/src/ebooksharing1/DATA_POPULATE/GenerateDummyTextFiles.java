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
         String c="abcdefghijklmnopqrstuvwxyz";
    
       //generate files 
        for(int file=1;file<=number_of_files;file++){
            
     
                //initial dummy_text of the file
                String  dummy_text="";
      
                //get a random number of words in range (2000,3000);
                int number_of_words=(int)(Math.random()*2000+1000);
                
                //generate random words, append to text
                for(int word=1;word<number_of_words;word++){
                    
                    String dummy_word="";
                    
                    //generate random character for each word in length(0,1)
                    for(int length=1;length<(int)(Math.random()*20);length++){
                        
                        //append random character to random_word
                        dummy_word+=c.charAt((int)(Math.random()*26));
                        
                    }
                    
                    //append to dummy_text
                    dummy_text+=dummy_word+" ";
                  
                    
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
        
    

