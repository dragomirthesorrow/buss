/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buss;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author oadm
 */
public class UserCli extends Thread{
    public void run(){
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while(Buss.running){
            try{
                String s = reader.readLine();
                if(s.equals("make")){
                    BackUp bu = new BackUp();
                    bu.make("");
                }else if(s.equals("show")){
                    BackUp bu = new BackUp();
                    bu.show();
                }else if(s.equals("stop")){
                    Buss.stop();
                }else{
                    char[] arr = s.toCharArray();
                    int space = 0;
                    for(int i=0;i<arr.length;i++){
                        if(arr[i]==' '){
                            space=i;
                        }
                    }
                    
                    String action = s.substring(0, space);
                    if(action.equals("delete")){
                        BackUp bu = new BackUp();
                        bu.delete(Integer.parseInt(s.substring(space+1)));
                    }else if(action.equals("restore")){
                    BackUp bu = new BackUp();
                    bu.restore(Integer.parseInt(s.substring(space+1)));
                    }else if(action.equals("make")){
                        BackUp bu = new BackUp();
                        bu.make(s.substring(space+1));
                    }
                }
            }catch(IOException e){
                
            }
        }
    }
}
