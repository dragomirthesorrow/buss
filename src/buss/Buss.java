/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buss;

import java.util.ArrayList;

/**
 *
 * @author oadm
 */
public class Buss {

    /**
     * @param args the command line arguments
     */
    static boolean running;
    static ArrayList<Thread> threads = new ArrayList<Thread>();
    public static void main(String[] args) {
        // TODO code application logic here
        try{
        if(args[0].equals("start")){
            start();
        }else if(args[0].equals("make")){
            
            if(args.length>1){
                String commit="";
                for(int i=1;i<args.length;i++){
                    commit=commit+args[i]+" ";
                }
                BackUp bu = new BackUp();
                bu.make(commit);
            }else{
                BackUp bu = new BackUp();
                bu.make("");
            }
        }else if(args[0].equals("restore")){
            int id = Integer.parseInt(args[1]);
            BackUp bu = new BackUp();
            bu.restore(id);
        }else if(args[0].equals("delete")){
            int id = Integer.parseInt(args[1]);
            BackUp bu = new BackUp();
            bu.delete(id);
        }else if(args[0].equals("show")){
            BackUp bu = new BackUp();
            bu.show();
            
        }else{
            System.out.println("Command sintax is incorrect. arguments: start, stop, make, restore id, show, delete id");
        }
        }catch(ArrayIndexOutOfBoundsException e){
            //Configuration cfg = new Configuration();
            //System.out.println(cfg.getBUFolder());
            System.out.println("There is no parameters! arguments: start, stop, make, restore id, show, delete id.");
            //Restore rs = new Restore(79);
            //rs.restore();
            start();
        }
    }
    static void start(){
        Configuration cf = new Configuration();
            int to=cf.getTimeOut();
            UserCli cli = new UserCli();
            cli.start();
            threads.add(cli);
            Buss.running=true;
            while(Buss.running==true){
                try{
                    Check ch = new Check();
                    ch.checkAll();
                    Thread.sleep((long)to*1000);
                }catch(InterruptedException e){
                    
                }
            }
        }
        static void stop(){
            Buss.running=false;
            for(Thread thr : Buss.threads){
                thr.stop();
            }
        }
}
