/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buss;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author oadm
 */
public class MakeBU {
    private String commit;
    MakeBU(){
        
    }
    MakeBU(String com){
        this.commit=com;
    }
    void make(){
        Configuration cf = new Configuration();
        String bupsfolder=cf.getBUFolder();
        String root_dir = cf.getRootDir();
        //1 создаем дамп базы
        //2 копируем файлы
        //3 создаем архив и регистрируем.
        
        //1
        //1.1 Получаем дату и время
        long dateUp=new Date().getTime();
        SimpleDateFormat sqlFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        String sqlDate = sqlFormat.format(dateUp);
        SimpleDateFormat sqlFormat1 = new SimpleDateFormat("YYYY-MM-dd_HH:mm:ss");
        String sqlDate1 = sqlFormat1.format(dateUp);
        //1.2 делаем дамп системной утилитой (быстрее, надежнее)

        ProcessBuilder proc = new ProcessBuilder("bash","-c","mysqldump -u"+cf.mysqlAcc()[0]+" -hlocalhost "+cf.getDBName()+" --password="+cf.mysqlAcc()[1]+" > /etc/buss/"+sqlDate1+".sql");
        proc.redirectErrorStream(true);
        try{
            
          Process process = proc.start();
          //process.getOutputStream()
            // читаем стандартный поток вывода
           // и выводим на экран
           InputStream stdout = process.getInputStream();
           InputStreamReader isrStdout = new InputStreamReader(stdout);
           BufferedReader brStdout = new BufferedReader(isrStdout);
           String line = null;
             while((line = brStdout.readLine()) != null) {
                 System.out.println(line);
             }
            int exitVal = process.waitFor();
             }catch(IOException | InterruptedException e){//}catch(IOException | InterruptedException e){

             }
        //2.
        //2.1 создаем временную папку
        String tpath="/etc/buss/arch";
        File tempPath = new File(tpath);
        if(tempPath.exists()==false){
        tempPath.mkdir();}
        //2.2 копируем файлы
        
        try{
            File src = new File(root_dir);
            File dst = new File(tpath);
            moveDirectory(src,dst);
            //Files.copy(rd.toPath(), tempPath.toPath());
            File sql = new File("/etc/buss/"+sqlDate1+".sql");
            File tempSql = new File("/etc/buss/arch/"+sqlDate1+".sql");
            Files.copy(sql.toPath(), tempSql.toPath());
            sql.delete();
        }catch(IOException e){
           System.out.println(e); 
           e.printStackTrace();
        }
        
        
        //3.
        //3.1 Архив
       // System.out.println(bupsfolder+sqlDate1+".tar");
        ProcessBuilder proctar = new ProcessBuilder("bash","-c","tar -cf "+bupsfolder+sqlDate1+".tar /etc/buss/arch");
        try{
            Process processtar = proctar.start();
            InputStream stdout = processtar.getInputStream();
           InputStreamReader isrStdout = new InputStreamReader(stdout);
           BufferedReader brStdout = new BufferedReader(isrStdout);
           String line = null;
             while((line = brStdout.readLine()) != null) {
                 System.out.println(line);
             }
            int exitVal = processtar.waitFor();
        }catch(IOException | InterruptedException e){
            
        }
        //3.2 Удаляем темп
        //tempPath.delete();
        deleteDirectory(tempPath);
        //3.3 Регистрируем архив в базе.
        SQL ins = new SQL();
        ins.insertUpdate("insert into `list` (`date_created`,`res`) values ('"+sqlDate+"','"+this.commit+"')");
        
        
        
    }    
    public static void deleteDirectory(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i=0; i<children.length; i++) {
                File f = new File(dir, children[i]);
                deleteDirectory(f);
            }
            dir.delete();
        } else dir.delete();
    }
    static void moveDirectory(File src, File dst){
        //Configuration cf = new Configuration();
        //String root = cf.getRootDir();
        //String app = "/etc/buss/arch";
        if(src.isDirectory()){
        String files[] = src.list();
        //File folder = new File();
        dst.mkdir();
            for(String file : files){
                File fl = new File(src.getAbsolutePath()+"/"+file);
                File nf = new File(dst.getAbsolutePath()+"/"+file);
                moveDirectory(fl,nf);
            }
        }else{
            try{
            Files.copy(src.toPath(), dst.toPath());
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    } 
}
