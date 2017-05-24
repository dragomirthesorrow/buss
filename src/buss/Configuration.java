/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buss;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 *
 * @author oadm
 */
public class Configuration {
    static File cfgFile;
    public Configuration(){
        this.cfgFile=new File("/etc/buss/buss.cfg");
    }
    public Configuration(String cfg_path){
        this.cfgFile=new File(cfg_path);
    }
    static int getTimeOut(){
        int time_out=0;
        try{
                ArrayList<String> list = new ArrayList<>();
                //File file = new File(getServiceDirectory()+"config/blind.config");
                BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(Configuration.cfgFile), "UTF-8"));
                String line = null;
                //StringBuilder stringBuilder = new StringBuilder();
                //String ls = System.getProperty("line.separator");
                while( ( line = reader.readLine() ) != null ) {
                    list.add(line);
                }
                reader.close();
                String directive = list.get(12).substring(21);
                time_out=Integer.parseInt(directive);
            //System.out.println("Directive: "+dir);
            }catch(IOException e){
                System.out.println(e);
            }
        return time_out;
    }
    static String getBUFolder(){
        String folder="";
        try{
                ArrayList<String> list = new ArrayList<>();
                //File file = new File(getServiceDirectory()+"config/blind.config");
                BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(cfgFile), "UTF-8"));
                String line = null;
                //StringBuilder stringBuilder = new StringBuilder();
                //String ls = System.getProperty("line.separator");
                while( ( line = reader.readLine() ) != null ) {
                    list.add(line);
                }
                reader.close();
                String directive = list.get(4).substring(17);
                folder=directive;
            //System.out.println("Directive: "+dir);
            }catch(IOException e){
                System.out.println(e);
            }
        return folder;
    }
    static String getRootDir(){
        String folder="";
        try{
                ArrayList<String> list = new ArrayList<>();
                BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(cfgFile), "UTF-8"));
                String line = null;

                while( ( line = reader.readLine() ) != null ) {
                    list.add(line);
                }
                reader.close();
                String directive = list.get(3).substring(20);
                folder=directive;
            }catch(IOException e){
                System.out.println(e);
            }
        return folder;
    }
    static String getDBName(){
        String folder="";
        try{
                ArrayList<String> list = new ArrayList<>();
                BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(cfgFile), "UTF-8"));
                String line = null;

                while( ( line = reader.readLine() ) != null ) {
                    list.add(line);
                }
                reader.close();
                String directive = list.get(10).substring(15);
                folder=directive;
            }catch(IOException e){
                System.out.println(e);
            }
        return folder;
    }
    static String[] mysqlAcc(){
        String[] acc=new String[2];
        acc[0]="";
        try{
                ArrayList<String> list = new ArrayList<>();
                BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(Configuration.cfgFile), "UTF-8"));
                String line = null;

                while( ( line = reader.readLine() ) != null ) {
                    list.add(line);
                }
                reader.close();
                String directive = list.get(8).substring(11);
                acc[0]=directive;
                //System.out.println("Directive: "+directive);
            }catch(IOException e){
                System.out.println(e);
            }
        acc[1]="";
        try{
                ArrayList<String> list = new ArrayList<>();
                BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(Configuration.cfgFile), "UTF-8"));
                String line = null;

                while( ( line = reader.readLine() ) != null ) {
                    list.add(line);
                }
                reader.close();
                String directive = list.get(9).substring(15);
                acc[1]=directive;
                //System.out.println("Directive: "+directive);
            }catch(IOException e){
                System.out.println(e);
            }
        return acc;
        
    }
    static int getDepth(){
        int depth=0;
        try{
                ArrayList<String> list = new ArrayList<>();
                //File file = new File(getServiceDirectory()+"config/blind.config");
                BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(Configuration.cfgFile), "UTF-8"));
                String line = null;
                //StringBuilder stringBuilder = new StringBuilder();
                //String ls = System.getProperty("line.separator");
                while( ( line = reader.readLine() ) != null ) {
                    list.add(line);
                }
                reader.close();
                String directive = list.get(16).substring(22);
                depth=Integer.parseInt(directive);
            //System.out.println("Directive: "+dir);
            }catch(IOException e){
                System.out.println(e);
            }
        return depth;
    }
    static int getTime(){
        int time=0;
        try{
                ArrayList<String> list = new ArrayList<>();
                //File file = new File(getServiceDirectory()+"config/blind.config");
                BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(Configuration.cfgFile), "UTF-8"));
                String line = null;
                //StringBuilder stringBuilder = new StringBuilder();
                //String ls = System.getProperty("line.separator");
                while( ( line = reader.readLine() ) != null ) {
                    list.add(line);
                }
                reader.close();
                String directive = list.get(15).substring(18);
                time=Integer.parseInt(directive);
            //System.out.println("Directive: "+dir);
            }catch(IOException e){
                System.out.println(e);
            }
        return time;
    }
}
